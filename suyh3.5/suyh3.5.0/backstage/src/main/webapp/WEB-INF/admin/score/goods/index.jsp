<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>

<body class="withvernav">
<div class="bodywrapper">

    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>积分商品列表</h3>
            </div><!--contenttitle-->
            <div class="details">
                <div class="search_input">
                    <div id="search" style="display: none">
                        <p>
                            获取限制:<select id="goods_type_search">
                            <option value="2">无限制</option>
                            <option value="1">每天限制一次</option>
                        </select>
                        </p>
                        <p>
                            发件区域:
                            <select id="province" style="width: 10%;min-width: 5%;">
                                <c:forEach items="${retInfo.obj.province}" var="province">
                                    <option value="${province.dict_id}">${province.province_name}</option>
                                </c:forEach>
                            </select>
                            <select id="city" style="width: 10%;min-width: 5%;">
                                <c:forEach items="${retInfo.obj.city}" var="city">
                                    <option value="${city.dict_id}">${city.city_name}</option>
                                </c:forEach>
                            </select>
                            <select id="region_id_search" style="width: 20%;min-width: 5%;">
                                <option value="0" selected>不限制</option>
                                <c:forEach items="${retInfo.obj.area}" var="area">
                                    <option value="${area.dict_id}">${area.area_name}</option>
                                </c:forEach>
                            </select>
                        </p>
                        <p>
                            快递公司:
                            <select id="express_id_search">
                                <option value="0" selected>不限制</option>
                                <c:forEach items="${retInfo.obj.express_company}" var="company">
                                    <option value="${company.dict_id}">${company.code_name}</option>
                                </c:forEach>
                            </select>
                        </p>
                        <p>
                            <button class="submit radius2" id="search_button">搜索</button>
                            <button class="submit radius2" id="search_button_end">搜索结束</button>
                        </p>
                    </div>

                    <p>
                        <button class="submit radius2" id="search_button_none">搜索</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </p>
                </div>
            </div><!--details-->
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="dyntable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0" width="5%">id</th>
                    <td class="head0" width="10%">商品图片</td>
                    <th class="head0" width="5%">名称</th>
                    <td class="head0" width="5%">副标题</td>
                    <td class="head0" width="15%">介绍</td>
                    <td class="head0" width="5%">价格</td>
                    <th class="head0" width="5%">所需积分</th>
                    <td class="head0" width="5%">优惠券金额</td>
                    <td class="head0" width="5%">有效天数</td>
                    <td class="head0" width="5%">限制</td>
                    <td class="head0" width="10%">发件区域</td>
                    <td class="head0" width="5%">发件来源</td>
                    <td class="head0" width="5%">快递公司</td>
                    <td class="head0" width="5%">限定金额</td>
                    <td class="head0" width="5%">折扣</td>
                    <td class="head0" width="5%">操作</td>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.goods}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.goods_id}</td>
                            <td><img src="${bean.goods_img_detail}" width="100%" alt=""/></td>
                            <td>${bean.goods_name}</td>
                            <td>${bean.goods_subhead}</td>
                            <td>${bean.goods_introduce}</td>
                            <td>${bean.goods_amount}</td>
                            <td>${bean.goods_score}</td>
                            <td>${bean.coupon_money}</td>
                            <td>${bean.effective_day}</td>
                            <td>
                                <c:if test="${bean.goods_type==1}">每天限制一次</c:if>
                                <c:if test="${bean.goods_type==2}">无限制</c:if>
                            </td>
                            <td>${bean.address}</td>
                            <td>
                                <c:if test="${bean.express_source==0}">不限制</c:if>
                                <c:if test="${bean.express_source==1}">公众号</c:if>
                                <c:if test="${bean.express_source==2}">APP</c:if>
                            </td>
                            <td>${bean.express_company}</td>
                            <td>
                                <c:if test="${bean.cou_limit_amount==0}">不限制</c:if>
                                <c:if test="${bean.cou_limit_amount!=0}">${bean.cou_limit_amount}</c:if>
                            </td>
                            <td>
                                <c:if test="${bean.cou_discount==1}">不打折</c:if>
                                <c:if test="${bean.cou_discount!=0}">${bean.cou_discount}</c:if>
                            </td>
                            <td>
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '上架'}">
                                            <c:if test="${bean.goods_state==1}">
                                                <a href="###" onclick="updateState(${bean.goods_id},0)">上架</a>||
                                            </c:if>
                                        </c:when>
                                        <c:when test="${funcion.func_name == '下架'}">
                                            <c:if test="${bean.goods_state==0}">
                                                <a href="###" onclick="updateState(${bean.goods_id},1)">下架</a>||
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?goods_id=${bean.goods_id}&father_id=${funcion.func_id}')">${funcion.func_name}</a> ||
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <c:if test="${retInfo.mark != 0}">
                <div class="data_none">${retInfo.tip}</div>
            </c:if>

            <c:if test="${retInfo.mark == 0}">
                <div style="margin:0 auto;">
                    <div id="kkpager"></div>
                </div>
            </c:if>

            <script type="text/javascript">

                $(function () {
                    var totalPage = ${retInfo.obj.totalPage};
                    var totalRecords = ${retInfo.obj.totalRecord};
                    var pageNo = ${retInfo.obj.currentPage};
                    var ctx = "${ctx}";
                    if (!pageNo) {
                        pageNo = 1;
                    }
                    //生成分页
                    //有些参数是可选的，比如lang，若不传有默认值
                    kkpager.generPageHtml({
                        pno: pageNo,
                        //总页码
                        total: totalPage,
                        //总数据条数
                        totalRecords: totalRecords,

                    });
                });
                function page(n) {
                    var href = "${ctx}/admin/score/goods/list?currentPage="+n+"&goods_state=${retInfo.obj.goods_state}&father_id=${funcions.obj.father_id}";
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
                }

            </script>
            <script type="text/javascript">
                $("#search_button_none").click(function () {
                    $("#search_button_none").hide();
                    $("#search").show();
                });
                $("#search_button_end").click(function () {
                    $("#search_button_none").show();
                    $("#search").hide();
                });
                $("#search_button").click(function () {
                    var href = "${ctx}/admin/score/goods/search?currentPage=1&goods_state=${retInfo.obj.goods_state}" +
                        "&goods_type=" + $("#goods_type_search").val() + "&region_id=" + $("#region_id_search").val() + "&express_id=" +
                        $("#express_id_search").val()+"&father_id=${funcions.obj.father_id}";

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
                });

                function updateState(goods_id ,goods_state){
                    var data = "goods_id=" + goods_id + "&goods_state=" + goods_state ;
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/score/goods/update/state',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                var href = "${ctx}/admin/score/goods/list?currentPage=1&goods_state=${retInfo.obj.goods_state}&father_id=${funcions.obj.father_id}";
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
                            }else{
                                alert(data.tip);
                            }
                        }

                    });
                }

                $("#province").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/city?parent_id='+$("#province").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#city").html(data);
                            $.ajax({
                                type:'GET',
                                url:'${ctx}/admin/dict/area?parent_id='+$("#city").val()+'&t='+$.now(),
                                dataType:'html',
                                success:function(data){
                                    $("#region_id_search").html(data);
                                },
                                error:function(){
                                    alert("error");
                                }
                            });
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                });
                $("#city").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/area?parent_id='+$("#city").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#region_id_search").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                });
            </script>

            <br/><br/>
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->

</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        kkpager.selectPage( ${retInfo.obj.currentPage})

    });
</script>