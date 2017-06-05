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
$(document).ready(
		function() {
			$.each(areaCodeMap,function(key,value){
				$("#areaCode").append("<option value='"+key+"'>"+value+"</option>");
			});
			chosen("#areaCode");

			$.post("${ctx}/userManage/getEnterpriseList",
					null, function(data) {
						if (data) {
							$.each(data.content, function(index, obj) {
								$("#eId").append(
										"<option value='"+obj.id+"'>"
												+ obj.name + "</option>");
							});
							chosen("#eId");
							chosen("#search_EQ_businessType");
						}
					});
		});
	$(function() {
		//还款账单
		$("#waitorder")
				.datagrid(
						{
							url : "${ctx}/waitOrder/findWaitOrder",
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
										field : 'clientSubmitTime',
										title : '客户下单时间',
										width : 150,
										align : "center"
									},
									{
										field : 'mobile',
										title : '手机号',
										width : 150,
										align : "center"
									},
									{
										field : 'yysTypeId',
										title : '运营商',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											var result = showProvince(row.provinceId);
											var yys = row.yysTypeId;
											if (yys == "1") {
												result += "电信";
											} else if (yys == "2") {
												result += "移动";
											} else {
												result += "联通";
											}
											if(row.areaCode){
												result +=  "<br/>" + showAreaName(row.areaCode) ;
											}
											return '<span style="color:white;background:green">' + result + '</span>';
										}
									},
// 									{
// 										field : 'provinceId',
// 										title : '省份',
// 										width : 80,
// 										align : "center",
// 										formatter : function(val, row, index) {
// 											return showProvince(row.provinceId);
// 										}
// 									},
									{
										field : 'downstreamOrderNo',
										title : '下游订单号',
										width : 250,
										align : "center"
									},
									{
										field : 'businessType',
										title : '业务',
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
										field : 'size',
										title : '大小',
										width : 70,
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
										field : 'discount',
										title : '折扣',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return row.discount / 100;
										}

									},
									{
										field : 'costMoney',
										title : '实际消费金额',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											var value = val * 0.001;
											return "¥" + value.toFixed(2);
										}
									},
									{
										field : 'beforeBalance',
										title : '扣费前余额',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											var value = val * 0.001;
											return "¥" + value.toFixed(2);
										}
									},
									{
										field : 'afterBalance',
										title : '扣费后余额',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											var value = val * 0.001;
											return "¥" + value.toFixed(2);
										}
									},
									{
										field : 'orderRecordkey',
										title : '订单key',
										width : 100,
										align : "center",
										styler : function(
												value, row,
												index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'isCorrectCostMoney',
										title : '金额状态',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return '<span style="color:white;background:red">异常</span>';
											} else if (val == 1) {
												return '<span style="color:white;background:green">正常</span>';
											} else if (val == -1) {
												return '<span style="color:white;background:orange">新创建</span>';
											}
										}
									},
									{
										field : 'process',
										title : '详情',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return '<a href="javascript:;" class="btn btn-default" onclick="openZd('
													+ row.id + ')">处理</a>';
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
				var username = $("#username").val();
				var mphone = $("#mphone").val();
				var billCode = $("#billCode").val();
				var orderStatus = $("#select_value").val();
				var completeStartTime1 = $('#completeStartTime1').datetimebox(
						'getValue');
				var completeEndTime1 = $('#completeEndTime1').datetimebox(
						'getValue');
				$.messager.progress({
					title : '处理中',
					msg : '请稍后',
				});
				$.messager.progress('close');
				location.href = "${ctx}/tradingManage/hkExportExcel?startTime="
						+ startTime + "&endTime=" + endTime + "&username="
						+ username + "&mphone=" + mphone + "&billCode="
						+ billCode + "&orderStatus=" + orderStatus
						+ "&completeStartTime1=" + completeStartTime1
						+ "&completeEndTime1=" + completeEndTime1;
			}
		});
	}
	//根据IDcode获取省份
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
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="待处理订单" style="padding: 2px">
			<table id="waitorder">
			</table>
			<div id="hkDgTb">
				<form id="queryHkForm">
					<table class=" datagrid-cell-c1-status"
						style="border-collapse: separate; border-spacing: 0px; height: 15px;">
						<tr>
							<td style="text-align: right; vertical-align: middle;"><span>下单时间:</span></td>
							<td>
								<table class="datagrid-cell datagrid-cell-c1-status"
									style="border-collapse: separate; border-spacing: 0px; height: 15px;">
									<tr>
										<td style="border: none;"><input
											class="easyui-datetimebox" name="startTime" id="startTime1"
											data-options="showSeconds:false" style="width: 125px"></td>
										<td style="border: none;"><input
											class="easyui-datetimebox" name="endTime" id="endTime1"
											data-options="showSeconds:false" style="width: 125px"></td>
										<!--<td style="border: none; width: 350px"></td>
										 <td style="border: none;"><input type="button"
											onclick="hkExportExcel();" value="导出excel"
											style="display: block; size: 20px; background-color: #95B8E7"></td> -->
									</tr>
								</table> <!-- 	<td style="text-align: right; vertical-align: middle;"><span>查询条件:</span></td>
							<td><select style="margin: 0; width: 80px;"
								name="search_EQ_status" id="select_value">
									<option value="">请选择</option>
									<option value="0">未还款</option>
									<option value="1">已还款</option>
									<option value="2">逾期</option>
							</select></td> -->
							<td>所属企业</td>
							<td><select id="eId" name="search_EQ_fxEnterprise.id" style="width: 180px;"><option value="">--请选择--</option></select></td>
							<td style="text-align: right; vertical-align: middle;x"><span></span></td>
							<td>
								<table>
									<tr>
										<td style="border: none; vertical-align: middle;"><input
											type="text" name="search_LIKE_downstreamOrderNo"
											placeholder="请输入订单号" id="billCode"
											style="margin: 0; width: 150px;" /></td>
										<td style="border: none; vertical-align: middle;"><input
											type="text" name="search_LIKE_mobile" placeholder="请输入手机号"
											style="margin: 0; width: 110px;" id="mphone" /></td>
										<td><select class="sel" name="search_EQ_businessType"
											id="search_EQ_businessType"
											style="width: 100px; padding-left: 0px;">
												<option value="">-业务类型-</option>
												<option value="0">流量</option>
												<option value="1">话费</option>
												<option value="2">物业缴费</option>
												<option value="3">加油卡</option>
										</select></td>
										<td>地市
										<select style="margin: 0; width: 75px;"
											name="search_EQ_areaCode" id="areaCode">
												<option value="">请选择</option>
										</select>
										</td>
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
	</div>
	<div id="zdxqDlg">
		<iframe id="zdxqIframe" frameborder="0" marginheight="0"
			marginwidth="0" style="width: 100%; height: 99%;" scrolling="yes"></iframe>
	</div>
</body>
</html>