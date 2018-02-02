package cn.hotol.app.service.test;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.TdHtMembThirdLogin;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.test.*;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.TestRepository;
import cn.hotol.app.repository.TsHtDictRepository;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-17.
 */
public class TestServiceImpl implements TestService {

    private static Logger logger = Logger.getLogger(TestServiceImpl.class);

    @Resource
    private TestRepository testRepository;
    @Resource
    private TsHtDictRepository tsHtDictRepository;

    @Override
    public RetInfo findUser() {
        String logInfo = this.getClass().getName() + ":register:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            SimpleDateFormat sdf_month = new SimpleDateFormat("yyyyMM");
            List<ProvinceDto> provinceDtoList = tsHtDictRepository.findAllProvince();

            for (int i = 0; i < provinceDtoList.size(); i++) {
                ProvinceDto provinceDto = provinceDtoList.get(i);
                List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDto.getDict_id());
                for (int j = 0; j < cityDtoList.size(); j++) {
                    CityDto cityDto = cityDtoList.get(j);
                    List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDto.getDict_id());
                    cityDto.setArea_list(areaDtoList);
                }
                provinceDto.setCity_list(cityDtoList);
            }
            List<User> mapList = testRepository.findUser();
            for (int i = 0; i < mapList.size(); i++) {
                User user = mapList.get(i);
                TdHtMemberDto tdHtMemberDto = new TdHtMemberDto();
                tdHtMemberDto.setMemb_phone(user.getMobile_no());
                tdHtMemberDto.setMemb_password(user.getPassword());
                if (user.getGender() == null || "".equals(user.getGender())) {
                    tdHtMemberDto.setMemb_sex(3);
                } else if ("男".equals(user.getGender())) {
                    tdHtMemberDto.setMemb_sex(1);
                } else if ("女".equals(user.getGender())) {
                    tdHtMemberDto.setMemb_sex(2);
                }
                tdHtMemberDto.setMemb_birthday(new Timestamp(user.getBirthday().getTime()));
                Double balance = testRepository.findBalanceByUser(user.getId());
                if (balance == null) {
                    balance = 0.00;
                }
                tdHtMemberDto.setMemb_balance(new BigDecimal(balance));
                tdHtMemberDto.setMemb_name("");
                tdHtMemberDto.setMemb_id_number("");
                tdHtMemberDto.setMemb_islock(1);
                WeUser weUser = testRepository.findWeUserByUser(user.getMobile_no());
                if (weUser == null) {
                    tdHtMemberDto.setMemb_score(0);
                    if (user.getName() == null || "".equals(user.getName())) {
                        tdHtMemberDto.setMemb_nick_name(user.getMobile_no());
                    } else {
                        tdHtMemberDto.setMemb_nick_name(user.getName());
                    }
                    if (user.getPhoto() == null || "".equals(user.getPhoto()) || "/images/hpic_eg.jpg".equals(user.getPhoto())) {
                        tdHtMemberDto.setMemb_head_portrait("http://file.hotol.cn/test/member/0/default_head.png");
                    } else {
                        tdHtMemberDto.setMemb_head_portrait("http://www.hotol.cn" + user.getPhoto());
                    }
                    tdHtMemberDto.setMemb_register_time(new Timestamp(user.getReg_time().getTime()));
                    tdHtMemberDto.setMemb_register_month(sdf_month.format(user.getReg_time().getTime()));
                } else {
                    tdHtMemberDto.setMemb_score(weUser.getTotal_integral());
                    if (user.getName() == null || "".equals(user.getName())) {
                        tdHtMemberDto.setMemb_nick_name(user.getMobile_no());
                    } else {
                        tdHtMemberDto.setMemb_nick_name(weUser.getWe_chat_name());
                    }
                    tdHtMemberDto.setMemb_head_portrait(weUser.getWe_chat_photo());
                    tdHtMemberDto.setMemb_register_time(new Timestamp(weUser.getAdd_time().getTime()));
                    tdHtMemberDto.setMemb_register_month(sdf_month.format(weUser.getAdd_time().getTime()));
                }
                tdHtMemberDto.setMemb_invite_code("");
                tdHtMemberDto.setMemb_role(1);
                tdHtMemberDto.setToken(CommonUtil.getToken());
                List<Push> push = testRepository.findPushByUser(user.getId());
                if (push.size() == 0) {
                    tdHtMemberDto.setPush_token("");
                    tdHtMemberDto.setPush_type(0);
                } else {
                    if ("android".equals(push.get(0).getClient_from())) {
                        tdHtMemberDto.setPush_type(1);
                    } else if ("ios".equals(push.get(0).getClient_from())) {
                        tdHtMemberDto.setPush_type(2);
                    } else {
                        tdHtMemberDto.setPush_type(0);
                    }
                    tdHtMemberDto.setPush_token(push.get(0).getClient_id());
                }
                testRepository.insertMember(tdHtMemberDto);
                //生成用户的邀请码
                createInviteCode(tdHtMemberDto.getMemb_id(), 0);

                if (weUser != null) {
                    TdHtMembThirdLogin tdHtMembThirdLogin = new TdHtMembThirdLogin();
                    tdHtMembThirdLogin.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtMembThirdLogin.setThr_type(1);
                    tdHtMembThirdLogin.setThird_id(2);
                    tdHtMembThirdLogin.setThr_token(weUser.getOpen_id());
                    tdHtMembThirdLogin.setThr_head_poration(weUser.getWe_chat_photo());
                    tdHtMembThirdLogin.setThr_nick_name(weUser.getWe_chat_name());
                    tdHtMembThirdLogin.setThr_sex(tdHtMemberDto.getMemb_sex());
                    tdHtMembThirdLogin.setThr_register_time(weUser.getAdd_time());
                    tdHtMembThirdLogin.setThr_register_month(sdf_month.format(weUser.getAdd_time()));
                    testRepository.insertMembThirdLogin(tdHtMembThirdLogin);
                }

                List<SendAddress> sendAddressList = testRepository.findSendAddress(user.getId());

                for (int j = 0; j < sendAddressList.size(); j++) {
                    TdHtMembAddress tdHtMembAddress = new TdHtMembAddress();
                    tdHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
                    if (sendAddressList.get(j).getLinkman() == null) {
                        tdHtMembAddress.setAdd_name("");
                    } else {
                        if (sendAddressList.get(j).getLinkman().length() > 16) {
                            tdHtMembAddress.setAdd_name(sendAddressList.get(j).getLinkman().substring(0, 16));
                        } else {
                            tdHtMembAddress.setAdd_name(sendAddressList.get(j).getLinkman());
                        }
                    }

                    tdHtMembAddress.setAdd_detail_address(sendAddressList.get(j).getStreet());

                    for (int p = 0; p < provinceDtoList.size(); p++) {
                        String address = sendAddressList.get(j).getAddress();
                        if (address != null) {
                            if (address.indexOf(provinceDtoList.get(p).getProvince_name()) >= 0) {
                                tdHtMembAddress.setAdd_province(provinceDtoList.get(p).getDict_id());
                                List<CityDto> cityDtoList = provinceDtoList.get(p).getCity_list();
                                for (int c = 0; c < cityDtoList.size(); c++) {
                                    if (address.indexOf(cityDtoList.get(c).getCity_name()) >= 0) {
                                        tdHtMembAddress.setAdd_city(cityDtoList.get(c).getDict_id());
                                        List<AreaDto> areaDtoList = cityDtoList.get(c).getArea_list();
                                        for (int a = 0; a < areaDtoList.size(); a++) {
                                            if (address.indexOf(areaDtoList.get(a).getArea_name()) >= 0) {
                                                tdHtMembAddress.setAdd_region(areaDtoList.get(a).getDict_id());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    tdHtMembAddress.setAdd_label(0);
                    if (sendAddressList.get(j).getMobile_no() == null || "".equals(sendAddressList.get(j).getMobile_no())) {
                        sendAddressList.get(j).setMobile_no("18569034337");
                    }
                    boolean is_ok = MobileOrPhoneUtil.getIsMobile(sendAddressList.get(j).getMobile_no());
                    if (is_ok) {
                        tdHtMembAddress.setAdd_mobile_phone(sendAddressList.get(j).getMobile_no().replace("-", ""));
                        tdHtMembAddress.setAdd_telephone("");
                    } else {
                        tdHtMembAddress.setAdd_mobile_phone("");
                        tdHtMembAddress.setAdd_telephone(sendAddressList.get(j).getMobile_no().replace("-", ""));
                    }
                    tdHtMembAddress.setAdd_longitude(0);
                    tdHtMembAddress.setAdd_latitude(0);
                    tdHtMembAddress.setAdd_id_number("");
                    if (sendAddressList.get(j).getIs_default() == 1) {
                        tdHtMembAddress.setAdd_is_default(0);
                    } else {
                        tdHtMembAddress.setAdd_is_default(1);
                    }
                    tdHtMembAddress.setAdd_type(1);
                    tdHtMembAddress.setAdd_state(0);
                    tdHtMembAddress.setAdd_express_size(0);
                    testRepository.insertAddress(tdHtMembAddress);
                }

                List<RevAddress> revAddressList = testRepository.findRevAddress(user.getId());

                for (int j = 0; j < revAddressList.size(); j++) {
                    TdHtMembAddress tdHtMembAddress = new TdHtMembAddress();
                    tdHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
                    if (revAddressList.get(j).getLinkman() == null) {
                        tdHtMembAddress.setAdd_name("");
                    } else {
                        if (revAddressList.get(j).getLinkman().length() > 16) {
                            tdHtMembAddress.setAdd_name(revAddressList.get(j).getLinkman().substring(0, 16));
                        } else {
                            tdHtMembAddress.setAdd_name(revAddressList.get(j).getLinkman());
                        }
                    }
                    tdHtMembAddress.setAdd_detail_address(revAddressList.get(j).getStreet());

                    for (int p = 0; p < provinceDtoList.size(); p++) {
                        String address = revAddressList.get(j).getAddress();
                        if (address != null) {
                            if (address.indexOf(provinceDtoList.get(p).getProvince_name()) >= 0) {
                                tdHtMembAddress.setAdd_province(provinceDtoList.get(p).getDict_id());
                                List<CityDto> cityDtoList = provinceDtoList.get(p).getCity_list();
                                for (int c = 0; c < cityDtoList.size(); c++) {
                                    if (address.indexOf(cityDtoList.get(c).getCity_name()) >= 0) {
                                        tdHtMembAddress.setAdd_city(cityDtoList.get(c).getDict_id());
                                        List<AreaDto> areaDtoList = cityDtoList.get(c).getArea_list();
                                        for (int a = 0; a < areaDtoList.size(); a++) {
                                            if (address.indexOf(areaDtoList.get(a).getArea_name()) >= 0) {
                                                tdHtMembAddress.setAdd_region(areaDtoList.get(a).getDict_id());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    tdHtMembAddress.setAdd_label(0);
                    if (revAddressList.get(j).getMobile_no() == null || "".equals(revAddressList.get(j).getMobile_no())) {
                        revAddressList.get(j).setMobile_no("18569034337");
                    }
                    boolean is_ok = MobileOrPhoneUtil.getIsMobile(revAddressList.get(j).getMobile_no());
                    if (is_ok) {
                        tdHtMembAddress.setAdd_mobile_phone(revAddressList.get(j).getMobile_no().replace("-", ""));
                        tdHtMembAddress.setAdd_telephone("");
                    } else {
                        tdHtMembAddress.setAdd_mobile_phone("");
                        tdHtMembAddress.setAdd_telephone(revAddressList.get(j).getMobile_no().replace("-", ""));
                    }
                    tdHtMembAddress.setAdd_longitude(0);
                    tdHtMembAddress.setAdd_latitude(0);
                    tdHtMembAddress.setAdd_id_number("");
                    tdHtMembAddress.setAdd_is_default(1);
                    tdHtMembAddress.setAdd_type(2);
                    tdHtMembAddress.setAdd_state(0);
                    tdHtMembAddress.setAdd_express_size(0);
                    testRepository.insertAddress(tdHtMembAddress);
                }

                List<ExpressRequest> expressRequestList = testRepository.findExpressRequestBuUser(user.getId());
                for (int j = 0; j < expressRequestList.size(); j++) {
                    TdHtExpressOrderDto tdHtExpressOrderDto = new TdHtExpressOrderDto();
                    tdHtExpressOrderDto.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtExpressOrderDto.setAgent_id(expressRequestList.get(j).getAgency_id());
                    tdHtExpressOrderDto.setAdd_id(0);
                    if (expressRequestList.get(j).getSender_name() == null) {
                        tdHtExpressOrderDto.setAdd_name("");
                    } else {
                        if (expressRequestList.get(j).getSender_name().length() > 16) {
                            tdHtExpressOrderDto.setAdd_name(expressRequestList.get(j).getSender_name().substring(0, 16));
                        } else {
                            tdHtExpressOrderDto.setAdd_name(expressRequestList.get(j).getSender_name());
                        }
                    }
                    tdHtExpressOrderDto.setAdd_id_number("");
                    tdHtExpressOrderDto.setAdd_detail_address(expressRequestList.get(j).getStreet());
                    for (int p = 0; p < provinceDtoList.size(); p++) {
                        String address = expressRequestList.get(j).getAddress();
                        if (address != null) {
                            if (address.indexOf(provinceDtoList.get(p).getProvince_name()) >= 0) {
                                tdHtExpressOrderDto.setAdd_province(provinceDtoList.get(p).getDict_id());
                                List<CityDto> cityDtoList = provinceDtoList.get(p).getCity_list();
                                for (int c = 0; c < cityDtoList.size(); c++) {
                                    if (address.indexOf(cityDtoList.get(c).getCity_name()) >= 0) {
                                        tdHtExpressOrderDto.setAdd_city(cityDtoList.get(c).getDict_id());
                                        List<AreaDto> areaDtoList = cityDtoList.get(c).getArea_list();
                                        for (int a = 0; a < areaDtoList.size(); a++) {
                                            if (address.indexOf(areaDtoList.get(a).getArea_name()) >= 0) {
                                                tdHtExpressOrderDto.setAdd_region(areaDtoList.get(a).getDict_id());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (expressRequestList.get(j).getSender_mobile_no() == null || "".equals(expressRequestList.get(j).getSender_mobile_no())) {
                        expressRequestList.get(j).setSender_mobile_no("18569034337");
                    }
                    boolean is_ok = MobileOrPhoneUtil.getIsMobile(expressRequestList.get(j).getSender_mobile_no());
                    if (is_ok) {
                        tdHtExpressOrderDto.setAdd_mobile_phone(expressRequestList.get(j).getSender_mobile_no().replace("-", ""));
                        tdHtExpressOrderDto.setAdd_telephone("");
                    } else {
                        tdHtExpressOrderDto.setAdd_mobile_phone("");
                        tdHtExpressOrderDto.setAdd_telephone(expressRequestList.get(j).getSender_mobile_no().replace("-", ""));
                    }
                    if (expressRequestList.get(j).getLongitude() == null) {
                        tdHtExpressOrderDto.setAdd_longitude(0);
                    } else {
                        tdHtExpressOrderDto.setAdd_longitude(expressRequestList.get(j).getLongitude().doubleValue());
                    }
                    if (expressRequestList.get(j).getLatitude() == null) {
                        tdHtExpressOrderDto.setAdd_latitude(0);
                    } else {
                        tdHtExpressOrderDto.setAdd_latitude(expressRequestList.get(j).getLatitude().doubleValue());
                    }
                    if (expressRequestList.get(j).getService_provider_id() == 7) {
                        tdHtExpressOrderDto.setExpress_id(28);
                        tdHtExpressOrderDto.setExp_ord_type(1);
                    } else if (expressRequestList.get(j).getService_provider_id() == 32) {
                        tdHtExpressOrderDto.setExpress_id(27);
                        tdHtExpressOrderDto.setExp_ord_type(1);
                    } else if (expressRequestList.get(j).getService_provider_id() == 68) {
                        tdHtExpressOrderDto.setExpress_id(30);
                        tdHtExpressOrderDto.setExp_ord_type(1);
                    } else if (expressRequestList.get(j).getService_provider_id() == 69) {
                        tdHtExpressOrderDto.setExpress_id(29);
                        tdHtExpressOrderDto.setExp_ord_type(1);
                    } else if (expressRequestList.get(j).getService_provider_id() == 4 || expressRequestList.get(j).getService_provider_id() == 70) {
                        tdHtExpressOrderDto.setExpress_id(3520);
                        tdHtExpressOrderDto.setExp_ord_type(2);
                    } else if (expressRequestList.get(j).getService_provider_id() == 6) {
                        tdHtExpressOrderDto.setExpress_id(3521);
                        tdHtExpressOrderDto.setExp_ord_type(3);
                    }
                    tdHtExpressOrderDto.setExp_ord_time(expressRequestList.get(j).getSubmit_time());
                    tdHtExpressOrderDto.setExp_ord_taking_time(expressRequestList.get(j).getSubmit_time());
                    tdHtExpressOrderDto.setDoor_start_time(expressRequestList.get(j).getSubmit_time());
                    tdHtExpressOrderDto.setDoor_end_time(new Date(expressRequestList.get(j).getSubmit_time().getTime() + 2 * 60 * 60 * 1000));
                    tdHtExpressOrderDto.setCollect_time(expressRequestList.get(j).getSubmit_time());
                    tdHtExpressOrderDto.setPay_time(expressRequestList.get(j).getSubmit_time());
                    tdHtExpressOrderDto.setStorge_time(expressRequestList.get(j).getSubmit_time());
                    if (expressRequestList.get(j).getStatus() == 0) {
                        tdHtExpressOrderDto.setExp_ord_state(0);
                    } else if (expressRequestList.get(j).getStatus() == 1) {
                        tdHtExpressOrderDto.setExp_ord_state(6);
                    } else if (expressRequestList.get(j).getStatus() == 20) {
                        tdHtExpressOrderDto.setExp_ord_state(2);
                    } else if (expressRequestList.get(j).getStatus() == 30) {
                        tdHtExpressOrderDto.setExp_ord_state(3);
                    } else if (expressRequestList.get(j).getStatus() == 40) {
                        tdHtExpressOrderDto.setExp_ord_state(4);
                    } else if (expressRequestList.get(j).getStatus() == 50) {
                        tdHtExpressOrderDto.setExp_ord_state(5);
                    } else if (expressRequestList.get(j).getStatus() == 200) {
                        tdHtExpressOrderDto.setExp_ord_state(5);
                    } else if (expressRequestList.get(j).getStatus() == 1030) {
                        tdHtExpressOrderDto.setExp_ord_state(3);
                    } else if (expressRequestList.get(j).getStatus() == 1200) {
                        tdHtExpressOrderDto.setExp_ord_state(5);
                    }
                    List<Express> expressList = testRepository.findExpressByRequest(expressRequestList.get(j).getId());
                    tdHtExpressOrderDto.setExp_ord_size(expressList.size());
                    if (expressRequestList.get(j).getGoods_memo() != null) {
                        if (expressRequestList.get(j).getGoods_memo().length() > 100) {
                            tdHtExpressOrderDto.setExp_ord_demand(expressRequestList.get(j).getGoods_memo().substring(0, 100));
                        } else {
                            tdHtExpressOrderDto.setExp_ord_demand(expressRequestList.get(j).getGoods_memo());
                        }
                    } else {
                        tdHtExpressOrderDto.setExp_ord_demand("");
                    }
                    tdHtExpressOrderDto.setExp_ord_number(CommonUtil.getOrderNub(expressRequestList.get(j).getSubmit_time()));
                    tdHtExpressOrderDto.setExp_ord_month(sdf_month.format(expressRequestList.get(j).getSubmit_time()));
                    tdHtExpressOrderDto.setExp_ord_gratuity(0);
                    tdHtExpressOrderDto.setAgent_note("");
                    BigDecimal total = new BigDecimal(0);
                    BigDecimal weight = new BigDecimal(0);
                    for (int k = 0; k < expressList.size(); k++) {
                        if (expressList.get(k).getService_fee() != null) {
                            total = total.add(expressList.get(k).getService_fee());
                        }
                        weight = weight.add(getIntNo(expressList.get(k).getWeight()));
                    }
                    tdHtExpressOrderDto.setExp_ord_weight(weight.doubleValue());
                    tdHtExpressOrderDto.setExp_ord_pay_amount(total.doubleValue());
                    tdHtExpressOrderDto.setExp_ord_amount(total.doubleValue());
                    testRepository.insertExpressOrder(tdHtExpressOrderDto);

                    for (int k = 0; k < expressList.size(); k++) {
                        TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = new TdHtExpressOrderCollectDto();
                        tdHtExpressOrderCollectDto.setExp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
                        if (expressList.get(k).getGoods_name() != null) {
                            if (expressList.get(k).getGoods_name().length() > 16) {
                                tdHtExpressOrderCollectDto.setExp_ord_category(expressList.get(k).getGoods_name().substring(0, 16));
                            } else {
                                tdHtExpressOrderCollectDto.setExp_ord_category(expressList.get(k).getGoods_name());
                            }
                        } else {
                            tdHtExpressOrderCollectDto.setExp_ord_category("");
                        }
                        tdHtExpressOrderCollectDto.setExp_ord_clt_height(getIntNo(expressList.get(k).getWeight()).doubleValue());
                        tdHtExpressOrderCollectDto.setAdd_id(0);
                        if (expressList.get(k).getRecipient_name() == null) {
                            tdHtExpressOrderCollectDto.setAdd_name("");
                        } else {
                            if (expressList.get(k).getRecipient_name().length() > 16) {
                                tdHtExpressOrderCollectDto.setAdd_name(expressList.get(k).getRecipient_name().substring(0, 16));
                            } else {
                                tdHtExpressOrderCollectDto.setAdd_name(expressList.get(k).getRecipient_name());
                            }
                        }
                        if (expressList.get(k).getRecipient_mobile_no() == null || "".equals(expressList.get(k).getRecipient_mobile_no())) {
                            expressList.get(k).setRecipient_mobile_no("18569034337");
                        }
                        boolean eeis_ok = MobileOrPhoneUtil.getIsMobile(expressList.get(k).getRecipient_mobile_no());
                        if (eeis_ok) {
                            tdHtExpressOrderCollectDto.setAdd_mobile_phone(expressList.get(k).getRecipient_mobile_no().replace("-", ""));
                            tdHtExpressOrderCollectDto.setAdd_telephone("");
                        } else {
                            tdHtExpressOrderCollectDto.setAdd_mobile_phone("");
                            tdHtExpressOrderCollectDto.setAdd_telephone(expressList.get(k).getRecipient_mobile_no().replace("-", ""));
                        }
                        tdHtExpressOrderCollectDto.setAdd_detail_address(expressList.get(k).getRecipient_street());
                        for (int p = 0; p < provinceDtoList.size(); p++) {
                            String address = expressList.get(k).getRecipient_address();
                            if (address != null) {
                                if (address.indexOf(provinceDtoList.get(p).getProvince_name()) >= 0) {
                                    tdHtExpressOrderCollectDto.setAdd_province(provinceDtoList.get(p).getDict_id());
                                    List<CityDto> cityDtoList = provinceDtoList.get(p).getCity_list();
                                    for (int c = 0; c < cityDtoList.size(); c++) {
                                        if (address.indexOf(cityDtoList.get(c).getCity_name()) >= 0) {
                                            tdHtExpressOrderCollectDto.setAdd_city(cityDtoList.get(c).getDict_id());
                                            List<AreaDto> areaDtoList = cityDtoList.get(c).getArea_list();
                                            for (int a = 0; a < areaDtoList.size(); a++) {
                                                if (address.indexOf(areaDtoList.get(a).getArea_name()) >= 0) {
                                                    tdHtExpressOrderCollectDto.setAdd_region(areaDtoList.get(a).getDict_id());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        tdHtExpressOrderCollectDto.setAdd_longitude(tdHtExpressOrderDto.getAdd_longitude());
                        tdHtExpressOrderCollectDto.setAdd_latitude(tdHtExpressOrderDto.getAdd_latitude());
                        if (tdHtExpressOrderDto.getExp_ord_state() == 0 || tdHtExpressOrderDto.getExp_ord_state() == 1) {
                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(0);
                        } else if (tdHtExpressOrderDto.getExp_ord_state() == 2 || tdHtExpressOrderDto.getExp_ord_state() == 3) {
                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(1);
                        } else if (tdHtExpressOrderDto.getExp_ord_state() == 6) {
                            tdHtExpressOrderCollectDto.setExp_ord_clt_state(5);
                        } else {
                            if (new Date().getTime() - tdHtExpressOrderDto.getExp_ord_time().getTime() > 3 * 24 * 60 * 60 * 1000) {
                                tdHtExpressOrderCollectDto.setExp_ord_clt_state(4);
                            } else {
                                tdHtExpressOrderCollectDto.setExp_ord_clt_state(2);
                            }
                        }
                        tdHtExpressOrderCollectDto.setExpress_number(expressList.get(k).getProvider_tracking_id());
                        tdHtExpressOrderCollectDto.setHt_number(expressList.get(k).getHotol_id());
                        tdHtExpressOrderCollectDto.setSign_time(tdHtExpressOrderDto.getExp_ord_time());
                        tdHtExpressOrderCollectDto.setSend_time(tdHtExpressOrderDto.getExp_ord_time());
                        tdHtExpressOrderCollectDto.setDelivery_time(tdHtExpressOrderDto.getExp_ord_time());
                        tdHtExpressOrderCollectDto.setExpress_id(String.valueOf(tdHtExpressOrderDto.getExpress_id()));
                        tdHtExpressOrderCollectDto.setCategory_id(0);
                        if (tdHtExpressOrderDto.getExpress_id() == 27) {
                            tdHtExpressOrderCollectDto.setExpress_name("天天快递");
                        } else if (tdHtExpressOrderDto.getExpress_id() == 28) {
                            tdHtExpressOrderCollectDto.setExpress_name("德邦快递");
                        } else if (tdHtExpressOrderDto.getExpress_id() == 29) {
                            tdHtExpressOrderCollectDto.setExpress_name("京东快递");
                        } else if (tdHtExpressOrderDto.getExpress_id() == 30) {
                            tdHtExpressOrderCollectDto.setExpress_name("速邮汇");
                        } else if (tdHtExpressOrderDto.getExpress_id() == 3520) {
                            tdHtExpressOrderCollectDto.setExpress_name("申通快递");
                        } else if (tdHtExpressOrderDto.getExpress_id() == 3521) {
                            tdHtExpressOrderCollectDto.setExpress_name("韵达快递");
                        }
                        tdHtExpressOrderCollectDto.setExpress_price(expressList.get(k).getService_fee());
                        testRepository.insertExpressOrderCollect(tdHtExpressOrderCollectDto);
                    }
                }

            }
            retInfo.setMark("0");
            retInfo.setTip("成功.");
            retInfo.setObj(mapList.size());
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            e.printStackTrace();
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo findAgent() {
        String logInfo = this.getClass().getName() + ":register:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            SimpleDateFormat sdf_month = new SimpleDateFormat("yyyyMM");
            List<Agency> agencyList = testRepository.findAgency();

            for (int i = 0; i < agencyList.size(); i++) {
                Agency agency = agencyList.get(i);
                TdHtAgentDto tdHtAgentDto = new TdHtAgentDto();
                tdHtAgentDto.setAgent_name(agency.getMobile_no());
                if (agency.getGender() == null || "".equals(agency.getGender())) {
                    tdHtAgentDto.setAgent_sex(3);
                } else if ("男".equals(agency.getGender())) {
                    tdHtAgentDto.setAgent_sex(1);
                } else if ("女".equals(agency.getGender())) {
                    tdHtAgentDto.setAgent_sex(2);
                }
                tdHtAgentDto.setAgent_phone(agency.getMobile_no());
                tdHtAgentDto.setAgent_id_number("");
                tdHtAgentDto.setAgent_state(1);
                tdHtAgentDto.setAgent_longitude(new BigDecimal(0));
                tdHtAgentDto.setAgent_latitude(new BigDecimal(0));
                tdHtAgentDto.setAgent_register_time(new Timestamp(agency.getReg_time().getTime()));
                tdHtAgentDto.setAgent_register_month(sdf_month.format(agency.getReg_time()));
                tdHtAgentDto.setAgent_adopt_time(new Timestamp(agency.getReg_time().getTime()));
                tdHtAgentDto.setAgent_password(agency.getPassword());
                tdHtAgentDto.setAgent_spare_phone("");
                tdHtAgentDto.setArea_id("0");
                tdHtAgentDto.setTotal_income(new BigDecimal(0));
                tdHtAgentDto.setAgent_balance(new BigDecimal(0));
                tdHtAgentDto.setAgent_head_portrait("");
                tdHtAgentDto.setAgent_birthday(new Timestamp(agency.getReg_time().getTime()));
                tdHtAgentDto.setAgent_email("");
                tdHtAgentDto.setAgent_address("");
                tdHtAgentDto.setAgent_pay_type(1);
                tdHtAgentDto.setAgent_pay_account("");
                List<Push> push = testRepository.findPushByAgency(agency.getId());
                if (push.size() == 0) {
                    tdHtAgentDto.setPush_token("");
                    tdHtAgentDto.setPush_type(0);
                } else {
                    if ("android".equals(push.get(0).getClient_from())) {
                        tdHtAgentDto.setPush_type(1);
                    } else if ("ios".equals(push.get(0).getClient_from())) {
                        tdHtAgentDto.setPush_type(2);
                    } else {
                        tdHtAgentDto.setPush_type(0);
                    }
                    tdHtAgentDto.setPush_token(push.get(0).getClient_id());
                }
                tdHtAgentDto.setToken(CommonUtil.getToken());
                tdHtAgentDto.setAgent_score(new BigDecimal(5.00));
                testRepository.insertAgent(tdHtAgentDto);

                testRepository.updateExpOrdAgent(tdHtAgentDto);
            }

            retInfo.setMark("0");
            retInfo.setTip("成功.");
            retInfo.setObj(agencyList.size());
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            e.printStackTrace();
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo findTest() {
        String logInfo = this.getClass().getName() + ":register:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            List<TdHtMembAddressDto> tdHtMembAddressDtoList = testRepository.findMembAddress();
            for (int i = 0; i < tdHtMembAddressDtoList.size(); i++) {
                TdHtMembAddressDto tdHtMembAddressDto = tdHtMembAddressDtoList.get(i);
                TsHtDictList p = tsHtDictRepository.findDictById(tdHtMembAddressDto.getAdd_province());
                TsHtDictList c = tsHtDictRepository.findDictById(tdHtMembAddressDto.getAdd_city());
                TsHtDictList r = tsHtDictRepository.findDictById(tdHtMembAddressDto.getAdd_region());
                BaiduMapUtil mapUtil = new BaiduMapUtil();
                String address = p.getCode_name() + c.getCode_name() + r.getCode_name() + tdHtMembAddressDto.getAdd_detail_address();
                LocationDto locationDto = mapUtil.changeAddress(address);
                if (locationDto != null) {
                    tdHtMembAddressDto.setAdd_latitude(locationDto.getLatitude().doubleValue());
                    tdHtMembAddressDto.setAdd_longitude(locationDto.getLongitude().doubleValue());
                    testRepository.updateMembAddress(tdHtMembAddressDto);
                }
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            e.printStackTrace();
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }


    /**
     * @param membId
     * @param frequency
     * @return RetInfo
     * @Purpose 用户注册获取邀请码
     * @version 3.0
     * @author lubin
     */
    public void createInviteCode(int membId, int frequency) {
        String logInfo = this.getClass().getName() + ":createInviteCode:" + "====第" + frequency + 1 + "次";
        logger.info("======" + logInfo + "begin======");
        try {
            int num = (int) (Math.random() * 90 + 10);
            String numStr = String.valueOf(num);
            String oneStr = numStr.substring(0, 1);
            String twoStr = numStr.substring(1, 2);
            String newStr = "";
            for (int i = 0; i < 6; i++) {
                if (i == Integer.valueOf(oneStr)) {
                    int intVal = (int) (Math.random() * 10 + 48);
                    newStr = newStr + (char) intVal;
                } else if (i == Integer.valueOf(twoStr)) {
                    int intVal = (int) (Math.random() * 10 + 48);
                    newStr = newStr + (char) intVal;
                } else {
                    int intVal = (int) (Math.random() * 26 + 65);
                    newStr = newStr + (char) intVal;
                }
            }

            int inviteNum = testRepository.findMembNumByInvite(newStr);
            if (inviteNum > 0) {
                if (frequency < 10) {
                    frequency = frequency + 1;
                    createInviteCode(membId, frequency);
                }
            } else {
                TdHtMemberDto tdHtMemberDto = new TdHtMemberDto();
                tdHtMemberDto.setMemb_id(membId);
                tdHtMemberDto.setMemb_invite_code(newStr);

                testRepository.updateMembInviteCode(tdHtMemberDto);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

    public BigDecimal getIntNo(String str) {
        String bbb = "";
        if (str != null) {
            if (str.indexOf(".") >= 0) {
                bbb = str.split("\\.")[0] + ".";
                str = str.split("\\.")[1];
            }
            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i))) {
                    bbb = bbb + str.charAt(i);
                } else {
                    break;
                }
            }
            if ("".equals(bbb)) {
                return new BigDecimal(0);
            } else {
                return new BigDecimal(bbb);
            }
        } else {
            return new BigDecimal(0);
        }
    }

    public RetInfo clearMemberCache() {
        String logInfo = this.getClass().getName() + ":clearMemberCache:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            int num = 0;
            List<TdHtMemberDto> memberDtos = testRepository.findAllMember();
            for (int i = 0; i < memberDtos.size(); i++) {
                TdHtMemberDto tdHtMemberDto = memberDtos.get(i);
                TdHtMemberDto memb = (TdHtMemberDto) MemcachedUtils.get(tdHtMemberDto.getToken());
                if (memb != null) {
                    MemcachedUtils.delete(tdHtMemberDto.getToken());
                    num = num + 1;
                }
            }
            logger.info("清除用户缓存" + num + "个");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            e.printStackTrace();
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }
}
