package cn.hotol.app.service.agent;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.apply.TdHtAgentApplyDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface AgentService {
    /**
     * @Purpose  代理人分页
     * @version  3.0
     * @author   lizhun
     * @param    currentPage
     * @param    pageSize
     * @return   RetInfo
     */
    public RetInfo agentPage(int agent_state,int currentPage, int pageSize, HttpServletRequest request);
    /**
     * @Purpose  锁定代理人或解锁代理人
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAgentDto
     * @return   RetInfo
     */
    public RetInfo lock(TdHtAgentDto tdHtAgentDto);

    /**
     * @Purpose 查询可指派的代理人列表
     * @version  3.0
     * @author   lubin
     * @param agent_phone
     * @return RetInfo
     */
    public RetInfo findAssignAgentList(String agent_phone, Integer add_city);

    /**
     * @Purpose 按条件查询代理人分页
     * @version  3.0
     * @author   lubin
     * @param currentPage pageSize agent_id agent_mobile agent_name
     * @return RetInfo
     */
    public RetInfo searchList(int currentPage, int pageSize,int agent_id,String agent_mobile,String agent_name, HttpServletRequest request);

    /**
     * @Purpose 代理人修改页面跳转
     * @version  3.0
     * @author   lubin
     * @param agent_id
     * @return RetInfo
     */
    public RetInfo agentUpdateJump(int agent_id);

    /**
     * @Purpose 修改代理人信息
     * @version  3.0
     * @author   lubin
     * @param tdHtAgentApplyDto
     * @return RetInfo
     */
    public RetInfo updateAgent(TdHtAgentApplyDto tdHtAgentApplyDto);
}
