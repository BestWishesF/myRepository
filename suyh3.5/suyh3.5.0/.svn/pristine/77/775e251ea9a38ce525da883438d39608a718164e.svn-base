package cn.hotol.app.service.member.refund;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.refund.TdHtMembRefundChangeDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-04-27.
 */
public class RefundServiceImpl implements RefundService {

    private static Logger logger = Logger.getLogger(RefundServiceImpl.class);

    private TdHtMembRefundChangeRepository tdHtMembRefundChangeRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    private TdHtMemberRepository tdHtMemberRepository;
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;

    /**
     * @Purpose   分页查找申请退款记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     refund_state  退款状态
     * @return    RetInfo
     */
    @Override
    public RetInfo findRefundPage(int currentPage, int pageSize, int refund_state) {
        String logInfo = this.getClass().getName() + ":findRefundPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("refund_state", refund_state);
            int totalRecord = tdHtMembRefundChangeRepository.findRefundSize(params);
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtMembRefundChangeDto> tdHtMembRefundChangeDtos = tdHtMembRefundChangeRepository.findRefundPage(map);

                for (int i = 0 ; i < tdHtMembRefundChangeDtos.size() ; i++){
                    TdHtMembRefundChangeDto tdHtMembRefundChangeDto = tdHtMembRefundChangeDtos.get(i);

                    TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMemberById(tdHtMembRefundChangeDto.getMemb_id());

                    tdHtMembRefundChangeDto.setMemb_phone(tdHtMemberDto.getMemb_phone());
                }

                map.put("currentPage", currentPage);
                map.put("refunds", tdHtMembRefundChangeDtos);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   处理订单退款
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     tdHtMembRefundChangeDto
     * @return    RetInfo
     */
    @Override
    public RetInfo handleRefundExpOrd(TdHtMembRefundChangeDto tdHtMembRefundChangeDto) {
        String logInfo = this.getClass().getName() + ":handleRefundExpOrd:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMembRefundChangeDto membRefundChangeDto = tdHtMembRefundChangeRepository.findRefundById(tdHtMembRefundChangeDto.getRefund_id());
            if(membRefundChangeDto.getRefund_state() == 1){

                TdHtMembFoundChangeDto tdHtMembFoundChangeDto = tdHtMembFoundChangeRepository.findFoundById(membRefundChangeDto.getMfchg_id());
                if(tdHtMembFoundChangeDto.getMfchg_channel() == 3){
                    TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMemberById(tdHtMembFoundChangeDto.getMemb_id());

                    TdHtMembFoundChangeDto membFoundChangeDto = new TdHtMembFoundChangeDto();
                    membFoundChangeDto.setMemb_id(tdHtMembFoundChangeDto.getMemb_id());
                    membFoundChangeDto.setMfchg_change_amount(tdHtMembFoundChangeDto.getMfchg_change_amount());
                    membFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                    membFoundChangeDto.setMfchg_back_amount(tdHtMemberDto.getMemb_balance().add(tdHtMembFoundChangeDto.getMfchg_change_amount()));
                    membFoundChangeDto.setMfchg_type(1);
                    membFoundChangeDto.setMfchg_time(new Timestamp(System.currentTimeMillis()));
                    membFoundChangeDto.setMfchg_month(CommonUtil.getMonth());
                    membFoundChangeDto.setMfchg_name("订单钱包支付退款");
                    membFoundChangeDto.setMfchg_state(0);
                    membFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub(new Date(System.currentTimeMillis())));
                    membFoundChangeDto.setMfchg_channel(3);
                    membFoundChangeDto.setExp_ord_id(tdHtMembFoundChangeDto.getExp_ord_id());
                    membFoundChangeDto.setMemb_mon_bill_id(0);
                    membFoundChangeDto.setMemb_ivc_his_id(0);
                    membFoundChangeDto.setMemb_goods_id(0);

                    tdHtMembFoundChangeRepository.insertMemberFoundChange(membFoundChangeDto);

                    tdHtMemberDto.setMemb_balance(tdHtMemberDto.getMemb_balance().add(tdHtMembFoundChangeDto.getMfchg_change_amount()));

                    tdHtMemberRepository.updateMemberBalance(tdHtMemberDto);
                }else {

                }

                tdHtMembRefundChangeRepository.updateRefundState(tdHtMembRefundChangeDto);

                TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(membRefundChangeDto.getExp_ord_id());

                tdHtExpressOrderDto.setExp_ord_state(8);

                List<TdHtExpressOrderCollectDto> collectDtoList = tdHtExpressCollectRepository.findExpressCollectByExpOrdId(membRefundChangeDto.getExp_ord_id());
                for (int i = 0 ; i < collectDtoList.size() ; i++){
                    TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);
                    collectDto.setExp_ord_clt_state(7);
                    tdHtExpressCollectRepository.updateExpOrdCltState(collectDto);
                }

                tdHtExpressOrderRepository.updateExpOrdState(tdHtExpressOrderDto);

                retInfo.setMark("0");
                retInfo.setTip("成功");
            }else {
                retInfo.setMark("1");
                retInfo.setTip("已处理.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtMembRefundChangeRepository(TdHtMembRefundChangeRepository tdHtMembRefundChangeRepository) {
        this.tdHtMembRefundChangeRepository = tdHtMembRefundChangeRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }

    public void setTdHtExpressCollectRepository(TdHtExpressCollectRepository tdHtExpressCollectRepository) {
        this.tdHtExpressCollectRepository = tdHtExpressCollectRepository;
    }

    public void setTdHtMemberRepository(TdHtMemberRepository tdHtMemberRepository) {
        this.tdHtMemberRepository = tdHtMemberRepository;
    }

    public void setTdHtMembFoundChangeRepository(TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository) {
        this.tdHtMembFoundChangeRepository = tdHtMembFoundChangeRepository;
    }
}
