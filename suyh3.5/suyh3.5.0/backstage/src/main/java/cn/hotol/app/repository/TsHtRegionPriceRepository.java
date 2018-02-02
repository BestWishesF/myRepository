package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.price.TsHtRegionPriceDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-08.
 */

@Repository
public interface TsHtRegionPriceRepository {

    /**
     * @param express_id
     * @return List<TsHtRegionPriceDto>
     * @Purpose 查找所有的快递定价
     * @version 3.0
     * @author lubin
     */
    public List<TsHtRegionPriceDto> findExpRegionPrice(Integer express_id);

}
