package cn.hotol.app.bean.dto.member;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2017-04-02.
 */
public class MembSetPassDto {

    private String token;//用户登录标识
    @NotNull(message = "请输入正确的新密码")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$", message = "请输入新密码")
    private String memb_password;//用户新密码 (64)  格式：6-20位

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMemb_password() {
        return memb_password;
    }

    public void setMemb_password(String memb_password) {
        this.memb_password = memb_password;
    }
}
