$(document).ready(function(){
    $("input[name='username']").val($("#username",window.parent.document).text().split("(")[0]);
});

function submitUser() {
    var param = {};
    $("[name]").each(function () {
        var condition = "param." + $(this).attr("name") + "='" + $(this).val() + "'";
        eval(condition);

    });
    $.ajax({
        url: "/user/edit",
        data: JSON.stringify(param),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            alert("保存成功");
            location.reload();
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
