package cn.hotol.app.service.three.agent;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.*;
import cn.hotol.app.bean.dto.comment.TdHtCommentDto;
import cn.hotol.app.bean.dto.found.AgentFinancialDetailsDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.member.ForgetPwdDto;
import cn.hotol.app.bean.dto.member.MemberPwdDto;
import cn.hotol.app.bean.dto.message.ShortMessageDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-26.
 */
@Service
public class AgentServiceImpl implements AgentService {

    private static Logger logger = Logger.getLogger(AgentServiceImpl.class);

    @Resource
    private TdHtAgentRepository tdHtAgentRepository;
    @Resource
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtCommentRepository tdHtCommentRepository;
    @Resource
    private TdHtAgentMemberRepository tdHtAgentMemberRepository;

    /**
     * @param token
     * @return RetInfo
     * @Purpose 代理人查询余额
     * @version 3.0
     * @author lubin
     * @Param pageDto
     */
    @Override
    public RetInfo getAgentBalacne(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":getAgentBalacne:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agent = (TdHtAgentDto) MemcachedUtils.get(token);

            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(agent.getAgent_id());
            Page<AgentFinancialDetailsDto> financialDetailsDtoPage = new Page<AgentFinancialDetailsDto>(pageDto.getPage_size(), pageDto.getPage_no());
            int count = tdHtAgentFoundChangeRepository.findAgentFoundChangeCount(agent.getAgent_id());
            pageDto.setLimit_str(financialDetailsDtoPage.getLimitCriterion());
            pageDto.setId(agent.getAgent_id());
            List<AgentFinancialDetailsDto> financialDetailsDtoList = tdHtAgentFoundChangeRepository.findAgentFoundChange(pageDto);
            financialDetailsDtoPage.setTotalCount(count);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("balacne", agentDto.getAgent_balance());
            map.put("total_pages", financialDetailsDtoPage.getTotalPages());
            map.put("page_no", financialDetailsDtoPage.getPageNo());
            map.put("items", financialDetailsDtoList);

            agent.setAgent_balance(agentDto.getAgent_balance());
            MemcachedUtils.replace(token, agent, new Date(20 * 24 * 60 * 60 * 1000));
            retInfo.setMark("0");
            retInfo.setTip("成功");
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
     * @param forgetPwdDto
     * @return RetInfo
     * @Purpose 代理人忘记密码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo forgetAgentPwd(ForgetPwdDto forgetPwdDto) {
        String logInfo = this.getClass().getName() + ":forgetAgentPwd:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentByPhone(forgetPwdDto.getMemb_phone());

            //验证该手机号是否有注册
            if (tdHtAgentDto == null) {
                retInfo.setMark("1");
                retInfo.setTip("您的手机号还没有注册.");
            } else {
                //取出缓存中的手机验证码
                ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.AGENT_FORGET_VERIFICATION_CODE + forgetPwdDto.getMemb_phone());
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
                            TdHtAgentDto tdHtAgent = new TdHtAgentDto();
                            tdHtAgent.setAgent_id(tdHtAgentDto.getAgent_id());
                            tdHtAgent.setAgent_password(PassEncryptionUtils.digest(forgetPwdDto.getMemb_password()));

                            tdHtAgentRepository.updateAgentPassword(tdHtAgent);

                            retInfo.setMark("0");
                            retInfo.setTip("密码修改成功.");
                            MemcachedUtils.delete(MemcachedKey.AGENT_FORGET_VERIFICATION_CODE + forgetPwdDto.getMemb_phone());
                            MemcachedUtils.delete(tdHtAgentDto.getToken());
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
     * @return RetInfo
     * @Purpose 代理人修改密码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo modifyAgentPwd(MemberPwdDto memberPwdDto, String token) {
        String logInfo = this.getClass().getName() + ":modifyAgentPwd:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agentDto.getAgent_id());
            //判断用户输入的密码是否正确
            String oldPwd = PassEncryptionUtils.digest(memberPwdDto.getOld_password());
            if (!tdHtAgentDto.getAgent_password().equals(oldPwd)) {
                retInfo.setMark("1");
                retInfo.setTip("您输入的原密码不正确.");
            } else {
                tdHtAgentDto.setAgent_password(PassEncryptionUtils.digest(memberPwdDto.getMemb_password()));

                tdHtAgentRepository.updateAgentPassword(tdHtAgentDto);

                retInfo.setMark("0");
                retInfo.setTip("密码修改成功.");
                MemcachedUtils.replace(token, tdHtAgentDto, new Date(20 * 24 * 60 * 60 * 1000));
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
     * @param token
     * @return RetInfo
     * @Purpose 代理人查询个人信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findAgentInfo(String token) {
        String logInfo = this.getClass().getName() + ":findAgentInfo:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agentDto = (TdHtAgentDto) MemcachedUtils.get(token);

            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);

            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agentDto.getAgent_id());
            TdHtAgentInfoDto tdHtAgentInfoDto = new TdHtAgentInfoDto();
            tdHtAgentInfoDto.setAgent_name(tdHtAgentDto.getAgent_name());
            tdHtAgentInfoDto.setAgent_email(tdHtAgentDto.getAgent_email());
            tdHtAgentInfoDto.setAgent_spare_phone(tdHtAgentDto.getAgent_spare_phone());
            tdHtAgentInfoDto.setAgent_pay_account(tdHtAgentDto.getAgent_pay_account());
            tdHtAgentInfoDto.setAgent_sex(tdHtAgentDto.getAgent_sex());
            tdHtAgentInfoDto.setAgent_birthday(tdHtAgentDto.getAgent_birthday());
            tdHtAgentInfoDto.setAgent_address(tdHtAgentDto.getAgent_address());
            tdHtAgentInfoDto.setAgent_phone(tdHtAgentDto.getAgent_phone());
            tdHtAgentInfoDto.setAgent_head_portrait(tdHtAgentDto.getAgent_head_portrait());
            tdHtAgentInfoDto.setAgent_code(dataConfigMap.get(Constant.AGENT_EXTENSION_SHARE_URL).toString() + tdHtAgentDto.getAgent_id());
            MemcachedUtils.replace(token, tdHtAgentDto, new Date(20 * 24 * 60 * 60 * 1000));
            retInfo.setMark("0");
            retInfo.setTip("用户信息查询成功.");
            retInfo.setObj(tdHtAgentInfoDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param agentEmailDto
     * @param token
     * @return RetInfo
     * @Purpose 代理人修改个人邮箱
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo modifyAgentEmail(AgentEmailDto agentEmailDto, String token) {
        String logInfo = this.getClass().getName() + ":modifyAgentEmail:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agentDto.getAgent_id());
            tdHtAgentDto.setAgent_email(agentEmailDto.getAgent_email());
            tdHtAgentRepository.updateAgentEmail(tdHtAgentDto);

            retInfo.setMark("0");
            retInfo.setTip("邮箱修改成功.");
            MemcachedUtils.replace(token, tdHtAgentDto, new Date(20 * 24 * 60 * 60 * 1000));
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param agentSparePhoneDto
     * @param token
     * @return RetInfo
     * @Purpose 代理人修改备用手机
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo modifyAgentSparePhone(AgentSparePhoneDto agentSparePhoneDto, String token) {
        String logInfo = this.getClass().getName() + ":modifyAgentSparePhone:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //取出缓存中的手机验证码
            ShortMessageDto shortMessageDto = (ShortMessageDto) MemcachedUtils.get(MemcachedKey.AGENT_SPARE_PHONE_VERIFICATION_CODE + agentSparePhoneDto.getSpare_phone());
            //验证验证码是否有效
            if (shortMessageDto == null) {
                retInfo.setMark("2");
                retInfo.setTip("您还没有获取验证码或验证码已过期，请重新获取.");
            } else {
                if (!shortMessageDto.getPhoneno().equals(agentSparePhoneDto.getSpare_phone())) {
                    retInfo.setMark("3");
                    retInfo.setTip("您输入的手机号和获取验证码的手机号不匹配.");
                } else {
                    if (!shortMessageDto.getVerification_code().equals(agentSparePhoneDto.getVerification_code())) {
                        retInfo.setMark("4");
                        retInfo.setTip("您输入的验证码不正确，请重新输入.");
                    } else {
                        //修改备用手机
                        TdHtAgentDto agentDto = (TdHtAgentDto) MemcachedUtils.get(token);
                        TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agentDto.getAgent_id());

                        tdHtAgentDto.setAgent_spare_phone(agentSparePhoneDto.getSpare_phone());
                        tdHtAgentRepository.updateAgentSparePhone(tdHtAgentDto);

                        retInfo.setMark("0");
                        retInfo.setTip("备用手机修改成功.");
                        MemcachedUtils.replace(token, tdHtAgentDto, new Date(20 * 24 * 60 * 60 * 1000));
                        MemcachedUtils.delete(MemcachedKey.AGENT_SPARE_PHONE_VERIFICATION_CODE + agentSparePhoneDto.getSpare_phone());
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
     * @param token
     * @return RetInfo
     * @Purpose 查询代理人我的界面数据
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findAgentData(String token) {
        String logInfo = this.getClass().getName() + ":findAgentData:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中取出代理人信息
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            //查询代理人信息
            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(tdHtAgentDto.getAgent_id());
            //查询代理人揽件数量
            Integer takingExpNum = tdHtExpressOrderRepository.findAgentTakingNum(tdHtAgentDto.getAgent_id());
            String completionRate = "0.00";
            if (takingExpNum == null) {
                takingExpNum = 0;
            } else {
                //查询代理人按时完成揽件数量
                Integer finishOnTimeNum = tdHtExpressOrderRepository.findAgentFinishOnTime(tdHtAgentDto.getAgent_id());
                if (finishOnTimeNum == null) {
                    finishOnTimeNum = 0;
                }
                //计算按时揽件百分比
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
                completionRate = numberFormat.format((float) finishOnTimeNum / (float) takingExpNum * 100);
            }
            //查询代理人评分，并计算平均分
            List<TdHtCommentDto> commentDtoList = tdHtCommentRepository.findAgentComment(tdHtAgentDto.getAgent_id());
            BigDecimal grade = new BigDecimal(0);
            if (commentDtoList.size() > 0) {
                int agentGrade = 0;
                for (int i = 0; i < commentDtoList.size(); i++) {
                    TdHtCommentDto tdHtCommentDto = commentDtoList.get(i);
                    agentGrade = agentGrade + tdHtCommentDto.getCom_grade();
                }
                grade = new BigDecimal(agentGrade).divide(new BigDecimal(commentDtoList.size()), 1, BigDecimal.ROUND_HALF_UP);
            }

            //从缓存中取得字典数据
            Map<String, Object> dataDictionary = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_DICTIONARY);
            List<TsHtDictDto> tsHtDictDtoList = (List<TsHtDictDto>) dataDictionary.get(Constant.AGENT_WORK_TIME);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("agent_head_portrait", agentDto.getAgent_head_portrait());
            map.put("agent_name", agentDto.getAgent_name());
            map.put("agent_phone", agentDto.getAgent_phone());
            map.put("taking_exp_num", takingExpNum);
            map.put("completion_rate", completionRate + "%");
            map.put("agent_balance", agentDto.getAgent_balance());
            map.put("grade", grade);
            map.put("state", agentDto.getAgent_state());
            if (tsHtDictDtoList.size() > 0) {
                map.put("agent_work_time", tsHtDictDtoList.get(0).getCode_name());
            } else {
                map.put("agent_work_time", "");
            }

            tdHtAgentDto.setAgent_balance(agentDto.getAgent_balance());
            MemcachedUtils.replace(token, tdHtAgentDto, new Date(20 * 24 * 60 * 60 * 1000));
            retInfo.setMark("0");
            retInfo.setTip("数据查询成功.");
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
     * @param tdHtAgentFoundChangeDto,token
     * @return RetInfo
     * @Purpose 代理人提现
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo agentWithdrawals(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto, String token) {
        String logInfo = this.getClass().getName() + ":findAgentData:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中取出代理人信息
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(tdHtAgentDto.getAgent_id());
            if (tdHtAgentFoundChangeDto.getAfchg_change_amount().compareTo(agentDto.getAgent_balance()) > 0) {
                retInfo.setMark("2");
                retInfo.setTip("您的余额不足.");
            } else {
                if (tdHtAgentFoundChangeDto.getAfchg_change_amount().intValue() > 0) {
                    tdHtAgentFoundChangeDto.setAfchg_front_amount(agentDto.getAgent_balance());
                    BigDecimal balance = agentDto.getAgent_balance().subtract(tdHtAgentFoundChangeDto.getAfchg_change_amount());
                    tdHtAgentFoundChangeDto.setAfchg_back_amount(balance);
                    tdHtAgentFoundChangeDto.setAfchg_month(CommonUtil.getMonth());
                    tdHtAgentFoundChangeDto.setAfchg_name("提现");
                    tdHtAgentFoundChangeDto.setAfchg_number(CommonUtil.getOrderNub());
                    tdHtAgentFoundChangeDto.setAfchg_state(2);
                    tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(System.currentTimeMillis()));
                    tdHtAgentFoundChangeDto.setAfchg_type(2);
                    tdHtAgentFoundChangeDto.setAgent_id(agentDto.getAgent_id());
                    tdHtAgentFoundChangeDto.setExp_ord_id(0);
                    tdHtAgentFoundChangeRepository.insertAgentFoundChange(tdHtAgentFoundChangeDto);

                    agentDto.setAgent_balance(balance);
                    tdHtAgentRepository.updateAgentBalance(agentDto);
                    MemcachedUtils.replace(token, agentDto, new Date(20 * 24 * 60 * 60 * 1000));

                    retInfo.setMark("0");
                    retInfo.setTip("提现申请发送成功.");
                } else {
                    retInfo.setMark("3");
                    retInfo.setTip("您的余额不足.");
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
     * @param agentStateDto,token
     * @return RetInfo
     * @Purpose 更新代理人状态
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo agentSetWork(AgentStateDto agentStateDto, String token) {
        String logInfo = this.getClass().getName() + ":agentSetWork:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中取出代理人信息
            TdHtAgentDto agent = (TdHtAgentDto) MemcachedUtils.get(token);
            //查询代理人信息
            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(agent.getAgent_id());
            agentDto.setAgent_state(agentStateDto.getAgent_state());
            tdHtAgentRepository.updateSetStateAgent(agentDto);

            retInfo.setMark("0");
            retInfo.setTip("设置成功.");
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
     * @return RetInfo
     * @Purpose 代理人退出登录
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo exitLogin(String token) {
        String logInfo = this.getClass().getName() + ":exitLogin:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中取出代理人信息
            TdHtAgentDto agent = (TdHtAgentDto) MemcachedUtils.get(token);
            //查询代理人信息
            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(agent.getAgent_id());
            if(agentDto.getAgent_state()==4){
                agentDto.setAgent_state(5);
            }
            tdHtAgentRepository.updateSetStateAgent(agentDto);
            //删除缓存
            MemcachedUtils.delete(token);
            retInfo.setMark("0");
            retInfo.setTip("退出登录成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   通过推广码查询代理人推广数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     params  推广码
     * @return    RetInfo  推广数量
     */
    @Override
    public RetInfo findExtensionNum(Map<String, String> params) {
        String logInfo = this.getClass().getName() + ":findExtensionNum:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //查询推广码
            String promote_code = params.get("promote_code");
            if (promote_code != null) {
                if (promote_code.contains("hotolagent")) {
                    String code = promote_code.replace("hotolagent", "");
                    int agent_id = Integer.valueOf(code);

                    //查找代理人是否存在
                    TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agent_id);
                    if (tdHtAgentDto != null) {
                        //查询总推广数量查询条件
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("agent_id", agent_id);

                        //查询总推广数量
                        int total_num = tdHtAgentMemberRepository.findAgentExtensionNum(map);

                        //查询当天推广数量查询条件
                        map.put("regiter_time", new Date(System.currentTimeMillis()));

                        //查询当天推广数量
                        int today_num = tdHtAgentMemberRepository.findAgentExtensionNum(map);

                        //返回查询结果
                        params.put("total_num", "" + total_num);
                        params.put("today_num", "" + today_num);

                        retInfo.setMark("0");
                        retInfo.setObj(params);
                    }else {
                        retInfo.setMark("1");
                        retInfo.setTip("代理人推广码不正确，请重新输入.");
                    }
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("代理人推广码不正确，请重新输入.");
                }
            }else {
                retInfo.setMark("1");
                retInfo.setTip("请输入代理人推广码.");
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
     * @Purpose   分页查询代理人入账记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     pageDto
     * @return    RetInfo
     */
    @Override
    public RetInfo findAgentEntryRecordPage(PageDto pageDto, String token){
        String logInfo = this.getClass().getName() + ":findAgentEntryRecordPage:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agent = (TdHtAgentDto) MemcachedUtils.get(token);

            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(agent.getAgent_id());
            pageDto.setId(agentDto.getAgent_id());
            pageDto.setType(1);
            pageDto.setState(1);
            Page<AgentFinancialDetailsDto> financialDetailsDtoPage = new Page<AgentFinancialDetailsDto>(pageDto.getPage_size(), pageDto.getPage_no());
            int count = tdHtAgentFoundChangeRepository.findAgentFoundChangeSize(pageDto);
            pageDto.setLimit_str(financialDetailsDtoPage.getLimitCriterion());
            List<Map<String, Object>> financialDetailsDtoList = tdHtAgentFoundChangeRepository.findAgentFoundChangePage(pageDto);
            financialDetailsDtoPage.setTotalCount(count);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("balacne", agentDto.getAgent_balance());
            map.put("total_pages", financialDetailsDtoPage.getTotalPages());
            map.put("page_no", financialDetailsDtoPage.getPageNo());
            map.put("items", financialDetailsDtoList);

            agent.setAgent_balance(agentDto.getAgent_balance());
            MemcachedUtils.replace(token, agent, new Date(20 * 24 * 60 * 60 * 1000));
            retInfo.setMark("0");
            retInfo.setTip("成功");
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
     * @Purpose   分页查询代理人提现记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     pageDto
     * @return    RetInfo
     */
    @Override
    public RetInfo findAgentWithdrawalsPage(PageDto pageDto, String token){
        String logInfo = this.getClass().getName() + ":findAgentWithdrawalsPage:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agent = (TdHtAgentDto) MemcachedUtils.get(token);

            TdHtAgentDto agentDto = tdHtAgentRepository.findAgentById(agent.getAgent_id());
            pageDto.setId(agentDto.getAgent_id());
            pageDto.setType(2);
            pageDto.setState(0);
            Page<AgentFinancialDetailsDto> financialDetailsDtoPage = new Page<AgentFinancialDetailsDto>(pageDto.getPage_size(), pageDto.getPage_no());
            int count = tdHtAgentFoundChangeRepository.findAgentFoundChangeSize(pageDto);
            pageDto.setLimit_str(financialDetailsDtoPage.getLimitCriterion());
            List<Map<String, Object>> financialDetailsDtoList = tdHtAgentFoundChangeRepository.findAgentFoundChangePage(pageDto);
            financialDetailsDtoPage.setTotalCount(count);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("balacne", agentDto.getAgent_balance());
            map.put("total_pages", financialDetailsDtoPage.getTotalPages());
            map.put("page_no", financialDetailsDtoPage.getPageNo());
            map.put("items", financialDetailsDtoList);

            agent.setAgent_balance(agentDto.getAgent_balance());
            MemcachedUtils.replace(token, agent, new Date(20 * 24 * 60 * 60 * 1000));
            retInfo.setMark("0");
            retInfo.setTip("成功");
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
