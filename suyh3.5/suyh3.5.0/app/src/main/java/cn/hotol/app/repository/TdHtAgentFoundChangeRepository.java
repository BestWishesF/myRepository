package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.found.AgentFinancialDetailsDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.page.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-27.
 */

@Repository
public interface TdHtAgentFoundChangeRepository {

    /**
     * @param agent_id
     * @return int
     * @Purpose 查询代理人收支记录数量
     * @version 3.0
     * @author lubin
     */
    public int findAgentFoundChangeCount(int agent_id);

    /**
     * @param pageDto
     * @return List<AgentFinancialDetailsDto>
     * @Purpose 分页查询代理人收支记录
     * @version 3.0
     * @author lubin
     */
    public List<AgentFinancialDetailsDto> findAgentFoundChange(PageDto pageDto);

    /**
     * @param tdHtAgentFoundChangeDto
     * @Purpose 插入代理人收支记录
     * @version 3.0
     * @author lubin
     */
    public void insertAgentFoundChange(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto);

    /**
     * @param exp_ord_id
     * @return TdHtAgentFoundChangeDto
     * @Purpose 通过订单id查询代理人资金变化
     * @version 3.0
     * @author lubin
     */
    public List<TdHtAgentFoundChangeDto> findAgentFoundByOrdId(int exp_ord_id);

    /**
     * @param tdHtAgentFoundChangeDto
     * @Purpose 更新代理人资金变化记录
     * @version 3.0
     * @author lubin
     */
    public void updateAgentFoundState(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto);

    /**
     * @param tdHtAgentFoundChangeDto
     * @return List<Map<String, Object>>
     * @Purpose 查询一定时间内未入账的代理人接单奖励
     * @version 3.0
     * @author lubin
     */
    public List<Map<String, Object>> findUnrecordedAfchg(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto);

    /**
     * @param tdHtAgentFoundChangeDtoList
     * @return
     * @Purpose 代理人奖励批量入账
     * @version 3.0
     * @author lubin
     */
    public void updateBonusAccount(List<TdHtAgentFoundChangeDto> tdHtAgentFoundChangeDtoList);

    /**
     * @Purpose   查询代理人资金变化明细总记录数
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     pageDto
     * @return    int
     */
    public int findAgentFoundChangeSize(PageDto pageDto);

    /**
     * @Purpose   分页查询代理人资金变化明细
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     pageDto
     * @return    List<Map<String, Object>>
     */
    public List<Map<String, Object>> findAgentFoundChangePage(PageDto pageDto);

}
