package cn.hotol.app.service.agent.found;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.found.TdHtAgentFoundChangeDto;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface AgentFoundService {
    /**
     * @Purpose  分页查找代理人资金变化记录
     * @version  3.0
     * @author   lizhun
     * @param    agent_id,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo agentFoundPage(int agent_id, int currentPage, int pageSize);

    /**
     * @Purpose   修改代理人资金变化记录状态
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     tdHtAgentFoundChangeDto
     * @return    RetInfo
     */
    public RetInfo updateAgentFound(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto);

    /**
     * @Purpose   分页查询代理人提现待审核列表
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     afchg_state
     * @return    RetInfo
     */
    public RetInfo findWithdrawalsPage(int currentPage ,int pageSize, int afchg_state);
}
