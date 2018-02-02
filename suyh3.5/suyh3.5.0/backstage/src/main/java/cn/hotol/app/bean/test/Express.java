package cn.hotol.app.bean.test;

import java.math.BigDecimal;

/**
 * Created by LuBin
 * Date 2017-01-18.
 */
public class Express {

    private String goods_name;
    private BigDecimal service_fee;
    private String weight;
    private String provider_tracking_id;
    private String recipient_name;
    private String recipient_mobile_no;
    private String recipient_street;
    private String recipient_address;
    private String hotol_id;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public BigDecimal getService_fee() {
        return service_fee;
    }

    public void setService_fee(BigDecimal service_fee) {
        this.service_fee = service_fee;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProvider_tracking_id() {
        return provider_tracking_id;
    }

    public void setProvider_tracking_id(String provider_tracking_id) {
        this.provider_tracking_id = provider_tracking_id;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_mobile_no() {
        return recipient_mobile_no;
    }

    public void setRecipient_mobile_no(String recipient_mobile_no) {
        this.recipient_mobile_no = recipient_mobile_no;
    }

    public String getRecipient_street() {
        return recipient_street;
    }

    public void setRecipient_street(String recipient_street) {
        this.recipient_street = recipient_street;
    }

    public String getRecipient_address() {
        return recipient_address;
    }

    public void setRecipient_address(String recipient_address) {
        this.recipient_address = recipient_address;
    }

    public String getHotol_id() {
        return hotol_id;
    }

    public void setHotol_id(String hotol_id) {
        this.hotol_id = hotol_id;
    }
}
