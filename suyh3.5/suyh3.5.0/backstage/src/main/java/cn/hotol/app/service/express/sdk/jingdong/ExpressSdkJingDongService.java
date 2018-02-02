package cn.hotol.app.service.express.sdk.jingdong;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.number.BatchExpressNumberDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;

import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public interface ExpressSdkJingDongService {

    /**
     * @Purpose  京东快递提取单号
     * @version  3.0
     * @author   lubin
     * @param    batchExpressNumberDto
     * @return   RetInfo
     */
    public RetInfo extractExpressNo(BatchExpressNumberDto batchExpressNumberDto);

    /**
     * @Purpose  京东快递检测超区
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   RetInfo
     */
    public RetInfo screenExpress(Map<String, Object> map);

    /**
     * @Purpose  京东快递提交订单
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return   RetInfo
     */
    public RetInfo submitOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  京东快递查询物流
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,expressNumber
     * @return   RetInfo
     */
    public RetInfo traceOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String expressNumber);

}
