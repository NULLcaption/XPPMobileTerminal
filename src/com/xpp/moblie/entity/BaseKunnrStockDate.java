package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class BaseKunnrStockDate implements Serializable{
	
	
	/**
	 * 库存上报时间
	 */
	
	private static final long serialVersionUID = 1750946171135400370L;
	
	@DatabaseField(id= true)
	private String id;
	@DatabaseField
	private String year;
	@DatabaseField
	private String month;
	@DatabaseField
	private String week;
	@DatabaseField
	private String state;
	@DatabaseField
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public BaseKunnrStockDate(String id,String year,String month,String week,String state,String type) {
		this.id=id;
		this.year=year;
		this.month=month;
		this.week=week;
		this.state=state;
		this.type=type;
	}
	public BaseKunnrStockDate() {
		super();
	}
}