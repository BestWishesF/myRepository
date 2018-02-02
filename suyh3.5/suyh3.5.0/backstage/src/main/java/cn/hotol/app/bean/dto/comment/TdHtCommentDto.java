package cn.hotol.app.bean.dto.comment;

import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-09.
 */
public class TdHtCommentDto {

    private int com_id;//评价id
    private int memb_id;//用户id
    private int agent_id;//代理人id
    private int com_grade;//评分
    private String com_content;//评价内容
    private int exp_ord_id;//订单id
    private Timestamp com_time;//评价时间
    private String com_month;//评价月份

    public int getCom_id() {
        return com_id;
    }

    public void setCom_id(int com_id) {
        this.com_id = com_id;
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

    public int getCom_grade() {
        return com_grade;
    }

    public void setCom_grade(int com_grade) {
        this.com_grade = com_grade;
    }

    public String getCom_content() {
        return com_content;
    }

    public void setCom_content(String com_content) {
        this.com_content = com_content;
    }

    public int getExp_ord_id() {
        return exp_ord_id;
    }

    public void setExp_ord_id(int exp_ord_id) {
        this.exp_ord_id = exp_ord_id;
    }

    public Timestamp getCom_time() {
        return com_time;
    }

    public void setCom_time(Timestamp com_time) {
        this.com_time = com_time;
    }

    public String getCom_month() {
        return com_month;
    }

    public void setCom_month(String com_month) {
        this.com_month = com_month;
    }
}
