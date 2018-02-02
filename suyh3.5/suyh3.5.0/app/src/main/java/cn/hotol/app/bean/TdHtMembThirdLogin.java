package cn.hotol.app.bean;

import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2017-01-04.
 */
public class TdHtMembThirdLogin {

    private int thr_id;//第三方登录主键ID
    private int thr_type;//类别
    private int third_id;//第三方配置id
    private String thr_token;//第三方唯一标示
    private String thr_head_poration;//头像
    private String thr_nick_name;//昵称
    private int thr_sex;//性别 1男2女3未知
    private Timestamp thr_register_time;//时间
    private String thr_register_month;//月份
    private int memb_id;//用户id

    public int getThr_id() {
        return thr_id;
    }

    public void setThr_id(int thr_id) {
        this.thr_id = thr_id;
    }

    public int getThr_type() {
        return thr_type;
    }

    public void setThr_type(int thr_type) {
        this.thr_type = thr_type;
    }

    public int getThird_id() {
        return third_id;
    }

    public void setThird_id(int third_id) {
        this.third_id = third_id;
    }

    public String getThr_token() {
        return thr_token;
    }

    public void setThr_token(String thr_token) {
        this.thr_token = thr_token;
    }

    public String getThr_head_poration() {
        return thr_head_poration;
    }

    public void setThr_head_poration(String thr_head_poration) {
        this.thr_head_poration = thr_head_poration;
    }

    public String getThr_nick_name() {
        return thr_nick_name;
    }

    public void setThr_nick_name(String thr_nick_name) {
        this.thr_nick_name = thr_nick_name;
    }

    public int getThr_sex() {
        return thr_sex;
    }

    public void setThr_sex(int thr_sex) {
        this.thr_sex = thr_sex;
    }

    public Timestamp getThr_register_time() {
        return thr_register_time;
    }

    public void setThr_register_time(Timestamp thr_register_time) {
        this.thr_register_time = thr_register_time;
    }

    public String getThr_register_month() {
        return thr_register_month;
    }

    public void setThr_register_month(String thr_register_month) {
        this.thr_register_month = thr_register_month;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }
}
