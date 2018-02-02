package cn.hotol.app.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-27.
 */
public class TdHtAgentFoundChange {

    private int afchg_id;//变化id
    private int agent_id;//代理人id
    private BigDecimal afchg_front_amount;//变化前金额
    private BigDecimal afchg_back_amount;//变化后余额
    private BigDecimal afchg_change_amount;//变化金钱
    private Timestamp afchg_time;//变化时间
    private String afchg_month;//变化月份
    private int afchg_type;//1加2减
    private int afchg_state;//资金变动状态 0成功1失败
    private String afchg_number;//变化单号
    private String afchg_name;//变化名称
    private int exp_ord_id;//订单id

    public int getAfchg_id() {
        return afchg_id;
    }

    public void setAfchg_id(int afchg_id) {
        this.afchg_id = afchg_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public BigDecimal getAfchg_front_amount() {
        return afchg_front_amount;
    }

    public void setAfchg_front_amount(BigDecimal afchg_front_amount) {
        this.afchg_front_amount = afchg_front_amount;
    }

    public BigDecimal getAfchg_back_amount() {
        return afchg_back_amount;
    }

    public void setAfchg_back_amount(BigDecimal afchg_back_amount) {
        this.afchg_back_amount = afchg_back_amount;
    }

    public BigDecimal getAfchg_change_amount() {
        return afchg_change_amount;
    }

    public void setAfchg_change_amount(BigDecimal afchg_change_amount) {
        this.afchg_change_amount = afchg_change_amount;
    }

    public Timestamp getAfchg_time() {
        return afchg_time;
    }

    public void setAfchg_time(Timestamp afchg_time) {
        this.afchg_time = afchg_time;
    }

    public String getAfchg_month() {
        return afchg_month;
    }

    public void setAfchg_month(String afchg_month) {
        this.afchg_month = afchg_month;
    }

    public int getAfchg_type() {
        return afchg_type;
    }

    public void setAfchg_type(int afchg_type) {
        this.afchg_type = afchg_type;
    }

    public int getAfchg_state() {
        return afchg_state;
    }

    public void setAfchg_state(int afchg_state) {
        this.afchg_state = afchg_state;
    }

    public String getAfchg_number() {
        return afchg_number;
    }

    public void setAfchg_number(String afchg_number) {
        this.afchg_number = afchg_number;
    }

    public String getAfchg_name() {
        return afchg_name;
    }

    public void setAfchg_name(String afchg_name) {
        this.afchg_name = afchg_name;
    }

    public int getExp_ord_id() {
        return exp_ord_id;
    }

    public void setExp_ord_id(int exp_ord_id) {
        this.exp_ord_id = exp_ord_id;
    }
}
