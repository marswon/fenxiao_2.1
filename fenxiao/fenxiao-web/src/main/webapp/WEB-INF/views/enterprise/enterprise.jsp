<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分销商管理</title>
<%-- <!-- 引入bootstrap样式 -->  
<link rel="stylesheet" type="text/css"   href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" />
<!-- 引入easyUi默认的CSS格式--蓝色 -->  
<link rel="stylesheet" type="text/css"   href="${ctx}/static/easyui/themes/default/easyui.css" />  
<!-- 引入Jquery -->  
<script type="text/javascript"   src="${ctx}/static/easyui/jquery-1.8.3.js" ></script>  
<!-- 引入Jquery_easyui -->  
<script type="text/javascript"   src="${ctx}/static/easyui/jquery.easyui.min.js" ></script>   --%>


<style type="text/css">
.text_red {
	color: red;
	font-size: 1.5rem;
	vertical-align: middle
}

.auth_create {
	font-size: .5rem;
}

.bored_div {
	border: 1px solid #ccc;
}

.sel {
	overflow-y: auto;
	border: solid 1px #d2d2d2;
	border-left-color: #ccc;
	border-top-color: #ccc;
	border-radius: 2px;
	box-shadow: inset 0 1px 0 #f8f8f8;
	background-color: #fff;
	padding: 4px 6px;
	height: 32px;
	line-height: 21px;
	color: #555;
	width: 195px;
	vertical-align: baseline;
}

