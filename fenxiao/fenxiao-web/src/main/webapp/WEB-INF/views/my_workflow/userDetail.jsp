<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员资料</title>
<script>
$(function(){
	//通过或未通过选择单击事件
	showBtgDiv();
	 $("input[name=status]").click(function(){
		 showBtgDiv();
		});
	 //不通过原因下拉选择事件
	 $("#btg_select").change(function(){
		 var s_value=$("#btg_select").val();
		 var s_text=$("#btg_select :selected").text();
		 if(s_value.length==0){
			 return;
		 }
		 addYy(s_value, s_text);
		});
	 $('#imgShowDlg').dialog({
		    title: '图片展示',
		    //fit:true,
		    //draggable:true,
		    width:450,
		    height:500,
		    resizable:true,
		    closed: true,
		    cache: false,
		    modal: false
		});
});
//显示或隐藏不通过信息相关
function showBtgDiv(){
 switch($("input[name=status]:checked").attr("id")){
  case "status1":
   $("#btg_div").hide();
   $('#btgxx_div').empty();
   break;
  case "status2":
   $("#btg_div").show();
   break;
  default:
   break;
 }
}
function closeRz(){
	window.parent.closeRz();
}
function removeYy(btn){
	$(btn).parent().remove();
}
function addYy(id,text){
	var yy_input_id="yy"+id;
    var input=$('#btgxx_div').find('#'+yy_input_id);
	 if(input.length>0){
	 }else{
   	var parentDiv=$('<div style="margin:5px 0 5px 0"></div>');
   	var deleteBtn=$('<button type="button" onclick="removeYy(this)" class="btn btn-default">删除</button>');
	 	var yy_input=$('<input type="text" style="margin: 0 5px 0 0; width:50%" readonly="readonly"/>');
	 	yy_input.val(text);
	 	yy_input.attr('id',yy_input_id);
	 	parentDiv.append(yy_input).append(deleteBtn);
	 	$('#btgxx_div').append(parentDiv);
	 }
}
//认证提交
function authSubmit(){
	$.messager.confirm('确认', '是否执行该操作？', function(r){
		if (r){
			var userId=$("#userId").val();
			var status=$("input[name=status]:checked").val();
			var btgyy="";
			if(status==3){
				//认证不通过
				$.each($('#btgxx_div').find('input'),function(key,value){
					if(btgyy.length==0){
						btgyy+=value.value;
					}else{
						btgyy+=';'+value.value;
					}
				});
				var btgyy_bz=$("#btgxx_textarea").val().trim();
				if(btgyy.length==0&&btgyy_bz.length==0){
					$.messager.alert('消息', "请选择或输入审核错误原因");
					return;
				}
				 if(btgyy.length==0){
					btgyy+='\n备注:'+btgyy_bz;
				}else{
					if(btgyy_bz.length>0){
						btgyy+="\n备注:"+btgyy_bz;
					}
				} 
			}
			$.messager.progress({
				title:'处理中',
				msg:'请稍后',
			}); 
			$.post('${ctx}/myWorkflow/userAuth/authUser',{
				id:userId,
				authFailMsg:btgyy,
				authenticateStatus:status,
				type:1
			},function(data){
				$.messager.progress('close');
				 if (data.resultCode==1){
					 	window.parent.$.messager.alert("消息","操作成功");
		    			window.parent.closeRz();
		    			window.parent.loadDrzDG();
					} else {
						$.messager.alert('消息', data.resultMsg);
					}
			});
		}
	});
}
function showImg(imgPath,title){
	$("#imgShow").attr("src",imgPath);
	$('#imgShowDlg').dialog("setTitle",title);
	$('#imgShowDlg').dialog("open");
}

function queryTdScore() {
		$.messager.confirm('确认', '请求同盾获取提现分数？', function(r) {
			if (r) {
				var userId = $("#userId").val();
				var withdrawApplyId = ${WithdrawId};
				$.messager.progress({
					title : '处理中',
					msg : '请稍后',
				});
				$.post('${ctx}/myWorkflow/queryTdScore', {
					userId : userId,
					withdrawApplyId : withdrawApplyId,
				}, function(data) {
					$.messager.progress('close');
					if (data.resultCode == 1) {
					        	window.parent.$.messager.alert("消息", "操作成功");
								window.location.reload();
					} else {
						$.messager.alert('消息', data.resultMsg);
					}
				});
			}
		});
	}
</script>
</head>
<body>
<div class="ymnbj">
<input type="hidden" id="userId" value="${user.id}"/>
<h3 align="center">会员资料</h3>
<hr/>
<div><h3>认证信息</h3></div>
<div><span>个人信息:</span></div>
<table class="table table-bordered">
	<tr>
		<td width="11%">姓名:</td>
		<td width="22%">${user.name}</td>
		<td width="11%">性别:</td>
		<td width="22%">${user.sexString}</td>
		<td width="11%">籍贯:</td>
		<td>${user.homeTown}</td>
	</tr>
	<tr>
		<td>身份证号:</td>
		<td>${authRealName.idCode}</td>
		<td>手机号码:</td>
		<td>${user.mphone}</td>
		<td>邮箱:</td>
		<td>${user.email}</td>
	</tr>
	<tr>
		<td>注册时间:</td>
		<td><fmt:formatDate  value="${user.createTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
		<td>提交认证时间:</td>
		<td><fmt:formatDate  value="${user.authSubmitTime}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
		<td>LBS:</td>
		<td>${authRealName.lbs}</td>
	</tr>
