<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                添加积分商品
            </h1>
        </div><!--pageheader-->
        <div class="contentwrapper">
            <div class="details">
                <div class="search_input">
                    <p>
                        积分商品名称:<input type="text" name="goods_name" id="goods_name" />
                    </p>
                    <p>
                        兑换所需积分:<input type="text" name="goods_score" id="goods_score" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                      onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" />
                    </p>
                    <p>
                        优惠券的金额:<input type="text" name="coupon_money" id="coupon_money" onkeyup="value=value.replace(/[^\d.]/g,'')" />
                    </p>
                    <p>
                        券的有效天数:<input type="text" name="effective_day" id="effective_day" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                      onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" />
                    </p>
                    <p>
                        券的兑换限制:<input type="radio" name="goods_type" value="2" checked="checked" />无兑换限制
                        <input type="radio" name="goods_type" value="1" />每天兑换一次
                    </p>
                    <p>
                        商品的副标题:<input type="text" name="goods_subhead" id="goods_subhead" />
                    </p>
                    <p>
                        商品详细介绍:<input type="text" name="goods_introduce" id="goods_introduce" />
                    </p>
                    <p>
                        商品购买价格:<input type="text" name="goods_amount" id="goods_amount" onkeyup="value=value.replace(/[^\d.]/g,'')" />
                    </p>
                    <p>
                        使用区域限制:<input type="radio" name="region_id" value="0" checked="checked" onclick="$('#region').hide();" />无使用区域限制
                        <input type="radio" name="region_id" value="1" onclick="$('#region').show();" />有使用区域限制
                    </p>
                    <p id="region" style="display: none;">
                        <select id="province" style="min-width: 10%;width: 20%;">
                            <c:forEach items="${retInfo.obj.province}" var="province">
                                <option value="${province.dict_id}">${province.province_name}</option>
                            </c:forEach>
                        </select>
                        <select id="city" style="min-width: 10%;width: 20%;">
                            <c:forEach items="${retInfo.obj.city}" var="city">
                                <option value="${city.dict_id}">${city.city_name}</option>
                            </c:forEach>
                        </select>
                        <select id="region_id" style="min-width: 10%;width: 20%;">
                            <c:forEach items="${retInfo.obj.area}" var="area">
                                <option value="${area.dict_id}">${area.area_name}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        券的兑换限制:<input type="radio" name="express_source" value="0" checked="checked" />无使用限制
                        <input type="radio" name="express_source" value="1" />仅限公众号使用
                        <input type="radio" name="express_source" value="2" />仅限APP使用
                    </p>
                    <p>
                        快递公司限制:
                        <select id="express_id">
                            <option value="0">无快递公司限制</option>
                            <c:forEach items="${retInfo.obj.express_company}" var="company">
                                <option value="${company.dict_id}">${company.code_name}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        券的限定金额:<input type="text" name="cou_limit_amount" id="cou_limit_amount" value="0" onkeyup="value=value.replace(/[^\d.]/g,'')" />
                    </p>
                    <p>
                        优惠券的折扣:<input type="text" name="cou_discount" id="cou_discount" value="1" onkeyup="value=value.replace(/[^\d.]/g,'')" />
                    </p>
                    <p>
                    <form enctype="multipart/form-data"  id="upload_goods_img">
                        积分商品图标:  <input type="file" class="input_update" id="goods_img" name="myFile" />
                    </form>

                    <img id="goods_img_photo"  src="http://file.hotol.cn/app/score/goodsIcon/20170331092137545.jpg" height="30px" style="display: none"/>
                    </p>
                    <p>
                    <form enctype="multipart/form-data"  id="upload_goods_img_detail">
                        积分商品图片:  <input type="file" class="input_update" id="goods_img_detail" name="myFile" />
                    </form>

                    <img id="goods_img_detail_photo"  src="http://file.hotol.cn/app/banner/icon_edit.png" height="100px" style="display: none"/>
                    </p>
                    <p>
                        <button class="submit radius2" id="submit_bt">添加</button>
                    </p>
                </div>
            </div><!--details-->
        </div><!--contentwrapper-->
    </div><!-- centercontent -->
    <b style="display: none" id="goods_img_default_value">http://file.hotol.cn/app/score/goodsIcon/20170331092137545.jpg</b>
    <b style="display: none" id="goods_img_detail_default_value"></b>
