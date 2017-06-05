<%@page import="com.kedang.fenxiao.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员资料修改</title>
<head>
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
            width: 200px;
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
            margin-left: 10px;
        }

        .table_title {
            display: inline-block;
            margin-bottom: 5px;
            padding-right: 10px;
            background-color: #c7c7c7;
        }

        .jphotogrid {
            width: 100%;
            height: auto;
            margin: 0;
            padding: 0;
            float: left;
        }

        .jphotogrid li {
            list-style: none;
            width: 200px;
            height: 200px;
            margin-right: 10px;
            float: left;
            position: relative;
        }

        .jphotogrid li img {
            width: 100%;
        }
    </style>
    <script type="text/javascript">
        jQuery(function ($) {
            $('#facultybox').combobox({
                url: "${ctx}/usermanagement/getAllFaculty",
                valueField: 'id',
                textField: 'name',
                editable: false,
                width: 150,
                panelHeight: 200,
                loadFilter: function (data) {
                    if (data.content) {
                        return data.content;
                    } else {
                        return false;
                    }
                },
            });
            $('#schoolbox').combobox({
                url: "${ctx}/usermanagement/getAllSchool",
                valueField: 'id',
                textField: 'name',
                editable: false,
                width: 150,
                panelHeight: 200,
                loadFilter: function (data) {
                    if (data.content) {
                        return data.content;
                    } else {
                        return false;
                    }
                },
                onChange: function () {
                    var id = $('#schoolbox').combobox('getValue');
                    $('#facultybox').combobox({
                        url: "${ctx}/usermanagement/getAllFaculty?id=" + id,
                        valueField: 'id',
                        textField: 'name',
                        editable: false,
                        width: 150,
                        panelHeight: 200,
                        loadFilter: function (data) {
                            if (data.content) {
                                return data.content;
                            } else {
                                return false;
                            }
                        },
                    });
                }
            });
            $("#status").val($("#statushidden").attr("value"));
            $("#rzxxsex").val($("#usersex").attr("value"));
            $("#schoolbox").combobox('select', $("#jcrzxxschool").attr("value"));
            $("#facultybox").combobox('select', $("#jcrzxxfaculty").attr("value"));

            $('#imgShowDlg').dialog({
                title: '图片展示',
                //fit:true,
                //draggable:true,
                width: 450,
                height: 460,
                resizable: true,
                closed: true,
                cache: false,
                modal: false
            });

        });

        $(function () {
            //显示资产证明图片
            showSrOrZcImg();
            //提现记录数据表格
            $("#jlDg").datagrid({
                url: "${ctx}/myWorkflow/queryWithdrawForUser",
                nowrap: false, striped: true, remoteSort: false, pageSize: 20, fit: true,
                queryParams: {
                    search_EQ_userId: $("#userId").val()
                },
                singleSelect: true,//是否单选
                pagination: true,//分页控件
                rownumbers: true,//行号
                fitColumns: true,//列宽自动填充满表格
                checkOnSelect: false,
                selectOnCheck: false,
                toolbar: "#jlDgTb",//工具栏
                loadFilter: function (data) {
                    if (data.content) {
                        return {total: data.totalElements, rows: data.content};
                    } else {
                        if (typeof data.length == "number" && typeof data.splice == "function") {
                            return {total: data.length, rows: data};
                        } else {
                            return data;
                        }
                    }
                },
                columns: [[
                    {field: 'createTimeString', title: '交易时间', width: 150, align: "center"},
                    {field: 'orderCode', title: '提现订单号', width: 150, align: "center"},
                    {field: 'userName', title: '姓名', width: 100, align: "center"},
                    {field: 'amount', title: '提现金额', width: 100, align: "center"},
                    {field: 'creditAmount', title: '可提现金额', width: 100, align: "center"},
                    {field: 'availableAmount', title: '提现前余额', width: 100, align: "center"},
                    {
                        field: 'txhye',
                        title: '提现后余额',
                        width: 100,
                        align: "center",
                        formatter: function (val, row, index) {
                            return (row.availableAmount - row.amount).toFixed(2);
                        }
                    },
                    {field: 'bankName', title: '提现银行', width: 100, align: "center"},
                    {field: 'cardNum', title: '提现银行卡', width: 150, align: "center"},
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        align: "center",
                        formatter: function (val, row, index) {
                            if (val == 0 || val == 3) {
                                return "处理中";
                            } else if (val == 1) {
                                return "交易成功";
                            } else if (val == 2) {
                                return "交易失败";
                            }
                        }
                    },
                    {field: 'remark', title: '备注', width: 150, align: "center"},
                    {
                        field: 'operate',
                        title: '操作',
                        width: 100,
                        align: "center",
                        formatter: function (val, row, index) {
                            var code = "'" + row.orderCode + "'";
                            return '<a href="javascript:;" class="btn btn-default" onclick="openDetail(' + code + ')">详情</a>';
                        }
                    },
                ]]
            });

            //备注表格
            $("#bzDg").datagrid({
                url: "${ctx}/usermanagement/userupdate/findUserRemark",
                nowrap: false, striped: true, remoteSort: false, pageSize: 20, fit: true,
                queryParams: {
                    search_EQ_userId: $("#remark_userId").val()
                },
                singleSelect: true,//是否单选
                pagination: true,//分页控件
                rownumbers: true,//行号
                fitColumns: true,//列宽自动填充满表格
                checkOnSelect: false,
                selectOnCheck: false,
                toolbar: "#bzDgTb",//工具栏
                loadFilter: function (data) {
                    if (data.content) {
                        return {total: data.totalElements, rows: data.content};
                    } else {
                        if (typeof data.length == "number" && typeof data.splice == "function") {
                            return {total: data.length, rows: data};
                        } else {
                            return data;
                        }
                    }
                },
                columns: [[
                    {field: 'createTime', title: '创建时间', width: 100, align: "center"},
                    {
                        field: 'type', title: '类型', width: 50, align: "center", formatter: function (val, row, index) {
                        if (val == 0) {
                            return "认证";
                        } else if (val == 1) {
                            return "提现";
                        } else if (val == 2) {
                            return "催款";
                        } else if (val == 3) {
                            return "预警";
                        }else if (val == 4) {
                            return "黑名单";
                        }else if (val == 5) {
                            return "其它";
                        }
                    }
                    },
                    {
                        field: 'adminInfo',
                        title: '备注人',
                        width: 50,
                        align: "center",
                        formatter: function (val, row, index) {
                            return row.adminInfo.realName;
                        }
                    },
                    {field: 'remark', title: '备注', width: 400, align: "center"},
                ]]
            });
            
            $("#txlDg").datagrid({
                url: "${ctx}/usermanagement/userupdate/findFydContacts",
                nowrap: false, striped: true, remoteSort: false, pageSize: 20, fit: true,
                queryParams: {
                	search_EQ_userId: $("#remark_userId").val()
                },
                singleSelect: true,//是否单选
                pagination: true,//分页控件
                rownumbers: true,//行号
                fitColumns: true,//列宽自动填充满表格
                checkOnSelect: false,
                selectOnCheck: false,
                toolbar: "#txlDgTb",//工具栏
                loadFilter: function (data) {
                    if (data.content) {
                        return {total: data.totalElements, rows: data.content};
                    } else {
                        if (typeof data.length == "number" && typeof data.splice == "function") {
                            return {total: data.length, rows: data};
                        } else {
                            return data;
                        }
                    }
                },
                columns: [[
                    {field: 'mobileTel', title: '联系人手机号码', width: 100, align: "center"},
                    {field: 'realName', title: '联系人姓名', width: 50, align: "center"},
                    {field: 'status',title: '联系人状态',width: 50,align: "center", formatter: function (val, row, index) {
                        if (val == -1) {
                            return "未注册";
                        } else if (val == 0) {
                            return "新申请";
                        } else if (val == 1) {
                            return "通过";
                        } else if (val == 2) {
                            return "未通过";
                        } else if (val == 3) {
                            return "进行中";
                        }
                    }},
                    {field: 'mobileRemark', title: '用户对联系人的备注', width: 400, align: "center"},
                ]]
            });
        });

        function saveJcrzxx() {
            $.messager.confirm('确认', '是否执行该操作？', function (r) {
                if (r) {
                    $.messager.progress({
                        title: '处理中',
                        msg: '请稍后',
                    });
                    $('#jcrzxx_form').form('submit', {
                        url: "${ctx}/usermanagement/userupdate/isNewUpdate",//userupdate/revalidation
                        onSubmit: function (param) {
                        },
                        success: function (data) {
                            $.messager.progress('close');
                            var d = eval("(" + data + ")");
                            if (d.resultCode == 1) {
                                $.messager.alert("消息", "保存成功");
                                window.location.reload();
                            } else {
                                if (d.resultMsg) {
                                    $.messager.alert("消息", d.resultMsg);
                                } else {
                                    $.messager.alert("消息", "未知错误");
                                }
                            }
                        }
                    });
                }
            });
        }

        function addWarning(userId) {
            $.messager.confirm('确认', '是否执行该操作？', function (r) {
                if (userId != null && userId != "") {
                    if (r) {
                        $.messager.progress({
                            title: '处理中',
                            msg: '请稍后',
                        });
                        $.getJSON("${ctx}/userlevel/addWarning", {userId: userId},
                                function (data) {
                                    $.messager.progress('close');
                                    if (data.resultCode == 1) {
                                        $.messager.alert("消息", "保存成功");
                                        window.location.reload();
                                    } else {
                                        if (data.resultMsg) {
                                            $.messager.alert("消息", data.resultMsg);
                                        } else {
                                            $.messager.alert("消息", "未知错误");
                                        }
                                    }
                                });
                    }
                } else {
                    $.messager.alert("消息", "用户id不能为空！");
                }

            });

        }

        function cancelWarning(userId) {
            $.messager.confirm('确认', '是否执行该操作？', function (r) {
                if (userId != null && userId != "") {
                    if (r) {
                        $.messager.progress({
                            title: '处理中',
                            msg: '请稍后',
                        });
                        $.getJSON("${ctx}/userlevel/cancelWarning", {userId: userId},
                                function (data) {
                                    $.messager.progress('close');
                                    if (data.resultCode == 1) {
                                        $.messager.alert("消息", "保存成功");
                                        window.location.reload();
                                    } else {
                                        if (data.resultMsg) {
                                            $.messager.alert("消息", data.resultMsg);
                                        } else {
                                            $.messager.alert("消息", "未知错误");
                                        }
                                    }
                                });
                    }
                } else {
                    $.messager.alert("消息", "用户id不能为空！");
                }

            });

        }

        function addBlacklist(userId) {
            $.messager.confirm('确认', '是否执行该操作？', function (r) {
                if (userId != null && userId != "") {
                    if (r) {
                        $.messager.progress({
                            title: '处理中',
                            msg: '请稍后',
                        });
                        $.getJSON("${ctx}/userlevel/addBlacklist", {userId: userId},
                                function (data) {
                                    $.messager.progress('close');
                                    if (data.resultCode == 1) {
                                        $.messager.alert("消息", "保存成功");
                                        window.location.reload();
                                    } else {
                                        if (data.resultMsg) {
                                            $.messager.alert("消息", data.resultMsg);
                                        } else {
                                            $.messager.alert("消息", "未知错误");
                                        }
                                    }
                                });
                    }
                } else {
                    $.messager.alert("消息", "用户id不能为空！");
                }

            });

        }
        function cancelBlacklist(userId) {
            $.messager.confirm('确认', '是否执行该操作？', function (r) {
                if (userId != null && userId != "") {
                    if (r) {
                        $.messager.progress({
                            title: '处理中',
                            msg: '请稍后',
                        });
                        $.getJSON("${ctx}/userlevel/cancelBlacklist", {userId: userId},
                                function (data) {
                                    $.messager.progress('close');
                                    if (data.resultCode == 1) {
                                        $.messager.alert("消息", "保存成功");
                                        window.location.reload();
                                    } else {
                                        if (data.resultMsg) {
                                            $.messager.alert("消息", data.resultMsg);
                                        } else {
                                            $.messager.alert("消息", "未知错误");
                                        }
                                    }
                                });
                    }
                } else {
                    $.messager.alert("消息", "用户id不能为空！");
                }

            });

        }

        function addRemark() {
            $.messager.confirm('确认', '是否执行该操作？', function (r) {
                var type = $("#lx_select_value").val();
                var remark = $("#remarkDetail").val();
                if (type != null && type != "") {
                    if (remark != null && remark != "") {
                        if (r) {
                            $.messager.progress({
                                title: '处理中',
                                msg: '请稍后',
                            });
                            $('#remark_form').form('submit', {
                                url: "${ctx}/usermanagement/userupdate/savebz",
                                onSubmit: function (param) {
                                },
                                success: function (data) {
                                    $.messager.progress('close');
                                    var d = eval("(" + data + ")");
                                    if (d.resultCode == 1) {
                                        $.messager.alert("消息", "保存成功");
                                        window.location.reload();
                                    } else {
                                        if (d.resultMsg) {
                                            $.messager.alert("消息", d.resultMsg);
                                        } else {
                                            $.messager.alert("消息", "未知错误");
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        $.messager.alert("消息", "备注不能为空");
                    }
                } else {
                    $.messager.alert("消息", "请选择类型");
                }

            });
        }


        function showSrOrZcImg() {
            var incomeImgPth = '${aotsr.imgPth}';
            var payImgPth = '${aotzc.imgPth}';
            if (incomeImgPth.length > 0) {
                var incomeArr = incomeImgPth.split(",");
                for (var i = 0, len = incomeArr.length; i < len; i++) {
                    appendImg('${ctx}/upload' + incomeArr[i], 1);
                }
            }
            if (payImgPth.length > 0) {
                var payArr = payImgPth.split(",");
                for (var i = 0, len = payArr.length; i < len; i++) {
                    appendImg('${ctx}/upload' + payArr[i], 2);
                }
            }
        }
        function appendImg(imgPath, type) {
            var li = $('<li></li>');
            var img = $('<img style="display: block;width: 100%;height:100%"/>');
            var a = $('<a href="javascript:;"></a>');
            var span = $('<span></span>');
            a.append(img);
            img.attr('src', imgPath);
            li.append(a).append(span);
            $('#zczmUl').append(li);
        }

        function showImg(imgPath, title) {
            $("#imgShow").attr("src", imgPath);
            $('#imgShowDlg').dialog("setTitle", title);
            $('#imgShowDlg').dialog("open");
        }

        function closeDf() {
            window.parent.closeDf();
        }


        $(function () {
            $(window).resize(function () {
                $('#detailDlg').dialog('resize', {
                    width: $(document.body).width(),
                    height: $(document.body).height()
                });
            });
            $('#detailDlg').dialog({
                title: '催款记录',
                fit: true,
                draggable: false,
                closed: true,
                cache: false,
                modal: true
            });
        });
        function openDetail(orderCode) {
            var detailIframe = $('#detailIframe');
            var url = '${ctx}/myWorkflow/dunningRecord/getDunningRecord?orderCode=' + orderCode;
            detailIframe.attr('src', url);
            $('#detailDlg').dialog('open');
        }
        function loadJl() {
            var params = $('#jlDg').datagrid('options').queryParams;
            var fields = $('#queryJlForm').serializeArray();
            $.each(fields, function (i, field) {
                params[field.name] = $.trim(field.value);
            });
            $('#jlDg').datagrid('load');
        }
        function selectRemark() {
            var params = $('#bzDg').datagrid('options').queryParams;
            var fields = $('#remark_form').serializeArray();
            $.each(fields, function (i, field) {
                params[field.name] = $.trim(field.value);
            });
            $('#bzDg').datagrid('load');
        }
    </script>
</head>
<body>
<div id="tt" class="easyui-tabs" data-options="fit:true">
    <!--  style="width:500px;height:250px; -->
    <div id="hyxxzl" title="会员详细资料" style="overflow: auto; padding: 20px;">
			<span
                    style="display: inline-block; font-size: 6px; text-align: center; width: 100%">会员详细资料</span>

        <form id="jcrzxx_form" method="post" enctype="multipart/form-data">
            <!-- ggx start-->
            <div>
                <span class="table_title">个人信息:</span>
            </div>
            <table class="table table-bordered">
                <tr>
                    <td width="11%">姓名:
                        <input type="hidden" name="userId" id="userId" value="${user.id}">
                        <input type="hidden" id="arnid" name="arnid" value="${authRealName.id}">
                        <input type="hidden" name=arnid id=arnid value="${authRealName.id}">
                        <input type="hidden" name=realname id=realname value="${authRealName.name}">
                    </td>
                    <td width="22%"><input type="text" value="${user.name }" name="userName"></td>
                    <td width="11%">性别:</td>
                    <td width="22%">
                        <c:choose>
                            <c:when test="${user.sex==1 }">
                                男
                            </c:when>
                            <c:when test="${user.sex==2 }">
                                女
                            </c:when>
                        </c:choose>
                    </td>
                    <td width="11%">籍贯:</td>
                    <td>${user.homeTown}</td>
                </tr>
                <tr>
                    <td>身份证号:</td>
                    <td>${authRealName.idCode}</td>
                    <td>手机号码:</td>
                    <td>${user.mphone}</td>
                    <td>邮箱:</td>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <td>注册时间:</td>
                    <td><fmt:formatDate value="${user.createTime}" type="both"
                                        pattern="yyyy.MM.dd HH:mm:ss"/></td>
                    <td>提交认证时间:</td>
                    <td><fmt:formatDate value="${user.authSubmitTime}"
                                        type="both" pattern="yyyy.MM.dd HH:mm:ss"/></td>
                    <td>LBS:</td>
                    <td>${authRealName.lbs}<input type="hidden" id="asrid" name="asrid" value="${authSchoolRoll.id}">
                    </td>
                </tr>
                <tr>
                    <td>用户行为等级:</td>
                    <td><c:choose>
                        <c:when test="${user.userLevel==0}">正常</c:when>
                        <c:when test="${user.userLevel==-1}">预警</c:when>
                        <c:when test="${user.userLevel==-2}">黑名单</c:when>
                        <c:otherwise>${user.userLevel}</c:otherwise>
                    </c:choose></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
            <div>
                <span class="table_title">学信网信息:</span>
            </div>
            <table class="table table-bordered">
                <tr>
                    <td width="11%">姓名：</td>
                    <td width="22%">${auth.name }</td>
                    <td width="11%">性别：</td>
                    <td width="22%">${auth.sex }</td>
                    <td width="11%">民族：</td>
                    <td width="22%">${auth.nation }</td>
                </tr>
                <tr>
                    <td width="11%">出生日期：</td>
                    <td width="22%">${auth.birthday }</td>
                    <td width="11%">身份证号：</td>
                    <td width="22%">${auth.cardId }</td>
                    <td width="11%">考生号：</td>
                    <td width="22%">${auth.candidateId }</td>
                </tr>
                <tr>
                    <td width="11%">学号：</td>
                    <td width="22%">${auth.studentId }</td>
                    <td width="11%">院校名称：</td>
                    <td width="22%">${auth.academy }</td>
                    <td width="11%">分院：</td>
                    <td width="22%">${auth.branch }</td>
                </tr>
                <tr>
                    <td width="11%">系(所、函授站)：</td>
                    <td width="22%">${auth.department }</td>
                    <td width="11%">专业名称：</td>
                    <td width="22%">${auth.major }</td>
                    <td width="11%">班级：</td>
                    <td width="22%">${auth.classs }</td>
                </tr>
                <tr>
                    <td width="11%">层次：</td>
                    <td width="22%">${auth.degree }</td>
                    <td width="11%">学制：</td>
                    <td width="22%">${auth.time }</td>
                    <td width="11%">学历类别：</td>
                    <td width="22%">${auth.majorType }</td>
                </tr>
                <tr>
                    <td width="11%">学习形式：</td>
                    <td width="22%">${auth.studyType }</td>
                    <td width="11%">入学日期：</td>
                    <td width="22%">${auth.enterDate }</td>
                    <td width="11%">学籍状态：</td>
                    <td width="22%">${auth.state }</td>
                </tr>
                <tr>
                    <td width="11%">预计毕业日期：</td>
                    <td width="22%">${auth.offDate }</td>
                    <td width="11%">学籍半身照：</td>
                    <td style="height: 240px;"><a href="javascript:;"
                                                  onclick="showImg('${ctx}/upload${auth.schoolAvatar}','学籍半身照')"><img
                            style="display: block;width: 100%;height: 100%"
                            src="${ctx}/upload${auth.schoolAvatar}"/></a></td>
                    <td width="11%">学籍半身照2：</td>
                    <td style="height: 240px;"><a href="javascript:;"
                                                  onclick="showImg('${ctx}/upload${auth.schoolAvatar_2}','学籍半身照')"><img
                            style="display: block;width: 100%;height: 100%" src="${ctx}/upload${auth.schoolAvatar_2}"/></a>
                    </td>
                </tr>
            </table>
            <div>
                <span class="table_title">备用认证资料</span>
            </div>
            <table class="table table-bordered">
                <tr>
                    <td width="11%">姓名：</td>
                    <td width="22%">${authRealName.name }</td>
                    <td width="11%">性别：</td>
                    <td width="22%">
                        <c:choose>
                            <c:when test="${authRealName.sex==1 }">
                                男
                            </c:when>
                            <c:when test="${authRealName.sex==2 }">
                                女
                            </c:when>
                        </c:choose>
                    </td>
                    <td width="11%">身份证号：</td>
                    <td width="22%">${authRealName.idCode }</td>
                </tr>
                <tr>
                    <td width="11%">籍贯：</td>
                    <td width="22%"></td>
                    <td width="11%">学校：</td>
                    <td width="22%">${school.name}</td>
                    <td width="11%">院系/入学年份：</td>
                    <td width="22%">${authSchoolRoll.faculty.name}/${authSchoolRoll.year }</td>
                </tr>
                <tr>
                    <td width="11%">学制：</td>
                    <td width="22%">${authSchoolRoll.totalYear }</td>
                    <td width="11%"></td>
                    <td width="22%"></td>
                    <td width="11%"></td>
                    <td width="22%"></td>
                </tr>
            </table>
            <div>
					<span class="table_title">第三方身份信息:</span>
				</div>
				<table class="table table-bordered">
					<tr>
						<td width="11%">姓名：</td>
						<td width="22%">${thirdpart.nickName }</td>
						<td width="11%">性别：</td>
						<td width="22%">${thirdpart.sex }</td>
						<td width="11%">省份：</td>
						<td width="22%">${thirdpart.province }</td>
					</tr>
					<tr>
						<td width="11%">城市：</td>
						<td width="22%">${thirdpart.city }</td>
						<td width="11%">登陆方式：</td>
						<td width="22%"><c:choose>
								<c:when test="${thirdpart.loginType eq weixin }">微信登陆</c:when>
							</c:choose> ${thirdpart.loginType }</td>
						<td width="11%">openId：</td>
						<td width="22%">${thirdpart.openId }</td>
					</tr>
					<tr>
						<td width="11%">微信头像：</td>
						<td style="height: 240px;"><a href="javascript:;"
							onclick="showImg('${thirdpart.headImgUrl}','学籍半身照')"><img
								style="display: block; width: 100%; height: 100%"
								src='${thirdpart.headImgUrl}' /></a></td>
					</tr>
				</table>
            <table style="width: 100%">
                <tr>
                    <td><span class="table_title">身份证：</span></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td><span class="table_title">手持证件半身照：</span></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

                    <td><span class="table_title">学生证或校园一卡通或市民卡：</span></td>
                </tr>
                <tr>

                    <td><a href="javascript:;"
                           onclick="showImg('${ctx}/upload${authRealName.idPic}','身份证')"><img
                            style="display: inline-block; width: 200px; height: 150px;"
                            src="${ctx}/upload${authRealName.idPic}"/></a><br> <input
                            style="display: inline-block; width: 200px;" type="file"
                            name="idPic1"/></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td><a href="javascript:;"
                           onclick="showImg('${ctx}/upload${authRealName.personPic}','手持证件半身照')"><img
                            style="display: inline-block; width: 200px; height: 150px;"
                            src="${ctx}/upload${authRealName.personPic}"/></a><br> <input
                            style="display: inline-block; width: 200px;" type="file"
                            name="personPic1"/></td>
                    <td></td>
                    <td>1.<a href="javascript:;"
                             onclick="showImg('${ctx}/upload${detailPicList[0]}','学生证或校园一卡通或市民卡')"><img
                            style="display: inline-block; width: 200px; height: 150px;"
                            src="${ctx}/upload${detailPicList[0]}"/></a><br> <input
                            style="display: inline-block; width: 200px;" type="file"
                            name="pic1"/></td>
                    <td>2.<a href="javascript:;"
                             onclick="showImg('${ctx}/upload${detailPicList[1]}','学生证或校园一卡通或市民卡')"><img
                            style="display: inline-block; width: 200px; height: 150px;"
                            src="${ctx}/upload${detailPicList[1]}"/></a><br> <input
                            style="display: inline-block; width: 200px;" type="file"
                            name="pic2"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>3.<a href="javascript:;"
                             onclick="showImg('${ctx}/upload${detailPicList[2]}','学生证或校园一卡通或市民卡')"><img
                            style="display: inline-block; width: 200px; height: 150px;"
                            src="${ctx}/upload${detailPicList[2]}"/></a><br> <input
                            style="display: inline-block; width: 200px;" type="file"
                            name="pic3"/></td>
                    <td>4.<a href="javascript:;"
                             onclick="showImg('${ctx}/upload${detailPicList[3]}','学生证或校园一卡通或市民卡')"><img
                            style="display: inline-block; width: 200px; height: 150px;"
                            src="${ctx}/upload${detailPicList[3]}"/></a><br> <input
                            style="display: inline-block; width: 200px;" type="file"
                            name="pic4"/></td>

                </tr>
            </table>
            <table>
                <tr>
                    <td><span class="table_title">认证员:</span></td>
                    <td><label class="fqfl_xq_tr">${user.firstAdmin.realName}</label>
                    </td>
                </tr>
                <tr>
                    <td><span class="table_title">分配的认证员:</span></td>
                    <td><label class="fqfl_xq_tr">${user.adminName}</label>
                    </td>
                </tr>
                <tr style="height: 30px">
                </tr>
                <tr>
                    <td><span class="table_title">备注:</span></td>
                    <td><label class="fqfl_xq_tr"><textarea rows="8" cols="" maxlength="1024"
                                                            name="remark">${user.remark}</textarea></label>
                    </td>
                </tr>
            </table>
            <table class="table table-bordered">
                <tr>
                    <td width="11%">注册分数:</td>
                    <td width="22%">${zcScore.finalScore}</td>
                </tr>
                <tr>
                    <td width="11%">注册命中规则:</td>
                    <td>${zcHitRules}</td>
                </tr>
                <tr>
                    <td width="11%">登录分数:</td>
                    <td width="22%">${dlScore.finalScore}</td>
                </tr>
                <tr>
                    <td width="11%">登录命中规则:</td>
                    <td>${dlHitRules}</td>
                </tr>
                <tr>
                    <td width="11%">绑卡分数:</td>
                    <td width="22%">${bkScore.finalScore}</td>
                </tr>
                <tr>
                    <td width="11%">绑卡命中规则:</td>
                    <td>${bkHitRules}</td>
                </tr>
                <tr>
                    <td width="11%">提现分数:</td>
                    <td width="22%">${txScore.finalScore}</td>
                </tr>
                <tr>
                    <td width="11%">提现命中规则:</td>
                    <td>${txRiskItems}</td>
                </tr>
                <tr>
                    <td width="11%">充值分数:</td>
                    <td width="22%">${czScore.finalScore}</td>
                </tr>
                <tr>
                    <td width="11%">充值命中规则:</td>
                    <td>${czHitRules}</td>
                </tr>
            </table>
            <br>

            <div style="align-content: center">
                <button type="button" onclick="addWarning(${user.id})"
                        class="btn btn-warning ">加入预警名单
                </button>
                <button type="button" onclick="cancelWarning(${user.id})"
                        class="btn btn-warning ">取消预警
                </button>
                <button type="button" onclick="addBlacklist(${user.id})"
                        class="btn btn-warning ">加入黑名单
                </button>
                <button type="button" onclick="cancelBlacklist(${user.id})"
                        class="btn btn-warning ">取消黑名单
                </button>
                <button type="button" onclick="saveJcrzxx()"
                        class="btn btn-warning ">保存
                </button>
                <button type="button" onclick="closeDf()" class="btn btn-default">取消</button>
            </div>
            <!--ggx end  -->

        </form>
    </div>
    <div id="yhk" title="银行卡" style="overflow: auto; padding: 20px;">
        <form id="yhkxx" method="post" enctype="multipart/form-data">
            <table style="float:right;">
                <tr>
                    <td><span class="table_title">认证员:</span></td>
                    <td><label class="fqfl_xq_tr">${cardAdmin.realName}</label>
                    </td>
                </tr>
                <tr>
                    <td><span class="table_title">认证状态:</span></td>
                    <td><label class="fqfl_xq_tr">
                        <c:choose>
                            <c:when test="${authCardUserTask.taskStatus==1}">通过</c:when>
                            <c:when test="${authCardUserTask.taskStatus==2}">失败</c:when>
                            <c:when test="${authCardUserTask.taskStatus==0}">可执行</c:when>
                            <c:otherwise>待审核</c:otherwise>
                        </c:choose>
                    </label>
                    </td>
                </tr>
            </table>
            <span class="table_title">银行卡信息：</span>
            <table class="table_inner table_td">
                <tr>
                    <td>序号</td>
                    <td>开户银行</td>
                    <td>银行卡号</td>
                    <td>持卡人姓名</td>
                    <td>状态</td>
                </tr>
                <tr>
                    <c:forEach var="card" items="${authCard }" varStatus="status">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${card.bankName }</td>
                    <td>${card.cardNum }</td>
                    <td>${user.name }</td>
                    <c:choose>
                        <c:when test="${card.status==0}">
                            <td>新申请</td>
                        </c:when>
                        <c:when test="${card.status==1}">
                            <td>通过</td>
                        </c:when>
                        <c:when test="${card.status==2}">
                            <td>未通过</td>
                        </c:when>
                        <c:when test="${card.status==3}">
                            <td>解绑成功</td>
                        </c:when>
                    </c:choose>
                </tr>
                </c:forEach>
                </tr>
            </table>
            <table class="table table-bordered">
                <tr>
                    <td>身份证</td>
                    <td>手持身份证</td>
                </tr>
                <tr>
                    <td style="height: 300px;width: 300px;"><a href="javascript:;"
                                                               onclick="showImg('${ctx}/upload${authCardUserTask.idPic}','身份证')"><img
                            style="display: block;width: 100%;height: 100%"
                            src="${ctx}/upload${authCardUserTask.idPic}"/></a></td>
                    <td style="height: 300px;width: 300px;"><a href="javascript:;"
                                                               onclick="showImg('${ctx}/upload${authCardUserTask.personPic}','手持证件半身照')"><img
                            style="display: block;width: 100%;height: 100%"
                            src="${ctx}/upload${authCardUserTask.personPic}"/></a></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="gltx" title="关联同学" style="padding: 20px;">
        <form id="gjrzxx_form" method="post">
            <span class="table_title">关联同学信息：</span>
            <table class="table_inner table_td">
                <tr>
                    <c:forEach var="acm" items="${acm}">
                <tr>
                    <td align=right>关联同学姓名：</td>
                    <td><input type="text" name="linkName" id="gjrzxxlinkName"
                               value="${acm.linkName}"/></td>
                    <td align=right>关联同学手机号：：</td>
                    <td><input type="text" style="width: 95px;"
                               name="linkPhone" id="gjrzxxlinkPhone" value="${acm.linkPhone}"/></td>
                </tr>
                </c:forEach>
                </tr>
            </table>
        </form>
    </div>
    <div id="ckjl" title="催款记录" style="padding: 20px;">
        <form id="ckjlxx_form" method="post">
            <table class="table_inner table_td">
                <tr>
                    <td>催款时间</td>
                    <td>账单编号</td>
                    <td>账单金额</td>
                    <td>催款人</td>
                    <td>备注信息</td>
                </tr>
                <c:forEach var="duninngRecord" items="${duninngRecords}" varStatus="index">
                    <tr>
                        <td><fmt:formatDate value="${duninngRecord.createTime}" type="both"
                                            pattern="yyyy.MM.dd HH:mm:ss"/></td>
                        <td>${duninngRecord.vuserBill.billCode}</td>
                        <td>${duninngRecord.vuserBill.consumeAmount}</td>
                        <td width="40px">${duninngRecord.adminInfo.realName}</td>
                        <td>${duninngRecord.remark}</td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>
    <div id="jlTab" title="提现记录" style="padding:2px">
        <table id="jlDg">
        </table>
        <div id="jlDgTb">
            <form id="queryJlForm">
                <table class="table table-bordered" style="margin-top: 10px;">
                    <tr>
                        <td width="20%" style="text-align: right;vertical-align: middle;"><span>查询时间:</span></td>
                        <td>
                            <table>
                                <tr>
                                    <td style="border: none;">开始时间:</td>
                                    <td style="border: none;"><input class="easyui-datetimebox" name="startTime"
                                                                     data-options="showSeconds:false"
                                                                     style="width:150px"></td>
                                    <td style="border: none;">结束时间:</td>
                                    <td style="border: none;"><input class="easyui-datetimebox" name="endTime"
                                                                     data-options="showSeconds:false"
                                                                     style="width:150px"></td>
                                    <td style="border: none;"><a href="javascript:;" class="easyui-linkbutton"
                                                                 data-options="iconCls:'icon-search'"
                                                                 onclick="loadJl()">搜索</a></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id="bzTab" title="备注" style="padding:2px">
        <table id="bzDg">
        </table>
        <div id="bzDgTb">
            <form id="remark_form">
                <input type="hidden" name="userId" id="remark_userId" value="${user.id}">
                <table class="table table-bordered" style="margin-top: 10px;">
                    <tr>
                        <td width="20%" style="text-align: right;vertical-align: middle;"><span>查询时间:</span></td>
                        <td>
                            <table>
                                <tr>
                                    <td style="border: none;">开始时间:</td>
                                    <td style="border: none;"><input class="easyui-datetimebox" name="startTime"
                                                                     data-options="showSeconds:false"
                                                                     style="width:150px"></td>
                                    <td style="border: none;">结束时间:</td>
                                    <td style="border: none;"><input class="easyui-datetimebox" name="endTime"
                                                                     data-options="showSeconds:false"
                                                                     style="width:150px"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right;vertical-align: middle;"><span>类型:</span></td>
                        <td>
                            <select style="margin: 0" name="search_EQ_type" id="lx_select_value">
                                <option value="">请选择</option>
                                <option value="0">认证</option>
                                <option value="1">提现</option>
                                <option value="2">催款</option>
                                <option value="3">预警</option>
                                <option value="4">黑名单</option>
                                <option value="5">其它</option>
                            </select>
                            <button type="button" onclick="selectRemark()">搜索</button>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right;vertical-align: middle;"><span id="userRemark"><input type="hidden"
                                                                                                           name="userReamrkId"
                                                                                                           value="${user.id}">备注:</span>
                        </td>
                        <td style="vertical-align: middle;"><textarea style="width:90%" rows="2" maxlength="1024"
                                                                      name="remark" id="remarkDetail"></textarea>
                            <button type="button"class="btn btn-primary" onclick="addRemark()">保存</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id="txlTab" title="通讯录" style="padding:2px">
        <table id="txlDg">
        </table>
        <div id="txlDgTb">
        </div>
    </div>
</div>
<div id="imgShowDlg">
    <img id="imgShow" alt="" src="" style="display: block;width: 100%;height: 100%">
</div>
<div id="detailDlg">
    <iframe id="detailIframe" frameborder="0" marginheight="0" marginwidth="0"
            style="width:100%;height:99%;" scrolling="yes"></iframe>
</div>
</body>
</html>