<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代付详情</title>
<script>
$(function(){
	//通过或未通过选择单击事件
	showDfshcwxxDiv();
	 $("input[name=status]").click(function(){
		 showDfshcwxxDiv();
		});
	 //不通过原因下拉选择事件
});
//显示或隐藏不通过信息相关
function showDfshcwxxDiv(){
	$("#errMsg").val('');
 switch($("input[name=status]:checked").attr("id")){
  case "status0":
	  $("#dfshcwxxDiv").show();
   break;
  case "status1":
	  $("#dfshcwxxDiv").hide();
   break;
  case "status2":
	  $("#dfshcwxxDiv").show();
   break;
  case "status3":
	  $("#dfshcwxxDiv").hide();
   break;
  case "status4":
	  $("#dfshcwxxDiv").show();
   break;
  default:
   break;
 }
}
function closeDf(){
	window.parent.closeDf();
}
function onlinePayCheck(){
	$.messager.confirm('确认', '是否执行该操作？', function(r){
		if (r){
			var payApplyId=$('#payApplyId').val();
			var errMsg=$("#errMsg").val();
			var status=$("input[name=status]:checked").val();
			$.post('${ctx}/myWorkflow/onlinePay/onlinePayCheck',{
				id:payApplyId,
				errMsg:errMsg,
				status:status
			},function(data){
				 if (data.resultCode==1){
					 window.parent.$.messager.alert("消息","操作成功");
		 			 window.parent.closeDf();
		 			 window.parent.loadDfDg(1);
					} else {
						$.messager.alert('消息', data.resultMsg);
					} 
			});
		}
	});
}
function closeDetail(){
	window.parent.closeDetail();
}
</script>
</head>
<body>	
<div class="ymnbj">
<input type="hidden" id="payApplyId" value="${paymentApply.id}"/>
		<c:choose>
	       <c:when test="${type==1}">
	       	<h3 align="center">代付初审</h3>
	       </c:when>
	       <c:otherwise>
	       	<h3 align="center">代付复审</h3>
	       </c:otherwise>
		</c:choose>
<hr/>
<div><span>交易信息</span></div>
<table class="table table-bordered">
	<tr>
		<td width="15%">平台订单号:</td>
		<td width="35%">${paymentApply.order.orderCode}</td>
		<td width="15%">下单时间:</td>
		<td width="35%"><fmt:formatDate  value="${paymentApply.createTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
	</tr>
	<tr>
		<td width="15%">商品名称:</td>
		<td width="35%">代付商品</td>
		<td width="15%">金额:</td>
		<td width="35%">${paymentApply.amount}</td>
	</tr>
	<tr>
		<td width="15%">支付宝订单号:</td>
		<td width="35%">${paymentApply.payOrderCode}</td>
		<td width="15%">姓名:</td>
		<td width="35%">${paymentApply.userName}</td>
	</tr>
</table>
<div><span>基础信息</span></div>
<table class="table table-bordered">
	<tr>
		<td width="11%">姓名:</td>
		<td width="22%">${user.name}</td>
		<td width="11%">会员ID:</td>
		<td width="22%">${user.id}</td>
		<td width="11%">帐号:</td>
		<td>${user.mphone}</td>
	</tr>
	<tr>
		<td>性别:</td>
		<td>${user.sexString}
		<%-- <c:choose>
	       <c:when test="${user.sex==1}">
	       	男
	       </c:when>
	       <c:when test="${user.sex==2}">
	       	女
	       </c:when>
	       <c:otherwise>
	       	未知
	       </c:otherwise>
		</c:choose> --%></td>
		<td>籍贯:</td>
		<td>${user.homeTown}</td>
		<td>身份证:</td>
		<td>${authRealName.idCode}</td>
	</tr>
	<tr>
		<td>寝室地址:</td>
		<td>${authSchoolRoll.roomAddr}</td>
		<td>学校:</td>
		<td>${authSchoolRoll.faculty.school.name}</td>
		<td>院系:</td>
		<td>${authSchoolRoll.faculty.name}</td>
	</tr>
	<tr>
		<td>入学年份:</td>
		<td>${authSchoolRoll.year}</td>
		<td>注册时间:</td>
		<td><fmt:formatDate  value="${user.createTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td>信用额度:</td>
		<td>${userAccount.creditAmount}</td>
		<td>购买前余额:</td>
		<td>${paymentApply.order.userOriginalAmount}</td>
		<td>购买后余额:</td>
		<td>${paymentApply.order.userOriginalAmount-paymentApply.order.amount}</td>
	</tr>
