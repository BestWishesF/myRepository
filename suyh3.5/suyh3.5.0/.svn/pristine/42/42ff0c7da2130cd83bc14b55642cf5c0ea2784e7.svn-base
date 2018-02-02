$(document).ready(function () {
    var storage = window.sessionStorage;
    var name = sessionStorage.getItem('update_name');
    var phone = sessionStorage.getItem('update_phone');
    storage.setItem('flag', "1");
    var token = getcookie("sra");
    var json = [];
    var stas = eval('(' + sessionStorage.getItem('td') + ")");
    var province;
    var city;
    var region;
    var title = sessionStorage.getItem('update_title');
    var address = sessionStorage.getItem('update_address');
    var lat = sessionStorage.getItem('update_lat');
    var lng = sessionStorage.getItem('update_lng');

    sessionStorage.removeItem("update_title");
    sessionStorage.removeItem("update_address");
    sessionStorage.removeItem("update_lat");
    sessionStorage.removeItem("update_lng");

    if (title != null && name != null) {
        $("#name").val(name);
    } else {
        name = $("#name").val(stas.add_name).val();
    }
    if (title != null && phone != null) {
        $("#phone").val(phone);
    } else {
        phone = $("#phone").val(stas.add_mobile_phone).val();
    }

    if (title == null) {
        $("#txt_area").val(stas.add_detail_address.replace(stas.add_street, ""));
    } else {
        $("#txt_area").val(title);
    }
    if (address == null) {
        $("#add_street").val(stas.add_street);
    } else {
        $("#add_street").val(address);
    }

    if (parseFloat(lng) > 0 && parseFloat(lat) > 0) {
        var json1 = {
            "add_longitude": parseFloat(lng),
            "add_latitude": parseFloat(lat)
        };
        var res = getUrl("/suyh/app/7/token/findDictByLatLng", json1, token);
        if (res != "") {
            var data = JSON.parse(res);
            if (data.mark == "0") {
                $("#province").val(data.obj.province_id);
                $("#city").val(data.obj.city_id);
                $("#region").val(data.obj.region_id);
            } else {
                $("#province").val(0);
                $("#city").val(0);
                $("#region").val(0);
            }
        }
    }

    /*======================================数据提交===================================*/
    $(".btn").click(function () {
        var name = $("#name").val();
        var phone = $("#phone").val();
        var txt = $("#txt_area").val();
        var Checked = $("#c").is(":checked");
        if (Checked == true) {
            var add_is_default = 0
        } else {
            var add_is_default = 1
        }
        if (name == null || name == "") {
            alert("姓名不能为空");
            return false;
        }
        if (phone == null || phone == "") {
            alert("手机号不能为空");
            return false;
        }
        if (address == null || address == "") {
            alert("详细地址不能为空");
            return false;
        }
        if (lng == null) {
            lng = stas.add_longitude;
        }
        if (lat == null) {
            lat = stas.add_latitude;
        }
        var json2 = {
            "add_id": parseInt(stas.id),
            "add_name": name,
            "add_detail_address": txt,
            "add_street": $("#add_street").val(),
            "add_province": $("#province").val(),
            "add_city": $("#city").val(),
            "add_region": $("#region").val(),
            "add_label": 0,
            "add_mobile_phone": phone,
            "add_longitude": lng,
            "add_latitude": lat,
            "add_is_default": add_is_default,
            "add_type": 1
        };
        var res = getUrl("/suyh/app/3/token/updateAddress", json2, token);
        if (res != "") {
            var data = JSON.parse(res);
            var datv = data.mark;
            var tip = data.tip;
            if (datv == 1) {
                alert(tip)
            } else {
                alert(tip);
                sessionStorage.removeItem("update_name");
                sessionStorage.removeItem("update_phone");
                sessionStorage.removeItem("update_title");
                sessionStorage.removeItem("update_address");
                sessionStorage.removeItem("update_lat");
                sessionStorage.removeItem("update_lng");
                window.location.href = "sender.html";
            }
        }
    })
})
function jump() {
    var storage = window.sessionStorage;
    storage.setItem('update_name', $("#name").val());
    storage.setItem('update_phone', $("#phone").val());
    window.location.href = "chooseMapAddress.html";
}
