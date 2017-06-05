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
						$("#oproTable")
								.datagrid(
										{
											url : '${ctx}/taobaoActivityConfig/getActivityList',
											method : 'get',
											nowrap : false,
											striped : true,
											remoteSort : false,
											pageSize : 20,
											fit : true,
											queryParams : {},
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
														field : 'taobaoPro.name',
														title : '宝贝名称',
														width : 150,
														align : "center",
														formatter : function(
																val, row, index) {
															if (row.taobaoPro) {
																return row.taobaoPro.name;
															} else {
																return "--";
															}
														}
													},
													{
														field : 'spuid',
														title : '宝贝编码',
														width : 100,
														align : "center"
													},
													{
														field : 'giftPro.name',
														title : '赠送宝贝名称',
														width : 150,
														align : "center",
														formatter : function(
																val, row, index) {
															if (row.giftPro) {
																return row.giftPro.name;
															} else {
																return "--";
															}
														}
													},
													{
														field : 'giftSpuid',
														title : '赠送宝贝编码',
														width : 100,
														align : "center"
													},
													{
														field : 'taobaoShop.name',
														title : '活动店铺',
														width : 100,
														align : "center",
														formatter : function(
																val, row, index) {
															if (row.taobaoShop) {
																return row.taobaoShop.name;
															} else {
																return "--";
															}
														}
													},
													{
														field : 'activityStartTime',
														title : '活动开始时间',
														width : 100,
														align : "center",
														formatter : function(
																val, row, index) {
															return new Date(val)
																	.format("yyyy-MM-dd hh:mm:ss");
														}
													},
													{
														field : 'activityEndTime',
														title : '活动结束时间',
														width : 100,
														align : "center",
														formatter : function(
																val, row, index) {
															return new Date(val)
																	.format("yyyy-MM-dd hh:mm:ss");
														}
													},
													{
														field : 'activitystatus',
														title : '活动状态',
														width : 50,
														align : "center",
														formatter : function(
																val, row, index) {
															var now = new Date()
																	.getTime();
															var start = row.activityStartTime;
															var end = row.activityEndTime;
															if (now < start) {
																return "<font color='white' style='background-color: gray'>未开始</font>";
															}
															if (now > end) {
																return "<font color='white' style='background-color: red'>已结束</font>";
															}
															if (now >= start
																	&& now <= end) {
																return "<font color='white' style='background-color: green'>进行中</font>";
															}
														}
													},
													{
														field : 'status',
														title : '操作',
														width : 60,
														align : "center",
														formatter : function(
																val, row, index) {
															var now = new Date()
																	.getTime();
															var end = row.activityEndTime;
															if (now > end) {
																return "--";
															} else
																return "<input type='button' value='删除' onclick=deleteActivity("
																		+ row.id
																		+ ") />";
														}
													} ] ]

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

						$.post("${ctx}/taobaoActivityConfig/getAllPro", null,
								function(data) {
									if (data) {
										$.each(data, function(index, obj) {
											$("#buyspuid").append(
													"<option value='"+obj[0]+"'>"
															+ obj[1]
															+ "</option>");
											$("#giftspuid").append(
													"<option value='"+obj[0]+"'>"
															+ obj[1]
															+ "</option>");
										});
									}
								});

						$.post("${ctx}/taobaoOrder/getShopList", null,
								function(data) {
									if (data) {
										$.each(data, function(index, obj) {
											$("#activityshop").append(
													"<option value='"+obj[0]+"'>"
															+ obj[1]
															+ "</option>");
										});
									}
								});
					});

	function addActivity() {
		showLayer();
		$("#buyspuid").val("");
		$("#giftspuid").val("");
		$("#activityshop").val("");
		$('#startTime').datetimebox('setValue', "");
		$('#endTime').datetimebox('setValue', "");
		$("#buyspuid").trigger("chosen:updated");
		$("#giftspuid").trigger("chosen:updated");
		chosen("#buyspuid");
		chosen("#giftspuid");
	}

	function showLayer() {
		showBg();
	}
	function showLayer1() {
		showBg1();
	}

	//关闭灰色 jQuery 遮罩
	function closeBg() {
		$("#fullbg,#dialog").hide();
	}
	function closeBg1() {
		$("#fullbg,#dialog1").hide();
	}
	//显示灰色 jQuery 遮罩层
	function showBg1() {
		var bh = $("body").height();
		var bw = $("body").width();
		$("#fullbg").css({
			height : bh,
			width : bw,
			display : "block"
		});
		$("#dialog1").show();
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

	function saveActivity() {
		var buyspuid = $("#buyspuid").val();
		var giftspuid = $("#giftspuid").val();
		var activityshop = $("#activityshop").val();
		var startTime = $('#startTime').datetimebox('getValue');
		var endTime = $('#endTime').datetimebox('getValue');

		if (!buyspuid) {
			$.messager.alert("消息", "请选择购买的宝贝");
			return;
		}
		if (!giftspuid) {
			$.messager.alert("消息", "请选择赠送的宝贝");
			return;
		}
		if (!activityshop) {
			$.messager.alert("消息", "请选择活动店铺");
			return;
		}
		if (!startTime) {
			$.messager.alert("消息", "请选择开始时间");
			return;
		}
		if (!endTime) {
			$.messager.alert("消息", "请选择结束时间");
			return;
		}
		$.messager.confirm("操作提示", "确认保存？", function(data) {
			if (data) {
				$.post("${ctx}/taobaoActivityConfig/addActivity", {
					buyspuid : buyspuid,
					giftspuid : giftspuid,
					activityshop : activityshop,
					startTime : startTime,
					endTime : endTime
				}, function(data) {
					if (data) {
						if (data.resultCode == 1) {
							$('#oproTable').datagrid('load');
							closeBg();
						} else {
							$.messager.alert("消息", data.resultMsg);
						}
					}
				});
			}
		});
	}

	function deleteActivity(id) {
		$.messager.confirm("操作提示", "确认删除？", function(data) {
			if (data) {
				$.post("${ctx}/taobaoActivityConfig/deleteTaobaoGift", {
					id : id
				}, function(data) {
					if (data) {
						if (data.resultCode == 1) {
							$('#oproTable').datagrid('load');
							closeBg();
						} else {
							$.messager.alert("消息", data.resultMsg);
						}
					}
				});
			}
		});
	}
</script>
</head>
<body>
	<div id="fullbg"></div>
	<div id="dialog">
		<div
			style="font-size: 16px; border-bottom: 1px solid #CCC; padding-bottom: 3px;">
			<font>新增</font><a href="#" onclick="closeBg();"
				style="background-color: #CCC; text-decoration: none; margin-left: 530px;">关闭</a>
		</div>
		<div
			style="padding: 5px; height: 330px; border-bottom: 1px solid #CCC;">
			<div style="width: 100%;" align="center">
				<table style="width: 90%; margin-top: 20px;" id="shopTable">
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">购买宝贝：</td>
						<td align="left"><select id="buyspuid" style="width: 205px"><option
									value="">--请选择--</option></select><font color="red"> *</font></td>
					</tr>
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">赠送宝贝：</td>
						<td align="left"><select id="giftspuid" style="width: 205px"><option
									value="">--请选择--</option></select><font color="red"> *</font></td>
					</tr>
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">活动店铺：</td>
						<td align="left"><select id="activityshop"
							style="width: 205px"><option value="">--请选择--</option></select><font
							color="red"> *</font></td>
					</tr>
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">活动开始时间：</td>
						<td align="left"><input class="easyui-datetimebox"
							name="startTime" id="startTime" style="width: 205px"><font
							color="red"> *</font></td>
					</tr>
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">活动结束时间：</td>
						<td align="left"><input class="easyui-datetimebox"
							name="endTime" id="endTime" style="width: 205px"><font
							color="red"> *</font></td>
					</tr>
				</table>
			</div>
		</div>
		<div style="font-size: 16px; padding: 3px;" align="center">
			<input type="button" class="btn btn-info"  value="确定" onclick="saveActivity()" /> <input
				type="button" class="btn btn-default"  value="取消" onclick="closeBg();"
				style="margin-left: 30px" />
		</div>
	</div>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="淘宝店活动配置" style="padding: 2px">
			<table id="oproTable">
			</table>
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table style="width: 100%">
						<tr>
							<td style="width: 75%;">宝贝编码 <input type="text"
								name="search_LIKE_spuid" style="width: 150px;" />宝贝名称 <input
								type="text" name="search_LIKE_taobaoPro.name"
								style="width: 150px;" />
							</td>
							<td style="width: 10%"><input type="button"
								style="width: 90px; height: 28px;" class="btn btn-info"  value="新增活动"
								onclick="addActivity()" /></td>
							<td style="width: 10%; text-align: left;" align="left"><a
								href="javascript:;" class="easyui-linkbutton"
								style="width: 80px;" data-options="iconCls:'icon-search'"
								id="search_id">搜索</a></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
