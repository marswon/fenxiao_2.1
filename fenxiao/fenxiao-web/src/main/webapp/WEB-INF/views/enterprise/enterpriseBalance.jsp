<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>分销商管理</title>
    <script>
        $(function () {
            $("#enterpriseList")
                    .datagrid(
                            {
                                url: '${ctx}/api/enterprise/getEnterpriseListBalance',
                                nowrap: true,
                                striped: true,
                                remoteSort: false,
                                pageSize: 20,
                                fit: true,
                                queryParams: {},
                                singleSelect: true,//是否单选
                                pagination: true,//分页控件
                                rownumbers: true,//行号
                                fitColumns: true,//列宽自动填充满表格
                                checkOnSelect: false,
                                selectOnCheck: false,
                                toolbar: "#hyDgTb",//工具栏
                                loadFilter: function (data) {
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
                                        field: 'id',
                                        title: 'id',
                                        width: 7,
                                        hidden: true
                                    },
                                    {
                                        field: 'mid',
                                        title: '合作商编号',
                                        width: 50,
                                        align: "center",
                                    },
                                    {
                                        field: 'name',
                                        title: '合作商名称',
                                        width: 100,
                                        align: "center",
                                    },
                                    {
                                        field: 'address',
                                        title: '地址',
                                        width: 100,
                                        align: "center",
                                    },
                                    {
                                        field: 'status',
                                        title: '状态',
                                        width: 30,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (row.status == 0) {
                                                return "可用";
                                            } else {
                                                return "不可用";
                                            }
                                        }
                                    },
                                    {
                                        field: 'balance',
                                        title: '余额',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return row.balance / 1000 + "元";
                                        }
                                    },
                                    {
                                        field: 'creditTopBalance',
                                        title: '冻结金额',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return row.creditTopBalance / 1000
                                                    + "元";
                                        }
                                    },
                                    {
                                        field: 'businessType',
                                        title: '业务类型',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (val == 0) {
                                                return "流量";
                                            } else if (val == 1) {
                                                return "话费";
                                            } else if (val == 2) {
                                                return "物业缴费";
                                            } else if (val == 3) {
                                                return "加油卡";
                                            }
                                        }
                                    },
                                    {
                                        field: 'selfProductType',
                                        title: '营销类型',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (val == 0) {
                                                return "非自营";
                                            } else if (val == 1) {
                                                return "自营";
                                            }
                                        }
                                    },
                                ]],
                            });

            $("#search_id").click(function () {
                var params = $('#enterpriseList').datagrid('options').queryParams;
                var fields = $('#queryForm').serializeArray();
                $.each(fields, function (i, field) {
                    params[field.name] = $.trim(field.value);
                });
                $('#enterpriseList').datagrid('load');
            });
            //=====================================================================
        });
    </script>
</head>
<body>
<div id="zjjyTabs" class="easyui-tabs" data-options="fit:true">
    <div id="hyTab" title="流量话费合作商" style="padding: 1px">
        <table id="enterpriseList">
        </table>
        <div id="hyDgTb">
            <!-- 主界面搜索框 -->
            <form id="queryForm">
                <div id="tb" style="padding: 5px; height: auto"
                     class="datagrid-toolbar">
                    <b>合作商名称：</b><input type="text" name="search_LIKE_name"> <b>合作商编号：</b><input
                        type="text" name="search_LIKE_mid"> <b>业务类型：</b> <select
                        class="sel" name="search_EQ_businessType"
                        id="search_EQ_businessType"
                        style="width: 80px; padding-left: 0px;">
                    <option value="">-请选择-</option>
                    <option value="0">流量</option>
                    <option value="1">话费</option>
                    <!--  <option value="2">物业缴费</option>
                    <option value="3">加油卡</option>-->
                </select>
                    <b>营销类型：</b> <select
                        class="sel" name="search_EQ_selfProductType"
                        id="search_EQ_selfProductType"
                        style="width: 80px; padding-left: 0px;">
                    <option value="">-请选择-</option>
                    <option value="0">非自营</option>
                    <option value="1">自营</option>
                </select>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search"
                       id="search_id">查询</a> <span id="js_valdate"
                                                   style="color: red"></span>&nbsp;<span
                        style="color: red"
                        id="js_no"></span>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>