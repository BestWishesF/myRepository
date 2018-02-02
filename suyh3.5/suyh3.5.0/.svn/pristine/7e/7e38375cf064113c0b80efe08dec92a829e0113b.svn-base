<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
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
                <h3>
                    划分区列表
                </h3>
            </div><!--contenttitle-->
            <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                <c:choose>
                    <c:when test="${funcion.func_name == '添加'}">
                        <div class="details">
                            <div class="search_input">
                                <p><button class="submit radius2" onclick="main('${ctx}${funcion.link_url}?region_id=${retInfo.obj.region_id}&father_id=${funcion.father_id}')">添加</button></p>
                            </div>
                        </div><!--details-->
                    </c:when>
                </c:choose>
            </c:forEach>
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
                    <th class="head0" width="20%">名称</th>
                    <th class="head0" width="5%">状态</th>
                    <th class="head1" width="10%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.divide_list}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.divide_id}</td>
                            <td>${bean.divide_name}</td>
                            <td>
                                <c:if test="${bean.divide_state == 1}">不可用</c:if>
                                <c:if test="${bean.divide_state == 0}">可用</c:if>
                            </td>
                            <td>
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '添加'}">

                                        </c:when>
                                        <c:when test="${funcion.func_name == '更新'}">
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?divide_id=${bean.divide_id}&father_id=${funcion.father_id}')">${funcion.func_name}</a> ||
                                        </c:when>
                                        <c:otherwise>
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?divide_id=${bean.divide_id}&father_id=${funcion.func_id}')">${funcion.func_name}</a> ||
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
                    var href = "${ctx}/admin/dict/divide/jump/list" + "?currentPage=" + n + "&region_id=${retInfo.obj.region_id}&father_id=${funcions.obj.father_id}";
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