$(document).ready(function(){
    var storage = window.sessionStorage;
    var name = sessionStorage.getItem('add_name');
    var phone = sessionStorage.getItem('add_phone');
    storage.setItem('flag', "0");
    var token = getcookie("sra");

    var title = sessionStorage.getItem('add_title');
    var address = sessionStorage.getItem('add_address');
    var lat = sessionStorage.getItem('add_lat');
    var lng = sessionStorage.getItem('add_lng');

    sessionStorage.removeItem("add_title");
    sessionStorage.removeItem("add_address");
    sessionStorage.removeItem("add_lat");
    sessionStorage.removeItem("add_lng");

    if(title != null && name != null){
        $("#name").val(name);
    }
    if(title != null && phone != null){
        $("#phone").val(phone);
    }

    $("#txt_area").val(title);
    $("#add_street").val(address);

    if(parseFloat(lng) > 0 && parseFloat(lat) > 0){
        var json = {
            "add_longitude": parseFloat(lng),
            "add_latitude":parseFloat(lat)
        };
        var res = getUrl("/suyh/app/7/token/findDictByLatLng", json, token);
        if(res != null){
            var data = JSON.parse(res);
            if(data.mark == "0"){
                $("#province").val(data.obj.province_id);
                $("#city").val(data.obj.city_id);
                $("#region").val(data.obj.region_id);
            }else {
                alert(data.tip);
            }
        }
    }

    /*======================================数据提交===================================*/
    $(".btn").click(function(){
        var name= $("#name").val();
        var phone= $("#phone").val();
        var txt=$("#txt_area").val();
        var Checked = $("#c").is(":checked");
        var add_is_default=1
        if(Checked==true){
            add_is_default=0
        }
        if(name==null || name==""){
            alert("姓名不能为空");
            return false;
        }
        if(phone==null || phone==""){
            alert("手机号不能为空");
            return false;
        }
        if(address==null||address==""){
            alert("详细地址不能为空");
            return false;
        }

        var json = {
            "add_name":name,
            "add_detail_address":txt,
            "add_street":$("#add_street").val(),
            "add_province":$("#province").val(),
            "add_city":$("#city").val(),
            "add_region":$("#region").val(),
            "add_label":0,
            "add_mobile_phone":phone,
            "add_longitude":lng,
            "add_latitude":lat,
            "add_is_default":add_is_default,
            "add_type":1
        };
        var res = getUrl("/suyh/app/3/token/saveAddress", json, token);
        if(res != ""){
            var data = JSON.parse(res);
            var datv=data.mark;
            var tip=data.tip;
            if(datv==1){
                alert(tip)
            }else{
                alert(tip);
                sessionStorage.removeItem("add_name");
                sessionStorage.removeItem("add_phone");
                sessionStorage.removeItem("add_title");
                sessionStorage.removeItem("add_address");
                sessionStorage.removeItem("add_lat");
                sessionStorage.removeItem("add_lng");
                window.location.href = "sender.html";
            }
        }
    })
});

function jump() {
    var storage = window.sessionStorage;
    storage.setItem('add_name',  $("#name").val());
    storage.setItem('add_phone',  $("#phone").val());
    window.location.href = "chooseMapAddress.html";
}