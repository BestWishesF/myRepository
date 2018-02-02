/**
 * Created by Administrator on 2016-11-29.
 */

$(document).ready(function () {
    var token = getcookie("token");
    $("#login").click(function () {
        $("#loginError").hide();
        if ($("#j_username").val() == null || $("#j_username").val().trim() == "") {
            $("#loginError").html("用户名不能为空.").show();
            return false;
        }
        if ($("#j_password").val() == null || $("#j_password").val().trim() == "") {
            $("#loginError").html("密码不能为空.").show();
            return false;
        }
        var time = Date.parse(new Date());
        var hash = hex_md5(time + "hotol");
        var jsonData = {
            memb_phone: $("#j_username").val(),
            memb_password: $("#j_password").val(),
            push_token: ""
        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            headers: getHeader(token),
            type: 'post',
            data: Encrypt(JSON.stringify(jsonData)),
            url: '/suyh/app/5/webLogin',
            dataType: 'text',
            accpet: "application/json",
            success: function (data) {
                data = JSON.parse(Decrypt(data));
                if (data.mark == "0") {
                    addcookie("token", data.obj.token);
                    addcookie("memb_role", data.obj.memb_role);
                    addcookie("memb_discount", data.obj.memb_discount);
                    addcookie("memb_phone", $("#j_username").val());
                    window.location.href = "send.html";
                } else {
                    $("#loginError").html(data.tip).show();
                }
            }
        })
    })

    $("#hideTable").click(function () {
        $("#wjmm").toggle();
        $("#wjmm").find("input[type='text']").val("");
    })

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
        if($("#new_password").val()!=$("#new_confirmPassword").val()){
            $("#aginPwdError").html("两次密码输入不相同.").show();
            return false;
        }
        var time = Date.parse(new Date());
        var hash = hex_md5(time + "hotol");
        var jsonData = {
            memb_phone: $("#mobileNo_username").val(),
            memb_password: $("#new_password").val(),
            verification_code:$("#verifyNo").val()
        }
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            headers: getHeader(""),
            type: 'post',
            data: Encrypt(JSON.stringify(jsonData)),
            url: '/suyh/app/3/forgetPwd',
            dataType: 'text',
            accpet: "application/json",
            success: function (data) {
                data = JSON.parse(Decrypt(data));
                if (data.mark == "0") {
                    $("#wjmm").hide();
                    $("#wjmm").find("input[type='text']").val("");
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
            url: '/suyh/app/5/checkForgetPassCode',
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