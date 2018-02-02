package cn.hotol.app.service.agent.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.member.TdHtAgentMemberDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TdHtAgentMemberRepository;
import cn.hotol.app.repository.TdHtAgentRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-11.
 */
public class AgentMemberServiceImpl implements AgentMemberService {

    private static Logger logger = Logger.getLogger(AgentMemberServiceImpl.class);

    private TdHtAgentMemberRepository tdHtAgentMemberRepository;
    private TdHtMemberRepository tdHtMemberRepository;
    private TdHtAgentRepository tdHtAgentRepository;

    @Override
    public RetInfo findAgentMemberPage(int currentPage, int pageSize, int agent_id) {
        String logInfo = this.getClass().getName() + ":findAgentMemberPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("agent_id", agent_id);
            int totalRecord = tdHtAgentMemberRepository.findAgentMemberSize(agent_id);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtAgentMemberDto> tdHtAgentMemberDtoList = tdHtAgentMemberRepository.findAgentMemberPage(map);
                for(int i=0;i<tdHtAgentMemberDtoList.size();i++){
                    TdHtAgentMemberDto tdHtAgentMemberDto=tdHtAgentMemberDtoList.get(i);
                    TdHtMemberDto tdHtMemberDto=tdHtMemberRepository.findMemberById(tdHtAgentMemberDto.getMemb_id());
                    TdHtAgentDto tdHtAgentDto=tdHtAgentRepository.findAgentById(tdHtAgentMemberDto.getAgent_id());
                    if(tdHtMemberDto!=null){
                        tdHtAgentMemberDto.setMember_name(tdHtMemberDto.getMemb_nick_name());
                        tdHtAgentMemberDto.setMember_mobile(tdHtMemberDto.getMemb_phone());
                    }
                    if(tdHtAgentDto!=null){
                        tdHtAgentMemberDto.setAgent_name(tdHtAgentDto.getAgent_name());
                        tdHtAgentMemberDto.setAgent_mobile(tdHtAgentDto.getAgent_phone());
                    }
                }
                map.put("currentPage", currentPage);
                map.put("agentMembers", tdHtAgentMemberDtoList);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                retInfo.setObj(params);
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtAgentMemberRepository(TdHtAgentMemberRepository tdHtAgentMemberRepository) {
        this.tdHtAgentMemberRepository = tdHtAgentMemberRepository;
    }

    public void setTdHtMemberRepository(TdHtMemberRepository tdHtMemberRepository) {
        this.tdHtMemberRepository = tdHtMemberRepository;
    }

    public void setTdHtAgentRepository(TdHtAgentRepository tdHtAgentRepository) {
        this.tdHtAgentRepository = tdHtAgentRepository;
    }
}
