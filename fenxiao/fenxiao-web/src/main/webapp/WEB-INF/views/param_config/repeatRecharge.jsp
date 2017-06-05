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

#enterpriseIdTable tr:HOVER {
	background-color: #DDD;
}

#chosenChannel tr:HOVER {
	background-color: #DDD;
}
</style>
<script type="text/javascript">
	var enterpriseMap = {};
	$(document).ready(
			function() {
				$.each(provinceMap, function(key, value) {
					$("#provinceId").append(
							"<option value='"+key+"'>" + value + "</option>");
					$("#provinceIdInner").append(
							"<option value='"+key+"'>" + value + "</option>");
				});
				chosen("#yysTypeId");
				chosen("#provinceId");
				chosen("#flowType");

				$.post("${ctx}/repeatrecharge/getEnterpriseList", null,
						function(data) {
							if (data) {
								$.each(data, function(index, obj) {
									$("#enterpriseId").append(
											"<option value='"+obj[0]+"'>"
													+ obj[1] + "</option>");
									enterpriseMap[obj[0]] = obj[1];
								});
							}
						});
				loadTable();
			});

	function addRepeat() {
		$("#repeatRechargeId").val("");
		showLayer();
		// 		$("#leftyysTypeId").val("");
		// 		$("#rightChannelId").val("");
		// 		$("#leftChannelId").trigger("chosen:updated");
		// 		$("#rightChannelId").trigger("chosen:updated");
		// 		$("#leftOproId").html("<option value=''>--请选择--</option>");
		// 		$("#rightOproId").html("<option value=''>--请选择--</option>");
		// 		$("#leftOproId").chosen("destroy");
		// 		$("#rightOproId").chosen("destroy");
		// 		chosen("#leftOproId");
		// 		chosen("#rightOproId");
		$("#yysTypeIdInner").val("");
		$("#provinceIdInner").val("");
		$("#flowTypeInner").val("");
		$("#yysTypeIdInner").trigger("chosen:updated");
		$("#provinceIdInner").trigger("chosen:updated");
		$("#flowTypeInner").trigger("chosen:updated");
		chosen("#yysTypeIdInner");
		chosen("#provinceIdInner");
		chosen("#flowTypeInner");
		$("#chosenChannel").html("");
		$("#channelList").html("");
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

	function saveRepeat() {
		var channelTRArray = $("#channelList tr");
		var yysTypeId = $("#yysTypeIdInner").val();
		var provinceId = $("#provinceIdInner").val();
		var flowType = $("#flowTypeInner").val();
		if (!yysTypeId) {
			$.messager.alert("消息", "请选择运营商");
			return;
		}
		if (!provinceId) {
			$.messager.alert("消息", "请选择省份");
			return;
		}
		if (!flowType) {
			$.messager.alert("消息", "请选择流量类型");
			return;
		}
		var channelIdStr = "";
		$.each(channelTRArray, function() {
			var channelId = $(this).attr("id");
			channelIdStr += channelId.split("_")[0] + "_"
					+ channelId.split("_")[1] + ",";
		});
		$.messager.confirm("操作提示", "确认保存？", function(data) {
			if (data) {
				$.post("${ctx}/repeatrecharge/saveRepeatRecharge", {
					id : $("#repeatRechargeId").val(),
					yysTypeId : yysTypeId,
					provinceId : provinceId,
					flowType : flowType,
					channelIdStr : channelIdStr
				}, function(data) {
					if (data) {
						$.messager.alert("消息", data.resultMsg);
						if (data.resultCode == 1) {
							loadTable();
							closeBg();
						}
					}
				});
			}
		});
	}

	function loadTable() {
		var yysTypeId = $("#yysTypeId").val();
		var provinceId = $("#provinceId").val();
		var flowType = $("#flowType").val();
		// 		
		// 	if (!yysTypeId) {
		// 			$.messager.alert("消息", "请选择运营商");
		// 			return;
		// 		}
		// 		if (!provinceId) {
		// 			$.messager.alert("消息", "请选择省份");
		// 			return;
		// 		}
		// 		if (!flowType) {
		// 			$.messager.alert("消息", "请选择流量类型");
		// 			return;
		// 		}
		$
				.post(
						"${ctx}/repeatrecharge/getRepeatRechargeListNew",
						{
							yysTypeId : yysTypeId,
							provinceId : provinceId,
							flowType : flowType
						},
						function(data) {
							$("#oproTable tr:gt(0)").remove();
							if (data) {
								$
										.each(
												data,
												function(index, obj) {
													var str = "";
													if (obj.repeatList) {
														$
																.each(
																		obj.repeatList,
																		function(
																				index1,
																				obj1) {
																			str += (index1 + 1)
																					+ ". "
																					+ obj1
																					+ "<br/>";
																		});
													}
													$("#oproTable")
															.append(
																	"<tr><td>"
																			+ (index + 1)
																			+ "</td><td>"
																			+ showYysName(obj.yysTypeId)
																			+ "</td><td>"
																			+ showProvince(obj.provinceId)
																			+ "</td><td>"
																			+ (obj.flowType == 0 ? "漫游"
																					: "本地")
																			+ "</td><td style='text-align: left;padding-left: 50px'>"
																			+ str
																			+ "</td></tr>");
												});
							}
						});
	}

	function showYysName(yys) {
		if (yys == "1") {
			return "电信";
		} else if (yys == "2") {
			return "移动";
		} else {
			return "联通";
		}
		return "--";
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
	function search() {
		loadTable();
	}

	function setRepeatRechargeEid() {
		showLayer1();
		chosen("#enterpriseId");
		$
				.post(
						"${ctx}/repeatrecharge/getPassEid",
						null,
						function(data) {
							$("#enterpriseIdTable").html("");
							if (data) {
								var array = data.split(",");
								for (var index = 0; index < array.length; index++) {
									if (array[index]) {
										var id = array[index];
										var name = enterpriseMap[id];
										$("#enterpriseIdTable")
												.append(
														"<tr class='enterpriseTR' style='border-bottom:1px solid #ccc;height:30px' id='eid_"+id+"'><td style='width:90%'>"
																+ name
																+ "</td><td><a href='#' onclick='deleteEnterprise(\""
																+ id
																+ "\")'>删除</a></td></tr>");
									}
								}
							}
						});
	}
	function addEnterpriseId() {
		var id = $("#enterpriseId").val();
		var name = $("#enterpriseId").find("option:selected").text();
		if (!id) {
			return;
		}
		if ($("#eid_" + id).length > 0) {
			$.messager.alert("消息", "已存在");
			return;
		}
		$("#enterpriseIdTable")
				.append(
						"<tr class='enterpriseTR' style='border-bottom:1px solid #ccc;height:30px' id='eid_"+id+"'><td style='width:90%'>"
								+ name
								+ "</td><td><a href='#' onclick='deleteEnterprise(\""
								+ id + "\")'>删除</a></td></tr>");
	}
	function deleteEnterprise(val) {
		$("#eid_" + val).remove();
	}

	function saveEnterpriseId() {
		var trArray = $(".enterpriseTR");
		var result = ",";
		$.each(trArray, function() {
			result += $(this).attr("id").split("_")[1] + ",";
		});
		$.post("${ctx}/repeatrecharge/updateSystemConfig", {
			systemValue : result
		}, function(data) {
			$.messager.alert("消息", data.resultMsg);
			if (data.resultCode == 1) {
				closeBg1();
			}
		});
	}

	function searchChannel() {
		var yysTypeId = $("#yysTypeIdInner").val();
		var provinceId = $("#provinceIdInner").val();
		var flowType = $("#flowTypeInner").val();
		if (!yysTypeId) {
			$.messager.alert("消息", "请选择运营商");
			return;
		}
		if (!provinceId) {
			$.messager.alert("消息", "请选择省份");
			return;
		}
		if (!flowType) {
			$.messager.alert("消息", "请选择流量类型");
			return;
		}
		$
				.post(
						"${ctx}/repeatrecharge/searchExistRepeatRehcarge",
						{
							yysTypeId : yysTypeId,
							provinceId : provinceId,
							flowType : flowType
						},
						function(data1) {
							$("#channelList").html("");
							if (data1) {
								$
										.each(
												data1,
												function(index, obj) {
													var trId = obj[0] + "_"
															+ obj[2]
															+ "_channelList";
													$("#channelList")
															.append(
																	"<tr id='"+trId+ "' style=\"border-bottom: 1px #CCCCCC solid;\"><td style=\"width: 85%\">"
																			+ ($("#channelList tr").length + 1)
																			+ ". "
																			+ obj[1]
																			+ " ["
																			+ (obj[2] == 0 ? "漫游"
																					: "本地")
																			+ "]</td><td><a href=\"#\" onclick='deleteFromChannelList(\""
																			+ trId
																			+ "\",\""
																			+ obj[1]
																			+ "\",\""
																			+ obj[2]
																			+ "\")'>删除</a></td></tr>");
												});
							}
							$
									.post(
											"${ctx}/repeatrecharge/searchChannel",
											{
												yysTypeId : yysTypeId,
												provinceId : provinceId,
												flowType : flowType
											},
											function(data) {
												$("#chosenChannel").html("");
												if (data) {

													$
															.each(
																	data,
																	function(
																			index,
																			obj) {
																		var trId = obj[0]
																				+ "_"
																				+ obj[2]
																				+ "_chosenChannel";
																		if ($("#"
																				+ obj[0]
																				+ "_"
																				+ obj[2]
																				+ "_channelList").length < 1) {
																			$(
																					"#chosenChannel")
																					.append(
																							"<tr id='"+trId+"' style=\"border-bottom: 1px #CCCCCC solid;\"><td style=\"width: 85%\">"
																									+ obj[1]
																									+ " ["
																									+ (obj[2] == 0 ? "漫游"
																											: "本地")
																									+ "]</td><td><a href=\"#\" onclick='addToChannelList(\""
																									+ trId
																									+ "\",\""
																									+ obj[1]
																									+ "\",\""
																									+ obj[2]
																									+ "\")'>添加</a></td></tr>");
																		}

																	});
												}
											});
						});

	}

	function addToChannelList(val, channelName, flowType) {
		var channelId = val.split("_")[0];
		var trId = channelId + "_" + flowType + "_channelList";
		var tdHtml = $("#" + val).html();
		$("#" + val).remove();
		if ($("#" + trId).length >= 1) {
			$.messager.alert("消息", "已添加到列表");
			return;
		}

		$("#channelList")
				.append(
						"<tr id='"+trId+ "' style=\"border-bottom: 1px #CCCCCC solid;\"><td style=\"width: 85%\">"
								+ ($("#channelList tr").length + 1)
								+ ". "
								+ channelName
								+ " ["
								+ (flowType == 0 ? "漫游" : "本地")
								+ "]</td><td><a href=\"#\" onclick='deleteFromChannelList(\""
								+ trId
								+ "\",\""
								+ channelName
								+ "\",\""
								+ flowType + "\")'>删除</a></td></tr>");

	}

	function deleteFromChannelList(val, channelName, flowType) {
		var channelId = val.split("_")[0];
		var trId = channelId + "_" + flowType + "_chosenChannel";
		var tdHtml = $("#" + val).html();
		$("#" + val).remove();
		var listTr = $("#channelList tr");
		$.each(listTr, function(index) {
			$(this).html($(this).html().replace(/\d+\./, (index + 1) + "."));
		});
		if ($("#" + trId).length >= 1) {
			$.messager.alert("消息", "已删除");
			return;
		}

		$("#chosenChannel")
				.append(
						"<tr id='"+trId+ "' style=\"border-bottom: 1px #CCCCCC solid;\"><td style=\"width: 85%\">"
								+ channelName
								+ " ["
								+ (flowType == 0 ? "漫游" : "本地")
								+ "]</td><td><a href=\"#\" onclick='addToChannelList(\""
								+ trId
								+ "\",\""
								+ channelName
								+ "\",\""
								+ flowType + "\")'>添加</a></td></tr>");

	}
</script>
</head>
<body>
	<input id="repeatRechargeId" type="hidden" value="" />
	<div id="fullbg"></div>
	<div id="dialog">
		<div
			style="font-size: 16px; border-bottom: 1px solid #CCC; padding-bottom: 3px;">
			<font>复充配置</font><a href="#" onclick="closeBg();"
				style="background-color: #CCC; text-decoration: none; margin-left: 500px;">关闭</a>
		</div>
		<div
			style="padding: 5px; height: 330px; border-bottom: 1px solid #CCC;">
			<div style="width: 100%; border-bottom: 1px solid #CCCCCC"
				align="center">
				<table style="width: 100%">
					<tr>
						<td style="width: 30%;">运营商：<select id="yysTypeIdInner"
							style="width: 100px"><option value="">--请选择--</option>
								<option value="1">电信</option>
								<option value="2">移动</option>
								<option value="3">联通</option></select></td>
						<td style="width: 30%">省份：<select id="provinceIdInner"
							style="width: 100px"><option value="">--请选择--</option></select></td>
						<td style="width: 32%">流量类型：<select id="flowTypeInner"
							style="width: 100px"><option value="">--请选择--</option>
								<option value="0">漫游</option>
								<option value="1">本地</option></select></td>
						<td style="width: 8%"><input type="button"
							style="width: 50px; height: 28px;" class="btn btn-default btn-small"  value="搜索"
							onclick="searchChannel()" /></td>
					</tr>
				</table>
			</div>
			<div
				style="width: 280px; height: 290px; border: 2px solid #CCC; float: left; font-size: 16px; margin-top: 3px; overflow: auto;">
				<table id="chosenChannel" style="width: 100%"></table>
			</div>
			<div
				style="width: 280px; height: 290px; border: 2px solid #CCC; float: left; font-size: 16px; margin-top: 3px; margin-left: 20px; overflow: auto;">
				<table id="channelList" style="width: 100%"></table>
			</div>
		</div>
		<div style="font-size: 16px; padding: 3px;" align="center">
			<input type="button" class="btn btn-info"  value="确定" onclick="saveRepeat()" /> <input
				type="button" class="btn btn-default"  value="取消" onclick="closeBg();"
				style="margin-left: 30px" />
		</div>
	</div>
	<div id="dialog1">
		<div
			style="font-size: 16px; border-bottom: 1px solid #CCC; padding-bottom: 3px;">
			<font>设置复充企业</font><a href="#" onclick="closeBg1();"
				style="background-color: #CCC; text-decoration: none; margin-left: 460px;">关闭</a>
		</div>
		<div
			style="height: 35px; border-bottom: 1px solid #CCC; font-size: 16px; padding-top: 5px;">
			企业：<select id="enterpriseId"><option value="">--请选择--</option></select>
			<input type="button"  class="btn btn-default btn-small"  value="添加" onclick="addEnterpriseId()" />
		</div>
		<div style="height: 300px; width: 100%; overflow: auto;"
			align="center">
			<table style="width: 80%" id="enterpriseIdTable"></table>
		</div>
		<div style="font-size: 16px; padding: 3px; border-top: 1px solid #CCC"
			align="center">
			<input type="button" class="btn btn-info"  value="确定" onclick="saveEnterpriseId()" /> <input
				type="button" class="btn btn-default"  value="取消" onclick="closeBg1();"
				style="margin-left: 30px" />
		</div>
	</div>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="复充配置" style="padding: 2px">
			<div style="width: 100%" align="center">
				<div style="width: 900px; border-bottom: 1px solid #CCCCCC"
					align="center">
					<table style="width: 100%">
						<tr>
							<td style="width: 20%;">运营商：<select id="yysTypeId"
								style="width: 100px"><option value="">--请选择--</option>
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option></select></td>
							<td style="width: 20%">省份：<select id="provinceId"
								style="width: 100px"><option value="">--请选择--</option></select></td>
							<td style="width: 25%">流量类型：<select id="flowType"
								style="width: 100px"><option value="">--请选择--</option>
									<option value="0">漫游</option>
									<option value="1">本地</option></select></td>
							<td style="width: 10%"><input type="button"
								style="width: 60px; height: 28px;" class="btn btn-default btn-small" value="查询" onclick="search()" /></td>
							<td style="width: 15%"><input type="button"
								style="width: 100px; height: 28px;" class="btn btn-info btn-small"  value="设置复充企业"
								onclick="setRepeatRechargeEid()" /></td>
							<td style="width: 20%"><input type="button"
								style="width: 140px; height: 28px;" class="btn btn-info btn-small"  value="添加/修改复充配置"
								onclick="addRepeat()" /></td>
						</tr>
					</table>
				</div>
				<div style="height: 5px;"></div>
				<div style="width: 900px" id="opro">
					<table style="width: 100%" id="oproTable">
						<tr>
							<th style="width: 8%">序号</th>
							<th style="width: 15%">运营商</th>
							<th style="width: 15%">省份</th>
							<th style="width: 15%">流量类型</th>
							<th style="width: 47%">复充渠道</th>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
