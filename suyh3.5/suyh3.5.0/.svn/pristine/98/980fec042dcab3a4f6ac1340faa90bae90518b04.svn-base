/**
 * Created by Administrator on 2017-03-13.
 */
$(document).ready(function () {
    $("input[type='text']").val("");

    $("#PictureCheckCode").click(function () {
        $("#PictureCheckCode").attr("src", "../getKaptchaImage.jpeg?r=" + Math.random());
    })

    $("#mobileNo_username").keyup(function () {
        if($("#mobileNo_username").val().length==11){
            $("#imgCode").show();
            $("#mobileError").hide();
        }else {
            $("#imgCode").hide();
        }
    })

    $("#doSubmit_a").click(function () {
        if($("#mobileNo_password").val()!=$("#again_pwd").val()){
            $("#pwdAgainError").html("两次密码输入不相同!").show();
            return false;
        }
        if(!$("#agreement").get(0).checked){
            $("#checkedError").html("请先阅读用户使用协议!").show();
            return false;
        }
        var time = Date.parse(new Date());
        var hash = hex_md5(time + "hotol");
        var jsonData = {
            memb_phone: $("#mobileNo_username").val(),
            memb_password: $("#verifyNo").val(),
            verification_code:$("#mobileNo_password").val(),
            push_token:"",

        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            headers: getHeader(""),
            type: 'post',
            data: Encrypt(JSON.stringify(jsonData)),
            url: '/suyh/app/3/register',
            dataType: 'text',
            accpet: "application/json",
            success: function (data) {
                data = JSON.parse(Decrypt(data));
                if (data.mark == "0") {
                    window.location.href="login.html";
                } else {
                    $('#mobileError').html(data.tip).show();
                    $("#PictureCheckCode").click();
                }
            }
        })
    })
})

function getVerify() {
    $('#mobileError').hide();
    $("#imgCodeError").hide();
    if ($("#mobileNo_username").val() == null || $("#mobileNo_username").val().trim() == "") {
        $("#mobileError").html("手机号不能为空！").show();
        return false;
    }
    if($("#mobileNo_username").val().length!=11){
        $("#mobileError").html("手机号不正确！").show();
        return false;
    }
    if ($("#code").val().length == 4) {
        var time = Date.parse(new Date());
        var hash = hex_md5(time + "hotol");
        var jsonData = {
            memb_phone: $("#mobileNo_username").val(),
            img_verify: $("#code").val()
        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            headers: getHeader(""),
            type: 'post',
            data: Encrypt(JSON.stringify(jsonData)),
            url: '/suyh/app/5/checkRegCode',
            dataType: 'text',
            accpet: "application/json",
            success: function (data) {
                data = JSON.parse(Decrypt(data));
                if (data.mark == "0") {
                    _f_timeout(60);
                } else {
                    $('#mobileError').html(data.tip).show();
                    $("#PictureCheckCode").click();
                }
            }
        })
    } else {
        $("#imgCodeError").html("请输入正确的图片验证码！").show();
        return false;
    }

}

function _f_timeout(time) {
    $('#verifyNoTimer').html("<span id='verifyNoTimeMeter'>" + time + "</span>秒后重新获取").attr("onclick", "");
    timer = self.setInterval(this._addSec, 1000);
}

function _addSec() {
    var t = $('#verifyNoTimeMeter').html();
    if (t > 0) {
        $('#verifyNoTimeMeter').html(t - 1);
    } else {
        window.clearInterval(timer);
        $("#mobileNo").removeAttr("disabled");
        $('#verifyNoTimer').html('重新获取验证码').attr("onclick", "getVerify()");
    }
}