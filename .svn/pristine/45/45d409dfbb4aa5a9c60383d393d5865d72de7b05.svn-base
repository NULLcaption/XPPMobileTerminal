package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;




import java.util.List;

import com.xpp.moblie.entity.BaseSign;
import com.xpp.moblie.provider.DataProviderFactory;
/**
 * @Description:手机签到数据获取
 * @author:xg.chen
 * @time:2017年4月19日 上午9:39:21
 * @version:1.0
 */
public class Sign extends BaseSign{
	private static final long serialVersionUID = 1L;
	public boolean save() {
		try {
			OrmHelper.getInstance().getSignDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean update() {
		try {
			return OrmHelper.getInstance().getSignDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
			}
	public boolean delete() {
		try {
			OrmHelper.getInstance().getSignDao().delete(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Sign getTodaySign(Sign sign){
		Sign si;
		Calendar yesteday = Calendar.getInstance();
		yesteday.add(Calendar.DATE, 0);

		Calendar tommorrow = Calendar.getInstance();
		tommorrow.add(Calendar.DATE, 1);
		
		try {
			List<Sign> list = OrmHelper.getInstance().getSignDao()
					.queryBuilder().where().eq("operator_id", 
							sign.getOperator_id()).and().eq("sign_type", 
									sign.getSign_type()).and().between("create_date", yesteday.getTime(), 
											tommorrow.getTime()).query();
			if (list != null && list.size() != 0) {
				si = list.get(0);
				return si;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
