<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>待处理订单</title>
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
	function agree(id) {
		console.log(11);
		$.messager.confirm("操作提示", "您确定要执行操作吗？", function(data) {
			if (data) {
				$.getJSON("${ctx}/financialManagement/updateRechargeStatus", {
					id : id
				}, function(data) {
					console.log(data);
					if (data.resultCode == -1) {
						$.messager.alert('消息', data.resultMsg);
					} else {
						$.messager.alert('消息', data.resultMsg);
						$("#waitorder").datagrid("reload");
						$("#rechargeRecord").datagrid("reload");
					}
				});
			}
		});
	}

	function disagree(id) {
		$.messager.confirm("操作提示", "您确定要执行操作吗？", function(data) {
			if (data) {
				$.getJSON("${ctx}/financialManagement/updateRecharge", {
					id : id
				}, function(data) {
					console.log(data);
					if (data.resultCode == -1) {
						$.messager.alert('消息', data.resultMsg);
					} else {
						$.messager.alert('消息', data.resultMsg);
						$("#waitorder").datagrid("reload");
						$("#rechargeRecord").datagrid("reload");

					}
				});
			}
		});
	}
	$(function() {
		//还款账单
		$("#waitorder")
				.datagrid(
						{
							url : "${ctx}/financialManagement/getAddBalanceList",
							nowrap : false,
							striped : true,
							remoteSort : false,
							pageSize : 20,
							fit : true,
							queryParams : {
								search_EQ_status : 0
							},
							singleSelect : true,//是否单选  
							pagination : true,//分页控件  
							rownumbers : true,//行号 
							fitColumns : true,//列宽自动填充满表格
							checkOnSelect : false,
							selectOnCheck : false,
							toolbar : "#hkDgTb",//工具栏
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
										field : 'accountName',
										title : '打款人姓名',
										width : 150,
										align : "center"
									},
									{
										field : 'account',
										title : '打款账号',
										width : 150,
										align : "center"
									},
									{
										field : 'addBalanceType',
										title : '类型',
										width : 50,
										align : "center",
										formatter : function(val, row, index) {
											if(row.addBalanceType == 1){
												return "<span style=\"color:white;background:green\">加款</span>";
											}else if(row.addBalanceType == 2){
												return "<span style=\"color:white;background:red\">授信</span>";
											}else if(row.addBalanceType == 3){
												return "<span style=\"color:white;background:green\">授信抵消</span>";
											}else {
												return "未知";
											}
										}
									},

									{
										field : 'submitTime',
										title : '申请加款时间',
										width : 80,
										align : "center"
									},
									{
										field : 'bankName',
										title : '所属银行名',
										width : 80,
										align : "center"
									},
									{
										field : 'amount',
										title : '充值金额(元)',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return "<b>¥" + (row.amount / 1000).toFixed(3) +"</b>";
										}
									},
									{
										field : 'submitUserId',
										title : '申请人',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {
											return row.adminInfo.realName;
										}
									},
									{
										field : 'description',
										title : '备注',
										width : 150,
										align : "center"
									},
									{
										field : 'eId',
										title : '企业名称',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return row.fxEnterprise.name;
										}
									},

									{
										field : 'process',
										title : '详情',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return '<input type="button" id="ok" value="通过" onclick="agree('
													+ row.id
													+ ')"/><input type="button" id="no" value="驳回" onclick="disagree('
													+ row.id + ')"></input>';
										}
									}, ] ],
						});
		$(window).resize(function() {
			$('#zdxqDlg').dialog('resize', {
				width : $(document.body).width(),
				height : $(document.body).height()
			});
		});
		$('#zdxqDlg').dialog({
			title : '账单详情',
			fit : true,
			draggable : false,
			closed : true,
			cache : false,
			modal : true
		});
