package cn.hotol.app.service.upload.excel;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.price.TsHtRegionPriceDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LuBin
 * Date 2017-01-24.
 */
public class UploadServiceImpl implements UploadService {

    private static Logger logger = Logger.getLogger(UploadServiceImpl.class);

    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;
    private TdHtMemberRepository tdHtMemberRepository;
    private TdHtAgentRepository tdHtAgentRepository;
    private TdHtMembAddressRepository tdHtMembAddressRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;

    /**
     * @param mapList
     * @return RetInfo
     * @Purpose 导入表格数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo uploadExpOrdData(List<Map<String, Object>> mapList, String exp_ord_time, String agent_phone, String member_phone) {
        String logInfo = this.getClass().getName() + ":uploadExpOrdData:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        int row = 0;
        try {
            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentByMobile(agent_phone);
            if (tdHtAgentDto == null) {
                retInfo.setMark("1");
                retInfo.setTip("归属代理人手机不正确.");
            } else {
                TdHtMemberDto member = tdHtMemberRepository.findMemberByMobile(member_phone);
                if (member == null) {
                    retInfo.setMark("1");
                    retInfo.setTip("归属用户手机不正确.");
                } else {
                    List<Integer> integerList = getIndex(mapList.size());
                    List<TdHtMembAddressDto> membAddressDtoList = tdHtMembAddressRepository.findMemberRevAddress();
                    for (int i = 0; i < mapList.size(); i++) {
                        Map<String, Object> sendMap = mapList.get(i);
                        String beginDate = exp_ord_time + " 08:00:00";
                        String endDate = exp_ord_time + " 18:00:00";
                        Date date = ExcelUtil.randomDate(beginDate, endDate);
                        String month = new SimpleDateFormat("yyyyMM").format(date);

                        TsHtDictList sendPro = new TsHtDictList();
                        sendPro.setCode_name(sendMap.get("send_province").toString());
                        sendPro.setParent_id(0);
                        List<TsHtDictList> sendP = tsHtDictRepository.findDictByName(sendPro);
                        if (sendP.size() > 0) {
                            TsHtDictList sendCity = new TsHtDictList();
                            sendCity.setCode_name(sendMap.get("send_city").toString());
                            sendCity.setParent_id(sendP.get(0).getDict_id());
                            List<TsHtDictList> sendC = tsHtDictRepository.findDictByName(sendCity);
                            if (sendC.size() > 0) {
                                if (sendC.get(0).getDict_id() == 347 || sendC.get(0).getDict_id() == 276) {
                                    TsHtDictList sendArea = new TsHtDictList();
                                    sendArea.setCode_name(sendMap.get("send_area").toString());
                                    sendArea.setParent_id(sendC.get(0).getDict_id());
                                    List<TsHtDictList> sendA = tsHtDictRepository.findDictByName(sendArea);
                                    if (sendA.size() == 0) {
                                        List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(347);
                                        AreaDto areaDto = areaDtoList.get(new Random().nextInt(areaDtoList.size()));
                                        TsHtDictList sendAr = new TsHtDictList();
                                        sendAr.setDict_id(areaDto.getDict_id());
                                        sendAr.setCode_name(areaDto.getArea_name());
                                        sendA.add(sendAr);
                                    }
                                    TdHtMembAddress tdSendHtMembAddress = new TdHtMembAddress();
                                    TdHtMembAddress tdRevHtMembAddress = new TdHtMembAddress();
                                    TdHtExpressOrderDto tdHtExpressOrderDto = new TdHtExpressOrderDto();
                                    TdHtMembFoundChangeDto tdHtMembFoundChangeDto = new TdHtMembFoundChangeDto();
                                    /*TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMemberByMobile(sendMap.get("send_mobile").toString());
                                    if (tdHtMemberDto == null) {
                                        //该用户不存在，创建新用户
                                        if (MobileOrPhoneUtil.getIsMobile(sendMap.get("send_mobile").toString())) {
                                            TdHtMemberDto newMember = new TdHtMemberDto();
                                            newMember.setMemb_phone(sendMap.get("send_mobile").toString().replace("-", ""));
                                            newMember.setMemb_password("");
                                            newMember.setMemb_sex(3);
                                            newMember.setMemb_birthday(new Timestamp(date.getTime()));
                                            newMember.setMemb_nick_name(sendMap.get("send_mobile").toString().replace("-", ""));
                                            newMember.setMemb_head_portrait("http://file.hotol.cn/app/member/0/default_head.png");
                                            newMember.setMemb_register_time(new Timestamp(date.getTime()));
                                            newMember.setMemb_register_month(month);
                                            newMember.setMemb_balance(new BigDecimal(0));
                                            newMember.setMemb_name("");
                                            newMember.setMemb_id_number("");
                                            newMember.setMemb_islock(2);
                                            newMember.setMemb_score(0);
                                            newMember.setMemb_invite_code("");
                                            newMember.setMemb_role(1);
                                            newMember.setToken(CommonUtil.getToken());
                                            newMember.setPush_token("");
                                            newMember.setPush_type(1);
                                            tdHtMemberRepository.insertMember(newMember);

                                            tdSendHtMembAddress.setMemb_id(newMember.getMemb_id());
                                            tdRevHtMembAddress.setMemb_id(newMember.getMemb_id());
                                            tdHtExpressOrderDto.setMemb_id(newMember.getMemb_id());
                                            tdHtMembFoundChangeDto.setMemb_id(newMember.getMemb_id());
                                        } else {*/
                                            tdSendHtMembAddress.setMemb_id(member.getMemb_id());
                                            tdRevHtMembAddress.setMemb_id(member.getMemb_id());
                                            tdHtExpressOrderDto.setMemb_id(member.getMemb_id());
                                            tdHtMembFoundChangeDto.setMemb_id(member.getMemb_id());
                                        /*}
                                    } else {
                                        tdSendHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
                                        tdRevHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
                                        tdHtExpressOrderDto.setMemb_id(tdHtMemberDto.getMemb_id());
                                        tdHtMembFoundChangeDto.setMemb_id(tdHtMemberDto.getMemb_id());
                                    }*/

