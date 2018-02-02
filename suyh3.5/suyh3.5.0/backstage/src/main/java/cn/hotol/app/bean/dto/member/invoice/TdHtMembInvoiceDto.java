package cn.hotol.app.bean.dto.member.invoice;

/**
 * Created by lubin
 * on 2017-03-25.
 * 用户发票详情
 */
public class TdHtMembInvoiceDto {

    private int memb_ivc_id;
    private int memb_id;//用户id
    private String memb_company;//公司抬头
    private String memb_taxpayer_number;//纳税人识别号
    private String memb_company_address;//注册地址
    private String memb_company_phone;//注册电话
    private String memb_company_bank;//开户行
    private String memb_company_number;//银行账号
    private String memb_ivc_remark;//备注
    private String add_name;//姓名
    private String add_detail_address;//详细地址
    private int add_province;//省编码
    private int add_city;//市编码
    private int add_region;//区编码
    private double add_longitude;//经度
    private double add_latitude;//维度
    private String add_mobile_phone;//手机号

    public int getMemb_ivc_id() {
        return memb_ivc_id;
    }

    public void setMemb_ivc_id(int memb_ivc_id) {
        this.memb_ivc_id = memb_ivc_id;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public String getMemb_company() {
        return memb_company;
    }

    public void setMemb_company(String memb_company) {
        this.memb_company = memb_company;
    }

    public String getMemb_taxpayer_number() {
        return memb_taxpayer_number;
    }

    public void setMemb_taxpayer_number(String memb_taxpayer_number) {
        this.memb_taxpayer_number = memb_taxpayer_number;
    }

    public String getMemb_company_address() {
        return memb_company_address;
    }

    public void setMemb_company_address(String memb_company_address) {
        this.memb_company_address = memb_company_address;
    }

    public String getMemb_company_phone() {
        return memb_company_phone;
    }

    public void setMemb_company_phone(String memb_company_phone) {
        this.memb_company_phone = memb_company_phone;
    }

    public String getMemb_company_bank() {
        return memb_company_bank;
    }

    public void setMemb_company_bank(String memb_company_bank) {
        this.memb_company_bank = memb_company_bank;
    }

    public String getMemb_company_number() {
        return memb_company_number;
    }

    public void setMemb_company_number(String memb_company_number) {
        this.memb_company_number = memb_company_number;
    }

    public String getMemb_ivc_remark() {
        return memb_ivc_remark;
    }

    public void setMemb_ivc_remark(String memb_ivc_remark) {
        this.memb_ivc_remark = memb_ivc_remark;
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
}
