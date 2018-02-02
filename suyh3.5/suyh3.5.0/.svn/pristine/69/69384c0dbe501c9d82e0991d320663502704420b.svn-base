package cn.hotol.app.service.three.location;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.admin.divide.DivideDto;
import cn.hotol.app.bean.dto.dict.DictInitialsSortDto;
import cn.hotol.app.bean.dto.dict.TsHtDictAddressDto;
import cn.hotol.app.bean.dto.location.AddressDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.BaiduMapUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TsHtDictRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
@Service
public class LocationServiceImpl implements LocationService {

    private static Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Resource
    private TsHtDictRepository tsHtDictRepository;

    /**
     * @return RetInfo
     * @Purpose 查询省市区
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findAllProvincialCity() {
        String logInfo = this.getClass().getName() + ":findAllProvincialCity:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) MemcachedUtils.get(MemcachedKey.PROVINCIAL_CITY);
            retInfo.setMark("0");
            retInfo.setTip("省市区数据获取成功.");
            retInfo.setObj(provinceDtoList);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   查询所有城市按照城市首字母返回
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @return    RetInfo
     */
    @Override
    public RetInfo findAllCityByInitials() {
        String logInfo = this.getClass().getName() + ":findAllCityByInitials:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中取出数据
            Map<String, List<DictInitialsSortDto>> dictInitialsSortMap = (Map<String, List<DictInitialsSortDto>>) MemcachedUtils.get(MemcachedKey.DICT_INITIALS_SORT_MAP);

            List<DictInitialsSortDto> hotCityList = dictInitialsSortMap.get(Constant.HOST_CITY_LIST);
            List<DictInitialsSortDto> allCityList = dictInitialsSortMap.get(Constant.INITIALS_SORT_CITY_LIST);

            Map<String, Object> cityMap = new HashMap<String, Object>();

            cityMap.put("hot_city", hotCityList);
            cityMap.put("all_city", allCityList);

            retInfo.setMark("0");
            retInfo.setTip("城市数据获取成功.");
            retInfo.setObj(cityMap);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   通过字典名称查询字典
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @param     tsHtDictAddressDto
     * @return    RetInfo
     */
    @Override
    public RetInfo findDictByName(TsHtDictAddressDto tsHtDictAddressDto) {
        String logInfo = this.getClass().getName() + ":findDictByName:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            if(tsHtDictAddressDto.getCode_name() == null || "".equals(tsHtDictAddressDto.getCode_name().trim())){
                retInfo.setMark("1");
                retInfo.setTip("请输入查询内容.");
            }else {
                List<DictInitialsSortDto> dictInitialsSortDtoList = tsHtDictRepository.findDictByName(tsHtDictAddressDto);

                List<String> city_names = new ArrayList<String>();
                for(int i = 0 ; i < dictInitialsSortDtoList.size(); i++){
                    DictInitialsSortDto dictInitialsSortDto = dictInitialsSortDtoList.get(i);
                    city_names.add(dictInitialsSortDto.getCode_name());
                }

                retInfo.setMark("0");
                retInfo.setTip("数据查询成功.");
                retInfo.setObj(city_names);
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
     * @Purpose   通过经纬度查询省市区
     * @version   3.0
     * @author    lubin
     * @time      2017-04-18
     * @param     tdHtMembAddressDto
     * @return    RetInfo
     */
    @Override
    public RetInfo findDictByLatLng(TdHtMembAddressDto tdHtMembAddressDto) {
        String logInfo = this.getClass().getName() + ":findDictByLatLng:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //通过经纬度得到行政区id
            if (tdHtMembAddressDto.getAdd_latitude() > 0 && tdHtMembAddressDto.getAdd_longitude() > 0) {
                //通过经纬度得到省市区名称
                BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                AddressDto addressDto = baiduMapUtil.changeLngAndLat(new BigDecimal(tdHtMembAddressDto.getAdd_latitude()), new BigDecimal(tdHtMembAddressDto.getAdd_longitude()));

                if(addressDto != null){
                    //获取省名称
                    TsHtDictAddressDto province = new TsHtDictAddressDto();
                    province.setCode_name(addressDto.getProvince());
                    province.setParent_id(0);

                    //匹配省
                    List<TsHtDictAddressDto> provinceList = tsHtDictRepository.findDictByParentName(province);

                    if (provinceList.size() > 0 ) {
                        //获取市名称
                        TsHtDictAddressDto city = new TsHtDictAddressDto();
                        city.setCode_name(addressDto.getCity());
                        city.setParent_id(provinceList.get(0).getDict_id());

                        //匹配市
                        List<TsHtDictAddressDto> cityList = tsHtDictRepository.findDictByParentName(city);

                        if (cityList.size() > 0) {
                            //获取行政区名称
                            TsHtDictAddressDto region = new TsHtDictAddressDto();
                            region.setCode_name(addressDto.getDistrict());
                            region.setParent_id(cityList.get(0).getDict_id());

                            //匹配行政区
                            List<TsHtDictAddressDto> regionList = tsHtDictRepository.findDictByParentName(region);

                            if (regionList.size() > 0) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("province_id", provinceList.get(0).getDict_id());
                                map.put("city_id", cityList.get(0).getDict_id());
                                map.put("region_id", regionList.get(0).getDict_id());
                                retInfo.setMark("0");
                                retInfo.setObj(map);
                            }else {
                                retInfo.setMark("1");
                                retInfo.setTip("经纬度不正确.");
                            }
                        }else {
                            retInfo.setMark("1");
                            retInfo.setTip("经纬度不正确.");
                        }
                    }else {
                        retInfo.setMark("1");
                        retInfo.setTip("经纬度不正确.");
                    }
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("经纬度不正确.");
                }
            }else {
                retInfo.setMark("1");
                retInfo.setTip("经纬度不正确.");
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
     * @Purpose   查询区域内的划分区域
     * @version   3.0
     * @author    lubin
     * @time      2017-04-18
     * @param     tsHtDictDto
     * @return    RetInfo
     */
    @Override
    public RetInfo findDivideByRegion(TsHtDictDto tsHtDictDto) {
        String logInfo = this.getClass().getName() + ":findDivideByRegion:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> map = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DIVIDE_DATA_MAP);
            List<DivideDto> divideDtoList = (List<DivideDto>) map.get("" + tsHtDictDto.getDict_id());

            List<Map<String, Object>> divideList = new ArrayList<Map<String, Object>>();
            for (int i = 0 ; i < divideDtoList.size() ; i++){
                DivideDto divideDto = divideDtoList.get(i);

                List<Double> lngs = divideDto.getLngs();
                List<Double> lats = divideDto.getLats();

                List<Map<String, Object>> pointList = new ArrayList<Map<String, Object>>();
                for (int j = 0 ; j < lngs.size() ; j++){
                    Double lng = lngs.get(j);
                    Double lat = lats.get(j);

                    Map<String, Object> point = new HashMap<String, Object>();
                    point.put("lng", lng);
                    point.put("lat", lat);
                    pointList.add(point);
                }

                Map<String, Object> divide = new HashMap<String, Object>();
                divide.put("divide_id", divideDto.getDivide_id());
                divide.put("divide_name", divideDto.getDivide_name());
                divide.put("point_list", pointList);
                divideList.add(divide);
            }

            retInfo.setObj(divideList);
            retInfo.setMark("0");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
