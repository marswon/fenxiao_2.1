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
											url : '${ctx}/taobaoConfig/getBaobeiList',
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
														field : 'spuid',
														title : '宝贝编码',
														width : 100,
														align : "center"
													},
													{
														field : 'name',
														title : '宝贝名称',
														width : 150,
														align : "center"
													},
													{
														field : 'businessType',
														title : '业务类型',
														width : 50,
														align : "center",
														formatter : function(
																val, row, index) {
															switch (val) {
															case 0:
																return "流量";
															case 1:
																return "话费";
															case 3:
																return "加油卡";
															default:
																return "其他";
															}
														}
													},
													{
														field : 'flowType',
														title : '流量类型',
														width : 50,
														align : "center",
														formatter : function(
																val, row, index) {
															if (row.businessType == 0) {

																switch (val) {
																case 0:
																	return "漫游";
																case 1:
																	return "本地";
																default:
																	return "其他";
																}
															} else {
																return "--";
															}
														}
													},
													{
														field : 'size',
														title : '下单编码',
														width : 50,
														align : "center"
													},
													{
														field : 'createTime',
														title : '创建时间',
														width : 80,
														align : "center",
														formatter : function(
																val, row, index) {
															return new Date(val)
																	.format("yyyy-MM-dd hh:mm:ss");
														}
													},
													{
														field : 'status',
														title : '状态',
														width : 60,
														align : "center",
														formatter : function(
																val, row, index) {
															switch (val) {
															case 0:
																return "<font color='white' style='background-color: green'>可用</font>";
															case 1:
																return "<font color='white' style='background-color: red'>不可用</font>";
															default:
																return "其他";
															}
														}
													},
													{
														field : 'statuss',
														title : '操作',
														width : 60,
														align : "center",
														formatter : function(
																val, row, index) {
															return "<input type='button' value='修改' onclick=toupdateTBPro("
																	+ row.id
																	+ ") />";
															// 															"<input type='button' value='删除' onclick='delBaobei("
															// 																	+ row.id
															// 																	+ ")' />";
														}
													} ] ]

										});

						$
								.post(
										"${ctx}/taobaoConfig/getBusinessType",
										null,
										function(data) {
											if (data) {
												$("#shopType")
														.html(
																"<option value=''>--请选择--</option>");
												$
														.each(
																data,
																function(key,
																		val) {
																	$(
																			"#shopType")
																			.append(
																					"<option value='"+key+"'>"
																							+ val
																							+ "</option>");
																	$(
																			"#belongto")
																			.append(
																					"<option value='"+key+"'>"
																							+ val
																							+ "</option>");
																	$(
																			"#searchBusinessType")
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

	function addBaobei() {
		showLayer();
		$("#belongToBusinessType").val("");
		$("#belongToBizType").val("");
		$("#updateTBProId").val("");
		$("#spuid").val("");
		$("#baobeiname").val("");
		$("#size").val("");
		$("#belongto").val("");
		$("#flowtype").val("");
		$("#flowTypeTR").hide();
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

	function saveBaobei() {
		var id = $("#updateTBProId").val();
		var spuid = $("#spuid").val();
		var baobeiname = $("#baobeiname").val();
		var size = $("#size").val();
		var belongto = $("#belongto").val();
		var flowtype = $("#flowtype").val();
		var bizType = $("#belongToBizType").val();
		if (!spuid) {
			$.messager.alert("消息", "请填写宝贝编码");
			return;
		}
		if (!baobeiname) {
			$.messager.alert("消息", "请填写宝贝名称");
			return;
		}
		if (!size) {
			$.messager.alert("消息", "请填写宝贝规格");
			return;
		}
		if (isNaN(size) || size % 1 != 0 || size.indexOf(".") >= 0) {
			$.messager.alert("消息", "规格只能为整数");
			return;
		}
		if (!belongto) {
			$.messager.alert("消息", "请填写宝贝所属店铺");
			return;
		}
		if ($("#belongToBusinessType").val() == "0" && !flowtype) {
			$.messager.alert("消息", "请选择流量类型");
			return;
		}
		$.messager.confirm("操作提示", "确认保存？", function(data) {
			if (data) {
				$.post("${ctx}/taobaoConfig/addTaobaoProduct", {
					id : id,
					spuid : spuid,
					baobeiname : baobeiname,
					size : size,
					belongto : belongto,
					flowtype : flowtype,
					bizType : bizType
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

	function delBaobei(id) {
		$.messager.confirm("操作提示", "确认删除？", function(data) {
			if (data) {
				$.post("${ctx}/taobaoConfig/deleteTaobaoProduct", {
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

	function addShop() {
		showLayer1();
		$("#supplierId").val("");
		$("#shopname").val("");
		$("#shopType").val("");
		$("#shopremark").val("");

	}

	function saveShop() {
		var supplierId = $("#supplierId").val();
		var shopname = $("#shopname").val();
		var shopType = $("#shopType").val();
		var remark = $("#shopremark").val();
		if (!supplierId) {
			$.messager.alert("消息", "请填写店铺编码");
			return;
		}
		if (!shopname) {
			$.messager.alert("消息", "请填写店铺名称");
			return;
		}
		if (!shopType) {
			$.messager.alert("消息", "请选择店铺类型");
			return;
		}
		$.post("${ctx}/taobaoConfig/addTaobaoShop", {
			supplierId : supplierId,
			name : shopname,
			businessType : shopType,
			remark : remark
		}, function(data) {
			$.messager.alert("消息", data.resultMsg);
			if (data.resultCode == 1) {
				closeBg1();
			}
		});

	}

	function chooseType() {
		var shopid = $("#belongto").val()
		if (shopid == 0) {
			$("#belongToBizType").val("ECARD_FLOWCHARGE");
			$("#flowTypeTR").show();
		} else if (shopid == 1) {
			$("#belongToBizType").val("ECARD_FLOWCHARGE");
			$("#flowTypeTR").hide();
		} else if (shopid == 3) {
			$("#belongToBizType").val("OIL");
			$("#flowTypeTR").hide();
		}
	}

	function toupdateTBPro(id) {
		showLayer();
		$.post("${ctx}/taobaoConfig/toUpdatePro", {
			id : id
		}, function(data) {
			if (data) {
				$("#spuid").val(data.spuid);
				$("#baobeiname").val(data.name);
				$("#size").val(data.size);
				$("#flowtype").val(data.flowType);
				$("#updateTBProId").val(data.id);
				$("#belongto").val(data.businessType);
				if (data.businessType == 0) {
					$("#flowTypeTR").show();
				} else {
					$("#flowTypeTR").hide();
				}
			}
		});
	}
</script>
</head>
<body>
	<input type="hidden" id="belongToBusinessType" />
	<input type="hidden" id="belongToBizType" />
	<input type="hidden" id="updateTBProId" />
	<div id="fullbg"></div>
	<div id="dialog">
		<div
			style="font-size: 16px; border-bottom: 1px solid #CCC; padding-bottom: 3px;">
			<font>新增/修改宝贝</font><a href="#" onclick="closeBg();"
				style="background-color: #CCC; text-decoration: none; margin-left: 460px;">关闭</a>
		</div>
		<div
			style="padding: 5px; height: 330px; border-bottom: 1px solid #CCC;">
			<div style="width: 100%;" align="center">
				<table style="width: 90%; margin-top: 20px;" id="shopTable">
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">宝贝编码：</td>
						<td align="left"><input id="spuid"
							style="font-weight: bold; width: 200px" /><font color="red">
								*</font></td>
					</tr>
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">宝贝名称：</td>
						<td align="left"><input id="baobeiname" style="width: 200px" /><font
							color="red"> *</font></td>
					</tr>
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">下单编码：</td>
						<td align="left"><input id="size" style="width: 200px" /><font
							color="red"> *</font></td>
					</tr>
					<tr style="height: 35px;">
						<td style="text-align: right; padding-right: 10px;">业务类型：</td>
						<td align="left"><select id="belongto" style="width: 205px"
							onchange="chooseType()"><option value="">--请选择--</option></select><font
							color="red"> *</font></td>
					</tr>
					<tr style="height: 35px;" id="flowTypeTR">
						<td style="text-align: right; padding-right: 10px;">流量类型：</td>
						<td align="left"><select id="flowtype" style="width: 205px"><option
									value="">--请选择--</option>
								<option value="0">漫游</option>
								<option value="1">本地</option></select><font color="red"> *</font></td>
					</tr>
				</table>
			</div>
		</div>
		<div style="font-size: 16px; padding: 3px;" align="center">
			<input type="button" class="btn btn-info"  value="确定" onclick="saveBaobei()" /> <input
				type="button" class="btn btn-default"  value="取消" onclick="closeBg();"
				style="margin-left: 30px" />
		</div>
	</div>
	<div id="dialog1">
		<div
			style="font-size: 16px; border-bottom: 1px solid #CCC; padding-bottom: 3px;">
			<font>新增店铺</font><a href="#" onclick="closeBg1();"
				style="background-color: #CCC; text-decoration: none; margin-left: 500px;">关闭</a>
		</div>
		<div style="height: 340px; width: 100%; overflow: auto;"
			align="center">
			<table style="width: 90%; margin-top: 20px;" id="shopTable">
				<tr style="height: 35px;">
					<td style="text-align: right; padding-right: 10px;">店铺编码(supplierId)：</td>
					<td align="left"><input id="supplierId"
						style="font-weight: bold; width: 200px" /><font color="red">
							*</font></td>
				</tr>
				<tr style="height: 35px;">
					<td style="text-align: right; padding-right: 10px;">店铺名称：</td>
					<td align="left"><input id="shopname" style="width: 200px" /><font
						color="red"> *</font></td>
				</tr>
				<tr style="height: 35px;">
					<td style="text-align: right; padding-right: 10px;">店铺类型：</td>
					<td align="left"><select id="shopType" style="width: 205px"><option
								value="">--请选择--</option></select><font color="red"> *</font></td>
				</tr>
				<tr style="height: 35px;">
					<td style="text-align: right; padding-right: 10px;">备注：</td>
					<td align="left"><input id="shopremark" style="width: 200px" /></td>
				</tr>
			</table>
		</div>
		<div style="font-size: 16px; padding: 3px; border-top: 1px solid #CCC"
			align="center">
			<input type="button" class="btn btn-info"  value="确定" onclick="saveShop()" /> <input
				type="button" class="btn btn-default"  value="取消" onclick="closeBg1();"
				style="margin-left: 30px" />
		</div>
	</div>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="淘宝店配置" style="padding: 2px">
			<table id="oproTable">
			</table>
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table style="width: 100%">
						<tr>
							<td style="width: 75%;">宝贝编码 <input type="text" name="spuid"
								style="width: 150px;" />宝贝名称 <input type="text" name="name"
								style="width: 150px;" />业务类型<select name="businessType"
								id="searchBusinessType" style="width: 120px;"><option
										value="">--请选择--</option></select>流量类型<select name="flowType"
								id="flowType" style="width: 100px;"><option value="">--请选择--</option>
									<option value="0">漫游</option>
									<option value="1">本地</option></select>
							</td>
							<td style="width: 10%"><input type="button"
								style="width: 80px; height: 28px;" class="btn btn-default"  value="新增宝贝"
								onclick="addBaobei()" /></td>
							<td style="width: 10%"><input type="button"
								style="width: 80px; height: 28px;" class="btn btn-default"  value="新增店铺"
								onclick="addShop()" /></td>
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
