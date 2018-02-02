package cn.hotol.app.service.three.appupdate;

import cn.hotol.app.base.RetInfo;

import java.util.Map;

/**
 * Created by lizhun on 2017/3/9.
 */
public interface AppUpdateService {
    /**
     * @Purpose  根据APP类别和系统类别查找最新版本
     * @version  3.2
     * @author   lizhun
     * @param    map
     * @return   RetInfo
     */
    public RetInfo findAppUpdateByType(Map<String, Object> map);
}
