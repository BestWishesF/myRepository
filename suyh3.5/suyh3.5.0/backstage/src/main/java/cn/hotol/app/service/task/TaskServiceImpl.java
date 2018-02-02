package cn.hotol.app.service.task;

import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;
import cn.hotol.app.repository.TsHtScoreTaskRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static Logger logger = Logger.getLogger(TaskServiceImpl.class);

    @Resource
    private TsHtScoreTaskRepository tsHtScoreTaskRepository;

    /**
     * @Purpose  查询任务列表
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtScoreTaskDto>
     */
    @Override
    public List<TsHtScoreTaskDto> findScoreTask() {
        String logInfo = this.getClass().getName() + ":findScoreTask:";
        logger.info("======" + logInfo + "begin======");
        List<TsHtScoreTaskDto> scoreTaskDtoList = new ArrayList<TsHtScoreTaskDto>();
        try {
            scoreTaskDtoList=tsHtScoreTaskRepository.findScoreTask();
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return scoreTaskDtoList;
    }

    public void setTsHtScoreTaskRepository(TsHtScoreTaskRepository tsHtScoreTaskRepository) {
        this.tsHtScoreTaskRepository = tsHtScoreTaskRepository;
    }
}