</table>
<!--@authr ggx  -->
<br>
<div><span>学信网信息:</span></div>
<table class="table table-bordered">
	<tr>
		<td width="11%">姓名：</td>
		<td width="22%">${auth.name }</td>
		<td width="11%">性别：</td>
		<td width="22%">${auth.sex }</td>
		<td width="11%">民族：</td>
		<td width="22%">${auth.nation }</td>
	</tr>
	<tr>
		<td width="11%">出生日期：</td>
		<td width="22%">${auth.birthday }</td>
		<td width="11%">身份证号：</td>
		<td width="22%">${auth.cardId }</td>
		<td width="11%">考生号：</td>
		<td width="22%">${auth.candidateId }</td>
	</tr>
	<tr>
		<td width="11%">学号：</td>
		<td width="22%">${auth.studentId }</td>
		<td width="11%">院校名称：</td>
		<td width="22%">${auth.academy }</td>
		<td width="11%">分院：</td>
		<td width="22%">${auth.branch }</td>
	</tr>
	<tr>
		<td width="11%">系(所、函授站)：</td>
		<td width="22%">${auth.department }</td>
		<td width="11%">专业名称：</td>
		<td width="22%">${auth.major }</td>
		<td width="11%">班级：</td>
		<td width="22%">${auth.classs }</td>
	</tr>
	<tr>
		<td width="11%">层次：</td>
		<td width="22%">${auth.degree }</td>
		<td width="11%">学制：</td>
		<td width="22%">${auth.time }</td>
		<td width="11%">学历类别：</td>
		<td width="22%">${auth.majorType }</td>
	</tr>
	<tr>
		<td width="11%"> 学习形式：</td>
		<td width="22%">${auth.studyType }</td>
		<td width="11%">入学日期：</td>
		<td width="22%">${auth.major }</td>
		<td width="11%">学籍状态：</td>
		<td width="22%">${auth.state }</td>
	</tr>
	<tr>
		<td width="11%">预计毕业日期：</td>
		<td width="22%">${auth.offDate }</td>
		<td width="11%">学籍半身照：</td>
		<td style="height: 300px;"><a href="javascript:;" onclick="showImg('${ctx}/upload${auth.schoolAvatar}','学籍半身照')"><img style="display: block;width: 100%;height: 100%" src="${ctx}/upload${auth.schoolAvatar}"/></a></td>
		<td width="11%">学籍半身照2：</td>
		<td style="height: 300px;"><a href="javascript:;" onclick="showImg('${ctx}/upload${auth.schoolAvatar_2}','学籍半身照')"><img style="display: block;width: 100%;height: 100%" src="${ctx}/upload${auth.schoolAvatar_2}"/></a></td>
	</tr>
</table>
<br>
<!--  -->
<div><span>学籍信息:</span></div>
<table class="table table-bordered">
	<tr>
		<td width="11%">学校:</td>
		<td width="22%">${authSchoolRoll.school.name}</td>
		<td width="11%">院系:</td>
		<td width="22%">${authSchoolRoll.faculty.name}</td>
		<td width="11%">入学年份:</td>
		<td>${authSchoolRoll.year}</td>
	</tr>
	<tr>
		<td>寝室地址:</td>
		<td>${authSchoolRoll.roomAddr}</td>
		<td>年制:</td>
		<td>${authSchoolRoll.totalYear}</td>
		<td></td>
		<td></td>
	</tr>
</table>
<div><span>银行卡信息:</span></div>
<table class="table table-bordered">
	<tr>
		<td width="11%">开户银行:</td>
		<td width="22%">${authCard.bankName}</td>
		<td width="11%">银行卡号:</td>
		<td width="22%">${authCard.cardNum}</td>
		<td width="11%"></td>
		<td></td>
	</tr>
</table>
<table class="table table-bordered">
	<tr>
		<td width="33%">身份证:</td>
		<td width="33%">手持证件半身照:</td>
		<td>学生证或校园一卡通或市民卡:</td>
	</tr>
	<tr>
		<td style="height: 300px;"><a href="javascript:;" onclick="showImg('${ctx}/upload${authRealName.idPic}','身份证')"><img style="display: block;width: 100%;height: 100%" src="${ctx}/upload${authRealName.idPic}"/></a></td>
		<td style="height: 300px;"><a href="javascript:;" onclick="showImg('${ctx}/upload${authRealName.personPic}','手持证件半身照')"><img style="display: block;width: 100%;height: 100%" src="${ctx}/upload${authRealName.personPic}"/></a></td>
        <td width="35%" colspan="3">
         	<c:forEach var="detailPic" items="${detailPicList}">
         	    <a href="javascript:;" onclick="showImg('${ctx}/upload${detailPic}','手持学生证半身照')"><img style="display:inline-block; width: 100%;height: 150px;" src="${ctx}/upload${detailPic}"/></a>
         	</c:forEach>
        </td>
	</tr>
</table>
<table class="table table-bordered">
	<tr>
		<td width="11%">绑卡分数:</td>
		<td width="22%">${bkScore.finalScore}</td>
	</tr>
	<tr>
		<td width="11%">绑卡命中规则:</td>
		<td>${bkHitRules}</td>
	</tr>
	<tr>
		<td width="11%">提现分数:</td>
		<td width="22%">${txScore.finalScore}</td>
	</tr>
	<tr>
		<td width="11%">提现命中规则:</td>
		<td>${txRiskItems}</td>
	</tr>
</table>
		<form action="findTdAndTxScore">
			<table class="table table-bordered">
				<tr>
					<td>用户id:</td>
					<td>${user.id}</td>
					<td>提现id:</td>
					<td>${WithdrawId}</td>
					<td></td>
					<td><a href="javascript:;" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" onclick="queryTdScore()">同盾查询分数</a></td>
				</tr>
			</table>
		</form>
	</div>
 <div id="imgShowDlg">
	<img id="imgShow" alt="" src="" style="display: block;width: 100%;height: 100%" >
</div>
</body>
</html>