.className {
	line-height: 30px;
	height: 30px;
	width: 100px;
	color: #ffffff;
	background-color: #ededed;
	font-size: 8px;
	font-weight: 100;
	font-family: Microsoft YaHei;
	background: -webkit-gradient(linear, left top, left bottom, color-start(0.05,
		#79bbff), color-stop(1, #378de5));
	background: -moz-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background: -o-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background: -ms-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background: linear-gradient(to bottom, #79bbff 5%, #378de5 100%);
	background: -webkit-linear-gradient(top, #79bbff 5%, #378de5 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#79bbff',
		endColorstr='#378de5', GradientType=0);
	border: 1px solid #84bbf3;
	-webkit-border-top-left-radius: 6px;
	-moz-border-radius-topleft: 6px;
	border-top-left-radius: 6px;
	-webkit-border-top-right-radius: 6px;
	-moz-border-radius-topright: 6px;
	border-top-right-radius: 6px;
	-webkit-border-bottom-left-radius: 6px;
	-moz-border-radius-bottomleft: 6px;
	border-bottom-left-radius: 6px;
	-webkit-border-bottom-right-radius: 6px;
	-moz-border-radius-bottomright: 6px;
	border-bottom-right-radius: 6px;
	-moz-box-shadow: inset 0px 1px 0px 0px #bbdaf7;
	-webkit-box-shadow: inset 0px 1px 0px 0px #bbdaf7;
	box-shadow: inset 0px 1px 0px 0px #bbdaf7;
	text-align: center;
	display: inline-block;
	text-decoration: none;
}

.className:hover {
	background-color: #f5f5f5;
	background: -webkit-gradient(linear, left top, left bottom, color-start(0.05,
		#378de5), color-stop(1, #79bbff));
	background: -moz-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background: -o-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background: -ms-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background: linear-gradient(to bottom, #378de5 5%, #79bbff 100%);
	background: -webkit-linear-gradient(top, #378de5 5%, #79bbff 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#378de5',
		endColorstr='#79bbff', GradientType=0);
}

.munt {
	width: auto;
	height: auto
}

.munt div {
	ffloat: left;
}

.panel-body {
	background-color: #ffffff;
	color: #000000;
	font-size: 15px;
}

.active {
	background: #00b5ff;
	border: 1px solid #fff;
	color: red;
}
</style>
<script>
	var flag = false;
	var a = "";
	var b = "";
	var c = "";
	var eId = "";
	var businessType = "";
	$(function() {
		$("#enterpriseList")
				.datagrid(
						{
							url : '${ctx}/api/enterprise/getEnterpriseList',
							nowrap : true,
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
										field : 'mid',
										title : '合作商编号',
										width : 50,
										align : "center",
									},
									{
										field : 'name',
										title : '合作商名称',
										width : 100,
										align : "center",
									},
									{
										field : 'address',
										title : '地址',
										width : 100,
										align : "center",
									},
									{
										field : 'privateKey',
										title : '密钥',
										width : 100,
										align : "center",
									},
									{
										field : 'status',
										title : '状态',
										width : 30,
										align : "center",
										formatter : function(val, row, index) {
											if (row.status == 0) {
												return "可用";
											} else {
												return "不可用";
											}
										}
									},
									{
										field : 'balance',
										title : '余额',
										width : 50,
										align : "center",
										formatter : function(val, row, index) {
											return row.balance / 1000 + "元";
										}
									},
									{
										field : 'creditTopBalance',
										title : '冻结金额',
										width : 50,
										align : "center",
										formatter : function(val, row, index) {
											return row.creditTopBalance / 1000
													+ "元";
										}
									},
									{
										field : 'businessType',
										title : '业务类型',
										width : 50,
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
										title : '营销类型',
										width : 50,
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
										field : 'edit',
										title : '操作',
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											return '<input type="button" class="btn btn-default btn-small" value="折扣配置" onclick="showEditDiscount(\''
													+ row.id
													+ '\',\''
													+ row.name
													+ '\',\''
													+ row.businessType
													+ '\');"/>&nbsp;&nbsp;<input type="button" class="btn btn-info btn-small" value="编辑" onclick="showEdit(\''
													+ row.id
													+ '\',\''
													+ row.businessType
													+ '\');"/>';
										}
									} ] ],
						});

		$("#search_id").click(function() {
			var params = $('#enterpriseList').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#enterpriseList').datagrid('load');
		});

	/* 	$('#webinformation1').dialog({
			title : "合作商编辑",
			width : 750,
			height : 500,
			closed : true,
			cache : false,
			modal : true,
			center : true,
		}); */
		
		
		
		$('#webinformation1').dialog({
			iconCls:'icon-edit',
			title : "&ensp;合作商编辑",
			width : 850,
			height : 600,
			closed : true,
			cache : false,
			modal : true,
			center : true,
			resizable:true,
			minimizable:true,  
			maximizable:true,  
		});
		$('#createAcount').dialog({
			iconCls:'icon-add',
			title : "&ensp;新增合作商",
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
		$('#discountformation').dialog({
			iconCls:'icon-edit',
			title : "&ensp;折扣配置",
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
		//=====================================================================
		$('#tt_liuliang').tree({
			checkbox : true,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return data;
				}
			}
		});
		$('#tt_huafei').tree({
			checkbox : true,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return data;
				}
			}
		});
		$('#tt_wuye').tree({
			checkbox : true,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return data;
				}
			}
		});
		$('#tt_jiayouka').tree({
			checkbox : true,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return data;
				}
			}
		});
		//销毁树
        $("#webinformation1").dialog({
            onClose: function () {
               // alert("blablabla");
               // $(this).dialog('destroy');//销毁
               // 清除树
                $("#tt_huafei").tree('loadData', []);
                $("#tt_liuliang").tree('loadData', []);
				flag = false;
            }
        });
		//=====================================================================
	});

	function openAddEntenpriseView() {
		$('#createAcount').dialog('open');
	}
	function showEdit(id, businessType) {
		//1.判断业务类型
		var businessTypeStr = "";
		if (businessType == 0) {
			//流量
			businessTypeStr = "流量";
			document.getElementById("order_type_0").style.display = "";//显示
			document.getElementById("order_type_1").style.display = "none";//隐藏
			document.getElementById("order_type_2").style.display = "none";//隐藏
			document.getElementById("order_type_3").style.display = "none";//隐藏
			//$("#tt_liuliang").tree('loadData', []);

		} else if (businessType == 1) {
			//话费
			businessTypeStr = "话费";
			document.getElementById("order_type_0").style.display = "none";//隐藏
			document.getElementById("order_type_1").style.display = "";//显示
			document.getElementById("order_type_2").style.display = "none";//隐藏
			document.getElementById("order_type_3").style.display = "none";//隐藏
			$("#tt_huafei").tree('loadData', []);

		} else if (businessType == 2) {
			//物业缴费
			businessTypeStr = "物业缴费";
			document.getElementById("order_type_0").style.display = "none";//隐藏
			document.getElementById("order_type_1").style.display = "none";//隐藏
			document.getElementById("order_type_2").style.display = "";//显示
			document.getElementById("order_type_3").style.display = "none";//隐藏
			//清除缓存
			//$("#tt_wuye").tree('loadData', []);
		} else if (businessType == 3) {
			//加油卡
			businessTypeStr = "加油卡";
			document.getElementById("order_type_0").style.display = "none";//隐藏
			document.getElementById("order_type_1").style.display = "none";//隐藏
			document.getElementById("order_type_2").style.display = "none";//隐藏
			document.getElementById("order_type_3").style.display = "";//显示
			//清除缓存
			//$("#tt_jiayouka").tree('loadData', []);
		}

		//清除内容
		$(':input', '#adminDetail').not(':button, :submit, :reset, :hidden')
				.val('').removeAttr('checked').removeAttr('selected');
		//2
		//获取全网折扣
		$.getJSON("${ctx}/api/discount/findDiscountByeId", {
			eId : id
		}, function(data) {
			if (data.resultCode == -1) {
				$.messager.alert('消息', data.resultMsg);
			} else {
				if (data.content.length > 0) {
					if (businessType == 0) {
						$("#liuliangDiscountDX").val(data.content[0].discount);
						$("#liuliangDiscountYD").val(data.content[1].discount);
						$("#liuliangDiscountLT").val(data.content[2].discount);
					}
				}
			}
		});
		$.getJSON("${ctx}/api/enterprise/findEnterpriseById", {
			id : id
		}, function(data) {
			if (data.resultCode == -1) {
				$.messager.alert('消息', data.resultMsg);
			} else {
				//	var id = data.content.role.id;
				$("#mid").val(data.content.mid);
				$("#authIP").val(data.content.authIP);
				$("#privateKey").val(data.content.privateKey);
				$("#email").val(data.content.email);
				$("#linkMan").val(data.content.linkMan);
				$("#linkPhone").val(data.content.linkPhone);
				$("#address").val(data.content.address);
				$("#name").val(data.content.name);
				$("#id").val(data.content.id);
				$("#eId").val(data.content.id);
				$("#crestValue").val(data.content.crestValue/1000);
				$("#balanceReminder").val(data.content.balanceReminder / 1000);
				$("#creditTopBalance")
						.val(data.content.creditTopBalance / 1000);
				if (data.content.status == "1") {
					$('input:radio[name="status"][value="1"]').prop('checked',
							true);
				} else {
					$('input:radio[name="status"][value="0"]').prop('checked',
							true);
				}
				if (data.content.openAreaStrategy == "1") {
					$('input:radio[name="openAreaStrategy"][value="1"]').prop('checked',
							true);
				} else {
					$('input:radio[name="openAreaStrategy"][value="0"]').prop('checked',
							true);
				}
				$('#webinformation1').dialog('open');
			}
		});
	}
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
		$('#admininfo_form').form('submit', {
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

	function editEnterprise() {
		$.messager.confirm("操作提示", "您确定要执行操作吗？", function(data) {
			if (data) {
				$('#createAcountForm').form('submit', {
					url : "${ctx}/api/enterprise/saveEnterprise",
					onSubmit : function(param) {
						var count = 0;
						var txt1 = "";
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
				//alert("取消");
			}
		});
	}

	//保存产品
	function edit(businessType) {
		if (businessType == 0) {
			//流量
			edit_liuliang();
		} else if (businessType == 1) {
			//话费
			edit_huafei();
		} else if (businessType == 2) {
			//物业缴费
			edit_wuye();
		} else if (businessType == 3) {
			//加油卡
			edit_jiayouka();
		}
	}

	//保存流量产品配置
	function edit_liuliang() {
		$.messager
				.confirm(
						"操作提示",
						"您确定要执行操作吗？",
						function(data) {
							if (data) {
								$('#adminDetail')
										.form(
												'submit',
												{
													url : "${ctx}/api/enterprise/saveEnterpriseAndProductToDiscount",
													onSubmit : function(param) {
														var checked = $(
																'#tt_liuliang')
																.tree(
																		'getChecked');
														var count = 0;
														var txt1 = "";
														for (var i = 0; i < checked.length; i++) {
															if (i == checked.length - 1) {
																txt1 = txt1
																		+ checked[i].id;
															} else {
																txt1 = txt1
																		+ checked[i].id
																		+ ",";
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
														param.dx = document
																.getElementById("liuliangDiscountDX").value;
														param.yd = document
																.getElementById("liuliangDiscountYD").value;
														param.lt = document
																.getElementById("liuliangDiscountLT").value;
														param.businessType = "0";

													},
													success : function(data) {
														var d = eval("(" + data
																+ ")");
														if (d.resultCode == 1) {
															/* 	$.messager.alert(
																		"消息",
																		"保存成功"); */

															$.messager
																	.confirm(
																			"操作提示",
																			"保存成功",
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
																$.messager
																		.alert(
																				"消息",
																				d.resultMsg);
															} else {
																$.messager
																		.alert(
																				"消息",
																				"未知错误");
															}
														}
													}
												});
							} else {
								//alert("取消");
							}
						});
	}

	//保存话费产品配置
	function edit_huafei() {
		$.messager
				.confirm(
						"操作提示",
						"您确定要执行操作吗？",
						function(data) {
							if (data) {
								$('#adminDetail')
										.form(
												'submit',
												{
													url : "${ctx}/api/enterprise/saveEnterpriseAndProductToDiscount",
													onSubmit : function(param) {
														var checked = $(
																'#tt_huafei')
																.tree(
																		'getChecked');
														var count = 0;
														var txt1 = "";
														for (var i = 0; i < checked.length; i++) {
															if (i == checked.length - 1) {
																txt1 = txt1
																		+ checked[i].id;
															} else {
																txt1 = txt1
																		+ checked[i].id
																		+ ",";
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
														param.businessType = "1";
													},
													success : function(data) {
														var d = eval("(" + data
																+ ")");
														if (d.resultCode == 1) {
															/* 	$.messager.alert(
																		"消息",
																		"保存成功"); */

															$.messager
																	.confirm(
																			"操作提示",
																			"保存成功",
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
																$.messager
																		.alert(
																				"消息",
																				d.resultMsg);
															} else {
																$.messager
																		.alert(
																				"消息",
																				"未知错误");
															}
														}
													}
												});
							} else {
								//alert("取消");
							}
						});
	}

	/*
	 *根据业务类型打开开通产品
	 */
	function openTreeProduct(businessType) {
		flag = true;
		//分销商ID
		var eId = $('#eId').val();
		$.post('${ctx}/product/getProductPage', {
			eId : eId,
			businessType : businessType
		}, function(data) {
			if (data.resultCode == 1) {
				if (businessType == 0) {
					$("#tt_liuliang").tree('loadData', data.content);
				} else if (businessType == 1) {
					$("#tt_huafei").tree('loadData', data.content);

				} else if (businessType == 2) {

				} else if (businessType == 3) {

				}
			} else if (data.resultCode == -1 || data.resultCode == -500) {
				$.messager.alert('消息', data.resultMsg);
			}
		});
	}

	/*
	 *根据业务类型打开折扣配置 弹框
	 */
	function showEditDiscount(id, name, businessTypes) {
		$(':input', '#adminDetail').not(':button, :submit, :reset, :hidden')
				.val('').removeAttr('checked').removeAttr('selected');
		businessType = businessTypes;
		if (businessTypes == 0) {
			showEditDiscount_liuliang(id, name, businessTypes);
		} else if (businessTypes == 1) {
			showEditDiscount_huafei(id, name, businessTypes)
		} else if (businessTypes == 2) {

		}
	}

	/*
	 *打开流量折扣配置 弹框
	 */
	function showEditDiscount_liuliang(id, name, businessType) {
		$(":checkbox").attr("checked", false);
		$("#yysType").empty();
		$("#province").empty();
		$("#productInfo").empty();
		liuliang_demo
		//1.判断业务类型
		var businessTypeStr = "";
		if (businessType == 0) {
			//流量
			businessTypeStr = "流量";
			document.getElementById("liuliang_value").style.display = "";//显示
			document.getElementById("liuliang_demo").style.display = "";//显示
			document.getElementById("huafei_value").style.display = "none";//
			document.getElementById("span_flowType").style.display = "";//

		}
		//2.弹出对话框
		$('#discountformation').panel({
			title : businessTypeStr + ":折扣配置( " + name + " )"
		});
		//
		eId = id;
		//ggx
		$
				.getJSON(
						"${ctx}/api/enterprise/findFXMobileAreaByEid",
						{
							eId : id,
							businessType : businessType
						},
						function(data) {

							var province = $("#province");
							province.empty();
							//省份
							var _li_quanguo = $('<li style="list-style-type:none;"></li>');
							_li_quanguo.attr('productCode', "111");
							_li_quanguo.attr('remark', '000');
							_li_quanguo
									.append('<input type="button" id="000" style="width:100px;" value="全国" onclick="searchProduct(\'a\',\'000\');"/>');
							province.append(_li_quanguo);
							//省份
							for (var i = 0; i < data.content.length; i++) {
								var _li = $('<li style="list-style-type:none;"></li>');
								_li.attr('productCode', "111");
								_li.attr('remark', data.content.id);
								_li.append('<input type="button" id="'
										+ data.content[i].provinceId
										+ '" style="width:100px;" value="'
										+ data.content[i].provinceName
										+ '" onclick="searchProduct(\'a\',\''
										+ data.content[i].provinceId
										+ '\');"/>');
								province.append(_li);
							}
							//运营商
							var yysType = $("#yysType");
							yysType.empty();
							var _li = $('<li style="list-style-type:none;"></li>');
							_li
									.append('<input type="button" style="width:100px;" value="电信" onclick="searchProduct(\'b\',\''
											+ 1 + '\');"/>');
							_li
									.append('<input type="button" style="width:100px;" value="移动" onclick="searchProduct(\'b\',\''
											+ 2 + '\');"/>');
							_li
									.append('<input type="button" style="width:100px;" value="联通" onclick="searchProduct(\'b\',\''
											+ 3 + '\');"/>');
							yysType.append(_li);
							//包类型
							var flowType = $("#flowType");
							flowType.empty();
							var _li = $('<li style="list-style-type:none;"></li>');
							_li
									.append('<input type="button" style="width:100px;" value="漫游" onclick="searchProduct(\'c\',\''
											+ 0 + '\');"/>');
							_li
									.append('<input type="button" style="width:100px;" value="本地" onclick="searchProduct(\'c\',\''
											+ 1 + '\');"/>');
							flowType.append(_li);

							$('#discountformation').dialog('open');

						});
	}
	/*
	 *打开话费折扣配置 弹框
	 */
	function showEditDiscount_huafei(id, name, businessType) {
		$(":checkbox").attr("checked", false);
		$("#yysType").empty();
		$("#province").empty();
		$("#productInfo").empty();
		$("#productInfo").html("");
		$("#flowType").html("");

		//1.判断业务类型
		var businessTypeStr = "";
		if (businessType == 1) {
			//话费
			businessTypeStr = "话费";
			document.getElementById("huafei_value").style.display = "";//显示
			document.getElementById("liuliang_value").style.display = "none";//
			document.getElementById("liuliang_demo").style.display = "none";//
			document.getElementById("span_flowType").style.display = "none";//

		}
		//2.弹出对话框
		$('#discountformation').panel({
			title : businessTypeStr + ":折扣配置( " + name + " )"
		});
		//
		eId = id;
		//ggx
		$
				.getJSON(
						"${ctx}/api/enterprise/findFXMobileAreaByEid",
						{
							eId : id,
							businessType : businessType
						},
						function(data) {

							var province = $("#province");
							province.empty();
							//省份
							var _li_quanguo = $('<li style="list-style-type:none;"></li>');
							_li_quanguo.attr('productCode', "111");
							_li_quanguo.attr('remark', '000');
							_li_quanguo
									.append('<input type="button" id="000" style="width:100px;" value="全国" onclick="searchProduct(\'a\',\'000\');"/>');
							province.append(_li_quanguo);

							for (var i = 0; i < data.content.length; i++) {
								var _li = $('<li style="list-style-type:none;"></li>');
								_li.attr('productCode', "111");
								_li.attr('remark', data.content.id);
								_li.append('<input type="button" id="'
										+ data.content[i].provinceId
										+ '" style="width:100px;" value="'
										+ data.content[i].provinceName
										+ '" onclick="searchProduct(\'a\',\''
										+ data.content[i].provinceId
										+ '\');"/>');
								province.append(_li);
							}
							//运营商
							var yysType = $("#yysType");
							yysType.empty();
							var _li = $('<li style="list-style-type:none;"></li>');
							_li
									.append('<input type="button" style="width:100px;" value="电信" onclick="searchProduct(\'b\',\''
											+ 1 + '\');"/>');
							_li
									.append('<input type="button" style="width:100px;" value="移动" onclick="searchProduct(\'b\',\''
											+ 2 + '\');"/>');
							_li
									.append('<input type="button" style="width:100px;" value="联通" onclick="searchProduct(\'b\',\''
											+ 3 + '\');"/>');
							yysType.append(_li);
							$('#discountformation').dialog('open');

						});
	}
	//点击省份，运营商，漫游本地宝，查询配置的产品折扣
	function searchProduct(type, id) {
		$("#province li input").click(function() {
			// 添加流量包选中样式
			$("#province li input").removeClass("active");
			$(this).addClass("active").siblings().removeClass("active");
		});
		$("#yysType li input").click(function() {
			// 添加流量包选中样式
			$("#yysType li input").removeClass("active");
			$(this).addClass("active").siblings().removeClass("active");
		});
		$("#flowType li input").click(function() {
			// 添加流量包选中样式
			$("#flowType li input").removeClass("active");
			$(this).addClass("active").siblings().removeClass("active");
		});

		if (type == "a") {
			a = id;
		}
		if (type == "b") {
			b = id;
		}
		if (type == "c") {
			c = id;
		}
		//	alert(a + "====" + b + "=====" + "c====" + c + "====eId=====" + eId)
		$
				.getJSON(
						"${ctx}/api/discount/findDiscountByEnterprise",
						{
							provinceCode : a,
							yysType : b,
							flowType : c,
							eId : eId,
							businessType : businessType
						},
						function(data) {
							var productInfo = $("#productInfo");
							productInfo.empty();
							//alert(data.content);
							//省份
							for (var i = 0; i < data.content.length; i++) {
								var _li = $('<li style="list-style-type:none;"></li>');
								_li.attr('productCode', "111");
								_li.attr('remark', data.content.id);
								_li
										.append('<input type="button" style="width:100px;" value="'
						+ data.content[i].size
						+ '"/>');
								_li
										.append('<input type="button" style="width:100px;" value="'
						+ data.content[i].discount / 100
						+ '折"/>');
								_li
										.append('<input type="button" style="width:100px;color:red" value="删除" onclick="removeDiscount(\''
												+ data.content[i].id
												+ '\');"/>');
								productInfo.append(_li);
							}
							$('#discountformation').dialog('open');
						});
	}

	/*
	 *保存折扣配置
	 */
	function saveDiscount() {

		text = $("input:checkbox[name='flowValue']:checked").map(
				function(index, elem) {
					return $(elem).val();
				}).get().join(',');

		var options = $("#provinceDiscount option:selected"); //获取选中的项
		var discount = options.val();

		if (text == "" || options.val() == "") {
			alert("流量值或折扣不能为空");
			return;
		}

		$.messager
				.confirm(
						"提示",
						"您确定要执行操作吗？",
						function(data) {
							if (data) {
								$('#adminDetail')
										.form(
												'submit',
												{
													url : "${ctx}/api/discount/saveDiscount",
													onSubmit : function(param) {
														param.flowValue = text;
														param.discount = discount;
														param.eId = eId;
														param.provinceCode = a;
														param.yysType = b;
														param.flowType = c;
														param.businessType = businessType;
													},
													success : function(data) {
														var d = eval("(" + data
																+ ")");
														if (d.resultCode == 1) {
															/* 	$.messager.alert(
																		"消息",
																		"保存成功"); */

															$.messager
																	.confirm(
																			"操作提示",
																			"保存成功",
																			function(
																					data) {
																				if (data) {
																					/* $(
																							'#webinformation1')
																							.dialog(
																									'close'); */
																					/* window.location.reload(); */
																					$
																							.getJSON(
																									"${ctx}/api/discount/findDiscountByEnterprise",
																									{
																										provinceCode : a,
																										yysType : b,
																										flowType : c,
																										eId : eId,
																										businessType : businessType
																									},
																									function(
																											data) {
																										var productInfo = $("#productInfo");
																										productInfo
																												.empty();
																										//alert(data.content);
																										//省份
																										for (var i = 0; i < data.content.length; i++) {
																											var _li = $('<li style="list-style-type:none;"></li>');
																											_li
																													.attr(
																															'productCode',
																															"111");
																											_li
																													.attr(
																															'remark',
																															data.content.id);
																											_li
																													.append('<input type="button" style="width:100px;" value="'
													+ data.content[i].size
													+ '"/>');
																											_li
																													.append('<input type="button" style="width:100px;" value="'
													+ data.content[i].discount / 100
													+ '折"/>');
																											_li
																													.append('<input type="button" style="width:100px;color:red" value="删除" onclick="removeDiscount(\''
																															+ data.content[i].id
																															+ '\');"/>');
																											productInfo
																													.append(_li);
																										}
																										$(
																												'#discountformation')
																												.dialog(
																														'open');
																									});

																				} else {
																					//alert("取消");
																				}
																			});

														} else {
															if (d.resultMsg) {
																$.messager
																		.alert(
																				"消息",
																				d.resultMsg);
															} else {
																$.messager
																		.alert(
																				"消息",
																				"未知错误");
															}
														}
													}
												});
							} else {
								//alert("取消");
							}
						});
	}

	/*
	 *删除折扣配置
	 */
	function removeDiscount(id) {
		//删除折扣
		$.messager
				.confirm(
						"提示",
						"您确定要删除吗？",
						function(data) {
							if (data) {
								$('#adminDetail')
										.form(
												'submit',
												{
													url : "${ctx}/api/discount/removeDiscount",
													onSubmit : function(param) {
														param.id = id;
													},
													success : function(data) {
														var d = eval("(" + data
																+ ")");
														if (d.resultCode == 1) {
															/* 	$.messager.alert(
																		"消息",
																		"保存成功"); */

															$.messager
																	.confirm(
																			"操作提示",
																			"删除成功",
																			function(
																					data) {
																				if (data) {
																					/* $(
																							'#webinformation1')
																							.dialog(
																									'close'); */
																					/* window.location.reload(); */
																					$
																							.getJSON(
																									"${ctx}/api/discount/findDiscountByEnterprise",
																									{
																										provinceCode : a,
																										yysType : b,
																										flowType : c,
																										eId : eId,
																										businessType : businessType
																									},
																									function(
																											data) {
																										var productInfo = $("#productInfo");
																										productInfo
																												.empty();
																										//alert(data.content);
																										//省份
																										for (var i = 0; i < data.content.length; i++) {
																											var _li = $('<li style="list-style-type:none;"></li>');
																											_li
																													.attr(
																															'productCode',
																															"111");
																											_li
																													.attr(
																															'remark',
																															data.content.id);
																											_li
																													.append('<input type="button" style="width:100px;" value="'
											+ data.content[i].size
											+ '"/>');
																											_li
																													.append('<input type="button" style="width:100px;" value="'
											+ data.content[i].discount / 100
											+ '折"/>');
																											_li
																													.append('<input type="button" style="width:100px;color:red" value="删除" onclick="removeDiscount(\''
																															+ data.content[i].id
																															+ '\');"/>');
																											productInfo
																													.append(_li);
																										}
																										$(
																												'#discountformation')
																												.dialog(
																														'open');
																									});

																				} else {
																					//alert("取消");
																				}
																			});

														} else {
															if (d.resultMsg) {
																$.messager
																		.alert(
																				"消息",
																				d.resultMsg);
															} else {
																$.messager
																		.alert(
																				"消息",
																				"未知错误");
															}
														}
													}
												});
							} else {
								//alert("取消");
							}
						});

	}
</script>
</head>
<body>
	<div id="zjjyTabs" class="easyui-tabs" data-options="fit:true">
		<div id="hyTab" title="流量话费合作商" style="padding: 1px">
			<table id="enterpriseList">
			</table>
			<div id="hyDgTb">
				<!-- 主界面搜索框 -->
				<form id="queryForm">
					<div id="tb" style="padding: 5px; height: auto"
						class="datagrid-toolbar">
						<b>合作商名称：</b><input type="text" name="search_LIKE_name"> <b>合作商编号：</b><input
							type="text" name="search_LIKE_mid"> <b>业务类型：</b> <select
							class="sel" name="search_EQ_businessType"
							id="search_EQ_businessType"
							style="width: 80px; padding-left: 0px;">
							<option value="">-请选择-</option>
							<option value="0">流量</option>
							<option value="1">话费</option>
							<!--  <option value="2">物业缴费</option>
							<option value="3">加油卡</option>-->
						</select>
						<b>营销类型：</b> <select
							class="sel" name="search_EQ_selfProductType"
							id="search_EQ_selfProductType"
							style="width: 80px; padding-left: 0px;">
						<option value="">-请选择-</option>
						<option value="0">非自营</option>
						<option value="1">自营</option>
					</select>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							id="search_id">查询</a> <a href="javascript:;"
							class="easyui-linkbutton" data-options="iconCls:'icon-add'"
							onclick="openAddEntenpriseView();">新增</a><span id="js_valdate"
							style="color: red"></span>&nbsp;<span style="color: red"
							id="js_no"></span>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 添加合作商 start -->
	<div id="createAcount" style="margin-top: 10px;">
		<form id="createAcountForm" method="post">
			<table cellpadding="2">
				<tr>
					<td>合作商名称：</td>
					<td><input type="text" id="name" name="name"><font
						class="text_red">*</font></td>
					<td style="padding-left: 50px">联系人：</td>
					<td><input type="text" id="linkMan" name="linkMan"><font
						class="text_red">*</font></td>
				</tr>
				<tr>
					<td>联系电话：</td>
					<td><input type="text" id=linkPhone name="linkPhone"><font
						class="text_red">*</font></td>
					<td style="padding-left: 50px">联系地址：</td>
					<td><input type="text" id="address" name="address"><font
						class="text_red">*</font></td>
				</tr>
				<tr>
					<td>电子邮箱：</td>
					<td><input type="text" id="email" name="email"><font
						class="text_red">*</font></td>
				</tr>
			</table>
			<br />
			<div
				style="BORDER-TOP: #008080 .12rem dashed; OVERFLOW: hidden; HEIGHT: 1.8px"></div>
			<br />
			<table cellpadding="2">

				<tr>
					<td>合作商ID：</td>
					<td><input type="text" id="mid" name="mid" readonly><font
						class="text_red auth_create">(系统自动分配)</font></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>鉴权IP：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><input type="text" id="authIP" name="authIP"><font
						class="text_red">*</font></td>
				</tr>

				<tr>
					<td>密钥：</td>
					<td><input type="text" id="privateKey" name="privateKey"
						value="" readonly> <font class="text_red auth_create">(系统自动分配)</font></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><input type="radio" id="status" name="status" value="0"
						checked="checked">&nbsp;可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="radio" id="status" name="status" value="1">&nbsp;不可用</td>
					<td></td>
				</tr>
			</table>
			<br />
			<div
				style="BORDER-TOP: #008080 .12rem dashed; OVERFLOW: hidden; HEIGHT: 1.8px"></div>
			<br />
			<table cellpadding="2">
				<tr>
					<td>业务类型：</td>
					<td><select class="sel" name="businessType" id="businessType"
						style="width: 80px; padding-left: 0px;">
							<option value="">-请选择-</option>
							<option value="0">流量</option>
							<option value="1">话费</option>
							<option value="2">物业缴费</option>
							<option value="3">加油卡</option>
					</select><font class="text_red auth_create">(确定后不可更改)</font></td>
					<td></td>
				</tr>
				<tr>
					<td>营销类型：</td>
					<td><select class="sel" name="selfProductType" id="selfProductType"
								style="width: 80px; padding-left: 0px;">
						<option value="">-请选择-</option>
						<option value="0">非自营</option>
						<option value="1">自营</option>
					</select><font class="text_red auth_create">(确定后不可更改)</font></td>
					<td></td>
				</tr>
			</table>
			<div style="margin: 20px 0; text-align: center;">
				<a href="#"  class="btn btn-primary btn-big" onclick="editEnterprise()">保存分配</a>
			</div>
		</form>
	</div>
	<!-- 添加合作商 end -->

	<!-- 编辑合作商 start -->
	<div id="webinformation1" style="padding: 30px">
		<!-- 通用编辑 start -->
		<form id="adminDetail" method="post">
			<table cellpadding="2">
				<tr>
					<td>合作商名称：</td>
					<td><input type="text" id="name" name="name"><font
						class="text_red">*</font></td>
					<td style="padding-left: 50px">联系人：</td>
					<td><input type="text" id="linkMan" name="linkMan"><font
						class="text_red">*</font></td>
				</tr>
				<tr>
					<td>联系电话：</td>
					<td><input type="text" id=linkPhone name="linkPhone"><font
						class="text_red">*</font></td>
					<td style="padding-left: 50px">联系地址：</td>
					<td><input type="text" id="address" name="address"><font
						class="text_red">*</font></td>
				</tr>
				<tr>
					<td>电子邮箱：</td>
					<td><input type="text" id="email" name="email"><font
						class="text_red">*</font></td>
				</tr>
			</table>
			<br />
			<div
				style="BORDER-TOP: #008080 .12rem dashed; OVERFLOW: hidden; HEIGHT: 1.8px"></div>
			<br />
			<table cellpadding="2">
				<tr>
					<td><input type="hidden" id="id" name="id"><input
						type="hidden" id="eId"> 合作商ID：</td>
					<td><input type="text" id="mid" name="mid" readonly><font
						class="text_red auth_create">(系统自动分配)</font></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>鉴权IP：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><input type="text" id="authIP" name="authIP"><font
						class="text_red">*</font></td>
				</tr>
				<tr>
					<td>密钥：</td>
					<td><input type="text" id="privateKey" name="privateKey"
						value="" readonly> <font class="text_red auth_create">(系统自动分配)</font></td>
				</tr>
				<tr>
					<td>余额提醒值：</td>
					<td><input type="text" value="" name="balanceReminder"
						id="balanceReminder" width="100px;" /></td>
					<td>元</td>
				</tr>
				<tr>
					<td>冻结金额：</td>
					<td><input type="text" value="" name="creditTopBalance"
						id="creditTopBalance" width="100px;" /></td>
					<td>元<font class="text_red auth_create">(冻结金额从余额中减除;恢复时输入0，则恢复)</font></td>
				</tr>
				<tr>
					<td>开启地市路由策略：</td>
					<td><input type="radio"  name="openAreaStrategy" value="1"
							   checked="checked">&nbsp;开启&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="radio"  name="openAreaStrategy" value="0">&nbsp;关闭</td>
					<td></td>
				</tr>
				<tr>
					<td>通知：</td>
					<td><input type="checkbox" name="topDownProduct" value="1">&nbsp;开启产品上下架通知</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><input type="radio" id="status" name="status" value="0"
						checked="checked">&nbsp;可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="radio" id="status" name="status" value="1">&nbsp;不可用</td>
					<td></td>
				</tr>
				<tr>
					<td>余额上限：</td>
					<td><input type="text" id="crestValue" name="crestValue"/></td>
				</tr>
			</table>
			<div
				style="BORDER-TOP: #008080 .12rem dashed; OVERFLOW: hidden; HEIGHT: 1.8px"></div>
			<br />
			<!-- 通用编辑 end -->

			<!-- 流量 start-->
			<div id="order_type_0">
				<table cellpadding="2">
					<%--<tr>--%>
						<%--<td>流量折扣：</td>--%>
						<%--<td>电信:<input id="liuliangDiscountDX"--%>
							<%--name="liuliangDiscountDX" style="width: 50px;" /> 移动:<input--%>
							<%--id="liuliangDiscountYD" name="liuliangDiscountYD"--%>
							<%--style="width: 50px;" /> 联通:<input id="liuliangDiscountLT"--%>
							<%--name="liuliangDiscountLT" style="width: 50px;" /><font--%>
							<%--class="text_red auth_create">(三位有效数字)</font>--%>
						<%--</td>--%>
					<%--</tr>--%>
					<tr>
						<td></td>
						<td><button type="button" class='className'
								onclick="openTreeProduct(0)">开通产品包</button></td>
					</tr>
				</table>
				<!-- ggxstart tree-->
				<div class="easyui-panel" style="padding: 5px">
					<ul id="tt_liuliang"></ul>
				</div>

				<div style="margin: 20px 0;">
					<a href="#" class="btn btn-primary"  onclick="edit(0)">保存分配</a>
				</div>
			</div>
			<!-- 流量 end-->

			<!-- 话费 start-->
			<div id="order_type_1">
				<table cellpadding="2">
					<!-- <tr>
						<td>话费折扣：</td>
						<td>电信:<input id="huafeiDiscountDX" name="huafeiDiscountDX"
							style="width: 50px;" /> 移动:<input id="huafeiDiscountYD"
							name="huafeiDiscountYD" style="width: 50px;" /> 联通:<input
							id="huafeiDiscountLT" name="huafeiDiscountLT"
							style="width: 50px;" /><font class="text_red auth_create">(三位有效数字)</font>
						</td>
					</tr> -->
					<tr>
						<td></td>
						<td><button type="button" class='className'
								onclick="openTreeProduct(1)">开通产品包</button></td>
					</tr>
				</table>
				<!-- ggxstart tree-->
				<div class="easyui-panel" style="padding: 5px">
					<ul id="tt_huafei"></ul>
				</div>

				<div style="margin: 20px 0;">
					<a href="#" class="btn btn-primary" onclick="edit(1)">保存分配</a>
				</div>
			</div>
			<!-- 话费 end-->
			<!-- 物业缴费 start-->
			<div id="order_type_2">
				<table cellpadding="2">
					<tr>
						<td>流量折扣：</td>
						<td>电信:<input id="liuliangDiscountDX"
							name="liuliangDiscountDX" style="width: 50px;" /> 移动:<input
							id="liuliangDiscountYD" name="liuliangDiscountYD"
							style="width: 50px;" /> 联通:<input id="liuliangDiscountLT"
							name="liuliangDiscountLT" style="width: 50px;" /><font
							class="text_red auth_create">(三位有效数字)</font>
						</td>
					</tr>
					<tr>
						<td></td>
						<td><button type="button" class='className'
								onclick="openTreeProduct(2)">开通产品包</button></td>
					</tr>
				</table>
				<div class="easyui-panel" style="padding: 5px">
					<ul id="tt_wuye"></ul>
				</div>

				<div style="margin: 20px 0;">
					<a href="#" class="btn btn-primary"onclick="edit(2)">保存分配</a>
				</div>
			</div>
			<!-- 物业缴费 end-->
			<!-- 加油卡 start-->
			<div id="order_type_3"></div>
			<!-- 加油卡 end-->

		</form>
	</div>
	<!-- 编辑合作商 end -->

	<!-- change discount start -->
	<div id="discountformation" style="margin-top: 10px;">
		<form id="discountDetail" method="post">
			<div class="munt">
				<div
					style="width: 100px; height: AUTO; float: left; background-color: #AFEEEE">
					省份<br />
					<ul id="province" class="clearfix default"
						style="padding: 0; margin: 0;"></ul>
				</div>
				<div
					style="width: 100px; height: AUTO; float: left; background-color: #5cbedd">
					运营商<br />
					<ul id="yysType" class="clearfix default"
						style="padding: 0; margin: 0;"></ul>
				</div>
				<div
					style="width: 100px; height: AUTO; float: left; background-color: #AFEEEE">
					<span id="span_flowType" style="display: none">包类型</span><br />
					<ul id="flowType" class="clearfix default"
						style="padding: 0; margin: 0;"></ul>
				</div>
				<div
					style="width: 400px; height: 2500px; float: left; background-color: #5cbedd; right: 0">

					面值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;折扣

					<br />
					<ul id="productInfo" class="clearfix default"
						style="padding: 0; margin: 0;"></ul>
					<br /> <br /> <br />
					<!-- 流量 -->
					<div id="liuliang_value" style="display: none">
						<table>
							<tr>
								<td><input name="flowValue" type="checkbox" value="5" />5M</td>
								<td><input name="flowValue" type="checkbox" value="10" />10M</td>
								<td><input name="flowValue" type="checkbox" value="20" />20M</td>
								<td><input name="flowValue" type="checkbox" value="30" />30M</td>
								<td><input name="flowValue" type="checkbox" value="50" />50M</td>
								<td><input name="flowValue" type="checkbox" value="70" />70M</td>
							</tr>
							<tr>
								<td><input name="flowValue" type="checkbox" value="100" />100M</td>
								<td><input name="flowValue" type="checkbox" value="150" />150M</td>
								<td><input name="flowValue" type="checkbox" value="200" />200M</td>
								<td><input name="flowValue" type="checkbox" value="300" />300M</td>
								<td><input name="flowValue" type="checkbox" value="400" />400M</td>
								<td><input name="flowValue" type="checkbox" value="500" />500M</td>
							</tr>
							<tr>
								<td><input name="flowValue" type="checkbox" value="600" />600M</td>
								<td><input name="flowValue" type="checkbox" value="700" />700M</td>
								<td><input name="flowValue" type="checkbox" value="1500" />1500M</td>
								<td><input name="flowValue" type="checkbox" value="1024" />1G</td>
								<td><input name="flowValue" type="checkbox" value="2048" />2G</td>
								<td><input name="flowValue" type="checkbox" value="3072" />3G</td>
							</tr>
							<tr>
								<td><input name="flowValue" type="checkbox" value="4096" />4G</td>
								<td><input name="flowValue" type="checkbox" value="5120" />5G</td>
								<td><input name="flowValue" type="checkbox" value="6144" />6G</td>
								<td><input name="flowValue" type="checkbox" value="10240" />10G</td>
								<td><input name="flowValue" type="checkbox" value="11264" />11G</td>
								<td><input name="flowValue" type="checkbox" value="12288" />12G</td>
							</tr>
						</table>

						－－－－－－－－－－－－－－－－－－－－－－－－<br /> 
							<input name="flowValue"
							type="checkbox" value="200003" />&nbsp;&nbsp;200M(24小时包)
							<input name="flowValue"
							type="checkbox" value="500003" />&nbsp;&nbsp;500M(24小时包)
							<input name="flowValue"
							type="checkbox" value="1024003" />&nbsp;&nbsp;1G(24小时包)
							</br>
							<input name="flowValue"
							type="checkbox" value="3072003" />&nbsp;&nbsp;3G(24小时包)
							<input name="flowValue"
							type="checkbox" value="500005" />&nbsp;&nbsp;500M(三日包)
							<input name="flowValue"
							type="checkbox" value="1024005" />&nbsp;&nbsp;1G(三日包)
							</br>
							<input name="flowValue"
							type="checkbox" value="500007" />&nbsp;&nbsp;500M(七日包)
							<input name="flowValue"
							type="checkbox" value="1024007" />&nbsp;&nbsp;1G(七日包)
							<input name="flowValue"
							type="checkbox" value="2048007" />&nbsp;&nbsp;2G(七日包)
							<br>
							<input name="flowValue"
							type="checkbox" value="3072007" />&nbsp;&nbsp;3G(七日包)
							<br>
							<input name="flowValue"
							type="checkbox" value="1024008" />&nbsp;&nbsp;1G(90日包)
							<input name="flowValue"
							type="checkbox" value="2048008" />&nbsp;&nbsp;2G(90日包)
							<input name="flowValue"
							type="checkbox" value="3072008" />&nbsp;&nbsp;3G(90日包)
							<br>
							<input name="flowValue"
							type="checkbox" value="1024009" />&nbsp;&nbsp;1G(180日包)
							<input name="flowValue"
							type="checkbox" value="2048009" />&nbsp;&nbsp;2G(180日包)
							<input name="flowValue"
							type="checkbox" value="3072009" />&nbsp;&nbsp;3G(180日包)
							<br>
							<input name="flowValue"
							type="checkbox" value="1024010" />&nbsp;&nbsp;1G(365日包)
							<input name="flowValue"
							type="checkbox" value="2048010" />&nbsp;&nbsp;2G(365日包)
							<input name="flowValue"
							type="checkbox" value="3072010" />&nbsp;&nbsp;3G(365日包)
					</div>
					<!-- 话费 -->
					<div id="huafei_value" style="display: none">
						<table>
							<tr>
								<td><input name="flowValue" type="checkbox" value="1" />1元</td>
								<td><input name="flowValue" type="checkbox" value="2" />2元</td>
								<td><input name="flowValue" type="checkbox" value="3" />3元</td>
								<td><input name="flowValue" type="checkbox" value="4" />4元</td>
								<td><input name="flowValue" type="checkbox" value="5" />5元</td>
							</tr>
							<tr>
								<td><input name="flowValue" type="checkbox" value="10" />10元</td>
								<td><input name="flowValue" type="checkbox" value="20" />20元</td>
								<td><input name="flowValue" type="checkbox" value="30" />30元</td>
								<td><input name="flowValue" type="checkbox" value="50" />50元</td>
								<td><input name="flowValue" type="checkbox" value="70" />70元</td>
							</tr>
							<tr>
								<td><input name="flowValue" type="checkbox" value="100" />100元</td>
								<td><input name="flowValue" type="checkbox" value="150" />150元</td>
								<td><input name="flowValue" type="checkbox" value="200" />200元</td>
								<td><input name="flowValue" type="checkbox" value="300" />300元</td>
								<td><input name="flowValue" type="checkbox" value="500" />500元</td>
							</tr>
						</table>
					</div>
					<br /> <br /> 折扣配置：<select class="sel" name="provinceDiscount"
						id="provinceDiscount" style="width: 70px; padding-left: 0px;">
						<option value="">-折扣-</option>
						<option value="100">1 折</option>
						<option value="101">1.01 折</option>
						<option value="102">1.02 折</option>
						<option value="103">1.03 折</option>
						<option value="104">1.04 折</option>
						<option value="105">1.05 折</option>
						<option value="106">1.06 折</option>
						<option value="107">1.07 折</option>
						<option value="108">1.08 折</option>
						<option value="109">1.09 折</option>
						<option value="110">1.10 折</option>
						<option value="111">1.11 折</option>
						<option value="112">1.12 折</option>
						<option value="113">1.13 折</option>
						<option value="114">1.14 折</option>
						<option value="115">1.15 折</option>
						<option value="116">1.16 折</option>
						<option value="117">1.17 折</option>
						<option value="118">1.18 折</option>
						<option value="119">1.19 折</option>
						<option value="120">1.20 折</option>
						<option value="121">1.21 折</option>
						<option value="122">1.22 折</option>
						<option value="123">1.23 折</option>
						<option value="124">1.24 折</option>
						<option value="125">1.25 折</option>
						<option value="126">1.26 折</option>
						<option value="127">1.27 折</option>
						<option value="128">1.28 折</option>
						<option value="129">1.29 折</option>
						<option value="130">1.30 折</option>
						<option value="131">1.31 折</option>
						<option value="132">1.32 折</option>
						<option value="133">1.33 折</option>
						<option value="134">1.34 折</option>
						<option value="135">1.35 折</option>
						<option value="136">1.36 折</option>
						<option value="137">1.37 折</option>
						<option value="138">1.38 折</option>
						<option value="139">1.39 折</option>
						<option value="140">1.40 折</option>
						<option value="141">1.41 折</option>
						<option value="142">1.42 折</option>
						<option value="143">1.43 折</option>
						<option value="144">1.44 折</option>
						<option value="145">1.45 折</option>
						<option value="146">1.46 折</option>
						<option value="147">1.47 折</option>
						<option value="148">1.48 折</option>
						<option value="149">1.49 折</option>
						<option value="150">1.50 折</option>
						<option value="151">1.51 折</option>
						<option value="152">1.52 折</option>
						<option value="153">1.53 折</option>
						<option value="154">1.54 折</option>
						<option value="155">1.55 折</option>
						<option value="156">1.56 折</option>
						<option value="157">1.57 折</option>
						<option value="158">1.58 折</option>
						<option value="159">1.59 折</option>
						<option value="160">1.60 折</option>
						<option value="161">1.61 折</option>
						<option value="162">1.62 折</option>
						<option value="163">1.63 折</option>
						<option value="164">1.64 折</option>
						<option value="165">1.65 折</option>
						<option value="166">1.66 折</option>
						<option value="167">1.67 折</option>
						<option value="168">1.68 折</option>
						<option value="169">1.69 折</option>
						<option value="170">1.70 折</option>
						<option value="171">1.71 折</option>
						<option value="172">1.72 折</option>
						<option value="173">1.73 折</option>
						<option value="174">1.74 折</option>
						<option value="175">1.75 折</option>
						<option value="176">1.76 折</option>
						<option value="177">1.77 折</option>
						<option value="178">1.78 折</option>
						<option value="179">1.79 折</option>
						<option value="180">1.80 折</option>
						<option value="181">1.81 折</option>
						<option value="182">1.82 折</option>
						<option value="183">1.83 折</option>
						<option value="184">1.84 折</option>
						<option value="185">1.85 折</option>
						<option value="186">1.86 折</option>
						<option value="187">1.87 折</option>
						<option value="188">1.88 折</option>
						<option value="189">1.89 折</option>
						<option value="190">1.90 折</option>
						<option value="191">1.91 折</option>
						<option value="192">1.92 折</option>
						<option value="193">1.93 折</option>
						<option value="194">1.94 折</option>
						<option value="195">1.95 折</option>
						<option value="196">1.96 折</option>
						<option value="197">1.97 折</option>
						<option value="198">1.98 折</option>
						<option value="199">1.99 折</option>
						<option value="200">2 折</option>
						<option value="201">2.01 折</option>
						<option value="202">2.02 折</option>
						<option value="203">2.03 折</option>
						<option value="204">2.04 折</option>
						<option value="205">2.05 折</option>
						<option value="206">2.06 折</option>
						<option value="207">2.07 折</option>
						<option value="208">2.08 折</option>
						<option value="209">2.09 折</option>
						<option value="210">2.10 折</option>
						<option value="211">2.11 折</option>
						<option value="212">2.12 折</option>
						<option value="213">2.13 折</option>
						<option value="214">2.14 折</option>
						<option value="215">2.15 折</option>
						<option value="216">2.16 折</option>
						<option value="217">2.17 折</option>
						<option value="218">2.18 折</option>
						<option value="219">2.19 折</option>
						<option value="220">2.20 折</option>
						<option value="221">2.21 折</option>
						<option value="222">2.22 折</option>
						<option value="223">2.23 折</option>
						<option value="224">2.24 折</option>
						<option value="225">2.25 折</option>
						<option value="226">2.26 折</option>
						<option value="227">2.27 折</option>
						<option value="228">2.28 折</option>
						<option value="229">2.29 折</option>
						<option value="230">2.30 折</option>
						<option value="231">2.31 折</option>
						<option value="232">2.32 折</option>
						<option value="233">2.33 折</option>
						<option value="234">2.34 折</option>
						<option value="235">2.35 折</option>
						<option value="236">2.36 折</option>
						<option value="237">2.37 折</option>
						<option value="238">2.38 折</option>
						<option value="239">2.39 折</option>
						<option value="240">2.40 折</option>
						<option value="241">2.41 折</option>
						<option value="242">2.42 折</option>
						<option value="243">2.43 折</option>
						<option value="244">2.44 折</option>
						<option value="245">2.45 折</option>
						<option value="246">2.46 折</option>
						<option value="247">2.47 折</option>
						<option value="248">2.48 折</option>
						<option value="249">2.49 折</option>
						<option value="250">2.50 折</option>
						<option value="251">2.51 折</option>
						<option value="252">2.52 折</option>
						<option value="253">2.53 折</option>
						<option value="254">2.54 折</option>
						<option value="255">2.55 折</option>
						<option value="256">2.56 折</option>
						<option value="257">2.57 折</option>
						<option value="258">2.58 折</option>
						<option value="259">2.59 折</option>
						<option value="260">2.60 折</option>
						<option value="261">2.61 折</option>
						<option value="262">2.62 折</option>
						<option value="263">2.63 折</option>
						<option value="264">2.64 折</option>
						<option value="265">2.65 折</option>
						<option value="266">2.66 折</option>
						<option value="267">2.67 折</option>
						<option value="268">2.68 折</option>
						<option value="269">2.69 折</option>
						<option value="270">2.70 折</option>
						<option value="271">2.71 折</option>
						<option value="272">2.72 折</option>
						<option value="273">2.73 折</option>
						<option value="274">2.74 折</option>
						<option value="275">2.75 折</option>
						<option value="276">2.76 折</option>
						<option value="277">2.77 折</option>
						<option value="278">2.78 折</option>
						<option value="279">2.79 折</option>
						<option value="280">2.80 折</option>
						<option value="281">2.81 折</option>
						<option value="282">2.82 折</option>
						<option value="283">2.83 折</option>
						<option value="284">2.84 折</option>
						<option value="285">2.85 折</option>
						<option value="286">2.86 折</option>
						<option value="287">2.87 折</option>
						<option value="288">2.88 折</option>
						<option value="289">2.89 折</option>
						<option value="290">2.90 折</option>
						<option value="291">2.91 折</option>
						<option value="292">2.92 折</option>
						<option value="293">2.93 折</option>
						<option value="294">2.94 折</option>
						<option value="295">2.95 折</option>
						<option value="296">2.96 折</option>
						<option value="297">2.97 折</option>
						<option value="298">2.98 折</option>
						<option value="299">2.99 折</option>
						<option value="300">3 折</option>
						<option value="301">3.01 折</option>
						<option value="302">3.02 折</option>
						<option value="303">3.03 折</option>
						<option value="304">3.04 折</option>
						<option value="305">3.05 折</option>
						<option value="306">3.06 折</option>
						<option value="307">3.07 折</option>
						<option value="308">3.08 折</option>
						<option value="309">3.09 折</option>
						<option value="310">3.10 折</option>
						<option value="311">3.11 折</option>
						<option value="312">3.12 折</option>
						<option value="313">3.13 折</option>
						<option value="314">3.14 折</option>
						<option value="315">3.15 折</option>
						<option value="316">3.16 折</option>
						<option value="317">3.17 折</option>
						<option value="318">3.18 折</option>
						<option value="319">3.19 折</option>
						<option value="320">3.20 折</option>
						<option value="321">3.21 折</option>
						<option value="322">3.22 折</option>
						<option value="323">3.23 折</option>
						<option value="324">3.24 折</option>
						<option value="325">3.25 折</option>
						<option value="326">3.26 折</option>
						<option value="327">3.27 折</option>
						<option value="328">3.28 折</option>
						<option value="329">3.29 折</option>
						<option value="330">3.30 折</option>
						<option value="331">3.31 折</option>
						<option value="332">3.32 折</option>
						<option value="333">3.33 折</option>
						<option value="334">3.34 折</option>
						<option value="335">3.35 折</option>
						<option value="336">3.36 折</option>
						<option value="337">3.37 折</option>
						<option value="338">3.38 折</option>
						<option value="339">3.39 折</option>
						<option value="340">3.40 折</option>
						<option value="341">3.41 折</option>
						<option value="342">3.42 折</option>
						<option value="343">3.43 折</option>
						<option value="344">3.44 折</option>
						<option value="345">3.45 折</option>
						<option value="346">3.46 折</option>
						<option value="347">3.47 折</option>
						<option value="348">3.48 折</option>
						<option value="349">3.49 折</option>
						<option value="350">3.50 折</option>
						<option value="351">3.51 折</option>
						<option value="352">3.52 折</option>
						<option value="353">3.53 折</option>
						<option value="354">3.54 折</option>
						<option value="355">3.55 折</option>
						<option value="356">3.56 折</option>
						<option value="357">3.57 折</option>
						<option value="358">3.58 折</option>
						<option value="359">3.59 折</option>
						<option value="360">3.60 折</option>
						<option value="361">3.61 折</option>
						<option value="362">3.62 折</option>
						<option value="363">3.63 折</option>
						<option value="364">3.64 折</option>
						<option value="365">3.65 折</option>
						<option value="366">3.66 折</option>
						<option value="367">3.67 折</option>
						<option value="368">3.68 折</option>
						<option value="369">3.69 折</option>
						<option value="370">3.70 折</option>
						<option value="371">3.71 折</option>
						<option value="372">3.72 折</option>
						<option value="373">3.73 折</option>
						<option value="374">3.74 折</option>
						<option value="375">3.75 折</option>
						<option value="376">3.76 折</option>
						<option value="377">3.77 折</option>
						<option value="378">3.78 折</option>
						<option value="379">3.79 折</option>
						<option value="380">3.80 折</option>
						<option value="381">3.81 折</option>
						<option value="382">3.82 折</option>
						<option value="383">3.83 折</option>
						<option value="384">3.84 折</option>
						<option value="385">3.85 折</option>
						<option value="386">3.86 折</option>
						<option value="387">3.87 折</option>
						<option value="388">3.88 折</option>
						<option value="389">3.89 折</option>
						<option value="390">3.90 折</option>
						<option value="391">3.91 折</option>
						<option value="392">3.92 折</option>
						<option value="393">3.93 折</option>
						<option value="394">3.94 折</option>
						<option value="395">3.95 折</option>
						<option value="396">3.96 折</option>
						<option value="397">3.97 折</option>
						<option value="398">3.98 折</option>
						<option value="399">3.99 折</option>
						<option value="400">4 折</option>
						<option value="401">4.01 折</option>
						<option value="402">4.02 折</option>
						<option value="403">4.03 折</option>
						<option value="404">4.04 折</option>
						<option value="405">4.05 折</option>
						<option value="406">4.06 折</option>
						<option value="407">4.07 折</option>
						<option value="408">4.08 折</option>
						<option value="409">4.09 折</option>
						<option value="410">4.10 折</option>
						<option value="411">4.11 折</option>
						<option value="412">4.12 折</option>
						<option value="413">4.13 折</option>
						<option value="414">4.14 折</option>
						<option value="415">4.15 折</option>
						<option value="416">4.16 折</option>
						<option value="417">4.17 折</option>
						<option value="418">4.18 折</option>
						<option value="419">4.19 折</option>
						<option value="420">4.20 折</option>
						<option value="421">4.21 折</option>
						<option value="422">4.22 折</option>
						<option value="423">4.23 折</option>
						<option value="424">4.24 折</option>
						<option value="425">4.25 折</option>
						<option value="426">4.26 折</option>
						<option value="427">4.27 折</option>
						<option value="428">4.28 折</option>
						<option value="429">4.29 折</option>
						<option value="430">4.30 折</option>
						<option value="431">4.31 折</option>
						<option value="432">4.32 折</option>
						<option value="433">4.33 折</option>
						<option value="434">4.34 折</option>
						<option value="435">4.35 折</option>
						<option value="436">4.36 折</option>
						<option value="437">4.37 折</option>
						<option value="438">4.38 折</option>
						<option value="439">4.39 折</option>
						<option value="440">4.40 折</option>
						<option value="441">4.41 折</option>
						<option value="442">4.42 折</option>
						<option value="443">4.43 折</option>
						<option value="444">4.44 折</option>
						<option value="445">4.45 折</option>
						<option value="446">4.46 折</option>
						<option value="447">4.47 折</option>
						<option value="448">4.48 折</option>
						<option value="449">4.49 折</option>
						<option value="450">4.50 折</option>
						<option value="451">4.51 折</option>
						<option value="452">4.52 折</option>
						<option value="453">4.53 折</option>
						<option value="454">4.54 折</option>
						<option value="455">4.55 折</option>
						<option value="456">4.56 折</option>
						<option value="457">4.57 折</option>
						<option value="458">4.58 折</option>
						<option value="459">4.59 折</option>
						<option value="460">4.60 折</option>
						<option value="461">4.61 折</option>
						<option value="462">4.62 折</option>
						<option value="463">4.63 折</option>
						<option value="464">4.64 折</option>
						<option value="465">4.65 折</option>
						<option value="466">4.66 折</option>
						<option value="467">4.67 折</option>
						<option value="468">4.68 折</option>
						<option value="469">4.69 折</option>
						<option value="470">4.70 折</option>
						<option value="471">4.71 折</option>
						<option value="472">4.72 折</option>
						<option value="473">4.73 折</option>
						<option value="474">4.74 折</option>
						<option value="475">4.75 折</option>
						<option value="476">4.76 折</option>
						<option value="477">4.77 折</option>
						<option value="478">4.78 折</option>
						<option value="479">4.79 折</option>
						<option value="480">4.80 折</option>
						<option value="481">4.81 折</option>
						<option value="482">4.82 折</option>
						<option value="483">4.83 折</option>
						<option value="484">4.84 折</option>
						<option value="485">4.85 折</option>
						<option value="486">4.86 折</option>
						<option value="487">4.87 折</option>
						<option value="488">4.88 折</option>
						<option value="489">4.89 折</option>
						<option value="490">4.90 折</option>
						<option value="491">4.91 折</option>
						<option value="492">4.92 折</option>
						<option value="493">4.93 折</option>
						<option value="494">4.94 折</option>
						<option value="495">4.95 折</option>
						<option value="496">4.96 折</option>
						<option value="497">4.97 折</option>
						<option value="498">4.98 折</option>
						<option value="499">4.99 折</option>
						<option value="500">5 折</option>
						<option value="501">5.01 折</option>
						<option value="502">5.02 折</option>
						<option value="503">5.03 折</option>
						<option value="504">5.04 折</option>
						<option value="505">5.05 折</option>
						<option value="506">5.06 折</option>
						<option value="507">5.07 折</option>
						<option value="508">5.08 折</option>
						<option value="509">5.09 折</option>
						<option value="510">5.1 折</option>
						<option value="511">5.11 折</option>
						<option value="512">5.12 折</option>
						<option value="513">5.13 折</option>
						<option value="514">5.14 折</option>
						<option value="515">5.15 折</option>
						<option value="516">5.16 折</option>
						<option value="517">5.17 折</option>
						<option value="518">5.18 折</option>
						<option value="519">5.19 折</option>
						<option value="520">5.2 折</option>
						<option value="521">5.21 折</option>
						<option value="522">5.22 折</option>
						<option value="523">5.23 折</option>
						<option value="524">5.24 折</option>
						<option value="525">5.25 折</option>
						<option value="526">5.26 折</option>
						<option value="527">5.27 折</option>
						<option value="528">5.28 折</option>
						<option value="529">5.29 折</option>
						<option value="530">5.3 折</option>
						<option value="531">5.31 折</option>
						<option value="532">5.32 折</option>
						<option value="533">5.33 折</option>
						<option value="534">5.34 折</option>
						<option value="535">5.35 折</option>
						<option value="536">5.36 折</option>
						<option value="537">5.37 折</option>
						<option value="538">5.38 折</option>
						<option value="539">5.39 折</option>
						<option value="540">5.4 折</option>
						<option value="541">5.41 折</option>
						<option value="542">5.42 折</option>
						<option value="543">5.43 折</option>
						<option value="544">5.44 折</option>
						<option value="545">5.45 折</option>
						<option value="546">5.46 折</option>
						<option value="547">5.47 折</option>
						<option value="548">5.48 折</option>
						<option value="549">5.49 折</option>
						<option value="550">5.5 折</option>
						<option value="551">5.51 折</option>
						<option value="552">5.52 折</option>
						<option value="553">5.53 折</option>
						<option value="554">5.54 折</option>
						<option value="555">5.55 折</option>
						<option value="556">5.56 折</option>
						<option value="557">5.57 折</option>
						<option value="558">5.58 折</option>
						<option value="559">5.59 折</option>
						<option value="560">5.6 折</option>
						<option value="561">5.61 折</option>
						<option value="562">5.62 折</option>
						<option value="563">5.63 折</option>
						<option value="564">5.64 折</option>
						<option value="565">5.65 折</option>
						<option value="566">5.66 折</option>
						<option value="567">5.67 折</option>
						<option value="568">5.68 折</option>
						<option value="569">5.69 折</option>
						<option value="570">5.7 折</option>
						<option value="571">5.71 折</option>
						<option value="572">5.72 折</option>
						<option value="573">5.73 折</option>
						<option value="574">5.74 折</option>
						<option value="575">5.75 折</option>
						<option value="576">5.76 折</option>
						<option value="577">5.77 折</option>
						<option value="578">5.78 折</option>
						<option value="579">5.79 折</option>
						<option value="580">5.8 折</option>
						<option value="581">5.81 折</option>
						<option value="582">5.82 折</option>
						<option value="583">5.83 折</option>
						<option value="584">5.84 折</option>
						<option value="585">5.85 折</option>
						<option value="586">5.86 折</option>
						<option value="587">5.87 折</option>
						<option value="588">5.88 折</option>
						<option value="589">5.89 折</option>
						<option value="590">5.9 折</option>
						<option value="591">5.91 折</option>
						<option value="592">5.92 折</option>
						<option value="593">5.93 折</option>
						<option value="594">5.94 折</option>
						<option value="595">5.95 折</option>
						<option value="596">5.96 折</option>
						<option value="597">5.97 折</option>
						<option value="598">5.98 折</option>
						<option value="599">5.99 折</option>
						<option value="600">6 折</option>
						<option value="601">6.01 折</option>
						<option value="602">6.02 折</option>
						<option value="603">6.03 折</option>
						<option value="604">6.04 折</option>
						<option value="605">6.05 折</option>
						<option value="606">6.06 折</option>
						<option value="607">6.07 折</option>
						<option value="608">6.08 折</option>
						<option value="609">6.09 折</option>
						<option value="610">6.1 折</option>
						<option value="611">6.11 折</option>
						<option value="612">6.12 折</option>
						<option value="613">6.13 折</option>
						<option value="614">6.14 折</option>
						<option value="615">6.15 折</option>
						<option value="616">6.16 折</option>
						<option value="617">6.17 折</option>
						<option value="618">6.18 折</option>
						<option value="619">6.19 折</option>
						<option value="620">6.2 折</option>
						<option value="621">6.21 折</option>
						<option value="622">6.22 折</option>
						<option value="623">6.23 折</option>
						<option value="624">6.24 折</option>
						<option value="625">6.25 折</option>
						<option value="626">6.26 折</option>
						<option value="627">6.27 折</option>
						<option value="628">6.28 折</option>
						<option value="629">6.29 折</option>
						<option value="630">6.3 折</option>
						<option value="631">6.31 折</option>
						<option value="632">6.32 折</option>
						<option value="633">6.33 折</option>
						<option value="634">6.34 折</option>
						<option value="635">6.35 折</option>
						<option value="636">6.36 折</option>
						<option value="637">6.37 折</option>
						<option value="638">6.38 折</option>
						<option value="639">6.39 折</option>
						<option value="640">6.4 折</option>
						<option value="641">6.41 折</option>
						<option value="642">6.42 折</option>
						<option value="643">6.43 折</option>
						<option value="644">6.44 折</option>
						<option value="645">6.45 折</option>
						<option value="646">6.46 折</option>
						<option value="647">6.47 折</option>
						<option value="648">6.48 折</option>
						<option value="649">6.49 折</option>
						<option value="650">6.5 折</option>
						<option value="651">6.51 折</option>
						<option value="652">6.52 折</option>
						<option value="653">6.53 折</option>
						<option value="654">6.54 折</option>
						<option value="655">6.55 折</option>
						<option value="656">6.56 折</option>
						<option value="657">6.57 折</option>
						<option value="658">6.58 折</option>
						<option value="659">6.59 折</option>
						<option value="660">6.6 折</option>
						<option value="661">6.61 折</option>
						<option value="662">6.62 折</option>
						<option value="663">6.63 折</option>
						<option value="664">6.64 折</option>
						<option value="665">6.65 折</option>
						<option value="666">6.66 折</option>
						<option value="667">6.67 折</option>
						<option value="668">6.68 折</option>
						<option value="669">6.69 折</option>
						<option value="670">6.7 折</option>
						<option value="671">6.71 折</option>
						<option value="672">6.72 折</option>
						<option value="673">6.73 折</option>
						<option value="674">6.74 折</option>
						<option value="675">6.75 折</option>
						<option value="676">6.76 折</option>
						<option value="677">6.77 折</option>
						<option value="678">6.78 折</option>
						<option value="679">6.79 折</option>
						<option value="680">6.8 折</option>
						<option value="681">6.81 折</option>
						<option value="682">6.82 折</option>
						<option value="683">6.83 折</option>
						<option value="684">6.84 折</option>
						<option value="685">6.85 折</option>
						<option value="686">6.86 折</option>
						<option value="687">6.87 折</option>
						<option value="688">6.88 折</option>
						<option value="689">6.89 折</option>
						<option value="690">6.9 折</option>
						<option value="691">6.91 折</option>
						<option value="692">6.92 折</option>
						<option value="693">6.93 折</option>
						<option value="694">6.94 折</option>
						<option value="695">6.95 折</option>
						<option value="696">6.96 折</option>
						<option value="697">6.97 折</option>
						<option value="698">6.98 折</option>
						<option value="699">6.99 折</option>
						<option value="700">7 折</option>
						<option value="701">7.01 折</option>
						<option value="702">7.02 折</option>
						<option value="703">7.03 折</option>
						<option value="704">7.04 折</option>
						<option value="705">7.05 折</option>
						<option value="706">7.06 折</option>
						<option value="707">7.07 折</option>
						<option value="708">7.08 折</option>
						<option value="709">7.09 折</option>
						<option value="710">7.1 折</option>
						<option value="711">7.11 折</option>
						<option value="712">7.12 折</option>
						<option value="713">7.13 折</option>
						<option value="714">7.14 折</option>
						<option value="715">7.15 折</option>
						<option value="716">7.16 折</option>
						<option value="717">7.17 折</option>
						<option value="718">7.18 折</option>
						<option value="719">7.19 折</option>
						<option value="720">7.2 折</option>
						<option value="721">7.21 折</option>
						<option value="722">7.22 折</option>
						<option value="723">7.23 折</option>
						<option value="724">7.24 折</option>
						<option value="725">7.25 折</option>
						<option value="726">7.26 折</option>
						<option value="727">7.27 折</option>
						<option value="728">7.28 折</option>
						<option value="729">7.29 折</option>
						<option value="730">7.3 折</option>
						<option value="731">7.31 折</option>
						<option value="732">7.32 折</option>
						<option value="733">7.33 折</option>
						<option value="734">7.34 折</option>
						<option value="735">7.35 折</option>
						<option value="736">7.36 折</option>
						<option value="737">7.37 折</option>
						<option value="738">7.38 折</option>
						<option value="739">7.39 折</option>
						<option value="740">7.4 折</option>
						<option value="741">7.41 折</option>
						<option value="742">7.42 折</option>
						<option value="743">7.43 折</option>
						<option value="744">7.44 折</option>
						<option value="745">7.45 折</option>
						<option value="746">7.46 折</option>
						<option value="747">7.47 折</option>
						<option value="748">7.48 折</option>
						<option value="749">7.49 折</option>
						<option value="750">7.5 折</option>
						<option value="751">7.51 折</option>
						<option value="752">7.52 折</option>
						<option value="753">7.53 折</option>
						<option value="754">7.54 折</option>
						<option value="755">7.55 折</option>
						<option value="756">7.56 折</option>
						<option value="757">7.57 折</option>
						<option value="758">7.58 折</option>
						<option value="759">7.59 折</option>
						<option value="760">7.6 折</option>
						<option value="761">7.61 折</option>
						<option value="762">7.62 折</option>
						<option value="763">7.63 折</option>
						<option value="764">7.64 折</option>
						<option value="765">7.65 折</option>
						<option value="766">7.66 折</option>
						<option value="767">7.67 折</option>
						<option value="768">7.68 折</option>
						<option value="769">7.69 折</option>
						<option value="770">7.7 折</option>
						<option value="771">7.71 折</option>
						<option value="772">7.72 折</option>
						<option value="773">7.73 折</option>
						<option value="774">7.74 折</option>
						<option value="775">7.75 折</option>
						<option value="776">7.76 折</option>
						<option value="777">7.77 折</option>
						<option value="778">7.78 折</option>
						<option value="779">7.79 折</option>
						<option value="780">7.8 折</option>
						<option value="781">7.81 折</option>
						<option value="782">7.82 折</option>
						<option value="783">7.83 折</option>
						<option value="784">7.84 折</option>
						<option value="785">7.85 折</option>
						<option value="786">7.86 折</option>
						<option value="787">7.87 折</option>
						<option value="788">7.88 折</option>
						<option value="789">7.89 折</option>
						<option value="790">7.9 折</option>
						<option value="791">7.91 折</option>
						<option value="792">7.92 折</option>
						<option value="793">7.93 折</option>
						<option value="794">7.94 折</option>
						<option value="795">7.95 折</option>
						<option value="796">7.96 折</option>
						<option value="797">7.97 折</option>
						<option value="798">7.98 折</option>
						<option value="799">7.99 折</option>
						<option value="800">8 折</option>
						<option value="801">8.01 折</option>
						<option value="802">8.02 折</option>
						<option value="803">8.03 折</option>
						<option value="804">8.04 折</option>
						<option value="805">8.05 折</option>
						<option value="806">8.06 折</option>
						<option value="807">8.07 折</option>
						<option value="808">8.08 折</option>
						<option value="809">8.09 折</option>
						<option value="810">8.1 折</option>
						<option value="811">8.11 折</option>
						<option value="812">8.12 折</option>
						<option value="813">8.13 折</option>
						<option value="814">8.14 折</option>
						<option value="815">8.15 折</option>
						<option value="816">8.16 折</option>
						<option value="817">8.17 折</option>
						<option value="818">8.18 折</option>
						<option value="819">8.19 折</option>
						<option value="820">8.2 折</option>
						<option value="821">8.21 折</option>
						<option value="822">8.22 折</option>
						<option value="823">8.23 折</option>
						<option value="824">8.24 折</option>
						<option value="825">8.25 折</option>
						<option value="826">8.26 折</option>
						<option value="827">8.27 折</option>
						<option value="828">8.28 折</option>
						<option value="829">8.29 折</option>
						<option value="830">8.3 折</option>
						<option value="831">8.31 折</option>
						<option value="832">8.32 折</option>
						<option value="833">8.33 折</option>
						<option value="834">8.34 折</option>
						<option value="835">8.35 折</option>
						<option value="836">8.36 折</option>
						<option value="837">8.37 折</option>
						<option value="838">8.38 折</option>
						<option value="839">8.39 折</option>
						<option value="840">8.4 折</option>
						<option value="841">8.41 折</option>
						<option value="842">8.42 折</option>
						<option value="843">8.43 折</option>
						<option value="844">8.44 折</option>
						<option value="845">8.45 折</option>
						<option value="846">8.46 折</option>
						<option value="847">8.47 折</option>
						<option value="848">8.48 折</option>
						<option value="849">8.49 折</option>
						<option value="850">8.5 折</option>
						<option value="851">8.51 折</option>
						<option value="852">8.52 折</option>
						<option value="853">8.53 折</option>
						<option value="854">8.54 折</option>
						<option value="855">8.55 折</option>
						<option value="856">8.56 折</option>
						<option value="857">8.57 折</option>
						<option value="858">8.58 折</option>
						<option value="859">8.59 折</option>
						<option value="860">8.6 折</option>
						<option value="861">8.61 折</option>
						<option value="862">8.62 折</option>
						<option value="863">8.63 折</option>
						<option value="864">8.64 折</option>
						<option value="865">8.65 折</option>
						<option value="866">8.66 折</option>
						<option value="867">8.67 折</option>
						<option value="868">8.68 折</option>
						<option value="869">8.69 折</option>
						<option value="870">8.7 折</option>
						<option value="871">8.71 折</option>
						<option value="872">8.72 折</option>
						<option value="873">8.73 折</option>
						<option value="874">8.74 折</option>
						<option value="875">8.75 折</option>
						<option value="876">8.76 折</option>
						<option value="877">8.77 折</option>
						<option value="878">8.78 折</option>
						<option value="879">8.79 折</option>
						<option value="880">8.8 折</option>
						<option value="881">8.81 折</option>
						<option value="882">8.82 折</option>
						<option value="883">8.83 折</option>
						<option value="884">8.84 折</option>
						<option value="885">8.85 折</option>
						<option value="886">8.86 折</option>
						<option value="887">8.87 折</option>
						<option value="888">8.88 折</option>
						<option value="889">8.89 折</option>
						<option value="890">8.9 折</option>
						<option value="891">8.91 折</option>
						<option value="892">8.92 折</option>
						<option value="893">8.93 折</option>
						<option value="894">8.94 折</option>
						<option value="895">8.95 折</option>
						<option value="896">8.96 折</option>
						<option value="897">8.97 折</option>
						<option value="898">8.98 折</option>
						<option value="899">8.99 折</option>
						<option value="900">9 折</option>
						<option value="901">9.01 折</option>
						<option value="902">9.02 折</option>
						<option value="903">9.03 折</option>
						<option value="904">9.04 折</option>
						<option value="905">9.05 折</option>
						<option value="906">9.06 折</option>
						<option value="907">9.07 折</option>
						<option value="908">9.08 折</option>
						<option value="909">9.09 折</option>
						<option value="910">9.1 折</option>
						<option value="911">9.11 折</option>
						<option value="912">9.12 折</option>
						<option value="913">9.13 折</option>
						<option value="914">9.14 折</option>
						<option value="915">9.15 折</option>
						<option value="916">9.16 折</option>
						<option value="917">9.17 折</option>
						<option value="918">9.18 折</option>
						<option value="919">9.19 折</option>
						<option value="920">9.2 折</option>
						<option value="921">9.21 折</option>
						<option value="922">9.22 折</option>
						<option value="923">9.23 折</option>
						<option value="924">9.24 折</option>
						<option value="925">9.25 折</option>
						<option value="926">9.26 折</option>
						<option value="927">9.27 折</option>
						<option value="928">9.28 折</option>
						<option value="929">9.29 折</option>
						<option value="930">9.3 折</option>
						<option value="931">9.31 折</option>
						<option value="932">9.32 折</option>
						<option value="933">9.33 折</option>
						<option value="934">9.34 折</option>
						<option value="935">9.35 折</option>
						<option value="936">9.36 折</option>
						<option value="937">9.37 折</option>
						<option value="938">9.38 折</option>
						<option value="939">9.39 折</option>
						<option value="940">9.4 折</option>
						<option value="941">9.41 折</option>
						<option value="942">9.42 折</option>
						<option value="943">9.43 折</option>
						<option value="944">9.44 折</option>
						<option value="945">9.45 折</option>
						<option value="946">9.46 折</option>
						<option value="947">9.47 折</option>
						<option value="948">9.48 折</option>
						<option value="949">9.49 折</option>
						<option value="950">9.5 折</option>
						<option value="951">9.51 折</option>
						<option value="952">9.52 折</option>
						<option value="953">9.53 折</option>
						<option value="954">9.54 折</option>
						<option value="955">9.55 折</option>
						<option value="956">9.56 折</option>
						<option value="957">9.57 折</option>
						<option value="958">9.58 折</option>
						<option value="959">9.59 折</option>
						<option value="960">9.6 折</option>
						<option value="961">9.61 折</option>
						<option value="962">9.62 折</option>
						<option value="963">9.63 折</option>
						<option value="964">9.64 折</option>
						<option value="965">9.65 折</option>
						<option value="966">9.66 折</option>
						<option value="967">9.67 折</option>
						<option value="968">9.68 折</option>
						<option value="969">9.69 折</option>
						<option value="970">9.7 折</option>
						<option value="971">9.71 折</option>
						<option value="972">9.72 折</option>
						<option value="973">9.73 折</option>
						<option value="974">9.74 折</option>
						<option value="975">9.75 折</option>
						<option value="976">9.76 折</option>
						<option value="977">9.77 折</option>
						<option value="978">9.78 折</option>
						<option value="979">9.79 折</option>
						<option value="980">9.8 折</option>
						<option value="981">9.81 折</option>
						<option value="982">9.82 折</option>
						<option value="983">9.83 折</option>
						<option value="984">9.84 折</option>
						<option value="985">9.85 折</option>
						<option value="986">9.86 折</option>
						<option value="987">9.87 折</option>
						<option value="988">9.88 折</option>
						<option value="989">9.89 折</option>
						<option value="990">9.9 折</option>
						<option value="991">9.91 折</option>
						<option value="992">9.92 折</option>
						<option value="993">9.93 折</option>
						<option value="994">9.94 折</option>
						<option value="995">9.95 折</option>
						<option value="996">9.96 折</option>
						<option value="997">9.97 折</option>
						<option value="998">9.98 折</option>
						<option value="999">9.99 折</option>
						<option value="1000">10 折</option>
					</select>
					<div style="margin: 20px 0; text-align: center;">
						<a href="#" class="btn btn-primary"onclick="saveDiscount()">保存</a>
					</div>
					<div id="liuliang_demo" style="display: none">
						<h5 style="color: red">&nbsp;* 日包规格参考</h5>
						<table>
							<th>&nbsp;大小（M）&nbsp;</th>
							<th>&nbsp;有效期（小时）&nbsp;</th>
							<th>&nbsp;对应编码&nbsp;</th>
							<tr>
								<td>1024</td>
								<td>6</td>
								<td>1024001</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>12</td>
								<td>1024002</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>24</td>
								<td>1024003</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>48</td>
								<td>1024004</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>72</td>
								<td>1024005</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>120</td>
								<td>1024006</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>168</td>
								<td>1024007</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>90天</td>
								<td>1024008</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>180天</td>
								<td>1024009</td>
							</tr>
							<tr>
								<td>1024</td>
								<td>365天</td>
								<td>1024010</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- charge discount end -->
</body>
</html>