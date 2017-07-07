package com.xpp.moblie.entity;

import java.io.Serializable;

public class BaseDivision implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String diviName ;
	private String zwl04 ;
	private String zwl04t ;
	public String getDiviName() {
		return diviName;
	}
	public void setDiviName(String diviName) {
		this.diviName = diviName;
	}
	public String getZwl04() {
		return zwl04;
	}
	public void setZwl04(String zwl04) {
		this.zwl04 = zwl04;
	}
	public String getZwl04t() {
		return zwl04t;
	}
	public void setZwl04t(String zwl04t) {
		this.zwl04t = zwl04t;
	}

	

}
