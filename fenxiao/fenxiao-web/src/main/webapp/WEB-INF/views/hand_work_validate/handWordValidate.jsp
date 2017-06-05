<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>待审核订单</title>
    <style type="text/css">
        form {
            margin: 0px 0 10px;
        }

        #ok {
            color: rgb(255, 255, 255);
            font-size: 12px;
            padding-top: 3px;
            padding-bottom: 3px;
            padding-left: 6px;
            padding-right: 6px;
            border-width: 2px;
            border-color: rgb(197, 229, 145);
            border-style: solid;
            border-radius: 8px;
            background-color: rgb(14, 143, 40);
        }

        #ok:hover {
            color: #ffffff;
            background-color: #78c300;
            border-color: #c5e591;
        }

        #no {
            color: rgb(255, 255, 255);
            font-size: 12px;
            padding-top: 3px;
            padding-bottom: 3px;
            padding-left: 6px;
            padding-right: 6px;
            border-width: 2px;
            border-color: #fad2e1;
            border-style: solid;
            border-radius: 8px;
            background-color: rgb(230, 23, 33);
            border-style: solid;
        }

        #no:hover {
            color: #ffffff;
            background-color: #ff3300;
            border-color: #fad2e1;
        }
    </style>
    <script>
        //通过
        function agree(id) {
            console.log(11);
            $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
                if (data) {
                    $.getJSON("${ctx}/handWorkValidate/updateRechargeAgree", {
                        id: id
                    }, function (data) {
                        console.log(data);
                        if (data.resultCode == -1) {
                            $.messager.alert('消息', data.resultMsg);
                        } else {
                            $.messager.alert('消息', data.resultMsg);
                            $("#waitorder").datagrid("reload");
                            $("#rechargeRecord").datagrid("reload");
                        }
                    });
                }
            });
        }

        function disagree(id) {
            $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
                if (data) {
                    $.getJSON("${ctx}/handWorkValidate/updateRechargeDisagree", {
                        id: id
                    }, function (data) {
                        console.log(data);
                        if (data.resultCode == -1) {
                            $.messager.alert('消息', data.resultMsg);
                        } else {
                            $.messager.alert('消息', data.resultMsg);
                            $("#waitorder").datagrid("reload");
                            $("#rechargeRecord").datagrid("reload");

                        }
                    });
                }
            });
        }
        $(function () {
            //还款账单
            $("#waitorder")
                    .datagrid(
                            {
                                url: "${ctx}/handWorkValidate/getFXWaitValidateRecord",
                                nowrap: false,
                                striped: true,
                                remoteSort: false,
                                pageSize: 20,
                                fit: true,
                                queryParams: {
                                    search_EQ_status: 0
                                },
                                singleSelect: true,//是否单选
                                pagination: true,//分页控件
                                rownumbers: true,//行号
                                fitColumns: true,//列宽自动填充满表格
                                checkOnSelect: false,
                                selectOnCheck: false,
                                toolbar: "#hkDgTb",//工具栏
                                loadFilter: function (data) {
                                    console.log(data);
                                    if (data.content) {

                                        return {
                                            total: data.totalElements,
                                            rows: data.content
                                        };
                                    } else {
                                        if (typeof data.length == "number"
                                                && typeof data.splice == "function") {
                                            return {
                                                total: data.length,
                                                rows: data
                                            };
                                        } else {
                                            return data;
                                        }
                                    }
                                },
                                columns: [[
                                    {
                                        field: 'mid',
                                        title: '合作商',
                                        width: 150,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + row.enterprise.name + "</b>";
                                        }
                                    },
                                    {
                                        field: 'phone',
                                        title: '充值账号',
                                        width: 150,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + val + "</b>";
                                        }
                                    },
                                    {
                                        field: 'size',
                                        title: '面值',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + val + "</b>";
                                        }
                                    },
                                    {
                                        field: 'buyNum',
                                        title: '数量',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + val + "</b>";
                                        }
                                    },
                                    {
                                        field: 'snum',
                                        title: '订单号',
                                        width: 120,
                                        align: "center"
                                    },
                                    {
                                        field: 'flowType',
                                        title: '流量类型',
                                        width: 80,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (val == 0) {
                                                return "<b>" + "漫游" + "</b>";
                                            } else if (val == 1) {
                                                return "<b>" + "本地" + "</b>";
                                            } else if (val == -1) {
                                                return "<b>--</b>";
                                            }
                                        }
                                    },
                                    {
                                        field: 'businessType',
                                        title: '业务类型',
                                        width: 80,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (val == 0) {
                                                return "<b>" + "流量" + "</b>";
                                            } else if (val == 1) {
                                                return "<b>" + "话费" + "</b>";
                                            } else if (val == 3) {
                                                return "<b>加油卡</b>";
                                            }
                                        }
                                    },
                                    {
                                        field: 'applyOperator',
                                        title: '申请人',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'createTime',
                                        title: '申请时间',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'description',
                                        title: '备注',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'demo',
                                        title: '操作',
                                        width: 100,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return '<input type="button" id="ok" value="通过" onclick="agree('
                                                    + row.id
                                                    + ')"/><input type="button" id="no" value="驳回" onclick="disagree('
                                                    + row.id + ')"/>';
                                        }
                                    },]],
                            });
            $(window).resize(function () {
                $('#zdxqDlg').dialog('resize', {
                    width: $(document.body).width(),
                    height: $(document.body).height()
                });
            });
            $('#zdxqDlg').dialog({
                title: '账单详情',
                fit: true,
                draggable: false,
                closed: true,
                cache: false,
                modal: true
            });
            $("#rechargeRecord")
                    .datagrid(
                            {
                                url: "${ctx}/handWorkValidate/getFXWaitValidateRecord",
                                nowrap: false,
                                striped: true,
                                remoteSort: false,
                                pageSize: 20,
                                fit: true,
                                queryParams: {
                                    //search_EQ_status : 0
                                },
                                singleSelect: true,//是否单选
                                pagination: true,//分页控件
                                rownumbers: true,//行号
                                fitColumns: true,//列宽自动填充满表格
                                checkOnSelect: false,
                                selectOnCheck: false,
                                toolbar: "#hkDgTb2",//工具栏
                                loadFilter: function (data) {
                                    console.log(data);
                                    if (data.content) {

                                        return {
                                            total: data.totalElements,
                                            rows: data.content
                                        };
                                    } else {
                                        if (typeof data.length == "number"
                                                && typeof data.splice == "function") {
                                            return {
                                                total: data.length,
                                                rows: data
                                            };
                                        } else {
                                            return data;
                                        }
                                    }
                                },
                                columns: [[
                                    {
                                        field: 'mid',
                                        title: '合作商',
                                        width: 150,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + row.enterprise.name + "</b>";
                                        }
                                    },
                                    {
                                        field: 'phone',
                                        title: '充值账号',
                                        width: 150,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + val + "</b>";
                                        }
                                    },
                                    {
                                        field: 'size',
                                        title: '面值',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + val + "</b>";
                                        }
                                    },
                                    {
                                        field: 'buyNum',
                                        title: '数量',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return "<b>" + val + "</b>";
                                        }
                                    },
                                    {
                                        field: 'snum',
                                        title: '订单号',
                                        width: 120,
                                        align: "center"
                                    },
                                    {
                                        field: 'flowType',
                                        title: '流量类型',
                                        width: 80,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (val == 0) {
                                                return "<b>" + "漫游" + "</b>";
                                            } else if (val == 1) {
                                                return "<b>" + "本地" + "</b>";
                                            } else if (val == -1) {
                                                return "<b>--</b>";
                                            }
                                        }
                                    },
                                    {
                                        field: 'businessType',
                                        title: '业务类型',
                                        width: 80,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (val == 0) {
                                                return "<b>" + "流量" + "</b>";
                                            } else if (val == 1) {
                                                return "<b>" + "话费" + "</b>";
                                            } else if (val == 3) {
                                                return "<b>加油卡</b>";
                                            }
                                        }
                                    },
                                    {
                                        field: 'applyOperator',
                                        title: '申请人',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'createTime',
                                        title: '申请时间',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'description',
                                        title: '备注',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'checkOperator',
                                        title: '确认人',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'status',
                                        title: '状态',
                                        width: 80,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (row.status == 0) {
                                                return '<span style="color:white;background:orange">未处理</span>';
                                            } else if (row.status == 1) {
                                                return '<span style="color:white;background:green">已通过</span>';
                                            } else {
                                                return '<span style="color:white;background:red">已驳回</span>';
                                            }
                                        }
                                    }]],
                            });
            $(window).resize(function () {
                $('#zdxqDlg').dialog('resize', {
                    width: $(document.body).width(),
                    height: $(document.body).height()
                });
            });
            $('#zdxqDlg').dialog({
                title: '账单详情',
                fit: true,
                draggable: false,
                closed: true,
                cache: false,
                modal: true
            });
        });
        //普通账单
        function loadHk() {
            var params = $('#waitorder').datagrid('options').queryParams;
            var fields = $('#queryHkForm').serializeArray();
            $.each(fields, function (i, field) {
                params[field.name] = $.trim(field.value);
            });
            $('#waitorder').datagrid('load');
        }
        function loadHk1() {
            var params = $('#rechargeRecord').datagrid('options').queryParams;
            var fields = $('#queryHkForm1').serializeArray();
            $.each(fields, function (i, field) {
                params[field.name] = $.trim(field.value);
            });
            $('#rechargeRecord').datagrid('load');
        }
        //导出Excel表格

        function hkExportExcel() {
            $.messager.confirm('确认', '确认把该搜索结果导出Excel表格 ？', function (r) {
                if (r) {
                    var startTime = $('#startTime2').datetimebox('getValue');
                    var endTime = $('#endTime2').datetimebox('getValue');
                    var eId = $('#eId2').val();
                    var eName = $('#eId2').find("option:selected").text();
                    //	var orderNo = $("#billCode").val();
                    //	var orderStatus = $("#select_value").val();
                    //	var provinceId = $("#provinceId").val();
                    //	var completeStartTime1 = $('#completeStartTime1').datetimebox(
                    //			'getValue');
                    //	var completeEndTime1 = $('#completeEndTime1').datetimebox(
                    //			'getValue');
                    $.messager.progress({
                        title: '处理中',
                        msg: '请稍后',
                    });
                    $.messager.progress('close');

                    location.href = "${ctx}/export/exportBalanceManageExcel?startTime="
                            + startTime + "&endTime=" + endTime + "&eId=" + eId
                            + "&eName=" + eName;
                }
            });
        }
    </script>
