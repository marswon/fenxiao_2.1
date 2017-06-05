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

#main {
	height: 1800px;
	padding-top: 90px;
	text-align: center;
}

#fullbg {
	background-color: gray;
	left: 0;
	opacity: 0.5;
	position: absolute;
	top: 0;
	z-index: 3;
	filter: alpha(opacity = 50);
	-moz-opacity: 0.5;
	-khtml-opacity: 0.5;
}

#dialog {
	background-color: #fff;
	border: 1px solid rgba(0, 0, 0, 0.4);
	height: 400px;
	left: 50%;
	margin: -200px 0 0 -300px;
	padding: 10px;
	position: fixed !important; /* 浮动对话框 */
	position: absolute;
	top: 50%;
	width: 600px;
	z-index: 5;
	border-radius: 5px;
	display: none;
}

#dialog p {
	margin: 0 0 12px;
	height: 24px;
	line-height: 24px;
	background: #CCCCCC;
}

#dialog p.close {
	text-align: right;
	padding-right: 3px;
	padding-left: 3px;
}

#dialog p.close a {
	color: #000;
	text-decoration: none;
}

#repeatList td {
	font-size: 16px;
}

#repeatList tr {
	height: 30px;
}

#detailTable tr {
	height: 30px;
}

#detailTable {
	border-collapse: collapse;
}

#detailTable td {
	border: 1px #CCC solid;
	text-align: center;
}

#detailTable td {
	font-size: 16px;
}

#repeatList {
	border-collapse: collapse;
	border: 1px #CCC solid;
}

#repeatList td {
	border: 1px #CCC solid;
	text-align: center;
}

#repeatList th {
	border: 1px #CCC solid;
	text-align: center;
}

