
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link type="image/x-icon" href="${ctx}/static/images/favicon_kd.ico">

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

<%-- 	<script type="text/javascript" src="${ctx}/static/libs/router.js"></script>
	<script type="text/javascript" src="${ctx}/static/libs/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/libs/fastclick.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/libs/app.js"></script>
	<script type="text/javascript" src="${ctx}/js/index.js"></script> --%>
