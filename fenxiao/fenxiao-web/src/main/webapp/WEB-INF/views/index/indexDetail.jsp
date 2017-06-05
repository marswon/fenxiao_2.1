<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="index" value="0" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/chart/Chart.js"></script>

<title>首页</title>
<style type="text/css">
.table_inner {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.table_inner input[type="text"] {
	margin: 0 5px 0 5px;
	width: 50px;
}

.table_inner select {
	margin: 0 5px 0 5px;
	width: 100px;
}

.table_inner td, th {
	border: 1px solid #000;
}

.table_td td {
	padding: 3px 7px 2px 7px;
}

.table_inner th {
	font-size: 12px;
	height: 40px;
}

.table_inner td {
	font-size: 12px;
	height: 40px;
}

.gsh_input {
	display: inline-block;
	margin-left: 5px;
	margin-right: 5px;
	width: 50px;
}

.ydq {
	text-align: right;
}

#fqfl_xq_tr input[type="text"] {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	text-align: center;
}

#hkfl_div {
	margin-top: 50px;
	margin-bottom: 50px;
}

#yqzdtx_div {
	margin-bottom: 50px;
}

#fqfl_div {
	margin-bottom: 50px;
}

#yqtx_arr_table td {
	padding: 0;
	border: none;
}

.bcxg_btn {
	margin-left: 210px;
}

.table_title {
	display: inline-block;
	margin-bottom: 5px;
	padding-right: 10px;
	background-color: #c7c7c7;
}

#jyxx {
	width: 100%;
}

.tdydq td {
	text-align: center;
}

#b {
	width: 350px;
	height: 173px;
	float: right;
	margin: 25px;
	padding: 0;
	display: none;
	position: absolute;
	top: 50px;
	right: 50px;
	z-index: 1;
}

#m1 {
	text-decoration: none;
	color: gray;
	font-family: "微软雅黑";
	font-size: 12px;
}

#a {
	float: right;
	margin-right: 100px;
}
</style>
<script>
	function closeDetail() {
		window.parent.closeDetail();
	}
	/*$(function(){
	 var ctx = $("#sevenDayzc").get(0).getContext("2d");
	 var msg = ${result.sevenDay};
	 var userSevenTime = ${result.userSevenTime};
	 var data = {
	 labels : userSevenTime,
	 datasets : [
	 {
	 fillColor : "rgba(151,187,205,0.5)",
	 strokeColor : "rgba(151,187,205,1)",
	 pointColor : "rgba(151,187,205,1)",
	 pointStrokeColor : "#fff",
	 data : msg
	 }
	 ]
	 }
	 new Chart(ctx).Line(data);
	 });
	 $(function(){
	 var ctx = $("#thirtyDayzc").get(0).getContext("2d");
	 var msg = ${result.thirtyDay};
	 var userThirtyTime = ${result.userThirtyTime};
	 var data = {
	 labels : userThirtyTime,
	 datasets : [
	 {
	 fillColor : "rgba(151,187,205,0.5)",
	 strokeColor : "rgba(151,187,205,1)",
	 pointColor : "rgba(151,187,205,1)",
	 pointStrokeColor : "#fff",
	 data : msg
	 }
	 ]
	 }
	 new Chart(ctx).Line(data);
	 });
	 $(function(){
	 var ctx = $("#sevenDayrz").get(0).getContext("2d");
	 var msgRz = ${result.sevenDayRz};
	 var userSevenTimeRz = ${result.userSevenTimeRz};
	 var data = {
	 labels : userSevenTimeRz,
	 datasets : [
	 {
	 fillColor : "rgba(151,187,205,0.5)",
	 strokeColor : "rgba(151,187,205,1)",
	 pointColor : "rgba(151,187,205,1)",
	 pointStrokeColor : "#fff",
	 data : msgRz
	 }
	 ]
	 }
	 new Chart(ctx).Line(data);
	 });
	 $(function(){
	 var ctx = $("#thirtyDayrz").get(0).getContext("2d");
	 var msgRz = ${result.thirtyDayRz};
	 var userThirtyTimeRz = ${result.userThirtyTimeRz};
	 var data = {
	 labels : userThirtyTimeRz,
	 datasets : [
	 {
	 fillColor : "rgba(151,187,205,0.5)",
	 strokeColor : "rgba(151,187,205,1)",
	 pointColor : "rgba(151,187,205,1)",
	 pointStrokeColor : "#fff",
	 data : msgRz
	 }
	 ]
	 }
	 new Chart(ctx).Line(data);
	 });*/

	//$(function(){
	//		setInterval(quoto, 4000);
	//})

	$(function() {
		submitForm3();
	});
	//Ajax使用get方式发送  
	function submitForm3() {

	}

	function mess() {
		$("#tb1").datagrid(
				{
					url : "${ctx}/index/getMessage",
					nowrap : true,
					striped : true,
					remoteSort : false,
					pageSize : 20,
					fit : true,

					queryParams : {
					//search_EQ_isInstalments : 0
					},
					singleSelect : true,//是否单选  
					//	pagination : true,//分页控件  
					//	rownumbers : true,//行号 
					fitColumns : false,//列宽自动填充满表格
					checkOnSelect : false,
					selectOnCheck : false,
					toolbar : "#hkDgTb",//工具栏
					loadFilter : function(data) {
						if (data.content) {
							return {
								total : data.totalElements,
								rows : data.content
							};
						} else {
							if (typeof data.length == "number"
									&& typeof data.splice == "function") {
								return {
									total : data.length,
									rows : data
								};
							} else {
								return data;
							}
						}
					},
					columns : [ [ {
						field : 'content',
						title : '最新消息',
						width : 700,
						align : "left",
						formatter : function(val, row, index) {
							if (row.content) {
								return index+1+". "+row.content;
							} else {
								return "";
							}
						}
					}, ] ],
				});
		$("#b").toggle();
	}
