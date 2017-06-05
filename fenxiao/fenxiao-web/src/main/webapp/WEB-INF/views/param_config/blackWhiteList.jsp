<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>黑白名单</title>
<style type="text/css">
form {
	margin: 0px 0 10px;
}

#ok {
	color: rgb(255, 255, 255);
	font-size: 12px;
	padding-top: 3px;
	padding-bottom: 3px;
	padding-left: 6px;
	padding-right: 6px;
	border-width: 2px;
	border-color: rgb(197, 229, 145);
	border-style: solid;
	border-radius: 8px;
	background-color: rgb(14, 143, 40);
}

#ok:hover {
	color: #ffffff;
	background-color: #78c300;
	border-color: #c5e591;
}

#no {
	color: rgb(255, 255, 255);
	font-size: 12px;
	padding-top: 3px;
	padding-bottom: 3px;
	padding-left: 6px;
	padding-right: 6px;
	border-width: 2px;
	border-color: #fad2e1;
	border-style: solid;
	border-radius: 8px;
	background-color: rgb(230, 23, 33);
	border-style: solid;
}

#no:hover {
	color: #ffffff;
	background-color: #ff3300;
	border-color: #fad2e1;
}
</style>
<script>
	var flag = false;

	jQuery(function($) {
		$("#orderList")
				.datagrid(
						{
							url : '${ctx}/ExceptionOrder/getOrderList',
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
										field : 'midName',
										title : '企业名称',
										width : 130,
										align : "center"
									},
									{
										field : 'downstreamOrderNo',
										title : '订单号',
										width : 250,
										align : "center"
									},
									{
										field : 'mobile',
										title : '手机号',
										width : 120,
										align : "center"
									},
									{
										field : 'clientSubmitTime',
										title : '客户下单时间',
										width : 120,
										align : "center"
									},
									{
										field : 'provinceId',
										title : '省份',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.provinceId) {
												var yys = "";
												if (row.yysTypeId == 1) {
													yys = "电信";
												} else if (row.yysTypeId == 2) {
													yys = "移动";
												} else if (row.yysTypeId == 3) {
													yys = "联通";
												}
												return '<span style="color:white;background:green">'
														+ showProvince(row.provinceId)
														+ '</span>'
														+ '<span style="color:white;background:green">'
														+ yys + '</span>';
											} else {
												return "";
											}
										}
									},
									{
										field : 'size',
										title : '面值',
										width : 50,
										align : "center"
									},
									{
										field : 'flowType',
										title : '类型',
										width : 50,
										align : "center",
										formatter : function(val, row, index) {
											if (row.flowType == 1) {
												return "本地";
											} else {
												return "漫游";
											}
										}
									},
									{
										field : 'ip',
										title : 'IP',
										width : 120,
										align : "center"
									},
									{
										field : 'orderType',
										title : '订单类型',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return '提交失败';
											} else if (val == 1) {
												return '黑名单';
											} else if (val == 2) {
												return '白名单';
											}
										}
									},
									{
										field : 'exceptionCode',
										title : '订单状态',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (val == "0000") {
												return '<span style="color:white;background:green">成功</span>';
											} else {
												return '<span style="color:white;background:red">失败</span>';
											}
										}
									},
									{
										field : 'message',
										title : 'info',
										width : 120,
										align : "center"
									},
									{
										field : 'exceptionDesc',
										title : '描述',
										width : 120,
										align : "center"
									},
									{
										field : 'id',
										title : '操作',
										width : 180,
										align : "center",
										formatter : function(val, row, index) {
											return '<button style="color:white;background:green;font-size:.1rem" onclick="handWorkStatus(\''
													+ row.id
													+ '\',\''
													+ 1
													+ '\')">回调成功</button><button style="color:white;background:red;font-size:.1rem" onclick="handWorkStatus(\''
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
			url : "${ctx}/userManage/getEnterpriseList",
			valueField : 'mid',
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
	/* 	$('#webinformation1').dialog({
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
			title : "&ensp;编辑",
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
		var fields = $('#queryExceptionForm').serializeArray();
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
				$.getJSON("${ctx}/handwork/callbackExceptionOrder", {
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
								var eId = $('#eId').combobox('getValue');
								var eName = $('#eId').combobox('getText');
								var channelId = $('#channelId').combobox(
										'getValue');

								var provinceId = $("#provinceId").val();
								var isNormal = $("#isNormal").val();
								$.messager.progress({
									title : '处理中',
									msg : '请稍后',
								});
								$.messager.progress('close');

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
										+ "&isNormal=" + isNormal;
							}
						});
	}
	/**
	 * 保存黑名单入库
	 */
	function saveblack() {
		$('#queryHkForm').form('submit', {
			url : "${ctx}/blackwhitelist/saveBlackList",
			onSubmit : function(param) {
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.alert("消息", d.resultMsg);
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
	/**
	黑名单号码
	 */

	$.ajax({
		type : "POST",
		url : "${ctx}/blackwhitelist/queryBlackList",
		dataType : 'text',
		success : function(data) {
			var jsonData = eval("(" + data + ")");
			var arr = eval(data);
			var phone = arr[0];
			var str = phone;
			$(".blacktext").val(str);
			//以下代码不可删除		    
			/* $(".blacktext").focus(function(){
			    var v=this.value;
			    if(v == str){
			        $(this).val("").addClass("focus");
			    }
			}).blur(function(){
			    var v=this.value;
			    if(v == ""){
			        $(this).val(str).removeClass("focus");			             
			    }
			}); */
		}
	});

	/**
	白名单号码
	 */

	$.ajax({
		type : "POST",
		url : "${ctx}/blackwhitelist/queryWhiteList",
		dataType : 'text',
		success : function(data) {
			var jsonData = eval("(" + data + ")");
			var arr = eval(data);
			var phone = arr[0];
			var str = phone;
			$(".whitetext").val(str);
			//以下代码不可删除	
			/*
			$(".whitetext").focus(function(){
			    var v=this.value;
			    if(v == str){
			        $(this).val("").addClass("focus");
			    }
			}).blur(function(){
			    var v=this.value;
			    if(v == ""){
			        $(this).val(str).removeClass("focus");
			         
			    }
			}); */
		}
	});

	/**
	 * 保存白名单入库
	 */
	function savewhite() {
		$('#queryHkForm1').form('submit', {
			url : "${ctx}/blackwhitelist/saveWhiteList",
			onSubmit : function(param) {
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.alert("消息", d.resultMsg);
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
</script>
</head>
<body>
	<div id="hkzdTabs" class="easyui-tabs" data-options="fit:true">
		<div id="hkzdTab" title="黑名单" style="padding: 2px">
			<table id="waitorder">
			</table>
			<div id="hkDgTb">
				<form id="queryHkForm">
					<table class=" datagrid-cell datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px;">
						<tr>
							<td>
								<table class="datagrid-cell datagrid-cell-c1-status"
									style="border-collapse: separate; border-spacing: 0px; height: 15px;">

									<tr>
										<td><b style="size:">保存黑名单(文本框中是已存在的黑名单):</b></td>
									</tr>
									<tr>
										<td><textarea class="blacktext" type="text" id="black"
												name="black" style="height: 120px; width: 500px" size="14"></textarea>多个号码用英文逗号分割(,)
										</td>
									</tr>
									<tr>
										<td>
											<button type="button" class="btn btn-primary" onclick="saveblack()">保存</button>
										</td>
									</tr>
								</table>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div id="hkzdTab" title="白名单" style="padding: 2px">
			<table id="rechargeRecord">
			</table>
			<div id="hkDgTb2">
				<form id="queryHkForm1">
					<table class=" datagrid-cell datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px;">
						<tr>
							<td>
								<table class="datagrid-cell datagrid-cell-c1-status"
									style="border-collapse: separate; border-spacing: 0px; height: 15px;">
									<tr>
										<td><b style="size:">保存白名单(文本框中是已存在的白名单):</b></td>
									</tr>
									<tr>
										<td><textarea class="whitetext" type="text" id="white"
												name="white" style="height: 120px; width: 500px" size="14"></textarea>多个号码用英文逗号分割(,)
										</td>
									</tr>
									<tr>
										<td>
											<button type="button"class="btn btn-primary" onclick="savewhite()">保存</button>
										</td>
									</tr>
								</table>
					</table>
				</form>
			</div>
		</div>
		<div id="hkzdTab" title="特殊订单记录" style="padding: 2px">
			<table id="orderList"></table>
			<div id="xxDgTb">
				<form id="queryExceptionForm">
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
							<td><span>所属企业</span><input id="eId" name="search_EQ_mid"></td>
							<td>
								<table>
									<tr>
										<!-- 	<td><select style="margin: 0; width: 100%;"
											name="search_EQ_downstreamStatus" id="downstreamStatus">
												<option value="">请选择下游订单状态</option>
												<option value="0">成功</option>
												<option value="1">失败</option>
												<option value="2">处理中</option>
												<option value="3">提交成功</option>
										</select></td> -->
										<!-- 	<td><select style="margin: 0; width: 100%;"
											name="search_EQ_yysTypeId" id="select_value">
												<option value="">请选择运营商</option>
												<option value="1">电信</option>
												<option value="2">移动</option>
												<option value="3">联通</option>
										</select></td> -->
										<td><select style="margin: 0; width: 100%;"
											name="search_EQ_provinceId" id="provinceId">
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
										<td><select style="margin: 0; width: 100%;"
											name="search_EQ_orderType" id="orderType">
												<option value="">请选择订单类型</option>
												<option value="0">提交失败</option>
												<option value="1">黑名单</option>
												<option value="2">白名单</option>
										</select></td>
										<!-- <td style="border: none;"><input type="button"
											onclick="hkExportExcel();" value="导出excel"
											style="display: block; size: 20px; background-color: #95B8E7"></td> -->
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
	<div id="zdxqDlg">
		<iframe id="zdxqIframe" frameborder="0" marginheight="0"
			marginwidth="0" style="width: 100%; height: 99%;" scrolling="yes"></iframe>
	</div>
</body>
</html>