function initMap() {
    map = new BMap.Map("map", {enableMapClick: false});
    map.centerAndZoom(new BMap.Point(121.846199, 31.045218), 15);
    map.setMapStyle({style: 'midnight'});
    map.enableScrollWheelZoom(true);
    map.enablePinchToZoom(true);
    var opts = {anchor: BMAP_ANCHOR_BOTTOM_RIGHT};
    map.addControl(new BMap.NavigationControl(opts));
    heatmap()
}

function heatmap() {
    $.ajax({
        url: "/oldman/getLocation",
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "labelIdList": selectLabelId,
            "isView": true,
            "areaVillage":village
        }),
        success: function (result) {
            var allOverlay = map.getOverlays();
            for (var j = 0; j < allOverlay.length; j++) {
                //删除指定的楼
                // if (allOverlay[j].area === undefined || allOverlay[j].area === null) {
                    map.removeOverlay(allOverlay[j]);
                // }
            }


            var points = result.voList;
            heatmapOverlay = new BMapLib.HeatmapOverlay({"radius": 30, "visible": true});
            map.addOverlay(heatmapOverlay);
            heatmapOverlay.setDataSet({data: points, max: 20});
            heatmapOverlay.show();
            initArea();
        }
    });
}


function initArea() {
    var data = [{
        "name": "大河村",
        "lng": "121.853449",
        "lat": "31.022799"
    }, {
        "name": "牛肚村",
        "lng": "121.835494",
        "lat": "31.058899"
    }, {
        "name": "建港村",
        "lng": "121.848",
        "lat": "31.036518"
    }, {
        "name": "成日村",
        "lng": "121.831647",
        "lat": "31.068763"
    }, {
        "name": "中港村",
        "lng": "121.850268",
        "lat": "31.047318"
    }, {
        "name": "东河村",
        "lng": "121.860352",
        "lat": "31.022495"
    }, {
        "name": "欣河村",
        "lng": "121.833767",
        "lat": "31.027842"
    }, {
        "name": "宏港苑居委",
        "lng": "121.847597",
        "lat": "31.040091"
    }, {
        "name": "老港居委",
        "lng": "121.846428",
        "lat": "31.044623"
    }
        , {
            "name": "滨海居委",
            "lng": "121.893672",
            "lat": "31.012919"
        }];
    for (var i = 0; i < data.length; i++) {
        // var myIcon = new BMap.Icon("/static/img/danwei.png", new BMap.Size(32, 32));
        var marker = new BMap.Marker(new BMap.Point(data[i].lng, data[i].lat));
        marker.setTitle(data[i].name);
        var label = new BMap.Label(data[i].name, {offset: new BMap.Size(20, -10)});
        label.setStyle({
            color: "white",
            fontSize: "20px",
            border:0,
            backgroundColor: "rgb(f,f,f,0.5)",
            fontWeight: "bold",
            padding: "4px 10px",
            position: "relative"
        });
        marker.type="area";
        marker.setLabel(label);
        map.addOverlay(marker);
    }
}