package cn.hotol.app.service.express.sdk.jingdong;

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
import cn.hotol.app.repository.*;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.etms.JDeliveryServiceJsf.ResultInfoDTO;
import com.jd.open.api.sdk.domain.etms.OrderInfoJosService.GetResultInfoDTO;
import com.jd.open.api.sdk.domain.etms.OrderInfoJosService.SendResultInfoDTO;
import com.jd.open.api.sdk.request.etms.EtmsRangeCheckRequest;
import com.jd.open.api.sdk.request.etms.EtmsTraceGetRequest;
import com.jd.open.api.sdk.request.etms.EtmsWaybillSendRequest;
import com.jd.open.api.sdk.request.etms.EtmsWaybillcodeGetRequest;
import com.jd.open.api.sdk.response.etms.*;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public class ExpressSdkJingDongServiceImpl implements ExpressSdkJingDongService {

    private static Logger logger = Logger.getLogger(ExpressSdkJingDongServiceImpl.class);

    private TsHtExpressOpenRegionRepository tsHtExpressOpenRegionRepository;
    private TsHtExpressSdkRepository tsHtExpressSdkRepository;
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;

    /**
     * @Purpose  京东快递提取单号
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

                JdClient client = new DefaultJdClient(tsHtExpressSdkDto.getSdk_itf_url(), tsHtExpressSdkDto.getAccess_token(), tsHtExpressSdkDto.getApp_key(), tsHtExpressSdkDto.getApp_secret());
                EtmsWaybillcodeGetRequest request = new EtmsWaybillcodeGetRequest();
                request.setPreNum(String.valueOf(batchExpressNumberDto.getNumber_size()));//运单数量
                request.setCustomerCode(tsHtExpressSdkDto.getCustomer_number());//商户编码
                request.setOrderType(0);//运单类型(普通外单：0，O2O外单：1)默认为0
                EtmsWaybillcodeGetResponse response = client.execute(request);
                GetResultInfoDTO getResultInfoDTO = response.getResultInfo();
                if (("100").equals(getResultInfoDTO.getCode())) {
                    List<TsHtExpressOpenNumberDto> tsHtExpressOpenNumberDtoList=new ArrayList<TsHtExpressOpenNumberDto>();

                    for(int i=0;i<getResultInfoDTO.getDeliveryIdList().size();i++){
                        TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto=new TsHtExpressOpenNumberDto();
                        tsHtExpressOpenNumberDto.setAgent_id(0);
                        tsHtExpressOpenNumberDto.setExp_open_state(0);
                        tsHtExpressOpenNumberDto.setExpress_id(tsHtExpressOpenRegionDto.getExpress_id());
                        tsHtExpressOpenNumberDto.setRegion_id(Integer.valueOf(tsHtExpressOpenRegionDto.getRegion_id()));
                        tsHtExpressOpenNumberDto.setExpress_number(getResultInfoDTO.getDeliveryIdList().get(i));
                        tsHtExpressOpenNumberDtoList.add(tsHtExpressOpenNumberDto);
                    }
                    tsHtExpressOpenNumberRepository.batchInsertExpressNumber(tsHtExpressOpenNumberDtoList);
                    retInfo.setMark("0");
                    retInfo.setTip("京东快递提取单号成功.");
                }else{
                    retInfo.setMark("1");
                    retInfo.setTip("京东快递提取单号错误:"+getResultInfoDTO.getMessage());
                }
            }else{
                retInfo.setMark("1");
                retInfo.setTip("该地区还没有开通.");
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
     * @Purpose  京东快递检测超区
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   RetInfo
     */
    @Override
    public RetInfo screenExpress(Map<String, Object> map) {
        String logInfo = this.getClass().getName() + ":screenExpress:";
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
            expressSdkDto.setSdkj_itf_type(Constant.EXPRESS_SDK_SCREEN_EXPRESS);

            TsHtExpressSdkDto tsHtExpressSdkDto=tsHtExpressSdkRepository.findExpressSdkByBen(expressSdkDto);

            JdClient client = new DefaultJdClient(tsHtExpressSdkDto.getSdk_itf_url(), tsHtExpressSdkDto.getAccess_token(), tsHtExpressSdkDto.getApp_key(), tsHtExpressSdkDto.getApp_secret());
            EtmsRangeCheckRequest request = new EtmsRangeCheckRequest();
            request.setCustomerCode(tsHtExpressSdkDto.getCustomer_number());//商户编码
            request.setOrderId(map.get("order_no").toString());//订单编号
            request.setGoodsType(1);//配送业务类型（ 1:普通，3:填仓，4:特配，5:鲜活，6:控温，7:冷藏，8:冷冻，9:深冷）默认是1
            request.setReceiveAddress(map.get("rev_pro").toString()+map.get("rev_city").toString()+map.get("rev_area").toString()+map.get("rev_address").toString());//收货人地址
            request.setSendTime(new Date());//提交日期
            request.setWareHouseCode(tsHtExpressSdkDto.getDot_number());//发货仓
            EtmsRangeCheckResponse response = client.execute(request);
            ResultInfoDTO resultInfoDTO = response.getResultInfo();
            if(resultInfoDTO.getRcode()==100){

                map.put("sourcet_sort_center_name",resultInfoDTO.getSourcetSortCenterName());
                map.put("original_cross_code",resultInfoDTO.getOriginalCrossCode());
                map.put("target_sort_center_name",resultInfoDTO.getTargetSortCenterName());
                map.put("destination_cross_code",resultInfoDTO.getDestinationCrossCode());
                map.put("site_name",resultInfoDTO.getSiteName());
                map.put("road",resultInfoDTO.getRoad());
                map.put("print_day", CommonUtil.getDate());
                map.put("customer_code",expressSdkDto.getCustomer_number());

                retInfo.setMark("0");
                retInfo.setTip("查询成功.");
                retInfo.setObj(map);
            }else{
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
     * @Purpose  京东快递提交订单
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
            boolean is_ok=true;
            for (int i=0;i<collectDtoList.size();i++){
                TdHtExpressOrderCollectDto collectDto=collectDtoList.get(i);
                JdClient client = new DefaultJdClient(tsHtExpressSdkDto.getSdk_itf_url(), tsHtExpressSdkDto.getAccess_token(), tsHtExpressSdkDto.getApp_key(), tsHtExpressSdkDto.getApp_secret());
                EtmsWaybillSendRequest request = new EtmsWaybillSendRequest();
                request.setDeliveryId(collectDto.getExpress_number());//运单号
                request.setSalePlat("0030001");//销售平台:如为京东平台，则传入0010001。如没有以下平台，可传入0030001（即其他）
                request.setCustomerCode(tsHtExpressSdkDto.getCustomer_number());//商家编码。
                request.setOrderId(collectDto.getExp_ord_clt_number());//商家订单号
                request.setSelfPrintWayBill(1);//是否客户打印运单(是：1，否：0。不填或者超出范围，默认是1)
                request.setSenderName(orderDto.getAdd_name());//寄件人姓名（最大支持25个汉字）
                String sendAddress=dicts.get(""+orderDto.getAdd_province()).getCode_name();
                sendAddress=sendAddress+dicts.get(""+orderDto.getAdd_city()).getCode_name();
                sendAddress=sendAddress+dicts.get(""+orderDto.getAdd_region()).getCode_name();
                request.setSenderAddress(sendAddress + orderDto.getAdd_detail_address());//寄件人地址（最大支持128个汉字）
                request.setSenderMobile(orderDto.getAdd_mobile_phone());
                request.setSenderTel(orderDto.getAdd_telephone());
                request.setReceiveName(collectDto.getAdd_name());//收件人名称（最大支持25个汉字）
                String receiveAddress=dicts.get(""+collectDto.getAdd_province()).getCode_name();
                receiveAddress=receiveAddress+dicts.get(""+collectDto.getAdd_city()).getCode_name();
                receiveAddress=receiveAddress+dicts.get(""+collectDto.getAdd_region()).getCode_name();
                request.setReceiveAddress(receiveAddress + collectDto.getAdd_detail_address());//收件人地址（最大支持128个汉字）
                request.setReceiveMobile(collectDto.getAdd_mobile_phone());
                request.setReceiveTel(collectDto.getAdd_telephone());
                request.setCollectionValue(0);
                request.setPackageCount(1);//包裹数(大于0，小于1000)
                request.setWeight(0d);//重量(单位：kg，保留小数点后两位) 可默认为0
                request.setVloumn(0d);//体积(单位：cm3，保留小数点后两位) 可默认为0
                EtmsWaybillSendResponse response = client.execute(request);
                SendResultInfoDTO sendResultInfoDTO = response.getResultInfo();
                if(sendResultInfoDTO==null){
                    is_ok=false;
                    retInfo.setMark("1");
                    retInfo.setTip("京东快递订单提交错误:"+response.getZhDesc());
                }else{
                    if("100".equals(sendResultInfoDTO.getCode())){
                        retInfo.setMark("0");
                        retInfo.setTip("京东快递订单提交成功.");
                    }else{
                        is_ok=false;
                        retInfo.setMark("1");
                        retInfo.setTip("京东快递订单提交错误:"+sendResultInfoDTO.getMessage());
                    }
                }
            }
            if(is_ok){
                orderDto.setStorge_time(new Date());
                orderDto.setExp_ord_state(5);
                tdHtExpressOrderRepository.updateStorageExpressOrder(orderDto);
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
     * @Purpose  京东快递查询物流
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,expressNumber
     * @return   RetInfo
     */
    @Override
    public RetInfo traceOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String expressNumber) {
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

            JdClient client = new DefaultJdClient(tsHtExpressSdkDto.getSdk_itf_url(), tsHtExpressSdkDto.getAccess_token(), tsHtExpressSdkDto.getApp_key(), tsHtExpressSdkDto.getApp_secret());
            EtmsTraceGetRequest request = new EtmsTraceGetRequest();
            request.setWaybillCode(expressNumber);
            EtmsTraceGetResponse response = client.execute(request);
            List<TraceApiDto> traceApiDtos = response.getTraceApiDtos();
            retInfo.setMark("0");
            retInfo.setTip("京东物流查询成功");
            retInfo.setObj(traceApiDtos);
        } catch (JdException e) {
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
