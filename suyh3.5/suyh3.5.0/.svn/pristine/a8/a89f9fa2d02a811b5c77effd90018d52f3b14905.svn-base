$(document).ready(function() {
    var third_id = getUrlParam("third_id");
    var access_code = getUrlParam('code');
    var aa = getcookie("access_code");
    var token = getcookie("sra");
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
    if (access_code == null || access_code == "") {
        fun()
    } else {
        var tsHtThird = {
            third_id: third_id
        }
        var res = getUrl("/suyh/app/3/findTsHtThirdDto", tsHtThird, "");
        if(res != ""){
            var result = JSON.parse(res);
            if (result.mark == '0') {
                var appid = result.obj;
                if (access_code == null) {
                    var fromurl = "http://hotol.cn/wechat/html/coupon.html?third_id=2";
                    // var fromurl="http://qq291575087.6655.la/wechat/html/send.html?third_id=1";
                    var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + appid + '&redirect_uri=' + fromurl + '&response_type=code&scope=snsapi_userinfo&state=' + third_id + '#wechat_redirect';
                    window.location.href = url;
                } else {
                    var code_data = {
                        code: access_code,
                        third_id: third_id
                    }
                    var resp = getUrl("/suyh/app/3/obtainWXPersonal", code_data, "");
                    if(resp != ""){
                        var resultp = JSON.parse(resp);
                        var str = resultp.obj.token;
                        var openid = resultp.obj.openid;
                        if (str == "" || str == null) {
                            var storage = window.localStorage;
                            var open = {
                                openid: openid
                            };
                            var d = JSON.stringify(open);
                            storage.setItem('open', d);
                            window.location.href = "bund.html";
                        }
                        addcookie("third_id", third_id);
                        addcookie("sra", str);
                        token = str;
                        fun()
                    }
                }
            }
        }
    }
    function fun() {
        var m = 1
        var json = {
            "page_size": "10",
            "page_no": m,
            "state": 0
        };
        var res = getUrl("/suyh/app/3/token/findCouponByMember", json, token);
        if(res != ""){
            var data = JSON.parse(res);
            var arr = data.obj;
            var str = arr.items;
            if(str.length==0){
                $("#btn").html("暂无数据")
            }else
            if(str.length>0){
                $(".stan").hide()
            }
            if(str.length<10&&str.length>0){
                $("#btn").html("")
            }
            for (var i = 0; i < str.length; i++) {
                var date = new Date(str[i].cou_limit_time);
                Y = date.getFullYear() + '-';
                M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                D = date.getDate() + ' ';
                var timen = Y + M + D;
                var html = [];
                html += '<div class="box">';
                html += '<div class="top">';
                html += '<span class="left">' + '速邮汇.快递优惠券' + '</span>';
                html += '<span class="right">' + '￥' + '<i>' + str[i].cou_amount + '</i></span>';
                html += '<input id="input" type="hidden" value="' + str[i].cou_id + ',' + str[i].cou_name + ','
                    + str[i].cou_amount + ',' + str[i].cou_discount + ',' + str[i].cou_limit_amount + '">';
                html += '<span class="time">' + '有效期至' + '<em>' + timen + '</em></span>';
                html += '</div>';
                html += '<p class="bottom">限下城区寄件使用</p>';
                html += '</div>';
                $(".conten").append(html);
            }
        }
        $("#btn").click(function () {
            m = m + 1
            var json = {
                "page_size": "10",
                "page_no": m,
                "state": 0
            };
            var res = getUrl("/suyh/app/3/token/findCouponByMember", json, token);
            if(res != ""){
                var data = JSON.parse(res);
                var arr = data.obj;
                var str = arr.items;
                if (str.length < 10) {
                    $("#btn").html("")
                }
                for (var i = 0; i < str.length; i++) {
                    var date = new Date(str[i].cou_limit_time);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    var timen = Y + M + D;
                    var html = [];
                    html += '<div class="box">';
                    html += '<div class="top">';
                    html += '<span class="left">' + '速邮汇.快递优惠券' + '</span>';
                    html += '<span class="right">' + '￥' + '<i>' + str[i].cou_amount + '</i></span>';
                    html += '<input id="input" type="hidden" value="' + str[i].cou_id + '">';
                    html += '<input id="in" type="hidden" value="' + str[i].cou_name + '">';
                    html += '<input id="inpt" type="hidden" value="' + str[i].cou_amount + '">';
                    html += '<span class="time">' + '有效期至' + '<em>' + timen + '</em></span>';
                    html += '</div>';
                    html += '<p class="bottom">限下城区寄件使用</p>';
                    html += '</div>';
                    $(".conten").append(html);
                }
            }
        })
    }
})


