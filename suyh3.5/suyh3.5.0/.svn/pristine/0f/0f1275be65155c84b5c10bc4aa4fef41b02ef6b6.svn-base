<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>

</head>

<div class="zhezhao"></div>
<div class="Mask2" style="display: none">
    <div id="apply_type">
        <dl class="fill_singleChoice">
            <dt>新密码：</dt>
            <dd>
                <input type="hidden" id="admin_id" />
                <input id="user_pass" type="text" class="longinput"/>
            </dd>
        </dl>
    </div>

    <div class="styleButton">
        <input type="button" value="保存" class="btn_save" id="submit_bt"/>
        <input type="button" value="取消" class="btn_cancel" id="submit_cl"/>
    </div>
</div>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>帐号列表</h3>
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
                    <th class="head0" width="10%">qq</th>
                    <th class="head0" width="10%">联系方式</th>
                    <th class="head0" width="10%">角色类型</th>
                    <th class="head0" width="10%">权限归属地</th>
                    <th class="head0" width="10%">最后登陆时间</th>
                    <th class="head0" width="10%">最后登陆ip</th>
                    <th class="head0" width="10%">添加时间</th>
                    <th class="head1" width="5%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.admins}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.admin_id}</td>
                            <td>${bean.user_name}</td>
                            <td>${bean.qq}</td>
                            <td>${bean.phone}</td>
                            <td>${bean.role_name}</td>
                            <td>${bean.address}</td>
                            <td>${bean.login_time}</td>
                            <td>${bean.login_ip}</td>
                            <td>${bean.reg_time}</td>
                            <td class="center">
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '添加'}">

                                        </c:when>
                                        <c:when test="${funcion.func_name == '重置密码'}">
                                            <a href="###" onclick="updatePass(${bean.admin_id})">重置密码</a>||
                                        </c:when>
                                        <c:when test="${funcion.func_name == '注销账号'}">
                                            <a href="###" onclick="updateAdminValid(${bean.admin_id})">注销账号</a> ||
                                        </c:when>
                                        <c:otherwise>
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?admin_id=${bean.admin_id}')">${funcion.func_name}</a> ||
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
                        url:"${ctx}/admin/accounts/list?&father_id=${funcions.obj.father_id}&currentPage=" + n,
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                }
                function updatePass(admin_id) {
                    $("#admin_id").val(admin_id);
                    $(".zhezhao").fadeIn(300);
                    $(".Mask2").fadeIn(300);
                }
                $("#submit_cl").click(function () {
                    $(".zhezhao").hide();
                    $(".Mask2").hide();
                })

                $("#submit_bt").click(function () {
                    var data = "admin_id="+$("#admin_id").val()+"&user_pass="+$("#user_pass").val();
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/accounts/resetPass',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                $.ajax({
                                    type:'GET',
                                    url:"${ctx}/admin/accounts/list?&father_id=${funcions.obj.father_id}&currentPage=${retInfo.obj.currentPage}",
                                    dataType:'html',
                                    success:function(data){
                                        $("#main").html(data);
                                    },
                                    error:function(){
                                        alert("error");
                                    }
                                });
                            } else {
                                alert(data.tip);
                            }
                        }
                    });
                });

                function updateAdminValid(admin_id) {
                    var data="admin_id="+admin_id;
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/accounts/cancelAccounts',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                $.ajax({
                                    type:'GET',
                                    url:"${ctx}/admin/accounts/list?&father_id=${funcions.obj.father_id}&currentPage=${retInfo.obj.currentPage}",
                                    dataType:'html',
                                    success:function(data){
                                        $("#main").html(data);
                                    },
                                    error:function(){
                                        alert("error");
                                    }
                                });
                            } else {
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
<script type="text/javascript">
    $(document).ready(function(){
        kkpager.selectPage( ${retInfo.obj.currentPage})

    });
</script>