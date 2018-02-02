package cn.hotol.app.service.three.timer;

import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.monthbill.TdHtMembMonthBillDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.PushUtil;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import cn.hotol.app.repository.TdHtMembMonthBillRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuBin
 * Date 2017-02-16.
 */
public class MonthBillTimer {

    private static Logger logger = Logger.getLogger(MonthBillTimer.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtMembMonthBillRepository tdHtMembMonthBillRepository;

    /**
     * @return RetInfo
     * @Purpose 生成月结账单定时器
     * @version 3.0
     * @author lubin
     */
    public void generateMonthBill() {
        String logInfo = this.getClass().getName() + ":generateMonthBill:";
        logger.info("======" + logInfo + "begin======");
        try {
            String month = CommonUtil.getMonth();
            Long now_date = System.currentTimeMillis();
            List<TdHtMemberDto> tdHtMemberDtoList = tdHtMemberRepository.findMemberByRole(Constant.MONTH_MEMBER_ROLE);
            List<String> androidRegistrationId = new ArrayList<String>();
            List<String> iosRegistrationId = new ArrayList<String>();
            for (int i = 0; i < tdHtMemberDtoList.size(); i++) {
                TdHtMemberDto tdHtMemberDto = tdHtMemberDtoList.get(i);

                TdHtExpressOrderDto tdHtExpressOrderDto = new TdHtExpressOrderDto();
                tdHtExpressOrderDto.setMemb_id(tdHtMemberDto.getMemb_id());
                tdHtExpressOrderDto.setExp_ord_month(month);
                BigDecimal total_money = new BigDecimal(0);
                List<TdHtExpressOrderDto> tdHtExpressOrderDtoList = tdHtExpressOrderRepository.findExpOrdByMonthAndMemb(tdHtExpressOrderDto);
                for (int j = 0; j < tdHtExpressOrderDtoList.size(); j++) {
                    TdHtExpressOrderDto tdHtExpressOrder = tdHtExpressOrderDtoList.get(j);
                    BigDecimal order_money = new BigDecimal(tdHtExpressOrder.getExp_ord_amount());
                    if (tdHtExpressOrder.getDoor_end_time().getTime() >= tdHtExpressOrder.getCollect_time().getTime()) {
                        order_money = order_money.add(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity()));
                    }
                    total_money = total_money.add(order_money);
                }

                if(tdHtExpressOrderDtoList.size()>0){
                    TdHtMembMonthBillDto tdHtMembMonthBillDto = new TdHtMembMonthBillDto();
                    tdHtMembMonthBillDto.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtMembMonthBillDto.setMemb_mon_bill_amount(total_money);
                    tdHtMembMonthBillDto.setMemb_mon_bill_pay_amount(new BigDecimal(0));
                    tdHtMembMonthBillDto.setMemb_mon_bill_number(CommonUtil.getOrderNub());
                    tdHtMembMonthBillDto.setMemb_mon_bill_time(new Timestamp(now_date));
                    tdHtMembMonthBillDto.setMemb_mon_bill_pay_time(new Timestamp(now_date));
                    tdHtMembMonthBillDto.setState(1);
                    tdHtMembMonthBillDto.setMemb_mon_bill_month(month);
                    tdHtMembMonthBillDto.setPay_channel(1);
                    tdHtMembMonthBillRepository.insertMemberMonthBill(tdHtMembMonthBillDto);

                    if (tdHtMemberDto.getPush_type() == 1) {
                        PushUtil.pushAndroidMember(tdHtMemberDto.getPush_token(), "您有月结账单需要支付.", "13", "", tdHtMemberDto.getApp_version());
                    } else if (tdHtMemberDto.getPush_type() == 2) {
                        PushUtil.pushIosMember(tdHtMemberDto.getPush_token(), "您有月结账单需要支付.", "13", "default", tdHtMemberDto.getApp_version());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
