package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.express.sdk.ExpressSdkService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-10.
 */
public class ExpressSdkInit {

    private static ExpressSdkService expressSdkService = (ExpressSdkService) SpringInfo.getBean("expressSdkService");
    private static Log logger = LogFactory.getLog(ExpressSdkInit.class);

    public static void getExpressSdk() {
        Map<String, Map<String, TsHtExpressSdkDto>> expressSdkMap = expressSdkService.findExpressSdk();
        MemcachedUtils.add(MemcachedKey.EXPRESS_SDK_MAP, expressSdkMap, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.EXPRESS_SDK_MAP);
        getExpressSdk();
        logger.info("********************end flush**************");
    }

}
