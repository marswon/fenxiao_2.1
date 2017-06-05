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
	var cityName;
	var lastOpid;
	var lastOproName;
	var lastStatus;
	var lastFlowType;
	var lastSize;
	var lastChannelDiscount;
	var lastProName;
	var lastParentId;
	var open_proId;
	var map = {
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
	jQuery(function($) {
		$("#_productList")
				.datagrid(
						{
							url : '${ctx}/product/getProductListWithOutGas',
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
										title : '流量包名称',
										width : 250,
										align : "center"
									},
									{
										field : 'fxProductGroup',
										title : '所属产品组',
										width : 250,
										align : "center",
										formatter : function(val, row, index) {
											if (row.fxProductGroup) {
												return row.fxProductGroup.name;
											} else {
												return "";
											}
										}
									},
									{
										field : 'size',
										title : '流量包大小',
										width : 100,
										align : "center"
									},
									{
										field : 'flowType',
										title : '流量包类型',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.flowType == 0) {
												return "漫游";
											} else if (row.flowType == 1) {
												return "本地";
											} else if (row.flowType == -1) {
												return "其它";
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
											} else {
												return '联通';
											}
										}
									},
									{
										field : 'provinceId',
										title : '省份',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											return showProvince(row.provinceId);
										}
									},
									{
										field : 'businessType',
										title : '业务类型',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.businessType == 0) {
												return '<span >流量</span>';
											} else {
												return '<span >话费</span>';
											}
										}
									},
									{
										field : 'selfProductType',
										title : '营业类型',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.selfProductType == 0) {
												return '<span >非自营</span>';
											} else {
												return '<span >自营</span>';
											}
										}
									},
									{
										field : 'status',
										title : '状态',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.status == 0) {
												return '<span id="ysj'
														+ row.id
														+ '" style="color:#fff;background:green" onclick="updateStatus(\''
														+ row.id
														+ '\',1)">已上架</span>';
											} else {
												return '<span id="yxj'
														+ row.id
														+ '" style="color:#fff;background:red" onclick="updateStatus(\''
														+ row.id
														+ '\',0)">已下架</span>';
											}
										}
									},
									{
										field : 'userAccountstatus',
										title : '操作',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {//
											return '<input type="button" class="btn btn-info btn-small"  value="编辑" onclick="add_product(\''
													+ row.id
													+ '\',\''
													+ row.businessType
													+ '\');"/>';
										}
									}, {
										field : 'adminId',
										title : 'adminId',
										width : 150,
										align : "center",
										hidden : true,
										formatter : function(val, row, index) {
											if (row.adminId != 1) {
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

		$('#productParentId').combobox({
			editable : false,
			width : 150,
			panelHeight : 200,
		});
		$('#productParentId_huafei').combobox({
			editable : false,
			width : 150,
			panelHeight : 200,
		});
		$("#search_id").click(function() {
			var params = $('#_productList').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#_productList').datagrid('load');
		});
/* 		$('#webinformation1').dialog({
			title : "流量产品编辑",
			width : 950,
			height : 590,
			closed : true,
			cache : false,
			modal : true,
			center : true,
		}); */
		
		
		$('#webinformation1').dialog({
			iconCls:'icon-edit',
			title : "&ensp;流量产品编辑",
			width : 950,
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
			iconCls:'icon-edit',
			title : "&ensp;话费产品编辑",
			width : 950,
			height : 590,
			closed : true,
			cache : false,
			modal : true,
			center : true,
			resizable:true,
			minimizable:true,  
			maximizable:true,  
		});
		loadParentAllPacket();
	});

	function closeDf() {
		$('#dfxqDlg').dialog('close');
	}

	/**
	 *添加平台产品
	 **/
	function add_product(id, businessType) {
		if (businessType == 0) {
			add_liuliangProduct(id);
		} else if (businessType == 1) {
			add_huafeiProduct(id);
		}
	}

	function add_liuliangProduct(id) {
		if (id == 0) {
		} else {
			//清除内容
			$(':input', '#webinformation1').not(
					':button, :submit, :reset, :hidden').val('').removeAttr(
					'checked').removeAttr('selected');
			$(':input', '#xxDgTb').not(':button, :submit, :reset, :hidden')
					.val('').removeAttr('checked').removeAttr('selected');
			open_proId = id;
			//获取一个平台产品的详细信息
			$
					.getJSON(
							"${ctx}/product/findOneProduct",
							{
								proId : id
							},
							function(data) {
								if (data.resultCode == -1) {
									$.messager.alert('消息', data.resultMsg);
								} else {
									//设置默认流量包名称
									$("#savename").val(data.content.name);
									//设置默认流量包价格
									$("#saveprice").val(
											data.content.price / 1000);
									//选中默认产品组
									$("#productParentId").combobox('select',
											data.content.fxProductGroup.id);
									//设置默认流量包大小
									$("#_opflowSize").val(data.content.size);
									//设置默认选中省份
									$("#provinceId_liuliang").val(
											data.content.provinceId);
									//设置默认选中流量类型
									$("#flowTypeByProvinceId_liuliang").val(
											data.content.flowType);
									//设置默认选中运营商
									$("#yysId_liuliang").val(
											data.content.yysTypeId);
									$("#selfProductType_liuliang").val(
											data.content.selfProductType);
									//
									var name = data.content.name;
									var size = data.content.size;
									var channelDiscount = data.content.channelDiscount;
									var _provinceId = data.content.provinceId;
									var flowType = data.content.flowType;
									var lastFlowTypeStr;
									if (flowType == 0) {
										lastFlowTypeStr = "漫游";
									} else {
										lastFlowTypeStr = "本地";
									}
									//获取一个平台产品配置的所有和供应商产品有关的数据
									$
											.getJSON(
													"${ctx}/productOperatorProduct/findPopByProId",
													{
														id : id
													},
													function(data) {
														if (data.resultCode == -1) {
															$.messager
																	.alert(
																			'消息',
																			data.resultMsg);
														} else {
															$("#oproTable")
																	.empty();
															$("#oproTable")
																	.append(
																			'<thead style="color: green"><tr><th width="10%">城市</th><th width="15%">平台流量包名称</th><th width="15%">渠道流量包名称</th><th width="10%">规格</th><th width="10%">流量类型</th><th width="8%">价格</th><th width="8%">折扣</th><th width="8%">状态</th><th width="8%">操作</th></tr></thead>');
															if (data.content.length != 0) {
																$
																		.each(
																				map,
																				function(
																						key,
																						values) {
																					$(
																							"#p_"
																									+ key)
																							.show();

																				});
																$
																		.each(
																				data.content,
																				function(
																						index,
																						element) {
																					if(element.fxOperatorsProduct.flowType==0){
																						lastFlowTypeStr="漫游";
																					}else if(element.fxOperatorsProduct.flowType==1){
																						lastFlowTypeStr = "本地";
																					}
																					$(
																							"#oproTable")
																							.append(
																									"<tr>"
																											+ "<td style=\"display:none\">"
																											+ element.fxOperatorsProduct.id
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ element.provinceId
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ data.content.name
																											+ "</td>"
																											+ "<td style=\"display:none\">¥"
																											+ data.content.price / 1000
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ element.fxOperatorsProduct.flowType
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ element.fxOperatorsProduct.yysTypeId
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ data.content.groupId
																											+ "</td><td>"
																											+ showProvince(element.provinceId)
																											+ "</td><td>"
																											+ name
																											+ "</td><td> "
																											+ element.fxOperatorsProduct.name
																											+ "</td><td>"
																											+ size
																											+ "</td><td>"
																											+ lastFlowTypeStr
																											+ "</td><td>¥"
																											+ element.fxOperatorsProduct.price
																											/ 1000
																											+ "</td><td>"
																											+ (element.fxOperatorsProduct.channelDiscount / 100)
																											+ "</td><td >"
																											+ element.fxOperatorsProduct.status
																											+ '</td><td><a href="javascript:;" class="easyui-linkbutton" onclick="delRow(this,\''
																											+ element.provinceId
																											+ '\');" color="red"'
																											+ '>删除</a></td></tr>');
																					//隐藏已有省份
																					$
																							.each(
																									map,
																									function(
																											key,
																											values) {
																										if (key == element.provinceId) {
																											$(
																													"#p_"
																															+ key)
																													.hide();
																											$(
																													"#p1_"
																															+ key)
																													.hide();
																										} else {

																										}

																									});
																				});
																if (_provinceId != "000") {
																	$
																			.each(
																					map,
																					function(
																							key,
																							values) {
																						$(
																								"#p_"
																										+ key)
																								.hide();
																						$(
																								"#p1_"
																										+ key)
																								.hide();
																					});
																}
															} else {
																if (_provinceId != "000") {
																	$
																			.each(
																					map,
																					function(
																							key,
																							values) {
																						$(
																								"#p_"
																										+ key)
																								.hide();
																						$(
																								"#p1_"
																										+ key)
																								.hide();
																					});

																	$(
																			"#p_"
																					+ _provinceId)
																			.show();
																}
															}
														}
													});
								}
							});
		}
		$('#webinformation1').dialog('open');
		//流量
		operators_product(0);
		addEnventSelect(0);
		loadParentPacket();
	}
	/* 添加话费产品start  */
	/**
	 *添加平台产品
	 **/
	function add_huafeiProduct(id) {
		if (id == 0) {
		} else {
			//清除内容
			$(':input', '#webinformation2').not(
					':button, :submit, :reset, :hidden').val('').removeAttr(
					'checked').removeAttr('selected');
			$(':input', '#xxDgTb').not(':button, :submit, :reset, :hidden')
					.val('').removeAttr('checked').removeAttr('selected');
			open_proId = id;
			//获取一个平台产品的详细信息
			$
					.getJSON(
							"${ctx}/product/findOneProduct",
							{
								proId : id
							},
							function(data) {
								if (data.resultCode == -1) {
									$.messager.alert('消息', data.resultMsg);
								} else {
									//设置默认流量包名称
									$("#savename_huafei")
											.val(data.content.name);
									//设置默认流量包价格
									$("#saveprice_huafei").val(
											data.content.price / 1000);
									//选中默认产品组
									$("#productParentId_huafei").combobox(
											'select',
											data.content.fxProductGroup.id);
									//设置默认流量包大小
									$("#_opflowSize_huafei").val(
											data.content.size);
									//设置默认选中省份
									$("#provinceId_huafei").val(
											data.content.provinceId);
									//设置默认选中流量类型
									$("#flowTypeByProvinceId_huafei").val(
											data.content.flowType);
									//设置默认选中运营商
									$("#yysId_huafei").val(
											data.content.yysTypeId);
									$("#selfProductType_huafei").val(
											data.content.selfProductType);
									//
									var name = data.content.name;
									var size = data.content.size;
									var channelDiscount = data.content.channelDiscount;
									var _provinceId = data.content.provinceId;
									//获取一个平台产品配置的所有和供应商产品有关的数据
									$
											.getJSON(
													"${ctx}/productOperatorProduct/findPopByProId",
													{
														id : id
													},
													function(data) {
														if (data.resultCode == -1) {
															$.messager
																	.alert(
																			'消息',
																			data.resultMsg);
														} else {
															$(
																	"#oproTable_huafei")
																	.empty();
															$(
																	"#oproTable_huafei")
																	.append(
																			'<thead style="color: green"><tr><th width="10%">城市</th><th width="15%">平台流量包名称</th><th width="15%">渠道流量包名称</th><th width="10%">规格</th><th width="8%">价格</th><th width="8%">折扣</th><th width="8%">状态</th><th width="8%">操作</th></tr></thead>');
															if (data.content.length != 0) {
																$
																		.each(
																				map,
																				function(
																						key,
																						values) {
																					$(
																							"#p_"
																									+ key)
																							.show();

																				});
																$
																		.each(
																				data.content,
																				function(
																						index,
																						element) {
																					$(
																							"#oproTable_huafei")
																							.append(
																									"<tr>"
																											+ "<td style=\"display:none\">"
																											+ element.fxOperatorsProduct.id
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ element.provinceId
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ data.content.name
																											+ "</td>"
																											+ "<td style=\"display:none\">¥"
																											+ data.content.price / 1000
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ data.content.flowType
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ element.fxOperatorsProduct.yysTypeId
																											+ "</td>"
																											+ "<td style=\"display:none\">"
																											+ data.content.groupId
																											+ "</td><td>"
																											+ showProvince(element.provinceId)
																											+ "</td><td>"
																											+ name
																											+ "</td><td> "
																											+ element.fxOperatorsProduct.name
																											+ "</td><td>"
																											+ size
																											+ "</td><td>¥"
																											+ element.fxOperatorsProduct.price
																											/ 1000
																											+ "</td><td>"
																											+ (element.fxOperatorsProduct.channelDiscount / 100)
																											+ "</td><td >"
																											+ element.fxOperatorsProduct.status
																											+ '</td><td><a href="javascript:;" class="easyui-linkbutton" onclick="delRow(this,\''
																											+ element.provinceId
																											+ '\');" color="red"'
																											+ '>删除</a></td></tr>');
																					//隐藏已有省份
																					$
																							.each(
																									map,
																									function(
																											key,
																											values) {
																										if (key == element.provinceId) {
																											$(
																													"#p_huafei_"
																															+ key)
																													.hide();
																											$(
																													"#p1_huafei_"
																															+ key)
																													.hide();
																										} else {

																										}

																									});
																				});
																if (_provinceId != "000") {
																	$
																			.each(
																					map,
																					function(
																							key,
																							values) {
																						$(
																								"#p_huafei_"
																										+ key)
																								.hide();
																						$(
																								"#p1_huafei_"
																										+ key)
																								.hide();
																					});
																}
															} else {
																if (_provinceId != "000") {
																	$
																			.each(
																					map,
																					function(
																							key,
																							values) {
																						$(
																								"#p_huafei_"
																										+ key)
																								.hide();
																						$(
																								"#p1_huafei_"
																										+ key)
																								.hide();
																					});

																	$(
																			"#p_huafei_"
																					+ _provinceId)
																			.show();
																}
															}
														}
													});
								}
							});
		}
		$('#webinformation2').dialog('open');
		//话费
		operators_product(1);
		addEnventSelect(1);
		loadParentPacket_huafei();
	}
	/* 添加话费产品 end */

	function addEnventSelect(businessType) {
		if (businessType == 0) {
			addEnventSelect_liuliang();
		} else if (businessType == 1) {
			addEnventSelect_huafei();
		}
	}
	function addEnventSelect_liuliang() {
		//点击全网产品
		$("#provinceCheckBox input").click(
				function() {
					if ($("#_opflowSize").val() == ""
							|| $("#_opflowSize").val() == undefined) {
						alert("请输入流量包大小");
						return false;
					}
					var p1 = $("#provinceId_liuliang").val(); //这就是selected的值 
					if (p1 == "000") {
						//alert(p1 + "===" + "全网配置分省通道，判断选择的省份有几个");
						var getCK = document.getElementsByTagName('input');
						var count = 0;
						for (var i = 0; i < getCK.length; i++) {
							whichObj = getCK[i];
							if (whichObj.type == "checkbox") {
								if (whichObj.checked == true) {
									count++;
								}
							}
						}
						if (count == 1) {
							//搜索分省流量包（单个）
							var params = $('#_operatorsProduct').datagrid(
									'options').queryParams;
							var fields = $('#_operatorsProductform')
									.serializeArray();
							$.each(fields, function(i, field) {
								params[field.name] = $.trim(field.value);
								//获取分省
								var _provinceCheckbox = $(
										"input:checkbox:checked").val();
								params['provinceId_liuliang'] = $
										.trim(_provinceCheckbox);
							});
							$('#_operatorsProduct').datagrid('load');
						} else if (count > 1) {
							//多个分省 则选择全国流量
							var params = $('#_operatorsProduct').datagrid(
									'options').queryParams;
							var fields = $('#_operatorsProductform')
									.serializeArray();
							$.each(fields, function(i, field) {
								params[field.name] = $.trim(field.value);
								params['provinceId_liuliang'] = $.trim("000000");
							});
							$('#_operatorsProduct').datagrid('load');
							//搜索全国流量包
						} else {
							alert("请选择分省");
						}
						//	alert("已选择分省：=====" + count + "个");
					}
				});
		$('#provinceId_liuliang')
				.change(
						function() {
							var p1 = $(this).children('option:selected').val();//这就是selected的值 
							var value1 = $(this).children('option:selected')
									.text();
							cityName = value1;
							if (p1 == 000 || p1 == "000") {
								$("provinceCheckBox checkbox").checked = false;

								document
										.getElementById("provinceCheckBoxHidden").style.display = "none";//隐藏
								document.getElementById("provinceCheckBox").style.display = "";//显示
							} else {
								document.getElementById("provinceCheckBox").style.display = "none";//隐藏
								document
										.getElementById("provinceCheckBoxHidden").style.display = "";//显示
								$("#provinceCheckBoxHidden").empty();
								$("#provinceCheckBoxHidden")
										.append(
												'<div style="display:;" id="p1_'+p1+'" ><input name="provinceBox" type="checkbox" value="'
									+ p1
									+ '" style="width:20px;"/>'
														+ value1 + '</div>');
								//点击分省产品
								$("#provinceCheckBoxHidden li input")
										.click(
												function() {
													//alert("分省");
													if ($("#_opflowSize").val() == ""
															|| $("#_opflowSize")
																	.val() == undefined) {
														alert("请输入流量包大小");
														return false;
													}
													//搜索分省流量包（单个）
													var params = $(
															'#_operatorsProduct')
															.datagrid('options').queryParams;
													var fields = $(
															'#_operatorsProductform')
															.serializeArray();
													$
															.each(
																	fields,
																	function(i,
																			field) {
																		params[field.name] = $
																				.trim(field.value);
																		//获取分省
																		var _provinceCheckbox = $(
																				"input:checkbox:checked")
																				.val();
																		params['provinceId_liuliang'] = $
																				.trim(_provinceCheckbox);
																	});
													$('#_operatorsProduct')
															.datagrid('load');
												});

							}
						});
	}

	/**
	 *话费
	 */
	function addEnventSelect_huafei() {
		//点击全网产品
		$("#provinceCheckBox_huafei input").click(
				function() {
					var p1 = $("#provinceId_huafei").val(); //这就是selected的值 
					if (p1 == "000") {
						//alert(p1 + "===" + "全网配置分省通道，判断选择的省份有几个");
						var getCK = document.getElementsByTagName('input');
						var count = 0;
						for (var i = 0; i < getCK.length; i++) {
							whichObj = getCK[i];
							if (whichObj.type == "checkbox") {
								if (whichObj.checked == true) {
									count++;
								}
							}
						}
						if (count == 1) {
							//搜索分省流量包（单个）
							var params = $('#_operatorsProduct_huafei')
									.datagrid('options').queryParams;
							var fields = $('#_operatorsProductform_huafei')
									.serializeArray();
							$.each(fields, function(i, field) {
								params[field.name] = $.trim(field.value);
								//获取分省
								var _provinceCheckBox_huafei = $(
										"input:checkbox:checked").val();
								params['provinceId_huafei'] = $
										.trim(_provinceCheckBox_huafei);
							});
							$('#_operatorsProduct_huafei').datagrid('load');
						} else if (count > 1) {
							//多个分省 则选择全国流量
							var params = $('#_operatorsProduct_huafei')
									.datagrid('options').queryParams;
							var fields = $('#_operatorsProductform_huafei')
									.serializeArray();
							$.each(fields, function(i, field) {
								params[field.name] = $.trim(field.value);
								params['provinceId_huafei'] = $.trim("000000");
							});
							$('#_operatorsProduct_huafei').datagrid('load');
							//搜索全国流量包
						} else {
							alert("请选择分省");
						}
						//	alert("已选择分省：=====" + count + "个");
					}
				});
		$('#provinceId_huafei')
				.change(
						function() {
							var p1 = $(this).children('option:selected').val();//这就是selected的值 
							var value1 = $(this).children('option:selected')
									.text();
							cityName = value1;
							if (p1 == 000 || p1 == "000") {
								$("provinceCheckBox_huafei checkbox").checked = false;
								document
										.getElementById("provinceCheckBoxHidden_huafei").style.display = "none";//隐藏
								document
										.getElementById("provinceCheckBox_huafei").style.display = "";//显示
							} else {
								document
										.getElementById("provinceCheckBox_huafei").style.display = "none";//隐藏
								document
										.getElementById("provinceCheckBoxHidden_huafei").style.display = "";//显示
								$("#provinceCheckBoxHidden_huafei").empty();
								$("#provinceCheckBoxHidden_huafei")
										.append(
												'<div style="display:;" id="p1_huafei_'+p1+'" ><input name="provinceBox_huafei" type="checkbox" value="'
									+ p1
									+ '" style="width:20px;"/>'
														+ value1 + '</div>');
								//点击分省产品
								$("#provinceCheckBoxHidden_huafei li input")
										.click(
												function() {
													//alert("分省");
													if ($("#_opflowSize").val() == ""
															|| $("#_opflowSize")
																	.val() == undefined) {
														alert("请输入流量包大小");
														return false;
													}
													//搜索分省流量包（单个）
													var params = $(
															'#_operatorsProduct_huafei')
															.datagrid('options').queryParams;
													var fields = $(
															'#_operatorsProductform_huafei')
															.serializeArray();
													$
															.each(
																	fields,
																	function(i,
																			field) {
																		params[field.name] = $
																				.trim(field.value);
																		//获取分省
																		var _provinceCheckbox_huafei = $(
																				"input:checkbox:checked")
																				.val();
																		params['provinceId_huafei'] = $
																				.trim(_provinceCheckbox_huafei);
																	});
													$(
															'#_operatorsProduct_huafei')
															.datagrid('load');
												});

							}
						});
	}
	/**
	 *供应商产品
	 */

	function operators_product(businessType) {
		if (businessType == 0) {
			//话费
			operators_liuliang_product();
		} else if (businessType == 1) {
			operators_huafei_product();
		}
	}

	/**
	 *流量供应商
	 */
	function operators_liuliang_product() {
		$("#_operatorsProduct")
				.datagrid(
						{
							url : '${ctx}/operatorProduct/getProductList',
							method : 'get',
							nowrap : false,
							striped : true,
							remoteSort : false,
							pageSize : 10,
							fit : true,
							queryParams : {
								businessType : 0
							},
							singleSelect : true,//是否单选  
							pagination : true,//分页控件  
							//rownumbers : true,//行号 
							fitColumns : true,//列宽自动填充满表格
							checkOnSelect : false,
							selectOnCheck : false,
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
										title : '选择',
										width : 40,
										align : "left",
										formatter : function(value, row,
												rowIndex) {
											return '<input type="radio" name="id" id="selectRadio"' + rowIndex + '    value="' + row.id + '" />';
										}
									},
									{
										field : 'name',
										title : '流量包名称',
										width : 250,
										align : "center"
									},
									{
										field : 'size',
										title : '流量包大小',
										width : 100,
										align : "center"
									},
									{
										field : 'flowType',
										title : '流量包类型',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.flowType == 0) {
												return "漫游";
											} else if (row.flowType = 1) {
												return "本地";
											}
										}
									},
									{
										field : 'price',
										title : '价格',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.price > 0) {
												return "¥" + row.price / 1000;
											} else {
												return "¥" + row.price;
											}
										}
									},
									{
										field : 'channelDiscount',
										title : '折扣',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.channelDiscount > 0) {
												return row.channelDiscount
														/ 100 + " 折";
											} else {
												return row.channelDiscount
														+ " 折";
											}
										}
									},
									{
										field : 'status',
										title : '状态',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.status == 0) {
												return '<span style="color:green;background:#D6D6D6">正常</span>';
											} else {
												return '<span style="color:red;background:#D6D6D6">异常</span>';
											}
										}
									} ] ],
							onLoadSuccess : function(data) {
								//加载完毕后获取所有的checkbox遍历
								if (data.rows.length > 0) {
									//循环判断操作为新增的不能选择
									for (var i = 0; i < data.rows.length; i++) {
										//根据operate让某些行不可选
										if (data.rows[i].operate == "false") {
											$("input[type='radio']")[i].disabled = true;
										}
									}
								}
							},
							onClickRow : function(rowIndex, rowData) {
								//判断省份是否选择
								var _provinceCheckbox = $(
										"input:checkbox:checked").val();
								if (undefined == _provinceCheckbox
										|| _provinceCheckbox == "") {
									alert("请先选择省份");
									$("input[type='radio']").attr("checked",
											false);
									return false;
								} else {
									//加载完毕后获取所有的checkbox遍历
									var radio = $("input[type='radio']")[rowIndex].disabled;
									//如果当前的单选框不可选，则不让其选中
									if (radio != true) {
										//让点击的行单选按钮选中
										$("input[type='radio']")[rowIndex].checked = true;
									} else {
										$("input[type='radio']")[rowIndex].checked = false;
									}
									//获取radio选中值
									var opid = $("input:radio:checked").val();
									lastOpid = $("input:radio:checked").val();
									lastOproName = rowData.name;
									lastStatus = rowData.status;
									lastFlowType = rowData.flowType;
									lastSize = rowData.size;
									lastChannelDiscount = rowData.channelDiscount;
									lastPrice = rowData.price;
								}
							}
						});
	}
	/**
	 *话费供应商
	 */
	function operators_huafei_product() {
		$("#_operatorsProduct_huafei")
				.datagrid(
						{
							url : '${ctx}/operatorProduct/getProductList',
							method : 'get',
							nowrap : false,
							striped : true,
							remoteSort : false,
							pageSize : 10,
							fit : true,
							queryParams : {
								businessType : 1
							},
							singleSelect : true,//是否单选  
							pagination : true,//分页控件  
							//rownumbers : true,//行号 
							fitColumns : true,//列宽自动填充满表格
							checkOnSelect : false,
							selectOnCheck : false,
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
										title : '选择',
										width : 40,
										align : "left",
										formatter : function(value, row,
												rowIndex) {
											return '<input type="radio" name="id" id="selectRadio"' + rowIndex + '    value="' + row.id + '" />';
										}
									},
									{
										field : 'name',
										title : '流量包名称',
										width : 250,
										align : "center"
									},
									{
										field : 'size',
										title : '面值',
										width : 100,
										align : "center"
									},
									{
										field : 'price',
										title : '价格',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.price > 0) {
												return "¥" + row.price / 1000;
											} else {
												return "¥" + row.price;
											}
										}
									},
									{
										field : 'channelDiscount',
										title : '折扣',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.channelDiscount > 0) {
												return row.channelDiscount
														/ 100 + " 折";
											} else {
												return row.channelDiscount
														+ " 折";
											}
										}
									},
									{
										field : 'status',
										title : '状态',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.status == 0) {
												return '<span style="color:green;background:#D6D6D6">正常</span>';
											} else {
												return '<span style="color:red;background:#D6D6D6">异常</span>';
											}
										}
									} ] ],
							onLoadSuccess : function(data) {
								//加载完毕后获取所有的checkbox遍历
								if (data.rows.length > 0) {
									//循环判断操作为新增的不能选择
									for (var i = 0; i < data.rows.length; i++) {
										//根据operate让某些行不可选
										if (data.rows[i].operate == "false") {
											$("input[type='radio']")[i].disabled = true;
										}
									}
								}
							},
							onClickRow : function(rowIndex, rowData) {
								//判断省份是否选择
								var _provinceCheckbox = $(
										"input:checkbox:checked").val();
								if (undefined == _provinceCheckbox
										|| _provinceCheckbox == "") {
									alert("请先选择省份");
									$("input[type='radio']").attr("checked",
											false);
									return false;
								} else {
									//加载完毕后获取所有的checkbox遍历
									var radio = $("input[type='radio']")[rowIndex].disabled;
									//如果当前的单选框不可选，则不让其选中
									if (radio != true) {
										//让点击的行单选按钮选中
										$("input[type='radio']")[rowIndex].checked = true;
									} else {
										$("input[type='radio']")[rowIndex].checked = false;
									}
									//获取radio选中值
									var opid = $("input:radio:checked").val();
									lastOpid = $("input:radio:checked").val();
									lastOproName = rowData.name;
									lastStatus = rowData.status;
									lastFlowType = rowData.flowType;
									lastSize = rowData.size;
									lastChannelDiscount = rowData.channelDiscount;
									lastPrice = rowData.price;
								}
							}
						});
	}
	//根据IDcode获取省份
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

	function pipei(businessType) {
		if (businessType == 0) {
			pipei_liuliang();
		} else if (businessType == 1) {
			pipei_huafei();
		}
	}

	//匹配流量包
	function pipei_liuliang() {

		if ($("#_opflowSize").val() == ""
				|| $("#_opflowSize").val() == undefined) {
			alert("请输入流量包大小");
			return false;
		}
		//清空所有checkbox
		//
		$("input[name='provinceBox']").attr("checked", false);
		//点击分省或者全国，load
		var params = $('#_operatorsProduct').datagrid('options').queryParams;
		var fields = $('#_operatorsProductform').serializeArray();
		$.each(fields, function(i, field) {
			params[field.name] = $.trim(field.value);
		});
		$('#_operatorsProduct').datagrid('load');
		//获取radio选中值
		//var opid = $("input:radio:checked").val();
		//查询已配置省份
	}

	//匹配话费包
	function pipei_huafei() {

		if ($("#_opflowSize_huafei").val() == ""
				|| $("#_opflowSize_huafei").val() == undefined) {
			alert("请输入面值");
			return false;
		}
		//清空所有checkbox
		//
		$("input[name='provinceBox_huafei']").attr("checked", false);
		//点击分省或者全国，load
		var params = $('#_operatorsProduct_huafei').datagrid('options').queryParams;
		var fields = $('#_operatorsProductform_huafei').serializeArray();
		$.each(fields, function(i, field) {
			params[field.name] = $.trim(field.value);
		});
		$('#_operatorsProduct_huafei').datagrid('load');
		//获取radio选中值
		//var opid = $("input:radio:checked").val();
		//查询已配置省份
	}

	//加载所有产品组
	function loadParentAllPacket() {
		$('#productGroup_id').combobox({
			url : "${ctx}/productGroup/getWithOutGasFXProductGroup",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 130,
			panelHeight : 200,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return false;
				}
			}
		});
	}
	//加载平台产品组
	function loadParentPacket() {
		$('#productParentId').combobox({
			url : "${ctx}/productGroup/getAllFXProductGroup?businessType=0",
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
	}
	//加载平台话费产品组
	function loadParentPacket_huafei() {
		$('#productParentId_huafei').combobox({
			url : "${ctx}/productGroup/getAllFXProductGroup?businessType=1",
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
	}

	//删除临时配置product
	function remove_temp_product() {
		var _oproduct = $("#_oproduct");
		_oproduct.empty();
		$("input:radio:checked").val("");
		$("input[type='radio']").attr("checked", false);
		//清空所有checkbox
		$("input[name='provinceBox']").attr("checked", false);
		$("input[name='provinceBox_huafei']").attr("checked", false);
		//点击分省或者全国，load
		var params = $('#_operatorsProduct').datagrid('options').queryParams;
		var fields = $('#_operatorsProductform').serializeArray();
		$.each(fields, function(i, field) {
			params[field.name] = $.trim(field.value);
		});
		$('#_operatorsProduct').datagrid('load');
	}

	//添加平台产品
	function addProduct_liuliang() {
		var paramValue = "";
		var table = document.getElementById("oproTable");
		var tr = table.getElementsByTagName("tr");
		var td = table.getElementsByTagName("td");

		$("#oproTable").find("tr").each(
				function() {
					var len = $(this).children("td").length;
					if (len == 0) {
					} else {
						paramValue = paramValue + "oproId="
								+ $.trim($(this).children('td').eq(0).text())
								+ "&provinceCode="
								+ $.trim($(this).children('td').eq(1).text())
								+ "&proYysType="
								+ $.trim($(this).children('td').eq(5).text())
								+ "#####";
					}
				});

		//分销商产品包
		var opid = $("input:radio:checked").val();
		//省份
		var _provinceCheckbox = $("input:checkbox:checked").val();
		//流量包大小
		var saveflowsize = $("#_opflowSize").val();
		//组ID
		var parentId = $('#productParentId').combobox('getValue');
		//保存平台产品与平台产品运营商产品关系表
		$.messager
				.confirm(
						"操作提示",
						"您确定要保存吗？",
						function(data) {
							if (data) {
								$('#queryForm')
										.form(
												'submit',
												{
													url : "${ctx}/product/saveProductAndOpro",
													onSubmit : function(param) {
														param.paramValue = paramValue;
														param.status = "1";
														param.businessType_save = "0";
														param.maxProvince = $(
																"#provinceId_liuliang")
																.val();//这就是selected的值 
														param.size = $(
																"#_opflowSize")
																.val();
														param.parentId = parentId;
														param.saveName = $(
																"#savename")
																.val();
														param.proPrice = $(
																"#saveprice")
																.val() * 1000;
														param.flowType = $(
																"#flowTypeByProvinceId_liuliang")
																.val();
														param.yysType = $(
																'#yysId_liuliang')
																.val();
														param.open_proId = open_proId;
														param.save_selfProductType=$("#selfProductType_liuliang").val();
													},
													success : function(data) {
														var d = eval("(" + data
																+ ")");
														if (d.resultCode == 1) {
															$.messager
																	.confirm(
																			"操作提示",
																			"保存成功",
																			function(
																					data) {
																				if (data) {
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

	//添加平台产品
	function addProduct_huafei() {
		var paramValue = "";
		var table = document.getElementById("oproTable_huafei");
		var tr = table.getElementsByTagName("tr");
		var td = table.getElementsByTagName("td");

		$("#oproTable_huafei").find("tr").each(
				function() {
					var len = $(this).children("td").length;
					if (len == 0) {
					} else {
						paramValue = paramValue + "oproId="
								+ $.trim($(this).children('td').eq(0).text())
								+ "&provinceCode="
								+ $.trim($(this).children('td').eq(1).text())
								+ "&proYysType="
								+ $.trim($(this).children('td').eq(5).text())
								+ "#####";
					}
				});

		//分销商产品包
		var opid = $("input:radio:checked").val();
		//省份
		var _provinceCheckbox = $("input:checkbox:checked").val();
		//流量包大小
		var saveflowsize = $("#_opflowSize").val();
		//组ID
		var parentId = $('#productParentId_huafei').combobox('getValue');
		//保存平台产品与平台产品运营商产品关系表
		$.messager.confirm("操作提示", "您确定要保存吗？", function(data) {
			if (data) {
				$('#queryForm').form('submit', {
					url : "${ctx}/product/saveHuaFeiProductAndOpro",
					onSubmit : function(param) {
						param.paramValue = paramValue;
						param.status = "1";
						param.businessType_save = "1";
						param.maxProvince = $("#provinceId_huafei").val();//这就是selected的值 
						param.size = $("#_opflowSize_huafei").val();
						param.parentId = parentId;
						param.saveName = $("#savename_huafei").val();
						param.proPrice = $("#saveprice_huafei").val() * 1000;
						param.yysType = $('#yysId_huafei').val();
						param.open_proId = open_proId;
						param.save_selfProductType=$("#selfProductType_huafei").val();

					},
					success : function(data) {
						var d = eval("(" + data + ")");
						if (d.resultCode == 1) {
							$.messager.confirm("操作提示", "保存成功", function(data) {
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
			} else {
				//alert("取消");
			}
		});
	}

	//添加产品到临时
	function endProduct_liuliang() {

		//流量名称
		var saveName = $("#savename").val();
		lastProName = saveName;
		//价格
		var saveprice = $("#saveprice").val();
		//分销商产品包
		var opid = $("input:radio:checked").val();
		//省份
		var _provinceCheckbox = $("input:checkbox:checked").val();

		//流量包大小
		var saveflowsize = $("#_opflowSize").val();
		//组ID
		var parentId = $('#productParentId').combobox('getValue');
		lastParentId = parentId;
		//alert("渠道包ID:" + opid + ",===省份code:" + _provinceCheckbox
		//		+ ",===parentId:" + parentId);
		//alert(parentId + "======");
		if (saveName == "" || undefined == saveName) {
			alert("流量包名称不可为空");
			return false;
		}
		if (saveprice == "" || undefined == saveprice) {
			alert("流量包价格不可为空");
			return false;
		}
		if (_provinceCheckbox == "" || undefined == _provinceCheckbox) {
			alert("请选择省份");
			return false;
		}
		if (opid == "" || undefined == opid) {
			alert("请选择可用渠道包");
			return false;
		}
		if (saveflowsize == "" || undefined == saveflowsize) {
			alert("请选择流量包大小");
			return false;
		}
		if (parentId == "" || parentId == undefined) {
			alert("请选择产品组");
			return false;
		}
		//var _oproduct = $("#_oproduct");
		/* _oproduct.empty(); */
		//运营商
		var yysType = $('#yysId_liuliang').val();
		//显示在临时数据中
		//$("#oproTable").append('');
		//获取所有选中的checkbox省份，循环，并添加到 临时数据中
		var getCKbbox = document.getElementsByTagName('input');
		//组ID
		var parentId = $('#productParentId').combobox('getValue');
		for (var i = 0; i < getCKbbox.length; i++) {
			whichObj = getCKbbox[i];
			if (whichObj.type == "checkbox") {
				if (whichObj.checked == true) {
					$("#p1_" + whichObj.value).hide();
					$("#p_" + whichObj.value).hide();
					if (lastStatus == 0) {
						lastStatus = "正常";
					} else {
						lastStatus = "异常";
					}
					var lastFlowTypeStr;
					if (lastFlowType == 0) {
						lastFlowTypeStr = "漫游";
					} else {
						lastFlowTypeStr = "本地";
					}

					$("#oproTable")
							.append(
									"<tr>" + "<td style=\"display:none\">"
											+ opid
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ whichObj.value
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ lastProName
											+ "</td>"
											+ "<td style=\"display:none\">¥"
											+ lastPrice / 1000
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ lastFlowType
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ yysType
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ parentId
											+ "</td><td>"
											+ showProvince(whichObj.value)
											+ "</td><td>"
											+ lastProName
											+ "</td><td> "
											+ lastOproName
											+ "</td><td>"
											+ lastSize
											+ "</td><td>"
											+ lastFlowTypeStr
											+ "</td><td>¥"
											+ lastPrice
											/ 1000
											+ "</td><td>"
											+ (lastChannelDiscount / 100)
											+ "</td><td >"
											+ lastStatus
											+ '</td><td><a href="javascript:;" class="easyui-linkbutton" onclick="delRow(this,\''
											+ whichObj.value
											+ '\');" color="red"'
											+ '>删除</a></td></tr>');

				} else {
				}
			}
		}
		$("input[name='provinceBox']").attr("checked", false);
	}
	//添加产品到临时
	function endProduct_huafei() {
		//流量名称
		var saveName_huafei = $("#savename_huafei").val();
		lastProName = saveName_huafei;
		//价格
		var saveprice_huafei = $("#saveprice_huafei").val();
		//分销商产品包
		var opid = $("input:radio:checked").val();
		//省份
		var _provinceCheckbox = $("input:checkbox:checked").val();
		//流量包大小
		var saveflowsize_huafei = $("#_opflowSize_huafei").val();
		//组ID
		var parentId_huafei = $('#productParentId_huafei').combobox('getValue');
		lastParentId = parentId_huafei;
		if (saveName_huafei == "" || undefined == saveName_huafei) {
			alert("流量包名称不可为空");
			return false;
		}
		if (saveprice_huafei == "" || undefined == saveprice_huafei) {
			alert("流量包价格不可为空");
			return false;
		}
		if (_provinceCheckbox == "" || undefined == _provinceCheckbox) {
			alert("请选择省份");
			return false;
		}
		if (opid == "" || undefined == opid) {
			alert("请选择可用渠道包");
			return false;
		}
		if (saveflowsize_huafei == "" || undefined == saveflowsize_huafei) {
			alert("请选择流量包大小");
			return false;
		}
		if (parentId_huafei == "" || parentId_huafei == undefined) {
			alert("请选择产品组");
			return false;
		}
		//var _oproduct = $("#_oproduct");
		/* _oproduct.empty(); */
		//运营商
		var yysType = $('#yysId_huafei').val();
		//显示在临时数据中
		//$("#oproTable").append('');
		//获取所有选中的checkbox省份，循环，并添加到 临时数据中
		var getCKbbox = document.getElementsByTagName('input');
		//组ID
		//var parentId = $('#productParentId').combobox('getValue');
		for (var i = 0; i < getCKbbox.length; i++) {
			whichObj = getCKbbox[i];
			if (whichObj.type == "checkbox") {
				if (whichObj.checked == true) {
					$("#p1_huafei_" + whichObj.value).hide();
					$("#p_huafei_" + whichObj.value).hide();
					if (lastStatus == 0) {
						lastStatus = "正常";
					} else {
						lastStatus = "异常";
					}
					$("#oproTable_huafei")
							.append(
									"<tr>" + "<td style=\"display:none\">"
											+ opid
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ whichObj.value
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ lastProName
											+ "</td>"
											+ "<td style=\"display:none\">¥"
											+ lastPrice / 1000
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ lastFlowType
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ yysType
											+ "</td>"
											+ "<td style=\"display:none\">"
											+ parentId_huafei
											+ "</td><td>"
											+ showProvince(whichObj.value)
											+ "</td><td>"
											+ lastProName
											+ "</td><td> "
											+ lastOproName
											+ "</td><td>"
											+ lastSize
											+ "</td><td>¥"
											+ lastPrice
											/ 1000
											+ "</td><td>"
											+ (lastChannelDiscount / 100)
											+ "</td><td >"
											+ lastStatus
											+ '</td><td><a href="javascript:;" class="easyui-linkbutton" onclick="delRow(this,\''
											+ whichObj.value
											+ '\');" color="red"'
											+ '>删除</a></td></tr>');

				} else {
					//未选中
					/* 	var provinceIdOne = $("#provinceIdOne");
						provinceIdOne.empty();
						var _li = $('<li></li>');
						_li
								.append('<input name="provinceBox" type="checkbox" value="'
									+ p1
									+ '" style="width:20px;"/>'
										+ value1);
						provinceIdOne.append(_li); */
				}
			}
		}
		//	var provinceIdOne = $("#provinceIdOne");
		//	provinceIdOne.empty();
		$("input[name='provinceBox_huafei']").attr("checked", false);
	}

	function delRow(obj, removeRow) {
		var tr = this.getRowObj(obj);
		if (tr != null) {
			tr.parentNode.removeChild(tr);
			$("#p_" + removeRow).show();
			$("#p1_" + removeRow).show();
			$("#p_huafei_" + removeRow).show();
			$("#p1_huafei_" + removeRow).show();
		} else {
			throw new Error("the given object is not contained by the table");
		}
	}
	function getRowObj(obj) {
		var i = 0;
		while (obj.tagName.toLowerCase() != "tr") {
			obj = obj.parentNode;
			if (obj.tagName.toLowerCase() == "table")
				return null;
		}
		return obj;
	}
	function updateStatus(id, status) {
		if (status == 0) {
			$.messager
					.confirm(
							"操作提示",
							"您确定要上架吗？",
							function(data) {
								if (data) {
									$('#queryForm')
											.form(
													'submit',
													{
														url : "${ctx}/product/updateProductStatus",
														onSubmit : function(
																param) {
															param.status = status;
															param.id = id;
														},
														success : function(data) {
															var d = eval("("
																	+ data
																	+ ")");
															if (d.resultCode == 1) {
																$("#yxj" + id)
																		.text(
																				"已上架");
																document
																		.getElementById("yxj"
																				+ id).style.background = "green";
																document
																		.getElementById("yxj"
																				+ id).style.color = "#fff";
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
		} else if (status == 1) {
			$.messager
					.confirm(
							"操作提示",
							"您确定要下架吗？",
							function(data) {
								if (data) {
									$('#queryForm')
											.form(
													'submit',
													{
														url : "${ctx}/product/updateProductStatus",
														onSubmit : function(
																param) {
															param.status = status;
															param.id = id;
														},
														success : function(data) {
															var d = eval("("
																	+ data
																	+ ")");
															if (d.resultCode == 1) {
																$("#ysj" + id)
																		.text(
																				"已下架");
																document
																		.getElementById("ysj"
																				+ id).style.color = "#fff";
																document
																		.getElementById("ysj"
																				+ id).style.background = "red";

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
	}
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="流量话费平台产品" style="padding: 2px">
			<table id="_productList"></table>
			<!-- toolbar="#tb" -->
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table cellpadding="1">
						<tr>
							<td><b>运营商：</b> <select class="sel" name="yysId" id="yysId"
								style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option>
							</select></td>
							<td><b>省份：</b> <select id="provinceId" name="provinceId"
								style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
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
							</select></td>
							<td><b>包类型：</b> <select class="sel"
								name="flowTypeByProvinceId" id="flowTypeByProvinceId"
								style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="0">漫游</option>
									<option value="1">本地</option>
									<option value="-1">其它</option>
							</select></td>

							<td><b>面值：</b> <select id="flowSize" name="flowSize"
								style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="5">5</option>
									<option value="10">10</option>
									<option value="15">15</option>
									<option value="20">20</option>
									<option value="30">30</option>
									<option value="50">50</option>
									<option value="70">70</option>
									<option value="100">100</option>
									<option value="200">200</option>
									<option value="300">300</option>
									<option value="500">500</option>
									<option value="700">700</option>
									<option value="1000">1000</option>
									<option value="1024">1024</option>
									<option value="2048">2048</option>
									<option value="3096">3096</option>
									<option value="4096">4G</option>
									<option value="6144">6G</option>
									<option value="10240">10G</option>
									<option value="12288">12G</option>
							</select></td>
							<td><b>业务类型：</b><select class="sel" name="businessType"
								id="businessType" style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="0">流量</option>
									<option value="1">话费</option>
							</select></td>
							<td><b>所属产品组：</b><input type="text" name="productGroup_id"
								id="productGroup_id" style="width: 100px; padding-left: 0px;"><b>&nbsp;产品名称：</b><input
								type="text" name="flowName"
								style="width: 130px; padding-left: 0px;"></td>
							<td></td>
						</tr>
						<tr>
							<td><div style="float: left; margin-right: 10px;">
								<a href="javascript:;" class="easyui-linkbutton"
								   data-options="iconCls:'icon-add'" id="add_product"
								   onclick="add_product(0,0);">流量产品</a>
							</div></td>
							<td>
								<div style="float: left;">
									<a href="javascript:;" class="easyui-linkbutton"
									   data-options="iconCls:'icon-add'" id="add_product"
									   onclick="add_product(0,1);">话费产品</a>
								</div>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td>
								<div style="float: left;">
									<b>营业类型：</b><select class="sel" id="selfProductType" name="selfProductType" style="width: 80px; padding-left: 0px;">
									<option value="">-请选择-</option>
									<option value="0">非自营</option>
									<option value="1">自营</option>
								</select>
								<a href="javascript:;"
										class="easyui-linkbutton"
										style="width: 100px; margin-left: 150px"
										data-options="iconCls:'icon-search'" id="search_id">搜索</a>
								</div></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="webinformation1" style="margin-top: 10px;">
				<form id="_operatorsProductform" method="post">
					<table cellpadding="2">
						<tr>
							<td>运营商：</td>
							<td><select class="sel" id="yysId_liuliang"
								name="yysId_liuliang" style="width: 100px;">
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option>
							</select> <font class="text_red">*</font></td>
							<td>流量类型：</td>
							<td><select class="sel" id="flowTypeByProvinceId_liuliang"
								name="flowTypeByProvinceId_liuliang" style="width: 100px;">
									<option value="0">漫游</option>
									<option value="1">本地</option>
							</select> <font class="text_red">*</font></td>

							<td>省份：</td>
							<td>
							<td><select id="provinceId_liuliang"
								name="provinceId_liuliang" style="width: 100px;">
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
							</select> <font class="text_red">*</font></td>
							<td>流量包大小：</td>
							<td><input type="text" id="_opflowSize" name="flowSize"
								style="width: 100px;">(M)<font class="text_red">*</font></td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td><a href="#" class="easyui-linkbutton"
								style="width: 100px; height: 30px; background: #049cf5; size: 1rem;"
								onclick="pipei(0)">匹配</a></td>
						</tr>
					</table>
					<div style="background: #5cbedd; width: 936px; height: auto;"
						id="_oproduct">
						<ul class="clearfix default" style="padding: 0; margin: 0;"></ul>
					</div>

					<!-- updiv -->
					<div style="background: #5cbedd; width: 936px; height: auto;"
						id="updiv">
						<table id="oproTable" style="width: 100%">
							<thead style="color: green">
								<tr>
									<th width="10%">城市</th>
									<th width="15%">平台流量包名称</th>
									<th width="15%">渠道流量包名称</th>
									<th width="10%">规格</th>
									<th width="10%">流量类型</th>
									<th width="8%">价格</th>
									<th width="8%">折扣</th>
									<th width="8%">状态</th>
									<th width="8%">操作</th>
								</tr>
							</thead>


						</table>
					</div>
					<div id="downdiv" style="width: auto; height: auto;">
						<div id="provinceCheckBox"
							style="background: #CCC; width: 300px; height: 287px; float: left; font-size: .9rem;">
							<BR />
							<div style="display:; float: left;" id="p_010">
								<input name="provinceBox" type="checkbox" value="010" />北京&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_020">
								<input name="provinceBox" type="checkbox" value="020" />广东&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_021">
								<input name="provinceBox" type="checkbox" value="021">上海&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_022">
								<input name="provinceBox" type="checkbox" value="022">天津&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_023">
								<input name="provinceBox" type="checkbox" value="023">重庆&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_025">
								<input name="provinceBox" type="checkbox" value="025">江苏&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_026">
								<input name="provinceBox" type="checkbox" value="026">青海&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_027">
								<input name="provinceBox" type="checkbox" value="027">海南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_028">
								<input name="provinceBox" type="checkbox" value="028">四川&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_029">
								<input name="provinceBox" type="checkbox" value="029">陕西&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_030">
								<input name="provinceBox" type="checkbox" value="030">山西&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_035">
								<input name="provinceBox" type="checkbox" value="035">河北&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_039">
								<input name="provinceBox" type="checkbox" value="039">河南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_040">
								<input name="provinceBox" type="checkbox" value="040">内蒙古&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_041">
								<input name="provinceBox" type="checkbox" value="041">辽宁&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_045">
								<input name="provinceBox" type="checkbox" value="045">吉林&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_046">
								<input name="provinceBox" type="checkbox" value="046">黑龙江&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_050">
								<input name="provinceBox" type="checkbox" value="050">安徽&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_055">
								<input name="provinceBox" type="checkbox" value="055">浙江&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_059">
								<input name="provinceBox" type="checkbox" value="059">福建&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_060">
								<input name="provinceBox" type="checkbox" value="060">山东&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_070">
								<input name="provinceBox" type="checkbox" value="070">广西&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_071">
								<input name="provinceBox" type="checkbox" value="071">湖北&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_073">
								<input name="provinceBox" type="checkbox" value="073">湖南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_075">
								<input name="provinceBox" type="checkbox" value="075">江西&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_080">
								<input name="provinceBox" type="checkbox" value="080">云南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_085">
								<input name="provinceBox" type="checkbox" value="085">贵州&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_089">
								<input name="provinceBox" type="checkbox" value="089">西藏&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_090">
								<input name="provinceBox" type="checkbox" value="090">宁夏&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_093">
								<input name="provinceBox" type="checkbox" value="093">甘肃&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_095">
								<input name="provinceBox" type="checkbox" value="095">新疆
							</div>

							<table></table>
						</div>
						<div id="provinceCheckBoxHidden"
							style="background: #CCC; width: 300px; height: 287px; float: left; font-size: .9rem; display: none">
							<BR />
							<ul id="provinceIdOne" class="clearfix default"
								style="padding: 0; margin: 0;"></ul>
						</div>
						<div
							style="height: 20px; width: 300px; color: green; text-align: center;">
							<a href="#" class="easyui-linkbutton"
								onclick="endProduct_liuliang()"
								style="background: #049cf5; width: 100px; height: 30px; margin-top: 50px;">确定</a>
						</div>
					</div>
					<div
						style="background: #5cbedd; width: 606px; height: 387px; float: left; right: 0; margin-top: -20px;">
						<table id="_operatorsProduct"></table>
					</div>

					<div
						style="background: #5cbedd; width: 936px; height: 200px; float: left; right: 0">
						<div
							style="margin-top: 10px; margin-left: 10px; text-align: left;">
							产品组名称:<input name="productParentId" id="productParentId"
								style="margin-top: -5px;"> <span>&nbsp;&nbsp;&nbsp;流量包名称:</span><input
								type="text" id="savename" name="savename" /> <span>&nbsp;&nbsp;&nbsp;价格:</span><input
								type="text" id="saveprice" name="saveprice"
								style="width: 70px;" />元
							<span>&nbsp;&nbsp;&nbsp;类型:</span><select class="sel" id="selfProductType_liuliang" style="width:80px">
							<option value="">请选择</option>
							<option value="0">非自营</option>
							<option value="1">自营</option>
						</select>
							<a href="#" class="easyui-linkbutton"
								onclick="addProduct_liuliang()"
								style="background: #049cf5; width: 100px; height: 30px; margin-left: 20px; margin-top: -6px;">保存</a>
						</div>
					</div>
				</form>
			</div>
			<div id="webinformation2" style="margin-top: 10px;">
				<form id="_operatorsProductform_huafei" method="post">
					<table cellpadding="2">
						<tr>
							<td>运营商：</td>
							<td><select class="sel" id="yysId_huafei"
								name="yysId_huafei" style="width: 100px;">
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option>
							</select> <font class="text_red">*</font></td>
							<td>省份：</td>
							<td>
							<td><select id="provinceId_huafei" name="provinceId_huafei"
								style="width: 100px;">
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
							</select> <font class="text_red">*</font></td>
							<td>面值：</td>
							<td><input type="text" id="_opflowSize_huafei"
								name="_opflowSize_huafei" style="width: 100px;">(元)<font
								class="text_red">*</font></td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td><a href="#" class="easyui-linkbutton"
								style="width: 100px; height: 30px; background: #049cf5; size: 1rem;"
								onclick="pipei(1)">匹配</a></td>
						</tr>
					</table>
					<div style="background: #5cbedd; width: 936px; height: auto;"
						id="_oproduct">
						<ul class="clearfix default" style="padding: 0; margin: 0;"></ul>
					</div>

					<!-- updiv -->
					<div style="background: #5cbedd; width: 936px; height: auto;"
						id="updiv">
						<table id="oproTable_huafei" style="width: 100%">
							<thead style="color: green">
								<tr>
									<th width="10%">城市</th>
									<th width="15%">平台产品名称</th>
									<th width="15%">渠道产品名称</th>
									<th width="10%">面值</th>
									<th width="8%">价格</th>
									<th width="8%">折扣</th>
									<th width="8%">状态</th>
									<th width="8%">操作</th>
								</tr>
							</thead>


						</table>
					</div>
					<div id="downdiv" style="width: auto; height: auto;">
						<div id="provinceCheckBox_huafei"
							style="background: #CCC; width: 300px; height: 287px; float: left; font-size: .9rem;">
							<BR />
							<div style="display:; float: left;" id="p_huafei_010">
								<input name="provinceBox_huafei" type="checkbox" value="010" />北京&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_020">
								<input name="provinceBox_huafei" type="checkbox" value="020" />广东&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_021">
								<input name="provinceBox_huafei" type="checkbox" value="021">上海&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_022">
								<input name="provinceBox_huafei" type="checkbox" value="022">天津&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_023">
								<input name="provinceBox_huafei" type="checkbox" value="023">重庆&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_025">
								<input name="provinceBox_huafei" type="checkbox" value="025">江苏&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_026">
								<input name="provinceBox_huafei" type="checkbox" value="026">青海&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_027">
								<input name="provinceBox_huafei" type="checkbox" value="027">海南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_028">
								<input name="provinceBox_huafei" type="checkbox" value="028">四川&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_029">
								<input name="provinceBox_huafei" type="checkbox" value="029">陕西&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_huafei_030">
								<input name="provinceBox_huafei" type="checkbox" value="030">山西&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_035">
								<input name="provinceBox_huafei" type="checkbox" value="035">河北&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_039">
								<input name="provinceBox_huafei" type="checkbox" value="039">河南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_040">
								<input name="provinceBox_huafei" type="checkbox" value="040">内蒙古&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_041">
								<input name="provinceBox_huafei" type="checkbox" value="041">辽宁&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_huafei_045">
								<input name="provinceBox_huafei" type="checkbox" value="045">吉林&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_046">
								<input name="provinceBox_huafei" type="checkbox" value="046">黑龙江&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_050">
								<input name="provinceBox_huafei" type="checkbox" value="050">安徽&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_055">
								<input name="provinceBox_huafei" type="checkbox" value="055">浙江&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_059">
								<input name="provinceBox_huafei" type="checkbox" value="059">福建&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_huafei_060">
								<input name="provinceBox_huafei" type="checkbox" value="060">山东&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_070">
								<input name="provinceBox_huafei" type="checkbox" value="070">广西&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_071">
								<input name="provinceBox_huafei" type="checkbox" value="071">湖北&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_073">
								<input name="provinceBox_huafei" type="checkbox" value="073">湖南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_075">
								<input name="provinceBox_huafei" type="checkbox" value="075">江西&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_huafei_080">
								<input name="provinceBox_huafei" type="checkbox" value="080">云南&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_085">
								<input name="provinceBox_huafei" type="checkbox" value="085">贵州&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_089">
								<input name="provinceBox_huafei" type="checkbox" value="089">西藏&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_090">
								<input name="provinceBox_huafei" type="checkbox" value="090">宁夏&nbsp;&nbsp;&nbsp;
							</div>
							<div style="display:; float: left;" id="p_huafei_093">
								<input name="provinceBox_huafei" type="checkbox" value="093">甘肃&nbsp;&nbsp;&nbsp;<br />
							</div>
							<div style="display:; float: left;" id="p_huafei_095">
								<input name="provinceBox_huafei" type="checkbox" value="095">新疆
							</div>

							<table></table>
						</div>
						<div id="provinceCheckBoxHidden_huafei"
							style="background: #CCC; width: 300px; height: 287px; float: left; font-size: .9rem; display: none">
							<BR />
							<ul id="provinceIdOne" class="clearfix default"
								style="padding: 0; margin: 0;"></ul>
						</div>
						<div
							style="height: 20px; width: 300px; color: green; text-align: center;">
							<a href="#" class="easyui-linkbutton"
								onclick="endProduct_huafei()"
								style="background: #049cf5; width: 100px; height: 30px; margin-top: 50px;">确定</a>
						</div>
					</div>
					<div
						style="background: #5cbedd; width: 606px; height: 387px; float: left; right: 0; margin-top: -20px;">
						<table id="_operatorsProduct_huafei"></table>
					</div>

					<div
						style="background: #5cbedd; width: 936px; height: 200px; float: left; right: 0">
						<div
							style="margin-top: 10px; margin-left: 10px; text-align: left;">
							产品组名称:<input name="productParentId_huafei"
								id="productParentId_huafei" style="margin-top: -5px;"> <span>&nbsp;&nbsp;&nbsp;平台话费包名称:</span><input
								type="text" id="savename_huafei" name="savename_huafei" /> <span>&nbsp;&nbsp;&nbsp;价格:</span><input
								type="text" id="saveprice_huafei" name="saveprice_huafei"
								style="width: 70px;" />元
							<span>&nbsp;&nbsp;&nbsp;类型:</span><select class="sel" id="selfProductType_huafei" style="width:80px">
							<option value="">请选择</option>
							<option value="0">非自营</option>
								<option value="1">自营</option>
							</select>
							<a href="#" class="easyui-linkbutton"
								onclick="addProduct_huafei()"
								style="background: #049cf5; width: 100px; height: 30px; margin-left: 20px; margin-top: -6px;">保存</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
