var id;
var table;
$(document).ready(function(){
    id = getQueryVariable("id");
    loadInfo();
    loadOldman();
});

function loadOldman() {
    table = $(".table").dataTable(
        {
            "sPaginationType": "full_numbers",
            "bPaginite": true,
            "bInfo": true,
            "bSort": false,
            "bFilter": false, //搜索栏
            "bStateSave": true,
            "bProcessing": true, //加载数据时显示正在加载信息
            "bServerSide": true, //指定从服务器端获取数据
            "columns":[{},{
                data:"name"
            },{
                data:"male"
            },{
                data:"age"
            },{
                data:"areaVillage"
            },{
                data:"idCard"
            },{
                data:"phone"
            },{
                data:"finish"
            }
            ],
            "columnDefs": [
                // 列样式
                {
                    "targets": [0], // 目标列位置，下标从0开始
                    "data": "id", // 数据列名
                    "render": function(data, type, full) { // 返回自定义内容
                        return"<input type='checkbox' name='id' value='"+data+"'/>";
                    }
                },
                {
                    "targets": [8], // 目标列位置，下标从0开始
                    "data": "idFinish", // 数据列名
                    "render": function(data, type, full) { // 返回自定义内容
                        var id=data.split("_")[0];
                        var finish = data.split("_")[1];
                        var desc = finish ==0?"完成":"完成撤销";
                        return "<button class='btn btn-success' style='padding: 2px 4px;font-size: 10px' onclick=window.open('/home/oldman/info?id="+id+"')>查看</button>" +
                            "<button class='btn btn-success' style='padding: 2px 4px;font-size: 10px' onclick=finishChange('"+id+"','"+finish+"')>"+desc+"</button>";
                    }
                }
            ],
            "sAjaxSource": "/policy/getOldman",//这个是请求的地址
            "fnServerData": retrieveData1
        });
    function retrieveData1(url, aoData, fnCallback) {
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data :JSON.stringify({
                "pageParam": {
                    "pageNo": aoData.iDisplayStart / aoData.iDisplayLength,
                    "pageSize": aoData.iDisplayLength
                },
                "id":id
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

function finishChange(oldmanId,finish) {
    $.ajax({
        url: "/policy/oldman/finish",
        data: JSON.stringify({
            "oldmanId":oldmanId,
            "policyId":id,
            "finish":Math.abs(finish-1)
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            table.fnFilter();
        }
    });
}

function submitOrgan() {
    var param = {};
    $("[name]").each(function () {
        var condition = "param." + $(this).attr("name") + "='" + $(this).val() + "'";
        eval(condition);

    });
    $.ajax({
        url: "/organ/edit",
        data: JSON.stringify(param),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            alert("保存成功");
        }
    });
}

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return null;
}

function loadInfo() {
    $.ajax({
        url: "/policy/getById",
        data :JSON.stringify({"id":id}),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var data = result;
            $("#info [name]").each(function () {
                var field = $(this).attr("name");
                var value =eval("data."+field);
                $(this).val(value);
                var tagType = $(this).prop("tagName");
                if (tagType === "SELECT"){
                    $(this).children().each(function () {
                        if ($(this).text() === value) {
                            $(this).attr("selected", "selected");
                        }
                    })
                }
            });
        }
    });
}
