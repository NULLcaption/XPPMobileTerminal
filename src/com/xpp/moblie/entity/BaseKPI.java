package com.xpp.moblie.entity;

import java.io.Serializable;

public class BaseKPI implements Serializable {
	private int id;
	private String area;//����
	private String province;//ʡ��
	private String city;//����
	private String areaDest;//����Ŀ��
	private String areaComp;//������
	private String areaRate;//���������
	private String provinceDest;//ʡĿ��
	private String provinceComp;//ʡ���
	private String provinceprovinceRate;//ʡ�����
	private String cityDest;//����Ŀ��
	private String cityComp;//�������
	private String cityRate;//���г���
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreaDest() {
		return areaDest;
	}
	public void setAreaDest(String areaDest) {
		this.areaDest = areaDest;
	}
	public String getAreaComp() {
		return areaComp;
	}
	public void setAreaComp(String areaComp) {
		this.areaComp = areaComp;
	}


	public String getAreaRate() {
		return areaRate;
	}
	public void setAreaRate(String areaRate) {
		this.areaRate = areaRate;
	}
	public String getProvinceDest() {
		return provinceDest;
	}
	public void setProvinceDest(String provinceDest) {
		this.provinceDest = provinceDest;
	}
	public String getProvinceComp() {
		return provinceComp;
	}
	public void setProvinceComp(String provinceComp) {
		this.provinceComp = provinceComp;
	}
	public String getProvinceprovinceRate() {
		return provinceprovinceRate;
	}
	public void setProvinceprovinceRate(String provinceprovinceRate) {
		this.provinceprovinceRate = provinceprovinceRate;
	}
	public String getCityDest() {
		return cityDest;
	}
	public void setCityDest(String cityDest) {
		this.cityDest = cityDest;
	}
	public String getCityComp() {
		return cityComp;
	}
	public void setCityComp(String cityComp) {
		this.cityComp = cityComp;
	}
	public String getCityRate() {
		return cityRate;
	}
	public void setCityRate(String cityRate) {
		this.cityRate = cityRate;
	}
	
	
	
	
}
