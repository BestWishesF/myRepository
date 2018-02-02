package cn.hotol.app.bean.dto.thirdlogin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * Created by LuBin
 * Date 2017-01-11.
 */
public class BindingPhoneDto {

    @NotNull(message = "请重新授权")
    private String openid;//openid
    @NotNull(message = "请输入正确手机号码")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "请输入正确手机号码")
    private String phone;//手机号
    @NotNull(message = "请输入正确短信验证码")
    @Pattern(regexp = "^[0-9]{6}$", message = "请输入正确短信验证码")
    private String verification_code;//验证码
    private BigDecimal longitude = new BigDecimal(0);//用户注册时的经度
    private BigDecimal latitude = new BigDecimal(0);//用户注册时的纬度
    @NotNull(message = "用户设备号不能为空")
    private String device_number;//用户设备号
    private int app_version;//APP版本号
    private int memb_register_region;
    private int divide_id;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
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

    public int getMemb_register_region() {
        return memb_register_region;
    }

    public void setMemb_register_region(int memb_register_region) {
        this.memb_register_region = memb_register_region;
    }

    public int getDivide_id() {
        return divide_id;
    }

    public void setDivide_id(int divide_id) {
        this.divide_id = divide_id;
    }
}
