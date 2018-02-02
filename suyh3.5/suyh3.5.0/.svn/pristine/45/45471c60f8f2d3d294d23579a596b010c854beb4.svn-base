$(document).ready(function () {
    var tate = eval('(' + sessionStorage.getItem('taner') + ")");
    var token = getcookie("sra");
    var json = {
        "exp_ord_id": tate.id,
        "exp_ord_state": tate.state
    };
    var much = eval('(' + sessionStorage.getItem('muchea') + ")");
    if (much == "" || much == null) {
        $("#name").html(tate.name)
    } else {
        $("#name").html(much.name)
    }
    var res = getUrl("/suyh/app/3/token/findAcceptOrderDetail", json, token);
    if (res != "") {
        var data = JSON.parse(res);
        var arr = data.obj;
        var lis = arr.accept_collect_list
        var list = lis[0]
        str = [];
        str += ' <img src="../img/send/site1.png"/>';
        str += '<span class="span">' + arr.add_name + '&nbsp;&nbsp;&nbsp;&nbsp;'
            + arr.add_mobile_phone + '</span><br>';
        str += '<span class="timend">' + arr.add_detail_address + '</span>';
        $(".buck .top").append(str);
        sar = [];
        sar += ' <img src="../img/send/site2.png"/>';
        sar += '<span class="span">' + list.add_name + '&nbsp;&nbsp;&nbsp;&nbsp;'
            + list.add_mobile_phone + '</span><br>';
        sar += '<span class="timend">' + list.add_detail_address + '</span>';
        $(".buck .bottom").append(sar);
    }
    $(".buck .right").click(function () {
        window.location.href = "choosear.html";
    })
})