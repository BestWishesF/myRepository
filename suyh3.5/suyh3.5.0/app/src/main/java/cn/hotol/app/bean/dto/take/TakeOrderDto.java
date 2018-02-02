package cn.hotol.app.bean.dto.take;

import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-29.
 */
public class TakeOrderDto {

    private int exp_ord_id;//订单id
    private int pay_type;//支付方式 1、直接支付；2、代付
    private List<TdHtExpressOrderCollectDto> collects;//快件列表
    private int exp_ord_type;//订单类型（1 电子面单；2 纸质面单；）
    private BigDecimal adjusted_amount;//调整金额

    public int getExp_ord_id() {
        return exp_ord_id;
    }

    public void setExp_ord_id(int exp_ord_id) {
        this.exp_ord_id = exp_ord_id;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public List<TdHtExpressOrderCollectDto> getCollects() {
        return collects;
    }

    public void setCollects(List<TdHtExpressOrderCollectDto> collects) {
        this.collects = collects;
    }

    public int getExp_ord_type() {
        return exp_ord_type;
    }

    public void setExp_ord_type(int exp_ord_type) {
        this.exp_ord_type = exp_ord_type;
    }

    public BigDecimal getAdjusted_amount() {
        return adjusted_amount;
    }

    public void setAdjusted_amount(BigDecimal adjusted_amount) {
        this.adjusted_amount = adjusted_amount;
    }
}
