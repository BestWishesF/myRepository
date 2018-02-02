package cn.hotol.app.service.config;

import java.util.Map;

/**
 * Created by Administrator on 2017-03-16.
 */
public interface ConfigService {

    /**
     * @param
     * @return Map<String, Object>
     * @Purpose 查询所有的数据配置
     * @version 3.0
     * @author lubin
     */
    public Map<String, Object> findAllDataConfig();

}
