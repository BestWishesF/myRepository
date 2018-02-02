package cn.hotol.wechat.domain.model.login;

import cn.hotol.wechat.domain.model.BaseEntity;

/**
 * 用户实体类
 * Created by LuBin
 * Date 2016-11-29.
 */
public class User extends BaseEntity {

    private String mobileNo;//用户登录手机号
    private String password;//用户登录密码

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
