<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="${ctx}/css/jquery.treeview.css">
    <script src="${ctx}/js/jquery.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="${ctx}/js/jquery.treeview.js"></script>
    <script src="${ctx}/js/demo2.2.js"></script>
    <script src="${ctx}/js/jquery.cookie.js"></script>
    <style>
        html{
        }
        *{
            margin: 0px;padding: 0px;
        }
        body{
            font-family: 'Arial';
            font-size: 12px;
        }
        li{

            list-style: none;

        }
        .box{
            border: 1px solid lightgray;
            position: relative;
        }
        .san{
            width: 100px;padding: 10px 0px;
            border: 1px solid lightgray;
            color: #999;
            font-size: 12px;
        }
        .san:hover{
            color: black;
        }
        .btn button{
            background: lightblue;
            border: none;
            padding:5px 10px;
            border-radius: 5px;
        }
        .btn{
            position: absolute;
            left: 60px;
            bottom: -50px;
        }
    </style>
</head>
<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                更新角色
            </h1>

        </div><!--pageheader-->

        <div class="contentwrapper">
            <div class="details">
                <div class="search_input">
                    <p>
                        名称:<input type="text" name="role_name" id="role_name" value="${role.role_name}"/>
                    </p>
                    <p>
                        状态:
                        可用<input type="radio" name="is_valid" value="0" <c:if test="${role.is_valid == 0}">checked="checked" </c:if>>
                        不可用<input type="radio" name="is_valid" value="1" <c:if test="${role.is_valid == 1}">checked="checked" </c:if>/>
                        (不可用后所有角色也不可登陆)</p>
                    </p>
                    <p>
                        区域:<c:if test="${retInfo.obj.province_id == 0}">全国</c:if>
                        <c:if test="${retInfo.obj.province_id == -1 || retInfo.obj.city_id == -1 || retInfo.obj.region_id == -1}">
                            <select id="dicts">
                            <c:forEach items="${retInfo.obj.dicts}" var="bean" >
                                    <option value="${bean.dict_id}"
                                            <c:if test="${retInfo.obj.province_id == -1}">
                                                <c:if test="${role.province_id == bean.dict_id}">
                                                    selected="selected"
                                                </c:if>
                                            </c:if>
                                            <c:if test="${ retInfo.obj.city_id == -1 }">
                                                <c:if test="${role.city_id == bean.dict_id}">
                                                    selected="selected"
                                                </c:if>
                                            </c:if>
                                            <c:if test="${retInfo.obj.region_id == -1}">
                                                <c:if test="${role.region_id == bean.dict_id}">
                                                    selected="selected"
                                                </c:if>
                                            </c:if>

                                    >${bean.code_name}</option>
                            </c:forEach>
                            </select>
                        </c:if>
                        <c:if test="${retInfo.obj.divide_id == -1}">
                            <select id="divides">
                                <c:forEach items="${retInfo.obj.divides}" var="bean" >
                                    <option value="${bean.divide_id}"
                                      <c:if test="${role.divide_id == bean.divide_id}">
                                        selected="selected"
                                      </c:if>
                                    >${bean.divide_name}</option>
                                </c:forEach>
                            </select>
                        </c:if>
                        <c:if test="${retInfo.obj.divide_id > 0}">
                            ${retInfo.obj.divides}
                        </c:if>
                    </p>

                    <p>
                        权限:
                    <div class="box">
                        <ul id="tree" class="filetree">
                            <c:forEach items="${functions.obj}" var="bean">
                                <li class="menu"><input type="checkbox" name="" value="${bean.func_id}"  <c:if test="${bean.is_check == 0}">checked="checked" </c:if>  >${bean.func_name}
                                    <c:if test="${bean.is_father == 0}">
                                        <ul>
                                            <c:forEach items="${bean.tdHtFuncionDtos}" var="bean1">
                                                <li class="menu"><input type="checkbox" name="" value="${bean1.func_id}"  <c:if test="${bean1.is_check == 0}">checked="checked" </c:if>>${bean1.func_name}
                                                    <c:if test="${bean1.is_father == 0}">
                                                        <ul>
                                                            <c:forEach items="${bean1.tdHtFuncionDtos}" var="bean2">
                                                                <li class="menu"><input type="checkbox" name="" value="${bean2.func_id}"  <c:if test="${bean2.is_check == 0}">checked="checked" </c:if>>${bean2.func_name}
                                                                    <c:if test="${bean2.is_father == 0}">
                                                                        <ul>
                                                                            <c:forEach items="${bean2.tdHtFuncionDtos}" var="bean3">
                                                                                <li class="menu"><input type="checkbox" name="" value="${bean3.func_id}" <c:if test="${bean3.is_check == 0}">checked="checked" </c:if>>${bean3.func_name}
                                                                                    <c:if test="${bean3.is_father == 0}">
                                                                                        <ul>
                                                                                            <c:forEach items="${bean3.tdHtFuncionDtos}" var="bean4">
                                                                                                <li class="menu"><input type="checkbox" name="" value="${bean4.func_id}"  <c:if test="${bean4.is_check == 0}">checked="checked" </c:if>>${bean4.func_name}
                                                                                                    <c:if test="${bean4.is_father == 0}">
                                                                                                        <ul>
                                                                                                            <c:forEach items="${bean4.tdHtFuncionDtos}" var="bean5">
                                                                                                                <li class="menu"><input type="checkbox" name="" value="${bean5.func_id}"  <c:if test="${bean5.is_check == 0}">checked="checked" </c:if>>${bean5.func_name}

                                                                                                                </li>
                                                                                                            </c:forEach>
                                                                                                        </ul>
                                                                                                    </c:if>
                                                                                                </li>
                                                                                            </c:forEach>
                                                                                        </ul>
                                                                                    </c:if>
                                                                                </li>
                                                                            </c:forEach>
                                                                        </ul>
                                                                    </c:if>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:if>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    </p>


                    <p>
                        <button class="submit radius2" id="submit_bt">更新</button>
                    </p>
                </div>
            </div><!--details-->
        </div><!--contentwrapper-->
    </div><!-- centercontent -->
