<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<title>权限分配</title>
<jsp:include page="/commons/metanew.jsp"></jsp:include>
<head>
<script type="text/javascript">
 jQuery(function($){
	$('#role').combobox({
		url:"${ctx}/adminManage/getRoleList",
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
	});
	
	   function saveRight(){
 		if($('#role').combobox('getValue') == ""){
 		alert('请选择分组');
  		return false;
  		}
	 $('#qxfp_form').form('submit', {
		 url:"${ctx}//",
       	 onSubmit: function(param){
       	},
        success:function(data){
   	    	var d=eval("("+data+")");
   	    	if(d.resultCode==1){
   	    		$.messager.alert("消息","保存成功");
   	    	}else {
   	    		if(d.resultMsg){
   	    			$.messager.alert("消息",d.resultMsg);
   	    		}else{
   	    			$.messager.alert("消息","未知错误");
   	    		}
   	    	}
   	    }
 }); 
}
	
</script>
</head>
 <body>
 <div id="ttt" class="easyui-tabs">
 <div id="hyxxzl" title="管理员分组列表" style="overflow:auto;padding:20px;"> 
 <form id="">
</form>
</div>
<div id="qxfp" title="权限分配" style="overflow:auto;padding:20px;"> 
<form id="qxfp_form">
 <table><tr><td>分配权限:</td><td><input type="text" id="role" /></td></tr></table>
 <div id="tttt" class="easyui-tabs">
 <div id="wdgzl" title="我的工作流" style="overflow:auto;padding:20px;">
 <table><tr><td>
 <input type="checkbox" name="hyrz" /></td><td>会员认证</td>
 </tr>
 <tr><td></td><td align="right"><input type="checkbox" name="jchy" value="100011"/></td><td>&nbsp;基础会员</td><td>
 </td><td align="right"><input type="checkbox" name="gjhy" value="100012"/></td><td>&nbsp;高级会员</td></tr>
 </table>
 <br>
 <table><tr><td>
 <input type="checkbox" name="hyrz" /></td><td>代付审核</td>
 </tr>
 <tr><td></td><td align="right"><input type="checkbox" name="jchy" value="100021"/></td><td>&nbsp;代付初审</td>
 <td></td><td align="right"><input type="checkbox" name="gjhy" value="100022"/></td><td>&nbsp;代付复审</td></tr>
 </table>
 <br>
 <table><tr><td>
 <input type="checkbox" id="yhck" name="yhck" value="100030"/></td><td>用户催款</td>
 </tr>
 </table>
 <table><tr><td>
 <input type="checkbox" name="shsh" value="100040"/></td><td>商户审核</td>
 </tr>
 </table>
 </div>
 <div id="xxgl" title="信息管理" style="overflow:auto;padding:20px;">
 
 </div>
  <div id="hygl" title="会员管理" style="overflow:auto;padding:20px;">
 
 </div>
  <div id="shgl" title="商户管理" style="overflow:auto;padding:20px;">
 
 </div>
  <div id="yygl" title="运营管理" style="overflow:auto;padding:20px;">
 
 </div>
  <div id="cwgl" title="财务管理" style="overflow:auto;padding:20px;">
 
 </div>
  <div id="jygl" title="交易管理" style="overflow:auto;padding:20px;">
 
 </div>
  <div id="cspz" title="参数配置" style="overflow:auto;padding:20px;">
 
 </div>
  <div id="jygl" title="系统管理" style="overflow:auto;padding:20px;">
 
 </div>
 </div>
 <button type="button" class="btn btn-primary" onclick="saveRight()">保存</button>
</form>
 </div>
 </div>
 </body>
 </html>