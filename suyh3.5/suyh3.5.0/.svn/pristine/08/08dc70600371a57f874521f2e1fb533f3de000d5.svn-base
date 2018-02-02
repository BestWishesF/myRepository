package cn.hotol.app.bean.dto.gridchange;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2017-01-05.
 */
public class TdHtGridChangeDto {

    private int grid_chg_id;//主键
    private int agent_id;//代理人id
    private Timestamp grid_chg_time;//变化时间
    @NotNull(message = "代理人经度不能为空")
    private BigDecimal agent_longitude;//经度
    @NotNull(message = "代理人纬度不能为空")
    private BigDecimal agent_latitude;//纬度
    private String agent_month;//变化月份

    public int getGrid_chg_id() {
        return grid_chg_id;
    }

    public void setGrid_chg_id(int grid_chg_id) {
        this.grid_chg_id = grid_chg_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public Timestamp getGrid_chg_time() {
        return grid_chg_time;
    }

    public void setGrid_chg_time(Timestamp grid_chg_time) {
        this.grid_chg_time = grid_chg_time;
    }

    public BigDecimal getAgent_longitude() {
        return agent_longitude;
    }

    public void setAgent_longitude(BigDecimal agent_longitude) {
        this.agent_longitude = agent_longitude;
    }

    public BigDecimal getAgent_latitude() {
        return agent_latitude;
    }

    public void setAgent_latitude(BigDecimal agent_latitude) {
        this.agent_latitude = agent_latitude;
    }

    public String getAgent_month() {
        return agent_month;
    }

    public void setAgent_month(String agent_month) {
        this.agent_month = agent_month;
    }
}
