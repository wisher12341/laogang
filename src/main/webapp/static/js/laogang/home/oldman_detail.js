$(document).ready(function(){
    var id=getQueryVariable("id");
    var view=getQueryVariable("view");
    loadOldmanInfo(id);
    if(view==="true"){
        $(".navbar").css("display","none");
    }
});

function loadOldmanInfo(id) {
    $.ajax({
        url: "/oldman/getById",
        data :JSON.stringify({
            "id":id
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var data = result;
            $("[name]").each(function () {
                var field = $(this).attr("name");
                var value =eval("data."+field);
                $(this).text(value);
            });
            var type = data.typeMap;
            $("[type]").each(function () {
                var field = $(this).attr("type");
                var value =eval("type["+field+"]");
                $(this).text(value);
            });
        }
    });
}

function loadContactPeople() {
    var table =$(".dataTables-example").dataTable(
        {
            "sPaginationType": "full_numbers",
            "bPaginite": false,
            "bInfo": false,
            'paging':false,
            'bLengthChange': false,
            "bSort": false,
            "bFilter": false, //搜索栏
            "bStateSave": true,
            "bProcessing": true, //加载数据时显示正在加载信息
            "bServerSide": true, //指定从服务器端获取数据
            "columns":[{
                data:"name"
            },{
                data:"phone"
            },{
                data:"relation"
            },{
                data:"address"
            }
            ],
            "sAjaxSource": "/contact/getContactManByOid",//这个是请求的地址
            "fnServerData": retrieveData
        });
}

function retrieveData(url, aoData, fnCallback) {
    $.ajax({
        url: url,//这个就是请求地址对应sAjaxSource
        data :JSON.stringify({
            "oid":window.location.search.split("=")[1]
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var data ={
                "aaData":result.contactManVoList
            };
            fnCallback(data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            // alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
        }
    });
}


function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return null;
}