package cn.hotol.app.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lizhun on 2017/3/10.
 * 代理人和用户关联信息表
 */
public class TdHtAgentMember {
    private int agent_memb_id;//id
    private int memb_id;//用户id
    private int agent_id;//代理人id
    private BigDecimal register_reward;//注册奖励
    private BigDecimal first_single_reward;//首单奖励
    private Timestamp regiter_time;//注册时间
    private Timestamp first_single_time;//首单完成时间

    public int getAgent_memb_id() {
        return agent_memb_id;
    }

    public void setAgent_memb_id(int agent_memb_id) {
        this.agent_memb_id = agent_memb_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public BigDecimal getRegister_reward() {
        return register_reward;
    }

    public void setRegister_reward(BigDecimal register_reward) {
        this.register_reward = register_reward;
    }

    public BigDecimal getFirst_single_reward() {
        return first_single_reward;
    }

    public void setFirst_single_reward(BigDecimal first_single_reward) {
        this.first_single_reward = first_single_reward;
    }

    public Timestamp getRegiter_time() {
        return regiter_time;
    }

    public void setRegiter_time(Timestamp regiter_time) {
        this.regiter_time = regiter_time;
    }

    public Timestamp getFirst_single_time() {
        return first_single_time;
    }

    public void setFirst_single_time(Timestamp first_single_time) {
        this.first_single_time = first_single_time;
    }
}
