package cn.hotol.app.bean;

import java.sql.Timestamp;

/*
 * 管理平台功能信息表
 */
public class TdHtFuncion {

	private int func_id;//主键标示

	private String func_name;//功能名称
	private String link_url;//连接地址
	private int is_valid;//是否有效
	private String creater;//创建人
	private Timestamp cratetime;//创建时间

	private int father_id;//父id
	private int func_sort;//排序
	private int func_type;//1列表2不显示列表

	public int getFunc_type() {
		return func_type;
	}

	public void setFunc_type(int func_type) {

		this.func_type = func_type;
	}

	public int getFunc_id() {
		return func_id;
	}

	public void setFunc_id(int func_id) {
		this.func_id = func_id;
	}

	public String getFunc_name() {
		return func_name;
	}

	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}

	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	public int getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(int is_valid) {
		this.is_valid = is_valid;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Timestamp getCratetime() {
		return cratetime;
	}

	public void setCratetime(Timestamp cratetime) {
		this.cratetime = cratetime;
	}

	public int getFather_id() {
		return father_id;
	}

	public void setFather_id(int father_id) {
		this.father_id = father_id;
	}

	public int getFunc_sort() {
		return func_sort;
	}

	public void setFunc_sort(int func_sort) {
		this.func_sort = func_sort;
	}


	
}
