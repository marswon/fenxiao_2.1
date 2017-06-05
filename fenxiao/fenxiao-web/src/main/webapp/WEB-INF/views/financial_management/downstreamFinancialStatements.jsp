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
</style>
<script>
	$(function() {
		//还款账单
		$("#waitorder")
				.datagrid(
						{
							url : "${ctx}/financialManagement/getDownstreamFinancialStatements",
							nowrap : false,
							striped : true,
							remoteSort : false,
							pageSize : 20,
							fit : true,
							queryParams : {
							//search_EQ_isInstalments : 0
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
										field : 'eId',
										title : '所属企业',
										width : 180,
										align : "center",
										formatter : function(val, row, index) {
											if (row.enterprise) {
												return row.enterprise.name;
											} else {
												return "";
											}
										}
									},
									{
										field : 'id',
										title : '订单ID',
										width : 300,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 1) {
												if (row.fxOrderRecord) {
													return row.fxOrderRecord.downstreamOrderNo;
												}
											} else {
												return row.fxRecharge.id;

											}
										}
									},
									{
										field : 'productName',
										title : '产品名称',
										width : 250,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 1) {
												if (row.fxOrderRecord.fxProduct) {
													return row.fxOrderRecord.fxProduct.name;
												}
											} else {
												return "——";
											}
										}
									},
									{
										field : 'moblie',
										title : '充值账号',
										width : 250,
										align : "center",
										formatter : function(val, row, index) {
											if (row.fxOrderRecord) {
												return row.fxOrderRecord.mobile;
											} else {
												return "——";
											}
										}
									},
									{
										field : 'creatTime',
										title : '创建时间',
										width : 250,
										align : "center"
									},

									{
										field : 'businessType',
										title : '业务类型',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 1) {
												return "订单";
											} else {
												return "加款";
											}
										}
									},
									{
										field : 'in',
										title : '收入',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 2) {
												if (row.amount) {
													return "￥" + row.amount
															/ 1000;
												}
											} else {
												return "——";
											}
										}
									},

									{
										field : 'amount',
										title : '支出',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 1) {
												if (row.amount) {
													return "￥" + row.amount
															/ 1000;
												}
											} else {
												return "——";
											}
										}
									},
									{
										field : 'Money',
										title : '账户余额',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 1) {
												if (row.fxOrderRecord) {
													return "￥"
															+ row.fxOrderRecord.afterBalance
															/ 1000;
												}
											} else {
												return "￥"
														+ row.fxRecharge.afterRechargeBalance
														/ 1000;
											}
										}
									},
									{
										field : 'flowType',
										title : '状态',
										width : 90,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 1) {
												if (row.flowType == 1) {
													return '<span style="color:white;background:green">成功</span>';
												} else {
													return '<span style="color:white;background:red">退款</span>';
												}
											} else {
												if (row.fxRecharge.status == 1) {
													return '<span style="color:white;background:green">成功</span>';
												} else if (row.fxRecharge == 0) {
													return '<span style="color:white;background:orange">未处理</span>';
												} else {
													return '<span style="color:white;background:red">失败</span>';
												}
											}
										}
									},

							] ],
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
		$('#eId').combobox({
			url : "${ctx}/userManage/getEnterpriseList",
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
	//查看详情
	function openZd(id) {
		var zdxqIframe = $('#zdxqIframe');
		var url = '${ctx}/myWorkflow/queryUserBill?id=' + id;
		zdxqIframe.attr('src', url);
		$('#zdxqDlg').dialog('open');
	}
	//导出Excel表格
	function hkExportExcel() {
		$.messager.confirm('确认', '确认把该搜索结果导出Excel表格 ？', function(r) {
			if (r) {
				var startTime = $('#startTime1').datetimebox('getValue');
				var endTime = $('#endTime1').datetimebox('getValue');
				var mphone = $("#mphone").val();
				var billCode = $("#billCode").val();
				var businessType = $("#select_value").val();

				$.messager.progress({
					title : '处理中',
					msg : '请稍后',
				});
				$.messager.progress('close');
				$.ajax({
					url : "${ctx}/export/isOutOfFinancialExportExcel?startTime="
						+ startTime + "&endTime=" + endTime + "&mphone="
						+ mphone + "&orderNo=" + billCode + "&businessType="
						+ businessType,
					onSubmit : function(param) {
					},
					success : function(data) {
						if (data.resultCode == 1) {
							location.href = "${ctx}/export/financialExportExcel?startTime="
								+ startTime + "&endTime=" + endTime + "&mphone="
								+ mphone + "&orderNo=" + billCode + "&businessType="
								+ businessType;						
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
		<div id="hkzdTab" title="资金流水记录" style="padding: 2px">
			<table id="waitorder">
			</table>
			<div id="hkDgTb">
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
											data-options="showSeconds:false" style="width: 150px"></td>
										<td style="border: none;"><div
												style="width: 30px; border-top: 1px solid black; margin-top: 10px;"></div></td>
										<td style="border: none;"><input
											class="easyui-datetimebox" name="endTime" id="endTime1"
											data-options="showSeconds:false" style="width: 150px"></td>

									</tr>
								</table>
							<td style="text-align: right; vertical-align: middle;"><span>关键词:</span></td>
							<td>
								<table>
									<tr>
										<td style="border: none; vertical-align: middle;"><input
											type="text"
											name="search_LIKE_fxOrderRecord.downstreamOrderNo"
											placeholder="请输入订单号" id="billCode"
											style="margin: 0; width: 180px;" /></td>
										<td style="border: none; vertical-align: middle;"><input
											type="text" name="search_LIKE_fxOrderRecord.mobile"
											placeholder="请输入手机号" style="margin: 0; width: 130px;"
											id="mphone" /></td>
										<td><select style="margin: 0; width: 120px;"
											name="search_EQ_businessType" id="select_value">
												<option value="">请选择类型</option>
												<option value="1">订单</option>
												<option value="2">加款</option>
										</select></td>
										<td style="border: none; vertical-align: middle;"><a
											href="javascript:;" class="easyui-linkbutton"
											data-options="iconCls:'icon-search'" onclick="loadHk()">搜索</a>
										</td>
										<td style="border: none; width: 50px"></td>
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