package cn.hotol.app.bean;

/**
 * Created by lizhun on 2016/12/24.
 * 代理人申请记录表
 */
public class TdHtAgentApply {
    private int apply_id;//id
    private int agent_id;//代理人id
    private String agent_head_portrait;//头像
    private String agent_name;//真实姓名
    private String agent_id_number;//身份证号
    private String apply_id_front;//身份证正面
    private String apply_id_back;//身份证反面
    private int agent_pay_type;//类别 1支付宝
    private String agent_pay_account;//支付账号
    private int apply_state;//状态1成功2申请中3申请失败
    private int agent_province;//省id
    private int agent_city;//市id
    private int agent_region;//区id
    private String agent_address;//地址
    private String apply_fail_reason;//审核结果
    private int divide_id;//划分区域id

    public int getApply_id() {
        return apply_id;
    }

    public void setApply_id(int apply_id) {
        this.apply_id = apply_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_head_portrait() {
        return agent_head_portrait;
    }

    public void setAgent_head_portrait(String agent_head_portrait) {
        this.agent_head_portrait = agent_head_portrait;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_id_number() {
        return agent_id_number;
    }

    public void setAgent_id_number(String agent_id_number) {
        this.agent_id_number = agent_id_number;
    }

    public String getApply_id_front() {
        return apply_id_front;
    }

    public void setApply_id_front(String apply_id_front) {
        this.apply_id_front = apply_id_front;
    }

    public String getApply_id_back() {
        return apply_id_back;
    }

    public void setApply_id_back(String apply_id_back) {
        this.apply_id_back = apply_id_back;
    }

    public int getAgent_pay_type() {
        return agent_pay_type;
    }

    public void setAgent_pay_type(int agent_pay_type) {
        this.agent_pay_type = agent_pay_type;
    }

    public String getAgent_pay_account() {
        return agent_pay_account;
    }

    public void setAgent_pay_account(String agent_pay_account) {
        this.agent_pay_account = agent_pay_account;
    }

    public int getApply_state() {
        return apply_state;
    }

    public void setApply_state(int apply_state) {
        this.apply_state = apply_state;
    }

    public int getAgent_province() {
        return agent_province;
    }

    public void setAgent_province(int agent_province) {
        this.agent_province = agent_province;
    }

    public int getAgent_city() {
        return agent_city;
    }

    public void setAgent_city(int agent_city) {
        this.agent_city = agent_city;
    }

    public int getAgent_region() {
        return agent_region;
    }

    public void setAgent_region(int agent_region) {
        this.agent_region = agent_region;
    }

    public String getAgent_address() {
        return agent_address;
    }

    public void setAgent_address(String agent_address) {
        this.agent_address = agent_address;
    }

    public String getApply_fail_reason() {
        return apply_fail_reason;
    }

    public void setApply_fail_reason(String apply_fail_reason) {
        this.apply_fail_reason = apply_fail_reason;
    }

    public int getDivide_id() {
        return divide_id;
    }

    public void setDivide_id(int divide_id) {
        this.divide_id = divide_id;
    }
}
