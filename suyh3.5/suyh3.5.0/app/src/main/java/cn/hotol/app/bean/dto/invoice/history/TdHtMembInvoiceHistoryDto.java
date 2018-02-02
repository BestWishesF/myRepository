package cn.hotol.app.bean.dto.invoice.history;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017-03-15.
 */
public class TdHtMembInvoiceHistoryDto {

    private int memb_ivc_his_id;//id
    private int memb_id;//用户id
    private BigDecimal memb_inc_amount;//开票金额
    private int memb_ivc_express_id;//快递id
    private String memb_ivc_express_number;//快递单号
    private int state;//状态 0待支付1待审核2已发货
    private Timestamp memb_ivc_his_time;//申请时间
    private Timestamp  memb_ivc_his_pay_time;//支付时间
    private Timestamp memb_ivc_his_audit_time;//审核时间
    private int memb_ivc_id;//用户发票信息表id

    public int getMemb_ivc_his_id() {
        return memb_ivc_his_id;
    }

    public void setMemb_ivc_his_id(int memb_ivc_his_id) {
        this.memb_ivc_his_id = memb_ivc_his_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public BigDecimal getMemb_inc_amount() {
        return memb_inc_amount;
    }

    public void setMemb_inc_amount(BigDecimal memb_inc_amount) {
        this.memb_inc_amount = memb_inc_amount;
    }

    public int getMemb_ivc_express_id() {
        return memb_ivc_express_id;
    }

    public void setMemb_ivc_express_id(int memb_ivc_express_id) {
        this.memb_ivc_express_id = memb_ivc_express_id;
    }

    public String getMemb_ivc_express_number() {
        return memb_ivc_express_number;
    }

    public void setMemb_ivc_express_number(String memb_ivc_express_number) {
        this.memb_ivc_express_number = memb_ivc_express_number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Timestamp getMemb_ivc_his_time() {
        return memb_ivc_his_time;
    }

    public void setMemb_ivc_his_time(Timestamp memb_ivc_his_time) {
        this.memb_ivc_his_time = memb_ivc_his_time;
    }

    public Timestamp getMemb_ivc_his_pay_time() {
        return memb_ivc_his_pay_time;
    }

    public void setMemb_ivc_his_pay_time(Timestamp memb_ivc_his_pay_time) {
        this.memb_ivc_his_pay_time = memb_ivc_his_pay_time;
    }

    public Timestamp getMemb_ivc_his_audit_time() {
        return memb_ivc_his_audit_time;
    }

    public void setMemb_ivc_his_audit_time(Timestamp memb_ivc_his_audit_time) {
        this.memb_ivc_his_audit_time = memb_ivc_his_audit_time;
    }

    public int getMemb_ivc_id() {
        return memb_ivc_id;
    }

    public void setMemb_ivc_id(int memb_ivc_id) {
        this.memb_ivc_id = memb_ivc_id;
    }
}
