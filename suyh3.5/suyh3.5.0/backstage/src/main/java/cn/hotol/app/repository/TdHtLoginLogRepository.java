package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.log.TdHtLoginLogDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/11/30.
 */
@Repository
public interface TdHtLoginLogRepository {
    /**
     * @Purpose  添加登陆日志
     * @version  3.0
     * @author   lizhun
     * @param    tdHtLoginLogDto
     * @return   void
     */
    public void insertLoginLog(TdHtLoginLogDto tdHtLoginLogDto);
    /**
     * @Purpose  根据类别查找LoginLog
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findLoginLogSize(Map<String, Object> map);
    /**
     * @Purpose  分页根据类别查找LoginLog
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtLoginLogDto>
     */
    public List<TdHtLoginLogDto> findLoginLogPage(Map<String, Object> map);
}
