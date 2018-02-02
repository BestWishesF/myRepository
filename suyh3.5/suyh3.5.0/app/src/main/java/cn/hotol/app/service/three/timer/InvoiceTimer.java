package cn.hotol.app.service.three.timer;

import cn.hotol.app.bean.dto.invoice.history.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import cn.hotol.app.repository.TdHtMembInvoiceHistoryRepository;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-22.
 */
public class InvoiceTimer {

    private static Logger logger = Logger.getLogger(InvoiceTimer.class);

    @Resource
    private TdHtMembInvoiceHistoryRepository tdHtMembInvoiceHistoryRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;

    /**
     * @return RetInfo
     * @Purpose 开票申请1天没有支付自动取消
     * @version 3.0
     * @author lubin
     */
    @Transactional
    public void cancelInvoicePay(){
        String logInfo = this.getClass().getName() + ":cancelInvoicePay:";
        logger.info("======" + logInfo + "begin======");
        try {
            long date = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
            TdHtMembInvoiceHistoryDto membInvoiceHistoryDto=new TdHtMembInvoiceHistoryDto();
            membInvoiceHistoryDto.setState(0);
            membInvoiceHistoryDto.setMemb_ivc_his_time(new Timestamp(date));
            List<TdHtMembInvoiceHistoryDto> tdHtMembInvoiceHistoryDtoList = tdHtMembInvoiceHistoryRepository.findMembIvcHisByTime(membInvoiceHistoryDto);
            if(tdHtMembInvoiceHistoryDtoList.size()>0){
                List<Integer> membIvcHisIds=new ArrayList<Integer>();
                for (int i=0;i<tdHtMembInvoiceHistoryDtoList.size();i++){
                    TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto=tdHtMembInvoiceHistoryDtoList.get(i);
                    membIvcHisIds.add(tdHtMembInvoiceHistoryDto.getMemb_ivc_his_id());
                }
                tdHtExpressOrderRepository.batchCancelInvoice(membIvcHisIds);
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("state",3);
                map.put("memb_ivc_his_ids",membIvcHisIds);
                tdHtMembInvoiceHistoryRepository.batchUpdateMembIvcHisState(map);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
