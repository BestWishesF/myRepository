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
                <h3>快递SDK列表</h3>
            </div><!--contenttitle-->
            <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                <c:choose>
                    <c:when test="${funcion.func_name == '添加'}">
                        <%--<div class="details">
                            <div class="search_input">
                                <p><button class="submit radius2" onclick="insertExpSdk(${retInfo.obj.eoa_id})">添加</button></p>
                            </div>
                        </div><!--details-->--%>
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
                    <th class="head0" width="25%">所属地区</th>
                    <th class="head0" width="10%">网点名称</th>
                    <th class="head0" width="10%">网点编号</th>
                    <th class="head0" width="10%">分配客户名称</th>
                    <th class="head0" width="10%">分配客户编号</th>
                    <th class="head0" width="10%">接口类型</th>
                    <th class="head0" width="10%">接口地址</th>
                    <th class="head1" width="10%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.exp_sdk_list}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.exp_sdk_id}</td>
                            <td>${retInfo.obj.address}</td>
                            <td>${bean.dot_name}</td>
                            <td>${bean.dot_number}</td>
                            <td>${bean.customer_name}</td>
                            <td>${bean.customer_number}</td>
                            <td>
                                <c:if test="${bean.sdkj_itf_type == 1}">提取单号</c:if>
                                <c:if test="${bean.sdkj_itf_type == 2}">筛单</c:if>
                                <c:if test="${bean.sdkj_itf_type == 3}">获取打印大头笔</c:if>
                                <c:if test="${bean.sdkj_itf_type == 4}">提交订单</c:if>
                                <c:if test="${bean.sdkj_itf_type == 5}">查询物流</c:if>
                                <c:if test="${bean.sdkj_itf_type == 6}">获取可用单号数量</c:if>
                                <c:if test="${bean.sdkj_itf_type == 7}">批量下单(最多50单)</c:if>
                            </td>
                            <td>${bean.sdk_itf_url}</td>
                            <td class="center">
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '添加'}">

                                        </c:when>
                                        <c:when test="${funcion.func_name == '修改'}">

                                        </c:when>
                                        <c:otherwise>
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?exp_sdk_id=${bean.exp_sdk_id}&father_id=${funcion.func_id}')">${funcion.func_name}</a> ||
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
                        totalRecords : totalRecords

                    });
                });

                function page(n) {
                    var href =  ctx + "/admin/express/sdk/list" + "?currentPage=" + n
                        + "&eoa_id=${retInfo.obj.eoa_id}&father_id=${funcions.obj.father_id}";
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
