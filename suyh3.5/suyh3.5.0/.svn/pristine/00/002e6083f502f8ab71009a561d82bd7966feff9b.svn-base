package cn.hotol.app.service.three.agent.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.member.TdHtAgentMemberDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtAgentMemberRepository;
import cn.hotol.app.repository.TdHtAgentRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by lizhun on 2017/3/10.
 */
@Service
public class AgentMemberServiceImpl implements AgentMemberService{

    private static Logger logger = Logger.getLogger(AgentMemberServiceImpl.class);
    @Resource
    private TdHtAgentMemberRepository tdHtAgentMemberRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;
    @Override
    public RetInfo insertAgentMember(Map<String, String> map, String token) {
        String logInfo = this.getClass().getName() + ":insertAgentMember:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtAgentMemberDto agentMemberDto = tdHtAgentMemberRepository.findTdHtAgentMemberDtoByMembId(member.getMemb_id());
            if (agentMemberDto == null) {
                //查询推广码
                String promote_code = map.get("promote_code");
                if (promote_code != null) {
                    if (promote_code.contains("hotolagent")) {
                        String code = promote_code.replace("hotolagent", "");
                        int agent_id = Integer.valueOf(code);
                        //查找代理人代理人不为空时 添加用户代理人关联信信息
                        TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agent_id);
                        if (tdHtAgentDto != null) {
                            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);

                            TdHtAgentMemberDto tdHtAgentMemberDto = new TdHtAgentMemberDto();
                            tdHtAgentMemberDto.setAgent_id(agent_id);
                            tdHtAgentMemberDto.setMemb_id(member.getMemb_id());
                            tdHtAgentMemberDto.setFirst_single_reward(new BigDecimal(dataConfigMap.get(Constant.AGENT_EXTENSION_FIRST_REWARD).toString()));
                            tdHtAgentMemberDto.setRegister_reward(new BigDecimal("0"));
                            tdHtAgentMemberRepository.insertTdHtAgentMember(tdHtAgentMemberDto);

                            retInfo.setMark("0");
                            retInfo.setTip("成功.");
                        }else {
                            retInfo.setMark("1");
                            retInfo.setTip("代理人推广不正确，请重新扫描.");
                        }
                    }else {
                        retInfo.setMark("1");
                        retInfo.setTip("代理人推广不正确，请重新扫描.");
                    }
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("代理人推广不正确，请重新扫描.");
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("已接受其他代理人推广.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
