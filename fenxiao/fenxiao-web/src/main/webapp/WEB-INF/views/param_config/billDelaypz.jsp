<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>额度配置管理</title>
<style type="text/css">
.table_inner{
  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse:collapse;
  margin-bottom: 20px;
}

.table_inner td,th{
  	border:1px solid #000;
}
.table_td td{
	text-align:center;
	vertical-align:middle;
	padding:3px 7px 2px 7px; 
}

.bcxg_btn{
	margin-left: 210px;
}

.title{
	display:inline-block;
	font-size:40px;
	margin-top:50px;
	margin-bottom:50px;
	padding-right:10px;
}
.table_title{
	display:inline-block;
	font-size:20px;
	margin-bottom:10px;
	padding-right:10px;
}
.img{
	width:95%;
	margin-bottom:30px;
}
#td1{
	width:150px;
}
#td2{
	width:400px;
}
</style>
<script>
function saveZdyqpz(){
	var zdyqbg = document.getElementById("zdyqbg").rows;
	var yqcs = $("#yqcspz").val();
	var sxf = $("#sxfpz").val();
	$.post('${ctx}/paramConfig/saveZdyqpz',{
		yqcs:yqcs,
		sxf:sxf
	},function(data){
		$.messager.progress("close");
		if (data.resultCode==1){
			window.location.reload();
		} else if(data.resultCode==-1){
			$.messager.alert('消息', data.resultMsg);
		} 
	});
}
</script>
</head>
<body>
<div style="margin-left: 20px;margin-right: 50px;">
	<!-- 标题 -->
	<div>
		<span class="title">账单延期配置管理</span>
	</div>
    <!-- 账单延期配置  -->
    <div id="zdyq_div">
   		<form id="zdyq_form" method="post">
   			<span class="table_title">账单延期参数配置：</span></br>
         	<table class="table_inner table_td" id="zdyqbg" name="zdyqbg">
      			<tr>
      				<td id="td1"><span>任务名称</span></td>
      				<td id="td2"><span>参数配置</span></td>
      			</tr>
      			<tr>
      				<td id="td1"><span id="yqcs">延期次数配置</span></td>
      				<td id="td2"><input type="text" style="width:90%" id="yqcspz" value="${delay.delayTimes}"/> 次</td>
      			</tr>
      			<tr>
      				<td id="td1"><span id="sxf">每次延期手续费配置</span></td>
      				<td id="td2"><input type="text" style="width:90%" id="sxfpz" value="${delay.delayRate}"/> %</td>
      			</tr>
      		</table>
      		<button type="button" class="btn btn-warning bcxg_btn" onclick="saveZdyqpz()">保存修改</button>
   		</form>
    </div>
</div>
</body>
</html>