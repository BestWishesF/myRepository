package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.score.TdHtMembScoreDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 积分
 * Created by liyafei on 2016/12/12.
 */
@Repository
public interface TdHtMembScoreRepository {

    /**
     * @Purpose  查找用户积分变化记录数量
     * @version  3.0
     * @author   lizhun
     * @param    memb_id
     * @return   int
     */
    public int findMemberScoreSize(int memb_id);
    /**
     * @Purpose  分页查找用户积分变化记录
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtMembScoreDto>
     */
    public List<TdHtMembScoreDto> findMemberScorePage(Map<String, Object> map);


}
