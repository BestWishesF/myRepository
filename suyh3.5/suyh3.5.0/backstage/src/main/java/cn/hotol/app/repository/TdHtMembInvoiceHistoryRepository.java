package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.member.invoice.TdHtMembInvoiceHistoryDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lubin
 * on 2017-03-25.
 * 用户开票历史
 */
public interface TdHtMembInvoiceHistoryRepository {

    /**
     * @Purpose   查询用户开票记录数量
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     memb_id  用户id
     * @return    int  用户开票记录数量
     */
    public int findMembInvHisSize(int memb_id);

    /**
     * @Purpose   分页查询用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     map  查询条件
     * @return    List<TdHtMembInvoiceHistoryDto>  用户开票记录
     */
    public List<TdHtMembInvoiceHistoryDto> findMembInvHisPage(Map<String, Object> map);

    /**
     * @Purpose   按条件查询用户开票记录数量
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     map  查询条件
     * @return    int  用户开票记录数量
     */
    public int searchMembInvHisSize(Map<String, Object> map);

    /**
     * @Purpose   按条件分页查询用户开票记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-28
     * @param     map  查询条件
     * @return    List<TdHtMembInvoiceHistoryDto>  用户开票记录
     */
    public List<TdHtMembInvoiceHistoryDto> searchMembInvHisPage(Map<String, Object> map);

    /**
     * @Purpose   通过用户开票信息id查询开票历史记录
     * @version   3.0
     * @author    lubin
     * @time      2017-03-29
     * @param     memb_ivc_id  开票信息id
     * @return    TdHtMembInvoiceHistoryDto  用户开票历史记录
     */
    public TdHtMembInvoiceHistoryDto findIvcHisByIvcId(int memb_ivc_id);

    /**
     * @Purpose   修改用户开票历史记录状态
     * @version   3.0
     * @author    lubin
     * @time      2017-03-29
     * @param     tdHtMembInvoiceHistoryDto  修改的内容
     * @return
     */
    public void updateIvcHisState(TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto);

}
