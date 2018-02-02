<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>
<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                代理人修改
            </h1>

        </div><!--pageheader-->
        <div class="contentwrapper">
            <div class="details">
                <div class="search_input">
                    <p>代理人姓名：${retInfo.obj.agent.agent_name}</p>
                    <p>代理人手机：${retInfo.obj.agent.agent_phone}</p>
                    <p>
                        代理人类别：
                        <input type="radio" name="agent_type" value="1" <c:if test="${retInfo.obj.agent.agent_type==1}">checked="checked"</c:if>/>公司员工
                        <input type="radio" value="2" name="agent_type" <c:if test="${retInfo.obj.agent.agent_type==2}">checked="checked"</c:if>/>兼职人员
                    </p>
                    <p>
                        代理人地址：
                        <select id="province" style="min-width: 10%;width: 20%;">
                            <c:forEach items="${retInfo.obj.province}" var="bean">
                                <option value="${bean.dict_id}" <c:if test="${bean.dict_id==retInfo.obj.province_id}">selected</c:if>>${bean.province_name}</option>
                            </c:forEach>
                        </select>省
                        <select id="city" style="min-width: 10%;width: 20%;">
                            <c:forEach items="${retInfo.obj.city}" var="bean">
                                <option value="${bean.dict_id}" <c:if test="${bean.dict_id==retInfo.obj.city_id}">selected</c:if>>${bean.code_name}</option>
                            </c:forEach>
                        </select>市
                        <select id="area_id" style="min-width: 10%;width: 20%;">
                            <c:forEach items="${retInfo.obj.area}" var="bean">
                                <option value="${bean.dict_id}" <c:if test="${bean.dict_id==retInfo.obj.area_id}">selected</c:if>>${bean.code_name}</option>
                            </c:forEach>
                        </select>区
                    </p>
                    <p>
                        代理人区域：
                        <select id="divide_id" style="min-width: 10%;width: 60%;">
                            <c:forEach items="${retInfo.obj.divide}" var="bean">
                                <option value="${bean.divide_id}" <c:if test="${bean.divide_id==retInfo.obj.divide_id}">selected</c:if>>${bean.divide_name}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <div class="styleButton">
                        <input type="button" value="保存" class="btn_save" id="submit_bt"/>
                        <input type="button" value="取消" class="btn_cancel" id="btn_cancel"/>
                    </div>
                </div>
            </div>
            <c:if test="${retInfo.mark != 0}">
                <div class="data_none">${retInfo.tip}</div>
            </c:if>

            <c:if test="${retInfo.mark == 0}">
                <div style="margin:0 auto;">
                    <div id="kkpager"></div>
                </div>
            </c:if>

            <script type="text/javascript">

                $("#province").change(function () {
                    $.ajax({
                        type: 'GET',
                        url: '${ctx}/admin/dict/city?parent_id=' + $("#province").val() + '&t=' + $.now(),
                        dataType: 'html',
                        success: function (data) {
                            $("#city").html(data);
                            $.ajax({
                                type: 'GET',
                                url: '${ctx}/admin/dict/area?parent_id=' + $("#city").val() + '&t=' + $.now(),
                                dataType: 'html',
                                success: function (data) {
                                    $("#area_id").html(data);
                                    $.ajax({
                                        type: 'GET',
                                        url: '${ctx}/admin/dict/divide?region_id=' + $("#area_id").val() + '&t=' + $.now(),
                                        dataType: 'html',
                                        success: function (data) {
                                            $("#divide_id").html(data);
                                        },
                                        error: function () {
                                            alert("error");
                                        }
                                    });
                                },
                                error: function () {
                                    alert("error");
                                }
                            });
                        },
                        error: function () {
                            alert("error");
                        }
                    });
                });
                $("#city").change(function () {
                    $.ajax({
                        type: 'GET',
                        url: '${ctx}/admin/dict/area?parent_id=' + $("#city").val() + '&t=' + $.now(),
                        dataType: 'html',
                        success: function (data) {
                            $("#area_id").html(data);
                            $.ajax({
                                type: 'GET',
                                url: '${ctx}/admin/dict/divide?region_id=' + $("#area_id").val() + '&t=' + $.now(),
                                dataType: 'html',
                                success: function (data) {
                                    $("#divide_id").html(data);
                                },
                                error: function () {
                                    alert("error");
                                }
                            });
                        },
                        error: function () {
                            alert("error");
                        }
                    });
                });

                $("#area_id").change(function () {
                    $.ajax({
                        type: 'GET',
                        url: '${ctx}/admin/dict/divide?region_id=' + $("#area_id").val() + '&t=' + $.now(),
                        dataType: 'html',
                        success: function (data) {
                            $("#divide_id").html(data);
                        },
                        error: function () {
                            alert("error");
                        }
                    });
                })

                $("#submit_bt").click(function () {
                    var dataJson = {
                        agent_id:${retInfo.obj.agent_id},
                        area_id:$("#area_id").val(),
                        agent_type:$('input[name="agent_type"]:checked').val(),
                        divide_id:$("#divide_id").val()
                    }

                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/agent/updateAgent',
                        dataType: 'json',
                        data: JSON.stringify(dataJson),
                        contentType: "application/json; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                $.ajax({
                                    type:'GET',
                                    url:"${ctx}/admin/agent/list?currentPage=1&agent_state=2&father_id=15",
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

                $("#btn_cancel").click(function () {
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/agent/list?currentPage=1&agent_state=2&father_id=15",
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                })
            </script>

            <br/><br/>
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->

</body>
</html>
