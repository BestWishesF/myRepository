<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>

<div class="zhezhao" id="exp_ord_zhezhao"></div>
<div class="Mask2" id="Mask1" style="display: none;position: fixed;">
    <div id="update">
        <dl style="margin-left: 15px;">发件人信息</dl>
        <hr />
        <dl class="fill_singleChoice" id="send_info">
            <input id="exp_ord_id" type="hidden">
            <p>发件人姓名：<input id="send_name" type="text" /></p>
            <p>发件人电话：<input id="send_mobile" type="text" /></p>
            <p>发件人地址：
                <select id="send_p"></select>省&nbsp;
                <select id="send_c"></select>市&nbsp;
                <select id="send_a"></select>区</p>
            <p style="text-indent: 6em;"><input id="send_address" type="text" /></p>
            <p style="text-indent: 1em;">订单类型：
                <select id="exp_ord_type">
                </select></p>
        </dl>
    </div>
    <div class="styleButton" style="text-align: center;">
        <button class="btn_b" id="btn_save">保存</button>
        <button class="btn_b" id="btn_cancel">取消</button>
    </div>
</div>

<div class="Mask2" id="Mask2" style="display: none;position: fixed;width: 60%;overflow-y:auto; height:400px;">
    <div id="assign">
        <dl style="margin-left: 15px;">指派代理人</dl>
        <hr />
        <dl class="fill_singleChoice" id="assign_info">
            <div>
                <p>
                    <input id="ord_id" type="hidden">
                    <input id="add_city" type="hidden">
                    <input id="agent_mobile" type="text"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn_b" id="btn_search">查询</button>
                </p>
            </div>
            <div id="agent">
                <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                    <colgroup>
                        <col class="con0"/>
                        <col class="con1"/>
                        <col class="con0"/>
                        <col class="con1"/>
                        <col class="con0"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>&nbsp;</th>
                        <th class="head0" width="10%">id</th>
                        <th class="head0" width="30%">代理人姓名</th>
                        <th class="head0" width="20%">代理人电话</th>
                        <th class="head0" width="40%">代理人地址</th>
                    </tr>
                    </thead>
                    <tbody id="agent_info">

                    </tbody>
                </table>
            </div>
        </dl>
    </div>
    <div class="styleButton" style="text-align: center;">
        <button class="btn_b" id="btn_assign">保存</button>
        <button class="btn_b" id="btn_assign_cancel">取消</button>
    </div>
</div>

