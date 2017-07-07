package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xpp.moblie.entity.BaseUserInfo;

public class UserInfo extends BaseUserInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * @Description:save user info 
	 * @author:xg.chen 
	 * @date:2017年6月5日 下午3:18:47 
	 * @return
	 * @version:1.0
	 */
	public boolean save() {
		try {
			OrmHelper.getInstance().getUserInfoDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Description:find by login name for local sql
	 * @author:xg.chen 
	 * @date:2017年6月5日 下午3:19:04 
	 * @param loginName
	 * @return
	 * @version:1.0
	 */
	public static UserInfo findByLoginName(String loginName) {
		try {
			List<UserInfo> list  = new ArrayList<UserInfo>();
			if(loginName.matches("^[0-9]*$")&&loginName.length()==11){//电话号码
				list = OrmHelper.getInstance().getUserInfoDao()
						.queryBuilder().where().eq("mobile", loginName).query();
			}else{//用户名
				list = OrmHelper.getInstance().getUserInfoDao()
						.queryBuilder().where().eq("userCode", loginName).query();
			}
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static UserInfo findById(String userId) {
		try {
			List<UserInfo> list  = new ArrayList<UserInfo>();
			
				list = OrmHelper.getInstance().getUserInfoDao()
						.queryBuilder().where().eq("userId", userId).query();
		
			 
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<UserInfo> findAll() {
		try {
			List<UserInfo> list = OrmHelper.getInstance().getUserInfoDao()
					.queryForAll();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
