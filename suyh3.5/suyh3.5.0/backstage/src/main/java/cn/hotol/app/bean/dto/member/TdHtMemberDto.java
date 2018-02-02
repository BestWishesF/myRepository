package cn.hotol.app.bean.dto.member;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lizhun on 2016/11/30.
 */
public class TdHtMemberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int memb_id;//用户id
    private String memb_phone;//注册手机号码
    private String memb_password;//用户登录密码 (64)  格式：6-20位
    private String memb_nick_name;//用户昵称  手机号码注册用户默认为手机号码  第三方注册默认为第三方昵称
    private String memb_head_portrait;//用户头像文件地址
    private String memb_register_month;//户注册月份  格式YYYYMM
    private String memb_name ;//用户真实姓名  默认''
    private String memb_id_number ;//用户身份证号码 默认为''
    private String memb_invite_code;//邀请码
    private String token;//token
    private String push_token;//推送标示

    private int push_type;//推送类别1Android2iOS
    private int memb_sex;//用户性别 1男2女3其他 默认为3
    private int memb_islock;//用户锁定状态 0锁定1未锁定
    private int memb_score;//用户积分
    private int memb_role;//角色 1普通用户

    private Timestamp memb_birthday;//用户生日时间戳 默认为当前时间戳
    private Timestamp memb_register_time;//用户注册时间

    private BigDecimal memb_balance;//用户余额
    private BigDecimal memb_discount;//用户折扣  默认为1为折扣
    private int channel_id=0;//渠道id
    private int memb_register_client;//用户注册客户端
    private BigDecimal memb_register_longitude;//用户注册时的经度
    private BigDecimal memb_register_latitude;//用户注册时的纬度
    private int memb_register_region;//用户注册时的行政区id
    private String device_number;//用户设备号
    private int app_version;//APP版本号
    private int divide_id;//划分区域id

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public String getMemb_phone() {
        return memb_phone;
    }

    public void setMemb_phone(String memb_phone) {
        this.memb_phone = memb_phone;
    }

    public String getMemb_password() {
        return memb_password;
    }

    public void setMemb_password(String memb_password) {
        this.memb_password = memb_password;
    }

    public String getMemb_nick_name() {
        return memb_nick_name;
    }

    public void setMemb_nick_name(String memb_nick_name) {
        this.memb_nick_name = memb_nick_name;
    }

    public String getMemb_head_portrait() {
        return memb_head_portrait;
    }

    public void setMemb_head_portrait(String memb_head_portrait) {
        this.memb_head_portrait = memb_head_portrait;
    }

    public String getMemb_register_month() {
        return memb_register_month;
    }

    public void setMemb_register_month(String memb_register_month) {
        this.memb_register_month = memb_register_month;
    }

    public String getMemb_name() {
        return memb_name;
    }

    public void setMemb_name(String memb_name) {
        this.memb_name = memb_name;
    }

    public String getMemb_id_number() {
        return memb_id_number;
    }

    public void setMemb_id_number(String memb_id_number) {
        this.memb_id_number = memb_id_number;
    }

    public String getMemb_invite_code() {
        return memb_invite_code;
    }

    public void setMemb_invite_code(String memb_invite_code) {
        this.memb_invite_code = memb_invite_code;
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

    public int getMemb_sex() {
        return memb_sex;
    }

    public void setMemb_sex(int memb_sex) {
        this.memb_sex = memb_sex;
    }

    public int getMemb_islock() {
        return memb_islock;
    }

    public void setMemb_islock(int memb_islock) {
        this.memb_islock = memb_islock;
    }

    public int getMemb_score() {
        return memb_score;
    }

    public void setMemb_score(int memb_score) {
        this.memb_score = memb_score;
    }

    public int getMemb_role() {
        return memb_role;
    }

    public void setMemb_role(int memb_role) {
        this.memb_role = memb_role;
    }

    public Timestamp getMemb_birthday() {
        return memb_birthday;
    }

    public void setMemb_birthday(Timestamp memb_birthday) {
        this.memb_birthday = memb_birthday;
    }

    public Timestamp getMemb_register_time() {
        return memb_register_time;
    }

    public void setMemb_register_time(Timestamp memb_register_time) {
        this.memb_register_time = memb_register_time;
    }

    public BigDecimal getMemb_balance() {
        return memb_balance;
    }

    public void setMemb_balance(BigDecimal memb_balance) {
        this.memb_balance = memb_balance;
    }

    public BigDecimal getMemb_discount() {
        return memb_discount;
    }

    public void setMemb_discount(BigDecimal memb_discount) {
        this.memb_discount = memb_discount;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getMemb_register_client() {
        return memb_register_client;
    }

    public void setMemb_register_client(int memb_register_client) {
        this.memb_register_client = memb_register_client;
    }

    public BigDecimal getMemb_register_longitude() {
        return memb_register_longitude;
    }

    public void setMemb_register_longitude(BigDecimal memb_register_longitude) {
        this.memb_register_longitude = memb_register_longitude;
    }

    public BigDecimal getMemb_register_latitude() {
        return memb_register_latitude;
    }

    public void setMemb_register_latitude(BigDecimal memb_register_latitude) {
        this.memb_register_latitude = memb_register_latitude;
    }

    public int getMemb_register_region() {
        return memb_register_region;
    }

    public void setMemb_register_region(int memb_register_region) {
        this.memb_register_region = memb_register_region;
    }

    public String getDevice_number() {
        return device_number;
    }

    public void setDevice_number(String device_number) {
        this.device_number = device_number;
    }

    public int getApp_version() {
        return app_version;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }

    public int getDivide_id() {
        return divide_id;
    }

    public void setDivide_id(int divide_id) {
        this.divide_id = divide_id;
    }
}
