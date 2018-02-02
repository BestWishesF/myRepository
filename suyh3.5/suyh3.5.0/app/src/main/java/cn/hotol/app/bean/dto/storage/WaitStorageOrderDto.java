package cn.hotol.app.bean.dto.storage;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-28.
 */
public class WaitStorageOrderDto {

    private int exp_ord_id;//订单id
    private int express_id;//快递公司id
    private int add_region;//发件区id
    private String add_detail_address;//详细地址
    private String add_name;//发件人姓名
    private int exp_ord_size;//快件数量
    private Timestamp collect_time;//揽件时间
    private int exp_ord_state;//状态 0已取消1待接单2待揽收3待支付4待入库5已完成
    private String express_logo;//快递Logo
    private BigDecimal exp_ord_amount;//总金额
    private BigDecimal exp_ord_gratuity;//小费
    private int exp_ord_type;//订单类型（1 电子面单；2 纸质面单；）
    private Timestamp door_end_time;//上门结束时间

    public int getExp_ord_id() {
        return exp_ord_id;
    }

    public void setExp_ord_id(int exp_ord_id) {
        this.exp_ord_id = exp_ord_id;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
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

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public int getExp_ord_size() {
        return exp_ord_size;
    }

    public void setExp_ord_size(int exp_ord_size) {
        this.exp_ord_size = exp_ord_size;
    }

    public Timestamp getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Timestamp collect_time) {
        this.collect_time = collect_time;
    }

    public int getExp_ord_state() {
        return exp_ord_state;
    }

    public void setExp_ord_state(int exp_ord_state) {
        this.exp_ord_state = exp_ord_state;
    }

    public String getExpress_logo() {
        return express_logo;
    }

    public void setExpress_logo(String express_logo) {
        this.express_logo = express_logo;
    }

    public BigDecimal getExp_ord_amount() {
        return exp_ord_amount;
    }

    public void setExp_ord_amount(BigDecimal exp_ord_amount) {
        this.exp_ord_amount = exp_ord_amount;
    }

    public BigDecimal getExp_ord_gratuity() {
        return exp_ord_gratuity;
    }

    public void setExp_ord_gratuity(BigDecimal exp_ord_gratuity) {
        this.exp_ord_gratuity = exp_ord_gratuity;
    }

    public int getExp_ord_type() {
        return exp_ord_type;
    }

    public void setExp_ord_type(int exp_ord_type) {
        this.exp_ord_type = exp_ord_type;
    }

    public Timestamp getDoor_end_time() {
        return door_end_time;
    }

    public void setDoor_end_time(Timestamp door_end_time) {
        this.door_end_time = door_end_time;
    }
}
