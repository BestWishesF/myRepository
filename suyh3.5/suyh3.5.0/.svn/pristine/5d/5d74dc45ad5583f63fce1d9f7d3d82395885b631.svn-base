<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                添加banner
            </h1>

        </div><!--pageheader-->

        <div class="contentwrapper">
            <div class="details">
                <div    class="search_input">
                    <p>
                        名称:<input type="text" name="img_name" id="img_name" /></p>
                    <p>
                        链接:<input type="text" name="img_link" id="img_link"/></p>
                    <p>
                        排序:<input type="text" name="img_sort" id="img_sort" value="0"/></p>
                    <p>
                        状态:<input type="radio" name="is_valid" value="0" checked="checked"/>可用 <input type="radio" name="is_valid" value="1"/>不可用</p>
                    <p>

                        <form enctype="multipart/form-data"  id="uploadForm_default_tea">
                        图片:  <input type="file" class="input_update" id="tea_cate_img" name="myFile" />
                        </form>

                        <img id="tea_cate_img_photo"  src="http://file.hotol.cn/app/banner/icon_edit.png" height="100px" style="display: none"/>  <br/>

                    </p>
                    <p>
                        <button class="submit radius2" id="submit_bt">添加</button>
                    </p>

                </div>
            </div><!--details-->
        </div><!--contentwrapper-->
    </div><!-- centercontent -->

    <b style="display: none" id="photo_default_value"></b>
</div><!--bodywrapper-->
<script type="text/javascript">
    $("#tea_cate_img").change(function(){
        var option={
            url:"${ctx}/admin/banner/fileImg",
            type:"post",
            dataType:"json",
            success:function(data){
                if(data.mark=="0"){
                    $("#tea_cate_img_photo").attr("src", data.tip);
                    $("#tea_cate_img_photo").css("display","block");
                    $("#photo_default_value").html(data.tip);
                }
                else{
                    alert(data.tip);
                }
            }
        }

        jQuery("#uploadForm_default_tea").ajaxSubmit(option);
        return false;
    });
    $("#submit_bt").click(function(){


        var data = "img_name="+$("#img_name").val()+"&img_link="+$("#img_link").val()
                +"&img_sort="+$("#img_sort").val()+"&img_src="+$("#photo_default_value").html()
                +"&is_valid="+$('input:radio[name="is_valid"]:checked').val()+"&img_type=1&img_alt="+$("#img_name").val();
        $.ajax({
            type:'post',
            url:'${ctx}/admin/banner/insert',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    var href;
                    var father_id;
                    if ($('input:radio[name="is_valid"]:checked').val()==0) {
                        href="${ctx}/admin/banner/list?currentPage=1&is_valid=0&father_id=74";
                    } else {
                        href="${ctx}/admin/banner/list?currentPage=1&is_valid=1&father_id=74";
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
    });
</script>
</body>
</html>
