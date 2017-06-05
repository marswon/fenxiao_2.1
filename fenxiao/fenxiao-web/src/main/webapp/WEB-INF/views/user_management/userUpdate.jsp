<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<title>会员资料修改</title>
<head>
<style type="text/css">
.table_inner {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.table_inner input[type="text"] {
	margin: 0 5px 0 5px;
	width: 50px;
}

.table_inner select {
	margin: 0 5px 0 5px;
	width: 100px;
}

.table_inner td,th {
	border: 1px solid #000;
}

.table_td td {
	padding: 3px 7px 2px 7px;
}

.table_inner th {
	font-size: 12px;
	height: 40px;
}

.table_inner td {
	font-size: 12px;
	height: 40px;
}

.gsh_input {
	display: inline-block;
	margin-left: 5px;
	margin-right: 5px;
	width: 50px;
}

.ydq {
	text-align: right;
}

#fqfl_xq_tr input[type="text"] {
	margin: 0;
	padding: 0;
	border: 0;
	width: 200px;
	height: 100%;
	text-align: center;
}

#hkfl_div {
	margin-top: 50px;
	margin-bottom: 50px;
}

#yqzdtx_div {
	margin-bottom: 50px;
}

#fqfl_div {
	margin-bottom: 50px;
}

#yqtx_arr_table td {
	padding: 0;
	border: none;
}

.bcxg_btn {
	margin-left: 500px;
}

.table_title {
	display: inline-block;
	margin-bottom: 5px;
	padding-right: 10px;
	background-color: #c7c7c7;
}
.jphotogrid{width: 100%;height: auto;margin: 0;padding: 0;float: left;}
.jphotogrid li {list-style: none;width: 200px;height:200px;margin-right:10px;float: left; position: relative;}
.jphotogrid li img {width: 100%;}
</style>
<script type="text/javascript">
	jQuery(function($) {
		/*  if($("#authenticateType").attr("value")<7){
		 	tab_option = $('#tt').tabs('getTab',"高级认证信息").panel('options').tab;
		tab_option.hide();
		 	tab_option = $('#tt').tabs('getTab',"基础认证信息").panel('options').tab;
		tab_option.hide(); 
		 }
		 if($("#authenticateType").attr("value")>=7 && $("#authenticateType").attr("value")<63){
		 	tab_option = $('#tt').tabs('getTab',"高级认证信息").panel('options').tab;
		tab_option.hide();
		 }
		 $('#tt').tabs({
		 	border:false,   
		 	onSelect:function(title,id){  
		 }
		});  */
		$('#facultybox').combobox({
		url:"${ctx}/usermanagement/getAllFaculty",
		 valueField:'id',
		 textField:'name',
		 editable:false,
		 width:150,
		 panelHeight:200,
		 loadFilter:function(data){
			 if(data.content){
				 return data.content;
			 } else{
				 return false;
			 }
		 },
	});
		$('#schoolbox').combobox({
		url:"${ctx}/usermanagement/getAllSchool",
		 valueField:'id',
		 textField:'name',
		 editable:false,
		 width:150,
		 panelHeight:200,
		 loadFilter:function(data){
			 if(data.content){
				 return data.content;
			 } else{
				 return false;
			 }
		 },
		 onChange:function(){
                   var id = $('#schoolbox').combobox('getValue');
                   $('#facultybox').combobox({
				   url:"${ctx}/usermanagement/getAllFaculty?id="+id,
		 		   valueField:'id',
		 	       textField:'name',
			       editable:false,
		           width:150,
		           panelHeight:200,
		         loadFilter:function(data){
			     if(data.content){
				 return data.content;
			     }else{
				 return false;
			    }
		        }, 
		        });
                }
	});
		$("#status").val($("#statushidden").attr("value"));
		$("#rzxxsex").val($("#usersex").attr("value"));
	    $("#schoolbox").combobox('select',$("#jcrzxxschool").attr("value")); 
	    $("#facultybox").combobox('select',$("#jcrzxxfaculty").attr("value"));  
	    
	     $('#imgShowDlg').dialog({
		    title: '图片展示',
		    //fit:true,
		    //draggable:true,
		    width:450,
		    height:460,
		    resizable:true,
		    closed: true,
		    cache: false,
		    modal: false
		});
	    
	});

