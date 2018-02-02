/**
 * Created by Administrator on 2017/3/27 0027.
 */
function getHeader(token) {
    var time = Date.parse(new Date());
    var hash = hex_md5(time + "hotol");
    if(token == null){
        token = "";
    }
    var headers = {
        "token":token,
        "version":"5",
        "client_type":"3",
        "Timestamp":time,
        "SignInfo":hash,
        "Access-Control-Allow-Origin":"*"
    };
    return headers;
}

var getToken = {
    init:function(){
        var third_id=getUrlParam("third_id");
        sessionStorage.setItem("third_id",third_id);
        var access_code=getUrlParam('code');
        var code_data={
            code:access_code,
            third_id:third_id
        }
        $.ajax({
            contentType: "application/json; charset=utf-8",
            headers: getHeader(),
            url:"/suyh/app/3/obtainWXPersonal",
            type: "POST",
            data:JSON.stringify(code_data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: function (result) {
                if(result.mark == "0"){
                    var token = result.obj.token;
                    var openid=result.obj.openid;
                    sessionStorage.setItem("openid", openid);
                    sessionStorage.setItem("token", token);
                    if(token==""||token==null){
                        window.location.href = "bund.html";
                    }else {
                        var role=result.obj.memb_role;
                        var discount=result.obj.memb_discount;
                        sessionStorage.setItem("role", role);
                        sessionStorage.setItem("discount", discount);
                    }
                }else {
                    alert(result.tip);
                }
            }
        })
    }
}


var MaskUtil = (function () {
    var $mask, $maskMsg;
    var defMsg = '<img id="load" src="../img/load.gif" alt=""/>';
    function init() {
        if (!$mask) {
            $mask = $("<div></div>")
                .css({
                    'position': 'absolute'
                    , 'left': '0'
                    , 'top': '0'
                    , 'width': '100%'
                    , 'height': '16rem'
                    , 'opacity': '1'
                    , 'filter': 'alpha(opacity=30)'
                    , 'display': 'none'
                    , 'background-color': '#fff'
                })
                .appendTo("body");
        }
        if (!$maskMsg) {
            $maskMsg = $("<div></div>")
                .css({
                    'position': 'absolute'
                    , 'top': '35%'
                    , 'left': '35%'
                    , 'width': 'auto'
                    , 'display': 'none'
                    , 'background-color': '#fff'
                    /* ,'margin-left':'0.8rem'*/
                })
                .appendTo("body");
        }
        $mask.css({width: "100%", height: $(document).height("16rem")});
        var scrollTop = $(document.body).scrollTop();
        $maskMsg.css({
            /*    left:( $(document.body).outerWidth(true) - 190 ) / 2*/
            /* ,top:( ($(window).height() - 45) / 2 ) + scrollTop*/
        });
    }
    return {
        mask: function (msg) {
            init();
            $mask.show();
            $maskMsg.html(msg || defMsg).show();
        }
        , unmask: function () {
            $mask.hide();
            $maskMsg.hide();
        }
    }
}());