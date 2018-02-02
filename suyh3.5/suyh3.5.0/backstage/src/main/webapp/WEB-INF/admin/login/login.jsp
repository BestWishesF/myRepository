<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>速邮汇后台管理系统</title>
    <link rel="stylesheet" href="${ctx}/css/style.default.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
    <!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="${ctx}/css/style.ie9.css"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="${ctx}/css/style.ie8.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <!--<script src="${ctx}/js/plugins/css3-mediaqueries.js"></script>-->
    <![endif]-->
</head>

<body class="loginpage">
<div class="loginbox">
    <div class="loginboxinner">

        <div class="logo">
            <h1 class="logo">厚通.<span>速邮汇</span></h1>
            <span class="slogan">后台管理系统</span>
        </div><!--logo-->

        <br clear="all" /><br />

        <div class="nousername" id="nousername">
            <div class="loginmsg">密码不正确.</div>
        </div><!--nousername-->

        <div class="username">
            <div class="usernameinner">
                <input type="text" name="username"  id="user_name"/>
            </div>
        </div>

        <div class="password">
            <div class="passwordinner">
                <input type="password" name="password" id="user_pass"/>
            </div>
        </div>

        <button id="login">登录</button>

            <%--<div class="keep"><input type="checkbox" /> 记住密码</div>--%>


    </div><!--loginboxinner-->
</div><!--loginbox-->


</body>
</html>


<script type="text/javascript">
    $(document).ready(function(){
        $("#login").click(function(){
            if($("#user_name").val()==null||$("#user_name").val()==""){
                $("#nousername").show();
                $('.loginmsg').html("用户名不能为空");
                return;
            }
            if($("#user_pass").val()==null||$("#user_pass").val()==""){
                $("#nousername").show();
                $('.loginmsg').html("密码不能为空");
                return;
            }
            var data = "user_name="+$("#user_name").val()+"&user_pass="+$("#user_pass").val();
            $.ajax({
                type:'post',
                url:'${ctx}/logins',
                dataType : 'json',
                data: data,
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(data){
                    if(data.mark=="0"){
                        window.location.href="${ctx}/admin/index?currentPage=1";
                    } else {
                        $("#nousername").show();
                        $('.loginmsg').html(data.tip);
                    }
                }

            });
        });

        document.onkeydown = function (e) {
            if (!e) e = window.event;
            if ((e.keyCode || e.which) == 13) {
                $("#login").click();
            }
        }
    });
</script>