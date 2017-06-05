<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户催款</title>
<script>
$(function(){
    $(window).resize(function(){  
    	$('#zdxqDlg').dialog('resize',{
    		width: $(document.body).width(),
    		height: $(document.body).height()
    	});
      }); 
	$("#zdDg").datagrid({
		url:"${ctx}/myWorkflow/dunningRecord/getOverdue",
		nowrap : false,striped : true,remoteSort : false,pageSize:20,fit:true,
		queryParams: {
			/* type:1 */
		},
		singleSelect : true,//是否单选  
		pagination : true,//分页控件  
		rownumbers : true,//行号 
		fitColumns : true,//列宽自动填充满表格
		checkOnSelect:false,
		selectOnCheck:false,
		toolbar : "#zdDgTb",//工具栏 
		loadFilter:function(data){
			if (data.content){
				return {total:data.totalElements,rows:data.content};
				//return data.content;
			} else {
				$.messager.alert('消息', data.resultMsg);
				//return data;
			}
		}, 
		columns:[[
           {field:'billCode',title:'帐单编号',width:150,align:"center"},
           {field:'startTime',title:'帐单开始时间',width:100,align:"center"},
           {field:'billDate',title:'帐单结束时间',width:100,align:"center"},
           {field:'repaymentDate',title:'最后还款时间',width:100,align:"center"},
           {field:'consumeAmount',title:'账单金额',width:100,align:"center"},
           {field:'withdrawAmount',title:'提现手续费',width:100,align:"center"},
           {field:'overdueAmount',title:'逾期手续费',width:100,align:"center"},
           {field:'installmentInterest',title:'分期手续费',width:100,align:"center"},
           {field:'totalAmount',title:'应还金额',width:100,align:"center",formatter:function(val,row,index){
        	   return (row.consumeAmount+row.withdrawAmount+row.overdueAmount+row.installmentInterest).toFixed(2);
           }},
           {field:'userMphone',title:'帐号',width:100,align:"center"},
           {field:'userName',title:'姓名',width:100,align:"center"},
           {field:'schoolName',title:'学校',width:100,align:"center"},
           {field:'dunningDate',title:'催款时间',width:100,align:"center"},
           {field:'isInstalments',title:'是否分期',width:100,align:"center",formatter:function(val,row,index){
        	   if(row.isInstalments==1){
        		   return "已分期";
        	   }else if(row.isInstalments==0){
        		   return "未分期";
        	   }
           }},
           {field:'status',title:'状态',width:100,align:"center",formatter:function(val,row,index){
          	if(row.status == 0){
          	return "未还款";
          	}else if(row.status == 1){
          	return "已还款";
          	}else{
          	return "逾期"+row.overdueDay+"天";
          	}
          }},
           {field:'f10',title:'处理',width:100,align:"center",formatter:function(val,row,index){
        	   return '<a href="javascript:;" class="btn btn-default" onclick="openZd('+row.id+')">处理</a>';
           }},
         /*   {field:'remark',title:'添加备注',width:100,align:"center",formatter:function(val,row,index){
        	   return '<a href="javascript:;" class="btn btn-default" onclick="showRemark('+row.id+')">添加备注</a>';
           }},  */
         ]],
        /*  data: [
        		{f1:'ZD201501031001', f2:'2015-1-1', f3:'2015-1-9', f4:'2015-1-10', f5:'1000元', f6:'13877651476', f7:'马云', f8:'浙江大学'
        			, f9:'逾期1天', f10:'', f11:'催邀短信已发[2]，请电话通知[1]', f12:'value12'},
        	], */
        	
	}); 
	$('#zdxqDlg').dialog({
	    title: '账单详情',
	    fit:true,
	    draggable:false,
	    closed: true,
	    cache: false,
	    modal: true
	});
	 $('#webinformation4').dialog({
		 width: 350, height: 180, closed: true, cache: false, modal: true,
		});
	 $("#search_isInstalments").click(function() {
			var params = $('#zdDg').datagrid('options').queryParams;
			var fields = $('#search_form').serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = $.trim(field.value);
			});
			$('#zdDg').datagrid('load');
		});
});
function openZd(id){
	var zdxqIframe=$('#zdxqIframe');
	var url='${ctx}/myWorkflow/queryUserBill?id='+id;
	zdxqIframe.attr('src',url);
	$('#zdxqDlg').dialog('open');
}


function showRemark(id){
	$("#dunningrecordid").val(id);
	//alert($("#dunningrecordid").attr("value"));
	$('#webinformation4').dialog('open');
}

/* function addRemark(){
		$('#remark_form').form('submit', {//updateEduZone
		 url:"${ctx}/myWorkflow/dunningRecord/addRemark",
       	 onSubmit: function(param){
       	},
        success:function(data){
   	    	var d=eval("("+data+")");
   	    	if(d.resultCode==1){
   	    		$.messager.alert("消息","添加成功",function(data){
   	    		$('#webinformation3').hide;
   	    		});
   	    	}else {
   	    		if(d.resultMsg){
   	    			$.messager.alert("消息",d.resultMsg);
   	    		}else{
   	    			$.messager.alert("消息","未知错误");
   	    		}
   	    	}
   	    }
 });
} */

function findDunningRecordList(){
		location.href="${ctx}/myWorkflow/dunningRecord/dunningRecordList";
	}
</script>
</head>
<body>

    <div id="zdDgTb" cellpadding="5">
		<table class="table table-bordered" style="margin-top: 10px;">
			<tr>
				<td width="50%" style="text-align: center;"><a href="javascript:;" onclick="">逾期用户</a></td>
				<td style="text-align: center;"><a href="javascript:;" onclick="findDunningRecordList()">逾期用户催缴记录</a></td>
			</tr>
		</table>
	<form id="search_form">
	<table>
	<tr><td><b>账单类型 ：</b></td>
	<td>
	<select name="isInstalments" id="js_type" style="width: 100px;">
	<option value="">请选择</option>
	<option value="0">未分期</option>
	<option value="1">已分期</option>
	</select>
	</td>
	<td></td>
	<td><a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="search_isInstalments">搜索</a></td>
	</tr>
	</table>
	</form>
	</div>
	<table id="zdDg">
    </table>
	<div id="zdxqDlg">
		<iframe id="zdxqIframe" frameborder="0" marginheight="0" marginwidth="0"
            style="width:100%;height:99%;" scrolling="yes" ></iframe>
	</div>
	<div id = "webinformation4" >
		<form id="remark_form">
		<table cellpadding="5"><tr><td>备注内容：</td><td>
		<input type="hidden" id="dunningrecordid" name="id">
		<input type="text" required="required" id="remark" name="remark" ></td></tr>
		<tr><td><button type="button" class="btn btn-primary" onclick="addRemark()">保存</button></td></tr>
      	</table>
		</form>
	</div>
</body>
</html>