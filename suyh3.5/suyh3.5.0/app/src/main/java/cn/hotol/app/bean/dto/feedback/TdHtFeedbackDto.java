package cn.hotol.app.bean.dto.feedback;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/**
 * TdNtFeedback entity. @author MyEclipse Persistence Tools
 */

public class TdHtFeedbackDto implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Fields

    private Integer feed_id;//id
    private Integer user_id;//用户id
    private String user_cate;//类别1学生2代理人
    @NotNull(message = "反馈内容必须为1到1000个字符")
    @Length(min = 1, max = 1000, message = "反馈内容必须为1到1000个字符")
    private String feed_content;//内容 1024
    private Timestamp feed_time;//反馈时间
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$", message = "请输入正确手机号码")
    private String feed_phone;//手机号码

    public String getFeed_phone() {
        return feed_phone;
    }

    public void setFeed_phone(String feed_phone) {
        this.feed_phone = feed_phone;
    }

    public Integer getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(Integer feed_id) {
        this.feed_id = feed_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_cate() {
        return user_cate;
    }

    public void setUser_cate(String user_cate) {
        this.user_cate = user_cate;
    }

    public String getFeed_content() {
        return feed_content;
    }

    public void setFeed_content(String feed_content) {
        this.feed_content = feed_content;
    }

    public Timestamp getFeed_time() {
        return feed_time;
    }

    public void setFeed_time(Timestamp feed_time) {
        this.feed_time = feed_time;
    }

}