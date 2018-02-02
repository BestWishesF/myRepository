package cn.hotol.app.service.express.open.number;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.express.number.BatchExpressNumberDto;
import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TdHtAgentRepository;
import cn.hotol.app.repository.TsHtDictRepository;
import cn.hotol.app.repository.TsHtExpressOpenNumberRepository;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public class ExpressOpenNumberServiceImpl implements ExpressOpenNumberService {

    private static Logger logger = Logger.getLogger(ExpressOpenNumberServiceImpl.class);

    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtAgentRepository tdHtAgentRepository;

    /**
     * @Purpose  查询快递单号
     * @version  3.0
     * @author   lubin
     * @param    express_id,currentPage,pageSize
     * @return   RetInfo
     */
    @Override
    public RetInfo expressOpenNumberPage(int express_id, int region_id, int exp_open_state, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":expressOpenRegionPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("express_id",express_id);
            params.put("region_id",region_id);
            params.put("exp_open_state",exp_open_state);

            TsHtDictList city = tsHtDictRepository.findDictById(region_id);
            TsHtDictList provice = tsHtDictRepository.findDictById(city.getParent_id());
            params.put("pro_id",provice.getDict_id());

            List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
            params.put("province", provinceDtos);
            List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(provice.getDict_id());
            params.put("city", cityDtos);

            TsHtDictList tsHtDictList=tsHtDictRepository.findDictById(express_id);
            params.put("express_name", tsHtDictList.getCode_name());
            int totalRecord = tsHtExpressOpenNumberRepository.findExpOpenNumberByExpIdSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TsHtExpressOpenNumberDto> tsHtExpressOpenNumberDtoList = tsHtExpressOpenNumberRepository.findExpOpenNumberByExpIdPage(map);
                for (int i = 0 ; i < tsHtExpressOpenNumberDtoList.size() ; i ++) {
                    TsHtDictList city_dict = tsHtDictRepository.findDictById(tsHtExpressOpenNumberDtoList.get(i).getRegion_id());
                    TsHtDictList provice_dict = tsHtDictRepository.findDictById(city_dict.getParent_id());
                    String address = provice_dict.getCode_name() + city_dict.getCode_name();
                    tsHtExpressOpenNumberDtoList.get(i).setAddress(address);
                    if(tsHtExpressOpenNumberDtoList.get(i).getExp_open_state()==1){
                        TdHtAgentDto tdHtAgentDto=tdHtAgentRepository.findAgentById(tsHtExpressOpenNumberDtoList.get(i).getAgent_id());
                        if(tdHtAgentDto != null){
                            tsHtExpressOpenNumberDtoList.get(i).setAgent_name(tdHtAgentDto.getAgent_name());
                        }
                    }
                }
                map.put("expOpenNumbers", tsHtExpressOpenNumberDtoList);
                map.put("currentPage", currentPage);

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

    /**
     * @Purpose  录入快递单号
     * @version  3.0
     * @author   lubin
     * @param    batchExpressNumberDto
     * @return   RetInfo
     */
    @Override
    public RetInfo insertExpressNumber(BatchExpressNumberDto batchExpressNumberDto) {
        String logInfo = this.getClass().getName() + ":insertExpressNumber:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            if(batchExpressNumberDto.getNumber_size()>0){
                String startNumber=batchExpressNumberDto.getStar_number();
                String start="";
                for(int i=startNumber.length();i>0;i--){
                    if(Character.isDigit(startNumber.charAt(i-1))){
                        start=startNumber.charAt(i-1)+start;
                    }else {
                        break;
                    }
                }
                if(start.length()>0){
                    List<TsHtExpressOpenNumberDto> numberDtos=new ArrayList<TsHtExpressOpenNumberDto>();
                    for(int i=0;i<batchExpressNumberDto.getNumber_size();i++){
                        TsHtExpressOpenNumberDto expressOpenNumberDto=new TsHtExpressOpenNumberDto();
                        expressOpenNumberDto.setExpress_id(batchExpressNumberDto.getExpress_id());
                        expressOpenNumberDto.setAgent_id(0);
                        expressOpenNumberDto.setExp_open_state(0);
                        expressOpenNumberDto.setRegion_id(batchExpressNumberDto.getRegion_id());
                        long expressNumber=Long.valueOf(start)+i;
                        if(String.valueOf(expressNumber).length()>start.length()){
                            retInfo.setMark("1");
                            retInfo.setTip("请检查运单号");
                            break;
                        }
                        expressOpenNumberDto.setExpress_number(startNumber.substring(0,startNumber.length()- start.length())+expressNumber);
                        numberDtos.add(expressOpenNumberDto);
                    }
                    tsHtExpressOpenNumberRepository.batchInsertExpressNumber(numberDtos);
                    retInfo.setMark("0");
                    retInfo.setTip("运单添加成功.");
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("请检查运单号");
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("请输入单号数量.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTsHtExpressOpenNumberRepository(TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository) {
        this.tsHtExpressOpenNumberRepository = tsHtExpressOpenNumberRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

    public void setTdHtAgentRepository(TdHtAgentRepository tdHtAgentRepository) {
        this.tdHtAgentRepository = tdHtAgentRepository;
    }
}
