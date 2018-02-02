/**
 * Created by Administrator on 2016-11-29.
 */

function doLogin() {
    var userName=$("#userName").val();
    var userPass=$("#userPass").val();
    var user={
        mobileNo:userName,
        password:userPass
    }
    $.ajax({
        url:"../../login",
        type: 'POST',
        data:JSON.stringify(user),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result.value){
                window.location.href="success.html";
            }else{
                alert("登录失败！")
            }
        }
    })
}
