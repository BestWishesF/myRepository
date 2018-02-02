package cn.hotol.app.service.admin.divide;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.divide.DivideDto;
import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;
import cn.hotol.app.bean.dto.admin.divide.grid.TdHtAdminDivideGridDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.PointUtil;
import cn.hotol.app.repository.TdHtAdminDivideGridRepository;
import cn.hotol.app.repository.TdHtAdminDivideRepository;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import cn.hotol.app.repository.TsHtDictRepository;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-04.
 */
public class DivideServiceImpl implements DivideService {

    private static Logger logger = Logger.getLogger(DivideServiceImpl.class);

    private TdHtAdminDivideRepository tdHtAdminDivideRepository;
    private TdHtAdminDivideGridRepository tdHtAdminDivideGridRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;

    /**
     * @Purpose  查询行政区内的有效的划分区域
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDivideDto
     * @return   RetInfo
     */
    @Override
    public RetInfo findGridByRegion(TdHtAdminDivideDto tdHtAdminDivideDto) {
        String logInfo = this.getClass().getName() + ":findGridByRegion:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            Map<String, Object> map=new HashMap<>();
            TsHtDictList region=tsHtDictRepository.findDictById(tdHtAdminDivideDto.getRegion_id());
            TsHtDictList city=tsHtDictRepository.findDictById(region.getParent_id());
            TsHtDictList province=tsHtDictRepository.findDictById(city.getParent_id());
            List<TdHtAdminDivideDto> divideList=tdHtAdminDivideRepository.findDivideByRegion(tdHtAdminDivideDto);
            for(int i=0;i<divideList.size();i++){
                TdHtAdminDivideDto adminDivideDto=divideList.get(i);
                List<TdHtAdminDivideGridDto> divideGridDtoList=tdHtAdminDivideGridRepository.findGridByDivide(adminDivideDto.getDivide_id());
                adminDivideDto.setDivideGridDtoList(divideGridDtoList);
            }
            map.put("region_name",province.getCode_name()+city.getCode_name()+region.getCode_name());
            map.put("divide_list",divideList);
            retInfo.setMark("0");
            retInfo.setObj(map);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  查询所有的划分区域
     * @version  3.0
     * @author   lubin
     * @param
     * @return   Map<String, Object>
     */
    @Override
    public Map<String, Object> findAllDivide() {
        String logInfo = this.getClass().getName() + ":findAllDivide:";
        logger.info("======" + logInfo + "begin======");
        Map<String, Object> divideMap = new HashMap<>();
        try {
            List<TsHtDictDto> regionList = tsHtDictRepository.findDictByType(Constant.AREA);
            for(int i = 0 ; i < regionList.size() ; i++){
                TsHtDictDto tsHtDictDto = regionList.get(i);
                List<TdHtAdminDivideDto> divideList = tdHtAdminDivideRepository.findAllDivideByRegion(tsHtDictDto.getDict_id());
                List<DivideDto> divideDtoList = new ArrayList<>();
                for(int j = 0 ; j < divideList.size() ; j++){
                    TdHtAdminDivideDto tdHtAdminDivideDto = divideList.get(j);
                    DivideDto divideDto = new DivideDto();
                    List<Double> lngs = new ArrayList<Double>();
                    List<Double> lats = new ArrayList<Double>();

                    List<TdHtAdminDivideGridDto> divideGridDtoList = tdHtAdminDivideGridRepository.findGridByDivide(tdHtAdminDivideDto.getDivide_id());
                    for(int k = 0 ; k < divideGridDtoList.size() ; k++){
                        TdHtAdminDivideGridDto tdHtAdminDivideGridDto = divideGridDtoList.get(k);
                        lngs.add(tdHtAdminDivideGridDto.getGrid_longitude().doubleValue());
                        lats.add(tdHtAdminDivideGridDto.getGrid_latitude().doubleValue());
                    }
                    divideDto.setDivide_id(tdHtAdminDivideDto.getDivide_id());
                    divideDto.setRegion_id(tdHtAdminDivideDto.getRegion_id());
                    divideDto.setDivide_name(tdHtAdminDivideDto.getDivide_name());
                    divideDto.setDivide_type(tdHtAdminDivideDto.getDivide_type());
                    divideDto.setLngs(lngs);
                    divideDto.setLats(lats);
                    divideDtoList.add(divideDto);
                }
                divideMap.put(""+tsHtDictDto.getDict_id(),divideDtoList);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return divideMap;
    }

    /**
     * @Purpose  分页查询区域下划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    currentPage  请求页码
     * @param    pageSize 每页显示数量
     * @param    region_id  区域id
     * @return   RetInfo
     */
    @Override
    public RetInfo findDivideByRegionPage(int currentPage, int pageSize, int region_id) {
        String logInfo = this.getClass().getName() + ":findDivideByRegionPage:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("region_id", region_id);

            int totalRecord = tdHtAdminDivideRepository.findDivideByRegionSize(region_id);
            if(totalRecord > 0){
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.putAll(params);
                List<TdHtAdminDivideDto> tdHtAdminDivideDtoList = tdHtAdminDivideRepository.findDivideByRegionPage(map);

                map.put("currentPage", currentPage);
                map.put("divide_list", tdHtAdminDivideDtoList);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            }else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  查询区域下所有的划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    region_id  区域id
     * @return   RetInfo
     */
    @Override
    public RetInfo findDivideByRegion(int region_id) {
        String logInfo = this.getClass().getName() + ":findDivideByRegion:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            Map<String, Object> map = new HashMap<>();
            TsHtDictList region = tsHtDictRepository.findDictById(region_id);
            TsHtDictList city = tsHtDictRepository.findDictById(region.getParent_id());
            TsHtDictList province = tsHtDictRepository.findDictById(city.getParent_id());
            List<TdHtAdminDivideDto> divideList = tdHtAdminDivideRepository.findAllDivideByRegion(region_id);
            for(int i = 0 ; i < divideList.size() ; i++){
                TdHtAdminDivideDto adminDivideDto = divideList.get(i);
                List<TdHtAdminDivideGridDto> divideGridDtoList = tdHtAdminDivideGridRepository.findGridByDivide(adminDivideDto.getDivide_id());
                adminDivideDto.setDivideGridDtoList(divideGridDtoList);
            }
            map.put("region_id", region_id);
            map.put("address", province.getCode_name() + city.getCode_name() + region.getCode_name());
            map.put("divide_list", divideList);
            retInfo.setMark("0");
            retInfo.setObj(map);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  新增划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    tdHtAdminDivideDto  划分区域信息
     * @return   RetInfo
     */
    @Override
    public RetInfo insertDivide(TdHtAdminDivideDto tdHtAdminDivideDto) {
        String logInfo = this.getClass().getName() + ":insertDivide:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            List<TdHtAdminDivideGridDto> divideGridDtoList = tdHtAdminDivideDto.getDivideGridDtoList();
            if(divideGridDtoList.size() > 0){
                boolean is_ok = false;
                List<TdHtAdminDivideDto> divideDtoList = tdHtAdminDivideRepository.findAllDivideByRegion(tdHtAdminDivideDto.getRegion_id());
                for(int i = 0 ; i < divideDtoList.size() ; i++){
                    TdHtAdminDivideDto adminDivideDto = divideDtoList.get(i);
                    List<TdHtAdminDivideGridDto> adminDivideGridDtoList = tdHtAdminDivideGridRepository.findGridByDivide(adminDivideDto.getDivide_id());

                    List<Double> lngs = new ArrayList<Double>();
                    List<Double> lats = new ArrayList<Double>();
                    for(int j = 0 ; j < adminDivideGridDtoList.size() ; j++){
                        TdHtAdminDivideGridDto tdHtAdminDivideGridDto = adminDivideGridDtoList.get(j);
                        lngs.add(tdHtAdminDivideGridDto.getGrid_longitude().doubleValue());
                        lats.add(tdHtAdminDivideGridDto.getGrid_latitude().doubleValue());
                    }

                    for (int k = 0 ; k < divideGridDtoList.size() ; k++){
                        TdHtAdminDivideGridDto tdHtAdminDivideGridDto = divideGridDtoList.get(k);
                        boolean isPoint = PointUtil.isPointInPolygon(tdHtAdminDivideGridDto.getGrid_longitude().doubleValue(), tdHtAdminDivideGridDto.getGrid_latitude().doubleValue(), lngs, lats);
                        if(isPoint){
                            is_ok=true;
                            retInfo.setMark("1");
                            retInfo.setTip("该区域内存在其他区域.");
                            break;
                        }
                    }
                }

                if(!is_ok){
                    tdHtAdminDivideDto.setDivide_type(0);
                    tdHtAdminDivideRepository.insertDivide(tdHtAdminDivideDto);
                    for(int i=0;i<divideGridDtoList.size();i++){
                        TdHtAdminDivideGridDto adminDivideGridDto=divideGridDtoList.get(i);
                        adminDivideGridDto.setDivide_id(tdHtAdminDivideDto.getDivide_id());
                        adminDivideGridDto.setGrid_state(tdHtAdminDivideDto.getDivide_state());
                    }
                    tdHtAdminDivideGridRepository.insertDivideGrids(divideGridDtoList);

                    List<TdHtExpressOrderDto> expressOrderDtoList=tdHtExpressOrderRepository.findOrderByRegion(tdHtAdminDivideDto.getRegion_id());
                    for(int i=0;i<expressOrderDtoList.size();i++){
                        TdHtExpressOrderDto tdHtExpressOrderDto=expressOrderDtoList.get(i);

                        if(tdHtExpressOrderDto.getAdd_longitude() > 0 && tdHtExpressOrderDto.getAdd_latitude() > 0){
                            List<Double> lngs = new ArrayList<Double>();
                            List<Double> lats = new ArrayList<Double>();
                            for(int j = 0 ; j < divideGridDtoList.size() ; j++){
                                TdHtAdminDivideGridDto tdHtAdminDivideGridDto = divideGridDtoList.get(j);
                                lngs.add(tdHtAdminDivideGridDto.getGrid_longitude().doubleValue());
                                lats.add(tdHtAdminDivideGridDto.getGrid_latitude().doubleValue());
                            }

                            boolean isPoint = PointUtil.isPointInPolygon(tdHtExpressOrderDto.getAdd_longitude(), tdHtExpressOrderDto.getAdd_latitude(), lngs, lats);
                            if(isPoint){
                                tdHtExpressOrderDto.setDivide_id(tdHtAdminDivideDto.getDivide_id());
                                tdHtExpressOrderRepository.updateOrderDivide(tdHtExpressOrderDto);
                            }
                        }
                    }

                    retInfo.setMark("0");
                    retInfo.setTip("区域划分成功.");
                }
            }else {
                retInfo.setMark("1");
                retInfo.setTip("区域没有划分.");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  通过id查询划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    divide_id  划分区域id
     * @return   RetInfo
     */
    @Override
    public RetInfo findDivideById(int divide_id) {
        String logInfo = this.getClass().getName() + ":findDivideById:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            Map<String, Object>  params= new HashMap<>();
            params.put("divide_id", divide_id);

            TdHtAdminDivideDto tdHtAdminDivideDto = tdHtAdminDivideRepository.findDivideById(divide_id);

            TsHtDictList region = tsHtDictRepository.findDictById(tdHtAdminDivideDto.getRegion_id());
            TsHtDictList city = tsHtDictRepository.findDictById(region.getParent_id());
            TsHtDictList province = tsHtDictRepository.findDictById(city.getParent_id());

            List<TdHtAdminDivideDto> divideList = tdHtAdminDivideRepository.findAllDivideByRegion(tdHtAdminDivideDto.getRegion_id());
            for(int i = 0 ; i < divideList.size() ; i++){
                TdHtAdminDivideDto adminDivideDto = divideList.get(i);
                List<TdHtAdminDivideGridDto> divideGridDtoList = tdHtAdminDivideGridRepository.findGridByDivide(adminDivideDto.getDivide_id());
                adminDivideDto.setDivideGridDtoList(divideGridDtoList);
            }

            params.put("divide", tdHtAdminDivideDto);
            params.put("address", province.getCode_name() + city.getCode_name() + region.getCode_name());
            params.put("divide_list", divideList);
            retInfo.setMark("0");
            retInfo.setObj(params);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  修改划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    tdHtAdminDivideDto  划分区域信息
     * @return   RetInfo
     */
    @Override
    public RetInfo updateDivide(TdHtAdminDivideDto tdHtAdminDivideDto) {
        String logInfo = this.getClass().getName() + ":updateDivide:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            List<TdHtAdminDivideGridDto> divideGridDtoList = tdHtAdminDivideDto.getDivideGridDtoList();
            if(divideGridDtoList.size() == 0 && tdHtAdminDivideDto.getDivide_state() == 0){
                retInfo.setMark("1");
                retInfo.setTip("区域没有划分.");
            }else {
                boolean is_ok = false;

                if(tdHtAdminDivideDto.getDivide_state() == 0){
                    List<TdHtAdminDivideDto> divideDtoList = tdHtAdminDivideRepository.findAllDivideByRegion(tdHtAdminDivideDto.getRegion_id());
                    for(int i = 0 ; i < divideDtoList.size() ; i++){
                        TdHtAdminDivideDto adminDivideDto = divideDtoList.get(i);

                        if(adminDivideDto.getDivide_id() != tdHtAdminDivideDto.getDivide_id()){
                            List<TdHtAdminDivideGridDto> adminDivideGridDtoList = tdHtAdminDivideGridRepository.findGridByDivide(adminDivideDto.getDivide_id());

                            List<Double> lngs = new ArrayList<Double>();
                            List<Double> lats = new ArrayList<Double>();
                            for(int j = 0 ; j < adminDivideGridDtoList.size() ; j++){
                                TdHtAdminDivideGridDto tdHtAdminDivideGridDto = adminDivideGridDtoList.get(j);
                                lngs.add(tdHtAdminDivideGridDto.getGrid_longitude().doubleValue());
                                lats.add(tdHtAdminDivideGridDto.getGrid_latitude().doubleValue());
                            }

                            for (int k = 0 ; k < divideGridDtoList.size() ; k++){
                                TdHtAdminDivideGridDto tdHtAdminDivideGridDto = divideGridDtoList.get(k);
                                boolean isPoint = PointUtil.isPointInPolygon(tdHtAdminDivideGridDto.getGrid_longitude().doubleValue(), tdHtAdminDivideGridDto.getGrid_latitude().doubleValue(), lngs, lats);
                                if(isPoint){
                                    is_ok=true;
                                    retInfo.setMark("1");
                                    retInfo.setTip("该区域内存在其他区域.");
                                    break;
                                }
                            }
                        }
                    }
                }


                if(!is_ok){

                    tdHtExpressOrderRepository.updateOrderByDivide(tdHtAdminDivideDto.getDivide_id());
                    tdHtAdminDivideDto.setDivide_type(0);
                    tdHtAdminDivideRepository.updateDivide(tdHtAdminDivideDto);
                    tdHtAdminDivideGridRepository.updateGridStateByDivide(tdHtAdminDivideDto.getDivide_id());
                    if(tdHtAdminDivideDto.getDivide_state() == 0){
                        for(int i=0;i<divideGridDtoList.size();i++){
                            TdHtAdminDivideGridDto adminDivideGridDto=divideGridDtoList.get(i);
                            adminDivideGridDto.setDivide_id(tdHtAdminDivideDto.getDivide_id());
                            adminDivideGridDto.setGrid_state(tdHtAdminDivideDto.getDivide_state());
                        }
                        tdHtAdminDivideGridRepository.insertDivideGrids(divideGridDtoList);

                        List<TdHtExpressOrderDto> expressOrderDtoList=tdHtExpressOrderRepository.findOrderByRegion(tdHtAdminDivideDto.getRegion_id());
                        for(int i=0;i<expressOrderDtoList.size();i++){
                            TdHtExpressOrderDto tdHtExpressOrderDto=expressOrderDtoList.get(i);

                            if(tdHtExpressOrderDto.getAdd_longitude() > 0 && tdHtExpressOrderDto.getAdd_latitude() > 0){
                                List<Double> lngs = new ArrayList<Double>();
                                List<Double> lats = new ArrayList<Double>();
                                for(int j = 0 ; j < divideGridDtoList.size() ; j++){
                                    TdHtAdminDivideGridDto tdHtAdminDivideGridDto = divideGridDtoList.get(j);
                                    lngs.add(tdHtAdminDivideGridDto.getGrid_longitude().doubleValue());
                                    lats.add(tdHtAdminDivideGridDto.getGrid_latitude().doubleValue());
                                }

                                boolean isPoint = PointUtil.isPointInPolygon(tdHtExpressOrderDto.getAdd_longitude(), tdHtExpressOrderDto.getAdd_latitude(), lngs, lats);
                                if(isPoint){
                                    tdHtExpressOrderDto.setDivide_id(tdHtAdminDivideDto.getDivide_id());
                                    tdHtExpressOrderRepository.updateOrderDivide(tdHtExpressOrderDto);
                                }
                            }
                        }
                    }

                    retInfo.setMark("0");
                    retInfo.setTip("区域划分修改成功.");
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  根据区id查找划分区列表
     * @version  3.0
     * @author   lubin
     * @time     2017-04-15
     * @param    region_id  行政区id
     * @return   RetInfo
     */
    @Override
    public RetInfo findDivideByRegionId(int region_id) {
        String logInfo = this.getClass().getName() + ":findDivideByRegionId:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            List<TdHtAdminDivideDto> divideDtoList = tdHtAdminDivideRepository.findAllDivideByRegion(region_id);
            params.put("divideDtos", divideDtoList);
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

    public void setTdHtAdminDivideRepository(TdHtAdminDivideRepository tdHtAdminDivideRepository) {
        this.tdHtAdminDivideRepository = tdHtAdminDivideRepository;
    }

    public void setTdHtAdminDivideGridRepository(TdHtAdminDivideGridRepository tdHtAdminDivideGridRepository) {
        this.tdHtAdminDivideGridRepository = tdHtAdminDivideGridRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }
}
