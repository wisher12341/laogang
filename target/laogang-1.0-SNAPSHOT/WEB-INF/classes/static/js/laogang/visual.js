var map;

$(document).ready(function () {
    init1();
    init2();
});

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


function initMap() {
    map = new BMap.Map("map",{enableMapClick:false});
    map.centerAndZoom(new BMap.Point(121.846199,31.045218), 15);
    map.setMapStyle({style:'midnight'});
    map.enableScrollWheelZoom(true);
    map.enablePinchToZoom(true);
    var opts = {anchor: BMAP_ANCHOR_BOTTOM_RIGHT};
    map.addControl(new BMap.NavigationControl(opts));


    var points =[{"lng":"121.846199","lat":"31.045218","count":"1000000000"}];
    heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":100,"visible":true});
    map.addOverlay(heatmapOverlay);
    heatmapOverlay.setDataSet({data:points,max:100});
    heatmapOverlay.show();
}

function setGradient(){
    /*格式如下所示:
    {
        0:'rgb(102, 255, 0)',
        .5:'rgb(255, 170, 0)',
        1:'rgb(255, 0, 0)'
    }*/
    var gradient = {};
    var colors = document.querySelectorAll("input[type='color']");
    colors = [].slice.call(colors,0);
    colors.forEach(function(ele){
        gradient[ele.getAttribute("data-key")] = ele.value;
    });
    heatmapOverlay.setOptions({"gradient":gradient});
}


/**
 * 初始化老人列表
 * @constructor
 */
function initOldman() {
    var table =$(".dataTables-example").dataTable(
        {
            "sPaginationType": "full_numbers",
            "bPaginite": true,
            "bInfo": true,
            "bSort": false,
            "bFilter": false, //搜索栏
            "bStateSave": true,
            "bProcessing": true, //加载数据时显示正在加载信息
            "bServerSide": true, //指定从服务器端获取数据
            "columns":[{
                data:"name"
            },{
                data:"areaVillage"
            },{
                data:"male"
            },{
                data:"age"
            },{
                data:"idCard"
            }
            ],
            "sAjaxSource": "/oldman/getByPage",//这个是请求的地址
            "fnServerData": retrieveData
        });
    function retrieveData(url, aoData, fnCallback) {
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data :JSON.stringify({
                "pageParam": {
                    "pageNo": aoData.iDisplayStart / aoData.iDisplayLength,
                    "pageSize": aoData.iDisplayLength
                },
                "oldmanParam":{}
            }),
            type: 'post',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                var data ={
                    "iTotalRecords":result.count,
                    "iTotalDisplayRecords":result.count,
                    "aaData":result.oldmanVoList
                };
                fnCallback(data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
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
        data: JSON.stringify({

        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var list = result.voList;
            var item = group(list,6);
            for(var i=0;i<item.length;i++){
                var row =$("<div class='row'></div>");
                for(var j=0;j<item[i].length;j++){
                    var div= $("<div class='col-xs-2 label' >"+item[i][j].label+"</div>");
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
            for(var i=0;i<list.length;i++){
                var div= $("<div class='col-xs-2'><label class='label'>"+list[i].label+"</label></div>");
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
        data: JSON.stringify({

        }),
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var map = result.map;
            createHeightAndWidthFromSourceDoc("a", "age", 0.55, 0.6);
            bar2(null, map, "age", null, null);
        }
    });
}


