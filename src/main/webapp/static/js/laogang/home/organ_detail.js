$(document).ready(function(){
    var id=getQueryVariable("id");
    var view=getQueryVariable("view");
    loadOrganInfo(id);
    if(view==="true"){
        $(".navbar").css("display","none");
    }
});

function loadOrganInfo(id) {
    $.ajax({
        url: "/organ/getById",
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