$(function(){
	 //显示资产证明图片
	 showSrOrZcImg();
	});

	function saveJcrzxx() {
		$.messager.confirm('确认', '是否执行该操作？', function(r){
			if (r){
				$.messager.progress({
					title:'处理中',
					msg:'请稍后',
				}); 
				$('#jcrzxx_form').form('submit', {
					url : "${ctx}/usermanagement/userupdate/isUpdate",//userupdate/revalidation
					onSubmit : function(param) {
					},
					success : function(data) {
						$.messager.progress('close');
						var d = eval("(" + data + ")");
						if (d.resultCode == 1) {
							$.messager.alert("消息", "保存成功");
							window.location.reload();
						} else {
							if (d.resultMsg) {
								$.messager.alert("消息", d.resultMsg);
							} else {
								$.messager.alert("消息", "未知错误");
							}
						}
					}
				});
			}
		});
	}
	
	function saveGjrzxx(){
		$.messager.confirm('确认', '是否执行该操作？', function(r){
			if (r){
				$.messager.progress({
					title:'处理中',
					msg:'请稍后',
				}); 
				$('#gjrzxx_form').form('submit', {
					url : "${ctx}/usermanagement/userupdate/updateGJRZXX",//userupdate/revalidation
					onSubmit : function(param) {
						param.userId=$("#userId").val();
					},
					success : function(data) {
						$.messager.progress('close');
						var d = eval("(" + data + ")");
						if (d.resultCode == 1) {
							$.messager.alert("消息", "保存成功");
						} else {
							if (d.resultMsg) {
								$.messager.alert("消息", d.resultMsg);
							} else {
								$.messager.alert("消息", "未知错误");
							}
						}
					}
				});
			}
		});
	}

/* 	 function saveXyxx() {
		$('#xyxx_form').form('submit', {
			url : '${ctx}/usermanagement/userupdate/unBlockedUser',
			onSubmit : function(param) {
			 var userId=$('#userId').val();
				param.userId=userId;
			},
			success : function(data) {
				var d = eval("(" + data + ")");
				if (d.resultCode == 1) {
					$.messager.alert("消息", "保存成功");
					location.reload();;
				} else {
					if (d.resultMsg) {
						$.messager.alert("消息", d.resultMsg);
					} else {
						$.messager.alert("消息", "未知错误");
					}
				}
			}
		});
	} */
/* function showSrOrZcImg(){
	 var incomeImgPth='${aotsr.imgPth}';
	 var payImgPth='${aotzc.imgPth}';
	 var count=0;
	 if(incomeImgPth.length>0){
		var incomeArr= incomeImgPth.split(",");
		for(var i=0,len=incomeArr.length;i<len;i++){
			var li=$('<li></li>');
			var img=$('<img/>');
			//var path='${ctx}/static/images/20130111141436_vGeKH.jpeg';
			var path='${ctx}/upload'+incomeArr[i];
			img.attr('src',path);
			var p=$('<p>收入证明</p>');
			li.append(img).append(p);
			$('#zczmUl').append(li);
			count++;
		}
	 }
	 if(payImgPth.length>0){
		 var payArr=payImgPth.split(",");
			for(var i=0,len=payArr.length;i<len;i++){
				var li=$('<li></li>');
				var img=$('<img/>');
				//var path='${ctx}/static/images/20130111141436_vGeKH.jpeg';
				var path='${ctx}/upload'+payArr[i];
				img.attr('src',path);
				var p=$('<p>支出证明</p>');
				li.append(img).append(p);
				$('#zczmUl').append(li);
				count++;
			}
	 }
	 if(count>0){
		 var line=0;
		 if(count%4==0){
			 line=parseInt(count/4);
		 }else{
			 line=parseInt(count/4)+1;
		 }
		 $('#zczmUl').css('height',line*120);
	 }else{
		 $('#zczmUl').css('height',0);
	 }
} */

