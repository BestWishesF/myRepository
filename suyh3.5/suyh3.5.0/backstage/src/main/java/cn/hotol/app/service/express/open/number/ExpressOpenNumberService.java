package cn.hotol.app.service.express.open.number;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.number.BatchExpressNumberDto;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface ExpressOpenNumberService {

    /**
     * @Purpose  查询快递单号
     * @version  3.0
     * @author   lubin
     * @param    express_id,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo expressOpenNumberPage(int express_id, int region_id, int exp_open_state, int currentPage, int pageSize);

    /**
     * @Purpose  录入快递单号
     * @version  3.0
     * @author   lubin
     * @param    batchExpressNumberDto
     * @return   RetInfo
     */
    public RetInfo insertExpressNumber(BatchExpressNumberDto batchExpressNumberDto);

}
