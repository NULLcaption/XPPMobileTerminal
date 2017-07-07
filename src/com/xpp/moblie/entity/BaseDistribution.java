package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xpp.moblie.application.XPPApplication.Status;
/**
 * ÆÌ»õ
 * **/
@DatabaseTable(tableName = "distribution")
public class BaseDistribution implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private String custId;
	@DatabaseField
    private String skuId;
	@DatabaseField
    private Status status;
	@DatabaseField
    private String quantity;
	@DatabaseField
    private String dayType;
	@DatabaseField
    private String userId;
	
	public BaseDistribution() {
		super();
	}
	
	public BaseDistribution(String custId, String skuId, Status status,
			String quantity, String dayType, String userId) {
		super();
		this.custId = custId;
		this.skuId = skuId;
		this.status = status;
		this.quantity = quantity;
		this.dayType = dayType;
		this.userId = userId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatu(Status status) {
		this.status = status;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BaseDistribution [id=" + id + ", custId=" + custId + ", skuId="
				+ skuId + ", status=" + status + ", quantity=" + quantity
				+ ", dayType=" + dayType + ", userId=" + userId + "]";
	}
	
	
	

}
