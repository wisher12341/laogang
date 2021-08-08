var map;
var totalOldmanCount;
$(document).ready(function () {
    init1();
    init2();
    init3();
    init4();
    initFinish();
});

function initFinish() {
    $.ajax({
        url: "/oldman/getZdFinish",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "group": "area_village",
            "oldmanParam":{}
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;

            createHeightAndWidthFromSourceDoc("d", "completeness1", 0.3, 0.5);
            bar4(null, map, "completeness1", null, null)
        }
    });

    $.ajax({
        url: "/oldman/getZdFinish",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "group": "area_custom_one",
            "oldmanParam":{
                "areaVillage":"大河村"
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;

            createHeightAndWidthFromSourceDoc("d", "completeness2", 0.3, 0.5);
            bar4(null, map, "completeness2", null, null)
        }
    });
}

function init4() {
    $.ajax({
        url: "/oldman/getGroupCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "fieldNameList": ["area_village", "FamilyType", "Health", "Economic"]
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            for (var key in map) {
                if (key === "area_village") {
                    createHeightAndWidthFromSourceDoc("c", "areaVillage3", 0.3, 0.9);
                    bar3(null, map["area_village"], "areaVillage3", null, null)
                } else {
                    createHeightAndWidthFromSourceDoc("c", key, 0.2, 0.5);
                    pie2(null, map[key], key, null, null)
                }
            }
        }
    });
}

/**
 * 初始化 第一块
 */
function init1() {
    initStatistics();
    initLabel();
    initOldman();
}

/**
 * 初始化第二块
 */
function init2() {
    initMap();
}


function init3() {
    $.ajax({
        url: "/oldman/getCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify([{}, {
            "age": "60-"
        }, {
            "age": "80-"
        }, {"isZd": true}]),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            totalOldmanCount=map["1"];
            $("#sum").html(map["1"]);
            $("#sixSum").html(map["2"]);
            createHeightAndWidthFromSourceDoc("b", "oldmanSum", 0.2, 0.5);
            gauge1("老年人占比", 100 * map["2"] / 100000, "oldmanSum", null, null);
            $("#eightSum").html(map["3"]);
            createHeightAndWidthFromSourceDoc("b", "zdOldman", 0.2, 0.5);
            gauge1("重点老人占比", 100 * map["4"] / map["1"], "zdOldman", null, null);

            createHeightAndWidthFromSourceDoc("b", "eightSum2", 0.2, 0.5);

            var m = {"80以上": map["3"], "80以下": map["1"] - map["3"]};
            pie1(null, m, "eightSum2", null, null);

            init31();
        }
    });


    var organ = {"养老院1":300,"养老院2":200,"养老院1":100};
    createHeightAndWidthFromSourceDoc("b", "organ", 0.2, 0.3);
    pie2(null, organ, "organ", null, null);
    var community = {"长者照护之家":300,"日照中心":200,"助餐点":100};
    createHeightAndWidthFromSourceDoc("b", "community", 0.2, 0.3);
    pie2(null, community, "community", null, null);

    $.ajax({
        url: "/home/getCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify(),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("b", "home", 0.2, 0.3);
            pie2(null, map, "home", null, null);
        }
    });
}

