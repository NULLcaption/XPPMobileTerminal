package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "customer")
public class BaseCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@DatabaseField(id = true)
	private String custId;// �ͻ�Id
	@DatabaseField
	private String custName;// �ͻ�Name
	// @DatabaseField
	// private String contactPerson;//��ϵ��
	// @DatabaseField
	// private String contactTel;//��ϵ�绰
	@DatabaseField
	private String isVisit;// �Ƿ�ݷ�
	@DatabaseField
	private String isActivity;// �Ƿ����г��
	@DatabaseField
	private String channelId;// ����
	@DatabaseField
	private String channelDesc;// ����
	@DatabaseField
	private String channelChildId;// ������
	@DatabaseField
	private String channelChildDesc;// ������
	@DatabaseField
	private String address;// ��ַ
	@DatabaseField
	private String userName;// ҵ��
	@DatabaseField
	private String userId;// ҵ��Id
	@DatabaseField
	private String custState;// �ͻ�״̬
	@DatabaseField
	private String contractName;// ��ϵ��
	@DatabaseField
	private String contractPhone;// ��ϵ�˵绰
	@DatabaseField
	private String contractMobile;// ��ϵ���ֻ�
	@DatabaseField
	private String orgId;
	@DatabaseField
	private String orgName;
	@DatabaseField
	private String custLevel;
	@DatabaseField
	private String custType;
	@DatabaseField
	private String longitude;// ����
	@DatabaseField
	private String latitude;// γ��
	@DatabaseField
	private String businessLicense;// Ӫҵִ��
	@DatabaseField
	private String isVisitShop;// �Ƿ���ӵ��ݷ��б� 1 �ڰݷ��б���ʾ ,null Ϊ���ش����ͻ���δ��ӵ��б� ��
	@DatabaseField
	private String diviName;// ������������
	@DatabaseField
	private String zwl04;// ��id
	@DatabaseField
	private String kunnr;// ������code
	@DatabaseField
	private String kunnrName;// ����������
	@DatabaseField
	private String routeDate;// ��·ʱ�䣺�����±��
	@DatabaseField
	private String week_day;//����·ʱ�䣺
	@DatabaseField
	private String customerImportance;//�ŵ���Ҫ��
	@DatabaseField
	private String customerAnnualSale;//�ŵ����۽��

	public String getWeek_day() {
		return week_day;
	}

	public void setWeek_day(String week_day) {
		this.week_day = week_day;
	}

	public String getCustomerDataType() {
		return customerDataType;
	}

	public void setCustomerDataType(String customerDataType) {
		this.customerDataType = customerDataType;
	}

	@DatabaseField
	private String customerDataType ;//�����̿ͻ���kunnr��;��ͨ�ͻ� ��;customerStock �ŵ�������ᱨ

	
	public BaseCustomer() {
		super();
	}

	public BaseCustomer(String custName, String channelId, String channelDesc,
			String diviName, String zwl04, String address, 
			String userId, String contractName, String contractPhone,String contractMobile,
			String custLevel, String custType, String businessLicense,String kunnr,String kunnrName,
			String longitude,String latitude,String customerImportance,String customerAnnualSale) {
		super();
		this.custName = custName;
		this.channelId = channelId;
		this.channelDesc = channelDesc;
		this.diviName = diviName;
		this.zwl04 = zwl04;
		this.address = address;
		this.userId = userId;
		this.contractName = contractName;
		this.contractPhone = contractPhone;
		this.contractMobile = contractMobile ;
		this.custLevel = custLevel;
		this.custType = custType;
		this.businessLicense = businessLicense;
		this.kunnr = kunnr;
		this.kunnrName = kunnrName;
		this.longitude=longitude;
		this.latitude=latitude;
		this.customerImportance=customerImportance;
		this.customerAnnualSale=customerAnnualSale;
	}
	
	public String getCustomerImportance() {
		return customerImportance;
	}

	public void setCustomerImportance(String customerImportance) {
		this.customerImportance = customerImportance;
	}

	public String getCustomerAnnualSale() {
		return customerAnnualSale;
	}

	public void setCustomerAnnualSale(String customerAnnualSale) {
		this.customerAnnualSale = customerAnnualSale;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(String isVisit) {
		this.isVisit = isVisit;
	}

	public String getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(String isActivity) {
		this.isActivity = isActivity;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelDesc() {
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public String getChannelChildId() {
		return channelChildId;
	}

	public void setChannelChildId(String channelChildId) {
		this.channelChildId = channelChildId;
	}

	public String getChannelChildDesc() {
		return channelChildDesc;
	}

	public void setChannelChildDesc(String channelChildDesc) {
		this.channelChildDesc = channelChildDesc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustState() {
		return custState;
	}

	public void setCustState(String custState) {
		this.custState = custState;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractPhone() {
		return contractPhone;
	}

	public void setContractPhone(String contractPhone) {
		this.contractPhone = contractPhone;
	}

	public String getContractMobile() {
		return contractMobile;
	}

	public void setContractMobile(String contractMobile) {
		this.contractMobile = contractMobile;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
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

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getIsVisitShop() {
		return isVisitShop;
	}

	public void setIsVisitShop(String isVisitShop) {
		this.isVisitShop = isVisitShop;
	}

	public String getDiviName() {
		return diviName;
	}

	public void setDiviName(String diviName) {
		this.diviName = diviName;
	}

	public String getZwl04() {
		return zwl04;
	}

	public void setZwl04(String zwl04) {
		this.zwl04 = zwl04;
	}

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public String getKunnrName() {
		return kunnrName;
	}

	public void setKunnrName(String kunnrName) {
		this.kunnrName = kunnrName;
	}

	public String getRouteDate() {
		return routeDate;
	}

	public void setRouteDate(String routeDate) {
		this.routeDate = routeDate;
	}
	
	@Override
	public String toString() {
		return "BaseCustomer [custId=" + custId + ", custName=" + custName
				+ ", isVisit=" + isVisit + ", isActivity=" + isActivity
				+ ", channelId=" + channelId + ", channelDesc=" + channelDesc
				+ ", channelChildId=" + channelChildId + ", channelChildDesc="
				+ channelChildDesc + ", address=" + address + ", userName="
				+ userName + ", userId=" + userId + ", custState=" + custState
				+ ", contractName=" + contractName + ", contractPhone="
				+ contractPhone + ", contractMobile=" + contractMobile
				+ ", orgId=" + orgId + ", orgName=" + orgName + ", custLevel="
				+ custLevel + ", custType=" + custType + ", longitude="
				+ longitude + ", latitude=" + latitude + ", businessLicense="
				+ businessLicense + ", isVisitShop=" + isVisitShop
				+ ", diviName=" + diviName + ", zwl04=" + zwl04 
				+ ", kunnr=" + kunnr + ", kunnrName=" + kunnrName 
				+ ", customerImportance=" + customerImportance
				+ ", customerAnnualSale=" + customerAnnualSale
				+ ", routeDate=" + routeDate + ", week_day=" + week_day + ", customerDataType="
				+ customerDataType + "]";
	}

	
	
}
