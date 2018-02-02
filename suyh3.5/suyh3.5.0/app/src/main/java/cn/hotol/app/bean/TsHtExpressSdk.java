package cn.hotol.app.bean;

/**
 * 快递接口配置信息表
 */
public class TsHtExpressSdk {

    private int exp_sdk_id;//主标示--单纯主键使用，自增长字段
    private int eoa_id;//快递公司开通区域表id
    private String dot_name;//网点名称
    private String dot_number;//网点编号
    private String customer_name;//分配客户名称
    private String customer_number;//分配客户编号
    private String customer_password;//客户密码
    private int sdkj_itf_type;//接口类型
    private String sdk_itf_url;//接口地址
    private String app_key;//api接口密钥
    private String app_secret;//api接口密文
    private String access_token;//接口调用凭证
    private String sdk_code;//接口方法标识

    public int getExp_sdk_id() {
        return exp_sdk_id;
    }

    public void setExp_sdk_id(int exp_sdk_id) {
        this.exp_sdk_id = exp_sdk_id;
    }

    public int getEoa_id() {
        return eoa_id;
    }

    public void setEoa_id(int eoa_id) {
        this.eoa_id = eoa_id;
    }

    public String getDot_name() {
        return dot_name;
    }

    public void setDot_name(String dot_name) {
        this.dot_name = dot_name;
    }

    public String getDot_number() {
        return dot_number;
    }

    public void setDot_number(String dot_number) {
        this.dot_number = dot_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public int getSdkj_itf_type() {
        return sdkj_itf_type;
    }

    public void setSdkj_itf_type(int sdkj_itf_type) {
        this.sdkj_itf_type = sdkj_itf_type;
    }

    public String getSdk_itf_url() {
        return sdk_itf_url;
    }

    public void setSdk_itf_url(String sdk_itf_url) {
        this.sdk_itf_url = sdk_itf_url;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getSdk_code() {
        return sdk_code;
    }

    public void setSdk_code(String sdk_code) {
        this.sdk_code = sdk_code;
    }
}
