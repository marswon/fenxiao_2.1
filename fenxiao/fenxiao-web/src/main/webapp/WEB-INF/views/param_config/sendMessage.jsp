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
</style>
<script type="text/javascript">
	var flag = false;
	jQuery(function($) {
		$("#_productList")
				.datagrid(
						{
							url : '${ctx}/paramConfig/getSendMessage',
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
									/* {field:'id',title:'id',width:7,hidden:true}, */
									{
										field : 'createTime',
										title : '创建时间',
										width : 100,
										align : "center"
									},
									{
										field : 'updateTime',
										title : '修改时间',
										width : 100,
										align : "center"
									},
									{
										field : 'content',
										title : '消息内容',
										width : 400,
										align : "center"
									},
									
									{
										field : 'userAccountstatus',
										title : '操作',
										width : 120,
										align : "center",
										formatter : function(val, row, index) {//

											return '<input type="button" class="btn btn-info btn-small" value="编辑" onclick="showEdit(\''
													+ row.id + '\')"/>';

										}
									}, {
										field : 'id',
										title : 'Id',
										width : 150,
										align : "center",
										hidden : true,
										
									} ] ],
						});
		$('#dfxqDlg').dialog({
			title : '会员资料',
			fit : true,
			draggable : false,
			closed : true,
			cache : false,
			modal : true
		});
	

		$('#flowSize').combobox({
			editable : false,
			width : 150,
			panelHeight : 200,
		});
		$('#FlowTypeByPorince').combobox({
			editable : false,
			width : 150,
			panelHeight : 200,
		});
		$('#yysbox').combobox({
			editable : false,
			width : 150,
			panelHeight : 200,
		});
		$('#provicebox').combobox({
			editable : false,
			width : 150,
			panelHeight : 200,
		});

	/* 	$('#webinformation1').dialog({
			title : "推送消息编辑",
			width : 550,
			height : 300,
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

	function blocked(id) {
		var s = confirm("是否确定冻结该账户");
		if (s) {
			$.getJSON("${ctx}/usermanagement/blockedUser", {
				userId : id
			}, function(data) {
				if (data.resultCode == -1) {
					$.messager.alert('消息', data.resultMsg);
				} else {
					$.messager.alert('消息', data.resultMsg);
					$("#_productList").datagrid("reload");
				}
			});
		}
	}

	function unblocked(id) {
		var s = confirm("是否确定解除冻结该账户");
		if (s) {
			$.getJSON("${ctx}/usermanagement/unBlockedUser", {
				userId : id
			}, function(data) {
				if (data.resultCode == -1) {
					$.messager.alert('消息', data.resultMsg);
				} else {
					$.messager.alert('消息', data.resultMsg);
					$("#_productList").datagrid("reload");
				}
			});
		}
	}

	function updateUser(id) {
		var dfxqIframe = $('#dfxqIframe');
		var url = "${ctx}/usermanagement/userupdate/userUpdate?id=" + id;
		//	var url="${ctx}/usermanagement/userupdate/uupdate?id="+id;
		dfxqIframe.attr('src', url);
		$('#dfxqDlg').dialog('open');
	}
	function closeDf() {
		$('#dfxqDlg').dialog('close');
	}

	function showEdit(id) {
		//清除内容
		$(':input', '#operatorProductDetail').not(
				':button, :submit, :reset, :hidden').val('').removeAttr(
				'checked').removeAttr('selected');
		if (id == 0) {
			$("#ta1").val("");
			$('#webinformation1').dialog('open');
		} else {
			
			//清除缓存
			$.getJSON("${ctx}/paramConfig/findMessageById", {
				id : id
			}, function(data) {
				if (data.resultCode == -1) {
					$.messager.alert('消息', data.resultMsg);
				} else {
					console.log(data);
					$("#ta1").val(data.content.content);
					$("#id").val(data.content.id);
					$('#webinformation1').dialog('open');

				}
			});
		}
	}
	function edit() {

		$.messager.confirm("操作提示", "您确定要执行操作吗？", function(data) {
			if (data) {
				$('#operatorProductDetail').form('submit', {
					url : "${ctx}/paramConfig/saveMessage",
					onSubmit : function(param) {
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
							if (!flag) {
								param.value = "no";
							} else {
								param.value = "clearAll";
							}
						} else {
							param.value = txt1;
						}

					},
					success : function(data) {
						var d = eval("(" + data + ")");
						if (d.resultCode == 1) {
							/* 	$.messager.alert(
										"消息",
										"保存成功"); */

							$.messager.confirm("操作提示", "保存成功", function(data) {
								if (data) {
									/* $(
											'#webinformation1')
											.dialog(
													'close'); */
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
			} else {
			}
		});
	}

	$('#channelId').combobox({
		editable : false,
		width : 150,

		panelHeight : 200,
	});
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="消息推送管理" style="padding: 2px">
			<table id="_productList"></table>
			<!-- toolbar="#tb" -->
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table cellpadding="1">
						</br>
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" class="easyui-linkbutton"
								data-options="iconCls:'icon-add'" id="add_product"
								onclick="showEdit(0);">新增</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="webinformation1" style="margin-top: 10px;">
				<form id="operatorProductDetail" method="post">
					<input type="hidden" id="id" name="id">
					<span>&nbsp;&nbsp;&nbsp;推送内容：</span><textarea rows="5" cols="30" id="ta1" name="ta1"></textarea>
					<div style="margin: 20px 0;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="btn btn-primary"onclick="edit()">保存</a>
					</div>
				</form>
			</div>
			<!-- </div> -->
			<div id="dfxqDlg">
				<iframe id="dfxqIframe" name="dfxqIframe" frameborder="0"
					marginheight="0" marginwidth="0" style="width: 100%; height: 99%;"
					scrolling="yes"></iframe>
			</div>
		</div>
	</div>
</body>
</html>
