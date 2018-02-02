package cn.hotol.app.service.three.found;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.page.PageDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/21.
 */
public interface FoundService {
    /**
     * @param tdHtMembFoundChangeDto
     * @return RetInfo
     * @Purpose 充值
     * @version 3.0
     * @author lizhun
     */
    public RetInfo recharge(TdHtMembFoundChangeDto tdHtMembFoundChangeDto, HttpServletRequest request);

    /**
     * @param charse
     * @return RetInfo
     * @Purpose 充值回调
     * @version 3.0
     * @author lizhun
     */
    public RetInfo notify(Map<String, Object> charse);

    /**
     * @param mfchg_number
     * @return RetInfo
     * @Purpose 查询充值结果
     * @version 3.0
     * @author lizhun
     */
    public RetInfo rechargeResult(String mfchg_number);

    /**
     * @param expressOrderPayDto
     * @return RetInfo
     * @Purpose 订单支付
     * @version 3.0
     * @author lubin
     */
    public RetInfo paymentOrder(ExpressOrderPayDto expressOrderPayDto, String token, HttpServletRequest request);

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询用户的收支记录
     * @version 3.0
     * @author lubin
     */
    public RetInfo findMemberFound(PageDto pageDto, String token);

    /**
     * @param expressOrderPayDto
     * @return RetInfo
     * @Purpose 代理人代付订单
     * @version 3.0
     * @author lubin
     */
    public RetInfo replacePayOrder(ExpressOrderPayDto expressOrderPayDto, String token, HttpServletRequest request);

}
