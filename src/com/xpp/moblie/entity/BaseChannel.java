package com.xpp.moblie.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "channel")
public class BaseChannel implements Serializable{
	private static final long serialVersionUID = 1L;
	@DatabaseField(id = true)
	private String channelId;//渠道ID
	@DatabaseField
	private String channelDesc;//渠道描述
	@DatabaseField
	private String channelParentId;//父级渠道id
	
	public BaseChannel() {
		super();
	}
	public BaseChannel(String channelId, String channelDesc,
			String channelParentId) {
		super();
		this.channelId = channelId;
		this.channelDesc = channelDesc;
		this.channelParentId = channelParentId;
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
	public String getChannelParentId() {
		return channelParentId;
	}
	public void setChannelParentId(String channelParentId) {
		this.channelParentId = channelParentId;
	}
	
	
	
	

}
