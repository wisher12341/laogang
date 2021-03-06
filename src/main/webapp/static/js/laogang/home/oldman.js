var table;
$(document).ready(function(){
    $(".politics").selectpicker({
        noneSelectedText : '政治面貌'//默认显示内容
    });

    $(".jkzk").selectpicker({
        noneSelectedText : '健康状况'//默认显示内容
    });
    $(".eyesight").selectpicker({
        noneSelectedText : '视力情况'//默认显示内容
    });
    $(".psychosis").selectpicker({
        noneSelectedText : '精神状态'//默认显示内容
    });
    $(".family").selectpicker({
        noneSelectedText : '家庭结构'//默认显示内容
    });
    $(".familyType").selectpicker({
        noneSelectedText : '家庭属性'//默认显示内容
    });
    $(".income").selectpicker({
        noneSelectedText : '经济条件'//默认显示内容
    });
    $(".areaVillage").selectpicker({
        noneSelectedText : '村/居委'//默认显示内容
    });
    $(".jujia").selectpicker({
        noneSelectedText : '居家养老项目'//默认显示内容
    });
    $(".serviceStatus").selectpicker({
        noneSelectedText : '养老状态'//默认显示内容
    });

    loadOrgan();

    table =$(".dataTables-example").dataTable(
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
                    "targets": [7], // 目标列位置，下标从0开始
                    "data": "id", // 数据列名
                    "render": function(data, type, full) { // 返回自定义内容
                        return "<button class='btn btn-success' style='padding: 2px 4px;font-size: 10px' onclick=window.open('/home/oldman/info?id="+data+"')>查看</button>";
                    }
                }
            ],
            "sAjaxSource": "/oldman/getByPage",//这个是请求的地址
            "fnServerData": retrieveData
        });
    function retrieveData(url, aoData, fnCallback) {
        $.ajax({
            url: url,//这个就是请求地址对应sAjaxSource
            data :JSON.stringify({
                "pageParam": {
                    "pageNo": aoData.iDisplayStart / aoData.iDisplayLength,
                    "pageSize": aoData.iDisplayLength
                },
                "oldmanParam":{
                    "age":($("input[name='ageStart']").val().length>0 || $("input[name='ageEnd']").val().length>0)?$("input[name='ageStart']").val()+"-"+$("input[name='ageEnd']").val():null,
                    "male":$("select[name='male']").val(),
                    "huiji":$("select[name='huji']").val(),
                    "politicsList":$("select[name='politics']").val(),
                    "isZd":$("select[name='isZd']").val(),
                    "eyesightList":$("select[name='eyesight']").val(),
                    "psychosisList":$("select[name='psychosis']").val(),
                    "jkzkList":$("select[name='jkzk']").val(),
                    "familyList":$("select[name='family']").val(),
                    "familyTypeList":$("select[name='familyType']").val(),
                    "incomeList":$("select[name='income']").val(),
                    "serviceStatusList":$("select[name='serviceStatus']").val(),
                    "search":$("input[name='search']").val(),
                    "areaVillageList":$("select[name='areaVillage']").val(),
                    "jujiaList":$("select[name='jujia']").val(),
                    "rh":$("select[name='rh']").val(),
                    "haveDoctor":$("select[name='haveDoctor']").val(),
                    "shequList":$("select[name='shequ']").val(),
                    "jigouList":$("select[name='jigou']").val()
                }
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
                $("#spinner",window.parent.document).hide();
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                // alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
            }
        });
    }

    // var select = $("#DataTables_Table_0_length label select");
    // $("#DataTables_Table_0_length label").text("");
    // $("#DataTables_Table_0_length label").append(select);

    //bootstrap 版本问题 col- 不生效， 需要手动设置 表格下面的配置的 css
    $("#DataTables_Table_0_info").parent().addClass("span6");

    $('#search').click(function () {
        $("#spinner",window.parent.document).show();
        table.fnFilter();
        $(".lll").html("标签：");
        $(".sxx select").each(function () {
            var text = $(this).children("option:selected").text();
            var value = $(this).children("option:selected").val();
            if (value!==null && value!=="" && value!==undefined){
                var span = $("<span>"+text+"，</span>");
                $(".lll").append(span);
            }
        });
        $(".sxx input").each(function () {
            var value = $(this).val();
            if (value!==null && value!=="" && value!==undefined){
                var pre="";
                if ($(this).attr("name")==="ageStart"){
                    pre=">="
                }
                if ($(this).attr("name")==="ageEnd"){
                    pre="<="
                }
                var span = $("<span>"+pre+value+"</span>");
                $(".lll").append(span);
            }
        });
    });

    var oTable=$("#editable").dataTable();
    oTable.$("td").editable("",{
        "callback":function(sValue,y){var aPos=oTable.fnGetPosition(this);oTable.fnUpdate(sValue,aPos[0],aPos[1])},
        "submitdata":function(value,settings){return{"row_id":this.parentNode.getAttribute("id"),
            "column":oTable.fnGetPosition(this)[2]}},"width":"90%","height":"100%"});

});

