$(document).ready(function () {
    var lon=0;
    var lat=0;
    var third_id=getcookie("third_id");
    var tsHtThird = {
        third_id: third_id
    };
    var res = getUrl("/suyh/app/3/findTsHtThirdDto", tsHtThird, "");
    if(res != ""){
        var result = JSON.parse(res);
        if (result.mark == '0') {
            var appid = result.obj;
            var json = {
                "appid": appid,
                "http_url": window.location.href
            };
            var resp = getUrl("/suyh/app/3/obtainJsSdk", json, "");
            if(resp != ""){
                var dresult = JSON.parse(resp);
                var packageValJson = dresult.obj;
                wx.config({
                    debug: false,
                    appId: packageValJson.appid, // 必填，公众号的唯一标识
                    timestamp: packageValJson.time_stamp, // 必填，生成签名的时间戳
                    nonceStr: packageValJson.nonce_str, // 必填，生成签名的随机串
                    signature: packageValJson.signature,// 必填，签名，见附录1
                    jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

                });
                wx.checkJsApi({
                    jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                    success: function (res) {

                    }
                });
                wx.ready(function () {
                    wx.getLocation({
                        type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                        success: function (res) {
                            var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                            var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                            var speed = res.speed; // 速度，以米/每秒计
                            var accuracy = res.accuracy; // 位置精度

                            $.ajax({
                                url: 'http://api.map.baidu.com/geoconv/v1/?coords=' + longitude + ',' +latitude + '&from=1&to=5&ak=IstZcZGxIWMgybcvDntmTbvM',
                                type: "get",
                                dataType: "jsonp",
                                jsonp: "callback",
                                success: function (data) {
                                    if(data.status == 0){
                                        var location = data.result[0];
                                        lon = location.x;
                                        lat = location.y;
                                    }
                                }
                            });
                        }
                    });
                });
                wx.error(function (res) {

                });
            }
        }
    }

    $("#btn").click(function() {
        var value = $("#phone").val();
        var openid = eval('(' + localStorage.getItem('open') + ")");
        var open=openid.openid;
        var code = $("#name").val();
        if(value==""||value==null){
            alert("请输入手机号")
            return false
        }
        if (value != "") {
            if ($("#phone").val().length < 11 || $("#phone").val().length > 11) {
                alert("你输入的手机号有误")
                return false;
            }
            if ($("#name").val() == "") {
                alert("请输入验证码")
                return false;
            }
        }
        var storage=window.sessionStorage;
        var phone={
            "phone":value
        };
        var d=JSON.stringify(phone);
        storage.setItem("phone",d);
        var date = {
            openid: open,
            phone: value,
            verification_code:code,
            longitude:lon,
            latitude:lat,
            device_number:"123"  //用户设备号需要获取
        };
        var res = getUrl("/suyh/app/3/bindingPhone", date, "");
        if(res != ""){
            var data = JSON.parse(res);
            if(data.mark=="0"){
                var sra = data.obj;
                addcookie("sra",sra);
                // alert("绑定成功");
                window.location.href = "Sharing.html";
            }else {
                alert(data.tip)
            }
        }
    })
})
var num = 60;

function getCode() {
    $("#code").attr("onclick","");
    var value = $("#phone").val();
    var json = {phoneno: value}
    if (value != "") {
        if ($("#phone").val() == "") {
            $("#code").attr("onclick","getCode()");
            alert("请输入手机号")
            return false;
        }
        if ($("#phone").val().length < 11 || $("#phone").val().length > 11) {
            $("#code").attr("onclick","getCode()");
            alert("你输入的手机号有误")
            return false;
        }
    } else {
        $("#code").attr("onclick","getCode()");
        alert("请输入手机号");
        return false
    }
    var res = getUrl("/suyh/app/3/sendWeChatBinding", json, "");
    if(res != ""){
        var data = JSON.parse(res);
        if(data.mark=="0"){
            var coder=$("#code").text();
            if(coder=="重发"||coder=="获取验证码"){
                timeout()
            }
        }else {
            $("#code").attr("onclick","getCode()");
            alert(data.tip);
        }
    }
}

function timeout() {
    if (num == 0) {
        num=60
        $("#code").html("重发").attr("onclick","getCode()");
        $("#code").css("background","")
    }else{
        num--;
        setTimeout(timeout, 1000);
        $("#code").html("重发" + "("+num+")");
        $("#code").css("background","#ccc")
    }
}