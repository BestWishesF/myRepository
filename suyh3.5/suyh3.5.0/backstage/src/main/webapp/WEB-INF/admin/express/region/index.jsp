<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
    <style type="text/css">
        .styleButton input.btn_save {
            background: #f0801d none repeat scroll 0 0;
            border: 1px solid #f0801d;
            color: #fff;
            text-shadow: 0 1px 0 #444;
        }
        .styleButton input {
            border-radius: 3px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 20px;
            padding: 6px 18px;
        }
        .styleButton input.btn_cancel {
            background: #f8f8f8 none repeat scroll 0 0;
            border: 1px solid #ddd;
            color: #555;
        }
    </style>
</head>
<div class="zhezhao" id="region_zhezhao"></div>
<div class="Mask2" id="Mask1" style="display: none">
    <div id="apply_type">
        <dl class="fill_singleChoice">
            <dt>区域：</dt>
            <dd>
                省:<select id="province" style="line-height: 35px;height: 35px;">
                <c:forEach items="${retInfo.obj.province}" var="bean">
                    <option value ="${bean.dict_id}">${bean.province_name}</option>
                </c:forEach>

            </select>
                市:<select id="city" style="line-height: 35px;height: 35px;">
                <c:forEach items="${retInfo.obj.city}" var="bean">
                    <option value ="${bean.dict_id}">${bean.city_name}</option>
                </c:forEach>
            </select>
                区:<select id="area_id" style="line-height: 35px;height: 35px;">
                <c:forEach items="${retInfo.obj.areaDtos}" var="bean">
                    <option value ="${bean.dict_id}">${bean.area_name}</option>
                </c:forEach>
            </select>
            </dd>
            <dt>面单类型：</dt>
            <dd>
                <select id="eoa_type" style="line-height: 35px;height: 35px;">
                    <option value="1">电子面单</option>
                    <option value="2">纸质面单</option>
                </select>
            </dd>
            <dt>首单奖励：</dt>
            <dd><input type="text" id="first_single_bonus"/></dd>
            <dt>多单奖励：</dt>
            <dd><input type="text" id="more_single_bonus"/></dd>
            <dt>补贴重量：</dt>
            <dd><input type="text" id="subsidy_weight"/></dd>
            <dt>补贴金额：</dt>
            <dd><input type="text" id="subsidy_bonus"/></dd>
        </dl>
    </div>

    <div class="styleButton" style="text-align: center;">
        <input type="button" value="保存" class="btn_save" id="submit_bt"/>
        <input type="button" value="取消" class="btn_cancel" id="btn_cancel"/>
    </div>
</div>

<div class="Mask2" id="Mask2" style="display: none">
    <div>
        <input type="hidden" id="eoa_id"/>
        <dl class="fill_singleChoice">
            <dt>区域：</dt>
            <dd>
                <span id="area" style="line-height: 35px;height: 35px;"></span>
            </dd>
            <dt>面单类型：</dt>
            <dd>
                <select id="eoa_type_update" style="line-height: 35px;height: 35px;">

                </select>
            </dd>
            <dt>首单奖励：</dt>
            <dd><input type="text" id="first_single_bonus_update"/></dd>
            <dt>多单奖励：</dt>
            <dd><input type="text" id="more_single_bonus_update"/></dd>
            <dt>补贴重量：</dt>
            <dd><input type="text" id="subsidy_weight_update"/></dd>
            <dt>补贴金额：</dt>
            <dd><input type="text" id="subsidy_bonus_update"/></dd>
        </dl>
    </div>

    <div class="styleButton" style="text-align: center;">
        <input type="button" value="保存" class="btn_save" id="submit_bt_update"/>
        <input type="button" value="取消" class="btn_cancel" id="region_cancel" />
    </div>
