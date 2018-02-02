package cn.hotol.app.service.express.sdk.zhongtong;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.express.BatchExpressOrderDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public interface ExpressSdkZhongTongService {

    /**
     * @Purpose  中通快递查询可用运单数量
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,express_no
     * @return   RetInfo
     */
    public RetInfo queryWaybillAmount(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String express_no);

    /**
     * @Purpose  中通快递提交订单
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return   RetInfo
     */
    public RetInfo submitOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  中通快递批量提交订单
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,batchExpressOrderDtoList
     * @return   RetInfo
     */
    public RetInfo batchSubmitOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, List<BatchExpressOrderDto> batchExpressOrderDtoList);

    /**
     * @Purpose  中通快递获取打印大头笔数据
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   RetInfo
     */
    public RetInfo obtainPaintMarker(Map<String, Object> map);

    /**
     * @Purpose  中通快递查询物流
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto,express_no
     * @return   RetInfo
     */
    public RetInfo traceOrder(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, String express_no);

}
