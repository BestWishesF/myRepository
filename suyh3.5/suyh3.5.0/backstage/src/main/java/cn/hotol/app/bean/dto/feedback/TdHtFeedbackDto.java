package cn.hotol.app.bean.dto.feedback;

import java.sql.Timestamp;

/**
 * TdHtFeedback entity. @author MyEclipse Persistence Tools
 */

public class TdHtFeedbackDto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer feed_id;//id
	private Integer user_id;//用户id
	private String user_cate;//用户类别 1学生2代理人
	private String feed_content;//反馈内容
	private Timestamp feed_time;//反馈时间
	private String feed_phone;//手机号

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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}