// 		$('#eId').combobox({
// 			url : "${ctx}/userManage/getEnterpriseList",
// 			valueField : 'id',
// 			textField : 'name',
// 			editable : false,
// 			width : 150,
// 			panelHeight : 200,
// 			loadFilter : function(data) {
// 				if (data.content) {
// 					return data.content;
// 				} else {
// 					return false;
// 				}
// 			},
// 		});
		$(document).ready(function(){
			$.post("${ctx}/userManage/getEnterpriseList", null, function(data) {
				if (data) {
					$.each(data.content, function(index, obj) {
						$("#eId").append(
								"<option value='"+obj.id+"'>" + obj.name
										+ "</option>");
						$("#eId2").append(
								"<option value='"+obj.id+"'>" + obj.name
										+ "</option>");
					});
					chosen("#eId");
				}
			})
			
			$('#hkzdTabs').tabs({
			    border:false,
			    onSelect:function(title,index){
			        if(title == '加款记录'){
			        	chosen("#eId2");
			        	chosen("#addBalanceType");
			        }
			    }
			});
			
		});

		$("#rechargeRecord")
				.datagrid(
						{
							url : "${ctx}/financialManagement/getAddBalanceList",
							nowrap : false,
							striped : true,
							remoteSort : false,
							pageSize : 20,
							fit : true,
							queryParams : {
							//search_EQ_status : 0
							},
							singleSelect : true,//是否单选  
							pagination : true,//分页控件  
							rownumbers : true,//行号 
							fitColumns : true,//列宽自动填充满表格
							checkOnSelect : false,
							selectOnCheck : false,
							toolbar : "#hkDgTb2",//工具栏
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
										field : 'accountName',
										title : '打款人姓名',
										width : 100,
										align : "center"
									},
									{
										field : 'account',
										title : '打款账号',
										width : 200,
										align : "center"
									},
									{
										field : 'addBalanceType',
										title : '类型',
										width : 50,
										align : "center",
										formatter : function(val, row, index) {
											if(row.addBalanceType == 1){
												return "<span style=\"color:white;background:green\">加款</span>";
											}else if(row.addBalanceType == 2){
												return "<span style=\"color:white;background:red\">授信</span>";
											}else if(row.addBalanceType == 3){
												return "<span style=\"color:white;background:green\">授信抵消</span>";
											}else {
												return "未知";
											}										}
									},
									{
										field : 'submitTime',
										title : '申请加款时间',
										width : 100,
										align : "center"
									},
									{
										field : 'bankName',
										title : '所属银行',
										width : 80,
										align : "center"
									},
									{
										field : 'amount',
										title : '充值金额(元)',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return "<b>¥" + (row.amount / 1000).toFixed(3)+"</b>";
										}
									},
									{
										field : 'submitUserId',
										title : '申请人',
										width : 60,
										align : "center",
										formatter : function(val, row, index) {
											return row.adminInfo.realName;
										}
									},
									{
										field : 'description',
										title : '备注',
										width : 100,
										align : "center"
									},
									{
										field : 'eId',
										title : '企业名称',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return row.fxEnterprise.name;
										}
									},
									{
										field : 'comfirmTime',
										title : '确认时间',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.confirmTime) {
												return row.confirmTime;
											} else {
												return "";
											}
										}
									},
									{
										field : 'beforeRechargeBalance',
										title : '充值前余额',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return "<b>¥"
													+ (row.beforeRechargeBalance
													/ 1000).toFixed(3)+"</b>";
										}
									},
									{
										field : 'afterRechargeBalance',
										title : '充值后余额',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return "<b>¥"
													+ (row.afterRechargeBalance
													/ 1000).toFixed(3)+"</b>";
										}
									},
									{
										field : 'confirmUser',
										title : '确认人',
										width : 60,
										align : "center",
										formatter : function(val, row, index) {
											if (row.confirmUser) {
												return row.confirmUser.realName;
											} else {
												return "";
											}
										}
									},
									{
										field : 'debt',
										title : '剩余欠款(元)',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return row.debt==0? "<span style=\"color:white;background:green\"><b>¥" + (row.debt / 1000).toFixed(3) + "</b></span>" : "<span style=\"color:white;background:red\"><b>¥" + (row.debt / 1000).toFixed(3) + "</b></span>";
										}
									},
									{
										field : 'status',
										title : '状态',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (row.status == 0) {
												return '<span style="color:white;background:orange">未处理</span>';
											} else if (row.status == 1) {
												return '<span style="color:white;background:green">已通过</span>';
											} else {
												return '<span style="color:white;background:red">已驳回</span>';
											}
										}
									} ] ],
						});
		$(window).resize(function() {
			$('#zdxqDlg').dialog('resize', {
				width : $(document.body).width(),
				height : $(document.body).height()
			});
		});
		$('#zdxqDlg').dialog({
			title : '账单详情',
			fit : true,
			draggable : false,
			closed : true,
			cache : false,
			modal : true
		});
