package cn.hotol.app.bean.dto.express;

import java.util.Date;

/**
 * Created by liyafei on 2016/12/1.
 * 发件信息
 */
public class TdHtExpressOrderDto {
    private int exp_ord_id;//发件订单id
    private int memb_id;//用户id
    private int agent_id;//代理人id
    private int add_id;//地址id
    private String add_name;//寄件人姓名
    private String add_mobile_phone;//手机号
    private String add_id_number;//身份证号
    private String add_detail_address;//详细地址
    private int add_province;//省
    private int add_city;//市
    private int add_region;//区
    private String add_telephone;//固定电话
    private double add_longitude;//经度
    private double add_latitude;//维度
    private int express_id;//快递公司id
    private Date exp_ord_time;//下单时间
    private Date exp_ord_taking_time;//接单时间
    private Date door_start_time;//上门开始时间
    private Date door_end_time;//上门结束时间
    private Date collect_time;//揽收时间
    private Date pay_time;//支付时间
    private Date storge_time;//入库时间
    private int exp_ord_state;//快件状态；1：待接单；2：待揽件；3：待支付；4：待入库；5：已完成
    private int exp_ord_size;//发件数量
    private String exp_ord_demand;//用户需求
    private double exp_ord_weight;//快件重量
    private String exp_ord_number;//订单编号
    private double exp_ord_pay_amount;//实际支付金额
    private double exp_ord_amount;//总价
    private String exp_ord_month;//下单月份 格式YYYYMM
    private double exp_ord_gratuity;//快速上门小费
    private String agent_note;//代理人备注
    private int exp_ord_type;//订单类型（1 电子面单；2 纸质面单；3 电商件；）
    private String agent_name;//真实姓名
    private String agent_phone;//手机号
    private String express_name;//快递公司名称
    private int memb_type;//用户类型 1普通用户2月结客户3大客户（int1）
    private int memb_client;//用户下单客户端：1Android2iOS3微信
    private int divide_id;//划分区域id
    private int import_marker;
    private String device_number;//设备号

    private double total_amount;//总金额
    private double adjusted_amount;//调整金额
    private double discount_amount;//折扣金额
    private double coupon_amount;//优惠券金额

    public double getTotal_amount() {
        if (total_amount == 0) {
            return exp_ord_amount;
        }
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getAdjusted_amount() {
        return adjusted_amount;
    }

    public void setAdjusted_amount(double adjusted_amount) {
        this.adjusted_amount = adjusted_amount;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(double discount_amount) {
        this.discount_amount = discount_amount;
    }

    public double getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(double coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_phone() {
        return agent_phone;
    }

    public void setAgent_phone(String agent_phone) {
        this.agent_phone = agent_phone;
    }

    public int getExp_ord_id() {
        return exp_ord_id;
    }

    public void setExp_ord_id(int exp_ord_id) {
        this.exp_ord_id = exp_ord_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
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

    public String getAdd_id_number() {
        return add_id_number;
    }

    public void setAdd_id_number(String add_id_number) {
        this.add_id_number = add_id_number;
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

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public Date getExp_ord_time() {
        return exp_ord_time;
    }

    public void setExp_ord_time(Date exp_ord_time) {
        this.exp_ord_time = exp_ord_time;
    }

    public Date getExp_ord_taking_time() {
        return exp_ord_taking_time;
    }

    public void setExp_ord_taking_time(Date exp_ord_taking_time) {
        this.exp_ord_taking_time = exp_ord_taking_time;
    }

    public Date getDoor_start_time() {
        return door_start_time;
    }

    public void setDoor_start_time(Date door_start_time) {
        this.door_start_time = door_start_time;
    }

    public Date getDoor_end_time() {
        return door_end_time;
    }

    public void setDoor_end_time(Date door_end_time) {
        this.door_end_time = door_end_time;
    }

    public Date getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Date collect_time) {
        this.collect_time = collect_time;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public Date getStorge_time() {
        return storge_time;
    }

    public void setStorge_time(Date storge_time) {
        this.storge_time = storge_time;
    }

    public int getExp_ord_state() {
        return exp_ord_state;
    }

    public void setExp_ord_state(int exp_ord_state) {
        this.exp_ord_state = exp_ord_state;
    }

    public int getExp_ord_size() {
        return exp_ord_size;
    }

    public void setExp_ord_size(int exp_ord_size) {
        this.exp_ord_size = exp_ord_size;
    }

    public String getExp_ord_demand() {
        return exp_ord_demand;
    }

    public void setExp_ord_demand(String exp_ord_demand) {
        this.exp_ord_demand = exp_ord_demand;
    }

    public double getExp_ord_weight() {
        return exp_ord_weight;
    }

    public void setExp_ord_weight(double exp_ord_weight) {
        this.exp_ord_weight = exp_ord_weight;
    }

    public String getExp_ord_number() {
        return exp_ord_number;
    }

    public void setExp_ord_number(String exp_ord_number) {
        this.exp_ord_number = exp_ord_number;
    }

    public double getExp_ord_pay_amount() {
        return exp_ord_pay_amount;
    }

    public void setExp_ord_pay_amount(double exp_ord_pay_amount) {
        this.exp_ord_pay_amount = exp_ord_pay_amount;
    }

    public double getExp_ord_amount() {
        return exp_ord_amount;
    }

    public void setExp_ord_amount(double exp_ord_amount) {
        this.exp_ord_amount = exp_ord_amount;
    }

    public String getExp_ord_month() {
        return exp_ord_month;
    }

    public void setExp_ord_month(String exp_ord_month) {
        this.exp_ord_month = exp_ord_month;
    }

    public double getExp_ord_gratuity() {
        return exp_ord_gratuity;
    }

    public void setExp_ord_gratuity(double exp_ord_gratuity) {
        this.exp_ord_gratuity = exp_ord_gratuity;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getAgent_note() {
        return agent_note;
    }

    public void setAgent_note(String agent_note) {
        this.agent_note = agent_note;
    }

    public int getExp_ord_type() {
        return exp_ord_type;
    }

    public void setExp_ord_type(int exp_ord_type) {
        this.exp_ord_type = exp_ord_type;
    }

    public int getMemb_type() {
        return memb_type;
    }

    public void setMemb_type(int memb_type) {
        this.memb_type = memb_type;
    }

    public int getMemb_client() {
        return memb_client;
    }

    public void setMemb_client(int memb_client) {
        this.memb_client = memb_client;
    }

    public int getDivide_id() {
        return divide_id;
    }

    public void setDivide_id(int divide_id) {
        this.divide_id = divide_id;
    }

    public int getImport_marker() {
        return import_marker;
    }

    public void setImport_marker(int import_marker) {
        this.import_marker = import_marker;
    }

    public String getDevice_number() {
        return device_number;
    }

    public void setDevice_number(String device_number) {
        this.device_number = device_number;
    }
}
