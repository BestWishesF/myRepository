<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>

</head>
<div class="zhezhao" id="apply_zhezhao"></div>
<div class="Mask" id="img">
    <i></i>
    <img src="" alt="" id="photo"/>
</div>
<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>代理人待审核申请记录列表</h3>
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
                    <th class="head0" width="8%">身份信息</th>
                    <th class="head0" width="8%">手机号码</th>
                    <th class="head0" width="8%">头像</th>
                    <th class="head0" width="8%">身份证正面</th>
                    <th class="head0" width="8%">身份证反面</th>

                    <th class="head0" width="15%">地址</th>
                    <th class="head0" width="5%">账号</th>
                    <th class="head0" width="5%">状态</th>
                    <th class="head1"  width="5%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.applys}" var="bean">
                <tr class="gradeX">
                        <td>${bean.apply_id}</td>
                        <td>
                            ${bean.agent_name}
                            <br/>
                            ${bean.agent_id_number}
                        </td>
                    <td>${bean.agent_phone}</td>
                        <td>
                            <img src="${bean.agent_head_portrait}"  height="80px" onclick="oncli('${bean.agent_head_portrait}')"/>
                        </td>
                    <td>
                        <img src="${bean.apply_id_front}"  height="80px" onclick="oncli('${bean.apply_id_front}')"/>
                    </td>
                    <td>
                        <img src="${bean.apply_id_back}"  height="80px" onclick="oncli('${bean.apply_id_back}')"/>
                    </td>

                    <td>${bean.agent_address}</td>
                    <td>${bean.agent_pay_account}</td>
                    <td>
                        <c:if test="${bean.apply_state == 1}">成功</c:if>
                        <c:if test="${bean.apply_state == 2}">未审核</c:if>
                        <c:if test="${bean.apply_state == 3}">失败</c:if>
                    </td>
                    <td class="center">
                        <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                            <c:choose>
                                <c:when test="${funcion.func_name == '审核'}">
                                    <a href="#" onclick="main('${ctx}/admin/agent/apply/jump?apply_id=${bean.apply_id}&father_id=${funcion.father_id}')">审核</a>||
                                </c:when>

                                <c:otherwise>
                                    <a  href="#"  onclick="main('${ctx}${funcion.link_url}?agent_id=${bean.agent_id}&currentPage=1')">${funcion.func_name}</a> ||
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

                    });
                });
                function page(n) {
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/agent/apply/list" + "?currentPage=" + n
                        + "&apply_state=${retInfo.obj.apply_state}&father_id=${funcions.obj.father_id}",
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                }

                $("#province").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/city?parent_id='+$("#province").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#city").html(data);
                            $.ajax({
                                type:'GET',
                                url:'${ctx}/admin/dict/area?parent_id='+$("#city").val()+'&t='+$.now(),
                                dataType:'html',
                                success:function(data){
                                    $("#area_id").html(data);
                                },
                                error:function(){
                                    alert("error");
                                }
                            });
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                });
                $("#city").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/area?parent_id='+$("#city").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#area_id").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                });
                function apply(id){
                    $("#apply_id").val(id);
                    $("#apply_zhezhao").fadeIn(300);
                    $(".Mask2").fadeIn(300);
                }
                function oncli(photo_url){
                    $("#apply_zhezhao").fadeIn(300);
                    $("#img").fadeIn(300);
                    $("#photo").attr("src",photo_url);
                }
                $("#submit_bt").click(function(){


                    if(!(/^.{1,127}$/.test($("#apply_fail_reason").val()))){
                        alert("审核描述为1-128个字符");
                        return;
                    }
                    var data = "apply_state="+$('input[name="apply_state"]:checked').val()+"&apply_fail_reason="+$("#apply_fail_reason").val()
                            +"&apply_id="+$("#apply_id").val()+"&area_id="+$("#area_id").val() + "&agent_sex="+$('input[name="agent_sex"]:checked').val();
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/agent/apply/examine',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                $.ajax({
                                    type:'GET',
                                    url:"${ctx}/admin/agent/apply/list?currentPage=1&apply_state=${retInfo.obj.apply_state}&father_id=26",
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
                });
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