package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseMarketCheck;
import com.xpp.moblie.provider.DataProviderFactory;

public class MarketCheck extends BaseMarketCheck {
	private static final long serialVersionUID = 1L;

	public boolean save() {
		try {
			OrmHelper.getInstance().getMarketCheckDao().createIfNotExists(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getMarketCheckDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteAll(List<MarketCheck> list) {
		try {
			OrmHelper.getInstance().getMarketCheckDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<MarketCheck> findAll() {
		try {
			return OrmHelper.getInstance().getMarketCheckDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 是否存在记录
		public static MarketCheck findById(String custId,String marketDetailId ) {
			try {
//				List<MarketCheck> list =  OrmHelper.getInstance().getMarketCheckDao().queryBuilder()
//						.where().eq("custId", custId).and()
//						.eq("marketDetailId", marketDetailId).and()
//						.eq("itemId", itemId).
//						query();
//				if(list!=null && list.size()!=0){
//					return list.get(0);
//				}
				return OrmHelper.getInstance().getMarketCheckDao().queryForId(Integer.valueOf(custId+marketDetailId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	
	// 客户下所有活动明细
	public static List<MarketCheck> findByCustId(String custId) {
		try {
			return OrmHelper.getInstance().getMarketCheckDao().queryBuilder()
					.where().eq("custId", custId).and().eq("empId", DataProviderFactory.getUserId()).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<MarketCheck> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getMarketCheckDao().queryBuilder()
					.where().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("empId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).and().isNotNull("status").query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<MarketCheck> synchronousMarketCheck(String custId) {
		try {
			// return OrmHelper.getInstance().getMarketCheckDao()
			// .queryBuilder().where().eq("status", Status.UNSYNCHRONOUS)
			// .and().eq("dayType", DataProviderFactory.getDayType())
			// .and().eq("empId", DataProviderFactory.getUserId())
			// .query();

			QueryBuilder<MarketCheck, Integer> qb = OrmHelper.getInstance()
					.getMarketCheckDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("dayType", DataProviderFactory.getDayType()).and()
						.eq("empId", DataProviderFactory.getUserId()).and()
						.eq("custId", custId);
			} else {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("dayType", DataProviderFactory.getDayType()).and()
						.eq("empId", DataProviderFactory.getUserId());
			}
			List<MarketCheck> list = OrmHelper.getInstance()
					.getMarketCheckDao().query(qb.prepare());

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
