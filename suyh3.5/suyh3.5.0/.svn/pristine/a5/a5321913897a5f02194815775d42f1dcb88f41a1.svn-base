package cn.hotol.app.service.three.logistics;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtExpressSearch;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.logistics.ExpDistinguishDto;
import cn.hotol.app.bean.dto.logistics.LogisticCodeDto;
import cn.hotol.app.bean.dto.logistics.LogisticsInfoDto;
import cn.hotol.app.bean.dto.logistics.TdHtExpressSearchDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.TdHtExpressSearchRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by LuBin
 * Date 2016-12-01.
 */

@Service
public class LogisticsServiceImpl implements LogisticsService {

    private static Logger logger = Logger.getLogger(LogisticsServiceImpl.class);

    @Resource
    private TdHtExpressSearchRepository tdHtExpressSearchRepository;

    /**
     * @param logisticCodeDto
     * @return RetInfo
     * @Purpose 根据快递单号查询快递公司
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findExpressCodeByWaybill(LogisticCodeDto logisticCodeDto) {
        String logInfo = this.getClass().getName() + ":findExpressCodeByWaybill:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //调用快递鸟的识别单号接口获取快递公司数据
            KdApiOrderDistinguish api = new KdApiOrderDistinguish();
            String result = api.getOrderTracesByJson(logisticCodeDto.getWaybill_number());

            //从字典中获取所有快递公司
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);
            //解析快递鸟返回的json
            JSONObject jsonObject = JSONObject.fromObject(result);
            if (jsonObject.getBoolean("Success")) {
                JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("Shippers"));
                List<Map> mapList = jsonArray;
                List<ExpDistinguishDto> expDistinguishDtoList = new ArrayList<ExpDistinguishDto>();
                for (int i = 0; i < mapList.size(); i++) {
                    Map logisticMap = mapList.get(i);
                    ExpDistinguishDto expDistinguish = new ExpDistinguishDto();
                    expDistinguish.setShipper_name(logisticMap.get("ShipperName").toString());
                    expDistinguish.setShipper_code(logisticMap.get("ShipperCode").toString());
                    for (int j = 0; j < expressCompanyList.size(); j++) {
                        TsHtDictDto tsHtDictDto = expressCompanyList.get(j);
                        if (tsHtDictDto.getDict_code().equals(expDistinguish.getShipper_code())) {
                            expDistinguish.setExpress_logo(tsHtDictDto.getDict_img());
                        } else {
                            expDistinguish.setExpress_logo("");
                        }
                    }
                    expDistinguishDtoList.add(expDistinguish);
                }
                retInfo.setMark("0");
                retInfo.setTip("快递单号识别成功.");
                retInfo.setObj(expDistinguishDtoList);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("未识别的快递单号.");
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
     * @param logisticCodeDto
     * @param tdHtMemberDto   登录用户
     * @return RetInfo
     * @Purpose 通过快递公司和单号查询物流信息
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo findExpressLogisticByWaybill(LogisticCodeDto logisticCodeDto, TdHtMemberDto tdHtMemberDto) {
        String logInfo = this.getClass().getName() + ":findExpressLogisticByWaybill:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
            String result = api.getOrderTracesByJson(logisticCodeDto.getShipper_code(), logisticCodeDto.getWaybill_number());

            TdHtExpressSearch tdHtExpressSearch = new TdHtExpressSearch();
            String detailed = "没有物流信息";
            if(!"".equals(result) && result != null){
                //解析快递鸟返回的json
                JSONObject jsonObject = JSONObject.fromObject(result);
                if (jsonObject.getBoolean("Success")) {
                    int state = 0;

                    if (jsonObject.get("State") != null) {
                        state = Integer.parseInt(jsonObject.get("State").toString());
                        JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("Traces"));
                        List<Map> mapList = jsonArray;
                        List<LogisticsInfoDto> logisticsInfoDtoList = new ArrayList<LogisticsInfoDto>();
                        for (int i = mapList.size() - 1; i >= 0; i--) {
                            LogisticsInfoDto logisticsInfoDto = new LogisticsInfoDto();
                            Map logisticsInfoMap = mapList.get(i);
                            logisticsInfoDto.setAccept_time(logisticsInfoMap.get("AcceptTime").toString());
                            logisticsInfoDto.setAccept_station(logisticsInfoMap.get("AcceptStation").toString());
                            logisticsInfoDtoList.add(logisticsInfoDto);
                            if (i == mapList.size() - 1) {
                                detailed = logisticsInfoMap.get("AcceptStation").toString();
                            }
                        }
                        logisticCodeDto.setLogistics_info_dto_list(logisticsInfoDtoList);
                        retInfo.setMark("0");
                        retInfo.setTip("物流查询成功.");
                        retInfo.setObj(logisticCodeDto);
                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("没有物流信息.");
                    }
                    tdHtExpressSearch.setExp_state(state);
                    logisticCodeDto.setState(state);
                    tdHtExpressSearch.setExp_detailed(detailed);
                } else {
                    tdHtExpressSearch.setExp_state(0);
                    tdHtExpressSearch.setExp_detailed("没有物流信息");
                    retInfo.setMark("1");
                    retInfo.setTip("没有物流信息.");
                }
            }else {
                tdHtExpressSearch.setExp_state(0);
                tdHtExpressSearch.setExp_detailed("没有物流信息");
                retInfo.setMark("1");
                retInfo.setTip("没有物流信息.");
            }

            //用户已登录
            if (tdHtMemberDto != null) {
                tdHtExpressSearch.setExp_name(logisticCodeDto.getShipper_name());
                tdHtExpressSearch.setExp_number(logisticCodeDto.getWaybill_number());
                tdHtExpressSearch.setExp_sea_month(CommonUtil.getMonth());
                tdHtExpressSearch.setExp_sea_time(new Timestamp(System.currentTimeMillis()));
                tdHtExpressSearch.setMemb_id(tdHtMemberDto.getMemb_id());
                tdHtExpressSearch.setExp_code(logisticCodeDto.getShipper_code());

                //查询用户的查件记录
                TdHtExpressSearch tdHtExpressSearchDB = tdHtExpressSearchRepository.findExpressSearchByWaybill(tdHtExpressSearch);

                if (tdHtExpressSearchDB != null) {
                    tdHtExpressSearch.setExp_sea_id(tdHtExpressSearchDB.getExp_sea_id());
                    tdHtExpressSearchRepository.updateExpressSearch(tdHtExpressSearch);
                } else {
                    tdHtExpressSearchRepository.insertExpressSearch(tdHtExpressSearch);
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
     * @param token
     * @return RetInfo
     * @Purpose 查询前十条的查件记录
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findFirstTenExpSearch(String token) {
        String logInfo = this.getClass().getName() + ":findExpressLogisticByWaybill:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            List<TdHtExpressSearchDto> tdHtExpressSearchDtoList = tdHtExpressSearchRepository.findTdHtExpressSearch(member.getMemb_id());

            retInfo.setMark("0");
            retInfo.setTip("查件记录查询成功.");
            retInfo.setObj(tdHtExpressSearchDtoList);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
