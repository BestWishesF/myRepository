package cn.hotol.app.bean.dto.wechatpay;

/**
 * Created by LuBin
 * Date 2017-01-04.
 */
public class WxRequestPayDto {

    private String appid;//微信支付appid
    private String time_stamp;//时间
    private String nonce_str;//随机字符串
    private String package_value;//订单详情扩展字符串
    private String sign_type;//签名方式
    private String pay_sign;//签名

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPackage_value() {
        return package_value;
    }

    public void setPackage_value(String package_value) {
        this.package_value = package_value;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getPay_sign() {
        return pay_sign;
    }

    public void setPay_sign(String pay_sign) {
        this.pay_sign = pay_sign;
    }
}
