<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>

<div class="zhezhao" id="member_zhezhao"></div>
<div class="Mask2" id="Mask1" style="display: none;position: fixed;">
    <div id="update">
        <dl style="margin-left: 15px;">审核用户发票申请</dl>
        <hr />
        <dl class="fill_singleChoice" id="send_info">
            <input id="memb_ivc_id" type="hidden">
            <p style="height: 30px;line-height: 30px;">
                审核状态：
                <select id="exa_state" style="height: 30px;line-height: 30px;width: 50%;">
                    <option value="3">不通过</option>
                    <option value="2">通过</option>
                </select>
            </p>
            <div id="choose" style="display: none;">
                <p style="height: 30px;line-height: 30px;">
                    快递公司：
                    <select id="express_id" style="height: 30px;line-height: 30px;width: 50%;">
                        <c:forEach items="${retInfo.obj.express_company}" var="express_company">
                            <option value="${express_company.dict_id}">${express_company.code_name}</option>
                        </c:forEach>
                    </select>
                </p>
                <p style="height: 30px;line-height: 30px;">
                    快件类别：
                    <select id="type" style="height: 30px;line-height: 30px;width: 50%;">
                        <option value="1">电子面单</option>
                        <option value="2">纸质面单</option>
                    </select>
                </p>
                <p style="height: 30px;line-height: 30px;">厚通单号：<input type="text" id="ht_number" style="height: 30px;line-height: 30px;width: 50%;" /></p>
                <p id="express_number_choose" style="height: 30px;line-height: 30px;display: none;">快递单号：<input type="text" id="express_number" style="height: 30px;line-height: 30px;width: 50%;"/></p>
                <p style="height: 30px;line-height: 30px;">发件手机：<input type="text" id="send_mobile" style="height: 30px;line-height: 30px;width: 50%;"/></p>
            </div>
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
                <h3>
                    用户开票记录
                </h3>
            </div><!--contenttitle-->
            <div class="details">
                <div class="search_input">
                    <div id="search" style="display: none">
                        <p>
                            状态:
                            <select id="state" style="padding: 6px 5px;border: 1px solid #ccc;border-radius: 2px;font-size: 12px;">
                                <option value="1">待审核</option>
                                <option value="0">待支付</option>
                                <option value="2">已发货</option>
                                <option value="3">已取消</option>
                            </select>
                        </p>
                        <p>手机:<input type="text" name="mobile" id="mobile"/></p>
                        <p>
                            <button class="submit radius2" id="btn_assign">搜索</button>
                            <button class="submit radius2" id="search_button_end">搜索结束</button>
                        </p>
                    </div>
                    <p>
                        <button class="submit radius2" id="search_button_none">搜索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </p>
                </div>
            </div><!--details-->
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
                    <th class="head0" width="5%">id</th>
                    <th class="head0" width="10%">姓名</th>
                    <th class="head0" width="10%">手机号</th>
                    <th class="head0" width="15%">地址</th>
                    <th class="head0" width="5%">开票金额</th>
                    <th class="head0" width="10%">状态</th>
                    <th class="head0" width="10%">支付时间</th>
                    <th class="head0" width="10%">快递公司</th>
                    <th class="head0" width="10%">快递单号</th>
                    <th class="head0" width="10%">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${retInfo.mark == 0}">
                    <c:forEach items="${retInfo.obj.invoices}" var="bean">
                        <tr class="gradeX">
                            <td>${bean.memb_ivc_his_id}</td>
                            <td>${bean.add_name}</td>
                            <td>${bean.add_mobile_phone}</td>
                            <td>${bean.add_detail_address}</td>
                            <td>${bean.memb_inc_amount}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${bean.state==0}">
                                        待支付
                                    </c:when>
                                    <c:when test="${bean.state==1}">
                                        待审核
                                    </c:when>
                                    <c:when test="${bean.state==2}">
                                        已发货
                                    </c:when>
                                    <c:when test="${bean.state==3}">
                                        已取消
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${bean.state > 0}">${bean.memb_ivc_his_pay_time}</c:if>
                            </td>
                            <td>${bean.express_name}</td>
                            <td>${bean.memb_ivc_express_number}</td>
                            <td>
                                <c:if test="${bean.state==1}">
                                    <a class="btn_a" onclick="examine(${bean.memb_ivc_id})" href="###">审核</a>
                                </c:if>
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
                        url:"${ctx}/admin/member/invoice/list?memb_id=${retInfo.obj.memb_id}&currentPage=" + n + "&father_id=${funcions.obj.father_id}",
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
                $("#btn_assign").click(function () {
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/member/invoice/searchList?currentPage=1&memb_id=${retInfo.obj.memb_id}&mobile="+$("#mobile").val()+"&state="+$("#state").val()+"&father_id=6",
                        dataType:'html',
                        success:function(data){
                            $("#main").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                })

                $("#search_button_none").click(function () {
                    $("#search_button_none").hide();
                    $("#search").show();
                });
                $("#search_button_end").click(function () {
                    $("#search_button_none").show();
                    $("#search").hide();
                });

                function examine(id) {
                    $("#member_zhezhao").fadeIn(300);
                    $("#Mask1").fadeIn(300);
                    $("#memb_ivc_id").val(id);
                }

                $("#btn_cancel").click(function () {
                    $("#member_zhezhao").hide();
                    $("#Mask1").hide();
                })

                $("#exa_state").change(function () {
                    if($("#exa_state").val()==3){
                        $("#choose").hide();
                    }else {
                        $("#choose").show();
                    }
                })

                $("#type").change(function () {
                    if($("#type").val()==1){
                        $("#express_number_choose").hide();
                    }else {
                        $("#express_number_choose").show();
                    }
                })

                $("#btn_save").click(function () {
                    var data = "memb_ivc_id=" + $("#memb_ivc_id").val() + "&state=" + $("#exa_state").val();
                    if($("#exa_state").val()==2){
                        if($("#send_mobile").val()==null||$("#send_mobile").val().trim()==""){
                            alert("发件人手机号不能为空");
                            return false;
                        }
                        if($("#ht_number").val()==null||$("#ht_number").val().trim()==""){
                            alert("厚通单号不能为空");
                            return false;
                        }
                        data = data + "&express_id=" + $("#express_id").val() + "&type=" + $("#type").val() + "&mobile=" + $("#send_mobile").val() + "&ht_number=" + $("#ht_number").val();
                        if($("#type").val()==2){
                            if($("#express_number").val()==null||$("#express_number").val().trim()==""){
                                alert("快递单号不能为空");
                                return false;
                            }
                            data = data + "&express_number=" + $("#express_number").val();
                        }
                    }

                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/member/invoice/examine',
                        dataType : 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                var href = "";
                                if($("#exa_state").val()!=2 || data.obj.exp_ord_type == 2){
                                    href = "${ctx}/admin/member/invoice/list?memb_id=${retInfo.obj.memb_id}&currentPage=1&father_id=6"
                                }else {
                                    href = "${ctx}/admin/express/order/batchPrint?exp_ord_id=" + data.obj.exp_ord_id;
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