</div><!--bodywrapper-->
<script type="text/javascript">

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
                        $("#region_id").html(data);
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
                $("#region_id").html(data);
            },
            error:function(){
                alert("error");
            }
        });
    });

    $("#goods_img").change(function(){
        var option={
            url:"${ctx}/admin/score/goods/fileGoodsImg",
            type:"post",
            dataType:"json",
            success:function(data){
                if(data.mark=="0"){
                    $("#goods_img_photo").attr("src", data.tip);
                    $("#goods_img_photo").css("display","block");
                    $("#goods_img_default_value").html(data.tip);
                }
                else{
                    alert(data.tip);
                }
            }
        }

        jQuery("#upload_goods_img").ajaxSubmit(option);
        return false;
    });

    $("#goods_img_detail").change(function(){
        var option={
            url:"${ctx}/admin/score/goods/fileGoodsDetailImg",
            type:"post",
            dataType:"json",
            success:function(data){
                if(data.mark=="0"){
                    $("#goods_img_detail_photo").attr("src", data.tip);
                    $("#goods_img_detail_photo").css("display","block");
                    $("#goods_img_detail_default_value").html(data.tip);
                }
                else{
                    alert(data.tip);
                }
            }
        }

        jQuery("#upload_goods_img_detail").ajaxSubmit(option);
        return false;
    });

    $("#submit_bt").click(function () {
        if($("#goods_score").val()==null||$("#goods_score").val().trim()==""){
            alert("所需积分不能为空.");
            return false;
        }
        if($("#coupon_money").val()==null||$("#coupon_money").val().trim()==""){
            alert("优惠券金额不能为空.");
            return false;
        }
        if($("#effective_day").val()==null||$("#effective_day").val().trim()==""){
            alert("有效天数不能为空.");
            return false;
        }
        if($("#goods_amount").val()==null||$("#goods_amount").val().trim()==""){
            alert("价格不能为空.");
            return false;
        }
        if($("#cou_limit_amount").val()==null||$("#cou_limit_amount").val().trim()==""){
            alert("限定金额不能为空.");
            return false;
        }
        if($("#cou_discount").val()==null||$("#cou_discount").val().trim()==""){
            alert("折扣不能为空.");
            return false;
        }

        var region_id=$('input:radio[name="region_id"]:checked').val();
        if(region_id==1){
            region_id=$("#region_id").val();
        }
        var data="goods_img="+$("#goods_img_default_value").html()+"&goods_name="+$("#goods_name").val()+
            "&goods_score="+$("#goods_score").val()+"&coupon_money="+$("#coupon_money").val()+
            "&effective_day="+$("#effective_day").val()+"&goods_type="+$('input:radio[name="goods_type"]:checked').val()+
            "&goods_img_detail="+$("#goods_img_detail_default_value").html()+"&goods_subhead="+$("#goods_subhead").val()+
            "&goods_introduce="+$("#goods_introduce").val()+"&goods_amount="+$("#goods_amount").val()+
            "&region_id="+region_id+"&express_source="+$('input:radio[name="express_source"]:checked').val()+
            "&express_id="+$("#express_id").val()+"&cou_limit_amount="+$("#cou_limit_amount").val()+
            "&cou_discount="+$("#cou_discount").val();

        $.ajax({
            type:'post',
            url:'${ctx}/admin/score/goods/insert',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    $.ajax({
                        type:'GET',
                        url:"${ctx}/admin/score/goods/list?currentPage=1&goods_state=0&father_id=92",
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
</body>
</html>
