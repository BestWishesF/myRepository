package cn.hotol.app.bean.dto.expressorder;

import cn.hotol.app.bean.dto.logistics.LogisticCodeDto;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by LuBin
 * Date 2016-12-09.
 */
public class DeliveryExpDto {

    private int exp_ord_clt_id;//主键id
    private String add_name;//收件人姓名
    private int add_province;//省
    private int add_city;//市
    private int add_region;//区
    private String add_detail_address;//收件人详细地址
    private int exp_ord_clt_state;//状态
    private String express_number;//运单号
    private Timestamp send_time;
    private int express_id;//快递公司id
    private String express_name;//快递公司名称
    private String express_logo;//快递公司logo
    private String logistic;//物流信息
    private String add_telephone;//电话
    private String add_mobile_phone;//手机号

    public int getExp_ord_clt_id() {
        return exp_ord_clt_id;
    }

    public void setExp_ord_clt_id(int exp_ord_clt_id) {
        this.exp_ord_clt_id = exp_ord_clt_id;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public int getAdd_province() {
        return add_province;
    }

    public void setAdd_province(int add_province) {
        this.add_province = add_province;
    }

    public int getAdd_city() {
        return add_city;
    }

    public void setAdd_city(int add_city) {
        this.add_city = add_city;
    }

    public int getAdd_region() {
        return add_region;
    }

    public void setAdd_region(int add_region) {
        this.add_region = add_region;
    }

    public String getAdd_detail_address() {
        return add_detail_address;
    }

    public void setAdd_detail_address(String add_detail_address) {
        this.add_detail_address = add_detail_address;
    }

    public int getExp_ord_clt_state() {
        return exp_ord_clt_state;
    }

    public void setExp_ord_clt_state(int exp_ord_clt_state) {
        this.exp_ord_clt_state = exp_ord_clt_state;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public Timestamp getSend_time() {
        return send_time;
    }

    public void setSend_time(Timestamp send_time) {
        this.send_time = send_time;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_logo() {
        return express_logo;
    }

    public void setExpress_logo(String express_logo) {
        this.express_logo = express_logo;
    }

    public String getLogistic() {
        return logistic;
    }

    public void setLogistic(String logistic) {
        this.logistic = logistic;
    }

    public String getAdd_telephone() {
        return add_telephone;
    }

    public void setAdd_telephone(String add_telephone) {
        this.add_telephone = add_telephone;
    }

    public String getAdd_mobile_phone() {
        return add_mobile_phone;
    }

    public void setAdd_mobile_phone(String add_mobile_phone) {
        this.add_mobile_phone = add_mobile_phone;
    }
}
