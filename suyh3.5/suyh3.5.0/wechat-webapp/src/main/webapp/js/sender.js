$(document).ready(function () {
    var h = 1;
    var json1 = {
        "page_size": "10",
        "page_no": h,
        "type": 1
    };
    var token = getcookie("sra");
    var res = getUrl("/suyh/app/3/token/findAddress", json1, token);
    if (res != "") {
        var data = JSON.parse(res);
        var str = data.obj;
        var arr = str.items;
        if (arr.length == 0) {
            $("#but").html("暂无数据")
        } else if (arr.length > 0) {
            $(".stan").hide()
        }
        if (arr.length < 10 && arr.length > 0) {
            $("#but").html("")
        }
        var lis = [];
        for (var i = 0; i < arr.length; i++) {
            var radione = '<input id="c' + arr[i].add_id + '" type="radio" name="opt" class="input" value="' + arr[i].add_id + '" />' +
                '<label for="c' + arr[i].add_id + '" class="radio">' + '<input id="set" type="hidden" value="' + arr[i].add_id + '">' + '<span class="set">设置默认地址</span>' + '</label>'
            if (arr[i].add_is_default == 0) {
                var radione = '<input id="c' + arr[i].add_id + '" type="radio" checked="checked" name="opt" class="input" value="' + arr[i].add_id + '" />' +
                    '<label for="c' + arr[i].add_id + '" class="radio"><span class="set">设置默认地址</span>' + '</label>'
            } else {

            }
            lis = '<div class="box">' +
                '<div class="address back">' +
                '<div class="span">' +
                '<span class="left">' + arr[i].add_name + '</span>' +
                '<span class="right">' + arr[i].add_mobile_phone + '</span>' + '</div>' +
                '<input type="hidden" class="name" name="name"  value="' + arr[i].add_name + '">' +
                '<input type="hidden" class="phone" name="name"  value="' + arr[i].add_mobile_phone + '">' +
                '<input type="hidden" class="province" name="name"  value="' + arr[i].province + '">' +
                '<input type="hidden" class="city" name="name"  value="' + arr[i].city + '">' +
                '<input type="hidden" class="region" name="name"  value="' + arr[i].region + '">' +
                '<input type="hidden" class="address" name="name"  value="' + arr[i].add_detail_address + '">' +
                '<input type="hidden" class="street" name="name"  value="' + arr[i].add_street + '">' +
                '<input type="hidden" class="add_id" name="name"  value="' + arr[i].add_id + '">' +
                '<input type="hidden" class="add_province" name="name"  value="' + arr[i].add_province + '">' +
                '<input type="hidden" class="add_city" name="name"  value="' + arr[i].add_city + '">' +
                '<input type="hidden" class="add_region" name="name"  value="' + arr[i].add_region + '">' +
                '<input type="hidden" class="longitude" name="name"  value="' + arr[i].add_longitude + '">' +
                '<input type="hidden" class="latitude" name="name"  value="' + arr[i].add_latitude + '">' +
                '<p>' + arr[i].province + arr[i].city + arr[i].region + arr[i].add_detail_address + '</p>' +
                '</div>' + '<div class="Defaulted">' + radione + '<span class="edit">' + '编辑' +
                '<input type="hidden" class="names" name="name"  value="' + arr[i].add_name + '">' +
                '<input type="hidden" class="phones" name="name"  value="' + arr[i].add_mobile_phone + '">' +
                '<input type="hidden" class="provinces" name="name"  value="' + arr[i].province + '">' +
                '<input type="hidden" class="citys" name="name"  value="' + arr[i].city + '">' +
                '<input type="hidden" class="regions" name="name"  value="' + arr[i].region + '">' +
                '<input type="hidden" class="addresss" name="name"  value="' + arr[i].add_detail_address + '">' +
                '<input type="hidden" class="street" name="name"  value="' + arr[i].add_street + '">' +
                '<input type="hidden" class="add_ids" name="name"  value="' + arr[i].add_id + '">' +
                '<input type="hidden" class="add_provinces" name="name"  value="' + arr[i].add_province + '">' +
                '<input type="hidden" class="add_citys" name="name"  value="' + arr[i].add_city + '">' +
                '<input type="hidden" class="add_regions" name="name"  value="' + arr[i].add_region + '">' +
                '<input type="hidden" class="longitudes" name="name"  value="' + arr[i].add_longitude + '">' +
                '<input type="hidden" class="latitudes" name="name"  value="' + arr[i].add_latitude + '">' + '</span>' + '<span class="remo">' +
                '删除' + '<input type="hidden" class="it" name="name"  value="' + arr[i].add_id + '"></span>' + '</div>' + '</div>';
            $(".list").append(lis);
        }
        $(".radio").click(function () {
            var valuer = $(this).children("#set").val();
            var jsonp = {add_id: parseInt(valuer), add_type: 1}
            var res = getUrl("/suyh/app/3/token/setDefault", "jsonp", token);
            if (res != "") {
                var data = JSON.parse(res);
                alert(data.tip);
            }

        })
        $(".remo").click(function () {
            var cata = $(this).children(".it").val();
            var jsod = {
                add_id: parseInt(cata)
            };
            var res = getUrl("/suyh/app/3/token/deleteAddress", jsod, token);
            if (res != "") {
                window.location.href = "sender.html";
            }
        })
        $(".edit").click(function () {
            var names = $(this).children(".names").val();
            var phones = $(this).children(".phones").val();
            var provinces = $(this).children(".provinces").val();
            var citys = $(this).children(".citys").val();
            var regions = $(this).children(".regions").val();
            var addresss = $(this).children(".addresss").val();
            var street = $(this).children(".street").val();
            var add_ids = $(this).children(".add_ids").val();
            var add_provinces = $(this).children(".add_provinces").val();
            var add_citys = $(this).children(".add_citys").val();
            var add_regions = $(this).children(".add_regions").val();
            var longitudes = $(this).children(".longitudes").val();
            var latitudes = $(this).children(".latitudes").val();
            var storage = window.sessionStorage;
            var td = {
                id: add_ids,
                add_name: names,
                add_mobile_phone: phones,
                province: provinces,
                add_province: add_provinces,
                city: citys,
                add_city: add_citys,
                region: regions,
                add_region: add_regions,
                add_detail_address: addresss,
                add_street: street,
                add_longitude: longitudes,
                add_latitude: latitudes
            };
            var d = JSON.stringify(td);
            storage.setItem('td', d);
            window.location.href = "sent.html";
        })
        $(".back").click(function () {
            var name = $(this).children(".name").val();
            var phone = $(this).children(".phone").val();
            var province = $(this).children(".province").val();
            var city = $(this).children(".city").val();
            var region = $(this).children(".region").val();
            var address = $(this).children(".address").val();
            var add_id = $(this).children(".add_id").val();
            var add_province = $(this).children(".add_province").val();
            var add_city = $(this).children(".add_city").val();
            var add_region = $(this).children(".add_region").val();
            var longitude = $(this).children(".longitude").val();
            var latitude = $(this).children(".latitude").val();
            var storage = window.sessionStorage;
            var send = {
                id: add_id,
                add_name: name,
                add_mobile_phone: phone,
                province: province,
                add_province: add_province,
                city: city,
                add_city: add_city,
                region: region,
                add_region: add_region,
                add_detail_address: address,
                add_longitude: longitude,
                add_latitude: latitude
            };
            var d = JSON.stringify(send);
            storage.setItem('send', d);
            window.location.href = "send.html";
        });
    }

    $("#but").click(function () {
        h = h + 1
        var json3 = {
            "page_size": "10",
            "page_no": h,
            "type": 1
        }
        var res = getUrl("/suyh/app/3/token/findAddress", json3, token);
        if (res != "") {
            var data = JSON.parse(res);
            var str = data.obj;
            var atr = str.total_pages
            var arr = str.items;
            var lis = [];
            for (var i = 0; i < arr.length; i++) {
                var radione = '<input id="c' + arr[i].add_id + '" type="radio" name="opt" class="input" value="' + arr[i].add_id + '" />' +
                    '<label for="c' + arr[i].add_id + '" class="radio">' + '<input id="set" type="hidden" value="' + arr[i].add_id + '">' + '<span class="set">设置默认地址</span>' + '</label>'
                if (arr[i].add_is_default == 0) {
                    var radione = '<input id="c' + arr[i].add_id + '" type="radio" checked="checked" name="opt" class="input" value="' + arr[i].add_id + '" />' +
                        '<label for="c' + arr[i].add_id + '" class="radio"><span class="set">设置默认地址</span>' + '</label>'
                }
                lis = '<div class="box">' +
                    '<div class="address back">' +
                    '<div class="span">' +
                    '<span class="left">' + arr[i].add_name + '</span>' +
                    '<span class="right">' + arr[i].add_mobile_phone + '</span>' + '</div>' +
                    '<input type="hidden" class="name" name="name"  value="' + arr[i].add_name + '">' +
                    '<input type="hidden" class="phone" name="name"  value="' + arr[i].add_mobile_phone + '">' +
                    '<input type="hidden" class="province" name="name"  value="' + arr[i].province + '">' +
                    '<input type="hidden" class="city" name="name"  value="' + arr[i].city + '">' +
                    '<input type="hidden" class="region" name="name"  value="' + arr[i].region + '">' +
                    '<input type="hidden" class="address" name="name"  value="' + arr[i].add_detail_address + '">' +
                    '<input type="hidden" class="add_id" name="name"  value="' + arr[i].add_id + '">' +
                    '<input type="hidden" class="add_province" name="name"  value="' + arr[i].add_province + '">' +
                    '<input type="hidden" class="add_city" name="name"  value="' + arr[i].add_city + '">' +
                    '<input type="hidden" class="add_region" name="name"  value="' + arr[i].add_region + '">' +
                    '<input type="hidden" class="longitude" name="name"  value="' + arr[i].add_longitude + '">' +
                    '<input type="hidden" class="latitude" name="name"  value="' + arr[i].add_latitude + '">' +
                    '<p>' + arr[i].province + arr[i].city + arr[i].region + arr[i].add_detail_address + '</p>' +
                    '</div>' + '<div class="Defaulted">' + radione + '<span class="edit">' + '编辑' +
                    '<input type="hidden" class="names" name="name"  value="' + arr[i].add_name + '">' +
                    '<input type="hidden" class="phones" name="name"  value="' + arr[i].add_mobile_phone + '">' +
                    '<input type="hidden" class="provinces" name="name"  value="' + arr[i].province + '">' +
                    '<input type="hidden" class="citys" name="name"  value="' + arr[i].city + '">' +
                    '<input type="hidden" class="regions" name="name"  value="' + arr[i].region + '">' +
                    '<input type="hidden" class="addresss" name="name"  value="' + arr[i].add_detail_address + '">' +
                    '<input type="hidden" class="add_ids" name="name"  value="' + arr[i].add_id + '">' +
                    '<input type="hidden" class="add_provinces" name="name"  value="' + arr[i].add_province + '">' +
                    '<input type="hidden" class="add_citys" name="name"  value="' + arr[i].add_city + '">' +
                    '<input type="hidden" class="add_regions" name="name"  value="' + arr[i].add_region + '">' +
                    '<input type="hidden" class="longitudes" name="name"  value="' + arr[i].add_longitude + '">' +
                    '<input type="hidden" class="latitudes" name="name"  value="' + arr[i].add_latitude + '">' + '</span>' + '<span class="remo">' +
                    '删除' + '<input type="hidden" class="it" name="name"  value="' + arr[i].add_id + '"></span>' + '</div>' + '</div>';
                $(".list").append(lis);
            }
            $(".radio").click(function () {
                var valuer = $(this).children("#set").val();
                var json4 = {add_id: parseInt(valuer), add_type: 1}
                var res = getUrl("/suyh/app/3/token/setDefault", json4, token);
                if (res != "") {
                    var data = JSON.parse(res);
                    alert(data.tip)
                }
            })

        }
        $(".remo").click(function () {
            var cata = $(this).children(".it").val();
            var json5 = {
                add_id: parseInt(cata)
            };
            var res = getUrl("/suyh/app/3/token/deleteAddress", json5, token);
            if (res != "") {
                window.location.href = "sender.html";
            }

        })
        $(".edit").click(function () {
            var names = $(this).children(".names").val();
            var phones = $(this).children(".phones").val();
            var provinces = $(this).children(".provinces").val();
            var citys = $(this).children(".citys").val();
            var regions = $(this).children(".regions").val();
            var addresss = $(this).children(".addresss").val();
            var add_ids = $(this).children(".add_ids").val();
            var add_provinces = $(this).children(".add_provinces").val();
            var add_citys = $(this).children(".add_citys").val();
            var add_regions = $(this).children(".add_regions").val();
            var longitudes = $(this).children(".longitudes").val();
            var latitudes = $(this).children(".latitudes").val();
            var storage = window.sessionStorage;
            var td = {
                id: add_ids,
                add_name: names,
                add_mobile_phone: phones,
                province: provinces,
                add_province: add_provinces,
                city: citys,
                add_city: add_citys,
                region: regions,
                add_region: add_regions,
                add_detail_address: addresss,
                add_longitude: longitudes,
                add_latitude: latitudes
            };
            var d = JSON.stringify(td);
            storage.setItem('td', d);
            window.location.href = "sent.html";
        })
        $(".back").click(function () {
            var name = $(this).children(".name").val();
            var phone = $(this).children(".phone").val();
            var province = $(this).children(".province").val();
            var city = $(this).children(".city").val();
            var region = $(this).children(".region").val();
            var address = $(this).children(".address").val();
            var add_id = $(this).children(".add_id").val();
            var add_province = $(this).children(".add_province").val();
            var add_city = $(this).children(".add_city").val();
            var add_region = $(this).children(".add_region").val();
            var longitude = $(this).children(".longitude").val();
            var latitude = $(this).children(".latitude").val();
            var storage = window.sessionStorage;
            var send = {
                id: add_id,
                add_name: name,
                add_mobile_phone: phone,
                province: province,
                add_province: add_province,
                city: city,
                add_city: add_city,
                region: region,
                add_region: add_region,
                add_detail_address: address,
                add_longitude: longitude,
                add_latitude: latitude
            };
            var d = JSON.stringify(send);
            storage.setItem('send', d);
            window.location.href = "send.html";
        });
        if (arr.length < 10) {
            $("#but").html("")
        }
    })
})
