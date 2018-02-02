package cn.hotol.app.service.three.agent;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.AgentEmailDto;
import cn.hotol.app.bean.dto.agent.AgentSparePhoneDto;
import cn.hotol.app.bean.dto.agent.AgentStateDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.member.ForgetPwdDto;
import cn.hotol.app.bean.dto.member.MemberPwdDto;
import cn.hotol.app.bean.dto.page.PageDto;

import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-26.
 */
public interface AgentService {

    /**
     * @param token
     * @return RetInfo
     * @Purpose 代理人查询余额
     * @version 3.0
     * @author lubin
     * @Param pageDto
     */
    public RetInfo getAgentBalacne(PageDto pageDto, String token);

    /**
     * @param forgetPwdDto
     * @return RetInfo
     * @Purpose 代理人忘记密码
     * @version 3.0
     * @author lubin
     */
    public RetInfo forgetAgentPwd(ForgetPwdDto forgetPwdDto);

    /**
     * @param memberPwdDto
     * @return RetInfo
     * @Purpose 代理人修改密码
     * @version 3.0
     * @author lubin
     */
    public RetInfo modifyAgentPwd(MemberPwdDto memberPwdDto, String token);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 代理人查询个人信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo findAgentInfo(String token);

    /**
     * @param agentEmailDto
     * @param token
     * @return RetInfo
     * @Purpose 代理人修改个人邮箱
     * @version 3.0
     * @author lubin
     */
    public RetInfo modifyAgentEmail(AgentEmailDto agentEmailDto, String token);

    /**
     * @param agentSparePhoneDto
     * @param token
     * @return RetInfo
     * @Purpose 代理人修改备用手机
     * @version 3.0
     * @author lubin
     */
    public RetInfo modifyAgentSparePhone(AgentSparePhoneDto agentSparePhoneDto, String token);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询代理人我的界面数据
     * @version 3.0
     * @author lubin
     */
    public RetInfo findAgentData(String token);

    /**
     * @param tdHtAgentFoundChangeDto,token
     * @return RetInfo
     * @Purpose 代理人提现
     * @version 3.0
     * @author lubin
     */
    public RetInfo agentWithdrawals(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto, String token);

    /**
     * @param agentStateDto,token
     * @return RetInfo
     * @Purpose 更新代理人状态
     * @version 3.0
     * @author lubin
     */
    public RetInfo agentSetWork(AgentStateDto agentStateDto, String token);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 代理人退出登录
     * @version 3.0
     * @author lubin
     */
    public RetInfo exitLogin(String token);

    /**
     * @Purpose   通过推广码查询代理人推广数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     params  推广码
     * @return    RetInfo  推广数量
     */
    public RetInfo findExtensionNum(Map<String, String> params);

    /**
     * @Purpose   分页查询代理人入账记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     pageDto
     * @return    RetInfo
     */
    public RetInfo findAgentEntryRecordPage(PageDto pageDto, String token);

    /**
     * @Purpose   分页查询代理人提现记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     pageDto
     * @return    RetInfo
     */
    public RetInfo findAgentWithdrawalsPage(PageDto pageDto, String token);

}
