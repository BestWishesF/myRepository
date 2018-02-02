package cn.hotol.app.service.three.invoice.history;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.invoice.TdHtMembInvoiceDto;
import cn.hotol.app.bean.dto.invoice.history.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.bean.dto.page.PageDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-03-15.
 */
public interface InvoiceHistoryService {

    /**
     * @param token
     * @param pageDto
     * @return RetInfo
     * @Purpose 分页查询用户开票历史记录
     * @version 3.0
     * @author lubin
     */
    public RetInfo findIvcHisPage(PageDto pageDto, String token);

    /**
     * @param tdHtMembInvoiceDto
     * @return RetInfo
     * @Purpose 用户重新支付开票邮费
     * @version 3.0
     * @author lubin
     */
    public RetInfo rePayInvoice(TdHtMembInvoiceDto tdHtMembInvoiceDto, String token, HttpServletRequest request);

    /**
     * @param tdHtMembInvoiceHistoryDto
     * @return RetInfo
     * @Purpose 用户取消开票申请
     * @version 3.0
     * @author lubin
     */
    public RetInfo cancelPayInvoice(TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto);

}
