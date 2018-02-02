$(document).ready(function () {

    var third_id = getUrlParam("third_id");
    addcookie("third_id", third_id);
    var access_code = getUrlParam('code');
    var aa = getcookie("access_code");
    var role = getcookie("role");
    var discount = getcookie("discount");
    var token = getcookie("sra")
    if (role == 2) {
        $("#wait").hide();
        $(".satar").hide()
        $(".tab-item").css("width", "33.333%")
        $(".tab em").css("padding", "0 0.76rem 0.05rem")
    }
    if (aa != null && aa != "") {
        if (access_code == aa) {
            access_code = null;
            delCookie("access_code");
        } else {
            addcookie("access_code", access_code);
        }
    } else {
        addcookie("access_code", access_code);
    }
    var appid = '';
    var token = getcookie("sra");
    if (access_code == null || access_code == "") {
        fn()
    } else {
        var json1 = {
            third_id: third_id
        }
        var res = getUrl("/suyh/app/3/findTsHtThirdDto", json1, "");
        if (res != "") {
            var result = JSON.parse(res);
            if (result.mark == '0') {
                var appid = result.obj;
                if (access_code == null) {
                    var fromurl = "http://hotol.cn/wechat/html/order.html?third_id=2";
                    var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + appid + '&redirect_uri=' + fromurl + '&response_type=code&scope=snsapi_userinfo&state=' + third_id + '#wechat_redirect';
                    location.href = url;
                } else {
                    var json2 = {
                        code: access_code,
                        third_id: third_id
                    }
                    var resp = getUrl("/suyh/app/3/obtainWXPersonal", json2, "");
                    if (resp != "") {
                        var resultp = JSON.parse(resp);
                        var str = resultp.obj.token;
                        var role = resultp.obj.memb_role
                        var discount = resultp.obj.memb_discount
                        var openid = resultp.obj.openid;
                        if (str == "" || str == null) {
                            var id = 2
                            var storage = window.localStorage;
                            var open = {
                                openid: openid,
                                id: id
                            };
                            var d = JSON.stringify(open);
                            storage.setItem('open', d);
                            window.location.href = "bund.html";
                        }
                        addcookie("third_id", third_id);
                        addcookie("sra", str);
                        token = str;
                        fn()
                    }
                }
            }
        }
    }
    var h = 1;//分页传值


    function fn() {
        h = 1
        var json3 = {"page_size": 10, "page_no": h};
        $(".accept").html("");
        var res = getUrl("/suyh/app/3/token/findAcceptInExp", json3, token);
        if (res != "") {
            var data = JSON.parse(res);
            $(".accept").html("");
            var str = data.obj;
            var atr = str.total_pages
            var arr = str.items;
            if (arr.length > 0) {
                $("#San").hide()
            }
            if (arr.length < 10 && arr.length > 0) {
                $("#btn").html("加载更多")
                $("#btn").hide()
            }
            if (arr.length >= 10) {
                $("#btn").html("加载更多")
                $("#btn").show()
            }
            var lis = [];
            for (var i = 0; i < arr.length; i++) {
                var date = new Date(arr[i].exp_ord_time);
                Y = date.getFullYear() + '-';
                M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                D = date.getDate() + ' ';
                z = date.getHours() + ':';
                m = date.getMinutes();
                var timen = Y + M + D + z + m;
                var stars = parseInt(arr[i].exp_ord_state);
                if (stars == 2) {
                    var mvvm = "已受理"
                    var p = "取消"
                    lis = '<div class="dadv">' +
                        '<div class="revise">' +
                        '<section id="left" class="left">' +
                        '<img class="logo" src="' + arr[i].express_logo + '">' +
                        '<span class="name">' + '发件人:' + arr[i].add_name + '</span><br/>' +
                        '<span class="address">' + arr[i].add_detail_address + '</span><br/>' +
                        '<input id="in" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                        '<span class="opend">' + '代理人：' + arr[i].agent_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].agent_phone + '">' + arr[i].agent_phone + '</a>' + '</span>' +
                        '</section>' +
                        '<section class="right">' +
                        '<input id="inte" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                        '<p class="but btnu">' + mvvm + '</p>' +
                        '</section>' + '</div>' + '<p  class="removes">' + '<span class="sh">' + timen + '</span>' + '<span class="remove">' + p + '<input type="hidden" id="remove" value="' + arr[i].exp_ord_id + '">' + '</span>' + '</p>'
                        + '</div>';
                    $(".accept").append(lis);
                    $(".btnu").css("background", "#44c9aa")
                } else {
                    var mvvm = "未受理"
                    var p = "取消"
                    lis = '<div class="dadv">' +
                        '<div class="revise">' +
                        '<section id="left" class="left">' +
                        '<img class="logo" src="' + arr[i].express_logo + '">' +
                        '<span class="name">' + '发件人:' + arr[i].add_name + '</span><br/>' +
                        '<span class="address">' + arr[i].add_detail_address + '</span><br/>' +
                        '<input id="in" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                        '</section>' +
                        '<section class="right">' +
                        '<input id="inte" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                        '<p class="but backgu">' + mvvm + '</p>' +
                        '</section>' + '</div>' + '<div  class="removes">' + '<span class="sh">' + timen + '</span>' + '<span class="remove">' + p + '<input type="hidden" id="remove" value="' + arr[i].exp_ord_id + '">' + '</span>' + '</div>'
                        + '</div>'
                    $(".accept").append(lis);
                    $(".backgu").css("background", "#ccc")
                }
            }
            $(".accept .right").click(function () {
                var p = $(this).children("#inte").val();
                var pat = p.split(",");
                console.log(pat)
                var cant = pat[0];
                if (cant == 2) {
                    var storage = window.sessionStorage;
                    var tate = {
                        id: parseInt(pat[1]),
                        state: parseInt(pat[0]),
                        express: parseInt(pat[2]),
                        name: pat[3],
                        add_region: pat[4]
                    }
                    var d = JSON.stringify(tate);
                    storage.setItem("tate", d);
                    if (role == 2) {
                        window.location.href = "seizeds.html";
                    } else {
                        window.location.href = "seized.html";
                    }
                }
                if (cant == 1) {
                    var storage = window.sessionStorage;
                    var taner = {
                        id: parseInt(pat[1]),
                        state: parseInt(pat[0]),
                        express: parseInt(pat[2]),
                        name: pat[3],
                        add_region: pat[4]
                    }
                    var d = JSON.stringify(taner);
                    storage.setItem("taner", d);
                    if (role == 2) {
                        window.location.href = "express.html";
                    } else {
                        window.location.href = "suspen.html";
                    }
                }
            })
            $(".accept .left").click(function () {
                var p = $(this).children("#in").val();
                var pat = p.split(",");
                console.log(pat)
                var cant = pat[0];
                if (cant == 2) {
                    var storage = window.sessionStorage;
                    var tate = {
                        id: parseInt(pat[1]),
                        state: parseInt(pat[0]),
                        express: parseInt(pat[2]),
                        name: pat[3],
                        add_region: pat[4]
                    }
                    var d = JSON.stringify(tate);
                    storage.setItem("tate", d);
                    if (role == 2) {
                        window.location.href = "seizeds.html";
                    } else {
                        window.location.href = "seized.html";
                    }
                }
                if (cant == 1) {
                    var storage = window.sessionStorage;
                    var taner = {
                        id: parseInt(pat[1]),
                        state: parseInt(pat[0]),
                        express: parseInt(pat[2]),
                        name: pat[3],
                        add_region: pat[4]
                    }
                    var d = JSON.stringify(taner);
                    storage.setItem("taner", d);
                    if (role == 2) {
                        window.location.href = "express.html";
                    } else {
                        window.location.href = "suspen.html";
                    }
                }
            })
            $(".remove").click(function () {
                var valy = $(this).children("#remove").val()
                var valu = {"exp_ord_id": parseInt(valy)};
                var res = getUrl("/suyh/app/3/token/cancelOrder", valu, token);
                if (res != "") {
                    window.location.href = "order.html";
                }
            })
        }
    }

    if (token != null && token != "") {
        /*=============================================受理中====================================*/
        $("#active").click(function () {
            fn()
        })
        $("#btn").click(function () {
            h = h + 1
            var json4 = {"page_size": "10", "page_no": h}
            var res = getUrl("/suyh/app/3/token/findAcceptInExp", json4, token);
            if (res != "") {
                var data = JSON.parse(res);
                var str = data.obj;
                var atr = str.total_pages
                var arr = str.items;
                var lis = [];
                for (var i = 0; i < arr.length; i++) {
                    var date = new Date(arr[i].exp_ord_time);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    z = date.getHours() + ':';
                    m = date.getMinutes();
                    var timen = Y + M + D + z + m;
                    var stars = parseInt(arr[i].exp_ord_state);
                    if (stars == 2) {
                        var mvvm = "已受理"
                        var p = "取消"
                        lis = '<div class="dadv">' +
                            '<div class="revise">' +
                            '<section id="left" class="left">' +
                            '<img class="logo" src="' + arr[i].express_logo + '">' +
                            '<span class="name">' + '发件人:' + arr[i].add_name + '</span><br/>' +
                            '<span class="address">' + arr[i].add_detail_address + '</span><br/>' +
                            '<input id="in" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                            '<span class="opend">' + '代理人：' + arr[i].agent_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].agent_phone + '">' + arr[i].agent_phone + '</a>' + '</span>' +
                            '</section>' +
                            '<section class="right">' +
                            '<input id="inte" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                            '<p class="but">' + mvvm + '</p>' +
                            '</section>' + '</div>' + '<p  class="removes">' + '<span class="sh">' + timen + '</span>' + '<span class="remove">' + p + '<input type="hidden" id="remove" value="' + arr[i].exp_ord_id + '">' + '</p>'
                            + '</div>'
                        $(".accept").append(lis);
                        $(".but").css("background", "#44c9aa")
                    } else {
                        var mvvm = "未受理"
                        var p = "取消"
                        lis = '<div class="dadv">' +
                            '<div class="revise">' +
                            '<section id="left" class="left">' +
                            '<img class="logo" src="' + arr[i].express_logo + '">' +
                            '<span class="name">' + '发件人:' + arr[i].add_name + '</span><br/>' +
                            '<span class="address">' + arr[i].add_detail_address + '</span><br/>' +
                            '<input id="in" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                            '</section>' +
                            '<section class="right">' +
                            '<input id="inte" type="hidden" value="' + arr[i].exp_ord_state + ',' + arr[i].exp_ord_id + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                            '<p class="but backgu">' + mvvm + '</p>' +
                            '</section>' + '</div>' + '<p  class="removes">' + '<span class="sh">' + timen + '</span>' + '<span class="remove">' + p + '<input type="hidden" id="remove" value="' + arr[i].exp_ord_id + '">' + '</span>' + '</p>'
                            + '</div>'
                        $(".accept").append(lis);
                        $(".backgu").css("background", "#ccc")
                    }
                }
                $(".accept .right").click(function () {
                    var p = $(this).children("#inte").val();
                    var pat = p.split(",");
                    var cant = pat[0];
                    if (cant == 2) {
                        var storage = window.sessionStorage;
                        var tate = {
                            id: parseInt(pat[1]),
                            state: parseInt(pat[0]),
                            express: parseInt(pat[2]),
                            name: pat[3],
                            add_region: pat[4]
                        }
                        var d = JSON.stringify(tate);
                        storage.setItem("tate", d);
                        if (role == 2) {
                            window.location.href = "seizeds.html";
                        } else {
                            window.location.href = "seized.html";
                        }
                    }
                    if (cant == 1) {
                        var storage = window.sessionStorage;
                        var taner = {
                            id: parseInt(pat[1]),
                            state: parseInt(pat[0]),
                            express: parseInt(pat[2]),
                            name: pat[3],
                            add_region: pat[4]
                        }
                        var d = JSON.stringify(taner);
                        storage.setItem("taner", d);
                        if (role == 2) {
                            window.location.href = "express.html";
                        } else {
                            window.location.href = "suspen.html";
                        }
                    }
                })
                $(".accept .left").click(function () {
                    var p = $(this).children("#in").val();
                    var pat = p.split(",");
                    var cant = pat[0];
                    if (cant == 2) {
                        var storage = window.sessionStorage;
                        var tate = {
                            id: parseInt(pat[1]),
                            state: parseInt(pat[0]),
                            express: parseInt(pat[2]),
                            name: pat[3],
                            add_region: pat[4]
                        }
                        var d = JSON.stringify(tate);
                        storage.setItem("tate", d);
                        if (role == 2) {
                            window.location.href = "seizeds.html";
                        } else {
                            window.location.href = "seized.html";
                        }
                    }
                    if (cant == 1) {
                        var storage = window.sessionStorage;
                        var taner = {
                            id: parseInt(pat[1]),
                            state: parseInt(pat[0]),
                            express: parseInt(pat[2]),
                            name: pat[3],
                            add_region: pat[4]
                        }
                        var d = JSON.stringify(taner);
                        storage.setItem("taner", d);
                        if (role == 2) {
                            window.location.href = "express.html";
                        } else {
                            window.location.href = "suspen.html";
                        }
                    }
                })
                $(".remove").click(function () {
                    var valy = $(this).children("#remove").val()
                    var json5 = {"exp_ord_id": parseInt(valy)};
                    var res = getUrl("/suyh/app/3/token/cancelOrder", json5, token);
                    if (res != "") {
                        window.location.href = "order.html";
                    }
                })
                if (arr.length < 10) {
                    $("#btn").html("加载更多")
                    $("#btn").hide()
                }
                if (arr.length >= 10) {
                    $("#bn").html("加载更多")
                }
            }
        })
        /*=============================================待付款====================================*/
        var j = 1
        var Data = {"page_size": "10", "page_no": j};
        $("#wait").click(function () {
            j = 1
            $(".want").html("");
            var res = getUrl("/suyh/app/3/token/findToBePaidExp", Data, token);
            if (res != "") {
                var data = JSON.parse(res);
                $(".want").html('');
                var str = data.obj;
                var atr = str.total_pages
                var arr = str.items;
                if (arr.length == 0) {
                    $("#bant").html("暂无数据")
                } else if (arr.length > 0) {
                    $("#Tan").hide()
                }
                if (arr.length < 10 && arr.length > 0) {
                    $("#bant").hide()
                }
                if (arr.length >= 10) {
                    $("#bant").show()
                }
                var list = [];
                for (var i = 0; i < arr.length; i++) {
                    var date = new Date(arr[i].exp_ord_time);
                    var muca = parseFloat(arr[i].exp_ord_amount);
                    var mac = +parseFloat(arr[i].exp_ord_gratuity)
                    var much = muca + mac;
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    z = date.getHours() + ':';
                    m = date.getMinutes();
                    var timen = Y + M + D + z + m;
                    list = '<div class="dadv">' +
                        '<div class="revise">' +
                        '<section class="left">' +
                        '<img class="logo" src="' + arr[i].express_logo + '">' +
                        '<span class="name">' + '发件人:' + arr[i].add_name + '</span><br>' +
                        '<span class="address">' + arr[i].add_detail_address + '</span><br>' +
                        '<input class="par" type="hidden" value="' + arr[i].exp_ord_id + ',' + arr[i].exp_ord_state + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                        '<span class="opend">' + '代理人：' + arr[i].agent_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].agent_phone + '">' + arr[i].agent_phone + '</a>' + '</span>' +
                        '</section>' +
                        '<section class="right">' +
                        '<em class="em">' + '￥' + parseFloat(much).toFixed(2) + '</em>' +
                        '<input id="input" type="hidden" value="' + arr[i].exp_ord_amount + ',' + arr[i].exp_ord_id + ',' + arr[i].agent_id +
                        ',' + arr[i].agent_head_portrait + ',' + arr[i].agent_name + ',' + arr[i].agent_phone + ',' + arr[i].exp_ord_gratuity + '">' +
                        '<i class="btn">' + '立即支付' + '</i>' +
                        '</section>' + '</div>' +
                        '<div class="removes">' + '<span class="sh">' + timen + '</span>' + '<span class="remove">' + "取消" + '<input type="hidden" id="remove" value="' + arr[i].exp_ord_id + '">' + '</span>' + '</div>';
                    +'</div>'
                    $(".want").append(list);
                }
                $(".want .right").click(function () {
                    var input = $(this).children("input").val();
                    var svvm = input.split(',')
                    var loaner = parseFloat(svvm[0])
                    var number = parseFloat(svvm[6])
                    var nub = loaner + number
                    var storage = window.sessionStorage;
                    var loan = {
                        loan: parseFloat(nub).toFixed(2),
                        exp_ord_id: svvm[1],
                        agent_id: svvm[2],
                        agent_head: svvm[3],
                        agent_name: svvm[4],
                        agent_phone: svvm[5],
                    }
                    var d = JSON.stringify(loan);
                    storage.setItem("loan", d);
                    window.location.href = "financed.html";
                })
                $(".left").click(function () {
                    var p = $(this).children(".par").val();
                    var pat = p.split(",");
                    var cant = pat[0];
                    var storage = window.sessionStorage;
                    var parts = {
                        id: parseInt(pat[0]),
                        state: parseInt(pat[1]),
                        express: parseInt(pat[2]),
                        name: pat[3],
                        add_region: pat[4]
                    }
                    var d = JSON.stringify(parts);
                    storage.setItem("parts", d)
                    window.location.href = "paid.html";
                })
                $(".remove").click(function () {
                    var valy = $(this).children("#remove").val()
                    var json6 = {"exp_ord_id": parseInt(valy)};
                    var res = getUrl("/suyh/app/3/token/cancelOrder", json6, token);
                    if (res != "") {
                        window.location.href = "order.html";
                    }
                })
            }
        })
        $("#bant").click(function () {
            j = j + 1
            var json7 = {"page_size": "10", "page_no": j}
            var res = getUrl("/suyh/app/3/token/findToBePaidExp", json7, token);
            if (res != "") {
                var data = JSON.parse(res);
                var str = data.obj;
                var atr = str.total_pages
                var arr = str.items;
                var list = [];
                for (var i = 0; i < arr.length; i++) {
                    var date = new Date(arr[i].exp_ord_time);
                    var muca = parseFloat(arr[i].exp_ord_amount);
                    var mac = +parseFloat(arr[i].exp_ord_gratuity)
                    var much = muca + mac;
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    z = date.getHours() + ':';
                    m = date.getMinutes();
                    var timen = Y + M + D + z + m;
                    list = '<div class="dadv">' +
                        '<div class="revise">' +
                        '<section class="left">' +
                        '<img class="logo" src="' + arr[i].express_logo + '">' +
                        '<input class="par" type="hidden" value="' + arr[i].exp_ord_id + ',' + arr[i].exp_ord_state + ',' + arr[i].express_id + ',' + arr[i].express_name + ',' + arr[i].add_region + '">' +
                        '<span class="name">' + '发件人:' + arr[i].add_name + '</span><br>' +
                        '<span class="address">' + arr[i].add_detail_address + '</span><br>' +
                        '<span class="opend">' + '代理人：' + arr[i].agent_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].agent_phone + '">' + arr[i].agent_phone + '</a>' + '</span>' +
                        '</section>' +
                        '<section class="right">' +
                        '<em class="em">' + '￥' + parseFloat(much).toFixed(2) + '</em>' +
                        '<input id="input" type="hidden" value="' + arr[i].exp_ord_amount + ',' + arr[i].exp_ord_id + ',' + arr[i].agent_id +
                        ',' + arr[i].agent_head_portrait + ',' + arr[i].agent_name + ',' + arr[i].agent_phone + ',' + arr[i].exp_ord_gratuity + '">' +
                        '<i class="btn">' + '立即支付' + '</i>' +
                        '<div class="removes">' + '<span class="sh">' + timen + '</span>' + '<span class="remove">' + "取消" + '<input type="hidden" id="remove" value="' + arr[i].exp_ord_id + '">' + '</span>' + '</p>';
                    +'</div>'
                    $(".want").append(list);
                }
                $(".want .right").click(function () {
                    var input = $(this).children("#input").val();
                    var svvm = input.split(',')
                    var loaner = parseFloat(svvm[0])
                    var number = parseFloat(svvm[6])
                    var nub = loaner + number
                    var storage = window.sessionStorage;
                    var loan = {
                        loan: parseFloat(nub).toFixed(2),
                        exp_ord_id: svvm[1],
                        agent_id: svvm[2],
                        agent_head: svvm[3]
                    }
                    var d = JSON.stringify(loan);
                    storage.setItem("loan", d);
                    window.location.href = "financed.html";
                })
                $(".left").click(function () {
                    var p = $(this).children(".par").val();
                    var pat = p.split(",");
                    var cant = pat[0];
                    var storage = window.sessionStorage;
                    var parts = {
                        id: parseInt(pat[0]),
                        state: parseInt(pat[1]),
                        express: parseInt(pat[2]),
                        name: pat[3],
                        add_region: pat[4]
                    }
                    var d = JSON.stringify(parts);
                    storage.setItem("parts", d)
                    window.location.href = "paid.html";
                })
                $(".remove").click(function () {
                    var valy = $(this).children("#remove").val()
                    var json8 = {"exp_ord_id": parseInt(valy)};
                    var res = getUrl("/suyh/app/3/token/cancelOrder", json8, token);
                    if (res != "") {
                        window.location.href = "order.html";
                    }
                })
                if (arr.length < 10) {
                    $("#bant").hide()
                }
            }
        })
        /*=============================================投递中====================================*/
        var k = 1;
        var json9 = {"page_size": "10", "page_no": k};
        $("#deliver").click(function () {
            k = 1
            $(".dliver").html("");
            var res = getUrl("/suyh/app/3/token/findDeliveryExp", json9, token);
            if (res != "") {
                var data = JSON.parse(res);
                $(".dliver").html("");
                var str = data.obj;
                var atr = str.total_pages
                var arr = str.items;
                if (arr.length == 0) {
                    $("#bt").html("暂无数据")
                } else if (arr.length > 0) {
                    $("#stan").hide()
                }
                if (arr.length < 10 && arr.length > 0) {
                    $("#bt").hide()
                }
                if (arr.length >= 10) {
                    $("#bt").show()
                }
                var html = [];
                for (var i = 0; i < arr.length; i++) {
                    var date = new Date(arr[i].send_time);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    z = date.getHours() + ':';
                    m = date.getMinutes();
                    var timen = Y + M + D + z + m;
                    var state = parseInt(arr[i].exp_ord_clt_state);
                    if (state == 1) {
                        var del_ive = "已揽件"
                    }
                    if (state == 2) {
                        var del_ive = "运输中"
                    }
                    if (state == 3) {
                        var del_ive = "派送中"
                    }
                    html = '<div class="news">' +
                        '<section class="left">' +
                        '<img class="logo" src="' + arr[i].express_logo + '">' +
                        '<p class="p">' + arr[i].express_name + '：' + arr[i].express_number + '</p>' +
                        '<span class="opend">' + arr[i].add_name + '&nbsp;&nbsp;</span>' +
                        '<span class="phone">' + '<a class="Phone" href="tel:' + arr[i].add_mobile_phone + '">' + arr[i].add_mobile_phone + '</a>' + '</span><br>' +
                        '<span class="address">' + arr[i].add_detail_address + '</span><br>' + '<input id="inter" type="hidden" value="' + arr[i].exp_ord_clt_id + '">' +
                        '<span class="recent">' + '最新快递信息:' + arr[i].logistic + '</span>' +
                        '</section>' + '<a href="../html/detail.html">' +
                        '<section class="right">' +
                        '<span class="sh">' + timen + '</span><br>'
                        + '<input id="int" type="hidden" value="' + arr[i].exp_ord_clt_id + '">' +
                        '<p class="delivers">' + del_ive + '</p>' +
                        '</section>' +
                        '</a>'
                        + '</div>'
                    $(".dliver").append(html);
                }
                $(".dliver .right").click(function () {
                    var valuer = $(this).children("#int").val();
                    var storage = window.sessionStorage;
                    var detail = {detail: valuer}
                    var d = JSON.stringify(detail);
                    storage.setItem("detail", d);
                    window.location.href = "detail.html";
                })
                $(".dliver .left").click(function () {
                    var valuer = $(this).children("#inter").val();
                    var storage = window.sessionStorage;
                    var detail = {detail: valuer}
                    var d = JSON.stringify(detail);
                    storage.setItem("detail", d);
                    window.location.href = "detail.html";
                })
            }
        })
        $("#bt").click(function () {
            k = k + 1;
            var json10 = {
                "page_size": "10",
                "page_no": k
            };
            var res = getUrl("/suyh/app/3/token/findDeliveryExp", json10, token);
            if (res != "") {
                var data = JSON.parse(res);
                var str = data.obj;
                var atr = str.total_pages
                var arr = str.items;
                var html = [];
                for (var i = 0; i < arr.length; i++) {
                    var date = new Date(arr[i].send_time);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    z = date.getHours() + ':';
                    m = date.getMinutes();
                    var timen = Y + M + D + z + m;
                    var state = parseInt(arr[i].exp_ord_clt_state);
                    if (state == 1) {
                        var del_ive = "已揽件"
                    }
                    if (state == 2) {
                        var del_ive = "运输中"
                    }
                    if (state == 3) {
                        var del_ive = "派送中"
                    }
                    html = '<div class="news">' +
                        '<section class="left">' +
                        '<img class="logo" src="' + arr[i].express_logo + '">' +
                        '<p class="p">' + arr[i].express_name + '：' + arr[i].express_number + '</p>' +
                        '<span class="opend">' + arr[i].add_name + '&nbsp;&nbsp;</span>' +
                        '<span class="phone">' + '<a class="Phone" href="tel:' + arr[i].add_mobile_phone + '">' + arr[i].add_mobile_phone + '</a>' + '</span><br>' +
                        '<span class="address">' + arr[i].add_detail_address + '</span><br>' + '<input id="inter" type="hidden" value="' + arr[i].exp_ord_clt_id + '">' +
                        '<span class="recent">' + '最新快递信息:' + arr[i].logistic + '</span>' +
                        '</section>' + '<a href="../html/detail.html">' +
                        '<section class="right">' +
                        '<span class="sh">' + timen + '</span><br>'
                        + '<input id="int" type="hidden" value="' + arr[i].exp_ord_clt_id + '">' +
                        '<p class="delivers">' + del_ive + '</p>' +
                        '</section>' +
                        '</a>'
                        + '</div>'
                    $(".dliver").append(html);
                }
                $(".dliver .right").click(function () {
                    var valuer = $(this).children("#int").val();
                    var storage = window.sessionStorage;
                    var detail = {detail: valuer}
                    var d = JSON.stringify(detail);
                    storage.setItem("detail", d);
                    window.location.href = "detail.html";
                })
                $(".dliver .left").click(function () {
                    var valuer = $(this).children("#inter").val();
                    var storage = window.sessionStorage;
                    var detail = {detail: valuer}
                    var d = JSON.stringify(detail);
                    storage.setItem("detail", d);
                    window.location.href = "detail.html";
                })
                if (arr.length < 10) {
                    $("#bt").hide()
                }
            }

        })
        /*=============================================已送达====================================*/
        var t = 1;
        var json11 = {
            "page_size": "10",
            "page_no": t
        };
        $("#reach").click(function () {
            t = 1
            $(".clact").html("");
            var res = getUrl("/suyh/app/3/token/findHasBeenFinishExp", json11, token);
            if (res != "") {
                var data = JSON.parse(res);
                $(".clact").html("");
                var str = data.obj;
                var atr = str.total_pages
                var arr = str.items;
                if (arr.length == 0) {
                    $("#bn").html("暂无数据")
                } else if (arr.length > 0) {
                    $("#an").hide()
                }
                if (arr.length < 10 && arr.length > 0) {
                    $("#bn").hide()
                }
                if (arr.length >= 10) {
                    $("#bn").show()
                }
                var lisy = [];
                for (var i = 0; i < arr.length; i++) {
                    var date = new Date(arr[i].send_time);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    z = date.getHours() + ':';
                    m = date.getMinutes();
                    var timen = Y + M + D + z + m;
                    var vnc = parseInt(arr[i].exp_ord_clt_state);
                    if (vnc == 4) {
                        var sare = "已签收"
                        lisy = '<div class="dadv">' +
                            '<section class="left">' +
                            '<img class="logo" src="' + arr[i].express_logo + '">' +
                            '<p class="p">' + arr[i].express_name + '&nbsp;&nbsp;' + arr[i].express_number + '</p>' +
                            '<span class="opend">' + arr[i].add_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].add_mobile_phone + '">' + arr[i].add_mobile_phone + '</a>' + '</span>' + '<br>' +
                            '<span class="address">' + arr[i].add_detail_address + '</span>' +
                            '<input id="iter" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '</section>' +
                            '<section class="right">' +
                            '<span class="sh">' + timen + '</span>' + '<br>' +
                            '<p class="achieve">' + sare + '</p>' +
                            '<input id="it" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '<em>' + '</em>' +
                            '</section>'
                            + '</div>'
                        $(".clact").append(lisy);
                    }
                    if (vnc == 5) {
                        var sare = "包裹超时"
                        lisy = '<div class="dadv">' +
                            '<section class="left">' +
                            '<img class="logo" src="' + arr[i].express_logo + '">' +
                            '<p class="p">' + arr[i].express_name + '&nbsp;&nbsp;' + arr[i].express_number + '</p>' +
                            '<span class="opend">' + arr[i].add_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].add_mobile_phone + '">' + arr[i].add_mobile_phone + '</a>' + '</span>' + '<br>' +
                            '<span class="address">' + arr[i].add_detail_address + '</span>' +
                            '<input id="iter" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '</section>' +
                            '<section class="right">' +
                            '<span class="sh">' + timen + '</span>' + '<br>' +
                            '<p class="achieve achee">' + sare + '</p>' +
                            '<input id="it" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '<em>' + '</em>' +
                            '</section>'
                            + '</div>'
                        $(".clact").append(lisy);
                        $(".achee").css({"color": "red", "background": "#fff"})
                    }

                }
                $(".clact .right").click(function () {
                    var center = $(this).children("#it").val();
                    var ctnt = center.split(",");
                    var clta = ctnt[0]
                    if (clta == 4) {
                        var storage = window.sessionStorage;
                        var detail = {
                            detail: ctnt[1]
                        }
                        var d = JSON.stringify(detail);
                        storage.setItem("detail", d);
                        window.location.href = "detail.html";
                    }
                })
                $(".clact .left").click(function () {
                    var center = $(this).children("#iter").val();
                    var ctnt = center.split(",");
                    var clta = ctnt[0]
                    if (clta == 4) {
                        var storage = window.sessionStorage;
                        var detail = {
                            detail: ctnt[1]
                        }
                        var d = JSON.stringify(detail);
                        storage.setItem("detail", d);
                        window.location.href = "detail.html";
                    } else {

                    }
                })
            }
        })
        $("#bn").click(function () {
            t = t + 1;
            var json12 = {
                "page_size": "10",
                "page_no": t
            };
            var res = getUrl("/suyh/app/3/token/findHasBeenFinishExp", json12, token);
            if (res != "") {
                var data = JSON.parse(res);
                var str = data.obj;
                var atr = str.total_pages
                var arr = str.items;
                var lisy = [];
                for (var i = 0; i < arr.length; i++) {
                    var date = new Date(arr[i].send_time);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                    D = date.getDate() + ' ';
                    z = date.getHours() + ':';
                    m = date.getMinutes();
                    var timen = Y + M + D + z + m;
                    var vnc = parseInt(arr[i].exp_ord_clt_state);
                    if (vnc == 4) {
                        var sare = "已签收"
                        lisy = '<div class="dadv">' +
                            '<section class="left">' +
                            '<img class="logo" src="' + arr[i].express_logo + '">' +
                            '<p class="p">' + arr[i].express_name + '&nbsp;&nbsp;' + arr[i].express_number + '</p>' +
                            '<span class="opend">' + arr[i].add_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].add_mobile_phone + '">' + arr[i].add_mobile_phone + '</a>' + '</span>' + '<br>' +
                            '<span class="address">' + arr[i].add_detail_address + '</span>' +
                            '<input id="iter" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '</section>' +
                            '<section class="right">' +
                            '<span class="sh">' + timen + '</span>' + '<br>' +
                            '<p class="achieve">' + sare + '</p>' +
                            '<input id="it" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '<em>' + '</em>' +
                            '</section>' + '</div>'
                        $(".clact").append(lisy);
                    }
                    if (vnc == 5) {
                        var sare = "包裹超时"
                        lisy = '<div class="dadv">' +
                            '<section class="left">' +
                            '<img class="logo" src="' + arr[i].express_logo + '">' +
                            '<p class="p">' + arr[i].express_name + '&nbsp;&nbsp;' + arr[i].express_number + '</p>' +
                            '<span class="opend">' + arr[i].add_name + '&nbsp;&nbsp;' + '<a class="Phone" href="tel:' + arr[i].add_mobile_phone + '">' + arr[i].add_mobile_phone + '</a>' + '</span>' + '<br>' +
                            '<span class="address">' + arr[i].add_detail_address + '</span>' +
                            '<input id="iter" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '</section>' +
                            '<section class="right">' +
                            '<span class="sh">' + timen + '</span>' + '<br>' +
                            '<p class="achieve achee">' + sare + '</p>' +
                            '<input id="it" type="hidden" value="' + arr[i].exp_ord_clt_state + ',' + arr[i].exp_ord_clt_id + '">' +
                            '<em>' + '</em>' +
                            '</section>'
                            + '</div>'
                        $(".clact").append(lisy);
                        $(".achee").css({"color": "red", "background": "#fff"})
                    }
                }
                $(".clact .right").click(function () {
                    var center = $(this).children("#it").val();
                    var ctnt = center.split(",");
                    var clta = ctnt[0]
                    if (clta == 4) {
                        var storage = window.sessionStorage;
                        var detail = {
                            detail: ctnt[1]
                        }
                        var d = JSON.stringify(detail);
                        storage.setItem("detail", d);
                        window.location.href = "detail.html";
                    }
                })
                $(".clact .left").click(function () {
                    var center = $(this).children("#iter").val();
                    var ctnt = center.split(",");
                    var clta = ctnt[0]
                    if (clta == 4) {
                        var storage = window.sessionStorage;
                        var detail = {
                            detail: ctnt[1]
                        }
                        var d = JSON.stringify(detail);
                        storage.setItem("detail", d);
                        window.location.href = "detail.html";
                    }
                })
                if (arr.length < 10) {
                    $("#bn").hide()
                }
            }
        })
    }
})
$(".tab li").click(function () {
    var $this = $(this),
        index = $this.index();
    $this.addClass("active").siblings("li").removeClass("active");
    $(".products .main").eq(index).addClass("selected").siblings("div").removeClass("selected");
});