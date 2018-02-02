package cn.hotol.app.bean.dto.agent;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by LuBin
 * Date 2016-12-26.
 */
public class AgentEmailDto {

    @NotNull(message = "请输入正确的邮箱格式.")
    @Email(message = "请输入正确的邮箱格式.")
    private String agent_email;//代理人邮箱

    public String getAgent_email() {
        return agent_email;
    }

    public void setAgent_email(String agent_email) {
        this.agent_email = agent_email;
    }
}
