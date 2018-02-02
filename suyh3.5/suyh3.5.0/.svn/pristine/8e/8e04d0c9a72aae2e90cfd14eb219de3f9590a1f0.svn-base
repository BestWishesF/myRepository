package cn.hotol.app.service.member.invoice;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.express.number.TsHtOpenNumberDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.member.invoice.InvoiceExamineDto;
import cn.hotol.app.bean.dto.member.invoice.TdHtMembInvoiceDto;
import cn.hotol.app.bean.dto.member.invoice.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.ExpressScreenUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017-03-27.
 */
public class InvoiceServiceImpl implements InvoiceService {

    private static Logger logger = Logger.getLogger(InvoiceServiceImpl.class);

    private TdHtMembInvoiceHistoryRepository tdHtMembInvoiceHistoryRepository;
    private TdHtMembInvoiceRepository tdHtMembInvoiceRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    private TdHtMemberRepository tdHtMemberRepository;
    private TdHtMembAddressRepository tdHtMembAddressRepository;
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TsHtOpenNumberRepository tsHtOpenNumberRepository;

    /**
     * @Purpose   分页查询用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     memb_id  用户id
     * @return    RetInfo  查询结果
     */
    @Override
    public RetInfo findInvoiceList(int currentPage, int pageSize, int memb_id) {
        String logInfo = this.getClass().getName() + ":findInvoiceList:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("memb_id", memb_id);
            //获取快递公司
            List<TsHtDictDto> openRegionExpCompany = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.OPEN_REGION_EXP_COMPANY);
            params.put("express_company", openRegionExpCompany);
            //查询用户开票记录条数
            int totalRecord = tdHtMembInvoiceHistoryRepository.findMembInvHisSize(memb_id);
            //判断用户开票记录条数是否大于0
            if (totalRecord > 0) {
                //创建分页查询条件map
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                //分页查询用户开票记录
                List<TdHtMembInvoiceHistoryDto> invoiceHistoryDtoList = tdHtMembInvoiceHistoryRepository.findMembInvHisPage(map);
                //取缓存中的省市区数据
                Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                //获取省市区已经快递公司名称
                for (int i = 0; i < invoiceHistoryDtoList.size(); i ++) {
                    TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto = invoiceHistoryDtoList.get(i);
                    String province = dicts.get("" + tdHtMembInvoiceHistoryDto.getAdd_province()).getCode_name();
                    String city = dicts.get("" + tdHtMembInvoiceHistoryDto.getAdd_city()).getCode_name();
                    String region = dicts.get("" + tdHtMembInvoiceHistoryDto.getAdd_region()).getCode_name();
                    tdHtMembInvoiceHistoryDto.setAdd_detail_address(province + city + region + tdHtMembInvoiceHistoryDto.getAdd_detail_address());
                    if(tdHtMembInvoiceHistoryDto.getState() == 2){
                        String express_name = dicts.get("" + tdHtMembInvoiceHistoryDto.getMemb_ivc_express_id()).getCode_name();
                        tdHtMembInvoiceHistoryDto.setExpress_name(express_name);
                    }
                }
                //查询结果放入map
                map.put("currentPage", currentPage);
                map.put("invoices", invoiceHistoryDtoList);
                //为返回类赋值
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                //为返回类赋值
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
     * @Purpose   按条件分页查询用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     memb_id  用户id
     * @param     mobile  收件人手机号
     * @param     state  发票状态
     * @return    RetInfo  查询结果
     */
    @Override
    public RetInfo searchInvoiceList(int currentPage, int pageSize, int memb_id, String mobile, String state) {
        String logInfo = this.getClass().getName() + ":searchInvoiceList:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("add_mobile_phone", mobile);
            if (state == null || "".equals(state)){
                params.put("state", "");
            }else {
                params.put("state", state);
            }
            params.put("memb_id", memb_id);
            //获取快递公司
            List<TsHtDictDto> openRegionExpCompany = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.OPEN_REGION_EXP_COMPANY);
            params.put("express_company", openRegionExpCompany);
            int totalRecord = tdHtMembInvoiceHistoryRepository.searchMembInvHisSize(params);
            //判断用户开票记录条数是否大于0
            if (totalRecord > 0) {
                //创建分页查询条件map
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                //分页查询用户开票记录
                List<TdHtMembInvoiceHistoryDto> invoiceHistoryDtoList = tdHtMembInvoiceHistoryRepository.searchMembInvHisPage(map);
                //取缓存中的省市区数据
                Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                //获取省市区已经快递公司名称
                for (int i = 0; i < invoiceHistoryDtoList.size(); i ++) {
                    TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto = invoiceHistoryDtoList.get(i);
                    String province = dicts.get("" + tdHtMembInvoiceHistoryDto.getAdd_province()).getCode_name();
                    String city = dicts.get("" + tdHtMembInvoiceHistoryDto.getAdd_city()).getCode_name();
                    String region = dicts.get("" + tdHtMembInvoiceHistoryDto.getAdd_region()).getCode_name();
                    tdHtMembInvoiceHistoryDto.setAdd_detail_address(province + city + region + tdHtMembInvoiceHistoryDto.getAdd_detail_address());
                    if(tdHtMembInvoiceHistoryDto.getState() == 2){
                        String express_name = dicts.get("" + tdHtMembInvoiceHistoryDto.getMemb_ivc_express_id()).getCode_name();
                        tdHtMembInvoiceHistoryDto.setExpress_name(express_name);
                    }
                }
                //查询结果放入map
                map.put("currentPage", currentPage);
                map.put("invoices", invoiceHistoryDtoList);
                //为返回类赋值
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                //为返回类赋值
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
     * @Purpose   审核用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-29
     * @param     invoiceExamineDto  审核内容
     * @return    RetInfo  查询结果
     */
    @Override
    public RetInfo invoiceExamine(InvoiceExamineDto invoiceExamineDto) {
        String logInfo = this.getClass().getName() + ":invoiceExamine:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //查询开票信息
            TdHtMembInvoiceDto tdHtMembInvoiceDto = tdHtMembInvoiceRepository.findMembInvoiceById(invoiceExamineDto.getMemb_ivc_id());
            //查询开票历史记录
            TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto = tdHtMembInvoiceHistoryRepository.findIvcHisByIvcId(invoiceExamineDto.getMemb_ivc_id());
            if (invoiceExamineDto.getState() == 2){
                //审核通过
                tdHtMembInvoiceHistoryDto.setState(invoiceExamineDto.getState());

                //查询发件用户是否存在
                TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMemberByMobile(invoiceExamineDto.getMobile());
                if(tdHtMemberDto == null){
                    //返回结果
                    retInfo.setMark("1");
                    retInfo.setTip("发件用户不存在.");
                }else {
                    boolean is_ok = false;
                    //取缓存中的省市区数据
                    Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                    //查询用户默认发件地址是否存在
                    TdHtMembAddressDto tdHtMembAddressDto = tdHtMembAddressRepository.findMembDefSendAddress(tdHtMemberDto.getMemb_id());
                    if(tdHtMembAddressDto == null){
                        //返回结果
                        retInfo.setMark("1");
                        retInfo.setTip("发件地址不存在.");
                    }else {
                        TsHtOpenNumberDto tsHtOpenNumberDto = tsHtOpenNumberRepository.findOpenNumberByHtNumber(invoiceExamineDto.getHt_number());
                        if (tsHtOpenNumberDto == null || tsHtOpenNumberDto.getHt_open_state() == 1) {
                            is_ok = true;
                            retInfo.setMark("1");
                            retInfo.setTip("厚通单号不正确.");
                        }

                        if(!is_ok && invoiceExamineDto.getType() == 1){
                            TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto = new TsHtExpressOpenNumberDto();
                            tsHtExpressOpenNumberDto.setExpress_id(invoiceExamineDto.getExpress_id());
                            tsHtExpressOpenNumberDto.setRegion_id(tdHtMembAddressDto.getAdd_city());
                            TsHtExpressOpenNumberDto numberDto = tsHtExpressOpenNumberRepository.findCanUseExpressNumber(tsHtExpressOpenNumberDto);
                            if(numberDto == null){
                                //返回结果
                                retInfo.setMark("1");
                                retInfo.setTip("该快递公司电子运单库已空.");
                                is_ok = true;
                            }else {
                                invoiceExamineDto.setExpress_number(numberDto.getExpress_number());

                                numberDto.setAgent_id(Constant.DEFAULT_AGENT_ID);
                                numberDto.setExp_open_state(1);
                                tsHtExpressOpenNumberRepository.updateExpressNumberState(numberDto);
                            }
                        }

                        if(!is_ok){
                            //创建发件订单数据
                            Date date = new Date(System.currentTimeMillis());
                            TdHtExpressOrderDto tdHtExpressOrderDto = new TdHtExpressOrderDto();
                            tdHtExpressOrderDto.setMemb_id(tdHtMemberDto.getMemb_id());
                            tdHtExpressOrderDto.setAgent_id(Constant.DEFAULT_AGENT_ID);
                            tdHtExpressOrderDto.setAdd_id(tdHtMembAddressDto.getAdd_id());
                            tdHtExpressOrderDto.setAdd_name(tdHtMembAddressDto.getAdd_name());
                            tdHtExpressOrderDto.setAdd_id_number("");
                            tdHtExpressOrderDto.setAdd_detail_address(tdHtMembAddressDto.getAdd_detail_address().replace(Constant.SENDER_ADDRESS_CONNECT, ""));
                            tdHtExpressOrderDto.setAdd_province(tdHtMembAddressDto.getAdd_province());
                            tdHtExpressOrderDto.setAdd_city(tdHtMembAddressDto.getAdd_city());
                            tdHtExpressOrderDto.setAdd_region(tdHtMembAddressDto.getAdd_region());
                            tdHtExpressOrderDto.setAdd_mobile_phone(tdHtMembAddressDto.getAdd_mobile_phone());
                            tdHtExpressOrderDto.setAdd_telephone(tdHtMembAddressDto.getAdd_telephone());
                            tdHtExpressOrderDto.setAdd_longitude(tdHtMembAddressDto.getAdd_longitude());
                            tdHtExpressOrderDto.setAdd_latitude(tdHtMembAddressDto.getAdd_latitude());
                            tdHtExpressOrderDto.setExpress_id(invoiceExamineDto.getExpress_id());
                            tdHtExpressOrderDto.setExp_ord_type(invoiceExamineDto.getType());
                            tdHtExpressOrderDto.setExp_ord_time(date);
                            tdHtExpressOrderDto.setExp_ord_taking_time(new Date(date.getTime() + 30 * 60 * 1000));
                            tdHtExpressOrderDto.setDoor_start_time(date);
                            tdHtExpressOrderDto.setDoor_end_time(new Date(date.getTime() + 2 * 60 * 60 * 1000));
                            tdHtExpressOrderDto.setCollect_time(new Date(date.getTime() + 30 * 60 * 1000));
                            tdHtExpressOrderDto.setPay_time(new Date(date.getTime() + 33 * 60 * 1000));
                            tdHtExpressOrderDto.setStorge_time(new Date(date.getTime() + 50 * 60 * 1000));
                            tdHtExpressOrderDto.setExp_ord_state(5);
                            tdHtExpressOrderDto.setExp_ord_size(1);
                            tdHtExpressOrderDto.setExp_ord_demand("");
                            tdHtExpressOrderDto.setExp_ord_number(CommonUtil.getOrderNub(date));
                            tdHtExpressOrderDto.setExp_ord_month(CommonUtil.getMonth());
                            tdHtExpressOrderDto.setExp_ord_gratuity(0);
                            tdHtExpressOrderDto.setAgent_note("");
                            tdHtExpressOrderDto.setDivide_id(0);
                            tdHtExpressOrderDto.setImport_marker(0);
                            tdHtExpressOrderDto.setExp_ord_pay_amount(0);
                            tdHtExpressOrderDto.setExp_ord_amount(0);
                            tdHtExpressOrderDto.setExp_ord_weight(1);

                            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = new TdHtExpressOrderCollectDto();
                            tdHtExpressOrderCollectDto.setExp_ord_category("文件");
                            tdHtExpressOrderCollectDto.setExp_ord_clt_height(tdHtExpressOrderDto.getExp_ord_weight());
                            tdHtExpressOrderCollectDto.setAdd_id(0);
                            tdHtExpressOrderCollectDto.setAdd_name(tdHtMembInvoiceDto.getAdd_name());
                            tdHtExpressOrderCollectDto.setAdd_mobile_phone(tdHtMembInvoiceDto.getAdd_mobile_phone());
                            tdHtExpressOrderCollectDto.setAdd_telephone("");
                            tdHtExpressOrderCollectDto.setAdd_detail_address(tdHtMembInvoiceDto.getAdd_detail_address());
                            tdHtExpressOrderCollectDto.setAdd_province(tdHtMembInvoiceDto.getAdd_province());
                            tdHtExpressOrderCollectDto.setAdd_city(tdHtMembInvoiceDto.getAdd_city());
                            tdHtExpressOrderCollectDto.setAdd_region(tdHtMembInvoiceDto.getAdd_region());
                            tdHtExpressOrderCollectDto.setAdd_longitude(tdHtMembInvoiceDto.getAdd_longitude());
                            tdHtExpressOrderCollectDto.setAdd_latitude(tdHtMembInvoiceDto.getAdd_latitude());
                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(3);
                            tdHtExpressOrderCollectDto.setExpress_number(invoiceExamineDto.getExpress_number());
                            tdHtExpressOrderCollectDto.setHt_number(invoiceExamineDto.getHt_number());
                            tdHtExpressOrderCollectDto.setSign_time(date);
                            tdHtExpressOrderCollectDto.setSend_time(date);
                            tdHtExpressOrderCollectDto.setDelivery_time(date);
                            tdHtExpressOrderCollectDto.setExpress_id(String.valueOf(invoiceExamineDto.getExpress_id()));
                            tdHtExpressOrderCollectDto.setCategory_id(11);
                            tdHtExpressOrderCollectDto.setExpress_name(dicts.get("" + invoiceExamineDto.getExpress_id()).getCode_name());
                            tdHtExpressOrderCollectDto.setExpress_price(new BigDecimal(0));
                            tdHtExpressOrderCollectDto.setExp_ord_clt_number(CommonUtil.getOrderNub(date));

                            ExpressScreenUtil expressScreenUtil = new ExpressScreenUtil();
                            List<TdHtExpressOrderCollectDto> tdHtExpressOrderCollectDtoList = new ArrayList<TdHtExpressOrderCollectDto>();
                            tdHtExpressOrderCollectDtoList.add(tdHtExpressOrderCollectDto);
                            retInfo = expressScreenUtil.expressScreen(tdHtExpressOrderDto, tdHtExpressOrderCollectDtoList);

                            if("0".equals(retInfo.getMark())){

                                tdHtExpressOrderRepository.insertExpressOrder(tdHtExpressOrderDto);

                                tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());

                                tdHtExpressCollectRepository.insertExpressOrderCollect(tdHtExpressOrderCollectDto);

                                tdHtMembInvoiceHistoryDto.setMemb_ivc_express_id(tdHtExpressOrderDto.getExpress_id());
                                tdHtMembInvoiceHistoryDto.setMemb_ivc_express_number(tdHtExpressOrderCollectDto.getExpress_number());

                                //修改开票历史记录
                                tdHtMembInvoiceHistoryRepository.updateIvcHisState(tdHtMembInvoiceHistoryDto);

                                //修改厚通单号状态
                                tsHtOpenNumberDto.setAgent_id(tdHtExpressOrderDto.getAgent_id());
                                tsHtOpenNumberDto.setHt_open_state(1);
                                tsHtOpenNumberRepository.updateOpenNumStateByNum(tsHtOpenNumberDto);

                                retInfo.setObj(tdHtExpressOrderDto);
                                retInfo.setMark("0");
                                retInfo.setTip("成功.");
                            }else {
                                //返回结果
                                retInfo.setMark("1");
                                retInfo.setTip("该快递公司无法承运.");
                            }
                        }
                    }
                }
            }else if(invoiceExamineDto.getState() == 3){
                //审核失败
                tdHtMembInvoiceHistoryDto.setState(invoiceExamineDto.getState());

                //修改开票历史记录
                tdHtMembInvoiceHistoryRepository.updateIvcHisState(tdHtMembInvoiceHistoryDto);

                //修改发件订单
                tdHtExpressOrderRepository.updateExpOrdIvcHis(tdHtMembInvoiceHistoryDto.getMemb_ivc_his_id());

                //返回结果
                retInfo.setMark("0");
                retInfo.setTip("成功.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtMembInvoiceHistoryRepository(TdHtMembInvoiceHistoryRepository tdHtMembInvoiceHistoryRepository) {
        this.tdHtMembInvoiceHistoryRepository = tdHtMembInvoiceHistoryRepository;
    }

    public void setTdHtMembInvoiceRepository(TdHtMembInvoiceRepository tdHtMembInvoiceRepository) {
        this.tdHtMembInvoiceRepository = tdHtMembInvoiceRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }

    public void setTdHtExpressCollectRepository(TdHtExpressCollectRepository tdHtExpressCollectRepository) {
        this.tdHtExpressCollectRepository = tdHtExpressCollectRepository;
    }

    public void setTdHtMemberRepository(TdHtMemberRepository tdHtMemberRepository) {
        this.tdHtMemberRepository = tdHtMemberRepository;
    }

    public void setTdHtMembAddressRepository(TdHtMembAddressRepository tdHtMembAddressRepository) {
        this.tdHtMembAddressRepository = tdHtMembAddressRepository;
    }

    public void setTsHtExpressOpenNumberRepository(TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository) {
        this.tsHtExpressOpenNumberRepository = tsHtExpressOpenNumberRepository;
    }

    public void setTsHtOpenNumberRepository(TsHtOpenNumberRepository tsHtOpenNumberRepository) {
        this.tsHtOpenNumberRepository = tsHtOpenNumberRepository;
    }
}
