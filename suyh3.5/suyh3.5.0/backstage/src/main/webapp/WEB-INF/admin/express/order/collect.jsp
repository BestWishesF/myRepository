<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
</head>

<div class="zhezhao" id="exp_ord_zhezhao"></div>
<div class="Mask2" style="display: none;position: fixed;">
    <div id="update">
        <dl style="margin-left: 15px;">收件人信息</dl>
        <hr />
        <dl class="fill_singleChoice" id="rev_info">
            <input id="clt_id" type="hidden">
            <p>收件人姓名：<input id="rev_name" type="text" /></p>
            <p>收件人电话：<input id="rev_mobile" type="text" /></p>
            <p>收件人地址：
                <select id="rev_p"></select>省&nbsp;
                <select id="rev_c"></select>市&nbsp;
                <select id="rev_a"></select>区</p>
            <p style="text-indent: 6em;"><input id="rev_address" type="text" /></p>
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
                    收货人信息列表
                </h3>
            </div><!--contenttitle-->
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
                    <th class="head0" width="5%">类别</th>
                    <th class="head0" width="5%">收件人姓名</th>
                    <th class="head0" width="5%">电话</th>
                    <th class="head0" width="15%">详细地址</th>

                    <th class="head0" width="5%">重量</th>
                    <th class="head0" width="5%">订单金额</th>
                    <th class="head0" width="5%">厚通单号</th>
                    <th class="head0" width="5%">快递单号</th>
                    <th class="head0" width="5%">快递公司</th>
                    <th class="head0" width="5%">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${retInfo.mark == 0}">
                <c:forEach items="${retInfo.obj.expOrdCols}" var="bean">
                <tr class="gradeX">
                    <td>${bean.exp_ord_clt_id}</td>
                    <td>${bean.exp_ord_category}</td>
                    <td>${bean.add_name}</td>
                    <td>${bean.add_mobile_phone}${bean.add_telephone}</td>
                    <td>${bean.add_detail_address}</td>

                    <td>${bean.exp_ord_clt_height}</td>
                    <td>${bean.express_price}</td>
                    <td>${bean.ht_number}</td>
                    <td>${bean.express_number}</td>
                    <td>${bean.express_name}</td>
                    <td>
                        <c:forEach items="${funcions.obj.sonFuncion}" var="funcion">
                            <c:choose>

                                <c:when test="${funcion.func_name == '修改'}">
                                    <a class="btn_a" onclick="update(${bean.exp_ord_clt_id})" href="###">修改</a>
                                </c:when>

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
                    var href = "${ctx}/admin/express/order/collect" + "?currentPage=" + n +"&exp_ord_state=${retInfo.obj.exp_ord_state}&exp_ord_id=${retInfo.obj.exp_ord_id}&memb_type=0&father_id=${funcions.obj.father_id}"  ;
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
                function update(exp_ord_clt_id) {
                    $("#exp_ord_zhezhao").fadeIn(300);
                    $(".Mask2").fadeIn(300);
                    var data="exp_ord_clt_id="+exp_ord_clt_id;
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/express/order/collect/findExpOrdClt',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                var db=data.obj;
                                $("#clt_id").val(db.clt_id);
                                $("#rev_name").val(db.rev_name);
                                $("#rev_mobile").val(db.rev_mobile);
                                $("#rev_address").val(db.rev_address);
                                $.each(db.rev_pn,function (index, item) {
                                    if(item.dict_id==db.rev_p){
                                        $("#rev_p").append('<option value="'+item.dict_id+'" selected="selected">'+item.province_name+'</option>');
                                    }else{
                                        $("#rev_p").append('<option value="'+item.dict_id+'">'+item.province_name+'</option>');
                                    }
                                })
                                $.each(db.rev_cn,function (index, item) {
                                    if(item.dict_id==db.rev_c){
                                        $("#rev_c").append('<option value="'+item.dict_id+'" selected="selected">'+item.city_name+'</option>');
                                    }else{
                                        $("#rev_c").append('<option value="'+item.dict_id+'">'+item.city_name+'</option>');
                                    }
                                })
                                $.each(db.rev_an,function (index, item) {
                                    if(item.dict_id==db.rev_a){
                                        $("#rev_a").append('<option value="'+item.dict_id+'" selected="selected">'+item.area_name+'</option>');
                                    }else{
                                        $("#rev_a").append('<option value="'+item.dict_id+'">'+item.area_name+'</option>');
                                    }
                                })
                            } else {
                                alert(data.tip);
                            }
                        }

                    });
                }

                $("#btn_cancel").click(function () {
                    $(".update_info").val("");
                    $("#exp_ord_zhezhao").hide();
                    $(".Mask2").hide();
                })

                $("#rev_p").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/city?parent_id='+$("#rev_p").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#rev_c").html(data);
                            $.ajax({
                                type:'GET',
                                url:'${ctx}/admin/dict/area?parent_id='+$("#rev_c").val()+'&t='+$.now(),
                                dataType:'html',
                                success:function(data){
                                    $("#rev_a").html(data);
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
                $("#rev_c").change(function(){
                    $.ajax({
                        type:'GET',
                        url:'${ctx}/admin/dict/area?parent_id='+$("#rev_c").val()+'&t='+$.now(),
                        dataType:'html',
                        success:function(data){
                            $("#rev_a").html(data);
                        },
                        error:function(){
                            alert("error");
                        }
                    });
                });

                $("#btn_save").click(function () {
                    var data="exp_ord_clt_id="+$("#clt_id").val()+"&add_name="+$("#rev_name").val()+"&add_mobile_phone="+$("#rev_mobile").val()+
                            "&add_province="+$("#rev_p").val()+"&add_city="+$("#rev_c").val()+"&add_region="+$("#rev_a").val()+
                            "&add_detail_address="+$("#rev_address").val();
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/admin/express/order/collect/updateExpOrdClt',
                        dataType: 'json',
                        data: data,
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success: function (data) {
                            if (data.mark == "0") {
                                alert("成功");

                                var href = "${ctx}/admin/express/order/collect" + "?currentPage=1&exp_ord_state=${retInfo.obj.exp_ord_state}&exp_ord_id=${retInfo.obj.exp_ord_id}&memb_type=0&father_id=${funcions.obj.father_id}"  ;
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