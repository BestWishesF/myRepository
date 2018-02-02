package cn.hotol.app.service.three.thirdparty;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.dto.dict.TsHtDictAddressDto;
import cn.hotol.app.bean.dto.location.AddressDto;
import cn.hotol.app.bean.dto.log.TdHtLoginLogDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.message.ShortMessageDto;
import cn.hotol.app.bean.dto.thirdlogin.BindingPhoneDto;
import cn.hotol.app.bean.dto.thirdlogin.TdHtMembThirdLoginDto;
import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import cn.hotol.app.service.three.register.RegisterService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LuBin
 * Date 2017-01-03.
 */

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    private static Logger logger = Logger.getLogger(ThirdPartyServiceImpl.class);

    @Resource
    private TdHtMembThirdLoginRepository tdHtMembThirdLoginRepository;
    @Resource
    private RegisterService registerService;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtLoginLogRepository tdHtLoginLogRepository;
    @Resource
    private TdHtCouponRepository tdHtCouponRepository;
    @Resource
    private TsHtDictRepository tsHtDictRepository;

    /**
     * @param third_id
     * @param code
     * @param ip
     * @return RetInfo
     * @Purpose 微信用户登录
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo obtainWXPersonal(String third_id, String code, String ip, int version) {
        String logInfo = this.getClass().getName() + ":obtainWXPersonal:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, TsHtThirdDto> map = (Map<String, TsHtThirdDto>) MemcachedUtils.get(MemcachedKey.THIRD_PARTY_MAP);
            TsHtThirdDto tsHtThirdDto = map.get(third_id);
            Map<String, Object> accessTokenMap = WeChatHttpsUtil.obtainAccessToken(tsHtThirdDto, code);
            TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
            Long nowDate = System.currentTimeMillis();
            String nowMonth = CommonUtil.getMonth();
            if (accessTokenMap != null) {
                String openid = accessTokenMap.get("openid").toString();
                String access_token = accessTokenMap.get("access_token").toString();
                Map<String, Object> weChatUserMap = WeChatHttpsUtil.obtainWeChatUserInfo(access_token, openid);
                if (weChatUserMap != null) {
                    String nickname = weChatUserMap.get("nickname").toString();
                    String sex = weChatUserMap.get("sex").toString();
                    if ("0".equals(sex)) {
                        sex = "3";
                    }
                    String headimgurl = weChatUserMap.get("headimgurl").toString();

                    TdHtMembThirdLoginDto membThirdLogin = new TdHtMembThirdLoginDto();
                    membThirdLogin.setThird_id(Integer.valueOf(third_id));
                    membThirdLogin.setThr_token(openid);
                    membThirdLogin.setThr_nick_name(EmojiFilter.filterEmoji(nickname + " "));
                    membThirdLogin.setThr_sex(Integer.valueOf(sex));
                    membThirdLogin.setThr_head_poration(headimgurl);

                    //通过第三方标识查询用户第三方登录信息
                    TdHtMembThirdLoginDto tdHtMembThirdLoginDto = tdHtMembThirdLoginRepository.findMembThirdLogin(membThirdLogin);
                    if (tdHtMembThirdLoginDto == null) {
                        TdHtMemberDto registerUser = new TdHtMemberDto();
                        registerUser.setMemb_phone("");
                        registerUser.setMemb_password("");
                        registerUser.setMemb_sex(membThirdLogin.getThr_sex());
                        registerUser.setMemb_birthday(new Timestamp(nowDate));
                        if(membThirdLogin.getThr_nick_name().length()>16){
                            registerUser.setMemb_nick_name(membThirdLogin.getThr_nick_name().substring(0,16));
                        }else {
                            registerUser.setMemb_nick_name(membThirdLogin.getThr_nick_name());
                        }
                        registerUser.setMemb_head_portrait(membThirdLogin.getThr_head_poration());
                        registerUser.setMemb_register_time(new Timestamp(nowDate));
                        registerUser.setMemb_register_month(nowMonth);
                        registerUser.setMemb_balance(new BigDecimal(0));
                        registerUser.setMemb_name("");
                        registerUser.setMemb_id_number("");
                        registerUser.setMemb_islock(1);
                        registerUser.setMemb_score(0);
                        registerUser.setMemb_invite_code("");
                        registerUser.setMemb_role(1);
                        registerUser.setToken(CommonUtil.getToken());
                        registerUser.setPush_token(openid);
                        registerUser.setPush_type(3);
                        registerUser.setMemb_discount(new BigDecimal(1));
                        registerUser.setChannel_id(0);
                        registerUser.setMemb_register_client(3);
                        registerUser.setMemb_register_longitude(new BigDecimal(0));
                        registerUser.setMemb_register_latitude(new BigDecimal(0));
                        registerUser.setMemb_register_region(0);
                        registerUser.setDevice_number("");
                        registerUser.setApp_version(version);
                        registerUser.setDivide_id(0);
                        tdHtMemberRepository.insertMember(registerUser);

                        //生成用户的邀请码
                        registerService.createInviteCode(registerUser.getMemb_id(), 0);

                        membThirdLogin.setMemb_id(registerUser.getMemb_id());
                        membThirdLogin.setThr_register_month(nowMonth);
                        membThirdLogin.setThr_register_time(new Timestamp(nowDate));
                        membThirdLogin.setThr_type(1);
                        tdHtMembThirdLoginRepository.insertMembThirdLogin(membThirdLogin);

                        //把用户信息放入缓存
                        MemcachedUtils.add(registerUser.getToken(), registerUser, new java.sql.Date(20 * 24 * 60 * 60 * 1000));

                        tdHtLoginLogDto.setLogin_type(registerUser.getPush_type());
                        tdHtLoginLogDto.setUser_id(registerUser.getMemb_id());
                        tdHtLoginLogDto.setUser_name(registerUser.getMemb_nick_name());

                        Map tokenMap = new HashMap();
                        tokenMap.put("token", "");
                        tokenMap.put("openid", openid);
                        retInfo.setObj(tokenMap);
                    } else {
                        membThirdLogin.setThr_id(tdHtMembThirdLoginDto.getThr_id());
                        tdHtMembThirdLoginRepository.updateMembThirdLogin(membThirdLogin);

                        TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(tdHtMembThirdLoginDto.getMemb_id());
                        MemcachedUtils.delete(tdHtMemberDto.getToken());

                        tdHtMemberDto.setMemb_head_portrait(membThirdLogin.getThr_head_poration());
                        if(membThirdLogin.getThr_nick_name().length()>16){
                            tdHtMemberDto.setMemb_nick_name(membThirdLogin.getThr_nick_name().substring(0,16));
                        }else {
                            tdHtMemberDto.setMemb_nick_name(membThirdLogin.getThr_nick_name());
                        }
                        tdHtMemberDto.setMemb_sex(membThirdLogin.getThr_sex());
                        tdHtMemberDto.setToken(CommonUtil.getToken());
                        tdHtMemberRepository.updateMembThirdInfo(tdHtMemberDto);

                        MemcachedUtils.add(tdHtMemberDto.getToken(), tdHtMemberDto, new java.sql.Date(20 * 24 * 60 * 60 * 1000));

                        tdHtLoginLogDto.setLogin_type(tdHtMemberDto.getPush_type());
                        tdHtLoginLogDto.setUser_id(tdHtMemberDto.getMemb_id());
                        tdHtLoginLogDto.setUser_name(tdHtMemberDto.getMemb_nick_name());

                        Map tokenMap = new HashMap();
                        if (tdHtMemberDto.getMemb_phone() != null && !"".equals(tdHtMemberDto.getMemb_phone())) {
                            tokenMap.put("token", tdHtMemberDto.getToken());
                            tokenMap.put("memb_role", tdHtMemberDto.getMemb_role());
                            tokenMap.put("memb_discount", tdHtMemberDto.getMemb_discount());
                        } else {
                            tokenMap.put("token", "");
                        }
                        tokenMap.put("openid", openid);
                        retInfo.setObj(tokenMap);
                    }
                    tdHtLoginLogDto.setIs_success(0);
                    tdHtLoginLogDto.setLogin_failer_desc("登陆成功.");
                    retInfo.setMark("0");
                    retInfo.setTip("登陆成功");
                } else {
                    tdHtLoginLogDto.setIs_success(1);
                    tdHtLoginLogDto.setLogin_failer_desc("没有获取到用户信息.");
                    retInfo.setMark("1");
                    retInfo.setTip("没有获取到用户信息");
                }
            } else {
                tdHtLoginLogDto.setIs_success(1);
                tdHtLoginLogDto.setLogin_failer_desc("没有获取到微信授权凭证.");
                retInfo.setMark("1");
                retInfo.setTip("没有获取到微信授权凭证");
            }
            tdHtLoginLogDto.setUser_type(1);
            tdHtLoginLogDto.setLogin_date(nowMonth);
            tdHtLoginLogDto.setLogin_time(new Timestamp(nowDate));
            tdHtLoginLogDto.setLogin_ip(ip);
            //添加登陆日志
            tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tsHtThirdDto
     * @return RetInfo
     * @Purpose 查询微信公众号配置
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findTsHtThirdDto(TsHtThirdDto tsHtThirdDto) {
        String logInfo = this.getClass().getName() + ":findTsHtThirdDto:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, TsHtThirdDto> tsHtThirdDtoMap = (Map<String, TsHtThirdDto>) MemcachedUtils.get(MemcachedKey.THIRD_PARTY_MAP);
            TsHtThirdDto tsHtThird = tsHtThirdDtoMap.get("" + tsHtThirdDto.getThird_id());

            retInfo.setMark("0");
            retInfo.setObj(tsHtThird.getAppid());
            retInfo.setTip("");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param bindingPhoneDto
     * @return RetInfo
     * @Purpose 绑定手机号
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public synchronized RetInfo bindingPhone(BindingPhoneDto bindingPhoneDto) {
        String logInfo = this.getClass().getName() + ":bindingPhone:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //取出缓存中的注册手机验证码
            ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.WE_CHAT_BINDING_VERIFICATION_CODE + bindingPhoneDto.getPhone());
            //验证验证码是否有效
            if (shortMessageDto == null) {
                retInfo.setMark("2");
                retInfo.setTip("您还没有获取验证码或验证码已过期，请重新获取.");
            } else {
                if (!shortMessageDto.getPhoneno().equals(bindingPhoneDto.getPhone())) {
                    retInfo.setMark("3");
                    retInfo.setTip("您输入的手机号和获取验证码的手机号不匹配.");
                } else {
                    if (!shortMessageDto.getVerification_code().equals(bindingPhoneDto.getVerification_code())) {
                        retInfo.setMark("4");
                        retInfo.setTip("您输入的验证码不正确，请重新输入.");
                    } else {
                        TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMemberByMembPhone(bindingPhoneDto.getPhone());
                        TdHtMembThirdLoginDto tdHtMembThirdLoginDto = tdHtMembThirdLoginRepository.findMemberThirdByToken(bindingPhoneDto.getOpenid());

                        //处理用户绑定时经纬度为空
                        if(bindingPhoneDto.getLongitude() == null){
                            bindingPhoneDto.setLongitude(new BigDecimal(0));
                        }
                        if(bindingPhoneDto.getLatitude() == null){
                            bindingPhoneDto.setLatitude(new BigDecimal(0));
                        }

                        //通过经纬度得到行政区id
                        if (bindingPhoneDto.getLatitude().doubleValue() > 0 && bindingPhoneDto.getLongitude().doubleValue() > 0) {
                            //通过经纬度得到省市区名称
                            BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                            AddressDto addressDto = baiduMapUtil.changeLngAndLat(bindingPhoneDto.getLatitude(), bindingPhoneDto.getLongitude());

                            if(addressDto != null){
                                //获取省名称
                                TsHtDictAddressDto province = new TsHtDictAddressDto();
                                province.setCode_name(addressDto.getProvince());
                                province.setParent_id(0);

                                //匹配省
                                List<TsHtDictAddressDto> provinceList = tsHtDictRepository.findDictByParentName(province);

                                if (provinceList.size() > 0 ) {
                                    //获取市名称
                                    TsHtDictAddressDto city = new TsHtDictAddressDto();
                                    city.setCode_name(addressDto.getCity());
                                    city.setParent_id(provinceList.get(0).getDict_id());

                                    //匹配市
                                    List<TsHtDictAddressDto> cityList = tsHtDictRepository.findDictByParentName(city);

                                    if (cityList.size() > 0) {
                                        //获取行政区名称
                                        TsHtDictAddressDto region = new TsHtDictAddressDto();
                                        region.setCode_name(addressDto.getDistrict());
                                        region.setParent_id(cityList.get(0).getDict_id());

                                        //匹配行政区
                                        List<TsHtDictAddressDto> regionList = tsHtDictRepository.findDictByParentName(region);

                                        if (regionList.size() > 0) {
                                            bindingPhoneDto.setMemb_register_region(regionList.get(0).getDict_id());
                                        }
                                    }
                                }
                            }

                            if(bindingPhoneDto.getMemb_register_region() > 0){
                                int divide_id = PointUtil.getDivideId(bindingPhoneDto.getMemb_register_region(), bindingPhoneDto.getLongitude().doubleValue(), bindingPhoneDto.getLatitude().doubleValue());
                                bindingPhoneDto.setDivide_id(divide_id);
                            }
                        }

                        //判断手机号是否已经注册过app
                        if (tdHtMemberDto == null) {
                            TdHtMemberDto memberDto = tdHtMemberRepository.findMembByMembId(tdHtMembThirdLoginDto.getMemb_id());
                            memberDto.setMemb_phone(bindingPhoneDto.getPhone());
                            memberDto.setMemb_register_latitude(bindingPhoneDto.getLatitude());
                            memberDto.setMemb_register_longitude(bindingPhoneDto.getLongitude());
                            memberDto.setDevice_number(bindingPhoneDto.getDevice_number());
                            memberDto.setApp_version(bindingPhoneDto.getApp_version());
                            memberDto.setMemb_register_region(bindingPhoneDto.getMemb_register_region());
                            memberDto.setDivide_id(bindingPhoneDto.getDivide_id());

                            tdHtMemberRepository.updateMemberBindingPhone(memberDto);
                            retInfo.setObj(memberDto.getToken());

                            //给1~3元优惠券
                            long now = System.currentTimeMillis();
                            Timestamp endTime = new Timestamp(now + 20 * 24 * 60 * 60 * 1000);
                            RandomPrizeUtil randomPrizeUtil = new RandomPrizeUtil();
                            int money = randomPrizeUtil.getGumByListTwo();
                            TdHtCoupon tdHtCoupon = new TdHtCoupon();
                            tdHtCoupon.setCou_amount(new BigDecimal(money));
                            tdHtCoupon.setCou_discount(new BigDecimal(1));
                            tdHtCoupon.setCou_limit_amount(new BigDecimal(0));
                            tdHtCoupon.setCou_limit_time(endTime);
                            tdHtCoupon.setCou_name("新用户" + money + "元立减优惠券");
                            tdHtCoupon.setCou_receive_time(new Timestamp(now));
                            tdHtCoupon.setCou_user_time(new Timestamp(now));
                            tdHtCoupon.setExp_ord_id(0);
                            tdHtCoupon.setMemb_id(memberDto.getMemb_id());
                            tdHtCoupon.setState(0);
                            tdHtCoupon.setRegion_id(0);
                            tdHtCoupon.setExpress_id(0);
                            tdHtCoupon.setMemb_type(2);
                            tdHtCoupon.setLimit_exp_ord_id(0);
                            tdHtCouponRepository.insertMembCoupon(tdHtCoupon);

                            //把用户信息放入缓存
                            MemcachedUtils.delete(memberDto.getToken());
                            MemcachedUtils.add(memberDto.getToken(), memberDto, new java.sql.Date(20 * 24 * 60 * 60 * 1000));
                        } else {
                            tdHtMemberRepository.deleteThirdMemberInfo(tdHtMembThirdLoginDto.getMemb_id());
                            tdHtMembThirdLoginDto.setMemb_id(tdHtMemberDto.getMemb_id());
                            TdHtMembThirdLoginDto memberThirdLoginDto = tdHtMembThirdLoginRepository.findMembThirdByBean(tdHtMembThirdLoginDto);
                            if (memberThirdLoginDto != null) {
                                tdHtMembThirdLoginRepository.deleteThirdMembLogin(memberThirdLoginDto.getThr_id());
                            }
                            tdHtMembThirdLoginRepository.updateMemberIdByThrId(tdHtMembThirdLoginDto);
                            if(tdHtMemberDto.getMemb_islock()==2){
                                tdHtMemberDto.setMemb_islock(1);
                                tdHtMemberDto.setMemb_register_time(new Timestamp(System.currentTimeMillis()));
                                tdHtMemberDto.setMemb_register_month(CommonUtil.getMonth());
                                tdHtMemberDto.setMemb_register_latitude(bindingPhoneDto.getLatitude());
                                tdHtMemberDto.setMemb_register_longitude(bindingPhoneDto.getLongitude());
                                tdHtMemberDto.setMemb_register_region(bindingPhoneDto.getMemb_register_region());
                                tdHtMemberDto.setDevice_number(bindingPhoneDto.getDevice_number());
                                tdHtMemberDto.setApp_version(bindingPhoneDto.getApp_version());
                                tdHtMemberDto.setDivide_id(bindingPhoneDto.getDivide_id());
                                tdHtMemberRepository.updateRegMember(tdHtMemberDto);
                                //生成用户的邀请码
                                registerService.createInviteCode(tdHtMemberDto.getMemb_id(), 0);

                                //给1~3元优惠券
                                long now = System.currentTimeMillis();
                                Timestamp endTime = new Timestamp(now + 20 * 24 * 60 * 60 * 1000);
                                RandomPrizeUtil randomPrizeUtil = new RandomPrizeUtil();
                                int money = randomPrizeUtil.getGumByListTwo();
                                TdHtCoupon tdHtCoupon = new TdHtCoupon();
                                tdHtCoupon.setCou_amount(new BigDecimal(money));
                                tdHtCoupon.setCou_discount(new BigDecimal(1));
                                tdHtCoupon.setCou_limit_amount(new BigDecimal(0));
                                tdHtCoupon.setCou_limit_time(endTime);
                                tdHtCoupon.setCou_name("新用户" + money + "元立减优惠券");
                                tdHtCoupon.setCou_receive_time(new Timestamp(now));
                                tdHtCoupon.setCou_user_time(new Timestamp(now));
                                tdHtCoupon.setExp_ord_id(0);
                                tdHtCoupon.setMemb_id(tdHtMemberDto.getMemb_id());
                                tdHtCoupon.setState(0);
                                tdHtCoupon.setRegion_id(0);
                                tdHtCoupon.setExpress_id(0);
                                tdHtCoupon.setMemb_type(2);
                                tdHtCoupon.setLimit_exp_ord_id(0);
                                tdHtCouponRepository.insertMembCoupon(tdHtCoupon);
                            }
                            retInfo.setObj(tdHtMemberDto.getToken());

                            //把用户信息放入缓存
                            MemcachedUtils.delete(tdHtMemberDto.getToken());
                            MemcachedUtils.add(tdHtMemberDto.getToken(), tdHtMemberDto, new java.sql.Date(20 * 24 * 60 * 60 * 1000));
                        }
                        retInfo.setMark("0");
                        retInfo.setTip("绑定成功.");
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
     * @param appid
     * @param http_url
     * @return RetInfo
     * @Purpose 微信使用权限签名
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo obtainJsSdk(String appid, String http_url) {
        String logInfo = this.getClass().getName() + ":obtainJsSdk:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            String js_sdk = (String) MemcachedUtils.get(MemcachedKey.WE_CHAT_JSSDK_TOKEN + appid);
            if (js_sdk == null) {
                retInfo.setMark("1");
                retInfo.setTip("js_sdk获取失败.");
            } else {
                long time_stamp = System.currentTimeMillis() / 1000;
                String nonce_str = UUID.randomUUID().toString();
                String signature = SignUtilSDK.getSignature(js_sdk, nonce_str, time_stamp, http_url);
                if (signature == null) {
                    retInfo.setMark("1");
                    retInfo.setTip("随机字符串获取失败.");
                } else {
                    Map map = new HashMap();
                    map.put("appid", appid);
                    map.put("time_stamp", time_stamp);
                    map.put("nonce_str", nonce_str);
                    map.put("signature", signature);

                    logger.info("======:" + js_sdk + ":js_sdk======");
                    logger.info("======" + time_stamp + "time_stamp======");
                    logger.info("======" + nonce_str + "nonce_str======");
                    logger.info("======" + signature + "signature======");
                    logger.info("======" + http_url + "http_url======");

                    retInfo.setMark("0");
                    retInfo.setTip("获取成功.");
                    retInfo.setObj(map);
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
}
