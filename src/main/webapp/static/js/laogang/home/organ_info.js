var id;
var view;
$(document).ready(function(){
    id = getQueryVariable("id");
    view = getQueryVariable("view");
    if (view==='true'){
        $("button").hide();
    }
    loadOrganInfo();
});


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

function loadOrganInfo() {
    $.ajax({
        url: "/organ/getByUser",
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
                $(this).val(value);
            });
            $("#spinner",window.parent.document).hide();

        }
    });
}
