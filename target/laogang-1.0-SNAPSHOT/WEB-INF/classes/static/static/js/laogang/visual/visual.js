var map;
var totalOldmanCount;
var healthData, incomeData, familyTypeData;
var selectLabelId = [];
var table
var interval;
var time;
//todo
var age,male,huji,oldsum;
var village="";
$(document).ready(function () {
    init1();
    init2();
    init3();
    init4();
    initFinish();
    $('#timeIcon').html(new Date().Format('yyyy-MM-dd hh:mm:ss'));
    time=self.setInterval("$('#timeIcon').html(new Date().Format('yyyy-MM-dd HH:mm:ss'))",1000);
});

function initFinish() {
    $.ajax({
        url: "/oldman/getZdFinish",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "group": "area_village",
            "oldmanParam": {}
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            map = {"大河村": 30, "牛肚村": 20, "居委1": 20, "居委2": 15}
            createHeightAndWidthFromSourceDoc("d", "completeness1", 0.9, 0.5);
            bar4("各地区完成度", map, "completeness1", null, null)
        }
    });

    var a = "大河村";
    if (village!=""){
        a=village
    }
    $.ajax({
        url: "/oldman/getZdFinish",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "group": "area_custom_one",
            "oldmanParam": {
                "areaVillage": a
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            map = {
                "1组": 60,
                "2组": 50,
                "3组": 40,
                "4组": 30,
                "5组": 20,
                "6组": 10,
                "7组": 5,
                "8组": 35,
                "9组": 55,
                "10组": 25,
                "11组": 45,
                "12组": 60
            }
            createHeightAndWidthFromSourceDoc("d", "completeness2", 0.9, 0.5);
            bar4("大河村完成度", map, "completeness2", null, null)
        }
    });
}

function init4() {
    $.ajax({
        url: "/oldman/attr/getExtGroup",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "type": 13,
            "value": 1,
            "oldmanParam":{
                "areaVillage":village
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("c", "organ", 0.5, 0.3);
            pie2("机构养老", map, "organ", null, null);
        }
    });

    $.ajax({
        url: "/oldman/attr/getExtGroup",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "type": 13,
            "value": 2,
            "oldmanParam":{
                "areaVillage":village
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("c", "community", 0.5, 0.3);
            pie2("社区养老", map, "community", null, null);
        }
    });


    $.ajax({
        url: "/home/getCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "oldmanParam":{
                "areaVillage":village
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("c", "home", 0.5, 0.3);
            pie2("居家养老", map, "home", null, null);
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
        data: JSON.stringify([{"areaVillage":village}, {
            "age": "60-","areaVillage":village
        }, {
            "age": "80-","areaVillage":village
        }, {"isZd": true,"areaVillage":village}]),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            totalOldmanCount = map["1"];
            $("#sum").html(map["1"]);
            $("#sixSum").html(map["2"]);
            createHeightAndWidthFromSourceDoc("b", "oldmanSum", 0.95, 0.48);
            gauge1("老年人占比", 70, "oldmanSum", null, null);
            oldsum=70;

            $("#eightSum").html(map["3"]);
            createHeightAndWidthFromSourceDoc("b", "zdOldman", 0.95, 0.48);
            gauge1("重点老人占比", 10, "zdOldman", null, null);

            createHeightAndWidthFromSourceDoc("f", "eightSum2", 0.5, 0.45);
            var m = {"80以上": map["3"], "80以下": map["1"] - map["3"]};
            pie1("80岁以上老人占比", m, "eightSum2", null, null);

            init31();
        }
    });
}

