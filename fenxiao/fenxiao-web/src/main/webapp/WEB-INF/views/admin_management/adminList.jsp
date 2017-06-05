<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户资金交易记录</title>
<script>
	$(function() {
		$("#adminlist")
				.datagrid(
						{
							url : '${ctx}/adminManage/getAdminRoleList',
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
							toolbar : "#hyDgTb",//工具栏
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
										field : 'id',
										title : 'id',
										width : 7,
										hidden : true
									},
									{
										field : 'realName',
										title : '姓名',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.realName;
											} else {
												return "";
											}
										}
									},
									{
										field : 'role',
										title : '分组',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.role) {
												return row.role.name;
											} else {
												return "";
											}
										}
									},
									{
										field : 'mphone',
										title : '手机号',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.mphone;
											} else {
												return "";
											}
										}
									},
									{
										field : 'loginName',
										title : '账号',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.loginName;
											} else {
												return "";
											}
										}
									},
									{
										field : 'edit',
										title : '操作',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return '<input type="button" value="修改" onclick="showAdmin('
													+ row.id + ')"/>';
										}
									} ] ],
						});
		$('#role').combobox({
			url : "${ctx}/adminManage/getRoleList",
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

		$('#roleId1').combobox({
			url : "${ctx}/adminManage/getRoleList",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 220,
			panelHeight : 200,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return false;
				}
			},
		});

		$('#roleId').combobox({
			url : "${ctx}/adminManage/getRoleList",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 220,
			panelHeight : 200,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return false;
				}
			},
		});

		$("#search_id").click(function() {
			var params = $('#adminlist').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#adminlist').datagrid('load');
		});

	/* 	$('#webinformation1').dialog({
			title : "编辑",
			width : 550,
			height : 500,
			closed : true,
			cache : false,
			modal : true,
			center : true,
		});
 */
 
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
		$('#zdxqDlg').dialog({
			title : '会员订单交易明细',
			fit : true,
			draggable : false,
			closed : true,
			cache : false,
			modal : true
		});
	});

	function openZd(id, type) {
		var zdxqIframe = $('#zdxqIframe');
		var url = '${ctx}/financeManage/queryFundsTradingOne?id=' + id
				+ '&type=' + type;
		zdxqIframe.attr('src', url);
		$('#zdxqDlg').dialog('open');
	}
	function closeDetail() {
		$('#zdxqDlg').dialog('close');
	}
	function showAdmin(id) {
		$.getJSON("${ctx}/adminManage/getAdmin", {
			id : id
		}, function(data) {
			if (data.resultCode == -1) {
				$.messager.alert('消息', data.resultMsg);
			} else {
				var id = data.content.role.id;
				$("#roleId").combobox('select', id);
				$("#mphone").val(data.content.adminInfo.mphone);
				$("#realname").val(data.content.adminInfo.realName);
				$("#adminRoleId").val(data.content.id);
				$("#adminInfoId").val(data.content.adminInfo.id);
				$("#RoleId").val(data.content.role.id);
				$('#webinformation1').dialog('open');
			}
		});
	}
	function updateAdmin() {
		$('#adminDetail').form('submit', {
			url : "${ctx}/adminManage/updateAdmin",
			onSubmit : function(param) {
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.alert("消息", "更新成功");
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
	<div id="zjjyTabs"  data-options="fit:true">
		<div id="hyTab" title="用户管理" style="padding: 1px">
			<table id="adminlist">
			</table>
			<div id="hyDgTb">
				<form id="queryForm">
					<div id="tb" style="padding: 5px; height: auto"
						class="datagrid-toolbar">
						<b>姓名：</b><input type="text" name="search_LIKE_adminInfo.realName">
						<b>手机号：</b><input type="text" name="search_LIKE_adminInfo.mphone">
						<b>分组：</b><input type="text" id="role" name="search_EQ_role.id">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							id="search_id">查询</a> <span id="js_valdate" style="color: red"></span>&nbsp;<span
							style="color: red" id="js_no"></span>
					</div>
				</form>
			</div>
		</div>
		<div id="jxrzxx" title="添加用户" style="padding: 1px">
			<!-- display:none; -->
			<div style="padding: 10px">
				<form id="admininfo_form">
					<table cellpadding="4">
						<tr>
							<td>账号：</td>
							<td><input type="text" required="required" name="loginName"></td>
						</tr>
						<tr>
							<td>姓名：</td>
							<td><input type="text" name="realName"></td>
						</tr>
						<tr>
							<td>登陆密码：</td>
							<td><input type="password" id="pwd1" name="pwd"
								required="required" size="14" maxlength="14"></td>
						</tr>
						<tr>
							<td>确认登陆密码：</td>
							<td><input type="password" id="pwd2" required="required"
								size="14" maxlength="14"></td>
						</tr>
						<tr>
							<td>分组：</td>
							<td><input id="roleId1" name="roleId"></td>
						</tr>
						<tr>
							<td>手机号：</td>
							<td><input name="mphone" type="text" size="14"
								maxlength="14"></td>
						</tr>
						<tr>
							<td><button type="button" class="btn btn-primary" onclick="saveAdInfo()">保存</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div id="webinformation1">
		<form id="adminDetail" method="post" style="padding: 30px">
			<table cellpadding="4">
				<tr>
					<td>姓名：</td>
					<td><input type="hidden" id="adminRoleId" name="adminRoleId">
						<input type="hidden" id="adminInfoId" name="adminInfoId">
						<input type="text" id="realname" name="realname"></td>
				</tr>
				<tr>
					<td>分组：</td>
					<td><input type="text" id="roleId" name="roleId"></td>
				</tr>
				<tr>
					<td>手机号：</td>
					<td><input type="text" id="mphone" name="mphone"></td>
				</tr>
				<tr>
					<td><button type="button" class="btn btn-primary" onclick="updateAdmin()">保存</button></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="webinformation2">
		<form id="role_form">
			<table cellpadding="4">
				<tr>
					<td>分组名称：</td>
					<td><input type="text" id="name" name="name"></td>
				</tr>
				<tr>
					<td><button type="button" class="btn btn-primary" onclick="saveRole()">保存</button></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- <div id="zdxqDlg">
		<iframe id="zdxqIframe" frameborder="0" marginheight="0"
			marginwidth="0" style="width: 100%; height: 99%;" scrolling="yes"></iframe>
	</div> -->
</body>
</html>