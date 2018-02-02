package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.express.ExpOrdDetailedDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;

import java.util.List;
import java.util.Map;

/**
 * Created by liyafei on 2016/12/1.
 */
public interface TdHtExpressOrderRepository {
    /**
     * @Purpose  根据状态查找快递订单数量
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findExpressOrderByStateSize(Map<String, Object> map);
    /**
     * @Purpose  根据状态查找快递订单分页
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtExpressOrderDto>
     */
    public List<TdHtExpressOrderDto> findExpressOrderByStatePage(Map<String, Object> map);

    /**
     * @Purpose  根据订单id查询订单信息
     * @version  3.0
     * @author   lubin
     * @param    exp_ord_id
     * @return   TdHtExpressOrderDto
     */
    public TdHtExpressOrderDto findTdHtExpressOrderDtoById(int exp_ord_id);

    /**
     * @Purpose  修改入库订单状态
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return
     */
    public void updateStorageExpressOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  保存订单
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return
     */
    public void insertExpressOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  根据状态查找快递订单数量
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findExpressOrderBySearchSize(Map<String, Object> map);
    /**
     * @Purpose  根据状态查找快递订单分页
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtExpressOrderDto>
     */
    public List<TdHtExpressOrderDto> findExpressOrderBySearchPage(Map<String, Object> map);

    /**
     * @Purpose  通过订单id更新订单信息
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return
     */
    public void updateExpOrdByExpOrdId(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  通过订单id更新订单状态
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return
     */
    public void updateExpOrdState(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  指派代理人
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return
     */
    public void updateAssignAgent(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  查询订单量
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return
     */
    public Integer findExpOrdCount(Map<String, Object> map);

    /**
     * @Purpose  查询新客量
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return
     */
    public Integer findNewCustomerCount(Map<String, Object> map);

    /**
     * @Purpose  查询一定时间内每天的下单量
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return List<Map<String,Object>>
     */
    public List<Map<String,Object>> statisticsOrdByCondition(Map<String, Object> map);

    /**
     * @Purpose  查询一定时间内每天的新客量
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return List<Map<String,Object>>
     */
    public List<Map<String,Object>> statisticsCustomByCondition(Map<String, Object> map);

    /**
     * @Purpose  根据时间、地址查询订单数量
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return int
     */
    public int findExpOrdByTimeSize(Map<String, Object> map);

    /**
     * @Purpose  根据时间、地址分页查询订单
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return List<ExpOrdDetailedDto>
     */
    public List<ExpOrdDetailedDto> findExpOrdByTimePage(Map<String, Object> map);

    /**
     * @Purpose  根据订单id查询订单信息
     * @version  3.0
     * @author   lubin
     * @param    exp_ord_id
     * @return ExpOrdDetailedDto
     */
    public ExpOrdDetailedDto findExpOrdDetailedDtoById(int exp_ord_id);

    /**
     * @Purpose  查询该区未分配划分区域id的订单
     * @version  3.0
     * @author   lubin
     * @param    add_region
     * @return List<TdHtExpressOrderDto>
     */
    public List<TdHtExpressOrderDto> findOrderByRegion(int add_region);

    /**
     * @Purpose  修改订单的划分区域id
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return
     */
    public void updateOrderDivide(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose  根据划分区域id修改订单
     * @version  3.0
     * @author   lubin
     * @param    divide_id
     * @return
     */
    public void updateOrderByDivide(int divide_id);

    /**
     * @Purpose  修改大客户订单
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderDto
     * @return
     */
    public void updateBigCusExp(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose   通过用户开票信息id查询开票历史记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-29
     * @param     memb_ivc_his_id  开票信息id
     * @return
     */
    public void updateExpOrdIvcHis(int memb_ivc_his_id);

    /**
     * @Purpose   查询用户发件数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-12
     * @param     memb_id  用户id
     * @return
     */
    public int findExpOrdSizeByMemb(int memb_id);

    /**
     * @Purpose   查询用户被代理人接单数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-14
     * @param     tdHtExpressOrderDto
     * @return
     */
    public int findExpOrdNumByMembAndAgent(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose   查询用户30分钟内支付订单数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-14
     * @param     tdHtExpressOrderDto
     * @return
     */
    public int findMembPayExpOrdCount(TdHtExpressOrderDto tdHtExpressOrderDto);
}