function init31() {
    $.ajax({
        url: "/oldman/getGroupCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "fieldNameList": ["area_village", "FamilyType", "Health", "Economic", "ServiceStatus"]
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            for (var key in map) {
                if (key === "area_village") {
                    createHeightAndWidthFromSourceDoc("b", "areaVillage3", 0.3, 0.9);
                    bar3(null, map["area_village"], "areaVillage3", null, null)
                } else if (key === "ServiceStatus") {
                    createHeightAndWidthFromSourceDoc("b", "ServiceStatus", 0.3, 0.5);
                    pie2(null, map[key], "ServiceStatus", null, null);

                    var map = [[{name: "机构养老", value:map["ServiceStatus"]["机构养老"]},
                        {value:map["ServiceStatus"]["社区养老"]+map["ServiceStatus"]["居家养老"], name: '社区居家养老', selected: 'true'},
                        {value:totalOldmanCount-map["ServiceStatus"]["机构养老"]-map["ServiceStatus"]["社区养老"]-map["ServiceStatus"]["居家养老"], name: '其它'}],
                        [{name: "社区养老", value:map["ServiceStatus"]["社区养老"]},
                            {value:map["ServiceStatus"]["居家养老"], name: '居家养老'}]];
                    createHeightAndWidthFromSourceDoc("b", "ServiceStatusCoverage", 0.3, 0.5);
                    var legendData=['社区居家养老',"机构养老","社区养老","居家养老","其他"];
                    pie3("养老服务覆盖率", map, "ServiceStatusCoverage", null, legendData);

                } else {
                    createHeightAndWidthFromSourceDoc("b", key, 0.2, 0.5);
                    pie2(null, map[key], key, null, null);
                }
            }
        }
    });
}

/**
 * 初始化老人列表
 * @constructor
 */
function initOldman() {
    var table = $(".dataTables-example").dataTable(
        {
            "sPaginationType": "full_numbers",
            "bPaginite": true,
            "bInfo": true,
            "bSort": false,
            "bFilter": false, //搜索栏
            "bStateSave": true,
            "bProcessing": true, //加载数据时显示正在加载信息
            "bServerSide": true, //指定从服务器端获取数据
            "columns": [{
                data: "name"
            }, {
                data: "areaVillage"
            }, {
                data: "male"
            }, {
                data: "age"
            }, {
                data: "idCard"
            }
            ],
            "sAjaxSource": "/oldman/getByPage",//这个是请求的地址
            "fnServerData": retrieveData
        });

    function retrieveData(url, aoData, fnCallback) {
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data: JSON.stringify({
                "pageParam": {
                    "pageNo": aoData.iDisplayStart / aoData.iDisplayLength,
                    "pageSize": aoData.iDisplayLength
                },
                "oldmanParam": {}
            }),
            type: 'post',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                var data = {
                    "iTotalRecords": result.count,
                    "iTotalDisplayRecords": result.count,
                    "aaData": result.oldmanVoList
                };
                fnCallback(data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
            }
        });
    }
}

/**
 * 初始化标签
 */
function initLabel() {
    $.ajax({
        url: "/label/getLabel",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({}),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var list = result.voList;
            var item = group(list, 6);
            for (var i = 0; i < item.length; i++) {
                var row = $("<div class='row'></div>");
                for (var j = 0; j < item[i].length; j++) {
                    var div = $("<div class='col-xs-2 label' >" + item[i][j].label + "</div>");
                    row.append(div);
                }
                $("#label").append(row);
            }
        }
    });


    $.ajax({
        url: "/label/getLabelFirst",
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var list = result.voList;
            for (var i = 0; i < list.length; i++) {
                var div = $("<div class='col-xs-2'><label class='label'>" + list[i].label + "</label></div>");
                $("#labelFirst").append(div);
            }
        }
    });
}

/**
 * 初始化老人统计数据
 */
function initStatistics() {
    $.ajax({
        url: "/oldman/getGroupCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "fieldNameList": ["male", "huji", "area_village"]
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            for (var key in map) {
                if (key === "male" || key === "huji") {
                    createHeightAndWidthFromSourceDoc("a", key, 0.45, 0.6);
                    pie1(null, map[key], key, null, null);
                }
                if (key === "area_village") {
                    createHeightAndWidthFromSourceDoc("a", key, 0.55, 0.6);
                    bar1(null, map[key], key, null, null)
                }
            }
        }
    });


    $.ajax({
        url: "/oldman/getAgeGroupCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({}),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("a", "age", 0.55, 0.6);
            bar2(null, map, "age", null, null);
        }
    });
}


