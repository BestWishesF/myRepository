$(document).ready(function () {
   var token = getcookie("sra");
    var json = {
        "region_id": "0"
    };
    var res = getUrl("/suyh/app/3/token/findSendExpInfo", json, token);
    if(res != ""){
        var data = JSON.parse(res);
        if(data.mark == "0") {
            var str = data.obj;
            var star = str.express_category;
            for (var i = 0; i < star.length; i++) {
                var html = [];
                html += '<div class="lal">';
                html += '<input class="input" type="radio" id="a' + star[i].dict_id + '" name="opt" value="' + star[i].code_name + ',' + star[i].dict_id + '" />';
                html += '<label for="a' + star[i].dict_id + '" class="radio"><span>' + star[i].code_name + '</span></label>';
                html += '</div>';
                $(".list").append(html);
            }
            $("#btn").click(function () {
                var val = $('input:radio[name="opt"]:checked').val();
                /*选中物品类型*/
                var ss = val.split(",");
                var srr = new Array();
                srr.push(ss[0]);
                srr.push(ss[1]);
                var storage = window.sessionStorage;
                /*保存数据*/
                var goods = {
                    name: srr[0],
                    id: srr[1]
                };
                var d = JSON.stringify(goods);
                storage.setItem("goods", d);
                window.location.href = "send.html";
            })
        }
    }
})
















