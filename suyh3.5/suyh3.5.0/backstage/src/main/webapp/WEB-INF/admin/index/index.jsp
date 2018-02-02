<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>控制台页面</title>
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

<div class="zhezhao"></div>
<div class="Mask2" id="Mask1" style="display: none;position: fixed;">
    <div id="update">
        <dl style="margin-left: 15px;">修改用户角色</dl>
        <hr />
        <dl class="fill_singleChoice" id="send_info">
            <input id="member_id" type="hidden">
            <input id="token" type="hidden">
            <p>用户角色：
                <select id="memb_role">

                </select>
            </p>
            <p>用户折扣：<input id="memb_discount" type="text" /></p>
        </dl>
    </div>
    <div class="styleButton" style="text-align: center;">
        <button class="btn_b" id="btn_save">保存</button>
        <button class="btn_b" id="btn_cancel">取消</button>
    </div>
</div>

<body class="withvernav">
<div class="bodywrapper">

    <%@ include file="../include/topheader.jsp" %>
    <div class="header">
        <ul class="headermenu">
            <c:if test="${user_name != '13127802758'}">
                <li><a href="${ctx}/admin/accounts/list?currentPage=1"><span class="icon icon-speech"></span>角色管理</a></li>
            </c:if>
            <li  class="current"><a href="${ctx}/admin/index?currentPage=1"><span class="icon icon-flatscreen"></span>用户管理</a></li>
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
                <h3>用户列表</h3>
            </div><!--contenttitle-->
            <div class="details">
                    <div class="search_input">
                        <div id="search" style="display: none">
                            <p>
                               &nbsp;&nbsp;&nbsp;&nbsp;id:<input type="text" name="memb_id" id="memb_id" width="30%"/></p>
                            <p>
                               名称:<input type="text" name="memb_nick_name" id="memb_nick_name"  width="30%"/></p>
                            <p>
                                手机:<input type="text" name="memb_phone" id="memb_phone"  width="30%"/></p>
                            <p>
                                <button class="submit radius2" id="search_button">搜索</button>
                                <button class="submit radius2" id="search_button_end">搜索结束</button>
                            </p>
                        </div>

                        <p><button class="submit radius2" id="search_button_none">搜索</button></p>
                    </div>


            </div><!--details-->
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
                    <th class="head0" width="8%">用户名称</th>
                    <th class="head0" width="8%">用户头像</th>
                    <th class="head0" width="10%">手机号码</th>
                    <th class="head0" width="4%">性别</th>
                    <th class="head0" width="5%">生日</th>
                    <th class="head0" width="10%">注册时间</th>
                    <th class="head0" width="5%">登陆方式</th>
                    <th class="head0" width="4%">积分</th>
                    <th class="head0" width="4%">余额</th>
                    <th class="head0" width="5%">用户状态</th>
                    <th class="head0" width="5%">用户角色</th>
                    <th class="head0" width="5%">用户折扣</th>
                    <th class="head1">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.members}" var="bean">
                <tr class="gradeX">
                        <td>${bean.memb_id}</td>
                        <td>${bean.memb_nick_name}</td>
                        <td>
                            <img src="${bean.memb_head_portrait}"  height="80px"></img>
                        </td>
                        <td>${bean.memb_phone}</td>
                        <td>
                            <c:if test="${bean.memb_sex == 1}">男</c:if>
                            <c:if test="${bean.memb_sex == 2}">女</c:if>
                            <c:if test="${bean.memb_sex == 3}">其他</c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${bean.memb_birthday}"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                        </td>
                        <td>
                            <fmt:formatDate value="${bean.memb_register_time}"   pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" />
                        </td>
                        <td>
                            <c:if test="${bean.push_type == 1}">Android</c:if>
                            <c:if test="${bean.push_type == 2}">ios</c:if>
                            <c:if test="${bean.push_type == 3}">微信公众号</c:if>
                        </td>
                        <td>${bean.memb_score}</td>
                        <td>${bean.memb_balance}</td>
                        <td>
                            <c:if test="${bean.memb_islock == 1}">正常</c:if>
                            <c:if test="${bean.memb_islock == 0}">封号</c:if>
                            <c:if test="${bean.memb_islock == 2}">正常</c:if>
                        </td>
                        <td>
                            <c:if test="${bean.memb_role == 1}">普通用户</c:if>
                            <c:if test="${bean.memb_role == 2}">月结用户</c:if>
                            <c:if test="${bean.memb_role == 3}">大客户</c:if>
                        </td>
                        <td>${bean.memb_discount}</td>
                        <td class="center">
                            <c:choose>
                                <c:when test="${bean.memb_islock == 0}">
                                    <a onclick="lock(${bean.memb_id} ,1 ,'${bean.token}');" href="###">解锁</a>
                                </c:when>
                                <c:otherwise>
                                    <a onclick="lock(${bean.memb_id} ,0 ,'${bean.token}' );" href="###">锁定</a>
                                </c:otherwise>
                            </c:choose>
                            ||
                            <a  href="${ctx}/admin/address/list?memb_id=${bean.memb_id}&currentPage=1">地址</a>
                             ||
                            <a  href="${ctx}/admin/score/list?memb_id=${bean.memb_id}&currentPage=1">积分</a>
                            ||  <a  href="${ctx}/admin/found/list?memb_id=${bean.memb_id}&currentPage=1">资金</a>
                            ||  <a onclick="openUpdateRole(${bean.memb_id},'${bean.token}',${bean.memb_role},${bean.memb_discount})">修改角色</a>
                            ||  <a  href="${ctx}/admin/member/invoice/list?currentPage=1&memb_id=${bean.memb_id}">开票记录</a>
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
                            var href =  ctx + "/admin/index" + "?currentPage="+n;
                            return href;
                        }

                    });
                });
            </script>

            <br /><br />
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->
<script type="text/javascript">
    function lock(memb_id ,memb_islock ,token){
        var data = "memb_id=" + memb_id + "&memb_islock=" + memb_islock + "&token=" + token;
        $.ajax({
            type:'post',
            url:'${ctx}/admin/member/lock',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    window.location.reload();
                }else{
                    alert(data.tip);
                }
            }

        });
    }
    $("#search_button_none").click(function(){
        $("#search_button_none").hide();
        $("#search").show();
    });
    $("#search_button_end").click(function(){
        $("#search_button_none").show();
        $("#search").hide();
    });

    $("#search_button").click(function(){
        var memb_id=$("#memb_id").val();
        if(memb_id==null||memb_id.trim()==""){
            memb_id=0;
        }
        window.location.href="${ctx}/admin/member/search?" + "memb_id="+memb_id+"&memb_nick_name="+$("#memb_nick_name").val()+"&memb_phone="+$("#memb_phone").val() +"&currentPage=1";
    });

    function openUpdateRole(member_id,token,memb_role,memb_discount) {
        $(".zhezhao").fadeIn(300);
        $("#Mask1").fadeIn(300);
        $("#member_id").val(member_id);
        $("#token").val(token);
        if(memb_role==1){
            $("#memb_role").append('<option value="1" selected>普通用户</option> ' +
                '<option value="2">月结用户</option>' +
                '<option value="3">大客户</option>');
        }else if(memb_role==2){
            $("#memb_role").append('<option value="1">普通用户</option> ' +
                '<option value="2" selected>月结用户</option>' +
                '<option value="3">大客户</option>');
        }else if(memb_role==3){
            $("#memb_role").append('<option value="1">普通用户</option> ' +
                '<option value="2">月结用户</option>' +
                '<option value="3" selected>大客户</option>');
        }
        $("#memb_discount").val(memb_discount);
    }

    $("#btn_cancel").click(function () {
        $(".update_info").val("");
        $("#memb_role").html("");
        $(".zhezhao").hide();
        $("#Mask1").hide();
    })

    $("#btn_save").click(function () {
        var data = "memb_id=" + $("#member_id").val() + "&token=" + $("#token").val() + "&memb_role=" + $("#memb_role").val() + "&memb_discount=" + $("#memb_discount").val();
        $.ajax({
            type:'post',
            url:'${ctx}/admin/member/updateRole',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    window.location.reload();
                }else{
                    alert(data.tip);
                }
            }

        });
    })
</script>
</body>
</html>
