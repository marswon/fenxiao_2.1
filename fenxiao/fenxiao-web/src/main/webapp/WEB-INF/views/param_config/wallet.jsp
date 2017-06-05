<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>钱包参数配置</title>
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
function saveTjrjl(){
	$('#hkfl_form').form('submit', {
		url:'${ctx}/paramConfig/saveRecommendAward',
       	 onSubmit: function(param){
       	},
        success:function(data){
   	    	var d=eval("("+data+")");
   	    	if(d.resultCode==1){
   	    		$.messager.alert("消息","保存成功");
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
function savePpxjjl(){
	$('#yqzdtx_form').form('submit', {
		url:'${ctx}/paramConfig/addCashAward',
       	 onSubmit: function(param){
       	},
        success:function(data){
   	    	var d=eval("("+data+")");
   	    	if(d.resultCode==1){
   	    		window.location.reload();
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
function deletePpxjjl(amount){
	$.messager.progress({
		title:'处理中',
		msg:'请稍后',
	});
	$.post('${ctx}/paramConfig/deleteCashAward',{
		xjjlmz:amount
	},function(data){
		$.messager.progress("close");
		 if (data.resultCode==1){
			 window.location.reload();
			} else if(data.resultCode==-1){
				$.messager.alert('消息', data.resultMsg);
			} 
	});
}
function saveQbtx(){
	$('#tx_form').form('submit', {
		url:'${ctx}/paramConfig/saveWalletWithdraw',
       	 onSubmit: function(param){
       	},
        success:function(data){
   	    	var d=eval("("+data+")");
   	    	if(d.resultCode==1){
   	    		$.messager.alert("消息","保存成功");
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
<div style="margin-left: 20px;margin-right: 50px;">
    <!-- 钱包奖励配置 -->
     <div id="hkfl_div">
   		<form id="hkfl_form" method="post">
   			<span class="table_title">钱包奖励配置</span>
         	<table class="table_inner table_td">
      			<tr>
      				<th style="width: 15%"><span>配置名称</span></th>
      				<th><span>参数</span></th>
      			</tr>
      			<tr>
      				<td class="ydq"><span>注册推荐人奖励金额:</span></td>
      				<td><span>推荐人</span><input type="text" required="required" name="tjrje" id="tjrje" value="${recommendAward.recAward}"/><span>元，被邀请人</span><input type="text" required="required" name="byqrje" id="byqrje" value="${recommendAward.invitedAward}"/><span>元</span></td>
      			</tr>
      			<tr>
      				<td class="ydq"><span>注册成功奖励金额:</span></td>
      				<td><input type="text" required="required" name="zccgjl"  value="${recommendAward.regAward}"/>元</td>
      			</tr>
      		</table>
      		<button type="button" onclick="saveTjrjl()" class="btn btn-warning bcxg_btn">保存修改</button>
   		</form>
    	</div>
    <!-- 拼拼现金奖励面值配置 -->
    <div id="yqzdtx_div">
		<form id="yqzdtx_form" method="post">
			<span class="table_title">拼拼现金奖励面值配置：</span>
        	<table class="table_inner table_td">
     			<tr>
      				<th style="width: 15%"><span>配置名称</span></th>
      				<th><span>参数</span></th>
      			</tr>
      			<tr>
      				<td class="ydq"><span>拼拼现金奖励面值配置:</span></td>
      				<td><input type="text" required="required" name="xjjlmz" id="xjjlmz" /><span>元</span>&nbsp;&nbsp;&nbsp;<button type="button" onclick="savePpxjjl()">增加</button></td>
      			</tr>
     		</table>
     		<table class="table_inner table_td">
     			<tr>
      				<th style="width: 15%"><span>序号</span></th>
      				<th><span>金额</span></th>
      				<th><span>删除</span></th>
      			</tr>
    			<c:forEach var="ulc" items="${cashAward}" varStatus="index">
	      			<tr>
	      				<td style="text-align: center;"><span>${index.count}</span></td>
	      				<td style="text-align: center;">${ulc}</td>
	      				<td style="text-align: center;"><button type="button" onclick="deletePpxjjl('${ulc}')">删除</button></td>
	      			</tr>
     			</c:forEach>	
     		</table>
    	</form>
    </div>	
    <!-- 钱包提现配置 -->
     <div id="tx_div">
   		<form id="tx_form" method="post">
   			<span class="table_title">钱包提现配置：</span>
         	<table class="table_inner table_td">
      			<tr>
      				<th style="width: 15%"><span>配置名称</span></th>
      				<th><span>参数</span></th>
      			</tr>
      			<tr>
      				<td class="ydq"><span>最低提现额度:</span></td>
      				<td><span>最低额度：</span><input type="text" name="zded" required="required" id="zded" value="${walletWithdraw.lowestCredits}"/></td>
      			</tr>
      			<tr>
      				<td class="ydq"><span>提现手续费:</span></td>
      				<td><span>每笔：</span><input type="text" name="txsxf" required="required" id="txsxf" value="${walletWithdraw.rate}"/><span>%手续费</span></td>
      			</tr>
      			<tr>
      				<td class="ydq"><span>每日可提现次数:</span></td>
      				<td><span>共：</span><input type="text" name="txcs" required="required" id="txcs" value="${walletWithdraw.count}"/><span>次</span></td>
      			</tr>
      		</table>
      		<button type="button" onclick="saveQbtx()" class="btn btn-warning bcxg_btn">保存修改</button>
   		</form>
    	</div>
</div>
</body>
</html>