<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<title>权限添加</title>
<jsp:include page="/commons/metanew.jsp"></jsp:include>
<head>
<script type="text/javascript">
	jQuery(function($) {
		$('#role').combobox(
				{
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
					onSelect : function(option) {
						$.post('${ctx}/privilege/getPrivilegePage', {
							roleId : option.id
						}, function(data) {
							if (data.resultCode == 1) {
								$("#tt").tree('loadData', data.content);
							} else if (data.resultCode == -1
									|| data.resultCode == -500) {
								$.messager.alert('消息', data.resultMsg);
							}
						});
					},
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

	function edit() {
		$('#childright').form(
				'submit',
				{
					url : "${ctx}/privilege/allocation",
					onSubmit : function(param) {
						if (($('#role').combobox('getValue') == "")) {
							$.messager.alert("消息", "请选择角色");
							return false;
						}
						var checked = $('#tt').tree('getChecked');
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
							param.value = "clearAll" + "#"
									+ $('#role').combobox('getValue');
						} else {
							param.value = txt1 + "#"
									+ $('#role').combobox('getValue');
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
	}
</script>
</head>
<body>
	<div id="hyxxzl" title="权限列表" style="overflow: auto; padding: 20px;">
		<table>
			<tr>
				<td>选择角色:</td>
				<td><input type="text" id="role" /></td>
			</tr>
		</table> 
	</div>
	<form id="childright">
		<div class="easyui-panel" style="padding: 5px">
			<ul id="tt"></ul>
		</div>
	</form>

	<div style="margin: 20px 0;">
		<a href="#" class="btn btn-primary" onclick="edit()">保存分配</a>
	</div>
</body>
</html>