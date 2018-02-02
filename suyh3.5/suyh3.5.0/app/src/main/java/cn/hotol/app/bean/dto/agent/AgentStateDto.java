package cn.hotol.app.bean.dto.agent;

/**
 * Created by LuBin
 * Date 2017-01-10.
 */
public class AgentStateDto {

    private int agent_state;//1未申请2申请中3申请失败4上班中5下班6锁定

    public int getAgent_state() {
        return agent_state;
    }

    public void setAgent_state(int agent_state) {
        this.agent_state = agent_state;
    }
}
