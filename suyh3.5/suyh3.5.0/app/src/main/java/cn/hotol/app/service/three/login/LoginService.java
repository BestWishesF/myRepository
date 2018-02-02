package cn.hotol.app.service.three.login;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;


public interface LoginService {
    /**
     * @Purpose  用户登陆
     * @version  3.0
     * @author   lizhun
     * @param    tdHtMemberDto
     * @param    ip
     * @return   RetInfo
     */
    public RetInfo login(TdHtMemberDto tdHtMemberDto, String ip);

    /**
     * @param tdHtAgentDto
     * @param ip
     * @return RetInfo
     * @Purpose 代理人登陆
     * @version 3.0
     * @author lubin
     */
    public RetInfo agentLogin(TdHtAgentDto tdHtAgentDto, String ip);

    /**
     * @Purpose  月结用户登陆
     * @version  3.0
     * @author   lizhun
     * @param    tdHtMemberDto
     * @param    ip
     * @return   RetInfo
     */
    public RetInfo monthlyLogin(TdHtMemberDto tdHtMemberDto, String ip);

}
