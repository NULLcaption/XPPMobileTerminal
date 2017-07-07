package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xpp.moblie.application.XPPApplication.Status;

/** 盘点表 */
@DatabaseTable(tableName = "inventory")
public class BaseInventory implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	protected int id;
	@DatabaseField
	protected String custId;
	@DatabaseField
	protected String categorySortId;
	@DatabaseField
	protected String categorySortDecs;// 品类
	@DatabaseField
	protected String quantity;// 数量
	@DatabaseField
	protected String unit;// 单位Id
	@DatabaseField
	protected String unitDesc;// 单位描述（中文）
	@DatabaseField
	protected String batch;// 时间（201311） 年月
	@DatabaseField
	protected String endBatch;// 时间（201311） 年月
	@DatabaseField
	protected String userId;
	@DatabaseField
	protected String dayType;
	@DatabaseField
	protected Status status;
	@DatabaseField
	protected String year;
	@DatabaseField
	protected String month;

	public BaseInventory(String custId, String categorySortId,
			String categorySortDecs, String quantity, String unit,
			String unitDesc, String batch, String endBatch, String userId,
			String dayType, Status status) {
		super();
		this.custId = custId;
		this.categorySortId = categorySortId;
		this.categorySortDecs = categorySortDecs;
		this.quantity = quantity;
		this.unit = unit;
		this.unitDesc = unitDesc;
		this.batch = batch;
		this.endBatch = endBatch;
		this.userId = userId;
		this.dayType = dayType;
		this.status = status;
	}

	
	
	public BaseInventory(String custId, String categorySortId,
			String categorySortDecs, String userId, String dayType,
			Status status, String year, String month) {
		super();
		this.custId = custId;
		this.categorySortId = categorySortId;
		this.categorySortDecs = categorySortDecs;
		this.userId = userId;
		this.dayType = dayType;
		this.status = status;
		this.year = year;
		this.month = month;
	}



	public BaseInventory(String categorySortId,String categorySortDecs ,String year, String month) {
		super();
		this.categorySortId = categorySortId;
		this.year = year;
		this.month = month;
		this.categorySortDecs = categorySortDecs;
	}







	public String getQuantity() {
		return quantity;
	}



	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BaseInventory() {
		super();
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

	public String getCategorySortId() {
		return categorySortId;
	}

	public void setCategorySortId(String categorySortId) {
		this.categorySortId = categorySortId;
	}

	public String getCategorySortDecs() {
		return categorySortDecs;
	}

	public void setCategorySortDecs(String categorySortDecs) {
		this.categorySortDecs = categorySortDecs;
	}



	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getEndBatch() {
		return endBatch;
	}

	public void setEndBatch(String endBatch) {
		this.endBatch = endBatch;
	}



	@Override
	public String toString() {
		return "BaseInventory [id=" + id + ", custId=" + custId
				+ ", categorySortId=" + categorySortId + ", categorySortDecs="
				+ categorySortDecs + ", quantity=" + quantity + ", unit="
				+ unit + ", unitDesc=" + unitDesc + ", batch=" + batch
				+ ", endBatch=" + endBatch + ", userId=" + userId
				+ ", dayType=" + dayType + ", status=" + status + ", year="
				+ year + ", month=" + month + "]";
	}

	
	
	
}
