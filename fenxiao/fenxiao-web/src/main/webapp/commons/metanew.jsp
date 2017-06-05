<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 引入easyUi默认的CSS格式--蓝色 -->
<link rel="stylesheet" type="text/css"   href="${ctx}/static/easyui/themes/bootstrap/easyui.css" />
<!-- 引入easyUi小图标 -->
<link rel="stylesheet" type="text/css"   href="${ctx}/static/easyui/themes/icon.css" />
<link href="${ctx}/static/styles/global.css" type="text/css" rel="stylesheet">
<link href="${ctx}/static/styles/layout/reset.css" type="text/css" rel="stylesheet">
<link href="${ctx}/static/styles/layout/layout.css" type="text/css" rel="stylesheet">
<!-- 引入Jquery -->
<script type="text/javascript"   src="${ctx}/static/easyui/jquery-1.8.3.js" ></script>
<!-- 引入Jquery_easyui -->
<script type="text/javascript"   src="${ctx}/static/easyui/jquery.easyui.min.js" ></script>
<!-- 引入easyUi国际化--中文 -->
<script type="text/javascript"   src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" >
$(document).ready(function(){
	$(document).keydown(function(event){
		switch(event.keyCode){
		//case 8:return false; //屏蔽退格删除键
		//case 82:return false;//屏蔽Ctrl+R
		case 116:return false;//屏蔽F5刷新键
		default:return true;
		}
	});
});
</script>	