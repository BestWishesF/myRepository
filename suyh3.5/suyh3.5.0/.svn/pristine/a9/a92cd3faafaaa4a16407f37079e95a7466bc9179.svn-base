package cn.hotol.app.service.three.dictionary;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private static Logger logger = Logger.getLogger(DictionaryServiceImpl.class);

    /**
     * @return RetInfo
     * @Purpose 根据字典类别查询字典数据
     * @version 3.0
     * @author lubin
     * @Param code_type
     */
    @Override
    public RetInfo findDictByType(String code_type) {
        String logInfo = this.getClass().getName() + ":findDictByType:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取得字典数据
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);

            retInfo.setMark("0");
            retInfo.setTip("字典数据获取成功.");
            retInfo.setObj(dataDictionary.get(code_type));
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @return RetInfo
     * @Purpose 获取开通快递公司数据
     * @version 3.0
     * @author lubin
     * @Param courierDto
     */
    @Override
    public RetInfo findExpressCompany(CourierDto courierDto) {
        String logInfo = this.getClass().getName() + ":findExpressCompany:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> openRegionExpCompany = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.OPEN_REGION_EXP_COMPANY);

            if (courierDto.getRegion_id() == 0) {
                retInfo.setMark("1");
                retInfo.setTip("请先选择发件地址.");
            } else {
                if(expressCompany.get(courierDto.getRegion_id())==null){
                    retInfo.setMark("1");
                    retInfo.setTip("该地区还未开通寄件服务.");
                }else{
                    retInfo.setObj(expressCompany.get(courierDto.getRegion_id()));
                    retInfo.setMark("0");
                    retInfo.setTip("快递公司数据获取成功.");
                }
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @return RetInfo
     * @Purpose 获取上门时间数据
     * @version 3.0
     * @author lubin
     * @Param code_type
     */
    @Override
    public RetInfo findCollectTime(String code_type, int version) {
        String logInfo = this.getClass().getName() + ":findCollectTime:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            List<TsHtDictDto> collect_time = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.COLLECT_TIME);

            if (Constant.COLLECT_TIME.equals(code_type)) {
                List<TsHtDictDto> toDayTimeList = new ArrayList<TsHtDictDto>();
                for (int i = 0; i < collect_time.size(); i++) {
                    TsHtDictDto tsHtDictDto = collect_time.get(i);
                    if (tsHtDictDto.getCode_name().indexOf("-") > 0) {
                        if(version > 6){
                            String startTime = tsHtDictDto.getCode_name().split("-")[0];
                            if (CommonUtil.getHour(startTime) > new Date().getHours()) {
                                toDayTimeList.add(tsHtDictDto);
                            }
                        }else {
                            String endTime = tsHtDictDto.getCode_name().split("-")[1];
                            if (CommonUtil.getHour(endTime) > new Date().getHours()) {
                                toDayTimeList.add(tsHtDictDto);
                            }
                        }
                    }
                }
                if (toDayTimeList.size() > 0) {
                    retInfo.setMark("0");
                    retInfo.setTip("上门时间数据获取成功.");
                    retInfo.setObj(toDayTimeList);
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("代理人已经下班了，请预约明天.");
                }
            } else {
                retInfo.setMark("0");
                retInfo.setTip("上门时间数据获取成功.");
                retInfo.setObj(collect_time);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
