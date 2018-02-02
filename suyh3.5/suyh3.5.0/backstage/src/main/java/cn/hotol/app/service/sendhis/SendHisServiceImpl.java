package cn.hotol.app.service.sendhis;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.sendhis.TwSmsSendHisDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TwSmsSendHisRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public class SendHisServiceImpl implements SendHisService {
    private static Logger logger = Logger.getLogger(SendHisServiceImpl.class);
    private TwSmsSendHisRepository twSmsSendHisRepository;

    @Override
    public RetInfo sendHisPage(int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":sendHisPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {

            int totalRecord = twSmsSendHisRepository.findSendHisSize();//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                List<TwSmsSendHisDto> twSmsSendHisDtos = twSmsSendHisRepository.findSendHisPage(map);
                map.put("currentPage", currentPage);
                map.put("sends", twSmsSendHisDtos);

                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                Map<String, Object> params = new HashMap<String, Object>();
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

    public void setTwSmsSendHisRepository(TwSmsSendHisRepository twSmsSendHisRepository) {
        this.twSmsSendHisRepository = twSmsSendHisRepository;
    }
}
