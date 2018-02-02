package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.price.TsHtRegionPriceDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.price.ExpressPriceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class ExpRegionPriceInit {

    private static ExpressPriceService expressPriceService = (ExpressPriceService) SpringInfo.getBean("expressPriceService");
    private static Log logger = LogFactory.getLog(ExpRegionPriceInit.class);

    public static void getExpRegionPrice() {
        Map<String, List<TsHtRegionPriceDto>> regionPriceMap = expressPriceService.findExpRegionPrice();
        MemcachedUtils.add(MemcachedKey.EXP_REGION_PRICE, regionPriceMap, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.EXP_REGION_PRICE);
        getExpRegionPrice();
        logger.info("********************end flush**************");
    }

}
