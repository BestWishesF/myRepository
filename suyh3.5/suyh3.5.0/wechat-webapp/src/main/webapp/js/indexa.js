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
    var tsHtThird = {
        third_id: third_id
    }
    var res = getUrl("/suyh/app/3/findTsHtThirdDto", tsHtThird, "");
    if(res != ""){
        var result = JSON.parse(res);
        if (result.mark == '0') {
            var appid = result.obj;
            if (access_code == null) {
                var fromurl = "http://hotol.cn/wechat/html/indea.html?third_id=2";
                // var fromurl="http://qq291575087.6655.la/wechat/html/indea.html?third_id=1";
                var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + appid + '&redirect_uri=' + fromurl + '&response_type=code&scope=snsapi_userinfo&state=' + third_id + '#wechat_redirect';
                location.href = url;
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
    function fun() {
        var res = getUrl("/suyh/app/3/token/findGreenMailByMemb", "", token);
        if(res != ""){
            var data = JSON.parse(res);
            var mark = data.mark;
            if (mark == 1) {
                $(".show").hide();
                $(".no").show();
            }
            if(mark==0){
                var obj = data.obj;
                var green_mail_id = obj.green_mail_id;
            }
            if(mark==2){
                window.location.href = "share.html";
            }
            $("#btn").click(function () {
                var name = $("#name").val();
                var phone = $("#phone").val();
                var address = $("#address").val();
                if (name == null || name == "") {
                    alert("姓名不能为空");
                    return false;
                }
                if (phone == null || phone == "") {
                    alert("手机号不能为空");
                    return false;
                }
                if(!(/^1[34578]\d{9}$/.test(phone))){
                    alert("手机号码不正确");
                    return false;
                }
                if (address == null || address == "") {
                    alert("详细地址不能为空");
                    return false;
                }
                $("#btn").unbind()
                var json = {
                    "green_mail_id": green_mail_id,
                    "name": name,
                    "address": address,
                    "phone": phone
                };
                var resp = getUrl("/suyh/app/3/token/saveGreenAddress", json, token);
                if(resp != ""){
                    var datap = JSON.parse(resp);
                    var datv = datap.mark;
                    var tip = datap.tip;
                    if (datv == 1) {
                        alert(tip)
                        return false
                    }
                    window.location.href = "share.html";
                }
            })
        }
    }
});