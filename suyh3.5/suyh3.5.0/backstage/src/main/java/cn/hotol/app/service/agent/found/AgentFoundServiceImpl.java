package cn.hotol.app.service.agent.found;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.found.AgentWithdrawalsFoundDto;
import cn.hotol.app.bean.dto.agent.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtAgentFoundChangeRepository;
import cn.hotol.app.repository.TdHtAgentRepository;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import cn.hotol.app.repository.TsHtDictRepository;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public class AgentFoundServiceImpl implements AgentFoundService{
    private static Logger logger = Logger.getLogger(AgentFoundServiceImpl.class);
    private TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository;
    private TdHtAgentRepository tdHtAgentRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TsHtDictRepository tsHtDictRepository;

    @Override
    public RetInfo agentFoundPage(int agent_id, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":agentFoundPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("agent_id", agent_id);
            int totalRecord = tdHtAgentFoundChangeRepository.findAgentFoundSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtAgentFoundChangeDto> tdHtAgentFoundChangeDtos = tdHtAgentFoundChangeRepository.findAgentFoundPage(map);
                for(int i = 0 ; i< tdHtAgentFoundChangeDtos.size() ; i++){
                    TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderDtoById(tdHtAgentFoundChangeDtos.get(i).getExp_ord_id());
                    if(tdHtExpressOrderDto != null){
                        tdHtAgentFoundChangeDtos.get(i).setExp_ord_state(tdHtExpressOrderDto.getExp_ord_state());
                    }
                }
                map.put("currentPage", currentPage);
                map.put("founds", tdHtAgentFoundChangeDtos);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
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

    /**
     * @Purpose   修改代理人资金变化记录状态
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     tdHtAgentFoundChangeDto
     * @return    RetInfo
     */
    @Override
    public RetInfo updateAgentFound(TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto) {
        String logInfo = this.getClass().getName() + ":updateAgentFound:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentFoundChangeDto agentFoundChangeDto = tdHtAgentFoundChangeRepository.findAgentFoundById(tdHtAgentFoundChangeDto.getAfchg_id());
            if(tdHtAgentFoundChangeDto.getAfchg_state() == 0 || tdHtAgentFoundChangeDto.getAfchg_state() == 3){
                tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(System.currentTimeMillis()));
                tdHtAgentFoundChangeRepository.updateWithdrawalsState(tdHtAgentFoundChangeDto);
            }else if(tdHtAgentFoundChangeDto.getAfchg_state() == 1){
                TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agentFoundChangeDto.getAgent_id());

                tdHtAgentDto.setAgent_balance(tdHtAgentDto.getAgent_balance().add(agentFoundChangeDto.getAfchg_change_amount()));
                tdHtAgentRepository.updateAgentBalance(tdHtAgentDto);

                tdHtAgentFoundChangeDto.setAfchg_time(new Timestamp(System.currentTimeMillis()));
                tdHtAgentFoundChangeRepository.updateWithdrawalsState(tdHtAgentFoundChangeDto);
            }
            retInfo.setMark("0");
            retInfo.setTip("成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   分页查询代理人提现待审核列表
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     afchg_state
     * @return    RetInfo
     */
    @Override
    public RetInfo findWithdrawalsPage(int currentPage, int pageSize, int afchg_state) {
        String logInfo = this.getClass().getName() + ":findWithdrawalsPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("afchg_state", afchg_state);

            int totalRecord = tdHtAgentFoundChangeRepository.findWithdrawalsSize(params);
            if(totalRecord > 0){
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<AgentWithdrawalsFoundDto> agentWithdrawalsFoundDtoList = tdHtAgentFoundChangeRepository.findWithdrawalsPage(map);

                Map<String, TsHtDictIndex> dictIndexMap = (Map<String, TsHtDictIndex>) MemcachedUtils.get(MemcachedKey.DICT_INDEX_MAP);
                for(int i = 0 ; i < agentWithdrawalsFoundDtoList.size() ; i++){
                    AgentWithdrawalsFoundDto agentWithdrawalsFoundDto = agentWithdrawalsFoundDtoList.get(i);

                    TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agentWithdrawalsFoundDto.getAgent_id());

                    agentWithdrawalsFoundDto.setAgent_name(tdHtAgentDto.getAgent_name());
                    agentWithdrawalsFoundDto.setAgent_phone(tdHtAgentDto.getAgent_phone());
                    agentWithdrawalsFoundDto.setAgent_pay_account(tdHtAgentDto.getAgent_pay_account());
                    agentWithdrawalsFoundDto.setAgent_pay_type(tdHtAgentDto.getAgent_pay_type());
                    agentWithdrawalsFoundDto.setAgent_score(tdHtAgentDto.getAgent_score());
                    agentWithdrawalsFoundDto.setAgent_balance(tdHtAgentDto.getAgent_balance());
                    TsHtDictIndex region = dictIndexMap.get("" + tdHtAgentDto.getArea_id());
                    TsHtDictIndex city = dictIndexMap.get("" + region.getParent_id());
                    TsHtDictIndex province = dictIndexMap.get("" + city.getParent_id());
                    agentWithdrawalsFoundDto.setArea_name(province.getCode_name() + city.getCode_name() + region.getCode_name());
                }

                map.put("currentPage", currentPage);
                map.put("agent_withdrawals", agentWithdrawalsFoundDtoList);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            }else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
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

    public void setTdHtAgentFoundChangeRepository(TdHtAgentFoundChangeRepository tdHtAgentFoundChangeRepository) {
        this.tdHtAgentFoundChangeRepository = tdHtAgentFoundChangeRepository;
    }

    public void setTdHtAgentRepository(TdHtAgentRepository tdHtAgentRepository) {
        this.tdHtAgentRepository = tdHtAgentRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }
}
