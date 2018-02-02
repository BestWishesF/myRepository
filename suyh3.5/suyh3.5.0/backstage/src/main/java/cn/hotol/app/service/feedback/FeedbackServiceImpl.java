package cn.hotol.app.service.feedback;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.feedback.TdHtFeedbackDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TdHtFeedbackRepository;
import cn.hotol.app.repository.TsHtDictRepository;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public class FeedbackServiceImpl implements FeedbackService{
    private static Logger logger = Logger.getLogger(FeedbackServiceImpl.class);
    private TdHtFeedbackRepository tdHtFeedbackRepository;
    private TsHtDictRepository tsHtDictRepository;

    @Override
    public RetInfo feedbackPage(int user_cate, int currentPage, int pageSize, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":feedbackPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("user_cate", user_cate);

            params.put("province", tdHtAdminDto.getProvince_id());
            params.put("city", tdHtAdminDto.getCity_id());
            params.put("region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            if(user_cate == 1){
                params.put("table_name", "td_ht_member");
                params.put("field_name", "memb_id");
                params.put("region_name", "memb_register_region");
            }
            if(user_cate == 2){
                params.put("table_name", "td_ht_agent");
                params.put("field_name", "agent_id");
                params.put("region_name", "area_id");
            }

            int totalRecord = tdHtFeedbackRepository.findFeedbackByTypeSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtFeedbackDto> tdHtFeedbackDtos = tdHtFeedbackRepository.findFeedbackByTypePage(map);
                map.put("currentPage", currentPage);
                map.put("feedbacks", tdHtFeedbackDtos);
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

    public void setTdHtFeedbackRepository(TdHtFeedbackRepository tdHtFeedbackRepository) {
        this.tdHtFeedbackRepository = tdHtFeedbackRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }
}
