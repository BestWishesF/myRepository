<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
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
                <h3>代理人列表</h3>
            </div><!--contenttitle-->
            <div class="details">
                <div class="search_input">
                    <div id="search" style="display: none">
                        <p>
                            代理人ID:<input type="text" id="agent_id"/></p>
                        <p>
                            代理人手机:<input type="text" id="agent_mobile"/></p>
                        <p>
                            代理人姓名:<input type="text" id="agent_name"/></p>
                        <p>
                            <button class="submit radius2" id="search_button">搜索</button>
                            <button class="submit radius2" id="search_button_end">搜索结束</button>
                        </p>
                    </div>

                    <p>
                        <button class="submit radius2" id="search_button_none">搜索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </p>
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
                    <th class="head0" width="8%">身份信息</th>
                    <th class="head0" width="8%">头像</th>
                    <th class="head0" width="10%">手机号码</th>
                    <th class="head0" width="5%">评分</th>
                    <th class="head0" width="4%">性别</th>
                    <th class="head0" width="5%">生日</th>
                    <th class="head0" width="4%">余额</th>
                    <th class="head0" width="5%">总收入</th>
                    <th class="head0" width="15%">地址</th>
                    <th class="head0" width="15%">代理区域</th>
                    <th class="head0" width="5%">账号</th>
                    <th class="head0" width="5%">状态</th>
                    <th class="head1"  width="10%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.agents}" var="bean">
                <tr class="gradeX">
                        <td>${bean.agent_id}</td>
                        <td>
                            ${bean.agent_name}
                            <br/>
                            ${bean.agent_id_number}
                        </td>
                        <td>
                            <img src="${bean.agent_head_portrait}"  height="80px"></img>
                        </td>
                        <td>
                            ${bean.agent_phone}
                            <br/>
                            ${bean.agent_spare_phone}
                        </td>
                    <td>
                            ${bean.agent_score}
                    </td>
                        <td>
                            <c:if test="${bean.agent_sex == 1}">男</c:if>
                            <c:if test="${bean.agent_sex == 2}">女</c:if>
                            <c:if test="${bean.agent_sex == 3}">其他</c:if>
                        </td>
                        <td>
                            <fmt:formatDate value="${bean.agent_birthday}"   pattern="yyyy-MM-dd" type="date" dateStyle="long" />
                        </td>
                        <td>${bean.agent_balance}</td>
                    <td>${bean.total_income}</td>
                    <td>${bean.agent_address}</td>
                    <td>${bean.area_id}</td>
                    <td>${bean.agent_pay_account}</td>
                    <td>
                        <c:if test="${bean.agent_state == 4}">上班中</c:if>
                        <c:if test="${bean.agent_state == 5}">下班中</c:if>
                    </td>
                    <td class="center">
                        <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                            <c:choose>
                                <c:when test="${funcion.func_name == '锁定'}">
                                    <a onclick="lock(${bean.agent_id} ,6 ,'${bean.token}');" href="###">锁定</a>||
                                </c:when>

                                <c:otherwise>
                                    <a  href="#"  onclick="main('${ctx}${funcion.link_url}?agent_id=${bean.agent_id}&currentPage=1&father_id=${funcion.func_id}')">${funcion.func_name}</a> ||
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
                function page(n) {
                    var href =  "${ctx}/admin/agent/list" + "?currentPage=" + n
                            + "&agent_state=${retInfo.obj.agent_state}&father_id=${funcions.obj.father_id}";
                    $.ajax({
                        type:'GET',
                        url:href,
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
<script type="text/javascript">
    function lock(agent_id ,agent_state ,token){
        var data = "agent_id=" + agent_id + "&agent_state=" + agent_state + "&token=" + token;
        $.ajax({
            type:'post',
            url:'${ctx}/admin/agent/lock',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/agent/list?currentPage=1&agent_state=${retInfo.obj.agent_state}&father_id=15",
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

    $("#search_button_none").click(function () {
        $("#search_button_none").hide();
        $("#search").show();
    });
    $("#search_button_end").click(function () {
        $("#search_button_none").show();
        $("#search").hide();
    });
    $("#search_button").click(function () {
        var agent_id=$("#agent_id").val();
        if(agent_id==null||agent_id.trim()==''){
            agent_id=0;
        }
        $.ajax({
            type:'GET',
            url:"${ctx}/admin/agent/searchList?currentPage=1&agent_id="+agent_id+"&agent_mobile=" + $("#agent_mobile").val() + "&agent_name=" + $("#agent_name").val()+"&father_id=15",
            dataType:'html',
            success:function(data){
                $("#main").html(data);
            },
            error:function(){
                alert("error");
            }
        });
    });
</script>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){
        kkpager.selectPage( ${retInfo.obj.currentPage})

    });
</script>