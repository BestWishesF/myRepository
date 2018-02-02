package cn.hotol.app.bean.dto.member;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by LuBin
 * Date 2016-12-07.
 */
public class MemberPwdDto {

    private int memb_id;//用户id
    @NotNull(message = "请输入正确的新密码")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$", message = "请输入新密码")
    private String memb_password;//用户新密码 (64)  格式：6-20位
    @NotNull(message = "请输入正确的原密码")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$", message = "请输入正确的原密码")
    private String old_password;//用户原密码
    private String memb_phone;

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public String getMemb_password() {
        return memb_password;
    }

    public void setMemb_password(String memb_password) {
        this.memb_password = memb_password;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getMemb_phone() {
        return memb_phone;
    }

    public void setMemb_phone(String memb_phone) {
        this.memb_phone = memb_phone;
    }
}
