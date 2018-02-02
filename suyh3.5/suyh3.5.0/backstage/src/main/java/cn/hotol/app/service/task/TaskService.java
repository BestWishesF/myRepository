package cn.hotol.app.service.task;

import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
public interface TaskService {

    /**
     * @Purpose  查询任务列表
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtScoreTaskDto>
     */
    public List<TsHtScoreTaskDto> findScoreTask();

}