// 		$('#eId2').combobox({
// 			url : "${ctx}/userManage/getEnterpriseList",
// 			valueField : 'id',
// 			textField : 'name',
// 			editable : false,
// 			width : 150,
// 			panelHeight : 200,
// 			loadFilter : function(data) {
// 				if (data.content) {
// 					return data.content;
// 				} else {
// 					return false;
// 				}
// 			},
// 		});
	});
	//普通账单
	function loadHk() {
		var params = $('#waitorder').datagrid('options').queryParams;
		var fields = $('#queryHkForm').serializeArray();
		$.each(fields, function(i, field) {
			params[field.name] = $.trim(field.value);
		});
		$('#waitorder').datagrid('load');
	}
	function loadHk1() {
		var params = $('#rechargeRecord').datagrid('options').queryParams;
		var fields = $('#queryHkForm1').serializeArray();
		$.each(fields, function(i, field) {
			params[field.name] = $.trim(field.value);
		});

		$('#rechargeRecord').datagrid('load');
	}
	//查看详情
	/*function openZd(id) {
		var zdxqIframe = $('#zdxqIframe');
		var url = '${ctx}/myWorkflow/queryUserBill?id=' + id;
		zdxqIframe.attr('src', url);
		$('#zdxqDlg').dialog('open');
	}*/
	//导出Excel表格
	
	function hkExportExcel() {
		$.messager.confirm('确认', '确认把该搜索结果导出Excel表格 ？', function(r) {
			if (r) {
				var startTime = $('#startTime2').datetimebox('getValue');
				var endTime = $('#endTime2').datetimebox('getValue');
				var eId = $('#eId2').val();
				var eName = $('#eId2').find("option:selected").text();
				//	var orderNo = $("#billCode").val();
				//	var orderStatus = $("#select_value").val();
				//	var provinceId = $("#provinceId").val();
				//	var completeStartTime1 = $('#completeStartTime1').datetimebox(
				//			'getValue');
				//	var completeEndTime1 = $('#completeEndTime1').datetimebox(
				//			'getValue');
				$.messager.progress({
					title : '处理中',
					msg : '请稍后',
				});
				$.messager.progress('close');

				location.href = "${ctx}/export/exportBalanceManageExcel?startTime="
						+ startTime + "&endTime=" + endTime + "&eId=" + eId
						+ "&eName=" + eName;
			}
		});
	}
</script>
</head>
<body>
	<div id="hkzdTabs" class="easyui-tabs" data-options="fit:true">
		<div id="hkzdTab" title="待处理加款申请" style="padding: 2px">
			<table id="waitorder">
			</table>
			<div id="hkDgTb">
				<form id="queryHkForm">
					<table class=" datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px;">
						<tr>
							<td style="text-align: right; vertical-align: middle;"><span>申请开始时间</span></td>
							<td>
								<table class="datagrid-cell datagrid-cell-c1-status"
									style="border-collapse: separate; border-spacing: 0px; height: 15px;">
									<tr>
										<td style="border: none;"><input
											class="easyui-datetimebox" name="startTime" id="startTime1"
											data-options="showSeconds:false" style="width: 150px"></td>
										<td style="border: none;">申请结束时间<input
											class="easyui-datetimebox" name="endTime" id="endTime1"
											data-options="showSeconds:false" style="width: 150px"></td>
									</tr>
								</table>
							<td>所属企业</td>
							<td><select id="eId" name="search_EQ_fxEnterprise.id"><option value="">--请选择--</option></select></td>
							<td>
								<table>
									<tr>

										<td style="border: none; vertical-align: middle;"><a
											href="javascript:;" class="easyui-linkbutton"
											data-options="iconCls:'icon-search'" onclick="loadHk()">搜索</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
						</tr>
					</table>
				</form>
			</div>

		</div>

		<div id="hkzdTab" title="加款记录" style="padding: 2px">
			<table id="rechargeRecord">
			</table>
			<div id="hkDgTb2">
				<form id="queryHkForm1">
					<table class="datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px;">
						<tr>
							<td style="text-align: right; vertical-align: middle;"><span>申请开始时间</span></td>
							<td>
								<table class="datagrid-cell datagrid-cell-c1-status"
									style="border-collapse: separate; border-spacing: 0px; height: 15px;">
									<tr>
										<td style="border: none;"><input
											class="easyui-datetimebox" name="startTime" id="startTime2"
											data-options="showSeconds:false" style="width: 150px"></td>
										<td style="border: none;">申请结束时间<input
											class="easyui-datetimebox" name="endTime" id="endTime2"
											data-options="showSeconds:false" style="width: 150px"></td>
									</tr>
								</table>
							<td>所属企业</td>
							<td><select id="eId2" name="search_EQ_fxEnterprise.id"><option value="">--请选择--</option></select></td>
							<td>类型</td>
							<td><select id="addBalanceType" name="search_EQ_addBalanceType" style="width: 120px;"><option value="">--请选择--</option><option value="1">加款</option><option value="2">授信</option><option value="3">授信抵消</option></select></td>
							<td>
								<table>
									<tr>
										<td style="border: none; vertical-align: middle;"><a
											href="javascript:;" class="easyui-linkbutton"
											data-options="iconCls:'icon-search'" onclick="loadHk1()">搜索</a>
										</td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td style="border: none;"><input type="button"
											onclick="hkExportExcel();" value="导出excel"
											style="display: block; size: 20px; background-color: #95B8E7"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
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