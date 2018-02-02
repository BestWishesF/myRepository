package cn.hotol.app.service.member.score;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.score.TdHtMembScoreDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TdHtMembScoreRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/20.
 */
public class ScoreServiceImpl implements  ScoreService {
    private static Logger logger = Logger.getLogger(ScoreServiceImpl.class);
    private TdHtMembScoreRepository tdHtMembScoreRepository;

    @Override
    public RetInfo memberScorePage(int memb_id, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":memberScorePage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {

            int totalRecord = tdHtMembScoreRepository.findMemberScoreSize(memb_id);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.put("memb_id", memb_id);
                List<TdHtMembScoreDto> tdHtMembScoreDtos = tdHtMembScoreRepository.findMemberScorePage(map);
                map.put("currentPage", currentPage);
                map.put("scores", tdHtMembScoreDtos);
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

    public void setTdHtMembScoreRepository(TdHtMembScoreRepository tdHtMembScoreRepository) {
        this.tdHtMembScoreRepository = tdHtMembScoreRepository;
    }
}
