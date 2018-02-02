<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
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
                <h3>用户资金变化列表</h3>
            </div><!--contenttitle-->
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="dyntable">
                <colgroup>
                    <col class="con0" />
                    <col class="con1" />
                    <col class="con0" />
                    <col class="con1" />
                    <col class="con0" />
                </colgroup>
                <thead>
                <tr>
                    <th class="head0" width="5%">id</th>
                    <th class="head0" width="5%">变化金额</th>
                    <th class="head0" width="5%">变化前金额</th>
                    <th class="head0" width="5%">变化后金额</th>
                    <th class="head0" width="5%">支付方式</th>
                    <th class="head0" width="10%">编号</th>
                    <th class="head0" width="5%">订单id</th>
                    <th class="head0" width="10%">时间</th>
                    <th class="head0" width="10%">原因</th>
                    <th class="head0" width="5%">状态</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.founds}" var="bean">
                <tr class="gradeX">
                    <td>${bean.mfchg_id}</td>
                    <td>
                        <c:if test="${bean.mfchg_type == 3}">-</c:if>
                        <c:if test="${bean.mfchg_type == 2}">-</c:if>
                        <c:if test="${bean.mfchg_type == 1}">+</c:if>
                        ${bean.mfchg_change_amount}
                    </td>
                    <td>${bean.mfchg_front_amount}</td>
                    <td>${bean.mfchg_back_amount}</td>
                    <td>
                        <c:if test="${bean.mfchg_channel == 3}">余额</c:if>
                        <c:if test="${bean.mfchg_channel == 2}">支付宝</c:if>
                        <c:if test="${bean.mfchg_channel == 1}">微信</c:if>
                    </td>
                    <td>${bean.mfchg_number}</td>
                    <td>${bean.exp_ord_id}</td>
                    <td>
                        <fmt:formatDate value="${bean.mfchg_time}"   pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" />
                    </td>
                    <td>${bean.mfchg_name}</td>
                    <td>
                        <c:if test="${bean.mfchg_state == 2}">成功</c:if>
                        <c:if test="${bean.mfchg_state == 1}">待支付</c:if>
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
                $(function(){
                    var totalPage = ${retInfo.obj.totalPage};
                    var totalRecords = ${retInfo.obj.totalRecord};
                    var pageNo = ${retInfo.obj.currentPage};
                    var ctx = "${ctx}";
                    if(!pageNo){
                        pageNo = 1;
                    }
                    //生成分页
                    //有些参数是可选的，比如lang，若不传有默认值
                    kkpager.generPageHtml({
                        pno : pageNo,
                        //总页码
                        total : totalPage,
                        //总数据条数
                        totalRecords : totalRecords,

                    });
                });
                function page(n) {
                    $.ajax({
                        type:'GET',
                        url:"${ctx} /admin/found/list" + "?currentPage=" + n  + "&memb_id=${retInfo.obj.memb_id}&father_id=${funcions.obj.father_id}",
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

            <br /><br />
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