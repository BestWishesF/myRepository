package cn.hotol.app.bean.dto.member;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by LuBin
 * Date 2016-12-07.
 */
public class ForgetPwdDto {

    private int memb_id;//用户id
    @NotNull(message = "请输入正确手机号码")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "请输入正确手机号码")
    private String memb_phone;//注册手机号码
    @NotNull(message = "密码格式为6～20位大小写字母或数字")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$", message = "密码格式为6～20位大小写字母或数字")
    private String memb_password;//用户登录密码 (64)  格式：6-20位
    @NotNull(message = "请输入正确短信验证码")
    @Pattern(regexp = "^[0-9]{6}$", message = "请输入正确短信验证码")
    private String verification_code;//短信验证码

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

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
}
