package cn.hotol.app.bean.dto.log;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lizhun on 2016/11/30.
 */

public class TdHtLoginLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int user_login_log_id;//id

    private String user_name;//用户昵称
    private String login_ip;//登陆IP
    private String login_date;//登陆月份  格式YYYYMM
    private String login_failer_desc;//登陆失败原因

    private int login_type;//登陆类别1ios2android3wechat4web
    private int user_id;//用户id
    private int is_success;//是否登陆成功 0是1否
    private int user_type;//用户类别 1用户2代理人3管理员

    private Timestamp login_time;//用户生日时间戳 默认为当前时间戳

    public int getUser_login_log_id() {
        return user_login_log_id;
    }

    public void setUser_login_log_id(int user_login_log_id) {
        this.user_login_log_id = user_login_log_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLogin_ip() {
        return login_ip;
    }

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }

    public String getLogin_date() {
        return login_date;
    }

    public void setLogin_date(String login_date) {
        this.login_date = login_date;
    }

    public String getLogin_failer_desc() {
        return login_failer_desc;
    }

    public void setLogin_failer_desc(String login_failer_desc) {
        this.login_failer_desc = login_failer_desc;
    }

    public int getLogin_type() {
        return login_type;
    }

    public void setLogin_type(int login_type) {
        this.login_type = login_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_success() {
        return is_success;
    }

    public void setIs_success(int is_success) {
        this.is_success = is_success;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public Timestamp getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Timestamp login_time) {
        this.login_time = login_time;
    }
}
