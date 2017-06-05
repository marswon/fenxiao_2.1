<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%@ include file="/WEB-INF/views/header/header.jsp"%> 
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<title>角色列表</title>
<link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctxStatic}/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctxStatic}/plugins/jqgrid/ui.jqgrid-bootstrap.css">
<link rel="stylesheet" href="${ctxStatic}/plugins/ztree/css/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${ctxStatic}/css/main.css">

<script type="text/javascript" src="${ctx}/static/libs/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/libs/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/libs/vue.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jqgrid/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>



</head>
<body>
<div id="rrapp" v-cloak >
	<div v-show="showList">
		<div class="grid-btn" style="margin-top: 20px">
			<div class="form-group col-sm-2">
				<input type="text" class="form-control" style="height: 35px" v-model="q.roleName" @keyup.enter="query" placeholder="角色名称">
			</div>
			<a class="btn btn-default" @click="query">查询</a>
			
			<!-- #if($shiro.hasPermission("sys:role:save")) -->
			<a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
		<!-- 	#end
			#if($shiro.hasPermission("sys:role:update")) -->
			<a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
		<!-- 	#end
			#if($shiro.hasPermission("sys:role:delete")) -->
			<a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
		<!-- 	#end -->
		</div>
	    <table id="jqGrid"></table>
	    <div id="jqGridPager"></div>
    </div>
    
    <div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">角色名称</div>
			   	<div class="col-sm-10">
			      <input type="text" style="height: 35px" class="form-control" v-model="role.name" placeholder="角色名称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-10">
			      <input type="text" style="height: 35px" class="form-control" v-model="role.description" placeholder="备注"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">授权</div>
			   	<div class="col-sm-10">
			      <ul id="menuTree" class="ztree"></ul>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
				&nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
			</div>
		</form>
	</div>
</div>
	<script src="${ctx}/js/sys/role.js"></script>

</body>
</html>