function showSrOrZcImg(){
	 var incomeImgPth='${aotsr.imgPth}';
	 var payImgPth='${aotzc.imgPth}';
	 if(incomeImgPth.length>0){
		var incomeArr= incomeImgPth.split(",");
		for(var i=0,len=incomeArr.length;i<len;i++){
			appendImg('${ctx}/upload'+incomeArr[i],1);
		}
	 }
	 if(payImgPth.length>0){
		 var payArr=payImgPth.split(",");
			for(var i=0,len=payArr.length;i<len;i++){
				appendImg('${ctx}/upload'+payArr[i],2);
			}
	 }
}
function appendImg(imgPath,type){
	var li=$('<li></li>');
	var img=$('<img style="display: block;width: 100%;height:100%"/>');
	var a=$('<a href="javascript:;"></a>');
	var span=$('<span></span>');
	a.append(img);
	img.attr('src',imgPath);
	if(type==1){
		var onclickStr="showImg("+"\'"+imgPath+"\',\'收入证明\')";
		a.attr('onclick',onclickStr);
		span.append("收入证明");
	}else{
		var onclickStr="showImg("+"\'"+imgPath+"\',\'支出证明\')";
		a.attr('onclick',onclickStr);
		span.append("支出证明");
	}
	li.append(a).append(span);
	$('#zczmUl').append(li);
}

function showImg(imgPath,title){
	$("#imgShow").attr("src",imgPath);
	$('#imgShowDlg').dialog("setTitle",title);
	$('#imgShowDlg').dialog("open");
}

function closeDf(){
	window.parent.closeDf();
}
</script>
</head>
<body>
	<div id="tt" class="easyui-tabs">
		<!--  style="width:500px;height:250px; -->
		<div id="hyxxzl" title="会员详细资料" style="overflow:auto;padding:20px;">
			<!-- padding:20px; style="display:none;"-->
		<!-- 	<center>
				<font size=6><b></b>
				</font>
			</center> -->
			<span  style="display:inline-block;font-size: 6px;text-align: center;width: 100%">会员详细资料</span>
			
				<span class="table_title">信用信息</span>
				<table class="table_inner table_td">
					 <tr>
						<td>用户状态：</td>
						 <td><input type="hidden" value="${user.userAccount.status}"
							id="statushidden">
							<input type="hidden" name="useraccountid" value="${user.userAccount.id}">
							<Select id="status" disabled="disabled" name="staus"><Option
									value="1">可用</Option>
								<Option value="0">冻结中</Option>
								</Select>
						</td> 
						<td>信用等级：</td>
						 <td><label>${user.creditLevel.name}</label>
							<input type="hidden" id="hyxxxxcreditLevel" name="creditLevel" value="${user.creditLevel.id}"/>
						</td>  
						<td>信用额度：</td>
						<td><label>${user.userAccount.creditAmount}</label> <input
							type="hidden" class="fqfl_xq_tr" required="required"
							id="creditAmount" name="creditAmount"
							value="${user.userAccount.creditAmount}" />
						</td>
					</tr>
					<tr>
						<td>已消费：</td>
						<td><label>${user.userStatic.totalConsumeAmount}</label> <input
							type="hidden" class="fqfl_xq_tr" required="required"
							name="totalConsumeAmount" id="totalConsumeAmount"
							value="${user.userStatic.totalConsumeAmount}" />
						</td>
						<td>已还款：</td>
						<td><label>${user.userStatic.totalRepayment}</label> <input
							type="hidden" class="fqfl_xq_tr" required="required"
							name="totalRepayment" id="totalRepayment"
							value="${user.userStatic.totalRepayment}" />
						</td>
						<td>负债：</td>
						<td><input type="text" class="fqfl_xq_tr" id="" value="" />
						</td>
					</tr>
					 <tr>
						<td>声望值：</td>
						<td><input type="text" class="fqfl_xq_tr" id="" value="" />
						</td>
					</tr>
				</table>
				<form id="xyxx_form" method="post">
				<span class="table_title">基础信息</span>
				<table class="table_inner table_td">
					<tr>
						<td>姓名：</td>
						<td><input type="text" class="fqfl_xq_tr"
							style="width:211px" required="required" id="name" name="name"
							value="${user.name}" />
						</td>
						<!-- <td>会员ID：</td>
						<td><input type="text" class="fqfl_xq_tr" id="creditLevel"
							value="" /> -->
						<td>账号：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							required="required" name="mphone" id="mphone"
							value="${user.mphone}" /></td>
						<td>寝室地址：</td>
						<td><label>${asr.roomAddr}</label>
						<%-- <input type="text" class="fqfl_xq_tr" style="width:211px"
							id="roomAddr" name="roomAddr" value="${asr.roomAddr}" /> --%>
						</td>
					</tr>
					<tr>
						<td>性别：</td>
						<td>${user.sexString}</td>
						<td>年龄：</td>
						<td><input type="text" class="fqfl_xq_tr" id="" value="" />
						<td>邮箱：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="" value="" />
					</tr>
					<tr>
						<td>籍贯：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="homeTown" name="homeTown" value="${user.homeTown}" />
						<td>身份证：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="idCode" name="idCode" value="${arn.idCode}" /> <input
							type="hidden" name=arnid id=arnid value="${arn.id}"> <input
							type="hidden" name=realname id=realname value="${arn.name}">
						</td>
						<td>手机号：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="" value="" />
						</td>
					</tr>
