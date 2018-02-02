package cn.hotol.app.service.three.found;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.TdHtMembScore;
import cn.hotol.app.bean.dto.agent.member.TdHtAgentMemberDto;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.found.MemberFinancialDetailsDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;
import cn.hotol.app.bean.dto.invoice.history.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.monthbill.TdHtMembMonthBillDto;
import cn.hotol.app.bean.dto.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.push.PushDto;
import cn.hotol.app.bean.dto.score.TdHtMembScoreGoodsDto;
import cn.hotol.app.bean.dto.score.TdHtMembScoreTaskDto;
import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lizhun on 2016/12/21.
 */
@Service
public class FoundServiceImpl implements FoundService {
    private static Logger logger = Logger.getLogger(FoundServiceImpl.class);
    @Resource
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtCouponRepository tdHtCouponRepository;
    @Resource
    private TdHtMembScoreTaskRepository tdHtMembScoreTaskRepository;
    @Resource
    private TdHtMembScoreRepository tdHtMembScoreRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;
    @Resource
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    @Resource
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    @Resource
    private TdHtMembMonthBillRepository tdHtMembMonthBillRepository;
    @Resource
    private TdHtGreenMailRepository tdHtGreenMailRepository;
    @Resource
    private TdHtAgentMemberRepository tdHtAgentMemberRepository;
    @Resource
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;
    @Resource
    private TdHtMembScoreGoodsRepository tdHtMembScoreGoodsRepository;
    @Resource
    private TdHtMembInvoiceHistoryRepository tdHtMembInvoiceHistoryRepository;

    /**
     * @param tdHtMembFoundChangeDto
     * @return RetInfo
     * @Purpose 充值
     * @version 3.0
     * @author lizhun
     */
    @Override
    public synchronized RetInfo recharge(TdHtMembFoundChangeDto tdHtMembFoundChangeDto, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":recharge:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tdHtMembFoundChangeDto.setExp_ord_id(0);
            tdHtMembFoundChangeDto.setMfchg_back_amount(new BigDecimal(0));
            tdHtMembFoundChangeDto.setMfchg_front_amount(new BigDecimal(0));
            tdHtMembFoundChangeDto.setMfchg_month(CommonUtil.getMonth());
            tdHtMembFoundChangeDto.setMfchg_name("充值");
            tdHtMembFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub());
            tdHtMembFoundChangeDto.setMfchg_state(1);
            tdHtMembFoundChangeDto.setMfchg_type(1);