function init31() {
    $.ajax({
        url: "/oldman/getGroupCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "fieldNameList": [ "FamilyType", "Income", "ServiceStatus"],
            "oldmanParam":{
                "areaVillage":village
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            for (var key in map) {
                if (key === "ServiceStatus") {
                    createHeightAndWidthFromSourceDoc("c", "ServiceStatus", 0.5, 0.5);
                    pie2("养老状态", map[key], "ServiceStatus", null, null);

                    var m = [[{name: "机构养老", value: map["ServiceStatus"]["机构养老"]},
                        {
                            value: map["ServiceStatus"]["社区养老"] + map["ServiceStatus"]["居家养老"],
                            name: '社区居家养老',
                            selected: 'true'
                        },
                        {
                            value: totalOldmanCount - map["ServiceStatus"]["机构养老"] - map["ServiceStatus"]["社区养老"] - map["ServiceStatus"]["居家养老"],
                            name: '其它'
                        }],
                        [{name: "社区养老", value: map["ServiceStatus"]["社区养老"]},
                            {value: map["ServiceStatus"]["居家养老"], name: '居家养老'}]];
                    createHeightAndWidthFromSourceDoc("c", "ServiceStatusCoverage", 0.5, 0.5);
                    var legendData = ['社区居家养老', "机构养老", "社区养老", "居家养老", "其他"];
                    pie3("养老服务覆盖率", m, "ServiceStatusCoverage", null, legendData);

                } else {
                    var title;
                    if (key === "FamilyType") {
                        title = "家庭结构";
                        familyTypeData = map[key];
                    }
                    if (key === "Income") {
                        title = "经济条件";
                        incomeData = map[key];
                    }
                    createHeightAndWidthFromSourceDoc("f", key, 0.5, 0.45);
                    pie2(title, map[key], key, null, null);
                }
            }
        }
    });

    $.ajax({
        url: "/oldman/getGroupCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "fieldNameList": ["area_village"],
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            for (var key in map) {
                createHeightAndWidthFromSourceDoc("b", "areaVillage3", 1.1, 1.2);
                bar3("片区老年人口分布", map["area_village"], "areaVillage3", selectVillage)
            }
        }
    });


    $.ajax({
        url: "/oldman/attr/getTypeCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "typeList": [8, 9, 10, 11, 12],
            "oldmanParam":{
                "areaVillage":village
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            healthData = map;
            createHeightAndWidthFromSourceDoc("f", "Health", 0.5, 0.45);
            pie2("健康状态", map, "Health", null, null);
        }
    });
}

function selectVillage(param) {
    village=param;
    init2();
    init3();
    init4();
    initFinish();
}

function change(title, type, trend) {
    $("#f").hide();
    $("#g").show();
    var map;
    if (title === "健康状态") {
        map = healthData;
    }
    if (title === "家庭结构") {
        map = familyTypeData;
    }
    if (title === "经济条件") {
        map = incomeData;
    }
    createHeightAndWidthFromSourceDoc("g", "g1", 0.5, 0.45);
    pie2(title, map, "g1", null, null);

    $.ajax({
        url: "/oldman/getOldmanBaseGroupByAttr",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "fieldNameList": ["male", "huji", "age"],
            "typeList": type,
            "oldmanParam":{
                "areaVillage":village
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            for (var key in map) {
                if (key === "male") {
                    createHeightAndWidthFromSourceDoc("g", "g3", 0.5, 0.3);
                    pie2("性别", map[key], "g3", null, null);
                }
                if (key === "huji") {
                    createHeightAndWidthFromSourceDoc("g", "g4", 0.5, 0.3);
                    pie2("户籍", map[key], "g4", null, null);
                }
                if (key === "age") {
                    createHeightAndWidthFromSourceDoc("g", "g5", 0.5, 0.3);
                    bar2("年龄", map[key], "g5", null, null);
                }
            }
        }
    });

//todo 区分areaVillage
    $.ajax({
        url: "/oldman/getTrend",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "types": [trend]
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("g", "g2", 0.5, 0.45);
            line1("趋势", map[trend], "g2", null, null)
        }
    });

}


/**
 * 初始化老人列表
 * @constructor
 */
