$(document).ready(function () {
    $('.star img').each(function (index) {
        var star = '../img/reted/star.png';
        var starRed = '../img/reted/star_red.png';
        var prompt = ['很差', '比较差', '一般', '比较好', '非常好'];
        this.id = index;
        var mark = 5
        var storage = window.sessionStorage;
        var mark = {
            mark: mark
        }
        var d = JSON.stringify(mark);
        storage.setItem("mark", d);
        $(this).unbind('click').click(function () {
            $('.star img').attr('src', star);
            $(this).attr('src', starRed);
            $(this).prevAll().attr('src', starRed);
            $(this).siblings('span').text(prompt[this.id]);
            var center = prompt[this.id];
            if (center == "很差") {
                var mark = 1
                var storage = window.sessionStorage;
                var mark = {
                    mark: mark
                }
                var d = JSON.stringify(mark);
                storage.setItem("mark", d);
            }
            if (center == "比较差") {
                var mark = 2
                var storage = window.sessionStorage;
                var mark = {
                    mark: mark
                }
                var d = JSON.stringify(mark);
                storage.setItem("mark", d);
            }
            if (center == "一般") {
                var mark = 3
                var storage = window.sessionStorage;
                var mark = {
                    mark: mark
                }
                var d = JSON.stringify(mark);
                storage.setItem("mark", d);
            }
            if (center == "比较好") {
                var mark = 4
                var storage = window.sessionStorage;
                var mark = {
                    mark: mark
                }
                var d = JSON.stringify(mark);
                storage.setItem("mark", d);
            }
            if (center == "非常好") {
                var mark = 5
                var storage = window.sessionStorage;
                var mark = {
                    mark: mark
                }
                var d = JSON.stringify(mark);
                storage.setItem("mark", d);
            }

        });
    });
    $("label").click(function () {
        $(this).siblings().css("background", "").end()
            .css("background", "#61d1b7");
    })
    $(".leave").keyup(function () {
        var len = $(this).val().length;
        if (len > 99) {
            $(this).val($(this).val().substring(0, 100));
        }
        var num = 100 - len;
        $("#word").text(num);
    });
    var agent = getcookie("agent");
    var agent_head = getcookie("agent_head");
    var agent_phone = getcookie("agent_phone");
    var agent_id = getcookie("agent_id")
    var token = getcookie("sra");
    var agent_name = decodeURIComponent(getcookie("agent_name"));
    html = [];
    html = '<img class="left" src="' + agent_head + '" alt="" />' +
        '<span class="bont">' + agent_name + '&nbsp;&nbsp;</span>' +
        '<span class="bont">' + agent_phone + '</span>' +
        '<img class="right" src="../img/reted/2.jpg" alt="" />'
    $(".centent").html(html);
    $("#btn").click(function () {
        var leave = $(".leave").val()
        var marke = eval('(' + sessionStorage.getItem('mark') + ")");
        if (leave == "" || leave == null) {
            alert("请输入评价内容");
            return false
        }
        var json1 = {
            agent_id: parseInt(agent),
            com_grade: marke.mark,
            com_content: leave,
            exp_ord_id: parseInt(agent_id)
        };
        var res = getUrl("/suyh/app/3/token/insertComment", json1, token);
        if (res != "") {
            alert("评价成功，感谢您的使用")
            sessionStorage.clear();
            delCookie("agent", 0)
            delCookie("agent_head", 0)
            delCookie("agent_phone", 0)
            delCookie("agent_id", 0)
            delCookie("agent_name", 0)
            window.location.href = "order.html";
        }
    })
})


