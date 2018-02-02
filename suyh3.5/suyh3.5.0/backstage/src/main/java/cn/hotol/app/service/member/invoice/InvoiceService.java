package cn.hotol.app.service.member.invoice;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.invoice.InvoiceExamineDto;

/**
 * Created by Administrator on 2017-03-27.
 */
public interface InvoiceService {

    /**
     * @Purpose   分页查询用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     memb_id  用户id
     * @return    RetInfo  查询结果
     */
    public RetInfo findInvoiceList(int currentPage, int pageSize, int memb_id);

    /**
     * @Purpose   按条件分页查询用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     memb_id  用户id
     * @param     mobile  收件人手机号
     * @param     state  发票状态
     * @return    RetInfo  查询结果
     */
    public RetInfo searchInvoiceList(int currentPage, int pageSize, int memb_id, String mobile, String state);

    /**
     * @Purpose   审核用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-29
     * @param     invoiceExamineDto  审核内容
     * @return    RetInfo  查询结果
     */
    public RetInfo invoiceExamine(InvoiceExamineDto invoiceExamineDto);

}
