<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<div class="zhezhao"></div>
<div id="updatePass" style="width: 100%;display: none;">
    <div class="Mask2" style="display: block;">
        <h3>修改密码</h3><hr/>
        <dl class="fill_singleChoice">
            <dt>原密码：</dt>
            <dd><input type="password" id="old_pass" /></dd>
        </dl>
        <dl class="fill_singleChoice">
            <dt>新密码：</dt>
            <dd><input type="password" id="new_pass" /></dd>
        </dl>
        <div class="styleButton" style="text-align: center;">
            <button class="btn_b" id="btn_update_pass">保存</button>
            <button class="btn_b" id="btn_cancel_pass">取消</button>
        </div>
    </div>
</div>
<div class="topheader">
    <div class="left">
        <h1 class="logo">厚通.<span>速邮汇</span></h1>
        <span class="slogan">后台管理系统</span>

        <%--<div class="search">--%>
        <%--<form action="" method="post">--%>
        <%--<input type="text" name="keyword" id="keyword" value="请输入" />--%>
        <%--<button class="submitbutton"></button>--%>
        <%--</form>--%>
        <%--</div><!--search-->--%>

        <br clear="all" />

    </div><!--left-->

    <div class="right">
        <!--<div class="notification">
            <a class="count" href="ajax/notifications.html"><span>9</span></a>
        </div>-->
        <div class="userinfo">
            <img src="${ctx}/images/thumbs/avatar.png" alt="" />
            <span>管理员</span>
        </div><!--userinfo-->

        <div class="userinfodrop">
            <%--<div class="avatar">--%>
            <%--<a href=""><img src="images/thumbs/avatarbig.png" alt="" /></a>--%>
            <%--<div class="changetheme">--%>
            <%--切换主题: <br />--%>
            <%--<a class="default"></a>--%>
            <%--<a class="blueline"></a>--%>
            <%--<a class="greenline"></a>--%>
            <%--<a class="contrast"></a>--%>
            <%--<a class="custombg"></a>--%>
            <%--</div>--%>
            <%--</div><!--avatar-->--%>
            <div class="userdata">
                <%--<h4>Juan</h4>--%>
                <%--<span class="email">youremail@yourdomain.com</span>--%>
                <ul>
                    <li><a href="###" onclick="updatePass()">修改密码</a></li>
                    <li><a href="###" onclick="cache();">清除缓存</a></li>
                    <li><a href="${ctx}/skip">退出</a></li>
                </ul>
            </div><!--userdata-->
        </div><!--userinfodrop-->
    </div><!--right-->
</div><!--topheader-->
<script type="text/javascript">
    function cache(){

        var data = "";
        $.ajax({
            type:'post',
            url:'${ctx}/admin/clear/cache',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    alert("成功");
                    window.location.reload();
                }else{
                    alert(data.tip);
                }
            }

        });
    }

    function updatePass(){
        $(".zhezhao").fadeIn(300);
        $("#updatePass").fadeIn(300);
    }

    $("#btn_cancel_pass").click(function () {
        $(".zhezhao").hide();
        $("#updatePass").hide(300);
    })

    $("#btn_update_pass").click(function () {
        var data="old_user_pass="+$("#old_pass").val()+"&user_pass="+$("#new_pass").val();
        $.ajax({
            type: 'post',
            url: '${ctx}/admin/updatePsd',
            dataType: 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (data) {
                if (data.mark == "0") {
                    window.location.href="${ctx}/login"
                } else {
                    alert(data.tip);
                }
            }
        });
    })
</script>