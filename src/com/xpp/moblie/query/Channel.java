package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.xpp.moblie.entity.BaseChannel;

public class Channel extends BaseChannel {
	private static final long serialVersionUID = 1L;
	
	public Channel() {
		super();
	}
	public Channel(String channelId, String channelDesc,
			String channelParentId) {
		super( channelId,  channelDesc,
				 channelParentId);
	}
	
	public boolean save(){
		try {
			OrmHelper.getInstance().getChannelDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getChannelDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<Channel> findAll(){
		try {
			List<Channel> list = OrmHelper.getInstance().getChannelDao().queryForAll();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public  static Channel findByChannel(String channelId){
		try {
			List<Channel> list = OrmHelper.getInstance().getChannelDao().queryBuilder().where().eq("channelId", channelId).query();
			if(list!=null&&list.size()!=0){
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
