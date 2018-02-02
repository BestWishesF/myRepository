package cn.hotol.app.service.express.sdk.tiantian;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.number.BatchExpressNumberDto;
import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.common.util.MessageXMLUtil;
import cn.hotol.app.repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
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
public class ExpressSdkTianTianServiceImpl implements ExpressSdkTianTianService {

    private static Logger logger = Logger.getLogger(ExpressSdkTianTianServiceImpl.class);

    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    private TsHtExpressSdkRepository tsHtExpressSdkRepository;
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;

    /**
     * @Purpose  天天快递提取单号
     * @version  3.0
     * @author   lubin
     * @param    batchExpressNumberDto
     * @return   RetInfo
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
            List<TsHtExpressOpenRegionDto> ExpressOpenRegionDtos=tsHtExpressOpenRegionRepository.findRegOpenNumByCity(tsHtExpressOpenRegionDto);

            if(ExpressOpenRegionDtos.size()>0){
                TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
                expressSdkDto.setEoa_id(ExpressOpenRegionDtos.get(0).getEoa_id());
                expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_EXTRACT_EXPRESS_NO);

                TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

                Map jsonMap=new HashMap();
                jsonMap.put("site",tsHtExpressSdkDto.getDot_name());
                jsonMap.put("cus",tsHtExpressSdkDto.getCustomer_name());
                jsonMap.put("password",tsHtExpressSdkDto.getCustomer_password());
                jsonMap.put("getcount",batchExpressNumberDto.getNumber_size());
                JSONObject params=JSONObject.fromObject(jsonMap);
                HttpClient httpClient = new HttpClient();
                //创建GET方法的实例
                GetMethod getMethod = new GetMethod(tsHtExpressSdkDto.getSdk_itf_url());
                getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
                NameValuePair[] nameValuePairs = { new NameValuePair("arg1", "手机接口"), new NameValuePair("arg2", "通用_提取空白单号"),new NameValuePair("arg3", params.toString()) };
                getMethod.setQueryString(nameValuePairs);
                int statusCode = 0;
                statusCode = httpClient.executeMethod(getMethod);
                if (statusCode != HttpStatus. SC_OK) {
                    retInfo.setMark("1");
                    retInfo.setTip("天天快递提取单号请求接口错误");
                }else{
                    //读取内容
                    byte[] responseBody = getMethod.getResponseBody();
                    //处理内容
                    String rs=new String(responseBody);
                    if(rs.indexOf("\n")>=0){
                        String[] s=rs.split("\n");
                        rs="";
                        for(int i=0;i<s.length;i++){
                            rs=rs+s[i];
                        }
                    }
                    JSONObject jsonObject=JSONObject.fromObject(rs);
                    String resultCode=jsonObject.getString("resultcode");
                    if("0".equals(resultCode)){
                        List<TsHtExpressOpenNumberDto> tsHtExpressOpenNumberDtoList=new ArrayList<TsHtExpressOpenNumberDto>();

                        String waybillStr=jsonObject.getString("data");
                        String minWaybill="";
                        String maxWaybill="";
                        if(waybillStr.indexOf(",")>0){
                            String[] waybillStrs=waybillStr.split(",");
                            for(int i=0;i<waybillStrs.length;i++){
                                String[] waybillSegment=waybillStrs[i].split("-");
                                minWaybill=waybillSegment[0];
                                maxWaybill=waybillSegment[1];
                                for(long waybill=Long.valueOf(minWaybill);waybill<=Long.valueOf(maxWaybill);waybill++){
                                    TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto=new TsHtExpressOpenNumberDto();
                                    tsHtExpressOpenNumberDto.setAgent_id(0);
                                    tsHtExpressOpenNumberDto.setExp_open_state(0);
                                    tsHtExpressOpenNumberDto.setExpress_id(tsHtExpressOpenRegionDto.getExpress_id());
                                    tsHtExpressOpenNumberDto.setRegion_id(Integer.valueOf(tsHtExpressOpenRegionDto.getRegion_id()));
                                    tsHtExpressOpenNumberDto.setExpress_number(String.valueOf(waybill));
                                    tsHtExpressOpenNumberDtoList.add(tsHtExpressOpenNumberDto);
                                }
                            }
                        }else{
                            String[] waybillSegment=waybillStr.split("-");
                            minWaybill=waybillSegment[0];
                            maxWaybill=waybillSegment[1];
                            for(long waybill=Long.valueOf(minWaybill);waybill<=Long.valueOf(maxWaybill);waybill++){
                                TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto=new TsHtExpressOpenNumberDto();
                                tsHtExpressOpenNumberDto.setAgent_id(0);
                                tsHtExpressOpenNumberDto.setExp_open_state(0);
                                tsHtExpressOpenNumberDto.setExpress_id(tsHtExpressOpenRegionDto.getExpress_id());
                                tsHtExpressOpenNumberDto.setRegion_id(Integer.valueOf(tsHtExpressOpenRegionDto.getRegion_id()));
                                tsHtExpressOpenNumberDto.setExpress_number(String.valueOf(waybill));
                                tsHtExpressOpenNumberDtoList.add(tsHtExpressOpenNumberDto);
                            }
                        }
                        tsHtExpressOpenNumberRepository.batchInsertExpressNumber(tsHtExpressOpenNumberDtoList);
                        retInfo.setMark("0");
                        retInfo.setTip("天天快递提取单号成功.");
                    }else{
                        retInfo.setMark("1");
                        retInfo.setTip("天天快递提取单号错误:"+jsonObject.getString("reason"));
                    }
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("该地区未开通服务.");
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
     * @Purpose  天天快递检测超区
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,collectDtoList,orderNo
     * @return   RetInfo
     */
    @Override
    public RetInfo screenExpress(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String orderNo, List<TdHtExpressOrderCollectDto> collectDtoList) {
        String logInfo = this.getClass().getName() + ":screenExpress:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_SCREEN_EXPRESS);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            List<Map> data=new ArrayList<Map>();
            for(int i=0;i<collectDtoList.size();i++){
                TdHtExpressOrderCollectDto collectDto=collectDtoList.get(i);
                Map map=new HashMap();
                map.put("key",orderNo+i);
                map.put("prov",dicts.get(""+collectDto.getAdd_province()).getCode_name());
                map.put("city",dicts.get(""+collectDto.getAdd_city()).getCode_name());
                map.put("area",dicts.get(""+collectDto.getAdd_region()).getCode_name());
                map.put("addr",collectDto.getAdd_detail_address());
                data.add(map);
            }
            Map jsonMap=new HashMap();
            jsonMap.put("data",data);
            JSONObject params=JSONObject.fromObject(jsonMap);

            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            NameValuePair[] nameValuePairs = { new NameValuePair("serviceCode", "超区检测"), new NameValuePair("params", params.toString()) };
            postMethod.setRequestBody(nameValuePairs);
            int statusCode= 0;
            statusCode = httpClient.executeMethod(postMethod);
            if(statusCode==HttpStatus.SC_OK){
                String res=postMethod.getResponseBodyAsString();
                JSONObject jsonObject=JSONObject.fromObject(res);
                JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("data"));
                String result="";
                for(int j=0;j<jsonArray.size();j++){
                    String rs=JSONObject.fromObject(jsonArray.get(0)).getString("result");
                    String key=JSONObject.fromObject(jsonArray.get(0)).getString("key");
                    if(!"0".equals(rs)){
                        for(int i=0;i<collectDtoList.size();i++){
                            TdHtExpressOrderCollectDto collectDto=collectDtoList.get(i);
                            if(key.equals("")){
                                result=result+","+collectDto.getAdd_name();
                            }
                        }
                        retInfo.setMark("1");
                        retInfo.setTip("发给"+result.substring(1,result.length())+"的快件无法派送，请选择其他快递公司.");
                    }else{
                        retInfo.setMark("0");
                        retInfo.setTip("可以派送");
                    }
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("天天快递检测超区接口错误");
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
     * @Purpose  天天快递电子面单打印所需大头笔数据
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
            List<Map> data=new ArrayList<Map>();

            Map dataMap=new HashMap();
            dataMap.put("key",map.get("order_no"));
            dataMap.put("prov",map.get("rev_pro"));
            dataMap.put("city",map.get("rev_city"));
            dataMap.put("area",map.get("rev_area"));
            dataMap.put("addr",map.get("rev_address"));
            data.add(dataMap);

            Map jsonMap=new HashMap();
            jsonMap.put("data",data);
            JSONObject params=JSONObject.fromObject(jsonMap);

            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            NameValuePair[] nameValuePairs = { new NameValuePair("serviceCode", "取大头笔"), new NameValuePair("params", params.toString()) };
            postMethod.setRequestBody(nameValuePairs);
            int statusCode= 0;
            statusCode = httpClient.executeMethod(postMethod);
            if(statusCode==HttpStatus.SC_OK){
                String res=postMethod.getResponseBodyAsString();
                JSONObject jsonObject=JSONObject.fromObject(res);
                JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("data"));
                String rs=jsonArray.get(0).toString();
                JSONObject dataObject=JSONObject.fromObject(rs);

                map.put("marker",dataObject.get("result"));
                map.put("package_name",dataObject.get("package"));
                map.put("package_code",dataObject.get("packageCode"));

                retInfo.setMark("0");
                retInfo.setTip("大头笔获取成功");
                retInfo.setObj(map);
            }else{
                retInfo.setMark("1");
                retInfo.setTip("天天快递取大头笔接口错误");
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
     * @Purpose  天天快递提交订单
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
            TdHtExpressOrderDto expressOrderDto=tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(tdHtExpressOrderDto.getExp_ord_id());
            List<TdHtExpressOrderCollectDto> collectDtoList=tdHtExpressCollectRepository.findExpressCollectByExpOrdId(tdHtExpressOrderDto.getExp_ord_id());

            TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto=new TsHtExpressOpenRegionDto();
            tsHtExpressOpenRegionDto.setExpress_id(expressOrderDto.getExpress_id());
            tsHtExpressOpenRegionDto.setRegion_id(String.valueOf(expressOrderDto.getAdd_region()));
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_SUBMIT_ORDER);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);

