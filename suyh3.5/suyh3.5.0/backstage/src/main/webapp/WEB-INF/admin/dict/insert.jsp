<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                添加数据
            </h1>
        </div><!--pageheader-->
        <div class="contentwrapper">
            <div class="details">
                <div    class="search_input">
                    <p>
                        名称:<input type="text" name="code_name" id="code_name"  /></p>
                    <p>
                        排序:<input type="text" name="dict_sort" id="dict_sort" /></p>
                    <p>
                        状态:<input type="radio" name="status" value="0" checked="checked" />可用
                        <input type="radio" name="status" value="1" />不可用</p>
                   <c:if test="${code_type == 'express_company'}">
                    <p>
                        <form enctype="multipart/form-data"  id="uploadForm_default_tea">
                        图片:  <input type="file" class="input_update" id="tea_cate_img" name="myFile" />
                        </form>

                        <img id="tea_cate_img_photo"  src="" height="100px" style="display: none"/>  <br/>

                    </p>
                    <p>
                        编码:<input type="text" name="dict_code" id="dict_code" value=""/></p>
                   </c:if>
                    <c:if test="${code_type == 'province' || code_type == 'city' || code_type == 'area'}">
                        <p>
                            首字母:<input type="text" name="dict_initials" id="dict_initials"/>
                        </p>
                        <p>
                            是否热门:<input type="radio" name="dict_is_hos" value="1" />是
                            <input type="radio" name="dict_is_hos" value="0" checked="checked"/>否
                        </p>
                    </c:if>
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
            url:"${ctx}/admin/dict/fileImg",
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
        var dict_sort = $("#dict_sort").val();
        var dict_code = "";
        var dict_img = "";
        var dict_initials = "";
        var dict_is_hos = 0;
        if(${code_type == 'express_company'}){
            dict_code = $("#dict_code").val();
            dict_img = $("#photo_default_value").html();
        }
        if(${code_type == 'province' || code_type == 'city' || code_type == 'area'}){
            dict_initials = $("#dict_initials").val();
            dict_is_hos = $('input:radio[name="dict_is_hos"]:checked').val()
            if(dict_initials == "" || dict_initials == null){
                alert("首字母不能为空.")
                return false;
            }
        }
        if(dict_code == null){
            dict_code = "";
        }
        if(dict_img == null){
            dict_img = "";
        }
        if(dict_sort == null || dict_sort.trim() == ""){
            dict_sort = 0;
        }

        var data = "code_name="+$("#code_name").val()+"&dict_sort="+dict_sort+"&dict_initials="+dict_initials
                +"&dict_code="+dict_code+"&dict_img="+dict_img+"&dict_is_hos="+dict_is_hos
                +"&status="+$('input:radio[name="status"]:checked').val()+"&code_type=${code_type}&parent_id=${parent_id}";
        $.ajax({
            type:'post',
            url:'${ctx}/admin/dict/insert',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    var href;
                    if ('${code_type}' == 'city' || '${code_type}' == 'area') {
                        href = "${ctx}/admin/dict/parent/list?currentPage=1&code_type=${code_type}&parent_id=${parent_id}&father_id=${father_id}";
                    } else {
                        href="${ctx}/admin/dict/type/list?currentPage=1&code_type=${code_type}&father_id=${father_id}";
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
