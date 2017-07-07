package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/** ��ȡ���Ϣ���ύ3���ֶ� */
@DatabaseTable(tableName = "product")
public class BaseProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(id = true)
	protected String categoryId;// Ʒ��id
	@DatabaseField
	protected String categoryDesc;// Ʒ������
	@DatabaseField
	protected String categorySortId;
	@DatabaseField
	protected String categorySortDesc;// Ʒ��
	@DatabaseField
	protected String brandsId;// Ʒ��Id
	@DatabaseField
	protected String brandsDesc;// Ʒ������
	@DatabaseField
	protected String productType;// 1 ��Ʒ 2��Ʒ
	@DatabaseField
	protected String empId;// id
	@DatabaseField
	protected int skuOrder;// ����
	@DatabaseField
	protected String skuUnit;
	@DatabaseField
	protected String skuUnitId;
	@DatabaseField
	protected String cloudId;//����������
	@DatabaseField
	protected String lastPrice;//����޸ļ۸�
	@DatabaseField
	protected String pinyinsearchkey;//ƴ��ģ����ѯ
	protected String price;// �۸�
	private String promotionalPrice;//�����۸�
	private String standardPrice;//��׼�۸�
	@DatabaseField
	protected String status; 
	public BaseProduct() {
		super();
	}

	public BaseProduct(String categoryId, String categoryDesc,
			String categorySortId, String categorySortDesc, String brandsId,
			String brandsDesc, String empId,String lastPrice) {
		super();
		this.categoryId = categoryId;
		this.categoryDesc = categoryDesc;
		this.categorySortId = categorySortId;
		this.categorySortDesc = categorySortDesc;
		this.brandsId = brandsId;
		this.brandsDesc = brandsDesc;
		this.empId = empId;
		this.lastPrice=lastPrice;
	}

	
	




	public String getCloudId() {
		return cloudId;
	}

	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
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

	public void setCategorySortDecs(String categorySortDesc) {
		this.categorySortDesc = categorySortDesc;
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

	public String getBrandsId() {
		return brandsId;
	}

	public void setBrandsId(String brandsId) {
		this.brandsId = brandsId;
	}

	public String getBrandsDesc() {
		return brandsDesc;
	}

	public void setBrandsDesc(String brandsDesc) {
		this.brandsDesc = brandsDesc;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setCategorySortDesc(String categorySortDesc) {
		this.categorySortDesc = categorySortDesc;
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

	public int getSkuOrder() {
		return skuOrder;
	}

	public void setSkuOrder(int skuOrder) {
		this.skuOrder = skuOrder;
	}

	public String getSkuUnit() {
		return skuUnit;
	}

	public void setSkuUnit(String skuUnit) {
		this.skuUnit = skuUnit;
	}

	public String getSkuUnitId() {
		return skuUnitId;
	}

	public void setSkuUnitId(String skuUnitId) {
		this.skuUnitId = skuUnitId;
	}

	public String getlastPrice() {
		return lastPrice;
	}

	public void setlastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getPinyinsearchkey() {
		return pinyinsearchkey;
	}

	public void setPinyinsearchkey(String pinyinsearchkey) {
		this.pinyinsearchkey = pinyinsearchkey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BaseProduct [categoryId=" + categoryId + ", categoryDesc="
				+ categoryDesc + ", categorySortId=" + categorySortId
				+ ", categorySortDesc=" + categorySortDesc + ", brandsId="
				+ brandsId + ", brandsDesc=" + brandsDesc + ", productType="
				+ productType + ", empId=" + empId + ", skuOrder=" + skuOrder
				+ ", skuUnit=" + skuUnit + ", skuUnitId=" + skuUnitId
				+ ", cloudId=" + cloudId + ", lastPrice=" + lastPrice
				+ ", pinyinsearchkey=" + pinyinsearchkey + ", price=" + price
				+ ", promotionalPrice=" + promotionalPrice + ", standardPrice="
				+ standardPrice + "]";
	}

	
	
}
