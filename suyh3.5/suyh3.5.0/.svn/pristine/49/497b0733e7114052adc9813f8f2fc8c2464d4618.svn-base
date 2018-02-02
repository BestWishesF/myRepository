package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.agent.found.AgentWithdrawalsFoundDto;
import cn.hotol.app.bean.dto.agent.found.TdHtAgentFoundChangeDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/26.
 */
public interface TdHtAgentFoundChangeRepository {
    /**
     * @Purpose  查找代理人资金变化数量
     * @version  3.0
     * @author   lizhun
     * @param
     * @return   int
     */
    public int findAgentFoundSize(Map<String, Object> map);
    /**
     * @Purpose  分页查找代理人资金变化
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtAgentFoundChangeDto>
     */
    public List<TdHtAgentFoundChangeDto> findAgentFoundPage(Map<String, Object> map);

    /**
     * @Purpose  通过id查询代理人资金变化
     * @version  3.0
     * @author   lubin
     * @param    afchg_id
     * @return   TdHtAgentFoundChangeDto
     */
    public TdHtAgentFoundChangeDto findAgentFoundById(int afchg_id);

    /**
     * @Purpose  更新代理人资金变化记录
     * @version  3.0
     * @author   lubin
     * @param    tdHtAgentFoundChangeDto
     * @return
     */
    public void updateAgentFoundState(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto);

    /**
     * @Purpose  通过订单id查询代理人资金变化
     * @version  3.0
     * @author   lubin
     * @param    exp_ord_id
     * @return   TdHtAgentFoundChangeDto
     */
    public List<TdHtAgentFoundChangeDto> findAgentFoundByOrdId(int exp_ord_id);

    /**
     * @Purpose   插入代理人收支记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-14
     * @param     tdHtAgentFoundChangeDto
     * @return
     */
    public void insertAgentFoundChange(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto);

    /**
     * @Purpose   查询代理人提现申请数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     map
     * @return    int
     */
    public int findWithdrawalsSize(Map<String, Object> map);

    /**
     * @Purpose   分页查询代理人提现申请
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     map
     * @return    List<AgentWithdrawalsFoundDto>
     */
    public List<AgentWithdrawalsFoundDto> findWithdrawalsPage(Map<String, Object> map);

    /**
     * @Purpose   修改代理人提现申请状态
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     tdHtAgentFoundChangeDto
     * @return
     */
    public void updateWithdrawalsState(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto);

}
