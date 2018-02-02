$(document).ready(function () {
    var token = getcookie("sra");
    var role = getcookie("role");
    var discount = getcookie("discount");
    $("#name").click(function () {
        var bh = $("body").height();
        var bw = $("body").width();
        $("#fullbg").css({
            height: bh,
            width: bw,
            display: "block",
        });
        $("#dialog").show();
    })
    var loan = eval('(' + sessionStorage.getItem('loan') + ")");
    var agent = loan.agent_id;
    var agent_head = loan.agent_head;
    var name = loan.agent_name;
    var agent_phone = loan.agent_phone;
    var agent_id = loan.exp_ord_id
    console.log(agent_id)
    addcookie("agent_id", agent_id)
    addcookie("agent", agent);
    addcookie("agent_head", agent_head)
    addcookie("agent_name", encodeURIComponent(name))
    addcookie("agent_phone", agent_phone)
    var arr = [];
    arr += '<i>' + '订单金额：' + '<em> ' + '￥' + loan.loan + '</em>' + '</i>';
    $(".site .loan").html(arr);
    var sar = [];
    sar += '￥' + loan.loan;
    $(".need .num").html(sar);
    var m = 1;
    var ord = parseInt(loan.exp_ord_id);
    var json = {
        exp_ord_id: ord
    };
    var res = getUrl("/suyh/app/5/token/findCouponByCondition", json, token);
    if (res != "") {
        var data = JSON.parse(res);
        var datea = data.obj;
        var items = datea;
        $("#Load").html("")
        /*if (items.length <= 6) {
            $("#Load").html("")
        }*/
        if (items == "" || items == null) {
            $(".box .aa").html("暂无优惠券")
            $("#name").unbind("click");
        } else {
            $(".box .aa").html("请选择优惠券")
        }
        if (role == 1 && discount != 1) {
            $(".box .aa").html("暂无优惠券")
            $("#name").unbind("click");
        }
        for (var i = 0; i < items.length; i++) {
            html = [];
            html = '<p class="label">' +
                '<label for="in" class="no">' +
                '<span>' + items[i].cou_name + '</span>' +
                '<input type="radio" id="in" checked="checked" name="coupon" value="' + items[i].cou_amount + ',' + items[i].cou_name + ',' + items[i].cou_limit_amount + ',' + items[i].cou_discount + ',' + items[i].cou_id + '">' +
                '</label>' +
                '</p>'
            $(".Discount").append(html);
        }
        $("#right").click(function () {
            var art = new Array();
            $('input[name="coupon"]:checked').each(function () {
                var sfruit = $(this).val();
                var stop = sfruit.split(",");
                var sum = parseFloat(loan.loan);//订单金额
                var amount = parseInt(stop[0])//优惠券金额
                var limit = parseInt(stop[3]);//折扣
                var rebate = parseInt(stop[2]);//限定金额
                if (sum >= rebate) {
                    if (limit == 1) {
                        var a = sum - amount;
                        if (a < 0) {
                            var a = 0
                        }
                    }
                    if (limit < 1) {
                        var b = sum * (1 - limit);
                        var c = b - amount;
                        if (c < 0) {
                            var c = amount
                        }
                        var a = sum - c;
                        if (a < 0) {
                            a = 0
                        }
                    }
                    if (amount == 0 && limit < 1) {
                        var a = sum * limit;
                    }
                    $("#fullbg,#dialog").hide();
                    var sar = [];
                    sar += '￥' + parseFloat(a).toFixed(2);
                    $(".need .num").html(sar);
                    var cou_id = stop[4]
                    $(".box .aa").html(stop[1])
                    var storage = window.sessionStorage;
                    var cou_id = {
                        cou_id: cou_id,
                        number: a
                    };
                    var d = JSON.stringify(cou_id);
                    storage.setItem('cou_id', d);
                } else {
                    alert("优惠券不可使用")
                }
            });
        })
        $("#left").click(function () {
            $("#fullbg,#dialog").hide();
            var sar = [];
            sar += '￥' + loan.loan;
            $(".need .num").html(sar);
            $(".box .aa").html("不使用优惠券")
            var cou_id = 0;
            var storage = window.sessionStorage;
            var cou_id = {
                cou_id: cou_id,
                number: loan.loan
            };
            var d = JSON.stringify(cou_id);
            storage.setItem('cou_id', d);
        })
    }

    /*================================================支付订单=================================================*/
    $("#but").click(function () {
        var cou_id = eval('(' + sessionStorage.getItem('cou_id') + ")");
        if (cou_id == "" || cou_id == null) {
            var cou_id = 0
            var nur = loan.loan
        } else {
            var nur = cou_id.number
            var cou_id = cou_id.cou_id
        }
        if (nur == 0) {
            var p = 3
        } else {
            var p = 1
        }
        var ord = parseInt(loan.exp_ord_id);
        var cou = parseInt(cou_id);
        var text = $("#txt").val();
        var wxRequestPayDto = {
            exp_ord_id: ord,
            coupon_id: cou,
            pay_type: p,
            we_chat_code: getcookie("third_id")
        };
        var res = getUrl("/suyh/app/3/token/weChatOrderPay", wxRequestPayDto, token);
        if (res != "") {
            var result = JSON.parse(res);
            if (result.mark == "0") {
                var packageVal = result.obj;
                if (packageVal != null && packageVal != "") {
                    WeixinJSBridge.invoke('getBrandWCPayRequest', {
                            "appId": packageVal.appid,     //公众号名称，由商户传入
                            "timeStamp": packageVal.time_stamp,         //时间戳，自1970年以来的秒数
                            "nonceStr": packageVal.nonce_str, //随机串
                            "package": packageVal.package_value,
                            "signType": packageVal.sign_type,         //微信签名方式：
                            "paySign": packageVal.pay_sign //微信签名
                        },
                        function (res) {
                            //支付成功或失败前台判断
                            if (res.err_msg == 'get_brand_wcpay_request:ok') {
                                alert("支付成功.");
                                localStorage.clear();
                                window.location.href = "rated.html";
                            } else {
                                alert('支付未成功');
                                window.location.reload(true);
                            }
                        });
                } else {
                    alert("支付未成功")
                    sessionStorage.clear();
                    window.location.href = "rated.html";
                }
            }
        }
    })
})
