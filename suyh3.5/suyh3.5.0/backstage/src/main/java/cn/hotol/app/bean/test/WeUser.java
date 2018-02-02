package cn.hotol.app.bean.test;

import java.util.Date;

/**
 * Created by LuBin
 * Date 2017-01-17.
 */
public class WeUser {

    private String we_chat_name;
    private String we_chat_photo;
    private String open_id;
    private Date add_time;
    private int total_integral;

    public String getWe_chat_name() {
        return we_chat_name;
    }

    public void setWe_chat_name(String we_chat_name) {
        this.we_chat_name = we_chat_name;
    }

    public String getWe_chat_photo() {
        return we_chat_photo;
    }

    public void setWe_chat_photo(String we_chat_photo) {
        this.we_chat_photo = we_chat_photo;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public int getTotal_integral() {
        return total_integral;
    }

    public void setTotal_integral(int total_integral) {
        this.total_integral = total_integral;
    }
}
