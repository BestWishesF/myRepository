package cn.hotol.app.bean.test;

import java.util.Date;

/**
 * Created by LuBin
 * Date 2017-01-18.
 */
public class Agency {

    private int id;
    private String name;
    private String gender;
    private String mobile_no;
    private String emergency_contact_mobile_no;
    private String street;
    private String address;
    private Date reg_time;
    private String password;
    private Date birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmergency_contact_mobile_no() {
        return emergency_contact_mobile_no;
    }

    public void setEmergency_contact_mobile_no(String emergency_contact_mobile_no) {
        this.emergency_contact_mobile_no = emergency_contact_mobile_no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getReg_time() {
        return reg_time;
    }

    public void setReg_time(Date reg_time) {
        this.reg_time = reg_time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
