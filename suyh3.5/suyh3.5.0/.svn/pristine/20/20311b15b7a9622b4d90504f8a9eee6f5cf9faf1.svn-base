$(document).ready(function () {
    var h = 1;
    var json1 = {
        "page_size": "10",
        "page_no": h,
        "type": 2
    };
    var token = getcookie("sra");
    var res = getUrl("/suyh/app/3/token/findAddress", json1, token);
    if (res != "") {
        var result = JSON.parse(res);
        if (result.mark == "0") {
            var str = result.obj;
            var arr = str.items;
            if (arr.length == 0) {
                $("#but").html("加载更多")
            } else if (arr.length > 0) {
                $(".stan").hide()
            }
            var lis = [];
            for (var i = 0; i < arr.length; i++) {
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
                    '</div>' +
                    '<div class="Defaulted">' +
                    '<span class="radio">' + '</span>' + '<span class="redss readss">' + '编辑' +
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
            $("#but").click(function () {
                h = h + 1
                var json2 = {
                    "page_size": "10",
                    "page_no": h,
                    "type": 2
                };
                var res = getUrl("/suyh/app/3/token/findAddress", json2, token);
                if (res != "") {
                    var result = JSON.parse(res);
                    if (result.mark == "0") {
                        var str = result.obj;
                        var arr = str.items;
                        if (arr.length == 0) {
                            $("#but").html("加载更多")
                        } else if (arr.length > 0) {
                            $(".stan").hide()
                        }
                        var lis = [];
                        for (var i = 0; i < arr.length; i++) {
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
                                '</div>' +
                                '<div class="Defaulted">' +
                                '<span class="radio">' + '</span>' + '<span class="redss readss">' + '编辑' +
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

                    }
                }
            })

        }
    }
    function searchAddress() {
        var h = 1;
        var json3 = {
            "page_size": "10",
            "page_no": h,
            "query_criteria": $("#search").val(),
        };
        var res = getUrl("/suyh/app/7/token/findMembAddByBean", json3, token);
        if (res != "") {
            var result = JSON.parse(res);
            if (result.mark == "0") {
                $(".list").html("")
                var str = result.obj;
                var arr = str.items;
                var lis = [];
                for (var i = 0; i < arr.length; i++) {
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
                        '</div>' +
                        '<div class="Defaulted">' +
                        '<span class="radio">' + '</span>' + '<span class="redss readss">' + '编辑' +
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
            }
        }
    };

    $('#search').bind('input propertychange', function () {
        searchAddress();
    });

})
