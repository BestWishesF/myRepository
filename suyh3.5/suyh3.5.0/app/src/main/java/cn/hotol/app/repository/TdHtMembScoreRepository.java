package cn.hotol.app.repository;

import cn.hotol.app.bean.TdHtMembScore;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.score.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 积分
 * Created by liyafei on 2016/12/12.
 */
@Repository
public interface TdHtMembScoreRepository {

    /**
     * @param memb_id
     * @return FindMembScoreDto
     * @Purpose 查询积分详情条数
     * @version 3.0
     * @author liyafei
     */
    public int findMembScoreCount(int memb_id);

    /**
     * @param pageDto
     * @return FindMembScoreDto
     * @Purpose 查询积分详情
     * @version 3.0
     * @author liyafei
     */
    public List<FindMembScoreDto> findMembScore(PageDto pageDto);

    /**
     * @param st_type
     * @return int
     * @Purpose 查询积分任务详情条数
     * @version 3.0
     * @author liyafei
     */
    public int findScoreTaskCount(int st_type);

    /**
     * @param taskPageDto
     * @return ScoreTaskDto
     * @Purpose 查询积分任务详情
     * @version 3.0
     * @author liyafei
     */
    public List<ScoreTaskDto> findScoreTask(TaskPageDto taskPageDto);

    /**
     * @return int
     * @Purpose 查询积分兑换物品条数
     * @version 3.0
     * @author liyafei
     */
    public int findScoreGoodsCount();

    /**
     * @param pageDto
     * @return ScoreGoodsDto
     * @Purpose 查询积分兑换物品列表
     * @version 3.0
     * @author liyafei
     */
    public List<ScoreGoodsDto> findScoreGoods(PageDto pageDto);

    /**
     * @param tdHtMembScore
     * @return void
     * @Purpose 签到：添加积分表记录
     * @version 3.0
     * @author liyafei
     */
    public Integer insertMembScore(TdHtMembScore tdHtMembScore);

}
