package cn.hotol.app.service.agent.work;

import cn.hotol.app.bean.dto.agent.work.TsHtAgentWorkTimeDto;
import cn.hotol.app.repository.TsHtAgentWorkTimeRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-04-28.
 */
public class WorkTimeServiceImpl implements WorkTimeService {

    private static Logger logger = Logger.getLogger(WorkTimeServiceImpl.class);

    private TsHtAgentWorkTimeRepository tsHtAgentWorkTimeRepository;

    /**
     * @Purpose   查询所有的代理人工作时间
     * @version   3.0
     * @author    lubin
     * @time      2017-04-28
     * @param
     * @return    Map<String, TsHtAgentWorkTimeDto>
     */
    @Override
    public Map<String, TsHtAgentWorkTimeDto> findAllAgentWorkTime() {
        String logInfo = this.getClass().getName() + ":findAllAgentWorkTime:";
        logger.info("======" + logInfo + "begin======");
        Map<String, TsHtAgentWorkTimeDto> agentWorkTime = new HashMap<String, TsHtAgentWorkTimeDto>();
        try {
            List<TsHtAgentWorkTimeDto> tsHtAgentWorkTimeDtoList = tsHtAgentWorkTimeRepository.findAllAgentWorkTime();

            for(int i = 0 ; i < tsHtAgentWorkTimeDtoList.size() ; i++){
                TsHtAgentWorkTimeDto tsHtAgentWorkTimeDto = tsHtAgentWorkTimeDtoList.get(i);
                String key = tsHtAgentWorkTimeDto.getProvince_id() + "#" + tsHtAgentWorkTimeDto.getCity_id() + "#" + tsHtAgentWorkTimeDto.getRegion_id() + "#" + tsHtAgentWorkTimeDto.getDivide_id();
                agentWorkTime.put(key, tsHtAgentWorkTimeDto);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return agentWorkTime;
    }

    public void setTsHtAgentWorkTimeRepository(TsHtAgentWorkTimeRepository tsHtAgentWorkTimeRepository) {
        this.tsHtAgentWorkTimeRepository = tsHtAgentWorkTimeRepository;
    }
}
