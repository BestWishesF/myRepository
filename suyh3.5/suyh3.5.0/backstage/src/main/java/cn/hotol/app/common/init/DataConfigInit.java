package cn.hotol.app.common.init;

import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.article.ArticleService;
import cn.hotol.app.service.config.ConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public class DataConfigInit {

    private static ConfigService configService = (ConfigService) SpringInfo.getBean("configService");
    private static Log logger = LogFactory.getLog(DataConfigInit.class);

    public static void getDataConfig() {
        Map<String, Object> dataConfigMap = configService.findAllDataConfig();
        MemcachedUtils.add(MemcachedKey.DATA_CONFIG_MAP, dataConfigMap, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.DATA_CONFIG_MAP);
        getDataConfig();
        logger.info("********************end flush**************");
    }

}
