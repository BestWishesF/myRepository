package cn.hotol.app.service.three.timer;

import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.repository.TdHtAgentFoundChangeRepository;
import cn.hotol.app.repository.TdHtAgentRepository;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-22.
 */
public class AgentBonusAccountTimer {

    private static Logger logger = Logger.getLogger(AgentBonusAccountTimer.class);

    @Resource
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;

    /**
     * @return RetInfo
     * @Purpose 代理人奖励七天后自动入账
     * @version 3.0
     * @author lubin
     */
    @Transactional
    public void bonusAccount(){
        String logInfo = this.getClass().getName() + ":bonusAccount:";
        logger.info("======" + logInfo + "begin======");
        try {
            long date = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000;
            TdHtAgentFoundChangeDto agentFoundChangeDto=new TdHtAgentFoundChangeDto();
            agentFoundChangeDto.setAfchg_time(new Timestamp(date));
            agentFoundChangeDto.setAfchg_type(1);
            agentFoundChangeDto.setAfchg_state(2);
            List<Map<String, Object>> unrecordedAfchgList=tdHtAgentFoundChangeRepository.findUnrecordedAfchg(agentFoundChangeDto);
            List<TdHtAgentFoundChangeDto> agentFoundChangeDtoList=new ArrayList<TdHtAgentFoundChangeDto>();
            Map<String, BigDecimal> backAmountMap=new HashMap<String, BigDecimal>();
            List<Integer> agentIds=new ArrayList<Integer>();
            for(int i=0;i<unrecordedAfchgList.size();i++){
                Map<String, Object> unrecordedAfchgMap=unrecordedAfchgList.get(i);
                TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto=new TdHtAgentFoundChangeDto();
                int agent_id=Integer.parseInt(unrecordedAfchgMap.get("agent_id").toString());
                int afchg_id=Integer.parseInt(unrecordedAfchgMap.get("afchg_id").toString());
                BigDecimal afchg_change_amount= new BigDecimal(unrecordedAfchgMap.get("afchg_change_amount").toString());
                if(backAmountMap.get(""+agent_id)==null){
                    agentIds.add(agent_id);
                    TdHtAgentDto tdHtAgentDto=tdHtAgentRepository.findAgentById(agent_id);
                    BigDecimal afchg_back_amount=tdHtAgentDto.getAgent_balance().add(afchg_change_amount);
                    tdHtAgentFoundChangeDto.setAfchg_id(afchg_id);
                    tdHtAgentFoundChangeDto.setAfchg_front_amount(tdHtAgentDto.getAgent_balance());
                    tdHtAgentFoundChangeDto.setAfchg_back_amount(afchg_back_amount);
                    backAmountMap.put(""+agent_id,afchg_back_amount);
                }else {
                    BigDecimal afchg_back_amount=backAmountMap.get(""+agent_id).add(afchg_change_amount);
                    tdHtAgentFoundChangeDto.setAfchg_id(afchg_id);
                    tdHtAgentFoundChangeDto.setAfchg_front_amount(backAmountMap.get(""+agent_id));
                    tdHtAgentFoundChangeDto.setAfchg_back_amount(afchg_back_amount);
                    backAmountMap.put(""+agent_id,afchg_back_amount);
                }
                tdHtAgentFoundChangeDto.setAfchg_state(0);
                agentFoundChangeDtoList.add(tdHtAgentFoundChangeDto);
            }

            if(agentFoundChangeDtoList.size()>0){
                tdHtAgentFoundChangeRepository.updateBonusAccount(agentFoundChangeDtoList);
                for(int i=0;i<agentIds.size();i++){
                    int agent_id=agentIds.get(i);
                    TdHtAgentDto tdHtAgentDto=new TdHtAgentDto();
                    tdHtAgentDto.setAgent_id(agent_id);
                    tdHtAgentDto.setAgent_balance(backAmountMap.get(""+agent_id));
                    tdHtAgentRepository.updateAgentBalance(tdHtAgentDto);
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
