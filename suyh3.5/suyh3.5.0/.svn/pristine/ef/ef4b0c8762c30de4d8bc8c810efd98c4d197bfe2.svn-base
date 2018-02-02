<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>管理员登录列表</title>
    <link rel="stylesheet" href="${ctx}/css/style.default.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <link rel="stylesheet" href="${ctx}/css/style.default.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.uniform.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/general.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/index.js"></script>
    <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="${ctx}/js/plugins/excanvas.min.js"></script><![endif]-->
    <link href="${ctx}/css/kkpager_blue.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
    <!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="${ctx}css/style.ie9.css"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="${ctx}css/style.ie8.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <script src="${ctx}js/plugins/css3-mediaqueries.js"></script>
    <![endif]-->
</head>

<body class="withvernav">
<div class="bodywrapper">

    <%@ include file="../include/topheader.jsp" %>
    <%@ include file="../include/header.jsp" %>
    <%@ include file="../include/member.jsp" %>
    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>管理员登录列表</h3>
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
                function getParameter(name) {
                    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
                    var r = window.location.search.substr(1).match(reg);
                    if (r!=null) return unescape(r[2]); return null;
                }

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
                        //链接前部
                        hrefFormer : 'pager_test',
                        //链接尾部
                        hrefLatter : '.html',
                        lang				: {
                            firstPageText			: '首页',
                            firstPageTipText		: '首页',
                            lastPageText			: '尾页',
                            lastPageTipText			: '尾页',
                            prePageText				: '上一页',
                            prePageTipText			: '上一页',
                            nextPageText			: '下一页',
                            nextPageTipText			: '下一页',
                            totalPageBeforeText		: '共',
                            totalPageAfterText		: '页',
                            currPageBeforeText		: '当前第',
                            currPageAfterText		: '页',
                            totalInfoSplitStr		: '/',
                            totalRecordsBeforeText	: '共',
                            totalRecordsAfterText	: '条数据',
                            gopageBeforeText		: ' 转到',
                            gopageButtonOkText		: '确定',
                            gopageAfterText			: '页',
                            buttonTipBeforeText		: '第',
                            buttonTipAfterText		: '页'
                        },
                        getLink : function(n){
                            var href =  ctx + "/admin/loginlog/list" + "?currentPage="+n + "&user_type=${retInfo.obj.user_type}";
                            return href;
                        }

                    });
                });
            </script>

            <br /><br />
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->

</body>
</html>
