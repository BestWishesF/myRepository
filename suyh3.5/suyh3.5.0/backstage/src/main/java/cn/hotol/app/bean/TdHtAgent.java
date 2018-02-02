package cn.hotol.app.bean;

import com.sun.org.apache.xpath.internal.operations.String;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lizhun on 2016/12/24.
 * 代理人信息表
 */
public class TdHtAgent {
    private int agent_id;//id
    private String agent_name;//真实姓名
    private int agent_sex;//性别

    private String agent_phone;//手机号
    private String agent_id_number;//身份证号
    private int agent_state;//状态1未申请2申请中3申请失败4上班中5下班6锁定
    private BigDecimal agent_longitude;//最新经度
    private BigDecimal agent_latitude;//最新纬度
    private Timestamp agent_register_time;//注册时间
    private String agent_register_month;//注册月份
    private Timestamp agent_adopt_time;//成为代理人世间
    private String agent_password;//密码
    private String agent_spare_phone;//备用电话
    private int area_id;//代理区域id
    private BigDecimal total_income;//总收入
    private BigDecimal agent_balance;//余额
    private String agent_head_portrait;//头像
    private Timestamp agent_birthday;//生日
    private String agent_email;//邮箱
    private String agent_address;//地址
    private int agent_pay_type;//支付类型1支付宝
    private String agent_pay_account;//支付账号
    private String push_token;//推送标示

    private int push_type;//推送类别1Android2iOS
    private String token;//token
    private BigDecimal agent_score;//评分

    private int agent_type;//代理人类型（1-公司员工；2-招募的代理人；）
    private int divide_id;//划分区域id
    private int app_version;//代理人登录app版本号

    public BigDecimal getAgent_score() {
        return agent_score;
    }

    public void setAgent_score(BigDecimal agent_score) {
        this.agent_score = agent_score;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getPush_token() {
        return push_token;
    }

    public void setPush_token(String push_token) {
        this.push_token = push_token;
    }

    public int getPush_type() {
        return push_type;
    }

    public void setPush_type(int push_type) {
        this.push_type = push_type;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public int getAgent_sex() {
        return agent_sex;
    }

    public void setAgent_sex(int agent_sex) {
        this.agent_sex = agent_sex;
    }

    public String getAgent_phone() {
        return agent_phone;
    }

    public void setAgent_phone(String agent_phone) {
        this.agent_phone = agent_phone;
    }

    public String getAgent_id_number() {
        return agent_id_number;
    }

    public void setAgent_id_number(String agent_id_number) {
        this.agent_id_number = agent_id_number;
    }

    public int getAgent_state() {
        return agent_state;
    }

    public void setAgent_state(int agent_state) {
        this.agent_state = agent_state;
    }

    public BigDecimal getAgent_longitude() {
        return agent_longitude;
    }

    public void setAgent_longitude(BigDecimal agent_longitude) {
        this.agent_longitude = agent_longitude;
    }

    public BigDecimal getAgent_latitude() {
        return agent_latitude;
    }

    public void setAgent_latitude(BigDecimal agent_latitude) {
        this.agent_latitude = agent_latitude;
    }

    public Timestamp getAgent_register_time() {
        return agent_register_time;
    }

    public void setAgent_register_time(Timestamp agent_register_time) {
        this.agent_register_time = agent_register_time;
    }

    public String getAgent_register_month() {
        return agent_register_month;
    }

    public void setAgent_register_month(String agent_register_month) {
        this.agent_register_month = agent_register_month;
    }

    public Timestamp getAgent_adopt_time() {
        return agent_adopt_time;
    }

    public void setAgent_adopt_time(Timestamp agent_adopt_time) {
        this.agent_adopt_time = agent_adopt_time;
    }

    public String getAgent_password() {
        return agent_password;
    }

    public void setAgent_password(String agent_password) {
        this.agent_password = agent_password;
    }

    public String getAgent_spare_phone() {
        return agent_spare_phone;
    }

    public void setAgent_spare_phone(String agent_spare_phone) {
        this.agent_spare_phone = agent_spare_phone;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public BigDecimal getTotal_income() {
        return total_income;
    }

    public void setTotal_income(BigDecimal total_income) {
        this.total_income = total_income;
    }

    public BigDecimal getAgent_balance() {
        return agent_balance;
    }

    public void setAgent_balance(BigDecimal agent_balance) {
        this.agent_balance = agent_balance;
    }

    public String getAgent_head_portrait() {
        return agent_head_portrait;
    }

    public void setAgent_head_portrait(String agent_head_portrait) {
        this.agent_head_portrait = agent_head_portrait;
    }

    public Timestamp getAgent_birthday() {
        return agent_birthday;
    }

    public void setAgent_birthday(Timestamp agent_birthday) {
        this.agent_birthday = agent_birthday;
    }

    public String getAgent_email() {
        return agent_email;
    }

    public void setAgent_email(String agent_email) {
        this.agent_email = agent_email;
    }

    public String getAgent_address() {
        return agent_address;
    }

    public void setAgent_address(String agent_address) {
        this.agent_address = agent_address;
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

    public int getAgent_type() {
        return agent_type;
    }

    public void setAgent_type(int agent_type) {
        this.agent_type = agent_type;
    }

    public int getDivide_id() {
        return divide_id;
    }

    public void setDivide_id(int divide_id) {
        this.divide_id = divide_id;
    }

    public int getApp_version() {
        return app_version;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }
}
