package cn.hotol.app.service.three.invoice.history;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.invoice.TdHtMembInvoiceDto;
import cn.hotol.app.bean.dto.invoice.history.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import cn.hotol.app.repository.TdHtMembFoundChangeRepository;
import cn.hotol.app.repository.TdHtMembInvoiceHistoryRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2017-03-15.
 */

@Service
public class InvoiceHistoryServiceImpl implements InvoiceHistoryService {

    private static Logger logger = Logger.getLogger(InvoiceHistoryServiceImpl.class);

    @Resource
    private TdHtMembInvoiceHistoryRepository tdHtMembInvoiceHistoryRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;

    /**
     * @param token
     * @param pageDto
     * @return RetInfo
     * @Purpose 分页查询用户开票历史记录
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findIvcHisPage(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":saveMembInvoice:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            int count = tdHtMembInvoiceHistoryRepository.findMembInvoiceHisSize(tdHtMemberDto.getMemb_id());
            Page<TdHtMembInvoiceHistoryDto> membIvcHisPage = new Page<TdHtMembInvoiceHistoryDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setId(tdHtMemberDto.getMemb_id());
            pageDto.setLimit_str(membIvcHisPage.getLimitCriterion());
            List<TdHtMembInvoiceHistoryDto> membIvcHisList = tdHtMembInvoiceHistoryRepository.findMembInvoiceHisPage(pageDto);
            List<Map<String, Object>> membIvcHisMapList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < membIvcHisList.size(); i++) {
                TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto = membIvcHisList.get(i);
                Map<String, Object> membIvcHisMap = new HashMap<String, Object>();
                membIvcHisMap.put("memb_ivc_name", "快递服务费");
                membIvcHisMap.put("memb_inc_amount", tdHtMembInvoiceHistoryDto.getMemb_inc_amount());
                membIvcHisMap.put("state", tdHtMembInvoiceHistoryDto.getState());
                membIvcHisMap.put("memb_ivc_his_time", tdHtMembInvoiceHistoryDto.getMemb_ivc_his_time());
                membIvcHisMap.put("memb_ivc_id", tdHtMembInvoiceHistoryDto.getMemb_ivc_id());
                membIvcHisMapList.add(membIvcHisMap);
            }
            membIvcHisPage.setTotalCount(count);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", membIvcHisMapList);
            map.put("total_pages", membIvcHisPage.getTotalPages());
            map.put("page_no", membIvcHisPage.getPageNo());

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
     * @param tdHtMembInvoiceDto
     * @return RetInfo
     * @Purpose 用户重新支付开票邮费
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo rePayInvoice(TdHtMembInvoiceDto tdHtMembInvoiceDto, String token, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":rePayInvoice:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());

            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
            TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto = tdHtMembInvoiceHistoryRepository.findMembIvcHisByIvcId(tdHtMembInvoiceDto.getMemb_ivc_id());
            if (tdHtMembInvoiceHistoryDto == null) {
                retInfo.setMark("1");
                retInfo.setTip("订单不存在.");
            } else {
                if (tdHtMembInvoiceHistoryDto.getState() != 0) {
                    retInfo.setMark("1");
                    retInfo.setTip("已支付或已取消.");
                } else {
                    TdHtMembFoundChangeDto tdHtMembFoundChangeDto = new TdHtMembFoundChangeDto();
                    tdHtMembFoundChangeDto.setExp_ord_id(0);
                    tdHtMembFoundChangeDto.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtMembFoundChangeDto.setMfchg_time(new Timestamp(System.currentTimeMillis()));
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

                            retInfo.setMark("0");
                            retInfo.setTip("下单成功");
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
     * @param tdHtMembInvoiceHistoryDto
     * @return RetInfo
     * @Purpose 用户取消开票申请
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo cancelPayInvoice(TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto) {
        String logInfo = this.getClass().getName() + ":cancelPayInvoice:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMembInvoiceHistoryDto membInvoiceHistoryDto = tdHtMembInvoiceHistoryRepository.findMembIvcHisByIvcId(tdHtMembInvoiceHistoryDto.getMemb_ivc_id());
            if (membInvoiceHistoryDto == null) {
                retInfo.setMark("1");
                retInfo.setTip("订单不存在.");
            } else {
                if (membInvoiceHistoryDto.getState() != 0) {
                    retInfo.setMark("1");
                    retInfo.setTip("已支付或已取消.");
                } else {
                    membInvoiceHistoryDto.setState(3);
                    tdHtMembInvoiceHistoryRepository.updateMembInvoiceHisById(membInvoiceHistoryDto);
                    tdHtExpressOrderRepository.cancelInvoice(tdHtMembInvoiceHistoryDto.getMemb_ivc_his_id());
                    retInfo.setMark("0");
                    retInfo.setTip("取消成功.");
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
