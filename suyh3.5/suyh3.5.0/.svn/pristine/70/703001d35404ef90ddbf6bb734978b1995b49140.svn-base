package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.feedback.TdHtFeedbackDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 15/12/25.
 */
public interface TdHtFeedbackRepository {

    /**
     * @Purpose  根据类别查找用户反馈
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findFeedbackByTypeSize(Map<String, Object> map);
    /**
     * @Purpose  根据类别查找用户反馈分页
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtFeedbackDto>
     */
    public List<TdHtFeedbackDto> findFeedbackByTypePage(Map<String, Object> map);

}
