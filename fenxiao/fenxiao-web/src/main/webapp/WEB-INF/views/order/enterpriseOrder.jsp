<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title></title>
<style type="text/css" media=screen>
.text_red {
	color: red;
	font-size: 1.5rem;
	vertical-align: middle
}

.active {
	background: #00b5ff;
	border: 1px solid #fff;
	color: red;
}

.datagrid-cell {
	line-height: 21px;
}

.remo_li_style {
	list-style-type: none;
}
</style>
<script type="text/javascript">
	var flag = false;

	jQuery(function($) {
		var date = new Date().format("yyyy-MM-dd 00:00");
		$('#startTime1').datetimebox("setValue", date);
		$("#orderList")
				.datagrid(
						{
							url : '${ctx}/order/getOrderListByEnterprise',
							method : 'get',
							nowrap : false,
							striped : true,
							remoteSort : false,
							pageSize : 20,
							fit : true,
							queryParams : {
								startTime : date
							},
							singleSelect : true,//是否单选  
							pagination : true,//分页控件  
							rownumbers : true,//行号 
							fitColumns : true,//列宽自动填充满表格
							checkOnSelect : false,
							selectOnCheck : false,
							toolbar : "#xxDgTb",//工具栏
							loadFilter : function(data) {
								console.log(data);
								if (data.content) {
									return {
										total : data.totalElements,
										rows : data.content
									};
								} else {
									if (typeof data.length == "number"
											&& typeof data.splice == "function") {
										return {
											total : data.length,
											rows : data
										};
									} else {
										return data;
									}
								}
							},
							columns : [ [
									{
										field : 'eId',
										title : '企业名称',
										width : 130,
										align : "center",
										formatter : function(val, row, index) {
											return row.fxEnterprise.name;
										}
									},
									{
										field : 'downstreamOrderNo',
										title : '订单号',
										width : 270,
										align : "center"
									},
									{
										field : 'mobile',
										title : '手机号',
										width : 150,
										align : "center"
									},
									{
										field : 'clientSubmitTime',
										title : '客户下单时间',
										width : 140,
										align : "center"
									},
									/* {
										field : 'systemSubmitTime',
										title : '系统提交时间',
										width : 140,
										align : "center"
									}, */
									{
										field : 'reportTime',
										title : '回调时间',
										width : 140,
										align : "center"
									},
									{
										field : 'yysName',
										title : '运营商',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
// 											if (row.fxProduct) {
												var yys = row.yysTypeId;
												if (yys == "1") {
													return '<span style="color:white;background:green">'
															+ showProvince(row.provinceId)
															+ "电信" + '</span>';
												} else if (yys == "2") {
													return '<span style="color:white;background:green">'
															+ showProvince(row.provinceId)
															+ "移动" + '</span>';
												} else if (yys == "3") {
													return '<span style="color:white;background:green">'
															+ showProvince(row.provinceId)
															+ "联通" + '</span>';
												}else if (yys == "-1") {
													return '<span style="color:white;background:green">'
													+ "其他" + '</span>';
												}
// 											} else {
// 												return "";
// 											}
										}
									},
									{
										field : 'proId',
										title : '产品名称',
										width : 190,
										align : "center",
										formatter : function(val, row, index) {
											if (row.fxProduct) {
												return row.fxProduct.name;
											} else {
												return "";
											}
										}
									},
									{
										field : 'downstreamStatus',
										title : '订单状态',
										width : 120,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return '<span style="color:white;background:green">成功</span>';
											} else if (val == 1) {
												return '<span style="color:white;background:red">失败</span>';
											} else if (val == 2) {
												return '<span style="color:white;background:orange">充值中</span>';
											} else if (val == 3) {
												return "提交成功";
											}
										}
									}, {
										field : 'costMoney',
										title : '实际消费',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											var value = val * 0.001;
											return "¥" + value.toFixed(3);
										}
									}, {
										field : 'afterBalance',
										title : '账户余额',
										width : 130,
										align : "center",
										formatter : function(val, row, index) {
											var value = val * 0.001;
											return "¥" + value.toFixed(2);
										}
									},
							/* 	  {
									field : 'id',
									title : '操作',
									width : 150,
									align : "center",
									formatter : function(val, row, index) {
											return '<span style="color:white;background:green">推送成功</span><br/><span style="color:white;background:red">推送失败</span>';
									}
								},  */
							] ],
						});
		$('#dfxqDlg').dialog({
			title : '会员资料',
			fit : true,
			draggable : false,
			closed : true,
			cache : false,
			modal : true
		});
		$("#search_id").click(function() {
			var params = $('#orderList').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#orderList').datagrid('load');
		});
		/* $('#webinformation1').dialog({
			title : "平台产品编辑",
			width : 950,
			height : 590,
			closed : true,
			cache : false,
			modal : true,
			center : true,
		}); */
		
		
		$('#webinformation1').dialog({
			iconCls:'icon-edit',
			title : "&ensp;平台产品编辑",
			width : 800,
			height : 600,
			closed : true,
			cache : false,
			modal : true,
			center : true,
			resizable:true,
			minimizable:true,  
			maximizable:true,  
		});
	});

	function closeDf() {
		$('#dfxqDlg').dialog('close');
	}

	function showProvince(provinceId) {
		var map = {
			'000' : '全国',
			'010' : '北京',
			'020' : '广东',
			'021' : '上海',
			'022' : '天津',
			'023' : '重庆',
			'025' : '江苏',
			'026' : '青海',
			'027' : '海南',
			'028' : '四川',
			'029' : '陕西',
			'030' : '山西',
			'035' : '河北',
			'039' : '河南',
			'040' : '内蒙古',
			'041' : '辽宁',
			'045' : '吉林',
			'046' : '黑龙江',
			'050' : '安徽',
			'055' : '浙江',
			'059' : '福建',
			'060' : '山东',
			'070' : '广西',
			'071' : '湖北',
			'073' : '湖南',
			'075' : '江西',
			'080' : '云南',
			'085' : '贵州',
			'089' : '西藏',
			'090' : '宁夏',
			'093' : '甘肃',
			'095' : '新疆'
		};
		return map[provinceId];
	}
	/**
	 *添加平台产品
	 **/
	function add_product(id) {
		if (id == 0) {
			$('#webinformation1').dialog('open');
		} else {
			$.getJSON("${ctx}/productGroup/getProductGroupById", {
				id : id
			}, function(data) {
				if (data.resultCode == -1) {
					$.messager.alert('消息', data.resultMsg);
				} else {
					console.log(data);
					$("#productGroupName").val(data.content.name);
					$("#desc").val(data.content.description);
					$("#id").val(data.content.id);
					$('#webinformation1').dialog('open');
				}
			});
		}
	}

	function loadHk() {
		var params = $('#orderList').datagrid('options').queryParams;
		var fields = $('#queryHkForm').serializeArray();
		$.each(fields, function(i, field) {
			params[field.name] = $.trim(field.value);
		});
		$('#orderList').datagrid('load');
	}

	function edit() {
		$('#_operatorsProductform').form('submit', {
			url : "${ctx}/productGroup/saveProductGroup",
			onSubmit : function(param) {
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.confirm("消息", "更新成功", function(data) {
						if (data) {
							window.location.reload();
						} else {
							//alert("取消");
						}
					});
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

	function hkExportExcel() {
		$.messager.confirm('确认', '确认把该搜索结果导出Excel表格 ？', function(r) {
			if (r) {
				var startTime = $('#startTime1').datetimebox('getValue');
				var endTime = $('#endTime1').datetimebox('getValue');
				//	var username = $("#username").val();
				var mphone = $("#mphone").val();
				var orderNo = $("#billCode").val();
				var orderStatus = $("#select_value").val();
				var provinceId = $("#provinceId").val();
				var yysTypeId = $("#yysTypeId").val();
				//	var completeStartTime1 = $('#completeStartTime1').datetimebox(
				//			'getValue');
				//	var completeEndTime1 = $('#completeEndTime1').datetimebox(
				//			'getValue');
				$.messager.progress({
					title : '处理中',
					msg : '请稍后',
				});
				$.messager.progress('close');
				$.ajax({
					url : "${ctx}/export/isOutOfEnterpriseOrderExcel?startTime="
						+ startTime + "&endTime=" + endTime + "&mphone="
						+ mphone + "&orderStatus=" + orderStatus + "&orderNo="
						+ orderNo + "&provinceId=" + provinceId + "&yysTypeId=" + yysTypeId,
					onSubmit : function(param) {
					},
					success : function(data) {
						if (data.resultCode == 1) {
							location.href = "${ctx}/export/exportOrderExcel?startTime="
								+ startTime + "&endTime=" + endTime + "&mphone="
								+ mphone + "&orderStatus=" + orderStatus + "&orderNo="
								+ orderNo + "&provinceId=" + provinceId + "&yysTypeId=" + yysTypeId;
						} else {
							if (data.resultMsg) {
								$.messager.alert("消息", data.resultMsg);
							} else {
								$.messager.alert("消息", "未知错误");
							}
						}
					}
				});
				
			}
		});
	}
</script>
</head>
<body>
	<div id="hkzdTabs" class="easyui-tabs" data-options="fit:true">
		<div id="hkzdTab" title="合作商订单管理" style="padding: 2px">
			<table id="orderList"></table>
			<!-- toolbar="#tb" -->
			<div id="xxDgTb">
				<form id="queryHkForm">
					<table class=" datagrid-cell datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px;">
						<tr>
							<td style="text-align: right; vertical-align: middle;"><span>下单时间:</span></td>
							<td>
								<table class="datagrid-cell datagrid-cell-c1-status"
									style="border-collapse: separate; border-spacing: 0px; height: 15px;">
									<tr>
										<td style="border: none;"><input
											class="easyui-datetimebox" name="startTime" id="startTime1"
											data-options="showSeconds:false" style="width: 120%"></td>
										<td style="border: none;"><div
												style="width: 100%; border-top: 1px solid black; margin-top: 10px;"></div></td>
										<td style="border: none;"><input
											class="easyui-datetimebox" name="endTime" id="endTime1"
											data-options="showSeconds:false" style="width: 120%"></td>
										<td><span>订单状态:</span></td>
										<td><select style="margin: 0; width: 140px;"
											name="search_EQ_downstreamStatus" id="select_value">
												<option value="">请选择订单状态</option>
												<option value="0">成功</option>
												<option value="1">失败</option>
												<option value="2">处理中</option>
												<option value="3">提交成功</option>
										</select></td>
										<td><select style="margin: 0; width: 100%;"
											name="search_EQ_yysTypeId" id="yysTypeId">
												<option value="">请选择运营商</option>
												<option value="1">电信</option>
												<option value="2">移动</option>
												<option value="3">联通</option>
												<option value="-1">其他</option>
										</select></td>
										<td><select style="margin: 0; width: 100%;"
											name="search_EQ_provinceId" id="provinceId">
												<option value="">请选择省份</option>
												<option value="010">北京</option>
												<option value="020">广东</option>
												<option value="021">上海</option>
												<option value="022">天津</option>
												<option value="023">重庆</option>
												<option value="025">江苏</option>
												<option value="026">青海</option>
												<option value="027">海南</option>
												<option value="028">四川</option>
												<option value="029">陕西</option>
												<option value="030">山西</option>
												<option value="035">河北</option>
												<option value="039">河南</option>
												<option value="040">内蒙古</option>
												<option value="041">辽宁</option>
												<option value="045">吉林</option>
												<option value="046">黑龙江</option>
												<option value="050">安徽</option>
												<option value="055">浙江</option>
												<option value="059">福建</option>
												<option value="060">山东</option>
												<option value="070">广西</option>
												<option value="071">湖北</option>
												<option value="073">湖南</option>
												<option value="075">江西</option>
												<option value="080">云南</option>
												<option value="085">贵州</option>
												<option value="089">西藏</option>
												<option value="090">宁夏</option>
												<option value="093">甘肃</option>
												<option value="095">新疆</option>
										</select></td>
										<td style="border: none;"><input type="button"
											onclick="hkExportExcel();" value="导出excel"
											style="display: block; size: 20px; background-color: #95B8E7"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

					<table class="datagrid-cell datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px;">
						<tr>
							<td style="text-align: right; vertical-align: middle;"><span>关键词:</span></td>
							<td></td>
							<td style="border: none; vertical-align: middle;"><input
								type="text" name="search_LIKE_downstreamOrderNo"
								placeholder="请输入订单号" id="billCode"
								style="margin-left: 13px; width: 120%;" /></td>
							<td style="border: none; vertical-align: middle;"><input
								type="text" name="search_LIKE_mobile" placeholder="请输入手机号"
								style="margin-left: 60px; width: 100%;" id="mphone" /></td>
							<td style="border: none; vertical-align: middle;"><span
								style="margin-left: 80px"><a href="javascript:;"
									class="easyui-linkbutton" data-options="iconCls:'icon-search'"
									onclick="loadHk()">搜索</a></span></td>
						</tr>
						<tr>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
