package cn.hotol.app.bean.dto.funcion;

import java.sql.Timestamp;

/*
 * 功能角色对应关系表
 */
public class TdHtFuncroleRelDto {
	
	private int id;//主键
	private int func_id;//功能标示
	private  int role_id;//角色标示
	private int is_valid;//是否有效
	private String creater;//创建人
	private Timestamp creattime;//创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFunc_id() {
		return func_id;
	}
	public void setFunc_id(int funcId) {
		func_id = funcId;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int roleId) {
		role_id = roleId;
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
	public Timestamp getCreattime() {
		return creattime;
	}
	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}
	
	
}
