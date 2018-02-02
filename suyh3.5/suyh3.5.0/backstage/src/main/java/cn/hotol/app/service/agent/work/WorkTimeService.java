package cn.hotol.app.service.agent.work;

import cn.hotol.app.bean.dto.agent.work.TsHtAgentWorkTimeDto;

import java.util.Map;

/**
 * Created by Administrator on 2017-04-28.
 */
public interface WorkTimeService {

    /**
     * @Purpose   查询所有的代理人工作时间
     * @version   3.0
     * @author    lubin
     * @time      2017-04-28
     * @param
     * @return    Map<String, TsHtAgentWorkTimeDto>
     */
    public Map<String, TsHtAgentWorkTimeDto> findAllAgentWorkTime();

}
