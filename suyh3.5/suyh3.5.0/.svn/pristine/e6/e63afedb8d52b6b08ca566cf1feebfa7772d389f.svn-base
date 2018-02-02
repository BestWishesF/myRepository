package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.comment.TdHtCommentDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TdHtCommentRepository {

    /**
     * @Purpose  查找评论
     * @version  3.0
     * @author   lizhun
     * @param
     * @return   int
     */
    public int findCommentSize(Map<String, Object> map);
    /**
     * @Purpose  评论分页
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtCommentDto>
     */
    public List<TdHtCommentDto> findCommentPage(Map<String, Object> map);

}
