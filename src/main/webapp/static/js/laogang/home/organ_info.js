$(document).ready(function(){
    loadOrganInfo();
});

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
        data :JSON.stringify({}),
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
        }
    });
}
