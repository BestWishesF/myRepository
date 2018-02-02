package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.found.MemberFinancialDetailsDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.page.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/20.
 */

@Repository
public interface TdHtMembFoundChangeRepository {

    /**
     * @param memb_id
     * @return int
     * @Purpose 查找用户资金变化记录数量
     * @version 3.0
     * @author lizhun
     */
    public int findMemberFoundSize(int memb_id);

    /**
     * @param pageDto
     * @return List<MemberFinancialDetailsDto>
     * @Purpose 分页查找用户资金变化记录
     * @version 3.0
     * @author lizhun
     */
    public List<MemberFinancialDetailsDto> findMemberFoundPage(PageDto pageDto);

    /**
     * @param tdHtMembFoundChangeDto
     * @return void
     * @Purpose 添加资金变动记录
     * @version 3.0
     * @author lizhun
     */
    public void insertMemberFoundChange(TdHtMembFoundChangeDto tdHtMembFoundChangeDto);

    /**
     * @param mfchg_number
     * @return TdHtMembFoundChangeDto
     * @Purpose 根据订单号查找资金变化记录
     * @version 3.0
     * @author lizhun
     */
    public TdHtMembFoundChangeDto findfindMemberFoundByNumber(String mfchg_number);

    /**
     * @param tdHtMembFoundChangeDto
     * @return void
     * @Purpose 更新资金变化记录
     * @version 3.0
     * @author lizhun
     */
    public void updateMemberFoundChange(TdHtMembFoundChangeDto tdHtMembFoundChangeDto);

}
