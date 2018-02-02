$(document).ready(function () {
    var token = getcookie("sra");

    var stas = eval('(' + sessionStorage.getItem('dtd') + ")");
    var name = $("#name").val(stas.add_name);
    var phone = $("#phone").val(stas.add_mobile_phone);
    var txt_area = $("#txt_area").val(stas.province + stas.city + stas.region);
    var address = $("#address").val(stas.add_detail_address);
    var area = $("#area").val(stas.add_province + ',' + stas.add_city + ',' + stas.add_region);
    var res = getUrl("/suyh/app/3/token/findProvincialCity", "", token);
    if (res != "") {
        var data = JSON.parse(res);
        var str = data.obj;
        var json = [];
        for (var i = 0; i < str.length; i++) {
            var arr = {"id": str[i].dict_id, "name": str[i].province_name, "child": []};
            var down = str[i].city_list;
            for (var j = 0; j < down.length; j++) {
                var urban = {"id": down[j].dict_id, "name": down[j].city_name, "child": []};
                var areaList = down[j].area_list;
                for (var f = 0; f < areaList.length; f++) {
                    var area = {"id": areaList[f].dict_id, "name": areaList[f].area_name};
                    urban.child.push(area);
                }
                arr.child.push(urban);
            }
            json.push(arr);

        }
        var selectArea = new MobileSelectArea();
        selectArea.init({
            trigger: '#txt_area',
            value: $('#txt_area').data('value'),
            data: json,
            eventName: 'click',
            position: "bottom"

        })
        $("#txt_area").focus(function () {
            document.activeElement.blur();
        });
    } else {
        var selectArea = new MobileSelectArea();
        selectArea.init({
            trigger: '#txt_area',
            value: $('#txt_area').data('value'),
            data: 'json.json',
            eventName: 'click',
            position: "bottom"
        })
        $("#txt_area").focus(function () {
            document.activeElement.blur();
        });
    }
    /*======================================省市区三级联动====================================*/
    /*======================================提交数据====================================*/
    $(".btn").click(function () {
        var name = $("#name").val();
        var phone = $("#phone").val();
        var address = $("#address").val();
        var txt = $("#txt_area").val();
        var strs = txt.split(" ");
        var area = $("#area").val();
        var dd = area.split(",");
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
        var json2 = {
            "add_id": parseInt(stas.id),
            "add_name": name,
            "add_detail_address": address,
            "add_province": parseInt(dd[0]),
            "add_city": parseInt(dd[1]),
            "add_region": parseInt(dd[2]),
            "add_label": 0,
            "add_mobile_phone": phone,
            "add_longitude": 0,
            "add_latitude": 0,
            "add_type": 2
        };
        var Checked = $("#c").is(":checked");
        if (Checked == true) {
            var res = getUrl("/suyh/app/3/token/saveAddress", json2, token);
            if (res != "") {
                var data = JSON.parse(res);
                var datv = data.mark;
                var tip = data.tip;
                if (datv == 1) {
                    alert(tip);
                } else {
                    alert(tip);
                    window.location.href = "recipients.html";
                }
            }

        } else {
            var res = getUrl("/suyh/app/3/token/updateAddress", json2, token);
            if (res != "") {
                var data = JSON.parse(res);
                var datv = data.mark;
                var tip = data.tip;
                if (datv == 1) {
                    alert(tip)
                } else {
                    alert(tip);
                    window.location.href = "recipients.html";
                }
            }
        }
    })
});
