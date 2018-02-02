package cn.hotol.app.bean.dto.agent;

import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-24.
 */
public class TdHtAgentInfoDto {

    private String agent_name;//姓名
    private String agent_email;//邮箱
    private String agent_spare_phone;//备用电话
    private String agent_pay_account;//提现账号
    private int agent_sex;//1男2女
    private Timestamp agent_birthday;//生日
    private String agent_address;//联系地址
    private String agent_phone;//电话
    private String agent_head_portrait;//照片


    private String agent_code;//代理人推广标示 (hotolagent+代理人id)

    public String getAgent_code() {
        return agent_code;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_email() {
        return agent_email;
    }

    public void setAgent_email(String agent_email) {
        this.agent_email = agent_email;
    }

    public String getAgent_spare_phone() {
        return agent_spare_phone;
    }

    public void setAgent_spare_phone(String agent_spare_phone) {
        this.agent_spare_phone = agent_spare_phone;
    }

    public String getAgent_pay_account() {
        return agent_pay_account;
    }

    public void setAgent_pay_account(String agent_pay_account) {
        this.agent_pay_account = agent_pay_account;
    }

    public int getAgent_sex() {
        return agent_sex;
    }

    public void setAgent_sex(int agent_sex) {
        this.agent_sex = agent_sex;
    }

    public Timestamp getAgent_birthday() {
        return agent_birthday;
    }

    public void setAgent_birthday(Timestamp agent_birthday) {
        this.agent_birthday = agent_birthday;
    }

    public String getAgent_address() {
        return agent_address;
    }

    public void setAgent_address(String agent_address) {
        this.agent_address = agent_address;
    }

    public String getAgent_phone() {
        return agent_phone;
    }

    public void setAgent_phone(String agent_phone) {
        this.agent_phone = agent_phone;
    }

    public String getAgent_head_portrait() {
        return agent_head_portrait;
    }

    public void setAgent_head_portrait(String agent_head_portrait) {
        this.agent_head_portrait = agent_head_portrait;
    }
}
