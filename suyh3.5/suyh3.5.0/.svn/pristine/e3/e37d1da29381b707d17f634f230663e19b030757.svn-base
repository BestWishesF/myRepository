$(document).ready(function () {
    var x = document.getElementsByName("opt");  //获取所有name的元素
    for (var i = 0; i < x.length; i++) { //对所有结果进行遍历，如果状态是被选中的，则将其选择取消
        if (x[i].checked == true) {
            x[i].checked = false;
        }
    }
    var minus = document.getElementById("minus");
    var number = document.getElementById("txt");
    var plus = document.getElementById("plus");
    $(minus).click(function () {
        if (number.value <= 2 + "元") {

            //如果文本框的值小于1,则设置值为1
            number.value = 2 + "元";

        } else {
            number.value = parseInt(number.value) - 1 + "元";
        }
    })
    plus.onclick = function () {
        number.value = parseInt(number.value) + 1 + "元";
    };
    $(".radio").click(function () {
        var toggle = $(this).siblings(".pull").css("display") == "none" ? true : false;
        $(".pull").hide();
        if (toggle) {
            $(this).siblings(".pull").show();
        } else {
            $(this).siblings(".pull").hide();
        }
    })

    var token = getcookie("sra");
    /*======================================获取上门时间====================================*/
    var json = {
        "code_type": "today"
    };
    var res = getUrl("/suyh/app/3/token/findSendExpInfo", json, token);
    if (res != "") {
        var dele = JSON.parse(res);
        var dath = new Date();
        var hour = (dath.getHours()).toString();
        var seperator2 = ":"
        var min = (dath.getMinutes()).toString();
        var currentTime = hour + min;
        currentTime = parseInt(currentTime);
        var starTime = dele.obj.work_time.start_time;
        starTime = starTime.replace(":", "");
        starTime = parseInt(starTime);
        var endTime = dele.obj.work_time.end_time;
        endTime = endTime.replace(":", "");
        endTime = parseInt(endTime);
        if (starTime > currentTime && currentTime > endTime) {
            $("#yi").css("background", "#e5e5e5")
            $("#yi span").css("color", "#999")
        }
        if (starTime > currentTime && currentTime > endTime) {
            $("#er").css("background", "#e5e5e5");
            $("#er span").css("color", "#999");
            $("#er .biao").remove();
        }
        if (starTime > currentTime && currentTime > endTime) {
            $("#san").css("background", "#e5e5e5");
            $("#san .pull").remove();
            $("#san span").css("color", "#999")
        }

        $(".home1").click(function () {
            if (starTime > currentTime || currentTime > endTime) {
                $("#a").prop("checked", false);
            } else {
                $("#a").prop("checked", true);
            }
        })
        $(".home2").click(function () {
            if (starTime > currentTime || currentTime > endTime) {
                $("#b").prop("checked", false);
            } else {
                $("#b").prop("checked", true);
            }
        })
        $(".home3").click(function () {
            if (starTime > currentTime || currentTime > endTime) {
                $("#c").prop("checked", false);
            } else {
                $("#c").prop("checked", true);
            }
        })
        $(".home4").click(function () {
            $("#d").prop("checked", true);
        })
        var str_dath = hour + seperator2 + min
        var see = dele.mark;
        var stae = dele.tip;
        var data = dele.obj.today_collect_time;
        if (see == 1) {
            $(".ban").hide()
            alert(stae)
        } else {
            $(".ban").show()
        }

        var str_t = "";
        for (var i = 0; i < data.length; i++) {
            str_t = str_t + '<input type="radio" id="it_t" name="list" value="' + data[i].code_name + '">' +
                '<label id="itn" class="int_t" for="it_t">' + data[i].code_name + '&nbsp;&nbsp;' + '</label>';
        }
        $("#pull_t").append(str_t);
        /*var timent = dele.obj[0].code_name;
        if (str_dath >= timent) {
            $("#str_time").hide()
        }*/
        $(".int_t").click(function () {
            $(this).siblings().css({"background": "", "color": ""}).end()
                .css({"background": "#61d1b7", "color": "#fff"});
        })

        var json = {
            "code_type": ""
        };
        var resp = getUrl("/suyh/app/3/token/findCollectTime", json, token);
        if (resp != "") {
            var data = JSON.parse(resp);
            var data = data.obj;
            var str = "";
            for (var i = 0; i < data.length; i++) {
                console.log(i)
                str = str + '<input type="radio" id="it" name="list" value="' + data[i].code_name + '">' +
                    '<label class="int" for="it">' + data[i].code_name + '&nbsp;&nbsp;' + '</label>';
            }
            $("#pull_w").append(str);
            $(".int").click(function () {
                $(this).siblings().css({"background": "", "color": ""}).end()
                    .css({"background": "#61d1b7", "color": "#fff"});
            })
        }

        var quickly = $("#a").val();
        var quickl = $("#b").val();
        var input3 = $("#c").val();
        var input4 = $("#d").val();
        var input5 = $("#t").val();
        /*======================================保存提交数据===================================*/
        $("#preserved").click(function () {
            var expense = $("#txt").val();
            var bb = expense.split("元")
            var val = $('input:radio[name="opt"]:checked').val();
            if (val == null) {
                alert("请选择日期!");
                return false;
            }
            else {
            }
            /*判断选择时间*/
            if (val == quickly) {
                var srr = new Array();
                srr.push(val);
                var storage = window.sessionStorage;
                var daty = {
                    expense: 0,
                    date: srr[0],
                    home: ""
                }
                var d = JSON.stringify(daty);
                storage.setItem("daty", d);
            }
            if (val == quickl) {
                var srr = new Array();
                srr.push(val);
                srr.push(expense);
                var storage = window.sessionStorage;
                var daty = {
                    expense: parseInt(bb[0]),
                    date: srr[0],
                    home: ""
                }
                var d = JSON.stringify(daty);
                storage.setItem("daty", d);
            }
            if (val == input3) {
                var list = $('input:radio[name="list"]:checked').val();
                var srr = new Array();
                srr.push(expense);
                srr.push(val);
                srr.push(list);
                var homet = srr[2]
                if (homet == "" || homet == null) {
                    alert("请选择时间")
                    return false;
                }
                var storage = window.sessionStorage;
                var daty = {
                    expense: 0,
                    date: srr[1],
                    home: srr[2]
                }
                var d = JSON.stringify(daty);
                storage.setItem("daty", d);
            }
            if (val == input4) {
                var list = $('input:radio[name="list"]:checked').val();
                var srr = new Array();
                srr.push(expense);
                srr.push(val);
                srr.push(list);
                var homet = srr[2]
                if (homet == "" || homet == null) {
                    alert("请选择时间")
                    return false;
                }
                var storage = window.sessionStorage;
                var daty = {
                    expense: 0,
                    date: srr[1],
                    home: srr[2]
                }
                var d = JSON.stringify(daty);
                storage.setItem("daty", d);
            }
            if (val == input5) {
                var list = $('input:radio[name="list"]:checked').val();
                var srr = new Array();
                srr.push(expense);
                srr.push(val);
                srr.push(list);
                var storage = window.sessionStorage;
                var daty = {
                    expense: 0,
                    date: srr[1],
                    home: srr[2]
                }
                var d = JSON.stringify(daty);
                storage.setItem("daty", d);
            }
            window.location.href = "send.html";

        });
    }
})








