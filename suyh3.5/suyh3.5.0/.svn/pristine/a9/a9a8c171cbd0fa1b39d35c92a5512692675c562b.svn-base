package cn.hotol.app.service.three.coupon;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.page.PageDto;

/**
 * Created by LuBin
 * Date 2016-12-13.
 */
public interface CouponService {

    /**
     * @param pageDto
     * @param token
     * @Purpose 查询用户的优惠券
     * @version 3.0
     * @author lubin
     */
    public RetInfo findCouponByMember(PageDto pageDto, String token);

    /**
     * @param exp_ord_id
     * @param token
     * @Purpose 查询用户的该次订单支付可使用优惠券
     * @version 3.0
     * @author lubin
     */
    public RetInfo findCouponByCondition(int exp_ord_id, String token, int client_type);
    /**
     * @Purpose  领取优惠券
     * @version  3.4
     * @author   lizhun
     * @param    token
     * @return   RetInfo
     */
    public RetInfo receiveCoupon(String token);

    /**
     * @Purpose  判断是否符合条件
     * @version  3.4
     * @author   lizhun
     * @param    token
     * @return   RetInfo
     */
    public RetInfo ineligible(String token);
}
