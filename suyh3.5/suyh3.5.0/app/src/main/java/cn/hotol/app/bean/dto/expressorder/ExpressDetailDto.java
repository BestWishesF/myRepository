package cn.hotol.app.bean.dto.expressorder;

import cn.hotol.app.bean.dto.logistics.LogisticsInfoDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-16.
 */
public class ExpressDetailDto {

    private String add_name;//发件人姓名
    private String add_telephone;//发件人电话
    private String add_mobile_phone;//发件人手机
    private int add_province;//发件人省份
    private int add_city;//发件人城市
    private int add_region;//发件人区域
    private String add_detail_address;//发件人地址
    private String collect_name;//收件人姓名
    private String collect_telephone;//收件人电话
    private String collect_mobile;//收件人手机
    private int collect_province;//收件人省份
    private int collect_city;//收件人城市
    private int collect_region;//收件人区域
    private String collect_detail_address;//收件人地址
    private int express_id;//快递id
    private String express_name;//快递名称
    private String express_number;//快递运单号
    private BigDecimal express_price;//快递定价
    private String express_logo;//快递logo
    private List<LogisticsInfoDto> express_logistics;//快递物流

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
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

    public String getCollect_name() {
        return collect_name;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getCollect_telephone() {
        return collect_telephone;
    }

    public void setCollect_telephone(String collect_telephone) {
        this.collect_telephone = collect_telephone;
    }

    public String getCollect_mobile() {
        return collect_mobile;
    }

    public void setCollect_mobile(String collect_mobile) {
        this.collect_mobile = collect_mobile;
    }

    public int getCollect_province() {
        return collect_province;
    }

    public void setCollect_province(int collect_province) {
        this.collect_province = collect_province;
    }

    public int getCollect_city() {
        return collect_city;
    }

    public void setCollect_city(int collect_city) {
        this.collect_city = collect_city;
    }

    public int getCollect_region() {
        return collect_region;
    }

    public void setCollect_region(int collect_region) {
        this.collect_region = collect_region;
    }

    public String getCollect_detail_address() {
        return collect_detail_address;
    }

    public void setCollect_detail_address(String collect_detail_address) {
        this.collect_detail_address = collect_detail_address;
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

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public BigDecimal getExpress_price() {
        return express_price;
    }

    public void setExpress_price(BigDecimal express_price) {
        this.express_price = express_price;
    }

    public String getExpress_logo() {
        return express_logo;
    }

    public void setExpress_logo(String express_logo) {
        this.express_logo = express_logo;
    }

    public List<LogisticsInfoDto> getExpress_logistics() {
        return express_logistics;
    }

    public void setExpress_logistics(List<LogisticsInfoDto> express_logistics) {
        this.express_logistics = express_logistics;
    }
}
