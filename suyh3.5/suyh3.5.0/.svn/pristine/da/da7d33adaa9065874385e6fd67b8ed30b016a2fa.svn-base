/**
 * Created by Administrator on 2017-03-10.
 */

locationUtil = {
    init: function (idPrefix,idSuffix) {
        if ($("body").data("allProvinces")) {
            callback.apply(target);
            return;
        }
        var token=getcookie("token");
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            headers: getHeader(token),
            type: 'post',
            url: '/suyh/app/3/token/findProvincialCity',
            dataType: 'text',
            accpet: "application/json",
            success: function (data) {
                data = JSON.parse(Decrypt(data));
                if(data.mark=="0"){
                    $("body").data("allProvinces", data.obj);
                    $.each(data.obj, function (index, province) {
                        $("#" + idPrefix + "Province" + idSuffix).append("<option value='" + province.dict_id + "'>" + province.province_name + "</option>");
                    })
                    locationUtil.onChangeProvince(idPrefix,idSuffix);
                }else {
                    window.location.href="login.html";
                }
            },error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(textStatus);
            }
        })
    },

    onChangeProvince: function (idPrefix, idSuffix) {
        var provinceAdminCode = $("#" + idPrefix + "Province" + idSuffix).val();
        var province;
        $($("body").data("allProvinces")).each(function (index, iProvince) {
            if (iProvince.dict_id == provinceAdminCode) {
                province = iProvince;
            }
        });
        $("#" + idPrefix + "City" + idSuffix).empty();
        $(province.city_list).each(function (index, city) {
            $("#" + idPrefix + "City" + idSuffix).append("<option value='" + city.dict_id + "'>" + city.city_name + "</option>");
        });
        this.onChangeCity(idPrefix, idSuffix);
    },

    onChangeCity: function (idPrefix, idSuffix) {
        var provinceAdminCode = $("#" + idPrefix + "Province" + idSuffix).val();
        var province;
        $($("body").data("allProvinces")).each(function (index, iProvince) {
            if (iProvince.dict_id == provinceAdminCode) {
                province = iProvince;
            }
        });
        var cityAdminCode = $("#" + idPrefix + "City" + idSuffix).val();
        var city;
        $(province.city_list).each(function (index, iCity) {
            if (iCity.dict_id == cityAdminCode) {
                city = iCity;
            }
        });
        $("#" + idPrefix + "District" + idSuffix).empty();
        $(city.area_list).each(function (index, district) {
            $("#" + idPrefix + "District" + idSuffix).append("<option value='" + district.dict_id + "'>" + district.area_name + "</option>");
        });
    },

    setAddress: function (idPrefix, idSuffix, provinceId, cityId, regionId) {
        $("#" + idPrefix + "Province" + idSuffix).empty();
        var cityList=new Array;
        $($("body").data("allProvinces")).each(function (provinceIndex, province) {
            if(province.dict_id==provinceId){
                $("#" + idPrefix + "Province" + idSuffix).append("<option value='" + province.dict_id + "' selected>" + province.province_name + "</option>");
                $("#" + idPrefix + "City" + idSuffix).empty();
                $(province.city_list).each(function (index, city) {
                    if(city.dict_id==cityId){
                        $("#" + idPrefix + "City" + idSuffix).append("<option value='" + city.dict_id + "' selected>" + city.city_name + "</option>");
                        $("#" + idPrefix + "District" + idSuffix).empty();
                        $(city.area_list).each(function (index, region) {
                            if (region.dict_id == regionId) {
                                $("#" + idPrefix + "District" + idSuffix).append("<option value='" + region.dict_id + "' selected>" + region.area_name + "</option>");
                            }else {
                                $("#" + idPrefix + "District" + idSuffix).append("<option value='" + region.dict_id + "'>" + region.area_name + "</option>");
                            }
                        });
                    }else {
                        $("#" + idPrefix + "City" + idSuffix).append("<option value='" + city.dict_id + "'>" + city.city_name + "</option>");
                    }
                });
            }else {
                $("#" + idPrefix + "Province" + idSuffix).append("<option value='" + province.dict_id + "'>" + province.province_name + "</option>");
            }
        })
    }
}