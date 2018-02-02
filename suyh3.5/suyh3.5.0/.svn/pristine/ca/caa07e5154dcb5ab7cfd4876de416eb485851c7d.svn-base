package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.task.TaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
public class TaskInit {

    private static TaskService taskService = (TaskService) SpringInfo.getBean("taskService");
    private static Log logger = LogFactory.getLog(BannerInit.class);

    public static void getScoreTask() {
        List<TsHtScoreTaskDto> scoreTaskDtoList=taskService.findScoreTask();
        Map<String, TsHtScoreTaskDto> scoreTask = new HashMap<String, TsHtScoreTaskDto>();
        for(int i=0;i<scoreTaskDtoList.size();i++){
            TsHtScoreTaskDto tsHtScoreTaskDto=scoreTaskDtoList.get(i);
            scoreTask.put(String.valueOf(tsHtScoreTaskDto.getSt_id()),tsHtScoreTaskDto);
        }
        MemcachedUtils.add(MemcachedKey.SCORE_TASK_LIST, scoreTaskDtoList, new Date(0));
        MemcachedUtils.add(MemcachedKey.SCORE_TASK_MAP, scoreTask, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.SCORE_TASK_LIST);
        MemcachedUtils.delete(MemcachedKey.SCORE_TASK_MAP);
        getScoreTask();
        logger.info("********************end flush**************");
    }

}
