/**
 * Created by Administrator on 2017-03-10.
 */
$(document).ready(function() {
    var token=getcookie("token");
    locationUtil.init("sender","");
    getAddress(token);
    getCollect();
    $("#file").change(function () {
        var fileSize = $("#file")[0].size;
        var reg= /(.xlsx|.xls)$/;
        var size = fileSize / 1024;
        if (size > 2000) {
            alert("文件不能大于2M");
            return false;
        }
        if(reg.test($("#file").val())){
            $("#uploadFile").ajaxForm({
                headers: getHeader(token),
                dataType: 'text',
                success : function(result){
                    result=JSON.parse(Decrypt(result));
                    if(result.mark=="0"){
                        var express_order_collects=result.obj;
                        sessionStorage.removeItem("express_order_collects");
                        sessionStorage.setItem("express_order_collects",JSON.stringify(express_order_collects));
                        getCollect();
                    }else if(result.mark=="100"||result.mark=="101"){
                        window.location.href="login.html";
                    }else {
                        alert(result.tip);
                    }
                }
            }).submit();
        }else{
            alert("文件格式不正确.")
        }
    })

    $("#senderProvince").change(function () {
        locationUtil.onChangeProvince("sender","");
        getExpressCompany($("#senderDistrict").val(), token);
    })

    $("#senderCity").change(function () {
        locationUtil.onChangeCity("sender","");
        getExpressCompany($("#senderDistrict").val(), token);
    })

    $("#senderDistrict").change(function () {
        getExpressCompany($("#senderDistrict").val(), token);
    })

    $("#senderNameList").change(function () {
        $("#add_id").val($("#senderNameList").val());
        var time = Date.parse(new Date());
        var hash = hex_md5(time +"hotol");
        var address={
            add_id:$("#senderNameList").val()
        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            headers: getHeader(token),
            type: 'post',
            data: Encrypt(JSON.stringify(address)),
            url: '/suyh/app/3/token/findOneAddress',
            dataType: 'text',
            accpet: "application/json",
            success: function (data) {
                data = JSON.parse(Decrypt(data));
                if(data.mark=="0"){
                    $("#senderName").val(data.obj.add_name);
                    $("#senderMobileNo").val(data.obj.add_mobile_phone+data.obj.add_telephone);
                    $("#senderStreet").val(data.obj.add_detail_address);
                    locationUtil.setAddress("sender","",data.obj.add_province,data.obj.add_city,data.obj.add_region);
                    getExpressCompany(data.obj.add_region, token);
                }else if(data.mark=="100"||data.mark=="101"){
                    window.location.href="login.html";
                }else{
                    alert(data.tip);
                }
            }
        })
    })

    $("#senderName").keydown(function () {
        $("#add_id").val(0)
    })

    $("#quickVerifyNoSubmit").click(function () {
        if($("#senderName").val()==null||$("#senderName").val().trim()==""){
            alert("发件人姓名不能为空.");
            return false;
        }
        if($("#senderMobileNo").val()==null||$("#senderMobileNo").val().trim()==""){
            alert("发件人手机号不能为空.");
            return false;
        }
        if($("#senderStreet").val()==null||$("#senderStreet").val().trim()==""){
            alert("发件人详细地址不能为空.");
            return false;
        }
        if(parseInt($("#providerName").val())==0){
            alert("请选择快递公司.");
            return false;
        }
        var collectStr=sessionStorage.getItem("express_order_collects");
        if(collectStr!=null&&collectStr!="") {
            var collects = JSON.parse(collectStr);
            var order={
                add_name:$("#senderName").val(),
                add_mobile_phone:$("#senderMobileNo").val(),
                add_province:$("#senderProvince").val(),
                add_city:$("#senderCity").val(),
                add_region:$("#senderDistrict").val(),
                add_detail_address:$("#senderStreet").val(),
                express_id:$("#providerName").val(),
                add_id:$("#add_id").val(),
                add_longitude:0.00,
                add_latitude:0.00
            }
            var exp={
                express_order:order,
                express_order_collects:collects
            }
            var time = Date.parse(new Date());
            var hash = hex_md5(time +"hotol");
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                headers: getHeader(token),
                type: 'post',
                url: '/suyh/app/5/token/bigClientsSendExp',
                data:Encrypt(JSON.stringify(exp)),
                dataType: 'text',
                accpet: "application/json",
                success: function (data) {
                    data = JSON.parse(Decrypt(data));
                    if (data.mark == "0") {
                        alert("提交成功.");
                        sessionStorage.removeItem("express_order_collects");
                        window.location.reload();
                    }else if(data.mark=="100"||data.mark=="101"){
                        window.location.href="login.html";
                    } else {
                        alert(data.tip);
                    }
                }
            })
        }else {
            alert("请上传快件信息.")
        }
    })
})

