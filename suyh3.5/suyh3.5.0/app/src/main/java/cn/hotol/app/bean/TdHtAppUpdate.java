package cn.hotol.app.bean;

import java.sql.Timestamp;

/**
 * Created by lizhun on 2017/3/9.
 * APP更新信息表
 */
public class TdHtAppUpdate {
    private int app_upt_id;
    private int app_type;//类别1用户2代理人3统计
    private int app_system_type;//系统类别 1Android2iOS
    private int app_version;//版本号
    private int is_force;//是否强制 0是1否
    private String app_dowload_url;//下载链接
    private String app_update_description;//更新内容
    private Timestamp app_update_time;//更新时间

    public int getApp_system_type() {
        return app_system_type;
    }

    public void setApp_system_type(int app_system_type) {
        this.app_system_type = app_system_type;
    }

    public int getApp_upt_id() {
        return app_upt_id;
    }

    public void setApp_upt_id(int app_upt_id) {
        this.app_upt_id = app_upt_id;
    }

    public int getApp_type() {
        return app_type;
    }

    public void setApp_type(int app_type) {
        this.app_type = app_type;
    }

    public int getApp_version() {
        return app_version;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }

    public int getIs_force() {
        return is_force;
    }

    public void setIs_force(int is_force) {
        this.is_force = is_force;
    }

    public String getApp_dowload_url() {
        return app_dowload_url;
    }

    public void setApp_dowload_url(String app_dowload_url) {
        this.app_dowload_url = app_dowload_url;
    }

    public String getApp_update_description() {
        return app_update_description;
    }

    public void setApp_update_description(String app_update_description) {
        this.app_update_description = app_update_description;
    }

    public Timestamp getApp_update_time() {
        return app_update_time;
    }

    public void setApp_update_time(Timestamp app_update_time) {
        this.app_update_time = app_update_time;
    }
}
