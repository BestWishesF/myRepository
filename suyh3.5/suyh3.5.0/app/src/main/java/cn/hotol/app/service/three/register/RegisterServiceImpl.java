package cn.hotol.app.service.three.register;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.TdHtMembScore;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.member.TdHtAgentMemberDto;
import cn.hotol.app.bean.dto.dict.TsHtDictAddressDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.location.AddressDto;
import cn.hotol.app.bean.dto.log.TdHtLoginLogDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.member.VerificationLoginDto;
import cn.hotol.app.bean.dto.message.ShortMessageDto;
import cn.hotol.app.bean.dto.register.RegisterDto;
import cn.hotol.app.bean.dto.score.TdHtMembScoreTaskDto;
import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */

@Service
public class RegisterServiceImpl implements RegisterService {

    private static Logger logger = Logger.getLogger(RegisterServiceImpl.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtLoginLogRepository tdHtLoginLogRepository;
    @Resource
    private TdHtMembScoreRepository tdHtMembScoreRepository;
    @Resource
    private TdHtMembScoreTaskRepository tdHtMembScoreTaskRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;
    @Resource
    private TdHtCouponRepository tdHtCouponRepository;
    @Resource
    private TdHtAgentMemberRepository tdHtAgentMemberRepository;
    @Resource
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;
    @Resource
    private TsHtDictRepository tsHtDictRepository;

    /**
     * @param registerDto
     * @return RetInfo
     * @Purpose 用户注册
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo register(RegisterDto registerDto, String ip) {
        String logInfo = this.getClass().getName() + ":register:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMemberByMembPhone(registerDto.getMemb_phone());

            //处理用户注册经纬度为空
            if(registerDto.getMemb_register_latitude() == null){
                registerDto.setMemb_register_latitude(new BigDecimal(0));
            }
            if(registerDto.getMemb_register_longitude() == null){
                registerDto.setMemb_register_longitude(new BigDecimal(0));
            }
            if(registerDto.getDevice_number() == null){
                registerDto.setDevice_number("");
            }

            //验证该手机号是否有注册
            if (tdHtMemberDto != null) {
                if (tdHtMemberDto.getMemb_password() != null && !"".equals(tdHtMemberDto.getMemb_password())) {
                    retInfo.setMark("1");
                    retInfo.setTip("您的手机号已注册.");
                } else {
                    //取出缓存中的注册手机验证码
                    ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.REGISTRATION_VERIFICATION_CODE + registerDto.getMemb_phone());
                    //验证验证码是否有效
                    if (shortMessageDto == null) {
                        retInfo.setMark("2");
                        retInfo.setTip("您还没有获取验证码或验证码已过期，请重新获取.");
                    } else {
                        if (!shortMessageDto.getPhoneno().equals(registerDto.getMemb_phone())) {
                            retInfo.setMark("3");
                            retInfo.setTip("您输入的手机号和获取验证码的手机号不匹配.");
                        } else {
                            if (!shortMessageDto.getVerification_code().equals(registerDto.getVerification_code())) {
                                retInfo.setMark("4");
                                retInfo.setTip("您输入的验证码不正确，请重新输入.");
                            } else {
                                //获取首次登录奖励积分
                                Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
                                TsHtScoreTaskDto tsHtScoreTaskDto = scoreTask.get(Constant.FIRST_LOGIN_TASK_ID);

                                //注册新用户

                                Long date = System.currentTimeMillis();
                                tdHtMemberDto.setMemb_phone(registerDto.getMemb_phone());
                                tdHtMemberDto.setMemb_password(PassEncryptionUtils.digest(registerDto.getMemb_password()));
                                tdHtMemberDto.setMemb_register_time(new Timestamp(date));
                                tdHtMemberDto.setMemb_register_month(CommonUtil.getMonth());
                                tdHtMemberDto.setMemb_score(tsHtScoreTaskDto.getSt_score());
                                tdHtMemberDto.setToken(CommonUtil.getToken());
                                tdHtMemberDto.setPush_token(registerDto.getPush_token());
                                tdHtMemberDto.setPush_type(registerDto.getPush_type());
                                tdHtMemberDto.setChannel_id(registerDto.getChannel_id());
                                tdHtMemberDto.setMemb_register_client(registerDto.getPush_type());
                                tdHtMemberDto.setMemb_register_longitude(registerDto.getMemb_register_longitude());
                                tdHtMemberDto.setMemb_register_latitude(registerDto.getMemb_register_latitude());
                                tdHtMemberDto.setMemb_register_region(registerDto.getMemb_register_region());
                                tdHtMemberDto.setDevice_number(registerDto.getDevice_number());

                                /**
                                 * 推送标识唯一，修改已存在的标识
                                 */
                                TdHtMemberDto member = new TdHtMemberDto();
                                member.setPush_token(registerDto.getPush_token());
                                member.setPush_type(registerDto.getPush_type());
                                List<TdHtMemberDto> members = tdHtMemberRepository.findMemberIdByPush(member);
                                for (int i = 0; i < members.size(); i++) {
                                    TdHtMemberDto memberDto = members.get(i);
                                    if (memberDto.getPush_token() != null && !"".equals(memberDto.getPush_token())) {
                                        memberDto.setPush_token("");
                                        tdHtMemberRepository.updateExistingPushToken(memberDto);
                                    }
                                }

                                if(!"".equals(registerDto.getDevice_number())){
                                    tdHtMemberRepository.updateMembDevice(registerDto.getDevice_number());
                                }

                                if(tdHtMemberDto.getMemb_islock() == 2){
                                    tdHtMemberDto.setMemb_islock(1);

                                    //通过经纬度得到行政区id
                                    if (tdHtMemberDto.getMemb_register_latitude().doubleValue() > 0 && tdHtMemberDto.getMemb_register_longitude().doubleValue() > 0 && tdHtMemberDto.getMemb_register_region() == 0) {
                                        //通过经纬度得到省市区名称
                                        BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                                        AddressDto addressDto = baiduMapUtil.changeLngAndLat(tdHtMemberDto.getMemb_register_latitude(), tdHtMemberDto.getMemb_register_longitude());

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
                                                        tdHtMemberDto.setMemb_register_region(regionList.get(0).getDict_id());
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if(tdHtMemberDto.getMemb_register_region() > 0){
                                        int divide_id = PointUtil.getDivideId(tdHtMemberDto.getMemb_register_region(), tdHtMemberDto.getMemb_register_longitude().doubleValue(), tdHtMemberDto.getMemb_register_latitude().doubleValue());
                                        tdHtMemberDto.setDivide_id(divide_id);
                                    }

                                    //生成用户的邀请码
                                    createInviteCode(tdHtMemberDto.getMemb_id(), 0);
                                }


                                tdHtMemberRepository.updateRegMember(tdHtMemberDto);

                                TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                                tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                                tdHtLoginLogDto.setLogin_ip(ip);
                                tdHtLoginLogDto.setLogin_type(tdHtMemberDto.getPush_type());
                                tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                                tdHtLoginLogDto.setUser_id(tdHtMemberDto.getMemb_id());
                                tdHtLoginLogDto.setUser_name(tdHtMemberDto.getMemb_nick_name());
                                tdHtLoginLogDto.setUser_type(1);

                                retInfo.setMark("0");
                                retInfo.setTip("登陆成功");
                                retInfo.setObj(tdHtMemberDto.getToken());

                                tdHtLoginLogDto.setIs_success(0);
                                tdHtLoginLogDto.setLogin_failer_desc("登陆成功.");
                                //添加登陆日志
                                tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
                                MemcachedUtils.delete(MemcachedKey.REGISTRATION_VERIFICATION_CODE + registerDto.getMemb_phone());

                                //插入用户积分变化记录表
                                TdHtMembScore tdHtMembScore = new TdHtMembScore();
                                tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                                tdHtMembScore.setScore_change_point(tdHtMemberDto.getMemb_score());
                                tdHtMembScore.setScore_front_point(0);
                                tdHtMembScore.setScore_back_point(tdHtMemberDto.getMemb_score());
                                tdHtMembScore.setScore_month(CommonUtil.getMonth());
                                tdHtMembScore.setScore_time(new java.util.Date());
                                tdHtMembScore.setScore_reason(tsHtScoreTaskDto.getSt_name());
                                tdHtMembScore.setScore_type(1);
                                tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                                //插入用户积分任务完成记录表
                                TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                                tdHtMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
                                tdHtMembScoreTaskDto.setSt_id(tsHtScoreTaskDto.getSt_id());
                                tdHtMembScoreTaskDto.setSt_state(tsHtScoreTaskDto.getSt_state());
                                tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                                tdHtMembScoreTaskDto.setSt_img(tsHtScoreTaskDto.getSt_img());
                                tdHtMembScoreTaskDto.setSt_name(tsHtScoreTaskDto.getSt_name());
                                tdHtMembScoreTaskDto.setSt_score(tsHtScoreTaskDto.getSt_score());
                                tdHtMembScoreTaskDto.setSt_synopsis(tsHtScoreTaskDto.getSt_synopsis());
                                tdHtMembScoreTaskDto.setSt_type(tsHtScoreTaskDto.getSt_type());
                                tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                                tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);


                                //把用户信息放入缓存
                                MemcachedUtils.add(tdHtMemberDto.getToken(), tdHtMemberDto, new Date(20 * 24 * 60 * 60 * 1000));
                            }
                        }
                    }
                }
            } else {
                //取出缓存中的注册手机验证码
                ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.REGISTRATION_VERIFICATION_CODE + registerDto.getMemb_phone());
                //验证验证码是否有效
                if (shortMessageDto == null) {
                    retInfo.setMark("2");
                    retInfo.setTip("您还没有获取验证码或验证码已过期，请重新获取.");
                } else {
                    if (!shortMessageDto.getPhoneno().equals(registerDto.getMemb_phone())) {
                        retInfo.setMark("3");
                        retInfo.setTip("您输入的手机号和获取验证码的手机号不匹配.");
                    } else {
                        if (!shortMessageDto.getVerification_code().equals(registerDto.getVerification_code())) {
                            retInfo.setMark("4");
                            retInfo.setTip("您输入的验证码不正确，请重新输入.");
                        } else {
                            //获取首次登录奖励积分
                            Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
                            TsHtScoreTaskDto tsHtScoreTaskDto = scoreTask.get(Constant.FIRST_LOGIN_TASK_ID);

                            //注册新用户
                            TdHtMemberDto registerUser = new TdHtMemberDto();
                            Long date = System.currentTimeMillis();
                            registerUser.setMemb_phone(registerDto.getMemb_phone());
                            registerUser.setMemb_password(PassEncryptionUtils.digest(registerDto.getMemb_password()));
                            registerUser.setMemb_sex(3);
                            registerUser.setMemb_birthday(new Timestamp(date));
                            registerUser.setMemb_nick_name(registerDto.getMemb_phone());
                            registerUser.setMemb_head_portrait(Constant.DEFAULT_HEAD_IMG);
                            registerUser.setMemb_register_time(new Timestamp(date));
                            registerUser.setMemb_register_month(CommonUtil.getMonth());
                            registerUser.setMemb_balance(new BigDecimal(0));
                            registerUser.setMemb_name("");
                            registerUser.setMemb_id_number("");
                            registerUser.setMemb_islock(1);
                            registerUser.setMemb_score(tsHtScoreTaskDto.getSt_score());
                            registerUser.setMemb_invite_code("");
                            registerUser.setMemb_role(1);
                            registerUser.setToken(CommonUtil.getToken());
                            registerUser.setPush_token(registerDto.getPush_token());
                            registerUser.setPush_type(registerDto.getPush_type());
                            registerUser.setChannel_id(registerDto.getChannel_id());
                            registerUser.setMemb_register_client(registerDto.getPush_type());
                            registerUser.setMemb_register_longitude(registerDto.getMemb_register_longitude());
                            registerUser.setMemb_register_latitude(registerDto.getMemb_register_latitude());
                            registerUser.setMemb_register_region(registerDto.getMemb_register_region());
                            registerUser.setMemb_discount(new BigDecimal(1));
                            registerUser.setDevice_number(registerDto.getDevice_number());

                            //通过经纬度得到行政区id
                            if (registerDto.getMemb_register_latitude().doubleValue() > 0 && registerDto.getMemb_register_longitude().doubleValue() > 0 && registerDto.getMemb_register_region() == 0) {
                                //通过经纬度得到省市区名称
                                BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                                AddressDto addressDto = baiduMapUtil.changeLngAndLat(registerDto.getMemb_register_latitude(), registerDto.getMemb_register_longitude());

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
                                                registerUser.setMemb_register_region(regionList.get(0).getDict_id());
                                            }
                                        }
                                    }
                                }
                            }

                            if(registerUser.getMemb_register_region() > 0){
                                int divide_id = PointUtil.getDivideId(registerUser.getMemb_register_region(), registerUser.getMemb_register_longitude().doubleValue(), registerUser.getMemb_register_latitude().doubleValue());
                                registerUser.setDivide_id(divide_id);
                            }

                            /**
                             * 推送标识唯一，修改已存在的标识
                             */
                            TdHtMemberDto member = new TdHtMemberDto();
                            member.setPush_token(registerDto.getPush_token());
                            member.setPush_type(registerDto.getPush_type());
                            List<TdHtMemberDto> members = tdHtMemberRepository.findMemberIdByPush(member);
                            for (int i = 0; i < members.size(); i++) {
                                TdHtMemberDto memberDto = members.get(i);
                                if (memberDto.getPush_token() != null && !"".equals(memberDto.getPush_token())) {
                                    memberDto.setPush_token("");
                                    tdHtMemberRepository.updateExistingPushToken(memberDto);
                                }
                            }

                            if(!"".equals(registerDto.getDevice_number())){
                                tdHtMemberRepository.updateMembDevice(registerDto.getDevice_number());
                            }

                            tdHtMemberRepository.insertMember(registerUser);

                            //生成用户的邀请码
                            createInviteCode(registerUser.getMemb_id(), 0);

                            TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                            tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                            tdHtLoginLogDto.setLogin_ip(ip);
                            tdHtLoginLogDto.setLogin_type(registerUser.getPush_type());
                            tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                            tdHtLoginLogDto.setUser_id(registerUser.getMemb_id());
                            tdHtLoginLogDto.setUser_name(registerUser.getMemb_nick_name());
                            tdHtLoginLogDto.setUser_type(1);

                            retInfo.setMark("0");
                            retInfo.setTip("登陆成功");
                            retInfo.setObj(registerUser.getToken());

                            tdHtLoginLogDto.setIs_success(0);
                            tdHtLoginLogDto.setLogin_failer_desc("登陆成功.");
                            //添加登陆日志
                            tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
                            MemcachedUtils.delete(MemcachedKey.REGISTRATION_VERIFICATION_CODE + registerDto.getMemb_phone());

                            //插入用户积分变化记录表
                            TdHtMembScore tdHtMembScore = new TdHtMembScore();
                            tdHtMembScore.setMemb_id(registerUser.getMemb_id());
                            tdHtMembScore.setScore_change_point(registerUser.getMemb_score());
                            tdHtMembScore.setScore_front_point(0);
                            tdHtMembScore.setScore_back_point(registerUser.getMemb_score());
                            tdHtMembScore.setScore_month(CommonUtil.getMonth());
                            tdHtMembScore.setScore_time(new java.util.Date());
                            tdHtMembScore.setScore_reason(tsHtScoreTaskDto.getSt_name());
                            tdHtMembScore.setScore_type(1);
                            tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                            //插入用户积分任务完成记录表
                            TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                            tdHtMembScoreTaskDto.setMemb_id(registerUser.getMemb_id());
                            tdHtMembScoreTaskDto.setSt_id(tsHtScoreTaskDto.getSt_id());
                            tdHtMembScoreTaskDto.setSt_state(tsHtScoreTaskDto.getSt_state());
                            tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                            tdHtMembScoreTaskDto.setSt_img(tsHtScoreTaskDto.getSt_img());
                            tdHtMembScoreTaskDto.setSt_name(tsHtScoreTaskDto.getSt_name());
                            tdHtMembScoreTaskDto.setSt_score(tsHtScoreTaskDto.getSt_score());
                            tdHtMembScoreTaskDto.setSt_synopsis(tsHtScoreTaskDto.getSt_synopsis());
                            tdHtMembScoreTaskDto.setSt_type(tsHtScoreTaskDto.getSt_type());
                            tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                            tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                            //给4~6元优惠券
                            long now = System.currentTimeMillis();
                            Timestamp endTime = new Timestamp(now + 20 * 24 * 60 * 60 * 1000);
                            TdHtCoupon tdHtCoupon = new TdHtCoupon();
                            tdHtCoupon.setCou_discount(new BigDecimal(1));
                            tdHtCoupon.setCou_limit_amount(new BigDecimal(0));
                            tdHtCoupon.setCou_receive_time(new Timestamp(now));
                            tdHtCoupon.setCou_user_time(new Timestamp(now));
                            tdHtCoupon.setExp_ord_id(0);
                            tdHtCoupon.setMemb_id(registerUser.getMemb_id());
                            tdHtCoupon.setState(0);
                            tdHtCoupon.setCou_limit_time(endTime);
                            tdHtCoupon.setRegion_id(0);
                            tdHtCoupon.setExpress_id(0);
                            tdHtCoupon.setMemb_type(2);
                            if (now >= 1488902400000L && now < 1489593599000L) {
                                tdHtCoupon.setCou_amount(new BigDecimal(5));
                                tdHtCoupon.setCou_name("新用户5元立减优惠券");
                            } else {
                                RandomPrizeUtil randomPrizeUtil = new RandomPrizeUtil();
                                int money = randomPrizeUtil.getGumByListTwo();
                                tdHtCoupon.setCou_amount(new BigDecimal(money));
                                tdHtCoupon.setCou_name("新用户" + money + "元立减优惠券");
                            }
                            tdHtCoupon.setLimit_exp_ord_id(0);
                            tdHtCouponRepository.insertMembCoupon(tdHtCoupon);

                            //把用户信息放入缓存
                            MemcachedUtils.add(registerUser.getToken(), registerUser, new Date(20 * 24 * 60 * 60 * 1000));

                            //查询推广码
                            if (registerDto.getPromote_code() != null) {
                                if (registerDto.getPromote_code().contains("hotolagent")) {
                                    String code = registerDto.getPromote_code().replace("hotolagent", "");
                                    int agent_id = Integer.valueOf(code);
                                    //查找代理人代理人不为空时 添加用户代理人关联信信息 代理人新增资金变化记录
                                    TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agent_id);
                                    if (tdHtAgentDto != null) {
                                        Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);

                                        TdHtAgentMemberDto tdHtAgentMemberDto = new TdHtAgentMemberDto();
                                        tdHtAgentMemberDto.setAgent_id(agent_id);
                                        tdHtAgentMemberDto.setMemb_id(registerUser.getMemb_id());
                                        tdHtAgentMemberDto.setFirst_single_reward(new BigDecimal(dataConfigMap.get(Constant.AGENT_EXTENSION_FIRST_REWARD).toString()));
                                        tdHtAgentMemberDto.setRegister_reward(new BigDecimal(dataConfigMap.get(Constant.AGENT_EXTENSION_REG_REWARD).toString()));
                                        tdHtAgentMemberRepository.insertTdHtAgentMember(tdHtAgentMemberDto);

                                        TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto = new TdHtAgentFoundChangeDto();
                                        tdHtAgentFoundChangeDto.setAfchg_change_amount(tdHtAgentMemberDto.getRegister_reward());
                                        tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(System.currentTimeMillis()));
                                        tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                                        tdHtAgentFoundChangeDto.setAfchg_type(1);
                                        tdHtAgentFoundChangeDto.setAfchg_state(2);
                                        tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub());
                                        tdHtAgentFoundChangeDto.setAfchg_name("推广注册奖励");
                                        tdHtAgentFoundChangeDto.setAgent_id(agent_id);
                                        tdHtAgentFoundChangeDto.setAfchg_front_amount(tdHtAgentDto.getAgent_balance());
                                        tdHtAgentFoundChangeDto.setAfchg_back_amount(tdHtAgentDto.getAgent_balance().add(tdHtAgentFoundChangeDto.getAfchg_change_amount()));
                                        tdHtAgentFoundChangeDto.setExp_ord_id(0);
                                        tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);

                                    }
                                }
                            }

                        }
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
     * @param membId
     * @param frequency
     * @return RetInfo
     * @Purpose 用户注册获取邀请码
     * @version 3.0
     * @author lubin
     */
    @Override
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

            int inviteNum = tdHtMemberRepository.findMembNumByInvite(newStr);
            if (inviteNum > 0) {
                if (frequency < 10) {
                    frequency = frequency + 1;
                    createInviteCode(membId, frequency);
                }
            } else {
                TdHtMemberDto tdHtMemberDto = new TdHtMemberDto();
                tdHtMemberDto.setMemb_id(membId);
                tdHtMemberDto.setMemb_invite_code(newStr);

                tdHtMemberRepository.updateMembInviteCode(tdHtMemberDto);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

    /**
     * @param registerDto
     * @return RetInfo
     * @Purpose 代理人注册
     * @version 3.0
     * @author lubin
     */
    @Override
    public synchronized RetInfo agentRegister(RegisterDto registerDto, String ip) {
        String logInfo = this.getClass().getName() + ":register:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentByPhone(registerDto.getMemb_phone());

            //验证该手机号是否有注册
            if (tdHtAgentDto != null) {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号已注册.");
            } else {
                //取出缓存中的注册手机验证码
                ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.AGENT_REGISTRATION_VERIFICATION_CODE + registerDto.getMemb_phone());
                //验证验证码是否有效
                if (shortMessageDto == null) {
                    retInfo.setMark("2");
                    retInfo.setTip("您还没有获取验证码或验证码已过期，请重新获取.");
                } else {
                    if (!shortMessageDto.getPhoneno().equals(registerDto.getMemb_phone())) {
                        retInfo.setMark("3");
                        retInfo.setTip("您输入的手机号和获取验证码的手机号不匹配.");
                    } else {
                        if (!shortMessageDto.getVerification_code().equals(registerDto.getVerification_code())) {
                            retInfo.setMark("4");
                            retInfo.setTip("您输入的验证码不正确，请重新输入.");
                        } else {
                            //注册新代理人
                            TdHtAgentDto registerAgent = new TdHtAgentDto();
                            Long date = System.currentTimeMillis();
                            registerAgent.setAgent_name(registerDto.getMemb_phone());
                            registerAgent.setAgent_sex(3);
                            registerAgent.setAgent_phone(registerDto.getMemb_phone());
                            registerAgent.setAgent_id_number("");
                            registerAgent.setAgent_state(1);
                            registerAgent.setAgent_longitude(new BigDecimal(0));
                            registerAgent.setAgent_latitude(new BigDecimal(0));
                            registerAgent.setAgent_register_time(new Timestamp(date));
                            registerAgent.setAgent_register_month(CommonUtil.getMonth());
                            registerAgent.setAgent_adopt_time(new Timestamp(date));
                            registerAgent.setAgent_password(PassEncryptionUtils.digest(registerDto.getMemb_password()));
                            registerAgent.setAgent_spare_phone("");
                            registerAgent.setArea_id(0);
                            registerAgent.setTotal_income(new BigDecimal(0));
                            registerAgent.setAgent_balance(new BigDecimal(0));
                            registerAgent.setAgent_head_portrait("");
                            registerAgent.setAgent_birthday(new Timestamp(date));
                            registerAgent.setAgent_email("");
                            registerAgent.setAgent_address("");
                            registerAgent.setAgent_pay_type(0);
                            registerAgent.setAgent_pay_account("");
                            registerAgent.setPush_token(registerDto.getPush_token());
                            registerAgent.setPush_type(registerDto.getPush_type());
                            registerAgent.setToken(CommonUtil.getToken());
                            registerAgent.setApp_version(registerDto.getApp_version());

                            /**
                             * 推送标识唯一，修改已存在的标识
                             */
                            TdHtAgentDto agent = new TdHtAgentDto();
                            agent.setPush_token(registerDto.getPush_token());
                            agent.setPush_type(registerDto.getPush_type());
                            List<TdHtAgentDto> agents = tdHtAgentRepository.findAgentIdByPush(agent);
                            for (int i = 0; i < agents.size(); i++) {
                                TdHtAgentDto agentDto = agents.get(i);
                                if (agentDto.getPush_token() != null && !"".equals(agentDto.getPush_token())) {
                                    agentDto.setPush_token("");
                                    tdHtAgentRepository.updateAgentPushToken(agentDto);
                                }
                            }

                            tdHtAgentRepository.insertAgent(registerAgent);

                            TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                            tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                            tdHtLoginLogDto.setLogin_ip(ip);
                            tdHtLoginLogDto.setLogin_type(registerAgent.getPush_type());
                            tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                            tdHtLoginLogDto.setUser_id(registerAgent.getAgent_id());
                            tdHtLoginLogDto.setUser_name(registerAgent.getAgent_name());
                            tdHtLoginLogDto.setUser_type(2);

                            retInfo.setMark("0");
                            retInfo.setTip("登陆成功");
                            retInfo.setObj(registerAgent.getToken());

                            tdHtLoginLogDto.setIs_success(0);
                            tdHtLoginLogDto.setLogin_failer_desc("登陆成功.");
                            //添加登陆日志
                            tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
                            MemcachedUtils.delete(MemcachedKey.AGENT_REGISTRATION_VERIFICATION_CODE + registerDto.getMemb_phone());

                            //把用户信息放入缓存
                            MemcachedUtils.add(registerAgent.getToken(), registerAgent, new Date(20 * 24 * 60 * 60 * 1000));
                        }
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
     * @Purpose   用户短信验证码登录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     verificationLoginDto  登录信息
     * @return    RetInfo
     */
    @Override
    public RetInfo verificationLogin(VerificationLoginDto verificationLoginDto, String ip) {
        String logInfo = this.getClass().getName() + ":login:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //取出缓存中的注册手机验证码
            ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.VERIFICATION_LOGIN_VERIFICATION_CODE + verificationLoginDto.getMemb_phone());
            //验证验证码是否有效
            boolean is_verification = false;
            if (shortMessageDto == null) {
                retInfo.setMark("2");
                retInfo.setTip("您还没有获取验证码或验证码已过期，请重新获取.");
                is_verification = true;
            } else {
                if (!shortMessageDto.getPhoneno().equals(verificationLoginDto.getMemb_phone())) {
                    retInfo.setMark("3");
                    retInfo.setTip("您输入的手机号和获取验证码的手机号不匹配.");
                    is_verification = true;
                } else {
                    if (!shortMessageDto.getVerification_code().equals(verificationLoginDto.getVerification_code())) {
                        retInfo.setMark("4");
                        retInfo.setTip("您输入的验证码不正确，请重新输入.");
                        is_verification = true;
                    }
                }
            }

            if(!is_verification){
                //处理用户注册经纬度为空
                if(verificationLoginDto.getMemb_register_latitude() == null){
                    verificationLoginDto.setMemb_register_latitude(new BigDecimal(0));
                }
                if(verificationLoginDto.getMemb_register_longitude() == null){
                    verificationLoginDto.setMemb_register_longitude(new BigDecimal(0));
                }
                if(verificationLoginDto.getDevice_number() == null){
                    verificationLoginDto.setDevice_number("");
                }

                //判断用户是否已注册
                TdHtMemberDto member = tdHtMemberRepository.findMemberByMembPhone(verificationLoginDto.getMemb_phone());
                if (member != null) {
                    //记录用户登录日志
                    TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                    tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                    tdHtLoginLogDto.setLogin_ip(ip);
                    tdHtLoginLogDto.setLogin_type(verificationLoginDto.getPush_type());
                    tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                    tdHtLoginLogDto.setUser_id(member.getMemb_id());
                    tdHtLoginLogDto.setUser_name(member.getMemb_nick_name());
                    tdHtLoginLogDto.setUser_type(1);

                    //判断用户是否已锁定
                    if (member.getMemb_islock() == 1 || member.getMemb_islock() == 2) {
                        //删除原缓存
                        MemcachedUtils.delete(member.getToken());
                        //更新用户token,推送标示,推送类别
                        member.setToken(CommonUtil.getToken());
                        member.setPush_token(verificationLoginDto.getPush_token());
                        member.setPush_type(verificationLoginDto.getPush_type());
                        /**
                         * 推送标识唯一，修改已存在的标识
                         */
                        List<TdHtMemberDto> members = tdHtMemberRepository.findMemberIdByPush(member);
                        for (int i = 0; i < members.size(); i++) {
                            TdHtMemberDto memberDto = members.get(i);
                            if (memberDto.getPush_token() != null && !"".equals(memberDto.getPush_token())) {
                                memberDto.setPush_token("");
                                tdHtMemberRepository.updateExistingPushToken(memberDto);
                            }
                        }

                        if(!"".equals(verificationLoginDto.getDevice_number())){
                            tdHtMemberRepository.updateMembDevice(verificationLoginDto.getDevice_number());
                        }

                        if(member.getMemb_islock() == 2){
                            member.setMemb_register_region(verificationLoginDto.getMemb_register_region());

                            //通过经纬度得到行政区id
                            if (verificationLoginDto.getMemb_register_latitude().doubleValue() > 0 && verificationLoginDto.getMemb_register_longitude().doubleValue() > 0 && member.getMemb_register_region() == 0) {
                                //通过经纬度得到省市区名称
                                BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                                AddressDto addressDto = baiduMapUtil.changeLngAndLat(verificationLoginDto.getMemb_register_latitude(), verificationLoginDto.getMemb_register_longitude());

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
                                                member.setMemb_register_region(regionList.get(0).getDict_id());
                                            }
                                        }
                                    }
                                }
                            }

                            if(member.getMemb_register_region() > 0){
                                int divide_id = PointUtil.getDivideId(member.getMemb_register_region(), verificationLoginDto.getMemb_register_longitude().doubleValue(), verificationLoginDto.getMemb_register_latitude().doubleValue());
                                member.setDivide_id(divide_id);
                            }

                            member.setChannel_id(verificationLoginDto.getChannel_id());
                            member.setMemb_register_client(verificationLoginDto.getPush_type());

                            //生成用户的邀请码
                            createInviteCode(member.getMemb_id(), 0);
                        }
                        member.setMemb_register_longitude(verificationLoginDto.getMemb_register_longitude());
                        member.setMemb_register_latitude(verificationLoginDto.getMemb_register_latitude());

                        //获取首次登录奖励积分
                        Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
                        TsHtScoreTaskDto tsHtScoreTaskDto = scoreTask.get(Constant.FIRST_LOGIN_TASK_ID);

                        TdHtMembScoreTaskDto scoreTaskDto = new TdHtMembScoreTaskDto();
                        scoreTaskDto.setSt_id(tsHtScoreTaskDto.getSt_id());
                        scoreTaskDto.setMemb_id(member.getMemb_id());
                        int count = tdHtMembScoreTaskRepository.findMembNoviceTaskCount(scoreTaskDto);
                        if(count == 0){
                            //插入用户积分变化记录表
                            TdHtMembScore tdHtMembScore = new TdHtMembScore();
                            tdHtMembScore.setMemb_id(member.getMemb_id());
                            tdHtMembScore.setScore_change_point(member.getMemb_score());
                            tdHtMembScore.setScore_front_point(0);
                            tdHtMembScore.setScore_back_point(member.getMemb_score());
                            tdHtMembScore.setScore_month(CommonUtil.getMonth());
                            tdHtMembScore.setScore_time(new java.util.Date());
                            tdHtMembScore.setScore_reason(tsHtScoreTaskDto.getSt_name());
                            tdHtMembScore.setScore_type(1);
                            tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                            //插入用户积分任务完成记录表
                            TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                            tdHtMembScoreTaskDto.setMemb_id(member.getMemb_id());
                            tdHtMembScoreTaskDto.setSt_id(tsHtScoreTaskDto.getSt_id());
                            tdHtMembScoreTaskDto.setSt_state(tsHtScoreTaskDto.getSt_state());
                            tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                            tdHtMembScoreTaskDto.setSt_img(tsHtScoreTaskDto.getSt_img());
                            tdHtMembScoreTaskDto.setSt_name(tsHtScoreTaskDto.getSt_name());
                            tdHtMembScoreTaskDto.setSt_score(tsHtScoreTaskDto.getSt_score());
                            tdHtMembScoreTaskDto.setSt_synopsis(tsHtScoreTaskDto.getSt_synopsis());
                            tdHtMembScoreTaskDto.setSt_type(tsHtScoreTaskDto.getSt_type());
                            tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                            tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                            //给4~6元优惠券
                            long now = System.currentTimeMillis();
                            Timestamp endTime = new Timestamp(now + 20 * 24 * 60 * 60 * 1000);
                            TdHtCoupon tdHtCoupon = new TdHtCoupon();
                            tdHtCoupon.setCou_discount(new BigDecimal(1));
                            tdHtCoupon.setCou_limit_amount(new BigDecimal(0));
                            tdHtCoupon.setCou_receive_time(new Timestamp(now));
                            tdHtCoupon.setCou_user_time(new Timestamp(now));
                            tdHtCoupon.setExp_ord_id(0);
                            tdHtCoupon.setMemb_id(member.getMemb_id());
                            tdHtCoupon.setState(0);
                            tdHtCoupon.setCou_limit_time(endTime);
                            tdHtCoupon.setRegion_id(0);
                            tdHtCoupon.setExpress_id(0);
                            tdHtCoupon.setMemb_type(2);
                            RandomPrizeUtil randomPrizeUtil = new RandomPrizeUtil();
                            int money = randomPrizeUtil.getGumByListTwo();
                            tdHtCoupon.setCou_amount(new BigDecimal(money));
                            tdHtCoupon.setCou_name("新用户" + money + "元立减优惠券");
                            tdHtCoupon.setLimit_exp_ord_id(0);

                            tdHtCouponRepository.insertMembCoupon(tdHtCoupon);

                            member.setMemb_score(tsHtScoreTaskDto.getSt_score() + member.getMemb_score());
                        }

                        member.setDevice_number(verificationLoginDto.getDevice_number());
                        member.setMemb_islock(1);
                        member.setApp_version(verificationLoginDto.getApp_version());
                        tdHtMemberRepository.updateRegMember(member);

                        tdHtLoginLogDto.setIs_success(0);
                        tdHtLoginLogDto.setLogin_failer_desc("登陆成功");

                        //把用户信息放入缓存
                        MemcachedUtils.add(member.getToken(), member, new Date(20 * 24 * 60 * 60 * 1000));

                        Map<String, Object> map = new HashMap();
                        map.put("token", member.getToken());
                        map.put("memb_role", member.getMemb_role());
                        map.put("memb_discount", member.getMemb_discount());

                        retInfo.setMark("0");
                        retInfo.setTip("登陆成功");
                        retInfo.setObj(map);
                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("用户已锁定,如有疑问请联系客服.");
                        tdHtLoginLogDto.setIs_success(1);
                        tdHtLoginLogDto.setLogin_failer_desc("用户已锁定");
                    }
                    //添加登陆日志
                    tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
                }else {
                    //获取首次登录奖励积分
                    Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
                    TsHtScoreTaskDto tsHtScoreTaskDto = scoreTask.get(Constant.FIRST_LOGIN_TASK_ID);

                    //注册新用户
                    TdHtMemberDto registerUser = new TdHtMemberDto();
                    Long date = System.currentTimeMillis();
                    registerUser.setMemb_phone(verificationLoginDto.getMemb_phone());
                    registerUser.setMemb_password("");
                    registerUser.setMemb_sex(3);
                    registerUser.setMemb_birthday(new Timestamp(date));
                    registerUser.setMemb_nick_name(verificationLoginDto.getMemb_phone());
                    registerUser.setMemb_head_portrait(Constant.DEFAULT_HEAD_IMG);
                    registerUser.setMemb_register_time(new Timestamp(date));
                    registerUser.setMemb_register_month(CommonUtil.getMonth());
                    registerUser.setMemb_balance(new BigDecimal(0));
                    registerUser.setMemb_name("");
                    registerUser.setMemb_id_number("");
                    registerUser.setMemb_islock(1);
                    registerUser.setMemb_score(tsHtScoreTaskDto.getSt_score());
                    registerUser.setMemb_invite_code("");
                    registerUser.setMemb_role(1);
                    registerUser.setToken(CommonUtil.getToken());
                    registerUser.setPush_token(verificationLoginDto.getPush_token());
                    registerUser.setPush_type(verificationLoginDto.getPush_type());
                    registerUser.setChannel_id(verificationLoginDto.getChannel_id());
                    registerUser.setMemb_register_client(verificationLoginDto.getPush_type());
                    registerUser.setMemb_register_longitude(verificationLoginDto.getMemb_register_longitude());
                    registerUser.setMemb_register_latitude(verificationLoginDto.getMemb_register_latitude());
                    registerUser.setMemb_register_region(verificationLoginDto.getMemb_register_region());
                    registerUser.setMemb_discount(new BigDecimal(1));
                    registerUser.setDevice_number(verificationLoginDto.getDevice_number());
                    registerUser.setApp_version(verificationLoginDto.getApp_version());

                    //通过经纬度得到行政区id
                    if (verificationLoginDto.getMemb_register_latitude().doubleValue() > 0 && verificationLoginDto.getMemb_register_longitude().doubleValue() > 0 && verificationLoginDto.getMemb_register_region() == 0) {
                        //通过经纬度得到省市区名称
                        BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                        AddressDto addressDto = baiduMapUtil.changeLngAndLat(verificationLoginDto.getMemb_register_latitude(), verificationLoginDto.getMemb_register_longitude());

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
                                        registerUser.setMemb_register_region(regionList.get(0).getDict_id());
                                    }
                                }
                            }
                        }
                    }

                    if(registerUser.getMemb_register_region() > 0){
                        int divide_id = PointUtil.getDivideId(registerUser.getMemb_register_region(), verificationLoginDto.getMemb_register_longitude().doubleValue(), verificationLoginDto.getMemb_register_latitude().doubleValue());
                        registerUser.setDivide_id(divide_id);
                    }

                    /**
                     * 推送标识唯一，修改已存在的标识
                     */
                    TdHtMemberDto tdHtMemberDto = new TdHtMemberDto();
                    tdHtMemberDto.setPush_token(verificationLoginDto.getPush_token());
                    tdHtMemberDto.setPush_type(verificationLoginDto.getPush_type());
                    List<TdHtMemberDto> members = tdHtMemberRepository.findMemberIdByPush(tdHtMemberDto);
                    for (int i = 0; i < members.size(); i++) {
                        TdHtMemberDto memberDto = members.get(i);
                        if (memberDto.getPush_token() != null && !"".equals(memberDto.getPush_token())) {
                            memberDto.setPush_token("");
                            tdHtMemberRepository.updateExistingPushToken(memberDto);
                        }
                    }

                    if(!"".equals(verificationLoginDto.getDevice_number())){
                        tdHtMemberRepository.updateMembDevice(verificationLoginDto.getDevice_number());
                    }

                    tdHtMemberRepository.insertMember(registerUser);

                    Map<String, Object> map = new HashMap();
                    map.put("token", registerUser.getToken());
                    map.put("memb_role", registerUser.getMemb_role());
                    map.put("memb_discount", registerUser.getMemb_discount());

                    retInfo.setMark("0");
                    retInfo.setTip("登陆成功");
                    retInfo.setObj(map);

                    //生成用户的邀请码
                    createInviteCode(registerUser.getMemb_id(), 0);

                    TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                    tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                    tdHtLoginLogDto.setLogin_ip(ip);
                    tdHtLoginLogDto.setLogin_type(registerUser.getPush_type());
                    tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                    tdHtLoginLogDto.setUser_id(registerUser.getMemb_id());
                    tdHtLoginLogDto.setUser_name(registerUser.getMemb_nick_name());
                    tdHtLoginLogDto.setUser_type(1);

                    tdHtLoginLogDto.setIs_success(0);
                    tdHtLoginLogDto.setLogin_failer_desc("登陆成功.");
                    //添加登陆日志
                    tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);

                    //插入用户积分变化记录表
                    TdHtMembScore tdHtMembScore = new TdHtMembScore();
                    tdHtMembScore.setMemb_id(registerUser.getMemb_id());
                    tdHtMembScore.setScore_change_point(registerUser.getMemb_score());
                    tdHtMembScore.setScore_front_point(0);
                    tdHtMembScore.setScore_back_point(registerUser.getMemb_score());
                    tdHtMembScore.setScore_month(CommonUtil.getMonth());
                    tdHtMembScore.setScore_time(new java.util.Date());
                    tdHtMembScore.setScore_reason(tsHtScoreTaskDto.getSt_name());
                    tdHtMembScore.setScore_type(1);
                    tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                    //插入用户积分任务完成记录表
                    TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                    tdHtMembScoreTaskDto.setMemb_id(registerUser.getMemb_id());
                    tdHtMembScoreTaskDto.setSt_id(tsHtScoreTaskDto.getSt_id());
                    tdHtMembScoreTaskDto.setSt_state(tsHtScoreTaskDto.getSt_state());
                    tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                    tdHtMembScoreTaskDto.setSt_img(tsHtScoreTaskDto.getSt_img());
                    tdHtMembScoreTaskDto.setSt_name(tsHtScoreTaskDto.getSt_name());
                    tdHtMembScoreTaskDto.setSt_score(tsHtScoreTaskDto.getSt_score());
                    tdHtMembScoreTaskDto.setSt_synopsis(tsHtScoreTaskDto.getSt_synopsis());
                    tdHtMembScoreTaskDto.setSt_type(tsHtScoreTaskDto.getSt_type());
                    tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                    tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                    //给4~6元优惠券
                    long now = System.currentTimeMillis();
                    Timestamp endTime = new Timestamp(now + 20 * 24 * 60 * 60 * 1000);
                    TdHtCoupon tdHtCoupon = new TdHtCoupon();
                    tdHtCoupon.setCou_discount(new BigDecimal(1));
                    tdHtCoupon.setCou_limit_amount(new BigDecimal(0));
                    tdHtCoupon.setCou_receive_time(new Timestamp(now));
                    tdHtCoupon.setCou_user_time(new Timestamp(now));
                    tdHtCoupon.setExp_ord_id(0);
                    tdHtCoupon.setMemb_id(registerUser.getMemb_id());
                    tdHtCoupon.setState(0);
                    tdHtCoupon.setCou_limit_time(endTime);
                    tdHtCoupon.setRegion_id(0);
                    tdHtCoupon.setExpress_id(0);
                    tdHtCoupon.setMemb_type(2);
                    RandomPrizeUtil randomPrizeUtil = new RandomPrizeUtil();
                    int money = randomPrizeUtil.getGumByListTwo();
                    tdHtCoupon.setCou_amount(new BigDecimal(money));
                    tdHtCoupon.setCou_name("新用户" + money + "元立减优惠券");
                    tdHtCoupon.setLimit_exp_ord_id(0);

                    tdHtCouponRepository.insertMembCoupon(tdHtCoupon);

                    //把用户信息放入缓存
                    MemcachedUtils.add(registerUser.getToken(), registerUser, new Date(20 * 24 * 60 * 60 * 1000));
                }
                MemcachedUtils.delete(MemcachedKey.VERIFICATION_LOGIN_VERIFICATION_CODE + verificationLoginDto.getMemb_phone());
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
