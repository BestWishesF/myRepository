package cn.hotol.app.bean.test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by LuBin
 * Date 2017-01-18.
 */
public class ExpressRequest {

    private Long id;
    private String address;
    private String street;
    private int agency_id;
    private int service_provider_id;
    private int status;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Date submit_time;
    private String sender_name;
    private String sender_mobile_no;
    private Date accep_time;
    private String goods_memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public int getService_provider_id() {
        return service_provider_id;
    }

    public void setService_provider_id(int service_provider_id) {
        this.service_provider_id = service_provider_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_mobile_no() {
        return sender_mobile_no;
    }

    public void setSender_mobile_no(String sender_mobile_no) {
        this.sender_mobile_no = sender_mobile_no;
    }

    public Date getAccep_time() {
        return accep_time;
    }

    public void setAccep_time(Date accep_time) {
        this.accep_time = accep_time;
    }

    public String getGoods_memo() {
        return goods_memo;
    }

    public void setGoods_memo(String goods_memo) {
        this.goods_memo = goods_memo;
    }
}
