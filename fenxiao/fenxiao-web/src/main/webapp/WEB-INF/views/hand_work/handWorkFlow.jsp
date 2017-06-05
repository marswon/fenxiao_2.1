<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>手工充值</title>
<script>
	$(function() {
		<%--$("#camilist")--%>
				<%--.datagrid(--%>
						<%--{--%>
							<%--url : '${ctx}/handwork/findFXHandWorkFlow',--%>
							<%--nowrap : false,--%>
							<%--striped : true,--%>
							<%--remoteSort : false,--%>
							<%--pageSize : 20,--%>
							<%--fit : true,--%>
							<%--queryParams : {},--%>
							<%--singleSelect : true,//是否单选--%>
							<%--pagination : true,//分页控件--%>
							<%--rownumbers : true,//行号--%>
							<%--fitColumns : true,//列宽自动填充满表格--%>
							<%--checkOnSelect : false,--%>
							<%--selectOnCheck : false,--%>
							<%--toolbar : "#hyDgTb",//工具栏--%>
							<%--loadFilter : function(data) {--%>
								<%--if (data.content) {--%>
									<%--return {--%>
										<%--total : data.totalElements,--%>
										<%--rows : data.content--%>
									<%--};--%>
								<%--} else {--%>
									<%--if (typeof data.length == "number"--%>
											<%--&& typeof data.splice == "function") {--%>
										<%--return {--%>
											<%--total : data.length,--%>
											<%--rows : data--%>
										<%--};--%>
									<%--} else {--%>
										<%--return data;--%>
									<%--}--%>
								<%--}--%>
							<%--},--%>
							<%--columns : [ [--%>
									<%--{--%>
										<%--field : 'id',--%>
										<%--title : 'id',--%>
										<%--width : 7,--%>
										<%--hidden : true--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'phone',--%>
										<%--title : '手机号',--%>
										<%--width : 100,--%>
										<%--align : "center"--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'camiStr',--%>
										<%--title : '卡密',--%>
										<%--width : 100,--%>
										<%--align : "center"--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'flowType',--%>
										<%--title : '流量类型',--%>
										<%--width : 100,--%>
										<%--align : "center",--%>
										<%--formatter : function(val, row, index) {--%>
											<%--if (row.flowType == 0) {--%>
												<%--return "漫游";--%>
											<%--} else {--%>
												<%--return "本地";--%>
											<%--}--%>
										<%--}--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'size',--%>
										<%--title : '大小(M)',--%>
										<%--width : 100,--%>
										<%--align : "center"--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'createTime',--%>
										<%--title : '创建时间',--%>
										<%--width : 100,--%>
										<%--align : "center"--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'updateTime',--%>
										<%--title : '更新时间',--%>
										<%--width : 100,--%>
										<%--align : "center"--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'status',--%>
										<%--title : '状态',--%>
										<%--width : 100,--%>
										<%--align : "center",--%>
										<%--formatter : function(val, row, index) {--%>
											<%--if (row.status == 1) {--%>
												<%--return '<span id="ysj'--%>
                                                        <%--+ row.id--%>
                                                        <%--+ '" style="color:#fff;background:green">成功</span>';--%>
											<%--} else if (row.status == 2) {--%>
												<%--return '<span id="yxj'--%>
                                                        <%--+ row.id--%>
                                                        <%--+ '" style="color:#fff;background:red">失败</span>';--%>
											<%--}--%>
										<%--}--%>
									<%--},--%>
									<%--{--%>
										<%--field : 'errorMsg',--%>
										<%--title : '描述',--%>
										<%--width : 100,--%>
										<%--align : "center"--%>
									<%--}] ],--%>
						<%--});--%>
		$("#search_id").click(function() {
			var params = $('#camilist').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#camilist').datagrid('load');
		});

	/* 	$('#webinformation1').dialog({
			title : "编辑",
			width : 550,
			height : 500,
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

		$('#webinformation2').dialog({
			width : 320,
			height : 200,
			closed : true,
			cache : false,
			modal : true,
		});
		$(window).resize(function() {
			$('#zdxqDlg').dialog('resize', {
				width : $(document.body).width(),
				height : $(document.body).height()
			});
		});
	});


	/**
	 * 手工充值
	 */
	
	function save() {
		$.messager.confirm('确认', '是否充值该流量包？', function(r) {
			if (r) {
				$.messager.progress({
					title : '充值中',
					msg : '请稍后',
				});
				$('#save_handWorkFlow').form('submit', {
					url : "${ctx}/handwork/save_handWorkFlow",
					onSubmit : function(param) {
					},
					success : function(data) {
						$.messager.progress('close');
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
		});
	}
	
	/*手工流量充值
	*/
	function flowSave() {
		$.messager.confirm('确认', '是否充值该流量包？', function(r) {
			if (r) {
				$.messager.progress({
					title : '充值中',
					msg : '请稍后',
				});
				$('#saveFlowHander').form('submit', {
					url : "${ctx}/handwork/handWorkFlow",
					onSubmit : function(param) {
					},
					success : function(data) {
						$.messager.progress('close');
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
		});
	}
	
	function billSave(){
		$.messager.confirm('确认', '是否充值？', function(r) {
			if (r) {
				$.messager.progress({
					title : '充值中',
					msg : '请稍后',
				});
				$('#saveBillHander').form('submit', {
					url : "${ctx}/handwork/handWorkBill",
					onSubmit : function(param) {
					},
					success : function(data) {
						$.messager.progress('close');
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
		});
	}

	/**
	 * 加油卡手工充值
	 */

	function gasCardSave() {
		$.messager.confirm('确认', '是否充值该加油包？', function(r) {
			if (r) {
				$.messager.progress({
					title : '充值中',
					msg : '请稍后',
				});
				$('#save_gasCard_handWorkFlow').form('submit', {
					url : "${ctx}/handwork/save_gasCard_handWorkFlow",
					onSubmit : function(param) {
					},
					success : function(data) {
						$.messager.progress('close');
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
		});
	}
</script>
</head>
<body>
	<div id="zjjyTabs" class="easyui-tabs" data-options="fit:true">
		<%--<div id="jxrzxx" title="卡密手工充值" style="padding: 1px">--%>
			<%--<!-- display:none; -->--%>
			<%--<div style="padding: 10px">--%>
				<%--<form id="save_handWorkFlow">--%>
					<%--<table cellpadding="4">--%>
						<%--<tr>--%>
							<%--<td>流量类型：</td>--%>
							<%--<td><input type="radio" name="flowType" value="0"--%>
								<%--checked="checked">&nbsp;漫游&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input--%>
								<%--type="radio" name="flowType" value="1">&nbsp;省内</td>--%>
						<%--</tr>--%>
						<%--<tr>--%>
							<%--<td>流量大小：</td>--%>
							<%--<td><select class="sel" name="size" id="size"--%>
								<%--style="width: 100px; padding-left: 0px;">--%>
									<%--<option value="">-请选择-</option>--%>
									<%--<option value="5">5M</option>--%>
									<%--<option value="10">10M</option>--%>
									<%--<option value="20">20M</option>--%>
									<%--<option value="30">30M</option>--%>
									<%--<option value="50">50M</option>--%>
									<%--<option value="70">70M</option>--%>
									<%--<option value="100">100M</option>--%>
									<%--<option value="200">200M</option>--%>
									<%--<option value="300">300M</option>--%>
									<%--<option value="500">500M</option>--%>
									<%--<option value="1024">1G</option>--%>
									<%--<option value="2048">2G</option>--%>
									<%--<option value="3072">3G</option>--%>
									<%--<option value="5120">5G</option>--%>
							<%--</select></td>--%>
						<%--</tr>--%>
						<%--<tr>--%>
							<%--<td>手机号码</td>--%>
							<%--<td><textarea type="text" id="phone" name="phone"--%>
									<%--style="height: 50px; width: 200px" size="14"></textarea>多个手机号用英文逗号分割(,)--%>
							<%--</td>--%>
						<%--</tr>--%>
						<%--<tr>--%>
							<%--<td>--%>
								<%--<button type="button" onclick="save()">保存</button>--%>
							<%--</td>--%>
						<%--</tr>--%>
					<%--</table>--%>
				<%--</form>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div id="flowHander" title="流量手工充值" style="padding: 1px">
			<!-- display:none; -->
			<div style="padding: 10px">
				<form id=saveFlowHander>
					<table cellpadding="4">
					<tr>
							<td>流量类型：</td>
							<td><input type="radio" name="flowType" value="0"
								checked="checked">&nbsp;漫游&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
								type="radio" name="flowType" value="1">&nbsp;省内</td>
						</tr>
						<tr>
							<td>流量大小：</td>
							<td><select class="sel" name="flowSize" id="flowSize"
								style="width: 100px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="5">5M</option>
									<option value="10">10M</option>
									<option value="20">20M</option>
									<option value="30">30M</option>
									<option value="50">50M</option>
									<option value="70">70M</option>
									<option value="100">100M</option>
									<option value="200">200M</option>
									<option value="300">300M</option>
									<option value="500">500M</option>
									<option value="1024">1G</option>
									<option value="2048">2G</option>
									<option value="3072">3G</option>
									<option value="5120">5G</option>
							</select></td>
						</tr>
						<tr>
							<td>手机号码：</td>
							<td><textarea type="text" id="flowAcount" name="flowAcount"
										  style="height: 50px; width: 200px" size="14"></textarea>多个卡号用英文逗号分割(,)
							</td>
						</tr>
						<tr>
							<td>备注：</td>
							<td><textarea type="text" name="description"
										  style="height: 50px; width: 200px" size="14"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<button type="button" class="btn btn-primary" onclick="flowSave()">保存</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<div id="billHander" title="话费手工充值" style="padding: 1px">
			<!-- display:none; -->
			<div style="padding: 10px">
				<form id=saveBillHander>
					<table cellpadding="4">
						<tr>
							<td><input type="hidden" name="flowType" value="0"></td>
						</tr>
						<tr>
							<td>充值金额：</td>
							<td><select class="sel" name="billSize" id="billSize"
								style="width: 100px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="5">5元</option>
									<option value="10">10元</option>
									<option value="20">20元</option>
									<option value="30">30元</option>
									<option value="50">50元	</option>
									<option value="100">100元</option>
									<option value="200">200元</option>
									<option value="300">300元</option>
									<option value="500">500元</option>
							</select></td>
						</tr>
						<tr>
							<td>手机号码：</td>
							<td><textarea type="text" id="billAcount" name="billAcount"
										  style="height: 50px; width: 200px" size="14"></textarea>多个卡号用英文逗号分割(,)
							</td>
						</tr>
						<tr>
							<td>备注：</td>
							<td><textarea type="text" name="description"
										  style="height: 50px; width: 200px" size="14"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<button type="button" class="btn btn-primary" onclick="billSave()">保存</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="gasCardHander" title="加油卡手工充值" style="padding: 1px">
			<!-- display:none; -->
			<div style="padding: 10px">
				<form id="save_gasCard_handWorkFlow">
					<table cellpadding="4">
						<tr>
							<td>充值面值：</td>
							<td><select class="sel" name="amount" id="amount"
										style="width: 100px; padding-left: 0px;">
								<option value="">-请选择-</option>
								<option value="50">50元</option>
								<option value="100">100元</option>
								<option value="200">200元</option>
								<option value="500">500元</option>
								<option value="800">800元</option>
								<option value="1000">1000元</option>
								<option value="2000">2000元</option>
							</select></td>
						</tr>
						
						<tr>
							<td>充值数量：</td>
							<td><select class="sel" name="payNum" id="payNum"
										style="width: 100px; padding-left: 0px;">
								<option value="">-请选择充值数量-</option>
								<option value="1">1个</option>
								<option value="2">2个</option>
								<option value="3">3个</option>
								<option value="4">4个</option>
								<option value="5">5个</option>

							</select></td>
						</tr>
						<tr>
							<td>充值卡号：</td>
							<td><textarea type="text" id="gasCardAcount" name="gasCardAcount"
										  style="height: 50px; width: 200px" size="14"></textarea>多个卡号用英文逗号分割(,)
							</td>
						</tr>
                        <tr>
                            <td>备注：</td>
                            <td><textarea type="text" name="description"
                                          style="height: 50px; width: 200px" size="14"></textarea>
                            </td>
                        </tr>
						<tr>
							<td>
								<button type="button" class="btn btn-primary" onclick="gasCardSave()">保存</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<%--<div id="hyTab" title="充值记录" style="padding: 1px">--%>
			<%--<table id="camilist">--%>
			<%--</table>--%>
			<%--<div id="hyDgTb">--%>
				<%--<form id="queryForm">--%>
					<%--<div id="tb" style="padding: 5px; height: auto"--%>
						<%--class="datagrid-toolbar">--%>
						<%--<b>手机号：</b> <b><input type="text" style="width: 100px;"--%>
							<%--name="search_EQ_phone" /> </b> <b>流量类型：</b> <select class="sel"--%>
							<%--name="search_EQ_flowType" id="flowType"--%>
							<%--style="width: 100px; padding-left: 0px;">--%>
							<%--<option value="">-请选择-</option>--%>
							<%--<option value="0">漫游</option>--%>
							<%--<option value="1">本地</option>--%>
						<%--</select> <b>流量大小：</b> <select class="sel" name="search_EQ_size"--%>
							<%--style="width: 100px; padding-left: 0px;">--%>
							<%--<option value="">-请选择-</option>--%>
							<%--<option value="5">5M</option>--%>
							<%--<option value="10">10M</option>--%>
							<%--<option value="20">20M</option>--%>
							<%--<option value="30">30M</option>--%>
							<%--<option value="50">50M</option>--%>
							<%--<option value="70">70M</option>--%>
							<%--<option value="100">100M</option>--%>
							<%--<option value="200">200M</option>--%>
							<%--<option value="300">300M</option>--%>
							<%--<option value="500">500M</option>--%>
							<%--<option value="1024">1G</option>--%>
							<%--<option value="2048">2G</option>--%>
							<%--<option value="3072">3G</option>--%>
							<%--<option value="5120">5G</option>--%>
						<%--</select> <b>状态：</b> <select class="sel" name="search_EQ_status"--%>
							<%--id="status" style="width: 100px; padding-left: 0px;">--%>
							<%--<option value="">-请选择-</option>--%>
							<%--<option value="1">成功</option>--%>
							<%--<option value="2">失败</option>--%>
						<%--</select> <a href="#" class="easyui-linkbutton" iconCls="icon-search"--%>
							<%--id="search_id">查询</a> <span id="js_valdate" style="color: red"></span>&nbsp;<span--%>
							<%--style="color: red" id="js_no"></span>--%>
					<%--</div>--%>
				<%--</form>--%>
			<%--</div>--%>
		<%--</div>--%>

	</div>
</body>
</html>