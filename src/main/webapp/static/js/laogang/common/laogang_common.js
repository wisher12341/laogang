function tableAllCheck(obj) {
    tableAllCheckWithId(obj,"id");
}

function tableAllCheckWithId(obj,idVaule) {
    console.log(idVaule);
    var check = $(obj).prop("checked");
    if (check === undefined){
        check = false;
    }

    if (!check){
        $("table input[name='"+idVaule+"']").each(function () {
            $(this).prop("checked",false);
        });
    }else{
        $("table input[name='"+idVaule+"']").each(function () {
            $(this).prop("checked",true);
        });
    }
}

function deleteTable(tableName) {
    var param =[];
    var i=0;
    $("input[name='id']:checked").each(function () {
        param[i]=$(this).val();
        i++;
    });
    $.ajax({
        url: "/db/delete",//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({"idList": param,"table":tableName}),
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