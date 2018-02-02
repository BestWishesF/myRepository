<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>

<div class="zhezhao" id="refund_zhezhao"></div>
<div class="Mask2" id="Mask1" style="display: none;position: fixed;">
    <div id="update">
        <dl style="margin-left: 15px;">发件人信息</dl>
        <hr />
        <dl class="fill_singleChoice" id="send_info">
            <input id="refund_id" type="hidden">
            <p>
                <select id="refund_state" style="line-height: 30px;height: 30px;">
                    <option value="0">通过</option>
                    <option value="2">不通过</option>
                </select>
            </p>
            <p id="change" style="display: none;">
                不通过原因：<br />
                <textarea id="refund_failure_cause" style="width: 90%;height: 200px;margin-left: 5%;"></textarea>
            </p>
        </dl>
    </div>
    <div class="styleButton" style="text-align: center;">
        <button class="btn_b" id="btn_save">保存</button>
        <button class="btn_b" id="btn_cancel">取消</button>
    </div>
</div>

<body class="withvernav">
<div class="bodywrapper">

    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>
                    <c:if test="${retInfo.obj.refund_state == 2}">不通过退款订单</c:if>
                    <c:if test="${retInfo.obj.refund_state == 1}">待处理退款订单</c:if>
                    <c:if test="${retInfo.obj.refund_state == 0}">已处理退款订单</c:if>
                </h3>
            </div><!--contenttitle-->
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="dyntable" width="2400px">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0" width="10%">id</th>
                    <th class="head0" width="10%">订单id</th>
                    <th class="head0" width="10%">用户id</th>
                    <th class="head0" width="10%">手机号码</th>
                    <th class="head0" width="10%">支付类型</th>
                    <th class="head0" width="10%">退款金额</th>
                    <th class="head0" width="10%">交易流水号</th>
                    <c:if test="${retInfo.obj.refund_state == 2}">
                        <th class="head0" width="20%">不通过原因</th>
                    </c:if>
                    <td width="10%">操作</td>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.refunds}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.refund_id}</td>
                            <td>${bean.exp_ord_id}</td>
                            <td>${bean.memb_id}</td>
                            <td>${bean.memb_phone}</td>
                            <td>
                                <c:if test="${bean.mfchg_channel == 1}">APP微信</c:if>
                                <c:if test="${bean.mfchg_channel == 2}">APP支付宝</c:if>
                                <c:if test="${bean.mfchg_channel == 3}">钱包余额</c:if>
                                <c:if test="${bean.mfchg_channel == 4}">微信公众号</c:if>
                            </td>
                            <td>${bean.refund_amount}</td>
                            <td>${bean.mfchg_number}</td>
                            <c:if test="${retInfo.obj.refund_state == 2}">
                                <td>${bean.refund_failure_cause}</td>
                            </c:if>
                            <td>
                                <c:if test="${retInfo.obj.refund_state != 1}">
                                    已处理
                                </c:if>
                                <c:if test="${retInfo.obj.refund_state == 1}">
                                    <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                        <c:choose>
                                            <c:when test="${funcion.func_name == '处理'}">
                                                <a class="btn_a" onclick="refund(${bean.refund_id})" href="###">处理</a> ||
                                            </c:when>
                                            <c:otherwise>
                                                <a href="#">${funcion.func_name}</a> ||
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
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
                    var href = "${ctx}/admin/found/refundList?currentPage=" + n + "&refund_state=${retInfo.obj.refund_state}&father_id=${funcions.obj.father_id}";

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

                function refund(refund_id){
                    /*$("#refund_zhezhao").fadeIn(300);
                    $("#Mask1").fadeIn(300);
                    $("#refund_id").val(refund_id);*/

                    var data = "refund_id=" + refund_id + "&refund_state=0&refund_failure_cause=通过";

                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/found/handleRefundExpOrd',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                alert("成功");

                                var href = "${ctx}/admin/found/refundList?currentPage=1&refund_state=${retInfo.obj.refund_state}&father_id=${funcions.obj.father_id}";

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

                }
                $("#btn_cancel").click(function () {
                    $("#refund_zhezhao").hide();
                    $("#Mask1").hide();
                })

                $("#refund_state").change(function () {
                    if($("#refund_state").val()==0){
                        $("#change").hide();
                    }else {
                        $("#change").show();
                    }
                })

                $("#btn_save").click(function () {
                    var data = "refund_id=" + $("#refund_id").val() +"&refund_state=" + $("#refund_state").val();
                    if($("#refund_state").val()==0){
                        data = data + "&refund_failure_cause=通过";
                    }else {
                        data = data + "&refund_failure_cause=" + $("#refund_failure_cause").val();
                    }

                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/found/handleRefundExpOrd',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                alert("成功");

                                var href = "${ctx}/admin/found/refundList?currentPage=1&refund_state=${retInfo.obj.refund_state}&father_id=${funcions.obj.father_id}";

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
                })
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