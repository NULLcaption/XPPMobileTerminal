package com.xpp.moblie.entity;

import java.util.Date;

import android.widget.EditText;

import com.j256.ormlite.field.DatabaseField;

public class BaseOrderDetail {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String orderDetailId;
	@DatabaseField
    private String userId;
	@DatabaseField
    private double quantity;
	@DatabaseField
    private String unitCode;
	@DatabaseField
    private String unitDesc;
	@DatabaseField
    private String priceUnitCode;
	@DatabaseField
    private String priceUnitDesc;
	@DatabaseField
    private double price;  //价格
	@DatabaseField
    private String orderDetailStatus;
	@DatabaseField
    private double totalPrice;
	@DatabaseField
    private String type;//增删查改
	@DatabaseField
    private String orderType;//A本品B赠品
	@DatabaseField
	private String skuId;//品项
	@DatabaseField
	private String categoryDesc;//品项名称
	@DatabaseField
	private String orderId;//订单号 (未生成订单时为order的)
	@DatabaseField
	private String custId;//客户ID
	@DatabaseField
    private String  dateType;//
	@DatabaseField
    private  String  mappingSKUId;//关联行项目   skuId&unitCode
	@DatabaseField
	private  String lastPrice; 

	public BaseOrderDetail( double quantity, String unitCode,String unitDesc,
			String priceUnitDesc, double price, double totalPrice,
			String orderType,String skuId,String categoryDesc,String custId,String  dateType
			,String userId,String mappingSKUId,String lastPrice) {
		super();
		this.unitCode= unitCode;
		this.quantity = quantity;
		this.unitDesc = unitDesc;
		this.priceUnitDesc = priceUnitDesc;
		this.price = price;
		this.totalPrice = totalPrice;
		this.orderType = orderType;
		this.skuId = skuId;
		this.categoryDesc = categoryDesc;
		this.custId = custId;
		this.dateType = dateType;
		this.userId= userId;
		this.mappingSKUId = mappingSKUId;
		this.lastPrice=lastPrice;
	}
	
	public BaseOrderDetail() {
		super();
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}
	
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getUnitCode() {
		return unitCode;
	}
	
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getUnitDesc() {
		return unitDesc;
	}
	
	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	
	public String getPriceUnitCode() {
		return priceUnitCode;
	}
	
	public void setPriceUnitCode(String priceUnitCode) {
		this.priceUnitCode = priceUnitCode;
	}
	
	public String getPriceUnitDesc() {
		return priceUnitDesc;
	}
	
	public void setPriceUnitDesc(String priceUnitDesc) {
		this.priceUnitDesc = priceUnitDesc;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}
	
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getMappingSKUId() {
		return mappingSKUId;
	}

	public void setMappingSKUId(String mappingSKUId) {
		this.mappingSKUId = mappingSKUId;
	}

	public String getlastPrice() {
		return lastPrice;
	}

	public void setlastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	@Override
	public String toString() {
		return "BaseOrderDetail [id=" + id + ", orderDetailId=" + orderDetailId
				+ ", userId=" + userId + ", quantity=" + quantity
				+ ", unitCode=" + unitCode + ", unitDesc=" + unitDesc
				+ ", priceUnitCode=" + priceUnitCode + ", priceUnitDesc="
				+ priceUnitDesc + ", price=" + price + ", orderDetailStatus="
				+ orderDetailStatus + ", totalPrice=" + totalPrice + ", type="
				+ type + ", orderType=" + orderType + ", skuId=" + skuId
				+ ", categoryDesc=" + categoryDesc + ", orderId=" + orderId
				+ ", custId=" + custId + ", dateType=" + dateType
				+ ", mappingSKUId=" + mappingSKUId + ", lastPrice=" + lastPrice
				+ "]";
	}



	
	
	
}
