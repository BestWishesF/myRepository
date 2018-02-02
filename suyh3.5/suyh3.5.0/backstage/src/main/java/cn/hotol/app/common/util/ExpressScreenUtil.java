package cn.hotol.app.common.util;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.price.TsHtRegionPriceDto;
import cn.hotol.app.common.Constant;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.etms.JDeliveryServiceJsf.ResultInfoDTO;
import com.jd.open.api.sdk.request.etms.EtmsRangeCheckRequest;
import com.jd.open.api.sdk.response.etms.EtmsRangeCheckResponse;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by LuBin
 * Date 2017-01-10.
 */
public class ExpressScreenUtil {

    private static Logger logger = Logger.getLogger(ExpressScreenUtil.class);

    /**
     * @param tsHtExpressSdkDto,tdHtExpressOrderDto,collectDto
     * @return RetInfo
     * @Purpose 德邦快递检测超区
     * @version 3.0
     * @author lubin
     */
    public RetInfo deBangScreenExpress(TsHtExpressSdkDto tsHtExpressSdkDto, TdHtExpressOrderDto tdHtExpressOrderDto, TdHtExpressOrderCollectDto collectDto) {
        String logInfo = this.getClass().getName() + ":deBangScreenExpress:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);

            Map sender = new HashMap();//发件人信息
            sender.put("name", tdHtExpressOrderDto.getAdd_name());//发货人名称
            sender.put("phone", tdHtExpressOrderDto.getAdd_telephone());//发货人电话
            sender.put("mobile", tdHtExpressOrderDto.getAdd_mobile_phone());//发货人手机
            sender.put("province", dicts.get("" + tdHtExpressOrderDto.getAdd_province()).getCode_name());//发货人省份
            sender.put("city", dicts.get("" + tdHtExpressOrderDto.getAdd_city()).getCode_name());//发货人城市
            sender.put("county", dicts.get("" + tdHtExpressOrderDto.getAdd_region()).getCode_name());//发货人区县
            sender.put("address", tdHtExpressOrderDto.getAdd_detail_address());//发货人详细地址

            Map receiver = new HashMap();//收件人信息
            receiver.put("name", collectDto.getAdd_name());//收货人名称
            receiver.put("phone", collectDto.getAdd_telephone());//收货人电话
            receiver.put("mobile", collectDto.getAdd_mobile_phone());//收货人手机
            receiver.put("province", dicts.get("" + collectDto.getAdd_province()).getCode_name());//收货人省份
            receiver.put("city", dicts.get("" + collectDto.getAdd_city()).getCode_name());//收货人城市
            receiver.put("county", dicts.get("" + collectDto.getAdd_region()).getCode_name());//收货人区县
            receiver.put("address", collectDto.getAdd_detail_address());//收货人详细地址

            Map params = new HashMap();
            params.put("sender", sender);//发件人信息
            params.put("receiver", receiver);//收件人信息
            params.put("gmtCommit", CommonUtil.getDateAndTime());//订单提交时间
            params.put("cargoName", collectDto.getExp_ord_category());//货物名称
            params.put("payType", "2");//支付方式1:收货人付款（到付）2:发货人付款（月结）
            params.put("transportType", "PACKAGE");//运输方式(PACKAGE:标准快递;RCP:360特惠件;EPEP:电商尊享;DEAP：特准快件.)
            params.put("codType", "3");//代收货款类型1：即日退3：三日退
            params.put("deliveryType", "3");//送货方式3:送货上楼
            params.put("backSignBill", "0");//签收回单0:无需返单2:客户签收单传真返回4: 运单到达联传真返回
            params.put("logisticCompanyID", "DEPPON");//物流公司ID为“DEPPON”
            params.put("logisticID", tsHtExpressSdkDto.getApp_secret() + collectDto.getExp_ord_clt_number());//渠道单号由第三方接入商产生的订单号（生成规则为sign+数字，sign值由双方约定）
            params.put("orderSource", tsHtExpressSdkDto.getDot_number());//订单来源与companyCode保持一致
            params.put("serviceType", "2");//服务类型2．快递在线下单
            params.put("customerCode", tsHtExpressSdkDto.getCustomer_number());//客户编码与德邦crm中的客户编码保持一致
            params.put("customerID", tsHtExpressSdkDto.getCustomer_number());//渠道用户与companyCode保持一致

            long nowDate = System.currentTimeMillis();
            JSON json = JSONObject.fromObject(params);
            String str = json.toString() + tsHtExpressSdkDto.getApp_key() + String.valueOf(nowDate);
            String digest = Base64Util.encode(MD5Util.MD5Encoder(str).getBytes());
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            NameValuePair[] data = {new NameValuePair("params", json.toString()), new NameValuePair("digest", digest), new NameValuePair("timestamp", String.valueOf(nowDate)), new NameValuePair("companyCode", tsHtExpressSdkDto.getDot_number())};
            postMethod.setRequestBody(data);
            int state = httpClient.executeMethod(postMethod);
            if (state == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer result = new StringBuffer();
                String ts = "";
                while((ts = br.readLine()) != null){
                    result .append(ts );
                }

                String res = new String(result.toString().getBytes(), "utf-8");
                if (res.indexOf("result") > 0) {
                    JSONObject jsonObject = JSONObject.fromObject(res);
                    if ("true".equals(jsonObject.get("result"))) {
                        retInfo.setMark("0");
                        retInfo.setTip("可以发件");
                    } else {
                        retInfo.setMark("1");
                        String code = jsonObject.get("resultCode").toString();
                        if ("1008".equals(code)) {
                            retInfo.setTip("该快递公司无法承运，请选择其他快递公司");
                        } else if ("2006".equals(code)) {
                            retInfo.setTip(jsonObject.get("reason").toString());
                        }else{
                            retInfo.setTip("该快递公司无法承运，请选择其他快递公司");
                        }
                    }
                }else{
                    retInfo.setMark("1");
                    retInfo.setTip("该快递公司无法承运，请选择其他快递公司");
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("该快递公司无法承运，请选择其他快递公司");
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
     * @param tsHtExpressSdkDto,orderDto,collectDtoList
     * @return RetInfo
     * @Purpose 京东快递检测超区
     * @version 3.0
     * @author lubin
     */
    public RetInfo jingDongScreenExpress(TsHtExpressSdkDto tsHtExpressSdkDto, TdHtExpressOrderDto orderDto, TdHtExpressOrderCollectDto collectDto) {
        String logInfo = this.getClass().getName() + ":jingDongScreenExpress:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            JdClient client = new DefaultJdClient(tsHtExpressSdkDto.getSdk_itf_url(), tsHtExpressSdkDto.getAccess_token(), tsHtExpressSdkDto.getApp_key(), tsHtExpressSdkDto.getApp_secret());
            EtmsRangeCheckRequest request = new EtmsRangeCheckRequest();
            request.setCustomerCode(tsHtExpressSdkDto.getCustomer_number());//商户编码
            request.setOrderId(collectDto.getExp_ord_clt_number());//订单编号
            request.setGoodsType(1);//配送业务类型（ 1:普通，3:填仓，4:特配，5:鲜活，6:控温，7:冷藏，8:冷冻，9:深冷）默认是1
            String address = dicts.get("" + collectDto.getAdd_province()).getCode_name();
            address = address + dicts.get("" + collectDto.getAdd_city()).getCode_name();
            address = address + dicts.get("" + collectDto.getAdd_region()).getCode_name();
            request.setReceiveAddress(address + collectDto.getAdd_detail_address());//收货人地址
            request.setSendTime(new Date());//提交日期
            request.setWareHouseCode(tsHtExpressSdkDto.getDot_number());//发货仓
            EtmsRangeCheckResponse response = client.execute(request);
            ResultInfoDTO resultInfoDTO = response.getResultInfo();
            if (resultInfoDTO.getRcode() == 100) {
                retInfo.setMark("0");
                retInfo.setTip("可以发件.");
                retInfo.setObj(resultInfoDTO);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("您的地址信息当前快递无法承运，请检查地址信息或选择其他快递公司.");
            }
        } catch (JdException e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tsHtExpressSdkDto,collectDtoList,orderNo
     * @return RetInfo
     * @Purpose 天天快递检测超区
     * @version 3.0
     * @author lubin
     */
    public RetInfo tianTianScreenExpress(TsHtExpressSdkDto tsHtExpressSdkDto, List<TdHtExpressOrderCollectDto> collectDtoList) {
        String logInfo = this.getClass().getName() + ":tianTianScreenExpress:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            List<Map> data = new ArrayList<Map>();
            for (int i = 0; i < collectDtoList.size(); i++) {
                TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);
                Map map = new HashMap();
                map.put("key", collectDto.getExp_ord_clt_number());
                map.put("prov", dicts.get("" + collectDto.getAdd_province()).getCode_name());
                map.put("city", dicts.get("" + collectDto.getAdd_city()).getCode_name());
                map.put("area", dicts.get("" + collectDto.getAdd_region()).getCode_name());
                map.put("addr", collectDto.getAdd_detail_address());
                data.add(map);
            }
            Map jsonMap = new HashMap();
            jsonMap.put("data", data);
            JSONObject params = JSONObject.fromObject(jsonMap);

            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(tsHtExpressSdkDto.getSdk_itf_url());
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            NameValuePair[] nameValuePairs = {new NameValuePair("serviceCode", "超区检测"), new NameValuePair("params", params.toString())};
            postMethod.setRequestBody(nameValuePairs);
            int statusCode = 0;
            statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                String res = postMethod.getResponseBodyAsString();
                JSONObject jsonObject = JSONObject.fromObject(res);
                JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("data"));
                String result = "";
                for (int j = 0; j < jsonArray.size(); j++) {
                    String rs = JSONObject.fromObject(jsonArray.get(0)).getString("result");
                    String key = JSONObject.fromObject(jsonArray.get(0)).getString("key");
                    if (!"0".equals(rs)) {
                        for (int i = 0; i < collectDtoList.size(); i++) {
                            TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);
                            result = result + "," + collectDto.getAdd_name();
                        }
                        retInfo.setMark("1");
                        retInfo.setTip("发给" + result.substring(1, result.length()) + "的快件无法派送，请选择其他快递公司.");
                    } else {
                        retInfo.setMark("0");
                        retInfo.setTip("可以派送");
                    }
                }
            } else {
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
     * @Purpose   快件筛单
     * @version   3.0
     * @author    lubin
     * @time      2017-03-24
     * @param     tdHtExpressOrderDto  发件人数据
     * @param     tdHtExpressOrderCollectDtoList  收件人数据集合
     * @return    RetInfo  筛单结果
     */
    public RetInfo expressScreen(TdHtExpressOrderDto tdHtExpressOrderDto, List<TdHtExpressOrderCollectDto> tdHtExpressOrderCollectDtoList){
        String logInfo = this.getClass().getName() + ":expressScreen:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            List<CourierDto> courierDtoList = (List<CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_LIST);
            Map<String, Map<String, TsHtExpressSdkDto>> sdkMap = (Map<String, Map<String, TsHtExpressSdkDto>>) MemcachedUtils.get(MemcachedKey.EXPRESS_SDK_MAP);
            int eoa_id = 0;
            for (int i = 0; i < courierDtoList.size(); i++) {
                CourierDto courierDto = courierDtoList.get(i);
                if (courierDto.getExpress_id() == tdHtExpressOrderDto.getExpress_id() && courierDto.getRegion_id() == tdHtExpressOrderDto.getAdd_region()) {
                    eoa_id = courierDto.getEoa_id();
                }
            }
            Map<String, TsHtExpressSdkDto> expressSdkMap = (Map<String, TsHtExpressSdkDto>) sdkMap.get("" + eoa_id);
            TsHtExpressSdkDto tsHtExpressSdkDto = (TsHtExpressSdkDto) expressSdkMap.get("" + Constant.EXPRESS_SDK_SCREEN_EXPRESS);

            if (tsHtExpressSdkDto != null) {
                if (tdHtExpressOrderDto.getExpress_id() == Constant.DBL) {
                    for (int j = 0; j < tdHtExpressOrderCollectDtoList.size(); j++) {
                        TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(j);
                        retInfo = deBangScreenExpress(tsHtExpressSdkDto, tdHtExpressOrderDto, tdHtExpressOrderCollectDto);
                        if (!"0".equals(retInfo.getMark())) {
                            break;
                        } else {
                            Thread.sleep(500);
                        }
                    }

                } else if (tdHtExpressOrderDto.getExpress_id() == Constant.JD) {
                    for (int j = 0; j < tdHtExpressOrderCollectDtoList.size(); j++) {
                        TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(j);
                        retInfo = jingDongScreenExpress(tsHtExpressSdkDto, tdHtExpressOrderDto, tdHtExpressOrderCollectDto);
                        if (!"0".equals(retInfo.getMark())) {
                            break;
                        } else {
                            Thread.sleep(500);
                        }
                    }
                } else if (tdHtExpressOrderDto.getExpress_id() == Constant.HHTT) {
                    for (int j = 0; j < tdHtExpressOrderCollectDtoList.size(); j++) {
                        retInfo = tianTianScreenExpress(tsHtExpressSdkDto, tdHtExpressOrderCollectDtoList);
                        if (!"0".equals(retInfo.getMark())) {
                            break;
                        }
                    }
                }
            }else {
                retInfo.setMark("0");
            }
        }catch (Exception e){
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   通过收发件地址、快递公司和重量计算价格
     * @version   3.0
     * @author    lubin
     * @time      2017-03-24
     * @param     express_id  快递公司id
     * @param     exp_ord_clt_height  快件重量
     * @param     send_province  发件人省份id
     * @param     rev_province  收件人省份id
     * @return    BigDecimal  快递估价
     */
    public BigDecimal reckonExpressPrice(int express_id, BigDecimal exp_ord_clt_height, int send_province, int rev_province){
        String logInfo = this.getClass().getName() + ":expressScreen:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        BigDecimal price = new BigDecimal(0);
        try {
            Map<String, List<TsHtRegionPriceDto>> priceMap = (Map<String, List<TsHtRegionPriceDto>>) MemcachedUtils.get(MemcachedKey.EXP_REGION_PRICE);
            List<TsHtRegionPriceDto> priceDtoList = priceMap.get(""+express_id);
            for (int j = 0; j < priceDtoList.size(); j++) {
                TsHtRegionPriceDto priceDto = priceDtoList.get(j);
                if (priceDto.getSend_province_id() == send_province && priceDto.getCollect_province_id() == rev_province
                        && express_id == Constant.DBL && exp_ord_clt_height.intValue() > 2 && priceDto.getFirst_weight() == 3) {
                    price = priceDto.getFirst_amount();
                    if (exp_ord_clt_height.intValue() > priceDto.getFirst_weight()) {
                        int addWeight = exp_ord_clt_height.intValue() - priceDto.getFirst_weight();
                        int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                        if (addWeight % priceDto.getAdd_weight() > 0) {
                            addWeightMultiple = addWeightMultiple + 1;
                        }
                        BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                        price = price.add(addPrice);
                    }
                }
                if (priceDto.getSend_province_id() == send_province && priceDto.getCollect_province_id() == rev_province
                        && express_id == Constant.DBL && exp_ord_clt_height.intValue() < 3 && priceDto.getFirst_weight() == 1) {
                    price = priceDto.getFirst_amount();
                    if (exp_ord_clt_height.intValue() > priceDto.getFirst_weight()) {
                        int addWeight = exp_ord_clt_height.intValue() - priceDto.getFirst_weight();
                        int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                        if (addWeight % priceDto.getAdd_weight() > 0) {
                            addWeightMultiple = addWeightMultiple + 1;
                        }
                        BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                        price = price.add(addPrice);
                    }
                }
                if (priceDto.getSend_province_id() == send_province && priceDto.getCollect_province_id() == rev_province
                        && express_id != Constant.DBL) {
                    price = priceDto.getFirst_amount();
                    if (exp_ord_clt_height.intValue() > priceDto.getFirst_weight()) {
                        int addWeight = exp_ord_clt_height.intValue() - priceDto.getFirst_weight();
                        int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                        if (addWeight % priceDto.getAdd_weight() > 0) {
                            addWeightMultiple = addWeightMultiple + 1;
                        }
                        BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                        price = price.add(addPrice);
                    }
                }
            }
        }catch (Exception e){
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return price;
    }

}