<!-- 					<tr> -->
						<!-- <td>QQ：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="" value="" /> -->
					<!--<td>生日：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="" value="" />
						</td> -->
						<!-- <td>星座：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="" value="" />
						</td> -->
<!-- 					</tr> -->
					<!-- <tr>
						
					</tr> -->
				</table>

				<span class="table_title">学籍信息</span>
				<table class="table_inner table_td">
					<tr>
						<td>学校：</td>
						<td><label class="fqfl_xq_tr">${user.school.name}</label>
						<input type="hidden" required="required" id="schoolname" name="schoolname" value="${user.school.name}"/>
						</td>
						<td>院系：</td>
						<td><label class="fqfl_xq_tr">${user.faculty.name}</label>
						<input type="hidden" required="required" name="facultyname" id="facultyname" value="${user.faculty.name}"/>
						</td>
						<td>年制：</td>
						<td><label>${asr.totalYear}</label>
						<%-- <input type="hidden" name=asrid id=asrid
							value="${asr.id}"> <input type="text" class="fqfl_xq_tr"
							required="required" id="totalYear" name="totalYear"
							value="${asr.totalYear}" /> --%>
						</td>
					</tr>
					<tr>
						<td>入学时间：</td>
						<td><label>${asr.year}</label>
						<%-- <input type="text" style="width:211px" class="fqfl_xq_tr"
							id="year" name="year" value="${asr.year}" /> --%>
						</td>
						<td>年级：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="" value="" />
						</td>
						<td>学费：</td>
						<td><input type="text" style="width:211px" class="fqfl_xq_tr"
							id="" value="" />
						</td>
					</tr>
					<tr>
						<td>高教园区：</td>
						<td><label class="fqfl_xq_tr">${eduZone.name}</label>
						</td>
					</tr>
				</table>

				<span class="table_title">银行卡信息</span>
				<table class="table_inner table_td">
					<tr>
						<td>开户银行：</td>
						<td><label>${ac.bankName}</label>
						</td>
						<td>银行卡号：</td>
						<td><label>${ac.cardNum}</label>
						<%-- <input style="width:211px" class="fqfl_xq_tr" type="text"
							required="required" id="cardNum" name="cardNum"
							value="${ac.cardNum}" /> --%>
						<td>开户人姓名：</td>
						<td><input style="width:211px" class="fqfl_xq_tr" type="text"
							id="" value="" /></td>
					</tr> 
				</table>
				<div align="center" style="margin-bottom: 100px;">
