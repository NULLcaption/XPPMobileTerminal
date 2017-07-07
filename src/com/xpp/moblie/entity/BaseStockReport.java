package com.xpp.moblie.entity;


import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xpp.moblie.application.XPPApplication.Status;

@DatabaseTable(tableName = "stockreporty")
public class BaseStockReport  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 232253708871652932L;
	@DatabaseField(generatedId = true)
	private  int id;
	@DatabaseField
	private String stockId;
	@DatabaseField
	private String skuId;
	@DatabaseField
	private String custId;
	@DatabaseField
	private String categoryId;
	@DatabaseField
	private String productionDate;
	@DatabaseField
	private String checkTime;
	@DatabaseField
	private String unitDesc;
	@DatabaseField
	private String quantity;
	@DatabaseField
	private String flag;
	@DatabaseField
	private  Status status;
	@DatabaseField
	private String userId;
	@DatabaseField
	private String userType;
	@DatabaseField
	private String createDate;
	@DatabaseField
	private String sku_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getUnitDesc() {
		return unitDesc;
	}
	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BaseStockReport [id=" + id + ", stockId=" + stockId
				+ ", skuId=" + skuId + ", custId=" + custId + ", categoryId="
				+ categoryId + ", productionDate=" + productionDate
				+ ", checkTime=" + checkTime + ", unitDesc=" + unitDesc
				+ ", quantity=" + quantity + ", flag=" + flag + ", status="
				+ status + ", userId=" + userId + ", userType=" + userType
				+ ", createDate=" + createDate + ", sku_name=" + sku_name + "]";
	}
	public BaseStockReport() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BaseStockReport(int id, String stockId, String skuId, String custId,
			String categoryId, String productionDate, String checkTime,
			String unitDesc, String quantity, String flag, Status status,
			String userId, String userType, String createDate, String sku_name) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.skuId = skuId;
		this.custId = custId;
		this.categoryId = categoryId;
		this.productionDate = productionDate;
		this.checkTime = checkTime;
		this.unitDesc = unitDesc;
		this.quantity = quantity;
		this.flag = flag;
		this.status = status;
		this.userId = userId;
		this.userType = userType;
		this.createDate = createDate;
		this.sku_name = sku_name;
	}
	public BaseStockReport(String skuId, String sku_name, String checkTime) {
		this.skuId = skuId;
		this.sku_name = sku_name;
		this.checkTime = checkTime;
	}
	
	
}
