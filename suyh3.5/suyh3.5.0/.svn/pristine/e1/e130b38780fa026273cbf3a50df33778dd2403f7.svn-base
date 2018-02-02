<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>
<div class="zhezhao" id="number_zhezhao"></div>
<div class="Mask2" id="Mask2" style="display: none">
    <div id="apply_type">
        <dl class="fill_singleChoice">
            <dt>开始单号：</dt>
            <dd>
                <input id="star_number" type="text" class="longinput"/>
            </dd>
            <dt>单号数量：</dt>
            <dd>
                <input id="number_size" type="text" class="longinput"/>
            </dd>
        </dl>
        <dl class="fill_singleChoice">
            <dt>地址区域：</dt>
            <dd>
                省:<select id="province">
                <c:forEach items="${retInfo.obj.province}" var="bean">
                    <option value="${bean.dict_id}"
                            <c:if test="${retInfo.obj.pro_id==bean.dict_id}"> selected="selected" </c:if>
                    >${bean.province_name}</option>
                </c:forEach>

            </select>
                市:<select id="city">
                <c:forEach items="${retInfo.obj.city}" var="bean">
                    <option value="${bean.dict_id}"
                            <c:if test="${retInfo.obj.city_id==bean.dict_id}"> selected="selected" </c:if>
                    >${bean.city_name}</option>
                </c:forEach>
            </select>
            </dd>
        </dl>
    </div>

    <div class="styleButton">
        <input type="button" value="保存" class="btn_save" id="submit_bt"/>
        <input type="button" value="取消" class="btn_cancel" id="btn_cancel"/>
    </div>
</div>
<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <c:if test="${retInfo.obj.exp_open_state == 0}"><h3>${retInfo.obj.express_name}可使用单号</h3></c:if>
                <c:if test="${retInfo.obj.exp_open_state == 1}"><h3>${retInfo.obj.express_name}已使用单号</h3></c:if>
            </div><!--contenttitle-->
            <div class="details">
                <div class="search_input">
                    <p>
                        <button class="submit radius2" onclick="addNumber()">添加</button>
                        <a class="stdbtn" style="float: right;" onclick="canuse()">${retInfo.obj.express_name}可使用单号</a>
                        <a class="stdbtn" style="float: right;"
                           onclick="notcanuse()">${retInfo.obj.express_name}已使用单号</a>
                        <span style="float: right;">
                            地址区域：
                        省:<select id="province_choose" style="width: 20%;min-width: 5%;">
                        <c:forEach items="${retInfo.obj.province}" var="bean">
                                <option value="${bean.dict_id}"
                                        <c:if test="${retInfo.obj.pro_id==bean.dict_id}"> selected="selected" </c:if>
                                >${bean.province_name}</option>
                        </c:forEach>
                    </select>
                        市:<select id="city_choose" style="width: 20%;min-width: 5%;">
                        <c:forEach items="${retInfo.obj.city}" var="bean">
                            <option value="${bean.dict_id}"
                                    <c:if test="${retInfo.obj.city_id==bean.dict_id}">selected="selected"</c:if>
                            >${bean.city_name}</option>
                        </c:forEach>
                    </select>
                        </span>
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
                    <c:if test="${retInfo.obj.exp_open_state == 1}">
                        <th class="head0" width="20%">揽件代理人</th>
                    </c:if>
                    <th class="head0" width="20%">快递单号</th>
                    <th class="head0" width="45%">地区</th>
                    <th class="head1" width="10%">是否可用</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.expOpenNumbers}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.exp_open_id}</td>
                            <td>${bean.express_number}</td>
                            <c:if test="${retInfo.obj.exp_open_state == 1}">
                                <td>${bean.agent_name}</td>
                            </c:if>
                            <td>${bean.address}</td>
                            <td>
                                <c:if test="${bean.exp_open_state == 0}">
                                    可用
                                </c:if>
                                <c:if test="${bean.exp_open_state == 1}">
                                    已使用
                                </c:if>
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

                //init
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
                    var href = "${ctx}/admin/express/open/number/list?currentPage=" + n
                        + "&express_id=${retInfo.obj.express_id}&region_id=${retInfo.obj.region_id}&exp_open_state=${retInfo.obj.exp_open_state}&father_id=${funcions.obj.father_id}";
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

            <br/><br/>
        </div>
    </div><!-- centercontent -->
    <script type="text/javascript">
        $("#province").change(function () {
            $.ajax({
                type: 'GET',
                url: '${ctx}/admin/dict/city?parent_id=' + $("#province").val() + '&t=' + $.now(),
                dataType: 'html',
                success: function (data) {
                    $("#city").html(data);
                },
                error: function () {
                    alert("error");
                }
            });
        });

        $("#province_choose").change(function () {
            $.ajax({
                type: 'GET',
                url: '${ctx}/admin/dict/city?parent_id=' + $("#province_choose").val() + '&t=' + $.now(),
                dataType: 'html',
                success: function (data) {
                    $("#city_choose").html(data);
                },
                error: function () {
                    alert("error");
                }
            });
        });

        function addNumber() {
            $("#number_zhezhao").fadeIn(300);
            $("#Mask2").fadeIn(300);
        }

        $("#btn_cancel").click(function () {
            $("#number_zhezhao").hide();
            $("#Mask2").hide();
        })

        $("#submit_bt").click(function () {
            var data = "express_id=${retInfo.obj.express_id}" + "&region_id=" + $("#city").val() +
                    "&star_number=" + $("#star_number").val() + "&number_size=" + $("#number_size").val();
            $.ajax({
                type: 'post',
                url: '${ctx}/admin/express/open/number/insert',
                dataType: 'json',
                data: data,
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success: function (data) {
                    if (data.mark == "0") {

                        var href = "${ctx}/admin/express/open/number/list?currentPage=1" +
                            "&express_id=${retInfo.obj.express_id}&region_id=${retInfo.obj.region_id}&exp_open_state=${retInfo.obj.exp_open_state}&father_id=${funcions.obj.father_id}";
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

        function canuse() {
            var href = "${ctx}/admin/express/open/number/list?currentPage=1&express_id=${retInfo.obj.express_id}&region_id=" + $('#city_choose').val() + "&exp_open_state=0&father_id=${funcions.obj.father_id}";
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

        function notcanuse() {
            var href = "${ctx}/admin/express/open/number/list?currentPage=1&express_id=${retInfo.obj.express_id}&region_id=" + $('#city_choose').val() + "&exp_open_state=1&father_id=${funcions.obj.father_id}";
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

</div><!--bodywrapper-->

</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        kkpager.selectPage( ${retInfo.obj.currentPage})

    });
</script>