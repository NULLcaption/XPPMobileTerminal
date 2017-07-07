package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseDisPlay;
import com.xpp.moblie.provider.DataProviderFactory;

public class DisPlay extends BaseDisPlay {

	private static final long serialVersionUID = 1L;

	public DisPlay() {
		super();
	}

	public DisPlay(String custId, String userId, String dictTypeValue,
			String itemValue, String itemDesc, String counts, String dayType,
			Status status) {
		super(custId, userId, dictTypeValue, itemValue, itemDesc, counts,
				dayType, status);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getDisPlayDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getDisPlayDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAll(List<DisPlay> list) {
		try {
			OrmHelper.getInstance().getDisPlayDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<DisPlay> findAll() {
		try {
			return OrmHelper.getInstance().getDisPlayDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DisPlay findByItemValue(String custId, String type,
			String itemValue) {
		try {
			List<DisPlay> list = OrmHelper.getInstance().getDisPlayDao()
					.queryBuilder().where().eq("custId", custId).and()
					.eq("dictTypeValue", type).and().eq("itemValue", itemValue)
					.and().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId())
					.query();
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DisPlay findByItemValueAndDesc(String custId, String type,
			String itemValue, String itemDesc) {
		try {
			List<DisPlay> list = OrmHelper.getInstance().getDisPlayDao()
					.queryBuilder().where().eq("custId", custId).and()
					.eq("dictTypeValue", type).and().eq("itemValue", itemValue)
					.and().eq("itemDesc", itemDesc).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("userId", DataProviderFactory.getUserId()).query();
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<DisPlay> synchronousDisPlay(String custId) {
		try {
			// return OrmHelper.getInstance().getDisPlayDao()
			// .queryBuilder().where().eq("status", Status.UNSYNCHRONOUS)
			// .and().eq("dayType", DataProviderFactory.getDayType())
			// .and().eq("userId", DataProviderFactory.getUserId())
			// .query();

			QueryBuilder<DisPlay, Integer> qb = OrmHelper.getInstance()
					.getDisPlayDao().queryBuilder();
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
			List<DisPlay> list = OrmHelper.getInstance().getDisPlayDao()
					.query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<DisPlay> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getDisPlayDao().queryBuilder()
					.where().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
