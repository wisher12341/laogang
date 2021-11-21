var id;
$(document).ready(function(){
    $(".selectpicker").selectpicker({
        noneSelectedText: '身体状况'//默认显示内容
    });
    id=getQueryVariable("id");
    if (id!==null && id!==undefined && id!==""){
        loadOrganInfo();
    }
});

function submitOrgan() {
    var param = {};
    $("[name]").each(function () {
        var condition = "param." + $(this).attr("name") + "='" + $(this).val() + "'";
        eval(condition);

    });
    $.ajax({
        url: "/organ/editOrAddOldman",
        data: JSON.stringify(param),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            alert("保存成功");
        }
    });
}

function loadOrganInfo() {
    $.ajax({
        url: "/organ/getOldmanById",
        data :JSON.stringify({
            "id": id
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var data = result;
            $("[name]").each(function () {
                var field = $(this).attr("name");
                var value =eval("data."+field);
                if ($(this).attr("class").indexOf("selectpicker") != -1) {
                    var a = [];
                    a=value.split(",");
                    $(this).val(a);
                    $(this).selectpicker("refresh")
                }else{
                    $(this).val(value);
                }
            });
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