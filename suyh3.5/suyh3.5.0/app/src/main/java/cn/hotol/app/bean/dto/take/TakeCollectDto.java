package cn.hotol.app.bean.dto.take;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-29.
 */
public class TakeCollectDto {

    private int exp_ord_clt_id;//快件id
    private int express_id;//快递公司id
    private Timestamp send_time;//发件时间
    private String add_name;//收件人姓名
    private String add_mobile_phone;//收件人手机
    private String add_telephone;//收件人电话
    private int add_province;//收件人省id
    private int add_city;//收件人市id
    private int add_region;//收件人区id
    private String add_detail_address;//收件人详细地址
    private String express_logo;//快递公司logo
    private BigDecimal exp_ord_clt_height;//快件重量
    private BigDecimal express_price;//快递定价
    private String ht_number;

    public int getExp_ord_clt_id() {
        return exp_ord_clt_id;
    }

    public void setExp_ord_clt_id(int exp_ord_clt_id) {
        this.exp_ord_clt_id = exp_ord_clt_id;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public Timestamp getSend_time() {
        return send_time;
    }

    public void setSend_time(Timestamp send_time) {
        this.send_time = send_time;
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

    public String getAdd_telephone() {
        return add_telephone;
    }

    public void setAdd_telephone(String add_telephone) {
        this.add_telephone = add_telephone;
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

    public String getExpress_logo() {
        return express_logo;
    }

    public void setExpress_logo(String express_logo) {
        this.express_logo = express_logo;
    }

    public BigDecimal getExp_ord_clt_height() {
        return exp_ord_clt_height;
    }

    public void setExp_ord_clt_height(BigDecimal exp_ord_clt_height) {
        this.exp_ord_clt_height = exp_ord_clt_height;
    }

    public BigDecimal getExpress_price() {
        return express_price;
    }

    public void setExpress_price(BigDecimal express_price) {
        this.express_price = express_price;
    }

    public String getHt_number() {
        return ht_number;
    }

    public void setHt_number(String ht_number) {
        this.ht_number = ht_number;
    }
}
