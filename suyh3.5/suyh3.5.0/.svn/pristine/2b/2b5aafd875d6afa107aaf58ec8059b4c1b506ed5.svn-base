package cn.hotol.app.service.express.sdk.shentong;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.number.BatchExpressNumberDto;
import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.common.util.MessageXMLUtil;
import cn.hotol.app.repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public class ExpressSdkShenTongServiceImpl implements ExpressSdkShenTongService {

    private static Logger logger = Logger.getLogger(ExpressSdkShenTongServiceImpl.class);

    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    private TsHtExpressSdkRepository tsHtExpressSdkRepository;
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;

    /**
     * @param batchExpressNumberDto
     * @return RetInfo
     * @Purpose 申通快递提取单号
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo extractExpressNo(BatchExpressNumberDto batchExpressNumberDto) {
        String logInfo = this.getClass().getName() + ":extractExpressNo:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto=new TsHtExpressOpenRegionDto();
            tsHtExpressOpenRegionDto.setExpress_id(batchExpressNumberDto.getExpress_id());
            tsHtExpressOpenRegionDto.setRegion_id(String.valueOf(batchExpressNumberDto.getRegion_id()));
            List<TsHtExpressOpenRegionDto> ExpressOpenRegionDtos = tsHtExpressOpenRegionRepository.findRegOpenNumByCity(tsHtExpressOpenRegionDto);

            if(ExpressOpenRegionDtos.size()>0){
                TsHtExpressSdkDto expressSdkDto = new TsHtExpressSdkDto();
                expressSdkDto.setEoa_id(ExpressOpenRegionDtos.get(0).getEoa_id());
                expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_EXTRACT_EXPRESS_NO);

                TsHtExpressSdkDto tsHtExpressSdkDto = tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

                HttpClient httpClient = new HttpClient();
                PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
                postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
                NameValuePair[] data = {
                        new NameValuePair("code", tsHtExpressSdkDto.getSdk_code()),
                        new NameValuePair("data_digest", tsHtExpressSdkDto.getAccess_token()),
                        new NameValuePair("cuspwd", tsHtExpressSdkDto.getCustomer_password()),//100@suyh
                        new NameValuePair("cusname", tsHtExpressSdkDto.getCustomer_name()),
                        new NameValuePair("cusite", tsHtExpressSdkDto.getDot_name()),
                        new NameValuePair("len", String.valueOf(batchExpressNumberDto.getNumber_size()))};
                postMethod.setRequestBody(data);
                int statusCode = httpClient.executeMethod(postMethod);
                if (statusCode == HttpStatus.SC_OK) {
                    String result = postMethod.getResponseBodyAsString();
                    JSONObject jsonObject = JSONObject.fromObject(result);
                    if (jsonObject.getBoolean("success")) {
                        List<TsHtExpressOpenNumberDto> tsHtExpressOpenNumberDtoList = new ArrayList<TsHtExpressOpenNumberDto>();

                        String waybillStr = jsonObject.getString("data");
                        if (batchExpressNumberDto.getNumber_size() > 1) {
                            String[] waybillStrs = waybillStr.split(",");
                            for (int i = 0; i < waybillStrs.length; i++) {
                                TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto = new TsHtExpressOpenNumberDto();
                                tsHtExpressOpenNumberDto.setAgent_id(0);
                                tsHtExpressOpenNumberDto.setExp_open_state(0);
                                tsHtExpressOpenNumberDto.setExpress_id(tsHtExpressOpenRegionDto.getExpress_id());
                                tsHtExpressOpenNumberDto.setRegion_id(Integer.valueOf(tsHtExpressOpenRegionDto.getRegion_id()));
                                tsHtExpressOpenNumberDto.setExpress_number(waybillStrs[i]);
                                tsHtExpressOpenNumberDtoList.add(tsHtExpressOpenNumberDto);
                            }
                        } else {
                            TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto = new TsHtExpressOpenNumberDto();
                            tsHtExpressOpenNumberDto.setAgent_id(0);
                            tsHtExpressOpenNumberDto.setExp_open_state(0);
                            tsHtExpressOpenNumberDto.setExpress_id(tsHtExpressOpenRegionDto.getExpress_id());
                            tsHtExpressOpenNumberDto.setRegion_id(Integer.valueOf(tsHtExpressOpenRegionDto.getRegion_id()));
                            tsHtExpressOpenNumberDto.setExpress_number(waybillStr);
                            tsHtExpressOpenNumberDtoList.add(tsHtExpressOpenNumberDto);
                        }
                        tsHtExpressOpenNumberRepository.batchInsertExpressNumber(tsHtExpressOpenNumberDtoList);
                        retInfo.setMark("0");
                        retInfo.setTip("申通快递获取单号成功");
                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("申通快递获取单号错误:" + jsonObject.getString("data"));
                    }
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("申通快递获取单号错误");
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("该地区未开通寄件服务.");
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
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 申通快递提交订单
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo submitOrder(TdHtExpressOrderDto tdHtExpressOrderDto) {
        String logInfo = this.getClass().getName() + ":submitOrder:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto orderDto=tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(tdHtExpressOrderDto.getExp_ord_id());
            List<TdHtExpressOrderCollectDto> collectDtoList=tdHtExpressCollectRepository.findExpressCollectByExpOrdId(tdHtExpressOrderDto.getExp_ord_id());

            TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto=new TsHtExpressOpenRegionDto();
            tsHtExpressOpenRegionDto.setExpress_id(orderDto.getExpress_id());
            tsHtExpressOpenRegionDto.setRegion_id(String.valueOf(orderDto.getAdd_region()));
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto = new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_SUBMIT_ORDER);

            TsHtExpressSdkDto tsHtExpressSdkDto = tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            boolean is_ok=true;
            for (int i=0;i<collectDtoList.size();i++) {
                TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);
                Map map = new HashMap();
                map.put("billno", collectDto.getExpress_number());
                map.put("senddate", CommonUtil.getDate());
                map.put("sendsite", tsHtExpressSdkDto.getDot_name());
                map.put("sendcus", tsHtExpressSdkDto.getCustomer_name());
                map.put("sendperson", orderDto.getAdd_name());
                if (orderDto.getAdd_mobile_phone() == null || "".equals(orderDto.getAdd_mobile_phone())) {
                    map.put("sendtel", orderDto.getAdd_telephone());
                } else {
                    map.put("sendtel", orderDto.getAdd_mobile_phone());
                }
                map.put("receiveperson", collectDto.getAdd_name());
                if (collectDto.getAdd_mobile_phone() == null || "".equals(collectDto.getAdd_mobile_phone())) {
                    map.put("receivetel", collectDto.getAdd_telephone());
                } else {
                    map.put("receivetel", collectDto.getAdd_mobile_phone());
                }
                map.put("goodsname", collectDto.getExp_ord_category());
                map.put("inputdate", CommonUtil.getDate());
                map.put("inputperson", tsHtExpressSdkDto.getCustomer_name());
                map.put("inputsite", tsHtExpressSdkDto.getDot_name());
                map.put("receiveprovince", dicts.get("" + collectDto.getAdd_province()).getCode_name());
                map.put("receivecity", dicts.get("" + collectDto.getAdd_city()).getCode_name());
                map.put("receivearea", dicts.get("" + collectDto.getAdd_region()).getCode_name());
                map.put("receiveaddress", collectDto.getAdd_detail_address());
                map.put("sendprovince", dicts.get("" + orderDto.getAdd_province()).getCode_name());
                map.put("sendcity", dicts.get("" + orderDto.getAdd_city()).getCode_name());
                map.put("sendarea", dicts.get("" + orderDto.getAdd_region()).getCode_name());
                map.put("sendaddress", orderDto.getAdd_detail_address());
                map.put("orderno", collectDto.getExp_ord_clt_number());
                List list = new ArrayList();
                list.add(map);
                JSONArray jsonData = JSONArray.fromObject(list);

                HttpClient httpClient = new HttpClient();
                PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
                postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
                NameValuePair[] data = {
                        new NameValuePair("code", tsHtExpressSdkDto.getSdk_code()),
                        new NameValuePair("data_digest", tsHtExpressSdkDto.getAccess_token()),
                        new NameValuePair("cuspwd", tsHtExpressSdkDto.getCustomer_password()),
                        new NameValuePair("data", jsonData.toString())};
                postMethod.setRequestBody(data);
                int statusCode = httpClient.executeMethod(postMethod);
                if (statusCode == HttpStatus.SC_OK) {
                    String result = postMethod.getResponseBodyAsString();
                    JSONObject jsonObject = JSONObject.fromObject(result);
                    if (jsonObject.getBoolean("success")) {
                        retInfo.setMark("0");
                        retInfo.setTip("申通订单提交成功");
                    } else {
                        is_ok=false;
                        retInfo.setMark("1");
                        retInfo.setTip("申通订单提交失败:" + jsonObject.getString("data"));
                    }
                } else {
                    is_ok=false;
                    retInfo.setMark("1");
                    retInfo.setTip("申通订单提交失败");
                }
            }
            if(is_ok){
            orderDto.setStorge_time(new Date());
            orderDto.setExp_ord_state(5);
            tdHtExpressOrderRepository.updateStorageExpressOrder(orderDto);
            retInfo.setMark("0");
            retInfo.setTip("申通订单提交成功");
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
     * @param map
     * @return RetInfo
     * @Purpose 申通快递电子面单打印所需大头笔数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo obtainPaintMarker(Map<String, Object> map) {
        String logInfo = this.getClass().getName() + ":obtainPaintMarker:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto=new TsHtExpressOpenRegionDto();
            tsHtExpressOpenRegionDto.setExpress_id(Integer.valueOf(map.get("express_id").toString()));
            tsHtExpressOpenRegionDto.setRegion_id(map.get("add_region").toString());
            TsHtExpressOpenRegionDto ExpressOpenRegionDto = tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto = new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_OBTAIN_PAINT_MARKER);

            TsHtExpressSdkDto tsHtExpressSdkDto = tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            NameValuePair[] data = {
                    new NameValuePair("code", tsHtExpressSdkDto.getSdk_code()),
                    new NameValuePair("data_digest", tsHtExpressSdkDto.getAccess_token()),
                    new NameValuePair("cuspwd", tsHtExpressSdkDto.getCustomer_password()),
                    new NameValuePair("receiveprovince", map.get("rev_pro").toString()),
                    new NameValuePair("receivecity", map.get("rev_city").toString()),
                    new NameValuePair("receivearea", map.get("rev_area").toString())};
            postMethod.setRequestBody(data);
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                String result = postMethod.getResponseBodyAsString();
                JSONObject jsonObject = JSONObject.fromObject(result);
                if (jsonObject.getBoolean("success")) {

                    map.put("marker",jsonObject.getString("data"));

                    retInfo.setMark("0");
                    retInfo.setTip("申通获取大头笔成功");
                    retInfo.setObj(map);
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("申通获取大头笔失败:" + jsonObject.getString("data"));
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("申通获取大头笔失败");
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
     * @param tsHtExpressOpenRegionDto,expressNumber
     * @return RetInfo
     * @Purpose 申通快递查询物流
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo traceOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String expressNumber) {
        String logInfo = this.getClass().getName() + ":traceOrder:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto ExpressOpenRegionDto = tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto = new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_TRACE_ORDER);

            TsHtExpressSdkDto tsHtExpressSdkDto = tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(tsHtExpressSdkDto.getSdk_itf_url());
            getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            NameValuePair[] nameValuePairs = {new NameValuePair("billcode", expressNumber)};
            getMethod.setQueryString(nameValuePairs);
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                //读取内容
                byte[] responseBody = getMethod.getResponseBody();
                //处理内容
                String rs = new String(responseBody, new String(responseBody).substring(new String(responseBody).indexOf("encoding") + 10, new String(responseBody).indexOf("root") - 4));
                if (rs.indexOf("\n") >= 0) {
                    String[] s = rs.split("\n");
                    rs = "";
                    for (int i = 0; i < s.length; i++) {
                        rs = rs + s[i];
                    }
                }
                String res = MessageXMLUtil.xml2JSON(rs.substring(rs.indexOf("root") - 1, rs.length()));
                if (res.indexOf("detail") > 0) {
                    retInfo.setMark("0");
                    retInfo.setTip("申通快递物流查询成功");
                } else {
                    retInfo.setMark("0");
                    retInfo.setTip("没有物流信息");
                    retInfo.setObj(new ArrayList<Map>());
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("申通快递查询物流失败");
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

    public void setTsHtExpressOpenNumberRepository(TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository) {
        this.tsHtExpressOpenNumberRepository = tsHtExpressOpenNumberRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }

    public void setTdHtExpressCollectRepository(TdHtExpressCollectRepository tdHtExpressCollectRepository) {
        this.tdHtExpressCollectRepository = tdHtExpressCollectRepository;
    }
}
