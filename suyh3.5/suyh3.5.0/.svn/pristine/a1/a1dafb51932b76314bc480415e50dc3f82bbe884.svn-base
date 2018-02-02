<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
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
                代理人审核
            </h1>

        </div><!--pageheader-->

        <div class="contentwrapper">
            <div class="details">
                <div class="search_input">
                    <p>
                        审核结果：
                        <input type="radio" name="apply_state" value="1" id="service_content_yes" checked="checked"
                               onclick="$('#apply_type').show()"/>通过
                        <input type="radio" id="service_content_no" value="3" name="apply_state"
                               onclick="$('#apply_type').hide()"/>不通过
                    </p>
                    <div id="apply_type">
                        <p>
                            性别：
                            <input type="radio" name="agent_sex" value="1" checked="checked"/>男
                            <input type="radio" value="2" name="agent_sex"/>女
                        </p>
                        <p>
                            类别：
                            <input type="radio" name="agent_type" value="1" checked="checked"/>公司员工
                            <input type="radio" value="2" name="agent_type"/>兼职人员
                        </p>
                        <p>
                            地址：
                            <select id="province" style="min-width: 10%;width: 20%;">
                                <c:forEach items="${retInfo.obj.province}" var="bean">
                                    <option value="${bean.dict_id}" <c:if test="${bean.dict_id==retInfo.obj.agent_apply.agent_province}">selected</c:if>>${bean.province_name}</option>
                                </c:forEach>
                            </select>省
                            <select id="city" style="min-width: 10%;width: 20%;">
                                <c:forEach items="${retInfo.obj.city}" var="bean">
                                    <option value="${bean.dict_id}" <c:if test="${bean.dict_id==retInfo.obj.agent_apply.agent_city}">selected</c:if>>${bean.city_name}</option>
                                </c:forEach>
                            </select>市
                            <select id="area_id" style="min-width: 10%;width: 20%;">
                                <c:forEach items="${retInfo.obj.area}" var="bean">
                                    <option value="${bean.dict_id}" <c:if test="${bean.dict_id==retInfo.obj.agent_apply.agent_region}">selected</c:if>>${bean.area_name}</option>
                                </c:forEach>
                            </select>区
                        </p>
                        <p>
                            区域：
                            <select id="divide_id" style="min-width: 10%;width: 60%;">
                                <c:forEach items="${retInfo.obj.divide}" var="bean">
                                    <option value="${bean.divide_id}" <c:if test="${bean.divide_id==retInfo.obj.agent_apply.divide_id}">selected</c:if>>${bean.divide_name}</option>
                                </c:forEach>
                            </select>
                        </p>
                    </div>
                    <div style="margin-top: 10px;">
                        审核描述：<textarea id="apply_fail_reason"></textarea>
                    </div>
                    <div class="styleButton">
                        <input type="button" value="保存" class="btn_save" id="submit_bt"/>
                        <input type="button" value="取消" class="btn_cancel"/>
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
                    if (!(/^.{1,127}$/.test($("#apply_fail_reason").val()))) {
                        alert("审核描述为1-128个字符.");
                        return false;
                    }

                    var dataJson = {
                        apply_state:$('input[name="apply_state"]:checked').val(),
                        apply_fail_reason:$("#apply_fail_reason").val(),
                        apply_id:${retInfo.obj.apply_id},
                        area_id:$("#area_id").val(),
                        agent_sex:$('input[name="agent_sex"]:checked').val(),
                        agent_type:$('input[name="agent_type"]:checked').val(),
                        divide_id:$("#divide_id").val()
                    }

                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/agent/apply/examine',
                        dataType: 'json',
                        data: JSON.stringify(dataJson),
                        contentType: "application/json; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                var href;
                                if($('input[name="apply_state"]:checked').val() == 1){
                                    href = "${ctx}/admin/agent/apply/list?currentPage=1&apply_state=1&father_id=${father_id}";
                                }else {
                                    href = "${ctx}/admin/agent/apply/list?currentPage=1&apply_state=3&father_id=${father_id}";
                                }

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

            <br/><br/>
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->

</body>
</html>
