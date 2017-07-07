package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xpp.moblie.application.XPPApplication.Status;

/**   
 * @Title: BaseStock.java 
 * @Package com.xpp.moblie.entity 
 * @Description: 库存上报
 * @author will.xu 
 * @date 2014年5月9日 下午4:27:10 
 */
@DatabaseTable(tableName = "stock")
public class BaseStock implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	protected int pId;
	@DatabaseField
	protected Long id;
	@DatabaseField
	protected String custId;
	@DatabaseField
	protected String userId;
	@DatabaseField
	protected String dayType;
	@DatabaseField
	protected String categoryId;// 品项id
	@DatabaseField
	protected String categoryDesc;// 品类描述
	@DatabaseField
	protected String categorySortId;
	@DatabaseField
	protected String categorySortDesc;// 品类描述
	@DatabaseField
    private String unitCode;
	@DatabaseField
    private String unitDesc;
	@DatabaseField
    private double quantity;
	@DatabaseField
    private String productionDate;
	@DatabaseField
    private String checkTime;
	@DatabaseField
    private String flag;//kunnr_week   kunnr_month    sales_day
	@DatabaseField
    private boolean add;//add || data 
	@DatabaseField
    private Status status;
	
	
	
	
	
	
	public BaseStock() {
		super();
	}
	public BaseStock(String custId, String userId, String dayType,
			String categoryId, String categoryDesc, String categorySortId,
			String categorySortDesc, String unitCode, String unitDesc,
			double quantity, String productionDate, String checkTime,
			String flag, Status status) {
		super();
		this.custId = custId;
		this.userId = userId;
		this.dayType = dayType;
		this.categoryId = categoryId;
		this.categoryDesc = categoryDesc;
		this.categorySortId = categorySortId;
		this.categorySortDesc = categorySortDesc;
		this.unitCode = unitCode;
		this.unitDesc = unitDesc;
		this.quantity = quantity;
		this.productionDate = productionDate;
		this.checkTime = checkTime;
		this.flag = flag;
		this.status = status;
	}
	
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
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
	public String getCategorySortId() {
		return categorySortId;
	}
	public void setCategorySortId(String categorySortId) {
		this.categorySortId = categorySortId;
	}
	public String getCategorySortDesc() {
		return categorySortDesc;
	}
	public void setCategorySortDesc(String categorySortDesc) {
		this.categorySortDesc = categorySortDesc;
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
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BaseStock [pId=" + pId + ", id=" + id + ", custId=" + custId
				+ ", userId=" + userId + ", dayType=" + dayType
				+ ", categoryId=" + categoryId + ", categoryDesc="
				+ categoryDesc + ", categorySortId=" + categorySortId
				+ ", categorySortDesc=" + categorySortDesc + ", unitCode="
				+ unitCode + ", unitDesc=" + unitDesc + ", quantity="
				+ quantity + ", productionDate=" + productionDate
				+ ", checkTime=" + checkTime + ", flag=" + flag + ", add="
				+ add + ", status=" + status + "]";
	}
	
	
	
}
