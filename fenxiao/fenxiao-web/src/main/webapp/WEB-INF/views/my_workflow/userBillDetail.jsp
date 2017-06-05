<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账单详情</title>
<script>
$(function(){
	$('#pushInfoDlg').dialog({
		title: '自定义发送消息/短信',
		 width: 550,
		 height: 520,
		 closed: true,
		 cache: false,
		 modal: true,
	});
});

function addRemark(){
		$('#remark_form').form('submit', {//updateEduZone
		 url:"${ctx}/myWorkflow/dunningRecord/addRemark",
       	 onSubmit: function(param){
       	},
        success:function(data){
   	    	var d=eval("("+data+")");
   	    	if(d.resultCode==1){
   	    		$.messager.alert("消息","添加成功","info",function(){
   	    		 location.reload();
   	    		}); 
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
function pushMessageInfo(id){
	$('#pushInfoDlg').dialog('open');
}
function sendMessage(id){
 	$.messager.confirm('确认','确认发送短信\消息?',function (data){
		//短信内容
		var content = $('#contentMessage').val();
		var title = $('#title').val();
		if(content.length==0){
			$.messager.alert('消息',"请输入消息内容");
		}else{
			$.getJSON("${ctx}/infoManage/sendUserMessageToOne?userId="+id+"&content="
					+encodeURI(encodeURI(content))+"&title="
					+encodeURI(encodeURI(title)),function(data){
				if(data.resultCode==1){
					$('#pushInfoDlg').dialog("close");
					$.messager.alert('消息',"发送成功");
				}
			});
		}
	});	 
}
function closeDlg(){
	$('#pushInfoDlg').dialog("close");
}
</script>
</head>
<body>	
<div class="ymnbj">
<h3 align="center">账单明细</h3>
<hr/>
<div><span>帐单基础信息:</span></div>
<table class="table table-bordered">
	<tr>
		<td width="15%">账单编号:</td>
		<td width="35%">${userbill.billCode}</td>
		<td width="15%">账单开始时间:</td>
		<td width="35%"><fmt:formatDate  value="${userbill.startTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
	</tr>
	<tr>
		<td width="15%">账单结束时间:</td>
		<td width="35%"><fmt:formatDate  value="${userbill.billDate}" type="both" pattern="yyyy.MM.dd" /></td>
		<td width="15%">最后还款时间:</td>
		<td width="35%"><fmt:formatDate  value="${userbill.repaymentDate}" type="both" pattern="yyyy.MM.dd" /></td>
	</tr>
	<tr>
		<td width="15%">金额:</td>
		<td width="35%">${userbill.consumeAmount}</td>
		<td width="15%">提现手续费:</td>
		<td width="35%">${userbill.withdrawAmount}</td>
	</tr>
	<tr>
		<td width="15%">分期手续费:</td>
		<td width="35%">${userbill.installmentInterest}</td>
		<td width="15%">逾期手续费:</td>
		<td width="35%">${userbill.overdueAmount}</td>
	</tr>
	<tr>
		<td width="15%">应还金额:</td>
		<td width="35%">${userbill.consumeAmount+userbill.withdrawAmount+userbill.installmentInterest+userbill.overdueAmount}</td>
		<td width="15%">账单延期次数</td>
		<td width="35%">${count }</td>
	</tr>
	<tr>
		<td width="15%">逾期天数:</td>
		<td width="35%">${userbill.overdueDay}</td>

	</tr>
</table>
<div><span>基础信息</span></div>
<table class="table table-bordered">
	<tr>
		<td width="11%">姓名:</td>
		<td width="22%"><a href='${ctx}/usermanagement/userupdate/userUpdate?id=${userbill.userId}'>${userbill.userName}</a></td>
		<td width="11%">会员ID:</td>
		<td width="22%">${user.id} </td> 
		<td width="11%">帐号:</td>
		<td>${userbill.userMphone}</td>
	</tr>
	<tr>
		<td>学校:</td>
		<td>${userbill.schoolName}</td>
		<td>院系:</td>
		<td>${userbill.facultyName}</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>信用额度:</td>
		<td>${userAccount.creditAmount}</td>
		<td>剩余额度:</td>
		<td>${userAccount.availableAmount}</td>
		<td></td>
		<td></td>
	</tr>
</table>
<div><span>账单交易明细</span></div>
<table class="table table-bordered">
	<tr><td>消费类型</td><td>创建时间</td><td>处理时间</td><td>订单号</td><td>会员姓名</td><td>消费产品</td><td>消费金额</td><td>商户</td></tr>
	<c:forEach var="order" items="${vUserOrders}" varStatus="index">
	<tr>
		<%-- <c:if test="${order.type == 1}">
		<td>线下消费</td>
		</c:if> --%>
		<c:choose>  
   		<c:when test="${order.type == 1}">
   		<td>线下消费</td>
   		</c:when>
   		<c:when test="${order.type == 2}">
   		<td>网购代付</td>
   		</c:when>  
   		<c:when test="${order.type == 3}">
   		<td>用户提现</td>
   		</c:when>
   		<c:when test="${order.type == 4}">
   		<td>全额还款</td>
   		</c:when>
   		<c:when test="${order.type == 5}">
   		<td>分期还款</td>
   		</c:when>
   		<c:when test="${order.type == 6}">
   		<td>钱包提现 </td>
   		</c:when>
		</c:choose>  
		<td><fmt:formatDate  value="${order.createTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
		<td><fmt:formatDate  value="${order.updateTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
		<td>${order.orderCode}</td>
		<td>${order.userName}</td>
		<td>${order.productName}</td>
		<td>${order.amount}</td>
		<td>${order.shopName}</td>
	</tr>
	</c:forEach>
</table>
<c:if test="${userbill.isInstalments==1}">
<div><span>分期还款记录</span></div>
<table class="table table-bordered">
	<tr><td>最后还款日</td><td>期数</td><td>分期订单创建时间</td><td>还款金额</td><td>逾期金额</td><td>应还金额</td><td>还款状态</td><td>还款日期</td></tr>
	<c:forEach var="billrecord" items="${billrecords}" varStatus="index">
	<tr>
		<td><fmt:formatDate  value="${billrecord.endDate}" type="both" pattern="yyyy.MM.dd" /></td>
		<td>${billrecord.number}</td>
		<td><fmt:formatDate  value="${billrecord.createTime}" type="both" pattern="yyyy.MM.dd" /></td>
		<td>${billrecord.amount}</td>
		<td>${billrecord.overdueAmount}</td>
		<td>${billrecord.amount+billrecord.overdueAmount}</td>
		<c:choose>
		<c:when test="${billrecord.status == 0}">
   		<td>未还款</td>
   		</c:when>
		<c:when test="${billrecord.status == 1}">
   		<td>已还款</td>
   		</c:when>
   		<c:when test="${billrecord.status == 2}">
   		<td style="color: red;">逾期${billrecord.overdueDay}天</td>
   		</c:when>
   		<c:when test="${billrecord.status == 3}">
   		<td>银行处理中</td>
   		</c:when>
		</c:choose>
		<td><fmt:formatDate  value="${billrecord.repaymenetDate}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
	</tr>
	</c:forEach>
</table>
</c:if>
<div><span>催款记录</span></div>
<table class="table table-bordered">
	<tr><td>催款时间</td><td>催款人</td><td>备注信息</td><td></td><td></td></tr>
 	<c:forEach var="duninngRecord" items="${duninngRecords}" varStatus="index">
	<tr>
		<td><fmt:formatDate  value="${duninngRecord.createTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
		<td>${duninngRecord.adminInfo.realName}</td>
		<td>${duninngRecord.remark}</td>
	</tr>
	</c:forEach> 
</table>
<form id="remark_form">
<input type="hidden" name="id" value="${userbill.id}">
<table>
<tr>
<td>
<textarea id="remark" name="remark" cols="20" rows="5">
</textarea></td>
<td>
<button type="button" onclick="addRemark()">添加备注</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" onclick="pushMessageInfo(${userbill.userId});" class="btn btn-warning" >推送消息/短信</button>
</td>
</tr>
</table>
</form>
</div>
	<div id="pushInfoDlg">
		<form action="">
			<p>
			<p>
				请输入标题 :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="title" />
			<p>
				请输入消息内容：
				<textarea rows="" id="contentMessage" cols=""
					style="width: 300px; height: 200px"></textarea><p>
			<div align="center">
				<button type="button" onclick="sendMessage(${userbill.userId})"
					id="id" class="btn btn-warning">发送</button>
					<button type="button" onclick="closeDlg();" class="btn btn-default">取消</button>
			</div>
		</form>
	</div>
</body>
</html>