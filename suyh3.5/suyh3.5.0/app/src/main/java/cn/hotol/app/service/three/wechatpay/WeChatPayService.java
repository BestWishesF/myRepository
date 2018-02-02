package cn.hotol.app.service.three.wechatpay;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by LuBin
 * Date 2017-01-04.
 */
public interface WeChatPayService {

    /**
     * @param expressOrderPayDto
     * @param token
     * @param ip
     * @return RetInfo
     * @Purpose 微信用户支付订单
     * @version 3.0
     * @author lubin
     */
    public RetInfo weChatOrderPay(ExpressOrderPayDto expressOrderPayDto, String ip, String token);

    /**
     * @param request
     * @return RetInfo
     * @Purpose 微信用户支付订单回调
     * @version 3.0
     * @author lubin
     */
    public RetInfo orderPayNotify(HttpServletRequest request);

}
