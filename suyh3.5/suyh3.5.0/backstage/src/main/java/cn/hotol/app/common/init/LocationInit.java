package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.location.LocationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class LocationInit {

    private static LocationService locationService = (LocationService) SpringInfo.getBean("locationService");
    private static Log logger = LogFactory.getLog(LocationInit.class);

    public static void getLocationInfo() {
        List<ProvinceDto> provinceDtoList = locationService.findAllProvincialCity();
        MemcachedUtils.add(MemcachedKey.PROVINCIAL_CITY, provinceDtoList, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.PROVINCIAL_CITY);
        getLocationInfo();
        logger.info("********************end flush**************");
    }

}
