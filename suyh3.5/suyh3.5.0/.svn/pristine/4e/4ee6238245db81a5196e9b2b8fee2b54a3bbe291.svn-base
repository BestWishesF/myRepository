package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.thirdparty.ThirdPartyService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-02.
 */
public class ThirdPartyInit {

    private static ThirdPartyService thirdPartyService = (ThirdPartyService) SpringInfo.getBean("thirdPartyService");
    private static Log logger = LogFactory.getLog(ThirdPartyInit.class);

    public static void getThirdPartyInfo() {
        List<TsHtThirdDto> tsHtThirdDtoList = thirdPartyService.findTsHtThird();
        Map<String, TsHtThirdDto> map = new HashedMap();
        for (int i = 0 ; i < tsHtThirdDtoList.size() ; i ++) {
            map.put("" + tsHtThirdDtoList.get(i).getThird_id(), tsHtThirdDtoList.get(i));
        }

        MemcachedUtils.add(MemcachedKey.THIRD_PARTY_LIST, tsHtThirdDtoList, new Date(0));
        MemcachedUtils.add(MemcachedKey.THIRD_PARTY_MAP, map, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.THIRD_PARTY_LIST);
        MemcachedUtils.delete(MemcachedKey.THIRD_PARTY_MAP);
        getThirdPartyInfo();
        logger.info("********************end flush**************");
    }

}
