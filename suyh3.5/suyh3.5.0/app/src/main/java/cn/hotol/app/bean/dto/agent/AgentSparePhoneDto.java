package cn.hotol.app.bean.dto.agent;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by LuBin
 * Date 2016-12-26.
 */
public class AgentSparePhoneDto {

    @NotNull(message = "请输入正确手机号码")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "请输入正确手机号码")
    private String spare_phone;//代理人备用电话
    @NotNull(message = "请输入正确短信验证码")
    @Pattern(regexp = "^[0-9]{6}$", message = "请输入正确短信验证码")
    private String verification_code;//代理人备用电话验证码

    public String getSpare_phone() {
        return spare_phone;
    }

    public void setSpare_phone(String spare_phone) {
        this.spare_phone = spare_phone;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
}
