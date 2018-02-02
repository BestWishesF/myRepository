package cn.hotol.app.service.three.order;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtExpressOrder;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.storage.WaitStorageOrdDetailDto;
import cn.hotol.app.bean.dto.take.TakeOrderDto;

/**
 * Created by LuBin
 * Date 2016-12-07.
 */
public interface OrderService {

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询待支付订单
     * @version 3.0
     * @author lubin
     */
    public RetInfo findToBePaidExp(PageDto pageDto, String token);

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询受理中订单
     * @version 3.0
     * @author lubin
     */
    public RetInfo findAcceptInExp(PageDto pageDto, String token);

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询投递中快件
     * @version 3.0
     * @author lubin
     */
    public RetInfo findDeliveryExp(PageDto pageDto, String token);

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询已完成快件
     * @version 3.0
     * @author lubin
     */
    public RetInfo findHasBeenFinishExp(PageDto pageDto, String token);

    /**
     * @param tdHtExpressOrder
     * @return RetInfo
     * @Purpose 查询受理中订单详情
     * @version 3.0
     * @author lubin
     */
    public RetInfo findAcceptOrderDetail(TdHtExpressOrder tdHtExpressOrder);

    /**
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 查询快件详情
     * @version 3.0
     * @author lubin
     */
    public RetInfo findExpDetail(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 取消待受理的订单
     * @version 3.0
     * @author lubin
     */
    public RetInfo cancelOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 查询待接单列表
     * @version 3.0
     * @author lubin
     */
    public RetInfo waitForAccess(PageDto pageDto, String token);

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询待接单订单详细信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo waitForAccessDetail(int orderId);

    /**
     * @param orderId
     * @param token
     * @return RetInfo
     * @Purpose 接单
     * @version 3.0
     * @author lubin
     */
    public RetInfo accessExpressOder(int orderId, String token);

    /**
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 查询待揽件列表
     * @version 3.0
     * @author lubin
     */
    public RetInfo waitForTake(PageDto pageDto, String token);

    /**
     * @param tdHtExpressOrderDto
     * @param token
     * @return RetInfo
     * @Purpose 保存代理人备注
     * @version 3.0
     * @author lubin
     */
    public RetInfo writeAgentNote(TdHtExpressOrderDto tdHtExpressOrderDto, String token);

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询揽件展示
     * @version 3.0
     * @author lubin
     */
    public RetInfo takeCollectDetail(int orderId);

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询待揽件订单详细信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo waitForTakeDetail(int orderId);

    /**
     * @param takeOrderDto
     * @param token
     * @return RetInfo
     * @Purpose 揽件
     * @version 3.0
     * @author lubin
     */
    public RetInfo takeExpressOrder(TakeOrderDto takeOrderDto, String token, Integer version, Integer client_type);

    /**
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 查询待入库列表
     * @version 3.0
     * @author lubin
     */
    public RetInfo waitForStorage(PageDto pageDto, String token);

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询待入库详情
     * @version 3.0
     * @author lubin
     */
    public RetInfo waitStorageDetail(int orderId);

    /**
     * @param waitStorageOrdDetailDto
     * @param token
     * @return RetInfo
     * @Purpose 入库
     * @version 3.0
     * @author lubin
     */
    public RetInfo storageExpressOrder(WaitStorageOrdDetailDto waitStorageOrdDetailDto, String token);

    /**
     * @param pageDto
     * @param token
     * @return RetInfo
     * @Purpose 分页查询已完成订单
     * @version 3.0
     * @author lubin
     */
    public RetInfo findFinishOrder(PageDto pageDto, String token);

    /**
     * @param orderId
     * @return RetInfo
     * @Purpose 查询已完成订单详情
     * @version 3.0
     * @author lubin
     */
    public RetInfo findFinishDetail(int orderId);

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 用户修改快递公司
     * @version 3.0
     * @author lubin
     */
    public RetInfo modifyExpressCompany(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 月结用户修改厚通订单号
     * @version 3.0
     * @author lubin
     */
    public RetInfo modifyExpressHtNumber(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto, String token);

    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 分页查询用户未开票的订单列表
     * @version 3.0
     * @author lubin
     */
    public RetInfo findMembCanInvoiceExpOrd(PageDto pageDto, String token);

}