</script>
</head>
<body>
	<div id="a">
		<a href="javascript:mess();" id="m1"><span>消息</span></a>
	</div>
	<div id="b">
		<table id="tb1"></table>
		<div id="d1"></div>
	</div>
	<%--  <div id="hkfl_div">   		
	<div><span class="table_title">交易信息:</span></div>
	<div id="jj">
	<table id="jyxx">
	<tr>
		<td width="50%">
			<table class="tdydq table table-bordered">
				<tr>
					<td colspan="2">今日还款数据</td>
				</tr>
				<tr>
					<td>今日还款金额(${result.o2odtzje})</td>
					<td>今日还款笔数(${result.o2odtzbs})</td>
				</tr>
				<tr>
					<td>总还款金额(${result.o2ozje})</td>
					<td>总还款笔数(${result.o2ozbs})</td>
				</tr>
			</table>
		</td>
		<td width="50%">
			<table class="tdydq table table-bordered">
				<tr>
					<td colspan="2">今日提现数据</td>
				</tr>
				<tr>
					<td>今日提现金额(${result.dydfzje})</td>
					<td>今日提现笔数(${result.dtdfzbs})</td>
				</tr>
				<tr>
					<td>总提现金额(${result.dfzje})</td>
					<td>总提现笔数(${result.dfzbs})</td>
				</tr>
			</table>
		</td>
	<tr/>
		<!--  -->
	<tr>
		<!--  -->
	</tr>
	<tr>
		<td width="100%">
			<table class="tdydq table table-bordered">
				<tr>
					<td>一周趋势图</td>
				</tr>
				<tr>
					<td><canvas id="myChartWithdrawWeek" width="500" height="400"></canvas></td>
				</tr>
			</table> 
		</td>
		<td width="100%">
			<table class="tdydq table table-bordered">
				<tr>
					<td>一周趋势图</td>
				</tr>
				<tr>
					<td><canvas id="myChartRepaymentWeek" width="500" height="400"></canvas></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100%">
			<table class="tdydq table table-bordered">
				<tr>
					<td>一月趋势图</td>
				</tr>
				<tr>
					<td><canvas id="myChartWithdrawMonth" width="500" height="400"></canvas></td>
				</tr>
			</table>
		</td>
		<td width="100%">
			<table class="tdydq table table-bordered">
				<tr>
					<td>一月趋势图</td>
				</tr>
				<tr>
					<td><canvas id="myChartRepaymentMonth" width="500" height="400"></canvas></td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
	</div> --%>
	<%-- <table id="jyxx">
	    <tr>
			<td width="100%">
				<table class="tdydq table table-bordered">
					<tr>
						<td>注册一周趋势图</td>
						<td>注册一月趋势图</td>
					</tr>
					<tr>
						<td><canvas id="sevenDayzc" width="500" height="400"></canvas></td>
						<td><canvas id="thirtyDayzc" width="500" height="400"></canvas></td>
					</tr>
				</table>
			</td>
		</tr>
    </table>	 --%>

	<%--  <div><span class="table_title">认证审核:</span></div>
	<table  id="jyxx">
		<tr>
			<td width="100%">
				<table class="tdydq table table-bordered">
					<tr>
						<td>认证一周趋势图</td>
						<td>认证一月趋势图</td>
					</tr>
					<tr>
						<td><canvas id="sevenDayrz" width="500" height="400"></canvas></td>
						<td><canvas id="thirtyDayrz" width="500" height="400"></canvas></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
    </div>
