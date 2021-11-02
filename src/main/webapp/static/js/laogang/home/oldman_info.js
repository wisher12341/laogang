var id;
var table;
var haslink=false;
var view;
$(document).ready(function () {
    view = getQueryVariable("view");
    if(view==="true"){
        $("#view").css("display","none");
    }
    id = getQueryVariable("id");
    $('#rootwizard').bootstrapWizard({
        onTabShow: function (tab, navigation, index) {
            var $total = navigation.find('li').length;
            var $current = index + 1;
            var $percent = ($current / $total) * 100;
            $('#rootwizard').find('.bar').css({width: $percent + '%'});
            // If it's the last tab then hide the last button and show the finish instead
            if ($current >= $total) {
                $('#rootwizard').find('.pager .next').hide();
                $('#rootwizard').find('.pager .finish').show();
                $('#rootwizard').find('.pager .finish').removeClass('disabled');
            } else {
                $('#rootwizard').find('.pager .next').show();
                $('#rootwizard').find('.pager .finish').hide();
            }
        }
    });
    $('#rootwizard .finish').click(function () {
        alert('Finished!, Starting over!');
        $('#rootwizard').find("a[href*='tab1']").trigger('click');
    });


    $(".selectpicker").selectpicker({
        noneSelectedText: ''//默认显示内容
    });

    loadOldmanInfo(id);


    table = $(".dataTables-example").dataTable(
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
                data: "name"
            }, {
                data: "idCard"
            }, {
                data: "relation"
            }, {
                data: "phone"
            }, {
                data: "iscall"
            }
            ],
            "columnDefs": [
                // 列样式
                {
                    "targets": [0], // 目标列位置，下标从0开始
                    "data": "id", // 数据列名
                    "render": function (data, type, full) { // 返回自定义内容
                        return "<input type='checkbox' name='linkmanid' value='" + data + "'/>";
                    }
                }
            ],
            "sAjaxSource": "/linkman/getByPage",//这个是请求的地址
            "fnServerData": retrieveData
        });

    function retrieveData(url, aoData, fnCallback) {
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data: JSON.stringify({
                "pageParam": {
                    "pageNo": aoData.iDisplayStart / aoData.iDisplayLength,
                    "pageSize": aoData.iDisplayLength
                },
                "param": {
                    "oldmanId": id
                }
            }),
            type: 'post',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if(result.voList!==null && result.voList.length>0){
                    haslink = true;
                }
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
});

