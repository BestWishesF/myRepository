$(document).ready(function () {
    var parts = eval('(' + sessionStorage.getItem('parts') + ")");
    var token = getcookie("sra");
    var part = eval('(' + sessionStorage.getItem('part') + ")");
    if (part == "" || part == null) {
        $(".company .right").html(parts.name)
    } else {
        $(".company .right").html(part.name)
    }
    var json1 = {
        "exp_ord_id": parts.id,
        "exp_ord_state": parts.state
    };
    var res = getUrl("/suyh/app/3/token/findAcceptOrderDetail", json1, token);
    if (res != "") {
        var data = JSON.parse(res);
        var arr = data.obj;
        var obj = arr.accept_collect_list;
        var list = obj[0];
        var da = new Date(arr.exp_ord_time);
        Y = da.getFullYear() + '-';
        M = (da.getMonth() + 1 < 10 ? '0' + (da.getMonth() + 1) : da.getMonth() + 1) + '-';
        D = da.getDate() + ' ';
        h = da.getHours() + ':';
        m = da.getMinutes() + ':';
        s = da.getSeconds();
        var deta = Y + M + D + h + m + s;
        var da = new Date(arr.door_end_time);
        Y = da.getFullYear() + '-';
        M = (da.getMonth() + 1 < 10 ? '0' + (da.getMonth() + 1) : da.getMonth() + 1) + '-';
        D = da.getDate() + '';
        h = da.getHours() + ':';
        m = da.getMinutes() + ':';
        s = da.getSeconds();
        var dete = Y + M + D + h + m + s;
        html = [];
        html += '<img class="left" src="' + arr.agent_head_portrait + '" alt=""/>';
        html += '<div class="box">';
        html += '<span class="size">' + arr.agent_name + '</span><br>';
        html += '<span class="size">' + '<a class="Phone" href="tel:' + arr.agent_phone + '">' + arr.agent_phone + '</span></br>';
        html += '<span clss="timend" id="timed">' + '下单时间：' + '<i>' + deta + '</i>' + '</span></br>';
        html += '<span class="timend">' + '上门时间：' + '<i>' + dete + '</i>' + '</span>';
        html += '</div>'
        html += '<img class="right" src="../img/send/2.jpg" alt="" />'
        $(".up").append(html);
        str = [];
        str += ' <img src="../img/send/site1.png"/>';
        str += '<span class="span">' + arr.add_name + '&nbsp;&nbsp;&nbsp;&nbsp;'
            + '<a class="Phone" href="tel:' + arr.add_mobile_phone + '">' + arr.add_mobile_phone + '</span><br>';
        str += '<span class="timend">' + arr.add_detail_address + '</span>';
        $(".buck .top").append(str);
        sar = [];
        sar += ' <img src="../img/send/site2.png"/>';
        sar += '<span class="span">' + list.add_name + '&nbsp;&nbsp;&nbsp;&nbsp;'
            + '<a class="Phone" href="tel:' + list.add_mobile_phone + '">' + list.add_mobile_phone + '</span><br>';
        sar += '<span class="timend">' + list.add_detail_address + '</span>';
        $(".buck .bottom").append(sar);
    }
    $(".buck .right").click(function () {
        window.location.href = "chooseed.html";
    })
})