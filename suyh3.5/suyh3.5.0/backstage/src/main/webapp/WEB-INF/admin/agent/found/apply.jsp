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
                <c:if test="${retInfo.obj.afchg_state == 2}"><h3>待审核提现列表</h3></c:if>
                <c:if test="${retInfo.obj.afchg_state == 1}"><h3>审核不通过提现列表</h3></c:if>
                <c:if test="${retInfo.obj.afchg_state == 0}"><h3>已处理提现列表</h3></c:if>
                <c:if test="${retInfo.obj.afchg_state == 3}"><h3>待处理提现列表</h3></c:if>
            </div><!--contenttitle-->
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
                    <th class="head0" width="5%">代理人id</th>
                    <th class="head0" width="5%">代理人名称</th>
                    <th class="head0" width="5%">代理人手机号</th>
                    <th class="head0" width="10%">代理人评分</th>
                    <th class="head0" width="10%">代理人余额</th>
                    <th class="head0" width="10%">代理人区域</th>
                    <c:if test="${retInfo.obj.afchg_state == 2}">
                        <th class="head0" width="5%">提现时间</th>
                    </c:if>
                    <c:if test="${retInfo.obj.afchg_state == 1 || retInfo.obj.afchg_state == 0}">
                        <th class="head0" width="5%">审批时间</th>
                    </c:if>
                    <th class="head0" width="5%">提现金额</th>
                    <c:if test="${retInfo.obj.afchg_state == 3}">
                        <th class="head0" width="5%">支付宝账号</th>
                        <th class="head0" width="5%">支付类型</th>
                        <th class="head0" width="5%">批准时间</th>
                    </c:if>
                    <th class="head0" width="5%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.agent_withdrawals}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.afchg_id}</td>
                            <td>${bean.agent_id}</td>
                            <td>${bean.agent_name}</td>
                            <td>${bean.agent_phone}</td>
                            <td>${bean.agent_score}</td>
                            <td>${bean.agent_balance}</td>
                            <td>${bean.area_name}</td>
                            <c:if test="${retInfo.obj.afchg_state == 2}">
                                <td>${bean.afchg_time}</td>
                            </c:if>
                            <c:if test="${retInfo.obj.afchg_state == 1 || retInfo.obj.afchg_state == 0}">
                                <td>${bean.afchg_time}</td>
                            </c:if>
                            <td>${bean.afchg_change_amount}</td>
                            <c:if test="${retInfo.obj.afchg_state == 3}">
                                <td>${bean.agent_pay_account}</td>
                                <td>${bean.agent_pay_type}</td>
                                <td>${bean.afchg_time}</td>
                            </c:if>
                            <td>
                                <c:if test="${retInfo.obj.afchg_state == 0}">
                                    已处理
                                </c:if>
                                <c:if test="${retInfo.obj.afchg_state == 1}">
                                    审核不通过
                                </c:if>
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '通过'}">
                                            <c:if test="${retInfo.obj.afchg_state == 2}">
                                                <a class="btn_a" onclick="applyWithdrawals(${bean.afchg_id},3)" href="###">通过</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:when test="${funcion.func_name == '拒绝'}">
                                            <c:if test="${retInfo.obj.afchg_state == 2}">
                                                <a class="btn_a" onclick="applyWithdrawals(${bean.afchg_id},1)" href="###">拒绝</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:when test="${funcion.func_name == '处理'}">
                                            <c:if test="${retInfo.obj.afchg_state == 3}">
                                                <a class="btn_a" onclick="applyWithdrawals(${bean.afchg_id},0)" href="###">处理</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="#">${funcion.func_name}</a> ||
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
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/agent/found/applyList?currentPage=" + n + "&afchg_state=${retInfo.obj.afchg_state}&father_id=${funcions.obj.father_id}",
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                }

                function applyWithdrawals(afchg_id, afchg_state) {
                    var data = "afchg_id=" + afchg_id + "&afchg_state=" + afchg_state;
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/agent/found/update',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                alert("成功");
                                $.ajax({
                                    type:'GET',
                                    url:"${ctx}/admin/agent/found/applyList?currentPage=1&afchg_state=${retInfo.obj.afchg_state}&father_id=${funcions.obj.father_id}",
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