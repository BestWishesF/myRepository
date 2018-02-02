package cn.hotol.app.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 用户兑换记录表
 */
public class TdHtMembScoreGoods {
    private int memb_goods_id;//主键id
    private int goods_id;//商品id
    private String goods_img;//商品图片
    private String goods_name;//商品名称
    private int goods_score;//所需积分
    private int goods_state;//状态
    private BigDecimal coupon_money;//优惠券金额
    private int memb_id;//用户id
    private Timestamp exchange_time;//有效期
    private int score_id;//积分变化id
    private int effective_day;//有效天数
    private int goods_type;//1每天限制一次2无限制
    private String goods_img_detail;//商品图片
    private String goods_subhead;//副标题
    private String goods_introduce;//介绍
    private BigDecimal goods_amount;//价格
    private int region_id;//发件区域 0不限制
    private int express_source;//发件来源 0不限制 1公众号 2APP
    private int express_id;//快递公司 0不限制
    private BigDecimal cou_limit_amount;//限定金额：优惠券限定使用金额 0不限制
    private BigDecimal cou_discount;//折扣 1不打折

    public int getMemb_goods_id() {
        return memb_goods_id;
    }

    public void setMemb_goods_id(int memb_goods_id) {
        this.memb_goods_id = memb_goods_id;
    }

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

    public int getGoods_score() {
        return goods_score;
    }

    public void setGoods_score(int goods_score) {
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

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public Timestamp getExchange_time() {
        return exchange_time;
    }

    public void setExchange_time(Timestamp exchange_time) {
        this.exchange_time = exchange_time;
    }

    public int getScore_id() {
        return score_id;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }

    public int getEffective_day() {
        return effective_day;
    }

    public void setEffective_day(int effective_day) {
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
}