</div>
<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>快递开通区域</h3>
            </div><!--contenttitle-->
            <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                <c:choose>
                    <c:when test="${funcion.func_name == '添加'}">
                        <div class="details">
                            <div class="search_input">
                                <p><button class="submit radius2" onclick="apply()">添加</button></p>
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
                    <th class="head0" width="30%">开通区域</th>
                    <th class="head0" width="10%">面单类型</th>
                    <th class="head0" width="10%">首单奖励金额</th>
                    <th class="head0" width="10%">多单奖励金额</th>
                    <th class="head0" width="10%">补贴重量值</th>
                    <th class="head0" width="10%">重量补贴金额</th>
                    <th class="head1" width="15%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.expOpenRegions}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.eoa_id}</td>
                            <td>${bean.region_id}</td>
                            <td>
                                <c:if test="${bean.eoa_type==1}">
                                    电子面单
                                </c:if>
                                <c:if test="${bean.eoa_type==2}">
                                    纸质面单
                                </c:if>
                            </td>
                            <td>${bean.first_single_bonus}</td>
                            <td>${bean.more_single_bonus}</td>
                            <td>${bean.subsidy_weight}</td>
                            <td>${bean.subsidy_bonus}</td>
                            <td class="center">
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '添加'}">

                                        </c:when>
                                        <c:when test="${funcion.func_name == '删除'}">
                                            <a onclick="deleteExp(${bean.eoa_id})" href="###">删除</a> ||
                                        </c:when>
                                        <c:when test="${funcion.func_name == '修改'}">
                                            <a onclick="updateExp(${bean.eoa_id})" href="###">修改</a> ||
                                        </c:when>
                                        <c:otherwise>
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?currentPage=1&eoa_id=${bean.eoa_id}&father_id=${funcion.func_id}')">${funcion.func_name}</a> ||
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
                    var href = "${ctx}/admin/express/open/region/list" + "?currentPage=" + n
                        + "&express_id=${retInfo.obj.express_id}&father_id=${funcions.obj.father_id}";
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

            <script type="text/javascript">
                function apply(){
                    $("#region_zhezhao").fadeIn(300);
                    $("#Mask1").fadeIn(300);
                }

                $("#btn_cancel").click(function () {
                    $("#region_zhezhao").hide();
                    $("#Mask1").hide();
                })

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

                function deleteExp(eoa_id){
                    var data = "eoa_id=" + eoa_id + "&state=1";
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/express/open/region/update',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                var href = "${ctx}/admin/express/open/region/list?currentPage=1" +
                                    "&express_id=${retInfo.obj.express_id}&father_id=${funcions.obj.father_id}";
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
                            }else{
                                alert(data.tip);
                            }
                        }
                    });
                }
                $("#submit_bt").click(function(){
                    var data = "express_id=${retInfo.obj.express_id}"+"&region_id="+$("#area_id").val() + "&state=0&eoa_type="+$("#eoa_type").val()+"&first_single_bonus=" +
                        $("#first_single_bonus").val()+"&more_single_bonus="+$("#more_single_bonus").val()+"&subsidy_weight="+$("#subsidy_weight").val()+"&subsidy_bonus="+$("#subsidy_bonus").val();
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/express/open/region/insert',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                var href = "${ctx}/admin/express/open/region/list?currentPage=1" +
                                    "&express_id=${retInfo.obj.express_id}&father_id=${funcions.obj.father_id}";
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
                            }else{
                                alert(data.tip);
                            }
                        }
                    });
                });

                function updateExp(eoa_id) {
                    var data = "eoa_id=" + eoa_id;
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/express/open/region/findRegionById',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                var obj=data.obj;
                                $("#region_zhezhao").fadeIn(300);
                                $("#Mask2").fadeIn(300);
                                $("#eoa_id").val(eoa_id);
                                $("#area").html(obj.region_id);
                                $("#first_single_bonus_update").val(obj.first_single_bonus);
                                $("#more_single_bonus_update").val(obj.more_single_bonus);
                                $("#subsidy_weight_update").val(obj.subsidy_weight);
                                $("#subsidy_bonus_update").val(obj.subsidy_bonus);
                                if(obj.eoa_type==1){
                                    $("#eoa_type_update").html('<option value="1" selected>电子面单</option> ' +
                                        '<option value="2">纸质面单</option>');
                                }else{
                                    $("#eoa_type_update").html('<option value="1">电子面单</option> ' +
                                        '<option value="2" selected>纸质面单</option>');
                                }
                            }else{
                                alert(data.tip);
                            }
                        }
                    });
                }

                $("#region_cancel").click(function () {
                    $("#region_zhezhao").hide();
                    $("#Mask2").hide();
                })

                $("#submit_bt_update").click(function () {
                    var data = "eoa_id="+$("#eoa_id").val() + "&state=0&eoa_type="+$("#eoa_type_update").val()+"&first_single_bonus=" +
                        $("#first_single_bonus_update").val()+"&more_single_bonus="+$("#more_single_bonus_update").val()+
                        "&subsidy_weight="+$("#subsidy_weight_update").val()+"&subsidy_bonus="+$("#subsidy_bonus_update").val();
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/express/open/region/update',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                var href = "${ctx}/admin/express/open/region/list?currentPage=1" +
                                    "&express_id=${retInfo.obj.express_id}&father_id=${funcions.obj.father_id}";
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
                            }else{
                                alert(data.tip);
                            }
                        }
                    });
                })
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