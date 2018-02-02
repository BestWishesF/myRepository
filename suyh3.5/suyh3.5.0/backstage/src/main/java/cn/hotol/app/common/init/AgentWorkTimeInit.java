package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.agent.work.TsHtAgentWorkTimeDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.agent.work.WorkTimeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017-04-28.
 */
public class AgentWorkTimeInit {

    private static Log logger = LogFactory.getLog(AgentWorkTimeInit.class);

    private static WorkTimeService workTimeService = (WorkTimeService) SpringInfo.getBean("workTimeService");

    public static void getAgentWorkTime() {
        Map<String, TsHtAgentWorkTimeDto> agentWorkTime = workTimeService.findAllAgentWorkTime();
        MemcachedUtils.add(MemcachedKey.AGENT_WORK_TIME_MAP, agentWorkTime, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.AGENT_WORK_TIME_MAP);
        getAgentWorkTime();
        logger.info("********************end flush**************");
    }

}