<!-- 				<button type="button" onclick="onlinePayCheck()" class="btn btn-warning">确认</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
				<button type="button" onclick="closeDf()" class="btn btn-default">取消</button>
			</div>
			 	<!-- <button type="button" onclick="saveXyxx()"
					class="btn btn-warning bcxg_btn">解除冻结</button>  -->
			</form>
		</div>
		<div id="jxrzxx" title="基础认证信息" style="overflow:auto;padding:20px;">
			<center>
				<font size=6><b>基础认证信息</b>
				</font>
			</center>
			<form id="jcrzxx_form" method="post" enctype="multipart/form-data">
				<span class="table_title">会员个人信息：</span>
				<table class="table_inner table_td">
					<tr>
						<td>姓名：</td>
						<td><input type="hidden" name="userId" id="userId"
							value="${user.id}"> <input type="text" id="username" name="name" value="${user.name}" class="fqfl_xq_tr">
						</td>
						<td>性别：</td>
						<td><input type="hidden" id="usersex" value="${user.sex}"/><Select id="rzxxsex" name="sex"><option value="1">男</option>
						<option value="2">女</option>
						</Select>
						<td>籍贯：</td>
						<td><input style="width:40%" type="text" id="jcrzxxhomeTown" name="homeTown" class="fqfl_xq_tr" value="${user.homeTown}" /></td>
					</tr>
					<tr>
					<%-- 	<td>用户级别：</td>
						<td><label class="fqfl_xq_tr">${user.level}</label></td> --%>
						<td>身份证号：</td>
						<td><input type="hidden" id="arnid" name="arnid" value="${arn.id}">
						<input class="fqfl_xq_tr" id="jcrzxxidCode" name="idCode" value="${arn.idCode}"/></td>
						<td>手机号码：</td>
						<td><input class="fqfl_xq_tr" id="jcrzxxmphone" name="mphone" value="${user.mphone}"/></td>
					</tr>
					<tr>
						<td>注册时间：</td>
						<td><label class="fqfl_xq_tr">${user.createTime}</label></td>
						<td>提交认证时间：</td>
						<td><label class="fqfl_xq_tr">${arn.createTime}</label></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<span class="table_title">学籍信息：</span>
				<table class="table_inner table_td">
					<tr>
						 <td>学校：</td>
						<td><%-- <label class="fqfl_xq_tr">${user.school.name}</label>  --%>
							<!-- <input name="schoolId" id="jcrzxxschool"> -->
							<input type="hidden" id="jcrzxxschool" style="width: 95px;" value="${user.faculty.school.id}">
							<input type="text" id="schoolbox" name="schoolId">
						</td>
						<td>院系：</td>
						<td><%-- <label class="fqfl_xq_tr">${user.faculty.name}</label> --%>
							 <input type="hidden" id="jcrzxxfaculty" style="width: 95px;"  value="${user.faculty.id}"> 
							<input type="text" id="facultybox" name="facultyId">
						</td> 
						<td>入学年份：</td>
						<td><input type="hidden" id="asrid" name="asrid" value="${asr.id}">
						<input type="text" id="jcrzxxyear" name="year" value="${asr.year}" /></td>
					</tr>
					<tr>
						<td>寝室地址：</td>
						<td><input style="width:40%" type="text" id="jcrzxxroomaddr" name="roomAddr" class="fqfl_xq_tr" value="${asr.roomAddr}" /></td>
					</tr>
				</table>
				<span class="table_title">LBS定位信息：</span>
				<table class="table_inner table_td">
					<tr>
						<td><input type="text" style="width:500px" class="fqfl_xq_tr"
							id="lbs" name="lbs" value="${arn.lbs}" /></td>
					</tr>
				</table>
				<span class="table_title">银行卡信息：</span>
				<table class="table_inner table_td">
					<tr>
						<td>开户银行：</td>
						<td><input type="hidden" id="acid" value="${ac.id}">
						<input id="jcrzxxbankName" name="bankName" value="${ac.bankName}" class="fqfl_xq_tr" />
						</td>
						<td>银行卡号：</td>
						<td><input id="jcrzxxcardNum" name="cardNum" value="${ac.cardNum}" class="fqfl_xq_tr" />
						</td>
						<td>银行预留手机：</td>
						<td><input id="acmphone" name="acmphone" value="${ac.mphone}" class="fqfl_xq_tr" />
						</td>
					</tr>
				</table>
				<table style="width: 100%">
						<tr>
						<td><span class="table_title">身份证：</span>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td><span class="table_title">手持证件半身照：</span>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						
						<td><span class="table_title">学生证或校园一卡通或市民卡：</span>
						</td>
					</tr>
					<tr>
					
      				<td ><a href="javascript:;" onclick="showImg('${ctx}/upload${arn.idPic}','身份证')"><img style="display: inline-block; width: 200px;height: 150px;" src="${ctx}/upload${arn.idPic}"/></a><br><input style="display: inline-block; width: 200px;" type="file" name="idPic1"/></td>
      				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      				<td ><a href="javascript:;" onclick="showImg('${ctx}/upload${arn.personPic}','手持证件半身照')"><img style="display:inline-block; width: 200px;height: 150px;" src="${ctx}/upload${arn.personPic}"/></a><br><input style="display: inline-block; width: 200px;" type="file" name="personPic1"/></td>
      				 <td ></td>
      				 <td >1.<a href="javascript:;" onclick="showImg('${ctx}/upload${personPicList[0]}','学生证或校园一卡通或市民卡')"><img style="display:inline-block; width: 200px;height: 150px;" src="${ctx}/upload${personPicList[0]}"/></a><br><input style="display: inline-block; width: 200px;" type="file" name="pic1"/></td>
      				 <td >2.<a href="javascript:;" onclick="showImg('${ctx}/upload${personPicList[1]}','学生证或校园一卡通或市民卡')"><img style="display:inline-block; width: 200px;height: 150px;" src="${ctx}/upload${personPicList[1]}"/></a><br><input style="display: inline-block; width: 200px;" type="file" name="pic2"/></td>
      				<td ></td>
      				</tr>
      				<tr>
      				<td ></td>
      				<td ></td>
      				<td ></td>
      				<td ></td>
      				 <td >3.<a href="javascript:;" onclick="showImg('${ctx}/upload${personPicList[2]}','学生证或校园一卡通或市民卡')"><img style="display:inline-block; width: 200px;height: 150px;" src="${ctx}/upload${personPicList[2]}"/></a><br><input style="display: inline-block; width: 200px;" type="file" name="pic3"/></td>
      				 <td >4.<a href="javascript:;" onclick="showImg('${ctx}/upload${personPicList[3]}','学生证或校园一卡通或市民卡')"><img style="display:inline-block; width: 200px;height: 150px;" src="${ctx}/upload${personPicList[3]}"/></a><br><input style="display: inline-block; width: 200px;" type="file" name="pic4"/></td>
      				
      				</tr>
					<!-- gegongxian -->
					<!--  -->
					<%-- <tr>
						 <td style="width:33%;height: 300px;"><a href="javascript:;" onclick="showImg('${ctx}/upload${arn.idPic}','身份证')"><img src="${ctx}/upload${arn.idPic}" /></a><!-- ${ctx}/upload${arn.idPic}" /> -->
						<a href="javascript:;" onclick="showImg('${ctx}/upload${arn.idPic}','身份证')">
						<img style="display: block;width: 100%;height:100%" src="${ctx}/upload${arn.idPic}" /></a>
						<!-- <input name="idpic" type="file" id="imgfile1" size="40" /> --><!-- onchange="viewmypic(showimg,this.form.imgfile);"  -->
						</td>
						 <td style="width:33%;height: 300px;"><a href="javascript:;" onclick="showImg('${ctx}/upload${arn.personPic}','手持证件半身照')"><img style="display: block;width: 100%;height:100%" src="${ctx}/upload${arn.personPic}" /></a>
						<!-- <input name="personpic" type="file" id="imgfile2" size="40"  /> -->
						</td>
						<td style="height: 300px;"><a href="javascript:;" onclick="showImg('${ctx}/upload${asr.personPic}','学生证或校园一卡通或市民卡')"><img style="display: block;width: 100%;height:100%" src="${ctx}/upload${asr.personPic}" /></a>
						<!-- <input name="asrpersonPic" type="file" id="imgfile3" size="40"  /> -->
						</td> 
					</tr> --%>
				</table>
				<div id="imgShowDlg">
				<img id="imgShow" alt="" src="" style="display: block;width: 100%;height: 100%">
				</div>     
				 <table>
					<tr>
						<td>认证员</td>
						<td><label class="fqfl_xq_tr">${user.firstAdmin.realName}</label>
						</td>
					<tr>
				</table>
				<button type="button"  onclick="saveJcrzxx()"
					class="btn btn-warning bcxg_btn">保存更新</button>
			</form>
		</div>
		<div id="gjrzxx" title="高级认证信息" 
			style="padding:20px;">
			<center>
				<font size=6><b>高级认证信息</b>
				</font>
			</center>
			<form id="gjrzxx_form" method="post">
				<span class="table_title">资产信息</span>
				<table class="table_inner table_td">
					<tr>
						<td align=right>每月生活费：</td>
						<td><%-- <label class="fqfl_xq_tr">${aotsr.money}</label> --%>
							<input type="hidden" name="aotsrid" value="${aotsr.id}">
							<input id="gjrzxxsrmoney" name="srmoney" class="fqfl_xq_tr" value="${aotsr.money}" />
						</td>
						<td align=right>每月开销：</td>
						<td><%-- <label class="fqfl_xq_tr">${aotzc.money}</label> --%>
							<input type="hidden" name="aotzcid" value="${aotzc.id}">
							<input id="gjrzxxzcmoney" name="zcmoney" class="fqfl_xq_tr" value="${aotzc.money}" />
						</td>
						<td align=right>认证提交时间：</td>
						<td><label class="fqfl_xq_tr">${aotzc.createTime}</label>
						</td>
					</tr>
				</table>
				<span class="table_title">社交信息</span>
				<table class="table_inner table_td">
					<tr>
						<td align=right>关联同学姓名：</td>
						<td><%-- <label class="fqfl_xq_tr">${acm.linkName}</label> --%>
						<input type="hidden" name="acmid" id="acmid" value="${acm[0].id}"> 
						<input type="text" name="linkName" id="gjrzxxlinkName" value="${acm[0].linkName}" />
						</td>
						<td align=right>关联同学手机号：</td>
						<td><%-- <label class="fqfl_xq_tr">${acm.linkPhone}</label> --%>
						<input type="text"  style="width:95px;" name="linkPhone" id="gjrzxxlinkPhone" value="${acm[0].linkPhone}" />
						</td>
					</tr>
					<tr>
						<td align=right>关联同学姓名：</td>
						<td><%-- <label class="fqfl_xq_tr">${acm.linkName}</label> --%>
						<input type="hidden" name="acmid" id="acmid" value="${acm[1].id}"> 
						<input type="text" name="linkName" id="gjrzxxlinkName" value="${acm[1].linkName}" />
						</td>
						<td align=right>关联同学手机号：</td>
						<td><%-- <label class="fqfl_xq_tr">${acm.linkPhone}</label> --%>
						<input type="text"  style="width:95px;" name="linkPhone" id="gjrzxxlinkPhone" value="${acm[1].linkPhone}" />
						</td>
					</tr>
				</table>
				 <table>
					<tr>
						<td>认证员：</td>
						<td><label class="fqfl_xq_tr">${user.secondAdmin.realName}</label>
						</td>
					<tr>
				</table>
				<span class="table_title">资产证明图片:</span>
				<ul id="zczmUl" class="jphotogrid">
				</ul>
				<button type="button" onclick="saveGjrzxx()" style="float: left;"
					class="btn btn-warning bcxg_btn">保存更新</button>
			</form>
		</div>
	</div>
</body>
</html>