<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/header/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<title>可当科技</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link rel="stylesheet" href="static/css/bootstrap.min.css">
<link rel="stylesheet" href="static/css/font-awesome.min.css">
<link rel="stylesheet" href="static/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="static/css/all-skins.min.css">
<link rel="stylesheet" href="static/css/main.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<!-- Site wrapper -->
	<div class="wrapper" id="rrapp" v-cloak>
		<header class="main-header"> <a href="javascript:void(0);"
			class="logo"> <!-- mini logo for sidebar mini 50x50 pixels --> <span
			class="logo-mini"><b>可当</b></span> <!-- logo for regular state and mobile devices -->
			<span class="logo-lg"><b>可当分销管理系统</b></span>
		</a> <!-- Header Navbar: style can be found in header.less --> <nav
			class="navbar navbar-static-top" role="navigation"> <!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span>
		</a> <!--1级标签 start-->
		<div class="navbar-custom-menu" style="float: left;">
			<ul class="nav navbar-nav" id="firstMenu">
			</ul>
		</div>
		<!--1级标签 end-->
		<%-- <div style="float: left; color: #fff; padding: 15px 10px;">
			欢迎，
			<shiro:principal property="name" />
		</div>
 --%>
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<li><a><span>欢迎，<shiro:principal property="name" />
						</font></span></a></li>
				<li><a> <span><i class="fa fa-gg"></i>&nbsp;账户余额：<font
							size="4rem" style="margin-left: 10px;">¥<fmt:formatNumber
									value="${fxEnterprise.balance/1000}" pattern="#,##0.00#" />
						</font> </span></a></li>
				<li><a> <span>&nbsp;&nbsp;<i class="fa fa-dollar"></i>&nbsp;冻结金额：<font
							size="4rem" style="margin-left: 10px;">¥<fmt:formatNumber
									value="${fxEnterprise.creditTopBalance/1000}"
									pattern="#,##0.00#" />
						</font> <font style="margin-left: 10px;"> </font>
					</span></a></li>
				<!-- <li><a href="javascript:;" @click="updatePassword"><i
								class="fa fa-lock"></i> &nbsp;修改密码</a></li> -->
				<li><a href="logout"><i class="fa fa-sign-out"></i>
						&nbsp;退出系统</a></li>
			</ul>
		</div>
		</nav> </header>

		<!-- =============================================== -->

		<!-- Left side column. contains the sidebar -->
		<aside class="main-sidebar"> <!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar"> <!-- /.search form --> <!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">导航菜单</li>

			<!-- vue生成的菜单 -->
			<menu-item :item="item" v-for="item in menuList"></menu-item>
		</ul>
		</section> <!-- /.sidebar --> </aside>
		<!-- =============================================== -->
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
			<ol class="breadcrumb" id="nav_title"
				style="position: static; float: none;">
				<li class="active"><i class="fa fa-home"
					style="font-size: 20px; position: relative; top: 2px; left: -3px;"></i>
					&nbsp; 首页</li>
				<li class="active">{{navTitle}}</li>
			</ol>
			</section>

			<!-- Main content -->
			<section class="content" style="background: #fff;"> <iframe
				scrolling="yes" frameborder="0"
				style="width: 100%; min-height: 200px; overflow: visible; background: #fff;"
				:src="main"></iframe> </section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<footer class="main-footer">
		<div class="pull-right hidden-xs">Version 2.0.0</div>
		杭州可当科技有限公司版权所有 &copy; 2017 <a href="http://www.kedang.net"
			target="_blank">kedang.net</a> [ 增值电信业务经营许可证 浙 B2-20150429 ] ICP 证号:浙
		ICP 备 13018300号 </footer>

		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>

		<!-- 修改密码 -->
		<div id="passwordLayer" style="display: none;">
			<form class="form-horizontal">
				<!-- 	<div class="form-group">
					<div class="form-group">
						<div class="col-sm-2 control-label">账号</div>
						<span class="label label-success" style="vertical-align: bottom;">{{user}}</span>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">原密码</div>
						<div class="col-sm-10">
							<input type="password" class="form-control" v-model="password"
								placeholder="原密码" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 control-label">新密码</div>
						<div class="col-sm-10">
							<input type="text" class="form-control" v-model="newPassword"
								placeholder="新密码" />
						</div>
					</div>
				</div> -->
			</form>
		</div>

	</div>
	<!-- ./wrapper -->

	<script type="text/javascript" src="${ctx}/static/libs/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/libs/vue.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/libs/router.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/libs/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/libs/jquery.slimscroll.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/libs/fastclick.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/libs/app.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}/js/index.js"></script>
	<script type="text/javascript" src="${ctx}/js/sys/firstMenu.js"></script>

</body>
</html>