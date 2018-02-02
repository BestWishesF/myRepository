<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.js"> </script>
</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent">
        <div class="pageheader notab">
            <h1 class="pagetitle">
                更新文章
            </h1>

        </div><!--pageheader-->
        <form id="myform">
        <div class="contentwrapper">
            <div class="details">
                <div    class="search_input">
                    <p>
                        标题:<input type="text" name="arti_name" id="arti_name" value="${article.arti_name}"/></p>
                    <p>
                        排序:<input type="text" name="arti_sort" id="arti_sort" value="${article.arti_sort}"/></p>
                    <p>
                        状态:<input type="radio" name="is_valid" value="0"<c:if test="${article.is_valid == 0}"> checked="checked"</c:if>/>可用
                        <input type="radio" name="is_valid" value="1" <c:if test="${article.is_valid == 1}"> checked="checked"</c:if>/>不可用</p>
                    <p>

                        内容:<script id="editor" type="text/plain" style="width:1024px;height:500px;"name="arti_content">${article.arti_content}</script>


                    </p>
                    <p>
                        <input type="hidden" name="menu_id" value="0"/>
                        <input type="hidden" name="arti_id" value="${article.arti_id}"/>
                        <button class="submit radius2" id="submit_bt">更新</button>
                    </p>

                </div>
            </div><!--details-->
        </div><!--contentwrapper--></form>
    </div><!-- centercontent -->

</div><!--bodywrapper-->
<script type="text/javascript">
    var ue = UE.getEditor('editor');

    $("#submit_bt").click(function(){


        var data = "arti_name="+$("#arti_name").val()+"&arti_sort="+$("#arti_sort").val()
                +"&is_valid="+$('input:radio[name="is_valid"]:checked').val()+"&menu_id=1&arti_id=${article.arti_id}&arti_content="+UE.getEditor('editor').getContent();
        var option={
            type:'post',
            url:'${ctx}/admin/article/update',
            dataType : 'json',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    var href;
                    var father_id;
                    if ($('input:radio[name="is_valid"]:checked').val()==0) {
                        href="${ctx}/admin/article/list?currentPage=1&is_valid=0&father_id=81";
                    } else {
                        href="${ctx}/admin/article/list?currentPage=1&is_valid=1&father_id=82";
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
        }
        jQuery("#myform").ajaxSubmit(option);
        return false;
    });
</script>
</body>
</html>
