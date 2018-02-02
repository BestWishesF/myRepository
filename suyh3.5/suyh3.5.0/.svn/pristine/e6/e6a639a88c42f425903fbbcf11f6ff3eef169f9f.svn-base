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

    <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
</head>

<body class="withvernav">
<div class="bodywrapper">

    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2" style="width: 100%;">
                <div class="details">
                    <div class="search_input">
                        <p>
                            <span>数据导入</span>
                        </p>
                    </div>
                </div>
            </div><!--contenttitle-->
            <div id="table">
                <form enctype="multipart/form-data" id="uploadForm_default_tea">
                    <div class="overviewhead">
                        <p>
                            数据时间：<input type="text" id="datepickfrom" value=""  name="exp_ord_time" />
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span>
                            归属代理人手机号：<input type="text" id="agent_phone" name="agent_phone" />
                        </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span>
                            归属用户手机号：<input type="text" id="member_phone" name="member_phone" />
                        </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </p>
                        <p>
                            导入数据：<input type="file" class="input_update" id="file" name="file" />
                        </p>
                    </div>
                </form>
            </div>

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
                    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                    var r = window.location.search.substr(1).match(reg);
                    if (r != null) return unescape(r[2]);
                    return null;
                }
            </script>

            <script type="text/javascript">
                var i=0;
                $("#file").change(function(){
                    if(i==1){
                        alert("订单导入中，请稍后....")
                        return false;
                    }
                    if($("#datepickfrom").val()==null||$("#datepickfrom").val().trim()==""){
                        alert("请先选择数据时间.")
                        return false;
                    }

                    /*jQuery("#uploadForm_default_tea").ajaxSubmit();
                    return false;*/
                    i=1;
                    var option={
                        url:"${ctx}/admin/express/order/upload/excel/file",
                        type:"post",
                        dataType:"json",
                        success:function(data){
                            i=0;
                            $("#file").val("");
                            $("#datepickfrom").val("");
                            if(data.mark=="0"){
                                alert(data.tip);
                            } else{
                                alert(data.tip);
                            }
                        }
                    }

                    jQuery("#uploadForm_default_tea").ajaxSubmit(option);
                    return false;

                });

                $(".userinfo").click(function () {
                    $(".userinfodrop").toggle();
                })
            </script>

            <br/><br/>
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->

</body>
</html>
