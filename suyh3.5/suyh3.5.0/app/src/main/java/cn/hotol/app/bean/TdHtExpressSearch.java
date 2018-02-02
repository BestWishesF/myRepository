package cn.hotol.app.bean;

import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-01.
 */
public class TdHtExpressSearch {

    private int exp_sea_id;//快件查询记录id（主键）
    private int memb_id;//用户id
    private int exp_state;//快递状态

    private Timestamp exp_sea_time;//查询日期

    private String exp_number;//运单号
    private String exp_name;//快递公司名称
    private String exp_sea_month;//查询月份
    private String exp_detailed;//快件物流明细
    private String exp_code;//快递公司编码

    public int getExp_sea_id() {
        return exp_sea_id;
    }

    public void setExp_sea_id(int exp_sea_id) {
        this.exp_sea_id = exp_sea_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public int getExp_state() {
        return exp_state;
    }

    public void setExp_state(int exp_state) {
        this.exp_state = exp_state;
    }

    public Timestamp getExp_sea_time() {
        return exp_sea_time;
    }

    public void setExp_sea_time(Timestamp exp_sea_time) {
        this.exp_sea_time = exp_sea_time;
    }

    public String getExp_number() {
        return exp_number;
    }

    public void setExp_number(String exp_number) {
        this.exp_number = exp_number;
    }

    public String getExp_name() {
        return exp_name;
    }

    public void setExp_name(String exp_name) {
        this.exp_name = exp_name;
    }

    public String getExp_sea_month() {
        return exp_sea_month;
    }

    public void setExp_sea_month(String exp_sea_month) {
        this.exp_sea_month = exp_sea_month;
    }

    public String getExp_detailed() {
        return exp_detailed;
    }

    public void setExp_detailed(String exp_detailed) {
        this.exp_detailed = exp_detailed;
    }

    public String getExp_code() {
        return exp_code;
    }

    public void setExp_code(String exp_code) {
        this.exp_code = exp_code;
    }
}
