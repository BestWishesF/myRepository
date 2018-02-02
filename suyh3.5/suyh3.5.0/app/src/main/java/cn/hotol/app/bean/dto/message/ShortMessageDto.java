package cn.hotol.app.bean.dto.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LuBin
 * Date 2016-12-06.
 */
public class ShortMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "请输入正确手机号码")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "请输入正确手机号码")
    private String phoneno;//手机号码
    private Date send_time;//发送时间
    private String verification_code;//短信验证码
    private int validity;//有效期时效（分钟）

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }
}