.datagrid-cell {
	font-size: 12px;
}
</style>
<script type="text/javascript">
	var flag = false;
	$(document).ready(
			function() {
				$.each(areaCodeMap, function(key, value) {
					$("#areaCode").append(
							"<option value='"+key+"'>" + value + "</option>");
				});
				chosen("#areaCode");
				$.each(provinceMap, function(key, value) {
					$("#provinceId").append(
							"<option value='"+key+"'>" + value + "</option>");
					$("#fxProduct_provinceId").append(
							"<option value='"+key+"'>" + value + "</option>");
				});
				chosen("#provinceId");
				chosen("#fxProduct_provinceId");

				$.post("${ctx}/api/enterprise/getAllEnterpriseWithOutGas",
						null, function(data) {
							if (data) {
								$.each(data.content, function(index, obj) {
									$("#eId").append(
											"<option value='"+obj.id+"'>"
													+ obj.name + "</option>");
								});
								chosen("#eId");
								chosen("#yysTypeId");
								chosen("#repeatRechargeStatus");
								chosen("#size");
								chosen("#flowType");
							}
						});
				$.post("${ctx}/channelManege/getAllChannelWithOutGas", null,
						function(data) {
							if (data) {
								$.each(data.content, function(index, obj) {
									$("#channelId").append(
											"<option value='"+obj.id+"'>"
													+ obj.name + "</option>");
								});
								chosen("#channelId");
							}
						});
			});
	jQuery(function($) {
		var date = new Date().format("yyyy-MM-dd 00:00");
		$('#startTime1').datetimebox("setValue", date);
		$("#orderList")
				.datagrid(
						{
							url : '${ctx}/orderHistory/getOrderList',
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
										width : 200,
										align : "center",
										formatter : function(val, row, index) {
											if(row.fxEnterprise){
												return row.fxEnterprise.name;
											}
											return "--";
										}
									},
									{
										field : 'downstreamOrderNo',
										title : '订单号',
										width : 270,
										align : "center",
										styler : function(value, row, index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'upstreamOrderNo',
										title : '上游订单号',
										width : 200,
										align : "center",
										styler : function(value, row, index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'mobile',
										title : '手机号',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {
											return "<b>" + val + "</b>";
										}
									},
									{
										field : 'size',
										title : '规格大小',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											var result = "<b>";
											var valStr = val + "";
											if (valStr.length >= 3) {
												var tail = valStr.substring(
														valStr.length - 3,
														valStr.length);
												if (tail == "001"
														|| tail == "002"
														|| tail == "003"
														|| tail == "004"
														|| tail == "005"
														|| tail == "006"
														|| tail == "007") {
													val = valStr.substring(0,
															valStr.length - 3);
												}
											}
											if (row.businessType == 0) {
												if (val >= 1024) {
													result += val / 1024 + "G";
												} else {
													result += val + "M";
												}
											} else if (row.businessType == 1) {
												result += "￥" + val;
											}
											return result + "</b>";
										}
									},
									{
										field : 'clientSubmitTime',
										title : '客户下单时间',
										width : 110,
										align : "center"
									},
									{
										field : 'systemSubmitTime',
										title : '系统提交时间',
										width : 110,
										align : "center"
									},
									{
										field : 'reportTime',
										title : '回调时间',
										width : 110,
										align : "center"
									},
									{
										field : 'yysName',
										title : '运营商',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											// 											if (row.fxProduct) {
											var result = showProvince(row.provinceId);
											var yys = row.yysTypeId;
											if (yys == "1") {
												result += "电信";
											} else if (yys == "2") {
												result += "移动";
											} else {
												result += "联通";
											}
											if (row.areaCode) {
												result += "<br/>"
														+ showAreaName(row.areaCode);
											}
											return '<span style="color:white;background:green">'
													+ result + '</span>';
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
										title : '类别',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return "流量";
											} else if (val == 1) {
												return "话费";
											}
										}
									},
									{
										field : 'downstreamStatus',
										title : '订单状态',
										width : 80,
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
									// 									{
									// 										field : 'upstreamStatus',
									// 										title : '上游状态',
									// 										width : 80,
									// 										align : "center",
									// 										formatter : function(val, row, index) {
									// 											if (val == 0) {
									// 												return '<span style="color:white;background:green">成功</span>';
									// 											} else if (val == 1) {
									// 												return '<span style="color:white;background:red">失败</span>';
									// 											} else if (val == 2) {
									// 												return '<span style="color:white;background:orange">充值中</span>';
									// 											} else if (val == 3) {
									// 												return "提交成功";
									// 											}
									// 										}
									// 									},
									// 									{
									// 										field : 'errorDesc',
									// 										title : '描述',
									// 										width : 120,
									// 										align : "center"
									// 									},
									{
										field : 'id',
										title : '操作',
										width : 130,
										align : "center",
										formatter : function(val, row, index) {
											var jsonStr = "\""
													+ JSON.stringify(row)
													+ "\"";
											return '<button style="color:white;background:green;" onclick="handWorkStatus(\''
													+ row.id
													+ '\',\''
													+ 1
													+ '\')">回调成功</button></br><button style="color:white;background:red;" onclick="handWorkStatus(\''
													+ row.id
													+ '\',\''
													+ 2
													+ '\')">回调失败</button></br><button style="color:black;background:#DDD;" onclick="detail('
													+ JSON.stringify(row)
															.replace(/"/g,
																	'&quot;')
													+ ')">查看详情</button>';

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

		$('#webinformation1').dialog({
			title : "平台产品编辑",
			width : 950,
			height : 590,
			closed : true,
			cache : false,
			modal : true,
			center : true,
		});
	});

	function closeDf() {
		$('#dfxqDlg').dialog('close');
	}
	function showAreaName(areaCode) {
		return areaCodeMap[areaCode];
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
								var systemStartTime = $('#systemStartTime')
										.datetimebox('getValue');
								var systemEndTime = $('#systemEndTime')
										.datetimebox('getValue');
								//	var username = $("#username").val();
								var mphone = $("#mphone").val();
								var orderNo = $("#billCode").val();
								var downStatus = $("#downstreamStatus").val();
								var upStatus = $("#upstreamStatus").val();
								var businessType = $("#businessType").val();
								var yysTypeId = $("#yysTypeId").val();
								var eId = $('#eId').val();
								var eName = $('#eId').find("option:selected")
										.text();
								var channelId = $('#channelId').val();
								var channelName = $('#channelId').find(
										"option:selected").text();
								var provinceId = $("#provinceId").val();
								var isNormal = $("#isNormal").val();
								var areaCode = $("#areaCode").val();
								$.messager.progress({
									title : '处理中',
									msg : '请稍后',
								});
								$.messager.progress('close');

								$
										.ajax({
											url : "${ctx}/export/isOutOfManageOrderHistoryExcel?startTime="
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
													+ "&businessType="
													+ businessType
													+ "&yysTypeId="
													+ yysTypeId
													+ "&areaCode="
													+ areaCode
													+ "&systemStartTime="
													+ systemStartTime
													+ "&systemEndTime="
													+ systemEndTime,
											onSubmit : function(param) {
											},
											success : function(data) {
												if (data.resultCode == 1) {
													location.href = "${ctx}/export/exportOrderHistoryManageExcel?startTime="
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
															+ "&businessType="
															+ businessType
															+ "&yysTypeId="
															+ yysTypeId
															+ "&areaCode="
															+ areaCode
															+ "&systemStartTime="
															+ systemStartTime
															+ "&systemEndTime="
															+ systemEndTime;

												} else {
													if (data.resultMsg) {
														$.messager.alert("消息",
																data.resultMsg);
													} else {
														$.messager.alert("消息",
																"未知错误");
													}
												}
											}
										});

							}
						});
	}

	function showLayer() {
		showBg();
	}

	//关闭灰色 jQuery 遮罩
	function closeBg() {
		$("#fullbg,#dialog").hide();
	}
	//显示灰色 jQuery 遮罩层
	function showBg() {
		var bh = $("body").height();
		var bw = $("body").width();
		$("#fullbg").css({
			height : bh,
			width : bw,
			display : "block"
		});
		$("#dialog").show();
	}
	function detail(obj) {
		$("#discount").html((obj.discount / 100).toFixed(2));
		$("#beforeBalance").html("￥" + (obj.beforeBalance / 1000).toFixed(3));
		$("#upstreamstatustd").html(showStatusText(obj.upstreamStatus));
		$("#repeatRechargeCounttd").html(obj.repeatRechargeCount);
		$("#operatorProducttd").html(obj.operatorProduct.name);
		$("#desctd").html(obj.errorDesc);
		$("#ifidtd").html(obj.receiveService);
		$("#reportidtd").html(obj.reportService);
		$.post("${ctx}/orderHistory/getRepeatList", {
			downstreamOrderNo : obj.downstreamOrderNo,
			mobile : obj.mobile
		}, function(data) {
			$("#repeatList tr:gt(0)").remove();
			if (data) {
				$.each(data, function(index, obj) {
					$("#repeatList").append(
							"<tr><td>" + (index + 1) + "</td><td>" + obj[0]
									+ "</td><td>" + obj[1] + "</td><td>"
									+ (obj[2] / 100).toFixed(2) + "</td><td>"
									+ showStatusText(obj[3]) + "</td></tr>");
				});
			}
		});
		showLayer();
	}

	function showStatusText(val) {
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
</script>
</head>
<body>
	<div id="fullbg"></div>
	<div id="dialog" align="center">
		<div
			style="font-size: 16px; border-bottom: 1px solid #CCC; padding-bottom: 3px;">
			<font>订单详情</font><a href="#" onclick="closeBg();"
				style="background-color: #CCC; text-decoration: none; margin-left: 500px;">关闭</a>
		</div>
		<div
			style="padding: 5px; height: 350px; border-bottom: 1px solid #CCC; width: 98%; overflow: auto;"
			align="center">
			<div style="width: 100%;">
				<table id="detailTable" style="width: 100%">
					<tr>
						<td style="width: 20%">消费前余额</td>
						<td id="beforeBalance" style="width: 30%"></td>
						<td style="width: 20%">折扣</td>
						<td id="discount" style="width: 30%"></td>
					</tr>
					<tr>
						<td>复充次数</td>
						<td id="repeatRechargeCounttd"></td>
						<td>供应商产品名</td>
						<td id="operatorProducttd"></td>
					<tr>
						<td>上游状态</td>
						<td id="upstreamstatustd"></td>
						<td>描述</td>
						<td id="desctd"
							style="word-wrap: break-word; word-break: break-all;"></td>
					</tr>
					<tr>
						<td>接单服务器</td>
						<td id="ifidtd"></td>
						<td>回调服务器</td>
						<td id="reportidtd"></td>
					</tr>
				</table>
			</div>
			<div style="width: 100%; font-size: 16px; margin-top: 10px;">充值渠道轨迹</div>
			<div>
				<table id="repeatList"
					style="width: 100%; border-top: 1px #CCC solid;">
					<tr>
						<th style="width: 7%">序号</th>
						<th style="width: 35%">通道名称</th>
						<th style="width: 35%">供应商产品名称</th>
						<th style="width: 12%">成本折扣</th>
						<th style="width: 11%">状态</th>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="流量话费历史订单查询" style="padding: 2px">
			<table id="orderList"></table>
			<!-- toolbar="#tb" -->
			<div id="xxDgTb">
				<form id="queryHkForm">
					<table class=" datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px; width: 1150px;">
						<tr>
							<td style="text-align: right; vertical-align: middle; width: 5%"><span>下单时间:</span></td>
							<td style="width: 20%">
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
							<td style="width: 20%"><span>所属企业</span><select id="eId"
								name="search_EQ_fxEnterprise.id" style="width: 167px"><option
										value="">--请选择--</option></select></td>
							<td style="width: 20%"><span>所属渠道</span><select
								id="channelId" name="search_EQ_fxChannel.id"
								style="width: 170px"><option value="">--请选择--</option></select></td>
							<td>
								<table>
									<tr>
										<td><select style="margin: 0; width: 150px;"
											name="search_EQ_upstreamStatus" id="upstreamStatus">
												<option value="">请选择上游订单状态</option>
												<option value="0">成功</option>
												<option value="1">失败</option>
												<option value="2">处理中</option>
												<option value="3">提交成功</option>
										</select></td>
										<td><select style="margin: 0; width: 150px;"
											name="search_EQ_downstreamStatus" id="downstreamStatus">
												<option value="">请选择下游订单状态</option>
												<option value="0">成功</option>
												<option value="1">失败</option>
												<option value="2">处理中</option>
												<option value="3">提交成功</option>
										</select></td>
										<td><input type="button" onclick="hkExportExcel();"
											value="导出excel"
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
								style="margin: 0; width: 93%;" /></td>
							<td style="border: none; vertical-align: middle;"><input
								type="text" name="search_LIKE_mobile" placeholder="请输入手机号"
								style="margin: 0; width: 90%;" id="mphone" /></td>
							<td><select class="sel" name="search_EQ_businessType"
								id="businessType" style="margin: 0; width: 110px;">
									<option value="">-业务类型-</option>
									<option value="0">流量</option>
									<option value="1">话费</option>
									<!-- <option value="2">物业缴费</option>
									<option value="3">加油卡</option> -->
							</select><select style="margin: 0; width: 110px;"
								name="search_EQ_isNormal" id="isNormal">
									<option value="">-是否异常-</option>
									<option value="0">正常订单</option>
									<option value="1">异常订单</option>
							</select></td>
							<td style="border: none; vertical-align: middle;">运营商 <select
								style="margin: 0; width: 75px;" name="search_EQ_yysTypeId"
								id="yysTypeId">
									<option value="">请选择</option>
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option>
							</select>省份<select style="margin: 0; width: 75px; margin-left: 3px"
								name="search_EQ_provinceId" id="provinceId">
									<option value="">请选择</option>
									<!-- 												<option value="000">全国</option> -->
									<!-- 												<option value="010">北京</option> -->
									<!-- 												<option value="020">广东</option> -->
									<!-- 												<option value="021">上海</option> -->
									<!-- 												<option value="022">天津</option> -->
									<!-- 												<option value="023">重庆</option> -->
									<!-- 												<option value="025">江苏</option> -->
									<!-- 												<option value="026">青海</option> -->
									<!-- 												<option value="027">海南</option> -->
									<!-- 												<option value="028">四川</option> -->
									<!-- 												<option value="029">陕西</option> -->
									<!-- 												<option value="030">山西</option> -->
									<!-- 												<option value="035">河北</option> -->
									<!-- 												<option value="039">河南</option> -->
									<!-- 												<option value="040">内蒙古</option> -->
									<!-- 												<option value="041">辽宁</option> -->
									<!-- 												<option value="045">吉林</option> -->
									<!-- 												<option value="046">黑龙江</option> -->
									<!-- 												<option value="050">安徽</option> -->
									<!-- 												<option value="055">浙江</option> -->
									<!-- 												<option value="059">福建</option> -->
									<!-- 												<option value="060">山东</option> -->
									<!-- 												<option value="070">广西</option> -->
									<!-- 												<option value="071">湖北</option> -->
									<!-- 												<option value="073">湖南</option> -->
									<!-- 												<option value="075">江西</option> -->
									<!-- 												<option value="080">云南</option> -->
									<!-- 												<option value="085">贵州</option> -->
									<!-- 												<option value="089">西藏</option> -->
									<!-- 												<option value="090">宁夏</option> -->
									<!-- 												<option value="093">甘肃</option> -->
									<!-- 												<option value="095">新疆</option> -->
							</select>地市 <select style="margin: 0; width: 75px;"
								name="search_EQ_areaCode" id="areaCode">
									<option value="">请选择</option>
							</select> <a href="javascript:;" class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" onclick="loadHk()">搜索</a>
							</td>

						</tr>
						<tr>
							<td colspan="5">复充状态: <select
								style="margin: 0; width: 100px;" name="repeatRechargeStatus"
								id="repeatRechargeStatus">
									<option value="">--请选择--</option>
									<option value="0">未复充</option>
									<option value="1">已复充</option>
							</select> 规格大小: <select style="margin: 0; width: 100px;"
								name="search_EQ_size" id="size">
									<option value="">--请选择--</option>
									<option value="5">5</option>
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="30">30</option>
									<option value="50">50</option>
									<option value="70">70</option>
									<option value="100">100</option>
									<option value="150">150</option>
									<option value="200">200</option>
									<option value="300">300</option>
									<option value="500">500</option>
									<option value="1024">1G</option>
									<option value="2048">2G</option>
									<option value="3072">3G</option>
									<option value="4096">4G</option>
									<option value="6144">6G</option>
									<option value="11264">11G</option>
									<option value="12288">12G</option>
							</select> 流量类型: <select style="margin: 0; width: 100px;"
								name="search_EQ_fxProduct.flowType" id="flowType">
									<option value="">--请选择--</option>
									<option value="0">漫游</option>
									<option value="1">本地</option>
							</select> 产品名称: <input type="text" name="search_LIKE_fxProduct.name"
								placeholder="请输入产品名称" id="" style="margin: 0; width: 150px;" />
								产品省份<select style="margin: 0; width: 100px; margin-left: 3px"
								name="search_EQ_fxProduct.provinceId" id="fxProduct_provinceId">
									<option value="">--请选择--</option>
									<option value="000">全国</option>
							</select> 系统提交时间：<input class="easyui-datebox" name="systemStartTime"
								id="systemStartTime" style="width: 90px"> <input
								class="easyui-datebox" name="systemEndTime" id="systemEndTime"
								style="width: 90px">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
