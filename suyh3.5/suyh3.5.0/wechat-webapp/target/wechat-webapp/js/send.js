$(document).ready(function () {
    var third_id = getUrlParam("third_id");
    addcookie("third_id", third_id);
    var access_code = getUrlParam('code');
    var aa = getcookie("access_code");
    var role = getcookie("role");
    var discount = getcookie("discount");
    if (role == 1 && discount == 1) {
        $("#total_").css("text-decoration", "none")
        $("#preefera").hide();
        $("#botom").hide()
    } else if (role == 1 && discount != 1) {
        $("#total_").css("text-decoration", "line-through")
    }
    if (role == 2) {
        $("#total_").css("text-decoration", "line-through")
    }

    if (aa != null && aa != "") {
        if (access_code == aa) {
            access_code = null;
            delCookie("access_code");
        } else {
            addcookie("access_code", access_code);
        }
    } else {
        addcookie("access_code", access_code);
    }
    var appid = '';
    var token = getcookie("sra");
    if (access_code == null || access_code == "") {
        fun()
    } else {
        var json1 = {
            third_id: third_id
        }
        var res = getUrl("/suyh/app/3/findTsHtThirdDto", json1, token);
        if (res != "") {
            var result = JSON.parse(res);
            if (result.mark == '0') {
                var appid = result.obj;
                if (access_code == null) {
                    var fromurl = "http://hotol.cn/wechat/html/send.html?third_id=2";
                    // var fromurl = "http://qq291575087.6655.la/wechat/html/send.html?third_id=1";
                    var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + appid + '&redirect_uri=' + fromurl + '&response_type=code&scope=snsapi_userinfo&state=' + third_id + '#wechat_redirect';
                    location.href = url;
                } else {
                    var json2 = {
                        code: access_code,
                        third_id: third_id
                    }
                    var res = getUrl("/suyh/app/3/obtainWXPersonal", json2, "");
                    if (res == "") {
                        var result = JSON.parse(res);
                        var str = result.obj.token;
                        var role = result.obj.memb_role
                        var discount = result.obj.memb_discount
                        var openid = result.obj.openid;
                        if (str == "" || str == null) {
                            var id = 1;
                            var storage = window.localStorage;
                            var open = {
                                openid: openid,
                                id: id
                            };
                            var d = JSON.stringify(open);
                            storage.setItem('open', d);
                            window.location.href = "bund.html";
                        }
                        addcookie("third_id", third_id);
                        addcookie("sra", str);
                        addcookie("role", role);
                        addcookie("discount", discount);
                        token = str;
                        fun()
                        if (role == 1 && discount == 1) {
                            $("#total_").css("text-decoration", "none")
                            $("#preefera").hide();
                            $("#botom").hide()
                        } else if (role == 1 && discount != 1) {
                            $("#total_").css("text-decoration", "line-through")
                        }
                        if (role == 2) {
                            $("#total_").css("text-decoration", "line-through")
                        }
                    }
                }
            }

        }
    }
    function fun() {
        /*================================================重量选择====================================================*/
        var minus = document.getElementById("minus");
        var number = document.getElementById("txt");
        var plus = document.getElementById("plus");
        minus.onclick = function () {
            if (number.value <= 1 + "公斤") {
                number.value = 1 + "公斤";
            } else {
                number.value = parseInt(number.value) - 1 + "公斤";
            }
        }
        plus.onclick = function () {
            number.value = parseInt(number.value) + 1 + "公斤";
        }
        $("#sq").click(function () {
            window.location.href = "adder.html";
        })
        $("#ss").click(function () {
            window.location.href = "add.html";
        })
        var strs = eval('(' + sessionStorage.getItem('datd') + ")");
        var te = eval('(' + sessionStorage.getItem('daty') + ")");
        var couriera = 0;
        /*================================================收件地址====================================================*/
        if (strs == "" || strs == null) {
        } else {
            var arr = [];
            arr += '<div class="top">';
            arr += '<p>';
            arr += '<span class="name">' + strs.add_name + '&nbsp;&nbsp;' + '</span>' + '<span class="name">' + strs.add_mobile_phone + '</span>' + '</br>';
            arr += '<span class="address">' + strs.province + strs.city + strs.region + strs.add_detail_address + '</span>';
            arr += '</p>';
            arr += '</div>';
            $(".ss").html(arr);
        }
        var dath = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var seperator3 = "月";
        var seperator4 = "日";
        var month = dath.getMonth() + 1;
        var strDate = dath.getDate();
        var str_data = dath.getDate() + 1;
        if (str_data == 29 && month == 2) {
            str_data = 1;
            month = 3;
        }
        if (str_data == 31 && (month == 4 || month == 6 || month == 9 || month == 11)) {
            str_data = 1;
            month = month + 1;
        }
        if (str_data == 32 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) {
            str_data = 1;
            month = month + 1;
        }
        var str_daty = dath.getDate() + 2;
        var str_date = dath.getHours() + 2;
        var str_datt = dath.getHours() + 1;
        var str_da = (dath.getMinutes() + 30) / 60;
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var cdate = month + seperator3 + strDate + seperator4;
        var ante = dath.getMinutes() * 60 + dath.getSeconds() + 1800;
        var theTime = parseInt(ante);// 秒
        var theTime1 = 0;// 分
        var theTime2 = 0;// 小时
        if (theTime > 60) {
            theTime1 = parseInt(theTime / 60);
            theTime = parseInt(theTime % 60);
            if (theTime1 > 60) {
                theTime2 = parseInt(theTime1 / 60);
                theTime1 = parseInt(theTime1 % 60);
            }
        }
        var result = "" + parseInt(theTime);
        if (theTime1 > 0) {
            result = "" + parseInt(theTime1) + ":" + result;
        }
        var resu = "30:00"
        /*================================================时间====================================================*/
        if (te == null || te == "") {
            var begun = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + dath.getHours() + seperator2 + dath.getMinutes()
                + seperator2 + dath.getSeconds();
            var beak = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + str_date + seperator2 + dath.getMinutes()
                + seperator2 + dath.getSeconds();
            var cash = 0;
        } else {
            var arr = te.date;
            var home = te.home;
            var cash = te.expense;
            if (home == "") {
                var str = [];
                str += '<i>' + te.date + '</i>';
                str += '<input id="input" type="hidden" vlue="' + te.date + '">';
                $(".date").html(str);
            } else {
                var str = [];
                str += '<i>' + te.date + te.home + '</i>';
                str += '<input  id="input" type="hidden" vlue="' + te.date + ',' + te.home + '">'
                $(".date").html(str);
            }
            if (arr == "2小时内取件") {
                var begun = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + dath.getHours() + seperator2 + dath.getMinutes()
                    + seperator2 + dath.getSeconds();
                var beak = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + str_date + seperator2 + dath.getMinutes()
                    + seperator2 + dath.getSeconds();
            } else if (arr == "30分钟内取件") {
                var begun = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + dath.getHours() + seperator2 + dath.getMinutes()
                    + seperator2 + dath.getSeconds();
                if (resu >= result) {
                    var beak = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + str_datt + seperator2 + result;
                } else {
                    var beak = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + dath.getHours() + seperator2 + result;
                }
            } else if (arr == "今天") {
                var star = home.split('-');
                var begun = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + star[0] + seperator2 + "00";
                +seperator2 + dath.getSeconds();
                var beak = dath.getFullYear() + seperator1 + month + seperator1 + strDate + " " + star[1] + seperator2 + "00";
                +seperator2 + dath.getSeconds();
            } else if (arr == "明天") {
                var star = home.split('-');
                var begun = dath.getFullYear() + seperator1 + month + seperator1 + str_data + " " + star[0] + seperator2 + "00";
                var beak = dath.getFullYear() + seperator1 + month + seperator1 + str_data + " " + star[1] + seperator2 + "00";
            } else if (arr == cdate) {
                var star = home.split('-');
                var begun = dath.getFullYear() + seperator1 + month + seperator1 + str_datya + " " + star[0] + seperator2 + dath.getMinutes()
                    + seperator2 + dath.getSeconds();
                var beak = dath.getFullYear() + seperator1 + month + seperator1 + str_daty + " " + star[1] + seperator2 + dath.getMinutes()
                    + seperator2 + dath.getSeconds();
            }
        }
        var cash = cash;
        begun = new Date(Date.parse(begun.replace(/-/g, "/")));
        begun = begun.getTime();
        beak = new Date(Date.parse(beak.replace(/-/g, "/")));
        beak = beak.getTime()
        /*================================================快递公司====================================================*/
        $(".courier").click(function () {
            var region = $("#regionsa").val();
            if (region == "" || region == null) {
                alert("请输入寄件地址")
                return false
            } else {
                var storage = window.sessionStorage;
                var region = {
                    region: region
                };
                var d = JSON.stringify(region);
                storage.setItem('region', d);
                window.location.href = "choose.html";
            }
        })
        var courier = eval('(' + sessionStorage.getItem('courier') + ")");
        if (courier == null) {
            var cou = 0;
        } else {
            var sar = [];
            sar += '<i>' + courier.name + '</i>';
            $(".courier").html(sar);
            var cou = parseInt(courier.id);
        }
        couriera = cou;
        // if (token != null && token != "") {
            function GetJson() {
                var json = {
                    "region_id": "0"
                };
                return json;
            }

            var res = getUrl("/suyh/app/3/token/findSendExpInfo", GetJson(), token);
            if (res != "") {
                var data = JSON.parse(res);
                var str = data.obj;
                var today = str.today_collect_time;
                var star = str.express_category;
                var goods_type = eval('(' + sessionStorage.getItem('goods') + ")");
                if(star.length > 0 && goods_type == null){
                    $(".goods_type").html(star[0].code_name);
                    $("#goods_type_id").val(star[0].dict_id);
                }
                if(goods_type != null){
                    $(".goods_type").html(goods_type.name);
                    $("#goods_type_id").val(goods_type.id);
                }
                var sty = str.express_ask;
                if (today == "" || today == null) {
                    var todad = 1
                }
                $(".list .lal").html('')
                for (var i = 0; i < star.length; i++) {
                    var html = [];
                    html += ' <label class="d' + star[i].dict_id + '" for="' + star[i].dict_id + '">';
                    html += '<input type="hidden" class="dale" value="' + star[i].dict_id + ',' + star[i].code_name + '">';
                    html += '<input type="hidden" id="adle" value="' + star[0].dict_id + ',' + star[0].code_name + '">';
                    html += '<input id="' + star[i].dict_id + '" type="radio" name="sele" value="' + star[i].code_name + ',' + star[i].dict_id + '"/>';
                    html += star[i].code_name;
                    html += '<input type="hidden" id="hiddens" value="' + todad + '">'
                    html += '</label>';
                    $(".list .lal").append(html);
                }
                var lael = eval('(' + sessionStorage.getItem('lael') + ")");
                $(".list .lal label").click(function () {
                    $(this).siblings().css({"background": "", "color": ""}).end()
                        .css({"background": "#61d1b7", "color": "#fff"});
                    var lael = $(this).children(".dale").val();
                    var la_el = lael.split(',')
                    var storage = window.sessionStorage;
                    var lael = {
                        id: la_el[0],
                        name: la_el[1]
                    }
                    var d = JSON.stringify(lael);
                    storage.setItem("lael", d);
                })
                if (lael == null || lael == "") {
                    $(".d" + star[0].dict_id).css({"background": "#61d1b7", "color": "#fff"})
                } else {
                    var lael = lael.id;
                    $(".d" + lael).css({"background": "#61d1b7", "color": "#fff"})
                }
                $(".needs .lal").html('');
                for (var i = 0; i < sty.length; i++) {
                    var html = [];
                    html += '<label for="dis' + sty[i].dict_id + '">';
                    html += '<input class="box" name="kemu" type="checkbox" id="dis' + sty[i].dict_id + '" value="' + sty[i].code_name + '" />';
                    html += '<span class="checkbox-label">' + sty[i].code_name + '</span>';
                    html += '</label>';
                    $(".needs .lal").append(html);
                    $(".checkbox-label").unbind('click').click(function () {
                        if ($(this).css("background-color") == 'rgb(68, 201, 170)') {
                            $(this).css({"background": "", "color": "#000"})
                        } else {
                            $(this).css({"background-color": "#44c9aa", "color": "#fff"})
                        }
                    });
                }


                if (role == 1 && discount == 1) {
                    $("#total_").css("text-decoration", "none")
                    $("#preefera").hide();
                    $("#botom").hide()
                } else if (role == 1 && discount != 1) {
                    $("#total_").css("text-decoration", "line-through")
                }
                if (role == 2) {
                    $("#total_").css("text-decoration", "line-through")
                }
                $("#code").click(function () {
                    var res = getUrl("/suyh/app/3/findTsHtThirdDto", tsHtThird, "");
                    if (res != "") {
                        var result = JSON.parse(res);
                        if (result.mark == '0') {
                            var appid = result.obj;
                            var json = {
                                "appid": appid,
                                "http_url": window.location.href
                            };
                            var res = getUrl("/suyh/app/3/obtainJsSdk", json, "");
                            if (res != "") {
                                var dresult = JSON.parse(res);
                                var packageValJson = dresult.obj;
                                wx.config({
                                    debug: false,
                                    appId: packageValJson.appid, // 必填，公众号的唯一标识
                                    timestamp: packageValJson.time_stamp, // 必填，生成签名的时间戳
                                    nonceStr: packageValJson.nonce_str, // 必填，生成签名的随机串
                                    signature: packageValJson.signature,// 必填，签名，见附录1
                                    jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

                                });
                                wx.checkJsApi({
                                    jsApiList: ['scanQRCode'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                                    success: function (res) {

                                    }
                                });
                                wx.ready(function () {
                                    wx.scanQRCode({
                                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                                        scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                                        success: function (res) {
                                            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                                            var resulta = result.split(',')
                                            if (resulta.length == 2) {
                                                $("#number").val(resulta[1])
                                            } else {
                                                $("#number").val(resulta[0])
                                            }
                                        }
                                    });
                                });
                                wx.error(function (res) {
                                });
                            }
                        }
                    }
                     /*================================================寄件地址====================================================*/

                })
                var send = eval('(' + sessionStorage.getItem('send') + ")");
                if (send == null || send == "") {
                    function GetJson() {
                        var json = {
                            "region_id": "0"
                        };
                        return json;
                    }

                    var defaul_ = data.obj.default_address;
                    if (defaul_ == null || defaul_ == "") {
                    } else {
                        var arr = [];
                        arr += '<div class="top">';
                        arr += '<p>';
                        arr += '<span class="name">' + defaul_.add_name + '&nbsp;&nbsp;' + '</span>' + '<span class="name">' + defaul_.add_mobile_phone + '</span>' + '</br>';
                        arr += '<span class="address">' + defaul_.province + defaul_.city + defaul_.region + '</span>';
                        arr += '<span class="address">' + defaul_.add_detail_address + '</span>';
                        arr += '<input type="hidden" id="namesa" name="name"  value="' + defaul_.add_name + '">'
                        arr += '<input type="hidden" id="phonesa" name="name"  value="' + defaul_.add_mobile_phone + '">'
                        arr += '<input type="hidden" id="provincesa" name="name"  value="' + defaul_.add_province + '">'
                        arr += '<input type="hidden" id="citysa" name="name"  value="' + defaul_.add_city + '">'
                        arr += '<input type="hidden" id="regionsa" name="name"  value="' + defaul_.add_region + '">'
                        arr += '<input type="hidden" id="addresssa" name="name"  value="' + defaul_.add_detail_address + '">'
                        arr += '<input type="hidden" id="add_idsa" name="name"  value="' + defaul_.add_id + '">'
                        arr += '<input type="hidden" id="longitudesa" name="name"  value="' + defaul_.add_longitude + '">'
                        arr += '<input type="hidden" id="latitudesa" name="name"  value="' + defaul_.add_latitude + '">'
                        arr += '</p>';
                        arr += '</div>';
                        $(".sq").html(arr);
                    }
                } else {
                    var arr = [];
                    arr += '<div class="top">';
                    arr += '<p>';
                    arr += '<span class="name">' + send.add_name + '&nbsp;&nbsp;' + '</span>' + '<span class="name">' + send.add_mobile_phone + '</span>' + '</br>';
                    arr += '<span class="address">' + send.province + send.city + send.region + '</span>';
                    arr += '<span class="address">' + send.add_detail_address + '</span>';
                    arr += '<input type="hidden" id="namesa" name="name"  value="' + send.add_name + '">'
                    arr += '<input type="hidden" id="phonesa" name="name"  value="' + send.add_mobile_phone + '">'
                    arr += '<input type="hidden" id="provincesa" name="name"  value="' + send.add_province + '">'
                    arr += '<input type="hidden" id="citysa" name="name"  value="' + send.add_city + '">'
                    arr += '<input type="hidden" id="regionsa" name="name"  value="' + send.add_region + '">'
                    arr += '<input type="hidden" id="addresssa" name="name"  value="' + send.add_detail_address + '">'
                    arr += '<input type="hidden" id="add_idsa" name="name"  value="' + send.id + '">'
                    arr += '<input type="hidden" id="longitudesa" name="name"  value="' + send.add_longitude + '">'
                    arr += '<input type="hidden" id="latitudesa" name="name"  value="' + send.add_latitude + '">'
                    arr += '</p>';
                    arr += '</div>';
                    $(".sq").html(arr);
                }

                /*================================================预估价格====================================================*/
                var json3 = {
                    "region_id": "0"
                };

                var de_faul_ = data.obj;
                var str_s = de_faul_.default_address;
                if (str_s == null) {
                } else {
                    var send_province = parseInt(str_s.add_province)
                }
                if (send == "" || send == null) {
                } else {
                    var send_province = parseInt(send.add_province)
                }
                if (courier == "" || courier == null) {
                } else {
                    var couner = parseInt(courier.id)
                }
                if (strs == "" || strs == null) {
                    var Provinces = 0;
                } else {
                    var Provinces = strs.add_province
                }
                $(".button").click(function () {
                    defaul();
                })
                defaul()
                function defaul() {
                    var txt = $("#txt").val();
                    var tat = txt.split("公斤")[0];
                    var rev_p = 0;
                    if (strs != null && strs != "") {
                        rev_p = strs.add_province;
                    }

                    function tJsonData() {
                        var add_province = parseInt(Provinces)
                        var exp_ord_clt_height = parseInt(tat)
                        var exp_ord_gratuity = cash
                        var collects = [];
                        var collect = {
                            "exp_ord_clt_height": exp_ord_clt_height,
                            "add_province": parseInt(rev_p)
                        };
                        collects.push(collect)
                        var json = {
                            send_province_id: send_province,
                            exp_ord_gratuity: exp_ord_gratuity,
                            express_id: couner,
                            collects: collects
                        };
                        return json;
                    }

                    var res = getUrl("/suyh/app/3/token/membCalculationPrice", tJsonData(), token);
                    if (res != "") {
                        var data = JSON.parse(res);
                        if(data.mark == "0"){
                            var stra = data.obj;
                            $("#total").html(stra.total_price)
                            $("#prefer").html(stra.discount_price)
                        }
                    }
                }


                /*================================================提交数据====================================================*/
                $("#btn").click(function () {
                    var name = $("#namesa").val();
                    var phone = $("#phonesa").val();
                    var province = $("#provincesa").val();
                    var city = $("#citysa").val();
                    var region = $("#regionsa").val();
                    var address = $("#addresssa").val();
                    var add_id = $("#add_idsa").val();
                    var longitude = $("#longitudesa").val();
                    var latitude = $("#latitudesa").val();
                    var datae = $("#hiddens").val()
                    var txt = $("#txt").val();
                    var tat = txt.split("公斤");


                    var lael = eval('(' + sessionStorage.getItem('lael') + ")");
                    var timeval = $("#timean").val();
                    if ($("#timean").val() == "2小时取件") {

                    }
                    var art = new Array();
                    $('input[name="kemu"]:checked').each(function () {
                        var sfruit = $(this).val();
                        art.push(sfruit);
                    });
                    var ht_number = $("#number").val()
                    if (ht_number == null) {
                        ht_number = "";
                    }
                    var stat = art.join(" ")
                    if (name == "" || name == null) {
                        alert("请输入寄件地址")
                        return false
                    }
                    if (strs == "" || strs == null) {
                        alert("请输入收件地址")
                        return false
                    }
                    if (datae == 1 && te == null || te == "") {
                        alert("代理人已经下班，请选择明天的时间")
                        return false
                    }
                    if (couriera == "" || couriera == null || couriera == 0) {
                        alert("请选择快递公司")
                        return false
                    }
                    function GetJsonData() {
                        var express_order = {
                            "add_id": parseInt(add_id),
                            "add_name": name,
                            "add_mobile_phone": phone,
                            "add_id_number": "",
                            "add_detail_address": address,
                            "add_province": parseInt(province),
                            "add_city": parseInt(city),
                            "add_region": parseInt(region),
                            "add_longitude": parseFloat(longitude),
                            "add_latitude": parseFloat(latitude),
                            "express_id": couriera,
                            "exp_ord_demand": stat,
                            "door_start_time": begun,
                            "door_end_time": beak,
                            "exp_ord_gratuity": $("#goods_type_id").val()
                        }
                        var express_order_collect = {
                            "exp_ord_category": $(".goods_type").html(),
                            "exp_ord_clt_height": parseInt(tat[0]),
                            "add_id": parseInt(strs.id),
                            "add_name": strs.add_name,
                            "add_mobile_phone": strs.add_mobile_phone,
                            "add_detail_address": strs.add_detail_address,
                            "add_province": parseInt(strs.add_province),
                            "add_city": parseInt(strs.add_city),
                            "add_region": parseInt(strs.add_region),
                            "category_id": $("#goods_type_id").val(),
                            "ht_number": ht_number
                        }
                        var json = {express_order: express_order, express_order_collect: express_order_collect};
                        return json;
                    }
                    var res = getUrl("/suyh/app/3/token/sendOneExpress", GetJsonData(), token);
                    if (res != "") {
                        var data = JSON.parse(res);
                        var tip = data.tip;
                        var dta = data.mark;
                        if (dta == 0) {
                            alert(tip)
                            sessionStorage.clear();
                            window.location.href = "order.html";
                        } else {
                            alert(tip)
                        }

                    }
                })
            }
        // }
    }
    })

$(".goods_type").click(function () {
    window.location.href = "goodsType.html";
})