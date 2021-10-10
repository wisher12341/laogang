var table;
var table2 = null;
var policyId = null;
$(document).ready(function () {
    table = $("#table1").dataTable(
        {
            "sPaginationType": "full_numbers",
            "bPaginite": true,
            "bInfo": true,
            "bSort": false,
            "bFilter": false, //搜索栏
            "bStateSave": true,
            "bProcessing": true, //加载数据时显示正在加载信息
            "bServerSide": true, //指定从服务器端获取数据
            "columns": [{}, {
                data: "sender"
            }, {
                data: "type"
            }, {
                data: "type"
            }, {
                data: "content"
            }, {
                data: "time"
            }
            ],
            "columnDefs": [
                // 列样式
                {
                    "targets": [0], // 目标列位置，下标从0开始
                    "data": "id", // 数据列名
                    "render": function (data, type, full) { // 返回自定义内容
                        return "<input type='checkbox' name='id' value='" + data + "'/>";
                    }
                },
                {
                    "targets": [5], // 目标列位置，下标从0开始
                    "data": "id", // 数据列名
                    "render": function (data, type, full) { // 返回自定义内容
                        return '<a href="#myModal" style="padding: 2px 4px;font-size: 10px" data-toggle="modal" class="btn btn-success" onclick=look(' + data + ')>查看</a>';
                    }
                }
            ],
            "sAjaxSource": "/message/getByPage",//这个是请求的地址
            "fnServerData": retrieveData
        });

    function retrieveData(url, aoData, fnCallback) {
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data: JSON.stringify({
                "pageNo": aoData.iDisplayStart / aoData.iDisplayLength,
                "pageSize": aoData.iDisplayLength

            }),
            type: 'post',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                var data = {
                    "iTotalRecords": result.count,
                    "iTotalDisplayRecords": result.count,
                    "aaData": result.voList
                };
                fnCallback(data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
            }
        });
    }


    $('#search').click(function () {
        table.fnFilter();
    });


});


function deleteOldman(oid) {
    $.ajax({
        url: "/oldman/delete",//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({"oid": oid}),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            table.fnFilter();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
        }
    });
}


function searchReset() {
    $("#searchDiv input[type!='button']").val("");
    $("#searchDiv select option:first").prop("selected", 'selected');
    table.fnFilter();
}

