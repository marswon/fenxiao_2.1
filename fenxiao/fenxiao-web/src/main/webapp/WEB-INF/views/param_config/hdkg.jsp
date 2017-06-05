<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动开关</title>
<style type="text/css">
.table_inner{
  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
  width:100%;
  border-collapse:collapse;
  margin-bottom: 20px;
}
.table_inner input[type="text"]{
	margin: 0 5px 0 5px;
	width:50px;
}
.table_inner select{
	margin: 0 5px 0 5px;
	width:100px;
}
.table_inner td,th{
  	border:1px solid #000;
}
.table_td td{
	 padding:3px 7px 2px 7px; 
}
.table_inner th{
	font-size: 16px;
	color:white;
	height: 40px;
	background-color: #6699CC;
}
.table_inner td{
	font-size: 12px;
	height: 40px;
}
.gsh_input{
	display: inline-block;
	margin-left:5px;
	margin-right:5px;
	width: 50px;
}
.ydq{
	text-align: right;
}
#fqfl_xq_tr input[type="text"]{
	margin:0;
	padding:0;
/* 	border:0; */
	/* width: 50%;
	height: 100%; */
	text-align: center;
}
#hkfl_div{
	margin-top:50px;
	margin-bottom: 50px;
}
#yqzdtx_div{
	margin-bottom: 50px;
}
#fqfl_div{
	margin-bottom: 50px;
}
#yqtx_arr_table td{
	padding:0;
	border: none;
}
.bcxg_btn{
	margin-left: 210px;
}
.table_title{
	display:inline-block;
	margin-bottom:5px;
	padding-right:10px;
	background-color: #c7c7c7;
}
</style>
<script>
function saveHdkg(hdkg){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/savePphdkg',{
		status:hdkg
	},function(data){
		$.messager.progress("close");
		 if (data.resultCode==1){
			 window.location.reload();
			} else if(data.resultCode==-1){
				$.messager.alert('消息', data.resultMsg);
			} 
	});
}
function saveTjrjlkg(tjrjlkg){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/saveTjrjlkg',{
		status:tjrjlkg,
		amount:$("#amount").val()
	},function(data){
		$.messager.progress("close");
		 if (data.resultCode==1){
			 window.location.reload();
			} else {
				$.messager.alert('消息', data.resultMsg);
			} 
	});
}
function saveTxshkg(txshkg){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/saveTxshkg',{
		status:txshkg
	},function(data){
		$.messager.progress("close");
		 if (data.resultCode==1){
			 window.location.reload();
			} else {
				$.messager.alert('消息', data.resultMsg);
			} 
	});
}
//钱包提现审核开关
function saveQbtxshkg(qbtxshkg){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/saveQbtxshkg',{
		status:qbtxshkg
	},function(data){
		$.messager.progress("close");
		if (data.resultCode==1){
			window.location.reload();
		} else {
			$.messager.alert('消息', data.resultMsg);
		}
	});
}

function saveXjrzkg(xjrzkg){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/saveXjrzkg',{
		status:xjrzkg
	},function(data){
		$.messager.progress("close");
		 if (data.resultCode==1){
			window.location.reload();
		 } else {
				$.messager.alert('消息', data.resultMsg);
			} 
	});
}

function saveXxwrzzdsh(xxwrzzdsh){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/saveXxwrzzdshKg',{
		status:xxwrzzdsh
	},function(data){
		$.messager.progress("close");
		 if (data.resultCode==1){
			window.location.reload();
		 } else {
				$.messager.alert('消息', data.resultMsg);
			} 
	});
}
</script>
</head>
<body>
<div style="margin-left: 20px;margin-right: 50px;">
    <!-- 活动开关 -->
     <div id="hkfl_div">
   		<form id="hkfl_form" method="post">
         	<table class="table_inner table_td" style="width: 600px;">
      			<tr>
      				<th style="width: 50%"><span>活动名称</span></th>
      				<th>操作</th>
      			</tr>
      			<tr>
      				<td align="center">拼拼活动开关</td>
      				<td align="center"><button type="button" onclick="saveHdkg('${pphdkg}')" class="btn" style="width: 100px;">${pphdkgStatus}</button></td>
      			</tr>
      		</table>
      		<table class="table_inner table_td" style="width: 600px;">
      			<tr>
      				<th style="width: 50%"><span>活动名称</span></th>
      				<th>投放金额</th>
      				<th>已领取</th>
      				<th>操作</th>
      			</tr>
      			<tr>
      				<td align="center">推荐人奖励</td>
      				<td align="center"><input type="text" name="amount"  id="amount" value="${totalAward}"/>元</td>
      				<td align="center">${totalRecAward}元</td>
      				<td align="center"><button type="button" onclick="saveTjrjlkg('${tjrkg}')" class="btn" style="width: 100px;">${tjrkgStatus}</button></td>
      			</tr>
      		</table>
      		<table class="table_inner table_td" style="width: 600px;">
      			<tr>
      				<th style="width: 50%"><span>开关名称</span></th>
      				<th>操作/状态</th>
      			</tr>
      			<tr>
      				<td align="center">额度提现订单：自动审核/人工审核</td>
      				<td align="center">
      					<button type="button" onclick="saveTxshkg('${txshkg}')" class="btn" style="width: 100px;">${txshkgStatus}</button>
      				</td>
      			</tr>
      		</table>
			<table class="table_inner table_td" style="width: 600px;">
				<tr>
					<th style="width: 50%"><span>开关名称</span></th>
					<th>操作/状态</th>
				</tr>
				<tr>
					<td align="center">钱包提现订单：自动审核/人工审核</td>
					<td align="center">
						<button type="button" onclick="saveQbtxshkg('${qbtxshkg}')" class="btn" style="width: 100px;">${qbtxshkgStatus}</button>
					</td>
				</tr>
			</table>
      		<table class="table_inner table_td" style="width: 600px;">
      			<tr>
      				<th style="width: 50%"><span>学籍认证状态</span></th>
      				<th>操作/状态</th>
      			</tr>
      			<tr>
      				<td align="center">学信网认证</td>
      				<td align="center">
      					<button type="button" id="button1" value="${xjrzkg}" onclick="saveXjrzkg('${xjrzkg}')" class="btn" style="width: 100px;">${xjrzkgXxwrzStatus}</button>
      				</td>
      			</tr>
      		</table>
      		<table class="table_inner table_td" style="width: 600px;">
      			<tr>
      				<th style="width: 50%"><span>学信网认证状态</span></th>
      				<th>操作/状态</th>
      			</tr>
      			<tr>
      				<td align="center">学信网认证自动审核</td>
      				<td align="center">
      					<button type="button" id="button2" value="${xxwrzzdshkg}" onclick="saveXxwrzzdsh('${xxwrzzdshkg}')" class="btn" style="width: 100px;">${xxwrzzdshStatus}</button>
      				</td>
      			</tr>
      		</table>
   		</form>
    	</div>
</div>
</body>
</html>