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
							url : '${ctx}/order/getGasOrderList',
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
										align : "center",
										styler : function(
												value, row,
												index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'upstreamOrderNo',
										title : '上游订单号',
										width : 200,
										align : "center",
										styler : function(
												value, row,
												index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'mobile',
										title : '手机号',
										width : 150,
										align : "center",
										styler : function(
												value, row,
												index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'clientSubmitTime',
										title : '客户下单时间',
										width : 140,
										align : "center"
									},
									{
										field : 'systemSubmitTime',
										title : '系统提交时间',
										width : 140,
										align : "center"
									},
									{
										field : 'reportTime',
										title : '回调时间',
										width : 140,
										align : "center"
									},
									{
										field : 'yysName',
										title : '运营商',
										width : 140,
										align : "center",
										formatter : function(val, row, index) {
											if (row.fxProduct) {
												var yys = row.fxProduct.yysTypeId;
												if (yys == "1") {
													return '<span style="color:white;background:green">'
															+ showProvince(row.fxProduct.provinceId)
															+ "电信" + '</span>';
												} else if (yys == "2") {
													return '<span style="color:white;background:green">'
															+ showProvince(row.fxProduct.provinceId)
															+ "移动" + '</span>';
												} else if (yys == "3") {
													return '<span style="color:white;background:green">'
															+ showProvince(row.fxProduct.provinceId)
															+ "联通" + '</span>';
												}else if (yys == "4") {
													return '<span style="color:white;background:green">'
													+ showProvince(row.fxProduct.provinceId)
													+ "中石化" + '</span>';
												}else if (yys == "5") {
													return '<span style="color:white;background:green">'
													+ showProvince(row.fxProduct.provinceId)
													+ "中石油" + '</span>';
												} else if (yys == "-1") {
													return '<span style="color:white;background:green">'
													+ "其他" + '</span>';
										}
											} else {
												return "";
											}
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
										field : 'channelName',
										title : '通道名称',
										width : 170,
										align : "center",
										formatter : function(val, row, index) {
											if (row.fxChannel) {
												return row.fxChannel.name;
											} else {
												return "";
											}
										}
									},
									{
										field : 'costMoney',
										title : '实际消费',
										width : 90,
										align : "center",
										formatter : function(val, row, index) {
											var value = val * 0.001;
											return "¥" + value.toFixed(3);
										}
									},
									{
										field : 'afterBalance',
										title : '账户余额',
										width : 120,
										align : "center",
										formatter : function(val, row, index) {
											var value = val * 0.001;
											return "¥" + value.toFixed(2);
										}
									},
									{
										field : 'businessType',
										title : '业务类型',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return "流量";
											} else if (val == 1) {
												return "话费";
											} else if (val == 3) {
												return "加油卡";
											} 
										}
									},
									{
										field : 'downstreamStatus',
										title : '订单状态',
										width : 100,
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
									},
									{
										field : 'upstreamStatus',
										title : '上游状态',
										width : 100,
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
									},
									{
										field: 'errorDesc',
										title: '描述',
										width: 120,
										align: "center",
										formatter: function (val, row, index) {
											if(row.demo&&row.demo!=null){
												return row.errorDesc + ":" + row.demo;
											}else {
												return row.errorDesc;
											}
										}
									},
									{
										field : 'handWork',
										title : '充值类型',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return "接口";
											} else if (val == 1) {
												return "手工";
											}
										}
									},
									{
										field : 'id',
										title : '操作',
										width : 180,
										align : "center",
										formatter : function(val, row, index) {
											return '<button style="color:white;background:green;" onclick="handWorkStatus(\''
													+ row.id
													+ '\',\''
													+ 1
													+ '\')">回调成功</button><button style="color:white;background:red;" onclick="handWorkStatus(\''
													+ row.id
													+ '\',\''
													+ 2
													+ '\')">回调失败</button>';

										}
									}, ] ],
						});
		$('#dfxqDlg').dialog({
			title : '会员资料',
			fit : true,
			draggable : false,
			closed : true,
			cache : false,
			modal : true
		});
		$('#eId').combobox({
			url : "${ctx}/api/enterprise/getAllGasEnterprise",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 150,
			panelHeight : 200,
			loadFilter : function(data) {
				if (data.content) {
					console.log(data.content);
					return data.content;
				} else {
					return false;
				}
			},
		});
		$('#channelId').combobox({
			url : "${ctx}/channelManege/getAllGasChannel",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 150,
			panelHeight : 200,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return false;
				}
			},
		});
