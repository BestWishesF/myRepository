package cn.hotol.app.service.three.sendexpress;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.work.TsHtAgentWorkTimeDto;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.expressorder.ExpressDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.location.*;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.monthbill.TdHtMembMonthBillDto;
import cn.hotol.app.bean.dto.number.TsHtOpenNumberDto;
import cn.hotol.app.bean.dto.push.PushDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by liyafei on 2016/12/3.
 */
@Service
public class ExpressServiceImpl implements ExpressService {

    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;
    @Resource
    private TdHtMembAddressRepository tdHtMembAddressRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TsHtOpenNumberRepository tsHtOpenNumberRepository;
    @Resource
    private TdHtMembMonthBillRepository tdHtMembMonthBillRepository;
    @Resource
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;

    private static Logger logger = Logger.getLogger(ExpressServiceImpl.class);

    /**
     * @param token
     * @param expressDto
     * @return RetInfo
     * @Purpose 用户寄单个件
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo sendOneExpress(ExpressDto expressDto, String token, int client_type, int version) {
        String logInfo = this.getClass().getName() + ":sendOneExpress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中获取用户
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtMemberDto.getMemb_id());

            boolean verification = true;
            String errMessage = "";
            TdHtExpressOrderDto tdHtExpressOrderDto = expressDto.getExpress_order();
            if (tdHtExpressOrderDto.getAdd_name() == null || "".equals(tdHtExpressOrderDto.getAdd_name().trim())) {
                errMessage = "请输入正确的发件人姓名.";
                verification = false;
            }
            boolean isMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderDto.getAdd_mobile_phone());
            boolean isPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderDto.getAdd_mobile_phone());
            if (tdHtExpressOrderDto.getAdd_mobile_phone() == null || (!isMobile && !isPhone)) {
                errMessage = "请输入正确的发件人联系方式.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderDto.getAdd_detail_address().trim())) {
                errMessage = "请输入正确的发件人详细地址.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getExpress_id() == 0) {
                errMessage = "请选择快递公司.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getDoor_start_time().getTime() >= tdHtExpressOrderDto.getDoor_end_time().getTime()) {
                errMessage = "请选择正确的预约时间.";
                verification = false;
            }
            if(tdHtExpressOrderDto.getDoor_start_time().getTime() + 300000 < System.currentTimeMillis()){
                errMessage = "请选择正确的预约时间.";
                verification = false;
            }

            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = expressDto.getExpress_order_collect();
            if (tdHtExpressOrderCollectDto.getAdd_name() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_name().trim())) {
                errMessage = "请输入正确的收件人姓名.";
                verification = false;
            }
            boolean isCollectMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
            boolean isCollectPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
            if (tdHtExpressOrderCollectDto.getAdd_mobile_phone() == null || (!isCollectMobile && !isCollectPhone)) {
                errMessage = "请输入正确的收件人联系方式.";
                verification = false;
            }
            if (tdHtExpressOrderCollectDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_detail_address().trim())) {
                errMessage = "请输入正确的收件人详细地址.";
                verification = false;
            }

            if (member.getMemb_role() == 2) {
                String month = CommonUtil.getMonth();
                TdHtMembMonthBillDto tdHtMembMonthBill = new TdHtMembMonthBillDto();
                tdHtMembMonthBill.setMemb_id(member.getMemb_id());
                tdHtMembMonthBill.setMemb_mon_bill_month(month);
                TdHtMembMonthBillDto tdHtMembMonthBillDto = tdHtMembMonthBillRepository.findMemberNoPayNotMonthBill(tdHtMembMonthBill);
                if (tdHtMembMonthBillDto != null) {
                    errMessage = "您有月结账单未支付，请先支付.";
                    verification = false;
                }
            }

            if (tdHtExpressOrderCollectDto.getHt_number() != null && !"".equals(tdHtExpressOrderCollectDto.getHt_number().trim())) {
                TsHtOpenNumberDto tsHtOpenNumberDto = tsHtOpenNumberRepository.findOpenNumberByHtNumber(tdHtExpressOrderCollectDto.getHt_number());
                if (tsHtOpenNumberDto == null) {
                    errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]不正确.";
                    verification = false;
                } else {
                    if (tsHtOpenNumberDto.getHt_open_state() == 1) {
                        errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]已使用.";
                        verification = false;
                    }
                }
            }else if((member.getMemb_role() == 2 && version < 6) || (member.getMemb_discount().doubleValue() < 1 && version >= 6)){
                errMessage = "厚通单号不能为空..";
                verification = false;
            }

            if (!verification) {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
            } else {
                retInfo.setMark("0");
                Date now = new Date();

                //获取快递公司名称
                String express_name = "";
                Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
                List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(tdHtExpressOrderDto.getAdd_region());//从字典中获取所有快递公司
                if (expressCompanyList != null) {
                    for (int e = 0; e < expressCompanyList.size(); e++) {
                        TsHtDictDto tsHtDict = expressCompanyList.get(e);
                        if (tsHtDict.getDict_id() == tdHtExpressOrderDto.getExpress_id()) {
                            express_name = tsHtDict.getCode_name();
                        }
                    }
                }

                if (!"".equals(express_name)) {
                    if (isMobile) {
                        tdHtExpressOrderDto.setAdd_telephone("");
                        tdHtExpressOrderDto.setAdd_mobile_phone(tdHtExpressOrderDto.getAdd_mobile_phone().replace("-", ""));
                    } else {
                        tdHtExpressOrderDto.setAdd_telephone(tdHtExpressOrderDto.getAdd_mobile_phone().replace("-", ""));
                        tdHtExpressOrderDto.setAdd_mobile_phone("");
                    }
                    if (isCollectMobile) {
                        tdHtExpressOrderCollectDto.setAdd_telephone("");
                        tdHtExpressOrderCollectDto.setAdd_mobile_phone(tdHtExpressOrderCollectDto.getAdd_mobile_phone().replace("-", ""));
                    } else {
                        tdHtExpressOrderCollectDto.setAdd_telephone(tdHtExpressOrderCollectDto.getAdd_mobile_phone().replace("-", ""));
                        tdHtExpressOrderCollectDto.setAdd_mobile_phone("");
                    }

                    //筛选订单（判断是否可以寄件）
                    ExpressScreenUtil expressScreenUtil = new ExpressScreenUtil();
                    List<TdHtExpressOrderCollectDto> collectDtoList = new ArrayList<TdHtExpressOrderCollectDto>();
                    tdHtExpressOrderCollectDto.setExp_ord_clt_number(CommonUtil.getOrderNub());
                    collectDtoList.add(tdHtExpressOrderCollectDto);
                    retInfo = expressScreenUtil.expressScreen(tdHtExpressOrderDto, collectDtoList);

                    if ("0".equals(retInfo.getMark())) {
                        BigDecimal weight = new BigDecimal(tdHtExpressOrderCollectDto.getExp_ord_clt_height());
                        BigDecimal price = expressScreenUtil.reckonExpressPrice(tdHtExpressOrderDto.getExpress_id(), weight, tdHtExpressOrderDto.getAdd_province(), tdHtExpressOrderCollectDto.getAdd_province());

                        tdHtExpressOrderCollectDto.setTotal_amount(price);
                        if (member.getMemb_role() == 1) {
                            if (member.getMemb_discount().doubleValue() < 1) {
                                price = price.multiply(member.getMemb_discount());
                            }
                        } else if (member.getMemb_role() == 2) {
                            price = price.multiply(member.getMemb_discount());
                        }
                        tdHtExpressOrderCollectDto.setExpress_price(price.setScale(2, BigDecimal.ROUND_HALF_UP));
                        tdHtExpressOrderCollectDto.setDiscount_amount(tdHtExpressOrderCollectDto.getTotal_amount().subtract(tdHtExpressOrderCollectDto.getExpress_price()));
                        tdHtExpressOrderCollectDto.setCoupon_amount(new BigDecimal(0));
                        tdHtExpressOrderCollectDto.setCoupon_amount_type(0);

                        TdHtExpressOrderDto newestWaitTakeExpOrd = tdHtExpressOrderRepository.findNewestWaitTakeExpOrd(member.getMemb_id());
                        TdHtExpressOrderDto expressOrderDto = new TdHtExpressOrderDto();
                        expressOrderDto.setMemb_id(member.getMemb_id());
                        expressOrderDto.setPay_time(new Date(now.getTime() - 30 * 60 * 1000));
                        TdHtExpressOrderDto memberPayExpOrd = tdHtExpressOrderRepository.findMembPayExpOrd(expressOrderDto);
                        Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
                        String key = tdHtExpressOrderDto.getExpress_id() + "#" + tdHtExpressOrderDto.getAdd_region();
                        CourierDto courierDto = courierMap.get(key);

                        TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = new TdHtAgentFoundChangeDto();
                        tdHtExpressOrderDto.setMemb_id(tdHtMemberDto.getMemb_id());
                        tdHtExpressOrderDto.setExp_ord_type(courierDto.getEoa_type());
                        if (newestWaitTakeExpOrd != null || memberPayExpOrd != null) {
                            tdHtAgentFoundChangeDto.setAfchg_change_amount(courierDto.getMore_single_bonus());
                            tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(now.getTime()));
                            tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                            tdHtAgentFoundChangeDto.setAfchg_type(1);
                            tdHtAgentFoundChangeDto.setAfchg_state(2);
                            tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub());
                            tdHtAgentFoundChangeDto.setAfchg_name("抢单奖励");
                            if (newestWaitTakeExpOrd != null) {
                                TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(newestWaitTakeExpOrd.getAgent_id());
                                tdHtExpressOrderDto.setAgent_id(newestWaitTakeExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAgent_id(newestWaitTakeExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAfchg_front_amount(tdHtAgentDto.getAgent_balance());
                                tdHtAgentFoundChangeDto.setAfchg_back_amount(tdHtAgentDto.getAgent_balance().add(courierDto.getMore_single_bonus()));
                            } else {
                                TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(memberPayExpOrd.getAgent_id());
                                tdHtExpressOrderDto.setAgent_id(memberPayExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAgent_id(memberPayExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAfchg_front_amount(tdHtAgentDto.getAgent_balance());
                                tdHtAgentFoundChangeDto.setAfchg_back_amount(tdHtAgentDto.getAgent_balance().add(courierDto.getMore_single_bonus()));
                            }
                            tdHtExpressOrderDto.setExp_ord_state(2);
                        } else {
                            tdHtExpressOrderDto.setAgent_id(0);
                            tdHtExpressOrderDto.setExp_ord_state(1);
                        }
                        tdHtExpressOrderDto.setExp_ord_time(now);
                        tdHtExpressOrderDto.setExp_ord_taking_time(now);
                        tdHtExpressOrderDto.setCollect_time(now);
                        tdHtExpressOrderDto.setPay_time(now);
                        tdHtExpressOrderDto.setStorge_time(now);
                        tdHtExpressOrderDto.setExp_ord_size(1);
                        tdHtExpressOrderDto.setExp_ord_weight(tdHtExpressOrderCollectDto.getExp_ord_clt_height());
                        tdHtExpressOrderDto.setExp_ord_number(CommonUtil.getOrderNub());
                        tdHtExpressOrderDto.setExp_ord_pay_amount(0);
                        tdHtExpressOrderDto.setExp_ord_amount(tdHtExpressOrderCollectDto.getExpress_price().doubleValue());
                        tdHtExpressOrderDto.setTotal_amount(tdHtExpressOrderCollectDto.getTotal_amount());
                        tdHtExpressOrderDto.setDiscount_amount(tdHtExpressOrderCollectDto.getDiscount_amount());
                        tdHtExpressOrderDto.setCoupon_amount(new BigDecimal(0));
                        tdHtExpressOrderDto.setCoupon_amount_type(0);
                        tdHtExpressOrderDto.setAdjusted_amount(new BigDecimal(0));
                        tdHtExpressOrderDto.setExp_ord_month(CommonUtil.getMonth());
                        tdHtExpressOrderDto.setAgent_note("");
                        if (member.getMemb_role() == 2) {
                            tdHtExpressOrderDto.setMemb_type(2);
                            tdHtExpressOrderDto.setExp_ord_gratuity(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity()).multiply(member.getMemb_discount()).doubleValue());
                        } else if (member.getMemb_role() == 1) {
                            if (member.getMemb_discount().doubleValue() < 1) {
                                tdHtExpressOrderDto.setMemb_type(3);
                                tdHtExpressOrderDto.setExp_ord_gratuity(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity()).multiply(member.getMemb_discount()).doubleValue());
                            } else {
                                tdHtExpressOrderDto.setMemb_type(1);
                            }
                        }
                        tdHtExpressOrderDto.setMemb_ivc_his_id(0);
                        tdHtExpressOrderDto.setMemb_client(client_type);
                        tdHtExpressOrderDto.setDivide_id(PointUtil.getDivideId(tdHtExpressOrderDto.getAdd_region(), tdHtExpressOrderDto.getAdd_longitude(), tdHtExpressOrderDto.getAdd_latitude()));
                        tdHtExpressOrderDto.setAdd_id_number("");
                        if(tdHtExpressOrderDto.getDevice_number() == null){
                            tdHtExpressOrderDto.setDevice_number("");
                        }
                        tdHtExpressOrderRepository.insertExpressOrder(tdHtExpressOrderDto);

                        tdHtMembAddressRepository.updateAddressSendExpressSize(tdHtExpressOrderDto.getAdd_id());

                        tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                        tdHtExpressOrderCollectDto.setAdd_longitude(tdHtExpressOrderDto.getAdd_longitude());
                        tdHtExpressOrderCollectDto.setAdd_latitude(tdHtExpressOrderDto.getAdd_latitude());
                        tdHtExpressOrderCollectDto.setExp_ord_clt_state(0);
                        tdHtExpressOrderCollectDto.setExpress_number("");
                        if (tdHtExpressOrderCollectDto.getHt_number() != null && !"".equals(tdHtExpressOrderCollectDto.getHt_number().trim())) {
                            TsHtOpenNumberDto tsHtOpenNumberDto = new TsHtOpenNumberDto();
                            tsHtOpenNumberDto.setAgent_id(0);
                            tsHtOpenNumberDto.setHt_open_state(1);
                            tsHtOpenNumberDto.setHt_number(tdHtExpressOrderCollectDto.getHt_number());
                            tsHtOpenNumberRepository.updateOpenNumberStateByOpenId(tsHtOpenNumberDto);
                        } else {
                            tdHtExpressOrderCollectDto.setHt_number("");
                        }
                        tdHtExpressOrderCollectDto.setSign_time(now);
                        tdHtExpressOrderCollectDto.setSend_time(now);
                        tdHtExpressOrderCollectDto.setDelivery_time(now);
                        tdHtExpressOrderCollectDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                        tdHtExpressOrderCollectDto.setExpress_name(express_name);
                        tdHtExpressCollectRepository.insertExpressOrderCollect(tdHtExpressOrderCollectDto);

                        tdHtAgentFoundChangeDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                        if (newestWaitTakeExpOrd != null || memberPayExpOrd != null) {
                            tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);
                        }

                        if (newestWaitTakeExpOrd != null || memberPayExpOrd != null) {
                            PushDto pushDto = new PushDto();
                            if (newestWaitTakeExpOrd != null) {
                                pushDto = tdHtAgentRepository.findAgentPushInfo(newestWaitTakeExpOrd.getAgent_id());
                            } else {
                                pushDto = tdHtAgentRepository.findAgentPushInfo(memberPayExpOrd.getAgent_id());
                            }
                            if (pushDto != null && pushDto.getPush_type() == 1) {
                                PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有新订单被追加.", "14", "esp_custom_add", pushDto.getApp_version());
                            } else if (pushDto != null && pushDto.getPush_type() == 2) {
                                PushUtil.pushIosAgent(pushDto.getPush_token(), "您有新订单被追加.", "14", "espsAddOrder.caf", pushDto.getApp_version());
                            }

                            if (member.getPush_type() == 1) {
                                PushUtil.pushAndroidMember(member.getPush_token(), "您的订单已被受理.", "2", "", member.getApp_version());
                            } else if (member.getPush_type() == 2) {
                                PushUtil.pushIosMember(member.getPush_token(), "您的订单已被受理.", "2", "default", member.getApp_version());
                            }
                        } else {
                            List<PushDto> pushDtoList = new ArrayList<>();
                            TdHtAgentDto tdHtAgentDto=new TdHtAgentDto();
                            tdHtAgentDto.setArea_id(tdHtExpressOrderDto.getAdd_city());
                            tdHtAgentDto.setAgent_type(1);
                            tdHtAgentDto.setDivide_id(tdHtExpressOrderDto.getDivide_id());
                            if (member.getMemb_role() == 2 || tdHtExpressOrderDto.getDivide_id() == 0) {
                                pushDtoList = tdHtAgentRepository.findPushRegistrationId(tdHtAgentDto);
                            }else {
                                pushDtoList = tdHtAgentRepository.findAgentPushByDivide(tdHtAgentDto);
                            }

                            for(int i = 0 ; i < pushDtoList.size() ; i++){
                                PushDto pushDto = pushDtoList.get(i);
                                String sound = "esp_custom1.wav";
                                if(pushDto.getApp_version() > 5){
                                    sound = "espsNewOrder.caf";
                                }

                                if(pushDto.getPush_type() == 1){
                                    PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有新的快件可以受理.", "1", "esp_sustom_order_come", pushDto.getApp_version());
                                }else if(pushDto.getPush_type() == 2){
                                    PushUtil.pushIosAgent(pushDto.getPush_token(), "您有新的快件可以受理.", "1", sound, pushDto.getApp_version());
                                }
                            }
                        }

                        retInfo.setTip("下单成功");
                    }
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("该区域没有开通该快递公司的寄件服务.");
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
     * @param token
     * @param expressDto
     * @return RetInfo
     * @Purpose 用户批量寄件
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo sendMultiExpress(ExpressDto expressDto, String token, int client_type, int version) {
        String logInfo = this.getClass().getName() + ":sendMultiExpress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中获取用户
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtMemberDto.getMemb_id());

            boolean verification = true;
            String errMessage = "";
            TdHtExpressOrderDto tdHtExpressOrderDto = expressDto.getExpress_order();
            if (tdHtExpressOrderDto.getAdd_name() == null || "".equals(tdHtExpressOrderDto.getAdd_name().trim())) {
                errMessage = "请输入正确的发件人姓名.";
                verification = false;
            }
            boolean isMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderDto.getAdd_mobile_phone());
            boolean isPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderDto.getAdd_mobile_phone());
            if (tdHtExpressOrderDto.getAdd_mobile_phone() == null || (!isMobile && !isPhone)) {
                errMessage = "请输入正确的发件人联系方式.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderDto.getAdd_detail_address().trim())) {
                errMessage = "请输入正确的发件人详细地址.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getExpress_id() == 0) {
                errMessage = "请选择快递公司.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getDoor_start_time().getTime() >= tdHtExpressOrderDto.getDoor_end_time().getTime()) {
                errMessage = "请选择正确的预约时间.";
                verification = false;
            }
            if(tdHtExpressOrderDto.getDoor_start_time().getTime() + 300000 < System.currentTimeMillis()){
                errMessage = "请选择正确的预约时间.";
                verification = false;
            }

            ExpressScreenUtil expressScreenUtil = new ExpressScreenUtil();
            double total_weight = 0;
            BigDecimal express_price = new BigDecimal(0);
            BigDecimal total_money = new BigDecimal(0);
            BigDecimal discount_money = new BigDecimal(0);
            List<TdHtExpressOrderCollectDto> tdHtExpressOrderCollectDtoList = expressDto.getExpress_order_collects();
            if (tdHtExpressOrderCollectDtoList.size() > 10) {
                errMessage = "批量寄件最多10条.";
                verification = false;
            } else {
                for (int i = 0; i < tdHtExpressOrderCollectDtoList.size(); i++) {
                    TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(i);
                    if (tdHtExpressOrderCollectDto.getAdd_name() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_name().trim())) {
                        errMessage = "请输入正确的收件人姓名.";
                        verification = false;
                    }
                    boolean isCollectMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
                    boolean isCollectPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
                    if (tdHtExpressOrderCollectDto.getAdd_mobile_phone() == null || (!isCollectMobile && !isCollectPhone)) {
                        errMessage = "请输入正确的收件人联系方式.";
                        verification = false;
                    }
                    if (tdHtExpressOrderCollectDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_detail_address().trim())) {
                        errMessage = "请输入正确的收件人详细地址.";
                        verification = false;
                    }

                    if (member.getMemb_role() == 2) {
                        String month = CommonUtil.getMonth();
                        TdHtMembMonthBillDto tdHtMembMonthBill = new TdHtMembMonthBillDto();
                        tdHtMembMonthBill.setMemb_id(member.getMemb_id());
                        tdHtMembMonthBill.setMemb_mon_bill_month(month);
                        TdHtMembMonthBillDto tdHtMembMonthBillDto = tdHtMembMonthBillRepository.findMemberNoPayNotMonthBill(tdHtMembMonthBill);
                        if (tdHtMembMonthBillDto != null) {
                            errMessage = "您有月结账单未支付，请先支付.";
                            verification = false;
                        }
                    }

                    if (tdHtExpressOrderCollectDto.getHt_number() != null && !"".equals(tdHtExpressOrderCollectDto.getHt_number().trim())) {
                        TsHtOpenNumberDto tsHtOpenNumberDto = tsHtOpenNumberRepository.findOpenNumberByHtNumber(tdHtExpressOrderCollectDto.getHt_number());
                        if (tsHtOpenNumberDto == null) {
                            errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]不正确.";
                            verification = false;
                            break;
                        } else {
                            if (tsHtOpenNumberDto.getHt_open_state() == 1) {
                                errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]已使用.";
                                verification = false;
                                break;
                            } else {
                                for (int j = i + 1; j < tdHtExpressOrderCollectDtoList.size(); j++) {
                                    TdHtExpressOrderCollectDto collectDtoTwo = tdHtExpressOrderCollectDtoList.get(j);
                                    if (tdHtExpressOrderCollectDto.getHt_number().equals(collectDtoTwo.getHt_number())) {
                                        errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]重复.";
                                        verification = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }else if((member.getMemb_role() == 2 && version < 6) || (member.getMemb_discount().doubleValue() < 1 && version >= 6)){
                        errMessage = "厚通单号不能为空..";
                        verification = false;
                    }

                    if (isCollectMobile) {
                        tdHtExpressOrderCollectDto.setAdd_telephone("");
                        tdHtExpressOrderCollectDto.setAdd_mobile_phone(tdHtExpressOrderCollectDto.getAdd_mobile_phone().replace("-", ""));
                    } else {
                        tdHtExpressOrderCollectDto.setAdd_telephone(tdHtExpressOrderCollectDto.getAdd_mobile_phone().replace("-", ""));
                        tdHtExpressOrderCollectDto.setAdd_mobile_phone("");
                    }
                    tdHtExpressOrderCollectDto.setExp_ord_clt_number(CommonUtil.getOrderNub());

                    total_weight = total_weight + tdHtExpressOrderCollectDto.getExp_ord_clt_height();
                    BigDecimal weight = new BigDecimal(tdHtExpressOrderCollectDto.getExp_ord_clt_height());
                    BigDecimal price = expressScreenUtil.reckonExpressPrice(tdHtExpressOrderDto.getExpress_id(), weight, tdHtExpressOrderDto.getAdd_province(), tdHtExpressOrderCollectDto.getAdd_province());

                    tdHtExpressOrderCollectDto.setTotal_amount(price);
                    if (member.getMemb_role() == 1) {
                        if (member.getMemb_discount().doubleValue() < 1) {
                            price = price.multiply(member.getMemb_discount());
                        }
                    } else if (member.getMemb_role() == 2) {
                        price = price.multiply(member.getMemb_discount());
                    }
                    tdHtExpressOrderCollectDto.setExpress_price(price.setScale(2, BigDecimal.ROUND_HALF_UP));
                    tdHtExpressOrderCollectDto.setDiscount_amount(tdHtExpressOrderCollectDto.getTotal_amount().subtract(tdHtExpressOrderCollectDto.getExpress_price()));
                    tdHtExpressOrderCollectDto.setCoupon_amount(new BigDecimal(0));
                    tdHtExpressOrderCollectDto.setCoupon_amount_type(0);
                    express_price = express_price.add(tdHtExpressOrderCollectDto.getExpress_price());
                    total_money = total_money.add(tdHtExpressOrderCollectDto.getTotal_amount());
                    discount_money = discount_money.add(tdHtExpressOrderCollectDto.getDiscount_amount());
                }
            }

            if (!verification) {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
            } else {
                retInfo.setMark("0");
                Date now = new Date();

                if (isMobile) {
                    tdHtExpressOrderDto.setAdd_telephone("");
                    tdHtExpressOrderDto.setAdd_mobile_phone(tdHtExpressOrderDto.getAdd_mobile_phone().replace("-", ""));
                } else {
                    tdHtExpressOrderDto.setAdd_telephone(tdHtExpressOrderDto.getAdd_mobile_phone().replace("-", ""));
                    tdHtExpressOrderDto.setAdd_mobile_phone("");
                }

                //获取快递公司名称
                String express_name = "";
                Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
                List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(tdHtExpressOrderDto.getAdd_region());//从字典中获取所有快递公司
                if (expressCompanyList != null) {
                    for (int e = 0; e < expressCompanyList.size(); e++) {
                        TsHtDictDto tsHtDict = expressCompanyList.get(e);
                        if (tsHtDict.getDict_id() == tdHtExpressOrderDto.getExpress_id()) {
                            express_name = tsHtDict.getCode_name();
                        }
                    }
                }

                if (!"".equals(express_name)) {
                    //筛选订单（判断是否可以寄件）
                    retInfo = expressScreenUtil.expressScreen(tdHtExpressOrderDto, tdHtExpressOrderCollectDtoList);

                    if ("0".equals(retInfo.getMark())) {
                        TdHtExpressOrderDto newestWaitTakeExpOrd = tdHtExpressOrderRepository.findNewestWaitTakeExpOrd(member.getMemb_id());
                        TdHtExpressOrderDto expressOrderDto = new TdHtExpressOrderDto();
                        expressOrderDto.setMemb_id(member.getMemb_id());
                        expressOrderDto.setPay_time(new Date(now.getTime() - 30 * 60 * 1000));
                        TdHtExpressOrderDto memberPayExpOrd = tdHtExpressOrderRepository.findMembPayExpOrd(expressOrderDto);
                        Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
                        String key = tdHtExpressOrderDto.getExpress_id() + "#" + tdHtExpressOrderDto.getAdd_region();
                        CourierDto courierDto = courierMap.get(key);

                        TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = new TdHtAgentFoundChangeDto();
                        tdHtExpressOrderDto.setMemb_id(tdHtMemberDto.getMemb_id());
                        tdHtExpressOrderDto.setExp_ord_type(courierDto.getEoa_type());
                        if (newestWaitTakeExpOrd != null || memberPayExpOrd != null) {
                            tdHtAgentFoundChangeDto.setAfchg_change_amount(courierDto.getMore_single_bonus().multiply(new BigDecimal(tdHtExpressOrderCollectDtoList.size())));
                            tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(now.getTime()));
                            tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                            tdHtAgentFoundChangeDto.setAfchg_type(1);
                            tdHtAgentFoundChangeDto.setAfchg_state(2);
                            tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub());
                            tdHtAgentFoundChangeDto.setAfchg_name("抢单奖励");
                            if (newestWaitTakeExpOrd != null) {
                                TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(newestWaitTakeExpOrd.getAgent_id());
                                tdHtExpressOrderDto.setAgent_id(newestWaitTakeExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAgent_id(newestWaitTakeExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAfchg_front_amount(tdHtAgentDto.getAgent_balance());
                                tdHtAgentFoundChangeDto.setAfchg_back_amount(tdHtAgentDto.getAgent_balance().add(tdHtAgentFoundChangeDto.getAfchg_change_amount()));
                            } else {
                                TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(memberPayExpOrd.getAgent_id());
                                tdHtExpressOrderDto.setAgent_id(memberPayExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAgent_id(memberPayExpOrd.getAgent_id());
                                tdHtAgentFoundChangeDto.setAfchg_front_amount(tdHtAgentDto.getAgent_balance());
                                tdHtAgentFoundChangeDto.setAfchg_back_amount(tdHtAgentDto.getAgent_balance().add(tdHtAgentFoundChangeDto.getAfchg_change_amount()));
                            }
                            tdHtExpressOrderDto.setExp_ord_state(2);
                        } else {
                            tdHtExpressOrderDto.setAgent_id(0);
                            tdHtExpressOrderDto.setExp_ord_state(1);
                        }
                        tdHtExpressOrderDto.setExp_ord_time(now);
                        tdHtExpressOrderDto.setExp_ord_taking_time(now);
                        tdHtExpressOrderDto.setCollect_time(now);
                        tdHtExpressOrderDto.setPay_time(now);
                        tdHtExpressOrderDto.setStorge_time(now);
                        tdHtExpressOrderDto.setExp_ord_size(tdHtExpressOrderCollectDtoList.size());
                        tdHtExpressOrderDto.setExp_ord_weight(total_weight);
                        tdHtExpressOrderDto.setExp_ord_number(CommonUtil.getOrderNub());
                        tdHtExpressOrderDto.setExp_ord_pay_amount(0);
                        tdHtExpressOrderDto.setExp_ord_amount(express_price.doubleValue());
                        tdHtExpressOrderDto.setTotal_amount(total_money);
                        tdHtExpressOrderDto.setDiscount_amount(discount_money);
                        tdHtExpressOrderDto.setCoupon_amount(new BigDecimal(0));
                        tdHtExpressOrderDto.setCoupon_amount_type(0);
                        tdHtExpressOrderDto.setAdjusted_amount(new BigDecimal(0));
                        tdHtExpressOrderDto.setExp_ord_month(CommonUtil.getMonth());
                        tdHtExpressOrderDto.setAgent_note("");
                        if (member.getMemb_role() == 2) {
                            tdHtExpressOrderDto.setMemb_type(2);
                            tdHtExpressOrderDto.setExp_ord_gratuity(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity()).multiply(member.getMemb_discount()).doubleValue());
                        } else if (member.getMemb_role() == 1) {
                            if (member.getMemb_discount().doubleValue() < 1) {
                                tdHtExpressOrderDto.setMemb_type(3);
                                tdHtExpressOrderDto.setExp_ord_gratuity(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity()).multiply(member.getMemb_discount()).doubleValue());
                            } else {
                                tdHtExpressOrderDto.setMemb_type(1);
                            }
                        }
                        tdHtExpressOrderDto.setMemb_ivc_his_id(0);
                        tdHtExpressOrderDto.setMemb_client(client_type);
                        tdHtExpressOrderDto.setDivide_id(PointUtil.getDivideId(tdHtExpressOrderDto.getAdd_region(), tdHtExpressOrderDto.getAdd_longitude(), tdHtExpressOrderDto.getAdd_latitude()));
                        tdHtExpressOrderDto.setAdd_id_number("");
                        if(tdHtExpressOrderDto.getDevice_number() == null){
                            tdHtExpressOrderDto.setDevice_number("");
                        }
                        tdHtExpressOrderRepository.insertExpressOrder(tdHtExpressOrderDto);

                        tdHtMembAddressRepository.updateAddressSendExpressSize(tdHtExpressOrderDto.getAdd_id());

                        for (int i = 0; i < tdHtExpressOrderCollectDtoList.size(); i++) {
                            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(i);
                            tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                            tdHtExpressOrderCollectDto.setAdd_longitude(tdHtExpressOrderDto.getAdd_longitude());
                            tdHtExpressOrderCollectDto.setAdd_latitude(tdHtExpressOrderDto.getAdd_latitude());
                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(0);
                            tdHtExpressOrderCollectDto.setExpress_number("");
                            if (tdHtExpressOrderCollectDto.getHt_number() != null && !"".equals(tdHtExpressOrderCollectDto.getHt_number().trim())) {
                                TsHtOpenNumberDto tsHtOpenNumberDto = new TsHtOpenNumberDto();
                                tsHtOpenNumberDto.setAgent_id(0);
                                tsHtOpenNumberDto.setHt_open_state(1);
                                tsHtOpenNumberDto.setHt_number(tdHtExpressOrderCollectDto.getHt_number());
                                tsHtOpenNumberRepository.updateOpenNumberStateByOpenId(tsHtOpenNumberDto);
                            } else {
                                tdHtExpressOrderCollectDto.setHt_number("");
                            }
                            tdHtExpressOrderCollectDto.setSign_time(now);
                            tdHtExpressOrderCollectDto.setSend_time(now);
                            tdHtExpressOrderCollectDto.setDelivery_time(now);
                            tdHtExpressOrderCollectDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                            tdHtExpressOrderCollectDto.setExpress_name(express_name);
                        }
                        tdHtExpressCollectRepository.batchInsertExpressOrderCollect(tdHtExpressOrderCollectDtoList);

                        tdHtAgentFoundChangeDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                        if (newestWaitTakeExpOrd != null || memberPayExpOrd != null) {
                            tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);
                        }

                        if (newestWaitTakeExpOrd != null || memberPayExpOrd != null) {
                            PushDto pushDto = new PushDto();
                            if (newestWaitTakeExpOrd != null) {
                                pushDto = tdHtAgentRepository.findAgentPushInfo(newestWaitTakeExpOrd.getAgent_id());
                            } else {
                                pushDto = tdHtAgentRepository.findAgentPushInfo(memberPayExpOrd.getAgent_id());
                            }
                            if (pushDto != null && pushDto.getPush_type() == 1) {
                                PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有新订单被追加.", "14", "esp_custom_add", pushDto.getApp_version());
                            } else if (pushDto != null && pushDto.getPush_type() == 2) {
                                PushUtil.pushIosAgent(pushDto.getPush_token(), "您有新订单被追加.", "14", "espsAddOrder.caf", pushDto.getApp_version());
                            }

                            if (member.getPush_type() == 1) {
                                PushUtil.pushAndroidMember(member.getPush_token(), "您的订单已被受理.", "2", "", member.getApp_version());
                            } else if (member.getPush_type() == 2) {
                                PushUtil.pushIosMember(member.getPush_token(), "您的订单已被受理.", "2", "default", member.getApp_version());
                            }
                        } else {
                            List<PushDto> pushDtoList = new ArrayList<>();
                            TdHtAgentDto tdHtAgentDto=new TdHtAgentDto();
                            tdHtAgentDto.setArea_id(tdHtExpressOrderDto.getAdd_city());
                            tdHtAgentDto.setAgent_type(1);
                            tdHtAgentDto.setDivide_id(tdHtExpressOrderDto.getDivide_id());
                            if (member.getMemb_role() == 2 || tdHtExpressOrderDto.getDivide_id() == 0) {
                                pushDtoList = tdHtAgentRepository.findPushRegistrationId(tdHtAgentDto);
                            }else {
                                pushDtoList = tdHtAgentRepository.findAgentPushByDivide(tdHtAgentDto);
                            }

                            for(int i = 0 ; i < pushDtoList.size() ; i++){
                                PushDto pushDto = pushDtoList.get(i);
                                String sound = "esp_custom1.wav";
                                if(pushDto.getApp_version() > 5){
                                    sound = "espsNewOrder.caf";
                                }

                                if(pushDto.getPush_type() == 1){
                                    PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有新的快件可以受理.", "1", "esp_sustom_order_come", pushDto.getApp_version());
                                }else if(pushDto.getPush_type() == 2){
                                    PushUtil.pushIosAgent(pushDto.getPush_token(), "您有新的快件可以受理.", "1", sound, pushDto.getApp_version());
                                }
                            }
                        }
                        retInfo.setTip("下单成功");
                    }
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("该快递公司无法承运，请选择其他快递.");
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
     * @param courierDto
     * @return RetInfo
     * @Purpose 查询用户寄件界面字典数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findSendExpInfo(CourierDto courierDto, String token, int version) {
        String logInfo = this.getClass().getName() + ":findSendExpInfo:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中获取用户
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);

            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> openRegionExpCompany = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.OPEN_REGION_EXP_COMPANY);

            //从缓存中取得字典数据
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<TsHtDictDto> expressCategory = (List<TsHtDictDto>) dataDictionary.get("express_category");
            List<TsHtDictDto> expressAsk = (List<TsHtDictDto>) dataDictionary.get("express_ask");

            List<TsHtDictDto> collect_time = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.COLLECT_TIME);
            List<TsHtDictDto> toDayTimeList = new ArrayList<TsHtDictDto>();
            for (int i = 0; i < collect_time.size(); i++) {
                TsHtDictDto tsHtDictDto = collect_time.get(i);
                if (tsHtDictDto.getCode_name().indexOf("-") > 0) {
                    if(version > 6){
                        String startTime = tsHtDictDto.getCode_name().split("-")[0];
                        if (CommonUtil.getHour(startTime) > new Date().getHours()) {
                            toDayTimeList.add(tsHtDictDto);
                        }
                    }else {
                        String endTime = tsHtDictDto.getCode_name().split("-")[1];
                        if (CommonUtil.getHour(endTime) > new Date().getHours()) {
                            toDayTimeList.add(tsHtDictDto);
                        }
                    }
                }
            }

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            TdHtMembAddressDto tdHtMembAddressDto = tdHtMembAddressRepository.findMemberDefaultAddress(tdHtMemberDto.getMemb_id());
            if (tdHtMembAddressDto != null) {
                tdHtMembAddressDto.setProvince(dicts.get("" + tdHtMembAddressDto.getAdd_province()).getCode_name());
                tdHtMembAddressDto.setCity(dicts.get("" + tdHtMembAddressDto.getAdd_city()).getCode_name());
                tdHtMembAddressDto.setRegion(dicts.get("" + tdHtMembAddressDto.getAdd_region()).getCode_name());
                courierDto.setRegion_id(tdHtMembAddressDto.getAdd_region());

                if (tdHtMembAddressDto.getAdd_mobile_phone() == null || "".equals(tdHtMembAddressDto.getAdd_mobile_phone())) {
                    tdHtMembAddressDto.setAdd_mobile_phone(tdHtMembAddressDto.getAdd_telephone());
                }

                if(tdHtMembAddressDto.getAdd_detail_address().indexOf(Constant.SENDER_ADDRESS_CONNECT) >= 0){
                    tdHtMembAddressDto.setAdd_street(tdHtMembAddressDto.getAdd_detail_address().split(Constant.SENDER_ADDRESS_CONNECT)[0]);
                }else {
                    tdHtMembAddressDto.setAdd_street("");
                }
                tdHtMembAddressDto.setAdd_detail_address(tdHtMembAddressDto.getAdd_detail_address().replace(Constant.SENDER_ADDRESS_CONNECT, ""));
            }

            Map<String, TsHtAgentWorkTimeDto> agentWorkTimeDtoMap = (Map<String, TsHtAgentWorkTimeDto>) MemcachedUtils.get(MemcachedKey.AGENT_WORK_TIME_MAP);

            Map<String, Object> workTime = new HashMap<String, Object>();
            //从字典中获取省市区数据
            Map<String, TsHtDictIndex> dictIndexMap = (Map<String, TsHtDictIndex>) MemcachedUtils.get(MemcachedKey.DICT_INDEX_MAP);
            if(courierDto.getRegion_id() > 0){
                TsHtDictIndex region = dictIndexMap.get("" + courierDto.getRegion_id());
                TsHtDictIndex city = dictIndexMap.get("" + region.getParent_id());
                TsHtDictIndex province = dictIndexMap.get("" + city.getParent_id());

                String key = province.getDict_id() + "#" + city.getDict_id() + "#" + region.getDict_id() + "#0";
                TsHtAgentWorkTimeDto tsHtAgentWorkTimeDto = agentWorkTimeDtoMap.get(key);

                if(tsHtAgentWorkTimeDto == null){
                    key = province.getDict_id() + "#" + city.getDict_id() + "#0#0";
                    tsHtAgentWorkTimeDto = agentWorkTimeDtoMap.get(key);
                }
                if(tsHtAgentWorkTimeDto == null){
                    key = province.getDict_id() + "#0#0#0";
                    tsHtAgentWorkTimeDto = agentWorkTimeDtoMap.get(key);
                }
                if(tsHtAgentWorkTimeDto == null){
                    key = "0#0#0#0";
                    tsHtAgentWorkTimeDto = agentWorkTimeDtoMap.get(key);
                }

                workTime.put("start_time", tsHtAgentWorkTimeDto.getStart_work_hour());
                workTime.put("end_time", tsHtAgentWorkTimeDto.getEnd_work_hour());
            }else {
                TsHtAgentWorkTimeDto tsHtAgentWorkTimeDto = agentWorkTimeDtoMap.get("0#0#0#0");
                workTime.put("start_time", tsHtAgentWorkTimeDto.getStart_work_hour());
                workTime.put("end_time", tsHtAgentWorkTimeDto.getEnd_work_hour());
            }

            Map<String, Object> map = new HashMap<String, Object>();
            if (courierDto.getRegion_id() == 0) {
                map.put("express_company", null);
            } else {
                map.put("express_company", expressCompany.get(courierDto.getRegion_id()));
            }
            map.put("express_category", expressCategory);
            map.put("express_ask", expressAsk);
            map.put("today_collect_time", toDayTimeList);
            map.put("tomorrow_collect_time", collect_time);
            map.put("default_address", tdHtMembAddressDto);
            map.put("work_time", workTime);

            retInfo.setMark("0");
            retInfo.setTip("字典数据获取成功.");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @param expressDto
     * @return RetInfo
     * @Purpose 大客户寄件
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo bigClientsSendExp(ExpressDto expressDto, String token, int client_type) {
        String logInfo = this.getClass().getName() + ":bigClientsSendExp:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中获取用户
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtMemberDto.getMemb_id());

            boolean verification = true;
            String errMessage = "";
            TdHtExpressOrderDto tdHtExpressOrderDto = expressDto.getExpress_order();
            if (tdHtExpressOrderDto.getAdd_name() == null || "".equals(tdHtExpressOrderDto.getAdd_name().trim())) {
                errMessage = "请输入正确的发件人姓名.";
                verification = false;
            }
            boolean isMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderDto.getAdd_mobile_phone());
            boolean isPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderDto.getAdd_mobile_phone());
            if (tdHtExpressOrderDto.getAdd_mobile_phone() == null || (!isMobile && !isPhone)) {
                errMessage = "请输入正确的发件人联系方式.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderDto.getAdd_detail_address().trim())) {
                errMessage = "请输入正确的发件人详细地址.";
                verification = false;
            }
            if (tdHtExpressOrderDto.getExpress_id() == 0) {
                errMessage = "请选择快递公司.";
                verification = false;
            }

            ExpressScreenUtil expressScreenUtil = new ExpressScreenUtil();
            double total_weight = 0;
            BigDecimal express_price = new BigDecimal(0);
            BigDecimal total_money = new BigDecimal(0);
            BigDecimal discount_money = new BigDecimal(0);
            List<TdHtExpressOrderCollectDto> tdHtExpressOrderCollectDtoList = expressDto.getExpress_order_collects();
            for (int i = 0; i < tdHtExpressOrderCollectDtoList.size(); i++) {
                TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(i);
                if (tdHtExpressOrderCollectDto.getAdd_name() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_name().trim())) {
                    errMessage = "请输入正确的收件人姓名.";
                    verification = false;
                }
                boolean isCollectMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
                boolean isCollectPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
                if (tdHtExpressOrderCollectDto.getAdd_mobile_phone() == null || (!isCollectMobile && !isCollectPhone)) {
                    errMessage = "请输入正确的收件人联系方式.";
                    verification = false;
                }
                if (tdHtExpressOrderCollectDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_detail_address().trim())) {
                    errMessage = "请输入正确的收件人详细地址.";
                    verification = false;
                }

                if (member.getMemb_role() == 3) {
                    if (tdHtExpressOrderCollectDto.getHt_number() == null || "".equals(tdHtExpressOrderCollectDto.getHt_number())) {
                        errMessage = "订单号不能为空.";
                        verification = false;
                        break;
                    } else {
                        TsHtOpenNumberDto tsHtOpenNumberDto = tsHtOpenNumberRepository.findOpenNumberByHtNumber(tdHtExpressOrderCollectDto.getHt_number());
                        if (tsHtOpenNumberDto == null) {
                            errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]不正确.";
                            verification = false;
                            break;
                        } else {
                            if (tsHtOpenNumberDto.getHt_open_state() == 1) {
                                errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]已使用.";
                                verification = false;
                                break;
                            } else {
                                for (int j = i + 1; j < tdHtExpressOrderCollectDtoList.size(); j++) {
                                    TdHtExpressOrderCollectDto collectDtoTwo = tdHtExpressOrderCollectDtoList.get(j);
                                    if (tdHtExpressOrderCollectDto.getHt_number().equals(collectDtoTwo.getHt_number())) {
                                        errMessage = "订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]重复.";
                                        verification = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    errMessage = "您不是大客户.";
                    verification = false;
                    break;
                }

                if (isCollectMobile) {
                    tdHtExpressOrderCollectDto.setAdd_telephone("");
                    tdHtExpressOrderCollectDto.setAdd_mobile_phone(tdHtExpressOrderCollectDto.getAdd_mobile_phone().replace("-", ""));
                } else {
                    tdHtExpressOrderCollectDto.setAdd_telephone(tdHtExpressOrderCollectDto.getAdd_mobile_phone().replace("-", ""));
                    tdHtExpressOrderCollectDto.setAdd_mobile_phone("");
                }
                tdHtExpressOrderCollectDto.setExp_ord_clt_number(CommonUtil.getOrderNub());

                total_weight = total_weight + tdHtExpressOrderCollectDto.getExp_ord_clt_height();
                BigDecimal weight = new BigDecimal(tdHtExpressOrderCollectDto.getExp_ord_clt_height());
                BigDecimal price = expressScreenUtil.reckonExpressPrice(tdHtExpressOrderDto.getExpress_id(), weight, tdHtExpressOrderDto.getAdd_province(), tdHtExpressOrderCollectDto.getAdd_province());

                tdHtExpressOrderCollectDto.setTotal_amount(price);
                if (member.getMemb_discount().doubleValue() < 1) {
                    price = price.multiply(member.getMemb_discount());
                }
                tdHtExpressOrderCollectDto.setExpress_price(price.setScale(2, BigDecimal.ROUND_HALF_UP));
                tdHtExpressOrderCollectDto.setDiscount_amount(tdHtExpressOrderCollectDto.getTotal_amount().subtract(tdHtExpressOrderCollectDto.getExpress_price()));
                tdHtExpressOrderCollectDto.setCoupon_amount(new BigDecimal(0));
                tdHtExpressOrderCollectDto.setCoupon_amount_type(0);
                express_price = express_price.add(tdHtExpressOrderCollectDto.getExpress_price());
                total_money = total_money.add(tdHtExpressOrderCollectDto.getTotal_amount());
                discount_money = discount_money.add(tdHtExpressOrderCollectDto.getDiscount_amount());
            }

            if (!verification) {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
            } else {
                retInfo.setMark("0");
                Date now = new Date();

                if (isMobile) {
                    tdHtExpressOrderDto.setAdd_telephone("");
                    tdHtExpressOrderDto.setAdd_mobile_phone(tdHtExpressOrderDto.getAdd_mobile_phone().replace("-", ""));
                } else {
                    tdHtExpressOrderDto.setAdd_telephone(tdHtExpressOrderDto.getAdd_mobile_phone().replace("-", ""));
                    tdHtExpressOrderDto.setAdd_mobile_phone("");
                }

                //获取快递公司名称
                String express_name = "";
                Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
                List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(tdHtExpressOrderDto.getAdd_region());//从字典中获取所有快递公司
                if (expressCompanyList != null) {
                    for (int e = 0; e < expressCompanyList.size(); e++) {
                        TsHtDictDto tsHtDict = expressCompanyList.get(e);
                        if (tsHtDict.getDict_id() == tdHtExpressOrderDto.getExpress_id()) {
                            express_name = tsHtDict.getCode_name();
                        }
                    }
                }

                if (!"".equals(express_name)) {
                    //筛选订单（判断是否可以寄件）
                    retInfo = expressScreenUtil.expressScreen(tdHtExpressOrderDto, tdHtExpressOrderCollectDtoList);

                    if ("0".equals(retInfo.getMark())) {
                        if (tdHtExpressOrderDto.getAdd_id() == 0) {
                            TdHtMembAddress tdHtMembAddress = new TdHtMembAddress();
                            tdHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
                            tdHtMembAddress.setAdd_name(tdHtExpressOrderDto.getAdd_name());
                            tdHtMembAddress.setAdd_mobile_phone(tdHtExpressOrderDto.getAdd_mobile_phone());
                            tdHtMembAddress.setAdd_telephone(tdHtExpressOrderDto.getAdd_telephone());
                            tdHtMembAddress.setAdd_province(tdHtExpressOrderDto.getAdd_province());
                            tdHtMembAddress.setAdd_city(tdHtExpressOrderDto.getAdd_city());
                            tdHtMembAddress.setAdd_region(tdHtExpressOrderDto.getAdd_region());
                            tdHtMembAddress.setAdd_id_number("");
                            tdHtMembAddress.setAdd_detail_address(tdHtExpressOrderDto.getAdd_detail_address());
                            if (tdHtExpressOrderDto.getAdd_longitude() == 0.00 && tdHtExpressOrderDto.getAdd_latitude() == 0.00) {
                                BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                                //从字典中获取省市区
                                Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
                                List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) dataDictionary.get(Constant.PROVINCE);
                                Map cityMap = (Map) dataDictionary.get(Constant.CITY);
                                Map areaMap = (Map) dataDictionary.get(Constant.AREA);
                                String address = "";
                                for (int p = 0; p < provinceDtoList.size(); p++) {
                                    ProvinceDto provinceDto = provinceDtoList.get(p);
                                    if (provinceDto.getDict_id() == tdHtMembAddress.getAdd_province()) {
                                        address = address + provinceDto.getProvince_name();
                                    }
                                }
                                List<CityDto> cityDtoList = (List<CityDto>) cityMap.get(tdHtMembAddress.getAdd_province());
                                for (int c = 0; c < cityDtoList.size(); c++) {
                                    CityDto cityDto = cityDtoList.get(c);
                                    if (cityDto.getDict_id() == tdHtMembAddress.getAdd_city()) {
                                        address = address + cityDto.getCity_name();
                                    }
                                }
                                List<AreaDto> areaDtoList = (List<AreaDto>) areaMap.get(tdHtMembAddress.getAdd_city());
                                for (int a = 0; a < areaDtoList.size(); a++) {
                                    AreaDto areaDto = areaDtoList.get(a);
                                    if (areaDto.getDict_id() == tdHtMembAddress.getAdd_region()) {
                                        address = address + areaDto.getArea_name();
                                    }
                                }
                                LocationDto location = baiduMapUtil.changeAddress(address + tdHtMembAddress.getAdd_detail_address());
                                if (location != null) {
                                    tdHtMembAddress.setAdd_longitude(location.getLongitude());
                                    tdHtMembAddress.setAdd_latitude(location.getLatitude());
                                } else {
                                    tdHtMembAddress.setAdd_longitude(new BigDecimal(0));
                                    tdHtMembAddress.setAdd_latitude(new BigDecimal(0));
                                }
                            }
                            tdHtMembAddress.setAdd_state(0);
                            tdHtMembAddress.setAdd_express_size(1);
                            tdHtMembAddress.setAdd_is_default(1);
                            tdHtMembAddress.setAdd_label(0);
                            tdHtMembAddress.setAdd_type(1);
                            tdHtMembAddressRepository.insertAddress(tdHtMembAddress);
                            tdHtExpressOrderDto.setAdd_id(tdHtMembAddress.getAdd_id());
                            tdHtExpressOrderDto.setAdd_latitude(tdHtMembAddress.getAdd_latitude().doubleValue());
                            tdHtExpressOrderDto.setAdd_longitude(tdHtMembAddress.getAdd_longitude().doubleValue());
                        } else {
                            tdHtMembAddressRepository.updateAddressSendExpressSize(tdHtExpressOrderDto.getAdd_id());
                        }

                        tdHtExpressOrderDto.setMemb_id(tdHtMemberDto.getMemb_id());
                        tdHtExpressOrderDto.setAgent_id(0);
                        tdHtExpressOrderDto.setExp_ord_state(1);
                        tdHtExpressOrderDto.setExp_ord_time(now);
                        tdHtExpressOrderDto.setExp_ord_taking_time(now);
                        tdHtExpressOrderDto.setCollect_time(now);
                        tdHtExpressOrderDto.setPay_time(now);
                        tdHtExpressOrderDto.setStorge_time(now);
                        tdHtExpressOrderDto.setExp_ord_size(tdHtExpressOrderCollectDtoList.size());
                        tdHtExpressOrderDto.setExp_ord_weight(total_weight);
                        tdHtExpressOrderDto.setExp_ord_number(CommonUtil.getOrderNub());
                        tdHtExpressOrderDto.setExp_ord_pay_amount(0);
                        tdHtExpressOrderDto.setExp_ord_amount(express_price.doubleValue());
                        tdHtExpressOrderDto.setTotal_amount(total_money);
                        tdHtExpressOrderDto.setDiscount_amount(discount_money);
                        tdHtExpressOrderDto.setCoupon_amount(new BigDecimal(0));
                        tdHtExpressOrderDto.setCoupon_amount_type(0);
                        tdHtExpressOrderDto.setAdjusted_amount(new BigDecimal(0));
                        tdHtExpressOrderDto.setExp_ord_month(CommonUtil.getMonth());
                        tdHtExpressOrderDto.setExp_ord_demand("");
                        tdHtExpressOrderDto.setAgent_note("");
                        tdHtExpressOrderDto.setAdd_id_number("");
                        if (member.getMemb_discount().doubleValue() < 1) {
                            tdHtExpressOrderDto.setMemb_type(4);
                            tdHtExpressOrderDto.setExp_ord_gratuity(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity()).multiply(member.getMemb_discount()).doubleValue());
                        } else {
                            tdHtExpressOrderDto.setMemb_type(4);
                        }
                        tdHtExpressOrderDto.setMemb_ivc_his_id(0);
                        tdHtExpressOrderDto.setMemb_client(client_type);
                        tdHtExpressOrderDto.setDivide_id(PointUtil.getDivideId(tdHtExpressOrderDto.getAdd_region(), tdHtExpressOrderDto.getAdd_longitude(), tdHtExpressOrderDto.getAdd_latitude()));
                        tdHtExpressOrderDto.setAdd_id_number("");
                        tdHtExpressOrderRepository.insertExpressOrder(tdHtExpressOrderDto);

                        for (int i = 0; i < tdHtExpressOrderCollectDtoList.size(); i++) {
                            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(i);
                            tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                            tdHtExpressOrderCollectDto.setAdd_longitude(tdHtExpressOrderDto.getAdd_longitude());
                            tdHtExpressOrderCollectDto.setAdd_latitude(tdHtExpressOrderDto.getAdd_latitude());
                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(0);
                            tdHtExpressOrderCollectDto.setExpress_number("");
                            if (member.getMemb_role() == 1) {
                                tdHtExpressOrderCollectDto.setHt_number("");
                            } else {
                                TsHtOpenNumberDto tsHtOpenNumberDto = new TsHtOpenNumberDto();
                                tsHtOpenNumberDto.setAgent_id(0);
                                tsHtOpenNumberDto.setHt_open_state(1);
                                tsHtOpenNumberDto.setHt_number(tdHtExpressOrderCollectDto.getHt_number());
                                tsHtOpenNumberRepository.updateOpenNumberStateByOpenId(tsHtOpenNumberDto);
                            }
                            tdHtExpressOrderCollectDto.setSign_time(now);
                            tdHtExpressOrderCollectDto.setSend_time(now);
                            tdHtExpressOrderCollectDto.setDelivery_time(now);
                            tdHtExpressOrderCollectDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                            tdHtExpressOrderCollectDto.setExpress_name(express_name);
                        }
                        tdHtExpressCollectRepository.batchInsertExpressOrderCollect(tdHtExpressOrderCollectDtoList);
                        retInfo.setMark("0");
                        retInfo.setTip("下单成功");
                    }
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("请选择快递公司.");
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
}
