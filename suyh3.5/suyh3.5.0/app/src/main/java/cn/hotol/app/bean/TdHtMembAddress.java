package cn.hotol.app.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by liyafei on 2016/12/2.
 */
public class TdHtMembAddress {
    private int add_id;//地址id
    private int memb_id;//用户id
    @NotNull(message = "请输入姓名")
    private String add_name;//姓名
    @NotNull(message = "请输入详细地址")
    private String add_detail_address;//详细地址
    @NotNull(message = "请选择省")
    @Min(value=1, message="地址信息不正确")
    private int add_province;//省编码
    @NotNull(message = "请选择市")
    @Min(value=1, message="地址信息不正确")
    private int add_city;//市编码
    @NotNull(message = "请选择区")
    @Min(value=1, message="地址信息不正确")
    private int add_region;//区编码
    @NotNull(message = "请选择标签")
    private int add_label;//标签
    private String add_telephone;//固定电话
    private BigDecimal add_longitude;//经度
    private BigDecimal add_latitude;//维度
    private String add_mobile_phone;//手机号
    private String add_id_number;//身份证号
    private int add_is_default;//是否默认0是1否
    private int add_type;//类型1发件人2收件人
    private int add_state;//状态0可用1不可用
    private int add_express_size;//发件次数

    private String add_street;//街道

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
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

    public int getAdd_label() {
        return add_label;
    }

    public void setAdd_label(int add_label) {
        this.add_label = add_label;
    }

    public BigDecimal getAdd_longitude() {
        return add_longitude;
    }

    public void setAdd_longitude(BigDecimal add_longitude) {
        this.add_longitude = add_longitude;
    }

    public String getAdd_telephone() {
        return add_telephone;
    }

    public void setAdd_telephone(String add_telephone) {
        this.add_telephone = add_telephone;
    }

    public BigDecimal getAdd_latitude() {
        return add_latitude;
    }

    public void setAdd_latitude(BigDecimal add_latitude) {
        this.add_latitude = add_latitude;
    }

    public String getAdd_mobile_phone() {
        return add_mobile_phone;
    }

    public void setAdd_mobile_phone(String add_mobile_phone) {
        this.add_mobile_phone = add_mobile_phone;
    }

    public String getAdd_id_number() {
        return add_id_number;
    }

    public void setAdd_id_number(String add_id_number) {
        this.add_id_number = add_id_number;
    }

    public int getAdd_is_default() {
        return add_is_default;
    }

    public void setAdd_is_default(int add_is_default) {
        this.add_is_default = add_is_default;
    }

    public int getAdd_type() {
        return add_type;
    }

    public void setAdd_type(int add_type) {
        this.add_type = add_type;
    }

    public int getAdd_state() {
        return add_state;
    }

    public void setAdd_state(int add_state) {
        this.add_state = add_state;
    }

    public int getAdd_express_size() {
        return add_express_size;
    }

    public void setAdd_express_size(int add_express_size) {
        this.add_express_size = add_express_size;
    }

    public String getAdd_street() {
        return add_street;
    }

    public void setAdd_street(String add_street) {
        this.add_street = add_street;
    }
}
