<%@page  pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/import.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>添加快递SDK</title>
    <link rel="stylesheet" href="${ctx}/css/style.default.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <link rel="stylesheet" href="${ctx}/css/style.default.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-1.7.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/js/plugins/jquery.uniform.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/general.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/index.js"></script>
    <!--[if lte IE 8]><script language="javascript" type="text/javascript" src="${ctx}/js/plugins/excanvas.min.js"></script><![endif]-->
    <link href="${ctx}/css/kkpager_blue.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
    <script type="text/javascript" src="${ctx}/js/commom.js"></script>
    <link rel="stylesheet" media="screen" href="${ctx}/css/UIKit.css"/>
    <!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="${ctx}/css/style.ie9.css"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="${ctx}/css/style.ie8.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <script src="${ctx}/js/plugins/css3-mediaqueries.js"></script>
    <![endif]-->

</head>

<body class="withvernav">
<div class="bodywrapper">

    <%@ include file="../../include/topheader.jsp" %>
    <div class="header">
        <ul class="headermenu">
            <c:if test="${user_name != '13127802758'}">
                <li><a href="${ctx}/admin/accounts/list?currentPage=1"><span class="icon icon-speech"></span>角色管理</a></li>
            </c:if>
            <li><a href="${ctx}/admin/index?currentPage=1"><span class="icon icon-flatscreen"></span>用户管理</a></li>
            <li><a href="${ctx}/admin/agent/list?currentPage=1&agent_state=2"><span class="icon icon-pencil"></span>代理人管理</a></li>
            <li><a href="${ctx}/admin/express/order/list?currentPage=1&exp_ord_state=1&memb_type=0"><span class="icon icon-message"></span>订单管理</a></li>
            <c:if test="${user_name != '13127802758'}">
                <li   class="current"><a href="${ctx}/admin/banner/list?currentPage=1&is_valid=0"><span class="icon icon-chart"></span>其他管理</a></li>
            </c:if>
        </ul>
    </div><!--header-->
    <div class="vernav2 iconmenu">
        <ul>
            <li><a href="#formsub" class="addons">banner管理</a>
                <span class="arrow"></span>
                <ul id="formsub">
                    <li><a href="${ctx}/admin/banner/list?currentPage=1&is_valid=0">可用banner</a></li>
                    <li><a href="${ctx}/admin/banner/list?currentPage=1&is_valid=1">不可用banner</a></li>
                    <li><a href="${ctx}/admin/banner/jump/insert">添加banner</a></li>
                </ul>
            </li>
            <li><a href="#article" class="addons">文章管理</a>
                <span class="arrow"></span>
                <ul id="article">
                    <li><a href="${ctx}/admin/article/list?currentPage=1&is_valid=0">可用文章</a></li>
                    <li><a href="${ctx}/admin/article/list?currentPage=1&is_valid=1">不可用文章</a></li>
                    <li><a href="${ctx}/admin/article/jump/insert">添加文章</a></li>
                </ul>
            </li>
            <li class="current"><a href="#addons" class="addons">数据管理</a>
                <span class="arrow"></span>
                <ul id="addons">
                    <li><a href="${ctx}/admin/dict/type/list?currentPage=1&code_type=province">省市区</a></li>
                    <li><a href="${ctx}/admin/dict/type/list?currentPage=1&code_type=express_category">发件类型</a></li>
                    <li><a href="${ctx}/admin/dict/type/list?currentPage=1&code_type=express_ask">发件需求</a></li>
                    <li><a href="${ctx}/admin/dict/type/list?currentPage=1&code_type=express_company">快递公司</a></li>
                </ul>
            </li>
            <li><a href="#scoreGoods" class="addons">积分商品管理</a>
                <span class="arrow"></span>
                <ul id="scoreGoods">
                    <li><a href="${ctx}/admin/score/goods/list?currentPage=1&goods_state=0">可用积分商品</a></li>
                    <li><a href="${ctx}/admin/score/goods/list?currentPage=1&goods_state=1">不可用积分商品</a></li>
                    <li><a href="${ctx}/admin/score/goods/jump/insert">添加积分商品</a></li>
                </ul>
            </li>
            <li><a href="${ctx}/admin/sendhis/list?currentPage=1" class="support">短信发送历史</a></li>

        </ul>
        <a class="togglemenu"></a>
        <br /><br />
    </div><!--leftmenu-->
    <div class="centercontent tables">
        <div id="contentwrapper" class="contentwrapper">
            <div class="contenttitle2">
                <h3>添加快递SDK</h3>
            </div><!--contenttitle-->
            <div class="contentwrapper">
                <div class="details">
                    <div class="search_input">
                        <p>
                            快递网点名称:<input type="text" name="dot_name" id="dot_name" />
                        </p>
                        <p>
                            快递网点编号:<input type="text" name="dot_number" id="dot_number" />
                        </p>
                        <p>
                            分配客户名称:<input type="text" name="customer_name" id="customer_name" />
                        </p>
                        <p>
                            分配客户编号:<input type="text" name="customer_number" id="customer_number" />
                        </p>
                        <p>
                            分配客户密码:<input type="text" name="customer_password" id="customer_password" />
                        </p>
                        <p>
                            快递接口类型:
                            <select id="sdkj_itf_type">
                                <option value="1">提取单号</option>
                                <option value="2">筛单</option>
                                <option value="3">获取打印大头笔</option>
                                <option value="4">提交订单</option>
                                <option value="5">查询物流</option>
                                <option value="6">获取可用单号数量</option>
                                <option value="7">获取可用单号数量</option>
                            </select>
                        </p>
                        <p>
                            快递接口地址:<input type="text" name="sdk_itf_url" id="sdk_itf_url" />
                        </p>
                        <p>
                            快递接口密钥:<input type="text" name="app_key" id="app_key" />
                        </p>
                        <p>
                            快递接口密文:<input type="text" name="app_secret" id="app_secret" />
                        </p>
                        <p>
                            接口调用凭证:<input type="text" name="access_token" id="access_token" />
                        </p>
                        <p>
                            接口方法标识:<input type="text" name="sdk_code" id="sdk_code" />
                        </p>
                        <p>
                            <button class="submit radius2" id="submit_bt">添加</button>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- centercontent -->
</div><!--bodywrapper-->
<script type="text/javascript">
    function getParameter(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r!=null) return unescape(r[2]); return null;
    }

    //init
    $(function(){

    });

    $("#submit_bt").click(function () {
        var data = "dot_name=" + $("#dot_name").val() + "&dot_number=" + $("#dot_number").val() + "&customer_name=" +
            $("#customer_name").val() + "&customer_number=" + $("#customer_number").val() + "&customer_password=" +
            $("#customer_password").val() + "&sdkj_itf_type=" + $("#sdkj_itf_type").val() + "&sdk_itf_url=" +
            $("#sdk_itf_url").val() + "&app_key=" + $("#app_key").val() + "&app_secret=" + $("#app_secret").val() +
            "&access_token=" + $("#access_token").val() + "&sdk_code=" + $("#sdk_code").val() + "&eoa_id=${eoa_id}";

        $.ajax({
            type:'post',
            url:'${ctx}/admin/express/sdk/insert',
            dataType : 'json',
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success:function(data){
                if(data.mark=="0"){
                    window.location.href="${ctx}/admin/express/sdk/list?currentPage=1&eoa_id=${eoa_id}";
                }else{
                    alert(data.tip);
                }
            }

        });
    })

</script>
</body>
</html>
