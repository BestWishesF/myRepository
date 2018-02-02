package cn.hotol.app.service.express.sdk.debang;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public interface ExpressSdkDeBangService {

    /**
     * @Purpose  德邦快递检测超区
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,tdHtExpressOrderDto,collectDto
     * @return   RetInfo
     */
    public RetInfo screenExpress(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, TdHtExpressOrderDto tdHtExpressOrderDto, TdHtExpressOrderCollectDto collectDto);

    /**
     * @Purpose  德邦快递提交订单
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return   RetInfo
     */
    public RetInfo submitOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  德邦快递查询物流
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,expressNumber
     * @return   RetInfo
     */
    public RetInfo traceOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String expressNumber);

}
