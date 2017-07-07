package com.xpp.moblie.print;

import java.util.Date;
import java.util.List;

public class Order {
	private String custName;
	private String custPhone;
	private String address;// 地址
	private String contractPhone;// 联系人电话
	private String orderId;
	private Date orderDate;
	private String operator;
	private List<OrderDetail> details;
	private double totalZQuantity;// 赠品总数
	private double totalQuantity;
	private String ordertypea;//赠品总数字符串
	private String ordertypeb;//赠品总数字符串
	private double totalPrice;
	private String haveKunnrPrice;// 锟角凤拷锟叫价革拷Y N
	private String orderUser; // 下单人
	private String orderStatus;// (Y:已送 ,N:未送)
	private String orderFundStatus;// 款项（Y:已打款 ,N:未打款）
	private String remark;//描述

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<OrderDetail> getDetails() {
		return this.details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public double getTotalQuantity() {
		if (this.details != null) {
			this.totalQuantity = 0.0D;
			for (OrderDetail detail : this.details) {
				if ("A".equals(detail.getOrderType())) {
					this.totalQuantity += detail.getQuantity();
				}
			}
		}
		return this.totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalPrice() {
		if (this.details != null) {
			this.totalPrice = 0.0D;
			for (OrderDetail detail : this.details) {
				this.totalPrice += detail.getTotalPrice();
			}
		}
		return this.totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCustPhone() {
		return this.custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getHaveKunnrPrice() {
		return haveKunnrPrice;
	}

	public void setHaveKunnrPrice(String haveKunnrPrice) {
		this.haveKunnrPrice = haveKunnrPrice;
	}

	public String getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderFundStatus() {
		return orderFundStatus;
	}

	public void setOrderFundStatus(String orderFundStatus) {
		this.orderFundStatus = orderFundStatus;
	}

	public double getTotalZQuantity() {
		if (this.details != null) {
			this.totalZQuantity = 0.0D;
			for (OrderDetail detail : this.details) {
				if ("B".equals(detail.getOrderType())) {
					this.totalZQuantity += detail.getQuantity();
				}
			}
		}
		return totalZQuantity;
	}

	public void setTotalZQuantity(double totalZQuantity) {
		this.totalZQuantity = totalZQuantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrdertypea() {
		return ordertypea;
	}

	public void setOrdertypea(String ordertypea) {
		this.ordertypea = ordertypea;
	}

	public String getOrdertypeb() {
		return ordertypeb;
	}

	public void setOrdertypeb(String ordertypeb) {
		this.ordertypeb = ordertypeb;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContractPhone() {
		return contractPhone;
	}

	public void setContractPhone(String contractPhone) {
		this.contractPhone = contractPhone;
	}

}