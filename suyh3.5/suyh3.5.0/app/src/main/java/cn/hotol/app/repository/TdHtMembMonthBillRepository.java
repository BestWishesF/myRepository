package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.monthbill.ConsumptionRecordDto;
import cn.hotol.app.bean.dto.monthbill.TdHtMembMonthBillDto;
import cn.hotol.app.bean.dto.page.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LuBin
 * Date 2017-02-15.
 */
@Repository
public interface TdHtMembMonthBillRepository {

    /**
     * @param tdHtMembMonthBillDto
     * @return TdHtMembMonthBillDto
     * @Purpose 查询用户未支付的非本月月结账单记录
     * @version 3.0
     * @author lubin
     */
    public TdHtMembMonthBillDto findMemberNoPayNotMonthBill(TdHtMembMonthBillDto tdHtMembMonthBillDto);

    /**
     * @param memb_mon_bill_id
     * @return TdHtMembMonthBillDto
     * @Purpose 通过月结账单id查询账单信息
     * @version 3.0
     * @author lubin
     */
    public TdHtMembMonthBillDto findMemberMonthBillById(Integer memb_mon_bill_id);

    /**
     * @param tdHtMembMonthBillDto
     * @Purpose 修改待支付月结账单信息
     * @version 3.0
     * @author lubin
     */
    public void updateWaitPayBill(TdHtMembMonthBillDto tdHtMembMonthBillDto);

    /**
     * @param tdHtMembMonthBillDto
     * @Purpose 支付成功修改月结账单
     * @version 3.0
     * @author lubin
     */
    public void updatePayMonthBill(TdHtMembMonthBillDto tdHtMembMonthBillDto);

    /**
     * @param memb_id
     * @return Integer
     * @Purpose 查询用户月结消费数量
     * @version 3.0
     * @author lubin
     */
    public Integer findMemberMonthBillSize(int memb_id);

    /**
     * @param pageDto
     * @return List<TdHtMembMonthBillDto>
     * @Purpose 分页查询用户月结消费记录
     * @version 3.0
     * @author lubin
     */
    public List<ConsumptionRecordDto> findMemberMonthBillPage(PageDto pageDto);

    /**
     * @param tdHtMembMonthBillDto
     * @Purpose 保存新的月结账单
     * @version 3.0
     * @author lubin
     */
    public void insertMemberMonthBill(TdHtMembMonthBillDto tdHtMembMonthBillDto);

}
