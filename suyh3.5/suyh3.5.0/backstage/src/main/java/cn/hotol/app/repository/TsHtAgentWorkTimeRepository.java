package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.agent.work.TsHtAgentWorkTimeDto;

import java.util.List;

/**
 * Created by Administrator on 2017-04-28.
 */
public interface TsHtAgentWorkTimeRepository {

    /**
     * @Purpose   查询所有的代理人工作时间
     * @version   3.0
     * @author    lubin
     * @time      2017-04-28
     * @param
     * @return    List<TsHtAgentWorkTimeDto>
     */
    public List<TsHtAgentWorkTimeDto> findAllAgentWorkTime();

}
