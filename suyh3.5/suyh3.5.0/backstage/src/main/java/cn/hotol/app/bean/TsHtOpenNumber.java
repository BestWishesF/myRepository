package cn.hotol.app.bean;

/**
 * 厚通单号信息表
 */
public class TsHtOpenNumber {

    private int ht_open_id;//主标示--单纯主键使用，自增长字段
    private int agent_id;//代理人id
    private String ht_number;//厚通单号
    private int ht_open_state;//状态 0可使用1已使用

    public int getHt_open_id() {
        return ht_open_id;
    }

    public void setHt_open_id(int ht_open_id) {
        this.ht_open_id = ht_open_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getHt_number() {
        return ht_number;
    }

    public void setHt_number(String ht_number) {
        this.ht_number = ht_number;
    }

    public int getHt_open_state() {
        return ht_open_state;
    }

    public void setHt_open_state(int ht_open_state) {
        this.ht_open_state = ht_open_state;
    }
}