function loadOrgan() {
    $.ajax({
        url: "/organ/getByPage",
        data: JSON.stringify({
            "pageParam": {
                "pageNo": 0,
                "pageSize": 100
            },
            "organParam": {
                "serviceType": [1,2]
            }
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            var data = result.organVoList;
            for (var i = 0; i < data.length; i++) {
                var option = $("<option value='"+data[i].id+"'>"+data[i].name+"</option>")
                if (data[i].serviceType===1){
                    $(".jigou").append(option);
                }
                if (data[i].serviceType===2){
                    $(".shequ").append(option);
                }
            }
            $(".shequ").selectpicker({
                noneSelectedText : '社区养老机构选择'//默认显示内容
            });
            $(".jigou").selectpicker({
                noneSelectedText : '机构养老选择'//默认显示内容
            });
        }
    });
}


function serviceStatusChange(obj) {
    var val = ""+$(obj).val();
    $("#shequ").hide();
    $(".shequ").val("");
    $(".shequ").selectpicker("refresh");
    $("#jigou").hide();
    $(".jigou").val("");
    $(".jigou").selectpicker("refresh");
    $("#jujia").hide();
    $(".jujia").val("");
    $(".jujia").selectpicker("refresh");

    if(val.indexOf("3")>=0){
        $("#jujia").css("display","inline");
    }
    if(val.indexOf("2")>=0){
        $("#shequ").css("display","inline");
    }
    if(val.indexOf("1")>=0){
        $("#jigou").css("display","inline");
    }
}

function deleteOldman(oid) {
    $.ajax({
        url: "/oldman/delete",//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({"oid": oid}),
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

function searchOldman() {
    $("#spinner",window.parent.document).show();
    table.fnFilter();
}

function searchReset() {
    $("#spinner",window.parent.document).show();
    $("#searchDiv input[type!='button']").val("");
    $("#searchDiv select option:first").prop("selected", 'selected');
    table.fnFilter();
}


function exportOldman() {
    $.ajax({
        url: "/oldman/exportExcel",
        data :JSON.stringify({
                "areaCountry":$("input[name='areaCountry']").val(),
                "areaTown":$("input[name='areaTown']").val(),
                "areaVillage":$("input[name='areaVillage']").val(),
                "areaCustomOne":$("input[name='areaCustomOne']").val(),
                "name":$("input[name='name']").val(),
                "idCard":$("input[name='idCard']").val(),
                "serviceStatus":$("select[name='serviceStatus']").val(),
                "createTimeStart":$("input[name='createTimeStart']").val()!=""?$("input[name='createTimeStart']").val()+" 00:00:00":$("input[name='createTimeStart']").val(),
                "createTimeEnd":$("input[name='createTimeEnd']").val()!=""?$("input[name='createTimeEnd']").val()+" 00:00:00":$("input[name='createTimeEnd']").val()
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        sync:true,
        success: function (result) {
        }
    });
}

function sx(value,obj) {
    $(".sxx").hide();
    $("."+value).show();
    $(".selectCx").attr("class","btn btn-success");
    $(obj).attr("class","btn btn-success selectCx");
}


function savePolicy() {
    $.ajax({
        url: "/policy/add",//这个就是请求地址对应sAjaxSource
        data :JSON.stringify({
            "oldmanParam":{
                "age":($("input[name='ageStart']").val().length>0 || $("input[name='ageEnd']").val().length>0)?$("input[name='ageStart']").val()+"-"+$("input[name='ageEnd']").val():null,
                "male":$("select[name='male']").val(),
                "huiji":$("select[name='huji']").val(),
                "politicsList":$("select[name='politics']").val(),
                "isZd":$("select[name='isZd']").val(),
                "eyesightList":$("select[name='eyesight']").val(),
                "psychosisList":$("select[name='psychosis']").val(),
                "jkzkList":$("select[name='jkzk']").val(),
                "familyList":$("select[name='family']").val(),
                "familyTypeList":$("select[name='familyType']").val(),
                "incomeList":$("select[name='income']").val(),
                "serviceStatusList":$("select[name='serviceStatus']").val(),
                "search":$("input[name='search']").val()
            }
        }),
        type: 'post',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            $("iframe",window.parent.document).attr("src","/home/policy/info");
        },
        error:function(XMLHttpRequest, textStatus, errorThrown) {
            // alert("status:"+XMLHttpRequest.status+",readyState:"+XMLHttpRequest.readyState+",textStatus:"+textStatus);
        }
    });
}

function searchReset1() {
    $("#spinner",window.parent.document).show();
    $("input[name='search']").val("");
    $("#sear input[name]").val("");
    $("#sear select[name]").each(function () {
        $(this).val("");
        if ($(this).attr("class").indexOf("selectpicker")>=0){
            $(this).selectpicker("refresh");
        }
    });
    $("#jujia").hide();
    $(".jujia").val("");
    $(".jujia").selectpicker("refresh");
    $(".sxx").hide();
    $(".selectCx").removeClass("selectCx");
    table.fnFilter();
}