function getCollect() {
    $("#collects").html("");
    var collectStr=sessionStorage.getItem("express_order_collects");
    if(collectStr!=null&&collectStr!=""){
        var collects=JSON.parse(collectStr);
        $.each(collects,function (index, item) {
            $("#collects").append("<tr id='tr"+index+"' style='line-height: 2;'>" +
                "<td style='text-align: center;'>"+item.ht_number+"</td>" +
                "<td style='text-align: center;'>"+item.add_name+"</td>" +
                "<td style='text-align: center;'>"+item.add_mobile_phone+"</td>" +
                "<td style='text-align: center;'>"+item.add_province_name+item.add_city_name+item.add_region_name+item.add_detail_address+"</td>" +
                "<td style='text-align: center;'>"+item.exp_ord_clt_height+"</td>" +
                "<td style='text-align: center;'>"+item.exp_ord_category+"</td>" +
                "<td style='text-align: center;'>" +
                "<a id='del"+index+"' onclick='del("+index+")' href='javascript:void(0)' style='padding: 2px 6px;border-radius: 10px;background-color: orange;'>删除</a>" +
                /*"<a id='update"+index+"' href='javascript:void(0)' style='padding: 2px 6px;border-radius: 10px;background-color: orange;'>修改</a>" +*/
                "</td>" +
                "</tr>");
        })
    }
}

function getAddress(token) {
    var time = Date.parse(new Date());
    var hash = hex_md5(time +"hotol");
    $.ajax({
        contentType: "application/json;charset=UTF-8",
        headers: getHeader(token),
        type: 'post',
        url: '/suyh/app/5/token/findAllSendAddress',
        dataType: 'text',
        accpet: "application/json",
        success: function (data) {
            data = JSON.parse(Decrypt(data));
            if(data.mark=="0"){
                $.each(data.obj,function (index, item) {
                    if(item.add_is_default==0){
                        $("#senderNameList").append("<option value='"+item.add_id+"'>"+item.add_name+"&nbsp;&nbsp;&nbsp;"+item.add_mobile_phone+item.add_telephone+"&nbsp;&nbsp;&nbsp;"+item.province+item.city+item.region+item.add_detail_address+"[默认]</option>");
                        $("#senderName").val(item.add_name);
                        $("#senderMobileNo").val(item.add_mobile_phone+item.add_telephone);
                        locationUtil.setAddress("sender","",item.add_province,item.add_city,item.add_region);
                        $("#senderStreet").val(item.add_detail_address);
                        $("#add_id").val(item.add_id);
                    }else {
                        $("#senderNameList").append("<option value='"+item.add_id+"'>"+item.add_name+"&nbsp;&nbsp;&nbsp;"+item.add_mobile_phone+item.add_telephone+"&nbsp;&nbsp;&nbsp;"+item.province+item.city+item.region+item.add_detail_address+"</option>");
                    }
                })
                getExpressCompany($("#senderDistrict").val(), token);
            }else{
                alert(data.tip);
            }
        }
    })
}

function getExpressCompany(dict_id, token) {
    var time = Date.parse(new Date());
    var hash = hex_md5(time +"hotol");
    var courierDto={
        region_id:dict_id
    }
    $.ajax({
        contentType: "application/json;charset=UTF-8",
        headers: getHeader(token),
        type: 'post',
        data:Encrypt(JSON.stringify(courierDto)),
        url: '/suyh/app/3/token/findExpressCompany',
        dataType: 'text',
        accpet: "application/json",
        success: function (data) {
            data = JSON.parse(Decrypt(data));
            if(data.mark=="0"){
                $("#providerName").empty();
                $(data.obj).each(function (index,item) {
                    $("#providerName").append("<option value='"+item.dict_id+"'>"+item.code_name+"</option>");
                })
            }else if(data.mark=="100"||data.mark=="101"){
                window.location.href="login.html";
            }else{
                $("#providerName").empty();
                $("#providerName").append("<option value='0'>"+data.tip+"</option>");
            }
        }
    })
}

function del(i) {
    var collectStr=sessionStorage.getItem("express_order_collects");
    if(collectStr!=null&&collectStr!=""){
        var collects=JSON.parse(collectStr);
        $.each(collects,function (index, item) {
            if(index==i){
                collects.splice(i,1);
            }
        })
        sessionStorage.setItem("express_order_collects",JSON.stringify(collects));
        getCollect();
    }
}