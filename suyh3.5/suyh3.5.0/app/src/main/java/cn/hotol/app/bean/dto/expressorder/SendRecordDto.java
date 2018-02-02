package cn.hotol.app.bean.dto.expressorder;

import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-08.
 */
public class SendRecordDto {

    private int exp_ord_clt_id;//收件信息主键
    private int express_id;//快递公司id
    private String express_name;//快递公司名称
    private String express_number;//快递运单号
    private int exp_ord_clt_state;//快递状态
    private Timestamp sign_time;//签收时间
    private Timestamp collect_time;//揽件时间
    private Timestamp delivery_time;//派送时间

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

    public int getExp_ord_clt_state() {
        return exp_ord_clt_state;
    }

    public void setExp_ord_clt_state(int exp_ord_clt_state) {
        this.exp_ord_clt_state = exp_ord_clt_state;
    }

    public Timestamp getSign_time() {
        return sign_time;
    }

    public void setSign_time(Timestamp sign_time) {
        this.sign_time = sign_time;
    }

    public Timestamp getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Timestamp collect_time) {
        this.collect_time = collect_time;
    }

    public Timestamp getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Timestamp delivery_time) {
        this.delivery_time = delivery_time;
    }
}