</table>
<div><span>订单信息</span></div>
<table class="table table-bordered">
	<tr>
		<td width="30%" style="text-align: right;">姓名:</td>
		<td>${paymentApply.userName}</td>
	</tr>
	<tr>
		<td style="text-align: right;">商品名称:</td>
		<td>代付商品</td>
	</tr>
	<tr>
		<td style="text-align: right;">金额:</td>
		<td>${paymentApply.amount}</td>
	</tr>
	<tr>
		<td style="text-align: right;">匹配状态:</td>
		<td>${paymentApply.matchStatusString}</td>
	</tr>
	<tr>
		<td style="text-align: right;">代付地址:</td>
		<td>
			<c:if test="${paymentApply.matchStatus==1}">
				<a href="${paymentApply.payUrl}" target="_blank">${paymentApply.payUrl}</a>
			</c:if>
			<c:if test="${type==2&&operate==true}"><button type="button" onclick="" class="btn btn-default">付款</button></c:if>
		</td>
	</tr>
</table>
<c:choose>
    <c:when test="${operate==true}">
    	<c:if test="${type==1}">
			<table class="table table-bordered">
				<tr>
					<td width="30%" style="text-align: right;">审核结果:</td>
					<td>
						<label style="float: left;margin-left:10px; margin-right: 50px;">
				     	 <input type="radio" id="status1" name="status" value="" checked="checked" style="margin: 0"/><span>通过</span>
				      	</label>
				      	<label style="float: left;">
				     	 <input type="radio" id="status2" name="status" value=""  style="margin: 0"/><span>不通过</span>
				      	</label>
			      	</td>
				</tr>
			</table>
			</c:if>
			<c:if test="${type==2}">
			<table class="table table-bordered">
				<tr>
					<td width="30%" style="text-align: right;">审核结果:</td>
					<td>
						<label style="float: left;margin-left:10px; margin-right: 50px;">
				     	 <input type="radio" id="status3" name="status" value="" checked="checked" style="margin: 0"/><span>通过</span>
				      	</label>
				      	<label style="float: left;margin-right: 50px;">
				     	 <input type="radio" id="status4" name="status" value=""  style="margin: 0"/><span>不通过</span>
				      	</label>
			      		<!-- <label style="float: left;">
				     	 <input type="radio" id="status0" name="status" value="0"  style="margin: 0"/><span>退回初审</span>
				      	</label> -->
			      	</td>
				</tr>
			</table>
			<div style="width:150px;margin-left:330px; background-color: #c6c6c6">初审:${paymentApply.firstAdmin.realName}</div>
			</c:if>
			<div id="dfshcwxxDiv" style="text-align: center;margin-bottom: 10px;">
				<span>审核错误原因:</span>
				<textarea id="errMsg" name="errMsg" rows="5" cols="5"></textarea>
			</div>
			<c:if test="${operate==true}">
			<div align="center" style="margin-bottom: 100px;">
				<button type="button" onclick="onlinePayCheck()" class="btn btn-warning">确认</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" onclick="closeDf()" class="btn btn-default">取消</button>
			</div>
		</c:if>
    </c:when>
    <c:otherwise>
    	<table class="table table-bordered">
				<tr>
					<td width="30%" style="text-align: right;vertical-align: middle;">审核结果:</td>
					<td>
					
						<c:choose>
					       <c:when test="${paymentApply.status==1}">
					       	初审通过
					       </c:when>
					       <c:when test="${paymentApply.status==2}">
					       	复审通过
					       </c:when>
					       <c:when test="${paymentApply.status==3}">
					       	初审未通过<br/>${paymentApply.errMsg}
					       </c:when>
					       <c:when test="${paymentApply.status==4}">
					       	复审未通过<br/>${paymentApply.errMsg}
					       </c:when>
					       <c:otherwise>
					       </c:otherwise>
						</c:choose>
			      	</td>
				</tr>
			</table>
    </c:otherwise>
</c:choose>
</div>
</body>
</html>