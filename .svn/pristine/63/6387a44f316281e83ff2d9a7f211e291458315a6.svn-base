package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xpp.moblie.application.XPPApplication.Status;

/***
 * 陈列数据采集
 * */
@DatabaseTable(tableName = "display")
public class BaseDisPlay implements Serializable{
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	protected int id;
	@DatabaseField
	protected String custId;
	@DatabaseField
	protected String userId;
	@DatabaseField
	protected String dictTypeValue;//陈列类型 
	@DatabaseField
	protected String itemValue;//陈列指标
	@DatabaseField
	protected String itemDesc;//所属品牌
	@DatabaseField
	protected String counts;//数量
	@DatabaseField
	protected String dayType; 
	@DatabaseField
	protected Status status;
	
	public BaseDisPlay() {
		super();
	}
	
	public BaseDisPlay(String custId, String userId, String dictTypeValue,
			String itemValue, String itemDesc, String counts, String dayType,Status status) {
		super();
		this.custId = custId;
		this.userId = userId;
		this.dictTypeValue = dictTypeValue;
		this.itemValue = itemValue;
		this.itemDesc = itemDesc;
		this.counts = counts;
		this.dayType = dayType;
		this.status =  status;
	}
	
	
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDictTypeValue() {
		return dictTypeValue;
	}
	public void setDictTypeValue(String dictTypeValue) {
		this.dictTypeValue = dictTypeValue;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	
	
	
	
	

}
