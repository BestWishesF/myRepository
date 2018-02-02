package cn.hotol.app.service.three.order;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.TdHtExpressOrder;
import cn.hotol.app.bean.TdHtExpressSearch;
import cn.hotol.app.bean.dto.accept.WaitAccessCollectDetailDto;
import cn.hotol.app.bean.dto.accept.WaitAccessCollectDto;
import cn.hotol.app.bean.dto.accept.WaitAccessOrderDetailDto;
import cn.hotol.app.bean.dto.accept.WaitAccessOrderDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.expressorder.*;
import cn.hotol.app.bean.dto.finish.FinishCollectDetailDto;
import cn.hotol.app.bean.dto.finish.FinishOrderDetailDto;
import cn.hotol.app.bean.dto.finish.FinishOrderDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.logistics.LogisticsInfoDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.number.TsHtOpenNumberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.push.PushDto;
import cn.hotol.app.bean.dto.storage.WaitStorageCollectDto;
import cn.hotol.app.bean.dto.storage.WaitStorageOrdDetailDto;
import cn.hotol.app.bean.dto.storage.WaitStorageOrderDto;
import cn.hotol.app.bean.dto.take.*;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LuBin
 * Date 2016-12-07.
 */

@Service
public class OrderServiceImpl implements OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;
    @Resource
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TsHtOpenNumberRepository tsHtOpenNumberRepository;
    @Resource
    private TdHtExpressSearchRepository tdHtExpressSearchRepository;
    @Resource
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;
    @Resource
    private TdHtCouponRepository tdHtCouponRepository;

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询待支付订单
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findToBePaidExp(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findToBePaidExp:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            Page<ToBePaidExpDto> toBePaidExpDtoPage = new Page<ToBePaidExpDto>(pageDto.getPage_size(), pageDto.getPage_no());
            //查询数据
            Integer count = tdHtExpressOrderRepository.findToBePaidExpAmount(member.getMemb_id());
            pageDto.setId(member.getMemb_id());
            pageDto.setLimit_str(toBePaidExpDtoPage.getLimitCriterion());
            List<ToBePaidExpDto> toBePaidExpDtoList = tdHtExpressOrderRepository.findToBePaidExp(pageDto);
            toBePaidExpDtoPage.setTotalCount(count);

            //从字典中获取省市区
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) dataDictionary.get(Constant.PROVINCE);
            Map cityMap = (Map) dataDictionary.get(Constant.CITY);
            Map areaMap = (Map) dataDictionary.get(Constant.AREA);
            //从字典中获取所有快递公司
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);

            List<ToBePaidExpDto> toBePaidExpList = new ArrayList<ToBePaidExpDto>();
            for (int t = 0; t < toBePaidExpDtoList.size(); t++) {
                ToBePaidExpDto toBePaidExpDto = toBePaidExpDtoList.get(t);
                String address = "";//发件地址
                for (int p = 0; p < provinceDtoList.size(); p++) {
                    ProvinceDto provinceDto = provinceDtoList.get(p);
                    if (provinceDto.getDict_id() == toBePaidExpDto.getAdd_province()) {
                        address = address + provinceDto.getProvince_name();
                    }
                }
                List<CityDto> cityDtoList = (List<CityDto>) cityMap.get(toBePaidExpDto.getAdd_province());
                for (int c = 0; c < cityDtoList.size(); c++) {
                    CityDto cityDto = cityDtoList.get(c);
                    if (cityDto.getDict_id() == toBePaidExpDto.getAdd_city()) {
                        address = address + cityDto.getCity_name();
                    }
                }
                List<AreaDto> areaDtoList = (List<AreaDto>) areaMap.get(toBePaidExpDto.getAdd_city());
                for (int a = 0; a < areaDtoList.size(); a++) {
                    AreaDto areaDto = areaDtoList.get(a);
                    if (areaDto.getDict_id() == toBePaidExpDto.getAdd_region()) {
                        address = address + areaDto.getArea_name();
                    }
                }
                for (int ec = 0; ec < expressCompanyList.size(); ec++) {
                    TsHtDictDto tsHtDict = expressCompanyList.get(ec);
                    if (tsHtDict.getDict_id() == toBePaidExpDto.getExpress_id()) {
                        toBePaidExpDto.setExpress_logo(tsHtDict.getDict_img());
                        toBePaidExpDto.setExpress_name(tsHtDict.getCode_name());
                    }
                }
                toBePaidExpDto.setAdd_detail_address(address + toBePaidExpDto.getAdd_detail_address());
                toBePaidExpList.add(toBePaidExpDto);
            }

            Map<String, Object> toBePaidExp = new HashMap<String, Object>();
            toBePaidExp.put("items", toBePaidExpList);
            toBePaidExp.put("total_pages", toBePaidExpDtoPage.getTotalPages());
            toBePaidExp.put("page_no", toBePaidExpDtoPage.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("待支付数据查询成功.");
            retInfo.setObj(toBePaidExp);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询受理中订单
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findAcceptInExp(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findAcceptInExp:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            //查询数据
            Page<AcceptInExpDto> acceptInExpPage = new Page<AcceptInExpDto>(pageDto.getPage_size(), pageDto.getPage_no());
            Integer count = tdHtExpressOrderRepository.findAcceptInExpAmount(member.getMemb_id());
            pageDto.setId(member.getMemb_id());
            pageDto.setLimit_str(acceptInExpPage.getLimitCriterion());
            List<AcceptInExpDto> acceptInExpDtoList = tdHtExpressOrderRepository.findAcceptInExp(pageDto);
            acceptInExpPage.setTotalCount(count);

            //从字典中获取省市区
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) dataDictionary.get(Constant.PROVINCE);
            Map cityMap = (Map) dataDictionary.get(Constant.CITY);
            Map areaMap = (Map) dataDictionary.get(Constant.AREA);
            //从字典中获取所有快递公司
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);

            List<AcceptInExpDto> acceptInExpList = new ArrayList<AcceptInExpDto>();
            for (int i = 0; i < acceptInExpDtoList.size(); i++) {
                AcceptInExpDto acceptInExpDto = acceptInExpDtoList.get(i);
                String address = "";//发件地址
                for (int p = 0; p < provinceDtoList.size(); p++) {
                    ProvinceDto provinceDto = provinceDtoList.get(p);
                    if (provinceDto.getDict_id() == acceptInExpDto.getAdd_province()) {
                        address = address + provinceDto.getProvince_name();
                    }
                }
                List<CityDto> cityDtoList = (List<CityDto>) cityMap.get(acceptInExpDto.getAdd_province());
                for (int c = 0; c < cityDtoList.size(); c++) {
                    CityDto cityDto = cityDtoList.get(c);
                    if (cityDto.getDict_id() == acceptInExpDto.getAdd_city()) {
                        address = address + cityDto.getCity_name();
                    }
                }
                List<AreaDto> areaDtoList = (List<AreaDto>) areaMap.get(acceptInExpDto.getAdd_city());
                for (int a = 0; a < areaDtoList.size(); a++) {
                    AreaDto areaDto = areaDtoList.get(a);
                    if (areaDto.getDict_id() == acceptInExpDto.getAdd_region()) {
                        address = address + areaDto.getArea_name();
                    }
                }
                for (int j = 0; j < expressCompanyList.size(); j++) {
                    TsHtDictDto tsHtDict = expressCompanyList.get(j);
                    if (tsHtDict.getDict_id() == acceptInExpDto.getExpress_id()) {
                        acceptInExpDto.setExpress_logo(tsHtDict.getDict_img());
                        acceptInExpDto.setExpress_name(tsHtDict.getCode_name());
                    }
                }
                acceptInExpDto.setAdd_detail_address(address + acceptInExpDto.getAdd_detail_address());
                acceptInExpList.add(acceptInExpDto);
            }

            Map<String, Object> acceptInExp = new HashMap<String, Object>();
            acceptInExp.put("items", acceptInExpList);
            acceptInExp.put("total_pages", acceptInExpPage.getTotalPages());
            acceptInExp.put("page_no", acceptInExpPage.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("受理中数据查询成功.");
            retInfo.setObj(acceptInExp);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询投递中快件
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findDeliveryExp(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findDeliveryExp:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            //查询数据
            Page<DeliveryExpDto> deliveryExpDtoPage = new Page<DeliveryExpDto>(pageDto.getPage_size(), pageDto.getPage_no());
            Integer count = tdHtExpressCollectRepository.findDeliveryExpAmount(member.getMemb_id());
            pageDto.setId(member.getMemb_id());
            pageDto.setLimit_str(deliveryExpDtoPage.getLimitCriterion());
            List<DeliveryExpDto> deliveryExpDtoList = tdHtExpressCollectRepository.findDeliveryExp(pageDto);
            deliveryExpDtoPage.setTotalCount(count);

            //从字典中获取省市区
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) dataDictionary.get(Constant.PROVINCE);
            Map cityMap = (Map) dataDictionary.get(Constant.CITY);
            Map areaMap = (Map) dataDictionary.get(Constant.AREA);
            //从字典中获取所有快递公司
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);

            List<DeliveryExpDto> deliveryExpList = new ArrayList<DeliveryExpDto>();
            for (int d = 0; d < deliveryExpDtoList.size(); d++) {
                DeliveryExpDto deliveryExpDto = deliveryExpDtoList.get(d);
                String address = "";
                for (int p = 0; p < provinceDtoList.size(); p++) {
                    ProvinceDto provinceDto = provinceDtoList.get(p);
                    if (provinceDto.getDict_id() == deliveryExpDto.getAdd_province()) {
                        address = address + provinceDto.getProvince_name();
                    }
                }
                List<CityDto> cityDtoList = (List<CityDto>) cityMap.get(deliveryExpDto.getAdd_province());
                for (int c = 0; c < cityDtoList.size(); c++) {
                    CityDto cityDto = cityDtoList.get(c);
                    if (cityDto.getDict_id() == deliveryExpDto.getAdd_city()) {
                        address = address + cityDto.getCity_name();
                    }
                }
                List<AreaDto> areaDtoList = (List<AreaDto>) areaMap.get(deliveryExpDto.getAdd_city());
                for (int a = 0; a < areaDtoList.size(); a++) {
                    AreaDto areaDto = areaDtoList.get(a);
                    if (areaDto.getDict_id() == deliveryExpDto.getAdd_region()) {
                        address = address + areaDto.getArea_name();
                    }
                }

                if (deliveryExpDto.getAdd_mobile_phone() == null || "".equals(deliveryExpDto.getAdd_mobile_phone().trim())) {
                    deliveryExpDto.setAdd_mobile_phone(deliveryExpDto.getAdd_telephone());
                }

                String expDetailed = "没有物流信息";
                //查询快递公司和物流信息
                for (int e = 0; e < expressCompanyList.size(); e++) {
                    TsHtDictDto tsHtDict = expressCompanyList.get(e);
                    if (tsHtDict.getDict_id() == deliveryExpDto.getExpress_id()) {
                        deliveryExpDto.setExpress_logo(tsHtDict.getDict_img());
                        TdHtExpressSearch tdHtExpressSearch = new TdHtExpressSearch();
                        tdHtExpressSearch.setExp_number(deliveryExpDto.getExpress_number());
                        tdHtExpressSearch.setExp_name(deliveryExpDto.getExpress_name());
                        tdHtExpressSearch.setMemb_id(member.getMemb_id());
                        TdHtExpressSearch tdHtExpressSearchDB = tdHtExpressSearchRepository.findExpressSearchByWaybill(tdHtExpressSearch);
                        if (tdHtExpressSearchDB != null) {
                            expDetailed = tdHtExpressSearchDB.getExp_detailed();
                        }
                    }
                }
                deliveryExpDto.setLogistic(expDetailed);
                deliveryExpDto.setAdd_detail_address(address + deliveryExpDto.getAdd_detail_address());
                deliveryExpList.add(deliveryExpDto);
            }

            Map<String, Object> deliveryExp = new HashMap<String, Object>();
            deliveryExp.put("items", deliveryExpList);
            deliveryExp.put("total_pages", deliveryExpDtoPage.getTotalPages());
            deliveryExp.put("page_no", deliveryExpDtoPage.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("投递中数据查询成功.");
            retInfo.setObj(deliveryExp);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询已完成快件
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findHasBeenFinishExp(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findHasBeenFinishExp:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            Page<HasBeenFinishExpDto> hasBeenFinishExpDtoPage = new Page<HasBeenFinishExpDto>(pageDto.getPage_size(), pageDto.getPage_no());
            //查询数据
            Integer count = tdHtExpressCollectRepository.findHasBeenFinishExpAmount(member.getMemb_id());
            pageDto.setId(member.getMemb_id());
            pageDto.setLimit_str(hasBeenFinishExpDtoPage.getLimitCriterion());
            List<HasBeenFinishExpDto> hasBeenFinishExpDtoList = tdHtExpressCollectRepository.findHasBeenFinishExp(pageDto);
            hasBeenFinishExpDtoPage.setTotalCount(count);

            //从字典中获取省市区
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) dataDictionary.get(Constant.PROVINCE);
            Map cityMap = (Map) dataDictionary.get(Constant.CITY);
            Map areaMap = (Map) dataDictionary.get(Constant.AREA);
            //从字典中获取所有快递公司
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);

            List<HasBeenFinishExpDto> hasBeenFinishExpList = new ArrayList<HasBeenFinishExpDto>();
            for (int h = 0; h < hasBeenFinishExpDtoList.size(); h++) {
                HasBeenFinishExpDto hasBeenFinishExpDto = hasBeenFinishExpDtoList.get(h);
                String address = "";
                for (int p = 0; p < provinceDtoList.size(); p++) {
                    ProvinceDto provinceDto = provinceDtoList.get(p);
                    if (provinceDto.getDict_id() == hasBeenFinishExpDto.getAdd_province()) {
                        address = address + provinceDto.getProvince_name();
                    }
                }
                List<CityDto> cityDtoList = (List<CityDto>) cityMap.get(hasBeenFinishExpDto.getAdd_province());
                for (int c = 0; c < cityDtoList.size(); c++) {
                    CityDto cityDto = cityDtoList.get(c);
                    if (cityDto.getDict_id() == hasBeenFinishExpDto.getAdd_city()) {
                        address = address + cityDto.getCity_name();
                    }
                }
                List<AreaDto> areaDtoList = (List<AreaDto>) areaMap.get(hasBeenFinishExpDto.getAdd_city());
                for (int a = 0; a < areaDtoList.size(); a++) {
                    AreaDto areaDto = areaDtoList.get(a);
                    if (areaDto.getDict_id() == hasBeenFinishExpDto.getAdd_region()) {
                        address = address + areaDto.getArea_name();
                    }
                }

                String expDetailed = "";
                for (int e = 0; e < expressCompanyList.size(); e++) {
                    TsHtDictDto tsHtDict = expressCompanyList.get(e);
                    if (tsHtDict.getDict_id() == hasBeenFinishExpDto.getExpress_id()) {
                        hasBeenFinishExpDto.setExpress_logo(tsHtDict.getDict_img());
                        if(hasBeenFinishExpDto.getExp_ord_clt_state() == 4){
                            expDetailed = "没有物流信息";
                            TdHtExpressSearch tdHtExpressSearch = new TdHtExpressSearch();
                            tdHtExpressSearch.setExp_number(hasBeenFinishExpDto.getExpress_number());
                            tdHtExpressSearch.setExp_name(hasBeenFinishExpDto.getExpress_name());
                            tdHtExpressSearch.setMemb_id(member.getMemb_id());
                            TdHtExpressSearch tdHtExpressSearchDB = tdHtExpressSearchRepository.findExpressSearchByWaybill(tdHtExpressSearch);
                            if (tdHtExpressSearchDB != null) {
                                expDetailed = tdHtExpressSearchDB.getExp_detailed();
                            }
                        }
                    }

                }
                hasBeenFinishExpDto.setLogistic(expDetailed);

                if (hasBeenFinishExpDto.getAdd_mobile_phone() == null || "".equals(hasBeenFinishExpDto.getAdd_mobile_phone().trim())) {
                    hasBeenFinishExpDto.setAdd_mobile_phone(hasBeenFinishExpDto.getAdd_telephone());
                }

                hasBeenFinishExpDto.setAdd_detail_address(address + hasBeenFinishExpDto.getAdd_detail_address());
                hasBeenFinishExpList.add(hasBeenFinishExpDto);
            }

            Map<String, Object> hasBeenFinishExp = new HashMap<String, Object>();
            hasBeenFinishExp.put("items", hasBeenFinishExpList);
            hasBeenFinishExp.put("total_pages", hasBeenFinishExpDtoPage.getTotalPages());
            hasBeenFinishExp.put("page_no", hasBeenFinishExpDtoPage.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("已完成数据查询成功.");
            retInfo.setObj(hasBeenFinishExp);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtExpressOrder
     * @return RetInfo
     * @Purpose 查询受理中订单详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findAcceptOrderDetail(TdHtExpressOrder tdHtExpressOrder) {
        String logInfo = this.getClass().getName() + ":findAcceptOrderDetail:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            AcceptOrderDto acceptOrderDto = tdHtExpressOrderRepository.findAcceptOrderById(tdHtExpressOrder.getExp_ord_id());

            if (tdHtExpressOrder.getExp_ord_state() >= Constant.ACCEPT_ORDER_STATE) {
                TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(acceptOrderDto.getAgent_id());

                acceptOrderDto.setAgent_head_portrait(tdHtAgentDto.getAgent_head_portrait());
                acceptOrderDto.setAgent_name(tdHtAgentDto.getAgent_name());
                acceptOrderDto.setAgent_phone(tdHtAgentDto.getAgent_phone());
                acceptOrderDto.setAgent_id(0);
            }

            List<AcceptCollectDto> acceptCollectDtoList = tdHtExpressCollectRepository.findAcceptCollectByOrder(tdHtExpressOrder.getExp_ord_id());

            //从字典中获取省市区
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) dataDictionary.get(Constant.PROVINCE);
            Map cityMap = (Map) dataDictionary.get(Constant.CITY);
            Map areaMap = (Map) dataDictionary.get(Constant.AREA);

            List<AcceptCollectDto> acceptCollectList = new ArrayList<AcceptCollectDto>();
            for (int i = 0; i < acceptCollectDtoList.size(); i++) {
                AcceptCollectDto acceptCollectDto = acceptCollectDtoList.get(i);
                if (acceptCollectDto.getAdd_mobile_phone() == null || "".equals(acceptCollectDto.getAdd_mobile_phone().trim())) {
                    acceptCollectDto.setAdd_mobile_phone(acceptCollectDto.getAdd_telephone());
                }
                String recipientAddress = "";
                for (int p = 0; p < provinceDtoList.size(); p++) {
                    ProvinceDto provinceDto = provinceDtoList.get(p);
                    if (provinceDto.getDict_id() == acceptCollectDto.getAdd_province()) {
                        recipientAddress = recipientAddress + provinceDto.getProvince_name();
                    }
                }
                List<CityDto> cityDtoList = (List<CityDto>) cityMap.get(acceptCollectDto.getAdd_province());
                for (int c = 0; c < cityDtoList.size(); c++) {
                    CityDto cityDto = cityDtoList.get(c);
                    if (cityDto.getDict_id() == acceptCollectDto.getAdd_city()) {
                        recipientAddress = recipientAddress + cityDto.getCity_name();
                    }
                }
                List<AreaDto> areaDtoList = (List<AreaDto>) areaMap.get(acceptCollectDto.getAdd_city());
                for (int a = 0; a < areaDtoList.size(); a++) {
                    AreaDto areaDto = areaDtoList.get(a);
                    if (areaDto.getDict_id() == acceptCollectDto.getAdd_region()) {
                        recipientAddress = recipientAddress + areaDto.getArea_name();
                    }
                }
                acceptCollectDto.setAdd_detail_address(recipientAddress + acceptCollectDto.getAdd_detail_address());
                acceptCollectList.add(acceptCollectDto);
            }

            acceptOrderDto.setAccept_collect_list(acceptCollectList);
            if (acceptOrderDto.getAdd_mobile_phone() == null || "".equals(acceptOrderDto.getAdd_mobile_phone().trim())) {
                acceptOrderDto.setAdd_mobile_phone(acceptOrderDto.getAdd_telephone());
            }
            String sendAddress = "";
            for (int p = 0; p < provinceDtoList.size(); p++) {
                ProvinceDto provinceDto = provinceDtoList.get(p);
                if (provinceDto.getDict_id() == acceptOrderDto.getAdd_province()) {
                    sendAddress = sendAddress + provinceDto.getProvince_name();
                }
            }
            List<CityDto> cityDtoList = (List<CityDto>) cityMap.get(acceptOrderDto.getAdd_province());
            for (int c = 0; c < cityDtoList.size(); c++) {
                CityDto cityDto = cityDtoList.get(c);
                if (cityDto.getDict_id() == acceptOrderDto.getAdd_city()) {
                    sendAddress = sendAddress + cityDto.getCity_name();
                }
            }
            List<AreaDto> areaDtoList = (List<AreaDto>) areaMap.get(acceptOrderDto.getAdd_city());
            for (int a = 0; a < areaDtoList.size(); a++) {
                AreaDto areaDto = areaDtoList.get(a);
                if (areaDto.getDict_id() == acceptOrderDto.getAdd_region()) {
                    sendAddress = sendAddress + areaDto.getArea_name();
                }
            }
            acceptOrderDto.setAdd_detail_address(sendAddress + acceptOrderDto.getAdd_detail_address());

            retInfo.setMark("0");
            retInfo.setTip("数据查询成功.");
            retInfo.setObj(acceptOrderDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 查询快件详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findExpDetail(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        String logInfo = this.getClass().getName() + ":findExpDetail:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            ExpressDetailDto expressDetailDto = tdHtExpressCollectRepository.findExpDetailByCollectId(tdHtExpressOrderCollectDto.getExp_ord_clt_id());

            //从字典中获取省市区
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<ProvinceDto> provinceDtoList = (List<ProvinceDto>) dataDictionary.get(Constant.PROVINCE);
            Map cityMap = (Map) dataDictionary.get(Constant.CITY);
            Map areaMap = (Map) dataDictionary.get(Constant.AREA);
            //从字典中获取所有快递公司
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);

            String sendAddress = "";
            String collectAddress = "";
            for (int p = 0; p < provinceDtoList.size(); p++) {
                ProvinceDto provinceDto = provinceDtoList.get(p);
                if (provinceDto.getDict_id() == expressDetailDto.getAdd_province()) {
                    sendAddress = sendAddress + provinceDto.getProvince_name();
                }
                if (provinceDto.getDict_id() == expressDetailDto.getCollect_province()) {
                    collectAddress = collectAddress + provinceDto.getProvince_name();
                }
            }
            List<CityDto> sendCityDtoList = (List<CityDto>) cityMap.get(expressDetailDto.getAdd_province());
            for (int c = 0; c < sendCityDtoList.size(); c++) {
                CityDto cityDto = sendCityDtoList.get(c);
                if (cityDto.getDict_id() == expressDetailDto.getAdd_city()) {
                    sendAddress = sendAddress + cityDto.getCity_name();
                }
            }
            List<CityDto> collectCityDtoList = (List<CityDto>) cityMap.get(expressDetailDto.getCollect_province());
            for (int c = 0; c < collectCityDtoList.size(); c++) {
                CityDto cityDto = collectCityDtoList.get(c);
                if (cityDto.getDict_id() == expressDetailDto.getCollect_city()) {
                    collectAddress = collectAddress + cityDto.getCity_name();
                }
            }
            List<AreaDto> sendAreaDtoList = (List<AreaDto>) areaMap.get(expressDetailDto.getAdd_city());
            for (int a = 0; a < sendAreaDtoList.size(); a++) {
                AreaDto areaDto = sendAreaDtoList.get(a);
                if (areaDto.getDict_id() == expressDetailDto.getAdd_region()) {
                    sendAddress = sendAddress + areaDto.getArea_name();
                }
            }
            List<AreaDto> collectAreaDtoList = (List<AreaDto>) areaMap.get(expressDetailDto.getAdd_city());
            for (int a = 0; a < collectAreaDtoList.size(); a++) {
                AreaDto areaDto = collectAreaDtoList.get(a);
                if (areaDto.getDict_id() == expressDetailDto.getCollect_region()) {
                    collectAddress = collectAddress + areaDto.getArea_name();
                }
            }
            expressDetailDto.setAdd_detail_address(sendAddress + expressDetailDto.getAdd_detail_address());
            expressDetailDto.setCollect_detail_address(collectAddress + expressDetailDto.getCollect_detail_address());

            String expressLogo = "";
            String expressCode = "";
            for (int i = 0; i < expressCompanyList.size(); i++) {
                TsHtDictDto tsHtDictDto = expressCompanyList.get(i);
                if (tsHtDictDto.getDict_id() == expressDetailDto.getExpress_id()) {
                    expressLogo = tsHtDictDto.getDict_img();
                    expressCode = tsHtDictDto.getDict_code();
                }
            }
            expressDetailDto.setExpress_logo(expressLogo);

            if (expressDetailDto.getAdd_mobile_phone() == null || "".equals(expressDetailDto.getAdd_mobile_phone().trim())) {
                expressDetailDto.setAdd_mobile_phone(expressDetailDto.getAdd_telephone());
            }
            if (expressDetailDto.getCollect_mobile() == null || "".equals(expressDetailDto.getCollect_mobile().trim())) {
                expressDetailDto.setCollect_mobile(expressDetailDto.getCollect_telephone());
            }

            List<LogisticsInfoDto> logisticsInfoDtoList = new ArrayList<LogisticsInfoDto>();
            KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
            String result = api.getOrderTracesByJson(expressCode, expressDetailDto.getExpress_number());
            //解析快递鸟返回的json
            JSONObject jsonObject = JSONObject.fromObject(result);
            if (jsonObject.getBoolean("Success")) {
                if (jsonObject.get("State") != null) {
                    JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("Traces"));
                    List<Map> mapList = jsonArray;
                    for (int i = mapList.size() - 1; i >= 0; i--) {
                        LogisticsInfoDto logisticsInfoDto = new LogisticsInfoDto();
                        Map logisticsInfoMap = mapList.get(i);
                        logisticsInfoDto.setAccept_time(logisticsInfoMap.get("AcceptTime").toString());
                        logisticsInfoDto.setAccept_station(logisticsInfoMap.get("AcceptStation").toString());
                        logisticsInfoDtoList.add(logisticsInfoDto);
                    }
                }
            }
            expressDetailDto.setExpress_logistics(logisticsInfoDtoList);

            retInfo.setMark("0");
            retInfo.setTip("数据查询成功.");
            retInfo.setObj(expressDetailDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 取消待受理的订单
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo cancelOrder(TdHtExpressOrderDto tdHtExpressOrderDto) {
        String logInfo = this.getClass().getName() + ":cancelOrder:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto expressOrderDB = tdHtExpressOrderRepository.findTdHtExpressOrderById(tdHtExpressOrderDto.getExp_ord_id());

            if (expressOrderDB.getExp_ord_state() >= Constant.WAIT_STORAGE_EXPRESS_STATE && expressOrderDB.getExp_ord_state() < 6) {
                retInfo.setMark("1");
                retInfo.setTip("该订单已支付.");
            } else {
                tdHtExpressOrderDto.setExp_ord_state(0);
                tdHtExpressOrderRepository.cancelExpressOrder(tdHtExpressOrderDto);
                tdHtExpressCollectRepository.cancelCollect(tdHtExpressOrderDto.getExp_ord_id());

                List<TdHtAgentFoundChangeDto> tdHtAgentFoundChangeDtos = tdHtAgentFoundChangeRepository.findAgentFoundByOrdId(expressOrderDB.getExp_ord_id());
                for (int i = 0; i < tdHtAgentFoundChangeDtos.size(); i++) {
                    TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = tdHtAgentFoundChangeDtos.get(i);
                    tdHtAgentFoundChangeDto.setAfchg_state(1);
                    tdHtAgentFoundChangeRepository.updateAgentFoundState(tdHtAgentFoundChangeDto);
                }

                if (expressOrderDB.getAgent_id() > 0) {
                    PushDto pushDto = tdHtAgentRepository.findAgentPushInfo(expressOrderDB.getAgent_id());

                    if(expressOrderDB.getExp_ord_state() == 2 && pushDto.getPush_type() == 1){
                        PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有待揽收的快件已被用户取消.", "11", "esp_custom_cancle", pushDto.getApp_version());
                    }
                    if(expressOrderDB.getExp_ord_state() == 2 && pushDto.getPush_type() == 2){
                        PushUtil.pushIosAgent(pushDto.getPush_token(), "您有待揽收的快件已被用户取消.", "11", "espsCancelOrder.caf", pushDto.getApp_version());
                    }
                    if(expressOrderDB.getExp_ord_state() == 3 && pushDto.getPush_type() == 1){
                        PushUtil.pushAndroidAgent(pushDto.getPush_token(), "您有待支付的快件已被用户取消.", "12", "esp_custom_cancle", pushDto.getApp_version());
                    }
                    if(expressOrderDB.getExp_ord_state() == 3 && pushDto.getPush_type() == 2){
                        PushUtil.pushIosAgent(pushDto.getPush_token(), "您有待支付的快件已被用户取消.", "12", "espsCancelOrder.caf", pushDto.getApp_version());
                    }
                }

                retInfo.setMark("0");
                retInfo.setTip("订单取消成功.");
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
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 查询待接单列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo waitForAccess(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":waitForAccess:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            TdHtAgentDto tdHtAgentDto=tdHtAgentRepository.findAgentById(agentDto.getAgent_id());

            Page<WaitAccessOrderDto> waitAccessOrderDtoPage = new Page<WaitAccessOrderDto>(pageDto.getPage_size(), pageDto.getPage_no());
            if(tdHtAgentDto.getAgent_type()==1){
                pageDto.setType(0);
            }else if(tdHtAgentDto.getAgent_type()==2){
                pageDto.setType(2);
            }
            pageDto.setId(tdHtAgentDto.getArea_id());
            int count = tdHtExpressOrderRepository.findWaitForAccessCount(pageDto);
            waitAccessOrderDtoPage.setTotalCount(count);
            pageDto.setLimit_str(waitAccessOrderDtoPage.getLimitCriterion());
            List<WaitAccessOrderDto> waitAccessOrderDtoList = tdHtExpressOrderRepository.findWaitForAccess(pageDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
            for (int i = 0; i < waitAccessOrderDtoList.size(); i++) {
                WaitAccessOrderDto waitAccessOrderDto = waitAccessOrderDtoList.get(i);
                //计算奖励
                String key = waitAccessOrderDto.getExpress_id() + "#" + waitAccessOrderDto.getAdd_region();
                CourierDto courierDto = courierMap.get(key);
                int expressNum = waitAccessOrderDto.getExp_ord_size();
                BigDecimal reward = courierDto.getFirst_single_bonus().add(courierDto.getMore_single_bonus().multiply(new BigDecimal(expressNum - 1)));
                List<WaitAccessCollectDto> waitAccessCollectDtoList = tdHtExpressCollectRepository.findWaitAccCollect(waitAccessOrderDto.getExp_ord_id());
                for (int j = 0; j < waitAccessCollectDtoList.size(); j++) {
                    WaitAccessCollectDto waitAccessCollectDto = waitAccessCollectDtoList.get(j);
                    String province = dicts.get(String.valueOf(waitAccessCollectDto.getAdd_province())).getCode_name();
                    waitAccessCollectDto.setProvince(province);
                    BigDecimal weight = waitAccessCollectDto.getExp_ord_clt_height();
                    if (weight.doubleValue() >= courierDto.getSubsidy_weight().doubleValue()) {
                        reward = reward.add(courierDto.getSubsidy_bonus());
                    }
                }
                waitAccessOrderDto.setReward(reward);
                waitAccessOrderDto.setCollects(waitAccessCollectDtoList);
                //计算上门时间差
                long doorStart = waitAccessOrderDto.getDoor_start_time().getTime();
                long doorEnd = waitAccessOrderDto.getDoor_end_time().getTime();
                long difference = doorEnd - doorStart;
                if (difference == 30 * 60 * 1000) {
                    waitAccessOrderDto.setTime_difference("30分钟");
                } else {
                    waitAccessOrderDto.setTime_difference("2小时");
                }

                if (doorStart - new Date().getTime() > 3 * 60 * 1000) {
                    waitAccessOrderDto.setBespoke(1);
                } else {
                    waitAccessOrderDto.setBespoke(0);
                }
                //计算距离
                double lngOne = waitAccessOrderDto.getAdd_longitude().doubleValue();
                double latOne = waitAccessOrderDto.getAdd_latitude().doubleValue();
                double lngTwo = tdHtAgentDto.getAgent_longitude().doubleValue();
                double latTwo = tdHtAgentDto.getAgent_latitude().doubleValue();
                double distance = CommonUtil.getDistance(lngOne, latOne, lngTwo, latTwo);
                waitAccessOrderDto.setDistance(new BigDecimal(distance));
                //获取快递logo
                String region = dicts.get(String.valueOf(waitAccessOrderDto.getAdd_region())).getCode_name();
                waitAccessOrderDto.setAdd_detail_address(region + waitAccessOrderDto.getAdd_detail_address());
                String expressLogo = dicts.get(String.valueOf(waitAccessOrderDto.getExpress_id())).getDict_img();
                waitAccessOrderDto.setExpress_logo(expressLogo);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", waitAccessOrderDtoList);
            map.put("total_pages", waitAccessOrderDtoPage.getTotalPages());
            map.put("page_no", waitAccessOrderDtoPage.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("待接单数据查询成功.");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询待接单订单详细信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo waitForAccessDetail(int orderId) {
        String logInfo = this.getClass().getName() + ":waitForAccessDetail:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            WaitAccessOrderDetailDto waitAccessOrderDetail = tdHtExpressOrderRepository.findWaitAccessDetail(orderId);

            //获取快递logo
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            String expressLogo = dicts.get(String.valueOf(waitAccessOrderDetail.getExpress_id())).getDict_img();
            waitAccessOrderDetail.setExpress_logo(expressLogo);

            if (waitAccessOrderDetail.getAdd_mobile_phone() == null || "".equals(waitAccessOrderDetail.getAdd_mobile_phone())) {
                waitAccessOrderDetail.setAdd_mobile_phone(waitAccessOrderDetail.getAdd_telephone());
            }

            List<WaitAccessCollectDetailDto> waitAccessCollectDetailList = tdHtExpressCollectRepository.findWaitAccCollectDetail(orderId);
            for (int i = 0; i < waitAccessCollectDetailList.size(); i++) {
                WaitAccessCollectDetailDto waitAccessCollectDetailDto = waitAccessCollectDetailList.get(i);

                if (waitAccessCollectDetailDto.getAdd_mobile_phone() == null || "".equals(waitAccessCollectDetailDto.getAdd_mobile_phone())) {
                    waitAccessCollectDetailDto.setAdd_mobile_phone(waitAccessCollectDetailDto.getAdd_telephone());
                }
                //从字典中获取省市区
                String address = dicts.get(String.valueOf(waitAccessCollectDetailDto.getAdd_province())).getCode_name();
                address = address + dicts.get(String.valueOf(waitAccessCollectDetailDto.getAdd_city())).getCode_name();
                address = address + dicts.get(String.valueOf(waitAccessCollectDetailDto.getAdd_region())).getCode_name();
                waitAccessCollectDetailDto.setAdd_detail_address(address + waitAccessCollectDetailDto.getAdd_detail_address());
            }
            waitAccessOrderDetail.setCollects(waitAccessCollectDetailList);

            retInfo.setMark("0");
            retInfo.setTip("待接单数据查询成功.");
            retInfo.setObj(waitAccessOrderDetail);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param orderId
     * @param token
     * @return RetInfo
     * @Purpose 接单
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo accessExpressOder(int orderId, String token) {
        String logInfo = this.getClass().getName() + ":accessExpressOder:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(tdHtAgentDto.getAgent_id());
            if (agentDto.getAgent_state() != 4) {
                retInfo.setMark("1");
                retInfo.setTip("您已下班.");
            } else {
                TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(orderId);
                if (tdHtExpressOrderDto.getExp_ord_state() != 1) {
                    retInfo.setMark("1");
                    retInfo.setTip("该订单已被接单，请接取其他订单.");
                } else {
                    tdHtExpressOrderDto.setAgent_id(tdHtAgentDto.getAgent_id());
                    Integer newestWaitTakeExpOrdCount = tdHtExpressOrderRepository.findExpOrdNumByMembAndAgent(tdHtExpressOrderDto);
                    TdHtExpressOrderDto expressOrderDto = new TdHtExpressOrderDto();
                    expressOrderDto.setMemb_id(tdHtExpressOrderDto.getMemb_id());
                    expressOrderDto.setAgent_id(tdHtAgentDto.getAgent_id());
                    expressOrderDto.setPay_time(new Date(System.currentTimeMillis() - 30 * 60 * 1000));
                    Integer memberPayExpOrdCount = tdHtExpressOrderRepository.findMembPayExpOrdCount(expressOrderDto);

                    Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
                    String key = tdHtExpressOrderDto.getExpress_id() + "#" + tdHtExpressOrderDto.getAdd_region();
                    CourierDto courierDto = courierMap.get(key);

                    tdHtExpressOrderDto.setExp_ord_id(orderId);
                    tdHtExpressOrderDto.setExp_ord_taking_time(new Date());
                    tdHtExpressOrderDto.setExp_ord_state(2);
                    tdHtExpressOrderRepository.accessExpressOrder(tdHtExpressOrderDto);

                    TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = new TdHtAgentFoundChangeDto();
                    if (newestWaitTakeExpOrdCount == 0 && memberPayExpOrdCount == 0) {
                        tdHtAgentFoundChangeDto.setAfchg_change_amount(courierDto.getFirst_single_bonus().add(courierDto.getMore_single_bonus().multiply(new BigDecimal(tdHtExpressOrderDto.getExp_ord_size() - 1))));
                    } else {
                        tdHtAgentFoundChangeDto.setAfchg_change_amount(courierDto.getMore_single_bonus().multiply(new BigDecimal(tdHtExpressOrderDto.getExp_ord_size())));
                    }
                    tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(System.currentTimeMillis()));
                    tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                    tdHtAgentFoundChangeDto.setAfchg_type(1);
                    tdHtAgentFoundChangeDto.setAfchg_state(2);
                    tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub());
                    tdHtAgentFoundChangeDto.setAfchg_name("抢单奖励");
                    tdHtAgentFoundChangeDto.setAgent_id(agentDto.getAgent_id());
                    tdHtAgentFoundChangeDto.setAfchg_front_amount(agentDto.getAgent_balance());
                    tdHtAgentFoundChangeDto.setAfchg_back_amount(agentDto.getAgent_balance().add(tdHtAgentFoundChangeDto.getAfchg_change_amount()));
                    tdHtAgentFoundChangeDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                    tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);

                    PushDto pushDto = tdHtMemberRepository.findPushInfoByMembId(tdHtExpressOrderDto.getMemb_id());

                    if (pushDto.getPush_type() == 1) {
                        PushUtil.pushAndroidMember(pushDto.getPush_token(), "您的订单已被受理.", "2", "", pushDto.getApp_version());
                    } else if (pushDto.getPush_type() == 2) {
                        PushUtil.pushIosMember(pushDto.getPush_token(), "您的订单已被受理.", "2", "default", pushDto.getApp_version());
                    }

                    retInfo.setMark("0");
                    retInfo.setTip("接单成功.");
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
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 查询待揽件列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo waitForTake(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":waitForTake:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);

            Page<WaitTakeOrderDto> waitTakeOrderPage = new Page<WaitTakeOrderDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setId(tdHtAgentDto.getAgent_id());
            pageDto.setLimit_str(waitTakeOrderPage.getLimitCriterion());
            List<WaitTakeOrderDto> waitTakeOrderList = tdHtExpressOrderRepository.findWaitForTake(pageDto);
            int count = tdHtExpressOrderRepository.findWaitForTakeCount(tdHtAgentDto.getAgent_id());
            waitTakeOrderPage.setTotalCount(count);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            for (int i = 0; i < waitTakeOrderList.size(); i++) {
                WaitTakeOrderDto waitTakeOrderDto = waitTakeOrderList.get(i);
                //获取快递logo
                String expressLogo = dicts.get(String.valueOf(waitTakeOrderDto.getExpress_id())).getDict_img();
                waitTakeOrderDto.setExpress_logo(expressLogo);
                //获取地址
                String region = dicts.get(String.valueOf(waitTakeOrderDto.getAdd_region())).getCode_name();
                waitTakeOrderDto.setAdd_detail_address(region + waitTakeOrderDto.getAdd_detail_address());
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", waitTakeOrderList);
            map.put("total_pages", waitTakeOrderPage.getTotalPages());
            map.put("page_no", waitTakeOrderPage.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("待揽件数据查询成功.");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderDto
     * @param token
     * @return RetInfo
     * @Purpose 保存代理人备注
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo writeAgentNote(TdHtExpressOrderDto tdHtExpressOrderDto, String token) {
        String logInfo = this.getClass().getName() + ":writeAgentNote:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);

            int agentState = tdHtAgentRepository.findAgentStateById(tdHtAgentDto.getAgent_id());
            if (agentState != 4) {
                retInfo.setMark("1");
                retInfo.setTip("您已下班.");
            } else {
                tdHtExpressOrderRepository.updateAgentNote(tdHtExpressOrderDto);
                retInfo.setMark("0");
                retInfo.setTip("备注保存成功.");
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
     * @param orderId
     * @return RetInfo
     * @Purpose 查询揽件展示
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo takeCollectDetail(int orderId) {
        String logInfo = this.getClass().getName() + ":takeCollectDetail:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(orderId);
            List<TakeCollectDto> takeCollectDtoList = tdHtExpressCollectRepository.findTakeCollect(orderId);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            for (int i = 0; i < takeCollectDtoList.size(); i++) {
                TakeCollectDto takeCollectDto = takeCollectDtoList.get(i);
                //从字典中获取省市区
                String address = dicts.get(String.valueOf(takeCollectDto.getAdd_province())).getCode_name();
                address = address + dicts.get(String.valueOf(takeCollectDto.getAdd_city())).getCode_name();
                address = address + dicts.get(String.valueOf(takeCollectDto.getAdd_region())).getCode_name();
                takeCollectDto.setAdd_detail_address(address + takeCollectDto.getAdd_detail_address());
                //获取快递logo
                String expressLogo = dicts.get(String.valueOf(takeCollectDto.getExpress_id())).getDict_img();
                takeCollectDto.setExpress_logo(expressLogo);
                //判断手机号
                if (takeCollectDto.getAdd_mobile_phone() == null || "".equals(takeCollectDto.getAdd_mobile_phone())) {
                    takeCollectDto.setAdd_mobile_phone(takeCollectDto.getAdd_telephone());
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            if (tdHtExpressOrderDto.getDoor_end_time().getTime() < new Date().getTime()) {
                map.put("gratuity", 0);
            } else {
                map.put("gratuity", tdHtExpressOrderDto.getExp_ord_gratuity());
            }
            map.put("collects", takeCollectDtoList);
            map.put("memb_type", tdHtExpressOrderDto.getMemb_type());

            Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
            String key = tdHtExpressOrderDto.getExpress_id() + "#" + tdHtExpressOrderDto.getAdd_region();
            CourierDto courierDto = courierMap.get(key);
            map.put("eoa_type", courierDto.getEoa_type());

            retInfo.setMark("0");
            retInfo.setTip("揽件明细查询成功.");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询待揽件订单详细信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo waitForTakeDetail(int orderId) {
        String logInfo = this.getClass().getName() + ":waitForTakeDetail:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            WaitTakeOrderDetailDto waitTakeOrderDetailDto = tdHtExpressOrderRepository.findWaitTakeDetail(orderId);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            //获取快递logo
            String expressLogo = dicts.get(String.valueOf(waitTakeOrderDetailDto.getExpress_id())).getDict_img();
            waitTakeOrderDetailDto.setExpress_logo(expressLogo);
            String region = dicts.get(String.valueOf(waitTakeOrderDetailDto.getAdd_region())).getCode_name();
            waitTakeOrderDetailDto.setAdd_detail_address(region + waitTakeOrderDetailDto.getAdd_detail_address());

            if (waitTakeOrderDetailDto.getAdd_mobile_phone() == null || "".equals(waitTakeOrderDetailDto.getAdd_mobile_phone())) {
                waitTakeOrderDetailDto.setAdd_mobile_phone(waitTakeOrderDetailDto.getAdd_telephone());
            }

            List<WaitTakeCollectDetailDto> waitTakeCollectDetailList = tdHtExpressCollectRepository.findWaitTakeCollectDetail(orderId);
            for (int i = 0; i < waitTakeCollectDetailList.size(); i++) {
                WaitTakeCollectDetailDto waitTakeCollectDetailDto = waitTakeCollectDetailList.get(i);

                if (waitTakeCollectDetailDto.getAdd_mobile_phone() == null || "".equals(waitTakeCollectDetailDto.getAdd_mobile_phone())) {
                    waitTakeCollectDetailDto.setAdd_mobile_phone(waitTakeCollectDetailDto.getAdd_telephone());
                }
                //从字典中获取省市区
                String address = dicts.get(String.valueOf(waitTakeCollectDetailDto.getAdd_province())).getCode_name();
                address = address + dicts.get(String.valueOf(waitTakeCollectDetailDto.getAdd_city())).getCode_name();
                address = address + dicts.get(String.valueOf(waitTakeCollectDetailDto.getAdd_region())).getCode_name();
                waitTakeCollectDetailDto.setAdd_detail_address(address + waitTakeCollectDetailDto.getAdd_detail_address());
            }
            waitTakeOrderDetailDto.setCollects(waitTakeCollectDetailList);

            retInfo.setMark("0");
            retInfo.setTip("待揽件详情数据查询成功.");
            retInfo.setObj(waitTakeOrderDetailDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param takeOrderDto
     * @param token
     * @return RetInfo
     * @Purpose 揽件
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public synchronized RetInfo takeExpressOrder(TakeOrderDto takeOrderDto, String token, Integer version, Integer client_type) {
        String logInfo = this.getClass().getName() + ":takeExpressOrder:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);

            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(tdHtAgentDto.getAgent_id());
            if (agentDto.getAgent_state() != 4) {
                retInfo.setMark("1");
                retInfo.setTip("您已下班.");
            } else {
                TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(takeOrderDto.getExp_ord_id());
                if(tdHtExpressOrderDto.getExp_ord_state() != 2){
                    retInfo.setMark("1");
                    retInfo.setTip("该订单已揽收或已取消.");
                }else {
                    Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
                    String key = tdHtExpressOrderDto.getExpress_id() + "#" + tdHtExpressOrderDto.getAdd_region();
                    CourierDto courierDto = courierMap.get(key);

                    TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = new TdHtAgentFoundChangeDto();
                    long date = System.currentTimeMillis();

                    ExpressScreenUtil expressScreenUtil = new ExpressScreenUtil();
                    List<TdHtExpressOrderCollectDto> tdHtExpressOrderCollectDtoList = takeOrderDto.getCollects();

                    TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(tdHtExpressOrderDto.getMemb_id());
                    for (int i = 0 ; i < tdHtExpressOrderCollectDtoList.size() ; i ++){
                        TdHtExpressOrderCollectDto collectDto = tdHtExpressOrderCollectDtoList.get(i);
                        TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressCollectRepository.findTdHtExpressCollectById(collectDto.getExp_ord_clt_id());

                        if(version <= 6 && client_type == 1 && tdHtExpressOrderCollectDtoList.size() > 1 && tdHtExpressOrderDto.getMemb_type() == 3
                                && tdHtExpressOrderCollectDto.getHt_number() != null && !"".equals(tdHtExpressOrderCollectDto.getHt_number())){
                            collectDto.setHt_number(tdHtExpressOrderCollectDto.getHt_number());
                        }

                        BigDecimal weight = new BigDecimal(collectDto.getExp_ord_clt_height());
                        BigDecimal price = expressScreenUtil.reckonExpressPrice(tdHtExpressOrderDto.getExpress_id(), weight, tdHtExpressOrderDto.getAdd_province(), tdHtExpressOrderCollectDto.getAdd_province());

                        collectDto.setTotal_amount(price);
                        if (tdHtMemberDto.getMemb_discount().doubleValue() < 1) {
                            price = price.multiply(tdHtMemberDto.getMemb_discount());
                        }
                        collectDto.setExpress_price(price.setScale(2, BigDecimal.ROUND_HALF_UP));
                        collectDto.setDiscount_amount(collectDto.getTotal_amount().subtract(collectDto.getExpress_price()));
                    }

                    BigDecimal weight_bonus = new BigDecimal(0);
                    if (tdHtExpressOrderDto.getMemb_type() == 2) {
                        BigDecimal weight = new BigDecimal(0);
                        BigDecimal totalPrice = new BigDecimal(0);
                        BigDecimal expressPrice = new BigDecimal(0);
                        BigDecimal discountPrice = new BigDecimal(0);

                        for (int i = 0; i < tdHtExpressOrderCollectDtoList.size(); i++) {
                            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(i);
                            if (tdHtExpressOrderCollectDto.getExp_ord_clt_height() >= courierDto.getSubsidy_weight().doubleValue()) {
                                weight_bonus = weight_bonus.add(courierDto.getSubsidy_bonus());
                            }
                            weight = weight.add(new BigDecimal(tdHtExpressOrderCollectDto.getExp_ord_clt_height()));
                            totalPrice = totalPrice.add(tdHtExpressOrderCollectDto.getTotal_amount());
                            expressPrice = expressPrice.add(tdHtExpressOrderCollectDto.getExpress_price());
                            discountPrice = discountPrice.add(tdHtExpressOrderCollectDto.getDiscount_amount());
                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(2);

                            if (takeOrderDto.getExp_ord_type() == 1) {
                                TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto = new TsHtExpressOpenNumberDto();
                                tsHtExpressOpenNumberDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                                tsHtExpressOpenNumberDto.setRegion_id(tdHtExpressOrderDto.getAdd_city());
                                TsHtExpressOpenNumberDto numberDto = tsHtExpressOpenNumberRepository.findCanUseExpressNumber(tsHtExpressOpenNumberDto);

                                if (numberDto != null) {
                                    tdHtExpressOrderCollectDto.setExpress_number(numberDto.getExpress_number());

                                    numberDto.setAgent_id(tdHtAgentDto.getAgent_id());
                                    numberDto.setExp_open_state(1);
                                    tsHtExpressOpenNumberRepository.updateExpressNumberState(numberDto);
                                }
                            } else if (takeOrderDto.getExp_ord_type() == 2) {
                                tdHtExpressOrderCollectDto.setExpress_number("");
                            }

                            tdHtExpressCollectRepository.takeMonthOrderCollect(tdHtExpressOrderCollectDto);
                        }
                        tdHtExpressOrderDto.setExp_ord_weight(weight.doubleValue());
                        tdHtExpressOrderDto.setExp_ord_amount(expressPrice.doubleValue());
                        tdHtExpressOrderDto.setTotal_amount(totalPrice);
                        tdHtExpressOrderDto.setDiscount_amount(discountPrice);
                        tdHtExpressOrderDto.setCollect_time(new Date(date));
                        tdHtExpressOrderDto.setExp_ord_state(4);
                        tdHtExpressOrderDto.setExp_ord_type(courierDto.getEoa_type());
                        tdHtExpressOrderDto.setPay_time(new Date(date));
                        tdHtExpressOrderDto.setExp_ord_pay_amount(totalPrice.doubleValue());
                        tdHtExpressOrderRepository.takeMonthOrder(tdHtExpressOrderDto);

                        if (tdHtMemberDto.getPush_type() == 1) {
                            PushUtil.pushAndroidMember(tdHtMemberDto.getPush_token(), "您的订单已被揽收.", "3", "", tdHtMemberDto.getApp_version());
                        } else if (tdHtMemberDto.getPush_type() == 2) {
                            PushUtil.pushIosMember(tdHtMemberDto.getPush_token(), "您的订单已被揽收.", "3", "default", tdHtMemberDto.getApp_version());
                        }

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("exp_ord_amount", tdHtExpressOrderDto.getExp_ord_amount());
                        if(tdHtExpressOrderDto.getDoor_end_time().getTime() < date){
                            map.put("exp_ord_gratuity", 0.00);
                        }else {
                            map.put("exp_ord_gratuity", tdHtExpressOrderDto.getExp_ord_gratuity());
                        }

                        retInfo.setMark("0");
                        retInfo.setTip("揽件成功.");
                        retInfo.setObj(0.00);
                    } else {
                        boolean is_ok = true;
                        if (tdHtExpressOrderCollectDtoList.size() == 0) {
                            logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "出现快件为空：" + takeOrderDto.getExp_ord_id());
                            is_ok = false;
                            retInfo.setMark("1");
                            retInfo.setTip("快件不能为空.");
                        }

                        for (int i = 0; i < tdHtExpressOrderCollectDtoList.size(); i++) {
                            TdHtExpressOrderCollectDto collectDtoOne = tdHtExpressOrderCollectDtoList.get(i);
                            if (collectDtoOne.getHt_number() == null || "".equals(collectDtoOne.getHt_number())) {
                                is_ok = false;
                                retInfo.setMark("1");
                                retInfo.setTip("订单号不能为空.");
                                break;
                            } else {
                                TdHtExpressOrderCollectDto collectDto = tdHtExpressCollectRepository.findExpOrdCltByHtNum(collectDtoOne);
                                TdHtExpressOrderCollectDto isHaveHt = tdHtExpressCollectRepository.findTdHtExpressCollectById(collectDtoOne.getExp_ord_clt_id());
                                if("".equals(isHaveHt.getHt_number())){
                                    //用户寄件时未输入厚通单号
                                    TsHtOpenNumberDto tsHtOpenNumberDto = tsHtOpenNumberRepository.findOpenNumberByHtNumber(collectDtoOne.getHt_number());
                                    if (tsHtOpenNumberDto == null) {
                                        is_ok = false;
                                        retInfo.setMark("1");
                                        retInfo.setTip("订单号[" + collectDtoOne.getHt_number() + "]不正确.");
                                        break;
                                    } else {
                                        if (tsHtOpenNumberDto.getHt_open_state() == 1) {
                                            is_ok = false;
                                            retInfo.setMark("1");
                                            retInfo.setTip("订单号[" + collectDtoOne.getHt_number() + "]已使用.");
                                            break;
                                        } else {
                                            for (int j = i + 1; j < tdHtExpressOrderCollectDtoList.size(); j++) {
                                                TdHtExpressOrderCollectDto collectDtoTwo = tdHtExpressOrderCollectDtoList.get(j);
                                                if (collectDtoOne.getHt_number().equals(collectDtoTwo.getHt_number())) {
                                                    is_ok = false;
                                                    retInfo.setMark("1");
                                                    retInfo.setTip("订单号[" + collectDtoOne.getHt_number() + "]重复.");
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                if(!"".equals(isHaveHt.getHt_number()) && collectDto == null){
                                    //用户寄件时输入厚通单号与揽件输入的厚通单号不同
                                    is_ok = false;
                                    retInfo.setMark("1");
                                    retInfo.setTip("用户寄件时已输入厚通单号.");
                                    break;
                                }
                            }
                        }

                        if (is_ok) {

                            if(tdHtExpressOrderDto.getMemb_type() == 1 && takeOrderDto.getAdjusted_amount().doubleValue() > 0){
                                tdHtExpressOrderDto.setCoupon_amount_type(1);

                                TdHtCoupon tdHtCoupon = new TdHtCoupon();
                                tdHtCoupon.setCou_amount(takeOrderDto.getAdjusted_amount());
                                tdHtCoupon.setCou_limit_amount(new BigDecimal(0));
                                tdHtCoupon.setCou_limit_time(new Timestamp(System.currentTimeMillis() + 20 * 24 * 60 * 60 * 1000));
                                tdHtCoupon.setCou_discount(new BigDecimal(1));
                                tdHtCoupon.setCou_receive_time(new Timestamp(System.currentTimeMillis()));
                                tdHtCoupon.setMemb_id(tdHtExpressOrderDto.getMemb_id());
                                tdHtCoupon.setCou_name(takeOrderDto.getAdjusted_amount().intValue() + "元专属优惠券");
                                tdHtCoupon.setState(0);
                                tdHtCoupon.setCou_user_time(tdHtCoupon.getCou_receive_time());
                                tdHtCoupon.setExp_ord_id(0);
                                tdHtCoupon.setRegion_id(0);
                                tdHtCoupon.setMemb_type(0);
                                tdHtCoupon.setExpress_id(0);
                                tdHtCoupon.setLimit_exp_ord_id(tdHtExpressOrderDto.getExp_ord_id());

                                tdHtCouponRepository.insertMembCoupon(tdHtCoupon);
                            }else {
                                tdHtExpressOrderDto.setCoupon_amount_type(0);
                            }

                            BigDecimal weight = new BigDecimal(0);
                            BigDecimal expressPrice = new BigDecimal(0);
                            BigDecimal totalPrice = new BigDecimal(0);
                            BigDecimal discountPrice = new BigDecimal(0);

                            for (int i = 0; i < tdHtExpressOrderCollectDtoList.size(); i++) {
                                TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressOrderCollectDtoList.get(i);
                                if (tdHtExpressOrderCollectDto.getExp_ord_clt_height() >= courierDto.getSubsidy_weight().doubleValue()) {
                                    weight_bonus = weight_bonus.add(courierDto.getSubsidy_bonus());
                                }
                                weight = weight.add(new BigDecimal(tdHtExpressOrderCollectDto.getExp_ord_clt_height()));
                                expressPrice = expressPrice.add(tdHtExpressOrderCollectDto.getExpress_price());
                                totalPrice = totalPrice.add(tdHtExpressOrderCollectDto.getTotal_amount());
                                discountPrice = discountPrice.add(tdHtExpressOrderCollectDto.getDiscount_amount());
                                tdHtExpressOrderCollectDto.setExp_ord_clt_state(1);
                                tdHtExpressOrderCollectDto.setExpress_number("");
                                tdHtExpressOrderCollectDto.setCoupon_amount_type(tdHtExpressOrderDto.getCoupon_amount_type());
                                if (tdHtExpressOrderCollectDto.getHt_number() != null && !"".equals(tdHtExpressOrderCollectDto.getHt_number().trim())) {
                                    TsHtOpenNumberDto tsHtOpenNumberDto = new TsHtOpenNumberDto();
                                    tsHtOpenNumberDto.setAgent_id(tdHtAgentDto.getAgent_id());
                                    tsHtOpenNumberDto.setHt_number(tdHtExpressOrderCollectDto.getHt_number());
                                    tsHtOpenNumberDto.setHt_open_state(1);
                                    tsHtOpenNumberRepository.updateOpenNumberStateByOpenId(tsHtOpenNumberDto);
                                }
                                tdHtExpressCollectRepository.takeOrderCollect(tdHtExpressOrderCollectDto);
                            }
                            tdHtExpressOrderDto.setExp_ord_weight(weight.doubleValue());
                            tdHtExpressOrderDto.setExp_ord_amount(expressPrice.doubleValue());
                            tdHtExpressOrderDto.setTotal_amount(totalPrice);
                            tdHtExpressOrderDto.setDiscount_amount(discountPrice);
                            tdHtExpressOrderDto.setCollect_time(new Date(date));
                            tdHtExpressOrderDto.setExp_ord_state(3);
                            tdHtExpressOrderDto.setExp_ord_type(courierDto.getEoa_type());
                            tdHtExpressOrderDto.setAdjusted_amount(takeOrderDto.getAdjusted_amount());
                            tdHtExpressOrderRepository.takeOrder(tdHtExpressOrderDto);

                            PushDto pushDto = tdHtMemberRepository.findPushInfoByMembId(tdHtExpressOrderDto.getMemb_id());

                            if (pushDto.getPush_type() == 1) {
                                PushUtil.pushAndroidMember(pushDto.getPush_token(), "您的订单已被揽收,请尽快支付.", "3", "", pushDto.getApp_version());
                            } else if (pushDto.getPush_type() == 2) {
                                PushUtil.pushIosMember(pushDto.getPush_token(), "您的订单已被揽收,请尽快支付.", "3", "default", pushDto.getApp_version());
                            }

                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("exp_ord_amount", tdHtExpressOrderDto.getExp_ord_amount());
                            if(tdHtExpressOrderDto.getDoor_end_time().getTime() < date){
                                map.put("exp_ord_gratuity", 0.00);
                            }else {
                                map.put("exp_ord_gratuity", tdHtExpressOrderDto.getExp_ord_gratuity());
                            }

                            retInfo.setMark("0");
                            retInfo.setTip("揽件成功.");
                            retInfo.setObj(map);
                        }
                    }
                    if (weight_bonus.intValue() > 0) {
                        tdHtAgentFoundChangeDto.setAfchg_change_amount(weight_bonus);
                        tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(date));
                        tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                        tdHtAgentFoundChangeDto.setAfchg_type(1);
                        tdHtAgentFoundChangeDto.setAfchg_state(2);
                        tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub());
                        tdHtAgentFoundChangeDto.setAfchg_name("重货补贴");
                        tdHtAgentFoundChangeDto.setAgent_id(agentDto.getAgent_id());
                        tdHtAgentFoundChangeDto.setAfchg_front_amount(agentDto.getAgent_balance());
                        tdHtAgentFoundChangeDto.setAfchg_back_amount(agentDto.getAgent_balance().add(weight_bonus));
                        tdHtAgentFoundChangeDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                        tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);
                    }
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
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 查询待入库列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo waitForStorage(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":waitForStorage:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            Page<WaitStorageOrderDto> page = new Page<WaitStorageOrderDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setId(tdHtAgentDto.getAgent_id());
            pageDto.setLimit_str(page.getLimitCriterion());
            int count = tdHtExpressOrderRepository.findWaitStorgeOrderCount(tdHtAgentDto.getAgent_id());
            List<WaitStorageOrderDto> waitStorageOrderDtoList = tdHtExpressOrderRepository.findWaitStorgeOrder(pageDto);
            page.setTotalCount(count);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            for (int i = 0; i < waitStorageOrderDtoList.size(); i++) {
                WaitStorageOrderDto waitStorageOrderDto = waitStorageOrderDtoList.get(i);
                String area = dicts.get(String.valueOf(waitStorageOrderDto.getAdd_region())).getCode_name();
                waitStorageOrderDto.setAdd_detail_address(area + waitStorageOrderDto.getAdd_detail_address());
                String expressLogo = dicts.get(String.valueOf(waitStorageOrderDto.getExpress_id())).getDict_img();
                waitStorageOrderDto.setExpress_logo(expressLogo);

                if(waitStorageOrderDto.getDoor_end_time().getTime() < waitStorageOrderDto.getCollect_time().getTime()){
                    waitStorageOrderDto.setExp_ord_gratuity(new BigDecimal(0));
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", waitStorageOrderDtoList);
            map.put("total_pages", page.getTotalPages());
            map.put("page_no", page.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("待入库数据查询成功.");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询待入库详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo waitStorageDetail(int orderId) {
        String logInfo = this.getClass().getName() + ":waitForStorage:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            WaitStorageOrdDetailDto waitStorageOrdDetailDto = tdHtExpressOrderRepository.findStorageOrdDetail(orderId);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            String expressLogo = dicts.get(String.valueOf(waitStorageOrdDetailDto.getExpress_id())).getDict_img();
            waitStorageOrdDetailDto.setExpress_logo(expressLogo);
            String region = dicts.get(String.valueOf(waitStorageOrdDetailDto.getAdd_region())).getCode_name();
            waitStorageOrdDetailDto.setAdd_detail_address(region + waitStorageOrdDetailDto.getAdd_detail_address());

            if (waitStorageOrdDetailDto.getAdd_mobile_phone() == null || "".equals(waitStorageOrdDetailDto.getAdd_mobile_phone().trim())) {
                waitStorageOrdDetailDto.setAdd_mobile_phone(waitStorageOrdDetailDto.getAdd_telephone());
            }

            List<WaitStorageCollectDto> waitStorageCollectDtoList = tdHtExpressCollectRepository.findWaitStorageCollectDetail(orderId);
            for (int i = 0; i < waitStorageCollectDtoList.size(); i++) {
                WaitStorageCollectDto waitStorageCollectDto = waitStorageCollectDtoList.get(i);
                String address = dicts.get(String.valueOf(waitStorageCollectDto.getAdd_province())).getCode_name();
                address = address + dicts.get(String.valueOf(waitStorageCollectDto.getAdd_city())).getCode_name();
                address = address + dicts.get(String.valueOf(waitStorageCollectDto.getAdd_region())).getCode_name();
                waitStorageCollectDto.setAdd_detail_address(address + waitStorageCollectDto.getAdd_detail_address());

                if (waitStorageCollectDto.getAdd_mobile_phone() == null || "".equals(waitStorageCollectDto.getAdd_mobile_phone().trim())) {
                    waitStorageCollectDto.setAdd_mobile_phone(waitStorageCollectDto.getAdd_telephone());
                }
            }
            waitStorageOrdDetailDto.setCollects(waitStorageCollectDtoList);

            retInfo.setMark("0");
            retInfo.setTip("待入库数据查询成功.");
            retInfo.setObj(waitStorageOrdDetailDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param waitStorageOrdDetailDto
     * @param token
     * @return RetInfo
     * @Purpose 入库
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo storageExpressOrder(WaitStorageOrdDetailDto waitStorageOrdDetailDto, String token) {
        String logInfo = this.getClass().getName() + ":storageExpressOrder:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);

            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(tdHtAgentDto.getAgent_id());
            if (agentDto.getAgent_state() != 4) {
                retInfo.setMark("1");
                retInfo.setTip("您已下班.");
            } else {
                if (waitStorageOrdDetailDto.getExp_ord_type() == 2) {
                    List<WaitStorageCollectDto> collects = waitStorageOrdDetailDto.getCollects();
                    boolean is_ok = true;
                    for (int i = 0; i < collects.size(); i++) {
                        WaitStorageCollectDto collectDtoOne = collects.get(i);
                        if (collectDtoOne.getExpress_number() == null || "".equals(collectDtoOne.getExpress_number())) {
                            is_ok = false;
                            retInfo.setMark("1");
                            retInfo.setTip("运单号不能为空.");
                            break;
                        } else {
                            for (int j = i + 1; j < collects.size(); j++) {
                                WaitStorageCollectDto collectDtoTwo = collects.get(j);
                                if (collectDtoOne.getExpress_number().equals(collectDtoTwo.getExpress_number())) {
                                    is_ok = false;
                                    retInfo.setMark("1");
                                    retInfo.setTip("运单号[" + collectDtoOne.getExpress_number() + "]重复");
                                    break;
                                }
                            }
                        }
                    }

                    if (is_ok) {
                        TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(waitStorageOrdDetailDto.getExp_ord_id());
                        tdHtExpressOrderDto.setExp_ord_id(waitStorageOrdDetailDto.getExp_ord_id());
                        tdHtExpressOrderDto.setStorge_time(new Date());
                        tdHtExpressOrderDto.setExp_ord_state(5);
                        tdHtExpressOrderRepository.storageExpressOrder(tdHtExpressOrderDto);

                        PushDto pushDto = tdHtMemberRepository.findPushInfoByMembId(tdHtExpressOrderDto.getMemb_id());

                        for (int i = 0; i < collects.size(); i++) {
                            WaitStorageCollectDto collectDto = collects.get(i);
                            tdHtExpressCollectRepository.storageExpressCollect(collectDto);

                            if (pushDto.getPush_type() == 1) {
                                PushUtil.pushAndroidMember(pushDto.getPush_token(), "您的订单已入库,运单号：" + collectDto.getExpress_number() + ".", "6", "", pushDto.getApp_version());
                            } else if (pushDto.getPush_type() == 2) {
                                PushUtil.pushIosMember(pushDto.getPush_token(), "您的订单已入库,运单号：" + collectDto.getExpress_number() + ".", "6", "default", pushDto.getApp_version());
                            }
                        }

                        retInfo.setMark("0");
                        retInfo.setTip("入库成功.");
                    }
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("您选择的不是纸质面单.");
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
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 分页查询已完成订单
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findFinishOrder(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findFinishOrder:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            Page<FinishOrderDto> page = new Page<FinishOrderDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setId(tdHtAgentDto.getAgent_id());
            pageDto.setLimit_str(page.getLimitCriterion());
            int count = tdHtExpressOrderRepository.findFinishOrderCount(tdHtAgentDto.getAgent_id());
            List<FinishOrderDto> finishOrderDtoList = tdHtExpressOrderRepository.findFinishOrder(pageDto);
            page.setTotalCount(count);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            for (int i = 0; i < finishOrderDtoList.size(); i++) {
                FinishOrderDto finishOrderDto = finishOrderDtoList.get(i);
                String area = dicts.get(String.valueOf(finishOrderDto.getAdd_region())).getCode_name();
                finishOrderDto.setAdd_detail_address(area + finishOrderDto.getAdd_detail_address());
                String expressLogo = dicts.get(String.valueOf(finishOrderDto.getExpress_id())).getDict_img();
                finishOrderDto.setExpress_logo(expressLogo);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", finishOrderDtoList);
            map.put("total_pages", page.getTotalPages());
            map.put("page_no", page.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("已完成数据查询成功.");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询已完成订单详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findFinishDetail(int orderId) {
        String logInfo = this.getClass().getName() + ":findFinishDetail:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            FinishOrderDetailDto finishOrdDetail = tdHtExpressOrderRepository.findFinishOrdDetail(orderId);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            String expressLogo = dicts.get(String.valueOf(finishOrdDetail.getExpress_id())).getDict_img();
            finishOrdDetail.setExpress_logo(expressLogo);
            String region = dicts.get(String.valueOf(finishOrdDetail.getAdd_region())).getCode_name();
            finishOrdDetail.setAdd_detail_address(region + finishOrdDetail.getAdd_detail_address());

            if (finishOrdDetail.getAdd_mobile_phone() == null || "".equals(finishOrdDetail.getAdd_mobile_phone().trim())) {
                finishOrdDetail.setAdd_mobile_phone(finishOrdDetail.getAdd_telephone());
            }

            List<FinishCollectDetailDto> finishCollectDetailDtoList = tdHtExpressCollectRepository.findFinishCollectDetail(orderId);
            for (int i = 0; i < finishCollectDetailDtoList.size(); i++) {
                FinishCollectDetailDto finishCollectDetailDto = finishCollectDetailDtoList.get(i);
                String address = dicts.get(String.valueOf(finishCollectDetailDto.getAdd_province())).getCode_name();
                address = address + dicts.get(String.valueOf(finishCollectDetailDto.getAdd_city())).getCode_name();
                address = address + dicts.get(String.valueOf(finishCollectDetailDto.getAdd_region())).getCode_name();
                finishCollectDetailDto.setAdd_detail_address(address + finishCollectDetailDto.getAdd_detail_address());

                if (finishCollectDetailDto.getAdd_mobile_phone() == null || "".equals(finishCollectDetailDto.getAdd_mobile_phone().trim())) {
                    finishCollectDetailDto.setAdd_mobile_phone(finishCollectDetailDto.getAdd_telephone());
                }
            }
            finishOrdDetail.setCollects(finishCollectDetailDtoList);

            retInfo.setMark("0");
            retInfo.setTip("已完成数据查询成功.");
            retInfo.setObj(finishOrdDetail);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 用户修改快递公司
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo modifyExpressCompany(TdHtExpressOrderDto tdHtExpressOrderDto) {
        String logInfo = this.getClass().getName() + ":modifyExpressCompany:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        retInfo.setMark("0");
        try {
            TdHtExpressOrderDto orderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(tdHtExpressOrderDto.getExp_ord_id());
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(orderDto.getMemb_id());
            if (orderDto.getExp_ord_state() < 4) {
                if (tdHtExpressOrderDto.getExpress_id() == orderDto.getExpress_id()) {
                    retInfo.setMark("1");
                    retInfo.setTip("您没有更改快递公司.");
                } else {
                    Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
                    List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(orderDto.getAdd_region());
                    boolean is_ok = false;
                    for (int i = 0; i < expressCompanyList.size(); i++) {
                        TsHtDictDto tsHtDictDto = expressCompanyList.get(i);
                        if (tsHtDictDto.getDict_id() == tdHtExpressOrderDto.getExpress_id()) {
                            is_ok = true;
                        }
                    }
                    if (is_ok) {
                        List<TdHtExpressOrderCollectDto> collectDtoList = tdHtExpressCollectRepository.findExpressCollectByOrder(orderDto.getExp_ord_id());
                        for (int i = 0; i < collectDtoList.size(); i++) {
                            TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);
                            collectDto.setExp_ord_clt_number(CommonUtil.getOrderNub());
                        }
                        //筛选订单（判断是否可以寄件）
                        ExpressScreenUtil expressScreenUtil = new ExpressScreenUtil();
                        orderDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                        retInfo = expressScreenUtil.expressScreen(orderDto, collectDtoList);

                        if ("0".equals(retInfo.getMark())) {
                            BigDecimal expressPrice = new BigDecimal(0);
                            BigDecimal totalPrice = new BigDecimal(0);
                            BigDecimal discountPrice = new BigDecimal(0);
                            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                            for (int i = 0; i < collectDtoList.size(); i++) {
                                TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);

                                BigDecimal weight = new BigDecimal(collectDto.getExp_ord_clt_height()).setScale(0, BigDecimal.ROUND_UP);
                                BigDecimal price = expressScreenUtil.reckonExpressPrice(orderDto.getExpress_id(), weight, orderDto.getAdd_province(), collectDto.getAdd_province());

                                collectDto.setTotal_amount(price);
                                totalPrice = totalPrice.add(price);
                                if (member.getMemb_role() == 1) {
                                    if (member.getMemb_discount().doubleValue() < 1) {
                                        price = price.multiply(member.getMemb_discount());
                                    }
                                } else if (member.getMemb_role() == 2) {
                                    price = price.multiply(member.getMemb_discount());
                                }

                                expressPrice = expressPrice.add(price);
                                collectDto.setExpress_price(price);
                                collectDto.setDiscount_amount(collectDto.getTotal_amount().subtract(collectDto.getExpress_price()));
                                discountPrice = discountPrice.add(collectDto.getDiscount_amount());
                                collectDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                                collectDto.setExpress_name(dicts.get("" + tdHtExpressOrderDto.getExpress_id()).getCode_name());
                                tdHtExpressCollectRepository.updateColExpComPrice(collectDto);
                            }

                            orderDto.setTotal_amount(totalPrice);
                            orderDto.setExp_ord_amount(expressPrice.doubleValue());
                            orderDto.setDiscount_amount(discountPrice);

                            Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
                            String key = orderDto.getExpress_id() + "#" + orderDto.getAdd_region();
                            CourierDto courierDto = courierMap.get(key);
                            orderDto.setExp_ord_type(courierDto.getEoa_type());

                            tdHtExpressOrderRepository.updateOrdExpComPrice(orderDto);

                            retInfo.setMark("0");
                            retInfo.setTip("快递公司修改成功.");
                        } else {
                            retInfo.setMark("1");
                            retInfo.setTip("该快递公司无法承运，请选择其他快递.");
                        }
                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("该地区尚未开通该快递公司.");
                    }
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("快件已入库.");
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
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 月结用户修改厚通订单号
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo modifyExpressHtNumber(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto, String token) {
        String logInfo = this.getClass().getName() + ":modifyExpressHtNumber:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        retInfo.setMark("0");
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());

            if (tdHtMemberDto.getMemb_discount().doubleValue() < 1) {
                if (tdHtExpressOrderCollectDto.getHt_number() == null || "".equals(tdHtExpressOrderCollectDto.getHt_number().trim())) {
                    retInfo.setMark("1");
                    retInfo.setTip("厚通订单号不能为空.");
                } else {
                    TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findExpOrdByCltId(tdHtExpressOrderCollectDto.getExp_ord_clt_id());
                    if (tdHtExpressOrderDto != null) {
                        if (tdHtExpressOrderDto.getExp_ord_state() > 0 && tdHtExpressOrderDto.getExp_ord_state() < 3) {
                            TsHtOpenNumberDto tsHtOpenNumberDto = tsHtOpenNumberRepository.findOpenNumberByHtNumber(tdHtExpressOrderCollectDto.getHt_number());
                            if (tsHtOpenNumberDto == null) {
                                retInfo.setMark("1");
                                retInfo.setTip("订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]不正确.");
                            } else {
                                if (tsHtOpenNumberDto.getHt_open_state() == 1) {
                                    retInfo.setMark("1");
                                    retInfo.setTip("订单号[" + tdHtExpressOrderCollectDto.getHt_number() + "]已使用.");
                                } else {
                                    tdHtExpressCollectRepository.updateExpOrdCltHtNumber(tdHtExpressOrderCollectDto);

                                    tsHtOpenNumberDto.setAgent_id(tdHtExpressOrderDto.getAgent_id());
                                    tsHtOpenNumberDto.setHt_number(tdHtExpressOrderCollectDto.getHt_number());
                                    tsHtOpenNumberDto.setHt_open_state(1);
                                    tsHtOpenNumberRepository.updateOpenNumberStateByOpenId(tsHtOpenNumberDto);
                                    retInfo.setMark("0");
                                    retInfo.setTip("修改成功.");
                                }
                            }
                        } else {
                            retInfo.setMark("1");
                            retInfo.setTip("您的快件已被揽收.");
                        }
                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("订单不存在.");
                    }
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("您无法修改厚通单号.");
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
     * @param pageDto
     * @return RetInfo
     * @Purpose 分页查询用户未开票的订单列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findMembCanInvoiceExpOrd(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findMembCanInvoiceExpOrd:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        retInfo.setMark("0");
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtMemberDto.getMemb_id());
            Page<TdHtExpressOrderDto> expressOrderDtoPage = new Page<TdHtExpressOrderDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setId(member.getMemb_id());
            pageDto.setLimit_str(expressOrderDtoPage.getLimitCriterion());
            int count = tdHtExpressOrderRepository.findMembCanInvoiceExpOrdSize(member.getMemb_id());
            expressOrderDtoPage.setTotalCount(count);
            List<TdHtExpressOrderDto> expressOrderDtoList = tdHtExpressOrderRepository.findMembCanInvoiceExpOrdPage(pageDto);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
            List<Map<String, Object>> canInvoiceExpOrdMapList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < expressOrderDtoList.size(); i++) {
                TdHtExpressOrderDto tdHtExpressOrderDto = expressOrderDtoList.get(i);
                Map<String, Object> canInvoiceExpOrdMap = new HashMap<String, Object>();
                canInvoiceExpOrdMap.put("exp_ord_id", tdHtExpressOrderDto.getExp_ord_id());
                canInvoiceExpOrdMap.put("exp_ord_time", tdHtExpressOrderDto.getExp_ord_time());
                canInvoiceExpOrdMap.put("exp_ord_pay_amount", tdHtExpressOrderDto.getExp_ord_pay_amount());
                String address = dicts.get("" + tdHtExpressOrderDto.getAdd_province()).getCode_name() +
                        dicts.get("" + tdHtExpressOrderDto.getAdd_city()).getCode_name() +
                        dicts.get("" + tdHtExpressOrderDto.getAdd_region()).getCode_name() +
                        tdHtExpressOrderDto.getAdd_detail_address();
                canInvoiceExpOrdMap.put("add_detail_address", address);
                canInvoiceExpOrdMapList.add(canInvoiceExpOrdMap);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", canInvoiceExpOrdMapList);
            map.put("total_pages", expressOrderDtoPage.getTotalPages());
            map.put("page_no", expressOrderDtoPage.getPageNo());
            map.put("postage", dataConfigMap.get(Constant.MEMB_INVOICE_POSTAGE));
            map.put("free_amount", dataConfigMap.get(Constant.MEMB_INVOICE_FREE_AMOUNT));

            retInfo.setMark("0");
            retInfo.setTip("已完成数据查询成功.");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
