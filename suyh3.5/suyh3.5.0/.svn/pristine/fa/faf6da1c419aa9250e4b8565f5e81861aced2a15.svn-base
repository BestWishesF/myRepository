package cn.hotol.app.bean.dto.goods;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2017-02-25.
 */
public class ScoreGoodsDto {

    private int goods_id;//主标识
    private String goods_img;//商品图片
    @NotNull(message = "名称为1-16个字符")
    @Length(min = 1,max = 16, message = "名称为1-16个字符")
    private String goods_name;//商品名称
    @Min(value = 0, message = "兑换所需积分最小为0")
    private Integer goods_score;//兑换所需积分
    private int goods_state;//积分商品状态 0、有效；1、无效；
    @Min(value = 0, message = "优惠券金额最小为0")
    private BigDecimal coupon_money;//优惠券金额
    @Min(value = 1, message = "有效天数最小为1")
    private Integer effective_day;//有效天数
    private int goods_type;//1每天限制一次2无限制
    private String goods_img_detail;//商品图片
    @NotNull(message = "副标题为1-32个字符")
    @Length(min = 1,max = 32, message = "副标题为1-32个字符")
    private String goods_subhead;//副标题
    @NotNull(message = "介绍为1-128个字符")
    @Length(min = 1,max = 128, message = "介绍为1-128个字符")
    private String goods_introduce;//介绍
    @Min(value = 0, message = "价格最小为0")
    private BigDecimal goods_amount;//价格
    private int region_id;//发件区域 0不限制
    private int express_source;//发件来源 0不限制 1公众号 2APP
    private int express_id;//快递公司 0不限制
    @Min(value = 0, message = "限定金额最小为0")
    private BigDecimal cou_limit_amount;//限定金额：优惠券限定使用金额 0不限制
    @Max(value = 1, message = "折扣最大为1")
    @Min(value = 0, message = "折扣最小为0")
    private BigDecimal cou_discount;//折扣 1不打折
    private String express_company;
    private String address;

    private int current_page;//请求的页码
    private int page_size;//每页显示数量

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Integer getGoods_score() {
        return goods_score;
    }

    public void setGoods_score(Integer goods_score) {
        this.goods_score = goods_score;
    }

    public int getGoods_state() {
        return goods_state;
    }

    public void setGoods_state(int goods_state) {
        this.goods_state = goods_state;
    }

    public BigDecimal getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(BigDecimal coupon_money) {
        this.coupon_money = coupon_money;
    }

    public Integer getEffective_day() {
        return effective_day;
    }

    public void setEffective_day(Integer effective_day) {
        this.effective_day = effective_day;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_img_detail() {
        return goods_img_detail;
    }

    public void setGoods_img_detail(String goods_img_detail) {
        this.goods_img_detail = goods_img_detail;
    }

    public String getGoods_subhead() {
        return goods_subhead;
    }

    public void setGoods_subhead(String goods_subhead) {
        this.goods_subhead = goods_subhead;
    }

    public String getGoods_introduce() {
        return goods_introduce;
    }

    public void setGoods_introduce(String goods_introduce) {
        this.goods_introduce = goods_introduce;
    }

    public BigDecimal getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(BigDecimal goods_amount) {
        this.goods_amount = goods_amount;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getExpress_source() {
        return express_source;
    }

    public void setExpress_source(int express_source) {
        this.express_source = express_source;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getCou_limit_amount() {
        return cou_limit_amount;
    }

    public void setCou_limit_amount(BigDecimal cou_limit_amount) {
        this.cou_limit_amount = cou_limit_amount;
    }

    public BigDecimal getCou_discount() {
        return cou_discount;
    }

    public void setCou_discount(BigDecimal cou_discount) {
        this.cou_discount = cou_discount;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}
