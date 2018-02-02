package cn.hotol.app.service.three.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.*;
import cn.hotol.app.bean.dto.message.ShortMessageDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.EmojiFilter;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.common.util.PassEncryptionUtils;
import cn.hotol.app.repository.TdHtMemberRepository;
import cn.hotol.app.service.three.login.LoginServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/21.
 */
@Service
public class MemberServiceImpl implements MemberService {
    private static Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Resource
    private TdHtMemberRepository tdHtMemberRepository;

    @Override
    public RetInfo getMembBalacne(String token) {
        String logInfo = this.getClass().getName() + ":getMembBalacne:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMemberBanlance(member.getMemb_id());
            member.setMemb_balance(tdHtMemberDto.getMemb_balance());
            MemcachedUtils.replace(token, member, new java.util.Date(20 * 24 * 60 * 60 * 1000));
            retInfo.setMark("0");
            retInfo.setTip("成功");
            retInfo.setObj(tdHtMemberDto.getMemb_balance());
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param forgetPwdDto
     * @return
     * @Purpose 用户忘记密码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo forgetPwd(ForgetPwdDto forgetPwdDto) {
        String logInfo = this.getClass().getName() + ":forgetPwd:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMebIdAndLockByPhone(forgetPwdDto.getMemb_phone());

            //验证该手机号是否有注册
            if (tdHtMemberDto == null) {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号还没有注册.");
            } else {
                //取出缓存中的手机验证码
                ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.FORGET_VERIFICATION_CODE + forgetPwdDto.getMemb_phone());
                //验证验证码是否有效
                if (shortMessageDto == null) {
                    retInfo.setMark("2");
                    retInfo.setTip("您还没有获取验证码或验证码已过期，请重新获取.");
                } else {
                    if (!shortMessageDto.getPhoneno().equals(forgetPwdDto.getMemb_phone())) {
                        retInfo.setMark("3");
                        retInfo.setTip("您输入的手机号和获取验证码的手机号不匹配.");
                    } else {
                        if (!shortMessageDto.getVerification_code().equals(forgetPwdDto.getVerification_code())) {
                            retInfo.setMark("4");
                            retInfo.setTip("您输入的验证码不正确，请重新输入.");
                        } else {
                            //修改密码
                            MemberPwdDto memberPwdDto = new MemberPwdDto();
                            memberPwdDto.setMemb_id(tdHtMemberDto.getMemb_id());
                            memberPwdDto.setMemb_password(PassEncryptionUtils.digest(forgetPwdDto.getMemb_password()));

                            tdHtMemberRepository.updateMemberPwd(memberPwdDto);

                            retInfo.setMark("0");
                            retInfo.setTip("密码修改成功.");
                            MemcachedUtils.delete(MemcachedKey.FORGET_VERIFICATION_CODE + forgetPwdDto.getMemb_phone());
                            MemcachedUtils.delete(tdHtMemberDto.getToken());
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
     * @param memberPwdDto
     * @return
     * @Purpose 用户修改密码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo modifyPwd(MemberPwdDto memberPwdDto, String token) {
        String logInfo = this.getClass().getName() + ":modifyPwd:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            //判断用户输入的密码是否正确
            String oldPwd = PassEncryptionUtils.digest(memberPwdDto.getOld_password());
            if (!tdHtMemberDto.getMemb_password().equals(oldPwd)) {
                retInfo.setMark("1");
                retInfo.setTip("您输入的原密码不正确.");
            } else {
                memberPwdDto.setMemb_id(tdHtMemberDto.getMemb_id());
                memberPwdDto.setMemb_password(PassEncryptionUtils.digest(memberPwdDto.getMemb_password()));

                tdHtMemberRepository.updateMemberPwd(memberPwdDto);

                retInfo.setMark("0");
                retInfo.setTip("密码修改成功.");

                tdHtMemberDto.setMemb_password(memberPwdDto.getMemb_password());
                MemcachedUtils.replace(token, tdHtMemberDto, new java.util.Date(20 * 24 * 60 * 60 * 1000));
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
     * @param memberInfoDto
     * @param token
     * @return
     * @Purpose 用户修改个人信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo modifyPersonalInfo(MemberInfoDto memberInfoDto, String token) {
        String logInfo = this.getClass().getName() + ":modifyPersonalInfo:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);

            member.setMemb_nick_name(EmojiFilter.filterEmoji(memberInfoDto.getMemb_nick_name()+" "));
            member.setMemb_birthday(memberInfoDto.getMemb_birthday());
            member.setMemb_sex(memberInfoDto.getMemb_sex());
            tdHtMemberRepository.updatePersonalInfo(member);

            MemcachedUtils.replace(token, member, new Date(20 * 24 * 60 * 60 * 1000));

            memberInfoDto.setMemb_mobile(member.getMemb_phone());
            memberInfoDto.setMemb_head_portrait(member.getMemb_head_portrait());
            retInfo.setMark("0");
            retInfo.setTip("用户信息修改成功.");
            retInfo.setObj(memberInfoDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @return
     * @Purpose 用户查询个人信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findPersonalInfo(String token) {
        String logInfo = this.getClass().getName() + ":findPersonalInfo:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtMemberDto.getMemb_id());

            MemberInfoDto memberInfoDto = new MemberInfoDto();
            memberInfoDto.setMemb_head_portrait(member.getMemb_head_portrait());
            memberInfoDto.setMemb_nick_name(member.getMemb_nick_name());
            memberInfoDto.setMemb_birthday(member.getMemb_birthday());
            memberInfoDto.setMemb_sex(member.getMemb_sex());
            memberInfoDto.setMemb_mobile(member.getMemb_phone());

            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
            memberInfoDto.setShare_invitation_switch(Integer.parseInt(dataConfigMap.get(Constant.MEMB_SHARE_INVITATION_SWITCH).toString()));
            memberInfoDto.setShare_invitation_link(dataConfigMap.get(Constant.MEMB_SHARE_INVITATION_LINK).toString());
            memberInfoDto.setShare_invitation_img(dataConfigMap.get(Constant.MEMB_SHARE_INVITATION_IMG).toString());

            //判断用户是否需要设置密码
            if(member.getMemb_password() == null || "".equals(member.getMemb_password())){
                memberInfoDto.setIs_setting_pass(0);
            }else {
                memberInfoDto.setIs_setting_pass(1);
            }

            retInfo.setMark("0");
            retInfo.setTip("用户信息查询成功.");
            retInfo.setObj(memberInfoDto);
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
     * @return
     * @Purpose 用户修改头像
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo uploadHeadFile(TdHtMemberDto tdHtMemberDto) {
        String logInfo = this.getClass().getName() + ":uploadHeadFile:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tdHtMemberRepository.updateMembHeadPortrait(tdHtMemberDto);

            MemcachedUtils.replace(tdHtMemberDto.getToken(), tdHtMemberDto, new Date(20 * 24 * 60 * 60 * 1000));

            retInfo.setMark("0");
            retInfo.setTip("用户头像修改成功.");
            retInfo.setObj(tdHtMemberDto.getMemb_head_portrait());
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   用户设置密码
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     membSetPassDto  用户设置的密码
     * @return    RetInfo
     */
    @Override
    public RetInfo setMemberPass(MembSetPassDto membSetPassDto) {
        String logInfo = this.getClass().getName() + ":setMemberPass:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto memberDto = (TdHtMemberDto) MemcachedUtils.get(membSetPassDto.getToken());

            //密码编码
            String password = PassEncryptionUtils.digest(membSetPassDto.getMemb_password());
            MemberPwdDto memberPwdDto = new MemberPwdDto();
            memberPwdDto.setMemb_id(memberDto.getMemb_id());
            memberPwdDto.setMemb_password(password);

            //修改密码
            tdHtMemberRepository.updateMemberPwd(memberPwdDto);

            retInfo.setMark("0");
            retInfo.setTip("设置密码成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
