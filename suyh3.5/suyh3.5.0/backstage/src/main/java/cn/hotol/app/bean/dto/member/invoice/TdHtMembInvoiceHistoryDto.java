package cn.hotol.app.bean.dto.member.invoice;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lubin
 * on 2017-03-25.
 * 用户开票历史
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

    private String add_name;//姓名
    private String add_detail_address;//详细地址
    private int add_province;//省编码
    private int add_city;//市编码
    private int add_region;//区编码
    private String add_mobile_phone;//手机号
    private String express_name;//快递公司名称

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

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public String getAdd_detail_address() {
        return add_detail_address;
    }

    public void setAdd_detail_address(String add_detail_address) {
        this.add_detail_address = add_detail_address;
    }

    public int getAdd_province() {
        return add_province;
    }

    public void setAdd_province(int add_province) {
        this.add_province = add_province;
    }

    public int getAdd_city() {
        return add_city;
    }

    public void setAdd_city(int add_city) {
        this.add_city = add_city;
    }

    public int getAdd_region() {
        return add_region;
    }

    public void setAdd_region(int add_region) {
        this.add_region = add_region;
    }

    public String getAdd_mobile_phone() {
        return add_mobile_phone;
    }

    public void setAdd_mobile_phone(String add_mobile_phone) {
        this.add_mobile_phone = add_mobile_phone;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }
}
