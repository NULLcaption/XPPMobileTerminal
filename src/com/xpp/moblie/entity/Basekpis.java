package com.xpp.moblie.entity;

import java.io.Serializable;

public class Basekpis implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String n_orgId;//��½�˵ĸ�λ��֯id
	private String n_stationId;//��½�˵ĸ�λid
	private String orgId; //���а�
	private String kunnr; //������
	private String orgName; //���а�����
	private String fx_actual; //ʵ�ʷ�����
	private String fx_target; //Ŀ�������
	private String fx_rate;//���������
	private String ck_actual; //ʵ�ʳ�����
	private String ck_target; //Ŀ�������
	private String ck_rate; //��������
	private String v_actual; //ʵ�ʰݷ�
	private String v_target; //Ŀ��ݷ�
	private String v_rate; //�ݷô����
	private String kunnrFlag;//�Ƿ���Ҫ���������
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getN_orgId() {
		return n_orgId;
	}
	public void setN_orgId(String n_orgId) {
		this.n_orgId = n_orgId;
	}
	public String getN_stationId() {
		return n_stationId;
	}
	public void setN_stationId(String n_stationId) {
		this.n_stationId = n_stationId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getKunnr() {
		return kunnr;
	}
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getFx_actual() {
		return fx_actual;
	}
	public void setFx_actual(String fx_actual) {
		this.fx_actual = fx_actual;
	}
	public String getFx_target() {
		return fx_target;
	}
	public void setFx_target(String fx_target) {
		this.fx_target = fx_target;
	}
	public String getFx_rate() {
		return fx_rate;
	}
	public void setFx_rate(String fx_rate) {
		this.fx_rate = fx_rate;
	}
	public String getCk_actual() {
		return ck_actual;
	}
	public void setCk_actual(String ck_actual) {
		this.ck_actual = ck_actual;
	}
	public String getCk_target() {
		return ck_target;
	}
	public void setCk_target(String ck_target) {
		this.ck_target = ck_target;
	}
	public String getCk_rate() {
		return ck_rate;
	}
	public void setCk_rate(String ck_rate) {
		this.ck_rate = ck_rate;
	}
	public String getKunnrFlag() {
		return kunnrFlag;
	}
	public void setKunnrFlag(String kunnrFlag) {
		this.kunnrFlag = kunnrFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getV_actual() {
		return v_actual;
	}
	public void setV_actual(String v_actual) {
		this.v_actual = v_actual;
	}
	public String getV_target() {
		return v_target;
	}
	public void setV_target(String v_target) {
		this.v_target = v_target;
	}
	public String getV_rate() {
		return v_rate;
	}
	public void setV_rate(String v_rate) {
		this.v_rate = v_rate;
	}
	
	
	


}