function loadOldmanInfo(id) {
    $.ajax({
        url: "/oldman/getById",
        data: JSON.stringify({
            "id": id
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var data = result;
            $("[name]").each(function () {
                var field = $(this).attr("name");
                var value = eval("data." + field);
                setOldmanValue(this, value);
            });
            var type = data.typeMap;
            $("[map]").each(function () {
                var field = $(this).attr("map");
                var value = eval("type[" + field + "]");
                var ext="";
                value=""+value;
                if(value.indexOf(",")!=-1){
                    var arr = value.split(",");
                    var values=[];
                    for(var i=0;i<arr.length;i++){
                        if (arr[i].indexOf("_")!=-1){
                            ext = arr[i].split("_")[1];
                            var s =0;
                            $(this).children().each(function () {
                               if($(this).text()===arr[i].split("_")[0]){
                                   s=$(this).val();
                               }
                            });
                            var item="input[ext='"+field+"_"+s+"']";
                            $(item).val(ext);
                            $("#ext"+field+"_"+s).show();
                            values[i] = arr[i].split("_")[0];
                        }else{
                            var s =0;
                            $(this).children().each(function () {
                                if($(this).text()===arr[i]){
                                    s=$(this).val();
                                }
                            });
                            $("#ext"+field+"_"+s).show();
                            values[i] = arr[i];
                        }
                    }
                    value = values.join(",");
                }else{
                    if (value.indexOf("_")!=-1){
                        ext = value.split("_")[1];
                        var s =0;
                        $(this).children().each(function () {
                            if($(this).text()===value.split("_")[0]){
                                s=$(this).val();
                            }
                        });
                        $("input[ext='"+field+"']").val(ext);
                        $("#ext"+field).show();
                        $("input[ext='"+field+"_"+s+"']").val(ext);
                        $("#ext"+field+"_"+s).show();
                        value = value.split("_")[0];
                    }
                }
                setOldmanValue(this, value);
            });
            if($("select[name='vaccine']").val()!=="" && $("select[name='vaccine']").val()!==null && $("select[name='vaccine']").val()!==undefined){
                $('.ym').show();
                $("#ra1").attr("checked",true);
            }else{
                $("#ra2").attr("checked",true);
            }
            if($("input[name='ideviceName']").val()!=="" && $("input[name='ideviceName']").val()!==null && $("input[name='ideviceName']").val()!==undefined){
                $('.id').show();
                $("#ra3").attr("checked",true);
            }else{
                $("#ra4").attr("checked",true);
            }
            if($("input[name='homeBedOrgan']").val()!=="" && $("input[name='homeBedOrgan']").val()!==null && $("input[name='homeBedOrgan']").val()!==undefined){
                $('.hb').show();
                $("#ra5").attr("checked",true);
            }else{
                $("#ra6").attr("checked",true);
            }
            if($("input[name='homeDoctorName']").val()!=="" && $("input[name='homeDoctorName']").val()!==null && $("input[name='homeDoctorName']").val()!==undefined){
                $('.hd').show();
                $("#ra7").attr("checked",true);
            }else{
                $("#ra8").attr("checked",true);
            }
            if($("select[map='13']").val()==="3"){
                $("#map14").show();
            }
            if($("select[map='11']").val()==="1" || $("select[map='11']").val()==="2"){
                $("#ext11").show();
            }
        }
    });
}
function map11Change(obj){
    if($(obj).val()==="1" || $(obj).val()==="2"){
        $("#ext11").show();
    }else{
        $("#ext11").hide();
        $("#ext11 input").val("");
    }
}

function map12Change(obj){
    var val = ""+$(obj).val();
    if(val.indexOf("7")!==-1){
        $("#ext12_7").show();
    }else{
        $("#ext12_7").hide();
        $("#ext12_7 input").val("");
    }
    if (val.indexOf("8")!==-1){
        $("#ext12_8").show();
    }else{
        $("#ext12_8").hide();
        $("#ext12_8 input").val("");
    }
}

function oldStatusChange(obj) {
    if($(obj).val()==="3"){
        $("#map14").show();
    }else{
        $("#map14").hide();
        $("#map14 select").val("");
    }
}

function deleteLinkman() {
    var chk_value =[];
    $('input[name="linkmanid"]:checked').each(function(){
        chk_value.push($(this).val());
    });
    $.ajax({
        url: "/linkman/delete",
        data: JSON.stringify({
            "idList": chk_value
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            table.fnFilter();
        }
    });
}

function submit() {
    if(!haslink) {
        alert("请填写必填项");
        return;
    }

    var param = {};
    $("[name]").each(function () {
        if ($(this).is(":disabled") === false && $(this).val() !== null && $(this).val().length > 0) {
            var condition = "param." + $(this).attr("name") + "='" + $(this).val() + "'";
            eval(condition);
        }
    });
    param.id = id;
    var map = {};
    $("[map]").each(function () {
        if ($(this).is(":disabled") === false) {
            var condition = "map.type" + $(this).attr("map") + "='" + $(this).val() + "'";
            eval(condition);
        }
    });
    param.map = map;
    var ext = {};
    $("[ext]").each(function () {
        if ($(this).is(":disabled") === false) {
            var condition = "ext.type" + $(this).attr("ext") + "='" + $(this).val() + "'";
            eval(condition);
        }
    });
    param.ext = ext;
    $.ajax({
        url: "/oldman/editOrAdd",
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

function setOldmanValue(obj, value) {
    if (value === "" || value === null || value === undefined) {
        return
    }
    var tagType = $(obj).prop("tagName");
    if (tagType === "SPAN") {
        $(obj).text(value);
    }
    else if (tagType === "INPUT") {
        $(obj).val(value);
    } else if (tagType === "SELECT") {
        var clazz = $(obj).attr("class");
        if (clazz.indexOf("selectpicker") != -1) {
            var a = [];
            var i = 0;
            $(obj).children("option").each(function () {
                if (value.indexOf($(this).text()) != -1) {
                    a[i] = $(this).val();
                    i++;
                }
            });
            $(obj).val(a);
            $(obj).selectpicker("refresh")
        } else {
            $(obj).children().each(function () {
                if ($(this).text() === value) {
                    $(this).attr("selected", "selected");
                }
            })
        }
    }
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


function saveLinkMan() {
    $.ajax({
        url: "/linkman/add",//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({
            "name": $("input[linkman='name']").val(),
            "phone": $("input[linkman='phone']").val(),
            "idCard": $("input[linkman='idCard']").val(),
            "iscall": $("select[linkman='call']").val(),
            "relation": $("input[linkman='relation']").val(),
            "oldmanId": id

        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            table.fnFilter();
            $("#myModal").modal('hide');
            $("[linkman]").val("")
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
        }
    });
}