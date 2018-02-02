<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>
        用户活动记录
    </title>
    <link rel="stylesheet" href="${ctx}/css/style.default.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.uniform.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/dashboard.js"></script>
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

    <%@ include file="../../include/topheader.jsp" %>
    <div class="header">
        <ul class="headermenu">
            <c:if test="${user_name != '13127802758'}">
                <li><a href="${ctx}/admin/accounts/list?currentPage=1"><span class="icon icon-speech"></span>角色管理</a></li>
            </c:if>
            <li class="current"><a href="${ctx}/admin/index?currentPage=1"><span class="icon icon-flatscreen"></span>用户管理</a></li>
            <li><a href="${ctx}/admin/agent/list?currentPage=1&agent_state=2"><span class="icon icon-pencil"></span>代理人管理</a></li>
            <li><a href="${ctx}/admin/express/order/list?currentPage=1&exp_ord_state=1&memb_type=0"><span class="icon icon-message"></span>订单管理</a></li>
            <c:if test="${user_name != '13127802758'}">
                <li><a href="${ctx}/admin/banner/list?currentPage=1&is_valid=0"><span class="icon icon-chart"></span>其他管理</a></li>
            </c:if>
        </ul>
    </div><!--header-->
    <div class="vernav2 iconmenu">
        <ul>
            <li><a href="${ctx}/admin/loginlog/list?currentPage=1&user_type=1" class="support">用户登录记录</a></li>
            <li><a href="${ctx}/admin/feedback/list?currentPage=1&user_cate=1" class="support">用户反馈记录</a></li>
            <c:if test="${user_name != '13127802758'}">
                <li><a href="${ctx}/admin/member/activity/findGiveCouActMemb?currentPage=1" class="support">用户唤醒活动</a></li>
            </c:if>
        </ul>
        <a class="togglemenu"></a>
        <br /><br />
    </div><!--leftmenu-->
    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>
                    用户活动记录
                </h3>
            </div><!--contenttitle-->
            <div class="overviewhead">
                活动参与时间: &nbsp;<input type="text" id="datepickfrom" value="${retInfo.obj.starTime}" /> &nbsp;
                - &nbsp;<input type="text" id="datepickto" value="${retInfo.obj.endTime}" />&nbsp; &nbsp;
                活动记录状态: &nbsp;
                <select id="state" style="padding: 6px 5px;border: 1px solid #ccc;border-radius: 2px;font-size: 12px;">
                    <option value="1">待赠送绿植</option>
                    <option value="2">待赠送优惠券</option>
                    <option value="3">已赠送优惠券</option>
                </select>&nbsp; &nbsp;
                姓名:&nbsp;<input type="text" id="name" />&nbsp; &nbsp;
                手机:&nbsp;<input type="text" id="phone" />
                <button class="btn_b" id="btn_assign">查询</button>
            </div><!--overviewhead-->
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="dyntable" width="2400px">
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
                    <th class="head0" width="10%">姓名</th>
                    <th class="head0" width="15%">地址</th>
                    <th class="head0" width="10%">手机号</th>
                    <th class="head0" width="5%">优惠券金额</th>
                    <th class="head0" width="10%">完成时间</th>
                    <th class="head0" width="10%">状态</th>
                    <th class="head0" width="10%">赠送绿植完成时间</th>
                    <th class="head0" width="10%">赠送优惠券完成时间</th>
                    <th class="head0" width="10%">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.greenMailList}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.green_mail_id}</td>
                            <td>${bean.name}</td>
                            <td>${bean.address}</td>
                            <td>${bean.phone}</td>
                            <td>${bean.event_reward}</td>
                            <td>${bean.done_time}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${bean.state==1}">
                                        待赠送绿植
                                    </c:when>
                                    <c:when test="${bean.state==2}">
                                        待赠送优惠券
                                    </c:when>
                                    <c:when test="${bean.state==3}">
                                        已赠送优惠券
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </td>
                            <td>${bean.give_green_time}</td>
                            <td>${bean.give_reward_time}</td>
                            <td>
                                <c:if test="${bean.state==1}">
                                    <a class="btn_a" onclick="giveMember(${bean.green_mail_id},2)" href="###">赠送绿植</a></td>
                                </c:if>
                                <c:if test="${bean.state==2}">
                                    <a class="btn_a" onclick="giveMember(${bean.green_mail_id},3)" href="###">赠送优惠券</a></td>
                                </c:if>
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
                            var href =  ctx + "/admin/member/green/list" + "?currentPage=" + n +"&state=${retInfo.obj.state}&starTime=${retInfo.obj.starTime}&endTime=${retInfo.obj.endTime}&phone=${retInfo.obj.phone}&name=${retInfo.obj.name}"  ;
                            return href;
                        }

                    });
                });
            </script>

            <script type="text/javascript">
                $("#btn_assign").click(function () {
                    window.location.href="${ctx}/admin/member/green/list?currentPage=1&state="+$("#state").val()+"&starTime="+$("#datepickfrom").val()+"&endTime="+$("#datepickto").val()+"&phone="+$("#phone").val()+"&name="+$("#name").val();
                })
                
                function giveMember(green_mail_id, state) {
                    if(state==3){
                        alert("请确认用户是否已完成该流程.")
                    }
                    var data="green_mail_id="+green_mail_id+"&state="+state;
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/member/green/update',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                if (state==2) {
                                    window.location.href="${ctx}/admin/member/green/list?currentPage=1&state=2&starTime=&endTime=&phone=&name=";
                                } else {
                                    window.location.href="${ctx}/admin/member/green/list?currentPage=1&state=3&starTime=&endTime=&phone=&name=";
                                }
                            }else{
                                alert(data.tip);
                            }
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
