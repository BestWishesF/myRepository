package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.accept.WaitAccessCollectDetailDto;
import cn.hotol.app.bean.dto.accept.WaitAccessCollectDto;
import cn.hotol.app.bean.dto.expressorder.*;
import cn.hotol.app.bean.dto.finish.FinishCollectDetailDto;
import cn.hotol.app.bean.dto.monthbill.MonthExpOrdDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.storage.WaitStorageCollectDto;
import cn.hotol.app.bean.dto.take.TakeCollectDto;
import cn.hotol.app.bean.dto.take.WaitTakeCollectDetailDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liyafei on 2016/12/1.
 */
@Repository
public interface TdHtExpressCollectRepository {
    /**
     * @param exp_ord_id
     * @return TdHtExpressCollectDto
     * @Purpose 查询订单下的快件信息
     * @version 3.0
     * @author lubin
     */
    public List<TdHtExpressOrderCollectDto> findExpressCollectByOrder(int exp_ord_id);

    /**
     * @param exp_ord_id
     * @return String
     * @Purpose 根据发件订单id查询快递单号
     * @version 3.0
     * @author liyafei
     */
    public String findExpressNumber(int exp_ord_id);

    /**
     * @param exp_ord_clt_id
     * @return String
     * @Purpose 根据id查询收件信息
     * @version 3.0
     * @author liyafei
     */
    public TdHtExpressOrderCollectDto findTdHtExpressCollectById(int exp_ord_clt_id);

    /**
     * @param tdHtExpressOrderCollect
     * @return void
     * @Purpose 添加收件信息
     * @version 3.0
     * @author liyafei
     */
    public void insertExpressOrderCollect(TdHtExpressOrderCollectDto tdHtExpressOrderCollect);

    /**
     * @param tdHtExpressOrderCollect
     * @return void
     * @Purpose 修改收件信息
     * @version 3.0
     * @author liyafei
     */
    public void updateExpressOrderCollect(TdHtExpressOrderCollectDto tdHtExpressOrderCollect);

    /**
     * @param memb_id
     * @return List<SendRecordDto>
     * @Purpose 查询最新的十条寄件记录
     * @version 3.0
     * @author lubin
     */
    public List<SendRecordDto> findNewTenSendRecord(Integer memb_id);

    /**
     * @param memb_id
     * @return Integer
     * @Purpose 查询投递中的订单总数量
     * @version 3.0
     * @author lubin
     */
    public Integer findDeliveryExpAmount(Integer memb_id);

    /**
     * @param expressPageDto
     * @return List<DeliveryExpDto>
     * @Purpose 分页查询投递中的订单
     * @version 3.0
     * @author lubin
     */
    public List<DeliveryExpDto> findDeliveryExp(PageDto expressPageDto);

    /**
     * @param memb_id
     * @return Integer
     * @Purpose 查询已完成的订单总数量
     * @version 3.0
     * @author lubin
     */
    public Integer findHasBeenFinishExpAmount(Integer memb_id);

    /**
     * @param expressPageDto
     * @return List<HasBeenFinishExpDto>
     * @Purpose 分页查询已完成的订单
     * @version 3.0
     * @author lubin
     */
    public List<HasBeenFinishExpDto> findHasBeenFinishExp(PageDto expressPageDto);

    /**
     * @param exp_ord_id
     * @return List<AcceptCollectDto>
     * @Purpose 查询受理中订单下的快件信息
     * @version 3.0
     * @author lubin
     */
    public List<AcceptCollectDto> findAcceptCollectByOrder(int exp_ord_id);

    /**
     * @param exp_ord_clt_id
     * @return ExpressDetailDto
     * @Purpose 通过快件id查询快件的信息
     * @version 3.0
     * @author lubin
     */
    public ExpressDetailDto findExpDetailByCollectId(int exp_ord_clt_id);

