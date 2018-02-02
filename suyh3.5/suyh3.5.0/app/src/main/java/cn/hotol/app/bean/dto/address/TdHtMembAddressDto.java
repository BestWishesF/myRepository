package cn.hotol.app.bean.dto.address;

/**
 * Created by liyafei on 2016/12/5.
 */
public class TdHtMembAddressDto {
    private int add_id;//id
    private String add_name;//姓名
    private String add_detail_address;//详细地址
    private int add_province;//省编码
    private int add_city;//市编码
    private int add_region;//区编码
    private int add_label;//标签
    private String add_telephone;//固定电话
    private double add_longitude;//经度
    private double add_latitude;//维度
    private String add_mobile_phone;//手机号
    private int add_is_default;//是否默认0是1否

    private String province;//省
    private String city;//市
    private String region;//区

    private String add_street;//街道

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getAdd_mobile_phone() {
        return add_mobile_phone;
    }

    public void setAdd_mobile_phone(String add_mobile_phone) {
        this.add_mobile_phone = add_mobile_phone;
    }

    public int getAdd_is_default() {
        return add_is_default;
    }

    public void setAdd_is_default(int add_is_default) {
        this.add_is_default = add_is_default;
    }

    public String getAdd_street() {
        return add_street;
    }

    public void setAdd_street(String add_street) {
        this.add_street = add_street;
    }
}
