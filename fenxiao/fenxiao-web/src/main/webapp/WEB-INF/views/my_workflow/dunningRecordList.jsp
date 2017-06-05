<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title></title>
  <jsp:include page="/commons/metanew.jsp"></jsp:include>
  <script type="text/javascript">
    jQuery(function($){
      $('#intentionuserTb').datagrid({
      	url:"${ctx}/myWorkflow/dunningRecord/getDunningRecordList1",
        method:'post',
        iconCls:'icon-edit', //图标
        singleSelect:true, //单选
        height: $(window).height()-70,
        fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。
        striped: true, //奇偶行颜色不同
        collapsible: false,//可折叠
        sortName: '', //排序的列
        idField:'id', //主键字段
        sortOrder: '', //倒序
        remoteSort: true, //服务器端排序
        queryParams:{}, //查询条件
        pageSize:20,//需要跟下面的每页数量对上
        pageList:[10,20,50,100],//每页数量选择
        pagination:true, //显示分页
        rownumbers:true, //显示行号
        frozenColumns:[[
        ]],
        columns:[[
          {field:'billCode',title:'帐单编号',width:100,align:"center"},
          {field:'startTime',title:'帐单开始时间',width:100,align:"center"},
         /*  {field:'end_date',title:'帐单结束时间',width:100,align:"center"},  */
          {field:'repaymentDate',title:'还款时间',width:100,align:"center"},
          {field:'consumeAmount',title:'累计消费金额',width:100,align:"center"},
          {field:'userMphone',title:'帐号',width:100,align:"center"},
          {field:'userName',title:'姓名',width:100,align:"center"},
          {field:'schoolName',title:'学校',width:100,align:"center"},
          {field:'status',title:'状态',width:100,align:"center",formatter:function(val,row,index){
          	if(row.status == 0){
          	return "未还款";
          	}else if(row.status == 1){
          	return "已还款";
          	}else{
          	return "逾期"+row.overdueDay+"天";
          	}
          }},
          {field:'f10',title:'帐单明细',width:100,align:"center",formatter:function(val,row,index){
        	   return '<a href="javascript:;" class="btn btn-default" onclick="openZd('+row.id+')">查看</a>';
           }},
           {field:'remark',title:'备注信息',width:70,align:"center"},
         ]],
        onLoadSuccess:function(){
          $('#logaccountTb').datagrid('clearSelections');
        },
          loadFilter:function(data){
          if(data.content){
            return {total:data.totalElements,rows:data.content};
          }else{
            if(typeof data.length=="number"&&typeof data.splice=="function"){
              return {total:data.length,rows:data};
            }else{
              return data;
            }
          }
        }
        /* loadFilter:function(data){
			 if (data.resultCode==1){
				return data.content;
			} else if(data.resultCode==-1){
				$.messager.alert('消息', data.resultMsg);
			} else {
				return data;
			}
		},  */
      });
      
      $("#search_id").click(function(){
//        var params = $('#intentionuserTb').datagrid('options').queryParams;
//        var fields = $('#queryForm').serializeArray();
//        $.each(fields, function(i, field) {
//          params[field.name] = $.trim(field.value);
//        });
//        $('#intentionuserTb').datagrid('load');
		var queryParams = $('#intentionuserTb').datagrid('options').queryParams; 
		queryParams.billCode = $('#billcode1').val();
		queryParams.uName = $('#username1').val();
		queryParams.mphone = $('#mphone1').val();
		$("#intentionuserTb").datagrid('options').queryParams = queryParams;  
		$("#intentionuserTb").datagrid('options').url ='${ctx}/myWorkflow/dunningRecord/getDunningRecordList1';
		$("#intentionuserTb").datagrid('reload'); 
      });
      
      $('#zdxqDlg').dialog({
	    title: '账单详情',
	    fit:true,
	    draggable:false,
	    closed: true,
	    cache: false,
	    modal: true
	});
      
      $(window).resize(function(){
    	$('#zdxqDlg').dialog('resize',{
    		width: $(document.body).width(),
    		height: $(document.body).height()
    	});
      });
      
    });
    
    function openZd(id){
	var zdxqIframe=$('#zdxqIframe');
	var url='${ctx}/myWorkflow/queryUserBill?id='+id;
	zdxqIframe.attr('src',url);
	$('#zdxqDlg').dialog('open');
}
  
  </script>
</head>
<body>
<div class="renwu_block">
  <div class="subtitle">
    <a href="#" class="current" id="a_user_id">催款列表</a>
  </div>
  <div class="table_data">
    <table id="intentionuserTb" toolbar="#tb"></table>
    <div id="tb" style="padding:5px;height:auto" class="datagrid-toolbar">
      <form id="queryForm">
       <table>
        <tr>
        <td style="border: none;vertical-align: middle;">账单编号:</td>
		<td style="border: none;vertical-align: middle;"><input type="text" id="billcode1" name="search_LIKE_vuserBill.billCode" placeholder="请输入账单编号" style="margin: 0"/></td>
		<td style="border: none;vertical-align: middle;">姓名</td>
		<td style="border: none;vertical-align: middle;"><input type="text" id="username1" name="search_LIKE_vuserBill.userName" placeholder="请输入姓名" style="margin: 0"/></td>
		<td style="border: none;vertical-align: middle;">手机号:</td>
		<td style="border: none;vertical-align: middle;">
			<input type="text" id="mphone1" name="search_LIKE_vuserBill.userMphone" placeholder="请输入手机号" style="margin: 0"/></td> 
		<td style="border: none;vertical-align: middle;">
		<a href="javascript:;" class="easyui-linkbutton" id="search_id" data-options="iconCls:'icon-search'">查询</a></td>
        </tr>
        <!-- <tr>
        <td><b>订单号：</b></td>
        <td><input type="text" name="search_EQ_billCode" id="creditLevel" /></td>
        <td><b>姓名：</b></td>
        <td><input type="text" name="search_EQ_name" id="js_status" /></td>
        <td><b>手机号：</b></td>
        <td><input type="text" name="search_EQ_mphone"></td>
        <td><a href="#" class="easyui-linkbutton" iconCls="icon-search" id="search_id">查询</a></td>
        <span id="js_valdate" style="color: red"></span>&nbsp;<span style="color: red" id="js_no"></span>
        </tr> -->
        </table>
      </form>
    </div>
  </div>
  	<div id="zdxqDlg">
		<iframe id="zdxqIframe" frameborder="0" marginheight="0" marginwidth="0"
            style="width:100%;height:99%;" scrolling="yes" ></iframe>
	</div>
</div>
</body>
</html>
