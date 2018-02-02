package cn.hotol.app.bean.dto.found;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-30.
 */
public class MemberFinancialDetailsDto {

    private String mfchg_name;//变化名称
    private Timestamp mfchg_time;//变化时间
    private BigDecimal mfchg_change_amount;//变化金钱
    private int mfchg_type;//1加2减
    private BigDecimal mfchg_back_amount;//变化后余额

    public String getMfchg_name() {
        return mfchg_name;
    }

    public void setMfchg_name(String mfchg_name) {
        this.mfchg_name = mfchg_name;
    }

    public Timestamp getMfchg_time() {
        return mfchg_time;
    }

    public void setMfchg_time(Timestamp mfchg_time) {
        this.mfchg_time = mfchg_time;
    }

    public BigDecimal getMfchg_change_amount() {
        return mfchg_change_amount;
    }

    public void setMfchg_change_amount(BigDecimal mfchg_change_amount) {
        this.mfchg_change_amount = mfchg_change_amount;
    }

    public int getMfchg_type() {
        return mfchg_type;
    }

    public void setMfchg_type(int mfchg_type) {
        this.mfchg_type = mfchg_type;
    }

    public BigDecimal getMfchg_back_amount() {
        return mfchg_back_amount;
    }

    public void setMfchg_back_amount(BigDecimal mfchg_back_amount) {
        this.mfchg_back_amount = mfchg_back_amount;
    }
}
