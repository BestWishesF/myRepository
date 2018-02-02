package cn.hotol.app.bean.dto.found;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-27.
 */
public class AgentFinancialDetailsDto {

    private String afchg_name;//变化名称
    private Timestamp afchg_time;//变化时间
    private BigDecimal afchg_change_amount;//变化金钱
    private int afchg_type;//1加2减
    private int afchg_state;//资金变动状态 0成功1失败2审核中

    public String getAfchg_name() {
        return afchg_name;
    }

    public void setAfchg_name(String afchg_name) {
        this.afchg_name = afchg_name;
    }

    public Timestamp getAfchg_time() {
        return afchg_time;
    }

    public void setAfchg_time(Timestamp afchg_time) {
        this.afchg_time = afchg_time;
    }

    public BigDecimal getAfchg_change_amount() {
        return afchg_change_amount;
    }

    public void setAfchg_change_amount(BigDecimal afchg_change_amount) {
        this.afchg_change_amount = afchg_change_amount;
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
}
