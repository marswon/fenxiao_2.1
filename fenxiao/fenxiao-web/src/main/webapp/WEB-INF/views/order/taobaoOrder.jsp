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

td {
	font-size: 16px;
}

#opro table {
	border-collapse: collapse;
}

#opro td {
	border: 1px solid #CCC;
	text-align: center;
	line-height: 30px;
}

#opro th {
	text-align: center;
	font-size: 15px;
	line-height: 30px;
	border: 1px solid #CCC;
}

#opro tr:HOVER {
	background-color: #DDD;
}

body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	margin: 0;
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

#dialog1 {
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

#dialog1 p {
	margin: 0 0 12px;
	height: 24px;
	line-height: 24px;
	background: #CCCCCC;
}

#dialog1 p.close {
	text-align: right;
	padding-right: 3px;
	padding-left: 3px;
}

#dialog1 p.close a {
	color: #000;
	text-decoration: none;
}

.messager-body div {
	padding-left: 10px;
}

.messager-icon {
	display: none;
}

.datagrid-cell {
	font-size: 14px;
	height: 30px;
}

.datagrid-cell-rownumber {
	height: 30px;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var date = new Date().format("yyyy-MM-dd");
						$('#startTime').datebox("setValue", date);
						$("#oproTable")
								.datagrid(
										{
											url : '${ctx}/taobaoOrder/getTaobaoOrderList',
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
														field : 'tbOrderNo',
														title : '订单号',
														width : 80,
														align : "center",
														styler : function(
																value, row,
																index) {
															return "word-break: break-all; word-wrap:break-word;";
														}
													},
													{
														field : 'supplierid',
														title : '店铺名称',
														width : 70,
														align : "center",
														formatter : function(
																val, row, index) {
															return row.taobaoShop.name;
														}
													},
													{
														field : 'spuidName',
														title : '宝贝名称',
														width : 60,
														align : "center",
														formatter : function(
																val, row, index) {
															if (row.taobaoPro) {
																return row.taobaoPro.name;
															}
															return "--";
														}
													},
													{
														field : 'accountVal',
														title : '充值账号',
														width : 70,
														align : "center",
														styler : function(
																value, row,
																index) {
															return "word-break: break-all; word-wrap:break-word;";
														}
													},
													{
														field : 'timeStart',
														title : '时间',
														width : 60,
														align : "center",
														formatter : function(
																val, row, index) {
															return new Date(val)
																	.format("yyyy-MM-dd hh:mm:ss");
														}
													},
													{
														field : 'coopOrderStatus',
														title : '订单状态',
														width : 50,
														align : "center",
														formatter : function(
																val, row, index) {
															switch (val) {
															case "SUCCESS":
																return "<font color='white' style='background-color: green'>充值成功</font>";
															case "FAILED":
																return "<font color='white' style='background-color: red'>充值失败</font>";
															case "UNDERWAY":
																return "<font color='white' style='background-color: orange'>充值中</font>";
															default:
																return "其他";
															}
														}
													},
													{
														field : 'size',
														title : '下单编码',
														width : 40,
														align : "center",
														formatter : function(
																val, row, index) {
															if (row.taobaoPro) {
																return row.taobaoPro.size;
															}
															return "--";
														}
													},
													{
														field : 'spuid',
														title : '宝贝编码',
														width : 60,
														align : "center"
													},
													{
														field : 'timeLimit',
														title : '充值限时',
														width : 40,
														align : "center",
														formatter : function(
																val, row, index) {
															if (val % 3600 == 0) {
																return val
																		/ 3600
																		+ " 小时";
															} else {
																return (val / 3600)
																		.toFixed(1)
																		+ " 小时";
															}

														}
													},
													{
														field : 'buyNum',
														title : '数量',
														width : 20,
														align : "center"
													},
													{
														field : 'tbPrice',
														title : '售价',
														width : 50,
														align : "center",
														formatter : function(
																val, row, index) {
															return "￥"
																	+ (val / 100)
																			.toFixed(2);
														}
													},
													{
														field : 'splitPrice',
														title : '分账金额',
														width : 50,
														align : "center",
														formatter : function(
																val, row, index) {
															return "￥"
																	+ (val / 100)
																			.toFixed(2);
														}
													},
													{
														field : 'handleStatus',
														title : '处理状态',
														width : 40,
														align : "center",
														formatter : function(
																val, row, index) {
															switch (val) {
															case 0:
																return "<font color='white' style='background-color: orange'>待处理</font>";
															case 1:
																return "<font color='white' style='background-color: green'>已处理</font>";
															default:
																return "其他";
															}
														}
													},
													{
														field : 'isJiaChong',
														title : '是否假充',
														width : 40,
														align : "center",
														formatter : function(
																val, row, index) {
															switch (val) {
															case 0:
																return "<font color='white' style='background-color: green'>否</font>";
															case 1:
																return "<font color='white' style='background-color: red'>是</font>";
															default:
																return "其他";
															}
														}
													},
													{
														field : 'jiaChongTime',
														title : '假充时间',
														width : 50,
														align : "center",
														formatter : function(
																val, row, index) {
															if (val
																	&& row.isJiaChong == 1) {
																return new Date(
																		val)
																		.format("yyyy-MM-dd hh:mm:ss");
															}
															return "--";
														}
													}, {
														field : 'desc',
														title : '描述',
														width : 40,
														align : "center"
													} ] ]

										});

						$.post("${ctx}/taobaoOrder/getShopList", null,
								function(data) {
									if (data) {
										$.each(data, function(index, obj) {
											$("#supplierid").append(
													"<option value='"+obj[0]+"'>"
															+ obj[1]
															+ "</option>");
										});
									}
								});
						$
								.post(
										"${ctx}/taobaoConfig/getBusinessType",
										null,
										function(data) {
											if (data) {
												$
														.each(
																data,
																function(key,
																		val) {
																	$(
																			"#businessType")
																			.append(
																					"<option value='"+key+"'>"
																							+ val
																							+ "</option>");
																});
											}
										});
						$("#search_id").click(
								function() {
									var params = $('#oproTable').datagrid(
											'options').queryParams;
									var fields = $('#queryForm')
											.serializeArray();
									$.each(fields, function(i, field) {
										params[field.name] = $
												.trim(field.value);
									});
									$('#oproTable').datagrid('load');
								});

					});

	function hkExportExcel() {
		$.messager
				.confirm(
						'确认',
						'确认把该搜索结果导出Excel表格 ？',
						function(r) {
							if (r) {
								var startTime = $('#startTime').datebox(
										'getValue');
								var endTime = $('#endTime').datebox('getValue');
								var supplierid = $("#supplierid").val();
								var tbOrderNo = $("#tbOrderNo").val();
								var accountVal = $("#accountVal").val();
								var spuid = $("#spuid").val();
								var businessType = $("#businessType").val();
								var coopOrderStatus = $("#coopOrderStatus")
										.val();
								var isJiaChong = $('#isJiaChong').val();
								$.messager.progress({
									title : '处理中',
									msg : '请稍后',
								});
								$.messager.progress('close');

								$
										.ajax({
											url : "${ctx}/export/isOutOfTaobaoOrderExcel?startTime="
													+ startTime
													+ "&endTime="
													+ endTime
													+ "&supplierid="
													+ supplierid
													+ "&tbOrderNo="
													+ tbOrderNo
													+ "&accountVal="
													+ accountVal
													+ "&spuid="
													+ spuid
													+ "&businessType="
													+ businessType
													+ "&coopOrderStatus="
													+ coopOrderStatus
													+ "&isJiaChong="
													+ isJiaChong,
											onSubmit : function(param) {
											},
											success : function(data) {
												if (data.resultCode == 1) {
													location.href = "${ctx}/export/exportTaobaoOrderExcel?startTime="
															+ startTime
															+ "&endTime="
															+ endTime
															+ "&supplierid="
															+ supplierid
															+ "&tbOrderNo="
															+ tbOrderNo
															+ "&accountVal="
															+ accountVal
															+ "&spuid="
															+ spuid
															+ "&businessType="
															+ businessType
															+ "&coopOrderStatus="
															+ coopOrderStatus
															+ "&isJiaChong="
															+ isJiaChong;

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
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="淘宝订单管理" style="padding: 2px">
			<table id="oproTable">
			</table>
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table style="width: 100%">
						<tr>
							<td style="width: 75%;">开始时间<input class="easyui-datebox"
								name="startTime" id="startTime" style="width: 90px">结束时间<input
								class="easyui-datebox" name="endTime" id="endTime"
								style="width: 90px">店铺名称<select
								name="search_EQ_supplierid" id="supplierid"
								style="width: 140px;"><option value="">--请选择--</option>
							</select>订单号 <input type="text" name="search_LIKE_tbOrderNo"
								style="width: 150px;" id="tbOrderNo" />充值账号 <input type="text"
								name="search_LIKE_accountVal" style="width: 150px;"
								id="accountVal" />宝贝编码<input type="text"
								name="search_LIKE_spuid" style="width: 100px;" id="spuid" />业务类型<select
								name="search_EQ_taobaoPro.businessType" id="businessType"
								style="width: 100px;"><option value="">--请选择--</option>
							</select>订单状态<select name="search_EQ_coopOrderStatus"
								style="width: 100px;" id="coopOrderStatus"><option
										value="">--请选择--</option>
									<option value="SUCCESS">充值成功</option>
									<option value="FAILED">充值失败</option>
									<option value="UNDERWAY">充值中</option></select>是否假充<select
								name="search_EQ_isJiaChong" style="width: 100px;"
								id="isJiaChong"><option value="">--请选择--</option>
									<option value="0">否</option>
									<option value="1">是</option></select><a href="javascript:;"
								class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								id="search_id">搜索</a><input type="button"
								onclick="hkExportExcel();" value="导出excel"
								style="display: block; size: 20px; background-color: #95B8E7"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