</div><!--bodywrapper-->
<script type="text/javascript">

    $("#submit_bt").click(function () {

        if(!(/^.{1,16}$/.test($("#role_name").val()))){
            alert("角色名称最多16个字符");
            return;
        }
        if($('input[type=checkbox]:checked').length < 1) {
            alert("最少选择一个功能");
            return;
        }
        var functions = new Array()
        $.each($('input:checkbox:checked'),function(i){
            functions[i] =  parseInt($(this).val());
        });
        var province_id = ${retInfo.obj.province_id};
        var city_id = ${retInfo.obj.city_id};
        var region_id = ${retInfo.obj.region_id};
        var divide_id = ${retInfo.obj.divide_id};
        if (province_id == -1) {
            province_id = $("#dicts").val();
        } else {
            province_id = ${role.province_id};
        }
        if (city_id == -1) {
            city_id = $("#dicts").val();
        }else {
            city_id = ${role.city_id};
        }
        if (region_id == -1) {
            region_id = $("#dicts").val();
        }else {
            region_id = ${role.region_id};
        }
        if (divide_id == -1) {
            divide_id = $("#divides").val();
        }else {
            divide_id = ${role.divide_id};
        }
        var dataJson = {
            functions:functions,
            role_name:$("#role_name").val() ,
            province_id:province_id,
            city_id:city_id,
            region_id:region_id,
            divide_id:divide_id,
            role_id:${role.role_id},
            is_valid:$('input:radio[name="is_valid"]:checked').val()
        }

        $.ajax({
            type: 'post',
            url: '${ctx}/admin/role/update',
            dataType: 'json',
            data: JSON.stringify(dataJson),
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.mark == "0") {
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/role/list?father_id=132",
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
