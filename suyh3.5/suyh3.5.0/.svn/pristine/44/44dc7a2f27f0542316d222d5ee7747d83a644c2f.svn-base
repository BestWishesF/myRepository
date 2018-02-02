/**
 * Created by Administrator on 2017-03-23.
 */
$(document).ready(function () {
    var token = getcookie("token");
    if(token==null||token==""){
        $("#profileRegion").hide();
        $("#a1").html("");
        $("#loginLink").show();
        $("#regLink").show();
        $("#send").attr("href","login.html");
    }else {
        $("#loginLink").hide();
        $("#regLink").hide();
        $("#a1").html(getcookie("memb_phone"));
        var memb_phone=getcookie("memb_phone");
        if(memb_phone==null||memb_phone==""){
            $("#a1").html(" ");
        }else {
            $("#a1").html(memb_phone);
        }
        $("#profileRegion").show();
        $("#send").attr("href","send.html");

        $("#logoutLink").click(function () {
            var time = Date.parse(new Date());
            var hash = hex_md5(time +"hotol");
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                headers: getHeader(token),
                type: 'post',
                url: '/suyh/app/3/token/exitLogin',
                dataType: 'text',
                accpet: "application/json",
                success: function (data) {
                    data = JSON.parse(Decrypt(data));
                    if (data.mark == "0") {
                        delCookie("token");
                        window.location.href="login.html";
                    } else {
                        alert(data.tip);
                    }
                }
            })
        })
    }
})