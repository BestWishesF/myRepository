package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.dict.DictInitialsSortDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.dictionary.DictionaryService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class DictionaryInit {

    private static DictionaryService dictionaryService = (DictionaryService) SpringInfo.getBean("dictionaryService");
    private static Log logger = LogFactory.getLog(DictionaryInit.class);

    public static void getDictionaryInfo() {
        Map<String, Object> dataDictionary = dictionaryService.findDictByType();
        Map<Integer, Object> expressMap = dictionaryService.findOpenExpressCompany();
        List<TsHtDictDto> CollectTimeList = dictionaryService.findCollectTime();
        List<TsHtDictDto> tsHtDictDtos = dictionaryService.findDicts();
        List<CourierDto> courierDtoList = dictionaryService.findCourierDto();
        Map<String, CourierDto> courierMap = new HashedMap();
        for (int i = 0; i < courierDtoList.size(); i++) {
            String express_id = "" + courierDtoList.get(i).getExpress_id();
            String region_id = "" + courierDtoList.get(i).getRegion_id();
            courierMap.put(express_id + "#" + region_id, courierDtoList.get(i));
        }
        List<TsHtDictDto> openRegionExpCompany = dictionaryService.findOpenRegionExpCompany();
        Map<String, TsHtDictDto> map = new HashedMap();
        for (int i = 0; i < tsHtDictDtos.size(); i++) {
            map.put("" + tsHtDictDtos.get(i).getDict_id(), tsHtDictDtos.get(i));
        }
        Map<String, TsHtDictIndex> dictIndexMap = dictionaryService.findAllDictIndex();
        Map<String, List<DictInitialsSortDto>> dictInitialsSortList = dictionaryService.findAllDictByInitials();

        MemcachedUtils.add(MemcachedKey.DICTS_MAP, map, new Date(0));
        MemcachedUtils.add(MemcachedKey.DATA_DICTIONARY, dataDictionary, new Date(0));
        MemcachedUtils.add(MemcachedKey.EXPRESS_COMPANY, expressMap, new Date(0));
        MemcachedUtils.add(MemcachedKey.COLLECT_TIME, CollectTimeList, new Date(0));
        MemcachedUtils.add(MemcachedKey.COURIER_DTO_LIST, courierDtoList, new Date(0));
        MemcachedUtils.add(MemcachedKey.OPEN_REGION_EXP_COMPANY, openRegionExpCompany, new Date(0));
        MemcachedUtils.add(MemcachedKey.COURIER_DTO_MAP, courierMap, new Date(0));
        MemcachedUtils.add(MemcachedKey.DICT_INDEX_MAP, dictIndexMap, new Date(0));
        MemcachedUtils.add(MemcachedKey.DICT_INITIALS_SORT_MAP, dictInitialsSortList, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.DATA_DICTIONARY);
        MemcachedUtils.delete(MemcachedKey.EXPRESS_COMPANY);
        MemcachedUtils.delete(MemcachedKey.COLLECT_TIME);
        MemcachedUtils.delete(MemcachedKey.DICTS_MAP);
        MemcachedUtils.delete(MemcachedKey.COURIER_DTO_LIST);
        MemcachedUtils.delete(MemcachedKey.OPEN_REGION_EXP_COMPANY);
        MemcachedUtils.delete(MemcachedKey.COURIER_DTO_MAP);
        MemcachedUtils.delete(MemcachedKey.DICT_INDEX_MAP);
        MemcachedUtils.delete(MemcachedKey.DICT_INITIALS_SORT_MAP);
        getDictionaryInfo();
        logger.info("********************end flush**************");
    }

}
