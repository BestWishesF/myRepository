package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.gridchange.TdHtGridChangeDto;
import org.springframework.stereotype.Repository;

/**
 * Created by LuBin
 * Date 2017-01-05.
 */

@Repository
public interface TdHtGridChangeRepository {

    /**
     * @param tdHtGridChangeDto
     * @Purpose 添加代理人经纬度变化记录
     * @version 3.0
     * @author lubin
     */
    public void insertAgentCoordinates(TdHtGridChangeDto tdHtGridChangeDto);

}
