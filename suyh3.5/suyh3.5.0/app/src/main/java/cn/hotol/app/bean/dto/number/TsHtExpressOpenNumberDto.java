package cn.hotol.app.bean.dto.number;

/**
 * Created by LuBin
 * Date 2017-01-13.
 */
public class TsHtExpressOpenNumberDto {

    private int exp_open_id;//主键id 自增长
    private int express_id;//快递公司id
    private int agent_id;//代理人id
    private String express_number;//快递单号
    private int exp_open_state;//状态0可使用1已使用
    private int region_id;//区域id

    public int getExp_open_id() {
        return exp_open_id;
    }

    public void setExp_open_id(int exp_open_id) {
        this.exp_open_id = exp_open_id;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public int getExp_open_state() {
        return exp_open_state;
    }

    public void setExp_open_state(int exp_open_state) {
        this.exp_open_state = exp_open_state;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
