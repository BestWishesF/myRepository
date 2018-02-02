package cn.hotol.app.service.express.open.region;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TsHtDictRepository;
import cn.hotol.app.repository.TsHtExpressOpenRegionRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public class ExpressOpenRegionServiceImpl implements ExpressOpenRegionService{
    private static Logger logger = Logger.getLogger(ExpressOpenRegionServiceImpl.class);
    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    private TsHtDictRepository tsHtDictRepository;
    @Override
    public RetInfo expressOpenRegionPage(int express_id, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":expressOpenRegionPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("express_id",express_id);
            List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
            params.put("province", provinceDtos);
            List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(provinceDtos.get(0).getDict_id());
            params.put("city", cityDtos);
            List<AreaDto> areaDtos = tsHtDictRepository.findAreaByCity(cityDtos.get(0).getDict_id());
            params.put("areaDtos", areaDtos);
            int totalRecord = tsHtExpressOpenRegionRepository.findExpOpenRegionByExpIdSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TsHtExpressOpenRegionDto> tsHtExpressOpenRegionDtos = tsHtExpressOpenRegionRepository.findExpOpenRegionByExpIdPage(map);
                for (int i = 0 ; i < tsHtExpressOpenRegionDtos.size() ; i ++) {
                    TsHtDictList area_dict = tsHtDictRepository.findDictById(Integer.valueOf(tsHtExpressOpenRegionDtos.get(i).getRegion_id()));
                    TsHtDictList city_dict = tsHtDictRepository.findDictById(area_dict.getParent_id());
                    TsHtDictList provice_dict = tsHtDictRepository.findDictById(city_dict.getParent_id());
                    String area = provice_dict.getCode_name() + city_dict.getCode_name() + area_dict.getCode_name();
                    tsHtExpressOpenRegionDtos.get(i).setRegion_id(area);
                }
                map.put("currentPage", currentPage);
                map.put("expOpenRegions", tsHtExpressOpenRegionDtos);
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
    public RetInfo insertExpressOpenRegion(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto) {
        String logInfo = this.getClass().getName() + ":insertExpressOpenRegion:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto expressOpenRegionDto = tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);
            if (expressOpenRegionDto != null) {
                if (expressOpenRegionDto.getState() == 0) {
                    retInfo.setMark("1");
                    retInfo.setTip("已添加");
                } else {
                    expressOpenRegionDto.setState(0);
                    tsHtExpressOpenRegionRepository.updateExpOpenRegion(expressOpenRegionDto);
                    retInfo.setMark("0");
                    retInfo.setTip("成功");
                }
            } else {
                tsHtExpressOpenRegionRepository.insertExpOpenRegion(tsHtExpressOpenRegionDto);
                retInfo.setMark("0");
                retInfo.setTip("成功");
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
    public RetInfo updateExpressOpenRegion(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto) {
        String logInfo = this.getClass().getName() + ":updateExpressOpenRegion:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            if(tsHtExpressOpenRegionDto.getState()==1){
                tsHtExpressOpenRegionRepository.updateExpOpenRegion(tsHtExpressOpenRegionDto);
            }else{
                tsHtExpressOpenRegionRepository.updateRegOpenInfoById(tsHtExpressOpenRegionDto);
            }
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

    /**
     * @Purpose  通过id查询开放区域信息
     * @version  3.0
     * @author   lubin
     * @param   tsHtExpressOpenRegionDto
     * @return   RetInfo
     */
    @Override
    public RetInfo findRegionById(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto) {
        String logInfo = this.getClass().getName() + ":findRegionById:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto expressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegByEoaId(tsHtExpressOpenRegionDto.getEoa_id());

            TsHtDictList area_dict = tsHtDictRepository.findDictById(Integer.valueOf(expressOpenRegionDto.getRegion_id()));
            TsHtDictList city_dict = tsHtDictRepository.findDictById(area_dict.getParent_id());
            TsHtDictList provice_dict = tsHtDictRepository.findDictById(city_dict.getParent_id());
            String area = provice_dict.getCode_name() + city_dict.getCode_name() + area_dict.getCode_name();
            expressOpenRegionDto.setRegion_id(area);

            retInfo.setMark("0");
            retInfo.setTip("成功");
            retInfo.setObj(expressOpenRegionDto);
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

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }
}
