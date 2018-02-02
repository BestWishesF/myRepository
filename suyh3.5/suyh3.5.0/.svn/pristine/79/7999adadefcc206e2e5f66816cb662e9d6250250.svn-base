package cn.hotol.app.service.three.monthbill;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.monthbill.ConsumptionDetailsDto;
import cn.hotol.app.bean.dto.monthbill.ConsumptionRecordDto;
import cn.hotol.app.bean.dto.monthbill.MonthExpOrdDto;
import cn.hotol.app.bean.dto.monthbill.TdHtMembMonthBillDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-02-15.
 */

@Service
public class MonthbillServiceImpl implements MonthbillService{

    private static Logger logger = Logger.getLogger(MonthbillServiceImpl.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    @Resource
    private TdHtMembMonthBillRepository tdHtMembMonthBillRepository;
    @Resource
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询用户待支付月结账单订单数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findMemberMonthBill(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findMemberMonthBill:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto=tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            if(tdHtMemberDto.getMemb_role()!=2){
                retInfo.setMark("1");
                retInfo.setTip("您还不是月结用户.");
            }else {
                String month= CommonUtil.getMonth();
                TdHtMembMonthBillDto tdHtMembMonthBill=new TdHtMembMonthBillDto();
                tdHtMembMonthBill.setMemb_id(member.getMemb_id());
                tdHtMembMonthBill.setMemb_mon_bill_month(month);
                TdHtMembMonthBillDto tdHtMembMonthBillDto=tdHtMembMonthBillRepository.findMemberNoPayNotMonthBill(tdHtMembMonthBill);
                Map<String, Object> map = new HashMap<String, Object>();
                if(tdHtMembMonthBillDto!=null){//有账单未支付
                    month=tdHtMembMonthBillDto.getMemb_mon_bill_month();
                    map.put("is_pay",1);
                    map.put("total_money", tdHtMembMonthBillDto.getMemb_mon_bill_amount());
                    map.put("memb_mon_bill_id",tdHtMembMonthBillDto.getMemb_mon_bill_id());
                }else {//账单已支付
                    TdHtExpressOrderDto tdHtExpressOrderDto=new TdHtExpressOrderDto();
                    tdHtExpressOrderDto.setMemb_id(member.getMemb_id());
                    tdHtExpressOrderDto.setExp_ord_month(month);
                    BigDecimal total_money=new BigDecimal(0);
                    List<TdHtExpressOrderDto> tdHtExpressOrderDtoList=tdHtExpressOrderRepository.findExpOrdByMonthAndMemb(tdHtExpressOrderDto);
                    for(int i=0;i<tdHtExpressOrderDtoList.size();i++){
                        TdHtExpressOrderDto tdHtExpressOrder=tdHtExpressOrderDtoList.get(i);
                        BigDecimal order_money=new BigDecimal(tdHtExpressOrder.getExp_ord_amount());
                        if(tdHtExpressOrder.getDoor_end_time().getTime()>=tdHtExpressOrder.getCollect_time().getTime()){
                            order_money = order_money.add(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity()));
                        }
                        total_money=total_money.add(order_money);
                    }
                    map.put("total_money", total_money);
                    map.put("memb_mon_bill_id",0);
                    map.put("is_pay",0);
                }
                Page<MonthExpOrdDto> page = new Page<MonthExpOrdDto>(pageDto.getPage_size(), pageDto.getPage_no());
                pageDto.setId(member.getMemb_id());
                pageDto.setExp_ord_month(month);
                pageDto.setState(5);
                pageDto.setLimit_str(page.getLimitCriterion());
                int count = tdHtExpressCollectRepository.findMonthMemberCltSize(pageDto);
                List<MonthExpOrdDto> monthExpOrdDtoList = tdHtExpressCollectRepository.findMonthMemberCltPage(pageDto);
                page.setTotalCount(count);

                Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                for (int i = 0; i < monthExpOrdDtoList.size(); i++) {
                    MonthExpOrdDto monthExpOrdDto = monthExpOrdDtoList.get(i);
                    String province = dicts.get(String.valueOf(monthExpOrdDto.getAdd_province())).getCode_name();
                    String city = dicts.get(String.valueOf(monthExpOrdDto.getAdd_city())).getCode_name();
                    String region = dicts.get(String.valueOf(monthExpOrdDto.getAdd_region())).getCode_name();
                    monthExpOrdDto.setAdd_detail_address(province + city + region + monthExpOrdDto.getAdd_detail_address());
                }

                map.put("items", monthExpOrdDtoList);
                map.put("total_pages", page.getTotalPages());
                map.put("page_no", page.getPageNo());

                retInfo.setMark("0");
                retInfo.setTip("已完成数据查询成功.");
                retInfo.setObj(map);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param expressOrderPayDto
     * @return
     * @Purpose 月结账单支付
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo memberPayMonthBill(ExpressOrderPayDto expressOrderPayDto, String token, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":findMemberMonthBill:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto=tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            if(tdHtMemberDto.getMemb_role()!=2){
                retInfo.setMark("1");
                retInfo.setTip("您还不是月结用户.");
            }else {
                TdHtMembMonthBillDto tdHtMembMonthBillDto=tdHtMembMonthBillRepository.findMemberMonthBillById(expressOrderPayDto.getMemb_mon_bill_id());
                if(tdHtMembMonthBillDto==null){
                    retInfo.setMark("1");
                    retInfo.setTip("该账单不存在.");
                }else{
                    if(tdHtMembMonthBillDto.getState()==2){
                        retInfo.setMark("1");
                        retInfo.setTip("该账单已经支付.");
                    }else{
                        TdHtMembFoundChangeDto tdHtMembFoundChangeDto=new TdHtMembFoundChangeDto();
                        tdHtMembFoundChangeDto.setExp_ord_id(0);
                        tdHtMembFoundChangeDto.setMemb_id(tdHtMembMonthBillDto.getMemb_id());
                        tdHtMembFoundChangeDto.setMfchg_time(new Timestamp(System.currentTimeMillis()));
                        tdHtMembFoundChangeDto.setMfchg_month(CommonUtil.getMonth());
                        tdHtMembFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub());
                        tdHtMembFoundChangeDto.setMfchg_name("月结账单支付");
                        tdHtMembFoundChangeDto.setMfchg_front_amount(new BigDecimal(0));
                        tdHtMembFoundChangeDto.setMfchg_back_amount(new BigDecimal(0));
                        tdHtMembFoundChangeDto.setMfchg_type(3);
                        tdHtMembFoundChangeDto.setMfchg_state(1);
                        tdHtMembFoundChangeDto.setMfchg_change_amount(tdHtMembMonthBillDto.getMemb_mon_bill_amount());
                        tdHtMembFoundChangeDto.setMfchg_channel(expressOrderPayDto.getPay_type());
                        tdHtMembFoundChangeDto.setMemb_mon_bill_id(tdHtMembMonthBillDto.getMemb_mon_bill_id());
                        tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                        tdHtMembMonthBillDto.setMemb_mon_bill_number(tdHtMembFoundChangeDto.getMfchg_number());
                        tdHtMembMonthBillDto.setPay_channel(expressOrderPayDto.getPay_type());
                        tdHtMembMonthBillDto.setMemb_mon_bill_pay_time(tdHtMembFoundChangeDto.getMfchg_time());
                        tdHtMembMonthBillRepository.updateWaitPayBill(tdHtMembMonthBillDto);

                        Pingpp.apiKey = Constant.Live_Secret_Key;
                        Map<String, Object> chargeParams = new HashMap<String, Object>();
                        chargeParams.put("order_no", tdHtMembMonthBillDto.getMemb_mon_bill_number());
                        chargeParams.put("amount", tdHtMembFoundChangeDto.getMfchg_change_amount().multiply(new BigDecimal(100)).intValue());
                        Map<String, String> app = new HashMap<String, String>();
                        app.put("id", Constant.MEMBER_APP_ID);
                        chargeParams.put("order_no", tdHtMembMonthBillDto.getMemb_mon_bill_number());
                        chargeParams.put("app", app);
                        if (tdHtMembMonthBillDto.getPay_channel() == 1) {
                            chargeParams.put("channel", "wx");  //微信wx
                        }
                        if (tdHtMembMonthBillDto.getPay_channel() == 2) {
                            chargeParams.put("channel", "alipay");  //支付宝alipay
                        }
                        chargeParams.put("currency", "cny");
                        chargeParams.put("client_ip", Ip.getIpAddr(request));
                        chargeParams.put("subject", "月结账单支付");
                        chargeParams.put("body", "月结账单支付");
                        Charge charge = Charge.create(chargeParams);
                        retInfo.setMark("0");
                        retInfo.setTip("下单成功");
                        Map<String, Object> map = new HashMap<>();
                        map.put("charge", charge);
                        retInfo.setObj(map);
                    }
                }
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询用户月结账单消费记录
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findMonthBillPage(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findMonthBillPage:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto=tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            if(tdHtMemberDto.getMemb_role()!=2){
                retInfo.setMark("1");
                retInfo.setTip("您还不是月结用户.");
            }else {
                Page<ConsumptionRecordDto> page = new Page<ConsumptionRecordDto>(pageDto.getPage_size(), pageDto.getPage_no());
                pageDto.setId(member.getMemb_id());
                pageDto.setLimit_str(page.getLimitCriterion());
                int count = tdHtMembMonthBillRepository.findMemberMonthBillSize(member.getMemb_id());
                List<ConsumptionRecordDto> consumptionRecordDtoList = tdHtMembMonthBillRepository.findMemberMonthBillPage(pageDto);
                page.setTotalCount(count);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("items", consumptionRecordDtoList);
                map.put("total_pages", page.getTotalPages());
                map.put("page_no", page.getPageNo());

                retInfo.setMark("0");
                retInfo.setTip("已完成数据查询成功.");
                retInfo.setObj(map);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 查询月结账单消费详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findMonthBillDetails(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findMonthBillDetails:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto=tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            if(tdHtMemberDto.getMemb_role()!=2){
                retInfo.setMark("1");
                retInfo.setTip("您还不是月结用户.");
            }else {
                TdHtMembMonthBillDto tdHtMembMonthBillDto=tdHtMembMonthBillRepository.findMemberMonthBillById(pageDto.getId());
                TdHtExpressOrderDto tdHtExpressOrderDto=new TdHtExpressOrderDto();
                tdHtExpressOrderDto.setMemb_id(member.getMemb_id());
                tdHtExpressOrderDto.setExp_ord_month(tdHtMembMonthBillDto.getMemb_mon_bill_month());

                Page<ConsumptionDetailsDto> page = new Page<ConsumptionDetailsDto>(pageDto.getPage_size(), pageDto.getPage_no());
                pageDto.setId(member.getMemb_id());
                pageDto.setExp_ord_month(tdHtMembMonthBillDto.getMemb_mon_bill_month());
                pageDto.setLimit_str(page.getLimitCriterion());
                int count = tdHtExpressOrderRepository.findMonConsumeDetailsSize(pageDto);
                List<ConsumptionDetailsDto> consumptionDetailsDtoList = tdHtExpressOrderRepository.findMonConsumeDetailsPage(pageDto);
                page.setTotalCount(count);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("items", consumptionDetailsDtoList);
                map.put("total_pages", page.getTotalPages());
                map.put("page_no", page.getPageNo());
                map.put("state", tdHtMembMonthBillDto.getState());

                retInfo.setMark("0");
                retInfo.setTip("已完成数据查询成功.");
                retInfo.setObj(map);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
