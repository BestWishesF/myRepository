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
                添加文章
            </h1>

        </div><!--pageheader-->

        <div class="contentwrapper">
            <div class="details">
                <div    class="search_input">
                    <p>
                        标题:<input type="text" name="arti_name" id="arti_name" /></p>
                    <p>
                        排序:<input type="text" name="arti_sort" id="arti_sort" value="0"/></p>
                    <p>
                        状态:<input type="radio" name="is_valid" value="0" checked="checked"/>可用 <input type="radio" name="is_valid" value="1"/>不可用</p>
                    <p>

                        内容:<script id="editor" type="text/plain" style="width:1024px;height:500px;"name="arti_cont"></script>


                    </p>
                    <p>
                        <button class="submit radius2" id="submit_bt">添加</button>
                    </p>

                </div>
            </div><!--details-->
        </div><!--contentwrapper-->
    </div><!-- centercontent -->
</div><!--bodywrapper-->
<script type="text/javascript">
    var ue = UE.getEditor('editor');

    $("#submit_bt").click(function(){


        var data = "arti_name="+$("#arti_name").val()+"&arti_sort="+$("#arti_sort").val()
                +"&is_valid="+$('input:radio[name="is_valid"]:checked').val()+"&menu_id=1&arti_content="+UE.getEditor('editor').getContent();
        $.ajax({
            type:'post',
            url:'${ctx}/admin/article/insert',
            dataType : 'json',
            data: data,
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

        });
    });
</script>
</body>
</html>
