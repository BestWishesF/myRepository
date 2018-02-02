package cn.hotol.app.service.three.invoice;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.invoice.TdHtMembInvoiceDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-03-15.
 */
public interface InvoiceService {

    /**
     * @param token
     * @param tdHtMembInvoiceDto
     * @return RetInfo
     * @Purpose 用户申请开票
     * @version 3.0
     * @author lubin
     */
    public RetInfo saveMembInvoice(TdHtMembInvoiceDto tdHtMembInvoiceDto, String token, HttpServletRequest request);

    /**
     * @param memb_ivc_id
     * @return RetInfo
     * @Purpose 通过id查询用户开票详情
     * @version 3.0
     * @author lubin
     */
    public RetInfo findMembIvcById(int memb_ivc_id);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 通过用户最近一次开票详情
     * @version 3.0
     * @author lubin
     */
    public RetInfo findLatelyIvcInfo(String token);

}