<body class="withvernav">
<div class="bodywrapper">

    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>
                    <c:if test="${retInfo.obj.exp_ord_state == 0}">已取消列表</c:if>
                    <c:if test="${retInfo.obj.exp_ord_state == 1}">待接单列表</c:if>
                    <c:if test="${retInfo.obj.exp_ord_state == 2}">待揽件列表</c:if>
                    <c:if test="${retInfo.obj.exp_ord_state == 3}">待支付列表</c:if>
                    <c:if test="${retInfo.obj.exp_ord_state == 4}">待入库列表</c:if>
                    <c:if test="${retInfo.obj.exp_ord_state == 5}">已完成列表</c:if>
                </h3>
            </div><!--contenttitle-->
            <div class="details">
                <div class="search_input">
                    <div id="search" style="display: none">
                        <p>
                            发件人电话:<input type="text" name="add_mobile_phone" id="add_mobile_phone"/></p>
                        <p>
                            厚通订单号:<input type="text" name="ht_number" id="ht_number"/></p>
                        <p>
                            快递运单号:<input type="text" name="express_number" id="express_number"/></p>
                        <p>
                            <button class="submit radius2" id="search_button">搜索</button>
                            <button class="submit radius2" id="search_button_end">搜索结束</button>
                        </p>
                    </div>

                    <p>
                        <button class="submit radius2" id="search_button_none">搜索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button class="submit radius2" id="ref">重置</button>
                    </p>
                </div>
            </div><!--details-->
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="dyntable" width="2400px">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                    <col class="con1"/>
                    <col class="con0"/>
                </colgroup>
                <thead>
                <tr>
                    <th class="head0" width="5%">id</th>
                    <th class="head0" width="5%">用户id</th>
                    <th class="head0" width="5%">寄件人姓名</th>
                    <th class="head0" width="5%">电话</th>
                    <th class="head0" width="15%">详细地址</th>
                    <th class="head0" width="2%">快递公司</th>
                    <th class="head0" width="2%">数量</th>
                    <th class="head0" width="2%">重量</th>
                    <th class="head0" width="2%">订单金额</th>
                    <th class="head0" width="2%">小费</th>
                    <th class="head0" width="2%">优惠券金额</th>
                    <th class="head0" width="2%">折扣金额</th>
                    <th class="head0" width="2%">代理人优惠金额</th>
                    <th class="head0" width="2%">实付金额</th>
                    <th class="head0" width="5%">订单编号</th>
                    <th class="head0" width="10%">下单时间</th>
                    <c:if test="${retInfo.obj.exp_ord_state == 1 || retInfo.obj.exp_ord_state == 2}">
                        <th class="head0" width="10%">上门开始时间</th>
                        <th class="head0" width="10%">上门结束时间</th>
                    </c:if>
                    <c:if test="${retInfo.obj.exp_ord_state > 1}">
                        <th class="head0" width="5%">代理人id</th>
                        <th class="head0" width="5%">代理人姓名</th>
                        <th class="head0" width="5%">代理人电话</th>
                    </c:if>
                    <td width="8%">操作</td>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.expOrds}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.exp_ord_id}</td>
                            <td>${bean.memb_id}</td>
                            <td>${bean.add_name}</td>
                            <td>${bean.add_mobile_phone}${bean.add_telephone}</td>
                            <td>${bean.add_detail_address}</td>
                            <td>${bean.express_name}</td>
                            <td>${bean.exp_ord_size}</td>
                            <td>${bean.exp_ord_weight}</td>
                            <td>${bean.total_amount}</td>
                            <td>${bean.exp_ord_gratuity}</td>
                            <td>${bean.coupon_amount}</td>
                            <td>${bean.discount_amount}</td>
                            <td>${bean.adjusted_amount}</td>
                            <td>${bean.exp_ord_pay_amount}</td>
                            <td>${bean.exp_ord_number}</td>
                            <td>
                                <fmt:formatDate value="${bean.exp_ord_time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                                dateStyle="long"/>
                            </td>
                            <c:if test="${retInfo.obj.exp_ord_state == 1 || retInfo.obj.exp_ord_state == 2}">
                                <td>
                                    <fmt:formatDate value="${bean.door_start_time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                                    dateStyle="long"/>
                                </td>
                                <td>
                                    <fmt:formatDate value="${bean.door_end_time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                                    dateStyle="long"/>
                                </td>
                            </c:if>
                            <c:if test="${retInfo.obj.exp_ord_state > 1}">
                                <td>${bean.agent_id}</td>
                                <td>${bean.agent_name}</td>
                                <td>${bean.agent_phone}</td>
                            </c:if>
                            <td>
                                <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                                    <c:choose>
                                        <c:when test="${funcion.func_name == '入库'}">
                                            <c:if test="${retInfo.obj.exp_ord_state == 4}">
                                                <a class="btn_a" onclick="storage(${bean.exp_ord_id},${bean.express_id})" href="###">入库</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:when test="${funcion.func_name == '修改'}">
                                            <c:if test="${retInfo.obj.exp_ord_state >0 && retInfo.obj.exp_ord_state<5}">
                                                <a class="btn_a" onclick="update(${bean.exp_ord_id})" href="###">${funcion.func_name}</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:when test="${funcion.func_name == '删除'}">
                                            <c:if test="${retInfo.obj.exp_ord_state < 4 && retInfo.obj.exp_ord_state >0}">
                                                <a class="btn_a" onclick="delExpOrd(${bean.exp_ord_id})" href="###">${funcion.func_name}</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:when test="${funcion.func_name == '指派'}">
                                            <c:if test="${retInfo.obj.exp_ord_state == 1}">
                                                <a class="btn_a" onclick="assignExpOrd(${bean.exp_ord_id},${bean.add_city})" href="###">${funcion.func_name}</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:when test="${funcion.func_name == '退款'}">
                                            <c:if test="${retInfo.obj.exp_ord_state == 4 || retInfo.obj.exp_ord_state == 5}">
                                                <a class="btn_a" onclick="refundExpOrd(${bean.exp_ord_id})" href="###">${funcion.func_name}</a> ||
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <a  href="#"  onclick="main('${ctx}${funcion.link_url}?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&exp_ord_id=${bean.exp_ord_id}&memb_type=0&father_id=${funcion.func_id}')">${funcion.func_name}</a> ||
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

                function storage(exp_ord_id, express_id) {
                    var data = "exp_ord_id=" + exp_ord_id + "&express_id=" + express_id;
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/express/order/storage',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                var href = "${ctx}/admin/express/order/batchPrint?exp_ord_id=" + exp_ord_id;
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
                            } else {
                                alert(data.tip);
                            }
                        }

                    });
                }

                //init
                $(function () {
                    var totalPage = ${retInfo.obj.totalPage};
                    var totalRecords = ${retInfo.obj.totalRecord};
                    var pageNo = ${retInfo.obj.currentPage};
                    var ctx = "${ctx}";
                    if (!pageNo) {
                        pageNo = 1;
                    }
                    //生成分页
                    //有些参数是可选的，比如lang，若不传有默认值
                    kkpager.generPageHtml({
                        pno: pageNo,
                        //总页码
                        total: totalPage,
                        //总数据条数
                        totalRecords: totalRecords,

                    });
                });
                function page(n) {
                    var href = "${ctx}/admin/express/order/list" + "?currentPage=" + n + "&exp_ord_state=${retInfo.obj.exp_ord_state}&memb_type=0&father_id=${funcions.obj.father_id}";

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
                $("#search_button_none").click(function () {
                    $("#search_button_none").hide();
                    $("#search").show();
                });
                $("#search_button_end").click(function () {
                    $("#search_button_none").show();
                    $("#search").hide();
                });
                $("#search_button").click(function () {
                    var href = "${ctx}/admin/express/order/searchList?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&add_mobile_phone=" + $("#add_mobile_phone").val() + "&ht_number=" + $("#ht_number").val() + "&express_number=" + $("#express_number").val()+"&memb_type=0&father_id=${funcions.obj.father_id}";
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
                });
                $("#ref").click(function () {
                    $("#add_mobile_phone").val("");
                    $("#ht_number").val("");
                    $("#express_number").val("");
                    var href = "${ctx}/admin/express/order/list?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&memb_type=0&father_id=${funcions.obj.father_id}";
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
                })
                function update(exp_ord_id){
                    $("#exp_ord_zhezhao").fadeIn(300);
                    $("#Mask1").fadeIn(300);
                    var data = "exp_ord_id=" + exp_ord_id ;
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/express/order/findExpOrd',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                var db=data.obj;
                                $("#exp_ord_id").val(db.exp_ord_id);
                                $("#send_name").val(db.send_name);
                                $("#send_mobile").val(db.send_mobile);
                                $("#send_address").val(db.send_address);
                                $.each(db.send_pn,function (index, item) {
                                    if(item.dict_id==db.send_p){
                                        $("#send_p").append('<option value="'+item.dict_id+'" selected="selected">'+item.province_name+'</option>');
                                    }else{
                                        $("#send_p").append('<option value="'+item.dict_id+'">'+item.province_name+'</option>');
                                    }
                                })
                                $.each(db.send_cn,function (index, item) {
                                    if(item.dict_id==db.send_c){
                                        $("#send_c").append('<option value="'+item.dict_id+'" selected="selected">'+item.city_name+'</option>');
                                    }else{
                                        $("#send_c").append('<option value="'+item.dict_id+'">'+item.city_name+'</option>');
                                    }
                                })
                                $.each(db.send_an,function (index, item) {
                                    if(item.dict_id==db.send_a){
                                        $("#send_a").append('<option value="'+item.dict_id+'" selected="selected">'+item.area_name+'</option>');
                                    }else{
                                        $("#send_a").append('<option value="'+item.dict_id+'">'+item.area_name+'</option>');
                                    }
                                })
                                if(db.exp_ord_type==1){
                                    $("#exp_ord_type").append('<option value="1" selected="selected">电子面单</option> ' +
                                            '<option value="2">纸质面单</option>');
                                }else if(db.exp_ord_type==2){
                                    $("#exp_ord_type").append('<option value="1">电子面单</option> ' +
                                            '<option value="2" selected="selected">纸质面单</option>');
                                }
                            } else {
                                alert(data.tip);
                            }
                        }

                    });
                }
                $("#btn_cancel").click(function () {
                    $(".update_info").val("");
                    $("#exp_ord_zhezhao").hide();
                    $("#Mask1").hide();
                })

                $("#send_p").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/city?parent_id='+$("#send_p").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#send_c").html(data);
                            $.ajax({
                                type:'GET',
                                url:'${ctx}/admin/dict/area?parent_id='+$("#send_c").val()+'&t='+$.now(),
                                dataType:'html',
                                success:function(data){
                                    $("#send_a").html(data);
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
                $("#send_c").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/area?parent_id='+$("#send_c").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#send_a").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                });

                $("#btn_save").click(function () {
                    var data="exp_ord_id="+$("#exp_ord_id").val()+"&add_name="+$("#send_name").val()+"&add_mobile_phone="+$("#send_mobile").val()+
                            "&add_province="+$("#send_p").val()+"&add_city="+$("#send_c").val()+"&add_region="+$("#send_a").val()+
                            "&add_detail_address="+$("#send_address").val()+"&exp_ord_type="+$("#exp_ord_type").val();
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/express/order/updateExpOrd',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                alert("成功");

                                var href = "${ctx}/admin/express/order/list" + "?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&memb_type=0&father_id=${funcions.obj.father_id}";

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
                            } else {
                                alert(data.tip);
                            }
                        }
                    });
                })

                function delExpOrd(exp_ord_id) {
                    var data="exp_ord_id="+exp_ord_id;
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/express/order/delExpOrd',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                alert("成功");

                                var href = "${ctx}/admin/express/order/list" + "?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&memb_type=0&father_id=${funcions.obj.father_id}";

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
                            } else {
                                alert(data.tip);
                            }
                        }
                    });
                }

                function assignExpOrd(exp_ord_id, add_city) {
                    $("#exp_ord_zhezhao").fadeIn(300);
                    $("#Mask2").fadeIn(300);

                    $("#ord_id").val(exp_ord_id);
                    $("#add_city").val(add_city);
                    getAgent("", add_city);
                }

                $("#btn_assign_cancel").click(function () {
                    $(".update_info").val("");
                    $("#exp_ord_zhezhao").hide();
                    $("#Mask2").hide();
                })

                $("#btn_assign").click(function () {
                    $.each($("input[name='radioName']"),function (index, item) {
                        if(item.checked){
                            var data="agent_id="+item.value+"&exp_ord_id="+$("#ord_id").val();
                            $.ajax({
                                type: 'post',
                                url: '${ctx}/admin/express/order/assignAgent',
                                dataType: 'json',
                                data: data,
                                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                                success: function (data) {
                                    if (data.mark == "0") {
                                        alert("成功");

                                        var href = "${ctx}/admin/express/order/list" + "?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&memb_type=0&father_id=${funcions.obj.father_id}";

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
                                    } else {
                                        alert(data.tip);
                                    }
                                }
                            });
                        }
                    })
                })

                $("#btn_search").click(function () {
                    var agent_mobile=$("#agent_mobile").val();
                    var add_city=$("#add_city").val();
                    getAgent(agent_mobile, add_city);
                })

                function getAgent(agent_phone, add_city) {
                    $.ajax({
                        type: 'get',
                        url: '${ctx}/admin/agent/findAssignAgentList?agent_phone='+agent_phone+'&add_city='+add_city,
                        dataType: 'json',
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                var db=data.obj;
                                $("#agent_info").html("");
                                $.each(db,function (index, item) {
                                    $("#agent_info").append('<tr> ' +
                                            '<td><input value="'+item.agent_id+'" type="radio" class="radio" name="radioName" /></td> ' +
                                            '<td>'+item.agent_id+'</td> ' +
                                            '<td>'+item.agent_name+'</td> ' +
                                            '<td>'+item.agent_phone+'</td> ' +
                                            '<td>'+item.agent_address+'</td> ' +
                                            '</tr>');
                                })
                            } else {
                                alert(data.tip);
                            }
                        }
                    })
                }

                function refundExpOrd(exp_ord_id) {
                    $.ajax({
                        type: 'get',
                        url: '${ctx}/admin/express/order/refundExpOrd?exp_ord_id='+exp_ord_id,
                        dataType: 'json',
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                alert("退款申请成功.");
                                var href = "${ctx}/admin/express/order/list" + "?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&memb_type=0&father_id=${funcions.obj.father_id}";
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
                            } else {
                                alert(data.tip);
                            }
                        }
                    })
                }
            </script>

            <br/><br/>
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