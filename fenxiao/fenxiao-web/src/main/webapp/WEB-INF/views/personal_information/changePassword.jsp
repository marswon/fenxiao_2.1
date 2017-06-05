<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	$(function() {
		$('#eId').combobox({
			url : "${ctx}/addBalance/findEnterprise",
			valueField : 'id',
			textField : 'name',
			editable : false,
			width : 220,
			panelHeight : 300,
			loadFilter : function(data) {
				if (data.content) {
					return data.content;
				} else {
					return false;
				}
			},
		});
	});

	function savePwd() {
		var flag = false;
		$.messager.confirm("操作提示", "您确定要执行操作吗？", function(data) {
			if (data) {
				$('#password_form').form('submit', {
					url : "${ctx}/personalInformation/changePwd",
					onSubmit : function(param) {
						param.eId = $("#eId").val();
						param.mode = $("#addMode").val();
					},
					success : function(data) {
						var d = eval("(" + data + ")");
						console.log(d);
						if (d.resultCode == 1) {
							$.messager.confirm("操作提示", "提交成功", function(data) {
								if (data) {
									window.location.reload();
								} else {
									//alert("取消");
								}
							});

						} else {
							if (d.resultMsg) {
								$.messager.alert("消息", d.resultMsg);
							} else {
								$.messager.alert("消息", "未知错误");
							}
						}
					}
				});
			} else {
			}
		});
	}
</script>

</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="修改密码" style="padding: 2px">
			<div id="p" class="easyui-panel" 
				style="width: 700px; height: 500px; padding: 30px;">
			<div id="jxrzxx" title="" style="padding: 1px">
				<!-- display:none; -->
				<div style="padding: 10px">
					<form id="password_form">
						<table cellpadding="4">
							
							<tr>
								<td>当前密码：</td>
								<td><input type="text" id="oldPwd" name="oldPwd"></td>
							</tr>
							<tr>
								<td>新密码：</td>
								<td><input type="text" id="newPwd" name="newPwd"></td>
							</tr>
							<tr>
								<td>确认密码：</td>
								<td><input type="text" id="checkPwd" name="checkPwd"></td>
							</tr>
							

							<tr>
								<td><button type="button" class="btn btn-primary" onclick="savePwd()">保存</button></td>
							</tr>
						</table>
					</form>
				</div>
			</div></div>
		</div>
	</div>
</body>
</html>