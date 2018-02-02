package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.banner.TsHtBannerIndex;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.banner.BannerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by lizhun on 15/12/24.
 */
public class BannerInit {
    private static BannerService bannerService = (BannerService) SpringInfo.getBean("bannerService");
    private static Log logger = LogFactory.getLog(BannerInit.class);

    public static void getBannerInfo() {
        List<TsHtBannerIndex> tsHtBannerIndices = bannerService.findTsHtBannerIndex();
        MemcachedUtils.add(MemcachedKey.BANNER_INDEX, tsHtBannerIndices, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.BANNER_INDEX);
        getBannerInfo();
        logger.info("********************end flush**************");
    }
}
