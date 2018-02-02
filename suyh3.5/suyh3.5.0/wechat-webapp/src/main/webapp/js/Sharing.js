var phone = eval('(' + sessionStorage.getItem('phone') + ")");
$(".txt i").html(phone.phone)
var openid = eval('(' + localStorage.getItem('open') + ")");
var id=openid.id;
if(id==1){
    setTimeout(' window.location.href = "send.html"',3000);
}
if(id==2){
    setTimeout(' window.location.href = "order.html"',3000);
}