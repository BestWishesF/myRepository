package cn.hotol.app.bean.dto.green;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017-03-07.
 */
public class TdHtGreenMailDto {

    private int green_mail_id;//
    private int memb_id;//
    private String name;//姓名
    private String address;//详细地址
    private String phone;//手机号
    private BigDecimal event_reward;//优惠券金额
    private Timestamp done_time;//完成时间
    private int state;//0待保存地址信息1待赠送绿植2待赠送优惠券
    private Timestamp give_green_time;//赠送绿植完成时间
    private Timestamp give_reward_time;//赠送优惠券完成时间

    public int getGreen_mail_id() {
        return green_mail_id;
    }

    public void setGreen_mail_id(int green_mail_id) {
        this.green_mail_id = green_mail_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getEvent_reward() {
        return event_reward;
    }

    public void setEvent_reward(BigDecimal event_reward) {
        this.event_reward = event_reward;
    }

    public Timestamp getDone_time() {
        return done_time;
    }

    public void setDone_time(Timestamp done_time) {
        this.done_time = done_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Timestamp getGive_green_time() {
        return give_green_time;
    }

    public void setGive_green_time(Timestamp give_green_time) {
        this.give_green_time = give_green_time;
    }

    public Timestamp getGive_reward_time() {
        return give_reward_time;
    }

    public void setGive_reward_time(Timestamp give_reward_time) {
        this.give_reward_time = give_reward_time;
    }
}
