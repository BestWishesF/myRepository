package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.invoice.history.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.bean.dto.page.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-15.
 */

@Repository
public interface TdHtMembInvoiceHistoryRepository {

    /**
     * @param tdHtMembInvoiceHistoryDto
     * @return
     * @Purpose 保存用户开票历史记录信息
     * @version 3.0
     * @author lubin
     */
    public void insertMembInvoiceHis(TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto);

    /**
     * @param memb_ivc_his_id
     * @return TdHtMembInvoiceHistoryDto
     * @Purpose 通过id查询用户开票历史记录信息
     * @version 3.0
     * @author lubin
     */
    public TdHtMembInvoiceHistoryDto findMembInvoiceHisById(int memb_ivc_his_id);

    /**
     * @param tdHtMembInvoiceHistoryDto
     * @return
     * @Purpose 通过id修改用户开票历史记录信息状态
     * @version 3.0
     * @author lubin
     */
    public void updateMembInvoiceHisById(TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto);

    /**
     * @param memb_id
     * @return int
     * @Purpose 查询用户的开票历史记录数量
     * @version 3.0
     * @author lubin
     */
    public int findMembInvoiceHisSize(int memb_id);

    /**
     * @param pageDto
     * @return List<TdHtMembInvoiceHistoryDto>
     * @Purpose 分页查询用户的开票历史记录
     * @version 3.0
     * @author lubin
     */
    public List<TdHtMembInvoiceHistoryDto> findMembInvoiceHisPage(PageDto pageDto);

    /**
     * @param memb_ivc_id
     * @return TdHtMembInvoiceHistoryDto
     * @Purpose 通过开票信息id查询用户开票历史记录信息
     * @version 3.0
     * @author lubin
     */
    public TdHtMembInvoiceHistoryDto findMembIvcHisByIvcId(int memb_ivc_id);

    /**
     * @param tdHtMembInvoiceHistoryDto
     * @return List<TdHtMembInvoiceHistoryDto>
     * @Purpose 查询一定时间前的不同状态的开票历史记录
     * @version 3.0
     * @author lubin
     */
    public List<TdHtMembInvoiceHistoryDto> findMembIvcHisByTime(TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto);

    /**
     * @param map
     * @return
     * @Purpose 批量修改开票状态
     * @version 3.0
     * @author lubin
     */
    public void batchUpdateMembIvcHisState(Map<String, Object> map);

}
