package cn.hotol.app.service.comment;

import cn.hotol.app.base.RetInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface CommentService {
    /**
     * @Purpose  评论分页
     * @version  3.0
     * @author   lizhun
     * @param    currentPage
     * @param    pageSize
     * @return   RetInfo
     */
    public RetInfo commentPage(int currentPage, int pageSize, HttpServletRequest request);
}
