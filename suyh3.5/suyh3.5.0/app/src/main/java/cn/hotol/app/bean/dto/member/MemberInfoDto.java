package cn.hotol.app.bean.dto.member;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Past;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-07.
 */
public class MemberInfoDto {

    @Length(max = 16, message = "用户昵称过长")
    private String memb_nick_name;//用户昵称  手机号码注册用户默认为手机号码  第三方注册默认为第三方昵称
    private String memb_head_portrait;//用户头像文件地址
    @Past(message = "请输入正确的生日日期")
    private Timestamp memb_birthday;//用户生日时间戳 默认为当前时间戳
    private int memb_sex;//用户性别 1男2女3其他 默认为3
    private String memb_mobile;//用户手机号

    private int share_invitation_switch;//分享邀请开关
    private String share_invitation_link;//分享邀请链接
    private String share_invitation_img;//分享邀请图片

    private int is_setting_pass;//是否需要设置密码 0、需要；1、不需要；

    public String getMemb_nick_name() {
        return memb_nick_name;
    }

    public void setMemb_nick_name(String memb_nick_name) {
        this.memb_nick_name = memb_nick_name;
    }

    public String getMemb_head_portrait() {
        return memb_head_portrait;
    }

    public void setMemb_head_portrait(String memb_head_portrait) {
        this.memb_head_portrait = memb_head_portrait;
    }

    public Timestamp getMemb_birthday() {
        return memb_birthday;
    }

    public void setMemb_birthday(Timestamp memb_birthday) {
        this.memb_birthday = memb_birthday;
    }

    public int getMemb_sex() {
        return memb_sex;
    }

    public void setMemb_sex(int memb_sex) {
        this.memb_sex = memb_sex;
    }

    public String getMemb_mobile() {
        return memb_mobile;
    }

    public void setMemb_mobile(String memb_mobile) {
        this.memb_mobile = memb_mobile;
    }

    public int getShare_invitation_switch() {
        return share_invitation_switch;
    }

    public void setShare_invitation_switch(int share_invitation_switch) {
        this.share_invitation_switch = share_invitation_switch;
    }

    public String getShare_invitation_link() {
        return share_invitation_link;
    }

    public void setShare_invitation_link(String share_invitation_link) {
        this.share_invitation_link = share_invitation_link;
    }

    public String getShare_invitation_img() {
        return share_invitation_img;
    }

    public void setShare_invitation_img(String share_invitation_img) {
        this.share_invitation_img = share_invitation_img;
    }

    public int getIs_setting_pass() {
        return is_setting_pass;
    }

    public void setIs_setting_pass(int is_setting_pass) {
        this.is_setting_pass = is_setting_pass;
    }
}
