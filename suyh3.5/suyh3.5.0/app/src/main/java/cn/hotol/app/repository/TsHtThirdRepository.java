package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LuBin
 * Date 2017-01-02.
 */

@Repository
public interface TsHtThirdRepository {

    /**
     * @param
     * @return List<TsHtThirdDto>
     * @Purpose 查询微信配置
     * @version 3.0
     * @author lubin
     */
    public List<TsHtThirdDto> findAllThird();

    /**
     * @param tsHtThirdDto
     * @return
     * @Purpose 修改微信配置
     * @version 3.0
     * @author lubin
     */
    public void updateThirdById(TsHtThirdDto tsHtThirdDto);

}
