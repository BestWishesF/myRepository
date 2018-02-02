$(document).ready(function() {
    var choose_city = sessionStorage.getItem('choose_city');
    $("#choose_city").val(choose_city);
    sessionStorage.removeItem("choose_city");
    var lon=0;
    var lat=0;
    if($("#choose_city").val() != "" && $("#choose_city").val() != null){
        createMap(0,0,$("#choose_city").val())
    }else {
        var third_id=getcookie("third_id");
        var tsHtThird = {
            third_id: third_id
        };
        var res = getUrl("/suyh/app/3/findTsHtThirdDto", tsHtThird, "");
        if(res != ""){
            var result = JSON.parse(res);
            if (result.mark == '0') {
                var appid = result.obj;
                var http_url = window.location.href;
                var json = {
                    "appid": appid,
                    "http_url": http_url
                };
                var resp = getUrl("/suyh/app/3/obtainJsSdk", json, "");
                if(resp != ""){
                    var dresult = JSON.parse(resp);
                    var packageValJson = dresult.obj;
                    wx.config({
                        debug: false,
                        appId: packageValJson.appid, // 必填，公众号的唯一标识
                        timestamp: packageValJson.time_stamp, // 必填，生成签名的时间戳
                        nonceStr: packageValJson.nonce_str, // 必填，生成签名的随机串
                        signature: packageValJson.signature,// 必填，签名，见附录1
                        jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

                    });
                    wx.checkJsApi({
                        jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                        success: function (res) {

                        }
                    });
                    wx.ready(function () {
                        wx.getLocation({
                            type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                            success: function (res) {
                                var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                var speed = res.speed; // 速度，以米/每秒计
                                var accuracy = res.accuracy; // 位置精度
                                $.ajax({
                                    url: 'http://api.map.baidu.com/geoconv/v1/?coords=' + longitude + ',' +latitude + '&from=1&to=5&ak=IstZcZGxIWMgybcvDntmTbvM',
                                    type: "get",
                                    dataType: "jsonp",
                                    jsonp: "callback",
                                    success: function (data) {
                                        if(data.status == 0){
                                            var location = data.result[0];
                                            createMap(location.x, location.y,"");
                                        }else {
                                            alert("请重新定位.")
                                        }
                                    }
                                });

                            }
                        });
                    });
                    wx.error(function (res) {
                        alert("请允许定位.")
                    });
                }
            }
        }
    }
})

function createMap(lng,lat,city_name){
    var map = new BMap.Map("map");    // 创建Map实例

    if(city_name == ""){
        var point = new BMap.Point(lng,lat);//创建地理点坐标
        map.centerAndZoom(point, 17);  // 初始化地图,设置中心点坐标和地图级别
        var marker = new BMap.Marker(point);  // 创建标注
        map.addOverlay(marker);
        marker.enableDragging();
        marker.addEventListener("dragend", function(e){
            var pt = e.point;
            periphery(pt);
        });
        periphery(point);
    }else {
        addressMap(city_name, city_name);
    }

    function addressMap(city_name, address) {
        // 创建地址解析器实例
        var myGeo = new BMap.Geocoder();
        // 将地址解析结果显示在地图上,并调整地图视野
        myGeo.getPoint(address, function(point){
            if (point) {
                map.centerAndZoom(point, 17);
                var marker = new BMap.Marker(point);  // 创建标注
                map.addOverlay(marker);
                marker.enableDragging();
                marker.addEventListener("dragend", function(e){
                    var pt = e.point;
                    periphery(pt);
                });
                periphery(point);
            }else{
                alert("您选择地址没有解析到结果!");
            }
        }, city_name);
    }

    function periphery(pt) {
        var geoc = new BMap.Geocoder();
        geoc.getLocation(pt, function(rs){
            var pois = rs.surroundingPois;
            var city = rs.addressComponents.city;
            var street = rs.addressComponents.street;
            $("#jumpcity").html(city);
            $("#search_address").val(street);
            var storage = window.sessionStorage;
            storage.setItem('city',  city);
            var html=[];
            for(var i=0;i<pois.length;i++){
                html += "<li class='poi_li' data-id='"+i+"'>" +
                    "<h2 class='poi_title' id='poi_title" + i + "' data-id='"+i+"'>"+pois[i].title+"</h2>" +
                    "<p class='poi_address' id='poi_address" + i + "' data-id='"+i+"'>"+pois[i].address+"</p>" +
                    "<input type='hidden' id='poi_lng" + i + "' data-id='"+i+"' value='"+pois[i].point.lng+"' />" +
                    "<input type='hidden' id='poi_lat" + i + "' data-id='"+i+"' value='"+pois[i].point.lat+"' />" +
                    "<input type='hidden' id='poi_city" + i + "' data-id='"+i+"' value='"+pois[i].city+"' /></li>" ;
            }
            $("#result ul").html(html);
            $(".poi_li").click(function (e) {
                var id = e.target.dataset.id;
                var storage = window.sessionStorage;

                var flag = sessionStorage.getItem('flag');
                if(flag == "1"){
                    storage.setItem('update_title', $("#poi_title" + id).html());
                    storage.setItem('update_address',  $("#poi_address" + id).html());
                    storage.setItem('update_lng',  $("#poi_lng" + id).val());
                    storage.setItem('update_lat',  $("#poi_lat" + id).val());
                    window.location.href="sent.html";
                }else if(flag=="0"){
                    storage.setItem('add_title', $("#poi_title" + id).html());
                    storage.setItem('add_address',  $("#poi_address" + id).html());
                    storage.setItem('add_lng',  $("#poi_lng" + id).val());
                    storage.setItem('add_lat',  $("#poi_lat" + id).val());
                    window.location.href= "adder.html";
                }
            });
        })
    }

    $("#search_address").blur(function () {
        addressMap(city_name,$("#search_address").val());
    })
}
