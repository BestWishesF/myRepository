package cn.hotol.app.service.location;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TsHtDictRepository;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */

public class LocationServiceImpl implements LocationService {

    private static Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Resource
    private TsHtDictRepository tsHtDictRepository;

    /**
     * @return List<ProvinceDto>
     * @Purpose 查找省市区信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public List<ProvinceDto> findAllProvincialCity() {
        String logInfo = this.getClass().getName() + ":findAllProvincialCity:";
        logger.info("======" + logInfo + "begin======");
        List<ProvinceDto> provinceDtoList = null;
        try {
            provinceDtoList = tsHtDictRepository.findAllProvince();

            for (int i = 0; i < provinceDtoList.size(); i++) {
                ProvinceDto provinceDto = provinceDtoList.get(i);
                List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDto.getDict_id());
                for (int j = 0; j < cityDtoList.size(); j++) {
                    CityDto cityDto = cityDtoList.get(j);
                    List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDto.getDict_id());
                    cityDto.setArea_list(areaDtoList);
                }
                provinceDto.setCity_list(cityDtoList);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return provinceDtoList;
    }

    @Override
    public RetInfo dictByTypePage(String code_type, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":dcitByTypePage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_type", code_type);
            List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
            List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(provinceDtos.get(0).getDict_id());
            params.put("city", cityDtos.get(0).getDict_id());
            int totalRecord = tsHtDictRepository.findDictByTypeSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TsHtDictList> tsHtDictLists = tsHtDictRepository.findDictByTypePage(map);
                map.put("currentPage", currentPage);
                map.put("dicts", tsHtDictLists);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo dictByParentIdPage(int parent_id, String code_type, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":dictByParentIdPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_type", code_type);
            params.put("parent_id", parent_id);
            int totalRecord = tsHtDictRepository.findDictByParentIdize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TsHtDictList> tsHtDictLists = tsHtDictRepository.findDictByParentIdPage(map);
                map.put("currentPage", currentPage);
                map.put("dicts", tsHtDictLists);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo insertDict(TsHtDictList tsHtDictList) {
        String logInfo = this.getClass().getName() + ":insertDict:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtDictRepository.insertDict(tsHtDictList);
            retInfo.setMark("0");
            retInfo.setTip("成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo updateDict(TsHtDictList tsHtDictList) {
        String logInfo = this.getClass().getName() + ":updateDict:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtDictRepository.updateDict(tsHtDictList);
            retInfo.setMark("0");
            retInfo.setTip("成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public TsHtDictList findDictById(int dict_id) {
        return tsHtDictRepository.findDictById(dict_id);
    }

    @Override
    public RetInfo findCityByProvinceId(int parent_id) {
        String logInfo = this.getClass().getName() + ":findCityByProvinceId:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(parent_id);
            params.put("city", cityDtos);
            retInfo.setMark("0");
            retInfo.setTip("成功");
            retInfo.setObj(params);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo findAreaByCityId(int parent_id) {
        String logInfo = this.getClass().getName() + ":findAreaByCityId:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            List<AreaDto> areaDtos = tsHtDictRepository.findAreaByCity(parent_id);
            params.put("areaDtos", areaDtos);
            retInfo.setMark("0");
            retInfo.setTip("成功");
            retInfo.setObj(params);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

}
