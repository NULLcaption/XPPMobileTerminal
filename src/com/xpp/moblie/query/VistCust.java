package com.xpp.moblie.query;

import java.io.Serializable;



public class VistCust implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String user_id; //用户ID
	private String vistday; //拜访日期
	private String countnum; //拜访门店数
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getVistday() {
		return vistday;
	}
	public void setVistday(String vistday) {
		this.vistday = vistday;
	}
	public String getCountnum() {
		return countnum;
	}
	public void setCountnum(String countnum) {
		this.countnum = countnum;
	}
	@Override
	public String toString() {
		return "VistCust [user_id=" + user_id + ", vistday=" + vistday
				+ ", countnum=" + countnum + "]";
	}
	
	
	
	
	
}
