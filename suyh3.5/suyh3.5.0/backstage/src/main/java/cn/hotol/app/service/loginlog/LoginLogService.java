package cn.hotol.app.service.loginlog;

import cn.hotol.app.base.RetInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface LoginLogService {
    /**
     * @Purpose  分页查找登录记录
     * @version  3.0
     * @author   lizhun
     * @param    user_type,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo loginLogPage(int user_type,int currentPage, int pageSize, HttpServletRequest request);
}
