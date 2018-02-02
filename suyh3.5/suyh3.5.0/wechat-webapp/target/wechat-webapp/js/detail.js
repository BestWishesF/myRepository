$(document).ready(function () {
    var tat = eval('(' + sessionStorage.getItem('detail') + ")");
    var json = {
        "exp_ord_clt_id": tat.detail
    };
    var token = getcookie("sra");
    var res = getUrl("/suyh/app/3/token/findExpDetail", json, token);
    if (res != "") {
        var data = JSON.parse(res);
        var str = data.obj;
        var minute = str.express_logistics
        var html = [];
        html += '<img src="../img/send/site1.png"/>';
        html += '<span class="span">' + str.add_name + '&nbsp;&nbsp;&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + str.add_mobile_phone + '">' + str.add_mobile_phone + '</a>' + '</span><br>';
        html += '<span class="address">' + str.add_detail_address + '</span>';
        $(".buck .box").append(html);
        arr = [];
        arr += '<img src="../img/send/site2.png"/>';
        arr += '<span class="span">' + str.collect_name + '&nbsp;&nbsp;&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + str.collect_mobile + '">' + str.collect_mobile + '</a>' + '&nbsp;&nbsp;&nbsp;&nbsp;' + '</span><br>';
        arr += '<span class="address">' + str.collect_detail_address + '</span>';
        $(".buck .top").append(arr);
        ctr = [];
        ctr += ' <h3>' + '快递公司' + '</h3> ';
        ctr += '<img src="' + str.express_logo + '">';
        ctr += '<span>' + str.express_name + '</span>';
        ctr += ' <span class="i">' + '￥' + str.express_price + '</span></br>';
        ctr += '<span class="number">' + '运单编号：' + '<i>' + str.express_number + '</i></span>';
        $(".list .top").append(ctr);
        if (minute.length == "" || minute.length == null) {

        } else {
            for (var i = 0; i < minute.length; i++) {
                var date = new Date(minute[i].accept_time);
                Y = date.getFullYear() + '-';
                M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                D = date.getDate() + ' ';
                h = date.getHours() + ':';
                m = date.getMinutes();
                s = date.getSeconds();
                var timen = Y + M + D;
                var timent = h + m
                Html = "";
                Html = '<div class="bottom">' +
                    '<div class="left">' +
                    '<span class="span">' + timent + '</span>' +
                    '<span class="data">' + timen + '</span>' +
                    '</div>' +
                    '<div class="right">' +
                    '<p>' + minute[i].accept_station + '</p>' +
                    '</div>' +
                    '</div>'
                $(".ckcd").append(Html)
            }
        }
    }
})