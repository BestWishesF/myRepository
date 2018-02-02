package cn.hotol.app.bean;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
public class TsHtScoreTask {

    private int st_id;//主标识
    private int st_type;//积分任务类别 1、新手任务；2、日常任务
    private String st_name;//积分任务名称
    private String st_synopsis;//积分任务介绍
    private int st_score;//奖励积分
    private int st_state;//积分任务状态 0：有效；1、无效；
    private String st_img;//积分任务图片

    public int getSt_id() {
        return st_id;
    }

    public void setSt_id(int st_id) {
        this.st_id = st_id;
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
}