function initOldman() {
    table = $(".dataTables-example").dataTable(
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
            "iDisplayLength": 4,
            "createdRow": function (row, data, dataIndex) {
                // $(row).css("background-color", "#052031");
                $(row).css("color", "white");
            },
            "sAjaxSource": "/oldman/getByPage",//这个是请求的地址
            "fnServerData": retrieveData
        });

    function retrieveData(url, aoData, fnCallback) {
        aoData.iDisplayLength = 6;
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data: JSON.stringify({
                "pageParam": {
                    "pageNo": aoData.iDisplayStart / aoData.iDisplayLength,
                    "pageSize": aoData.iDisplayLength
                },
                "oldmanParam": {
                    "labelIdList": selectLabelId,
                    "isView": true
                },
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
        url: "/label/getLabelFirst",
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var list = result.voList;
            var data = {name: "标签", id: 0, children: []};
            for (var i = 0; i < list.length; i++) {
                var label = {name: list[i].label, id: list[i].id}
                data.children[i] = label;
            }
            labelTree(data, "label", getLabel);
        }
    });
}


function getLabel(parentName, parentId) {
    if (parentName === "标签") {
        clearLabel();
    } else {
        var param = JSON.stringify({});
        if (parent != 0) {
            param = JSON.stringify({
                "parent": parentId
            });
        }
        $.ajax({
            url: "/label/getLabel",
            type: 'post',
            dataType: 'json',
            data: param,
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                var list = result.voList;
                var data = {name: parentName, id: 0, children: []};
                for (var i = 0; i < list.length; i++) {
                    var label = {name: list[i].label, id: list[i].id}
                    data.children[i] = label;
                }
                labelTree(data, "label", labelClick);
            }
        });
    }
}


function labelClick(label, id) {
    if (id === 0) {
        initLabel();
    } else {
        var f = true;
        for (var i = 0; i < selectLabelId.length; i++) {
            if (selectLabelId[i] === id) {
                f = false;
                break;
            }
        }
        if (f) {
            selectLabelId[selectLabelId.length] = id;
            var div = $("<div class='label labelC'>" + label + "</div>");
            $("#selectLabel").append(div);
            initStatistics();
            table.fnFilter();
            heatmap();
        }
    }
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
            "fieldNameList": ["male", "huji", "area_village"],
            "oldmanParam":{
                "labelIdList": selectLabelId
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            for (var key in map) {
                if (key === "male" || key === "huji") {
                    createHeightAndWidthFromSourceDoc("a", key, 0.5, 0.5);
                    var title;
                    if (key === "male") {
                        title = "男女";
                        if (male===null || male===undefined) {
                            male = map[key]
                        }
                    } else {
                        title = "戶籍";
                        if (huji===null || huji===undefined) {
                            huji = map[key];
                        }
                    }
                    pie1(title, map[key], key, null, null);
                }
                if (key === "area_village") {
                    createHeightAndWidthFromSourceDoc("a", key, 0.6, 0.5);
                    bar1("地区分布", map[key], key, null, null)
                }
            }
        }
    });


    $.ajax({
        url: "/oldman/getAgeGroupCount",
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "oldmanParam":{
                "labelIdList": selectLabelId
            }
        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("a", "age", 0.45, 0.45);
            bar2("年龄分布", map, "age", null, null);
            if (age===null || age===undefined){
                age=map;
            }
        }
    });
}

function clearLabel() {
    selectLabelId = [];
    $("#selectLabel").html("");
    initLabel();
    initStatistics();
    heatmap();
    table.fnFilter();
}

