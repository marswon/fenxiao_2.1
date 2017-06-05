<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="index" value="0" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员模板配置</title>
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
	font-size: 12px;
	height: 40px;
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
	border:0;
	width: 100%;
	height: 100%;
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
function saveHymb(){
	$('#hymb_form').form('submit', {
		url:'${ctx}/userlevel/updateUserLevelConfig1',
       	 onSubmit: function(param){
 			var input = document.getElementsByTagName("input");
       		var txt1 = "";
       	  	 for(var i=0; i<input.length;i++){
       	  	 	if((i)%3==0){
       	  	 	txt1 = txt1+"#";
       	  	 	}
       	  	 	 if(i+1 == input.length){
                  	txt1=txt1+input[i].name+":"+input[i].value;
                  	}else{
                  	txt1=txt1+input[i].name+":"+input[i].value+",";
                  	}
       	  	 }
       		 param.valuelist=txt1;
       	},
        success:function(data){
   	    	var d=eval("("+data+")");
   	    	if(d.resultCode==1){
   	    		$.messager.alert("消息","保存成功");
   	    		location.reload();
   	    	}else {
   	    		if(d.resultMsg){
   	    			$.messager.alert("消息",d.resultMsg);
   	    		}else{
   	    			$.messager.alert("消息","未知错误");
   	    		}
   	    	}
   	    }
	});
}
   
</script>
</head>
<body>
<%-- <div style="margin-left: 20px;margin-right: 50px;">
    <!-- 会员模板配置 -->
     <div id="hkfl_div">
   		<form id="hymb_form" method="post">
   			<span class="table_title">会员模板配置 ：</span>
         	<table class="table_inner table_td">
      			<tr>
      				<th style="width: 15%"><span>用户级别</span></th> 
      				<th colspan="3"><span>配置</span></th>
      			</tr>
      			<c:forEach var="ulc" items="${list}" varStatus="index">
      			<tr>
      				<td class="ydq"><span>${ulc.name}</span></td>
      				<td><span>初始额度：</span><input type="text" required="required" name="creditAmount" id="creditAmount" value="${ulc.creditAmount}"/>
      				<input type="hidden" name="id" value="${ulc.id}"></td>
      				<td><span>基础认证占初始额度：</span><input type="text" required="required" name="baseRate" id="baseRate" value="${ulc.baseRate}"/>%</td>
      				<td><nobr><span>高级认证占初始额度：</span><label>${100-ulc.baseRate}%</label></nobr></td>
      			</tr>
      			 </c:forEach>	
      		</table>
      		<button type="button" onclick="saveHymb()" class="btn btn-warning bcxg_btn">保存修改</button>
   		</form>
    	</div>
</div> --%>
</body>
</html>