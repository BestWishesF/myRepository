package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.agent.apply.TdHtAgentApplyDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/26.
 */
public interface TdHtAgentApplyRepository {
    /**
     * @Purpose  查找代理人申请记录数量
     * @version  3.0
     * @author   lizhun
     * @param
     * @return   int
     */
    public int findAgentApplySize(Map<String, Object> map);
    /**
     * @Purpose  分页查找代理人申请记录
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtAgentApplyDto>
     */
    public List<TdHtAgentApplyDto> findAgentApplyPage(Map<String, Object> map);
    /**
     * @Purpose  根据id查找代理人申请记录
     * @version  3.0
     * @author   lizhun
     * @param    apply_id
     * @return   List<TdHtAgentApplyDto>
     */
    public TdHtAgentApplyDto findAgentApplyById(int apply_id);
    /**
     * @Purpose  修改审核记录表
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAgentApplyDto
     * @return   void
     */
    public void updateAgentApply(TdHtAgentApplyDto tdHtAgentApplyDto);
}
