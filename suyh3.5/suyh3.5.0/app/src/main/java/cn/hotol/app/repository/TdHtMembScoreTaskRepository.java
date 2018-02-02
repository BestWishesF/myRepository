package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.score.TdHtMembScoreTaskDto;
import org.springframework.stereotype.Repository;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */

@Repository
public interface TdHtMembScoreTaskRepository {

    /**
     * @param tdHtMembScoreTaskDto
     * @return Integer
     * @Purpose 通过新手任务id查询用户完成次数
     * @version 3.0
     * @author lubin
     */
    public Integer findMembNoviceTaskCount(TdHtMembScoreTaskDto tdHtMembScoreTaskDto);

    /**
     * @param tdHtMembScoreTaskDto
     * @return Integer
     * @Purpose 通过日常任务id查询用户今日完成次数
     * @version 3.0
     * @author lubin
     */
    public Integer findMembDailyTaskCount(TdHtMembScoreTaskDto tdHtMembScoreTaskDto);

    /**
     * @param tdHtMembScoreTaskDto
     * @Purpose 插入用户完成任务记录
     * @version 3.0
     * @author lubin
     */
    public void insertMembTaskRecord(TdHtMembScoreTaskDto tdHtMembScoreTaskDto);

}