            Pingpp.apiKey = Constant.Live_Secret_Key;
            Map<String, Object> chargeParams = new HashMap<String, Object>();
            chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());

            chargeParams.put("amount", tdHtMembFoundChangeDto.getMfchg_change_amount().multiply(new BigDecimal(100)).intValue());
            Map<String, String> app = new HashMap<String, String>();
            app.put("id", Constant.MEMBER_APP_ID);
            chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());
            chargeParams.put("app", app);
            if (tdHtMembFoundChangeDto.getMfchg_channel() == 1) {
                chargeParams.put("channel", "wx");  //微信wx
            }
            if (tdHtMembFoundChangeDto.getMfchg_channel() == 2) {
                chargeParams.put("channel", "alipay");  //支付宝alipay
            }
            chargeParams.put("currency", "cny");
            chargeParams.put("client_ip", Ip.getIpAddr(request));
            chargeParams.put("subject", "充值");
            chargeParams.put("body", "充值");
            Charge charge = Charge.create(chargeParams);

            tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

            retInfo.setMark("0");
            retInfo.setTip("下单成功");
            Map<String, Object> map = new HashMap<>();
            map.put("charge", charge);
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
     * @param charse
     * @return RetInfo
     * @Purpose 充值回调
     * @version 3.0
     * @author lizhun
     */
    @Override
    public synchronized RetInfo notify(Map<String, Object> charse) {
        String logInfo = this.getClass().getName() + ":notify:";
        logger.info("======" + logInfo + "begin======" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        RetInfo retInfo = new RetInfo();
        try {
            Charge charge = new Charge();
            charge.setAmount(Integer.valueOf(charse.get("amount").toString()));
            charge.setOrderNo(charse.get("order_no").toString());
            charge.setPaid((Boolean) charse.get("paid"));
            //判断是否已付款
            if ((Boolean) charse.get("paid")) {
                BigDecimal amount = new BigDecimal(charge.getAmount()).divide(new BigDecimal(100));
                TdHtMembFoundChangeDto tdHtMembFoundChangeDto = tdHtMembFoundChangeRepository.findfindMemberFoundByNumber(charge.getOrderNo());
                String transaction_no = charse.get("transaction_no").toString();
                tdHtMembFoundChangeDto.setTransaction_no(transaction_no);
                if (tdHtMembFoundChangeDto.getMfchg_state() == 1) {
                    TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(tdHtMembFoundChangeDto.getMemb_id());
                    TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(tdHtMemberDto.getToken());
                    if (tdHtMembFoundChangeDto.getMfchg_type() == 1) {
                        BigDecimal memb_balance = tdHtMemberDto.getMemb_balance().add(amount);
                        //更新充值记录
                        tdHtMembFoundChangeDto.setMfchg_back_amount(memb_balance);
                        tdHtMembFoundChangeDto.setMfchg_change_amount(amount);
                        tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                        tdHtMembFoundChangeDto.setMfchg_state(0);
                        tdHtMembFoundChangeRepository.updateMemberFoundChange(tdHtMembFoundChangeDto);
                        //更新用户余额
                        tdHtMemberDto.setMemb_balance(memb_balance);
                        tdHtMemberRepository.updateMemberBanlance(tdHtMemberDto);
                        retInfo.setMark("0");
                    } else if (tdHtMembFoundChangeDto.getMfchg_type() == 3) {//订单支付
                        if (tdHtMembFoundChangeDto.getExp_ord_id() > 0) {
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

                            int payCode = 0;
                            //更新支付记录
                            if ("代付".equals(tdHtMembFoundChangeDto.getMfchg_name())) {
                                payCode = 1;
                            } else {
                                payCode = 2;
                            }
                            tdHtMembFoundChangeDto.setMfchg_back_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_change_amount(amount);
                            tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_state(0);
                            tdHtMembFoundChangeRepository.updateMemberFoundChange(tdHtMembFoundChangeDto);

                            //更新用户余额
//                            tdHtMemberDto.setMemb_balance(tdHtMembFoundChangeDto.getMfchg_back_amount());

                            if (tdHtMemberDto.getMemb_discount().intValue() == 1) {
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

                                    //代理人推广首单奖励
                                    TdHtAgentMemberDto tdHtAgentMemberDto = tdHtAgentMemberRepository.findTdHtAgentMemberDtoByMembId(tdHtMemberDto.getMemb_id());
                                    if (tdHtAgentMemberDto != null) {
                                        TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto=new TdHtAgentFoundChangeDto();
                                        tdHtAgentFoundChangeDto.setAfchg_change_amount(tdHtAgentMemberDto.getFirst_single_reward());
                                        tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(System.currentTimeMillis()));
                                        tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                                        tdHtAgentFoundChangeDto.setAfchg_type(1);
                                        tdHtAgentFoundChangeDto.setAfchg_state(2);
                                        tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub());
                                        tdHtAgentFoundChangeDto.setAfchg_name("推广首单奖励");
                                        tdHtAgentFoundChangeDto.setAgent_id(tdHtAgentMemberDto.getAgent_id());
                                        tdHtAgentFoundChangeDto.setAfchg_front_amount(new BigDecimal(0));
                                        tdHtAgentFoundChangeDto.setAfchg_back_amount(new BigDecimal(0));
                                        tdHtAgentFoundChangeDto.setExp_ord_id(0);
                                        tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);
                                        //更新首单奖励时间
                                        tdHtAgentMemberRepository.updateFirstSingleTime(tdHtAgentMemberDto);
                                    }


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
                            retInfo.setMark("0");

                            if (payCode == 1) {
                                if (tdHtMemberDto.getPush_type() == 1) {
                                    PushUtil.pushAndroidMember(tdHtMemberDto.getPush_token(), "您的订单已由代理人代付.", "5", "", tdHtMemberDto.getApp_version());
                                } else if (tdHtMemberDto.getPush_type() == 2) {
                                    PushUtil.pushIosMember(tdHtMemberDto.getPush_token(), "您的订单已由代理人代付.", "5", "default", tdHtMemberDto.getApp_version());
                                }
                            } else if (payCode == 2) {
                                PushDto pushDto = tdHtAgentRepository.findAgentPushInfo(expressOrder.getAgent_id());

                                if (pushDto.getPush_type() == 1) {
                                    PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有揽收的快件已支付.", "4", "", pushDto.getApp_version());
                                } else if (pushDto.getPush_type() == 2) {
                                    PushUtil.pushIosAgent(pushDto.getPush_token(), "您有揽收的快件已支付.", "4", "default", pushDto.getApp_version());
                                }
                            }
                        } else if(tdHtMembFoundChangeDto.getMemb_mon_bill_id()>0) {
                            //月结账单支付
                            tdHtMembFoundChangeDto.setMfchg_back_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_change_amount(amount);
                            tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_state(0);
                            tdHtMembFoundChangeRepository.updateMemberFoundChange(tdHtMembFoundChangeDto);

                            TdHtMembMonthBillDto tdHtMembMonthBillDto = new TdHtMembMonthBillDto();
                            tdHtMembMonthBillDto.setState(2);
                            tdHtMembMonthBillDto.setMemb_mon_bill_pay_amount(amount);
                            tdHtMembMonthBillDto.setMemb_mon_bill_id(tdHtMembFoundChangeDto.getMemb_mon_bill_id());
                            tdHtMembMonthBillRepository.updatePayMonthBill(tdHtMembMonthBillDto);

                            retInfo.setMark("0");
                        } else if(tdHtMembFoundChangeDto.getMemb_goods_id()>0){
                            //积分商品支付
                            tdHtMembFoundChangeDto.setMfchg_back_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_change_amount(amount);
                            tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_state(0);
                            tdHtMembFoundChangeRepository.updateMemberFoundChange(tdHtMembFoundChangeDto);

                            //缓存中取出积分兑换商品列表
                            Long now=System.currentTimeMillis();
                            Map<String, TsHtScoreGoodsDto> scoreGoods = (Map<String, TsHtScoreGoodsDto>) MemcachedUtils.get(MemcachedKey.SCORE_GOODS_MAP);
                            TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto=tdHtMembScoreGoodsRepository.findMembScoreGoodsById(tdHtMembFoundChangeDto.getMemb_goods_id());
                            TsHtScoreGoodsDto scoreGoodsDto = scoreGoods.get(String.valueOf(tdHtMembScoreGoodsDto.getGoods_id()));
                            if(scoreGoodsDto.getGoods_score()>0){
                                int nowScore = tdHtMemberDto.getMemb_score() - scoreGoodsDto.getGoods_score();
                                TdHtMembScore tdHtMembScore = new TdHtMembScore();
                                tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                                tdHtMembScore.setScore_change_point(scoreGoodsDto.getGoods_score());
                                tdHtMembScore.setScore_front_point(tdHtMemberDto.getMemb_score());
                                tdHtMembScore.setScore_back_point(nowScore);
                                tdHtMembScore.setScore_month(CommonUtil.getMonth());
                                tdHtMembScore.setScore_time(new Date(now));
                                tdHtMembScore.setScore_reason("用户兑换" + scoreGoodsDto.getGoods_name());
                                tdHtMembScore.setScore_type(2);
                                tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                                tdHtMembScoreGoodsDto.setScore_id(tdHtMembScore.getScore_id());
                                tdHtMemberDto.setMemb_score(nowScore);
                                tdHtMemberRepository.updateMembScore(tdHtMemberDto);
                            }
                            tdHtMembScoreGoodsDto.setGoods_state(0);
                            tdHtMembScoreGoodsRepository.updateMembGoodsState(tdHtMembScoreGoodsDto);

                            Timestamp endTime = new Timestamp(now + scoreGoodsDto.getEffective_day() * 24 * 60 * 60 * 1000L);
                            TdHtCoupon tdHtCoupon = new TdHtCoupon();
                            tdHtCoupon.setCou_amount(scoreGoodsDto.getCoupon_money());
                            tdHtCoupon.setCou_discount(scoreGoodsDto.getCou_discount());
                            tdHtCoupon.setCou_limit_amount(scoreGoodsDto.getCou_limit_amount());
                            tdHtCoupon.setCou_limit_time(endTime);
                            tdHtCoupon.setCou_name(scoreGoodsDto.getGoods_name());
                            tdHtCoupon.setCou_receive_time(new Timestamp(now));
                            tdHtCoupon.setCou_user_time(new Timestamp(now));
                            tdHtCoupon.setExp_ord_id(0);
                            tdHtCoupon.setMemb_id(tdHtMemberDto.getMemb_id());
                            tdHtCoupon.setState(0);
                            tdHtCoupon.setMemb_type(scoreGoodsDto.getExpress_source());
                            tdHtCoupon.setExpress_id(scoreGoodsDto.getExpress_id());
                            tdHtCoupon.setRegion_id(scoreGoodsDto.getRegion_id());
                            tdHtCoupon.setLimit_exp_ord_id(0);
                            tdHtCouponRepository.insertMembCoupon(tdHtCoupon);
                        } else if(tdHtMembFoundChangeDto.getMemb_ivc_his_id()>0){
                            //开票邮费支付
                            tdHtMembFoundChangeDto.setMfchg_back_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_change_amount(amount);
                            tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_state(0);
                            tdHtMembFoundChangeRepository.updateMemberFoundChange(tdHtMembFoundChangeDto);

                            TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto=tdHtMembInvoiceHistoryRepository.findMembInvoiceHisById(tdHtMembFoundChangeDto.getMemb_ivc_his_id());
                            tdHtMembInvoiceHistoryDto.setState(1);
                            tdHtMembInvoiceHistoryDto.setMemb_ivc_his_pay_time(new Timestamp(System.currentTimeMillis()));
                            tdHtMembInvoiceHistoryRepository.updateMembInvoiceHisById(tdHtMembInvoiceHistoryDto);
                        }
                    }

                    if (member != null) {
                        member.setMemb_balance(tdHtMemberDto.getMemb_balance());
                        member.setMemb_score(tdHtMemberDto.getMemb_score());
                        MemcachedUtils.replace(tdHtMemberDto.getToken(), member, new java.sql.Date(20 * 24 * 60 * 60 * 1000));
                    }
                } else {
                    retInfo.setMark("1");
                }
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return retInfo;
    }

    /**
     * @param mfchg_number
     * @return RetInfo
     * @Purpose 支付验证
     * @version 3.0
     * @author lizhun
     */
    @Override
    public RetInfo rechargeResult(String mfchg_number) {
        String logInfo = this.getClass().getName() + ":rechargeResult:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMembFoundChangeDto tdHtMembFoundChangeDto = tdHtMembFoundChangeRepository.findfindMemberFoundByNumber(mfchg_number);
            if (tdHtMembFoundChangeDto != null && tdHtMembFoundChangeDto.getMfchg_state() == 0) {
                retInfo.setMark("0");
                retInfo.setTip("成功");
            } else {
                retInfo.setMark("1");
                retInfo.setTip("支付失败,如有疑问联系客服.");
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
     * @return RetInfo
     * @Purpose 订单支付
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public synchronized RetInfo paymentOrder(ExpressOrderPayDto expressOrderPayDto, String token, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":paymentOrder:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto memberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            if (memberDto.getMemb_role() == 1 && memberDto.getMemb_discount().doubleValue() < 1 && expressOrderPayDto.getCoupon_id() > 0) {
                retInfo.setMark("1");
                retInfo.setTip("您是折扣用户，无法使用优惠券.");
            } else {
                TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(expressOrderPayDto.getExp_ord_id());

                if (tdHtExpressOrderDto.getExp_ord_state() == 3) {
                    TdHtCoupon tdHtCoupon = new TdHtCoupon();

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
                            if(tdHtCoupon.getExpress_id() > 0 && tdHtCoupon.getExpress_id() != tdHtExpressOrderDto.getExpress_id()){
                                couponUse = false;
                                retInfo.setMark("1");
                                retInfo.setTip("该优惠券无法优惠该快递公司.");
                            }else if(tdHtCoupon.getRegion_id() > 0 && tdHtCoupon.getRegion_id() != tdHtExpressOrderDto.getAdd_region()){
                                couponUse = false;
                                retInfo.setMark("1");
                                retInfo.setTip("该优惠券无法在该地区使用.");
                            }else if(tdHtCoupon.getMemb_type() > 0 && tdHtCoupon.getMemb_type() != 2){
                                couponUse = false;
                                retInfo.setMark("1");
                                retInfo.setTip("该优惠券无法在app端使用.");
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
                                tdHtMembFoundChangeDto.setMfchg_type(2);
                                tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                                //更新订单状态
                                TdHtExpressOrderDto expressOrder = new TdHtExpressOrderDto();
                                expressOrder.setExp_ord_id(expressOrderPayDto.getExp_ord_id());
                                expressOrder.setExp_ord_state(4);
                                expressOrder.setExp_ord_pay_amount(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                                expressOrder.setPay_time(new Date(nowDate));
                                logger.error("======支付时:" + tdHtExpressOrderDto.getExp_ord_amount() + "======");
                                logger.error("======支付时:" + payMoney + "======");
                                expressOrder.setCoupon_amount(new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()).subtract(payMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
                                logger.error("======支付时:" + new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()).subtract(payMoney) + "======");
                                logger.error("======支付时:" + new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()).subtract(payMoney).setScale(2, BigDecimal.ROUND_HALF_UP) + "======");
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

                                if (memberDto.getMemb_discount().intValue() == 1) {
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

                                //更新优惠券状态
                                if (tdHtCoupon != null) {
                                    tdHtCoupon.setState(1);
                                    tdHtCouponRepository.updateUseCoupon(tdHtCoupon);
                                }


                                PushDto pushDto = tdHtAgentRepository.findAgentPushInfo(tdHtExpressOrderDto.getAgent_id());

                                if (pushDto.getPush_type() == 1) {
                                    PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有揽收的快件已支付.", "4", "", pushDto.getApp_version());
                                } else if (pushDto.getPush_type() == 2) {
                                    PushUtil.pushIosAgent(pushDto.getPush_token(), "您有揽收的快件已支付.", "4", "default", pushDto.getApp_version());
                                }

                                retInfo.setMark("0");
                                retInfo.setTip("支付成功");
                                MemcachedUtils.replace(token, memberDto, new java.sql.Date(20 * 24 * 60 * 60 * 1000));
                            }
                        } else {
                            tdHtMembFoundChangeDto.setMfchg_front_amount(memberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_back_amount(memberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_type(3);
                            tdHtMembFoundChangeDto.setMfchg_state(1);
                            tdHtMembFoundChangeDto.setMfchg_change_amount(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
                            tdHtMembFoundChangeDto.setMfchg_channel(expressOrderPayDto.getPay_type());
                            tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                            Pingpp.apiKey = Constant.Live_Secret_Key;
                            Map<String, Object> chargeParams = new HashMap<String, Object>();
                            chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());
                            chargeParams.put("amount", payMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
                            Map<String, String> app = new HashMap<String, String>();
                            app.put("id", Constant.MEMBER_APP_ID);
                            chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());
                            chargeParams.put("app", app);
                            if (tdHtMembFoundChangeDto.getMfchg_channel() == 1) {
                                chargeParams.put("channel", "wx");  //微信wx
                            }
                            if (tdHtMembFoundChangeDto.getMfchg_channel() == 2) {
                                chargeParams.put("channel", "alipay");  //支付宝alipay
                            }
                            chargeParams.put("currency", "cny");
                            chargeParams.put("client_ip", Ip.getIpAddr(request));
                            chargeParams.put("subject", "订单支付");
                            chargeParams.put("body", "订单支付");
                            Charge charge = Charge.create(chargeParams);
                            retInfo.setMark("0");
                            retInfo.setTip("下单成功");
                            Map<String, Object> map = new HashMap<>();
                            map.put("charge", charge);
                            retInfo.setObj(map);
                        }
                    }
                } else {
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
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询用户的收支记录
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findMemberFound(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findMemberFound:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto memberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            Page<MemberFinancialDetailsDto> page = new Page<MemberFinancialDetailsDto>(pageDto.getPage_size(), pageDto.getPage_no());
            int count = tdHtMembFoundChangeRepository.findMemberFoundSize(memberDto.getMemb_id());
            pageDto.setLimit_str(page.getLimitCriterion());
            pageDto.setId(memberDto.getMemb_id());
            List<MemberFinancialDetailsDto> memberFinancialDetailsDtoList = tdHtMembFoundChangeRepository.findMemberFoundPage(pageDto);
            page.setTotalCount(count);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_pages", page.getTotalPages());
            map.put("page_no", page.getPageNo());
            map.put("items", memberFinancialDetailsDtoList);

            retInfo.setMark("0");
            retInfo.setTip("成功");
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
     * @param expressOrderPayDto
     * @return RetInfo
     * @Purpose 代理人代付订单
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo replacePayOrder(ExpressOrderPayDto expressOrderPayDto, String token, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":replacePayOrder:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(expressOrderPayDto.getExp_ord_id());

            if (tdHtExpressOrderDto.getExp_ord_state() == 3) {
                TdHtMemberDto memberDto = tdHtMemberRepository.findMembByMembId(tdHtExpressOrderDto.getMemb_id());

                long nowDate = System.currentTimeMillis();
                TdHtMembFoundChangeDto tdHtMembFoundChangeDto = new TdHtMembFoundChangeDto();
                BigDecimal payMoney = new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                if (tdHtExpressOrderDto.getDoor_end_time().getTime() >= tdHtExpressOrderDto.getCollect_time().getTime()) {
                    payMoney = payMoney.add(new BigDecimal(tdHtExpressOrderDto.getExp_ord_gratuity())).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                tdHtMembFoundChangeDto.setExp_ord_id(expressOrderPayDto.getExp_ord_id());
                tdHtMembFoundChangeDto.setMemb_id(tdHtExpressOrderDto.getMemb_id());
                tdHtMembFoundChangeDto.setMfchg_time(new Timestamp(nowDate));
                tdHtMembFoundChangeDto.setMfchg_month(CommonUtil.getMonth());
                tdHtMembFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub());
                tdHtMembFoundChangeDto.setMfchg_name("代付");
                tdHtMembFoundChangeDto.setMfchg_front_amount(memberDto.getMemb_balance());
                tdHtMembFoundChangeDto.setMfchg_back_amount(memberDto.getMemb_balance());
                tdHtMembFoundChangeDto.setMfchg_type(3);
                tdHtMembFoundChangeDto.setMfchg_state(1);
                tdHtMembFoundChangeDto.setMfchg_change_amount(payMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
                tdHtMembFoundChangeDto.setMfchg_channel(expressOrderPayDto.getPay_type());
                tdHtMembFoundChangeDto.setMemb_mon_bill_id(0);
                tdHtMembFoundChangeDto.setMemb_goods_id(0);
                tdHtMembFoundChangeDto.setMemb_ivc_his_id(0);
                tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                tdHtCouponRepository.updateCouponByOrder(expressOrderPayDto.getExp_ord_id());

                Pingpp.apiKey = Constant.Live_Secret_Key;
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());
                chargeParams.put("amount", payMoney.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
                Map<String, String> app = new HashMap<String, String>();
                app.put("id", Constant.AGENT_APP_ID);
                chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());
                chargeParams.put("app", app);
                if (tdHtMembFoundChangeDto.getMfchg_channel() == 1) {
                    chargeParams.put("channel", "wx");  //微信wx
                }
                if (tdHtMembFoundChangeDto.getMfchg_channel() == 2) {
                    chargeParams.put("channel", "alipay");  //支付宝alipay
                }
                chargeParams.put("currency", "cny");
                chargeParams.put("client_ip", Ip.getIpAddr(request));
                chargeParams.put("subject", "订单支付");
                chargeParams.put("body", "订单支付");
                Charge charge = Charge.create(chargeParams);

                retInfo.setMark("0");
                retInfo.setTip("下单成功");
                Map<String, Object> map = new HashMap<>();
                map.put("charge", charge);
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("该订单您已经代付.");
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
