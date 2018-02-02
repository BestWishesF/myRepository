package cn.hotol.app.service.three.wechatpay;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.TdHtMembScore;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.score.TdHtMembScoreTaskDto;
import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;
import cn.hotol.app.bean.dto.thirdlogin.TdHtMembThirdLoginDto;
import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import cn.hotol.app.bean.dto.wechatpay.WxRequestPayDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by LuBin
 * Date 2017-01-04.
 */
@Service
public class WeChatPayServiceImpl implements WeChatPayService {

    private static Logger logger = Logger.getLogger(WeChatPayServiceImpl.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtMembThirdLoginRepository tdHtMembThirdLoginRepository;
    @Resource
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;
    @Resource
    private TdHtCouponRepository tdHtCouponRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    @Resource
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    @Resource
    private TdHtMembScoreTaskRepository tdHtMembScoreTaskRepository;
    @Resource
    private TdHtMembScoreRepository tdHtMembScoreRepository;

    /**
     * @param expressOrderPayDto
     * @param token
     * @param ip
     * @return RetInfo
     * @Purpose 微信用户支付订单
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public synchronized RetInfo weChatOrderPay(ExpressOrderPayDto expressOrderPayDto, String ip, String token) {
        String logInfo = this.getClass().getName() + ":weChatOrderPay:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中获取用户
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto memberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            if(memberDto.getMemb_role()==1&&memberDto.getMemb_discount().doubleValue()<1&&expressOrderPayDto.getCoupon_id()>0){
                retInfo.setMark("1");
                retInfo.setTip("您是折扣用户，无法使用优惠券.");
            }else{
                TdHtMembThirdLoginDto membThirdLoginDto = new TdHtMembThirdLoginDto();
                membThirdLoginDto.setMemb_id(member.getMemb_id());
                membThirdLoginDto.setThird_id(expressOrderPayDto.getWe_chat_code());
                TdHtMembThirdLoginDto memberThirdLoginDto = tdHtMembThirdLoginRepository.findMembThirdByBean(membThirdLoginDto);
                TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(expressOrderPayDto.getExp_ord_id());

                if(tdHtExpressOrderDto.getExp_ord_state()==3){
                    TdHtCoupon tdHtCoupon = new TdHtCoupon();

                    Map<String, TsHtThirdDto> map = (Map<String, TsHtThirdDto>) MemcachedUtils.get(MemcachedKey.THIRD_PARTY_MAP);
                    TsHtThirdDto tsHtThirdDto = map.get("" + expressOrderPayDto.getWe_chat_code());

                    long nowDate = System.currentTimeMillis();
                    BigDecimal payMoney = new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    if (tdHtExpressOrderDto.getDoor_end_time().getTime() >= tdHtExpressOrderDto.getCollect_time().getTime()) {
                        payMoney = payMoney.add(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    boolean couponUse = true;
                    //判断是否使用优惠券
                    if (expressOrderPayDto.getCoupon_id() > 0) {
                        tdHtCouponRepository.updateCouponByOrder(expressOrderPayDto.getExp_ord_id());
                        tdHtCoupon = tdHtCouponRepository.findCouponById(expressOrderPayDto.getCoupon_id());
                        if (tdHtCoupon == null) {
                            couponUse = false;
                            retInfo.setMark("1");
                            retInfo.setTip("优惠券无法使用.");
                        } else {
                            if(tdHtCoupon.getExpress_id()>0&&tdHtCoupon.getExpress_id()!=tdHtExpressOrderDto.getExpress_id()){
                                couponUse = false;
                                retInfo.setMark("1");
                                retInfo.setTip("该优惠券无法优惠该快递公司.");
                            }else if(tdHtCoupon.getRegion_id()>0&&tdHtCoupon.getRegion_id()!=tdHtExpressOrderDto.getAdd_region()){
                                couponUse = false;
                                retInfo.setMark("1");
                                retInfo.setTip("该优惠券无法在该地区使用.");
                            }else if(tdHtCoupon.getMemb_type()>0&&tdHtCoupon.getMemb_type()!=1){
                                couponUse = false;
                                retInfo.setMark("1");
                                retInfo.setTip("该优惠券无法在微信端使用.");
                            }else if(tdHtCoupon.getLimit_exp_ord_id() > 0 && tdHtCoupon.getLimit_exp_ord_id() != tdHtExpressOrderDto.getExp_ord_id()){
                                couponUse = false;
                                retInfo.setMark("1");
                                retInfo.setTip("该优惠券无法在该订单中使用使用.");
                            }else {
                                tdHtCoupon.setCou_user_time(new Timestamp(nowDate));
                                tdHtCoupon.setExp_ord_id(expressOrderPayDto.getExp_ord_id());
                                //优惠券没有限定金额
                                if (tdHtCoupon.getCou_limit_amount().compareTo(new BigDecimal(0)) == 0) {
                                    //优惠券没有折扣
                                    if (tdHtCoupon.getCou_discount().compareTo(new BigDecimal(1)) == 0) {
                                        payMoney = (payMoney.subtract(tdHtCoupon.getCou_amount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                    }
                                    //优惠券有折扣
                                    else {
                                        BigDecimal money = (payMoney.multiply(tdHtCoupon.getCou_discount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                        //优惠券没有最高金额
                                        if (tdHtCoupon.getCou_amount().compareTo(new BigDecimal(0)) == 0) {
                                            payMoney = money;
                                        }
                                        //优惠券有最高金额
                                        else {
                                            //订单打折金额大于优惠券最高金额
                                            if (payMoney.subtract(money).compareTo(tdHtCoupon.getCou_amount()) > 0) {
                                                payMoney = (payMoney.subtract(tdHtCoupon.getCou_amount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                            } else {
                                                payMoney = money;
                                            }
                                        }
                                    }
                                    tdHtCouponRepository.updateUseCoupon(tdHtCoupon);
                                } else {
                                    //优惠券限定金额大于订单金额
                                    if (tdHtCoupon.getCou_limit_amount().compareTo(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP)) > 0) {
                                        couponUse = false;
                                        retInfo.setMark("1");
                                        retInfo.setTip("优惠券无法使用.");
                                    }
                                    //优惠券限定金额小于、等于订单金额
                                    else {
                                        //优惠券没有折扣
                                        if (tdHtCoupon.getCou_discount().compareTo(new BigDecimal(1)) == 0) {
                                            payMoney = (payMoney.subtract(tdHtCoupon.getCou_amount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                        }
                                        //优惠券有折扣
                                        else {
                                            BigDecimal money = (payMoney.multiply(tdHtCoupon.getCou_discount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                            //优惠券没有最高金额
                                            if (tdHtCoupon.getCou_amount().compareTo(new BigDecimal(0)) == 0) {
                                                payMoney = money;
                                            }
                                            //优惠券有最高金额
                                            else {
                                                //订单打折金额大于优惠券最高金额
                                                if (payMoney.subtract(money).compareTo(tdHtCoupon.getCou_amount()) > 0) {
                                                    payMoney = (payMoney.subtract(tdHtCoupon.getCou_amount())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                                } else {
                                                    payMoney = money;
                                                }
                                            }
                                        }
                                        tdHtCouponRepository.updateUseCoupon(tdHtCoupon);
                                    }
                                }
                                if (payMoney.doubleValue() < 0) {
                                    payMoney = new BigDecimal(0);
                                }
                            }
                        }
                    }

                    if (couponUse) {
                        TdHtMembFoundChangeDto tdHtMembFoundChangeDto = new TdHtMembFoundChangeDto();
                        tdHtMembFoundChangeDto.setExp_ord_id(expressOrderPayDto.getExp_ord_id());
                        tdHtMembFoundChangeDto.setMemb_id(memberDto.getMemb_id());
                        tdHtMembFoundChangeDto.setMfchg_time(new Timestamp(nowDate));
                        tdHtMembFoundChangeDto.setMfchg_month(CommonUtil.getMonth());
                        tdHtMembFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub());
                        tdHtMembFoundChangeDto.setMfchg_name("订单支付");
                        tdHtMembFoundChangeDto.setMemb_mon_bill_id(0);
                        tdHtMembFoundChangeDto.setMemb_goods_id(0);
                        tdHtMembFoundChangeDto.setMemb_ivc_his_id(0);

                        //判断是否需要第三方支付(3：钱包支付 ；1、2：需要进行第三方支付)
                        if (expressOrderPayDto.getPay_type() == 3) {
                            if (memberDto.getMemb_balance().compareTo(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP)) < 0) {
                                retInfo.setMark("1");
                                retInfo.setTip("余额不足.");
                            } else {
                                tdHtMembFoundChangeDto.setMfchg_front_amount(memberDto.getMemb_balance());
                                tdHtMembFoundChangeDto.setMfchg_change_amount(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
                                tdHtMembFoundChangeDto.setMfchg_back_amount((memberDto.getMemb_balance().subtract(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP))).setScale(2, BigDecimal.ROUND_HALF_UP));
                                tdHtMembFoundChangeDto.setMfchg_channel(3);
                                tdHtMembFoundChangeDto.setMfchg_state(0);
                                tdHtMembFoundChangeDto.setMfchg_type(3);
                                tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                                //更新订单状态
                                TdHtExpressOrderDto expressOrder = new TdHtExpressOrderDto();
                                expressOrder.setExp_ord_id(expressOrderPayDto.getExp_ord_id());
                                expressOrder.setExp_ord_state(4);
                                expressOrder.setExp_ord_pay_amount(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                                expressOrder.setPay_time(new Date(nowDate));
                                expressOrder.setCoupon_amount(new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()).subtract(payMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
                                tdHtExpressOrderRepository.payExpressOrder(expressOrder);

                                List<TdHtExpressOrderCollectDto> collectDtoList = tdHtExpressCollectRepository.findExpressCollectByOrder(tdHtExpressOrderDto.getExp_ord_id());
                                BigDecimal clt_coupon_amount = expressOrder.getCoupon_amount().divide(new BigDecimal(collectDtoList.size())).setScale(2, BigDecimal.ROUND_DOWN);
                                for (int i = 0; i < collectDtoList.size(); i++) {
                                    TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);

                                    if(i == collectDtoList.size() - 1){
                                        collectDto.setCoupon_amount(expressOrder.getCoupon_amount().subtract(clt_coupon_amount.multiply(new BigDecimal(collectDtoList.size() - 1))).setScale(2, BigDecimal.ROUND_HALF_UP));
                                    }else {
                                        collectDto.setCoupon_amount(clt_coupon_amount);
                                    }

                                    if (tdHtExpressOrderDto.getExp_ord_type() == 1) {
                                        TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto = new TsHtExpressOpenNumberDto();
                                        tsHtExpressOpenNumberDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                                        tsHtExpressOpenNumberDto.setRegion_id(tdHtExpressOrderDto.getAdd_city());
                                        TsHtExpressOpenNumberDto numberDto = tsHtExpressOpenNumberRepository.findCanUseExpressNumber(tsHtExpressOpenNumberDto);

                                        if (numberDto != null) {
                                            collectDto.setExpress_number(numberDto.getExpress_number());
                                            collectDto.setExp_ord_clt_state(2);
                                            tdHtExpressCollectRepository.updatePayCollect(collectDto);

                                            numberDto.setAgent_id(tdHtExpressOrderDto.getAgent_id());
                                            numberDto.setExp_open_state(1);
                                            tsHtExpressOpenNumberRepository.updateExpressNumberState(numberDto);
                                        }
                                    } else if (tdHtExpressOrderDto.getExp_ord_type() == 2) {
                                        collectDto.setExp_ord_clt_state(2);
                                        tdHtExpressCollectRepository.updatePayCollect(collectDto);
                                    }
                                }

                                if(memberDto.getMemb_discount().intValue()==1){
                                    //获取首次下单及每日寄件奖励积分
                                    Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
                                    TsHtScoreTaskDto firstSendScoreTask = scoreTask.get(Constant.FIRST_SEND_TASK_ID);
                                    TsHtScoreTaskDto sendScoreTask = scoreTask.get(Constant.SEND_TASK_ID);

                                    TdHtMembScoreTaskDto firstSendMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                    firstSendMembScoreTaskDto.setMemb_id(memberDto.getMemb_id());
                                    firstSendMembScoreTaskDto.setSt_id(firstSendScoreTask.getSt_id());
                                    int noviceTask = tdHtMembScoreTaskRepository.findMembNoviceTaskCount(firstSendMembScoreTaskDto);
                                    if (noviceTask == 0) {
                                        //插入用户积分变化记录表
                                        TdHtMembScore tdHtMembScore = new TdHtMembScore();
                                        tdHtMembScore.setMemb_id(memberDto.getMemb_id());
                                        tdHtMembScore.setScore_change_point(firstSendScoreTask.getSt_score());
                                        tdHtMembScore.setScore_front_point(memberDto.getMemb_score());
                                        tdHtMembScore.setScore_back_point(memberDto.getMemb_score() + firstSendScoreTask.getSt_score());
                                        tdHtMembScore.setScore_month(CommonUtil.getMonth());
                                        tdHtMembScore.setScore_time(new Date());
                                        tdHtMembScore.setScore_reason(firstSendScoreTask.getSt_name());
                                        tdHtMembScore.setScore_type(1);
                                        tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                                        //插入用户积分任务完成记录表
                                        TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                        tdHtMembScoreTaskDto.setMemb_id(memberDto.getMemb_id());
                                        tdHtMembScoreTaskDto.setSt_id(firstSendScoreTask.getSt_id());
                                        tdHtMembScoreTaskDto.setSt_state(firstSendScoreTask.getSt_state());
                                        tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                                        tdHtMembScoreTaskDto.setSt_img(firstSendScoreTask.getSt_img());
                                        tdHtMembScoreTaskDto.setSt_name(firstSendScoreTask.getSt_name());
                                        tdHtMembScoreTaskDto.setSt_score(firstSendScoreTask.getSt_score());
                                        tdHtMembScoreTaskDto.setSt_synopsis(firstSendScoreTask.getSt_synopsis());
                                        tdHtMembScoreTaskDto.setSt_type(firstSendScoreTask.getSt_type());
                                        tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                                        tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                                        memberDto.setMemb_score(tdHtMembScore.getScore_back_point());
                                    }

                                    TdHtMembScoreTaskDto sendMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                    sendMembScoreTaskDto.setMemb_id(memberDto.getMemb_id());
                                    sendMembScoreTaskDto.setSt_id(sendScoreTask.getSt_id());
                                    int dailyTask = tdHtMembScoreTaskRepository.findMembDailyTaskCount(sendMembScoreTaskDto);
                                    if (dailyTask == 0) {
                                        //插入用户积分变化记录表
                                        TdHtMembScore tdHtMembScore = new TdHtMembScore();
                                        tdHtMembScore.setMemb_id(memberDto.getMemb_id());
                                        tdHtMembScore.setScore_change_point(sendScoreTask.getSt_score());
                                        tdHtMembScore.setScore_front_point(memberDto.getMemb_score());
                                        tdHtMembScore.setScore_back_point(memberDto.getMemb_score() + sendScoreTask.getSt_score());
                                        tdHtMembScore.setScore_month(CommonUtil.getMonth());
                                        tdHtMembScore.setScore_time(new Date());
                                        tdHtMembScore.setScore_reason(sendScoreTask.getSt_name());
                                        tdHtMembScore.setScore_type(1);
                                        tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                                        //插入用户积分任务完成记录表
                                        TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                        tdHtMembScoreTaskDto.setMemb_id(memberDto.getMemb_id());
                                        tdHtMembScoreTaskDto.setSt_id(sendScoreTask.getSt_id());
                                        tdHtMembScoreTaskDto.setSt_state(sendScoreTask.getSt_state());
                                        tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                                        tdHtMembScoreTaskDto.setSt_img(sendScoreTask.getSt_img());
                                        tdHtMembScoreTaskDto.setSt_name(sendScoreTask.getSt_name());
                                        tdHtMembScoreTaskDto.setSt_score(sendScoreTask.getSt_score());
                                        tdHtMembScoreTaskDto.setSt_synopsis(sendScoreTask.getSt_synopsis());
                                        tdHtMembScoreTaskDto.setSt_type(sendScoreTask.getSt_type());
                                        tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                                        tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                                        memberDto.setMemb_score(tdHtMembScore.getScore_back_point());
                                    }
                                }
                                //更新用户积分和余额
                                memberDto.setMemb_balance(tdHtMembFoundChangeDto.getMfchg_back_amount());
                                tdHtMemberRepository.updateMemberBanlanceAndScore(memberDto);

                                tdHtCoupon = tdHtCouponRepository.findCouponByOrder(tdHtMembFoundChangeDto.getExp_ord_id());
                                //更新优惠券状态
                                if (tdHtCoupon != null) {
                                    tdHtCoupon.setState(1);
                                    tdHtCouponRepository.updateUseCoupon(tdHtCoupon);
                                }

                                retInfo.setMark("0");
                                retInfo.setTip("支付成功");
                                retInfo.setObj("");

                                MemcachedUtils.replace(token, memberDto, new java.sql.Date(20 * 24 * 60 * 60 * 1000));
                            }
                        } else {
                            tdHtMembFoundChangeDto.setMfchg_front_amount(memberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_back_amount(memberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_type(3);
                            tdHtMembFoundChangeDto.setMfchg_state(1);
                            tdHtMembFoundChangeDto.setMfchg_change_amount(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
                            tdHtMembFoundChangeDto.setMfchg_channel(4);
                            tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                            String nonce_str = CommonUtil.getOrderNub();
                            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
                            packageParams.put("appid", tsHtThirdDto.getAppid());
                            packageParams.put("mch_id", tsHtThirdDto.getPay_mchid());
                            packageParams.put("nonce_str", nonce_str);
                            packageParams.put("body", "订单支付");
                            packageParams.put("out_trade_no", tdHtMembFoundChangeDto.getMfchg_number());
                            packageParams.put("total_fee", String.valueOf(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue()));        // 这里写的金额为1 分到时修改
                            packageParams.put("spbill_create_ip", ip);
                            packageParams.put("notify_url", Constant.WE_CHAT_ORDER_PAY_NOTIFY_URL);
                            packageParams.put("trade_type", "JSAPI");
                            packageParams.put("openid", memberThirdLoginDto.getThr_token());

                            WeChatRequestHandlerUtil reqHandler = new WeChatRequestHandlerUtil(null, null);
                            reqHandler.init(tsHtThirdDto.getAppid(), tsHtThirdDto.getAppsecret(), tsHtThirdDto.getPay_partnerkey());
                            String sign = reqHandler.createSign(packageParams);
                            packageParams.put("sign", sign);

                            //生成预支付订单的XMl
                            String xml = WeChatPayUtil.getRequestXml(packageParams);
                            logger.info("=========生成预支付订单的XMl:\n\n" + xml);

                            //得到预支付订单 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
                            String prepay_id = WeChatHttpsUtil.httpRequest(Constant.WE_CHAT_PAY_PREPAY_ID_URL, "POST", xml);
                            logger.info("=========得到预支付订单:\n\n" + prepay_id);

                            //解析响应XML
                            Map<String, String> analyticalXML = WeChatPayUtil.doXMLParse(prepay_id);
                            logger.info("=========解析响应XML:\n\n" + analyticalXML);

                            //转换成JSon
                            JSONObject jsonObject = JSONObject.fromObject(analyticalXML);
                            String prepayId = jsonObject.get("prepay_id").toString();
                            logger.debug("=====转换后的JSON：\n\n" + jsonObject);
                            logger.debug("=====得到的prepayId是：\n\n" + prepayId);

                            //获取prepay_id后，拼接最后请求支付所需要的package
                            SortedMap<Object, Object> finalpackage = new TreeMap<Object, Object>();
                            String timestamp = WeChatPaySHAUtil.getTimeStamp();
                            String packages = "prepay_id=" + prepayId;
                            finalpackage.put("appId", tsHtThirdDto.getAppid());
                            finalpackage.put("timeStamp", timestamp);
                            finalpackage.put("nonceStr", nonce_str);
                            finalpackage.put("package", packages);
                            finalpackage.put("signType", "MD5");
                            String paySign = reqHandler.createSign(finalpackage);//要签名

                            WxRequestPayDto wxRequestPayDto = new WxRequestPayDto();
                            wxRequestPayDto.setAppid(tsHtThirdDto.getAppid());
                            wxRequestPayDto.setTime_stamp(timestamp);
                            wxRequestPayDto.setNonce_str(nonce_str);
                            wxRequestPayDto.setPackage_value(packages);
                            wxRequestPayDto.setSign_type("MD5");
                            wxRequestPayDto.setPay_sign(paySign);

                            retInfo.setMark("0");
                            retInfo.setTip("下单成功");
                            retInfo.setObj(wxRequestPayDto);
                        }
                    }
                }else{
                    retInfo.setMark("1");
                    retInfo.setTip("该订单您已经支付.");
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
     * @param request
     * @return RetInfo
     * @Purpose 微信用户支付订单回调
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo orderPayNotify(HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":orderPayNotify:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            String inputLine;
            String notityXml = "";
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
            Map m = WeChatPayUtil.parseXmlToList2(notityXml);
            if ("SUCCESS".equals(m.get("return_code"))) {
                //支付成功
                if ("SUCCESS".equals(m.get("result_code"))) {
                    String totalFee = m.get("total_fee").toString();
                    String transactionNo = m.get("out_trade_no").toString();
                    String transaction_id = m.get("transaction_id").toString();

                    BigDecimal amount = new BigDecimal(totalFee).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                    TdHtMembFoundChangeDto tdHtMembFoundChangeDto = tdHtMembFoundChangeRepository.findfindMemberFoundByNumber(transactionNo);
                    tdHtMembFoundChangeDto.setTransaction_no(transaction_id);
                    TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(tdHtMembFoundChangeDto.getMemb_id());
                    TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(tdHtMemberDto.getToken());
                    if (tdHtMembFoundChangeDto.getMfchg_type() == 3) {//订单支付
                        if (tdHtMembFoundChangeDto.getMfchg_state() == 1) {
                            //更新订单状态
                            TdHtExpressOrderDto expressOrder = tdHtExpressOrderRepository.findTdHtExpressOrderById(tdHtMembFoundChangeDto.getExp_ord_id());
                            expressOrder.setExp_ord_state(4);
                            expressOrder.setExp_ord_pay_amount(amount.doubleValue());
                            expressOrder.setPay_time(new Date());

                            TdHtCoupon tdHtCoupon = tdHtCouponRepository.findCouponByOrder(tdHtMembFoundChangeDto.getExp_ord_id());
                            //更新优惠券状态
                            if (tdHtCoupon != null) {
                                tdHtCoupon.setState(1);
                                tdHtCouponRepository.updateUseCoupon(tdHtCoupon);
                                expressOrder.setCoupon_amount(new BigDecimal(expressOrder.getExp_ord_amount()).subtract(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
                            }

                            tdHtExpressOrderRepository.payExpressOrder(expressOrder);

                            List<TdHtExpressOrderCollectDto> collectDtoList = tdHtExpressCollectRepository.findExpressCollectByOrder(expressOrder.getExp_ord_id());
                            BigDecimal clt_coupon_amount = expressOrder.getCoupon_amount().divide(new BigDecimal(collectDtoList.size())).setScale(2, BigDecimal.ROUND_DOWN);
                            for (int i = 0; i < collectDtoList.size(); i++) {
                                TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);

                                if(i == collectDtoList.size() - 1){
                                    collectDto.setCoupon_amount(expressOrder.getCoupon_amount().subtract(clt_coupon_amount.multiply(new BigDecimal(collectDtoList.size() - 1))).setScale(2, BigDecimal.ROUND_HALF_UP));
                                }else {
                                    collectDto.setCoupon_amount(clt_coupon_amount);
                                }

                                if (expressOrder.getExp_ord_type() == 1) {
                                    TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto = new TsHtExpressOpenNumberDto();
                                    tsHtExpressOpenNumberDto.setExpress_id(expressOrder.getExpress_id());
                                    tsHtExpressOpenNumberDto.setRegion_id(expressOrder.getAdd_city());
                                    TsHtExpressOpenNumberDto numberDto = tsHtExpressOpenNumberRepository.findCanUseExpressNumber(tsHtExpressOpenNumberDto);

                                    if (numberDto != null) {
                                        collectDto.setExpress_number(numberDto.getExpress_number());
                                        collectDto.setExp_ord_clt_state(2);
                                        tdHtExpressCollectRepository.updatePayCollect(collectDto);

                                        numberDto.setAgent_id(expressOrder.getAgent_id());
                                        numberDto.setExp_open_state(1);
                                        tsHtExpressOpenNumberRepository.updateExpressNumberState(numberDto);
                                    }
                                } else if (expressOrder.getExp_ord_type() == 2) {
                                    collectDto.setExp_ord_clt_state(2);
                                    tdHtExpressCollectRepository.updatePayCollect(collectDto);
                                }
                            }

                            tdHtMembFoundChangeDto.setMfchg_back_amount(new BigDecimal(0));
                            tdHtMembFoundChangeDto.setMfchg_change_amount(new BigDecimal(expressOrder.getExp_ord_amount()).add(new BigDecimal(expressOrder.getExp_ord_gratuity())));
                            tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_state(0);
                            tdHtMembFoundChangeRepository.updateMemberFoundChange(tdHtMembFoundChangeDto);

                            //更新用户余额
//                            tdHtMemberDto.setMemb_balance(tdHtMembFoundChangeDto.getMfchg_back_amount());

                            if(tdHtMemberDto.getMemb_discount().intValue()==1){
                                //获取首次下单及每日寄件奖励积分
                                Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
                                TsHtScoreTaskDto firstSendScoreTask = scoreTask.get(Constant.FIRST_SEND_TASK_ID);
                                TsHtScoreTaskDto sendScoreTask = scoreTask.get(Constant.SEND_TASK_ID);

                                TdHtMembScoreTaskDto firstSendMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                firstSendMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
                                firstSendMembScoreTaskDto.setSt_id(firstSendScoreTask.getSt_id());
                                int noviceTask = tdHtMembScoreTaskRepository.findMembNoviceTaskCount(firstSendMembScoreTaskDto);
                                if (noviceTask == 0) {
                                    //插入用户积分变化记录表
                                    TdHtMembScore tdHtMembScore = new TdHtMembScore();
                                    tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                                    tdHtMembScore.setScore_change_point(firstSendScoreTask.getSt_score());
                                    tdHtMembScore.setScore_front_point(tdHtMemberDto.getMemb_score());
                                    tdHtMembScore.setScore_back_point(tdHtMemberDto.getMemb_score() + firstSendScoreTask.getSt_score());
                                    tdHtMembScore.setScore_month(CommonUtil.getMonth());
                                    tdHtMembScore.setScore_time(new Date());
                                    tdHtMembScore.setScore_reason(firstSendScoreTask.getSt_name());
                                    tdHtMembScore.setScore_type(1);
                                    tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                                    //插入用户积分任务完成记录表
                                    TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                    tdHtMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
                                    tdHtMembScoreTaskDto.setSt_id(firstSendScoreTask.getSt_id());
                                    tdHtMembScoreTaskDto.setSt_state(firstSendScoreTask.getSt_state());
                                    tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                                    tdHtMembScoreTaskDto.setSt_img(firstSendScoreTask.getSt_img());
                                    tdHtMembScoreTaskDto.setSt_name(firstSendScoreTask.getSt_name());
                                    tdHtMembScoreTaskDto.setSt_score(firstSendScoreTask.getSt_score());
                                    tdHtMembScoreTaskDto.setSt_synopsis(firstSendScoreTask.getSt_synopsis());
                                    tdHtMembScoreTaskDto.setSt_type(firstSendScoreTask.getSt_type());
                                    tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                                    tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                                    tdHtMemberDto.setMemb_score(tdHtMembScore.getScore_back_point());
                                }

                                TdHtMembScoreTaskDto sendMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                sendMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
                                sendMembScoreTaskDto.setSt_id(sendScoreTask.getSt_id());
                                int dailyTask = tdHtMembScoreTaskRepository.findMembDailyTaskCount(sendMembScoreTaskDto);
                                if (dailyTask == 0) {
                                    //插入用户积分变化记录表
                                    TdHtMembScore tdHtMembScore = new TdHtMembScore();
                                    tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                                    tdHtMembScore.setScore_change_point(sendScoreTask.getSt_score());
                                    tdHtMembScore.setScore_front_point(tdHtMemberDto.getMemb_score());
                                    tdHtMembScore.setScore_back_point(tdHtMemberDto.getMemb_score() + sendScoreTask.getSt_score());
                                    tdHtMembScore.setScore_month(CommonUtil.getMonth());
                                    tdHtMembScore.setScore_time(new Date());
                                    tdHtMembScore.setScore_reason(sendScoreTask.getSt_name());
                                    tdHtMembScore.setScore_type(1);
                                    tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                                    //插入用户积分任务完成记录表
                                    TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                    tdHtMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
                                    tdHtMembScoreTaskDto.setSt_id(sendScoreTask.getSt_id());
                                    tdHtMembScoreTaskDto.setSt_state(sendScoreTask.getSt_state());
                                    tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                                    tdHtMembScoreTaskDto.setSt_img(sendScoreTask.getSt_img());
                                    tdHtMembScoreTaskDto.setSt_name(sendScoreTask.getSt_name());
                                    tdHtMembScoreTaskDto.setSt_score(sendScoreTask.getSt_score());
                                    tdHtMembScoreTaskDto.setSt_synopsis(sendScoreTask.getSt_synopsis());
                                    tdHtMembScoreTaskDto.setSt_type(sendScoreTask.getSt_type());
                                    tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                                    tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                                    tdHtMemberDto.setMemb_score(tdHtMembScore.getScore_back_point());
                                }
                            }
                            //更新用户积分和余额
                            tdHtMemberRepository.updateMemberBanlanceAndScore(tdHtMemberDto);
                        }
                    }
                    if (member != null) {
                        member.setMemb_balance(tdHtMemberDto.getMemb_balance());
                        member.setMemb_score(tdHtMemberDto.getMemb_score());
                        MemcachedUtils.replace(tdHtMemberDto.getToken(), member, new java.sql.Date(20 * 24 * 60 * 60 * 1000));
                    }

                    String result = "{\"return_code\":\"SUCCESS\",\"return_msg\":\"OK\"}";
                    retInfo.setMark("0");
                    retInfo.setTip(result);
                } else {
                    String result = "{\"return_code\":\"FAIL\",\"return_msg\":\"支付失败\"}";
                    retInfo.setMark("1");
                    retInfo.setTip(result);
                }
            } else {
                String result = "{\"return_code\":\"FAIL\",\"return_msg\":\"通信失败\"}";
                retInfo.setMark("1");
                retInfo.setTip(result);
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
