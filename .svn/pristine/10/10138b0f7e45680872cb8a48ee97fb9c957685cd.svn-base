package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/***≤Àµ•≈‰÷√*/
@DatabaseTable(tableName = "menu")
public class BaseMenu implements Serializable{
	private static final long serialVersionUID = 1L;
	@DatabaseField(id= true)
	private int id;
	@DatabaseField
	private String menuCode;
	@DatabaseField
	private String menuDesc;
	@DatabaseField
	private int menuValue;
	@DatabaseField
	private String menuType; //

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public int getMenuValue() {
		return menuValue;
	}
	public void setMenuValue(int menuValue) {
		this.menuValue = menuValue;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public BaseMenu(int id, String menuCode, String menuDesc, int menuValue,
			String menuType) {
		super();
		this.id = id;
		this.menuCode = menuCode;
		this.menuDesc = menuDesc;
		this.menuValue = menuValue;
		this.menuType = menuType;
	}
	public BaseMenu() {
		super();
	}
	@Override
	public String toString() {
		return "BaseMenu [id=" + id + ", menuCode=" + menuCode + ", menuDesc="
				+ menuDesc + ", menuValue=" + menuValue + ", menuType="
				+ menuType + "]";
	}
	
	
	
	
	
}
