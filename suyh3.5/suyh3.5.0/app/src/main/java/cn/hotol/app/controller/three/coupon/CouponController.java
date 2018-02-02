package cn.hotol.app.controller.three.coupon;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtExpressOrder;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.service.three.coupon.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2016-12-14.
 */

@Controller
public class CouponController {

    @Resource
    private CouponService couponService;

    /**
     * @param pageDto
     * @param token
     * @Purpose 查询优惠券列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findCouponByMember", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findCouponByMember3(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return couponService.findCouponByMember(pageDto, token);
    }

    /**
     * @param pageDto
     * @param token
     * @Purpose 查询优惠券列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/5/token/findCouponByMember", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findCouponByMember(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return couponService.findCouponByMember(pageDto, token);
    }

    /**
     * @param tdHtExpressOrder
     * @param token
     * @Purpose 查询用户的该次订单支付可使用优惠券
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/5/token/findCouponByCondition", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findCouponByCondition(@RequestBody TdHtExpressOrder tdHtExpressOrder,
                                         @RequestHeader(value = "token", required = true) String token,
                                         @RequestHeader(value = "client_type", required = true) Integer client_type) {
        return couponService.findCouponByCondition(tdHtExpressOrder.getExp_ord_id(), token, client_type);
    }
    /**
     * @Purpose  领取优惠券
     * @version  3.4
     * @author   lizhun
     * @param    token
     * @return   RetInfo
     */
    @RequestMapping(value = "/app/7/token/receiveCoupon", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo receiveCoupon(@RequestHeader(value = "token", required = true) String token) {
        return couponService.receiveCoupon(token);
    }
    /**
     * @Purpose  判断是否符合条件
     * @version  3.4
     * @author   lizhun
     * @param    token
     * @return   RetInfo
     */
    @RequestMapping(value = "/app/7/token/ineligible", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo ineligible(@RequestHeader(value = "token", required = true) String token) {
        return couponService.ineligible(token);
    }
}
