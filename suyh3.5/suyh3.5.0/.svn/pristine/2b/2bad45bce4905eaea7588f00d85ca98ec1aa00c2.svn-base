package cn.hotol.app.service.three.timer;

import cn.hotol.app.bean.dto.push.PushDto;
import cn.hotol.app.common.util.PushUtil;
import cn.hotol.app.repository.TdHtExpressCollectRepository;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by LuBin
 * Date 2017-01-07.
 */
public class ExpressOverdueTimer {

    private static Logger logger = Logger.getLogger(ExpressOverdueTimer.class);

    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;

    /**
     * @return RetInfo
     * @Purpose 超时件定时器
     * @version 3.0
     * @author lubin
     */
    @Transactional
    public void expressOrderOverdue() {
        String logInfo = this.getClass().getName() + ":expressOrderOverdue:";
        logger.info("======" + logInfo + "begin======");
        try {
            long date = System.currentTimeMillis() - 30 * 60 * 1000;
            List<PushDto> pushDtoList = tdHtExpressOrderRepository.findOvertimeOrder(new Date(date));
            List<Integer> orderIds = new ArrayList<Integer>();
            List<PushDto> pushDtos = new ArrayList<PushDto>();
            Map<String, PushDto> map = new HashMap<String, PushDto>();
            for (int i = 0; i < pushDtoList.size(); i++) {
                PushDto pushDto = pushDtoList.get(i);
                orderIds.add(pushDto.getExp_ord_id());
                if (pushDto.getPush_token() != null && !"".equals(pushDto.getPush_token().trim())) {
                    map.put(pushDto.getPush_token(), pushDto);
                }
            }
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next().toString();
                pushDtos.add(map.get(key));
            }

            if (orderIds.size() > 0) {
                tdHtExpressOrderRepository.updateOvertimeOrder(orderIds);
                tdHtExpressCollectRepository.updateOvertimeCollect(orderIds);
            }

            for (int i = 0; i < pushDtos.size(); i++) {
                PushDto pushDto = pushDtos.get(i);
                if (pushDto.getPush_type() == 1) {
                    PushUtil.pushAndroidMember(pushDto.getPush_token(), "您有快件已超时,请重新提交订单.", "7", "", pushDto.getApp_version());
                } else if (pushDto.getPush_type() == 2) {
                    PushUtil.pushIosMember(pushDto.getPush_token(), "您有快件已超时,请重新提交订单.", "7", "default", pushDto.getApp_version());
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
