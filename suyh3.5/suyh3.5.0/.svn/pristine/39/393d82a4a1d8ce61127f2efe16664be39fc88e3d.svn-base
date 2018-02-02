$(document).ready(function () {
    var tate = eval('(' + sessionStorage.getItem('tate') + ")");
    var token = getcookie("sra");
    var cour = eval('(' + sessionStorage.getItem('cour') + ")")
    if (cour == "" || cour == null) {
        $(".company .right").html(tate.name)
    } else {
        $(".company .right").html(cour.name)
    }
    var json1 = {
        "exp_ord_id": tate.id,
        "exp_ord_state": tate.state
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
        D = da.getDate() + ' ';
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
            + '<a class="Phone" href="tel:' + arr.add_mobile_phone + '">' + arr.add_mobile_phone + '</a>' + '</span><br>';
        str += '<span class="address">' + arr.add_detail_address + '</span>';
        $(".buck .top").append(str);
        sar = [];
        sar += ' <img src="../img/send/site2.png"/>';
        sar += '<span class="span">' + list.add_name + '&nbsp;&nbsp;&nbsp;&nbsp;'
            + '<a class="Phone" href="tel:' + list.add_mobile_phone + '">' + list.add_mobile_phone + '</a>' + '</span><br>';
        sar += '<span class="address">' + list.add_detail_address + '</span>';
        $(".buck .bottom").append(sar);
        star = [];
        star += '<span class="left"><i>厚通编号：</i><form>' +
            '<input type="text" id="a" value="' + list.ht_number + '">' + '</form></span>'
        star += '<input type="hidden" id="iden" value="' + list.exp_ord_clt_id + '">'
        star += '<span class="right"><img src="../img/sm.png"  id="code" alt=""></span>'
        $(".btom").append(star)
        $("#code").click(function () {
            var res = getUrl("/suyh/app/3/findTsHtThirdDto", tsHtThird, "");
            if (res != "") {
                var result = JSON.parse(res);
                if (result.mark == '0') {
                    var appid = result.obj;
                    var json = {
                        "appid": appid,
                        "http_url": window.location.href
                    };
                    var res = getUrl("/suyh/app/3/obtainJsSdk", json, "");
                    if (res != "") {
                        var dresult = JSON.parse(res);
                        var packageValJson = dresult.obj;
                        wx.config({
                            debug: false,
                            appId: packageValJson.appid, // 必填，公众号的唯一标识
                            timestamp: packageValJson.time_stamp, // 必填，生成签名的时间戳
                            nonceStr: packageValJson.nonce_str, // 必填，生成签名的随机串
                            signature: packageValJson.signature,// 必填，签名，见附录1
                            jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

                        });
                        wx.checkJsApi({
                            jsApiList: ['scanQRCode'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                            success: function (res) {

                            }
                        });
                        wx.ready(function () {
                            wx.scanQRCode({
                                needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                                scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                                success: function (res) {
                                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                                    var results = result.split(',')
                                    $("#a").val(results[1]);
                                    var valuer = $('#iden').val()
                                    var number = parseInt(results[1])
                                    var json3 = {
                                        "exp_ord_clt_id": valuer,
                                        "ht_number": number
                                    };
                                    var res = getUrl("/suyh/app/3/token/modifyExpressHtNumber", json3, token);
                                    if (res != "") {
                                        var ddata = JSON.parse(res);
                                        if (ddata.mark == 1) {
                                            alert(ddata.tip)
                                        } else {
                                            alert(ddata.tip)
                                        }
                                    }
                                }
                            });
                        });
                        wx.error(function (res) {
                        });
                    }
                }
            }
        })
        $('form').on('submit', function () {
            var valuer = $('#iden').val()
            var number = $("#a").val()
            var json4 = {
                "exp_ord_clt_id": valuer,
                "ht_number": number
            };
            var res = getUrl("/suyh/app/3/token/modifyExpressHtNumber", json4, token);
            if (res != "") {
                var rdata = JSON.parse(res);
                if (rdata.mark == 1) {
                    alert(rdata.tip)
                } else {
                    alert(rdata.tip)
                }
            }

        });

    }

    $(".buck .right").click(function () {
        window.location.href = "chooseer.html";
    })
})



