package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "skuunit")
public class BaseSkuUnit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField (id = true)
	protected String sku_id;
	@DatabaseField
	protected String sku_unit;
	@DatabaseField
	protected String sku_name;
	@DatabaseField
	protected String sku_category_id;
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_unit() {
		return sku_unit;
	}
	public void setSku_unit(String sku_unit) {
		this.sku_unit = sku_unit;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public String getSku_category_id() {
		return sku_category_id;
	}
	public void setSku_category_id(String sku_category_id) {
		this.sku_category_id = sku_category_id;
	}
	public BaseSkuUnit(String sku_id, String sku_unit, String sku_name,
			String sku_category_id) {
		super();
		this.sku_id = sku_id;
		this.sku_unit = sku_unit;
		this.sku_name = sku_name;
		this.sku_category_id = sku_category_id;
	}
	public BaseSkuUnit() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BaseSkuUnit [sku_id=" + sku_id + ", sku_unit=" + sku_unit
				+ ", sku_name=" + sku_name + ", sku_category_id="
				+ sku_category_id + "]";
	}
	
}
