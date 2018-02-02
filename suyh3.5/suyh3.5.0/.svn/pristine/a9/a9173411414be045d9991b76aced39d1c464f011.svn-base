package cn.hotol.app.service.three.feedback;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.feedback.TdHtFeedbackDto;
import cn.hotol.app.repository.TdHtFeedbackRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizhun on 2016/12/21.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private static Logger logger = Logger.getLogger(FeedbackServiceImpl.class);
    @Resource
    private TdHtFeedbackRepository tdHtFeedbackRepository;

    @Override
    public RetInfo insertFeedback(TdHtFeedbackDto tdHtFeedbackDto) {
        String logInfo = this.getClass().getName() + ":recharge:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tdHtFeedbackRepository.insertTdHtFeedback(tdHtFeedbackDto);

            retInfo.setMark("0");
            retInfo.setTip("成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
