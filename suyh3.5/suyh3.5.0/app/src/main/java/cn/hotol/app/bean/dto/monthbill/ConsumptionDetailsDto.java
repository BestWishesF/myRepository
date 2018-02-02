package cn.hotol.app.bean.dto.monthbill;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2017-02-15.
 */
public class ConsumptionDetailsDto {

    private BigDecimal exp_ord_amount;
    private BigDecimal exp_ord_gratuity;
    private Timestamp exp_ord_time;

    public BigDecimal getExp_ord_amount() {
        return exp_ord_amount;
    }

    public void setExp_ord_amount(BigDecimal exp_ord_amount) {
        this.exp_ord_amount = exp_ord_amount;
    }

    public BigDecimal getExp_ord_gratuity() {
        return exp_ord_gratuity;
    }

    public void setExp_ord_gratuity(BigDecimal exp_ord_gratuity) {
        this.exp_ord_gratuity = exp_ord_gratuity;
    }

    public Timestamp getExp_ord_time() {
        return exp_ord_time;
    }

    public void setExp_ord_time(Timestamp exp_ord_time) {
        this.exp_ord_time = exp_ord_time;
    }
}
