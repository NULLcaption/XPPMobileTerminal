package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xpp.moblie.application.XPPApplication.Status;

/** 获取活动信息，提交3个字段 */

@DatabaseTable(tableName = "marketcheck")
public class BaseMarketCheck implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(id= true)
	private String id;
	@DatabaseField
	private String marketDetailId;// 活动明细单号
	@DatabaseField
	private String custId;// 客户Id
	@DatabaseField
	private String marketTotalId;// 活动总单号
	@DatabaseField
	private String marketActivityName;// 活动名称
	@DatabaseField
	private String marketDesc;// 活动描述
	@DatabaseField
	private String execution;// 执行情况  。。未检查 0 执行1 未执行2 未完全执行 3
	@DatabaseField
	private String hxProportion;// 核销比例
	@DatabaseField
	private String dxExplanation;// 督导说明
	@DatabaseField
	private Status status;// 上传状态
	@DatabaseField
	private String empId;// 用户id
	@DatabaseField
	private String dayType;// 时间戳
	@DatabaseField
	private String photoId;// 照片“,”分割
	@DatabaseField
	private String itemId;// 
	@DatabaseField
	private String planYear;// 活动年
	@DatabaseField
	private String planMonth;// 活动月

	@DatabaseField
	private String checkPercentBus;// 业务自查
	@DatabaseField
	private String reason;// 理由
	@DatabaseField
	private String myProportion;// 我司承担比例
	@DatabaseField
	private String isModify; //能否修改（是否当月）
	@DatabaseField
	private String quantity; //数量
	@DatabaseField
	private String startDate; //开始时间
	@DatabaseField
	private String endDate; //结束时间
	@DatabaseField
	private String  skuname;//SKU名称
	@DatabaseField
	private String  skuprice;//SKU单价
	@DatabaseField
	private String  cxsku;//促销品项
	@DatabaseField
	private String  payment;//费用支付金额
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getMarketTotalId() {
		return marketTotalId;
	}

	public void setMarketTotalId(String marketTotalId) {
		this.marketTotalId = marketTotalId;
	}

	public String getMarketDetailId() {
		return marketDetailId;
	}

	public void setMarketDetailId(String marketDetailId) {
		this.marketDetailId = marketDetailId;
	}

	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}

	public String getHxProportion() {
		return hxProportion;
	}

	public void setHxProportion(String hxProportion) {
		this.hxProportion = hxProportion;
	}

	public String getDxExplanation() {
		return dxExplanation;
	}

	public void setDxExplanation(String dxExplanation) {
		this.dxExplanation = dxExplanation;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}

	public String getMarketDesc() {
		return marketDesc;
	}

	public void setMarketDesc(String marketDesc) {
		this.marketDesc = marketDesc;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMarketActivityName() {
		return marketActivityName;
	}

	public void setMarketActivityName(String marketActivityName) {
		this.marketActivityName = marketActivityName;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getPlanYear() {
		return planYear;
	}

	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}

	public String getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}

	public String getCheckPercentBus() {
		return checkPercentBus;
	}

	public void setCheckPercentBus(String checkPercentBus) {
		this.checkPercentBus = checkPercentBus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMyProportion() {
		return myProportion;
	}

	public void setMyProportion(String myProportion) {
		this.myProportion = myProportion;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSkuname() {
		return skuname;
	}

	public void setSkuname(String skuname) {
		this.skuname = skuname;
	}

	public String getSkuprice() {
		return skuprice;
	}

	public void setSkuprice(String skuprice) {
		this.skuprice = skuprice;
	}

	public String getCxsku() {
		return cxsku;
	}

	public void setCxsku(String cxsku) {
		this.cxsku = cxsku;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "BaseMarketCheck [id=" + id + ", marketDetailId="
				+ marketDetailId + ", custId=" + custId + ", marketTotalId="
				+ marketTotalId + ", marketActivityName=" + marketActivityName
				+ ", marketDesc=" + marketDesc + ", execution=" + execution
				+ ", hxProportion=" + hxProportion + ", dxExplanation="
				+ dxExplanation + ", status=" + status + ", empId=" + empId
				+ ", dayType=" + dayType + ", photoId=" + photoId + ", itemId="
				+ itemId + ", planYear=" + planYear + ", planMonth="
				+ planMonth + ", checkPercentBus=" + checkPercentBus
				+ ", reason=" + reason + ", myProportion=" + myProportion
				+ ", isModify=" + isModify + ", quantity=" + quantity
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", skuname=" + skuname + ", skuprice=" + skuprice
				+ ", cxsku=" + cxsku + ", payment=" + payment + "]";
	}

	
	
}
