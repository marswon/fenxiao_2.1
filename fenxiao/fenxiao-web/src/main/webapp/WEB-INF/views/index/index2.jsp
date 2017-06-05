<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link type="image/x-icon" href="${ctx}/static/images/favicon_kd.ico"
	rel="shortcut icon"/>
<!-- 引入easyUi默认的CSS格式--蓝色 -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/bootstrap/easyui.css" />
<!-- 引入easyUi小图标 -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/styles/layoutxx.css" />
<!-- 引入Jquery -->
<script type="text/javascript"
	src="${ctx}/static/easyui/jquery-1.8.3.js"></script>
<!-- 引入Jquery_easyui -->
<script type="text/javascript"
	src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<!-- 引入easyUi国际化--中文 -->
<script type="text/javascript"
	src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<title>可当科技</title>
<style type="text/css">
</style>
<script>
	$(function() {
		//document.getElementById("accordionMenu").style.display=="none";
		document.getElementById("accordionMenu").style.display = "none";
		//单击某一菜单改变其背景样式
		var centerIframe = $('#centerIframe');
		$('#accordionMenu ul li').click(
				function() {
					var _this = $(this);
					_this.addClass('li_selected_bgcolor');
					var _a = _this.find('a');
					centerIframe.attr('src', _a.attr('rel'));
					$('#accordionMenu ul li').not(this).removeClass(
							'li_selected_bgcolor');
				});
		//$("#accordionMenu").style.display="none";
		//文档加载后选中某一菜单并加载内容
		centerIframe.attr('src', '${ctx}/index/toIndexPage');
		$('#accordionMenu').accordion('select', '首页');
		//删除当前用户不需展示的一级目录
		$.getJSON("${ctx}/privilege/findAllPrivilegeGroupById", {}, function(
				data) {
			if (data.resultCode == -1) {
				$.messager.alert('消息', data.resultMsg);
			} else {
				document.getElementById("accordionMenu").style.display = "";
				$.each(data.content, function(index, element) {
					$("#accordionMenu").accordion('remove', element.text);
				});
			}
		});

	});

	function isWorking() {
		var adminId = $('#adminId').html();
		$("#hiddenAdminId").val(adminId);
		$('#admininfo_form')
				.form(
						'submit',
						{
							url : "${ctx}/myWorkflow/userAuth/isWorking",
							onSubmit : function(param) {
							},
							success : function(data) {
								var d = eval("(" + data + ")");
								if (d.resultCode == 1) {
									$("#isWorking_div")
											.html(
													'<input id="offLine" type="button" onclick="stopWorking()" value="停止领取">');
									$.messager.alert("消息", "领取任务操作成功");
								} else {
									if (d.resultMsg) {
										$.messager.alert("消息", d.resultMsg);
									} else {
										$.messager.alert("消息", "未知错误");
									}
								}
							}
						});
	}

	function stopWorking() {
		$('#admininfo_form')
				.form(
						'submit',
						{
							url : "${ctx}/myWorkflow/userAuth/offLine",
							onSubmit : function(param) {
							},
							success : function(data) {
								var d = eval("(" + data + ")");
								if (d.resultCode == 1) {
									$("#isWorking_div")
											.html(
													'<input id="working" type="button" onclick="isWorking()" value="领取任务">');
									$.messager.alert("消息", "停止领取操作成功");
								} else {
									if (d.resultMsg) {
										$.messager.alert("消息", d.resultMsg);
									} else {
										$.messager.alert("消息", "未知错误");
									}
								}
							}
						});
	}

	function logout() {
		$.ajax({
			type : "GET",
			url : "${ctx}/myWorkflow/userAuth/offLine",
			success : function(data) {
				if (data.resultCode == 1) {
					window.location.href = "${ctx}/logout";
				} else {
					if (data.resultMsg) {
						$.messager.alert("消息", data.resultMsg);
					} else {
						$.messager.alert("消息", "未知错误");
					}
				}
			},
			failure : function(data) {
			}
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<!-- 主页顶部 -->
	<div data-options="region:'north',border:false" style="height: 75px;">
		<div class="header">
			<div class="sy_logo">
				<span>可当科技</span>
			</div>
			<!-- 主页顶部右边Div -->
			<div class="login_info" style="width: 650px;margin-right: 30px;text-align:right">
				<span>账户余额:<font color="read" size="4rem"
					style="margin-left: 10px;">¥<fmt:formatNumber
							value="${fxEnterprise.balance/1000}" pattern="#,##0.00#" />
				</font>
				</span>
				<span>&nbsp;&nbsp;冻结金额:<font color="read" size="4rem"
								 style="margin-left: 10px;">¥<fmt:formatNumber
						value="${fxEnterprise.creditTopBalance/1000}" pattern="#,##0.00#" />
				</font> <font style="margin-left: 10px;"> </font>
				</span>
				<shiro:principal property="name" />
				,你好!<font style="margin-left: 10px;"> </font> <a
					href="${ctx}/logout"
					style="text-decoration: none; font-weight: bold;"><font
					 color="read">退出</font></a>
			</div>
			<form id="admininfo_form">
				<c:if test="${adminId!=1}">
					<div id="isWorking_div" style="margin-top: 25px;">
						<%-- <c:if test="${isWorking==0}">
					<input id="working" type="button" onclick="isWorking()" value="领取任务">
					</c:if>
					<c:if test="${isWorking==1}">
					<input id="offLine" type="button" onclick="stopWorking()" value="停止领取">
					</c:if> --%>
					</div>
				</c:if>
			</form>
		</div>
	</div>
	<!-- 主页主体左边显示菜单部分  -->
	<div data-options="region:'west',border:false"
		style="width: 200px; height: 290px">
		<div id="accordionMenu" class="easyui-accordion"
			data-options="border:false,animate:true">
			<div title="首页" style="overflow: hidden; border: none;">
				<ul>
					<li class="li_selected_bgcolor"><a href="javascript:;"
						rel="${ctx}/index/toIndexPage">首页</a></li>
				</ul>
			</div>
			<div title="用户管理" style="overflow: hidden; border: none;">
				<ul>
					<shiro:hasPermission name="100010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/userManage/userList">用户管理</a></li>
					</shiro:hasPermission>
				</ul>
			</div>
			<div title="合作商管理" style="overflow: hidden; border: none" id="200000">
				<ul>
					<shiro:hasPermission name="200010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/api/enterprise/getEnterpriseListView">流量话费合作商</a></li>
					</shiro:hasPermission>
				</ul>
				<ul>
					<shiro:hasPermission name="200020">
						<li class=""><a href="javascript:;"
							rel="${ctx}/api/enterprise/getGasEnterpriseListView">加油卡合作商</a></li>
					</shiro:hasPermission>
				</ul>
				<ul>
					<shiro:hasPermission name="200030">
						<li class=""><a href="javascript:;"
										rel="${ctx}/api/enterprise/getEnterpriseListBalanceView">合作商余额</a></li>
					</shiro:hasPermission>
				</ul>
			</div>
			<div title="平台产品管理" style="overflow: hidden; border: none"
				id="product">
				<ul>
					<shiro:hasPermission name="300010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/product/productView">流量话费平台产品</a></li>
					</shiro:hasPermission>
				</ul>
				<ul>
					<shiro:hasPermission name="300030">
						<li class=""><a href="javascript:;"
							rel="${ctx}/product/gasCardProductView">加油卡平台产品</a></li>
					</shiro:hasPermission>
				</ul>
				<ul>
					<shiro:hasPermission name="300020">
						<li class=""><a href="javascript:;"
							rel="${ctx}/productGroup/productGroupView">平台产品组管理</a></li>
					</shiro:hasPermission>
				</ul>
			</div>
			<div title="供应商管理" style="overflow: hidden; border: none">
				<ul>
					<shiro:hasPermission name="400010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/operatorProduct/operatorProduct">流量话费供应商产品管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="400012">
						<li class=""><a href="javascript:;"
							rel="${ctx}/operatorProduct/gasCardOperatorProduct">加油卡供应商产品管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="400011">
						<li class=""><a href="javascript:;"
							rel="${ctx}/channelManege/toChannelManege">供应商通道管理</a></li>
					</shiro:hasPermission>
					
				</ul>
			</div>
			<div title="订单管理" style="overflow: hidden; border: none">
				<ul>
					<shiro:hasPermission name="500010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/waitOrder/openWaitOrderView">待处理订单管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="500011">
						<li class=""><a href="javascript:;"
							rel="${ctx}/order/toOrder">流量话费订单记录管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="500013">
						<li class=""><a href="javascript:;"
							rel="${ctx}/order/toGasCardOrder">加油卡订单记录管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="500014">
						<li class=""><a href="javascript:;"
							rel="${ctx}/taobaoOrder/toOrder">淘宝订单记录管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="500012">
						<li class=""><a href="javascript:;"
							rel="${ctx}/order/enterpriseOrderView">订单记录</a></li>
					</shiro:hasPermission>
				</ul>
			</div>

			<div title="财务管理" style="overflow: hidden; border: none">
				<ul>
					<shiro:hasPermission name="600010">
						<li class=""><a href="javascript:;"
										rel="${ctx}/addBalance/openAddBalanceView">加款申请</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="600011">
						<li class=""><a href="javascript:;"
							rel="${ctx}/financialManagement/toFinancialManageMent">加款管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="600012">
						<li class=""><a href="javascript:;"
							rel="${ctx}/financialManagement/toFinancialStatements">财务报表管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="600013">
						<li class=""><a href="javascript:;"
							rel="${ctx}/financialManagement/toDownstreamFinancialStatements">财务报表</a></li>
					</shiro:hasPermission>

				</ul>
			</div>
			<div title="参数配置" style="overflow: hidden; border: none">
				<ul>
					<shiro:hasPermission name="700010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/channelManege/toChannelManege">通道配置</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="700011">
						<li class=""><a href="javascript:;"
							rel="${ctx}/paramConfig/tdkgPage">通道开关</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="700012">
						<li class=""><a href="javascript:;"
										rel="${ctx}/cami/openCamiView">卡密管理</a></li>
					</shiro:hasPermission>
                    <shiro:hasPermission name="700013">
                        <li class=""><a href="javascript:;"
                                        rel="${ctx}/handwork/openHandWordView">手工充值</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="700014">
                        <li class=""><a href="javascript:;"
                                        rel="${ctx}/paramConfig/toSendMessage">消息推送</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="700015">
                        <li class=""><a href="javascript:;"
                                        rel="${ctx}/paramConfig/toBlackWhite">黑白名单</a></li>
                    </shiro:hasPermission>
					<shiro:hasPermission name="700016">
						<li class=""><a href="javascript:;"
										rel="${ctx}/strategyConfig/openProductAreaStrategyConfigView">地市路由策略</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="700018">
                        <li class=""><a href="javascript:;"
                                        rel="${ctx}/paramConfig/toRepeatRecharge">复充配置</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="700019">
                        <li class=""><a href="javascript:;"
                                        rel="${ctx}/paramConfig/toTaobaoConfig">淘宝店宝贝配置</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="700020">
                        <li class=""><a href="javascript:;"
                                        rel="${ctx}/taobaoActivityConfig/toTaobaoActivityConfig">淘宝店活动配置</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="700021">
                        <li class=""><a href="javascript:;"
                                        rel="${ctx}/handWorkValidate/openHandWorkValidateView">手工充值审核</a></li>
                    </shiro:hasPermission>
				</ul>
			</div>
			<div title="个人信息" style="overflow: hidden; border: none">
				<ul>
					<shiro:hasPermission name="800010">
						<li class=""><a href="javascript:;"
                             rel="${ctx}/personalInformation/toChangePassword">修改密码</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="800020">
						<li class=""><a href="javascript:;"
                             rel="${ctx}/personalInformation/toShowProduct">已开通产品</a></li>
					</shiro:hasPermission>
				</ul>
			</div>
			<div title="文档API" style="overflow: hidden; border: none">
				<ul>
					<shiro:hasPermission name="1000010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/interfaceDocument/openDocument">API (2.0.2)</a></li>
					</shiro:hasPermission>
				</ul>
			</div>
			<div title="系统管理" style="overflow: hidden; border: none">
				<ul>
					<shiro:hasPermission name="900010">
						<li class=""><a href="javascript:;"
							rel="${ctx}/privilege/addprivilege">权限分配</a></li>
					</shiro:hasPermission>
				</ul>
			</div>
		</div>
	</div>
	<!-- 主页主体右边显示内容部分 -->
	<div data-options="region:'center',border:false">
		<iframe id="centerIframe" name="centerIframe" frameborder="0"
			marginheight="0" marginwidth="0"
			style="width: 100%; height: 100%; overflow: hidden;" scrolling="yes"></iframe>
	</div>
	<!-- 主页底部 -->
	<div data-options="region:'south'"
		style="overflow-x: hidden; padding: 0px; height: 25px; border-bottom: 0">
		<table style="width: 100%; height: 100%;">
			<tr>
				<td align="center"><font color="#525252;"
					style="font-size: 12px; font-family: arial;">杭州可当科技有限公司版权所有 @2016 [ 增值电信业务经营许可证 浙 B2-20150429 ]
ICP 证号:浙 ICP 备 13018300号</font></td>
			</tr>
		</table>
	</div>
</body>
</html>