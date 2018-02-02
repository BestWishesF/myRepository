<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                修改数据
            </h1>
        </div><!--pageheader-->
        <div class="contentwrapper">
            <div class="details">
                <div    class="search_input">
                    <p>
                        名称:<input type="text" name="divide_name" id="divide_name" value="${retInfo.obj.divide.divide_name}" /></p>
                    <p>
                        状态:<input type="radio" name="divide_state" value="0" <c:if test="${retInfo.obj.divide.divide_state == 0}">checked="checked"</c:if> />可用
                        <input type="radio" name="divide_state" value="1" <c:if test="${retInfo.obj.divide.divide_state == 1}">checked="checked"</c:if> />不可用</p>
                    <div id="mapList" class="search_input">
                        <p>
                            <button class="submit radius2" id="divide_start" <c:if test="${retInfo.obj.divide.divide_state == 0}">style="display: none;"</c:if> >开始绘制</button>
                            <button class="submit radius2" id="divide_newly" <c:if test="${retInfo.obj.divide.divide_state == 1}">style="display: none;"</c:if> >重新绘制</button>
                        </p>
                        <div id="allmap" style="height:800px;width:100%;"></div>
                    </div>
                    <p>
                        <button class="submit radius2" id="submit_bt">修改</button>
                    </p>
                </div>
            </div><!--details-->
        </div><!--contentwrapper-->
    </div><!-- centercontent -->
</div><!--bodywrapper-->
<script type="text/javascript">
    var divideGridList = new Array;

    $(function(){
        // 百度地图API功能
        var map = new BMap.Map("allmap");
        map.centerAndZoom("${retInfo.obj.address}", 15);
        map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
        map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
        var overlays = new Array;
        var drawingState=false;

        var alreadyCompletedStyle = {
            strokeColor: "blue",    //边线颜色。
            fillColor: "white",      //填充颜色。当参数为空时，圆形将没有填充效果。
            strokeWeight: 1,       //边线的宽度，以像素为单位。
            strokeOpacity: 0.7,	   //边线透明度，取值范围0 - 1。
            fillOpacity: 0.4,      //填充的透明度，取值范围0 - 1。
            strokeStyle: 'solid' //边线的样式，solid或dashed。
        }
        var haveInHandStyle = {
            strokeColor: "red",    //边线颜色。
            fillColor: "white",      //填充颜色。当参数为空时，圆形将没有填充效果。
            strokeWeight: 1,       //边线的宽度，以像素为单位。
            strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
            fillOpacity: 0.4,      //填充的透明度，取值范围0 - 1。
            strokeStyle: 'solid' //边线的样式，solid或dashed。
        }

        var k = 1;
        <c:forEach items="${retInfo.obj.divide_list}" var="divide_bean">
        var points = new Array;
        <c:if test="${divide_bean.divide_id == retInfo.obj.divide_id}">
        <c:forEach items="${divide_bean.divideGridDtoList}" var="grid_bean">
        points.push(new BMap.Point(${grid_bean.grid_longitude}, ${grid_bean.grid_latitude}))
        var grids = {
            grid_longitude: ${grid_bean.grid_longitude},
            grid_latitude: ${grid_bean.grid_latitude},
            grid_sort: k
        }
        divideGridList.push(grids);
        k = k + 1;
        </c:forEach>
        var have_polygon = new BMap.Polygon(points, haveInHandStyle);  //创建多边形
        map.addOverlay(have_polygon);    //创建多边形
        overlays.push(have_polygon);
        </c:if>
        <c:if test="${divide_bean.divide_id != retInfo.obj.divide_id}">
        <c:forEach items="${divide_bean.divideGridDtoList}" var="grid_bean">
        points.push(new BMap.Point(${grid_bean.grid_longitude}, ${grid_bean.grid_latitude}))
        </c:forEach>
        map.addOverlay(new BMap.Polygon(points, alreadyCompletedStyle));  //创建多边形
        </c:if>
        </c:forEach>

        var overlaycomplete = function (e) {
            drawingState=false;
            overlays.push(e.overlay);
            var pointList = e.overlay.getPath();
            divideGridList = new Array;
            for (var i = 0; i < pointList.length; i++) {
                var grid = {
                    grid_longitude: pointList[i].lng,
                    grid_latitude: pointList[i].lat,
                    grid_sort: i+1
                }
                divideGridList.push(grid);
            }
        };

        //实例化鼠标绘制工具
        var drawingManager = new BMapLib.DrawingManager(map, {
            isOpen: false, //是否开启绘制模式
            enableDrawingTool: false, //是否显示工具栏
            drawingToolOptions: {
                anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                offset: new BMap.Size(5, 5), //偏离值
                drawingModes: [BMAP_DRAWING_POLYGON]
            },
            polygonOptions: haveInHandStyle, //多边形的样式
        });

        //添加鼠标绘制工具监听事件，用于获取绘制结果
        drawingManager.addEventListener('overlaycomplete', overlaycomplete);

        $("#divide_start").click(function () {
            drawingManager.open();
            $("#divide_start").hide();
            $("#divide_newly").show();
            drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
            drawingState=true;
        })

        map.addEventListener("click", function (e) {
            if(drawingState){
                var pt = new BMap.Point(e.point.lng, e.point.lat);
                var result = false;
                <c:forEach items="${retInfo.obj.divide_list}" var="divide_bean">
                <c:if test="${divide_bean.divide_id != retInfo.obj.divide_id}">
                var points = new Array;
                <c:forEach items="${divide_bean.divideGridDtoList}" var="grid_bean">
                points.push(new BMap.Point(${grid_bean.grid_longitude}, ${grid_bean.grid_latitude}))
                </c:forEach>
                result = BMapLib.GeoUtils.isPointInPolygon(pt, new BMap.Polygon(points));
                </c:if>
                </c:forEach>
                if (result) {
                    alert("该区域已存在.");
                    $("#divide_newly").click();
                }
            }
        })

        $("#divide_newly").click(function () {
            map.removeOverlay(overlays[0]);
            overlays = new Array;
            divideGridList=new Array;
            drawingManager.open();
            drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
            drawingState=true;
        })
    })


    $("#submit_bt").click(function(){
        var divide = {
            divide_id: ${retInfo.obj.divide.divide_id},
            region_id: ${retInfo.obj.divide.region_id},
            divide_name: $("#divide_name").val(),
            divide_state: $('input:radio[name="divide_state"]:checked').val(),
            divideGridDtoList: divideGridList
        }

        $.ajax({
            type: 'post',
            url: '${ctx}/admin/dict/divide/update',
            dataType: 'json',
            data: JSON.stringify(divide),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.mark == "0") {
                    alert("成功");
                    var href = "${ctx}/admin/dict/divide/jump/list" + "?currentPage=1&region_id=${retInfo.obj.divide.region_id}&father_id=102";
                    $.ajax({
                        type:'GET',
                        url:href,
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                } else {
                    alert(data.tip);
                }
            }
        });
    });
</script>
</body>
</html>
