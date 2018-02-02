<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/plugins/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/custom/general.js"></script>

<div class="vernav2 iconmenu">
    <ul>
        <c:forEach items="${retInfo.obj.sonFuncion}" var="bean"  varStatus="status">

            <c:choose>
                <c:when test="${bean.is_father == 0 && bean.func_type == 1}">
                    <li>
                        <a href="#formsub${status.count}">${bean.func_name}</a>
                        <span class="arrow"></span>
                        <ul id="formsub${status.count}">
                            <c:forEach items="${bean.tdHtFuncionDtos}" var="funcion" varStatus="funcionStatus">
                                <li class="support"><a href="#" onclick="main('${ctx}${funcion.link_url}')">${funcion.func_name}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="#" onclick="main('${ctx}${bean.link_url}')" class="support">${bean.func_name}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

    </ul>
    <a class="togglemenu"></a>
    <br /><br />
</div>