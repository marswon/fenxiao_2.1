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

.active {
	background: #00b5ff;
	border: 1px solid #fff;
	color: red;
}

.datagrid-cell {
	line-height: 25px;
}

.remo_li_style {
	list-style-type: none;
}
</style>
<script type="text/javascript">
	var flag = false;

	jQuery(function($) {
		$("#productGroup")
				.datagrid(
						{
							url : '${ctx}/productGroup/getFXProductGroup',
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
										field : 'id',
										title : 'ID',
										width : 120,
										align : "left"
									},
									{
										field : 'name',
										title : '产品组名称',
										width : 250,
										align : "center"
									},
									{
										field : 'businessType',
										title : '业务类型',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return "流量";
											} else if (val == 1) {
												return "话费";
											} else if (val == 2) {
												return "物业缴费";
											} else if (val == 3) {
												return "加油卡";
											}
										}
									},
									{
										field : 'selfProductType',
										title : '运营类型',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (val == 0) {
												return "非自营";
											} else if (val == 1) {
												return "自营";
											}
										}
									},
									{
										field : 'description',
										title : '备注',
										width : 80,
										align : "center",
									},
									{
										field : 'create_time',
										title : '创建时间',
										width : 100,
										align : "center",

									},

									{
										field : 'userAccountstatus',
										title : '操作',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {//

											return '<input type="button"  class="btn btn-info btn-small" value="编辑" onclick="add_product(\''
													+ row.id + '\')"/>';

										}
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
		$("#search_id").click(function() {
			var params = $('#productGroup').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#productGroup').datagrid('load');
		});
/* 		$('#webinformation1').dialog({
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
			title : "&ensp;平台产品编辑",
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
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="平台产品组管理" style="padding: 2px">
			<table id="productGroup"></table>
			<!-- toolbar="#tb" -->
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table cellpadding="1">
						<tr>
							<td><b>产品组名称：</b><input type="text" name="search_LIKE_name"
								id="search_LIKE_name" style="width: 280px;"></td>
							<td><b>业务类型：</b>
							<td><select class="sel" name="search_EQ_businessType"
								id="search_EQ_businessType"
								style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="0">流量</option>
									<option value="1">话费</option>
									<option value="2">物业缴费</option>
									<option value="3">加油卡</option>
							</select></td>
							<td><b>营业类型：</b>
							<td><select class="sel" name="search_EQ_selfProductType"
										id="search_EQ_selfProductType"
										style="width: 80px; padding-left: 0px;">
								<option value="">-请选择-</option>
								<option value="0">非自营</option>
								<option value="1">自营</option>
							</select></td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" id="search_id">搜索</a></td>
							<td></td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								data-options="iconCls:'icon-add'" id="add_product"
								onclick="add_product(0);">新增</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="webinformation1" style="margin-top: 10px;">
				<form id="_operatorsProductform" method="post">
					<div style="width: 500px; height: 200px; float: left; right: 0">
						<div
							style="margin-top: 10px; margin-left: 10px; text-align: left;">
							<table>
								<tr>
									<td>产品组名称:</td>
									<td><input name="productGroupName" id="productGroupName"
										style="margin-top: -5px;"></td>
								</tr>
								<tr>
									<td>业务类型:</td>
									<td><select class="sel" name="businessType"
										id="businessType" style="width: 80px; padding-left: 0px;">
											<option value="">-请选择-</option>
											<option value="0">流量</option>
											<option value="1">话费</option>
											<option value="2">物业缴费</option>
											<option value="3">加油卡</option>
									</select></td>
								</tr>
								<tr>
									<td>运营类型:</td>
									<td><select class="sel" name="selfProductType"
												id="selfProductType" style="width: 80px; padding-left: 0px;">
										<option value="">-请选择-</option>
										<option value="0">非自营</option>
										<option value="1">自营</option>
									</select></td>
								</tr>
								<tr>
									<td>备注:</td>
									<td><textarea id="desc" name="desc"></textarea></td>
								</tr>
							</table>
							<input type="hidden" name="id" id="id"> <a href="#"
								class="easyui-linkbutton" onclick="edit()"
								style="background: #049cf5; width: 100px; height: 30px; margin-left: 20px; margin-top: -6px;">保存</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
