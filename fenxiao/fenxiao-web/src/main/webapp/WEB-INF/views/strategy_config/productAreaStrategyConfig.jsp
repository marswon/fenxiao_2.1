<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <style type="text/css" media=screen>
        .text_red {
            color: red;
            font-size: 1.5rem;
            vertical-align: middle
        }

        .active {
            background: #00b5ff;
            border: 1px solid #fff;
            color: red;
        }

        .datagrid-cell {
            line-height: 25px;
        }

        .remo_li_style {
            list-style-type: none;
        }
    </style>
    <script type="text/javascript">
        var map = {
            '010': '北京',
            '020': '广东',
            '021': '上海',
            '022': '天津',
            '023': '重庆',
            '025': '江苏',
            '026': '青海',
            '027': '海南',
            '028': '四川',
            '029': '陕西',
            '030': '山西',
            '035': '河北',
            '039': '河南',
            '040': '内蒙古',
            '041': '辽宁',
            '045': '吉林',
            '046': '黑龙江',
            '050': '安徽',
            '055': '浙江',
            '059': '福建',
            '060': '山东',
            '070': '广西',
            '071': '湖北',
            '073': '湖南',
            '075': '江西',
            '080': '云南',
            '085': '贵州',
            '089': '西藏',
            '090': '宁夏',
            '093': '甘肃',
            '095': '新疆'
        };

        function closeDf() {
            $('#dfxqDlg').dialog('close');
        }


        jQuery(function ($) {
            //查寻已经配置的策略
            $("#strategyConfig").datagrid({
                url: '${ctx}/strategyConfig/findAllFxproductAreaStrategy',
                method: 'get',
                nowrap: false,
                striped: true,
                remoteSort: false,
                pageSize: 20,
                fit: true,
                queryParams: {
                    businessType: 0
                },
                singleSelect: true,//是否单选
                pagination: true,//分页控件
                rownumbers : true,//行号
                fitColumns: true,//列宽自动填充满表格
                checkOnSelect: false,
                selectOnCheck: false,
                toolbar: "#xxDgTb",//工具栏
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
                    /* {field:'id',title:'id',width:7,hidden:true}, */
                    {
                        field: 'businessType',
                        title: '业务类型',
                        width: 80,
                        align: "center",
                        formatter: function (val, row, index) {
                            if (row.businessType == 0) {
                                return "流量";
                            } else if (row.businessType == 1) {
                                return "话费";
                            } else if (row.businessType == 3) {
                                return "加油卡";
                            }
                        }
                    },
                    {
                        field: 'yysTypeId',
                        title: '运营商',
                        width: 80,
                        align: "center",
                        formatter: function (val, row, index) {
                            if (row.yysTypeId == 1) {
                                return "电信";
                            } else if (row.yysTypeId == 2) {
                                return "移动";
                            } else if (row.yysTypeId == 3) {
                                return "联通";
                            } else if (row.yysTypeId == 4) {
                                return "中石化";
                            } else if (row.yysTypeId == 5) {
                                return "中石油";
                            }
                        }
                    },
                    {
                        field: 'provinceId',
                        title: '省份',
                        width: 80,
                        align: "center",
                        formatter: function (val, row, index) {
                            return showProvince(row.provinceId);
                        }
                    },
                    {
                        field: 'areaCode',
                        title: '城市',
                        width: 60,
                        align: "center",
                        formatter: function (val, row, index) {
                            return areaCodeMap[row.areaCode];
                        }
                    },
                    {
                        field: 'flowType',
                        title: '类型',
                        width: 60,
                        align: "center",
                        formatter: function (val, row, index) {
                            if (row.flowType == 0) {
                                return "漫游";
                            } else if (row.flowType == 1) {
                                return "本地";
                            }
                        }
                    },
                    {
                        field: 'size',
                        title: '面值',
                        width: 60,
                        align: "center"
                    }, {
                        field: 'channelName',
                        title: '通道名称',
                        width: 130,
                        align: "center",
                        formatter: function (val, row, index) {
                            return row.operatorsProduct.channel.name;
                        }
                    }, {
                        field: 'oproName',
                        title: '运营商产品名称',
                        width: 300,
                        align: "center",
                        formatter: function (val, row, index) {
                            var flowTypeStr = "";
                            if (row.operatorsProduct.flowType == 0) {
                                flowTypeStr = "漫游";
                            }
                            else if (row.operatorsProduct.flowType == 1) {
                                flowTypeStr = "本地";
                            }
                            return row.operatorsProduct.name + "&nbsp;&nbsp;" + flowTypeStr;
                        }
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        align: "center",
                        formatter: function (val, row, index) {
                            if (row.status == 1) {
                                return '<span id="ysj'
                                        + row.id
                                        + '" style="color:#fff;background:green" onclick="updateStatus(\''
                                        + row.id
                                        + '\',0)">已开启</span>';
                            } else if (row.status == 0) {
                                return '<span id="yxj'
                                        + row.id
                                        + '" style="color:#fff;background:red" onclick="updateStatus(\''
                                        + row.id
                                        + '\',1)">已关闭</span>';
                            }
                        }
                    }, {
                        field: 'statuss',
                        title: '操作',
                        width: 100,
                        align: "center",
                        formatter: function (val, row, index) {
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteAreaStrategy(\'' + row.id + '\')">删除</a>';
                        }
                    }]]

            });

            $('#areaStrategyConfig').dialog({
                title: "流量产品编辑",
                width: 950,
                height: 500,
                closed: true,
                cache: false,
                modal: true,
                center: true,
            });
            $("#search_id").click(function () {
                var params = $('#strategyConfig').datagrid('options').queryParams;
                var fields = $('#queryForm').serializeArray();
                $.each(fields, function (i, field) {
                    params[field.name] = $.trim(field.value);
                });
                $('#strategyConfig').datagrid('load');
            });
        });

        //新增策略
        function openAreaStrategyConfig() {

            //业务类型
            var businessType = $("#businessType").val();
            //运营商
            var yysTypeId = $("#yysTypeId").val();
            //省份
            var provinceId = $("#provinceId").val();
            //设置默认流量包大小
            var size = $("#size").val();
            //流量类型
            var flowType = $("#flowType").val();
            //城市
            var areaCode = $("#areaCode").val();
            console.log(businessType + "=" + yysTypeId + "=" + provinceId + "=" + size + "=" + areaCode);
            operators_product(businessType, yysTypeId, provinceId, size, flowType);
            $('#areaStrategyConfig').dialog('open');
        }

        /**
         *供应商产品
         */
        function operators_product(businessType, yysTypeId, provinceId, size, flowType) {
            $("#_operatorsProduct")
                    .datagrid(
                            {
                                url: '${ctx}/operatorProduct/getProductListNoHasFlowType',
                                method: 'get',
                                nowrap: false,
                                striped: true,
                                remoteSort: false,
                                pageSize: 10,
                                fit: true,
                                queryParams: {
                                    businessType: 0,
                                    yysTypeId: yysTypeId,
                                    provinceId: provinceId,
                                    size: size,
                                    flowType: flowType
                                },
                                singleSelect: true,//是否单选
                                pagination: true,//分页控件
                                //rownumbers : true,//行号
                                fitColumns: true,//列宽自动填充满表格
                                checkOnSelect: false,
                                selectOnCheck: false,
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
                                    /* {field:'id',title:'id',width:7,hidden:true}, */
                                    {
                                        field: 'id',
                                        title: '选择',
                                        width: 40,
                                        align: "left",
                                        formatter: function (value, row,
                                                             rowIndex) {
                                            return '<input type="radio" name="id" id="selectRadio"' + rowIndex + '    value="' + row.id + '" />';
                                        }
                                    },
                                    {
                                        field: 'name',
                                        title: '流量包名称',
                                        width: 250,
                                        align: "center"
                                    },
                                    {
                                        field: 'channelName',
                                        title: '通道名称',
                                        width: 250,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            return row.channel.name;
                                        }
                                    },
                                    {
                                        field: 'flowType',
                                        title: '通道名称',
                                        width: 250,
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
                                        width: 100,
                                        align: "center"
                                    },
                                    {
                                        field: 'price',
                                        title: '价格',
                                        width: 100,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (row.price > 0) {
                                                return "¥" + row.price / 1000;
                                            } else {
                                                return "¥" + row.price;
                                            }
                                        }
                                    },
                                    {
                                        field: 'channelDiscount',
                                        title: '折扣',
                                        width: 100,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (row.channelDiscount > 0) {
                                                return row.channelDiscount
                                                        / 100 + " 折";
                                            } else {
                                                return row.channelDiscount
                                                        + " 折";
                                            }
                                        }
                                    },
                                    {
                                        field: 'status',
                                        title: '状态',
                                        width: 100,
                                        align: "center",
                                        formatter: function (val, row, index) {
                                            if (row.status == 0) {
                                                return '<span style="color:green;background:#D6D6D6">正常</span>';
                                            } else {
                                                return '<span style="color:red;background:#D6D6D6">异常</span>';
                                            }
                                        }
                                    }]],
                                onLoadSuccess: function (data) {
                                    //加载完毕后获取所有的checkbox遍历
                                    if (data.rows.length > 0) {
                                        //循环判断操作为新增的不能选择
                                        for (var i = 0; i < data.rows.length; i++) {
                                            //根据operate让某些行不可选
                                            if (data.rows[i].operate == "false") {
                                                $("input[type='radio']")[i].disabled = true;
                                            }
                                        }
                                    }
                                },
                                onClickRow: function (rowIndex, rowData) {
                                    //加载完毕后获取所有的checkbox遍历
                                    var radio = $("input[type='radio']")[rowIndex].disabled;
                                    //如果当前的单选框不可选，则不让其选中
                                    if (radio != true) {
                                        //让点击的行单选按钮选中
                                        $("input[type='radio']")[rowIndex].checked = true;
                                    } else {
                                        $("input[type='radio']")[rowIndex].checked = false;
                                    }
                                    //获取radio选中值
                                    var opid = $("input:radio:checked").val();
                                    $("#oproId").val(opid);
                                }
                            });
        }

        //根据IDcode获取省份
        function showProvince(provinceId) {
            var map = {
                '000': '全国',
                '010': '北京',
                '020': '广东',
                '021': '上海',
                '022': '天津',
                '023': '重庆',
                '025': '江苏',
                '026': '青海',
                '027': '海南',
                '028': '四川',
                '029': '陕西',
                '030': '山西',
                '035': '河北',
                '039': '河南',
                '040': '内蒙古',
                '041': '辽宁',
                '045': '吉林',
                '046': '黑龙江',
                '050': '安徽',
                '055': '浙江',
                '059': '福建',
                '060': '山东',
                '070': '广西',
                '071': '湖北',
                '073': '湖南',
                '075': '江西',
                '080': '云南',
                '085': '贵州',
                '089': '西藏',
                '090': '宁夏',
                '093': '甘肃',
                '095': '新疆'
            };
            return map[provinceId];
        }

        //根据省份查找所有城市
        function showArea(provinceId) {
            var i = 0;
            $("#provinceCheckBox").html("");
            $.post("${ctx}/strategyConfig/areaToProvinceId?provinceId=" + provinceId,
                    null, function (data) {
                        console.log(data + "-------");
                        if (data) {
                            var maps = data.content;
                            $.each(maps, function (key, values) {
                                i = i + 1;
                                $("#provinceCheckBox").append(
                                        "&nbsp;&nbsp;<input type='radio' id ='_" + key + "' value='" + key + "'/>" + values + "&nbsp;&nbsp;&nbsp;&nbsp");
                                if (i % 4 == 0) {
                                    $("#provinceCheckBox").append("<br/><br/>");
                                }

                            });
                        }
                    });
        }
        //根据省份获取城市
        function selectAreaCode() {
            var provinceId = $("#provinceId").val(); //这就是selected的值
            //根据省份获取城市
            //根据省份查找所有城市
            $("#areaCode").html("");
            $.post("${ctx}/strategyConfig/areaToProvinceId?provinceId=" + provinceId,
                    null, function (data) {
                        console.log(data + "-------");
                        if (data) {
                            var maps = data.content;
                            $.each(maps, function (key, values) {

                                $("#areaCode").append(
                                        "<option value='" + key + "'>" + values + "</option>");
                                console.log("<option value='" + key + "'>" + values + "</option>");

                            });
                        }
                    });
        }

        //保存策略
        function saveProductAreaStrategy() {
            var oproId = $("#oproId").val();
            //保存策略
            $.messager.confirm("操作提示", "您确定要保存/覆盖吗？", function (data) {
                if (data) {
                    $('#queryForm').form('submit', {
                        url: "${ctx}/strategyConfig/saveProductAreaStrategy",
                        onSubmit: function (param) {
                            //运营商产品
                            param.oproId = oproId;
//                            //业务类型
//                            param.businessType = $("#businessType").val();
//                            //运营商
//                            param.yysTypeId = $("#yysId").val();
//                            //省份
//                            param.provinceId = $("#provinceId").val();//这就是selected的值
//                            //设置默认流量包大小
//                            param.size = $("#size").val();
//                            //流量类型
//                            param.flowType = $("#flowType").val();
//                            //城市
//                            param.areaCode = $("#areaCode").val();
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
                  //  alert("取消");
                }
            });
        }

        //开启策略，关闭策略
        function updateStatus(id, status) {
            if (status == 1) {
                $.messager
                        .confirm(
                                "操作提示",
                                "您确定要开启该策略吗？",
                                function (data) {
                                    if (data) {
                                        $('#queryForm')
                                                .form(
                                                        'submit',
                                                        {
                                                            url: "${ctx}/strategyConfig/updateFXProductAreaStrategyStatus",
                                                            onSubmit: function (param) {
                                                                param.status = status;
                                                                param.id = id;
                                                            },
                                                            success: function (data) {
                                                                var d = eval("("
                                                                        + data
                                                                        + ")");
                                                                if (d.resultCode == 1) {
                                                                    $("#yxj" + id)
                                                                            .text(
                                                                                    "已开启");
                                                                    document
                                                                            .getElementById("yxj"
                                                                                    + id).style.background = "green";
                                                                    document
                                                                            .getElementById("yxj"
                                                                                    + id).style.color = "#fff";
                                                                } else {
                                                                    if (d.resultMsg) {
                                                                        $.messager
                                                                                .alert(
                                                                                        "消息",
                                                                                        d.resultMsg);
                                                                    } else {
                                                                        $.messager
                                                                                .alert(
                                                                                        "消息",
                                                                                        "未知错误");
                                                                    }
                                                                }
                                                            }
                                                        });
                                    } else {
                                        //alert("取消");
                                    }
                                });
            } else if (status == 0) {
                $.messager
                        .confirm(
                                "操作提示",
                                "您确定要关闭策略吗？",
                                function (data) {
                                    if (data) {
                                        $('#queryForm')
                                                .form(
                                                        'submit',
                                                        {
                                                            url: "${ctx}/strategyConfig/updateFXProductAreaStrategyStatus",
                                                            onSubmit: function (param) {
                                                                param.status = status;
                                                                param.id = id;
                                                            },
                                                            success: function (data) {
                                                                var d = eval("("
                                                                        + data
                                                                        + ")");
                                                                if (d.resultCode == 1) {
                                                                    $("#ysj" + id)
                                                                            .text(
                                                                                    "已关闭");
                                                                    document
                                                                            .getElementById("ysj"
                                                                                    + id).style.color = "#fff";
                                                                    document
                                                                            .getElementById("ysj"
                                                                                    + id).style.background = "red";

                                                                } else {
                                                                    if (d.resultMsg) {
                                                                        $.messager
                                                                                .alert(
                                                                                        "消息",
                                                                                        d.resultMsg);
                                                                    } else {
                                                                        $.messager
                                                                                .alert(
                                                                                        "消息",
                                                                                        "未知错误");
                                                                    }
                                                                }
                                                            }
                                                        });
                                    } else {
                                        //alert("取消");
                                    }
                                });
            }
        }
        //删除配置
        function deleteAreaStrategy(id) {
            $.messager
                    .confirm(
                            "操作提示",
                            "您确定要删除该策略吗？",
                            function (data) {
                                if (data) {
                                    $('#queryForm')
                                            .form(
                                                    'submit',
                                                    {
                                                        url: "${ctx}/strategyConfig/deleteFXProductAreaStrategy",
                                                        onSubmit: function (param) {
                                                            param.id = id;
                                                        },
                                                        success: function (data) {
                                                            var d = eval("("
                                                                    + data
                                                                    + ")");

                                                            if (d.resultMsg) {
                                                                $.messager
                                                                        .alert(
                                                                                "消息",
                                                                                d.resultMsg);
                                                                $('#strategyConfig').datagrid('load');
                                                            } else {
                                                                $.messager
                                                                        .alert(
                                                                                "消息",
                                                                                "未知错误");
                                                            }
                                                        }
                                                    });
                                } else {
                                    //alert("取消");
                                }
                            });
        }
    </script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true">
    <div title="省份策略" style="padding: 2px">
        <table id="strategyConfig"></table>
        <!-- toolbar="#tb" -->
        <div id="xxDgTb">
            <form id="queryForm" method="get">
                <table cellpadding="1">
                    <tr>
                        <td><b>运营商：</b> <select class="sel" name="yysTypeId" id="yysTypeId"
                                                style="width: 80px; padding-left: 0px;">
                            <option value="">-请选择-</option>
                            <option value="1">电信</option>
                            <option value="2">移动</option>
                            <option value="3">联通</option>
                        </select></td>
                        <td><b>省份：</b> <select id="provinceId" name="provinceId" onchange="selectAreaCode();"
                                               style="width: 80px; padding-left: 0px;">
                            <option value="">-请选择-</option>
                            <option value="000">全国</option>
                            <option value="010">北京</option>
                            <option value="020">广东</option>
                            <option value="021">上海</option>
                            <option value="022">天津</option>
                            <option value="023">重庆</option>
                            <option value="025">江苏</option>
                            <option value="026">青海</option>
                            <option value="027">海南</option>
                            <option value="028">四川</option>
                            <option value="029">陕西</option>
                            <option value="030">山西</option>
                            <option value="035">河北</option>
                            <option value="039">河南</option>
                            <option value="040">内蒙古</option>
                            <option value="041">辽宁</option>
                            <option value="045">吉林</option>
                            <option value="046">黑龙江</option>
                            <option value="050">安徽</option>
                            <option value="055">浙江</option>
                            <option value="059">福建</option>
                            <option value="060">山东</option>
                            <option value="070">广西</option>
                            <option value="071">湖北</option>
                            <option value="073">湖南</option>
                            <option value="075">江西</option>
                            <option value="080">云南</option>
                            <option value="085">贵州</option>
                            <option value="089">西藏</option>
                            <option value="090">宁夏</option>
                            <option value="093">甘肃</option>
                            <option value="095">新疆</option>
                        </select></td>
                        <td><b>城市：</b><select id="areaCode" name="areaCode"
                                              style="width: 80px; padding-left: 0px;"></select></td>
                        <td><b>包类型：</b> <select class="sel"
                                                name="flowType" id="flowType"
                                                style="width: 80px; padding-left: 0px;">
                            <option value="">-请选择-</option>
                            <option value="0">漫游</option>
                            <option value="1">本地</option>
                            <option value="-1">其它</option>
                        </select></td>

                        <td><b>面值：</b> <select id="size" name="size"
                                               style="width: 80px; padding-left: 0px;">
                            <option value="">-请选择-</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                            <option value="20">20</option>
                            <option value="30">30</option>
                            <option value="50">50</option>
                            <option value="70">70</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                            <option value="300">300</option>
                            <option value="500">500</option>
                            <option value="700">700</option>
                            <option value="1000">1000</option>
                            <option value="1024">1024</option>
                            <option value="2048">2048</option>
                            <option value="3096">3096</option>
                            <option value="4096">4G</option>
                            <option value="6144">6G</option>
                            <option value="10240">10G</option>
                            <option value="12288">12G</option>
                        </select></td>
                        <td><b>业务类型：</b><select class="sel" name="businessType"
                                                id="businessType" style="width: 80px; padding-left: 0px;">
                            <option value="">-请选择-</option>
                            <option value="0">流量</option>
                            <option value="1">话费</option>
                        </select>
                        </td>
                        <td>
                            <a href="javascript:;"
                               class="easyui-linkbutton"
                               style="width: 100px; margin-left: 95px"
                               data-options="iconCls:'icon-search'" id="search_id">搜索</a>
                            <a href="javascript:;" class="easyui-linkbutton"
                               data-options="iconCls:'icon-add'" id="add_product"
                               onclick="openAreaStrategyConfig();">新增策略</a></td>
                    </tr>
                </table>

                <div id="areaStrategyConfig" style="margin-top: 0px;">
                    <div style="height: 400px;">
                        <table id="_operatorsProduct"></table>
                    </div>
                    <div align="center" style="height: 30px;">
                        <div type="hidden" id="oproId"></div>
                        <a href="#" class="easyui-linkbutton"
                           onclick="saveProductAreaStrategy()"
                           style="background: #049cf5; width: 100px; height: 30px;">保存</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