            String sender="<name>"+expressOrderDto.getAdd_name()+"</name>" +
                    "<phone>"+expressOrderDto.getAdd_telephone()+"</phone>" +
                    "<mobile>"+expressOrderDto.getAdd_mobile_phone()+"</mobile>"+
                    "<prov>"+dicts.get(""+expressOrderDto.getAdd_province()).getCode_name()+"</prov>" +
                    "<city>"+dicts.get(""+expressOrderDto.getAdd_city()).getCode_name()+","+dicts.get(""+expressOrderDto.getAdd_region()).getCode_name()+"</city>" +
                    "<address>"+expressOrderDto.getAdd_detail_address()+"</address>";

            boolean is_ok=true;
            for (int i=0;i<collectDtoList.size();i++){
                TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto=collectDtoList.get(i);
                String receiver="<name>"+tdHtExpressOrderCollectDto.getAdd_name()+"</name>" +
                        "<phone>"+tdHtExpressOrderCollectDto.getAdd_telephone()+"</phone>" +
                        "<mobile>"+tdHtExpressOrderCollectDto.getAdd_mobile_phone()+"</mobile>"+
                        "<prov>"+dicts.get(""+tdHtExpressOrderCollectDto.getAdd_province()).getCode_name()+"</prov>" +
                        "<city>"+dicts.get(""+tdHtExpressOrderCollectDto.getAdd_city()).getCode_name()+","+dicts.get(""+tdHtExpressOrderCollectDto.getAdd_region()).getCode_name()+"</city>" +
                        "<address>"+tdHtExpressOrderCollectDto.getAdd_detail_address()+"</address>";

                String items="<item>" +
                        "<itemName>"+tdHtExpressOrderCollectDto.getExp_ord_category()+"</itemName>" +
                        "<number>"+1+"</number>" +
                        "<itemValue>"+0+"</itemValue>" +
                        "</item>";

                String logistics_interface="<RequestOrder>" +
                        "<ecCompanyId>"+tsHtExpressSdkDto.getCustomer_number()+"</ecCompanyId>" +
                        "<logisticProviderID>TTKDEX</logisticProviderID>" +
                        "<txLogisticID>"+tdHtExpressOrderCollectDto.getExp_ord_clt_number()+"</txLogisticID>" +
                        "<mailNo>"+tdHtExpressOrderCollectDto.getExpress_number()+"</mailNo>" +
                        "<weight>"+tdHtExpressOrderCollectDto.getExp_ord_clt_height()+"</weight>" +
                        "<orderType>"+1+"</orderType>" +
                        "<serviceType>"+0+"</serviceType>" +
                        "<sender>"+sender+"</sender>" +
                        "<receiver>"+receiver+"</receiver>" +
                        "<goodsValue>"+0+"</goodsValue>" +
                        "<itemsValue>"+0+"</itemsValue>" +
                        "<items>"+items+"</items>" +
                        "</RequestOrder>";

                String data_digest=new String(Base64.encodeBase64(DigestUtils.md5((logistics_interface+tsHtExpressSdkDto.getApp_secret()).getBytes("GBK"))),"GBK");

                HttpClient httpClient = new HttpClient();
                PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
                postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
                NameValuePair[] nameValuePairs = { new NameValuePair("logistics_interface", logistics_interface), new NameValuePair("data_digest", data_digest),new NameValuePair("msg_type", "ORDERCREATE"), new NameValuePair("ecCompanyId", tsHtExpressSdkDto.getCustomer_number()) };
                postMethod.setRequestBody(nameValuePairs);
                int statusCode=httpClient.executeMethod(postMethod);
                if(statusCode==HttpStatus.SC_OK){
                    String res=postMethod.getResponseBodyAsString();
                    Map map= MessageXMLUtil.parseXmlToList2(res);
                    if("true".equals(map.get("success").toString())){
                        retInfo.setMark("0");
                        retInfo.setTip("订单提交成功");
                    }else{
                        retInfo.setMark("1");
                        retInfo.setTip("订单提交失败");
                        is_ok=false;
                    }
                }else{
                    retInfo.setMark("-1");
                    retInfo.setTip("订单提交失败");
                    is_ok=false;
                }
            }
            if(is_ok){
                expressOrderDto.setStorge_time(new Date());
                expressOrderDto.setExp_ord_state(5);
                tdHtExpressOrderRepository.updateStorageExpressOrder(expressOrderDto);
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
