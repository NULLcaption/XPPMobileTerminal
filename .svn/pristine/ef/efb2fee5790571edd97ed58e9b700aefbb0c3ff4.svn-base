package com.xpp.moblie.entity;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public class BaseSign implements Serializable{
	@DatabaseField(id = true)
	protected Long sign_id;
	@DatabaseField
	protected String operator_id;
	@DatabaseField
	protected String address;
	@DatabaseField
	protected String sign_type;
	@DatabaseField
	protected String longitude;
	@DatabaseField
	protected String latitude;
	@DatabaseField
	protected String sign_date;
	@DatabaseField
	protected Date create_date;
	public Long getSign_id() {
		return sign_id;
	}
	
	public BaseSign() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseSign(Long sign_id, String operator_id, String address,
			String sign_type, String longitude, String latitude,
			String sign_date, Date create_date) {
		super();
		this.sign_id = sign_id;
		this.operator_id = operator_id;
		this.address = address;
		this.sign_type = sign_type;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sign_date = sign_date;
		this.create_date = create_date;
	}

	public void setSign_id(Long sign_id) {
		this.sign_id = sign_id;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getSign_date() {
		return sign_date;
	}
	public void setSign_date(String sign_date) {
		this.sign_date = sign_date;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	@Override
	public String toString() {
		return "Sign [sign_id=" + sign_id + ", operator_id=" + operator_id
				+ ", address=" + address + ", sign_type=" + sign_type
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", sign_date=" + sign_date + ", create_date=" + create_date
				+ "]";
	}
}
