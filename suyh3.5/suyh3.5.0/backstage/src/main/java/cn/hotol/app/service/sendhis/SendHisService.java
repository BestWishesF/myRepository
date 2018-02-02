package cn.hotol.app.service.sendhis;

import cn.hotol.app.base.RetInfo;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface SendHisService {
    /**
     * @Purpose  分页查找短信发送记录
     * @version  3.0
     * @author   lizhun
     * @param    currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo sendHisPage( int currentPage, int pageSize);
}
