package cn.hotol.app.service.feedback;

import cn.hotol.app.base.RetInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface FeedbackService {
    /**
     * @Purpose  意见反馈分页
     * @version  3.0
     * @author   lizhun
     * @param    currentPage
     * @param    pageSize
     * @return   RetInfo
     */
    public RetInfo feedbackPage(int user_cate,int currentPage, int pageSize, HttpServletRequest request);
}
