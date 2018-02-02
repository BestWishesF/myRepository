package cn.hotol.app.service.three.monthbill;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.bean.dto.monthbill.ConsumptionRecordDto;
import cn.hotol.app.bean.dto.monthbill.TdHtMembMonthBillDto;
import cn.hotol.app.bean.dto.page.PageDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-02-15.
 */
public interface MonthbillService {

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询用户待支付月结账单订单数据
     * @version 3.0
     * @author lubin
     */
    public RetInfo findMemberMonthBill(PageDto pageDto, String token);

    /**
     * @param expressOrderPayDto
     * @return
     * @Purpose 月结账单支付
     * @version 3.0
     * @author lubin
     */
    public RetInfo memberPayMonthBill(ExpressOrderPayDto expressOrderPayDto, String token, HttpServletRequest request);

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询用户月结账单消费记录
     * @version 3.0
     * @author lubin
     */
    public RetInfo findMonthBillPage(PageDto pageDto, String token);

    /**
     * @param pageDto
     * @return
     * @Purpose 查询月结账单消费详情
     * @version 3.0
     * @author lubin
     */
    public RetInfo findMonthBillDetails(PageDto pageDto, String token);

}