function getOrgan(type) {

    var allOverlay = map.getOverlays();
    for (var j = 0; j < allOverlay.length; j++) {
        if (allOverlay[j].type !== undefined && allOverlay[j].type !== null && allOverlay[j].type === "organ") {
            map.removeOverlay(allOverlay[j]);
        }
    }

    $.ajax({
        url: "/organ/getByPage",
        data: JSON.stringify({
            "pageParam": {
                "pageNo": 0,
                "pageSize": 100
            },
            "organParam": {
                "type": type
            },
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var positions = [];
            var data = result.organVoList;
            for (var i = 0; i < data.length; i++) {
                var myIcon = new BMap.Icon("/static/img/organ" + type + ".png", new BMap.Size(32, 32));
                var marker = new BMap.Marker(new BMap.Point(data[i].lng, data[i].lat), {
                    icon: myIcon
                });
                marker.setTitle(data[i].name);
                var label = new BMap.Label(data[i].name, {offset: new BMap.Size(20, -10)});
                label.setStyle({
                    color: "green",
                    fontSize: "20px",
                    border:0,
                    fontWeight: "bold",
                    padding: "5px 10px",
                    position: "relative"
                });
                marker.type = "organ";
                marker.setLabel(label);
                map.addOverlay(marker);
                positions[i] = marker.getPosition();
            }
            map.panTo(map.getViewport(positions).center);
        }
    });
}

//todo 重新拉数据 筛选 area_village
function change2() {
    if($("#h").is(":hidden")){
        $("#f").hide();
        $("#h").show();
        createHeightAndWidthFromSourceDoc("h", "h1", 0.24, 0.48);
        gauge1("老年人占比", oldsum, "h1", null, null);


        createHeightAndWidthFromSourceDoc("h", "h5", 0.24, 0.48);
        pie1("性别", male, "h5", null, null);

        createHeightAndWidthFromSourceDoc("h", "h3",  0.24, 0.48);
        pie1("户籍", huji, "h3", null, null);

        createHeightAndWidthFromSourceDoc("h", "h7",  0.24, 0.48);
        bar2("年龄分布", age, "h7", null, null);

        $.ajax({
            url: "/oldman/getTrend",
            type: 'post',
            dataType: 'json',
            data: JSON.stringify({
                "types": [100,101,102,103]
            }),
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                var map = result.map;
                for(var type in map){
                    createHeightAndWidthFromSourceDoc("h", "h"+type, 0.25, 0.5);
                    line1("趋势", map[type], "h"+type, null, null)
                }
            }
        });
    }else{
        $("#h").hide();
        $("#f").show();
    }
}

function organClick() {
    $("#c").hide();
    $("#m").show();
    createHeightAndWidthFromSourceDoc("m", "m11", 0.5, 0.25);
    gauge1("床位数", 100, "m11", null, null);
    createHeightAndWidthFromSourceDoc("m", "m12", 0.5, 0.23);
    pie1("性别", male, "m12", null, null);
    createHeightAndWidthFromSourceDoc("m", "m13", 0.5, 0.23);
    bar2("年龄", age, "m13", null, null);
    createHeightAndWidthFromSourceDoc("m", "m14", 0.5, 0.23);
    pie2("健康状态", {"正常":1,"失能":1,"半失能":1}, "m14", null, null);

    createHeightAndWidthFromSourceDoc("m", "m21", 0.5, 0.23);
    gauge1("床位数", 100, "m21", null, null);
    createHeightAndWidthFromSourceDoc("m", "m22", 0.5, 0.23);
    pie1("性别", male, "m22", null, null);
    createHeightAndWidthFromSourceDoc("m", "m23", 0.5, 0.23);
    bar2("年龄", age, "m23", null, null);
    createHeightAndWidthFromSourceDoc("m", "m24", 0.5, 0.23);
    pie2("健康状态", {"正常":1,"失能":1,"半失能":1}, "m24", null, null);
    //     }
    // });
}

function monitor() {
    if(document.getElementById("divmask")){
        $("#divmask").show();
    }else{
        var mask = $('<div id="divmask" onclick="mask()" style="position:absolute;width:100%;height:100%;background-color:grey;opacity: 0.7;top: 0px;left:0px;"></div>');
        $("body").append(mask);
    }
    $("#monitor").show();
}

function mask() {
    $("#divmask").hide();
    $("#monitor").hide();
}