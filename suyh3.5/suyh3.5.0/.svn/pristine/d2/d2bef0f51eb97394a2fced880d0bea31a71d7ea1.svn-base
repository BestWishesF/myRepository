package cn.hotol.app.service.agent.apply;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.apply.TdHtAgentApplyDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface AgentApplyService {
    /**
     * @Purpose  代理人申请记录分页
     * @version  3.0
     * @author   lizhun
     * @param    currentPage
     * @param    pageSize
     * @return   RetInfo
     */
    public RetInfo agentAppllyPage(int apply_state, int currentPage, int pageSize, HttpServletRequest request);
    /**
     * @Purpose  审核
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAgentApplyDto
     * @return   RetInfo
     */
    public RetInfo examine(TdHtAgentApplyDto tdHtAgentApplyDto);

    /**
     * @Purpose  审核代理人页面跳转
     * @version  3.0
     * @author   lubin
     * @param    apply_id
     * @return   RetInfo
     */
    public RetInfo agentAppllyJump(int apply_id);
}
