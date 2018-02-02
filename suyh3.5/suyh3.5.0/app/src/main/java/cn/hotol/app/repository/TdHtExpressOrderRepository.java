package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.accept.WaitAccessOrderDetailDto;
import cn.hotol.app.bean.dto.accept.WaitAccessOrderDto;
import cn.hotol.app.bean.dto.expressorder.AcceptInExpDto;
import cn.hotol.app.bean.dto.expressorder.AcceptOrderDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.expressorder.ToBePaidExpDto;
import cn.hotol.app.bean.dto.finish.FinishOrderDetailDto;
import cn.hotol.app.bean.dto.finish.FinishOrderDto;
import cn.hotol.app.bean.dto.monthbill.ConsumptionDetailsDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.push.PushDto;
import cn.hotol.app.bean.dto.storage.WaitStorageOrdDetailDto;
import cn.hotol.app.bean.dto.storage.WaitStorageOrderDto;
import cn.hotol.app.bean.dto.take.WaitTakeOrderDetailDto;
import cn.hotol.app.bean.dto.take.WaitTakeOrderDto;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liyafei on 2016/12/1.
 */
@Repository
public interface TdHtExpressOrderRepository {
    /**
     * @param memb_id
     * @return List<TdHtExpressSearch>
     * @Purpose 查询寄件记录
     * @version 3.0
     * @author liyafei
     */
    public List<TdHtExpressOrderDto> findTdHtExpressOrder(int memb_id);

    /**
     * @param exp_ord_id
     * @return TdHtExpressOrderDto
     * @Purpose 查询寄件记录
     * @version 3.0
     * @author liyafei
     */
    public TdHtExpressOrderDto findTdHtExpressOrderById(int exp_ord_id);
//********************************************************查询止************************************************************************

    /**
     * @param tdHtExpressOrder
     * @return void
     * @Purpose 添加寄件信息
     * @version 3.0
     * @author liyafei
     */
    public int insertExpressOrder(TdHtExpressOrderDto tdHtExpressOrder);

    /**
     * @param tdHtExpressOrder
     * @return void
     * @Purpose 修改寄件信息
     * @version 3.0
     * @author liyafei
     */
    public void cancelExpressOrder(TdHtExpressOrderDto tdHtExpressOrder);

    /**
     * @param memb_id
     * @return Integer
     * @Purpose 查询待支付的订单总数量
     * @version 3.0
     * @author lubin
     */
    public Integer findToBePaidExpAmount(Integer memb_id);

    /**
     * @param expressPageDto
     * @return List<ToBePaidExpDto>
     * @Purpose 分页查询待支付的订单
     * @version 3.0
     * @author lubin
     */
    public List<ToBePaidExpDto> findToBePaidExp(PageDto expressPageDto);

    /**
     * @param memb_id
     * @return Integer
     * @Purpose 查询受理中的订单总数量
     * @version 3.0
     * @author lubin
     */
    public Integer findAcceptInExpAmount(Integer memb_id);

    /**
     * @param expressPageDto
     * @return List<AcceptInExpDto>
     * @Purpose 分页查询受理中的订单
     * @version 3.0
     * @author lubin
     */
    public List<AcceptInExpDto> findAcceptInExp(PageDto expressPageDto);

    /**
     * @param exp_ord_id
     * @return AcceptOrderDto
     * @Purpose 查询受理中的订单
     * @version 3.0
     * @author lubin
     */
    public AcceptOrderDto findAcceptOrderById(int exp_ord_id);

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 更新订单状态为已支付
     * @version 3.0
     * @author lubin
     */
    public void payExpressOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param pageDto
     * @return int
     * @Purpose 查询区域内待接单订单数量
     * @version 3.0
     * @author lubin
     */
    public int findWaitForAccessCount(PageDto pageDto);

    /**
     * @param pageDto
     * @return List<WaitAccessOrderDto>
     * @Purpose 分页查询待接单订单
     * @version 3.0
     * @author lubin
     */
    public List<WaitAccessOrderDto> findWaitForAccess(PageDto pageDto);

