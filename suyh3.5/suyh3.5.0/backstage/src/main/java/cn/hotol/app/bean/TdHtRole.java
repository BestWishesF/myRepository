package cn.hotol.app.bean;

import java.sql.Timestamp;

/*
 * 管理平台角色信息表
 */
public class TdHtRole {
	private int role_id;//主键标示
	private String role_name;//角色名称
	private int is_valid;//是否有效
	private String creater;//创建人
	private Timestamp create_time;//创建时间
	private int province_id;//省id
	private int city_id;//市id
	private int region_id;//区id
	private int divide_id;//划分的区域id

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public int getRegion_id() {
		return region_id;
	}

	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}

	public int getDivide_id() {
		return divide_id;
	}

	public void setDivide_id(int divide_id) {
		this.divide_id = divide_id;
	}
	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String roleName) {
		role_name = roleName;
	}
	public int getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(int isValid) {
		is_valid = isValid;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp createTime) {
		create_time = createTime;
	}




}
