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
                <h3>banner列表</h3>
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
                    <th class="head0" width="10%">名称</th>
                    <th class="head0" width="10%">图片地址</th>
                    <th class="head0" width="10%">图片alt</th>
                    <th class="head0" width="20%">图片链接</th>
                    <th class="head0" width="5%">类别</th>
                    <th class="head0" width="5%">是否可用</th>
                    <th class="head0" width="5%">排序</th>
                    <th class="head1" width="5%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.banners}" var="bean">
                <tr class="gradeX">
                        <td>${bean.banner_id}</td>
                        <td>${bean.img_name}</td>
                        <td>
                            <img src="${bean.img_src}"  height="80px"></img>
                        </td>
                        <td>${bean.img_alt}</td>
                        <td>${bean.img_link}</td>
                        <td>
                            <c:if test="${bean.img_type == 1}">APP首页</c:if>
                        </td>
                         <td>
                            <c:if test="${bean.is_valid == 0}">可用</c:if>
                            <c:if test="${bean.is_valid == 1}">不可用</c:if>
                        </td>
                        <td>${bean.img_sort}</td>


                        <td class="center">
                            <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                <c:choose>
                                    <c:when test="${funcion.func_name == '不可用'}">
                                        <c:if test="${bean.is_valid == 0}">
                                            <a onclick="lock(${bean.banner_id} ,1 );" href="###">不可用</a> ||
                                        </c:if>
                                    </c:when>
                                    <c:when test="${funcion.func_name == '可用'}">
                                        <c:if test="${bean.is_valid != 0}">
                                            <a onclick="lock(${bean.banner_id} ,0 );" href="###">可用</a> ||
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <a  href="#"  onclick="main('${ctx}${funcion.link_url}?banner_id=${bean.banner_id}')">${funcion.func_name}</a> ||
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
                    var href = "${ctx}/admin/banner/list" + "?currentPage=" + n + "&is_valid=${retInfo.obj.is_valid}&father_id=${funcions.obj.father_id}";
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
<script type="text/javascript">
    function lock(banner_id ,is_valid){
        var data = "banner_id=" + banner_id + "&is_valid=" + is_valid ;
        $.ajax({
            type:'post',
            url:'${ctx}/admin/banner/updateState',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    var father_id = 75;
                    if (is_valid == 0) {
                        father_id = 74
                    }
                    var href = "${ctx}/admin/banner/list" + "?currentPage=" + 1 + "&is_valid=" + is_valid + "&father_id=" + father_id;
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
                }else{
                    alert(data.tip);
                }
            }

        });
    }

</script>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        kkpager.selectPage( ${retInfo.obj.currentPage})

    });
</script>