package cn.hotol.app.bean.dto.register;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * Created by LuBin
 * Date 2016-12-06.
 */
public class RegisterDto {

    @NotNull(message = "请输入正确手机号码")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "请输入正确手机号码")
    private String memb_phone;//注册手机号码
    @NotNull(message = "密码格式为6～20位大小写字母或数字")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$", message = "密码格式为6～20位大小写字母或数字")
    private String memb_password;//用户登录密码 (64)  格式：6-20位
    @NotNull(message = "请输入正确短信验证码")
    @Pattern(regexp = "^[0-9]{6}$", message = "请输入正确短信验证码")
    private String verification_code;//短信验证码
    @NotNull(message = "推送标示不能为空")
    private String push_token;//推送标示
    private int push_type;//推送类别1Android2iOS
    private String promote_code;//推广标示 (代理人hotolagent+id)
    private int channel_id = 0;//渠道id
    private BigDecimal memb_register_longitude = new BigDecimal(0);//用户注册时的经度
    private BigDecimal memb_register_latitude = new BigDecimal(0);//用户注册时的纬度
    private int memb_register_region = 0;//用户注册时的行政区id
    private String device_number;//用户设备号
    private int app_version;//版本号

    public String getPromote_code() {
        return promote_code;
    }

    public void setPromote_code(String promote_code) {
        this.promote_code = promote_code;
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

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
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

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
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
}
