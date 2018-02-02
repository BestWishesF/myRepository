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
            <div class="contenttitle2" style="width: 100%;">
                <div class="details">
                    <div class="search_input">
                        <p>
                            <span>打印</span>
                            <c:if test="${retInfo.obj==null||retInfo.obj==''}">
                                <button class="submit radius2" onclick="expressPrint()" style="float: right;">打印设计
                                </button>
                            </c:if>
                        </p>
                    </div>
                </div>
            </div><!--contenttitle-->
            <div id="table" style="width: 100%;height: 570px;">
                <div style="width: 1050px;height: 570px;">
                    <div style="float: left;height: 100%;width: 150px;">
                        <p><label id="send" style="width: 100%;">发件人信息</label></p>
                        <p><ul id="send_info" style="list-style: none;display: block;width: 100%;">
                            <li><label id="send_name" value="测试">姓名</label></li>
                            <li><label id="send_tel" value="18569034337">电话</label></li>
                            <li><label id="send_address" value="地址">地址</label></li>
                        </ul></p>
                        <p><label id="rev" style="width: 100%;">收件人信息</label></p>
                        <p><ul id="rev_info" style="list-style: none;display: none;width: 100%;">
                            <li><label id="rev_name">姓名</label></li>
                            <li><label id="rev_tel">电话</label></li>
                            <li><label id="rev_address">地址</label></li>
                        </ul></p>
                        <p><label id="other" style="width: 100%;">其他信息</label></p>
                        <p><ul id="other_info" style="list-style: none;display: none;width: 100%;">
                            <li><label id="goods_name">内件名</label></li>
                            <li><label id="inspected">已验视</label></li>
                        </ul></p>
                    </div>
                    <div style="float: left;height: 100%;width: 900px;">
                        <object id="ELODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="100%" height="500">
                            <param name="Color" value="#F5F2E9">
                            <embed type="application/x-print-lodop" plugin="http://www.lodop.net/download.html" id="ELODOP_EM" width="823" height="538" color="#F5F2E9"></embed>
                        </object>
                    </div>
                </div>
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
                function expressPrint() {

                    $("#table").show();
                    var LODOP = getLodop(document.getElementById('ELODOP_OB'),document.getElementById('ELODOP_EM'));
                    LODOP.PRINT_INIT("物流快递单套打");
                    LODOP.SET_PRINT_MODE("PROGRAM_CONTENT_BYVAR",true);
                    LODOP.SET_SHOW_MODE("MESSAGE_NOSET_PROPERTY",'设计打印模板');
                    LODOP.SET_SHOW_MODE('SKIN_CUSTOM_COLOR', "#F5F2E9");
                    LODOP.SET_SHOW_MODE("SETUP_IN_BROWSE",1);
                    LODOP.SET_SHOW_MODE('HIDE_DISBUTTIN_SETUP', "1");
                    LODOP.SET_SHOW_MODE('SHOW_SCALEBAR', "1");
                    LODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);
                    LODOP.PRINT_SETUP();
                }

                $("#send").click(function () {
                    $("#send_info").toggle();
                })
                $("#rev").click(function () {
                    $("#rev_info").toggle();
                })
                $("#other").click(function () {
                    $("#other_info").toggle();
                })

                $("#send_name").click(function () {
                    var random = Math.floor(Math.random()*100);
                    var LODOP = getLodop(document.getElementById('ELODOP_OB'),document.getElementById('ELODOP_EM'));
                    if(LODOP.GET_VALUE("ProgramCodes",0).indexOf("sendernames")>=0){
                    }else{
                        LODOP.ADD_PRINT_TEXTA("sendernames",9+random,262-random,175,30,'发件人-姓名');
                        LODOP.SET_PRINT_STYLEA(0,"ContentVName",'data.sendernames');
                        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
                        LODOP.SET_PRINT_STYLEA(0,"FontColor","#36C1C9");
                        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                    }
                })
            </script>

            <br/><br/>
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->

</body>
</html>
