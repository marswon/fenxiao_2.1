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
//添加行  
var rowCount=${list.size()-1}; //默认行数
function addRow(){
	var rowIndex=1;
    rowCount++;  
    var newRow='<tr id="rw'+rowCount+'"><td id="d1"><input type="text" style="width:90%" id="rwmc'+rowCount+'"/></td><td id="d2"><input type="text" style="width:90%" id="edpz'+rowCount+'"/> 元</td><td><a href="#" onclick=delRow('+rowCount+')>删除</a></td></tr>';  
    $('tr:eq(-1)').before(newRow);
} 
//删除行  
function delRow(rowCount){  
    $("#rw"+rowCount).remove();
    rowCount--;  
}
function saveRwedpz(){
	var edpzbg = document.getElementById("edpzbg").rows;
	var list = "";
	for (var i = 1; i < edpzbg.length-1; i++) {
		var t = i-1;
		if ($("#rwmc"+t).html()!="") {
			list = list + t + ":" + $("#rwmc"+t).html() + ":" + $("#edpz"+t).val() + ";";
		}else{
			if ($("#rwmc"+t).val()=="") {
				continue;
			}
			list = list + t + ":" + $("#rwmc"+t).val() + ":" + $("#edpz"+t).val() + ";";
		}
	}
	$.post('${ctx}/paramConfig/saveRwedpz',{
		list:list
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
		<span class="title">额度配置管理</span>
	</div>
    <!-- 任务额度配置  -->
    <div id="rwed_div">
   		<form id="rwed_form" method="post">
   			<span class="table_title">任务额度配置：</span></br>
   			<img class="img" src="${ctx}/static/images/u124_line.png">
         	<table class="table_inner table_td" id="edpzbg" name="edpzbg">
      			<tr>
      				<td id="td1"><span>任务名称</span></td>
      				<td id="td2"><span>额度配置</span></td>
      			</tr>
      			<tr>
      				<td id="td1"><span id="rwmc0">学籍认证</span></td>
      				<td id="td2"><input type="text" style="width:90%" id="edpz0" value="${list.get(0).taskMoney}"/> 元</td>
      			</tr>
      			<tr>
      				<td id="td1"><span id="rwmc1">绑定银行卡</span></td>
      				<td id="td2"><input type="text" style="width:90%" id="edpz1" value="${list.get(1).taskMoney}"/> 元</td>
      			</tr>
      			<tr>
      				<td id="td1"><span id="rwmc2">关联同学</span></td>
      				<td id="td2"><input type="text" style="width:90%" id="edpz2" value="${list.get(2).taskMoney}"/> 元</td>
      			</tr>
      			<c:if test="${list.size()>3}">
      				<c:forEach var="i" begin="4" end="${list.size()}">
      				<tr>
	      				<td id="td1"><span id="rwmc${i-1}">${list.get(i-1).taskName}</span></td>
	      				<td id="td2"><input type="text" style="width:90%" id="edpz${i-1}" value="${list.get(i-1).taskMoney}"/> 元</td>
	      			</tr>
      				</c:forEach>
      			</c:if> 
      			<tr>
      				<td id="td1"></td>
      				<td id="td2"><button type="button" onclick="addRow()">新增</button></td>
      			</tr>
      		</table>
      		<button type="button" class="btn btn-warning bcxg_btn" onclick="saveRwedpz()">保存</button>
   		</form>
    </div>
</div>
</body>
</html>