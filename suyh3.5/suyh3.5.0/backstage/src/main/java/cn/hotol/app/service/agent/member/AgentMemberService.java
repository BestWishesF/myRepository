package cn.hotol.app.service.agent.member;

import cn.hotol.app.base.RetInfo;

/**
 * Created by Administrator on 2017-03-11.
 */
public interface AgentMemberService {

    public RetInfo findAgentMemberPage(int currentPage, int pageSize, int agent_id);

}
