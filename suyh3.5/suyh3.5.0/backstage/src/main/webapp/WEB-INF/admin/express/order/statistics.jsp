<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.uniform.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/dashboard.js"></script>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>
                    数据导出列表
                </h3>
            </div><!--contenttitle-->
            <div class="overviewhead">
                下单时间: &nbsp;<input type="text" id="datepickfrom" value="${retInfo.obj.star_time_str}" /> &nbsp;
                - &nbsp;<input type="text" id="datepickto" value="${retInfo.obj.end_time_str}" />&nbsp; &nbsp;
                快递公司:&nbsp;
                <select id="express_id" style="padding: 6px 5px;border: 1px solid #ccc;border-radius: 2px;font-size: 12px;">
                    <option value="0">全部快递公司</option>
                    <c:forEach items="${retInfo.obj.express_company}" var="express_company">
                        <option value="${express_company.dict_id}" <c:if test="${express_company.dict_id==retInfo.obj.express_id}">selected</c:if>>${express_company.code_name}</option>
                    </c:forEach>
                </select>&nbsp; &nbsp;
                发件地:&nbsp;
                <select id="send_province" style="padding: 6px 5px;border: 1px solid #ccc;border-radius: 2px;font-size: 12px;">
                    <c:forEach items="${retInfo.obj.provinceDtos}" var="province">
                        <option value="${province.dict_id}" <c:if test="${province.dict_id==retInfo.obj.province_id}">selected</c:if>>${province.province_name}</option>
                    </c:forEach>
                </select>省&nbsp;
                <select id="send_city" style="padding: 6px 5px;border: 1px solid #ccc;border-radius: 2px;font-size: 12px;">
                    <c:forEach items="${retInfo.obj.cityDtos}" var="city">
                        <option value="${city.dict_id}" <c:if test="${city.dict_id==retInfo.obj.send_city}">selected</c:if>>${city.city_name}</option>
                    </c:forEach>
                </select>市&nbsp; &nbsp;
                <button class="btn_b" id="btn_assign">查询</button>&nbsp; &nbsp;|&nbsp; &nbsp;
                <button class="btn_b" id="btn_export_data">导出数据</button>
            </div><!--overviewhead-->
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" id="dyntable" width="2400px">
                <colgroup>
                    <col class="con0" />
                    <col class="con1" />
                    <col class="con0" />
                    <col class="con1" />
                    <col class="con0" />
                </colgroup>
                <thead>
                <tr>
                    <th class="head0" width="5%">订单id</th>
                    <th class="head0" width="5%">发件时间</th>
                    <th class="head0" width="20%">发件人信息</th>
                    <th class="head0" width="20%">收件人信息</th>
                    <td class="head0" width="10%">代理人信息</td>
                    <th class="head0" width="5%">厚通单号</th>
                    <th class="head0" width="5%">快递单号</th>
                    <th class="head0" width="5%">快递定价</th>
                    <th class="head0" width="5%">快递名称</th>
                    <th class="head0" width="5%">重量</th>
                    <th class="head0" width="5%">数量</th>
                    <th class="head0" width="5%">总价</th>
                    <td class="head0" width="5%">支付金额</td>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.expOrds}" var="bean">
                    <tr class="gradeX">
                        <td>${bean.exp_ord_id}</td>
                        <td>${bean.exp_ord_time}</td>
                        <td>${bean.send_name}&nbsp;${bean.send_mobile}<br>${bean.send_address}</td>
                        <td>${bean.rev_name}&nbsp;${bean.rev_mobile}<br>${bean.rev_address}</td>
                        <td>${bean.agent_name}<br>${bean.agent_phone}</td>
                        <td>${bean.ht_number}</td>
                        <td>${bean.express_number}</td>
                        <td>${bean.express_price}</td>
                        <td>${bean.express_name}</td>
                        <td>${bean.exp_ord_clt_height}</td>
                        <td>${bean.exp_ord_size}</td>
                        <td>${bean.exp_ord_amount}</td>
                        <td>${bean.exp_ord_pay_amount}</td>
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
                    var href;
                    if(${retInfo.obj.send_city > 0}){
                        href ="${ctx}/admin/express/order/collect/searchOrdBillList" + "?current_page=" + n +
                            "&express_id=${retInfo.obj.express_id}&star_time=${retInfo.obj.star_time_str}&end_time=" +
                            "${retInfo.obj.end_time_str}&send_city=${retInfo.obj.send_city}&father_id=${funcions.obj.father_id}";
                    }else {
                        href ="${ctx}/admin/express/order/collect/searchOrdBillList" + "?current_page=" + n +"&father_id=${funcions.obj.father_id}";
                    }
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

                $("#send_province").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/city?parent_id='+$("#send_province").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#send_city").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                });

                $("#btn_assign").click(function () {
                    var data = "current_page=1&express_id="+$("#express_id").val()+"&star_time="+$("#datepickfrom").val()+
                        "&end_time="+$("#datepickto").val()+"&send_city="+$("#send_city").val()+"&father_id=${funcions.obj.father_id}";
                    var href = "${ctx}/admin/express/order/collect/searchOrdBillList?" + data;
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

                $("#btn_export_data").click(function () {
                    var data = "express_id=${retInfo.obj.express_id}&star_time=${retInfo.obj.star_time_str}&end_time=" +
                        "${retInfo.obj.end_time_str}&send_city=${retInfo.obj.send_city}";
                    window.location.href = "${ctx}/admin/express/order/collect/createExcel?" + data;
                })

                $(".userinfo").click(function () {
                    $(".userinfodrop").toggle();
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