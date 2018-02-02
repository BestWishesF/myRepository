package cn.hotol.app.service.express.sdk.debang;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import net.sf.json.JSON;
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
public class ExpressSdkDeBangServiceImpl implements ExpressSdkDeBangService {

    private static Logger logger = Logger.getLogger(ExpressSdkDeBangServiceImpl.class);

    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    private TsHtExpressSdkRepository tsHtExpressSdkRepository;
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;

    /**
     * @Purpose  德邦快递检测超区
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,tdHtExpressOrderDto,collectDto
     * @return   RetInfo
     */
    @Override
    public RetInfo screenExpress(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, TdHtExpressOrderDto tdHtExpressOrderDto, TdHtExpressOrderCollectDto collectDto) {
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

            Map sender=new HashMap();//发件人信息
            sender.put("name",tdHtExpressOrderDto.getAdd_name());//发货人名称
            sender.put("phone",tdHtExpressOrderDto.getAdd_telephone());//发货人电话
            sender.put("mobile",tdHtExpressOrderDto.getAdd_mobile_phone());//发货人手机
            sender.put("province",dicts.get(""+tdHtExpressOrderDto.getAdd_province()).getCode_name());//发货人省份
            sender.put("city",dicts.get(""+tdHtExpressOrderDto.getAdd_city()).getCode_name());//发货人城市
            sender.put("county",dicts.get(""+tdHtExpressOrderDto.getAdd_region()).getCode_name());//发货人区县
            sender.put("address",tdHtExpressOrderDto.getAdd_detail_address());//发货人详细地址

            Map receiver=new HashMap();//收件人信息
            receiver.put("name",collectDto.getAdd_name());//收货人名称
            receiver.put("phone",collectDto.getAdd_telephone());//收货人电话
            receiver.put("mobile",collectDto.getAdd_mobile_phone());//收货人手机
            receiver.put("province",dicts.get(""+collectDto.getAdd_city()).getCode_name());//收货人省份
            receiver.put("city",dicts.get(""+collectDto.getAdd_city()).getCode_name());//收货人城市
            receiver.put("county",dicts.get(""+collectDto.getAdd_city()).getCode_name());//收货人区县
            receiver.put("address",collectDto.getAdd_detail_address());//收货人详细地址

            Map params=new HashMap();
            params.put("sender",sender);//发件人信息
            params.put("receiver",receiver);//收件人信息
            params.put("gmtCommit", CommonUtil.getDateAndTime());//订单提交时间
            params.put("cargoName",collectDto.getExp_ord_category());//货物名称
            params.put("payType","2");//支付方式1:收货人付款（到付）2:发货人付款（月结）
            params.put("transportType","PACKAGE");//运输方式(PACKAGE:标准快递;RCP:360特惠件;EPEP:电商尊享;DEAP：特准快件.)
            params.put("codType","3");//代收货款类型1：即日退3：三日退
            params.put("deliveryType","3");//送货方式3:送货上楼
            params.put("backSignBill","0");//签收回单0:无需返单2:客户签收单传真返回4: 运单到达联传真返回
            params.put("logisticCompanyID","DEPPON");//物流公司ID为“DEPPON”
            params.put("logisticID",tsHtExpressSdkDto.getApp_secret()+collectDto.getExp_ord_clt_number());//渠道单号由第三方接入商产生的订单号（生成规则为sign+数字，sign值由双方约定）
            params.put("orderSource",tsHtExpressSdkDto.getDot_number());//订单来源与companyCode保持一致
            params.put("serviceType","2");//服务类型2．快递在线下单
            params.put("customerCode",tsHtExpressSdkDto.getCustomer_number());//客户编码与德邦crm中的客户编码保持一致
            params.put("customerID",tsHtExpressSdkDto.getCustomer_number());//渠道用户与companyCode保持一致

            long nowDate=System.currentTimeMillis();
            JSON json= JSONObject.fromObject(params);
            String str=json.toString()+ tsHtExpressSdkDto.getApp_key()+String.valueOf(nowDate);
            String digest= Base64Util.encode(MD5Util.MD5Encoder(str).getBytes());
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            NameValuePair[] data = { new NameValuePair("params", json.toString()), new NameValuePair("digest", digest), new NameValuePair("timestamp", String.valueOf(nowDate)), new NameValuePair("companyCode", tsHtExpressSdkDto.getDot_number()) };
            postMethod.setRequestBody(data);
            int state=httpClient.executeMethod(postMethod);
            if(state== HttpStatus.SC_OK){
                String res=postMethod.getResponseBodyAsString();
                if(res.indexOf("result")>0){
                    JSONObject jsonObject=JSONObject.fromObject(res);
                    if("true".equals(jsonObject.get("result"))){
                        retInfo.setMark("0");
                        retInfo.setTip("可以发件");
                    }else{
                        retInfo.setMark("1");
                        String code=jsonObject.get("resultCode").toString();
                        if("1008".equals(code)){
                            retInfo.setTip("该快递公司无法承运，请选择其他快递公司");
                        }else if("2006".equals(code)){
                            retInfo.setTip(jsonObject.get("reason").toString());
                        }
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
     * @Purpose  德邦快递提交订单
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

            Map sender=new HashMap();//发件人信息
            sender.put("name",expressOrderDto.getAdd_name());//发货人名称
            sender.put("phone",expressOrderDto.getAdd_telephone());//发货人电话
            sender.put("mobile",expressOrderDto.getAdd_mobile_phone());//发货人手机
            sender.put("province",dicts.get(""+expressOrderDto.getAdd_province()).getCode_name());//发货人省份
            sender.put("city",dicts.get(""+expressOrderDto.getAdd_city()).getCode_name());//发货人城市
            sender.put("county",dicts.get(""+expressOrderDto.getAdd_region()).getCode_name());//发货人区县
            sender.put("address",expressOrderDto.getAdd_detail_address());//发货人详细地址

            boolean is_ok=true;
            for (int i=0;i<collectDtoList.size();i++){
                TdHtExpressOrderCollectDto collectDto=collectDtoList.get(i);
                Map receiver=new HashMap();//收件人信息
                receiver.put("name",collectDto.getAdd_name());//收货人名称
                receiver.put("phone",collectDto.getAdd_telephone());//收货人电话
                receiver.put("mobile",collectDto.getAdd_mobile_phone());//收货人手机
                receiver.put("province",dicts.get(""+collectDto.getAdd_city()).getCode_name());//收货人省份
                receiver.put("city",dicts.get(""+collectDto.getAdd_city()).getCode_name());//收货人城市
                receiver.put("county",dicts.get(""+collectDto.getAdd_city()).getCode_name());//收货人区县
                receiver.put("address",collectDto.getAdd_detail_address());//收货人详细地址

                Map params=new HashMap();
                params.put("sender",sender);//发件人信息
                params.put("receiver",receiver);//收件人信息
                params.put("gmtCommit", CommonUtil.getDateAndTime());//订单提交时间
                params.put("cargoName",collectDto.getExp_ord_category());//货物名称
                params.put("payType","2");//支付方式1:收货人付款（到付）2:发货人付款（月结）
                params.put("transportType","PACKAGE");//运输方式(PACKAGE:标准快递;RCP:360特惠件;EPEP:电商尊享;DEAP：特准快件.)
                params.put("codType","3");//代收货款类型1：即日退3：三日退
                params.put("deliveryType","3");//送货方式3:送货上楼
                params.put("backSignBill","0");//签收回单0:无需返单2:客户签收单传真返回4: 运单到达联传真返回
                params.put("totalNumber","1");//总件数：筛单时不需要
                params.put("totalWeight",collectDto.getExp_ord_clt_height());//总重量(kg):筛单时不需要
                params.put("mailNo",collectDto.getExpress_number());//运单号：筛单时不需要
                params.put("logisticCompanyID","DEPPON");//物流公司ID为“DEPPON”
                params.put("logisticID",tsHtExpressSdkDto.getApp_secret()+collectDto.getExp_ord_clt_number());//渠道单号由第三方接入商产生的订单号（生成规则为sign+数字，sign值由双方约定）
                params.put("orderSource",tsHtExpressSdkDto.getDot_number());//订单来源与companyCode保持一致
                params.put("serviceType","2");//服务类型2．快递在线下单
                params.put("customerCode",tsHtExpressSdkDto.getCustomer_number());//客户编码与德邦crm中的客户编码保持一致
                params.put("customerID",tsHtExpressSdkDto.getCustomer_number());//渠道用户与companyCode保持一致

                long nowDate=System.currentTimeMillis();
                JSON json= JSONObject.fromObject(params);
                String str=json.toString()+ tsHtExpressSdkDto.getApp_key()+String.valueOf(nowDate);
                String digest= Base64Util.encode(MD5Util.MD5Encoder(str).getBytes());
                HttpClient httpClient = new HttpClient();
                PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
                postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
                NameValuePair[] data = { new NameValuePair("params", json.toString()), new NameValuePair("digest", digest), new NameValuePair("timestamp", String.valueOf(nowDate)), new NameValuePair("companyCode", tsHtExpressSdkDto.getDot_number()) };
                postMethod.setRequestBody(data);
                int state=httpClient.executeMethod(postMethod);
                if(state== HttpStatus.SC_OK){
                    String res=postMethod.getResponseBodyAsString();
                    if(res.indexOf("result")>0){
                        JSONObject jsonObject=JSONObject.fromObject(res);
                        if("true".equals(jsonObject.get("result"))){
                            retInfo.setMark("0");
                            retInfo.setTip("提交成功");
                        }else{
                            retInfo.setMark("1");
                            retInfo.setTip(jsonObject.get("reason").toString());
                            is_ok=false;
                        }
                    }else {
                        retInfo.setMark("0");
                        retInfo.setTip("提交失败");
                        is_ok=false;
                    }
                }else{
                    is_ok=false;
                    retInfo.setMark("0");
                    retInfo.setTip("提交失败");
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

    /**
     * @Purpose  德邦快递查询物流
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,expressNumber
     * @return   RetInfo
     */
    @Override
    public RetInfo traceOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String expressNumber) {
        String logInfo = this.getClass().getName() + ":submitOrder:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TsHtExpressOpenRegionDto ExpressOpenRegionDto=tsHtExpressOpenRegionRepository.findExpOpenRegionByBean(tsHtExpressOpenRegionDto);

            TsHtExpressSdkDto expressSdkDto=new TsHtExpressSdkDto();
            expressSdkDto.setEoa_id(ExpressOpenRegionDto.getEoa_id());
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_TRACE_ORDER);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            Map orders=new HashMap();
            orders.put("mailNo",expressNumber);
            List orderList=new ArrayList();
            orderList.add(orders);

            Map params=new HashMap();
            params.put("orders",orderList);
            params.put("logisticCompanyID","DEPPON");//物流公司ID为“DEPPON”

            long nowDate=System.currentTimeMillis();
            JSON json= JSONObject.fromObject(params);
            String str=json.toString()+ tsHtExpressSdkDto.getApp_key()+String.valueOf(nowDate);
            String digest= Base64Util.encode(MD5Util.MD5Encoder(str).getBytes());
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
            NameValuePair[] data = { new NameValuePair("params", json.toString()), new NameValuePair("digest", digest), new NameValuePair("timestamp", String.valueOf(nowDate)), new NameValuePair("companyCode", tsHtExpressSdkDto.getDot_number()) };
            postMethod.setRequestBody(data);
            int state=httpClient.executeMethod(postMethod);
            if(state== HttpStatus.SC_OK){
                String res=postMethod.getResponseBodyAsString();
                if(res.indexOf("result")>0){
                    JSONObject jsonObject=JSONObject.fromObject(res);
                    if("true".equals(jsonObject.get("result"))){
                        retInfo.setMark("0");
                        retInfo.setTip("查询成功");
                        JSONObject responseParam=JSONObject.fromObject(jsonObject.get("responseParam"));
                        JSONArray ordersArray=JSONArray.fromObject(responseParam.get("orders"));
                        JSONObject orderJson=JSONObject.fromObject(ordersArray.get(0));
                        JSONArray steps=JSONArray.fromObject(orderJson.get("steps"));
                        List<Map> stepsMaps=new ArrayList<Map>();
                        for(int i=0;i<steps.size();i++){
                            Map<String, Object> stepsMap=new HashMap<String, Object>();
                            JSONObject stepJson=JSONObject.fromObject(steps.get(i));
                            stepsMap.put("accept_time",stepJson.get("acceptTime"));
                            stepsMap.put("remark",stepJson.get("remark"));
                            stepsMaps.add(stepsMap);
                        }
                        retInfo.setObj(stepsMaps);
                    }else{
                        retInfo.setMark("1");
                        String code=jsonObject.get("resultCode").toString();
                        if("1005".equals(code)){
                            retInfo.setTip("运单不存在");
                        }else if("1006".equals(code)){
                            retInfo.setTip("流水号不存在");
                        }else if("2006".equals(code)){
                            retInfo.setTip(jsonObject.get("reason").toString());
                        }
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
