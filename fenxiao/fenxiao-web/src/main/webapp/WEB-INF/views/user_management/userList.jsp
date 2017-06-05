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
<title>客户资金交易记录</title>
<script>
	$(function() {
		document.getElementById("eeId").style.display='none';
		document.getElementById("bbusinessType").style.display='none';
		$("#adminlist")
				.datagrid(
						{
							url : '${ctx}/userManage/getUserRoleList',
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
								$("#id").val(row.adminInfo.id);
							},
							columns : [ [
									{
										field : 'id',
										title : 'id',
										width : 7,
										hidden : true
									},
									
									{
										field : 'real_name',
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
										field : 'password',
										title : '密码',
										width : 7,
										hidden : true,
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.pwd;
											} else {
												return "";
											}
										}
									},
									{
										field : 'role',//权限
										title : '分组',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.role.name) {
												return row.role.name;
											} else {
												return "";
											}
										}
									},
									{
										field : 'eId',
										title : '所属企业',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo.fxEnterprise) {
												return row.adminInfo.fxEnterprise.name;
											} else {
												return "";
											}
										}
									},
									{
										field : 'moblie',
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
										field : 'email',
										title : '电子邮箱',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.email;
											} else {
												return "";
											}
										}
									},
									{
										field : 'account',
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
										field : 'status',
										title : '状态',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												if(row.adminInfo.status==0){
													return "可用";
												}else{
													return "不可用";
												}
											} else {
												return "";
											}
										}
									},
									{
										field : 'businessType',
										title : '业务类型',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo.fxEnterprise) {
												if(row.adminInfo.fxEnterprise.businessType==0){
													return "流量";
												}else if(row.adminInfo.fxEnterprise.businessType==1){
													return "话费";
												}else if(row.adminInfo.fxEnterprise.businessType==3){
													return "加油卡";
												}else{
													return "";
												}
											} else {
												return "";
											}
										}
										
									},
									{
										field : 'lastLoginTime',
										title : '上次登录时间',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.lastLoginTime;
											} else {
												return "";
											}
										}
										
									},
									{
										field : 'createTime',
										title : '创建时间',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.createTime;
											} else {
												return "";
											}
										}
									},{
										field : 'last_login_ip',
										title : '上次登录IP',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.adminInfo) {
												return row.adminInfo.lastLoginIp;
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
											return '<input  class="btn btn-small btn-info"  type="button" value="修改" onclick="showUser('
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

/*		$('#roleId').combobox({
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
			onChange:function(){
				var name = $('#roleId').combobox('getValue');
				if(name=="2"){
					document.getElementById("eeId").style.display='';
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
				}else{
					document.getElementById("eeId").style.display='none';
				}
			}
		});*/
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
			onChange:function(){
				var name = $('#roleId').combobox('getValue');
				if(name=="2"){
					document.getElementById("bbusinessType").style.display='';
					$('#businessType').change(function(){
							var type = $('#businessType').val();
							if(type!=""){
								document.getElementById("eeId").style.display='';
								$('#eId').combobox({
									url : "${ctx}/userManage/getEnterpriseListByBusinessType?businessType="+type,
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
							}else{
								document.getElementById("bbusinessType").style.display='none';
							}
						}
					);
				}else{
					document.getElementById("eeId").style.display='none';
				}
			}
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
	function showUser(id) {
		//清除内容
		$(':input', '#userDetail')
				.not(':button, :submit, :reset, :hidden').val('')
				.removeAttr('checked').removeAttr('selected');
		if (id == 0) {
			$('#webinformation1').dialog('open');
		}else{
			$.getJSON("${ctx}/userManage/getUser", {
				id : id
			}, function(data) {
				if (data.resultCode == -1) {
					$.messager.alert('消息', data.resultMsg);
				} else {
					$("#businessType").val("");					
					console.log(data.content);
					$("#name").val(data.content.adminInfo.realName);
					$("#email").val(data.content.adminInfo.email);
					$("#mphone").val(data.content.adminInfo.mphone);
					if(data.content.adminInfo.status=='0'){
						 $("input[type='radio'][name=status][value='0']").attr("checked",true);
					}else{
						 $("input[type='radio'][name=status][value='1']").attr("checked",true);
					}
	//				$("#adminRoleId").val(data.content.id);
					$("#fxUserId").val(data.content.adminInfo.id);
					$("#adminRoleId").val(data.content.id);
					var id = data.content.role.id;
					$("#roleId").combobox('select',id);
					$("#account").val(data.content.adminInfo.loginName);
					$("#eeId").hide();
					if(id=="2"){
						var type = data.content.adminInfo.fxEnterprise.businessType;
							$("#businessType").val(type);
							//alert(type);
							$("#bbusinessType").show();
							$('#eId').combobox({
								url : "${ctx}/userManage/getEnterpriseListByBusinessType?businessType="+type,
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
							var eId = data.content.adminInfo.fxEnterprise.id;
							$("#eId").combobox('select',eId);
							$("#eeId").show();
					}else{
						document.getElementById("bbusinessType").style.display='none';
					}
					$('#webinformation1').dialog('open');
				}
			});
		}
	}
	
	$("#roleId").ready(function () {
		$("#roleId").combobox({
			onChange: function () {
				
				var id = $("#roleId").combobox('getValue');
				if(id=="2"){
					$("#businessType").val("");
					$("#bbusinessType").show();
				}else{
					$("#bbusinessType").hide();
					$("#eeId").hide();
				}
			}
		});
	});
	function changeBusinessType(){
		var myselect=document.getElementById("businessType");
		var index=myselect.selectedIndex;             // selectedIndex代表的是你所选中项的index
		var type = myselect.options[index].value;
		if(type == ""){
			$("#eeId").hide();
		}else{
			$("#eeId").show();
			$('#eId').combobox({
				url : "${ctx}/userManage/getEnterpriseListByBusinessType?businessType="+type,
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
		}
		
	};
	
	function updateAdmin() {
		$('#userDetail').form('submit', {
			url : "${ctx}/userManage/updateUser",
			onSubmit : function(param) {
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.confirm("消息", "更新成功",
							function(
									data) {
								if (data) {
									/* $(
											'#webinformation1')
											.dialog(
													'close'); */
									window.location
											.reload();
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
	
	$('#eId').combobox({
		editable : false,
		width : 220,
		panelHeight : 200,
	});
	/*$("#webinformation1").dialog({
		
        close: function() {
        	alert(1111);
        	$(':input', '#userDetail')
			.not(':button, :submit, :reset, :hidden').val('')
			.removeAttr('checked').removeAttr('selected');
        }
    });*/
	
	
</script>
</head>
<body>
	<div id="zjjyTabs" class="easyui-tabs" data-options="fit:true">
		<div id="hyTab" title="用户管理" style="padding: 1px">
			<table id="adminlist">
			</table>
			<div id="hyDgTb">
				<form id="queryForm">
					<div id="tb" style="padding: 5px; height: auto"
						class="datagrid-toolbar">
						<b>姓名：</b><input type="text" name="search_LIKE_adminInfo.realName">
						<b>手机号：</b><input type="text" name="search_LIKE_adminInfo.mphone">
						<b>业务类型：</b><select  name="search_EQ_adminInfo.fxEnterprise.businessType"  id="search_EQ_businessType" style="width:90px;">
							<option value="">-请选择-</option>
							<option value="0">流量</option>
							<option value="1">话费</option>
							<option value="3">加油卡</option>
						</select>
						<b>分组：</b><input type="text" id="role" name="search_EQ_role.id">
						<a href="#" class="btn btn-default" iconCls="icon-search"
							id="search_id"><i class="fa fa-search"></i>&nbsp;查询</a> <span id="js_valdate" style="color: red"></span>&nbsp;<span
							style="color: red" id="js_no"></span>
						<a href="javascript:;" class="btn btn-primary"
						data-options="iconCls:'icon-add'" id="add_product" onclick="showUser(0);"><i class="fa fa-plus"></i>&nbsp;新增</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="webinformation1" style="padding: 30px">
		<form id="userDetail" method="post">
			<table cellpadding="4">
				<tr>
					<td>姓名：</td>
					<td><input type="hidden" id="adminRoleId" name="adminRoleId">
						<input type="hidden" id="fxUserId" name="fxUserId">
						<input type="text" id="name" name="name"></td>
				</tr>
				<tr>
					<td>账号：</td>
					<td><input type="text" id="account" name="account"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="text" id="password" name="password"></td>
				</tr> 
				<tr>
					<td>电子邮箱：</td>
					<td><input type="text" id="email" name="email"></td>
				</tr>
				<tr>
					<td>状态：<input type="hidden" id="id" name="id" value=""></td>
					<td><input type="radio" id="status" name="status" value="0" checked>&nbsp;&nbsp;可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" id="status" name="status" value="1">&nbsp;&nbsp;不可用</td>
				
				</tr>
				<tr>
					<td>分组：</td>
					<td><input type="text" id="roleId" name="roleId"></td>
				</tr>
				<tr id="bbusinessType">
					<td>业务类型：</td>
					<td><select id="businessType" style="height:25px;font-size:12px;margin-top:9px" onchange = "changeBusinessType()">
						<option value="">-请选择-</option>
						<option value="0">流量</option>
						<option value="1">话费</option>
						<option value="3">加油卡</option>
					</select></td>
				</tr>
				<tr id="eeId">
					<td>所属企业：</td>
					<td><input type="text" id="eId" name="eId"></td>
				</tr>
				<tr>
					<td>手机号：</td>
					<td><input type="text" id="mphone" name="mphone"></td>
				</tr>
				<tr>
					<td></td>
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