    /**
     * @param exp_ord_id
     * @return WaitAccessOrderDetailDto
     * @Purpose 查询待接单订单详情
     * @version 3.0
     * @author lubin
     */
    public WaitAccessOrderDetailDto findWaitAccessDetail(int exp_ord_id);

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 代理人接单
     * @version 3.0
     * @author lubin
     */
    public void accessExpressOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param agent_id
     * @return int
     * @Purpose 查询代理人待揽件订单数量
     * @version 3.0
     * @author lubin
     */
    public int findWaitForTakeCount(int agent_id);

    /**
     * @param pageDto
     * @return List<WaitTakeOrderDto>
     * @Purpose 分页查询待揽件订单
     * @version 3.0
     * @author lubin
     */
    public List<WaitTakeOrderDto> findWaitForTake(PageDto pageDto);

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 更新代理人揽件备注
     * @version 3.0
     * @author lubin
     */
    public void updateAgentNote(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param exp_ord_id
     * @return WaitTakeOrderDetailDto
     * @Purpose 查询代理人揽件订单详情
     * @version 3.0
     * @author lubin
     */
    public WaitTakeOrderDetailDto findWaitTakeDetail(int exp_ord_id);

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 代理人揽件
     * @version 3.0
     * @author lubin
     */
    public void takeOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param exp_ord_id
     * @return WaitTakeOrderDetailDto
     * @Purpose 查询快件发件省id
     * @version 3.0
     * @author lubin
     */
    public TdHtExpressOrderDto findSendProvince(int exp_ord_id);

    /**
     * @param agent_id
     * @return int
     * @Purpose 查询代理人待入库订单数量
     * @version 3.0
     * @author lubin
     */
    public int findWaitStorgeOrderCount(int agent_id);

    /**
     * @param pageDto
     * @return List<WaitStorageOrderDto>
     * @Purpose 分页查询代理人待入库订单
     * @version 3.0
     * @author lubin
     */
    public List<WaitStorageOrderDto> findWaitStorgeOrder(PageDto pageDto);

    /**
     * @param exp_ord_id
     * @return WaitStorageOrdDetailDto
     * @Purpose 查询代理人待入库订单详情
     * @version 3.0
     * @author lubin
     */
    public WaitStorageOrdDetailDto findStorageOrdDetail(int exp_ord_id);

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 代理人入库订单
     * @version 3.0
     * @author lubin
     */
    public void storageExpressOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param agent_id
     * @return int
     * @Purpose 查询代理人已完成订单数量
     * @version 3.0
     * @author lubin
     */
    public int findFinishOrderCount(int agent_id);

    /**
     * @param pageDto
     * @return List<FinishOrderDto>
     * @Purpose 分页查询代理人已完成订单
     * @version 3.0
     * @author lubin
     */
    public List<FinishOrderDto> findFinishOrder(PageDto pageDto);

    /**
     * @param exp_ord_id
     * @return FinishOrderDetailDto
     * @Purpose 查询代理人已完成订单详情
     * @version 3.0
     * @author lubin
     */
    public FinishOrderDetailDto findFinishOrdDetail(int exp_ord_id);

    /**
     * @param agent_id
     * @return Integer
     * @Purpose 查询代理人揽收快件数量
     * @version 3.0
     * @author lubin
     */
    public Integer findAgentTakingNum(int agent_id);

    /**
     * @param agent_id
     * @return Integer
     * @Purpose 查询代理人按时完成揽收快件数量
     * @version 3.0
     * @author lubin
     */
    public Integer findAgentFinishOnTime(int agent_id);

    /**
     * @param exp_ord_time
     * @Purpose 查询超时件订单和推送信息
     * @version 3.0
     * @author lubin
     */
    public List<PushDto> findOvertimeOrder(Date exp_ord_time);

    /**
     * @param orderIds
     * @Purpose 修改超时订单状态
     * @version 3.0
     * @author lubin
     */
    public void updateOvertimeOrder(List orderIds);

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 修改订单快递公司和价格
     * @version 3.0
     * @author lubin
     */
    public void updateOrdExpComPrice(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param exp_ord_clt_id
     * @return TdHtExpressOrderDto
     * @Purpose 通过快件id查询订单信息
     * @version 3.0
     * @author lubin
     */
    public TdHtExpressOrderDto findExpOrdByCltId(int exp_ord_clt_id);

    /**
     * @param tdHtExpressOrderDto
     * @return Double
     * @Purpose 通过用户id和月份查询订单
     * @version 3.0
     * @author lubin
     */
    public List<TdHtExpressOrderDto> findExpOrdByMonthAndMemb(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param pageDto
     * @return Integer
     * @Purpose 查询月结用户月消费订单数量
     * @version 3.0
     * @author lubin
     */
    public Integer findMonConsumeDetailsSize(PageDto pageDto);

    /**
     * @param pageDto
     * @return List<ConsumptionDetailsDto>
     * @Purpose 分页查询月结用户月消费订单
     * @version 3.0
     * @author lubin
     */
    public List<ConsumptionDetailsDto> findMonConsumeDetailsPage(PageDto pageDto);

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 代理人揽收月结件修改订单状态
     * @version 3.0
     * @author lubin
     */
    public void takeMonthOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param memb_id
     * @return TdHtExpressOrderDto
     * @Purpose 查询用户最新待揽件订单
     * @version 3.0
     * @author lubin
     */
    public TdHtExpressOrderDto findNewestWaitTakeExpOrd(int memb_id);

    /**
     * @param tdHtExpressOrderDto
     * @return Integer
     * @Purpose 查询用户最新待揽件订单
     * @version 3.0
     * @author lubin
     */
    public Integer findExpOrdNumByMembAndAgent(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param tdHtExpressOrderDto
     * @return Integer
     * @Purpose 查询用户最新待揽件订单
     * @version 3.0
     * @author lubin
     */
    public TdHtExpressOrderDto findMembPayExpOrd(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param tdHtExpressOrderDto
     * @return Integer
     * @Purpose 查询用户30分钟内支付订单数量
     * @version 3.0
     * @author lubin
     */
    public Integer findMembPayExpOrdCount(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @param memb_id
     * @return Integer
     * @Purpose 查询用户未开票的订单数量
     * @version 3.0
     * @author lubin
     */
    public int findMembCanInvoiceExpOrdSize(int memb_id);

    /**
     * @param pageDto
     * @return List<TdHtExpressOrderDto>
     * @Purpose 分页查询用户未开票的订单列表
     * @version 3.0
     * @author lubin
     */
    public List<TdHtExpressOrderDto> findMembCanInvoiceExpOrdPage(PageDto pageDto);

    /**
     * @param orderIds
     * @return Double
     * @Purpose 查询开票订单支付金额总值
     * @version 3.0
     * @author lubin
     */
    public Map<String, Object> findSumPayAmountByOrderList(List<Integer> orderIds);

    /**
     * @param map
     * @return
     * @Purpose 修改开票订单所属开票历史记录
     * @version 3.0
     * @author lubin
     */
    public void updateInvoiceHisOrder(Map<String, Object> map);

    /**
     * @param memb_ivc_his_id
     * @return
     * @Purpose 用户取消开票申请
     * @version 3.0
     * @author lubin
     */
    public void cancelInvoice(int memb_ivc_his_id);

    /**
     * @param membIvcHisIds
     * @return
     * @Purpose 批量取消开票申请
     * @version 3.0
     * @author lubin
     */
    public void batchCancelInvoice(List<Integer> membIvcHisIds);
    /**
     * @Purpose  根据用户id查找用户是否有已完成寄件
     * @version  3.4
     * @author   lizhun
     * @param    memb_id
     * @return   RetInfo
     */
    public TdHtExpressOrderDto findExpressOrderIsEndByMembId(int memb_id);
}
