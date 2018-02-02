package cn.hotol.app.bean.dto.monthbill;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2017-02-15.
 */
public class ConsumptionRecordDto {

    private int  memb_mon_bill_id;//id
    private BigDecimal memb_mon_bill_pay_amount;//支付金额
    private Timestamp memb_mon_bill_time;//账单生成时间
    private int  state;//状态 1待支付2已支付

    public int getMemb_mon_bill_id() {
        return memb_mon_bill_id;
    }

    public void setMemb_mon_bill_id(int memb_mon_bill_id) {
        this.memb_mon_bill_id = memb_mon_bill_id;
    }

    public BigDecimal getMemb_mon_bill_pay_amount() {
        return memb_mon_bill_pay_amount;
    }

    public void setMemb_mon_bill_pay_amount(BigDecimal memb_mon_bill_pay_amount) {
        this.memb_mon_bill_pay_amount = memb_mon_bill_pay_amount;
    }

    public Timestamp getMemb_mon_bill_time() {
        return memb_mon_bill_time;
    }

    public void setMemb_mon_bill_time(Timestamp memb_mon_bill_time) {
        this.memb_mon_bill_time = memb_mon_bill_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
