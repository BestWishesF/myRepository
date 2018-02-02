package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.invoice.TdHtMembInvoiceDto;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017-03-15.
 */

@Repository
public interface TdHtMembInvoiceRepository {

    /**
     * @param tdHtMembInvoiceDto
     * @return
     * @Purpose 保存用户申请开票信息
     * @version 3.0
     * @author lubin
     */
    public void insertMembInvoice(TdHtMembInvoiceDto tdHtMembInvoiceDto);

    /**
     * @param memb_ivc_id
     * @return TdHtMembInvoiceDto
     * @Purpose 通过id查询用户开票详情
     * @version 3.0
     * @author lubin
     */
    public TdHtMembInvoiceDto findMembIvcById(int memb_ivc_id);

    /**
     * @param memb_id
     * @return TdHtMembInvoiceDto
     * @Purpose 通过用户最近一次开票详情
     * @version 3.0
     * @author lubin
     */
    public TdHtMembInvoiceDto findLatelyIvcInfo(int memb_id);

}
