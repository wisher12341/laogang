var id;
$(document).ready(function(){

    id=getQueryVariable("id");
    $('#rootwizard').bootstrapWizard({onTabShow: function(tab, navigation, index) {
        var $total = navigation.find('li').length;
        var $current = index+1;
        var $percent = ($current/$total) * 100;
        $('#rootwizard').find('.bar').css({width:$percent+'%'});
        // If it's the last tab then hide the last button and show the finish instead
        if($current >= $total) {
            $('#rootwizard').find('.pager .next').hide();
            $('#rootwizard').find('.pager .finish').show();
            $('#rootwizard').find('.pager .finish').removeClass('disabled');
        } else {
            $('#rootwizard').find('.pager .next').show();
            $('#rootwizard').find('.pager .finish').hide();
        }
    }});
    $('#rootwizard .finish').click(function() {
        alert('Finished!, Starting over!');
        $('#rootwizard').find("a[href*='tab1']").trigger('click');
    });


    $(".selectpicker").selectpicker({
        noneSelectedText : ''//默认显示内容
    });

    loadOldmanInfo(id);
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
                setOldmanValue(this,value);
            });
            var type = data.typeMap;
            $("[map]").each(function () {
                var field = $(this).attr("map");
                var value =eval("type["+field+"]");
                setOldmanValue(this,value);
            });
        }
    });
}

function submit() {
    var param={};
    $("[name]").each(function () {
        if ($(this).is(":disabled")===false && $(this).val()!== null && $(this).val().length>0) {
            var condition = "param." + $(this).attr("name") + "='" + $(this).val()+"'";
            eval(condition);
        }
    });
    param.id=id;
    var map={};
    $("[map]").each(function () {
        if ($(this).is(":disabled")===false) {
            var condition = "map.type" + $(this).attr("map") + "='" + $(this).val()+"'";
            eval(condition);
        }
    });
    param.map=map;
    console.log(JSON.stringify(param));
    $.ajax({
        url: "/oldman/edit",
        data :JSON.stringify({
            "oldman":param
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var data = result;
            $("[name]").each(function () {
                var field = $(this).attr("name");
                var value =eval("data."+field);
                setOldmanValue(this,value);
            });
            var type = data.typeMap;
            $("[map]").each(function () {
                var field = $(this).attr("map");
                var value =eval("type["+field+"]");
                setOldmanValue(this,value);
            });
        }
    });
}

function setOldmanValue(obj,value) {
    if (value==="" || value ===null || value===undefined){
        return
    }
    var tagType=$(obj).prop("tagName");
    if(tagType==="SPAN"){
        $(obj).text(value);
    }
    else if(tagType==="INPUT"){
        $(obj).val(value);
    }else if (tagType === "SELECT"){
        var clazz = $(obj).attr("class");
        if(clazz.indexOf("selectpicker")!=-1){
            var a=[];
            var i=0;
            $(obj).children("option").each(function () {
                if(value.indexOf($(this).text())!=-1){
                    a[i]=$(this).val();
                    i++;
                }
            });
            $(obj).val(a);
            $(obj).selectpicker("refresh")
        }else{
            $(obj).children().each(function () {
                if($(this).text()===value){
                    $(this).attr("selected","selected");
                }
            })
        }
    }
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