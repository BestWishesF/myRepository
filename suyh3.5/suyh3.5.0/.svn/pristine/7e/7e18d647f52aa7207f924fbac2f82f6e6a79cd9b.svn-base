package cn.hotol.app.service.express.sdk;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TsHtDictRepository;
import cn.hotol.app.repository.TsHtExpressOpenRegionRepository;
import cn.hotol.app.repository.TsHtExpressSdkRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-09.
 */
public class ExpressSdkServiceImpl implements ExpressSdkService {

    private static Logger logger = Logger.getLogger(ExpressSdkServiceImpl.class);

    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    private TsHtExpressSdkRepository tsHtExpressSdkRepository;
    private TsHtDictRepository tsHtDictRepository;

    /**
     * @return Map
     * @Purpose 查询快递公司的SDK
     * @version 3.0
     * @author lubin
     */
    @Override
    public Map<String, Map<String, TsHtExpressSdkDto>> findExpressSdk() {
        String logInfo = this.getClass().getName() + ":findExpressSdk:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        Map<String, Map<String, TsHtExpressSdkDto>> sdkMap = new HashMap<String, Map<String, TsHtExpressSdkDto>>();
        try {
            List<CourierDto> courierDtoList = tsHtExpressOpenRegionRepository.findAllExpressOpenRegion();
            for (int i = 0; i < courierDtoList.size(); i++) {
                CourierDto courierDto = courierDtoList.get(i);
                List<TsHtExpressSdkDto> tsHtExpressSdkDtoList = tsHtExpressSdkRepository.findExpressSdkByEoa(courierDto.getEoa_id());
                Map<String, TsHtExpressSdkDto> expressSdkMap = new HashMap<String, TsHtExpressSdkDto>();
                for (int j = 0; j < tsHtExpressSdkDtoList.size(); j++) {
                    expressSdkMap.put("" + tsHtExpressSdkDtoList.get(j).getSdkj_itf_type(), tsHtExpressSdkDtoList.get(j));
                }
                sdkMap.put("" + courierDto.getEoa_id(), expressSdkMap);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return sdkMap;
    }

    /**
     * @param eoa_id,currentPage,pageSize
     * @Purpose  分页查询快递公司接口配置
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @Override
    public RetInfo findExpressSdkPage(int eoa_id, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":findExpressSdkPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("eoa_id",eoa_id);
            TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegByEoaId(eoa_id);
            TsHtDictList area_dict = tsHtDictRepository.findDictById(Integer.parseInt(tsHtExpressOpenRegionDto.getRegion_id()));
            TsHtDictList city_dict = tsHtDictRepository.findDictById(area_dict.getParent_id());
            TsHtDictList provice_dict = tsHtDictRepository.findDictById(city_dict.getParent_id());
            String address = provice_dict.getCode_name() + city_dict.getCode_name() + area_dict.getCode_name();
            params.put("address", address);
            int totalRecord = tsHtExpressSdkRepository.findExpressSdkSize(params);//总条数
            if(totalRecord>0){
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TsHtExpressSdkDto> tsHtExpressSdkDtoList = tsHtExpressSdkRepository.findExpressSdkPage(map);
                map.put("exp_sdk_list", tsHtExpressSdkDtoList);

                retInfo.setMark("0");
                retInfo.setObj(map);
            }else {
                retInfo.setMark("1");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
                retInfo.setTip("暂无数据.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param exp_sdk_id
     * @Purpose  通过id查询快递公司接口配置
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @Override
    public RetInfo findExpressSdkById(int exp_sdk_id) {
        String logInfo = this.getClass().getName() + ":findExpressSdkById:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressSdkDto tsHtExpressSdkDto = tsHtExpressSdkRepository.findExpressSdkById(exp_sdk_id);
            retInfo.setMark("0");
            retInfo.setObj(tsHtExpressSdkDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   新增快递sdk配置
     * @version   3.0
     * @author    lubin
     * @time      2017-03-31
     * @param     tsHtExpressSdkDto  快递sdk数据
     * @return    RetInfo
     */
    @Override
    public RetInfo insertExpressSdk(TsHtExpressSdkDto tsHtExpressSdkDto) {
        String logInfo = this.getClass().getName() + ":findExpressSdkById:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //查询该类别接口是否存在
            TsHtExpressSdkDto expressSDk = tsHtExpressSdkRepository.findExpSdkByEoaType(tsHtExpressSdkDto);
            if(expressSDk == null){
                //新增接口配置
                tsHtExpressSdkRepository.insertExpressSdk(tsHtExpressSdkDto);

                retInfo.setMark("0");
            }else {
                retInfo.setMark("1");
                retInfo.setTip("该接口已配置.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   修改快递sdk配置
     * @version   3.0
     * @author    lubin
     * @time      2017-03-31
     * @param     tsHtExpressSdkDto  快递sdk数据
     * @return    RetInfo
     */
    @Override
    public RetInfo updateExpressSdk(TsHtExpressSdkDto tsHtExpressSdkDto) {
        String logInfo = this.getClass().getName() + ":findExpressSdkById:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //查询该类别接口是否存在
            TsHtExpressSdkDto expressSDk = tsHtExpressSdkRepository.findExpSdkByEoaType(tsHtExpressSdkDto);

            //判断接口是否重复
            if(expressSDk != null && expressSDk.getSdkj_itf_type() != tsHtExpressSdkDto.getSdkj_itf_type()){
                retInfo.setMark("1");
                retInfo.setTip("接口已存在.");
            }else {
                //修改接口配置
                tsHtExpressSdkRepository.updateExpressSdk(tsHtExpressSdkDto);

                retInfo.setMark("0");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTsHtExpressOpenRegionRepository(TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository) {
        this.tsHtExpressOpenRegionRepository = tsHtExpressOpenRegionRepository;
    }

    public void setTsHtExpressSdkRepository(TsHtExpressSdkRepository tsHtExpressSdkRepository) {
        this.tsHtExpressSdkRepository = tsHtExpressSdkRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }
}
