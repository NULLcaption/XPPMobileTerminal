package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * @Description:字典数据
 * @author:xg.chen
 * @time:2017年6月5日 下午3:44:15
 * @version:1.0
 */
@DatabaseTable(tableName = "dictionary")
public class BaseDictionary implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(id = true)
	private String itemId;// 字典明细项
	@DatabaseField
	private String itemName;
	@DatabaseField
	private String itemDesc;
	@DatabaseField
	private String itemValue;
	@DatabaseField
	private String dictTypeId;
	@DatabaseField
	private String dictTypeName;
	@DatabaseField
	private String dictTypeValue;
	@DatabaseField
	private String remark;
	@DatabaseField
	private int count;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public String getDictTypeValue() {
		return dictTypeValue;
	}

	public void setDictTypeValue(String dictTypeValue) {
		this.dictTypeValue = dictTypeValue;
	}

	@Override
	public String toString() {
		return "BaseDictionary [itemId=" + itemId + ", itemName=" + itemName
				+ ", itemDesc=" + itemDesc + ", itemValue=" + itemValue
				+ ", dictTypeId=" + dictTypeId + ", dictTypeName="
				+ dictTypeName + ", dictTypeValue=" + dictTypeValue
				+ ", remark=" + remark + ", count=" + count + "]";
	}

}
