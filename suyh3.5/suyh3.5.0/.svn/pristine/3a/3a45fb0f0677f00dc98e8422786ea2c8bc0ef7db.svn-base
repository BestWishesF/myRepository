
<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="../include/import.jsp" %>
<head>
    <%@ include file="../include/title.jsp" %>
    <script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/general.js"></script>
</head>
<body class="withvernav">
<!-------------------------------- 业务区域代码开始 ---------------------->

<div class="bodywrapper">


    <!--header-start-->
    <%@ include file="top.jsp" %>
    <div class="header">
        <ul class="headermenu">
            <c:forEach items="${retInfo.obj.fatherFuncion}" var="bean"  varStatus="status">
                <input type="text" id="father_id${status.count}" value="${bean.func_id}" style="display: none"/>
                <input type="text" id="menu${status.count}" value="${ctx}${bean.link_url}" style="display: none"/>
                <li><a href="##" onclick="menu('${ctx}${bean.link_url}',${bean.func_id})"><span class="icon icon-speech"></span>${bean.func_name}</a></li>
            </c:forEach>
        </ul>
    </div>
    <!--header-end-->
    <!--left-start-->
    <div id="left">
        <%--<div class="vernav2 iconmenu">--%>
            <%--<ul>--%>
                <%--<c:forEach items="${retInfo.obj.sonFuncion}" var="bean"  varStatus="status">--%>

                    <%--<c:choose>--%>
                        <%--<c:when test="${bean.is_father == 0 && bean.is_father == 1}">--%>
                            <%--<li class="support${status.count}">--%>
                                <%--<a href="#agent${status.count}" class="addons${status.count}">${bean.func_name}</a>--%>
                                <%--<span class="arrow"></span>--%>
                                <%--<ul id="agent${status.count}">--%>
                                    <%--<c:forEach items="${bean.tdHtFuncionDtos}" var="funcion" varStatus="funcionStatus">--%>
                                    <%--<li class="support"><a href="#" onclick="main('${ctx}${funcion.link_url}')">${funcion.func_name}</a></li>--%>
                                    <%--</c:forEach>--%>

                                <%--</ul>--%>
                            <%--</li>--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                            <%--<li><a href="#" onclick="main('${ctx}${bean.link_url}')" class="support${status.count}">${bean.func_name}</a></li>--%>
                        <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                <%--</c:forEach>--%>

            <%--</ul>--%>
            <%--<a class="togglemenu"></a>--%>
            <%--<br /><br />--%>
        <%--</div>--%>
    </div>
    <!--left-end-->

    <!--left-start-->
    <div id="main">

    </div>
    <!--left-end-->

</div>
<script type="text/javascript">

        function main(url) {
            $.ajax({
                type:'GET',
                url:url,
                dataType:'html',
                success:function(data){
                    $("#main").html(data);
                },
                error:function(){
                    alert("error");
                }
            });
        }
        function menu(url,id) {
            $.ajax({
                type:'GET',
                url:'${ctx}/admin/left?father_id=' + id,
                dataType:'html',
                success:function(data){
                    $("#left").html(data);
                },
                error:function(){
                    alert("error");
                }
            });
            $.ajax({
                type:'GET',
                url:url,
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

<!-------------------------------- 业务区域代码结束 ---------------------->
</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        var father_id = $("#father_id1").val();
        $.ajax({
            type:'GET',
            url:'${ctx}/admin/left?father_id=' + father_id,
            dataType:'html',
            success:function(data){
                $("#left").html(data);
            },
            error:function(){
                alert("error");
            }
        });

        var url = $("#menu1").val();
        $.ajax({
            type:'GET',
            url:url,
            dataType:'html',
            success:function(data){
                $("#main").html(data);
            },
            error:function(){
                alert("error");
            }
        });

    });
</script>