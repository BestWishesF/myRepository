<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                添加帐号
            </h1>

        </div><!--pageheader-->

        <div class="contentwrapper">
            <div class="details">
                <div class="search_input">
                    <p>
                        帐号:<input type="text" name="user_name" id="user_name"/>
                    </p>
                    <p>
                        密码:<input type="password" class="password" name="user_pass" id="user_pass"/>
                    </p>
                    <p>
                        Q&nbsp;&nbsp;Q:<input type="text" name="qq" id="qq"/>
                    </p>
                    <p>
                        电话:<input type="text" name="phone" id="phone"/>
                    </p>
                    <p>
                        角色:<select id="role_id"><c:forEach items="${roles}" var="bean">
                        <option value="${bean.role_id}">${bean.role_name}</option>
                    </c:forEach>
                    </select>
                    </p>

                    <p>
                        <button class="submit radius2" id="submit_bt">添加</button>
                    </p>
                </div>
            </div><!--details-->
        </div><!--contentwrapper-->
    </div><!-- centercontent -->
</div><!--bodywrapper-->
<script type="text/javascript">

    $("#submit_bt").click(function () {
        var dataJson = {
            user_name:$("#user_name").val(),
            user_pass:$("#user_pass").val(),
            qq:$("#qq").val(),
            phone:$("#phone").val(),
            role_id:$("#role_id").val(),
            province_id:$("#province_id").val(),
            city_id:$("#city_id").val(),
            region_id:$("#region_id").val(),
            divide_id:$("#divide_id").val()
        }

        $.ajax({
            type: 'post',
            url: '${ctx}/admin/accounts/insert',
            dataType: 'json',
            data: JSON.stringify(dataJson),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.mark == "0") {
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/accounts/list?&father_id=131&currentPage=1",
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
</script>
</body>
</html>
