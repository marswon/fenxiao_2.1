<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>客户资金交易记录</title>
    <script>
        $(function () {
            $("#camilist")
                    .datagrid(
                            {
                                url: '${ctx}/cami/findFXCami',
                                nowrap: false,
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
                                columns: [[
                                    {
                                        field: 'id',
                                        title: 'id',
                                        width: 7,
                                        hidden: true
                                    },
                                    {
                                        field: 'camiStr',
                                        title: '卡密',
                                        width: 120,
                                        align: "center"
                                    },
                                    {
                                        field: 'camiStrCard',
                                        title: '卡号',
                                        width: 120,
                                        align: "center"
                                    },
                                    {
                                        field: 'flowType',
                                        title: '类型',
                                        width: 50,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (row.flowType == 0) {
                                                return "漫游";
                                            } else {
                                                return "本地";
                                            }
                                        }
                                    },
                                    {
                                        field: 'size',
                                        title: '面值',
                                        width: 50,
                                        align: "center"
                                    },
                                    {
                                        field: 'businessType',
                                        title: '业务类型',
                                        width: 100,
                                        align: "center",
                                        formatter:function (val,row,index) {
                                            if(row.businessType==0){
                                                return "流量";
                                            }else if(row.businessType==1){
                                                return "话费";
                                            }else if(row.businessType==3){
                                                return "加油卡";
                                            }
                                        }
                                    },
                                    {
                                        field: 'createTime',
                                        title: '创建时间',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'updateTime',
                                        title: '更新时间',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'status',
                                        title: '状态',
                                        width: 100,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (row.status == 0) {
                                                return '<span id="ysj'
                                                        + row.id
                                                        + '" style="color:#fff;background:green">未使用</span>';
                                            } else if (row.status == 1) {
                                                return '<span id="yxj'
                                                        + row.id
                                                        + '" style="color:#fff;background:red">已使用</span>';
                                            } else if(row.status == 2){
                                                return '<span id="yxj'
                                                        + row.id
                                                        + '" style="color:#fff;background:gold">无效</span>';
                                            } else if(row.status == 3){
                                            	return '<span id="yxj'
                                                		+ row.id
                                               			+ '" style="color:#fff;background:gold">使用中</span>';
                                            }
                                        }
                                    }]],
                            });
            //
            $("#phoneLibrary")
                    .datagrid(
                            {
                                url: '${ctx}/phoneLibrary/findAll',
                                nowrap: false,
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
                                toolbar: "#addPhoneLibrary",//工具栏
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
                                        field: 'phone',
                                        title: '号码',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'commPort',
                                        title: '端口',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'toPhone',
                                        title: '发送至手机号',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'status',
                                        title: '状态',
                                        width: 100,
                                        align: "center",
                                        formatter:function (val,row,index) {
                                            if(row.status==0){
                                                return "不可用";
                                            }else if(row.status==1){
                                                return "可用";
                                            }else {
                                                return "异常";
                                            }
                                        }
                                    },
                                    {
                                        field: 'createTime',
                                        title: '创建时间',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'updateTime',
                                        title: '更新时间',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'remarks',
                                        title: '备注',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'phoneGroup',
                                        title: '号码组',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'sendCountToDay',
                                        title: '今日已发送条数',
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'edit',
                                        title: '操作',
                                        width: 100,
                                        align: "center",
                                        formatter : function(val, row, index) {
                                            return '<input type="button" value="编辑" onclick="addPhoneLibrary(\''
                                                    + row.id
                                                    + '\');"/>';
                                        }
                                    },
                                    ]],
                            });
            //
            $("#search_id").click(function () {
                var params = $('#camilist').datagrid('options').queryParams;
                var fields = $('#queryForm').serializeArray();
                $.each(fields, function (i, field) {
                    params[field.name] = $.trim(field.value);
                });
                $('#camilist').datagrid('load');
            });
            $('#phoneLibraryView').dialog({
                width: 420,
                height: 500,
                closed: true,
                cache: false,
                modal: true,
                title:"添加"
            });
            $(window).resize(function () {
                $('#zdxqDlg').dialog('resize', {
                    width: $(document.body).width(),
                    height: $(document.body).height()
                });
            });
        });


        function updateAdmin() {
            $('#adminDetail').form('submit', {
                url: "${ctx}/adminManage/updateAdmin",
                onSubmit: function (param) {
                },
                success: function (data) {
                    var d = eval("(" + data + ")");
                    if (d.resultCode == 1) {
                        $.messager.alert("消息", "更新成功");
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
        /**
         * 保存卡密入库
         */
        function save() {
            $('#save_cami').form('submit', {
                url: "${ctx}/cami/saveCami",
                onSubmit: function (param) {
                },
                success: function (data) {
                    var d = eval("(" + data + ")");
                    if (d.resultCode == 1) {
                        $.messager.alert("消息", d.resultMsg);
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

        //新增号码
        function addPhoneLibrary(id) {
           // $("#save_phoneLibrary").empty();
            if (id) {
                //查询该ID的记录信息
                $
                        .getJSON(
                                "${ctx}/phoneLibrary/findOnePhoneLibrary",
                                {
                                    id: id
                                },
                                function (data) {
                                    if (data.resultCode == -1) {
                                        $.messager.alert('消息', data.resultMsg);
                                    } else {
                                        //phone
                                        $("#phone").val(data.content.phone);
                                        //remarks
                                        $("#remarks").val(
                                                data.content.remarks);
                                        //commPort不可编辑
                                        //p$('#commPort').attr("disabled",true)
                                        //commPort
                                        $("#commPort").val(
                                                data.content.commPort);
                                        //设置默认选中流量类型
                                        $("#flowTypeByProvinceId_liuliang").val(
                                                data.content.flowType);
                                        //toPhone
                                        $("#phoneGroup_status").val(
                                                data.content.status);
                                        //phoneGroup不可修改
                                        //$('#phoneGroup').attr("disabled",true)
                                        //phoneGroup
                                        $("#phoneGroup").val(data.content.phoneGroup);
                                        //id
                                        $("#id").val(data.content.id);
                                    }
                                });
            }
            $('#phoneLibraryView').dialog('open');

        }

        //保存
        function savePhoneLibrary() {
            $.messager.confirm("操作提示", "您确定要保存吗？", function (data) {
                if (data) {
                    $('#save_phoneLibrary').form('submit', {
                        url: "${ctx}/phoneLibrary/save",
                        onSubmit: function (param) {
                        },
                        success: function (data) {
                            var d = eval("(" + data + ")");
                            if (d.resultCode == 1) {
                                $.messager.confirm("操作提示", "保存成功", function (data) {
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
                    //alert("取消");
                }
            });
        }
        function search_PhoneLibrary() {
                var params = $('#phoneLibrary').datagrid('options').queryParams;
                var fields = $('#search_phoneLibrary').serializeArray();
                $.each(fields, function(i, field) {
                    params[field.name] = $.trim(field.value);
                });
                 $('#phoneLibrary').datagrid('load');
        }

        function onchange_businessType(){
            //获取被选中的option标签
            var vs = $("#businessType").val();
            if(vs==3){
                $("#businessTypeTR").show();
            }else{
                $("#businessTypeTR").hide();
            }
        }
    </script>
</head>
<body>
<div id="zjjyTabs" class="easyui-tabs" data-options="fit:true">
    <div id="hyTab" title="卡密管理" style="padding: 1px">
        <table id="camilist">
        </table>
        <div id="hyDgTb">
            <form id="queryForm">
                <div id="tb" style="padding: 5px; height: auto"
                     class="datagrid-toolbar">
                    <td>卡密：</td>
                    <td><input type="text"  style="width: 100px;" name="search_EQ_camiStr" />
                    </td>
                    <b>流量类型：</b>
                    <select class="sel"
                            name="search_EQ_flowType" id="flowType"
                            style="width: 100px; padding-left: 0px;">
                        <option value="">-请选择-</option>
                        <option value="0">漫游</option>
                        <option value="1">本地</option>
                    </select>
                    <b>流量大小：</b>
                    <select class="sel"
                            name="search_EQ_size"
                            style="width: 100px; padding-left: 0px;">
                        <option value="">-请选择-</option>
                        <option value="5">5M</option>
                        <option value="10">10M</option>
                        <option value="20">20M</option>
                        <option value="30">30M</option>
                        <option value="50">50M</option>
                        <option value="70">70M</option>
                        <option value="100">100M</option>
                        <option value="200">200M</option>
                        <option value="300">300M</option>
                        <option value="500">500M</option>
                        <option value="1024">1G</option>
                        <option value="2048">2G</option>
                        <option value="3072">3G</option>
                        <option value="5120">5G</option>
                    </select>
                    <b>状态：</b>
                    <select class="sel"
                            name="search_EQ_status" id="status"
                            style="width: 100px; padding-left: 0px;">
                        <option value="">-请选择-</option>
                        <option value="0">未使用</option>
                        <option value="1">已使用</option>
                        <option value="2">无效</option>
                    </select>
                    <b>业务类型：</b>
                    <select class="sel"
                            name="search_EQ_businessType"
                            style="width: 100px; padding-left: 0px;">
                        <option value="">-请选择-</option>
                        <option value="0">流量</option>
                        <option value="1">话费</option>
                        <option value="3">加油卡</option>
                        <option value="4">兑换码</option>
                    </select>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search"
                       id="search_id">查询</a> <span id="js_valdate" style="color: red"></span>&nbsp;<span
                        style="color: red" id="js_no"></span>
                </div>
            </form>
        </div>
    </div>
    <div id="jxrzxx" title="添加卡密" style="padding: 1px">
        <!-- display:none; -->
        <div style="padding: 10px">
            <form id="save_cami">
                <table cellpadding="4">

                    <tr>
                        <td>业务类型：</td>
                        <td><select class="sel" onchange="onchange_businessType()"
                                    name="businessType" id="businessType"
                                    style="width: 100px; padding-left: 0px;">
                            <option value="">-请选择-</option>
                            <option value="0">流量</option>
                            <option value="1">话费</option>
                            <option value="3">加油卡</option>
                            <option value="4">兑换码</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>充值类型：</td>
                        <td><input type="radio" name="flowType" value="0"
                                   checked="checked">&nbsp;漫游&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
                                type="radio" name="flowType" value="1">&nbsp;省内
                        </td>
                    </tr>
                    <tr>
                        <td>面值：</td>
                        <td><select class="sel"
                                    name="size" id="size"
                                    style="width: 100px; padding-left: 0px;">
                            <option value="">-请选择-</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="30">30</option>
                            <option value="50">50</option>
                            <option value="70">70</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                            <option value="300">300</option>
                            <option value="500">500</option>
                            <option value="1000">1000</option>
                            <option value="1024">1G（仅流量）</option>
                            <option value="2048">2G（仅流量）</option>
                            <option value="3072">3G（仅流量）</option>
                            <option value="5120">5G（仅流量）</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>卡密</td>
                        <td><textarea type="text" id="camiStr" name="camiStr" style="height: 50px;width:200px"
                                      size="14"></textarea>多个卡密用英文逗号分割(,)
                        </td>
                    </tr>
                    <tr id="businessTypeTR" style="display: none">
                        <td>卡号</td>
                        <td><textarea type="text" id="camiStrCard" name="camiStrCard" style="height: 50px;width:200px"
                                      size="14"></textarea>仅类型为加油卡时填写,和卡密一一对应,多个卡号用英文逗号分割(,)
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <button type="button" onclick="save()">保存</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id="library" title="手机号码库" style="padding: 1px">
        <div id="addPhoneLibrary">
            <form id="search_phoneLibrary" method="post">
                手机号:<input name="search_LIKE_phone">
                猫池端口:<input name="search_LIKE_commPort">
                号码组:
                <select name="search_EQ_phoneGroup">
                    <option value="">-请选择-</option>
                    <option value="1">组1</option>
                    <option value="2">组2</option>
                </select>
                <a href="javascript:;"
                   class="easyui-linkbutton"
                   style="width: 100px; "
                   data-options="iconCls:'icon-search'" onclick="search_PhoneLibrary();">搜索</a>&nbsp;&nbsp;&nbsp;&nbsp;

                <a href="javascript:;" class="easyui-linkbutton" style=" margin-left: 150px"
                   data-options="iconCls:'icon-add'" id="add_product"
                   onclick="addPhoneLibrary(null);">新增</a>
            </form>
        </div>
        <table id="phoneLibrary"></table>
        <div id="phoneLibraryView" style="margin-top: 10px;">
            <form id="save_phoneLibrary" method="post">
                <table cellpadding="2">
                    <tr>
                        <td>号码：</td>
                        <td><input name="phone" id="phone"/></td>
                    </tr>
                    <tr>
                        <td>运营商</td>
                        <td><select class="sel" id="toPhone"
                                    name="toPhone" style="width: 100px;">
                            <option value="10659057195105888">电信</option>
                            <option value="106575650318">移动</option>
                            <option value="1065502795105888">联通</option>
                        </select> <font class="text_red">*</font></td>
                    </tr>
                    <tr>
                        <td>猫池端口：</td>
                        <td><input id="commPort" name="commPort"></td>
                    </tr>
                    <tr>
                        <td>号码组：</td>
                        <td>
                            <select name="phoneGroup" id="phoneGroup">
                                <option value="">-请选择-</option>
                                <option value="1">组1</option>
                                <option value="2">组2</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>备注：</td>
                        <td><input id="remarks" name="remarks"></td>
                    </tr>
                    <tr>
                        <td>状态：</td>
                        <td><select class="sel"
                                    name="status" id="phoneGroup_status" style="width: 100px;">
                            <option value="">-请选择-</option>
                            <option value="0">不可用</option>
                            <option value="1">可用</option>
                        </select></td>
                    </tr>
                </table>
                <input type="hidden" name ="id" id="id">
                <div style="text-align: center">
                    <a href="#" class="easyui-linkbutton"
                       onclick="savePhoneLibrary()"
                       style="background: #049cf5; width: 100px; height: 30px;">保存</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>