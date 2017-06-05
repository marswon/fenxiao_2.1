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
	// 	$(function() {
	// 		$('#eId').combobox({
	// 			url : "${ctx}/addBalance/findEnterprise",
	// 			valueField : 'id',
	// 			textField : 'name',
	// 			editable : false,
	// 			width : 220,
	// 			panelHeight : 300,
	// 			loadFilter : function(data) {
	// 				if (data.content) {
	// 					return data.content;
	// 				} else {
	// 					return false;
	// 				}
	// 			},
	// 		});
	// 	});
	$(document).ready(
			$.post("${ctx}/addBalance/findEnterprise", null, function(data) {
				if (data) {
					$.each(data.content, function(index, obj) {
						$("#eId").append(
								"<option value='"+obj.id+"'>" + obj.name
										+ "</option>");
					});
					chosen("#eId");
					chosen("#addMode");
					chosen("#addType");
				}
			}));

	function saveAddBalance() {
		var flag = false;
		$.messager.confirm("操作提示", "您确定要执行操作吗？", function(data) {
			if (data) {
				$('#addBalance_form').form('submit', {
					url : "${ctx}/addBalance/saveAddBalance",
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
// 									window.location.reload();
									$("#eId").val("");
									$("#addType").val("");
									$("#addMode").val("");
									$("#accountName").val("");
									$("#amount").val("");
									$("#checkAmount").val("");
									$("#desc").val("");
									$("#eId").trigger("chosen:updated");
									$("#addType").trigger("chosen:updated");
									$("#addMode").trigger("chosen:updated");

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
	
	function addTypeChange(){
		var addType = $("#addType").val();
		if(addType == "1"){
			$("#addModeTR").show();
			$("#accountNameTR").show();
		}else if (addType == "2"){
			$("#addModeTR").hide();
			$("#accountNameTR").hide();
		}
	}
</script>
</head>
<body>
	<div class="easyui-tabs" data-options="fit:true">
		<div title="加款申请" style="padding: 2px">
			<div id="jxrzxx" title="" style="padding: 1px">
				<!-- display:none; -->
				<div style="padding: 10px">
					<form id="addBalance_form">
						<table cellpadding="4" style="width: 40%">
							<tr>
								<td style="text-align: right; width: 22%">加款企业：</td>
								<td><select name="eId" id="eId" style="width: 240px;"><option
											value="">--请选择--</option></select><font color="red" style="font-weight: bold;"> * </font></td>
							</tr>
							<tr>
								<td style="text-align: right;">加款类型：</td>
								<td><select id="addType" name="addType"
									style="width: 240px;" onchange="addTypeChange()">
										<option value="">--请选择--</option>
										<option value="1">加款</option>
										<option value="2">授信</option>
								</select><font color="red" style="font-weight: bold;"> * </font></td>
							</tr>
							<tr id="addModeTR">
								<td style="text-align: right;">加款方式：</td>
								<td><select id="addMode" name="addMode"
									style="width: 240px;">
										<option value="">--请选择--</option>
										<option
											value="杭州可当科技有限公司，招商银行股份有限公司杭州转塘小微企业专营支行（571909145510201）">杭州可当科技有限公司，招商银行股份有限公司杭州转塘小微企业专营支行（571909145510201）</option>
										<option value="杭州牛出科技有限公司，招商银行富阳支行（571909401310903）">杭州牛出科技有限公司，招商银行富阳支行（571909401310903）</option>
										<option value="章泽锋，招商银行富阳支行（6214855715624793）">章泽锋，招商银行富阳支行（6214855715624793）</option>
										<option value="章泽锋，支付宝（kefu@kedang.net）">章泽锋，支付宝（kefu@kedang.net）</option>
								</select><font color="red" style="font-weight: bold;"> * </font></td>
							</tr>
							<tr id="accountNameTR">
								<td style="text-align: right;">打款人姓名：</td>
								<td><input type="text" id="accountName" name="accountName" style="width: 225px;"><font color="red" style="font-weight: bold;"> * </font></td>
							</tr>
							<tr>
								<td style="text-align: right;">充值金额：</td>
								<td><input type="text" id="amount" name="amount"
									style="width: 225px">（元）<font color="red" style="font-weight: bold;"> * </font></td>
							</tr>
							<tr>
								<td style="text-align: right;">确认充值金额：</td>
								<td><input type="text" id="checkAmount" name="checkAmount"
									style="width: 225px">（元）<font color="red" style="font-weight: bold;"> * </font></td>
							</tr>
							<tr>
								<td style="text-align: right;">备注：</td>
								<td><textarea id="desc" name="desc" style="resize:none;width: 225px;height: 50px;"></textarea><font color="red" style="font-weight: bold;"> * </font>
								</td>
							</tr>

							<tr>
								<td colspan="2" style="padding-left: 170px;" ><button type="button"class="btn btn-primary" onclick="saveAddBalance()" style="width: 60px">保存</button></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>