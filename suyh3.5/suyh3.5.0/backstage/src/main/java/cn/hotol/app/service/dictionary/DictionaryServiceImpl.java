package cn.hotol.app.service.dictionary;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.dict.DictInitialsSortDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.repository.TdHtAdminDivideRepository;
import cn.hotol.app.repository.TsHtDictRepository;
import cn.hotol.app.repository.TsHtExpressOpenRegionRepository;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class DictionaryServiceImpl implements DictionaryService {

    private static Logger logger = Logger.getLogger(DictionaryServiceImpl.class);

    @Resource
    private TsHtDictRepository tsHtDictRepository;
    @Resource
    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    @Resource
    private TdHtAdminDivideRepository tdHtAdminDivideRepository;
    /**
     * @return List<ProvinceDto>
     * @Purpose 根据字典类别查询字典数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public Map<String, Object> findDictByType() {
        String logInfo = this.getClass().getName() + ":findDictByType:";
        logger.info("======" + logInfo + "begin======");
        Map<String, Object> dataDictionary = new HashMap<String, Object>();
        try {
            //快件类别
            List<TsHtDictDto> expressCategory = tsHtDictRepository.findDictByType(Constant.EXPRESS_CATEGORY);
            //快件要求
            List<TsHtDictDto> expressAsk = tsHtDictRepository.findDictByType(Constant.EXPRESS_ASK);
            //上门时间类别
            List<TsHtDictDto> collectType = tsHtDictRepository.findDictByType(Constant.COLLECT_TYPE);
            //代理人上班时间
            List<TsHtDictDto> agentWorkTime = tsHtDictRepository.findDictByType(Constant.AGENT_WORK_TIME);
            //省
            List<ProvinceDto> provinceDtoList = tsHtDictRepository.findAllProvince();

            Map cityMap = new HashMap();
            Map areaMap = new HashMap();
            for (int i = 0; i < provinceDtoList.size(); i++) {
                ProvinceDto provinceDto = provinceDtoList.get(i);
                List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDto.getDict_id());
                cityMap.put(provinceDto.getDict_id(), cityDtoList);
                for (int j = 0; j < cityDtoList.size(); j++) {
                    CityDto cityDto = cityDtoList.get(j);
                    List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDto.getDict_id());
                    areaMap.put(cityDto.getDict_id(), areaDtoList);
                }
            }

            dataDictionary.put(Constant.EXPRESS_CATEGORY, expressCategory);
            dataDictionary.put(Constant.EXPRESS_ASK, expressAsk);
            dataDictionary.put(Constant.COLLECT_TYPE, collectType);
            dataDictionary.put(Constant.PROVINCE, provinceDtoList);
            dataDictionary.put(Constant.AGENT_WORK_TIME, agentWorkTime);
            dataDictionary.put(Constant.CITY, cityMap);
            dataDictionary.put(Constant.AREA, areaMap);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return dataDictionary;
    }

    /**
     * @return Map<Integer,Object>
     * @Purpose 查询字典中的快递公司配置
     * @version 3.0
     * @author lubin
     */
    @Override
    public Map<Integer, Object> findOpenExpressCompany() {
        String logInfo = this.getClass().getName() + ":findOpenExpressCompany:";
        logger.info("======" + logInfo + "begin======");
        Map<Integer, Object> expressMap = new HashMap<Integer, Object>();
        try {
            List<CourierDto> courierDtoList = tsHtExpressOpenRegionRepository.findExpressOpenRegion();
            List<TsHtDictDto> expressCompany = tsHtDictRepository.findDictByType(Constant.EXPRESS_COMPANY);

            expressMap.put(0, expressCompany);
            for (int i = 0; i < courierDtoList.size(); i++) {
                CourierDto courierDto = courierDtoList.get(i);
                List<TsHtDictDto> tsHtDictDtos = tsHtDictRepository.findExpressByRegion(courierDto.getRegion_id());
                expressMap.put(courierDto.getRegion_id(), tsHtDictDtos);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return expressMap;
    }

    /**
     * @return List<TsHtDictDto>
     * @Purpose 查询字典中的上门时间配置
     * @version 3.0
     * @author lubin
     */
    @Override
    public List<TsHtDictDto> findCollectTime() {
        String logInfo = this.getClass().getName() + ":findCollectTime:";
        logger.info("======" + logInfo + "begin======");
        List<TsHtDictDto> collectTime = new ArrayList<TsHtDictDto>();
        try {
            collectTime = tsHtDictRepository.findDictByType(Constant.COLLECT_TIME);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return collectTime;
    }

    /**
     * @Purpose  查询字典数据
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtDictDto>
     */
    @Override
    public List<TsHtDictDto> findDicts() {
        String logInfo = this.getClass().getName() + ":findDicts:";
        logger.info("======" + logInfo + "begin======");
        List<TsHtDictDto> tsHtDictDtoList = new ArrayList<TsHtDictDto>();
        try {
            tsHtDictDtoList = tsHtDictRepository.findDicts();
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return tsHtDictDtoList;
    }

    /**
     * @Purpose  查询快递公司开通区域数据
     * @version  3.0
     * @author   lubin
     * @return   List<CourierDto>
     */
    @Override
    public List<CourierDto> findCourierDto() {
        String logInfo = this.getClass().getName() + ":findCourierDto:";
        logger.info("======" + logInfo + "begin======");
        List<CourierDto> courierDtoList = new ArrayList<CourierDto>();
        try {
            courierDtoList = tsHtExpressOpenRegionRepository.findAllExpressOpenRegion();
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return courierDtoList;
    }

    /**
     * @Purpose  查询已经开通的快递公司列表
     * @version  3.0
     * @author   lubin
     * @return   List<CourierDto>
     */
    @Override
    public List<TsHtDictDto> findOpenRegionExpCompany() {
        String logInfo = this.getClass().getName() + ":findOpenRegionExpCompany:";
        logger.info("======" + logInfo + "begin======");
        List<TsHtDictDto> openRegionExpCompany = new ArrayList<TsHtDictDto>();
        try {
            List<TsHtDictDto> expressCompany = tsHtDictRepository.findDictByType(Constant.EXPRESS_COMPANY);
            for (int i=0;i<expressCompany.size();i++){
                TsHtDictDto tsHtDictDto=expressCompany.get(i);
                Map<String, Object> params = new HashMap<>();
                params.put("express_id",tsHtDictDto.getDict_id());
                int record = tsHtExpressOpenRegionRepository.findExpOpenRegionByExpIdSize(params);
                if(record>0){
                    openRegionExpCompany.add(tsHtDictDto);
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return openRegionExpCompany;
    }

    /**
     * @Purpose   查询所有的字典数据的id、名称和父id
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @return    List<TsHtDictIndex>  查询结果
     */
    @Override
    public Map<String, TsHtDictIndex> findAllDictIndex() {
        String logInfo = this.getClass().getName() + ":findAllDictIndex:";
        logger.info("======" + logInfo + "begin======");
        Map<String, TsHtDictIndex> map = new HashMap<>();
        try {
            List<TsHtDictIndex> tsHtDictIndexList = tsHtDictRepository.findAllDictIndex();
            for (int i = 0 ; i < tsHtDictIndexList.size() ; i++){
                TsHtDictIndex tsHtDictIndex = tsHtDictIndexList.get(i);
                map.put("" + tsHtDictIndex.getDict_id(), tsHtDictIndex);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return map;
    }

    /**
     * @Purpose   查询字典按首字母排序
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @return    Map<String, List<DictInitialsSortDto>>
     */
    @Override
    public Map<String, List<DictInitialsSortDto>> findAllDictByInitials() {
        String logInfo = this.getClass().getName() + ":findAllDictByInitials:";
        logger.info("======" + logInfo + "begin======");
        Map<String, List<DictInitialsSortDto>> map = new HashMap<>();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code_type", Constant.CITY);
            params.put("dict_is_hos", 1);

            //查询热门城市
            List<DictInitialsSortDto> hostCity = tsHtDictRepository.findAllDictByInitials(params);
            map.put(Constant.HOST_CITY_LIST, hostCity);

            //首字母排序城市
            params.put("dict_is_hos", 0);
            List<DictInitialsSortDto> initialsSortCity = tsHtDictRepository.findAllDictByInitials(params);
            map.put(Constant.INITIALS_SORT_CITY_LIST, initialsSortCity);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return map;
    }

    @Override
    public RetInfo findByAdmin(TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":findByAdmin:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            int province_id = 0;//省id
            int city_id = 0;//市id
            int region_id = 0;//区id
            int divide_id = 0;//划分的区域id
            Map<String, Object> result = new HashMap<>();
            List<TsHtDictDto> tsHtDictDtos = new ArrayList<>();

            if (tdHtAdminDto.getProvince_id() == 0 && tdHtAdminDto.getAdmin_id() != 1) {
                province_id = -1;
                tsHtDictDtos = tsHtDictRepository.findDictByType("province");
            }
            if (tdHtAdminDto.getProvince_id() != 0 && tdHtAdminDto.getCity_id() == 0) {
                province_id = tdHtAdminDto.getProvince_id();
                city_id = -1;
                tsHtDictDtos = tsHtDictRepository.findDictByParent(tdHtAdminDto.getProvince_id());
            }
            if (tdHtAdminDto.getCity_id() != 0 && tdHtAdminDto.getRegion_id() == 0) {
                city_id = tdHtAdminDto.getCity_id();
                region_id = -1;
                tsHtDictDtos = tsHtDictRepository.findDictByParent(tdHtAdminDto.getCity_id());
            }
            if (tdHtAdminDto.getRegion_id() != 0 && tdHtAdminDto.getDivide_id() == 0) {
                region_id = tdHtAdminDto.getRegion_id();
                divide_id = -1;

                List<TdHtAdminDivideDto> adminDivideDtos = tdHtAdminDivideRepository.findAllDivideByRegion(tdHtAdminDto.getRegion_id());
                result.put("divides", adminDivideDtos);
            }
            if (tdHtAdminDto.getDivide_id() != 0) {
                divide_id = tdHtAdminDto.getDivide_id();
                result.put("divides", tdHtAdminDivideRepository.findDivideById(tdHtAdminDto.getDivide_id()).getDivide_name());
            }
            if (divide_id == 0) {
                result.put("dicts", tsHtDictDtos);
            }
            result.put("province_id", province_id);
            result.put("city_id", city_id);
            result.put("region_id", region_id);
            result.put("divide_id", divide_id);

            retInfo.setMark("0");
            retInfo.setTip("成功");
            retInfo.setObj(result);

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

    public void setTsHtExpressOpenRegionRepository(TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository) {
        this.tsHtExpressOpenRegionRepository = tsHtExpressOpenRegionRepository;
    }

    public void setTdHtAdminDivideRepository(TdHtAdminDivideRepository tdHtAdminDivideRepository) {
        this.tdHtAdminDivideRepository = tdHtAdminDivideRepository;
    }
}
