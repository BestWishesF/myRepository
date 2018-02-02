package cn.hotol.app.service.express;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.express.*;
import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.location.*;
import cn.hotol.app.bean.dto.refund.TdHtMembRefundChangeDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lizhun on 2016/12/24.
 */
public class ExpressServiceImpl implements ExpressService {
    private static Logger logger = Logger.getLogger(ExpressServiceImpl.class);
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    private TdHtAgentRepository tdHtAgentRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtMemberRepository tdHtMemberRepository;
    private TdHtAdminDivideRepository tdHtAdminDivideRepository;
    private TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository;
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;
    private TdHtMembRefundChangeRepository tdHtMembRefundChangeRepository;

    @Override
    public RetInfo expressordPage(int exp_ord_state, int currentPage, int pageSize, int memb_type, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":expressordPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("exp_ord_state", exp_ord_state);
            params.put("memb_type", memb_type);

            params.put("add_province", tdHtAdminDto.getProvince_id());
            params.put("add_city", tdHtAdminDto.getCity_id());
            params.put("add_region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            int totalRecord = tdHtExpressOrderRepository.findExpressOrderByStateSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.putAll(params);
                List<TdHtExpressOrderDto> tdHtExpressOrderDtos = tdHtExpressOrderRepository.findExpressOrderByStatePage(map);

                for (int i = 0; i < tdHtExpressOrderDtos.size(); i++) {
                    TdHtAgentDto agent = tdHtAgentRepository.findAgentById(tdHtExpressOrderDtos.get(i).getAgent_id());
                    if (exp_ord_state > 1) {
                        if (agent != null) {
                            tdHtExpressOrderDtos.get(i).setAgent_name(agent.getAgent_name());
                            tdHtExpressOrderDtos.get(i).setAgent_phone(agent.getAgent_phone());
                        }
                    }
                    String province = dicts.get("" + tdHtExpressOrderDtos.get(i).getAdd_province()).getCode_name();
                    String city = dicts.get("" + tdHtExpressOrderDtos.get(i).getAdd_city()).getCode_name();
                    String area = dicts.get("" + tdHtExpressOrderDtos.get(i).getAdd_region()).getCode_name();
                    String address = province + city + area + tdHtExpressOrderDtos.get(i).getAdd_detail_address();
                    tdHtExpressOrderDtos.get(i).setAdd_detail_address(address);
                    tdHtExpressOrderDtos.get(i).setExpress_name(dicts.get("" + tdHtExpressOrderDtos.get(i).getExpress_id()).getCode_name());
                }

                map.put("currentPage", currentPage);
                map.put("expOrds", tdHtExpressOrderDtos);
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

    @Override
    public RetInfo expressCollectPage(int exp_ord_state, int exp_ord_id, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":expressCollectPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("exp_ord_state", exp_ord_state);
            params.put("exp_ord_id", exp_ord_id);
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(exp_ord_id);
            params.put("exp_ord_type", tdHtExpressOrderDto.getExp_ord_type());
            int totalRecord = tdHtExpressCollectRepository.findExpressCollectByExpOrdIdSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.putAll(params);
                List<TdHtExpressOrderCollectDto> tdHtExpressOrderCollectDtos = tdHtExpressCollectRepository.findExpressCollectByExpOrdIdPage(map);
                for (int i = 0; i < tdHtExpressOrderCollectDtos.size(); i++) {
                    String province = dicts.get("" + tdHtExpressOrderCollectDtos.get(i).getAdd_province()).getCode_name();
                    String city = dicts.get("" + tdHtExpressOrderCollectDtos.get(i).getAdd_city()).getCode_name();
                    String area = dicts.get("" + tdHtExpressOrderCollectDtos.get(i).getAdd_region()).getCode_name();
                    String address = province + city + area + tdHtExpressOrderCollectDtos.get(i).getAdd_detail_address();
                    tdHtExpressOrderCollectDtos.get(i).setAdd_detail_address(address);
                }
                map.put("currentPage", currentPage);
                map.put("expOrdCols", tdHtExpressOrderCollectDtos);

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
     * @param ht_number
     * @return RetInfo
     * @Purpose 根据厚通中转单号查询面单打印信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findPrintInfo(String ht_number) {
        String logInfo = this.getClass().getName() + ":findPrintInfo:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            if (!"".equals(ht_number.trim()) && ht_number != null) {
                TdHtExpressOrderCollectDto collectDto = tdHtExpressCollectRepository.findExpressCollectByHtNumber(ht_number);
                if (collectDto != null) {
                    TdHtExpressOrderDto orderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(collectDto.getExp_ord_id());
                    if (orderDto.getExp_ord_state() == 5) {
                        Map<String, Object> params = new HashMap<>();
                        Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);

                        String sendPro = dicts.get("" + orderDto.getAdd_province()).getCode_name();
                        String sendCity = dicts.get("" + orderDto.getAdd_city()).getCode_name();
                        String sendArea = dicts.get("" + orderDto.getAdd_region()).getCode_name();
                        String sendAddress = orderDto.getAdd_detail_address();

                        String sendMobile = orderDto.getAdd_mobile_phone();
                        if (sendMobile == null || "".equals(sendMobile.trim())) {
                            sendMobile = orderDto.getAdd_telephone();
                        }

                        String revPro = dicts.get("" + collectDto.getAdd_province()).getCode_name();
                        String revCity = dicts.get("" + collectDto.getAdd_city()).getCode_name();
                        String revArea = dicts.get("" + collectDto.getAdd_region()).getCode_name();
                        String revAddress = collectDto.getAdd_detail_address();

                        String revMobile = collectDto.getAdd_mobile_phone();
                        if (revMobile == null || "".equals(revMobile.trim())) {
                            revMobile = collectDto.getAdd_telephone();
                        }

                        collectDto.setExpress_name(dicts.get("" + collectDto.getExpress_id()).getCode_name());

                        params.put("express_id", collectDto.getExpress_id());
                        params.put("express_name", collectDto.getExpress_name());
                        params.put("express_number", collectDto.getExpress_number());
                        params.put("send_address", sendAddress);
                        params.put("send_pro", sendPro);
                        params.put("send_city", sendCity);
                        params.put("send_area", sendArea);
                        params.put("send_name", orderDto.getAdd_name());
                        params.put("send_mobile", sendMobile);
                        params.put("rev_address", revAddress);
                        params.put("rev_pro", revPro);
                        params.put("rev_city", revCity);
                        params.put("rev_area", revArea);
                        params.put("rev_name", collectDto.getAdd_name());
                        params.put("rev_mobile", revMobile);
                        params.put("ht_number", collectDto.getHt_number());
                        params.put("order_no", collectDto.getExp_ord_clt_number());
                        params.put("add_region", orderDto.getAdd_region());
                        params.put("weight", collectDto.getExp_ord_clt_height());
                        if (collectDto.getExp_ord_category() == null || "".equals(collectDto.getExp_ord_category().trim())) {
                            params.put("category", "日用品");
                        } else {
                            params.put("category", collectDto.getExp_ord_category());
                        }

                        retInfo.setMark("0");
                        retInfo.setTip("查询成功");
                        retInfo.setObj(params);
                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("该快件还没有入库，请先入库再进行打印订单");
                    }
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("该快件不存在，请核对");
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("请输入正确的厚通单号");
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
     * @param exp_ord_state
     * @param currentPage
     * @param pageSize
     * @param add_mobile_phone
     * @param ht_number
     * @param express_number
     * @return RetInfo
     * @Purpose 通过条件查询快递订单列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo searchList(int exp_ord_state, int currentPage, int pageSize, String add_mobile_phone, String ht_number, String express_number, int memb_type, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":searchList:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("exp_ord_state", exp_ord_state);
            params.put("add_mobile_phone", add_mobile_phone);
            params.put("ht_number", ht_number);
            params.put("express_number", express_number);
            params.put("memb_type", memb_type);

            params.put("add_province", tdHtAdminDto.getProvince_id());
            params.put("add_city", tdHtAdminDto.getCity_id());
            params.put("add_region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            int totalRecord = tdHtExpressOrderRepository.findExpressOrderBySearchSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.putAll(params);
                List<TdHtExpressOrderDto> tdHtExpressOrderDtos = tdHtExpressOrderRepository.findExpressOrderBySearchPage(map);

                for (int i = 0; i < tdHtExpressOrderDtos.size(); i++) {
                    TdHtAgentDto agent = tdHtAgentRepository.findAgentById(tdHtExpressOrderDtos.get(i).getAgent_id());
                    if (exp_ord_state > 1) {
                        if (agent != null) {
                            tdHtExpressOrderDtos.get(i).setAgent_name(agent.getAgent_name());
                            tdHtExpressOrderDtos.get(i).setAgent_phone(agent.getAgent_phone());
                        }
                    }
                    String province = dicts.get("" + tdHtExpressOrderDtos.get(i).getAdd_province()).getCode_name();
                    String city = dicts.get("" + tdHtExpressOrderDtos.get(i).getAdd_city()).getCode_name();
                    String area = dicts.get("" + tdHtExpressOrderDtos.get(i).getAdd_region()).getCode_name();
                    String address = province + city + area + tdHtExpressOrderDtos.get(i).getAdd_detail_address();
                    tdHtExpressOrderDtos.get(i).setAdd_detail_address(address);
                    tdHtExpressOrderDtos.get(i).setExpress_name(dicts.get("" + tdHtExpressOrderDtos.get(i).getExpress_id()).getCode_name());
                }

                map.put("currentPage", currentPage);
                map.put("expOrds", tdHtExpressOrderDtos);
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
     * @param exp_ord_id
     * @return RetInfo
     * @Purpose 通过订单id查询订单信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findExpOrd(Integer exp_ord_id) {
        String logInfo = this.getClass().getName() + ":findExpOrd:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map sendMap = new HashMap();
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(exp_ord_id);
            if (tdHtExpressOrderDto != null) {
                List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
                List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(tdHtExpressOrderDto.getAdd_province());
                List<AreaDto> areaDtos = tsHtDictRepository.findAreaByCity(tdHtExpressOrderDto.getAdd_city());

                sendMap.put("exp_ord_id", exp_ord_id);
                sendMap.put("send_name", tdHtExpressOrderDto.getAdd_name());
                if (tdHtExpressOrderDto.getAdd_mobile_phone() == null || "".equals(tdHtExpressOrderDto.getAdd_mobile_phone().trim())) {
                    sendMap.put("send_mobile", tdHtExpressOrderDto.getAdd_telephone());
                } else {
                    sendMap.put("send_mobile", tdHtExpressOrderDto.getAdd_mobile_phone());
                }
                sendMap.put("send_p", tdHtExpressOrderDto.getAdd_province());
                sendMap.put("send_pn", provinceDtos);
                sendMap.put("send_c", tdHtExpressOrderDto.getAdd_city());
                sendMap.put("send_cn", cityDtos);
                sendMap.put("send_a", tdHtExpressOrderDto.getAdd_region());
                sendMap.put("send_an", areaDtos);
                sendMap.put("send_address", tdHtExpressOrderDto.getAdd_detail_address());
                sendMap.put("exp_ord_type", tdHtExpressOrderDto.getExp_ord_type());

                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(sendMap);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要修改的订单");
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
     * @Purpose 通过订单id更新订单信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo updateExpOrd(TdHtExpressOrderDto tdHtExpressOrderDto) {
        String logInfo = this.getClass().getName() + ":updateExpOrd:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            boolean verification = false;
            String errMessage = "";
            if (tdHtExpressOrderDto.getAdd_name() == null || "".equals(tdHtExpressOrderDto.getAdd_name().trim())) {
                errMessage = "请输入正确的发件人姓名.";
                verification = true;
            }
            boolean isMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderDto.getAdd_mobile_phone());
            boolean isPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderDto.getAdd_mobile_phone());
            if (tdHtExpressOrderDto.getAdd_mobile_phone() == null || (!isMobile && !isPhone)) {
                errMessage = "请输入正确的发件人联系方式.";
                verification = true;
            }
            if (tdHtExpressOrderDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderDto.getAdd_detail_address().trim())) {
                errMessage = "请输入正确的发件人详细地址.";
                verification = true;
            }
            if (verification) {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
            } else {
                if (isMobile) {
                    tdHtExpressOrderDto.setAdd_telephone("");
                } else {
                    tdHtExpressOrderDto.setAdd_telephone(tdHtExpressOrderDto.getAdd_mobile_phone());
                    tdHtExpressOrderDto.setAdd_mobile_phone("");
                }
                tdHtExpressOrderRepository.updateExpOrdByExpOrdId(tdHtExpressOrderDto);
                retInfo.setMark("0");
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
     * @param exp_ord_id
     * @return RetInfo
     * @Purpose 通过订单id更新订单状态
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo delExpOrd(Integer exp_ord_id) {
        String logInfo = this.getClass().getName() + ":delExpOrd:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(exp_ord_id);
            if (tdHtExpressOrderDto != null) {
                if (tdHtExpressOrderDto.getExp_ord_state() < 4) {
                    List<TdHtAgentFoundChangeDto> tdHtAgentFoundChangeDtos = tdHtAgentFoundChangeRepository.findAgentFoundByOrdId(tdHtExpressOrderDto.getExp_ord_id());
                    for (int i = 0; i < tdHtAgentFoundChangeDtos.size(); i++) {
                        TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = tdHtAgentFoundChangeDtos.get(i);
                        tdHtAgentFoundChangeDto.setAfchg_state(1);
                        tdHtAgentFoundChangeRepository.updateAgentFoundState(tdHtAgentFoundChangeDto);
                    }

                    TdHtExpressOrderDto expressOrderDto = new TdHtExpressOrderDto();
                    expressOrderDto.setExp_ord_id(exp_ord_id);
                    expressOrderDto.setExp_ord_state(0);
                    tdHtExpressOrderRepository.updateExpOrdState(expressOrderDto);
                    TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = new TdHtExpressOrderCollectDto();
                    tdHtExpressOrderCollectDto.setExp_ord_id(exp_ord_id);
                    tdHtExpressOrderCollectDto.setExp_ord_clt_state(0);
                    tdHtExpressCollectRepository.updateExpOrdCltState(tdHtExpressOrderCollectDto);

                    retInfo.setMark("0");
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("该订单已支付");
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要删除的订单");
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
     * @param clt_id
     * @return RetInfo
     * @Purpose 通过快件id查询快件信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findExpOrdClt(Integer clt_id) {
        String logInfo = this.getClass().getName() + ":findExpOrdClt:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = tdHtExpressCollectRepository.findExpOrdCltById(clt_id);
            if (tdHtExpressOrderCollectDto != null) {
                Map revMap = new HashMap();
                List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
                List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(tdHtExpressOrderCollectDto.getAdd_province());
                List<AreaDto> areaDtos = tsHtDictRepository.findAreaByCity(tdHtExpressOrderCollectDto.getAdd_city());

                revMap.put("clt_id", clt_id);
                revMap.put("rev_name", tdHtExpressOrderCollectDto.getAdd_name());
                if (tdHtExpressOrderCollectDto.getAdd_mobile_phone() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_mobile_phone().trim())) {
                    revMap.put("rev_mobile", tdHtExpressOrderCollectDto.getAdd_telephone());
                } else {
                    revMap.put("rev_mobile", tdHtExpressOrderCollectDto.getAdd_mobile_phone());
                }
                revMap.put("rev_p", tdHtExpressOrderCollectDto.getAdd_province());
                revMap.put("rev_pn", provinceDtos);
                revMap.put("rev_c", tdHtExpressOrderCollectDto.getAdd_city());
                revMap.put("rev_cn", cityDtos);
                revMap.put("rev_a", tdHtExpressOrderCollectDto.getAdd_region());
                revMap.put("rev_an", areaDtos);
                revMap.put("rev_address", tdHtExpressOrderCollectDto.getAdd_detail_address());

                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(revMap);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要修改的快件");
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
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 通过快件id更新快件信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo updateExpOrdClt(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        String logInfo = this.getClass().getName() + ":updateExpOrdClt:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            boolean verification = false;
            String errMessage = "";
            if (tdHtExpressOrderCollectDto.getAdd_name() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_name().trim())) {
                errMessage = "请输入正确的收件人姓名.";
                verification = true;
            }
            boolean isMobile = MobileOrPhoneUtil.getIsMobile(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
            boolean isPhone = MobileOrPhoneUtil.getIsPhone(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
            if (tdHtExpressOrderCollectDto.getAdd_mobile_phone() == null || (!isMobile && !isPhone)) {
                errMessage = "请输入正确的收件人联系方式.";
                verification = true;
            }
            if (tdHtExpressOrderCollectDto.getAdd_detail_address() == null || "".equals(tdHtExpressOrderCollectDto.getAdd_detail_address().trim())) {
                errMessage = "请输入正确的收件人详细地址.";
                verification = true;
            }
            if (verification) {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
            } else {
                if (isMobile) {
                    tdHtExpressOrderCollectDto.setAdd_telephone("");
                } else {
                    tdHtExpressOrderCollectDto.setAdd_telephone(tdHtExpressOrderCollectDto.getAdd_mobile_phone());
                    tdHtExpressOrderCollectDto.setAdd_mobile_phone("");
                }
                tdHtExpressCollectRepository.updateExpOrdCltInfoById(tdHtExpressOrderCollectDto);
                retInfo.setMark("0");
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
     * @Purpose 指派代理人
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo assignAgent(TdHtExpressOrderDto tdHtExpressOrderDto) {
        String logInfo = this.getClass().getName() + ":assignAgent:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto expressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(tdHtExpressOrderDto.getExp_ord_id());
            if (tdHtExpressOrderDto != null) {
                if (expressOrderDto.getExp_ord_state() == 1) {
                    tdHtExpressOrderDto.setExp_ord_state(2);
                    tdHtExpressOrderDto.setExp_ord_taking_time(new Date());

                    Map<String, CourierDto> courierMap = (Map<String, CourierDto>) MemcachedUtils.get(MemcachedKey.COURIER_DTO_MAP);
                    String key = expressOrderDto.getExpress_id() + "#" + expressOrderDto.getAdd_region();
                    CourierDto courierDto = courierMap.get(key);

                    TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = new TdHtAgentFoundChangeDto();
                    expressOrderDto.setAgent_id(tdHtExpressOrderDto.getAgent_id());
                    Integer newestWaitTakeExpOrdCount = tdHtExpressOrderRepository.findExpOrdNumByMembAndAgent(expressOrderDto);
                    expressOrderDto.setPay_time(new Date(System.currentTimeMillis() - 30 * 60 * 1000));
                    Integer memberPayExpOrdCount = tdHtExpressOrderRepository.findMembPayExpOrdCount(expressOrderDto);
                    if (newestWaitTakeExpOrdCount == 0 && memberPayExpOrdCount == 0) {
                        tdHtAgentFoundChangeDto.setAfchg_change_amount(courierDto.getFirst_single_bonus().add(courierDto.getMore_single_bonus().multiply(new BigDecimal(tdHtExpressOrderDto.getExp_ord_size() - 1))));
                    } else {
                        tdHtAgentFoundChangeDto.setAfchg_change_amount(courierDto.getMore_single_bonus().multiply(new BigDecimal(expressOrderDto.getExp_ord_size())));
                    }
                    tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(System.currentTimeMillis()));
                    tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                    tdHtAgentFoundChangeDto.setAfchg_type(1);
                    tdHtAgentFoundChangeDto.setAfchg_state(2);
                    tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub(new Date()));
                    tdHtAgentFoundChangeDto.setAfchg_name("抢单奖励");
                    tdHtAgentFoundChangeDto.setAgent_id(tdHtExpressOrderDto.getAgent_id());

                    TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(tdHtExpressOrderDto.getAgent_id());
                    tdHtAgentFoundChangeDto.setAfchg_front_amount(tdHtAgentDto.getAgent_balance());
                    tdHtAgentFoundChangeDto.setAfchg_back_amount(tdHtAgentDto.getAgent_balance().add(tdHtAgentFoundChangeDto.getAfchg_change_amount()));
                    tdHtAgentFoundChangeDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                    tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);

                    tdHtExpressOrderRepository.updateAssignAgent(tdHtExpressOrderDto);
                    retInfo.setMark("0");
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("订单已被接单");
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("订单不存在");
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
     * @param exp_ord_id
     * @return RetInfo
     * @Purpose 验证入库操作订单快递单号是否存在
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo validateExpressNumber(Integer exp_ord_id) {
        String logInfo = this.getClass().getName() + ":validateExpressNumber:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            List<TdHtExpressOrderCollectDto> collectDtoList = tdHtExpressCollectRepository.findExpressCollectByExpOrdId(exp_ord_id);
            if (collectDtoList.size() > 0) {
                for (int i = 0; i < collectDtoList.size(); i++) {
                    TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);
                    if (collectDto.getExpress_number() == null || "".equals(collectDto.getExpress_number().trim())) {
                        retInfo.setMark("1");
                        retInfo.setTip("该订单没有快递单号.");
                        break;
                    } else {
                        retInfo.setMark("0");
                    }
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("订单快件不存在");
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
     * @param expOrdBillDto
     * @return RetInfo
     * @Purpose 通过条件查询快递列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo searchOrdBillList(ExpOrdBillDto expOrdBillDto, int pageSize) {
        String logInfo = this.getClass().getName() + ":searchOrdBillList:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormatStr = new SimpleDateFormat("yyyy-MM-dd");
            Long time = System.currentTimeMillis();
            if(expOrdBillDto.getStar_time() == null || "".equals(expOrdBillDto.getStar_time())){
                expOrdBillDto.setStar_time(dateFormatStr.format(new Date(time)));
            }
            if(expOrdBillDto.getEnd_time() == null || "".equals(expOrdBillDto.getEnd_time())){
                expOrdBillDto.setEnd_time(dateFormatStr.format(new Date(time)));
            }
            Map<String, Object> params = new HashMap<>();
            params.put("express_id", expOrdBillDto.getExpress_id());
            params.put("star_time_str", expOrdBillDto.getStar_time());
            params.put("end_time_str", expOrdBillDto.getEnd_time());
            params.put("send_city", expOrdBillDto.getSend_city());
            params.put("star_time", dateFormat.parse(expOrdBillDto.getStar_time() + " 00:00:00"));
            params.put("end_time", dateFormat.parse(expOrdBillDto.getEnd_time() + " 23:59:59"));

            //从字典中获取所有快递公司
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);
            params.put("express_company", expressCompanyList);

            List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
            params.put("provinceDtos", provinceDtos);
            if(expOrdBillDto.getSend_city() > 0){
                TsHtDictList tsHtDictList = tsHtDictRepository.findDictById(expOrdBillDto.getSend_city());
                List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(tsHtDictList.getParent_id());
                params.put("cityDtos", cityDtos);
                params.put("province_id", tsHtDictList.getParent_id());
            }else {
                List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(provinceDtos.get(0).getDict_id());
                params.put("cityDtos", cityDtos);
            }

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            int totalRecord = tdHtExpressCollectRepository.searchOrdBillSize(params);//总条数
            params.put("total_record", totalRecord);
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, expOrdBillDto.getCurrent_page(), pageSize);
                map.putAll(params);
                map.put("page", "page");
                List<Map<String, Object>> mapList = tdHtExpressCollectRepository.searchOrdBillList(map);

                int exp_ord_id = 0;
                for (int i = 0; i < mapList.size(); i++) {
                    Map collect = mapList.get(i);

                    if(Integer.valueOf(collect.get("exp_ord_id").toString()) == exp_ord_id){
                        collect.put("exp_ord_amount", 0);
                        collect.put("exp_ord_pay_amount", 0);
                    }else {
                        exp_ord_id = Integer.valueOf(collect.get("exp_ord_id").toString());
                    }

                    TdHtAgentDto agent = tdHtAgentRepository.findAgentById(Integer.valueOf(collect.get("agent_id").toString()));
                    if (agent != null) {
                        collect.put("agent_name", agent.getAgent_name());
                        collect.put("agent_phone", agent.getAgent_phone());
                    }

                    String send_province_name = dicts.get("" + collect.get("send_province")).getCode_name();
                    String send_city_name = dicts.get("" + collect.get("send_city")).getCode_name();
                    String send_area_name = dicts.get("" + collect.get("send_region")).getCode_name();
                    String send_address = send_province_name + send_city_name + send_area_name + collect.get("send_detail_address");
                    collect.put("send_address", send_address);

                    String rev_province = dicts.get("" + collect.get("rev_province")).getCode_name();
                    String rev_city = dicts.get("" + collect.get("rev_city")).getCode_name();
                    String rev_area = dicts.get("" + collect.get("rev_region")).getCode_name();
                    String rev_address = rev_province + rev_city + rev_area + collect.get("rev_detail_address");
                    collect.put("rev_address", rev_address);
                }

                map.put("currentPage", expOrdBillDto.getCurrent_page());
                map.put("expOrds", mapList);
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
     * @param expOrdBillDto
     * @return RetInfo
     * @Purpose 快件数据导出Excel
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo createExcel(ExpOrdBillDto expOrdBillDto, HttpServletResponse response) {
        String logInfo = this.getClass().getName() + ":createExcel:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormatStr = new SimpleDateFormat("yyyy-MM-dd");
            Long time = System.currentTimeMillis();
            if(expOrdBillDto.getStar_time() == null || "".equals(expOrdBillDto.getStar_time())){
                expOrdBillDto.setStar_time(dateFormatStr.format(new Date(time)));
            }
            if(expOrdBillDto.getEnd_time() == null || "".equals(expOrdBillDto.getEnd_time())){
                expOrdBillDto.setEnd_time(dateFormatStr.format(new Date(time)));
            }
            Map<String, Object> params = new HashMap<>();
            params.put("express_id", expOrdBillDto.getExpress_id());
            params.put("star_time_str", expOrdBillDto.getStar_time());
            params.put("end_time_str", expOrdBillDto.getEnd_time());
            params.put("send_city", expOrdBillDto.getSend_city());
            params.put("star_time", dateFormat.parse(expOrdBillDto.getStar_time() + " 00:00:00"));
            params.put("end_time", dateFormat.parse(expOrdBillDto.getEnd_time() + " 23:59:59"));

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            int totalRecord = tdHtExpressCollectRepository.searchOrdBillSize(params);//总条数
            if (totalRecord > 0) {
                List<Map<String, Object>> mapList = tdHtExpressCollectRepository.searchOrdBillList(params);

                int exp_ord_id = 0;
                for (int i = 0; i < mapList.size(); i++) {
                    Map collect = mapList.get(i);

                    if(Integer.valueOf(collect.get("exp_ord_id").toString()) == exp_ord_id){
                        collect.put("exp_ord_amount", 0);
                        collect.put("exp_ord_pay_amount", 0);
                    }else {
                        exp_ord_id = Integer.valueOf(collect.get("exp_ord_id").toString());
                    }

                    TdHtAgentDto agent = tdHtAgentRepository.findAgentById(Integer.valueOf(collect.get("agent_id").toString()));
                    if (agent != null) {
                        collect.put("agent_name", agent.getAgent_name());
                        collect.put("agent_phone", agent.getAgent_phone());
                    }

                    String send_province_name = dicts.get("" + collect.get("send_province")).getCode_name();
                    String send_city_name = dicts.get("" + collect.get("send_city")).getCode_name();
                    String send_area_name = dicts.get("" + collect.get("send_region")).getCode_name();
                    String send_address = send_province_name + send_city_name + send_area_name + collect.get("send_detail_address");
                    collect.put("send_address", send_address);

                    String rev_province = dicts.get("" + collect.get("rev_province")).getCode_name();
                    String rev_city = dicts.get("" + collect.get("rev_city")).getCode_name();
                    String rev_area = dicts.get("" + collect.get("rev_region")).getCode_name();
                    String rev_address = rev_province + rev_city + rev_area + collect.get("rev_detail_address");
                    collect.put("rev_address", rev_address);
                }

                List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
                Map<String, Object> sheetMap = new HashMap<String, Object>();
                sheetMap.put("sheetName", "sheet1");
                listmap.add(sheetMap);
                listmap.addAll(mapList);

                String fileName = "数据导出表格";

                String columnNames[] = {"订单id", "发件时间", "发件人姓名", "发件人手机号", "发件人地址", "收件人姓名",
                        "收件人手机号", "收件人地址", "代理人姓名", "代理人手机号", "厚通单号", "快递单号",
                        "快递定价", "快递名称", "重量", "数量", "总价", "支付金额"};//列名
                String keys[] = {"exp_ord_id", "exp_ord_time", "send_name", "send_mobile", "send_address", "rev_name",
                        "rev_mobile", "rev_address", "agent_name", "agent_phone", "ht_number", "express_number",
                        "express_price", "express_name", "exp_ord_clt_height", "exp_ord_size", "exp_ord_amount", "exp_ord_pay_amount"};//map中的key
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                    ExcelUtil.createWorkBook(listmap, keys, columnNames).write(os);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] content = os.toByteArray();
                InputStream is = new ByteArrayInputStream(content);
                // 设置response参数，可以打开下载页面
                response.reset();
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
                ServletOutputStream out = response.getOutputStream();

                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
                retInfo.setMark("0");
                retInfo.setTip("成功");
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
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
     * @param exp_ord_id
     * @return RetInfo
     * @Purpose 通过id查询订单信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findOrdById(Integer exp_ord_id) {
        String logInfo = this.getClass().getName() + ":searchCletList:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(exp_ord_id);
            if (tdHtExpressOrderDto != null) {
                retInfo.setMark("0");
                retInfo.setTip("查询成功.");
                retInfo.setObj(tdHtExpressOrderDto.getExp_ord_state());
            } else {
                retInfo.setMark("1");
                retInfo.setTip("订单不存在.");
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
     * @param tdHtAdminDto
     * @return RetInfo
     * @Purpose app端不同用户权限统计数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo statisticsDataByRole(TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":statisticsDataByRole:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> map = new HashMap();
            Map<String, Object> params = new HashMap<>();
            params.put("add_province", tdHtAdminDto.getProvince_id());
            params.put("add_city", tdHtAdminDto.getCity_id());
            params.put("add_region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());
            params.put("exp_ord_time", new Date());
            int expOrdCount = tdHtExpressOrderRepository.findExpOrdCount(params);
            int newCustomerCount = tdHtExpressOrderRepository.findNewCustomerCount(params);
            int memberCount = tdHtMemberRepository.findMembCount();
            int newMemberCount = tdHtMemberRepository.findNewMembCount();
            map.put("exp_ord_count", expOrdCount);
            map.put("new_customer_count", newCustomerCount);
            map.put("member_count", memberCount);
            map.put("new_member_count", newMemberCount);
            if (tdHtAdminDto.getProvince_id() == 0) {
                List<TsHtDictDto> provinceList = tsHtDictRepository.findDictByType(Constant.PROVINCE);
                map.put("dict", provinceList);
                map.put("name", "全国");
            } else if (tdHtAdminDto.getProvince_id() > 0 && tdHtAdminDto.getRole_id() == 3) {
                List<TsHtDictDto> cityList = tsHtDictRepository.findDictByParent(tdHtAdminDto.getProvince_id());
                map.put("dict", cityList);
                TsHtDictList tsHtDictDto = tsHtDictRepository.findDictById(tdHtAdminDto.getProvince_id());
                map.put("name", tsHtDictDto.getCode_name());
            } else if (tdHtAdminDto.getCity_id() > 0 && tdHtAdminDto.getRole_id() == 4) {
                List<TsHtDictDto> regionList = tsHtDictRepository.findDictByParent(tdHtAdminDto.getCity_id());
                map.put("dict", regionList);
                TsHtDictList tsHtDictDto = tsHtDictRepository.findDictById(tdHtAdminDto.getCity_id());
                map.put("name", tsHtDictDto.getCode_name());
            } else if (tdHtAdminDto.getRegion_id() > 0 && tdHtAdminDto.getRole_id() == 5) {
                TdHtAdminDivideDto tdHtAdminDivide=new TdHtAdminDivideDto();
                tdHtAdminDivide.setRegion_id(tdHtAdminDto.getRegion_id());
                tdHtAdminDivide.setDivide_type(1);
                List<TdHtAdminDivideDto> divideDtoList = tdHtAdminDivideRepository.findDivideByRegion(tdHtAdminDivide);
                List<TsHtDictDto> tsHtDictDtoList = new ArrayList<TsHtDictDto>();
                for (int i = 0; i < divideDtoList.size(); i++) {
                    TdHtAdminDivideDto tdHtAdminDivideDto = divideDtoList.get(i);
                    TsHtDictDto tsHtDictDto = new TsHtDictDto();
                    tsHtDictDto.setDict_id(tdHtAdminDivideDto.getDivide_id());
                    tsHtDictDto.setCode_name(tdHtAdminDivideDto.getDivide_name());
                    tsHtDictDtoList.add(tsHtDictDto);
                }
                map.put("dict", tsHtDictDtoList);
                TsHtDictList tsHtDictDto = tsHtDictRepository.findDictById(tdHtAdminDto.getRegion_id());
                map.put("name", tsHtDictDto.getCode_name());
            } else if (tdHtAdminDto.getDivide_id() > 0 && tdHtAdminDto.getRole_id() == 6) {
                TdHtAdminDivideDto tdHtAdminDivideDto = tdHtAdminDivideRepository.findDivideById(tdHtAdminDto.getDivide_id());
                map.put("dict", new ArrayList<TsHtDictDto>());
                map.put("name", tdHtAdminDivideDto.getDivide_name());
            }

            retInfo.setMark("0");
            retInfo.setObj(map);
            retInfo.setTip("查询成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tsHtDictList
     * @return RetInfo
     * @Purpose app端省市区统计下单量等数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo statisticsDataByDict(TsHtDictList tsHtDictList) {
        String logInfo = this.getClass().getName() + ":statisticsDataByDict:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> map = new HashMap();
            Map<String, Object> params = new HashMap<>();
            params.put("exp_ord_time", new Date());
            int memberCount = tdHtMemberRepository.findMembCount();
            map.put("member_count", memberCount);
            int newMemberCount = tdHtMemberRepository.findNewMembCount();
            map.put("new_member_count", newMemberCount);
            if (Constant.PROVINCE.equals(tsHtDictList.getCode_type())) {
                List<TsHtDictDto> cityList = tsHtDictRepository.findDictByParent(tsHtDictList.getDict_id());
                map.put("dict", cityList);
                TsHtDictList tsHtDictDto = tsHtDictRepository.findDictById(tsHtDictList.getDict_id());
                map.put("name", tsHtDictDto.getCode_name());

                params.put("add_province", tsHtDictList.getDict_id());

                int expOrdCount = tdHtExpressOrderRepository.findExpOrdCount(params);
                int newCustomerCount = tdHtExpressOrderRepository.findNewCustomerCount(params);
                map.put("exp_ord_count", expOrdCount);
                map.put("new_customer_count", newCustomerCount);
            } else if (Constant.CITY.equals(tsHtDictList.getCode_type())) {
                List<TsHtDictDto> regionList = tsHtDictRepository.findDictByParent(tsHtDictList.getDict_id());
                map.put("dict", regionList);
                TsHtDictList tsHtDictDto = tsHtDictRepository.findDictById(tsHtDictList.getDict_id());
                map.put("name", tsHtDictDto.getCode_name());

                params.put("add_city", tsHtDictList.getDict_id());

                int expOrdCount = tdHtExpressOrderRepository.findExpOrdCount(params);
                int newCustomerCount = tdHtExpressOrderRepository.findNewCustomerCount(params);
                map.put("exp_ord_count", expOrdCount);
                map.put("new_customer_count", newCustomerCount);
            } else if (Constant.AREA.equals(tsHtDictList.getCode_type())) {
                TdHtAdminDivideDto tdHtAdminDivide=new TdHtAdminDivideDto();
                tdHtAdminDivide.setDivide_type(1);
                tdHtAdminDivide.setRegion_id(tsHtDictList.getDict_id());
                List<TdHtAdminDivideDto> divideDtoList = tdHtAdminDivideRepository.findDivideByRegion(tdHtAdminDivide);
                List<TsHtDictDto> tsHtDictDtoList = new ArrayList<TsHtDictDto>();
                for (int i = 0; i < divideDtoList.size(); i++) {
                    TdHtAdminDivideDto tdHtAdminDivideDto = divideDtoList.get(i);
                    TsHtDictDto tsHtDictDto = new TsHtDictDto();
                    tsHtDictDto.setDict_id(tdHtAdminDivideDto.getDivide_id());
                    tsHtDictDto.setCode_name(tdHtAdminDivideDto.getDivide_name());
                    tsHtDictDtoList.add(tsHtDictDto);
                }
                map.put("dict", tsHtDictDtoList);
                TsHtDictList tsHtDictDto = tsHtDictRepository.findDictById(tsHtDictList.getDict_id());
                map.put("name", tsHtDictDto.getCode_name());

                params.put("add_region", tsHtDictList.getDict_id());

                int expOrdCount = tdHtExpressOrderRepository.findExpOrdCount(params);
                int newCustomerCount = tdHtExpressOrderRepository.findNewCustomerCount(params);
                map.put("exp_ord_count", expOrdCount);
                map.put("new_customer_count", newCustomerCount);
            } else {
                TdHtAdminDivideDto tdHtAdminDivideDto = tdHtAdminDivideRepository.findDivideById(tsHtDictList.getDict_id());
                map.put("dict", new ArrayList<TsHtDictDto>());
                map.put("name", tdHtAdminDivideDto.getDivide_name());

                params.put("divide_id", tsHtDictList.getDict_id());

                int expOrdCount = tdHtExpressOrderRepository.findExpOrdCount(params);
                int newCustomerCount = tdHtExpressOrderRepository.findNewCustomerCount(params);
                map.put("exp_ord_count", expOrdCount);
                map.put("new_customer_count", newCustomerCount);
            }

            retInfo.setMark("0");
            retInfo.setObj(map);
            retInfo.setTip("查询成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tsHtDictList
     * @return RetInfo
     * @Purpose app端按天统计下单量等数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo statisticsDayData(TsHtDictList tsHtDictList, TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":statisticsDayData:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            long nowDate = System.currentTimeMillis();
            Map<String, Object> map = new HashMap();
            Map<String, Object> params = new HashMap<>();
            params.put("condition", "%Y-%m-%d");
            BeforeOrAfterDate bd = new BeforeOrAfterDate();
            params.put("reduced", bd.beforDayNumTimeNum(new Date(nowDate), -30, "yyyy-MM-dd"));
            params.put("exp_ord_time", new Date());
            params.put("size", 30);

            if (Constant.COUNTRY.equals(tsHtDictList.getCode_type())) {

            } else if (Constant.PROVINCE.equals(tsHtDictList.getCode_type())) {
                params.put("add_province", tsHtDictList.getDict_id());
            } else if (Constant.CITY.equals(tsHtDictList.getCode_type())) {
                params.put("add_city", tsHtDictList.getDict_id());
            } else if (Constant.AREA.equals(tsHtDictList.getCode_type())) {
                params.put("add_region", tsHtDictList.getDict_id());
            } else {
                params.put("divide_id", tsHtDictList.getDict_id());
            }

            int newMemberCount = tdHtMemberRepository.findNewMembCount();
            int expOrdCount = tdHtExpressOrderRepository.findExpOrdCount(params);
            int newCustomerCount = tdHtExpressOrderRepository.findNewCustomerCount(params);
            map.put("exp_ord_count", expOrdCount);
            map.put("new_customer_count", newCustomerCount);
            map.put("new_member_count", newMemberCount);

            List<Map<String, Object>> expOrdMapList = tdHtExpressOrderRepository.statisticsOrdByCondition(params);
            List<Map<String, Object>> newCustomerMapList = tdHtExpressOrderRepository.statisticsCustomByCondition(params);
            List<Map<String, Object>> memberMapList = tdHtMemberRepository.statisticsMemberByCondition(params);

            map.put("exp_ord_list", bd.obtainDayDataMapList(expOrdMapList, new Date(nowDate), 30));
            map.put("new_customer_list", bd.obtainDayDataMapList(newCustomerMapList, new Date(nowDate), 30));
            map.put("member_list", bd.obtainDayDataMapList(memberMapList, new Date(nowDate), 30));

            retInfo.setObj(map);
            retInfo.setMark("0");
            retInfo.setTip("成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param params
     * @return RetInfo
     * @Purpose app端按需求统计下单量等数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo statisticsDataByDemand(Map params, TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":statisticsDataByDemand:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            long nowDate = System.currentTimeMillis();
            Map<String, Object> map = new HashMap();
            BeforeOrAfterDate bd = new BeforeOrAfterDate();

            if ("week".equals(params.get("demand"))) {
                params.put("condition", "%Y%U");
                params.put("reduced", bd.beforWeekNumTimeNum(new Date(nowDate), -4, "yyyyww"));
                params.put("size", 4);
            } else if ("month".equals(params.get("demand"))) {
                params.put("condition", "%Y-%m");
                params.put("reduced", bd.beforMonthNumTimeNum(new Date(nowDate), -6, "yyyy-MM"));
                params.put("size", 6);
            } else if ("year".equals(params.get("demand"))) {
                params.put("condition", "%Y");
            } else {
                params.put("condition", "%Y-%m-%d");
                int size = bd.daysBetween(new Date((Long) params.get("start_time")), new Date((Long) params.get("end_time")));
                params.put("start_time", new Date((Long) params.get("start_time")));
                params.put("end_time", new Date((Long) params.get("end_time")));
                params.put("size", size + 1);
            }

            if (Constant.COUNTRY.equals(params.get("dict_type"))) {

            } else if (Constant.PROVINCE.equals(params.get("dict_type"))) {
                params.put("add_province", params.get("dict_id"));
            } else if (Constant.CITY.equals(params.get("dict_type"))) {
                params.put("add_city", params.get("dict_id"));
            } else if (Constant.AREA.equals(params.get("dict_type"))) {
                params.put("add_region", params.get("dict_id"));
            } else {
                params.put("divide_id", params.get("dict_id"));
            }

            List<Map<String, Object>> expOrdMapList = tdHtExpressOrderRepository.statisticsOrdByCondition(params);
            List<Map<String, Object>> newCustomerMapList = tdHtExpressOrderRepository.statisticsCustomByCondition(params);
            List<Map<String, Object>> memberMapList = tdHtMemberRepository.statisticsMemberByCondition(params);

            if ("year".equals(params.get("demand"))) {
                map.put("exp_ord_list", bd.listReverse(expOrdMapList));
                map.put("new_customer_list", bd.listReverse(newCustomerMapList));
                map.put("member_list", bd.listReverse(memberMapList));
            } else if ("month".equals(params.get("demand"))) {
                map.put("exp_ord_list", bd.obtainMonthDataMapList(expOrdMapList, new Date(nowDate)));
                map.put("new_customer_list", bd.obtainMonthDataMapList(newCustomerMapList, new Date(nowDate)));
                map.put("member_list", bd.obtainMonthDataMapList(memberMapList, new Date(nowDate)));
            } else if ("week".equals(params.get("demand"))) {
                map.put("exp_ord_list", bd.obtainWeekDataMapList(expOrdMapList, new Date(nowDate)));
                map.put("new_customer_list", bd.obtainWeekDataMapList(newCustomerMapList, new Date(nowDate)));
                map.put("member_list", bd.obtainWeekDataMapList(memberMapList, new Date(nowDate)));
            } else {
                map.put("exp_ord_list", bd.obtainDayDataMapList(expOrdMapList, (Date) params.get("end_time"), (Integer) params.get("size")));
                map.put("new_customer_list", bd.obtainDayDataMapList(newCustomerMapList, (Date) params.get("end_time"), (Integer) params.get("size")));
                map.put("member_list", bd.obtainDayDataMapList(memberMapList, (Date) params.get("end_time"), (Integer) params.get("size")));
            }

            retInfo.setObj(map);
            retInfo.setMark("0");
            retInfo.setTip("成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param params
     * @return RetInfo
     * @Purpose app端按时间查询订单列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findExpOrdByTime(Map params, TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":findExpOrdByTime:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            String time = params.get("time").toString();
            if (time.indexOf("~") > 0) {
                String dateStr = time.split("~")[0];
                params.put("time", new SimpleDateFormat("yyyyww").format(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)));
            }
            if ("week".equals(params.get("demand"))) {
                params.put("condition", "%Y%U");
            } else if ("month".equals(params.get("demand"))) {
                params.put("condition", "%Y-%m");
            } else if ("year".equals(params.get("demand"))) {
                params.put("condition", "%Y");
            } else {
                params.put("condition", "%Y-%m-%d");
            }

            if (Constant.COUNTRY.equals(params.get("dict_type"))) {

            } else if (Constant.PROVINCE.equals(params.get("dict_type"))) {
                params.put("add_province", params.get("dict_id"));
            } else if (Constant.CITY.equals(params.get("dict_type"))) {
                params.put("add_city", params.get("dict_id"));
            } else if (Constant.AREA.equals(params.get("dict_type"))) {
                params.put("add_region", params.get("dict_id"));
            } else {
                params.put("divide_id", params.get("dict_id"));
            }

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            int totalRecord = tdHtExpressOrderRepository.findExpOrdByTimeSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, (Integer) params.get("current_page"), (Integer) params.get("page_size"));
                map.putAll(params);
                List<ExpOrdDetailedDto> expOrdDetailedDtoList = tdHtExpressOrderRepository.findExpOrdByTimePage(map);

                for (int i = 0; i < expOrdDetailedDtoList.size(); i++) {
                    ExpOrdDetailedDto expOrdDetailedDto = expOrdDetailedDtoList.get(i);
                    String province = dicts.get("" + expOrdDetailedDto.getAdd_province()).getCode_name();
                    String city = dicts.get("" + expOrdDetailedDto.getAdd_city()).getCode_name();
                    String area = dicts.get("" + expOrdDetailedDto.getAdd_region()).getCode_name();
                    String address = province + city + area + expOrdDetailedDto.getAdd_detail_address();
                    expOrdDetailedDto.setAdd_detail_address(address);

                    if (expOrdDetailedDto.getAdd_mobile_phone() == null || "".equals(expOrdDetailedDto.getAdd_mobile_phone())) {
                        expOrdDetailedDto.setAdd_mobile_phone(expOrdDetailedDto.getAdd_telephone());
                    }
                }

                map.put("currentPage", params.get("current_page"));
                map.put("expOrds", expOrdDetailedDtoList);
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
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param exp_ord_id
     * @return RetInfo
     * @Purpose app端按时间查询订单详情
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findExpOrdDetails(int exp_ord_id) {
        String logInfo = this.getClass().getName() + ":findExpOrdDetails:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            ExpOrdDetailedDto expOrdDetailedDto = tdHtExpressOrderRepository.findExpOrdDetailedDtoById(exp_ord_id);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            if (expOrdDetailedDto != null) {
                String sendProvince = dicts.get("" + expOrdDetailedDto.getAdd_province()).getCode_name();
                String sendCity = dicts.get("" + expOrdDetailedDto.getAdd_city()).getCode_name();
                String sendArea = dicts.get("" + expOrdDetailedDto.getAdd_region()).getCode_name();
                String sendAddress = sendProvince + sendCity + sendArea + expOrdDetailedDto.getAdd_detail_address();
                expOrdDetailedDto.setAdd_detail_address(sendAddress);

                if (expOrdDetailedDto.getAdd_mobile_phone() == null || "".equals(expOrdDetailedDto.getAdd_mobile_phone())) {
                    expOrdDetailedDto.setAdd_mobile_phone(expOrdDetailedDto.getAdd_telephone());
                }

                List<ExpOrdCollectDetailDto> collectDtos = tdHtExpressCollectRepository.findExpOrdCollectDetailDtoByExpOrdId(exp_ord_id);
                for (int i = 0; i < collectDtos.size(); i++) {
                    ExpOrdCollectDetailDto expOrdCollectDetailDto = collectDtos.get(i);
                    String revProvince = dicts.get("" + expOrdCollectDetailDto.getAdd_province()).getCode_name();
                    String revCity = dicts.get("" + expOrdCollectDetailDto.getAdd_city()).getCode_name();
                    String revArea = dicts.get("" + expOrdCollectDetailDto.getAdd_region()).getCode_name();
                    String revAddress = revProvince + revCity + revArea + expOrdCollectDetailDto.getAdd_detail_address();
                    expOrdCollectDetailDto.setAdd_detail_address(revAddress);

                    if (expOrdCollectDetailDto.getAdd_mobile_phone() == null || "".equals(expOrdCollectDetailDto.getAdd_mobile_phone())) {
                        expOrdCollectDetailDto.setAdd_mobile_phone(expOrdCollectDetailDto.getAdd_telephone());
                    }
                }

                expOrdDetailedDto.setCollects(collectDtos);
                retInfo.setMark("0");
                retInfo.setObj(expOrdDetailedDto);
                retInfo.setTip("数据查询成功.");
            } else {
                retInfo.setMark("1");
                retInfo.setTip("订单不存在.");
            }

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 大客户快件入库
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo bigCustomerStorage(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        String logInfo = this.getClass().getName() + ":bigCustomerStorage:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderCollectDto collectDto = tdHtExpressCollectRepository.findExpOrdCltById(tdHtExpressOrderCollectDto.getExp_ord_clt_id());
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(collectDto.getExp_ord_id());
            tdHtExpressOrderDto.setExp_ord_type(tdHtExpressOrderCollectDto.getExp_ord_type());
            tdHtExpressOrderDto.setExp_ord_state(5);
            int count = tdHtExpressCollectRepository.findCltByStateAndId(collectDto.getExp_ord_id());
            if (count == 1) {
                tdHtExpressOrderRepository.updateBigCusExp(tdHtExpressOrderDto);
            }
            if (tdHtExpressOrderCollectDto.getExp_ord_type() == 2) {
                collectDto.setExpress_number(tdHtExpressOrderCollectDto.getExpress_number());
                collectDto.setExp_ord_clt_state(2);
                tdHtExpressCollectRepository.updateCltById(collectDto);
                retInfo.setMark("0");
            } else {
                TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto = new TsHtExpressOpenNumberDto();
                tsHtExpressOpenNumberDto.setExpress_id(tdHtExpressOrderDto.getExpress_id());
                tsHtExpressOpenNumberDto.setRegion_id(tdHtExpressOrderDto.getAdd_city());
                tsHtExpressOpenNumberDto.setExp_open_state(0);
                TsHtExpressOpenNumberDto expressOpenNumberDto = tsHtExpressOpenNumberRepository.findCanUseExpressNumber(tsHtExpressOpenNumberDto);
                if (expressOpenNumberDto == null) {
                    retInfo.setMark("1");
                    retInfo.setTip("快递单号已用尽，请先补充.");
                } else {
                    collectDto.setExpress_number(expressOpenNumberDto.getExpress_number());
                    collectDto.setExp_ord_clt_state(2);
                    tdHtExpressCollectRepository.updateCltById(collectDto);
                    expressOpenNumberDto.setExp_open_state(1);
                    tsHtExpressOpenNumberRepository.updateExpressNumberState(expressOpenNumberDto);
                    retInfo.setMark("0");
                    if (count == 1) {
                        retInfo.setObj(tdHtExpressOrderDto);
                    }
                }
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   获取面单批量打印数据
     * @version   3.0
     * @author    lubin
     * @time      2017-03-30
     * @param     exp_ord_id  订单id
     * @return    RetInfo  查询结果
     */
    @Override
    public RetInfo batchPrint(int exp_ord_id) {
        String logInfo = this.getClass().getName() + ":batchPrint:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中获取字典数据
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);

            //查询订单数据
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(exp_ord_id);

            if(tdHtExpressOrderDto.getExp_ord_type() == 1){
                //获取面单发件信息
                String sendPro = dicts.get("" + tdHtExpressOrderDto.getAdd_province()).getCode_name();
                String sendCity = dicts.get("" + tdHtExpressOrderDto.getAdd_city()).getCode_name();
                String sendArea = dicts.get("" + tdHtExpressOrderDto.getAdd_region()).getCode_name();
                String sendAddress = tdHtExpressOrderDto.getAdd_detail_address();
                String sendMobile = tdHtExpressOrderDto.getAdd_mobile_phone();
                if (sendMobile == null || "".equals(sendMobile.trim())) {
                    sendMobile = tdHtExpressOrderDto.getAdd_telephone();
                }

                List<Map<String, Object>> mapList = new ArrayList<>();

                if(tdHtExpressOrderDto.getExp_ord_state() == 5){
                    //获取订单内快件数据
                    List<TdHtExpressOrderCollectDto> collectDtoList = tdHtExpressCollectRepository.findExpressCollectByExpOrdId(exp_ord_id);

                    //获取每个快件的面单数据
                    for (int i = 0 ; i < collectDtoList.size() ; i ++){
                        TdHtExpressOrderCollectDto collectDto = collectDtoList.get(i);

                        //获取面单收件信息
                        String revPro = dicts.get("" + collectDto.getAdd_province()).getCode_name();
                        String revCity = dicts.get("" + collectDto.getAdd_city()).getCode_name();
                        String revArea = dicts.get("" + collectDto.getAdd_region()).getCode_name();
                        String revAddress = collectDto.getAdd_detail_address();
                        String revMobile = collectDto.getAdd_mobile_phone();
                        if (revMobile == null || "".equals(revMobile.trim())) {
                            revMobile = collectDto.getAdd_telephone();
                        }

                        //创建面单数据
                        Map<String, Object> params = new HashMap<>();
                        params.put("exp_ord_clt_id", collectDto.getExp_ord_clt_id());
                        params.put("express_id", collectDto.getExpress_id());
                        params.put("express_name", collectDto.getExpress_name());
                        params.put("express_number", collectDto.getExpress_number());
                        params.put("send_address", sendAddress);
                        params.put("send_pro", sendPro);
                        params.put("send_city", sendCity);
                        params.put("send_area", sendArea);
                        params.put("send_name", tdHtExpressOrderDto.getAdd_name());
                        params.put("send_mobile", sendMobile);
                        params.put("rev_address", revAddress);
                        params.put("rev_pro", revPro);
                        params.put("rev_city", revCity);
                        params.put("rev_area", revArea);
                        params.put("rev_name", collectDto.getAdd_name());
                        params.put("rev_mobile", revMobile);
                        params.put("ht_number", collectDto.getHt_number());
                        params.put("order_no", collectDto.getExp_ord_clt_number());
                        params.put("add_region", tdHtExpressOrderDto.getAdd_region());
                        params.put("weight", collectDto.getExp_ord_clt_height());
                        if (collectDto.getExp_ord_category() == null || "".equals(collectDto.getExp_ord_category().trim())) {
                            params.put("category", "日用品");
                        } else {
                            params.put("category", collectDto.getExp_ord_category());
                        }
                        mapList.add(params);
                    }

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("exp_ord_info", mapList);
                    map.put("exp_ord_type", tdHtExpressOrderDto.getExp_ord_type());

                    retInfo.setMark("0");
                    retInfo.setObj(map);
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("该快件还没有入库，请先入库再进行打印订单");
                }
            }else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("exp_ord_info", new ArrayList<>());
                map.put("exp_ord_type", tdHtExpressOrderDto.getExp_ord_type());

                retInfo.setMark("0");
                retInfo.setObj(map);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("系统错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   订单申请退款
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     exp_ord_id  订单id
     * @return    RetInfo  查询结果
     */
    @Override
    public RetInfo refundExpOrd(int exp_ord_id) {
        String logInfo = this.getClass().getName() + ":refundExpOrd:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(exp_ord_id);

            if (tdHtExpressOrderDto.getExp_ord_state() > 3 && tdHtExpressOrderDto.getExp_ord_state() < 6 && tdHtExpressOrderDto.getMemb_type() != 2) {
                TdHtMembRefundChangeDto membRefundChangeDto = tdHtMembRefundChangeRepository.findRefundByExpOrdId(exp_ord_id);

                if(membRefundChangeDto == null){
                    TdHtMembFoundChangeDto tdHtMembFoundChangeDto = tdHtMembFoundChangeRepository.findFoundByOrder(exp_ord_id);

                    Long now = System.currentTimeMillis();
                    TdHtMembRefundChangeDto tdHtMembRefundChangeDto = new TdHtMembRefundChangeDto();
                    tdHtMembRefundChangeDto.setMemb_id(tdHtExpressOrderDto.getMemb_id());
                    tdHtMembRefundChangeDto.setRefund_amount(tdHtMembFoundChangeDto.getMfchg_change_amount());
                    tdHtMembRefundChangeDto.setRefund_number(CommonUtil.getOrderNub(new Date(now)));
                    tdHtMembRefundChangeDto.setRefund_time(new Timestamp(now));
                    tdHtMembRefundChangeDto.setRefund_month(CommonUtil.getMonth());
                    tdHtMembRefundChangeDto.setRefund_state(1);
                    tdHtMembRefundChangeDto.setRefund_type(1);
                    tdHtMembRefundChangeDto.setMfchg_id(tdHtMembFoundChangeDto.getMfchg_id());
                    tdHtMembRefundChangeDto.setMfchg_number(tdHtMembFoundChangeDto.getMfchg_number());
                    tdHtMembRefundChangeDto.setMfchg_channel(tdHtMembFoundChangeDto.getMfchg_channel());
                    tdHtMembRefundChangeDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                    tdHtMembRefundChangeDto.setRefund_failure_cause("");

                    tdHtMembRefundChangeRepository.insertMembRefund(tdHtMembRefundChangeDto);

                    retInfo.setMark("0");
                    retInfo.setTip("成功");
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("订单已申请退款.");
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("订单不符合退款要求.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtExpressCollectRepository(TdHtExpressCollectRepository tdHtExpressCollectRepository) {
        this.tdHtExpressCollectRepository = tdHtExpressCollectRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }

    public void setTdHtAgentRepository(TdHtAgentRepository tdHtAgentRepository) {
        this.tdHtAgentRepository = tdHtAgentRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

    public void setTdHtMemberRepository(TdHtMemberRepository tdHtMemberRepository) {
        this.tdHtMemberRepository = tdHtMemberRepository;
    }

    public void setTdHtAdminDivideRepository(TdHtAdminDivideRepository tdHtAdminDivideRepository) {
        this.tdHtAdminDivideRepository = tdHtAdminDivideRepository;
    }

    public void setTsHtExpressOpenNumberRepository(TsHtExpressOpenNumberRepository tsHtExpressOpenNumberRepository) {
        this.tsHtExpressOpenNumberRepository = tsHtExpressOpenNumberRepository;
    }

    public void setTdHtAgentFoundChangeRepository(TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository) {
        this.tdHtAgentFoundChangeRepository = tdHtAgentFoundChangeRepository;
    }

    public void setTdHtMembFoundChangeRepository(TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository) {
        this.tdHtMembFoundChangeRepository = tdHtMembFoundChangeRepository;
    }

    public void setTdHtMembRefundChangeRepository(TdHtMembRefundChangeRepository tdHtMembRefundChangeRepository) {
        this.tdHtMembRefundChangeRepository = tdHtMembRefundChangeRepository;
    }
}
