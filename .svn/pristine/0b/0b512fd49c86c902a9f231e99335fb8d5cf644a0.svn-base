package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseDistribution;
import com.xpp.moblie.provider.DataProviderFactory;

public class Distribution extends BaseDistribution {
	private static final long serialVersionUID = 1L;

	public Distribution() {
		super();
	}

	public Distribution(String custId, String skuId, Status statu,
			String quantity, String dayType, String userId) {
		super(custId, skuId, statu, quantity, dayType, userId);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getDistributionDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getDistributionDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteAll(List<Distribution> list) {
		try {
			OrmHelper.getInstance().getDistributionDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete() {
		try {
			OrmHelper.getInstance().getDistributionDao().delete(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Distribution> findAll() {
		try {
			return OrmHelper.getInstance().getDistributionDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Distribution> synchronousDis(String custId) {
		try {
			// return OrmHelper.getInstance().getDistributionDao()
			// .queryBuilder().where().eq("status", Status.UNSYNCHRONOUS)
			// .and().eq("dayType", DataProviderFactory.getDayType())
			// .and().eq("userId", DataProviderFactory.getUserId())
			// .query();

			QueryBuilder<Distribution, Integer> qb = OrmHelper.getInstance()
					.getDistributionDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("dayType", DataProviderFactory.getDayType()).and()
						.eq("userId", DataProviderFactory.getUserId()).and()
						.eq("custId", custId);
			} else {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("dayType", DataProviderFactory.getDayType()).and()
						.eq("userId", DataProviderFactory.getUserId());
			}

			List<Distribution> list = OrmHelper.getInstance()
					.getDistributionDao().query(qb.prepare());
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Distribution> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getDistributionDao().queryBuilder()
					.where().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}// .eq("status", Status.UNSYNCHRONOUS).and()

	public static List<Distribution> findByCustId(String custId) {
		try {
			List<Distribution> list = new ArrayList<Distribution>();
			list = OrmHelper.getInstance().getDistributionDao().queryBuilder()
					.where().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).query();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Distribution findByCustIdAndSkuId(String custId, String skuId) {
		try {
			List<Distribution> list = new ArrayList<Distribution>();
			list = OrmHelper.getInstance().getDistributionDao().queryBuilder()
					.where().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).and().eq("skuId", skuId).query();
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// public static boolean deleteNotIn(String custId ){
	// try {
	// List<Distribution> li = OrmHelper.getInstance().getDistributionDao()
	// .queryBuilder().where().eq("dayType", DataProviderFactory.getDayType())
	// .and().eq("userId", DataProviderFactory.getUserId()).and().eq("custId",
	// custId)
	// .query();
	// OrmHelper.getInstance().getDistributionDao().delete(li);
	// return true;
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return false;
	//
	// }

}
