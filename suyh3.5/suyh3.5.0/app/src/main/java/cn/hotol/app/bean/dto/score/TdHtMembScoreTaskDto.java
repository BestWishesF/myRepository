package cn.hotol.app.bean.dto.score;

import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
public class TdHtMembScoreTaskDto {

    private int memb_task_id;//用户积分任务完成记录表id
    private int st_id;//积分任务id
    private int memb_id;//用户id
    private int st_type;//积分任务类别1新手任务2常规任务
    private String st_name;//任务名称
    private String st_synopsis;//任务介绍
    private int st_score;//任务奖励
    private int st_state;//是否可用 0是1否
    private String st_img;//图片URL
    private Timestamp task_time;//任务完成时间
    private int score_id;//积分记录id

    public int getMemb_task_id() {
        return memb_task_id;
    }

    public void setMemb_task_id(int memb_task_id) {
        this.memb_task_id = memb_task_id;
    }

    public int getSt_id() {
        return st_id;
    }

    public void setSt_id(int st_id) {
        this.st_id = st_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public int getSt_type() {
        return st_type;
    }

    public void setSt_type(int st_type) {
        this.st_type = st_type;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_synopsis() {
        return st_synopsis;
    }

    public void setSt_synopsis(String st_synopsis) {
        this.st_synopsis = st_synopsis;
    }

    public int getSt_score() {
        return st_score;
    }

    public void setSt_score(int st_score) {
        this.st_score = st_score;
    }

    public int getSt_state() {
        return st_state;
    }

    public void setSt_state(int st_state) {
        this.st_state = st_state;
    }

    public String getSt_img() {
        return st_img;
    }

    public void setSt_img(String st_img) {
        this.st_img = st_img;
    }

    public Timestamp getTask_time() {
        return task_time;
    }

    public void setTask_time(Timestamp task_time) {
        this.task_time = task_time;
    }

    public int getScore_id() {
        return score_id;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }
}
