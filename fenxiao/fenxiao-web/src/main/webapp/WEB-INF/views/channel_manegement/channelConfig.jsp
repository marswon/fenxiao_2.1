<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title></title>
<style type="text/css" media=screen>
.text_red {
	color: red;
	font-size: 1.5rem;
	vertical-align: middle
}
</style>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="供应商渠道配置" style="padding: 2px">
			<iframe style="width: 100%;height: 100%"
				src="http://localhost:8088/fenxiao-channel/pages/channelManager.jsp"></iframe>
		</div>
	</div>
</body>
</html>
