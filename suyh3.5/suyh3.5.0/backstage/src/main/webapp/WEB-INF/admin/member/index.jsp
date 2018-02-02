<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>

<div class="zhezhao" id="member_zhezhao"></div>
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
                            <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                <c:choose>
                                    <c:when test="${funcion.func_name == '解锁'}">
                                        <c:if test="${bean.memb_islock == 0}">
                                            <a onclick="lock(${bean.memb_id} ,1 ,'${bean.token}');" href="###">解锁</a> ||
                                        </c:if>
                                    </c:when>
                                    <c:when test="${funcion.func_name == '锁定'}">
                                        <c:if test="${bean.memb_islock != 0}">
                                            <a onclick="lock(${bean.memb_id} ,0 ,'${bean.token}');" href="###">锁定</a> ||
                                        </c:if>
                                    </c:when>
                                    <c:when test="${funcion.func_name == '修改角色'}">
                                        <a onclick="openUpdateRole(${bean.memb_id},'${bean.token}',${bean.memb_role},${bean.memb_discount})">修改角色</a> ||
                                    </c:when>
                                    <c:otherwise>
                                        <a  href="#"  onclick="main('${ctx}${funcion.link_url}?memb_id=${bean.memb_id}&currentPage=1&father_id=${funcion.func_id}')">${funcion.func_name}</a> ||
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
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/member/index?father_id=5&currentPage=1",
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
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
        $.ajax({
            type:'GET',
            url:"${ctx}/admin/member/search?father_id=5&" + "memb_id="+memb_id+"&memb_nick_name="+$("#memb_nick_name").val()+"&memb_phone="+$("#memb_phone").val() +"&currentPage=1",
            dataType:'html',
            success:function(data){
                $("#main").html(data);
            },
            error:function(){
                alert("error");
            }
        });
    });

    function openUpdateRole(member_id,token,memb_role,memb_discount) {
        $("#member_zhezhao").fadeIn(300);
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
        $("#member_zhezhao").hide();
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
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/member/index?father_id=5&currentPage=1",
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                }else{
                    alert(data.tip);
                }
            }

        });
    })
    function page(n) {
        $.ajax({
            type:'GET',
            url:"${ctx}/admin/member/index?&father_id=${funcions.obj.father_id}&currentPage=" + n,
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
</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        kkpager.selectPage( ${retInfo.obj.currentPage})

    });
</script>