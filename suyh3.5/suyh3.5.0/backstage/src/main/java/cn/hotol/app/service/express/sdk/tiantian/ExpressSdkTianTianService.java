package cn.hotol.app.service.express.sdk.tiantian;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.number.BatchExpressNumberDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public interface ExpressSdkTianTianService {

    /**
     * @Purpose  天天快递提取单号
     * @version  3.0
     * @author   lubin
     * @param    batchExpressNumberDto
     * @return   RetInfo
     */
    public RetInfo extractExpressNo(BatchExpressNumberDto batchExpressNumberDto);

    /**
     * @Purpose  天天快递检测超区
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,collectDtoList.orderNo
     * @return   RetInfo
     */
    public RetInfo screenExpress(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String orderNo, List<TdHtExpressOrderCollectDto> collectDtoList);

    /**
     * @Purpose  天天快递电子面单打印所需大头笔数据
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   RetInfo
     */
    public RetInfo obtainPaintMarker(Map<String, Object> map);

    /**
     * @Purpose  天天快递提交订单
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return   RetInfo
     */
    public RetInfo submitOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

}
