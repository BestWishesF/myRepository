package cn.hotol.app.service.express;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.express.ExpOrdBillDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public interface ExpressService {
    /**
     * @Purpose  根据快递状态分页查找快递订单
     * @version  3.0
     * @author   lizhun
     * @param    exp_ord_state,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo expressordPage(int exp_ord_state, int currentPage, int pageSize, int memb_type, HttpServletRequest request);
    /**
     * @Purpose  根据快递状态分页查找快递订单
     * @version  3.0
     * @author   lizhun
     * @param    exp_ord_state,exp_ord_id,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo expressCollectPage(int exp_ord_state,int exp_ord_id, int currentPage, int pageSize);

    /**
     * @Purpose  根据厚通中转单号查询面单打印信息
     * @version  3.0
     * @author   lubin
     * @param    ht_number
     * @return   RetInfo
     */
    public RetInfo findPrintInfo(String ht_number);

    /**
     * @Purpose 通过条件查询快递订单列表
     * @version  3.0
     * @author   lubin
     * @param exp_ord_state
     * @param currentPage
     * @param pageSize
     * @param add_mobile_phone
     * @param ht_number
     * @param express_number
     * @return RetInfo
     */
    public RetInfo searchList(int exp_ord_state, int currentPage, int pageSize, String add_mobile_phone, String ht_number, String express_number, int memb_type, HttpServletRequest request);

    /**
     * @Purpose 通过订单id查询订单信息
     * @version  3.0
     * @author   lubin
     * @param exp_ord_id
     * @return RetInfo
     */
    public RetInfo findExpOrd(Integer exp_ord_id);

    /**
     * @Purpose 通过订单id更新订单信息
     * @version  3.0
     * @author   lubin
     * @param tdHtExpressOrderDto
     * @return RetInfo
     */
    public RetInfo updateExpOrd(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose 通过订单id更新订单状态
     * @version  3.0
     * @author   lubin
     * @param exp_ord_id
     * @return RetInfo
     */
    public RetInfo delExpOrd(Integer exp_ord_id);

    /**
     * @Purpose 通过快件id查询快件信息
     * @version  3.0
     * @author   lubin
     * @param clt_id
     * @return RetInfo
     */
    public RetInfo findExpOrdClt(Integer clt_id);

    /**
     * @Purpose 通过快件id更新快件信息
     * @version  3.0
     * @author   lubin
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     */
    public RetInfo updateExpOrdClt(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @Purpose 指派代理人
     * @version  3.0
     * @author   lubin
     * @param tdHtExpressOrderDto
     * @return RetInfo
     */
    public RetInfo assignAgent(TdHtExpressOrderDto tdHtExpressOrderDto);

    /**
     * @Purpose 验证入库操作订单快递单号是否存在
     * @version  3.0
     * @author   lubin
     * @param exp_ord_id
     * @return RetInfo
     */
    public RetInfo validateExpressNumber(Integer exp_ord_id);

    /**
     * @Purpose 通过条件查询快递列表
     * @version  3.0
     * @author   lubin
     * @param expOrdBillDto
     * @return RetInfo
     */
    public RetInfo searchOrdBillList(ExpOrdBillDto expOrdBillDto, int pageSize);

    /**
     * @Purpose 快件数据导出Excel
     * @version  3.0
     * @author   lubin
     * @param expOrdBillDto
     * @return RetInfo
     */
    public RetInfo createExcel(ExpOrdBillDto expOrdBillDto, HttpServletResponse response);

    /**
     * @Purpose 通过id查询订单信息
     * @version  3.0
     * @author   lubin
     * @param exp_ord_id
     * @return RetInfo
     */
    public RetInfo findOrdById(Integer exp_ord_id);

    /**
     * @Purpose  app端不同用户权限统计数据
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     * @param tdHtAdminDto
     */
    public RetInfo statisticsDataByRole(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  app端省市区统计下单量等数据
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     * @param tsHtDictList
     */
    public RetInfo statisticsDataByDict(TsHtDictList tsHtDictList);

    /**
     * @Purpose  app端按天统计下单量等数据
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     * @param tsHtDictList
     */
    public RetInfo statisticsDayData(TsHtDictList tsHtDictList, TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  app端按需求统计下单量等数据
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     * @param params
     */
    public RetInfo statisticsDataByDemand(Map params, TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  app端按时间查询订单列表
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     * @param params
     */
    public RetInfo findExpOrdByTime(Map params, TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  app端按时间查询订单详情
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     * @param exp_ord_id
     */
    public RetInfo findExpOrdDetails(int exp_ord_id);

    /**
     * @Purpose  大客户快件入库
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     * @param tdHtExpressOrderCollectDto
     */
    public RetInfo bigCustomerStorage(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @Purpose   获取面单批量打印数据
     * @version   3.0
     * @author    lubin
     * @time      2017-03-30
     * @param     exp_ord_id  订单id
     * @return    RetInfo  查询结果
     */
    public RetInfo batchPrint(int exp_ord_id);

    /**
     * @Purpose   订单申请退款
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     exp_ord_id  订单id
     * @return    RetInfo  查询结果
     */
    public RetInfo refundExpOrd(int exp_ord_id);

}
