package cn.hotol.app.common.init;

import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.admin.divide.DivideService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-08.
 */
public class DivideInit {

    private static DivideService divideService = (DivideService) SpringInfo.getBean("divideService");
    private static Log logger = LogFactory.getLog(DivideInit.class);

    public static void getDivide() {
        Map<String, Object> divideMap = divideService.findAllDivide();
        MemcachedUtils.add(MemcachedKey.DIVIDE_DATA_MAP, divideMap, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.DIVIDE_DATA_MAP);
        getDivide();
        logger.info("********************end flush**************");
    }

}
