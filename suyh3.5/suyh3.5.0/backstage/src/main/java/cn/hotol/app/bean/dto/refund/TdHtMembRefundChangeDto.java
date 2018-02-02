package cn.hotol.app.bean.dto.refund;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017-04-27.
 */
public class TdHtMembRefundChangeDto {

    private int refund_id;//退款记录id
    private int memb_id;//用户id
    private BigDecimal refund_amount;//退款金额
    private String refund_number;//退款订单号（唯一，退款用）
    private Timestamp refund_time;//退款时间
    private String refund_month;//退款记录产生月份
    private int refund_state;//退款状态（0成功；1待审批；2未通过）
    private int refund_type;//退款类型（1订单退款；）
    private int mfchg_id;//支付记录id
    private String mfchg_number;//支付订单号
    private int mfchg_channel;//支付渠道 1微信2支付宝3余额4公众号
    private int exp_ord_id;//订单id
    @Length(min = 1, max = 150, message = "请输入审核原因.")
    private String refund_failure_cause;//失败原因

    private String memb_phone;//用户手机号

    public int getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(int refund_id) {
        this.refund_id = refund_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public BigDecimal getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(BigDecimal refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getRefund_number() {
        return refund_number;
    }

    public void setRefund_number(String refund_number) {
        this.refund_number = refund_number;
    }

    public Timestamp getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(Timestamp refund_time) {
        this.refund_time = refund_time;
    }

    public String getRefund_month() {
        return refund_month;
    }

    public void setRefund_month(String refund_month) {
        this.refund_month = refund_month;
    }

    public int getRefund_state() {
        return refund_state;
    }

    public void setRefund_state(int refund_state) {
        this.refund_state = refund_state;
    }

    public int getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(int refund_type) {
        this.refund_type = refund_type;
    }

    public int getMfchg_id() {
        return mfchg_id;
    }

    public void setMfchg_id(int mfchg_id) {
        this.mfchg_id = mfchg_id;
    }

    public String getMfchg_number() {
        return mfchg_number;
    }

    public void setMfchg_number(String mfchg_number) {
        this.mfchg_number = mfchg_number;
    }

    public int getMfchg_channel() {
        return mfchg_channel;
    }

    public void setMfchg_channel(int mfchg_channel) {
        this.mfchg_channel = mfchg_channel;
    }

    public int getExp_ord_id() {
        return exp_ord_id;
    }

    public void setExp_ord_id(int exp_ord_id) {
        this.exp_ord_id = exp_ord_id;
    }

    public String getRefund_failure_cause() {
        return refund_failure_cause;
    }

    public void setRefund_failure_cause(String refund_failure_cause) {
        this.refund_failure_cause = refund_failure_cause;
    }

    public String getMemb_phone() {
        return memb_phone;
    }

    public void setMemb_phone(String memb_phone) {
        this.memb_phone = memb_phone;
    }
}
