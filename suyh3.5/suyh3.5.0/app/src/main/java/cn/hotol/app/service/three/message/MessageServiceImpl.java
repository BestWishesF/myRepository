package cn.hotol.app.service.three.message;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.message.ShortMessageDto;
import cn.hotol.app.bean.dto.message.TwSmsSendHisDto;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.common.util.SendMessage;
import cn.hotol.app.repository.TdHtAgentRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import cn.hotol.app.repository.TwSmsSendHisRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LuBin
 * Date 2016-12-06.
 */

@Service
public class MessageServiceImpl implements MessageService {

    private static Logger logger = Logger.getLogger(MessageServiceImpl.class);

    @Resource
    private TwSmsSendHisRepository twSmsSendHisRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;

    /**
     * @param phoneno
     * @return RetInfo
     * @Purpose 用户注册时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo sendRegValidateCode(String phoneno) {
        String logInfo = this.getClass().getName() + ":sendRegValidateCode:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMebIdAndLockByPhone(phoneno);

            boolean isReg = true;
            //验证该手机号是否有注册
            if (tdHtMemberDto != null) {
                if (tdHtMemberDto.getMemb_password() != null && !"".equals(tdHtMemberDto.getMemb_password())) {
                    retInfo.setMark("1");
                    retInfo.setTip("您的手机号已注册.");
                    isReg = false;
                }
            }
            if (isReg) {
                //删除原缓存
                MemcachedUtils.delete(MemcachedKey.REGISTRATION_VERIFICATION_CODE + phoneno);
                //保存发送短信记录
                TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
                twSmsSendHisDto.setPhoneno(phoneno);
                twSmsSendHisDto.setSend_time(new Date());
                twSmsSendHisDto.setSms_provider_id(0);
                twSmsSendHisDto.setSms_type(1);
                twSmsSendHisDto.setTemplate_id(0);
                twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
                String verificationCode = RandomStringUtils.random(6, false, true);
                twSmsSendHisDto.setSend_content("尊敬的用户，您本次的校验码为" + verificationCode + "，十分钟内有效！");
                twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);
                //发送短信
                ShortMessageDto shortMessageDto = new ShortMessageDto();
                shortMessageDto.setSend_time(twSmsSendHisDto.getSend_time());
                shortMessageDto.setPhoneno(phoneno);
                shortMessageDto.setValidity(10);
                shortMessageDto.setVerification_code(verificationCode);

                SendMessage.sendMessage(shortMessageDto.getPhoneno(), twSmsSendHisDto.getSend_content());

                //添加缓存
                MemcachedUtils.add(MemcachedKey.REGISTRATION_VERIFICATION_CODE + phoneno, shortMessageDto, new java.sql.Date(shortMessageDto.getValidity() * 60 * 1000));

                retInfo.setMark("0");
                retInfo.setTip("短信发送成功");
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
     * @param phoneno
     * @return RetInfo
     * @Purpose 用户忘记密码时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo sendForgetPassValidateCode(String phoneno) {
        String logInfo = this.getClass().getName() + ":sendForgetPassValidateCode:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMebIdAndLockByPhone(phoneno);

            //验证该手机号是否有注册
            if (tdHtMemberDto == null) {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号还没有注册，请先注册.");
            } else {
                if (tdHtMemberDto.getMemb_password() == null || "".equals(tdHtMemberDto.getMemb_password())) {
                    retInfo.setMark("1");
                    retInfo.setTip("您的手机号还没有注册，请先注册.");
                } else {
                    //删除原缓存
                    MemcachedUtils.delete(MemcachedKey.FORGET_VERIFICATION_CODE + phoneno);
                    //保存发送短信记录
                    TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
                    twSmsSendHisDto.setPhoneno(phoneno);
                    twSmsSendHisDto.setSend_time(new Date());
                    twSmsSendHisDto.setSms_provider_id(0);
                    twSmsSendHisDto.setSms_type(2);
                    twSmsSendHisDto.setTemplate_id(0);
                    twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
                    String verificationCode = RandomStringUtils.random(6, false, true);
                    twSmsSendHisDto.setSend_content("尊敬的用户，您本次的校验码为" + verificationCode + "，十分钟内有效！");
                    twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);
                    //发送短信
                    ShortMessageDto shortMessageDto = new ShortMessageDto();
                    shortMessageDto.setSend_time(twSmsSendHisDto.getSend_time());
                    shortMessageDto.setPhoneno(phoneno);
                    shortMessageDto.setValidity(10);
                    shortMessageDto.setVerification_code(verificationCode);

                    SendMessage.sendMessage(shortMessageDto.getPhoneno(), twSmsSendHisDto.getSend_content());

                    //添加缓存
                    MemcachedUtils.add(MemcachedKey.FORGET_VERIFICATION_CODE + phoneno, shortMessageDto, new java.sql.Date(shortMessageDto.getValidity() * 60 * 1000));

                    retInfo.setMark("0");
                    retInfo.setTip("短信发送成功");
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
     * @param phoneno
     * @return RetInfo
     * @Purpose 代理人注册时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo sendAgentRegCode(String phoneno) {
        String logInfo = this.getClass().getName() + ":sendAgentRegCode:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentByPhone(phoneno);

            //验证该手机号是否有注册
            if (tdHtAgentDto != null) {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号已注册.");
            } else {
                //删除原缓存
                MemcachedUtils.delete(MemcachedKey.AGENT_REGISTRATION_VERIFICATION_CODE + phoneno);
                //保存发送短信记录
                TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
                twSmsSendHisDto.setPhoneno(phoneno);
                twSmsSendHisDto.setSend_time(new Date());
                twSmsSendHisDto.setSms_provider_id(0);
                twSmsSendHisDto.setSms_type(3);
                twSmsSendHisDto.setTemplate_id(0);
                twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
                String verificationCode = RandomStringUtils.random(6, false, true);
                twSmsSendHisDto.setSend_content("尊敬的用户，您本次的校验码为" + verificationCode + "，十分钟内有效！");
                twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);
                //发送短信
                ShortMessageDto shortMessageDto = new ShortMessageDto();
                shortMessageDto.setSend_time(twSmsSendHisDto.getSend_time());
                shortMessageDto.setPhoneno(phoneno);
                shortMessageDto.setValidity(10);
                shortMessageDto.setVerification_code(verificationCode);

                SendMessage.sendMessage(shortMessageDto.getPhoneno(), twSmsSendHisDto.getSend_content());

                //添加缓存
                MemcachedUtils.add(MemcachedKey.AGENT_REGISTRATION_VERIFICATION_CODE + phoneno, shortMessageDto, new java.sql.Date(shortMessageDto.getValidity() * 60 * 1000));

                retInfo.setMark("0");
                retInfo.setTip("短信发送成功");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo sendAgentForgetPassCode(String phoneno) {
        String logInfo = this.getClass().getName() + ":sendAgentForgetPassCode:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentByPhone(phoneno);

            //验证该手机号是否有注册
            if (tdHtAgentDto == null) {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号还没有注册，请先注册.");
            } else {
                //删除原缓存
                MemcachedUtils.delete(MemcachedKey.AGENT_FORGET_VERIFICATION_CODE + phoneno);
                //保存发送短信记录
                TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
                twSmsSendHisDto.setPhoneno(phoneno);
                twSmsSendHisDto.setSend_time(new Date());
                twSmsSendHisDto.setSms_provider_id(0);
                twSmsSendHisDto.setSms_type(4);
                twSmsSendHisDto.setTemplate_id(0);
                twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
                String verificationCode = RandomStringUtils.random(6, false, true);
                twSmsSendHisDto.setSend_content("尊敬的用户，您本次的校验码为" + verificationCode + "，十分钟内有效！");
                twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);
                //发送短信
                ShortMessageDto shortMessageDto = new ShortMessageDto();
                shortMessageDto.setSend_time(twSmsSendHisDto.getSend_time());
                shortMessageDto.setPhoneno(phoneno);
                shortMessageDto.setValidity(10);
                shortMessageDto.setVerification_code(verificationCode);

                SendMessage.sendMessage(shortMessageDto.getPhoneno(), twSmsSendHisDto.getSend_content());

                //添加缓存
                MemcachedUtils.add(MemcachedKey.AGENT_FORGET_VERIFICATION_CODE + phoneno, shortMessageDto, new java.sql.Date(shortMessageDto.getValidity() * 60 * 1000));

                retInfo.setMark("0");
                retInfo.setTip("短信发送成功");
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
     * @param phoneno
     * @return RetInfo
     * @Purpose 代理人修改备用手机号时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo sendAgentSparePhoneCode(String phoneno) {
        String logInfo = this.getClass().getName() + ":sendAgentSparePhoneCode:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {

            int count = tdHtAgentRepository.findAgentCountBySpare(phoneno);
            if (count > 0) {
                retInfo.setMark("1");
                retInfo.setTip("该手机号已被其他帐号设为备用手机，请重新输入新的手机号.");
            } else {
                //删除原缓存
                MemcachedUtils.delete(MemcachedKey.AGENT_SPARE_PHONE_VERIFICATION_CODE + phoneno);
                //保存发送短信记录
                TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
                twSmsSendHisDto.setPhoneno(phoneno);
                twSmsSendHisDto.setSend_time(new Date());
                twSmsSendHisDto.setSms_provider_id(0);
                twSmsSendHisDto.setSms_type(5);
                twSmsSendHisDto.setTemplate_id(0);
                twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
                String verificationCode = RandomStringUtils.random(6, false, true);
                twSmsSendHisDto.setSend_content("尊敬的用户，您本次的校验码为" + verificationCode + "，十分钟内有效！");
                twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);
                //发送短信
                ShortMessageDto shortMessageDto = new ShortMessageDto();
                shortMessageDto.setSend_time(twSmsSendHisDto.getSend_time());
                shortMessageDto.setPhoneno(phoneno);
                shortMessageDto.setValidity(10);
                shortMessageDto.setVerification_code(verificationCode);

                SendMessage.sendMessage(shortMessageDto.getPhoneno(), twSmsSendHisDto.getSend_content());

                //添加缓存
                MemcachedUtils.add(MemcachedKey.AGENT_SPARE_PHONE_VERIFICATION_CODE + phoneno, shortMessageDto, new java.sql.Date(shortMessageDto.getValidity() * 60 * 1000));

                retInfo.setMark("0");
                retInfo.setTip("短信发送成功");
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
     * @param phoneno
     * @return RetInfo
     * @Purpose 第三方绑定手机号发送验证码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo sendWeChatBinding(String phoneno) {
        String logInfo = this.getClass().getName() + ":sendWeChatBinding:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //删除原缓存
            MemcachedUtils.delete(MemcachedKey.WE_CHAT_BINDING_VERIFICATION_CODE + phoneno);

            //保存发送短信记录
            TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
            twSmsSendHisDto.setPhoneno(phoneno);
            twSmsSendHisDto.setSend_time(new Date());
            twSmsSendHisDto.setSms_provider_id(0);
            twSmsSendHisDto.setSms_type(6);
            twSmsSendHisDto.setTemplate_id(0);
            twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
            String verificationCode = RandomStringUtils.random(6, false, true);
            twSmsSendHisDto.setSend_content("尊敬的用户，您本次的校验码为" + verificationCode + "，十分钟内有效！");
            twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);
            //发送短信
            ShortMessageDto shortMessageDto = new ShortMessageDto();
            shortMessageDto.setSend_time(twSmsSendHisDto.getSend_time());
            shortMessageDto.setPhoneno(phoneno);
            shortMessageDto.setValidity(10);
            shortMessageDto.setVerification_code(verificationCode);

            SendMessage.sendMessage(shortMessageDto.getPhoneno(), twSmsSendHisDto.getSend_content());

            //添加缓存
            MemcachedUtils.add(MemcachedKey.WE_CHAT_BINDING_VERIFICATION_CODE + phoneno, shortMessageDto, new java.sql.Date(shortMessageDto.getValidity() * 60 * 1000));

            retInfo.setMark("0");
            retInfo.setTip("短信发送成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   手机验证码登陆获取验证码
     * @version   3.0
     * @author    lubin
     * @time      2017-04-02
     * @param     phoneno  手机号
     * @return    RetInfo
     */
    @Override
    public RetInfo sendVerificaLoginCode(String phoneno) {
        String logInfo = this.getClass().getName() + ":sendVerificaLoginCode:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //删除原缓存
            MemcachedUtils.delete(MemcachedKey.VERIFICATION_LOGIN_VERIFICATION_CODE + phoneno);

            //保存发送短信记录
            TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
            twSmsSendHisDto.setPhoneno(phoneno);
            twSmsSendHisDto.setSend_time(new Date());
            twSmsSendHisDto.setSms_provider_id(0);
            twSmsSendHisDto.setSms_type(7);
            twSmsSendHisDto.setTemplate_id(0);
            twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
            String verificationCode = RandomStringUtils.random(6, false, true);
            twSmsSendHisDto.setSend_content("尊敬的用户，您本次的校验码为" + verificationCode + "，十分钟内有效！");
            twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);
            //发送短信
            ShortMessageDto shortMessageDto = new ShortMessageDto();
            shortMessageDto.setSend_time(twSmsSendHisDto.getSend_time());
            shortMessageDto.setPhoneno(phoneno);
            shortMessageDto.setValidity(10);
            shortMessageDto.setVerification_code(verificationCode);

            SendMessage.sendMessage(shortMessageDto.getPhoneno(), twSmsSendHisDto.getSend_content());

            //添加缓存
            MemcachedUtils.add(MemcachedKey.VERIFICATION_LOGIN_VERIFICATION_CODE + phoneno, shortMessageDto, new java.sql.Date(shortMessageDto.getValidity() * 60 * 1000));

            retInfo.setMark("0");
            retInfo.setTip("短信发送成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
