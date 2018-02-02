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
                <h3>代理人推广列表</h3>
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
                    <th class="head0" width="8%">代理人姓名</th>
                    <th class="head0" width="8%">代理人手机号</th>
                    <th class="head0" width="10%">推广用户昵称</th>
                    <th class="head0" width="5%">推广用户手机号</th>
                    <th class="head0" width="4%">新用户注册奖励</th>
                    <th class="head0" width="5%">新用户注册时间</th>
                    <th class="head0" width="4%">首次下单奖励</th>
                    <th class="head0" width="5%">首次下单时间</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.agentMembers}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.agent_memb_id}</td>
                            <td>${bean.agent_name}</td>
                            <td>${bean.agent_mobile}</td>
                            <td>${bean.member_name}</td>
                            <td>${bean.member_mobile}</td>
                            <td>
                                <c:if test="${bean.register_reward == '0.00'}">未注册</c:if>
                                <c:if test="${bean.register_reward > 0}">${bean.register_reward}</c:if>
                            </td>
                            <td>
                                <fmt:formatDate value="${bean.regiter_time}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long"/>
                            </td>
                            <td><c:if test="${bean.regiter_time == bean.first_single_time}">未下单</c:if>
                                <c:if test="${bean.regiter_time < bean.first_single_time}">${bean.first_single_reward}</c:if>
                            </td>
                            <td><fmt:formatDate value="${bean.first_single_time}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long"/></td>
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
                        url:"${ctx}/admin/agent/member/list" + "?currentPage=" + n + "&agent_id=${retInfo.obj.agent_id}&father_id=${funcions.obj.father_id}",
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