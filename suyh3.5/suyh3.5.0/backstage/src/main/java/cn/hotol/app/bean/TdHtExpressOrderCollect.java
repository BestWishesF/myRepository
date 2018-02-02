package cn.hotol.app.bean;

import com.sun.org.apache.xpath.internal.operations.String;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收件信息
 * Created by liyafei on 2016/12/3.
 */
public class TdHtExpressOrderCollect {
    private int exp_ord_clt_id;//id
    private int exp_ord_id;//发件订单id
    private String exp_ord_category;//快件类别名称
    private double exp_ord_clt_height;//重量
    private int add_id;//地址id
    private String add_name;//姓名
    private String add_mobile_phone;//手机号
    private String add_detail_address;//详细地址
    private int add_province;//省
    private int add_city;//市
    private int add_region;//区
    private String add_telephone;//固定电话
    private double add_longitude;//经度
    private double add_latitude;//纬度
    private int exp_ord_clt_state;//状态1已揽收2运输中3派送中4已签收
    private String express_number;//快递公司单号
    private String ht_number;//厚通单号
    private Date sign_time;//签收时间
    private Date send_time;//发件时间
    private Date delivery_time;//派送时间
    private int express_id;//快递公司id
    private int category_id;//类别id
    private String express_name;//快递公司名称
    private BigDecimal express_price;//快递定价
    private String exp_ord_clt_number;//快递编号（主要用于快递公司识别）

    public int getExp_ord_clt_id() {
        return exp_ord_clt_id;
    }

    public void setExp_ord_clt_id(int exp_ord_clt_id) {
        this.exp_ord_clt_id = exp_ord_clt_id;
    }

    public int getExp_ord_id() {
        return exp_ord_id;
    }

    public void setExp_ord_id(int exp_ord_id) {
        this.exp_ord_id = exp_ord_id;
    }

    public String getExp_ord_category() {
        return exp_ord_category;
    }

    public void setExp_ord_category(String exp_ord_category) {
        this.exp_ord_category = exp_ord_category;
    }

    public double getExp_ord_clt_height() {
        return exp_ord_clt_height;
    }

    public void setExp_ord_clt_height(double exp_ord_clt_height) {
        this.exp_ord_clt_height = exp_ord_clt_height;
    }

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public String getAdd_mobile_phone() {
        return add_mobile_phone;
    }

    public void setAdd_mobile_phone(String add_mobile_phone) {
        this.add_mobile_phone = add_mobile_phone;
    }

    public String getAdd_detail_address() {
        return add_detail_address;
    }

    public void setAdd_detail_address(String add_detail_address) {
        this.add_detail_address = add_detail_address;
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

    public String getAdd_telephone() {
        return add_telephone;
    }

    public void setAdd_telephone(String add_telephone) {
        this.add_telephone = add_telephone;
    }

    public double getAdd_longitude() {
        return add_longitude;
    }

    public void setAdd_longitude(double add_longitude) {
        this.add_longitude = add_longitude;
    }

    public double getAdd_latitude() {
        return add_latitude;
    }

    public void setAdd_latitude(double add_latitude) {
        this.add_latitude = add_latitude;
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

    public String getHt_number() {
        return ht_number;
    }

    public void setHt_number(String ht_number) {
        this.ht_number = ht_number;
    }

    public Date getSign_time() {
        return sign_time;
    }

    public void setSign_time(Date sign_time) {
        this.sign_time = sign_time;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Date getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Date delivery_time) {
        this.delivery_time = delivery_time;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public BigDecimal getExpress_price() {
        return express_price;
    }

    public void setExpress_price(BigDecimal express_price) {
        this.express_price = express_price;
    }

    public String getExp_ord_clt_number() {
        return exp_ord_clt_number;
    }

    public void setExp_ord_clt_number(String exp_ord_clt_number) {
        this.exp_ord_clt_number = exp_ord_clt_number;
    }

}
