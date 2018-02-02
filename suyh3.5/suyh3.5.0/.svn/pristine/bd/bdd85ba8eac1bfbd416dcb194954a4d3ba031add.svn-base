package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.number.TsHtExpressOpenNumberDto;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2017-01-13.
 */
@Repository
public interface TsHtExpressOpenNumberRepository {

    /**
     * @param tsHtExpressOpenNumberDto
     * @return TsHtExpressOpenNumberDto
     * @Purpose 查询可快递单号
     * @version 3.0
     * @author lubin
     */
    public TsHtExpressOpenNumberDto findCanUseExpressNumber(TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto);

    /**
     * @param  tsHtExpressOpenNumberDto
     * @Purpose 修改快递单号状态
     * @version 3.0
     * @author lubin
     */
    public void updateExpressNumberState(TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto);

}