                                    if (MobileOrPhoneUtil.getIsMobile(sendMap.get("send_mobile").toString())) {
                                        tdSendHtMembAddress.setAdd_mobile_phone(sendMap.get("send_mobile").toString());
                                        tdSendHtMembAddress.setAdd_telephone("");
                                    } else {
                                        tdSendHtMembAddress.setAdd_telephone(sendMap.get("send_mobile").toString());
                                        tdSendHtMembAddress.setAdd_mobile_phone("");
                                    }
                                    BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                                    LocationDto location = baiduMapUtil.changeAddress(sendP.get(0).getCode_name() + sendC.get(0).getCode_name() + sendA.get(0).getCode_name() + sendMap.get("send_address").toString());
                                    if (location != null) {
                                        tdSendHtMembAddress.setAdd_longitude(location.getLongitude().doubleValue());
                                        tdSendHtMembAddress.setAdd_latitude(location.getLatitude().doubleValue());
                                    }
                                    tdSendHtMembAddress.setAdd_province(sendP.get(0).getDict_id());
                                    tdSendHtMembAddress.setAdd_city(sendC.get(0).getDict_id());
                                    tdSendHtMembAddress.setAdd_region(sendA.get(0).getDict_id());
                                    tdSendHtMembAddress.setAdd_detail_address(sendMap.get("send_address").toString());
                                    tdSendHtMembAddress.setAdd_name(sendMap.get("send_name").toString());
                                    tdSendHtMembAddress.setAdd_label(0);
                                    tdSendHtMembAddress.setAdd_is_default(1);
                                    tdSendHtMembAddress.setAdd_type(1);
                                    tdSendHtMembAddress.setAdd_state(1);
                                    tdSendHtMembAddress.setAdd_express_size(1);
                                    tdSendHtMembAddress.setAdd_id_number("");
                                    tdHtMembAddressRepository.insertAddress(tdSendHtMembAddress);