</div> --%>
	<!-- chart.js -->
	<script type="text/javascript">
		//周提现
		var myChartWithdrawWeek = new Chart(document.getElementById(
				"myChartWithdrawWeek").getContext("2d"));
		//周还款
		var myChartRepaymentWeek = new Chart(document.getElementById(
				"myChartRepaymentWeek").getContext("2d"));
		//月提现
		var myChartWithdrawMonth = new Chart(document.getElementById(
				"myChartWithdrawMonth").getContext("2d"));
		//月还款
		var myChartRepaymentMonth = new Chart(document.getElementById(
				"myChartRepaymentMonth").getContext("2d"));
		var options = {
			//字体大小
			scaleFontSize : 13,
			//字体颜色
			scaleFontColor : "#111"
		};
		//-----------------------------------------------------------------------------------------
		$(function() {
			//周提现
			$.ajax({
				type : "GET",
				url : '${ctx}/chartJs/getWeekMoney?type=1',
				dataType : "script",
				success : function(data) {
					var withdrawWeekData = window.data;
					var withdrawWeekDataName = window.dataName;
					if ("200" == window.code) {
						var LineChart = {
							//标签
							labels : withdrawWeekDataName,
							//设置
							datasets : [ {
								//填充颜色
								fillColor : "rgba(252,147,65,0.5)",
								//笔画颜色
								strokeColor : "rgba(255,255,255,1)",
								//点色
								pointColor : "rgba(173,173,173,1)",
								//点画笔颜色
								pointStrokeColor : "#fff",
								//数据
								data : withdrawWeekData
							} ]
						};
						myChartWithdrawWeek.Line(LineChart, options);
					} else {
						if (data.resultMsg) {
							$.messager.alert("消息", data.resultMsg);
						} else {
							$.messager.alert("消息", "未知错误");
						}
					}
				},
			});
			//周还款
			$.ajax({
				type : "GET",
				url : '${ctx}/chartJs/getWeekMoney?type=2',
				dataType : "script",
				success : function(data) {
					var WeekRepaymentDataName = window.dataName;
					var WeekRepaymentData = window.data;
					if ("200" == window.code) {
						var LineChart = {
							//标签
							labels : WeekRepaymentDataName,
							//设置
							datasets : [ {
								//填充颜色
								fillColor : "rgba(252,147,65,0.5)",
								//笔画颜色
								strokeColor : "rgba(255,255,255,1)",
								//点色
								pointColor : "rgba(173,173,173,1)",
								//点画笔颜色
								pointStrokeColor : "#fff",
								//数据
								data : WeekRepaymentData
							} ]
						};
						myChartRepaymentWeek.Line(LineChart, options);
					} else {
						if (data.resultMsg) {
							$.messager.alert("消息", data.resultMsg);
						} else {
							$.messager.alert("消息", "未知错误");
						}
					}
				},
				failure : function(data) {
				}
			});
			//月提现

			$.ajax({
				type : "GET",
				url : "${ctx}/chartJs/getMonthMoney",
				data : {
					type : 1
				},
				dataType : "script",
				success : function(data) {
					var withdrawMonthData = window.data;
					var withdrawMonthDataName = window.dataName;
					if ("200" == window.code) {
						var LineChart = {
							//标签
							labels : withdrawMonthDataName,
							//设置
							datasets : [ {
								//填充颜色
								fillColor : "rgba(252,147,65,0.5)",
								//笔画颜色
								strokeColor : "rgba(255,255,255,1)",
								//点色
								pointColor : "rgba(173,173,173,1)",
								//点画笔颜色
								pointStrokeColor : "#fff",
								//数据
								data : withdrawMonthData
							} ]
						};
						myChartWithdrawMonth.Line(LineChart, options);
					} else {
						if (data.resultMsg) {
							$.messager.alert("消息", data.resultMsg);
						} else {
							$.messager.alert("消息", "未知错误");
						}
					}
				}

			});
			$.ajax({
				type : "GET",
				url : "${ctx}/chartJs/getMonthMoney",
				data : {
					type : 2
				},
				dataType : "script",
				success : function(data) {
					var repaymentMonthData = window.data;
					var repaymentMonthDataName = window.dataName;
					if ("200" == window.code) {
						var LineChart = {
							//标签
							labels : repaymentMonthDataName,
							//设置
							datasets : [ {
								//填充颜色
								fillColor : "rgba(252,147,65,0.5)",
								//笔画颜色
								strokeColor : "rgba(255,255,255,1)",
								//点色
								pointColor : "rgba(173,173,173,1)",
								//点画笔颜色
								pointStrokeColor : "#fff",
								//数据
								data : repaymentMonthData
							} ]
						};
						myChartRepaymentMonth.Line(LineChart, options);
					} else {
						if (data.resultMsg) {
							$.messager.alert("消息", data.resultMsg);
						} else {
							$.messager.alert("消息", "未知错误");
						}
					}
				}
			});
		});
		//--------------------------------------------------------------------------------------
	</script>
</body>
</html>