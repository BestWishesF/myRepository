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
                <h3>用户登录列表</h3>
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
                    <th class="head0" width="8%">用户id</th>
                    <th class="head0" width="8%">用户名称</th>
                    <th class="head0" width="10%">登录时间</th>
                    <th class="head0" width="8%">登录ip</th>
                    <th class="head0" width="4%">登录结果</th>
                    <th class="head0" width="5%">登录设备</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.loginlogs}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.user_login_log_id}</td>
                            <td>${bean.user_id}</td>
                            <td>${bean.user_name}</td>
                            <td>
                                <fmt:formatDate value="${bean.login_time}"   pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" />
                            </td>
                            <td>${bean.login_ip}</td>
                            <td>${bean.login_failer_desc}</td>
                            <td>
                                <c:if test="${bean.login_type == 1}">Android</c:if>
                                <c:if test="${bean.login_type == 2}">iOS</c:if>
                                <c:if test="${bean.login_type == 3}">微信公众号</c:if>
                                <c:if test="${bean.login_type == 4}">web</c:if>
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
                        url:"${ctx}/admin/loginlog/list?currentPage=" + n +  "&user_type=${retInfo.obj.user_type}&father_id=${funcions.obj.father_id}",
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