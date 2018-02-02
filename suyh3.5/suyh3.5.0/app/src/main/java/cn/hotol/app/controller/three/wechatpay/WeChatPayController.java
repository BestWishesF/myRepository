package cn.hotol.app.controller.three.wechatpay;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.common.util.Ip;
import cn.hotol.app.service.three.wechatpay.WeChatPayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by LuBin
 * Date 2017-01-04.
 */
@Controller
public class WeChatPayController {

    @Resource
    private WeChatPayService weChatPayService;

    /**
     * @param expressOrderPayDto
     * @return
     * @Purpose 微信端订单支付
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/weChatOrderPay", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo weChatOrderPay(@RequestBody ExpressOrderPayDto expressOrderPayDto, HttpServletRequest request,
                                  @RequestHeader(value = "token", required = true) String token) {
        return weChatPayService.weChatOrderPay(expressOrderPayDto, Ip.getIpAddr(request), token);
    }

    /**
     * @param request
     * @return
     * @Purpose 微信端支付回调
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/weChat/orderPayNotify", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String orderPayNotify(HttpServletRequest request) {
        RetInfo retInfo = weChatPayService.orderPayNotify(request);
        return retInfo.getTip();
    }

}
