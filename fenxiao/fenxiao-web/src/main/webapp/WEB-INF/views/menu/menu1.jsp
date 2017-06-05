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
<title>菜单列表</title>
<script>
	$(function() {
		$("#menulist")
				.datagrid(
						{
							url : '${ctx}/sys/menu/getMenu',
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
										title : '菜单id',
										width : 7,
										hidden : true
									},
									{
										field : 'name',
										title : '菜单名称',
										width : 100,
										align : "center",
									},
									{
										field : 'parentName',
										title : '上级菜单',
										width : 100,
										align : "center",

									},
									{
										field : 'icon',
										title : '菜单图标',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return val == null ? ''
													: '<span class="'+val+' fa-lg"></span>';

										}
									},
									{
										field : 'url',
										title : '菜单URL',
										width : 100,
										align : "center",
									},
									{
										field : 'perms',
										title : '授权标识',
										width : 100,
										align : "center",
									},
									{
										field : 'type',
										title : '类型',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (val === 0) {
												return '<span class="label label-primary">目录</span>';
											}
											if (val === 1) {
												return '<span class="label label-success">菜单</span>';
											}
											if (val === 2) {
												return '<span class="label label-warning">按钮</span>';
											}
										}
									},
									{
										field : 'orderNum',
										title : '排序号',
										width : 100,
										align : "center",
									},
									{
										field : 'edit',
										title : '操作',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											/* 	return '<a class="btn btn-primary" value="修改" onclick="showAdmin('
														+ row.id + ')"/>'; */
											/* return '<p class="btn btn-info btn-small" onclick="showMenu('
													+ row.id
													+ ')"><i class="fa fa-pencil-square-o"></i>&nbsp;编辑</p>&nbsp;&nbsp;<span class="btn btn-small btn-danger" onclick="delMenu('
													+ row.id
													+ ')"><i class="fa  fa-trash"></i>&nbsp;删除</span>'; */
											return '<input  class="btn btn-small btn-info"  type="button" value="编辑" onclick="showMenu('
											+ row.id + ')"/>&ensp;<input  class="btn btn-small"  type="button" value="删除" onclick="delMenu('
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

	/* 	$('#webinformation1').dialog({
			title : "编辑",
			width : 750,
			height : 550,
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
	function showMenu(id) {
		$.getJSON("${ctx}/sys/menu/getMenuinfo", {
			id : id
		}, function(data) {
			if (data.resultCode == -1) {
				$.messager.alert('消息', data.resultMsg);
			} else {
				var id = data.content.id;
				//	$("#type").combobox('select',type);
				$("#id").val(data.content.id);
				$("#name").val(data.content.name);
				$("#url").val(data.content.url);
				$("#perms").val(data.content.perms);
				$("#orderNum").val(data.content.orderNum);
				$("#icon").val(data.content.icon);
				$("#parentName1").val(data.content.parentName);

				$('#webinformation1').dialog('open');
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

	function delMenu(id) {
	/* 	$.messager.confirm("操作提示", "您确定要删除该菜单吗？", function(data) {
			if (data) {
				$.getJSON("${ctx}/sys/menu/delMenu", {
					id : id
				}, function(data) {
					alert(data);
					var d = eval("(" + data + ")");
					alert(d.resultCode)
					if (d.resultCode == 1) {
						alert("sss");
						$.messager.alert("消息", "更新成功");
					} else {
						if (d.resultMsg) {
							$.messager.alert("消息", d.resultMsg);
						} else {
							$.messager.alert("消息", "未知错误");
						}
					}
				});
			} else {
				//alert("取消");
			}
		}); */
		   $.messager.confirm('确认','确认删除?',function(row){  
               if(row){  
                   $.ajax({  
                       url:'${ctx}/sys/menu/delMenu?id='+id,    
                       success:function(){$.messager.alert("消息", "更新成功");
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
		<div id="hyTab" title="菜单管理" style="padding: 0px">
			<table id="menulist">
			</table>
			<!-- 		<div id="hyDgTb">
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
			</div> -->
		</div>
		<div id="jxrzxx" title="添加菜单" style="padding: 1px">
			<!-- display:none; -->
			<div id="p" class="easyui-panel" title="新增"
				style="width: 700px; height: 500px; padding: 30px;">
				<form id="menu_form">
					<table cellpadding="4">
						<tr>
							<td align="right">类型：</td>
							<td><input type="hidden" name="id" value=""> <input
								type="radio" name="type" value="0" checked>&nbsp;&nbsp;目录
								&nbsp;&nbsp; <input type="radio" name="type" value="1">&nbsp;&nbsp;菜单
								&nbsp;&nbsp; <input type="radio" name="type" value="2">&nbsp;&nbsp;按钮</td>
						</tr>
						<tr>
							<td>菜单名称：</td>
							<td><input type="text" name="name" placeholder="菜单名称或按钮名称"
								style="width: 280px"></td>
						</tr>
						<tr>
							<td>上级菜单：</td>
						<!-- 	<td><input type="text" name="parentName" id="parentName3"
								class="form-control" placeholder="一级菜单" style="height: 30px"></td> -->
								<td><input type="text" style="width: 280px"  class="form-control" style="cursor:pointer;" v-model="menu.parentName" @click="menuTree" readonly="readonly" placeholder="一级菜单"/>
								</td>
						</tr>
						<tr>
							<td align="right">菜单URL：</td>
							<td><input type="text" name="url" required="required"
								placeholder="菜单URL" style="width: 280px"></td>
						</tr>
						<tr>
							<td align="right">授权标识：</td>
							<td><input type="text" " name="perms"
								placeholder="多个用逗号分隔，如：user:list,user:create"
								style="width: 280px"></td>
						</tr>
						<tr>
							<td align="right">排序号：</td>
							<td>
								<!-- <input type="text" id="orderNum" required="required"
								size="14" maxlength="14"> --> <input type="number"
								class="form-control" name="orderNum" style="width: 280px"
								required="required" placeholder="排序号" />

							</td>
						</tr>
						<tr>
							<td align="right">图标：</td>
							<td><input type="text" name="icon" placeholder="菜单图标"
								style="width: 280px"></td>
						</tr>
						<tr>
							<td></td>
							<td><button type="button" class="btn btn-primary"
									onclick="saveMenu()">保存</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<div id="webinformation1" style="padding: 30px">
		<form id="menuDetail" method="post" style="padding: 30px">
			<table cellpadding="4">
				<tr>
					<td></td>
					<td><input type="hidden" id="id" name="id">
				</tr>
				<tr>
					<td align="right">类型：</td>
					<td><input type="radio" id="type" name="type" value="0"
						checked>&nbsp;&nbsp;目录 &nbsp;&nbsp; <input type="radio"
						id="type" name="type" value="1">&nbsp;&nbsp;菜单
						&nbsp;&nbsp; <input type="radio" id="type" name="type" value="2">&nbsp;&nbsp;按钮</td>
				</tr>
				<tr>
					<td>菜单名称：</td>
					<td><input type="text" id="name" name="name"
						style="width: 280px"></td>
				</tr>
				<tr>
					<td>上级菜单：</td>
					<td><!-- <input type="text" id="parentName1" name="parentName"
						style="height: 30px"> --></td>
				</tr>
				<tr>
					<td>菜单URL：</td>
					<td><input type="text" id="url" name="url"
						style="width: 280px"></td>
				</tr>
				<tr>
					<td align="right">授权标识：</td>
					<td><input type="text" id="perms" name="perms"
						style="width: 280px"></td>
				</tr>
				<tr>
					<td align="right">排序号：</td>
					<td>
						<!-- <input type="text" id="orderNum" required="required"
								size="14" maxlength="14"> --> <input type="number"
						class="form-control" id="orderNum" name="orderNum"
						style="width: 280px" />

					</td>
				</tr>
				<tr>
					<td align="right">图标：</td>
					<td><input type="text" id="icon" name="icon"
						style="width: 280px"></td>
				</tr>
				<tr>
					<td></td>
					<td><button type="button" class="btn btn-primary"
							onclick="updateMenu()">保存</button></td>
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
		</form>
	</div>
	<!-- <div id="zdxqDlg">
		<iframe id="zdxqIframe" frameborder="0" marginheight="0"
			marginwidth="0" style="width: 100%; height: 99%;" scrolling="yes"></iframe>
	</div> -->

	<!-- 选择菜单 -->
	<div id="menuLayer" style="display: none; padding: 10px;">
		<ul id="menuTree" class="ztree"></ul>
	</div>

</body>
</html>