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
                <h3>用户地址列表</h3>
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
                    <th class="head0" width="5%">类别</th>
                    <th class="head0" width="5%">状态</th>
                    <th class="head0" width="8%">收(发)件人</th>
                    <th class="head0" width="10%">手机号码</th>
                    <th class="head0" width="10%">固定电话</th>
                    <th class="head0" width="10%">是否默认地址</th>
                    <th class="head0" width="10%">发件数量</th>
                    <th class="head0" >地址</th>

                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.adddress}" var="bean">
                <tr class="gradeX">
                    <td>${bean.add_id}</td>
                    <td>
                        <c:if test="${bean.add_type == 2}">收件地址</c:if>
                        <c:if test="${bean.add_type == 1}">发件地址</c:if>
                    </td>
                    <td>
                        <c:if test="${bean.add_state == 0}">正常</c:if>
                        <c:if test="${bean.add_state == 1}">已删除</c:if>
                    </td>
                    <td>${bean.add_name}</td>
                    <td>${bean.add_mobile_phone}</td>
                    <td>${bean.add_telephone}</td>
                    <td>
                        <c:if test="${bean.add_is_default == 0}">是</c:if>
                        <c:if test="${bean.add_is_default == 1}">否</c:if>
                    </td>
                    <td>${bean.add_express_size}</td>
                    <td>${bean.add_detail_address}</td>
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
                        url:"${ctx}/admin/address/list?memb_id=${retInfo.obj.memb_id}&currentPage=" + n +"&father_id=${funcions.obj.father_id}",
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