                                    tdHtExpressOrderDto.setAgent_id(tdHtAgentDto.getAgent_id());
                                    tdHtExpressOrderDto.setAdd_id(tdSendHtMembAddress.getAdd_id());
                                    tdHtExpressOrderDto.setAdd_name(tdSendHtMembAddress.getAdd_name());
                                    tdHtExpressOrderDto.setAdd_id_number("");
                                    tdHtExpressOrderDto.setAdd_detail_address(tdSendHtMembAddress.getAdd_detail_address());
                                    tdHtExpressOrderDto.setAdd_province(tdSendHtMembAddress.getAdd_province());
                                    tdHtExpressOrderDto.setAdd_city(tdSendHtMembAddress.getAdd_city());
                                    tdHtExpressOrderDto.setAdd_region(tdSendHtMembAddress.getAdd_region());
                                    tdHtExpressOrderDto.setAdd_mobile_phone(tdSendHtMembAddress.getAdd_mobile_phone());
                                    tdHtExpressOrderDto.setAdd_telephone(tdSendHtMembAddress.getAdd_telephone());
                                    tdHtExpressOrderDto.setAdd_longitude(tdSendHtMembAddress.getAdd_longitude());
                                    tdHtExpressOrderDto.setAdd_latitude(tdSendHtMembAddress.getAdd_latitude());
                                    tdHtExpressOrderDto.setExpress_id(3520);
                                    tdHtExpressOrderDto.setExp_ord_type(2);
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
                                    tdHtExpressOrderDto.setExp_ord_month(month);
                                    tdHtExpressOrderDto.setExp_ord_gratuity(0);
                                    tdHtExpressOrderDto.setAgent_note("");
                                    tdHtExpressOrderDto.setDivide_id(PointUtil.getDivideId(tdHtExpressOrderDto.getAdd_region(), tdHtExpressOrderDto.getAdd_longitude(), tdHtExpressOrderDto.getAdd_latitude()));
                                    tdHtExpressOrderDto.setImport_marker(1);