</head>
<body>
<div id="hkzdTabs" class="easyui-tabs" data-options="fit:true">
    <div id="hkzdTab" title="待审核充值纪录" style="padding: 2px">
        <table id="waitorder">
        </table>
        <div id="hkDgTb">
            <form id="queryHkForm">
                <table class=" datagrid-cell-c1-status"
                       style="border-collapse: separate; border-spacing: 0px; height: 15px;">
                    <tr>
                        <td style="text-align: right; vertical-align: middle;"><span>申请开始时间</span></td>
                        <td>
                            <table class="datagrid-cell datagrid-cell-c1-status"
                                   style="border-collapse: separate; border-spacing: 0px; height: 15px;">
                                <tr>
                                    <td style="border: none;"><input
                                            class="easyui-datetimebox" name="startTime" id="startTime1"
                                            data-options="showSeconds:false" style="width: 150px"></td>
                                    <td style="border: none;">申请结束时间<input
                                            class="easyui-datetimebox" name="endTime" id="endTime1"
                                            data-options="showSeconds:false" style="width: 150px"></td>
                                </tr>
                            </table>
                        <td>业务类型</td>
                        <td><select id="businessType1" name="search_EQ_businessType">
                            <option value="">--请选择--</option>
                            <option value="0">流量</option>
                            <option value="1">话费</option>
                            <option value="3">加油卡</option>
                        </select></td>
                        <td>
                            <table>
                                <tr>

                                    <td style="border: none; vertical-align: middle;"><a
                                            href="javascript:;" class="easyui-linkbutton"
                                            data-options="iconCls:'icon-search'" onclick="loadHk()">搜索</a>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    </tr>
                    <tr>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id="hkzdTab" title="充值记录" style="padding: 2px">
        <table id="rechargeRecord">
        </table>
        <div id="hkDgTb2">
            <form id="queryHkForm1">
                <table class="datagrid-cell-c1-status"
                       style="border-collapse: separate; border-spacing: 0px; height: 15px;">
                    <tr>
                        <td style="text-align: right; vertical-align: middle;"><span>申请开始时间</span></td>
                        <td>
                            <table class="datagrid-cell datagrid-cell-c1-status"
                                   style="border-collapse: separate; border-spacing: 0px; height: 15px;">
                                <tr>
                                    <td style="border: none;"><input
                                            class="easyui-datetimebox" name="startTime" id="startTime2"
                                            data-options="showSeconds:false" style="width: 150px"></td>
                                    <td style="border: none;">申请结束时间<input
                                            class="easyui-datetimebox" name="endTime" id="endTime2"
                                            data-options="showSeconds:false" style="width: 150px"></td>
                                </tr>
                            </table>
                        <td>业务类型</td>
                        <td><select id="businessType2" name="search_EQ_businessType" style="width: 120px;">
                            <option value="">--请选择--</option>
                            <option value="0">流量</option>
                            <option value="1">话费</option>
                            <option value="3">加油卡</option>
                        </select></td>
                        <td>
                            <table>
                                <tr>
                                    <td style="border: none; vertical-align: middle;"><a
                                            href="javascript:;" class="easyui-linkbutton"
                                            data-options="iconCls:'icon-search'" onclick="loadHk1()">搜索</a>
                                    </td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td style="border: none;"><input type="button"
                                                                     onclick="hkExportExcel();" value="导出excel"
                                                                     style="display: block; size: 20px; background-color: #95B8E7">
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    </tr>
                    <tr>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="zdxqDlg">
    <iframe id="zdxqIframe" frameborder="0" marginheight="0"
            marginwidth="0" style="width: 100%; height: 99%;" scrolling="yes"></iframe>
</div>
</body>
</html>