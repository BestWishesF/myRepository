package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/20.
 */
public interface TdHtMembFoundChangeRepository {
    /**
     * @Purpose  查找用户资金变化记录数量
     * @version  3.0
     * @author   lizhun
     * @param    memb_id
     * @return   int
     */
    public int findMemberFoundSize(int memb_id);
    /**
     * @Purpose  分页查找用户资金变化记录
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtMembFoundChangeDto>
     */
    public List<TdHtMembFoundChangeDto> findMemberFoundPage(Map<String, Object> map);

    /**
     * @Purpose  添加用户资金变动记录
     * @version  3.0
     * @author   lubin
     * @param    tdHtMembFoundChangeDto
     * @return
     */
    public void insertMemberFoundChange(TdHtMembFoundChangeDto tdHtMembFoundChangeDto);

    /**
     * @Purpose   查询订单的支付记录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     exp_ord_id  订单id
     * @return    TdHtMembFoundChangeDto
     */
    public TdHtMembFoundChangeDto findFoundByOrder(int exp_ord_id);

    /**
     * @Purpose   查询支付记录通过id
     * @version   3.0
     * @author    lubin
     * @time      2017-04-26
     * @param     mfchg_id
     * @return    TdHtMembFoundChangeDto
     */
    public TdHtMembFoundChangeDto findFoundById(int mfchg_id);

}