                                    List<TsHtDictList> revP = new ArrayList<TsHtDictList>();
                                    List<TsHtDictList> revC = new ArrayList<TsHtDictList>();
                                    List<TsHtDictList> revA = new ArrayList<TsHtDictList>();
                                    int revAddressIndex = new Random().nextInt(membAddressDtoList.size());
                                    boolean isHaveRev=false;
                                    if(Integer.parseInt(month)>=201702){
                                        isHaveRev=true;
                                    }else {
                                        if (sendMap.get("rev_mobile")!=null&&!"".equals(sendMap.get("rev_mobile"))&&sendMap.get("express_number")!=null&&!"".equals(sendMap.get("express_number"))) {
                                            isHaveRev=true;
                                        }
                                    }
                                    if (isHaveRev) {
                                        if (sendMap.get("rev_mobile")!=null&&!"".equals(sendMap.get("rev_mobile"))&&sendMap.get("express_number")!=null&&!"".equals(sendMap.get("express_number"))) {
                                            TsHtDictList revPro = new TsHtDictList();
                                            revPro.setCode_name(sendMap.get("rev_province").toString());
                                            revPro.setParent_id(0);
                                            revP = tsHtDictRepository.findDictByName(revPro);
                                            if (revP.size() == 0) {
                                                List<ProvinceDto> provinceDtoList = tsHtDictRepository.findAllProvince();
                                                ProvinceDto provinceDto = provinceDtoList.get(new Random().nextInt(provinceDtoList.size()));
                                                TsHtDictList revPr = new TsHtDictList();
                                                revPr.setDict_id(provinceDto.getDict_id());
                                                revP.add(revPr);
                                                List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDto.getDict_id());
                                                CityDto cityDto = cityDtoList.get(new Random().nextInt(cityDtoList.size()));
                                                TsHtDictList revCr = new TsHtDictList();
                                                revCr.setDict_id(cityDto.getDict_id());
                                                revC.add(revCr);
                                                List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDto.getDict_id());
                                                AreaDto areaDto = areaDtoList.get(new Random().nextInt(areaDtoList.size()));
                                                TsHtDictList revAr = new TsHtDictList();
                                                revAr.setDict_id(areaDto.getDict_id());
                                                revA.add(revAr);
                                            } else {
                                                TsHtDictList revCity = new TsHtDictList();
                                                revCity.setCode_name(sendMap.get("rev_city").toString());
                                                revCity.setParent_id(revP.get(0).getDict_id());
                                                revC = tsHtDictRepository.findDictByName(revCity);
                                                if (revC.size() == 0) {
                                                    List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(revP.get(0).getDict_id());
                                                    CityDto cityDto = cityDtoList.get(new Random().nextInt(cityDtoList.size()));
                                                    TsHtDictList revCr = new TsHtDictList();
                                                    revCr.setDict_id(cityDto.getDict_id());
                                                    revC.add(revCr);
                                                    List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDto.getDict_id());
                                                    AreaDto areaDto = areaDtoList.get(new Random().nextInt(areaDtoList.size()));
                                                    TsHtDictList revAr = new TsHtDictList();
                                                    revAr.setDict_id(areaDto.getDict_id());
                                                    revA.add(revAr);
                                                } else {
                                                    TsHtDictList revArea = new TsHtDictList();
                                                    revArea.setCode_name(sendMap.get("rev_area").toString());
                                                    revArea.setParent_id(revC.get(0).getDict_id());
                                                    revA = tsHtDictRepository.findDictByName(revArea);
                                                    if (revA.size() == 0) {
                                                        List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(revC.get(0).getDict_id());
                                                        AreaDto areaDto = areaDtoList.get(new Random().nextInt(areaDtoList.size()));
                                                        TsHtDictList revAr = new TsHtDictList();
                                                        revAr.setDict_id(areaDto.getDict_id());
                                                        revA.add(revAr);
                                                    }
                                                }
                                            }
                                            int m = new Random().nextInt(15);
                                            int weight = getWeight(m);
                                            BigDecimal price = getPrice(tdHtExpressOrderDto.getExpress_id(), sendP.get(0).getDict_id(), revP.get(0).getDict_id(), weight);
                                            tdHtExpressOrderDto.setExp_ord_pay_amount(price.doubleValue());
                                            tdHtExpressOrderDto.setExp_ord_amount(price.doubleValue());
                                            tdHtExpressOrderDto.setExp_ord_weight(weight);
                                        }else {
                                            int m = new Random().nextInt(15);
                                            int weight = getWeight(m);
                                            TdHtMembAddressDto membAddressDto = membAddressDtoList.get(revAddressIndex);
                                            BigDecimal price = getPrice(tdHtExpressOrderDto.getExpress_id(), sendP.get(0).getDict_id(), membAddressDto.getAdd_province(), weight);
                                            tdHtExpressOrderDto.setExp_ord_pay_amount(price.doubleValue());
                                            tdHtExpressOrderDto.setExp_ord_amount(price.doubleValue());
                                            tdHtExpressOrderDto.setExp_ord_weight(weight);
                                        }
                                    } else {
                                        int m = new Random().nextInt(15);
                                        int weight = getWeight(m);
                                        boolean is_ok = false;
                                        for (int j = 0; j < integerList.size(); j++) {
                                            if (i == integerList.get(j)) {
                                                is_ok = true;
                                                TdHtMembAddressDto membAddressDto = membAddressDtoList.get(revAddressIndex);
                                                BigDecimal price = getPrice(tdHtExpressOrderDto.getExpress_id(), sendP.get(0).getDict_id(), membAddressDto.getAdd_province(), weight);
                                                tdHtExpressOrderDto.setExp_ord_pay_amount(price.doubleValue());
                                                tdHtExpressOrderDto.setExp_ord_amount(price.doubleValue());
                                            }
                                        }
                                        if (!is_ok) {
                                            double price = getRandomPrice(m);
                                            tdHtExpressOrderDto.setExp_ord_pay_amount(price);
                                            tdHtExpressOrderDto.setExp_ord_amount(price);
                                        }
                                        tdHtExpressOrderDto.setExp_ord_weight(weight);
                                    }
                                    tdHtExpressOrderRepository.insertExpressOrder(tdHtExpressOrderDto);

                                    tdHtMembFoundChangeDto.setMfchg_change_amount(new BigDecimal(tdHtExpressOrderDto.getExp_ord_pay_amount()));
                                    tdHtMembFoundChangeDto.setMfchg_front_amount(new BigDecimal(0));
                                    tdHtMembFoundChangeDto.setMfchg_back_amount(new BigDecimal(0));
                                    tdHtMembFoundChangeDto.setMfchg_type(3);
                                    tdHtMembFoundChangeDto.setMfchg_time(new Timestamp(tdHtExpressOrderDto.getPay_time().getTime()));
                                    tdHtMembFoundChangeDto.setMfchg_month(month);
                                    tdHtMembFoundChangeDto.setMfchg_name("订单支付");
                                    tdHtMembFoundChangeDto.setMfchg_state(2);
                                    tdHtMembFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub(date));
                                    tdHtMembFoundChangeDto.setMfchg_channel(2);
                                    tdHtMembFoundChangeDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                                    tdHtMembFoundChangeDto.setMemb_mon_bill_id(0);
                                    tdHtMembFoundChangeDto.setMemb_ivc_his_id(0);
                                    tdHtMembFoundChangeDto.setMemb_goods_id(0);
                                    tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);
                                    row = row + 1;

                                    if (isHaveRev) {
                                        if (sendMap.get("rev_mobile")!=null&&!"".equals(sendMap.get("rev_mobile"))&&sendMap.get("express_number")!=null&&!"".equals(sendMap.get("express_number"))) {
                                            if (MobileOrPhoneUtil.getIsMobile(sendMap.get("rev_mobile").toString())) {
                                                tdRevHtMembAddress.setAdd_mobile_phone(sendMap.get("rev_mobile").toString());
                                                tdRevHtMembAddress.setAdd_telephone("");
                                            } else {
                                                tdRevHtMembAddress.setAdd_telephone(sendMap.get("rev_mobile").toString());
                                                tdRevHtMembAddress.setAdd_mobile_phone("");
                                            }
                                            tdRevHtMembAddress.setAdd_latitude(0);
                                            tdRevHtMembAddress.setAdd_longitude(0);
                                            tdRevHtMembAddress.setAdd_province(revP.get(0).getDict_id());
                                            tdRevHtMembAddress.setAdd_city(revC.get(0).getDict_id());
                                            tdRevHtMembAddress.setAdd_region(revA.get(0).getDict_id());
                                            tdRevHtMembAddress.setAdd_detail_address(sendMap.get("rev_address").toString());
                                            tdRevHtMembAddress.setAdd_name(sendMap.get("rev_name").toString());
                                            tdRevHtMembAddress.setAdd_label(0);
                                            tdRevHtMembAddress.setAdd_is_default(1);
                                            tdRevHtMembAddress.setAdd_type(2);
                                            tdRevHtMembAddress.setAdd_state(1);
                                            tdRevHtMembAddress.setAdd_express_size(1);
                                            tdRevHtMembAddress.setAdd_id_number("");
                                            tdHtMembAddressRepository.insertAddress(tdRevHtMembAddress);

                                            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = new TdHtExpressOrderCollectDto();
                                            tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                                            tdHtExpressOrderCollectDto.setExp_ord_category("文件");
                                            tdHtExpressOrderCollectDto.setExp_ord_clt_height(tdHtExpressOrderDto.getExp_ord_weight());
                                            tdHtExpressOrderCollectDto.setAdd_id(tdRevHtMembAddress.getAdd_id());
                                            tdHtExpressOrderCollectDto.setAdd_name(tdRevHtMembAddress.getAdd_name());
                                            tdHtExpressOrderCollectDto.setAdd_mobile_phone(tdRevHtMembAddress.getAdd_mobile_phone());
                                            tdHtExpressOrderCollectDto.setAdd_telephone(tdRevHtMembAddress.getAdd_telephone());
                                            tdHtExpressOrderCollectDto.setAdd_detail_address(tdRevHtMembAddress.getAdd_detail_address());
                                            tdHtExpressOrderCollectDto.setAdd_province(tdRevHtMembAddress.getAdd_province());
                                            tdHtExpressOrderCollectDto.setAdd_city(tdRevHtMembAddress.getAdd_city());
                                            tdHtExpressOrderCollectDto.setAdd_region(tdRevHtMembAddress.getAdd_region());
                                            tdHtExpressOrderCollectDto.setAdd_longitude(tdRevHtMembAddress.getAdd_longitude());
                                            tdHtExpressOrderCollectDto.setAdd_latitude(tdRevHtMembAddress.getAdd_latitude());
                                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(4);
                                            tdHtExpressOrderCollectDto.setExpress_number(sendMap.get("express_number").toString());
                                            int coordinate = new Random().nextInt(mapList.size());
                                            tdHtExpressOrderCollectDto.setHt_number(CommonUtil.getOrderNo(new Date(), coordinate));
                                            tdHtExpressOrderCollectDto.setSign_time(date);
                                            tdHtExpressOrderCollectDto.setSend_time(date);
                                            tdHtExpressOrderCollectDto.setDelivery_time(date);
                                            tdHtExpressOrderCollectDto.setExpress_id(String.valueOf(3520));
                                            tdHtExpressOrderCollectDto.setCategory_id(11);
                                            tdHtExpressOrderCollectDto.setExpress_name("申通快递");
                                            tdHtExpressOrderCollectDto.setExpress_price(new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()));
                                            tdHtExpressOrderCollectDto.setExp_ord_clt_number(CommonUtil.getOrderNub(date));
                                            tdHtExpressCollectRepository.insertExpressOrderCollect(tdHtExpressOrderCollectDto);
                                        }else {
                                            TdHtMembAddressDto membAddressDto = membAddressDtoList.get(revAddressIndex);
                                            tdRevHtMembAddress.setAdd_mobile_phone(membAddressDto.getAdd_mobile_phone());
                                            tdRevHtMembAddress.setAdd_telephone(membAddressDto.getAdd_telephone());
                                            tdRevHtMembAddress.setAdd_latitude(membAddressDto.getAdd_latitude());
                                            tdRevHtMembAddress.setAdd_longitude(membAddressDto.getAdd_longitude());
                                            tdRevHtMembAddress.setAdd_province(membAddressDto.getAdd_province());
                                            tdRevHtMembAddress.setAdd_city(membAddressDto.getAdd_city());
                                            tdRevHtMembAddress.setAdd_region(membAddressDto.getAdd_region());
                                            tdRevHtMembAddress.setAdd_detail_address(membAddressDto.getAdd_detail_address());
                                            tdRevHtMembAddress.setAdd_name(membAddressDto.getAdd_name());
                                            tdRevHtMembAddress.setAdd_label(membAddressDto.getAdd_label());
                                            tdRevHtMembAddress.setAdd_is_default(1);
                                            tdRevHtMembAddress.setAdd_type(2);
                                            tdRevHtMembAddress.setAdd_state(0);
                                            tdRevHtMembAddress.setAdd_express_size(1);
                                            tdRevHtMembAddress.setAdd_id_number("");
                                            tdHtMembAddressRepository.insertAddress(tdRevHtMembAddress);

                                            TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = new TdHtExpressOrderCollectDto();
                                            tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                                            tdHtExpressOrderCollectDto.setExp_ord_category("文件");
                                            tdHtExpressOrderCollectDto.setExp_ord_clt_height(tdHtExpressOrderDto.getExp_ord_weight());
                                            tdHtExpressOrderCollectDto.setAdd_id(tdRevHtMembAddress.getAdd_id());
                                            tdHtExpressOrderCollectDto.setAdd_name(tdRevHtMembAddress.getAdd_name());
                                            tdHtExpressOrderCollectDto.setAdd_mobile_phone(tdRevHtMembAddress.getAdd_mobile_phone());
                                            tdHtExpressOrderCollectDto.setAdd_telephone(tdRevHtMembAddress.getAdd_telephone());
                                            tdHtExpressOrderCollectDto.setAdd_detail_address(tdRevHtMembAddress.getAdd_detail_address());
                                            tdHtExpressOrderCollectDto.setAdd_province(tdRevHtMembAddress.getAdd_province());
                                            tdHtExpressOrderCollectDto.setAdd_city(tdRevHtMembAddress.getAdd_city());
                                            tdHtExpressOrderCollectDto.setAdd_region(tdRevHtMembAddress.getAdd_region());
                                            tdHtExpressOrderCollectDto.setAdd_longitude(tdRevHtMembAddress.getAdd_longitude());
                                            tdHtExpressOrderCollectDto.setAdd_latitude(tdRevHtMembAddress.getAdd_latitude());
                                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(4);
                                            tdHtExpressOrderCollectDto.setExpress_number(sendMap.get("express_number").toString());
                                            int coordinate = new Random().nextInt(mapList.size());
                                            tdHtExpressOrderCollectDto.setHt_number(CommonUtil.getOrderNo(new Date(), coordinate));
                                            tdHtExpressOrderCollectDto.setSign_time(date);
                                            tdHtExpressOrderCollectDto.setSend_time(date);
                                            tdHtExpressOrderCollectDto.setDelivery_time(date);
                                            tdHtExpressOrderCollectDto.setExpress_id(String.valueOf(3520));
                                            tdHtExpressOrderCollectDto.setCategory_id(11);
                                            tdHtExpressOrderCollectDto.setExpress_name("申通快递");
                                            tdHtExpressOrderCollectDto.setExpress_price(new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()));
                                            tdHtExpressOrderCollectDto.setExp_ord_clt_number(CommonUtil.getOrderNub(date));
                                            tdHtExpressCollectRepository.insertExpressOrderCollect(tdHtExpressOrderCollectDto);
                                        }
                                    } else {
                                        for (int j = 0; j < integerList.size(); j++) {
                                            if (i == integerList.get(j)) {
                                                TdHtMembAddressDto membAddressDto = membAddressDtoList.get(revAddressIndex);
                                                tdRevHtMembAddress.setAdd_mobile_phone(membAddressDto.getAdd_mobile_phone());
                                                tdRevHtMembAddress.setAdd_telephone(membAddressDto.getAdd_telephone());
                                                tdRevHtMembAddress.setAdd_latitude(membAddressDto.getAdd_latitude());
                                                tdRevHtMembAddress.setAdd_longitude(membAddressDto.getAdd_longitude());
                                                tdRevHtMembAddress.setAdd_province(membAddressDto.getAdd_province());
                                                tdRevHtMembAddress.setAdd_city(membAddressDto.getAdd_city());
                                                tdRevHtMembAddress.setAdd_region(membAddressDto.getAdd_region());
                                                tdRevHtMembAddress.setAdd_detail_address(membAddressDto.getAdd_detail_address());
                                                tdRevHtMembAddress.setAdd_name(membAddressDto.getAdd_name());
                                                tdRevHtMembAddress.setAdd_label(membAddressDto.getAdd_label());
                                                tdRevHtMembAddress.setAdd_is_default(1);
                                                tdRevHtMembAddress.setAdd_type(2);
                                                tdRevHtMembAddress.setAdd_state(0);
                                                tdRevHtMembAddress.setAdd_express_size(1);
                                                tdRevHtMembAddress.setAdd_id_number("");
                                                tdHtMembAddressRepository.insertAddress(tdRevHtMembAddress);

                                                TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = new TdHtExpressOrderCollectDto();
                                                tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                                                tdHtExpressOrderCollectDto.setExp_ord_category("文件");
                                                tdHtExpressOrderCollectDto.setExp_ord_clt_height(tdHtExpressOrderDto.getExp_ord_weight());
                                                tdHtExpressOrderCollectDto.setAdd_id(tdRevHtMembAddress.getAdd_id());
                                                tdHtExpressOrderCollectDto.setAdd_name(tdRevHtMembAddress.getAdd_name());
                                                tdHtExpressOrderCollectDto.setAdd_mobile_phone(tdRevHtMembAddress.getAdd_mobile_phone());
                                                tdHtExpressOrderCollectDto.setAdd_telephone(tdRevHtMembAddress.getAdd_telephone());
                                                tdHtExpressOrderCollectDto.setAdd_detail_address(tdRevHtMembAddress.getAdd_detail_address());
                                                tdHtExpressOrderCollectDto.setAdd_province(tdRevHtMembAddress.getAdd_province());
                                                tdHtExpressOrderCollectDto.setAdd_city(tdRevHtMembAddress.getAdd_city());
                                                tdHtExpressOrderCollectDto.setAdd_region(tdRevHtMembAddress.getAdd_region());
                                                tdHtExpressOrderCollectDto.setAdd_longitude(tdRevHtMembAddress.getAdd_longitude());
                                                tdHtExpressOrderCollectDto.setAdd_latitude(tdRevHtMembAddress.getAdd_latitude());
                                                tdHtExpressOrderCollectDto.setExp_ord_clt_state(4);
                                                tdHtExpressOrderCollectDto.setExpress_number(sendMap.get("express_number").toString());
                                                int coordinate = new Random().nextInt(mapList.size());
                                                tdHtExpressOrderCollectDto.setHt_number(CommonUtil.getOrderNo(new Date(), coordinate));
                                                tdHtExpressOrderCollectDto.setSign_time(date);
                                                tdHtExpressOrderCollectDto.setSend_time(date);
                                                tdHtExpressOrderCollectDto.setDelivery_time(date);
                                                tdHtExpressOrderCollectDto.setExpress_id(String.valueOf(3520));
                                                tdHtExpressOrderCollectDto.setCategory_id(11);
                                                tdHtExpressOrderCollectDto.setExpress_name("申通快递");
                                                tdHtExpressOrderCollectDto.setExpress_price(new BigDecimal(tdHtExpressOrderDto.getExp_ord_amount()));
                                                tdHtExpressOrderCollectDto.setExp_ord_clt_number(CommonUtil.getOrderNub(date));
                                                tdHtExpressCollectRepository.insertExpressOrderCollect(tdHtExpressOrderCollectDto);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    retInfo.setMark("0");
                    retInfo.setTip("导入订单数据" + row + "条.");
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("第"+row+"条附近数据错误.");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * 计算价格
     *
     * @param ExpressId
     * @param sendPro
     * @param revPro
     * @param weight
     * @return
     */
    private BigDecimal getPrice(int ExpressId, int sendPro, int revPro, int weight) {
        Map<String, List<TsHtRegionPriceDto>> priceMap = (Map<String, List<TsHtRegionPriceDto>>) MemcachedUtils.get(MemcachedKey.EXP_REGION_PRICE);
        List<TsHtRegionPriceDto> priceDtoList = priceMap.get(String.valueOf(ExpressId));

        BigDecimal price = new BigDecimal(0);
        for (int i = 0; i < priceDtoList.size(); i++) {
            TsHtRegionPriceDto priceDto = priceDtoList.get(i);
            if (priceDto.getSend_province_id() == sendPro && priceDto.getCollect_province_id() == revPro) {
                if (ExpressId == Constant.DBL) {
                    if (weight > 2 && priceDto.getFirst_weight() == 3) {
                        price = priceDto.getFirst_amount();
                        if (weight > priceDto.getFirst_weight()) {
                            int addWeight = weight - priceDto.getFirst_weight();
                            int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                            if (addWeight % priceDto.getAdd_weight() > 0) {
                                addWeightMultiple = addWeightMultiple + 1;
                            }
                            BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                            price = price.add(addPrice);
                        }
                    } else if (weight < 3 && priceDto.getFirst_weight() == 1) {
                        price = priceDto.getFirst_amount();
                        if (weight > priceDto.getFirst_weight()) {
                            int addWeight = weight - priceDto.getFirst_weight();
                            int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                            if (addWeight % priceDto.getAdd_weight() > 0) {
                                addWeightMultiple = addWeightMultiple + 1;
                            }
                            BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                            price = price.add(addPrice);
                        }
                    }
                } else {
                    price = priceDto.getFirst_amount();
                    if (weight > priceDto.getFirst_weight()) {
                        int addWeight = weight - priceDto.getFirst_weight();
                        int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                        if (addWeight % priceDto.getAdd_weight() > 0) {
                            addWeightMultiple = addWeightMultiple + 1;
                        }
                        BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                        price = price.add(addPrice);
                    }
                }
            }
        }
        return price;
    }

    /**
     * 获取随机重量
     *
     * @param i
     * @return
     */
    private int getWeight(int i) {
        int[] weights = {1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 1, 1, 1, 1, 1};
        return weights[i];
    }

    /**
     * 获取相应重量的价格
     *
     * @param i
     * @return
     */
    private double getRandomPrice(int i) {
        double[] prices = {7.0, 7.0, 7.0, 7.0, 7.0, 7.0, 7.0, 12.0, 15.0, 18.0, 7.0, 7.0, 7.0, 7.0, 7.0};
        return prices[i];
    }

    private List<Integer> getIndex(int size) {
        int newSize = size / 6;
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < newSize; i++) {
            int randIndex = getRandIndex(size, integerList);
            integerList.add(randIndex);
        }
        return integerList;
    }

    private int getRandIndex(int size, List<Integer> integerList) {
        int m = new Random().nextInt(size);
        boolean is_ok = false;
        for (int i = 0; i < integerList.size(); i++) {
            if (m == integerList.get(i)) {
                is_ok = true;
                break;
            }
        }
        if (is_ok) {
            return getRandIndex(size, integerList);
        }else {
            return m;
        }
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

    public void setTdHtAgentRepository(TdHtAgentRepository tdHtAgentRepository) {
        this.tdHtAgentRepository = tdHtAgentRepository;
    }

    public void setTdHtMembAddressRepository(TdHtMembAddressRepository tdHtMembAddressRepository) {
        this.tdHtMembAddressRepository = tdHtMembAddressRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

    public void setTdHtMembFoundChangeRepository(TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository) {
        this.tdHtMembFoundChangeRepository = tdHtMembFoundChangeRepository;
    }
}
