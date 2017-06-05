<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
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
							url : '${ctx}/product/getEnterpriseProductList',
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
										field : 'name',
										title : '产品包名称',
										width : 200,
										align : "center"
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
											} else if (row.yysTypeId == 4){
												return '中石化';
											} else {
												return '中石油';
											}
										}
									},
									{
										field : 'flowType',
										title : '产品包类型',
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
										field : 'size',
										title : '产品包大小',
										width : 100,
										align : "center"
									},
									{
										field : 'discount',
										title : '产品包折扣',
										width : 100,
										align : "center",
										formatter : function(val, row, index) {
											if (row.discount) {
												return row.discount/100;
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
											} else if(row.businessType == 1){
												return '<span >话费</span>';
											} else {
												return '<span >加油卡</span>';
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
														+ '" style="color:#fff;background:green">已上架</span>';
											} else {
												return '<span id="yxj'
														+ row.id
														+ '" style="color:#fff;background:red"">已下架</span>';
											}
										}
									},
									 {
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
		$("#search_id").click(function() {
			var params = $('#_productList').datagrid('options').queryParams;
			var fields = $('#queryForm').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#_productList').datagrid('load');
		});
		
		loadParentAllPacket();
	});

	function closeDf() {
		$('#dfxqDlg').dialog('close');
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
	
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="产品信息" style="padding: 2px">
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
									<option value="4">中石化</option>
									<option value="5">中石油</option>
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
									<option value="3">加油卡</option>
							</select>
							<td><b>&nbsp;产品名称：</b><input
								type="text" name="flowName"
								style="width: 130px; padding-left: 0px;"></td>
							<td><a href="javascript:;"
										class="btn btn-default"
										style=" margin-left: 95px"
										data-options="iconCls:'icon-search'" id="search_id"><i class="fa fa-search"></i>&nbsp;搜索</a></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
