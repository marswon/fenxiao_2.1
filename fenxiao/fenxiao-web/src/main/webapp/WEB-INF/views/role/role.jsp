<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<title>角色列表</title>
<script type="text/javascript">
	jQuery(function($) {
		$.ajax({
			url : "${ctx}/sys/menu/perms",
			success : function(data) {
				if (data.resultCode == 1) {
					$("#tt").tree('loadData', data.content);
				} else if (data.resultCode == -1 || data.resultCode == -500) {
					$.messager.alert('消息', data.resultMsg);
				}
			}
		});
		$('#tt').tree({
			checkbox : true,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return data;
				}
			}
		});
	});
	function addparent() {
		$('#parentright').form('submit', {
			url : "${ctx}/privilege/savePrivilege",
			onSubmit : function(param) {
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.alert("消息", "保存成功");
					location.reload();
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

	function addchild() {
		$('#childright').form('submit', {
			url : "${ctx}/privilege/savePrivilege",
			onSubmit : function(param) {
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.alert("消息", "保存成功");
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

	function saveRole() {
		$('#role_form')
				.form(
						'submit',
						{
							url : "${ctx}/sys/menu/addRoleMenu",
							onSubmit : function(param) {
								if ((document.getElementById("role").value == "")) {
									$.messager.alert("消息", "请填写角色名称");
									return false;
								}
								//var checked = $('#tt').tree('getChecked');
								//var checked = $('#tt').tree('getChecked','indeterminate');
								var checked = $('#tt').tree('getChecked', ['checked','indeterminate']);
								var count = 0;
								var txt1 = "";
								for (var i = 0; i < checked.length; i++) {
									if (i == checked.length - 1) {
										txt1 = txt1 + checked[i].id;
									} else {
										txt1 = txt1 + checked[i].id + ",";
									}
									count++;
								}
								if (count == 0) {
									param.value = "clearAll"
											+ "#"
											+ document.getElementById("role").value
											+ "#"
											+ document
													.getElementById("description").value;
								} else {
									param.value = txt1
											+ "#"
											+ document.getElementById("role").value
											+ "#"
											+ document
													.getElementById("description").value;
								}

							},
							success : function(data) {
								var d = eval("(" + data + ")");
								if (d.resultCode == 1) {
									$.messager.alert("消息", "保存成功");
								} else {
									if (d.resultMsg) {
										$.messager.alert("消息", d.resultMsg);
									} else {
										$.messager.alert("消息", "未知错误");
									}
								}
							}
						});
	};

	/**修改权限角色**/
	 function updateRole() {
		 $('#role_form1').form('submit',{
					url : "${ctx}/sys/menu/updateRoleMenu",
					onSubmit : function(param) { 
					 	if ((document.getElementById("role1").value == "")) {
							$.messager.alert("消息", "请填写角色名称");
							return false;
						}
						//var checked = $('#tt1').tree('getChecked');
						//var checked = $('#tt1').tree('getChecked','indeterminate');
						var checked = $('#tt1').tree('getChecked', ['checked','indeterminate']);
						var count = 0;
						var txt1 = "";
						for (var i = 0; i < checked.length; i++) {
							if (i == checked.length - 1) {
								txt1 = txt1 + checked[i].id;
							} else {
								txt1 = txt1 + checked[i].id + ",";
							}
							count++;
						}
						if (count == 0) {
							param.value = "clearAll"								
									+ "#"
									+ document.getElementById("role1").value
									+ "#"
									+ document.getElementById("description1").value
									+ "#"
									+ document.getElementById("id1").value;
						} else {
							param.value = txt1
									+ "#"
									+ document.getElementById("role1").value
									+ "#"
									+ document.getElementById("description1").value
									+ "#"+ document.getElementById("id1").value;
						} 

					 },
					 success : function(data) {
						var d = eval("(" + data + ")");
						if (d.resultCode == 1) {
							$.messager.alert("消息", "保存成功");
						} else {
							if (d.resultMsg) {
								$.messager.alert("消息", d.resultMsg);
							} else {
								$.messager.alert("消息", "未知错误");
							}
						}
					} 
					 });
				};
		
	 
	$(function() {
		$("#rolelist")
				.datagrid(
						{
							url : '${ctx}/sys/role/getRole',
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
										title : '角色id',
										width : 7,
										hidden : true
									},
									{
										field : 'name',
										title : '角色名称',
										width : 100,
										align : "center",
									},
									{
										field : 'description',
										title : '备注',
										width : 100,
										align : "center",

									},
									{
										field : 'edit',
										title : '操作',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return '<input  class="btn btn-small btn-info"  type="button" value="编辑" onclick="showRole('
													+ row.id
													+ ')"/>&ensp;<input  class="btn btn-small"  type="button" value="删除" onclick="delRole('
													+ row.id + ')"/>';
										}
									} ] ],
						});
		/* 		$('#role').combobox({
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
		 }); */

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

		//上一级菜单

		$('#parentName3').combobox({
			url : "${ctx}/sys/menu/getMenuList",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 295,
			panelHeight : 500,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return false;
				}
			},
		});

		$('#parentName1').combobox({
			url : "${ctx}/sys/menu/getMenuList",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 295,
			panelHeight : 500,
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
	function showRole(id) {
		//$.getJSON("${ctx}/sys/menu/getPermsPage", {
			$.getJSON("${ctx}/sys/menu/perms", {
			roleId : id
		}, function(data) {			
			if (data.resultCode == 1) {
				$("#tt1").tree('loadData', data.content);					
				$('#webinformation1').dialog('open');
			} else if (data.resultCode == -1 || data.resultCode == -500) {
				$.messager.alert('消息', data.resultMsg);
			}			
		});
		$.getJSON("${ctx}/sys/menu/getPermsInfo", {
			id : id
		}, function(data) {			
			if (data.resultCode == -1) {
				$.messager.alert('消息', data.resultMsg);
			} else {				
				var id = data.content.id;
				$("#id1").val(data.content.id);
				$("#role1").val(data.content.name);
				$("#description1").val(data.content.description);
			} 
		});
				
		$('#tt1').tree({
			checkbox : true,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return data;
				}
			}
		});
	}
	function updateMenu() {
		$('#menuDetail').form('submit', {
			url : "${ctx}/sys/menu/updateMenu",
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
	};

	function delRole(id) { 
 		$.messager.confirm('操作提示', '您确定要删除该角色吗？', function(row) {
			if (row) {
				$.ajax({
					url : '${ctx}/sys/role/delRole?id=' + id,
					success : function() {
						$.messager.alert("消息", "更新成功");
					}				
				});
				location.reload();
			}
		}) 
	};

	function saveMenu() {
		$('#menu_form').form('submit', {
			url : "${ctx}/sys/menu/savaMenu",
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
	};
</script>
</head>
<body>
	<div id="zjjyTabs" class="easyui-tabs" data-options="fit:true">
		<div id="hyTab" title="角色管理" style="padding: 0px">
			<table id="rolelist">
			</table>
			<div id="hyDgTb">
				<form id="queryForm">
					<div id="tb" style="padding: 5px; height: auto"
						class="datagrid-toolbar">
						<b>角色：</b><input type="text" name="search_LIKE_adminInfo.realName">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							id="search_id">查询</a> <span id="js_valdate" style="color: red"></span>&nbsp;<span
							style="color: red" id="js_no"></span>
					</div>
				</form>
			</div>
		</div>
		<div id="jxrzxx" title="添加角色" style="padding: 1px">
			<!-- display:none; -->
			<div id="p" class="easyui-panel" title="新增"
				style="width: widows; height: 1000px; padding: 30px;">
				<form id="role_form">
					<table cellpadding="4">
						<tr>
							<td>角色名称：</td>
							<td><input type="text" id="role" placeholder="角色名称"
								style="width: 280px"></td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td><input type="text" id="description" placeholder="备注"
								style="width: 280px"></td>
						</tr>
						<tr>
							<td align="right">授权：</td>
							<td></td>
						</tr>
						<tr>
							<td align="right"></td>
							<td><ul id="tt"></ul></td>
						</tr>
						<tr>
							<td></td>
							<td><button type="button" class="btn btn-primary"
									onclick="saveRole()">保存</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<div id="webinformation1" style="padding: 20px">
		<form id="role_form1" method="post" style="padding: 20px">
			<table cellpadding="4">
				<tr>
					<td><input type="hidden" id="id1"></td>
				</tr>
				<tr>
					<td>角色名称：</td>
					<td><input type="text" id="role1" placeholder="角色名称"
						style="width: 280px"></td>
				</tr>
				<tr>
					<td align="right">备注：</td>
					<td><input type="text" id="description1" placeholder="备注"
						style="width: 280px"></td>
				</tr>
				<tr>
					<td align="right">授权：</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td><ul id="tt1"></ul></td>
				</tr>
				<tr>
					<td></td>
					<td><button type="button" class="btn btn-primary"
							onclick="updateRole()">保存</button></td>
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
					<td><button type="button" class="btn btn-small btn-info"
							onclick="saveRole()">保存</button></td>
				</tr>
			</table>
	</div>

	<script src="${ctx}/js/sys/role.js"></script>

</body>
</html>