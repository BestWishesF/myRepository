package cn.hotol.app.bean;

import java.util.Date;

/**
 * Created by LuBin
 * Date 2017-01-02.
 */
public class TsHtThird {

    private int third_id;//微信配置id
    private String appid;//微信开发者应用ID
    private String appsecret;//微信开发者应用密钥
    private String accesstoken;//微信接口密钥
    private Date accesstokentime;//微信接口密钥获取时间
    private String jsticket;//微信jssdk接口密钥
    private Date jstickettime;//微信jssdk接口密钥获取时间
    private int party_type;//微信类型：（1：正式；2：测试；）
    private String pay_mchid;//微信支付商户号
    private String pay_partnerkey;//微信支付密钥

    public int getThird_id() {
        return third_id;
    }

    public void setThird_id(int third_id) {
        this.third_id = third_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public Date getAccesstokentime() {
        return accesstokentime;
    }

    public void setAccesstokentime(Date accesstokentime) {
        this.accesstokentime = accesstokentime;
    }

    public String getJsticket() {
        return jsticket;
    }

    public void setJsticket(String jsticket) {
        this.jsticket = jsticket;
    }

    public Date getJstickettime() {
        return jstickettime;
    }

    public void setJstickettime(Date jstickettime) {
        this.jstickettime = jstickettime;
    }

    public int getParty_type() {
        return party_type;
    }

    public void setParty_type(int party_type) {
        this.party_type = party_type;
    }

    public String getPay_mchid() {
        return pay_mchid;
    }

    public void setPay_mchid(String pay_mchid) {
        this.pay_mchid = pay_mchid;
    }

    public String getPay_partnerkey() {
        return pay_partnerkey;
    }

    public void setPay_partnerkey(String pay_partnerkey) {
        this.pay_partnerkey = pay_partnerkey;
    }
}
