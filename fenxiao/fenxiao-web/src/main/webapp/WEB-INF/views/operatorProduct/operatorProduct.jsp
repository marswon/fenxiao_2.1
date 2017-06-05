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
							url : '${ctx}/operatorProduct/getOperatorProductListWithOutGas',
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
										field : 'id',
										title : 'ID',
										width : 120,
										align : "left"
									},
									{
										field : 'name',
										title : '流量包名称',
										width : 300,
										align : "center"
									},
									{
										field : 'size',
										title : '流量包大小',
										width : 80,
										align : "center"
									},
									{
										field : 'flowType',
										title : '流量包类型',
										width : 80,
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
										width : 80,
										align : "center",
										formatter : function(val, row, index) {
											return showProvince(row.provinceId);
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
											} else {
												return "话费";
											}
										}
									},
									{
										field : 'channel',
										title : '通道名称',
										width : 150,
										align : "center",
										formatter : function(val, row, index) {
											if (row.channel) {
												return row.channel.name;
											} else {
												return "";
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
												return "可用";
											} else {
												return "不可用";
											}
										}
									},
									{
										field : 'channelDiscount',
										title : '通道折扣(千分制)',
										width : 130,
										align : "center",
										formatter : function(val, row, index) {
											if (row.channelDiscount) {
												return row.channelDiscount / 100;
											} else {
												return "";
											}
										}
									},
									{
									field:'remarks',
									title:'备注',
									width:120,
									align:"center"
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

		/* $('#webinformation1').dialog({
			title : "产品管理编辑",
			width : 850,
			height : 500,
			closed : true,
			cache : false,
			modal : true,
			center : true,
		}); */
		
		
		$('#webinformation1').dialog({
			iconCls:'icon-edit',
			title : "&ensp;产品管理编辑",
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
		
		$.post("${ctx}/channelManege/getAllChannelWithOutGas", null,
				function(data) {
					if (data) {
						$.each(data.content, function(index, obj) {
							$("#channelIdSelect").append(
									"<option value='"+obj.id+"'>"
											+ obj.name + "</option>");
						});
						chosen("#channelIdSelect");
					}
				});

		$.each(provinceMap, function(key, value) {
			$("#provinceId_search").append(
					"<option value='"+key+"'>" + value + "</option>");
		});
		chosen("#yysId");
		chosen("#provinceId_search");
		chosen("#flowTypeByProvinceId");
		chosen("#flowSize");
		chosen("#businessType");

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
			$('#channelId').combobox({
				url : "${ctx}/channelManege/getAllChannelWithOutGas",
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
			$('#webinformation1').dialog('open');
		} else {
			$('#channelId').combobox({
				url : "${ctx}/channelManege/getAllChannelWithOutGas",
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
			//清除缓存
			$.getJSON("${ctx}/operatorProduct/getProductById", {
				id : id
			}, function(data) {
				if (data.resultCode == -1) {
					$.messager.alert('消息', data.resultMsg);
				} else {
					console.log(data);
					$("#yysTypeId").val(data.content.yysTypeId);
					$("#size").val(data.content.size);
					$("#orderCode").val(data.content.orderCode);
					$("#priceOp").val(data.content.price / 1000);
					$("#name").val(data.content.name);
					$("#id").val(data.content.id);
					$("#remarks").val(data.content.remarks);
					var id = data.content.channel.id;
					$("#channelId").combobox('select', id);
					if (data.content.status == "1") {
						$('input:radio[name="status"][value="1"]').prop(
								'checked', true);
					} else {
						$('input:radio[name="status"][value="0"]').prop(
								'checked', true);
					}
					if (data.content.flowType == "1") {
						$('input:radio[name="flowType"][value="1"]').prop(
								'checked', true);
					} else {
						$('input:radio[name="flowType"][value="0"]').prop(
								'checked', true);
					}
					if (data.content.businessType == "1") {
						$('input:radio[name="businessType"][value="1"]').prop(
								'checked', true);
					} else {
						$('input:radio[name="businessType"][value="0"]').prop(
								'checked', true);
					}
					$("#channelDiscount").val(data.content.channelDiscount);
					var provinceId = data.content.provinceId;
					obj = document.getElementById("provinceId");
					for (i = 0; i < obj.length; i++) {
						if (obj[i].value == provinceId)
							obj[i].selected = true;
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
					url : "${ctx}/operatorProduct/saveOperatorProduct",
					onSubmit : function(param) {
					},
					success : function(data) {
						var d = eval("(" + data + ")");
						if (d.resultCode == 1) {
								if (data) {
									 $(
											'#webinformation1')
											.dialog(
													'close'); 
// 									window.location.reload();
									var params = $('#_productList').datagrid('options').queryParams;
									var fields = $('#queryForm').serializeArray();
									$.each(fields, function(i, field) {
										params[field.name] = $.trim(field.value);
									});
									$('#_productList').datagrid('load');
								} 

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
	$('#channelId').combobox({
		editable : false,
		width : 150,

		panelHeight : 200,
	});
	function radio_onclick(businessType) {
		if (businessType == 0) {
			$('#flowType_liuliang1').show();
			$('#flowType_liuliang2').show();
		} else if (businessType == 1) {
			$('#flowType_liuliang1').hide();
			$('#flowType_liuliang2').hide();
		}
	}
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="流量话费运营商产品管理" style="padding: 2px">
			<table id="_productList"></table>
			<!-- toolbar="#tb" -->
			<div id="xxDgTb">
				<form id="queryForm" method="get">
					<table cellpadding="1">
						<tr>
							<td><b>运营商：</b> <select class="sel" name="yysId" id="yysId"
								style="width: 100px; padding-left: 0px;">
									<option value="">--请选择--</option>
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option>
							</select></td>
							<td><b>省份：</b> <select id="provinceId_search" name="provinceId_search"
								style="width: 100px; padding-left: 0px;">
									<option value="">--请选择--</option>
									<option value="000">全国QG</option>
							</select></td>
							<td><b>包类型：</b> <select class="sel"
								name="flowTypeByProvinceId" id="flowTypeByProvinceId"
								style="width: 100px; padding-left: 0px;">
									<option value="">--请选择--</option>
									<option value="0">漫游</option>
									<option value="1">本地</option>
									<option value="-1">其它</option>
							</select></td>
							<td><b>面值：</b> <select id="flowSize" name="flowSize"
								style="width: 100px; padding-left: 0px;">
									<option value="">--请选择--</option>
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
								id="businessType" style="width: 100px; padding-left: 0px;">
									<option value="">--请选择--</option>
									<option value="0">流量</option>
									<option value="1">话费</option>
							</select><b>通道：</b><select class="sel" name="channelId"
								id="channelIdSelect" style="width: 180px; padding-left: 0px;">
									<option value="">--请选择--</option>
							</select><b>&nbsp;产品名称：</b><input type="text" name="flowName"
								style="width: 130px; padding-left: 0px;"></td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								style="width: 100px; margin-top: -5px"
								data-options="iconCls:'icon-search'" id="search_id">搜索</a></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td><a href="javascript:;" class="easyui-linkbutton"
								data-options="iconCls:'icon-add'" id="add_product"
								onclick="showEdit(0);">新增</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="webinformation1" style="margin-top: 10px;">
				<form id="operatorProductDetail" method="post">
					<table cellpadding="2">
						<tr>
							<td>运营商产品名称：</td>
							<td><input type="text" id="name" name="name"><font
								class="text_red">*</font></td>
							<td style="padding-left: 50px">面值大小：</td>
							<td><input type="text" id="size" name="size"><font
								class="text_red">*</font>(MB/元)</td>
						</tr>
						<tr>
							<td>通道：</td>
							<td><input id="channelId" name="channelId"><font
								class="text_red">*</font></td>
							<td style="padding-left: 50px">售价：</td>
							<td><input type="text" id="priceOp" name="priceOp"><font
								class="text_red">*</font>(元)</td>
						</tr>
						<tr>

							<td>运营商：</td>
							<td><select id="yysTypeId" name="yysTypeId">
									<option value="1">电信</option>
									<option value="2">移动</option>
									<option value="3">联通</option>
							</select><font class="text_red">*</font></td>
							<td style="padding-left: 50px">省份：</td>
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
						</tr>

						<tr>
							<td>状态：<input type="hidden" id="id" name="id" value=""></td>
							<td><input type="radio" id="status" name="status" value="0"
								checked>&nbsp;&nbsp;可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
								id="status" name="status" value="1">&nbsp;&nbsp;不可用<font
								class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</font></td>
							<td style="padding-left: 50px">订购编码：</td>
							<td><input type="text" id="orderCode" name="orderCode"><font
								class="text_red">*</font></td>
						</tr>

						<tr>
							<td>业务类型：</td>
							<td><input type="radio" id="businessType"
								name="businessType" value="0" checked onclick="radio_onclick(0)">&nbsp;&nbsp;流量
								&nbsp;&nbsp;&nbsp; <input type="radio" id="businessType"
								name="businessType" value="1" onclick="radio_onclick(1)">&nbsp;&nbsp;话费&nbsp;&nbsp;&nbsp;<input
								type="radio" id="businessType" name="businessType" value="2"
								onclick="radio_onclick(1)">&nbsp;&nbsp;物业缴费&nbsp;&nbsp;&nbsp;<input
								type="radio" id="businessType" name="businessType" value="3"
								onclick="radio_onclick(1)">&nbsp;&nbsp;加油卡&nbsp;&nbsp;&nbsp;<font
								class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</font></td>
							<td id="flowType_liuliang1" style="display:; padding-left: 50px">流量类型：</td>
							<td id="flowType_liuliang2" style="display:"><input
								type="radio" id="flowType" name="flowType" value="0" checked>&nbsp;&nbsp;漫游流量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="flowType" name="flowType" value="1">&nbsp;&nbsp;本地流量<font
								class="text_red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*</font></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>通道折扣：</td>
							<td><input id="channelDiscount" name="channelDiscount"><font
								class="text_red">*</font>(三位数字)</td>
						</tr>
						<tr>
							<td></td>
							<td><font class="text_red" style="font-size: 10px">例:5.5折输入550,
									2折输入200, 7.85折输入 785</font></td>
							<td style="padding-left:50px">备注:</td><td><textarea style="height: 50px;" id="remarks" name ="remarks"></textarea></td>
						</tr>
					</table>
					<div style="margin: 20px 0;">
						<a href="#" class="btn btn-primary" onclick="edit()">保存</a>
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
