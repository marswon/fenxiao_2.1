<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>可当科技</title>
<link type="image/x-icon" href="${ctx}/static/images/favicon_kd.ico"
	rel="shortcut icon"/>
<style type="text/css" media=screen>
.wwwaaa {
	margin: 0 auto;
	width: 220px;
	padding: 330px;
	/* 	border:1px solid #000000; */
	background: url(static/images/login/white_backgroud.png) no-repeat;
	background-position: center;
	margin-top: -170px;
}

.img {
	width: 200px;
	height: 100px;
	background: url(static/images/login/u0.png) no-repeat;
}

body {
	background-image: url('${ctx}/static/images/login/big_backgroud.png');
	background-repeat: no-repeat;
	background-size: 100% 100%;
	text-align: center;
}

.style {
	margin: 0 auto;
}

.wenzi_kd {
	margin-top: 100px;
}

.max {
	left: 10%;
	top: 10%;
	margin-left: width/2;
	margin-top: height/2;
	top: 10%;
	margin-left: width/2;
	margin-top: height/2;
}

.wenzi {
	margin-top: -90px;
	margin-left: 0px;
}

.text_input {
	margin-left: -10px;
}
</style>
<script type="text/javascript">
	function myReload() {
		document.getElementById("CreateCheckCode").src = document
				.getElementById("CreateCheckCode").src
				+ "?nocache=" + new Date().getTime();
	}
</script>
</head>

<body>
	<form id="loginForm" method="post" class="form-horizontal"
		action="${ctx}/login">
		<%
			String error = (String) request
					.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if (error != null) {
		%>
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>
			登录失败，请重试.
		</div>
		<%
			}
		%>
		<div class="max">
			<img alt="wenzi_kd" class="wenzi_kd"
				src="${ctx}/static/images/login/zi.png">
			<div class="wwwaaa">
				<div class="control-group" style="margin-top: 20px;">
					<img alt="logo" class="wenzi"
						src="${ctx}/static/images/login/logo.png">
					<div class="">
						<input type="text" id="username" name="username" value="账号"
							class="input-medium required text_input"
							onfocus="if(this.value==defaultValue) {this.value='';}"
							onblur="if(!value) {value=defaultValue; this.type='text';}"
							style="color: #999999; width: 230px; height: 30px;" />
					</div>
				</div>

				<div class="">
					<div class="control-group">
						<input type="text" id="password" name="password"
							class="input-medium required text_input" value="密码"
							onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}"
							onblur="if(!value) {value=defaultValue; this.type='text';}"
							style="color: #999999; width: 230px; height: 30px;" />
					</div>
				</div>

				<div class="">
					<div class="control-group">
						<input type="text" id="checkCode" name="checkCode"
							class="input-medium required text_input" value="验证码"
							onfocus="if(this.value==defaultValue) {this.value='';}"
							onblur="if(!value) {value=defaultValue;}"
							style="color: #999999; width: 71px; height: 30px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img alt="" src="${ctx}/captcha/createCaptcha"
							onclick="myReload()" id="CreateCheckCode" align="middle">
					</div>
				</div>

				<div class="control-group">
					<div style="text-align: left">
						<label class="checkbox" for="rememberMe"
							style="display: block; float: left;"><input
							class="text_input" type="checkbox" id="rememberMe"
							name="rememberMe" />
							记住我&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</label><a style="display: block; float: left;" onclick="myReload()">
							看不清,换一个</a><br /> <input id="submit_btn"
							class="btn btn-primary text_input" type="button" value="登录"
							style="width: 240px; height: 30px;" onclick="isRightCode();" />
						<!-- <span class="help-block">(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)</span> -->
					</div>
				</div>
			</div>
		</div>
	</form>
	<script>
		function isRightCode() {
			//文本框验证码
			var input_code = $("#checkCode").val();
			var code = "c=" + input_code;
			$.ajax({
				type : "POST",
				url : "${ctx}/captcha/validateCode",
				data : code,
				success : callback
			});
			function callback(data) {
				if (data.resultCode == -1) {
					alert("验证码错误");
					myReload();
					return;
				} else {
					//alert("验证码正确");
					document.getElementById('loginForm').submit();
				}
				return true;
			}
		}
		document.onkeydown=function(e){
			var keycode=document.all?event.keyCode:e.which;
			if(keycode==13)isRightCode();
			}
	</script>
</body>

</html>
