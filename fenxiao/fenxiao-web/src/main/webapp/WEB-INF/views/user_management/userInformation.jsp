<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title></title>
<style type="text/css" media=screen>
</style>
<script type="text/javascript">
	jQuery(function($) {
		$("#intentionuserTb")
				.datagrid(
						{
							url : '${ctx}/usermanagement/getUserInfomationList',
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
										title : '会员ID',
										width : 120,
										align : "left"
									},
									{
										field : 'createTimeString',
										title : '注册时间',
										width : 150,
										align : "center"
									},
									{
										field : 'name',
										title : '姓名',
										width : 100,
										align : "center"
									},
									{
										field : 'mphone',
										title : '手机号',
										width : 100,
										align : "center"
									},
									{
										field : 'sexString',
										title : '性别',
										width : 40,
										align : "center"
									},
									{
										field : 'homeTown',
										title : '籍贯',
										width : 100,
										align : "center"
									},
									{
										field : 'schoolName',
										title : '学校',
										width : 100,
										align : "center"
									},
									{
										field : 'facultyName',
										title : '院系',
										width : 100,
										align : "center"
									},
									{
										field : 'creditAmount',
										title : '信用额度',
										width : 100,
										align : "center"
									},
									{
										field : 'totalConsumeAmount',
										title : '已消费',
										width : 100,
										align : "center"
									},
									{
										field : 'totalRepayment',
										title : '已还款',
										width : 100,
										align : "center"
									},
									{
										field : 'walletAmount',
										title : '钱包金额',
										width : 100,
										align : "center"
									},
									{
										field : 'authenticateType',
										title : '认证方式',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.authenticateType == 1) {
												return '学信网认证';
											} else {
												return '自有认证';
											}
										}
									},
									{
										field : 'authenticateStatus',
										title : '认证',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.authenticateStatus == 0) {
												return "未认证";
											} else if (row.authenticateStatus == 1) {
												return "待认证";
											} else if (row.authenticateStatus == 2) {
												return "已认证";
											} else if (row.authenticateStatus == 3) {
												return "认证失败";
											}
										}
									},
									{
										field : 'authenticateTime',
										title : '审核通过时间',
										width : 150,
										align : "center"
									},
									{
										field : 'isAutoCheck',
										title : '学信网审核方式',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											//是否自动审核 1：自动审核，2：人工审核
											if (row.isAutoCheck == 1) {
												return "自动审核";
											} else if (row.isAutoCheck == 2) {
												return "人工审核";
											}
										}
									},
									{
										field : 'recommended',
										title : '推荐人',
										width : 150,
										align : "center"
									},
									{
										field : 'userAccountstatus',
										title : '操作',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {//
											if (row.userAccountstatus == 0) {
												return '<input type="button" value="解除冻结" onclick="unblocked('
														+ row.id
														+ ')"/>'
														+ '&nbsp<input type="button" value="详情" onclick="updateUser('
														+ row.id + ')"/>';
											} else {
												return '<input type="button" value="冻结" onclick="blocked('
														+ row.id
														+ ')"/>'
														+ '&nbsp<input type="button" value="详情" onclick="updateUser('
														+ row.id + ')"/>';
											}
										}
									},{
										field : 'adminId',
										title : 'adminId',
										width : 150,
										align : "center",
										hidden:true,
										formatter : function(val, row, index) {
											if(row.adminId!=1){
												$("#hyzl").hide();
											}
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
		
		$('#schoolbox').combobox({
			url : "${ctx}/usermanagement/getAllSchool",
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
			onChange : function() {
				var id = $('#schoolbox').combobox('getValue');
				$('#facultybox').combobox({
					url : "${ctx}/usermanagement/getAllFaculty?id=" + id,
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
			}
		});

		$('#facultybox').combobox({
			url : "${ctx}/usermanagement/getAllFaculty",
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

		$("#search_id").click(function() {
			var params = $('#intentionuserTb').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#intentionuserTb').datagrid('load');
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
					$("#intentionuserTb").datagrid("reload");
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
					$("#intentionuserTb").datagrid("reload");
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
	//导出Excel表格
	function hyzlExportExcel() {
			$.messager.confirm('确认', '确认把该搜索结果导出Excel表格 ？', function(r) {
			if (r) {
				$.messager.progress({
					title : '处理中',
					msg : '请稍后',
				});
				$.messager.progress('close');
				location.href="${ctx}/tradingManage/hyzlExportExcel";
			}
		}); 
	}
</script>
</head>
<body>
	<!-- <div class="renwu_block"> -->
	<table id="intentionuserTb"></table>
	<!-- toolbar="#tb" -->
	<div id="xxDgTb">
		<!--  style="padding:5px;height:auto" class="datagrid-toolbar" -->
		<form id="queryForm">
			<table cellpadding="5">
				<tr>
					<td><b>学校院系：</b><input name="schoolId" id="schoolbox"></td>
					<td><b>院系：</b><input name="facultyId" id="facultybox"></td>
					<!--  <input  type="text" name="search_EQ_faculty.school.id"> -->
					<td><b>认证状态：</b><select name="authenticateStatus"
						id="js_status" style="width: 100px;"><option value="">请选择</option>
							<option value="2">已认证</option>
							<option value="1">待认证</option>
							<option value="0">未认证</option>
							<option value="3">认证失败</option></select></td>
					<td><b>认证类型：</b> <select name="authenticateType" id="js_type"
						style="width: 110px;"><option value="">请选择</option>
							<option value="1">学信网认证</option>
							<option value="2">自有认证</option></select>
					<td><b>冻结：</b><select name="userAccountstatus" id="js_status"
						style="width: 100px;"><option value="">请选择</option>
							<option value="0">冻结</option>
							<option value="1">可用</option></select></td>
							<td><input id="hyzl" type="button"  onclick="hyzlExportExcel();" value="导出excel" style="display:block;size:20px;background-color: #95B8E7"></td>							
				</tr>
				<tr>
					<td><b>会员ID：&nbsp;&nbsp;&nbsp;&nbsp;</b><input type="text"
						name="id" style="width: 100px;"></td>
					<td><b>姓名：</b><input type="text" name="name"
						style="width: 100px;"></td>
					<td><b>手机号：&nbsp;&nbsp;&nbsp;</b><input type="text"
						name="mphone" style="width: 100px;"></td>
					<td><b>推荐人：</b><input type="text" name="recommended"
						style="width: 100px;"></td>
					<td><b>学信网审核方式：</b> <select name="isAutoCheck" id="isAutoCheck"
						style="width: 100px">
							<option value="">请选择</option>
							<option value="1">自动审核</option>
							<option value="2">人工审核</option>
					</select></td>
				</tr>
				<tr>
					<td>审核通过时间从:<input class="easyui-datetimebox" name="startTime"
						data-options="showSeconds:false" style="width: 150px"></td>
					<td>至:<input class="easyui-datetimebox" name="endTime"
						data-options="showSeconds:false" style="width: 150px"></td>
					<td><b>审核人：&nbsp;&nbsp;&nbsp;&nbsp;</b><input type="text"
						name="shName" style="width: 100px;"></td>
					<td>用户行为等级：<select name="userLevel" id="userLevel"
									   style="width: 100px">
						<option value="">请选择</option>
						<option value="0">正常</option>
						<option value="-1">预警</option>
						<option value="-2">黑名单</option>
					</select></td>
					<td><a href="javascript:;" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" id="search_id">搜索</a> <!--  -->
					</td>
				</tr>
			</table>
			<!--  <span id="js_valdate" style="color: red"></span>&nbsp;<span style="color: red" id="js_no"></span> -->
		</form>
	</div>
	<!-- <div class="table_data">
   </div> -->
	<!-- </div> -->
	<div id="dfxqDlg">
		<iframe id="dfxqIframe" name="dfxqIframe" frameborder="0"
			marginheight="0" marginwidth="0" style="width: 100%; height: 99%;"
			scrolling="yes"></iframe>
	</div>
</body>
</html>
