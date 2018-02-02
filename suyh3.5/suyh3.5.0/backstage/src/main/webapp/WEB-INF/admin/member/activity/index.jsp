<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>
                    用户唤醒活动
                </h3>
            </div><!--contenttitle-->
            <div class="overviewhead">
                <button class="btn_b" id="choose_give">赠送优惠券</button>
            </div>
            <script type="text/javascript">
                function getParameter(name) {
                    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
                    var r = window.location.search.substr(1).match(reg);
                    if (r!=null) return unescape(r[2]); return null;
                }

                //init
                $(function(){

                });
            </script>

            <script type="text/javascript">
                $("#choose_give").click(function () {
                    $.ajax({
                        type:'post',
                        url:'${ctx}/admin/member/activity/giveMemberCoupon',
                        dataType : 'json',
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        success:function(data){
                            if(data.mark=="0"){
                                alert("成功.")
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