/* 
		$('#webinformation1').dialog({
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

	function handWorkStatus(id, status) {
		var str;
		if (status == 1) {
			str = "回调成功";
		} else {
			str = "回调失败";
		}
		$.messager.confirm('确认', '确定' + str + "?", function(r) {
			if (r) {
				$.messager.progress({
					title : '回调中...',
					msg : '请稍后',
				});
				$.getJSON("${ctx}/handwork/callback", {
					id : id,
					status : status
				}, function(data) {
					$.messager.progress('close');
					if (data.resultCode == 1) {
						$.messager.alert('消息', data.resultMsg);
					} else {
						$.messager.alert('消息', data.resultMsg);
					}
					//运营商
				});
			}
		});
	}

	function hkExportExcel() {
		$.messager
				.confirm(
						'确认',
						'确认把该搜索结果导出Excel表格 ？',
						function(r) {
							if (r) {
								var startTime = $('#startTime1').datetimebox(
										'getValue');
								var endTime = $('#endTime1').datetimebox(
										'getValue');
								//	var username = $("#username").val();
								var mphone = $("#mphone").val();
								var orderNo = $("#billCode").val();
								var downStatus = $("#downstreamStatus").val();
								var upStatus = $("#upstreamStatus").val();
								var handwork = $("#handWork").val();
								var businessType = 3;
								var yysTypeId = 4;
								var eId = $('#eId').combobox('getValue');
								var eName = $('#eId').combobox('getText');
								var channelId = $('#channelId').combobox(
										'getValue');
								var channelName = $('#channelId').combobox(
								'getText');
								var provinceId = $("#provinceId").val();
								var isNormal = $("#isNormal").val();
								$.messager.progress({
									title : '处理中',
									msg : '请稍后',
								});
								$.messager.progress('close');

								$.ajax({
									url : "${ctx}/export/isOutOfManageOrderExcel?startTime="
										+ startTime
										+ "&endTime="
										+ endTime
										+ "&mphone="
										+ mphone
										+ "&upStatus="
										+ upStatus
										+ "&downStatus="
										+ downStatus
										+ "&orderNo="
										+ orderNo
										+ "&eId="
										+ eId
										+ "&provinceId="
										+ provinceId
										+ "&channelId="
										+ channelId
										+ "&isNormal="
										+ isNormal
										+ "&businessType=" + businessType
										+ "&yysTypeId="
										+ yysTypeId
										+ "&handWork="
										+ handwork,
									onSubmit : function(param) {
									},
									success : function(data) {
										if (data.resultCode == 1) {
											location.href = "${ctx}/export/exportOrderManageExcel?startTime="
												+ startTime
												+ "&endTime="
												+ endTime
												+ "&mphone="
												+ mphone
												+ "&upStatus="
												+ upStatus
												+ "&downStatus="
												+ downStatus
												+ "&orderNo="
												+ orderNo
												+ "&eId="
												+ eId
												+ "&provinceId="
												+ provinceId
												+ "&channelId="
												+ channelId
												+ "&eName="
												+ eName
												+ "&channelName="
												+ channelName
												+ "&isNormal="
												+ isNormal
												+ "&businessType=" + businessType
												+ "&yysTypeId="
												+ yysTypeId
												+ "&handWork="
												+ handwork;

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
	<div class="easyui-tabs" data-options="fit:true">
		<div title="加油卡订单管理" style="padding: 2px">
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

									</tr>
								</table>
							<td><span>所属企业</span><input id="eId"
								name="search_EQ_fxEnterprise.id"></td>
							<td><span>所属渠道</span><input id="channelId"
								name="search_EQ_fxChannel.id"></td>
							<td>
								<table>
									<tr>
										<td><select style="margin: 0; width: 100%;"
											name="search_EQ_upstreamStatus" id="upstreamStatus">
												<option value="">请选择上游订单状态</option>
												<option value="0">成功</option>
												<option value="1">失败</option>
												<option value="2">处理中</option>
												<option value="3">提交成功</option>
										</select></td>
										<td><select style="margin: 0; width: 100%;"
											name="search_EQ_downstreamStatus" id="downstreamStatus">
												<option value="">请选择下游订单状态</option>
												<option value="0">成功</option>
												<option value="1">失败</option>
												<option value="2">处理中</option>
												<option value="3">提交成功</option>
										</select></td>
										<td><select style="margin: 0; width: 100px;"
								name="search_EQ_isNormal" id="isNormal">
									<option value="">-是否异常-</option>
									<option value="0">正常订单</option>
									<option value="1">异常订单</option>
							</select></td></td>
										<td><select style="margin: 0; width: 100%;"
											name="search_EQ_fxProduct.provinceId" id="provinceId">
												<option value="">请选择省份</option>
												<option value="000">全国</option>
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
						<tr>
							<td style="text-align: right; vertical-align: middle;"><span>关键词:</span></td>
							<td style="border: none; vertical-align: middle;"><input
								type="text" name="search_LIKE_downstreamOrderNo"
								placeholder="请输入订单号" id="billCode"
								style="margin: 0; width: 90%;" /></td>
							<td style="border: none; vertical-align: middle;"><input
								type="text" name="search_LIKE_mobile" placeholder="请输入手机号"
								style="margin: 0; width: 90%;" id="mphone" /></td>
							<td>
								<select style="margin: 0; width: 100px;"
												 name="search_EQ_handWork" id="handWork">
								<option value="">-充值类型-</option>
								<option value="0">接口</option>
								<option value="1">手工</option>
							</select>
							<select style="margin: 0; width: 120px;"
										name="search_EQ_yysTypeId" id="yysTypeId">
								<option value="">请选择运营商</option>
								<option value="4">中石化</option>
								<option value="5">中石油</option>
							</select></td>
							<td style="border: none; vertical-align: middle;"><a
								href="javascript:;" class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" onclick="loadHk()">搜索</a></td>

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
