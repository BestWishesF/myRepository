package cn.hotol.app.service.member.refund;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.refund.TdHtMembRefundChangeDto;

/**
 * Created by Administrator on 2017-04-27.
 */
public interface RefundService {

    /**
     * @Purpose   分页查找申请退款记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     refund_state  退款状态
     * @return    RetInfo
     */
    public RetInfo findRefundPage(int currentPage, int pageSize, int refund_state);

    /**
     * @Purpose   处理订单退款
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     tdHtMembRefundChangeDto
     * @return    RetInfo
     */
    public RetInfo handleRefundExpOrd(TdHtMembRefundChangeDto tdHtMembRefundChangeDto);

}
