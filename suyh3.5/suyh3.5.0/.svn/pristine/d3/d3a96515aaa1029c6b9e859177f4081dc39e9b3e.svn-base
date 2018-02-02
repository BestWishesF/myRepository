package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.agent.TdHtAgentDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface TdHtAgentRepository {
    /**
     * @Purpose  查找代理人数量
     * @version  3.0
     * @author   lizhun
     * @param
     * @return   int
     */
    public int findAgentSize(Map<String, Object> map);
    /**
     * @Purpose  分页查找代理人
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtAgentDto>
     */
    public List<TdHtAgentDto> findAgentPage(Map<String, Object> map);
    /**
     * @Purpose  更新代理人状态
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAgentDto
     * @return   void
     */
    public void updateAgentState(TdHtAgentDto tdHtAgentDto);
    /**
     * @Purpose  根据id查找代理人
     * @version  3.0
     * @author   lizhun
     * @param    agent_id
     * @return   TdHtAgentDto
     */
    public TdHtAgentDto findAgentById(int agent_id);
    /**
     * @Purpose  更新代理人信息
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAgentDto
     * @return   void
     */
    public void updateAgentApply(TdHtAgentDto tdHtAgentDto);

    /**
     * @Purpose  通过代理人手机号查询代理人信息
     * @version  3.0
     * @author   lubin
     * @param    agent_phone
     * @return   TdHtAgentDto
     */
    public TdHtAgentDto findAgentByMobile(String agent_phone);

    /**
     * @Purpose  通过手机号查询可指派代理人
     * @version  3.0
     * @author   lubin
     * @param map
     * @return   List<TdHtAgentDto>
     */
    public List<TdHtAgentDto> findAssignAgentList(Map<String, Object> map);

    /**
     * @Purpose  更新代理人的钱包
     * @version  3.0
     * @author   lubin
     * @param tdHtAgentDto
     * @return
     */
    public void updateAgentBalance(TdHtAgentDto tdHtAgentDto);

    /**
     * @Purpose  按条件查询代理人数量
     * @version  3.0
     * @author   lubin
     * @param map
     * @return   Integer
     */
    public Integer findAgentByBeanSize(Map<String, Object> map);

    /**
     * @Purpose  按条件查询代理人分页
     * @version  3.0
     * @author   lubin
     * @param map
     * @return   List<TdHtAgentDto>
     */
    public List<TdHtAgentDto> findAgentByBeanPage(Map<String, Object> map);
}