    /**
     * @param tdHtExpressOrderCollectDtoList
     * @Purpose 批量插入快件信息
     * @version 3.0
     * @author lubin
     */
    public void batchInsertExpressOrderCollect(List<TdHtExpressOrderCollectDto> tdHtExpressOrderCollectDtoList);

    /**
     * @param exp_ord_id
     * @return List<WaitAccessCollectDto>
     * @Purpose 查询订单下待接单快件
     * @version 3.0
     * @author lubin
     */
    public List<WaitAccessCollectDto> findWaitAccCollect(int exp_ord_id);

    /**
     * @param exp_ord_id
     * @return List<WaitAccessCollectDetailDto>
     * @Purpose 查询订单下待接单快件详情
     * @version 3.0
     * @author lubin
     */
    public List<WaitAccessCollectDetailDto> findWaitAccCollectDetail(int exp_ord_id);

    /**
     * @param exp_ord_id
     * @return List<TakeCollectDto>
     * @Purpose 查询订单下待揽件快件详情
     * @version 3.0
     * @author lubin
     */
    public List<TakeCollectDto> findTakeCollect(int exp_ord_id);

    /**
     * @param exp_ord_id
     * @return List<WaitTakeCollectDetailDto>
     * @Purpose 查询揽件详情界面订单下待揽件快件详情
     * @version 3.0
     * @author lubin
     */
    public List<WaitTakeCollectDetailDto> findWaitTakeCollectDetail(int exp_ord_id);

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 揽件
     * @version 3.0
     * @author lubin
     */
    public void takeOrderCollect(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @param exp_ord_id
     * @return List<WaitStorageCollectDto>
     * @Purpose 查询订单下待入库快件详情
     * @version 3.0
     * @author lubin
     */
    public List<WaitStorageCollectDto> findWaitStorageCollectDetail(int exp_ord_id);

    /**
     * @param exp_ord_id
     * @return List<FinishCollectDetailDto>
     * @Purpose 查询订单下已完成快件详情
     * @version 3.0
     * @author lubin
     */
    public List<FinishCollectDetailDto> findFinishCollectDetail(int exp_ord_id);

    /**
     * @param orderIds
     * @Purpose 修改超时快件状态
     * @version 3.0
     * @author lubin
     */
    public void updateOvertimeCollect(List orderIds);

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 支付完成修改快件状态
     * @version 3.0
     * @author lubin
     */
    public void updatePayCollect(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @param waitStorageCollectDto
     * @Purpose 纸质面单入库添加运单号
     * @version 3.0
     * @author lubin
     */
    public void storageExpressCollect(WaitStorageCollectDto waitStorageCollectDto);

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 修改快件的快递公司与价格
     * @version 3.0
     * @author lubin
     */
    public void updateColExpComPrice(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 修改快件的快递公司
     * @version 3.0
     * @author lubin
     */
    public void updateColExpCompany(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @param exp_ord_id
     * @Purpose 取消订单
     * @version 3.0
     * @author lubin
     */
    public void cancelCollect(int exp_ord_id);

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 修改快递厚通订单号
     * @version 3.0
     * @author lubin
     */
    public void updateExpOrdCltHtNumber(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @param pageDto
     * @return Integer
     * @Purpose 查询月结用户月结订单数量
     * @version 3.0
     * @author lubin
     */
    public Integer findMonthMemberCltSize(PageDto pageDto);

    /**
     * @param pageDto
     * @return List<MonthExpOrdDto>
     * @Purpose 分页查询月结用户订单
     * @version 3.0
     * @author lubin
     */
    public List<MonthExpOrdDto> findMonthMemberCltPage(PageDto pageDto);

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 揽收月结件修改快件信息
     * @version 3.0
     * @author lubin
     */
    public void takeMonthOrderCollect(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 通过厚通单号查询快件信息
     * @version 3.0
     * @author lubin
     */
    public TdHtExpressOrderCollectDto findExpOrdCltByHtNum(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

}
