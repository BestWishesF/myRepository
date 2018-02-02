package cn.hotol.app.bean.dto.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by LuBin
 * Date 2016-12-06.
 */
public class TwSmsSendHisDto {

    private int sms_his_id;//主键
    private int sms_provider_id;//短信提供商标示
    @NotNull(message = "请输入正确手机号码")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "请输入正确手机号码")
    private String phoneno;//手机号码
    private int sms_type;//短信类型 1.用户注册短信 2.用户忘记密码短信 3.代理人注册短信 4.代理人忘记密码短信 5.代理人修改备用手机号短信 6.第三方绑定手机号 7.验证码登录短信
    private int template_id;//模板标示
    private String yyyymmdd;//发送日期
    private Date send_time;//发送时间
    private String send_content;//发送内容

    public int getSms_his_id() {
        return sms_his_id;
    }

    public void setSms_his_id(int sms_his_id) {
        this.sms_his_id = sms_his_id;
    }

    public int getSms_provider_id() {
        return sms_provider_id;
    }

    public void setSms_provider_id(int sms_provider_id) {
        this.sms_provider_id = sms_provider_id;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getSms_type() {
        return sms_type;
    }

    public void setSms_type(int sms_type) {
        this.sms_type = sms_type;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public String getYyyymmdd() {
        return yyyymmdd;
    }

    public void setYyyymmdd(String yyyymmdd) {
        this.yyyymmdd = yyyymmdd;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public String getSend_content() {
        return send_content;
    }

    public void setSend_content(String send_content) {
        this.send_content = send_content;
    }
}
