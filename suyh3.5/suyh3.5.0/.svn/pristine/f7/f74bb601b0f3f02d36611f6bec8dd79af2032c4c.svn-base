function showdiv() {
    document.getElementById("bg").style.display ="block";
    document.getElementById("show").style.display ="block";
}
function hidediv() {
    document.getElementById("bg").style.display ='none';
    document.getElementById("show").style.display ='none';
}
$(".btn").click(function(){
    var phone=$("#phone").val()
    if(phone==""||phone==null){
        alert("手机号不能为空");
        return false;
    }
    if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
        alert("手机号码不正确");
        return false;
    }
    $(".banner").css("background","#019645");
    $(".banner").css("height","4.2rem");
    $(".phone").css("display","none")
    $(".btn").css("display","none")
    $(".coupon").css("display","block")
    $(".explain").css("display","block")
    $(".transmit").css("display","block")
    $("#bg").css("height","12.7rem")
	$("footer").css("margin","0")
    $("#show").css("margin-top","15%")
})


