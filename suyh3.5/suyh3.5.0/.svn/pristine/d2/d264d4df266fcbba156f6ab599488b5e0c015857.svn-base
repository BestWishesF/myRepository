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
                <h3>代理人审核失败申请记录列表</h3>
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
                    <th class="head0" width="8%">身份信息</th>
                    <th class="head0" width="8%">手机号码</th>
                    <th class="head0" width="8%">头像</th>
                    <th class="head0" width="8%">身份证正面</th>
                    <th class="head0" width="8%">身份证反面</th>

                    <th class="head0" width="15%">地址</th>
                    <th class="head0" width="5%">账号</th>
                    <th class="head0" width="5%">状态</th>
                    <th class="head0" width="5%">审核结果</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.applys}" var="bean">
                <tr class="gradeX">
                        <td>${bean.apply_id}</td>
                        <td>
                            ${bean.agent_name}
                            <br/>
                            ${bean.agent_id_number}
                        </td>
                    <td>${bean.agent_phone}</td>
                        <td>
                            <img src="${bean.agent_head_portrait}"  height="80px"></img>
                        </td>
                    <td>
                        <img src="${bean.apply_id_front}"  height="80px"></img>
                    </td>
                    <td>
                        <img src="${bean.apply_id_back}"  height="80px"></img>
                    </td>

                    <td>${bean.agent_address}</td>
                    <td>${bean.agent_pay_account}</td>
                    <td>
                        <c:if test="${bean.apply_state == 1}">成功</c:if>
                        <c:if test="${bean.apply_state == 2}">未审核</c:if>
                        <c:if test="${bean.apply_state == 3}">失败</c:if>
                    </td>
                    <td>${bean.apply_fail_reason}</td>

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
                        url:"${ctx}/admin/loginlog/list" + "?currentPage="+n + "&user_type=${retInfo.obj.user_type}&father_id=${funcions.obj.father_id}",
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