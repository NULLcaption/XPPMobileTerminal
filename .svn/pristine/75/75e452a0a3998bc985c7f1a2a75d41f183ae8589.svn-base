package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * 经销商自定义打印格式
 * @author judy.wang
 *
 */
@DatabaseTable(tableName = "orderPrintFormat")
public class BaseOrderPrintFormat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	protected int id;//id
	@DatabaseField
	private String formatId;
	@DatabaseField
	private String kunnr;
	@DatabaseField
	private String propertiesCode;
	@DatabaseField
    private String propertiesTxt;
	@DatabaseField
    private String type;
	@DatabaseField
    private String orderDesc;
	@DatabaseField
    private String memo;
	
	public BaseOrderPrintFormat() {
		super();
	}
	
	public BaseOrderPrintFormat(String formatId, String kunnr, String propertiesCode,
			String propertiesTxt, String type,String orderDesc,String memo) {
		super();
		this.formatId = formatId;
		this.kunnr = kunnr;
		this.propertiesCode = propertiesCode;
		this.propertiesTxt = propertiesTxt;
		this.type = type;
		this.orderDesc = orderDesc;
		this.memo = memo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFormatId() {
		return formatId;
	}

	public void setFormatId(String formatId) {
		this.formatId = formatId;
	}

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public String getPropertiesCode() {
		return propertiesCode;
	}

	public void setPropertiesCode(String propertiesCode) {
		this.propertiesCode = propertiesCode;
	}

	public String getPropertiesTxt() {
		return propertiesTxt;
	}

	public void setPropertiesTxt(String propertiesTxt) {
		this.propertiesTxt = propertiesTxt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
