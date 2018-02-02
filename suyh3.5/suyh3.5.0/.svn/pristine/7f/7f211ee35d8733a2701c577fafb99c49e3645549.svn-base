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
                <h3>角色列表</h3>
            </div><!--contenttitle-->
            <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                <c:choose>
                    <c:when test="${funcion.func_name == '添加'}">
                        <div class="details">
                            <div class="search_input">
                                <p><button class="submit radius2" onclick="main('${ctx}${funcion.link_url}')">添加</button></p>
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
                    <th class="head0" width="10%">名称</th>
                    <th class="head0" width="10%">创建人</th>
                    <th class="head0" width="10%">创建时间</th>
                    <th class="head0" width="10%">区域</th>
                    <th class="head1" width="5%">操作</th>
                </tr>
                </thead>

                <tbody>

                    <c:forEach items="${roles}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.role_id}</td>
                            <td>${bean.role_name}</td>
                            <td>${bean.creater}</td>
                            <td>${bean.create_time}</td>
                            <td>${bean.address}</td>
                            <td class="center">
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '添加'}">

                                        </c:when>
                                        <c:otherwise>
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?role_id=${bean.role_id}')">${funcion.func_name}</a> ||
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br /><br />
        </div>
    </div><!-- centercontent -->
</div><!--bodywrapper-->

</body>
</html>