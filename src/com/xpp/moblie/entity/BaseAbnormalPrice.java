package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.xpp.moblie.application.XPPApplication.Status;

public class BaseAbnormalPrice  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true)
	protected int id;
	@DatabaseField
	private String custId;
	@DatabaseField
	protected String categoryId;//品项id
	@DatabaseField
	protected String categoryDesc;//品项描述
	@DatabaseField
	private Status status;//状态
	@DatabaseField
	private String dayType;//数据缓存的日期
	@DatabaseField
	private String is;//是否存在异常价格
	@DatabaseField
	private String abnormalPrice;//异常价格
	@DatabaseField
	private String unit;//单位
	@DatabaseField
	private String userId;
	@DatabaseField
	private String promotionalPrice;//促销价格
	@DatabaseField
	private String standardPrice;//标准价格
	
	
	
	
	
	
	public BaseAbnormalPrice(String custId, String categoryId,
			String categoryDesc, Status status, String dayType, String is,
			String unit, String userId, String promotionalPrice,
			String standardPrice) {
		super();
		this.custId = custId;
		this.categoryId = categoryId;
		this.categoryDesc = categoryDesc;
		this.status = status;
		this.dayType = dayType;
		this.is = is;
		this.unit = unit;
		this.userId = userId;
		this.promotionalPrice = promotionalPrice;
		this.standardPrice = standardPrice;
	}


	public BaseAbnormalPrice(String custId, String categoryId,
			String categoryDesc, Status status, String dayType, String is,
			String abnormalPrice, String unit, String userId) {
		super();
		this.custId = custId;
		this.categoryId = categoryId;
		this.categoryDesc = categoryDesc;
		this.status = status;
		this.dayType = dayType;
		this.is = is;
		this.abnormalPrice = abnormalPrice;
		this.unit = unit;
		this.userId = userId;
	}


	public BaseAbnormalPrice() {
		super();
	}
	
	
	public String getCustId() {
		return custId;
	}


	public void setCustId(String custId) {
		this.custId = custId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public String getIs() {
		return is;
	}
	public void setIs(String is) {
		this.is = is;
	}
	public String getAbnormalPrice() {
		return abnormalPrice;
	}
	public void setAbnormalPrice(String abnormalPrice) {
		this.abnormalPrice = abnormalPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getPromotionalPrice() {
		return promotionalPrice;
	}


	public void setPromotionalPrice(String promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
	}


	public String getStandardPrice() {
		return standardPrice;
	}


	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}
	
	
	
	
}
