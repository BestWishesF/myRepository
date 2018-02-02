package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.comment.TdHtCommentDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-09.
 */
@Repository
public interface TdHtCommentRepository {

    /**
     * @param tdHtCommentDto
     * @Purpose 保存评价
     * @version 3.0
     * @author lubin
     */
    public void insertComment(TdHtCommentDto tdHtCommentDto);

    /**
     * @param agent_id
     * @Purpose 查询代理人的评价
     * @version 3.0
     * @author lubin
     */
    public List<TdHtCommentDto> findAgentComment(int agent_id);

}
