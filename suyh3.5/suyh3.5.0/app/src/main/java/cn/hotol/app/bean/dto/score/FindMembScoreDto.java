package cn.hotol.app.bean.dto.score;

import java.util.Date;

/**
 * 查询积分明细
 * Created by liyafei on 2016/12/13.
 */
public class FindMembScoreDto {

    private int score_change_point;//变化积分值
    private Date score_time;//积分时间
    private String score_reason;//积分增减原因
    private int score_type;//积分增减类型，1加2减

    public int getScore_change_point() {
        return score_change_point;
    }

    public void setScore_change_point(int score_change_point) {
        this.score_change_point = score_change_point;
    }

    public Date getScore_time() {
        return score_time;
    }

    public void setScore_time(Date score_time) {
        this.score_time = score_time;
    }

    public String getScore_reason() {
        return score_reason;
    }

    public void setScore_reason(String score_reason) {
        this.score_reason = score_reason;
    }

    public int getScore_type() {
        return score_type;
    }

    public void setScore_type(int score_type) {
        this.score_type = score_type;
    }
}
