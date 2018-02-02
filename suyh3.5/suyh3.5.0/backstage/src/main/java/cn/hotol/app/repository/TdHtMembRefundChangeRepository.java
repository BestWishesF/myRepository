package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.refund.TdHtMembRefundChangeDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-04-27.
 */
public interface TdHtMembRefundChangeRepository {

    /**
     * @Purpose   添加退款请求
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     tdHtMembRefundChangeDto
     * @return
     */
    public void insertMembRefund(TdHtMembRefundChangeDto tdHtMembRefundChangeDto);

    /**
     * @Purpose   查询退款记录数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     map
     * @return    int
     */
    public int findRefundSize(Map<String, Object> map);

    /**
     * @Purpose   分页查询退款记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     map
     * @return    List<TdHtMembRefundChangeDto>
     */
    public List<TdHtMembRefundChangeDto> findRefundPage(Map<String, Object> map);

    /**
     * @Purpose   修改退款状态
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     tdHtMembRefundChangeDto
     * @return
     */
    public void updateRefundState(TdHtMembRefundChangeDto tdHtMembRefundChangeDto);

    /**
     * @Purpose   通过id查询退款申请
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     refund_id
     * @return    TdHtMembRefundChangeDto
     */
    public TdHtMembRefundChangeDto findRefundById(int refund_id);

    /**
     * @Purpose   通过订单id查询退款申请
     * @version   3.0
     * @author    lubin
     * @time      2017-04-27
     * @param     exp_ord_id
     * @return    TdHtMembRefundChangeDto
     */
    public TdHtMembRefundChangeDto findRefundByExpOrdId(int exp_ord_id);

}
