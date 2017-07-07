package com.xpp.moblie.entity;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xpp.moblie.application.XPPApplication.Status;

@DatabaseTable(tableName = "order")
public class BaseOldOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	protected int id;//id
	@DatabaseField
	private String orderId;
	@DatabaseField
	private String custId;
	@DatabaseField
	private String userId;
	@DatabaseField
    private double totalPrice;
	@DatabaseField
    private String totalPricaeUniteCode;
	@DatabaseField
    private String totalPricaeUniteDesc;
	@DatabaseField
    private String orderDesc;
	@DatabaseField
    private double orderQuntity;
	@DatabaseField
    private String orgId;
	@DatabaseField
    private String orderStatus;//(Y:已送 ,N:未送)
	@DatabaseField
    private Long orderCreateDate;
	@DatabaseField
    private Status status;
	@DatabaseField
    private String  dateType;
	@DatabaseField 
	private double totalPremiumsQuntity;
	@DatabaseField
	private String couldId;
	@DatabaseField
	private String type;
	@DatabaseField
    private String orderFundStatus;//款项（Y:已打款 ,N:未打款）
	@DatabaseField
	private String createDate;
	private String orderDetailList;
	public BaseOldOrder() {
		super();
	}
	
	public BaseOldOrder(String custId, String userId, double totalPrice,
			String totalPricaeUniteCode, String totalPricaeUniteDesc,
			String orgId, Long orderCreateDate, Status status,double orderQuntity,String  dateType,double totalPremiumsQuntity,
			String couldId,String orderStatus, String orderFundStatus,String orderDesc) {
		super();
		this.custId = custId;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.totalPricaeUniteCode = totalPricaeUniteCode;
		this.totalPricaeUniteDesc = totalPricaeUniteDesc;
		this.orgId = orgId;
		this.orderCreateDate = orderCreateDate;
		this.status = status;
		this.dateType = dateType;
		this.orderQuntity = orderQuntity;
		this.totalPremiumsQuntity = totalPremiumsQuntity;
		this.couldId= couldId;
		this.orderStatus= orderStatus ;
		this.orderFundStatus = orderFundStatus;
		this.orderDesc = orderDesc;
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
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getTotalPricaeUniteCode() {
		return totalPricaeUniteCode;
	}
	
	public void setTotalPricaeUniteCode(String totalPricaeUniteCode) {
		this.totalPricaeUniteCode = totalPricaeUniteCode;
	}
	
	public String getTotalPricaeUniteDesc() {
		return totalPricaeUniteDesc;
	}
	
	public void setTotalPricaeUniteDesc(String totalPricaeUniteDesc) {
		this.totalPricaeUniteDesc = totalPricaeUniteDesc;
	}
	
	public String getOrderDesc() {
		return orderDesc;
	}
	
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	
	
	public double getOrderQuntity() {
		return orderQuntity;
	}

	public void setOrderQuntity(double orderQuntity) {
		this.orderQuntity = orderQuntity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public Long getOrderCreateDate() {
		return orderCreateDate;
	}
	
	public void setOrderCreateDate(Long orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getTotalPremiumsQuntity() {
		return totalPremiumsQuntity;
	}

	public void setTotalPremiumsQuntity(double totalPremiumsQuntity) {
		this.totalPremiumsQuntity = totalPremiumsQuntity;
	}
	public String getCouldId() {
		return couldId;
	}

	public void setCouldId(String couldId) {
		this.couldId = couldId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderFundStatus() {
		return orderFundStatus;
	}

	public void setOrderFundStatus(String orderFundStatus) {
		this.orderFundStatus = orderFundStatus;
	}

	public String getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(String orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "BaseOldOrder [id=" + id + ", orderId=" + orderId + ", custId="
				+ custId + ", userId=" + userId + ", totalPrice=" + totalPrice
				+ ", totalPricaeUniteCode=" + totalPricaeUniteCode
				+ ", totalPricaeUniteDesc=" + totalPricaeUniteDesc
				+ ", orderDesc=" + orderDesc + ", orderQuntity=" + orderQuntity
				+ ", orgId=" + orgId + ", orderStatus=" + orderStatus
				+ ", orderCreateDate=" + orderCreateDate + ", status=" + status
				+ ", dateType=" + dateType + ", totalPremiumsQuntity="
				+ totalPremiumsQuntity + ", couldId=" + couldId + ", type="
				+ type + ", orderFundStatus=" + orderFundStatus
				+ ", createDate=" + createDate + ", orderDetailList="
				+ orderDetailList + "]";
	}


	
  
	
	
}
