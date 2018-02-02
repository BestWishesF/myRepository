<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="${ctx}/css/code128.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${ctx}/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.qrcode.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-barcode.js"></script>
    <script type="text/javascript" src="${ctx}/js/utf.js"></script>
    <script type="text/javascript" src="${ctx}/js/code128.js"></script>
</head>

<body class="withvernav">
<div class="bodywrapper">


    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2" style="width: 100%;">
                <div class="details">
                    <div class="search_input">
                        <p>
                            <span>面单打印</span>
                            <c:if test="${retInfo.obj==null||retInfo.obj==''}">
                                <button class="submit radius2" onclick="findPrintInfo()" style="float: right;">查询
                                </button>
                            </c:if>
                            <c:if test="${retInfo.obj!=null&&retInfo.obj!=''}">
                                <button class="submit radius2" onclick="expressPrint()" style="float: right;">打印
                                </button>

                                <%--<button class="submit radius2" onclick="expressPrint()" style="float: right;">入库
                                </button>--%>
                            </c:if>

                            <span style="float: right;">
                                <%--<label>重量</label>
                                <input class="text" type="text" style="width: 20%;" />--%>
                                <label>厚通订单号</label>
                                <input id="ht_number" class="text" type="text" style="width: 40%;"/>
                            </span>
                        </p>
                    </div>
                </div>
            </div><!--contenttitle-->
            <div id="table">
                <c:if test="${retInfo.obj.express_id==27}">
                    <table cellpadding="0" cellspacing="0"
                           style="width: 9.6cm;border: solid 2px dodgerblue;margin-left: 0.1cm;">
                        <tr style="height: 0.8cm;">
                            <td colspan="2" style="width: 3cm;">&nbsp;</td>
                            <td colspan="2" style="width: 4.4cm;">&nbsp;</td>
                            <td colspan="2"
                                style="text-align: right;width: 2.2cm;font-size: 15px;font-family: 微软雅黑;">${retInfo.obj.express_name}</td>
                        </tr>
                        <tr style="height: 1.5cm;">
                            <td colspan="6"
                                style="width: 9.6cm;border-top: solid 1px black;text-align: left;font-family: 微软雅黑;font-size: 35px;font-weight: bold;">${retInfo.obj.marker}</td>
                        </tr>
                        <tr style="height: 1cm;">
                            <td colspan="4"
                                style="width: 7.4cm;border-top: solid 1px black;border-right: solid 1px black;text-align: left;font-family: 微软雅黑;font-size: 19px;">${retInfo.obj.package_name}</td>
                            <td colspan="2"
                                style="width: 2.2cm;border-top: solid 1px black;text-align: left;font-family: 微软雅黑;font-size: 19px;font-weight: bold;">${retInfo.obj.package_code}</td>
                        </tr>
                        <tr style="height: 1.8cm;">
                            <td style="width: 0.5cm;border-top: solid 1px black;border-right: solid 1px black;font-size: 10px;font-family: 微软雅黑;height: 1.8cm;text-align: center;">
                                <p style="margin: 0;width: 0.5cm;">收件</p>
                            </td>
                            <td colspan="3"
                                style="width: 6.9cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 10px;font-weight: bold;text-align: left;">
                                <div style="width: 6.9cm;height: 1.8cm;">
                                    <p style="margin: 0;line-height: 0.6cm;height: 0.6cm;">${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</p>
                                    <p style="margin: 0;height: 1.2cm;line-height: 0.6cm;">${retInfo.obj.rev_pro}${retInfo.obj.rev_city}${retInfo.obj.rev_area}${retInfo.obj.rev_address}</p>
                                </div>
                            </td>
                            <td rowspan="2" colspan="2"
                                style="width: 2.2cm;height: 3cm;border-top: solid 1px black;position: relative;">
                                <div style="height: 0.5cm;border-bottom: 1px solid;text-align: center;font-family: 微软雅黑;font-size: 8px;position: absolute;top: 0px;width: 100%;">
                                    服务
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 1.2cm;">
                            <td style="width: 0.5cm;border-top: solid 1px black;border-right: solid 1px black;font-size: 10px;font-family: 微软雅黑;height: 1.2cm;text-align: center;">
                                <p style="margin: 0;width: 0.5cm;">寄件</p>
                            </td>
                            <td colspan="3"
                                style="width: 6.9cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 8px;text-align: left;">
                                <div style="width: 6.9cm;height: 1.2cm;">
                                    <p style="margin: 0;line-height: 0.4cm;height: 0.4cm;">${retInfo.obj.send_name}&nbsp;&nbsp;${retInfo.obj.send_mobile}</p>
                                    <p style="margin: 0;height: 0.8cm;line-height: 0.4cm;">${retInfo.obj.send_pro}${retInfo.obj.send_city}${retInfo.obj.send_area}${retInfo.obj.send_address}</p>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 2.5cm;">
                            <td colspan="6" style="width: 9.6cm;border-top: solid 1px black;">
                                    <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}&fmt=png"
                                         style="height: 2cm;margin-top: 0.2cm;width: 7cm;"/>--%>
                                <div id="express_no1" style="margin-top: 0.1cm;"></div>
                                <script>
                                    $("#express_no1").empty().barcode("" +${retInfo.obj.express_number}, "code128", {
                                        barWidth: 2,
                                        barHeight: 60,
                                        showHRI: true
                                    });
                                </script>
                            </td>
                        </tr>
                        <tr style="height: 1.9cm;">
                            <td colspan="3"
                                style="width: 4.4cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 8px;text-align: left;">
                                <div style="margin: 0.1cm;">
                                    <p style="width: 4.2cm;margin: 0;">
                                        快件送达收件人地址，经收件人或收件人（寄件人）允许的代收人签字，视为送达。您的签字代表您已检视此包裹，并已确认商品信息无误、包装完好、没有划痕、破损等表面质量问题。
                                    </p>
                                </div>
                            </td>
                            <td colspan="2"
                                style="width: 3.5cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 12px;text-align: left;">
                                <div style="height: 1.9cm;">
                                    <p style="height: 1cm;line-height: 1cm;margin: 0;">签收人：</p>
                                    <p style="height: 0.9cm;line-height: 0.9cm;margin: 0;">时间：</p>
                                </div>
                            </td>
                            <td style="width: 1.7cm;border-top: solid 1px black;">
                                <div style="width: 1.7cm;height: 1.9cm;"></div>
                            </td>
                        </tr>
                        <tr style="height: 8px;line-height: 8px;background-color: dodgerblue;">
                            <td style="width: 0.5cm;border-top: solid 1px black;height: 8px;">&nbsp;</td>
                            <td style="width: 2.5cm;border-top: solid 1px black;height: 8px;">&nbsp;</td>
                            <td style="width: 1.4cm;border-top: solid 1px black;height: 8px;">&nbsp;</td>
                            <td style="width: 3cm;border-top: solid 1px black;height: 8px;">&nbsp;</td>
                            <td style="width: 0.5cm;border-top: solid 1px black;height: 8px;">&nbsp;</td>
                            <td style="width: 1.7cm;border-top: solid 1px black;height: 8px;">&nbsp;</td>
                        </tr>
                        <tr style="height: 1.4cm;">
                            <td colspan="2" style="width: 3cm;border-top: solid 1px black;">&nbsp;</td>
                            <td colspan="4" style="width: 6.6cm;border-top: solid 1px black;">
                                    <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}&fmt=png"
                                         style="height: 1.2cm;margin-top: 0.1cm;width: 4cm;"/>--%>
                                <div id="express_no2" style="margin-top: 0.1cm;float: right;"></div>
                                <script>
                                    $("#express_no2").empty().barcode("" +${retInfo.obj.express_number}, "code128", {
                                        barWidth: 1,
                                        barHeight: 30,
                                        showHRI: true
                                    });
                                </script>
                            </td>
                        </tr>
                        <tr style="height: 1.2cm;">
                            <td style="width: 0.5cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 10px;text-align: center;">
                                <p style="margin: 0;width: 0.5cm;">收件</p>
                            </td>
                            <td colspan="4"
                                style="width: 7.4cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 8px;text-align: left;">
                                <div style="width: 7.4cm;height: 1.2cm;">
                                    <p style="margin: 0;height: 0.4cm;line-height: 0.4cm;">${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</p>
                                    <p style="margin: 0;height: 0.8cm;line-height: 0.4cm;">${retInfo.obj.rev_pro}${retInfo.obj.rev_city}${retInfo.obj.rev_area}${retInfo.obj.rev_address}</p>
                                </div>
                            </td>
                            <td rowspan="2" style="width: 1.7cm;height: 2.4cm;border-top: solid 1px black;">
                                <div style="width: 1.7cm;height: 2.4cm;"></div>
                            </td>
                        </tr>
                        <tr style="height: 1.2cm;">
                            <td style="width: 0.5cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 10px;text-align: center;">
                                <p style="margin: 0;width: 0.5cm;">寄件</p>
                            </td>
                            <td colspan="4"
                                style="width: 7.2cm;border-top: solid 1px black;border-right: solid 1px black;font-family: 微软雅黑;font-size: 8px;text-align: left;">
                                <div style="width: 7.2cm;height: 1.2cm;">
                                    <p style="margin: 0;height: 0.4cm;line-height: 0.4cm;">${retInfo.obj.send_name}&nbsp;&nbsp;${retInfo.obj.send_mobile}</p>
                                    <p style="margin: 0;height: 0.8cm;line-height: 0.4cm;">${retInfo.obj.send_pro}${retInfo.obj.send_city}${retInfo.obj.send_area}${retInfo.obj.send_address}</p>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 2.3cm;">
                            <td colspan="6"
                                style="width: 9.6cm;height: 2.3cm;border-top: solid 1px black;font-family: 微软雅黑;font-size: 10px;text-align: left;">
                                <div style="width: 9.6cm;height: 1.8cm;position: relative;">
                                    <p style="margin: 3px;"><span>${retInfo.obj.category}</span></p>
                                    <p style="position: absolute;bottom: 0px;right: 0px;margin: 0px;"
                                       id="hotol_id">${retInfo.obj.ht_number}</p>
                                </div>
                                <div style="height: 1cm;width: 9.6cm;text-align: right;font-weight: bold;font-size: 11pt;">已验视</div>
                            </td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${retInfo.obj.express_id==28}">
                    <style>
                        html {
                            -webkit-text-size-adjust: none;
                        }

                        p {
                            padding: 0px;
                            margin: 0px;
                        }

                        .td1 {
                            border: solid #000000;
                            border-width: 0px 1px 1px 0px;
                        }

                        .td2 {
                            border: solid #000000;
                            border-width: 1px 1px 1px 1px;
                        }

                        .td3 {
                            border: solid #000000;
                            border-width: 0px 1px 1px 1px;
                        }
                    </style>
                    <table cellpadding="0" cellspacing="0" border="0" style="width: 9.6cm;margin-left: 0.1cm;">
                        <tr style="height: 0.9cm;">
                            <td colspan="5" class="td2"
                                style="font-family: 微软雅黑;font-size: 12px;text-align: right;width: 9.6cm;">
                                &nbsp;
                            </td>
                        </tr>
                        <tr style="height: 1.5cm;">
                            <td colspan="5" class="td3"
                                style="font-family: 微软雅黑;font-weight: bold;font-size: 20px;text-align: left;width: 9.6cm;">
                                    ${retInfo.obj.rev_city}
                            </td>
                        </tr>
                        <tr style="height: 1cm;">
                            <td colspan="3" class="td3"
                                style="width: 6.6cm;font-family: 微软雅黑;font-size: 16px;text-align: left;">&nbsp;</td>
                            <td colspan="2" class="td1" style="width: 3cm;">&nbsp;</td>
                        </tr>
                        <tr style="height: 2cm;">
                            <td class="td3" style="width: 0.5cm;font-family: 微软雅黑;font-size: 10px;text-align: center;">
                                <p style="width: 0.5cm;">收件</p>
                            </td>
                            <td colspan="2" class="td1"
                                style="width: 6.1cm;font-family: 微软雅黑;font-weight: bold;font-size: 12px;text-align: left;">
                                <div style="height: 2cm;line-height: 1cm;">
                                    <p style="height: 0.8cm;line-height: 0.8cm;">${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</p>
                                    <p style="height: 1.2cm;line-height: 0.6cm;">${retInfo.obj.rev_pro}${retInfo.obj.rev_city}${retInfo.obj.rev_area}${retInfo.obj.rev_address}</p>
                                </div>
                            </td>
                            <td colspan="2" class="td1" rowspan="2" style="width: 3cm;font-size: 10px;">
                                <p>件数：1/1</p>
                                <p>计费重量：${retInfo.obj.weight}Kg</p>
                                <p>付款方式：寄付</p>
                                <p>是否保价：</p>
                                <p>签单返还：</p>
                                <p>代收金额：</p>
                                <p>费用合计：</p>
                            </td>
                        </tr>
                        <tr style="height: 1cm;">
                            <td class="td3" style="width: 0.5cm;font-family: 微软雅黑;font-size: 10px;text-align: center;">
                                <p style="width: 0.5cm;">寄件</p>
                            </td>
                            <td colspan="2" class="td1"
                                style="width: 6.1cm;font-family: 微软雅黑;font-size: 10px;text-align: left;">
                                <div style="height: 1cm;">
                                    <p style="height: 0.35cm;">${retInfo.obj.send_name}&nbsp;&nbsp;${retInfo.obj.send_mobile}</p>
                                    <p style="height: 0.65cm;line-height: 0.32cm;">${retInfo.obj.send_pro}${retInfo.obj.send_city}${retInfo.obj.send_area}${retInfo.obj.send_address}</p>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 2.2cm;">
                            <td class="td3" colspan="5" style="text-align: center;width: 9.6cm;">
                                    <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}&fmt=png"
                                         style="height: 2cm;margin-top: 0.2cm;width: 8cm;"/>--%>
                                <div id="express_no1" style="margin-top: 0.1cm;"></div>
                                <script>
                                    $("#express_no1").empty().barcode("" +${retInfo.obj.express_number}, "code128", {
                                        barWidth: 2,
                                        barHeight: 50,
                                        showHRI: true
                                    });
                                </script>
                            </td>
                        </tr>
                        <tr style="height: 1.9cm;">
                            <td colspan="2" class="td3"
                                style="width: 3.8cm;font-family: 微软雅黑;font-size: 10px;text-align: left;">
                                <p style="width: 3.8cm;">
                                    快件到达收件人地址，收件人或寄件人允许签收，视为描述：您的签字代表您已验收此包裹，并已确认包商品完好无损、没有划痕、没有破损等质量问题。</p>
                            </td>
                            <td colspan="2" class="td1"
                                style="width: 3.8cm;font-family: 微软雅黑;font-size: 12px;text-align: left;">
                                <p style="height: 1.2cm;width: 3.8cm;">签收人：</p>
                                <p style="height: 0.7cm;width: 3.8cm;">时间：</p>
                            </td>
                            <td class="td1" style="width: 2cm;">

                            </td>
                        </tr>
                        <tr style="height: 1px;background-color: black;line-height: 1px;">
                            <td style="width: 0.5cm;height: 1px;">&nbsp;</td>
                            <td style="width: 3.3cm;height: 1px;">&nbsp;</td>
                            <td style="width: 2.8cm;height: 1px;">&nbsp;</td>
                            <td style="width: 1cm;height: 1px;">&nbsp;</td>
                            <td style="width: 2cm;height: 1px;">&nbsp;</td>
                        </tr>
                        <tr style="height: 1.4cm;">
                            <td class="td3" colspan="5">
                                    <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}&fmt=png"
                                         style="height: 1.2cm;margin-top: 0.1cm;width: 5.5cm;"/>--%>
                                <div id="express_no2" style="margin-top: 0.1cm;float: right;"></div>
                                <script>
                                    $("#express_no2").empty().barcode("" +${retInfo.obj.express_number}, "code128", {
                                        barWidth: 1,
                                        barHeight: 30,
                                        showHRI: true
                                    });
                                </script>
                            </td>
                        </tr>
                        <tr style="height: 1.4cm;">
                            <td class="td3" style="width: 0.5cm;font-family: 微软雅黑;font-size: 10px;text-align: center;">
                                <p style="width: 0.5cm;">收件</p>
                            </td>
                            <td colspan="2" class="td1"
                                style="width: 6.1cm;font-family: 微软雅黑;font-weight: bold;font-size: 12px;text-align: left;">
                                <div style="height: 1.4cm;">
                                    <p style="height: 0.4cm;">${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</p>
                                    <p style="height: 1cm;line-height: 0.5cm;">${retInfo.obj.rev_pro}${retInfo.obj.rev_city}${retInfo.obj.rev_area}${retInfo.obj.rev_address}</p>
                                </div>
                            </td>
                            <td colspan="2" class="td1" rowspan="2" style="width: 3cm;">
                                <div style="position: relative;margin-left: 0.4cm;width: 2.1cm;">
                                    <img src="${ctx}/images/dbwlewm.png" style="width: 2.1cm;height: 2.1cm;">
                                    <div style="width: 2.1cm;position: absolute;top: 2.1cm;font-size: 10px;text-align: center;left: 0px;">
                                        扫码有惊喜
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 1cm;">
                            <td class="td3" style="width: 0.5cm;font-family: 微软雅黑;font-size: 10px;text-align: center;">
                                <p style="width: 0.5cm;">寄件</p>
                            </td>
                            <td colspan="2" class="td1"
                                style="width: 6.1cm;font-family: 微软雅黑;font-size: 10px;text-align: left;">
                                <div style="height: 1cm;">
                                    <p style="height: 0.3cm;">${retInfo.obj.send_name}&nbsp;&nbsp;${retInfo.obj.send_mobile}</p>
                                    <p style="height: 0.7cm;line-height: 0.35cm;">${retInfo.obj.send_pro}${retInfo.obj.send_city}${retInfo.obj.send_area}${retInfo.obj.send_address}</p>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 1.7cm;">
                            <td class="td3" colspan="5"
                                style="font-family: 微软雅黑;font-weight: bold;font-size: 12px;text-align: left;border-bottom: 0px;">
                                <span>${retInfo.obj.category}</span>
                                <div style="height: 1.7cm;position: relative;width: 9.6cm;">
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 0.6cm;">
                            <td class="td3" colspan="4"
                                style="width: 6.6cm;font-family: 微软雅黑;font-weight: bold;font-size: 10px;text-align: left;border-width: 0px 0px 1px 1px;">
                                <p>${retInfo.obj.ht_number}</p>
                            </td>
                            <td class="td1" colspan="1"
                                style="width: 3cm;font-family: 微软雅黑;font-weight: bold;font-size: 12px;text-align: right;">
                                <p>已验视</p>
                            </td>
                        </tr>
                    </table>
                </c:if>

                <c:if test="${retInfo.obj.express_id==29}">
                    <style>
                        html {
                            -webkit-text-size-adjust: none;
                        }

                        p {
                            padding: 0px;
                            margin: 0px;
                        }

                        .td1 {
                            border: solid #000000;
                            border-width: 0px 1px 1px 0px;
                        }

                        .td2 {
                            border: solid #000000;
                            border-width: 1px 1px 1px 1px;
                        }

                        .td3 {
                            border: solid #000000;
                            border-width: 0px 1px 1px 1px;
                        }
                    </style>
                    <table cellpadding="0" cellspacing="0" border="0" style="width: 9.8cm;margin-left: 0.1cm;">
                        <tr style="height: 2cm;">
                            <td colspan="5" class="td2" style="position: relative;text-align: center;">
                                    <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}-1-1-&fmt=png"
                                         style="height: 1.5cm;margin-top: 0.1cm;width: 9cm;"/>--%>
                                <div id="express_no1" class="barcode" style="margin-top: 0.3cm;margin-left: 1.5cm;"></div>
                                <script>
                                    $("#express_no1").html(code128("${retInfo.obj.express_number}-1-1-","A",1));
                                </script>
                                <span style="position: absolute;top: 1.5cm;right: 2cm;font-size: 10px;">${retInfo.obj.weight}KG</span>
                            </td>
                        </tr>
                        <tr style="height: 1.3cm;">
                            <td colspan="2" class="td3" style="width: 4.9cm;">
                                <p style="font-family: 黑体;font-size: 10px;">${retInfo.obj.sourcet_sort_center_name}</p>
                                <div style="font-family: 黑体;font-size: 20px;width: 100%;text-align: center;">${retInfo.obj.original_cross_code}</div>
                            </td>
                            <td colspan="3" class="td1" style="width: 4.9cm;">
                                <p style="font-family: 黑体;font-size: 10px;">${retInfo.obj.target_sort_center_name}</p>
                                <div style="font-family: 黑体;font-size: 20px;width: 100%;text-align: center;">${retInfo.obj.destination_cross_code}</div>
                            </td>
                        </tr>
                        <tr style="height: 0.7cm;">
                            <td class="td3" style="width: 0.7cm;">&nbsp;</td>
                            <td class="td1"
                                style="font-family: 黑体;font-size: 16px;text-align: center;width: 4.2cm;">${retInfo.obj.site_name}</td>
                            <td class="td1" style="width: 1cm;font-family: 黑体;font-size: 16px;text-align: center;"
                                id="road">${retInfo.obj.road}</td>
                            <td class="td1" colspan="2"
                                style="width: 3.9cm;font-family: 黑体;font-size: 16px;text-align: center;">1/1
                            </td>
                        </tr>
                        <tr style="height: 0.9cm;">
                            <td class="td3" rowspan="2" style="text-align: center;width: 0.7cm;">
                                <p style="font-family: 黑体;font-size: 14px;width: 0.7cm;height: 1.7cm;line-height: 0.42cm;">
                                    客户信息</p>
                            </td>
                            <td class="td1" rowspan="2" colspan="2"
                                style="font-family: 黑体;font-size: 12px;width: 5.2cm;">
                                <div style="width: 100%;height: 1.7cm;line-height: 0.85cm;">
                                    <p style="line-height: 0.42cm;height: 0.85cm;">${retInfo.obj.rev_pro}${retInfo.obj.rev_city}${retInfo.obj.rev_area}${retInfo.obj.rev_address}</p>
                                    <p style="line-height: 0.42cm;height: 0.85cm;">${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</p>
                                </div>
                            </td>
                            <td class="td1" style="text-align: center;width: 0.9cm;">
                                <p style="font-family: 黑体;font-size: 12px;width: 0.9cm;line-height: 0.45cm;">客户签字</p>
                            </td>
                            <td class="td1" style="width: 3cm;">&nbsp;</td>
                        </tr>
                        <tr style="height: 0.8cm;">
                            <td class="td1" style="text-align: center;width: 0.9cm;">
                                <p style="font-family: 黑体;font-size: 12px;width: 0.9cm;line-height: 0.4cm;">应收金额</p>
                            </td>
                            <td class="td1" style="font-family: 黑体;font-size: 10px;text-align: center;width: 3cm;"></td>
                        </tr>
                    </table>
                    <div style="font-family: 黑体;font-size: 9px;height: 20px;width: 6.5cm;text-align: right;">${retInfo.obj.print_day}</div>
                    <table cellpadding="0" cellspacing="0" border="0" style="width: 9.8cm;margin-left: 0.1cm;">
                        <tr style="height: 0.7cm;font-family: 黑体;font-size: 13px;">
                            <td class="td2" colspan="4" style="text-align: center;">
                                运单号&nbsp;&nbsp;${retInfo.obj.express_number}</td>
                        </tr>
                        <tr style="height: 1.7cm;">
                            <td class="td3" colspan="2" style="width: 5.9cm;">
                                <div style="font-family: 黑体;font-size: 10px;">客户信息:<span>${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</span>
                                </div>
                                <div>
                                        <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}&fmt=png"
                                             style="height: 1.3cm;width: 5.9cm;"/>--%>
                                    <div id="express_no2" class="barcode" style="margin-top: 0.1cm;margin-left: 10px;"></div>
                                    <script>
                                        $("#express_no2").html(code128("${retInfo.obj.express_number}","A",1));
                                    </script>
                                </div>
                            </td>
                            <td class="td1" colspan="2" style="width: 3.9cm;">
                                <div style="height: 1.7cm;width: 3.7cm;font-size: 12px;">
                                    <p>备注</p>
                                    <span>${retInfo.obj.category}</span>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 0.7cm;">
                            <td class="td3" style="width: 2.5cm;">&nbsp;</td>
                            <td class="td1" style="width: 3.4cm;">&nbsp;</td>
                            <td class="td1" style="width: 2.4cm;">&nbsp;</td>
                            <td class="td1" style="width: 1.5cm;">&nbsp;</td>
                        </tr>
                        <tr style="height: 0.7cm;">
                            <td colspan="2" class="td3" style="width: 5.9cm;font-family: 黑体;font-size: 6px;">
                                <div style="width: 5.9cm;height: 0.7cm;line-height: 0.35cm;">
                                    <p>
                                        寄方信息：${retInfo.obj.send_pro}${retInfo.obj.send_city}${retInfo.obj.send_area}${retInfo.obj.send_address}</p>
                                    <p>${retInfo.obj.send_name}&nbsp;&nbsp;${retInfo.obj.send_mobile}</p>
                                </div>
                            </td>
                            <td colspan="2" class="td1" style="width: 3.9cm;font-family: 黑体;font-size: 9px;">
                                <div style="width: 3.8cm;height: 0.7cm;line-height: 0.35cm;">
                                    <p><font
                                            style="font-size: 8px;">商家ID：</font><span>${retInfo.obj.customer_code}</span>
                                    </p>
                                    <p><font style="font-size: 8px;">B商家订单号：</font><span
                                            style="font-size: 5px;">${retInfo.obj.ht_number}</span></p>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 0.7cm;">
                            <td colspan="2" class="td3" style="width: 5.9cm;">
                                <div style="width: 5.9cm;height: 0.7cm;line-height: 0.35cm;">
                                    <p style="font-family: 黑体;font-size:6.5px;">
                                        请您确认包裹完好，保留此包裹联时，代表您已经正常签收并确认外包裹完好。http://www.jd-ex.com<span
                                            style="margin-left: 1cm;">客服电话：400-603-3600</span></p>
                                </div>
                            </td>
                            <td colspan="2" class="td1" style="width: 3.9cm;font-family: 黑体;font-size: 10px;">
                                <div style="width: 3.8cm;height: 0.7cm;">
                                    <p style="font-size: 8px;">始发城市：</p>
                                    <div style="width: 100%;text-align: center;">${retInfo.obj.send_city}</div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div style="font-family: 黑体;font-size: 10px;width: 6.5cm;text-align: right;">${retInfo.obj.print_day}</div>
                </c:if>

                <c:if test="${retInfo.obj.express_id==3520}">
                    <table style="width: 10cm;" cellspacing="0" cellpadding="0">
                        <tr style="height: 1.3cm;">
                            <td colspan="4" style="border-top: solid 1px #000000;width: 8.3cm;height: 1.4cm;">&nbsp;</td>
                            <td style="font-size: 14pt;height: 1.4cm;font-family: 黑体;border-top: solid 1px #000000;width: 1.5cm;border-left: solid 1px #000000;text-align: center;">
                                <p style="width: 1.2cm;line-height: 0.7cm;padding: 0;margin: 0;">标准快递</p>
                            </td>
                        </tr>
                        <tr style="height: 1.5cm;">
                            <c:if test="${retInfo.obj.marker.length()<10}">
                                <td colspan="5"
                                    style="text-align: left;font-size: 30pt;font-family: 'Arial Black';font-weight: bold;border-top: solid 1px #000000;height: 1.5cm;">
                                        ${retInfo.obj.marker}
                                </td>
                            </c:if>
                            <c:if test="${retInfo.obj.marker.length()>9&&retInfo.obj.marker.length()<12}">
                                <td colspan="5"
                                    style="text-align: left;font-size: 25pt;line-height: 1.5cm;font-family: 'Arial Black';font-weight: bold;border-top: solid 1px #000000;height: 1.5cm;">
                                        ${retInfo.obj.marker}
                                </td>
                            </c:if>
                            <c:if test="${retInfo.obj.marker.length()>11&&retInfo.obj.marker.length()<14}">
                                <td colspan="5"
                                    style="text-align: left;font-size: 20pt;line-height: 1.5cm;font-family: 'Arial Black';font-weight: bold;border-top: solid 1px #000000;height: 1.5cm;">
                                        ${retInfo.obj.marker}
                                </td>
                            </c:if>
                            <c:if test="${retInfo.obj.marker.length()>13}">
                                <td colspan="5"
                                    style="text-align: left;font-size: 20pt;line-height: 0.7cm;font-family: 'Arial Black';font-weight: bold;border-top: solid 1px #000000;height: 1.5cm;">
                                        ${retInfo.obj.marker}
                                </td>
                            </c:if>
                        </tr>
                        <tr style="height: 1cm;">
                            <td colspan="5" style="border-top: solid 1px #000000;">&nbsp;</td>
                        </tr>
                        <tr style="height: 1.5cm;">
                            <td style="font-size: 11pt;font-family: 黑体;width: 0.9cm;border-top: solid 1px #000000;">
                                收
                            </td>
                            <td colspan="4"
                                style="width: 9.1cm;font-size: 10pt;font-family: 黑体;font-weight: bold;border-top: solid 1px #000000;">
                                <p style="padding: 0;margin: 0;">${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</p>
                                <p style="padding: 0;margin: 0;">${retInfo.obj.rev_pro}${retInfo.obj.rev_city}${retInfo.obj.rev_area}${retInfo.obj.rev_address}</p>
                            </td>
                        </tr>
                        <tr style="height: 1.2cm;">
                            <td style="font-size: 11pt;font-family: 黑体;width: 0.9cm;border-top: solid 1px #000000;">
                                寄
                            </td>
                            <td colspan="4"
                                style="width: 9.1cm;font-size: 8pt;font-family: 黑体;border-top: solid 1px #000000;">
                                <p style="padding: 0;margin: 0;">${retInfo.obj.send_name}&nbsp;&nbsp;${retInfo.obj.send_mobile}</p>
                                <p style="padding: 0;margin: 0;">${retInfo.obj.send_pro}${retInfo.obj.send_city}${retInfo.obj.send_area}${retInfo.obj.send_address}</p>
                            </td>
                        </tr>
                        <tr style="height: 2.3cm;">
                            <td colspan="5" style="text-align: center;border-top: solid 1px #000000;">
                                    <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}&fmt=png"
                                         style="height: 2cm;margin-top: 0.1cm;width: 9cm;"/>--%>
                                <div id="express_no1" style="margin-top: 0.1cm;"></div>
                                <script>
                                    $("#express_no1").empty().barcode("" +${retInfo.obj.express_number}, "code128", {
                                        barWidth: 2,
                                        barHeight: 60,
                                        showHRI: true,
                                        fontSize: 12,
                                        marginHRI: 1
                                    });
                                </script>
                            </td>
                        </tr>
                        <tr style="height: 2cm;">
                            <td colspan="2"
                                style="width: 2cm;border-top: solid 1px #000000;border-right: solid 1px #000000;">
                                <p style="padding: 0;margin: 0;font-size: 8pt;font-family: 黑体;text-align: center;width: 2cm;"><%=new SimpleDateFormat("yyyy/MM/dd").format(new Date()) %>
                                </p>
                                <p style="padding: 0;margin: 0;font-size: 11pt;font-family: 黑体;text-align: center;width: 2cm;"><%=new SimpleDateFormat("HH:mm:ss").format(new Date()) %>
                                </p>
                                <p style="padding: 0;margin: 0;font-size: 14pt;font-family: 黑体;text-align: center;width: 2cm;">
                                    &nbsp;</p>
                                <p style="padding: 0;margin: 0;font-size: 7pt;font-family: 黑体;text-align: right;width: 2cm;">
                                    打印时间<a class="btn_clock" style="display: block;"></a></p>
                            </td>
                            <td style="width: 6cm;border-top: solid 1px #000000;border-right: solid 1px #000000;position: relative;">
                                <p style="padding: 5px;margin: 0;width: 6cm;font-size: 8pt;font-family: 黑体;">
                                    快件送达收件人地址，经收件人或收件人（寄件人）允许的代收人签字，视为送达，您的签字代表您已经签收此包裹，并已确认商品信息无误、包装完好、没有划痕、破损等表面质量问题。
                                </p>
                                <p style="padding: 0;margin: 0;width: 6cm;font-size: 7pt;font-family: 黑体;text-align: right;position: absolute;bottom: 0px;">
                                    签收栏</p>
                            </td>
                            <td colspan="2" style="width: 2cm;border-top: solid 1px #000000;"></td>
                        </tr>
                        <tr style="height: 1px;background-color: black;line-height: 1px;">
                            <td style="width: 0.9cm;height: 1px;">&nbsp;</td>
                            <td style="width: 1.1cm;height: 1px;">&nbsp;</td>
                            <td style="width: 6cm;height: 1px;">&nbsp;</td>
                            <td style="width: 0.5cm;height: 1px;">&nbsp;</td>
                            <td style="width: 1.5cm;height: 1px;">&nbsp;</td>
                        </tr>
                        <tr style="height: 1.3cm;">
                            <td colspan="5" style="text-align: right;border-top: solid 1px #000000;">
                                    <%--<img src="${ctx}/genbc?type=code128&msg=${retInfo.obj.express_number}&fmt=png"
                                         style="height: 0.8cm;margin-top: 0.1cm;width: 5cm;"/>--%>
                                <div id="express_no2" style="margin-top: 1px;float: right;margin-right: 15px;"></div>
                                <script>
                                    $("#express_no2").empty().barcode("" +${retInfo.obj.express_number}, "code128", {
                                        barWidth: 1,
                                        barHeight: 30,
                                        showHRI: true,
                                        fontSize: 12,
                                        marginHRI: 1
                                    });
                                </script>
                            </td>
                        </tr>
                        <tr style="height: 1cm;">
                            <td style="width: 0.9cm;font-size: 11pt;font-family: 黑体;border-top: solid 1px #000000;">
                                收
                            </td>
                            <td colspan="2"
                                style="width: 7.1cm;font-size: 7pt;font-family: 黑体;border-top: solid 1px #000000;border-right: solid 1px #000000;">
                                <p style="padding: 0;margin: 0;">${retInfo.obj.rev_name}&nbsp;&nbsp;${retInfo.obj.rev_mobile}</p>
                                <p style="padding: 0;margin: 0;">${retInfo.obj.rev_pro}${retInfo.obj.rev_city}${retInfo.obj.rev_area}${retInfo.obj.rev_address}</p>
                            </td>
                            <td colspan="2" rowspan="2" style="width: 2cm;border-top: solid 1px #000000;"></td>
                        </tr>
                        <tr style="height: 1cm;">
                            <td style="width: 0.9cm;font-size: 11pt;font-family: 黑体;border-top: solid 1px #000000;">
                                寄
                            </td>
                            <td colspan="2"
                                style="width: 7.1cm;font-size: 7pt;font-family: 黑体;border-top: solid 1px #000000;border-right: solid 1px #000000;">
                                <p style="padding: 0;margin: 0;">${retInfo.obj.send_name}&nbsp;&nbsp;${retInfo.obj.send_mobile}</p>
                                <p style="padding: 0;margin: 0;">${retInfo.obj.send_pro}${retInfo.obj.send_city}${retInfo.obj.send_area}${retInfo.obj.send_address}</p>
                            </td>
                        </tr>
                        <tr style="height: 3cm;">
                            <td colspan="5"
                                style="font-family: 黑体;font-size: 8pt;position: relative;border-top: solid 1px #000000;">
                                <span style="position: absolute;bottom: 40px;right: 20px;">${retInfo.obj.category}</span>
                                <span style="position: absolute;bottom: 0px;left: 2px;height: 10pt;">${retInfo.obj.ht_number}</span>
                                <span style="position: absolute;bottom: 10px;right: 10px;height: 14pt;font-weight: bold;">已验视</span>
                            </td>
                        </tr>
                    </table>
                </c:if>
            </div>

            <c:if test="${retInfo.mark != 0}">
                <div class="data_none">${retInfo.tip}</div>
            </c:if>

            <c:if test="${retInfo.mark == 0}">
                <div style="margin:0 auto;">
                    <div id="kkpager"></div>
                </div>
            </c:if>
            <br/><br/>
        </div>
    </div><!-- centercontent -->


</div><!--bodywrapper-->

</body>
</html>
<script type="text/javascript">
    function findPrintInfo() {
        var href = "${ctx}/admin/express/order/collect/printInfo?ht_number=" + $("#ht_number").val();
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

    function expressPrint() {
        var lod = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
        lod.PRINT_INIT("2");

        if ("${retInfo.obj.express_id}" == "29") {
            lod.SET_PRINT_PAGESIZE(1, "100mm", "113mm", "连续纸");
        } else {
            lod.SET_PRINT_PAGESIZE(1, "100mm", "180mm", "连续纸");
        }
        lod.ADD_PRINT_HTM("2mm", 0, "100%", "100%", document.getElementById("table").innerHTML);
        lod.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW", true);
        lod.PREVIEW();

        var href = "${ctx}/admin/express/order/collect/print";
        function findPrintInfo() {
            var href = "${ctx}/admin/express/order/collect/printInfo?ht_number=" + $("#ht_number").val() +"&father_id=${father_id}";
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
    }
</script>