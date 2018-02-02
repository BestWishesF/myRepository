package cn.hotol.app.service.express.sdk.zhongtong;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.BatchExpressOrderDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public class ExpressSdkZhongTongServiceImpl implements ExpressSdkZhongTongService {

    private  static Logger logger = Logger.getLogger(ExpressSdkZhongTongServiceImpl.class);

    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    private TsHtExpressSdkRepository tsHtExpressSdkRepository;
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;

    /**
     * @Purpose  中通快递查询可用运单数量
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,express_no
     * @return   RetInfo
     */
    @Override
    public RetInfo queryWaybillAmount(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String express_no) {
        String logInfo = this.getClass().getName() + ":queryWaybillAmount:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_QUERY_WAYBILL_AMOUNT);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, Object> map=new HashMap();
            map.put("lastno",express_no);
            String date= CommonUtil.getDateAndTimes();
            String content = Base64Util.encode(JSONObject.fromObject(map).toString().getBytes("utf-8")).trim();
            String verify = MD5Util.MD5Encoder(tsHtExpressSdkDto.getApp_key() + date + content + tsHtExpressSdkDto.getApp_secret());

            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            NameValuePair[] data = {
                    new NameValuePair("style", "json"),
                    new NameValuePair("partner", tsHtExpressSdkDto.getApp_key()),
                    new NameValuePair("datetime", date),
                    new NameValuePair("content", content),
                    new NameValuePair("verify", verify),
                    new NameValuePair("func", tsHtExpressSdkDto.getSdk_code()),
            };
            postMethod.setRequestBody(data);
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                String result = postMethod.getResponseBodyAsString();
                JSONObject resultJson=JSONObject.fromObject(result);
                if("true".equals(resultJson.get("result"))){
                    String counter=resultJson.getString("counter");
                    int amount=JSONObject.fromObject(counter).getInt("available");
                    retInfo.setMark("0");
                    retInfo.setTip("中通快递获取可用单号数量成功");
                    retInfo.setObj(amount);
                }else{
                    retInfo.setMark("1");
                    retInfo.setTip("中通快递获取可用单号数量错误:"+resultJson.getString("remark"));
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("中通快递获取可用单号数量错误");
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
     * @Purpose  中通快递提交订单
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return   RetInfo
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

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_SUBMIT_ORDER);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);

            Map sendMap = new HashMap();
            sendMap.put("mobile", orderDto.getAdd_mobile_phone());
            sendMap.put("phone", orderDto.getAdd_telephone());
            sendMap.put("name", orderDto.getAdd_name());
            String sendAddress=dicts.get(""+orderDto.getAdd_province()).getCode_name();
            sendAddress=sendAddress+","+dicts.get(""+orderDto.getAdd_city()).getCode_name();
            sendAddress=sendAddress+","+dicts.get(""+orderDto.getAdd_region()).getCode_name();
            sendMap.put("city", sendAddress);
            sendMap.put("address", orderDto.getAdd_detail_address());

            if(collectDtoList.size()>50||collectDtoList.size()==1){
                boolean is_ok=true;
                for (int i=0;i<collectDtoList.size();i++){
                    TdHtExpressOrderCollectDto collectDto=collectDtoList.get(i);
                    Map receiverMap=new HashMap();
                    receiverMap.put("mobile", collectDto.getAdd_mobile_phone());
                    receiverMap.put("phone", collectDto.getAdd_telephone());
                    receiverMap.put("name", collectDto.getAdd_name());
                    String receiverAddress=dicts.get(""+collectDto.getAdd_province()).getCode_name();
                    receiverAddress=receiverAddress+","+dicts.get(""+collectDto.getAdd_city()).getCode_name();
                    receiverAddress=receiverAddress+","+dicts.get(""+collectDto.getAdd_region()).getCode_name();
                    receiverMap.put("city", receiverAddress);
                    receiverMap.put("address", collectDto.getAdd_detail_address());

                    Map dataMap=new HashMap();
                    dataMap.put("id", collectDto.getExp_ord_clt_number());
                    if (collectDto.getExpress_number()!=null&&!"".equals(collectDto.getExpress_number())) {
                        dataMap.put("mailno", collectDto.getExpress_number());
                    }
                    dataMap.put("sender", sendMap);
                    dataMap.put("receiver", receiverMap);
                    String date= CommonUtil.getDateAndTimes();
                    String content = Base64Util.encode(JSONObject.fromObject(dataMap).toString().getBytes("utf-8")).trim();
                    String verify = MD5Util.MD5Encoder(tsHtExpressSdkDto.getApp_key() + date + content + tsHtExpressSdkDto.getApp_secret());

                    HttpClient httpClient = new HttpClient();
                    PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
                    postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
                    NameValuePair[] data = {
                            new NameValuePair("style", "json"),
                            new NameValuePair("partner", tsHtExpressSdkDto.getApp_key()),
                            new NameValuePair("datetime", date),
                            new NameValuePair("content", content),
                            new NameValuePair("verify", verify),
                            new NameValuePair("func", tsHtExpressSdkDto.getSdk_code()),
                    };
                    postMethod.setRequestBody(data);
                    int statusCode = httpClient.executeMethod(postMethod);
                    if (statusCode == HttpStatus.SC_OK) {
                        String result = postMethod.getResponseBodyAsString();
                        JSONObject resultJson=JSONObject.fromObject(result);
                        if("true".equals(resultJson.get("result"))){
                            String keys=resultJson.getString("keys");
                            JSONObject keysJson=JSONObject.fromObject(keys);
                            Map resultMap=new HashMap();
                            resultMap.put("orderid",keysJson.get("orderid"));//订单号
                            resultMap.put("mailno",keysJson.get("mailno"));//快递单号
                            resultMap.put("mark",keysJson.get("mark"));//这个是大头笔
                            resultMap.put("isupdate",keysJson.get("isupdate"));//true 表示改订单是之前已经上传过的订单获取的单号也是之前获取的单号，false为新的订单
                            resultMap.put("sitecode",keysJson.get("sitecode"));//网点编号
                            resultMap.put("sitename",keysJson.get("sitename"));//网点名称
                            retInfo.setMark("0");
                            retInfo.setTip("中通快递提交订单成功");
                            retInfo.setObj(resultMap);
                        }else{
                            is_ok=false;
                            retInfo.setMark("1");
                            retInfo.setTip("中通快递提交订单错误:"+resultJson.getString("remark"));
                        }
                    }else{
                        is_ok=false;
                        retInfo.setMark("1");
                        retInfo.setTip("中通快递提交订单错误");
                    }
                }
                if(is_ok){
                    orderDto.setStorge_time(new Date());
                    orderDto.setExp_ord_state(5);
                    tdHtExpressOrderRepository.updateStorageExpressOrder(orderDto);
                }
            }else{
                List<BatchExpressOrderDto> batchExpressOrderDtoList=new ArrayList<BatchExpressOrderDto>();
                for (int i=0;i<collectDtoList.size();i++){
                    BatchExpressOrderDto batchExpressOrderDto=new BatchExpressOrderDto();
                    batchExpressOrderDto.setOrder_dto(orderDto);
                    batchExpressOrderDto.setCollect_dto(collectDtoList.get(i));
                    batchExpressOrderDtoList.add(batchExpressOrderDto);
                }
                retInfo=batchSubmitOrder(tsHtExpressOpenRegionDto,batchExpressOrderDtoList);
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
     * @Purpose  中通快递批量提交订单
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,batchExpressOrderDtoList
     * @return   RetInfo
     */
    @Transactional
    @Override
    public RetInfo batchSubmitOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, List<BatchExpressOrderDto> batchExpressOrderDtoList) {
        String logInfo = this.getClass().getName() + ":batchSubmitOrder:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_BATCH_SUBMIT_ORDER);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);

            boolean is_ok=true;
            List<Map> dataMaps=new ArrayList<Map>();
            for(int i=0;i<batchExpressOrderDtoList.size();i++){
                BatchExpressOrderDto batchExpressOrderDto=batchExpressOrderDtoList.get(i);
                TdHtExpressOrderDto orderDto=batchExpressOrderDto.getOrder_dto();
                TdHtExpressOrderCollectDto collectDto=batchExpressOrderDto.getCollect_dto();

                Map sendMap = new HashMap();
                sendMap.put("mobile", orderDto.getAdd_mobile_phone());
                sendMap.put("phone", orderDto.getAdd_telephone());
                sendMap.put("name", orderDto.getAdd_name());
                String sendAddress=dicts.get(""+orderDto.getAdd_province()).getCode_name();
                sendAddress=sendAddress+","+dicts.get(""+orderDto.getAdd_city()).getCode_name();
                sendAddress=sendAddress+","+dicts.get(""+orderDto.getAdd_region()).getCode_name();
                sendMap.put("city", sendAddress);
                sendMap.put("address", orderDto.getAdd_detail_address());

                Map receiverMap=new HashMap();
                receiverMap.put("mobile", collectDto.getAdd_mobile_phone());
                receiverMap.put("phone", collectDto.getAdd_telephone());
                receiverMap.put("name", collectDto.getAdd_name());
                String receiverAddress=dicts.get(""+collectDto.getAdd_province()).getCode_name();
                receiverAddress=receiverAddress+","+dicts.get(""+collectDto.getAdd_city()).getCode_name();
                receiverAddress=receiverAddress+","+dicts.get(""+collectDto.getAdd_region()).getCode_name();
                receiverMap.put("city", receiverAddress);
                receiverMap.put("address", collectDto.getAdd_detail_address());

                Map dataMap=new HashMap();
                dataMap.put("id", collectDto.getExp_ord_clt_number());
                if (collectDto.getExpress_number()!=null&&!"".equals(collectDto.getExpress_number())) {
                    dataMap.put("mailno", collectDto.getExpress_number());
                }
                dataMap.put("sender", sendMap);
                dataMap.put("receiver", receiverMap);
                dataMaps.add(dataMap);
            }
            if(is_ok){
                if(dataMaps.size()>0){
                    String date= CommonUtil.getDateAndTimes();
                    String content = Base64Util.encode(JSONObject.fromObject(dataMaps).toString().getBytes("utf-8")).trim();
                    String verify = MD5Util.MD5Encoder(tsHtExpressSdkDto.getApp_key() + date + content + tsHtExpressSdkDto.getApp_secret());

                    HttpClient httpClient = new HttpClient();
                    PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
                    postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
                    NameValuePair[] data = {
                            new NameValuePair("style", "json"),
                            new NameValuePair("partner", tsHtExpressSdkDto.getApp_key()),
                            new NameValuePair("datetime", date),
                            new NameValuePair("content", content),
                            new NameValuePair("verify", verify),
                            new NameValuePair("func", tsHtExpressSdkDto.getSdk_code()),
                    };
                    postMethod.setRequestBody(data);
                    int statusCode = httpClient.executeMethod(postMethod);
                    if (statusCode == HttpStatus.SC_OK) {
                        String result = postMethod.getResponseBodyAsString();
                        JSONObject resultJson=JSONObject.fromObject(result);
                        if("true".equals(resultJson.get("result"))){
                            String keys=resultJson.getString("keys");
                            JSONArray keysJson=JSONArray.fromObject(keys);
                            List<Map> resultMapList=new ArrayList<Map>();
                            for(int j=0;j<keysJson.size();j++){
                                Map resultMap=new HashMap();
                                JSONObject jsonObject=JSONObject.fromObject(keysJson.get(j));
                                resultMap.put("result",jsonObject.get("result"));
                                resultMap.put("id",jsonObject.get("orderid"));//订单号
                                resultMap.put("orderid",jsonObject.get("orderid"));//订单号
                                resultMap.put("mailno",jsonObject.get("mailno"));//快递单号
                                resultMap.put("mark",jsonObject.get("mark"));//这个是大头笔
                                resultMap.put("isupdate",jsonObject.get("isupdate"));//true 表示改订单是之前已经上传过的订单获取的单号也是之前获取的单号，false为新的订单
                                resultMap.put("sitecode",jsonObject.get("sitecode"));//网点编号
                                resultMap.put("sitename",jsonObject.get("sitename"));//网点名称
                                resultMapList.add(resultMap);
                            }
                            retInfo.setMark("0");
                            retInfo.setTip("中通快递批量提交订单成功");
                            retInfo.setObj(resultMapList);

                            for(int i=0;i<batchExpressOrderDtoList.size();i++) {
                                BatchExpressOrderDto batchExpressOrderDto = batchExpressOrderDtoList.get(i);
                                TdHtExpressOrderDto orderDto = batchExpressOrderDto.getOrder_dto();
                                orderDto.setStorge_time(new Date());
                                orderDto.setExp_ord_state(5);
                                tdHtExpressOrderRepository.updateStorageExpressOrder(orderDto);
                            }
                        }else{
                            retInfo.setMark("1");
                            retInfo.setTip("中通快递批量提交订单错误:"+resultJson.getString("remark"));
                        }
                    }else{
                        retInfo.setMark("1");
                        retInfo.setTip("中通快递批量提交订单错误");
                    }
                }
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
     * @Purpose  中通快递获取打印大头笔数据
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   RetInfo
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
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_OBTAIN_PAINT_MARKER);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            Map dataMap=new HashMap();
            dataMap.put("unionCode",UUID.randomUUID().toString());
            dataMap.put("send_province",map.get("send_pro"));
            dataMap.put("send_city",map.get("send_city"));
            dataMap.put("send_district",map.get("send_area"));
            dataMap.put("send_address",map.get("send_address"));
            dataMap.put("receive_province",map.get("rev_pro"));
            dataMap.put("receive_city",map.get("rev_city"));
            dataMap.put("receive_district",map.get("rev_area"));
            dataMap.put("receive_address",map.get("rev_address"));
            String data=JSONObject.fromObject(dataMap).toString();

            String data_digest = DigestUtil.digest(data, tsHtExpressSdkDto.getApp_key(), DigestUtil.UTF8);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("data", data);
            params.put("data_digest", data_digest);
            params.put("company_id", tsHtExpressSdkDto.getApp_secret());
            params.put("msg_type", tsHtExpressSdkDto.getSdk_code());
            // 向接口发送请求
            String response = HttpUtil.post(tsHtExpressSdkDto.getSdk_itf_url(), DigestUtil.UTF8, params);
            if(response!=null){
                JSONObject responseJson=JSONObject.fromObject(response);
                if(responseJson.getBoolean("status")){
                    JSONObject resultJson=JSONObject.fromObject(responseJson.get("result"));
                    map.put("mark",resultJson.get("mark"));
                    map.put("print_mark",resultJson.get("print_mark"));
                    retInfo.setMark("1");
                    retInfo.setTip("中通快递获取大头笔成功");
                    retInfo.setObj(map);
                }else{
                    retInfo.setMark("1");
                    retInfo.setTip("中通快递获取大头笔错误:"+responseJson.getString("message"));
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("中通快递获取大头笔错误");
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
     * @Purpose  中通快递查询物流
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,express_no
     * @return   RetInfo
     */
    @Override
    public RetInfo traceOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String express_no) {
        String logInfo = this.getClass().getName() + ":traceOrder:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_TRACE_ORDER);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            List dataList=new ArrayList();
            dataList.add(express_no);
            String data = JSONArray.fromObject(dataList).toString();

            String data_digest = DigestUtil.digest(data, tsHtExpressSdkDto.getApp_key(), DigestUtil.UTF8);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("data", data);
            params.put("data_digest", data_digest);
            params.put("company_id", tsHtExpressSdkDto.getApp_secret());
            params.put("msg_type", tsHtExpressSdkDto.getSdk_code());
            // 向接口发送请求
            String response = HttpUtil.post(tsHtExpressSdkDto.getSdk_itf_url(), DigestUtil.UTF8, params);
            if(response!=null){
                JSONObject responseJson=JSONObject.fromObject(response);
                if(responseJson.getBoolean("status")){
                    JSONArray dataArray=JSONArray.fromObject(responseJson.get("data"));
                    JSONObject jsonObject=JSONObject.fromObject(dataArray.get(0));
                    JSONArray tracesArray=JSONArray.fromObject(jsonObject.get("traces"));
                    List<Map> list=new ArrayList<Map>();
                    for (int i=0;i<tracesArray.size();i++){
                        Map map=new HashMap();
                        JSONObject result=JSONObject.fromObject(tracesArray.get(i));
                        map.put("desc",result.get("desc"));
                        map.put("dispOrRecMan",result.get("dispOrRecMan"));
                        map.put("dispOrRecManCode",result.get("dispOrRecManCode"));
                        map.put("dispOrRecManPhone",result.get("dispOrRecManPhone"));
                        map.put("isCenter",result.get("isCenter"));
                        map.put("preOrNextCity",result.get("preOrNextCity"));
                        map.put("preOrNextProv",result.get("preOrNextProv"));
                        map.put("preOrNextSite",result.get("preOrNextSite"));
                        map.put("preOrNextSiteCode",result.get("preOrNextSiteCode"));
                        map.put("preOrNextSitePhone",result.get("preOrNextSitePhone"));
                        map.put("remark",result.get("remark"));
                        map.put("scanCity",result.get("scanCity"));
                        map.put("scanDate",result.get("scanDate"));
                        map.put("scanProv",result.get("scanProv"));
                        map.put("scanSite",result.get("scanSite"));
                        map.put("scanSiteCode",result.get("scanSiteCode"));
                        map.put("scanSitePhone",result.get("scanSitePhone"));
                        map.put("scanType",result.get("scanType"));
                        map.put("signMan",result.get("signMan"));
                        list.add(map);
                    }
                    retInfo.setMark("1");
                    retInfo.setTip("中通快递查询物流成功");
                    retInfo.setObj(list);
                }else{
                    retInfo.setMark("1");
                    retInfo.setTip("中通快递查询物流错误:"+responseJson.getString("msg"));
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("中通快递查询物流错误");
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
