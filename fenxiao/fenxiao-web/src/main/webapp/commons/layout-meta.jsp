<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${ctx}/styles/easythemes/icon.css" type="text/css" rel="stylesheet">
<link href="${ctx}/styles/easythemes/gray/easyui.css" type="text/css" rel="stylesheet">
<link href="${ctx}/styles/layout/reset.css?v7" type="text/css" rel="stylesheet">
<link href="${ctx}/styles/layout/layout.css?v10" type="text/css" rel="stylesheet">
<script src="${ctx}/scripts/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="${ctx}/scripts/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/scripts/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${ctx}/scripts/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/js/mr_ajax.js" type="text/javascript"></script>
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