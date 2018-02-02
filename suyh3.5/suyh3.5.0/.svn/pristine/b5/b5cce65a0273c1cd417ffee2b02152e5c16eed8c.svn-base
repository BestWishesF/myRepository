package cn.hotol.app.service.three.invoice;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.invoice.TdHtMembInvoiceDto;
import cn.hotol.app.bean.dto.invoice.history.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.bean.dto.location.LocationDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-15.
 */

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static Logger logger = Logger.getLogger(InvoiceServiceImpl.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtMembInvoiceRepository tdHtMembInvoiceRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtMembInvoiceHistoryRepository tdHtMembInvoiceHistoryRepository;
    @Resource
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;

    /**
     * @param token
     * @param tdHtMembInvoiceDto
     * @return RetInfo
     * @Purpose 用户申请开票
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo saveMembInvoice(TdHtMembInvoiceDto tdHtMembInvoiceDto, String token, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":saveMembInvoice:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());

            Map<String, Object> expOrdMap = tdHtExpressOrderRepository.findSumPayAmountByOrderList(tdHtMembInvoiceDto.getExp_ord_ids());
            if (expOrdMap.get("exp_ord_pay_amount") == null || expOrdMap.get("memb_ivc_his_id") == null) {
                retInfo.setMark("1");
                retInfo.setTip("订单不正确.");
            } else if (Integer.parseInt(expOrdMap.get("memb_ivc_his_id").toString()) > 0) {
                retInfo.setMark("1");
                retInfo.setTip("订单已生成，请前往历史记录支付.");
            } else {
                double money = Double.parseDouble(expOrdMap.get("exp_ord_pay_amount").toString());
                boolean verification = false;
                String errMessage = "";

                //从字典中获取省市区数据
                Map<String, TsHtDictIndex> dicts = (Map<String, TsHtDictIndex>) MemcachedUtils.get(MemcachedKey.DICT_INDEX_MAP);
                TsHtDictIndex province = dicts.get("" + tdHtMembInvoiceDto.getAdd_province());
                TsHtDictIndex city = dicts.get("" + tdHtMembInvoiceDto.getAdd_city());
                TsHtDictIndex region = dicts.get("" + tdHtMembInvoiceDto.getAdd_region());

                //判断省市区数据的正确性
                if(province == null){
                    errMessage = "请选择正确的省市区.";
                    verification = true;
                }else {
                    if(city == null){
                        errMessage = "请选择正确的省市区.";
                        verification = true;
                    }else {
                        if(region == null){
                            errMessage = "请选择正确的省市区.";
                            verification = true;
                        }
                        if(city.getParent_id() != province.getDict_id()){
                            errMessage = "请选择正确的省市区.";
                            verification = true;
                        }
                        if(region != null && region.getParent_id() != city.getDict_id()){
                            errMessage = "请选择正确的省市区.";
                            verification = true;
                        }
                    }
                }

                Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
                if (!verification) {
                    if (tdHtMembInvoiceDto.getAdd_longitude() == 0 && tdHtMembInvoiceDto.getAdd_latitude() == 0) {
                        BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                        LocationDto location = baiduMapUtil.changeAddress(province.getCode_name() + city.getCode_name() + region.getCode_name() + tdHtMembInvoiceDto.getAdd_detail_address());
                        if (location != null) {
                            tdHtMembInvoiceDto.setAdd_longitude(location.getLongitude().doubleValue());
                            tdHtMembInvoiceDto.setAdd_latitude(location.getLatitude().doubleValue());
                        }
                    }
                    tdHtMembInvoiceDto.setMemb_id(tdHtMemberDto.getMemb_id());

                    if(tdHtMembInvoiceDto.getMemb_taxpayer_number() == null){
                        tdHtMembInvoiceDto.setMemb_taxpayer_number("");
                    }
                    if(tdHtMembInvoiceDto.getMemb_company_address() == null){
                        tdHtMembInvoiceDto.setMemb_company_address("");
                    }
                    if(tdHtMembInvoiceDto.getMemb_company_phone() == null){
                        tdHtMembInvoiceDto.setMemb_company_phone("");
                    }
                    if(tdHtMembInvoiceDto.getMemb_company_bank() == null){
                        tdHtMembInvoiceDto.setMemb_company_bank("");
                    }
                    if(tdHtMembInvoiceDto.getMemb_company_number() == null){
                        tdHtMembInvoiceDto.setMemb_company_number("");
                    }
                    if(tdHtMembInvoiceDto.getMemb_ivc_remark() == null){
                        tdHtMembInvoiceDto.setMemb_ivc_remark("");
                    }

                    tdHtMembInvoiceRepository.insertMembInvoice(tdHtMembInvoiceDto);

                    long now = System.currentTimeMillis();
                    TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto = new TdHtMembInvoiceHistoryDto();
                    tdHtMembInvoiceHistoryDto.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtMembInvoiceHistoryDto.setMemb_inc_amount(new BigDecimal(money));
                    tdHtMembInvoiceHistoryDto.setMemb_ivc_express_id(0);
                    tdHtMembInvoiceHistoryDto.setMemb_ivc_express_number("");
                    String free_amount = dataConfigMap.get(Constant.MEMB_INVOICE_FREE_AMOUNT).toString();
                    if (money < Double.parseDouble(free_amount)) {
                        tdHtMembInvoiceHistoryDto.setState(0);
                    } else {
                        tdHtMembInvoiceHistoryDto.setState(1);

                    }
                    tdHtMembInvoiceHistoryDto.setMemb_ivc_his_time(new Timestamp(now));
                    tdHtMembInvoiceHistoryDto.setMemb_ivc_his_pay_time(new Timestamp(now));
                    tdHtMembInvoiceHistoryDto.setMemb_ivc_his_audit_time(new Timestamp(now));
                    tdHtMembInvoiceHistoryDto.setMemb_ivc_id(tdHtMembInvoiceDto.getMemb_ivc_id());
                    tdHtMembInvoiceHistoryRepository.insertMembInvoiceHis(tdHtMembInvoiceHistoryDto);

                    Map<String, Object> invoiceOrderMap = new HashMap<String, Object>();
                    invoiceOrderMap.put("memb_ivc_his_id", tdHtMembInvoiceHistoryDto.getMemb_ivc_his_id());
                    invoiceOrderMap.put("exp_ord_ids", tdHtMembInvoiceDto.getExp_ord_ids());
                    tdHtExpressOrderRepository.updateInvoiceHisOrder(invoiceOrderMap);
                    retInfo.setMark("0");
                    retInfo.setTip("开票申请成功，请等待审核.");

                    if (money < Double.parseDouble(free_amount)) {
                        TdHtMembFoundChangeDto tdHtMembFoundChangeDto = new TdHtMembFoundChangeDto();
                        tdHtMembFoundChangeDto.setExp_ord_id(0);
                        tdHtMembFoundChangeDto.setMemb_id(tdHtMemberDto.getMemb_id());
                        tdHtMembFoundChangeDto.setMfchg_time(new Timestamp(now));
                        tdHtMembFoundChangeDto.setMfchg_month(CommonUtil.getMonth());
                        tdHtMembFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub());
                        tdHtMembFoundChangeDto.setMfchg_name("开票邮费支付");
                        tdHtMembFoundChangeDto.setMemb_mon_bill_id(0);
                        tdHtMembFoundChangeDto.setMemb_ivc_his_id(tdHtMembInvoiceHistoryDto.getMemb_ivc_his_id());
                        tdHtMembFoundChangeDto.setMemb_goods_id(0);

                        //判断是否需要第三方支付(3：钱包支付 ；1、2：需要进行第三方支付)
                        String payMoney = dataConfigMap.get(Constant.MEMB_INVOICE_POSTAGE).toString();
                        if (tdHtMembInvoiceDto.getPay_type() == 3) {
                            if (tdHtMemberDto.getMemb_balance().compareTo(new BigDecimal(payMoney)) < 0) {
                                retInfo.setMark("1");
                                retInfo.setTip("余额不足.");
                            } else {
                                tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                                tdHtMembFoundChangeDto.setMfchg_change_amount(new BigDecimal(payMoney));
                                tdHtMembFoundChangeDto.setMfchg_back_amount((tdHtMemberDto.getMemb_balance().subtract(new BigDecimal(payMoney))).setScale(2, BigDecimal.ROUND_HALF_UP));
                                tdHtMembFoundChangeDto.setMfchg_channel(3);
                                tdHtMembFoundChangeDto.setMfchg_state(0);
                                tdHtMembFoundChangeDto.setMfchg_type(2);
                                tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                                tdHtMemberDto.setMemb_balance(tdHtMembFoundChangeDto.getMfchg_back_amount());
                                tdHtMemberRepository.updateMemberBanlanceAndScore(tdHtMemberDto);

                                tdHtMembInvoiceHistoryDto.setState(1);
                                tdHtMembInvoiceHistoryRepository.updateMembInvoiceHisById(tdHtMembInvoiceHistoryDto);

                                MemcachedUtils.replace(token, tdHtMemberDto, new Date(20 * 24 * 60 * 60 * 1000));
                            }
                        } else {
                            tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_back_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_type(3);
                            tdHtMembFoundChangeDto.setMfchg_state(1);
                            tdHtMembFoundChangeDto.setMfchg_change_amount(new BigDecimal(payMoney));
                            tdHtMembFoundChangeDto.setMfchg_channel(tdHtMembInvoiceDto.getPay_type());
                            tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                            Pingpp.apiKey = Constant.Live_Secret_Key;
                            Map<String, Object> chargeParams = new HashMap<String, Object>();
                            chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());
                            chargeParams.put("amount", new BigDecimal(payMoney).multiply(new BigDecimal(100)).intValue());
                            Map<String, String> app = new HashMap<String, String>();
                            app.put("id", Constant.MEMBER_APP_ID);
                            chargeParams.put("app", app);
                            if (tdHtMembFoundChangeDto.getMfchg_channel() == 1) {
                                chargeParams.put("channel", "wx");  //微信wx
                            }
                            if (tdHtMembFoundChangeDto.getMfchg_channel() == 2) {
                                chargeParams.put("channel", "alipay");  //支付宝alipay
                            }
                            chargeParams.put("currency", "cny");
                            chargeParams.put("client_ip", Ip.getIpAddr(request));
                            chargeParams.put("subject", "发票邮费支付");
                            chargeParams.put("body", "发票邮费支付");
                            Charge charge = Charge.create(chargeParams);

                            Map<String, Object> map = new HashMap<>();
                            map.put("charge", charge);

                            retInfo.setMark("0");
                            retInfo.setTip("下单成功");
                            retInfo.setObj(map);
                        }
                    }
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip(errMessage);
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
     * @param memb_ivc_id
     * @return RetInfo
     * @Purpose 通过id查询用户开票详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findMembIvcById(int memb_ivc_id) {
        String logInfo = this.getClass().getName() + ":findMembIvcById:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMembInvoiceDto tdHtMembInvoiceDto = tdHtMembInvoiceRepository.findMembIvcById(memb_ivc_id);
            TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto = tdHtMembInvoiceHistoryRepository.findMembIvcHisByIvcId(memb_ivc_id);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            Map<String, Object> map = new HashMap<String, Object>();
            TsHtDictDto tsHtDictDto = dicts.get("" + tdHtMembInvoiceHistoryDto.getMemb_ivc_express_id());
            if (tsHtDictDto == null) {
                map.put("express_name", "");
            } else {
                map.put("express_name", tsHtDictDto.getCode_name());
            }
            map.put("memb_ivc_express_number", tdHtMembInvoiceHistoryDto.getMemb_ivc_express_number());
            map.put("add_name", tdHtMembInvoiceDto.getAdd_name());
            map.put("add_mobile_phone", tdHtMembInvoiceDto.getAdd_mobile_phone());
            String address = dicts.get("" + tdHtMembInvoiceDto.getAdd_province()).getCode_name() + dicts.get("" + tdHtMembInvoiceDto.getAdd_city()).getCode_name() +
                    dicts.get("" + tdHtMembInvoiceDto.getAdd_region()).getCode_name() + tdHtMembInvoiceDto.getAdd_detail_address();
            map.put("add_detail_address", address);
            map.put("memb_company", tdHtMembInvoiceDto.getMemb_company());
            map.put("memb_ivc_name", "快递服务费");
            map.put("memb_inc_amount", tdHtMembInvoiceHistoryDto.getMemb_inc_amount());
            map.put("memb_ivc_his_time", tdHtMembInvoiceHistoryDto.getMemb_ivc_his_time());
            map.put("memb_taxpayer_number", tdHtMembInvoiceDto.getMemb_taxpayer_number());
            map.put("memb_company_bank", tdHtMembInvoiceDto.getMemb_company_bank());
            map.put("memb_company_number", tdHtMembInvoiceDto.getMemb_company_number());
            map.put("memb_company_phone", tdHtMembInvoiceDto.getMemb_company_phone());
            map.put("memb_company_address", tdHtMembInvoiceDto.getMemb_company_address());

            retInfo.setMark("0");
            retInfo.setTip("数据查询成功.");
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
     * @return RetInfo
     * @Purpose 通过用户最近一次开票详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findLatelyIvcInfo(String token) {
        String logInfo = this.getClass().getName() + ":findLatelyIvcInfo:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            TdHtMembInvoiceDto tdHtMembInvoiceDto = tdHtMembInvoiceRepository.findLatelyIvcInfo(member.getMemb_id());
            if (tdHtMembInvoiceDto == null) {
                retInfo.setTip("没有开票记录.");
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("add_name", tdHtMembInvoiceDto.getAdd_name());
                map.put("add_mobile_phone", tdHtMembInvoiceDto.getAdd_mobile_phone());
                map.put("add_province", tdHtMembInvoiceDto.getAdd_province());
                map.put("add_city", tdHtMembInvoiceDto.getAdd_city());
                map.put("add_region", tdHtMembInvoiceDto.getAdd_region());
                map.put("add_detail_address", tdHtMembInvoiceDto.getAdd_detail_address());
                map.put("memb_company", tdHtMembInvoiceDto.getMemb_company());
                map.put("memb_taxpayer_number", tdHtMembInvoiceDto.getMemb_taxpayer_number());
                map.put("memb_company_bank", tdHtMembInvoiceDto.getMemb_company_bank());
                map.put("memb_company_number", tdHtMembInvoiceDto.getMemb_company_number());
                map.put("memb_company_phone", tdHtMembInvoiceDto.getMemb_company_phone());
                map.put("memb_company_address", tdHtMembInvoiceDto.getMemb_company_address());
                map.put("province", dicts.get("" + tdHtMembInvoiceDto.getAdd_province()).getCode_name());
                map.put("city", dicts.get("" + tdHtMembInvoiceDto.getAdd_city()).getCode_name());
                map.put("region", dicts.get("" + tdHtMembInvoiceDto.getAdd_region()).getCode_name());

                retInfo.setTip("数据查询成功.");
                retInfo.setObj(map);
            }
            retInfo.setMark("0");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
