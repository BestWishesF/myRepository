/**
 * Created by Administrator on 2017/3/25 0025.
 */

var wxJSSDK ={//声明微信全局变量，防止污染外部环境
    isReady:false,//微信JS SDK是否初始化完毕
    config:{
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: "", // 必填，公众号的唯一标识
        timestamp: "", // 必填，生成签名的时间戳
        nonceStr: "", // 必填，生成签名的随机串
        signature: "",// 必填，签名，见附录1
        jsApiList: [
            "scanQRCode","getLocation"
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    },
    /*
     函数功能：初始化
     */
    init:function(){
        if(!wx){//验证是否存在微信的js组件
            alert("微信接口调用失败，请检查是否引入微信js！");
            return;
        }
        var that = this;//保存当前作用域，方便回调函数使用
        var third_id=sessionStorage.getItem("third_id");
        var tsHtThird={
            third_id:third_id
        }
        $.ajax({
            contentType: "application/json; charset=utf-8",
            headers: getHeader(),
            url:"/suyh/app/3/findTsHtThirdDto",
            type: "POST",
            data:JSON.stringify(tsHtThird),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if(result.mark=='0'){
                    var appid=result.obj;
                    function GetJson() {
                        var json = {
                            "appid": appid,
                            "http_url":window.location.href
                        };
                        return json;
                    }
                    $.ajax({
                        contentType: "application/json; charset=utf-8",
                        headers: getHeader(),
                        type: 'post',
                        url: '/suyh/app/3/obtainJsSdk',
                        dataType: 'json',
                        data: JSON.stringify(GetJson()),
                        accpet: "application/json",
                        success: function (result) {
                            var packageValJson = result.obj;
                            that.config.appId = packageValJson.appid; // 必填，公众号的唯一标识
                            that.config.timestamp = packageValJson.time_stamp; // 必填，生成签名的时间戳
                            that.config.nonceStr = packageValJson.nonce_str; // 必填，生成签名的随机串
                            that.config.signature = packageValJson.signature;// 必填，签名，见附录1
                            that.initWx(function(){//初始化微信接口
                                //初始化完成后的执行
                            });
                        }
                    });
                }
            }
        })
    },
    initWx:function(call, errorCall){//初始化微信接口
        var that = this;
        wx.config(this.config);//初始化配置
        /*config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
         *config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
         *则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，
         *则可以直接调用，不需要放在ready函数中。
         * */
        wx.ready(function(){
            that.isReady = true;
            console.log("初始化成功")
            call && call();
        });
        /*config信息验证失败会执行error函数，如签名过期导致验证失败，
         *具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，
         * 对于SPA可以在这里更新签名。
         * */
        wx.error(function(res){
            that.isReady = "false";
            errorCall && errorCall();
        });
    }
}
//执行初始化
wxJSSDK.init();