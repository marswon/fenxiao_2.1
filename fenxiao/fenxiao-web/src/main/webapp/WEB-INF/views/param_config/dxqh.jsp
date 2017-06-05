<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信切换</title>
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
function saveDxqh(dxqh){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/saveDxqh',{
		status:dxqh
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
      				<th style="width: 50%"><span>短信切换状态</span></th>
      				<th>短信公司</th>
      			</tr>
      			<tr>
      				<td align="center">短信切换</td>
      				<td align="center">
      					<button type="button" id="button2" value="${dxqh}" onclick="saveDxqh('${dxqh}')" class="btn" style="width: 100px;">${dxqhStatus}</button>
      				</td>
      			</tr>
      		</table>
   		</form>
    </div>
</div>
</body>
</html>