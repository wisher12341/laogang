var polygon1 = new BMap.Polygon([
    new BMap.Point(121.798561,31.069558),
    new BMap.Point(121.809413,31.069991),
    new BMap.Point(121.816025,31.068073),
    new BMap.Point(121.826229,31.06931),
    new BMap.Point(121.839021,31.073702),
    new BMap.Point(121.85325,31.075372),
    new BMap.Point(121.87754,31.029338),
    new BMap.Point(121.887134,31.03138),
    new BMap.Point(121.895668,31.033453),
    new BMap.Point(121.877343,31.067175),
    new BMap.Point(121.87993,31.068721),
    new BMap.Point(121.872169,31.082949),
    new BMap.Point(121.891932,31.090928),
    new BMap.Point(121.945255,31.011852),
    new BMap.Point(121.941806,31.009871),
    new BMap.Point(121.932894,31.010119),
    new BMap.Point(121.931745,31.008138),
    new BMap.Point(121.886326,31.010119),
    new BMap.Point(121.885751,31.007643),
    new BMap.Point(121.873678,31.008138),
    new BMap.Point(121.873678,31.006157),
    new BMap.Point(121.847807,31.006157),
    new BMap.Point(121.84637,31.012223),
    new BMap.Point(121.8348,31.014204),
    new BMap.Point(121.834656,31.018722),
    new BMap.Point(121.826535,31.024849),
    new BMap.Point(121.833219,31.032029),
    new BMap.Point(121.832141,31.039146),
    new BMap.Point(121.824883,31.041064),
    new BMap.Point(121.822871,31.046014),
    new BMap.Point(121.828405,31.046261),
    new BMap.Point(121.814355,31.056563)


], { strokeColor: "#FFFFFF", strokeWeight: 3, strokeOpacity: 0.0, fillOpacity: 0.5,fillColor:"#021929"  });

function initMap() {
    map = new BMap.Map("map", {enableMapClick: false});
    map.centerAndZoom(new BMap.Point(121.875277,31.040663), 14);
    map.setMapStyle({style: 'midnight'});
    map.enableScrollWheelZoom(true);
    map.enablePinchToZoom(true);
    var opts = {anchor: BMAP_ANCHOR_TOP_RIGHT};
    map.addControl(new BMap.NavigationControl(opts));

    heatmap();
}

function heatmap() {
    $.ajax({
        url: "/oldman/visual/getLocation",
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
            map.addOverlay(polygon1);
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
        "lng": "121.865957",
        "lat": "31.023423"
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