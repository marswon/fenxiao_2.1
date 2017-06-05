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
	var userId;
	jQuery(function($) {
		$("#_productList")
				.datagrid(
						{
							url : '${ctx}/channelManege/toChannelList',
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
										width : 200,
										align : "left",
										halign : "center",
										styler : function(
												value, row,
												index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'name',
										title : '通道名称',
										width : 200,
										align : "center"
									},
									{
										field : 'appId',
										title : '所属服务器',
										width : 70,
										align : "center"
									},
									{
										field : 'autoStart',
										title : '自动启动',
										width : 70,
										align : "center",
										formatter : function(val, row, index) {
											if (row.autoStart == 1) {
												return "是";
											} else {
												return "否";
											}
										}

									},
									{
										field : 'yysTypeId',
										title : '运营商',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (row.yysTypeId == 1) {
												return '电信';
											} else if (row.yysTypeId == 2) {
												return '移动';
											} else if (row.yysTypeId == 3){
												return '联通';
											} else if (row.yysTypeId == -1){
												return '其他';
											}
										}
									},
									{
										field : 'provinceId',
										title : '省份',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											return showProvince(row.provinceId);
										}
									},
									{
										field : 'javaClassPath',
										title : '类路径',
										width : 300,
										halign : "center",
										align : "left",
										styler : function(
												value, row,
												index) {
											return "word-break: break-all; word-wrap:break-word;";
										}
									},
									{
										field : 'needQuery',
										title : '是否需要查询',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (row.needQuery == 1) {
												return "是";
											} else {
												return "否";
											}
										}
									},
									{
										field : 'businessType',
										title : '业务类型',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 0) {
												return "流量";
											} else if (row.businessType == 1){
												return "话费";
											} else if(row.businessType == 3){
												return "加油卡";
											}
										}
									},
									{
										field : 'balance',
										title : '通道余额',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {

											return '<span id="balance'
													+ row.id
													+ '" style="color:#fff;background:green" onclick="searchBalance(\''
													+ row.id
													+ '\',1)">查询</span>';
										}
									},
									{
										field : 'status',
										title : '操作',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return '<input type="button" class="btn btn-info btn-small" value="编辑"  id ="_edit" style="display:" onclick="showEdit(\''
													+ row.id
													+ '\')"/>&nbsp&nbsp<input class="btn btn-default btn-small" type="button" value="刷新" id ="_fresh" style="display:" onclick="showFresh(\''
													+ row.id + '\')"/>';
										}
									} ] ],
						});
		//-------
		//获取当前权限ID
		$.getJSON("${ctx}/userManage/getCurrentUser", {}, function(data) {
			if (data.resultCode == -1) {
				$.messager.alert('消息', data.resultMsg);
			} else {
				//用户ID超级管理员，则只能关闭开启权限
				//if (data.content.id == 1) {
				//	userId = 1;
					$("#_add").show();
				//}
			}
		});

		//--------
		$('#dfxqDlg').dialog({
			title : '会员资料',
			fit : true,
			draggable : false,
			closed : true,
			cache : false,
			modal : true
		});
		//运营商	－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		$('#yysbox')
				.combobox(
						{
							url : "${ctx}/product/getAllYys",
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
								//省份－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
								$('#provicebox')
										.combobox(
												{
													url : "${ctx}/product/getAllProvince",
													valueField : 'provinceId',
													textField : 'provinceName',
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
														//包类型－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
														var id = $(
																'#provicebox')
																.combobox(
																		'getValue');
														$('#FlowTypeByPorince')
																.combobox(
																		{
																			url : "${ctx}/product/getFlowTypeByPorinceId?proviceId="
																					+ id,
																			valueField : 'flowType',
																			textField : 'productFlowName',
																			editable : false,
																			width : 150,
																			panelHeight : 200,
																			loadFilter : function(
																					data) {
																				if (data.content) {
																					return data.content;
																				} else {
																					return false;
																				}
																			},
																			onChange : function() {
																				//包大小－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
																				var id = $(
																						'#provicebox')
																						.combobox(
																								'getValue');
																				var flowType = $(
																						'#FlowTypeByPorince')
																						.combobox(
																								'getValue');
																				$(
																						'#flowSize')
																						.combobox(
																								{
																									url : "${ctx}/product/getProvinceToSize?proviceId="
																											+ id
																											+ "&flowType="
																											+ flowType,
																									valueField : 'size',
																									textField : 'size',
																									editable : false,
																									width : 150,
																									panelHeight : 200,
																									loadFilter : function(
																											data) {
																										if (data.content) {
																											return data.content;
																										} else {
																											return false;
																										}
																									},
																								});
																			}
																		});
													}
												});
							}
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
			title : "通道编辑",
			width : 800,
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

		$("#search_id").click(function() {
			var params = $('#_productList').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#_productList').datagrid('load');
		});

	});

	function showFresh(id) {
		$.getJSON("${ctx}/channelManege/freshChannelById", {
			id : id
		}, function(data) {
			if (data.resultCode == 1) {
				$.messager.alert('消息', data.resultMsg);
			}
		});
	}

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
			$('#webinformation1').dialog('open');
		} else {
			console.log(id);
			//清除缓存
			$("#tt").tree('loadData', []);
			$.getJSON("${ctx}/channelManege/getChannelById", {
				id : id
			}, function(data) {
				if (data.resultCode == -1) {
					$.messager.alert('消息', data.resultMsg);
				} else {
					console.log(data);
					//	var id = data.content.role.id;
					//					$("#appId").val(data.content.appId);
					$("#id").val(data.content.id);
					$("#name").val(data.content.name);
					//alert(data.content.yysTypeId);
					$("#yysTypeId").val(data.content.yysTypeId);
					//			$("#autoStart").val(data.content.autoStart);
					$("#extended").val(data.content.extended);
					$("#javaClassPath").val(data.content.javaClassPath);
					$("#discount").val(data.content.discount);
					$("#provinceId").val(data.content.provinceId);
					if (data.content.status == 1) {
						$('input:radio[name="status"][value="1"]').prop(
								'checked', true);
					} else {
						$('input:radio[name="status"][value="0"]').prop(
								'checked', true);
					}
					if (data.content.autoStart == 1) {
						$('input:radio[name="autoStart"][value="1"]').prop(
								'checked', true);
					} else {
						$('input:radio[name="autoStart"][value="0"]').prop(
								'checked', true);
					}
					if (data.content.needQuery == 1) {
						$('input:radio[name="needQuery"][value="1"]').prop(
								'checked', true);
					} else {
						$('input:radio[name="needQuery"][value="0"]').prop(
								'checked', true);
					}
					if (data.content.businessType == 1) {
						$('input:radio[name="businessType"][value="1"]').prop(
								'checked', true);
					} else if(data.content.businessType == 0){
						$('input:radio[name="businessType"][value="0"]').prop(
								'checked', true);
					}else if(data.content.businessType == 3){
						$('input:radio[name="businessType"][value="3"]').prop(
								'checked', true);
					}
					$('#webinformation1').dialog('open');
				}
			});
		}
	}
	function edit() {
		$.messager.confirm("操作提示", "您确定要执行操作吗？", function(data) {
			if (data) {
				$('#operatorProductDetail').form('submit', {
					url : "${ctx}/channelManege/saveChannel",
					onSubmit : function(param) {
					},

					success : function(data) {
						console.log(3.5);
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
								$.messager.alert('消息', d.resultMsg);
							} else {
								$.messager.alert("消息", "未知错误");
							}
						}
					}
				});
			} else {
				//alert("取消");
			}
		});
	}

	$('#yysbox1').combobox({
		url : "${ctx}/product/getAllYys",
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
		}
	});
	$('#provincebox1').combobox({
		url : "${ctx}/product/getAllProvince",
		valueField : 'provinceId',
		textField : 'provinceName',
		editable : false,
		width : 150,
		panelHeight : 200,
		loadFilter : function(data) {
			if (data.content) {
				return data.content;
			} else {
				return false;
			}
		}
	});
	$('#yysbox1').combobox({
		editable : false,
		width : 150,
		panelHeight : 200,
	});
	$('#provincebox1').combobox({
		editable : false,
		width : 150,
		panelHeight : 200,
	});

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
	function updateStatus(id, status) {
		var text;
		if (status == 0) {
			text = "启用";
		} else {
			text = "关闭";
		}
		var s = confirm("是否" + text + "?");
		if (s) {
			$.getJSON("${ctx}/channelManege/updateChannelStatus", {
				id : id,
				status : status
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

	function searchBalance(id) {
		$('#queryForm')
				.form(
						'submit',
						{
							url : "${ctx}/queryBalance/query",
							onSubmit : function(param) {
								param.id = id;
							},
							success : function(data) {
								var d = eval("(" + data + ")");
								if (d.resultCode == 1) {
									$("#balance" + id).text(d.resultMsg);
									document.getElementById("balance" + id).style.background = "green";
									document.getElementById("balance" + id).style.color = "#fff";
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
		<div title="供应商渠道管理" style="padding: 2px">
			<table id="_productList"></table>
			<!-- toolbar="#tb" -->
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table cellpadding="1">
						<tr>

							<td><b>通道名称：</b><input type="text" name="flowName"
								style="width: 80px;"></td>
							<td><b>业务类型：</b>
							<td><select class="sel" name="businessType"
								id="businessType"
								style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="0">流量</option>
									<option value="1">话费</option>
									<option value="2">物业缴费</option>
									<option value="3">加油卡</option>
							</select></td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" id="search_id">搜索</a></td>
							<td><div style="display: none;" id="_add">
									<a href="javascript:;" class="easyui-linkbutton"
										data-options="iconCls:'icon-add'" id="add_product"
										onclick="showEdit(0);">新增</a>
								</div></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="webinformation1" style="padding: 30px">
				<form id="operatorProductDetail" method="post">
					<table cellpadding="2">
						<tr>
							<td>通道名称：</td>
							<td><input type="text" id="name" name="name"><font
								class="text_red">*</font></td>
							<td style="padding-left: 50px">对应的类路径：</td>
							<td><input type="text" id="javaClassPath"
								name="javaClassPath"><font class="text_red">*</font></td>
						</tr>
						<tr>
							<td>自动启动：</td>
							<td><input type="radio" id="autoStart" name="autoStart"
								value="1" checked>&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="autoStart" name="autoStart" value="0">&nbsp;&nbsp;否<font
								class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									*</font></td>
							<td style="padding-left: 50px">运营商：</td>
							<td><select id="yysTypeId" name="yysTypeId">
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option>
									<option value="-1">其他</option>
							</select><font class="text_red">*</font></td>

						</tr>
						<tr>

							<td>省份：</td>
							<td><select id="provinceId" name="provinceId">
									<option value="000">全国</option>
									<option value="010">北京</option>
									<option value="020">广东</option>
									<option value="021">上海</option>
									<option value="022">天津</option>
									<option value="023">重庆</option>
									<option value="025">江苏</option>
									<option value="026">青海</option>
									<option value="027">海南</option>
									<option value="028">四川</option>
									<option value="029">陕西</option>
									<option value="030">山西</option>
									<option value="035">河北</option>
									<option value="039">河南</option>
									<option value="040">内蒙古</option>
									<option value="041">辽宁</option>
									<option value="045">吉林</option>
									<option value="046">黑龙江</option>
									<option value="050">安徽</option>
									<option value="055">浙江</option>
									<option value="059">福建</option>
									<option value="060">山东</option>
									<option value="070">广西</option>
									<option value="071">湖北</option>
									<option value="073">湖南</option>
									<option value="075">江西</option>
									<option value="080">云南</option>
									<option value="085">贵州</option>
									<option value="089">西藏</option>
									<option value="090">宁夏</option>
									<option value="093">甘肃</option>
									<option value="095">新疆</option>
							</select><font class="text_red">*</font></td>
							<td style="padding-left: 50px">状态：<input type="hidden"
								id="id" name="id" value=""></td>
							<td><input type="radio" id="status" name="status" value="0"
								checked>&nbsp;&nbsp;可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
								id="status" name="status" value="1">&nbsp;&nbsp;不可用<font
								class="text_red">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</font></td>
						</tr>



						<tr>
							<td>是否需要查询：</td>
							<td><input type="radio" id="needQuery" name="needQuery"
								value="1" checked>&nbsp;&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
								id="needQuery" name="needQuery" value="0">&nbsp;&nbsp;否<font
								class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									*</font></td>
							<td style="padding-left: 50px">业务类型：</td>
							<td><input type="radio" id="businessType"
								name="businessType" value="1" checked>&nbsp;&nbsp;话费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
								id="businessType" name="businessType" value="0">&nbsp;&nbsp;流量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
								id="businessType" name="businessType" value="3">&nbsp;&nbsp;加油卡&nbsp; <font
								class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;*</font></td>

						</tr>
						<tr>

							<td>通道ID：</td>
							<td><select class="sel" name="appId" id="appId">
									<!--  							<option value="0">-电信-</option>-->
									<option value="app01">服务器1&nbsp;&nbsp;(127.0.0.1&nbsp;&nbsp;[80])</option>
									<option value="app02">服务器2&nbsp;&nbsp;(已停用)</option>
									<option value="app03">服务器3&nbsp;&nbsp;(已停用)</option>
							</select><font class="text_red">*</font></td>
						</tr>
						<tr>
							<td>通道信息：</td>
							<td><textarea rows="3" cols="20" id="extended"
									name="extended"></textarea><font class="text_red">*</font></td>
						</tr>
						<tr><td></td><td><div style="margin: 20px 0;">
						<a href="#" class="btn btn-primary" onclick="edit()">保存</a>
					</div></td></tr>
					</table>
					
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
