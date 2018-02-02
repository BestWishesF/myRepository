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
                <h3>代理人资金变化列表</h3>
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
                    <th class="head0" width="5%">变化金额</th>
                    <th class="head0" width="5%">变化前金额</th>
                    <th class="head0" width="5%">变化后金额</th>
                    <th class="head0" width="10%">编号</th>
                    <th class="head0" width="10%">时间</th>
                    <th class="head0" width="10%">原因</th>
                    <th class="head0" width="5%">状态</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.founds}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.afchg_id}</td>
                            <td>
                                <c:if test="${bean.afchg_type == 3}">-</c:if>
                                <c:if test="${bean.afchg_type == 2}">-</c:if>
                                <c:if test="${bean.afchg_type == 1}">+</c:if>
                                    ${bean.afchg_change_amount}
                            </td>
                            <td>${bean.afchg_front_amount}</td>
                            <td>${bean.afchg_back_amount}</td>

                            <td>${bean.afchg_number}</td>
                            <td>
                                <fmt:formatDate value="${bean.afchg_time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                                dateStyle="long"/>
                            </td>
                            <td>${bean.afchg_name}</td>
                            <td>
                                <c:if test="${bean.afchg_state == 0 && bean.afchg_type == 1}">奖励成功入账</c:if>
                                <c:if test="${bean.afchg_state == 1 && bean.afchg_type == 1}">奖励入账失败</c:if>
                                <c:if test="${bean.afchg_state == 2 && bean.afchg_type == 1}">奖励待入账</c:if>
                                <c:if test="${bean.afchg_state == 0 && bean.afchg_type == 2}">提现成功</c:if>
                                <c:if test="${bean.afchg_state == 1 && bean.afchg_type == 2}">提现失败</c:if>
                                <c:if test="${bean.afchg_state == 2 && bean.afchg_type == 2}">提现待审核</c:if>
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
                function getParameter(name) {
                    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                    var r = window.location.search.substr(1).match(reg);
                    if (r != null) return unescape(r[2]);
                    return null;
                }

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
                        url:"${ctx}/admin/agent/found/list" + "?currentPage=" + n + "&agent_id=${retInfo.obj.agent_id}&father_id=${funcions.obj.father_id}",
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


</div><!--bodywrapper-->

</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        kkpager.selectPage( ${retInfo.obj.currentPage})

    });
</script>