package cn.hotol.app.repository;

import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.dto.coupon.TdHtCouponDto;
import cn.hotol.app.bean.dto.page.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-13.
 */

@Repository
public interface TdHtCouponRepository {

    /**
     * @param pageDto
     * @return Integer
     * @Purpose 查询用户优惠券数量
     * @version 3.0
     * @author lubin
     */
    public Integer findCouponByMebamount(PageDto pageDto);

    /**
     * @param pageDto
     * @return List<TdHtCouponDto>
     * @Purpose 查询用户优惠券
     * @version 3.0
     * @author lubin
     */
    public List<TdHtCouponDto> findCouponByMeb(PageDto pageDto);

    /**
     * @param tdHtCoupon
     * @Purpose 添加优惠券
     * @version 3.0
     * @author lubin
     */
    public void insertMembCoupon(TdHtCoupon tdHtCoupon);

    /**
     * @param cou_id
     * @Purpose 通过优惠券id查询优惠券信息
     * @version 3.0
     * @author lubin
     */
    public TdHtCoupon findCouponById(int cou_id);

    /**
     * @param tdHtCoupon
     * @Purpose 修改优惠券状态为已使用
     * @version 3.0
     * @author lubin
     */
    public void updateUseCoupon(TdHtCoupon tdHtCoupon);

    /**
     * @param exp_ord_id
     * @Purpose 通过订单查询用户选择的优惠券
     * @version 3.0
     * @author lubin
     */
    public TdHtCoupon findCouponByOrder(int exp_ord_id);

    /**
     * @param tdHtCoupon
     * @Purpose 查询用户支付时可使用的优惠券
     * @version 3.0
     * @author lubin
     * @return List<TdHtCouponDto>
     */
    public List<TdHtCouponDto> findCouponByCondition(TdHtCoupon tdHtCoupon);

    /**
     * @param exp_ord_id
     * @Purpose 修改同一个订单使用的优惠券
     * @version 3.0
     * @author lubin
     * @return
     */
    public void updateCouponByOrder(int exp_ord_id);
    /**
     * @Purpose  根据名字查找优惠券
     * @version  3.4
     * @author   lizhun
     * @param    map
     * @return   RetInfo
     */
    public TdHtCouponDto findCouponByName(Map<String, Object> map);

}
