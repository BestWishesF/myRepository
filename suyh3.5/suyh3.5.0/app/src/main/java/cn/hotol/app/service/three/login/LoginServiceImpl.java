package cn.hotol.app.service.three.login;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.apply.TdHtAgentApplyDto;
import cn.hotol.app.bean.dto.log.TdHtLoginLogDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.TdHtAgentApplyRepository;
import cn.hotol.app.repository.TdHtAgentRepository;
import cn.hotol.app.repository.TdHtLoginLogRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录方法类
 * Created by LuBin
 * Date 2016-11-29.
 */

@Service
public class LoginServiceImpl implements LoginService {

    private static Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtLoginLogRepository tdHtLoginLogRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;
    @Resource
    private TdHtAgentApplyRepository tdHtAgentApplyRepository;

    /**
     * @param tdHtMemberDto
     * @param ip
     * @return RetInfo
     * @Purpose 用户登陆
     * @version 3.0
     * @author lizhun
     */
    @Transactional
    @Override
    public RetInfo login(TdHtMemberDto tdHtMemberDto, String ip) {
        String logInfo = this.getClass().getName() + ":login:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto member = tdHtMemberRepository.findMemberByMembPhone(tdHtMemberDto.getMemb_phone());
            //判断用户是否已注册
            if (member != null) {

                if (member.getMemb_password() == null || "".equals(member.getMemb_password()) || member.getMemb_islock() == 2) {
                    if(tdHtMemberDto.getApp_version() >= 7){
                        retInfo.setMark("1");
                        retInfo.setTip("您还未设置密码，请使用短信验证码登录.");
                    }else {
                        retInfo.setMark("1");
                        retInfo.setTip("您的手机号尚未注册,请先注册.");
                    }
                } else {
                    TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                    tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                    tdHtLoginLogDto.setLogin_ip(ip);
                    tdHtLoginLogDto.setLogin_type(tdHtMemberDto.getPush_type());
                    tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                    tdHtLoginLogDto.setUser_id(member.getMemb_id());
                    tdHtLoginLogDto.setUser_name(member.getMemb_nick_name());
                    tdHtLoginLogDto.setUser_type(1);

                    //判断用户是否已锁定
                    if (member.getMemb_islock() == 1) {
                        //判断密码是否正确
                        if (PassEncryptionUtils.digest(tdHtMemberDto.getMemb_password()).equals(member.getMemb_password())) {

                            //删除原缓存
                            MemcachedUtils.delete(member.getToken());

                            //更新用户token,推送标示,推送类别
                            member.setToken(CommonUtil.getToken());
                            member.setPush_token(tdHtMemberDto.getPush_token());
                            member.setPush_type(tdHtMemberDto.getPush_type());

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

                            tdHtMemberRepository.updateMemberLogin(member);
                            //
                            tdHtLoginLogDto.setIs_success(0);
                            tdHtLoginLogDto.setLogin_failer_desc("登陆成功");
                            //把用户信息放入缓存
                            MemcachedUtils.add(member.getToken(), member, new Date(20 * 24 * 60 * 60 * 1000));

                            retInfo.setMark("0");
                            retInfo.setTip("登陆成功");
                            retInfo.setObj(member.getToken());

                        } else {
                            retInfo.setMark("1");
                            retInfo.setTip("密码不正确");
                            tdHtLoginLogDto.setIs_success(1);
                            tdHtLoginLogDto.setLogin_failer_desc("密码不正确");
                        }

                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("用户已锁定,如有疑问请联系客服.");
                        tdHtLoginLogDto.setIs_success(1);
                        tdHtLoginLogDto.setLogin_failer_desc("用户已锁定");
                    }
                    //添加登陆日志
                    tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
                }

            } else {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号尚未注册,请先注册.");
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
     * @param tdHtAgentDto
     * @param ip
     * @return RetInfo
     * @Purpose 代理人登陆
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo agentLogin(TdHtAgentDto tdHtAgentDto, String ip) {
        String logInfo = this.getClass().getName() + ":agentLogin:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agent = tdHtAgentRepository.findAgentByPhone(tdHtAgentDto.getAgent_phone());
            //判断用户是否已注册
            if (agent != null) {

                TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                tdHtLoginLogDto.setLogin_ip(ip);
                tdHtLoginLogDto.setLogin_type(tdHtAgentDto.getPush_type());
                tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                tdHtLoginLogDto.setUser_id(agent.getAgent_id());
                tdHtLoginLogDto.setUser_name(agent.getAgent_name());
                tdHtLoginLogDto.setUser_type(2);

                //判断用户是否已锁定
                if (agent.getAgent_state() != 6) {
                    //判断密码是否正确
                    if (PassEncryptionUtils.digest(tdHtAgentDto.getAgent_password()).equals(agent.getAgent_password())) {

                        //删除原缓存
                        MemcachedUtils.delete(agent.getToken());

                        //更新用户token,推送标示,推送类别
                        agent.setToken(CommonUtil.getToken());
                        agent.setPush_token(tdHtAgentDto.getPush_token());
                        agent.setPush_type(tdHtAgentDto.getPush_type());

                        /**
                         * 推送标识唯一，修改已存在的标识
                         */
                        List<TdHtAgentDto> agents = tdHtAgentRepository.findAgentIdByPush(agent);
                        for (int i = 0; i < agents.size(); i++) {
                            TdHtAgentDto agentDto = agents.get(i);
                            if (agentDto.getPush_token() != null && !"".equals(agentDto.getPush_token())) {
                                agentDto.setPush_token("");
                                tdHtAgentRepository.updateAgentPushToken(agentDto);
                            }
                        }

                        if (agent.getAgent_state() == 5) {
                            agent.setAgent_state(4);
                        }
                        agent.setApp_version(tdHtAgentDto.getApp_version());
                        tdHtAgentRepository.updateAgentLogin(agent);
                        //
                        tdHtLoginLogDto.setIs_success(0);
                        tdHtLoginLogDto.setLogin_failer_desc("登陆成功");
                        //把用户信息放入缓存
                        MemcachedUtils.add(agent.getToken(), agent, new Date(20 * 24 * 60 * 60 * 1000));

                        String apply_fail_reason = "";
                        if(agent.getAgent_state() == 3){
                            TdHtAgentApplyDto tdHtAgentApplyDto = tdHtAgentApplyRepository.findApplyInfoByAgent(agent.getAgent_id());

                            if (tdHtAgentApplyDto != null) {
                                apply_fail_reason = tdHtAgentApplyDto.getApply_fail_reason();
                            }
                        }

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("token", agent.getToken());
                        map.put("agent_state", agent.getAgent_state());
                        map.put("apply_fail_reason", apply_fail_reason);

                        retInfo.setMark("0");
                        retInfo.setTip("登陆成功");
                        retInfo.setObj(map);

                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("密码不正确");
                        tdHtLoginLogDto.setIs_success(1);
                        tdHtLoginLogDto.setLogin_failer_desc("密码不正确");
                    }

                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("代理人帐号已锁定,如有疑问请联系客服.");
                    tdHtLoginLogDto.setIs_success(1);
                    tdHtLoginLogDto.setLogin_failer_desc("代理人已锁定");
                }
                //添加登陆日志
                tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);

            } else {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号尚未注册,请先注册.");
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
     * @param tdHtMemberDto
     * @param ip
     * @return RetInfo
     * @Purpose 用户登陆
     * @version 3.0
     * @author lizhun
     */
    @Transactional
    @Override
    public RetInfo monthlyLogin(TdHtMemberDto tdHtMemberDto, String ip) {
        String logInfo = this.getClass().getName() + ":login:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            if(tdHtMemberDto.getMemb_register_longitude() == null){
                tdHtMemberDto.setMemb_register_longitude(new BigDecimal(0));
            }
            if(tdHtMemberDto.getMemb_register_latitude() == null){
                tdHtMemberDto.setMemb_register_latitude(new BigDecimal(0));
            }

            TdHtMemberDto member = tdHtMemberRepository.findMemberByMembPhone(tdHtMemberDto.getMemb_phone());
            //判断用户是否已注册
            if (member != null) {

                if (member.getMemb_password() == null || "".equals(member.getMemb_password()) || member.getMemb_islock() == 2) {
                    if(tdHtMemberDto.getApp_version() >= 7){
                        retInfo.setMark("1");
                        retInfo.setTip("您还未设置密码，请使用短信验证码登录.");
                    }else {
                        retInfo.setMark("1");
                        retInfo.setTip("您的手机号尚未注册,请先注册.");
                    }
                } else {
                    TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                    tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                    tdHtLoginLogDto.setLogin_ip(ip);
                    tdHtLoginLogDto.setLogin_type(tdHtMemberDto.getPush_type());
                    tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                    tdHtLoginLogDto.setUser_id(member.getMemb_id());
                    tdHtLoginLogDto.setUser_name(member.getMemb_nick_name());
                    tdHtLoginLogDto.setUser_type(1);

                    //判断用户是否已锁定
                    if (member.getMemb_islock() == 1) {
                        //判断密码是否正确
                        if (PassEncryptionUtils.digest(tdHtMemberDto.getMemb_password()).equals(member.getMemb_password())) {

                            //删除原缓存
                            MemcachedUtils.delete(member.getToken());

                            //更新用户token,推送标示,推送类别
                            member.setToken(CommonUtil.getToken());
                            member.setPush_token(tdHtMemberDto.getPush_token());
                            member.setPush_type(tdHtMemberDto.getPush_type());

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

                            if(tdHtMemberDto.getDevice_number() == null){
                                tdHtMemberDto.setDevice_number("");
                            }

                            if(!"".equals(tdHtMemberDto.getDevice_number())){
                                tdHtMemberRepository.updateMembDevice(tdHtMemberDto.getDevice_number());
                            }

                            member.setDevice_number(tdHtMemberDto.getDevice_number());
                            member.setApp_version(tdHtMemberDto.getApp_version());
                            if(tdHtMemberDto.getMemb_register_longitude().doubleValue() > 0 && tdHtMemberDto.getMemb_register_latitude().doubleValue() > 0){
                                member.setMemb_register_longitude(tdHtMemberDto.getMemb_register_longitude());
                                member.setMemb_register_latitude(tdHtMemberDto.getMemb_register_latitude());
                            }else {
                                member.setMemb_register_longitude(new BigDecimal(0));
                                member.setMemb_register_latitude(new BigDecimal(0));
                            }
                            tdHtMemberRepository.updateMemberLogin(member);
                            //
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
                            retInfo.setTip("密码不正确");
                            tdHtLoginLogDto.setIs_success(1);
                            tdHtLoginLogDto.setLogin_failer_desc("密码不正确");
                        }

                    } else {
                        retInfo.setMark("1");
                        retInfo.setTip("用户已锁定,如有疑问请联系客服.");
                        tdHtLoginLogDto.setIs_success(1);
                        tdHtLoginLogDto.setLogin_failer_desc("用户已锁定");
                    }
                    //添加登陆日志
                    tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
                }

            } else {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号尚未注册,请先注册.");
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
