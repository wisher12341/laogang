

function initMap() {
    map = new BMap.Map("map",{enableMapClick:false});
    map.centerAndZoom(new BMap.Point(121.846199,31.045218), 15);
    map.setMapStyle({style:'midnight'});
    map.enableScrollWheelZoom(true);
    map.enablePinchToZoom(true);
    var opts = {anchor: BMAP_ANCHOR_BOTTOM_RIGHT};
    map.addControl(new BMap.NavigationControl(opts));

    $.ajax({
        url: "/oldman/getAllLocation",
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({

        }),
        success: function (result) {
            var points =result.voList;
            points = [{lng: "121.856373", lat: "31.044634", count: "10"},
                {lng: "121.847893", lat: "31.045871", count: "10"},
                {lng: "121.844947", lat: "31.045005", count: "10"},
                {lng: "121.856373", lat: "31.044634", count: "10"},
                {lng: "121.845881", lat: "31.044139", count: "10"},
                {lng: "121.846168", lat: "31.043644", count: "10"},
                {lng: "121.844731", lat: "31.045438", count: "10"},
                {lng: "121.847749", lat: "31.043644", count: "10"},
                {lng: "121.84854", lat: "31.046428", count: "10"},
                {lng: "121.84315", lat: "31.04581", count: "10"},
                {lng: "121.846312", lat: "31.046243", count: "10"},
                {lng: "121.847174", lat: "31.042654", count: "10"},
                {lng: "121.843581", lat: "31.0468", count: "10"},
                {lng: "121.847318", lat: "31.045253", count: "10"}
                ];
            heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20,"visible":true});
            map.addOverlay(heatmapOverlay);
            heatmapOverlay.setDataSet({data:points,max:20});
            heatmapOverlay.show();
        }
    });

}
