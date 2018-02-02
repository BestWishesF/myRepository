package cn.hotol.app.bean.dto.expressorder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-15.
 */
public class AcceptOrderDto {

    private int agent_id;//代理人id
    private Timestamp exp_ord_time;//发件时间
    private Timestamp door_start_time;//开始上门时间
    private Timestamp door_end_time;//上门结束时间
    private String add_name;//发件人姓名
    private String add_telephone;//发件人电话
    private String add_mobile_phone;//发件人手机号
    private int add_province;//发件人省份id
    private int add_city;//发件人城市id
    private int add_region;//发件人区域id
    private String add_detail_address;//发件人详细地址
    private String agent_head_portrait;//代理人头像
    private String agent_name;//代理人姓名
    private String agent_phone;//代理人手机号
    private BigDecimal exp_ord_gratuity;//上门小费
    private List<AcceptCollectDto> accept_collect_list;//订单下的快件信息

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public Timestamp getExp_ord_time() {
        return exp_ord_time;
    }

    public void setExp_ord_time(Timestamp exp_ord_time) {
        this.exp_ord_time = exp_ord_time;
    }

    public Timestamp getDoor_start_time() {
        return door_start_time;
    }

    public void setDoor_start_time(Timestamp door_start_time) {
        this.door_start_time = door_start_time;
    }

    public Timestamp getDoor_end_time() {
        return door_end_time;
    }

    public void setDoor_end_time(Timestamp door_end_time) {
        this.door_end_time = door_end_time;
    }

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

    public String getAgent_head_portrait() {
        return agent_head_portrait;
    }

    public void setAgent_head_portrait(String agent_head_portrait) {
        this.agent_head_portrait = agent_head_portrait;
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

    public List<AcceptCollectDto> getAccept_collect_list() {
        return accept_collect_list;
    }

    public void setAccept_collect_list(List<AcceptCollectDto> accept_collect_list) {
        this.accept_collect_list = accept_collect_list;
    }

    public BigDecimal getExp_ord_gratuity() {
        return exp_ord_gratuity;
    }

    public void setExp_ord_gratuity(BigDecimal exp_ord_gratuity) {
        this.exp_ord_gratuity = exp_ord_gratuity;
    }
}
