package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.agent.member.TdHtAgentMemberDto;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by lizhun
 * Date 2017-03-10.
 */

@Repository
public interface TdHtAgentMemberRepository {

    /**
     * @Purpose  添加用户代理人关联信息
     * @version  3.2
     * @author   lizhun
     * @param    tdHtAgentMemberDto
     * @return   void
     */
    public void insertTdHtAgentMember(TdHtAgentMemberDto tdHtAgentMemberDto);

    /**
     * @Purpose  根据用户id查找用户代理人关联信息
     * @version  3.2
     * @author   lizhun
     * @param    memb_id
     * @return   TdHtAgentMemberDto
     */
    public TdHtAgentMemberDto findTdHtAgentMemberDtoByMembId(int memb_id);

    /**
     * @Purpose  更新首单奖励时间
     * @version  3.2
     * @author   lizhun
     * @param    tdHtAgentMemberDto
     * @return   void
     */
    public void updateFirstSingleTime(TdHtAgentMemberDto tdHtAgentMemberDto);

    /**
     * @Purpose   查询代理人推广的数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     map  代理人id
     * @return    RetInfo  推广数量
     */
    public int findAgentExtensionNum(Map<String, Object> map);
}
