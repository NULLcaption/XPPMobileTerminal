package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import android.text.style.SuperscriptSpan;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseInventory;
import com.xpp.moblie.provider.DataProviderFactory;

public class Inventory extends BaseInventory {
	private static final long serialVersionUID = 1L;

	public Inventory(String custId, String categorySortId,
			String categorySortDecs, String quantity, String unit,
			String unitDesc, String batch, String endBatch, String userId,
			String dayType, Status status) {
		super(custId, categorySortId, categorySortDecs, quantity, unit,
				unitDesc, batch, endBatch, userId, dayType, status);
	}

	public Inventory(String custId, String categorySortId,
			String categorySortDecs, String userId, String dayType,
			Status status, String year, String month) {
		super(custId, categorySortId, categorySortDecs, userId, dayType,
				status, year, month);
	}

	public Inventory(String categorySortId, String categorySortDecs,
			String year, String month) {
		super(categorySortId, categorySortDecs, year, month);
	}

	public Inventory() {
		super();
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getInventoryDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getInventoryDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean delete(Inventory inventory) {
		try {
			OrmHelper.getInstance().getInventoryDao().delete(inventory);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteAll(List<Inventory> list) {
		try {
			OrmHelper.getInstance().getInventoryDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Inventory> findAll() {
		try {
			return OrmHelper.getInstance().getInventoryDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Inventory> findByCustId(String custId) {
		try {
			List<Inventory> list = OrmHelper.getInstance().getInventoryDao()
					.queryBuilder().where().eq("custId", custId).and()
					.eq("userId", DataProviderFactory.getUserId()).and()
					.eq("dayType", DataProviderFactory.getDayType()).query();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Inventory findByParameter(String custId,
			String categorySortId, String year, String month) {
		try {
			List<Inventory> list = OrmHelper.getInstance().getInventoryDao()
					.queryBuilder().where().eq("custId", custId).and()
					.eq("userId", DataProviderFactory.getUserId()).and()
					.eq("categorySortId", categorySortId).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("year", year).and().eq("month", month).query();
			// QueryBuilder<Inventory, Integer>qb =
			// OrmHelper.getInstance().getInventoryDao().queryBuilder();
			// qb.where().eq("custId", custId).and()
			// .eq("userId", DataProviderFactory.getUserId()).and()
			// .eq("dayType", DataProviderFactory.getDayType());
			// qb.where().eq("categorySortId", categorySortId);
			// qb.where().eq("year", year);
			// qb.where().eq("month", month);
			// qb.query();
			// List<Inventory> list
			// =OrmHelper.getInstance().getInventoryDao().query(qb.prepare());
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Inventory> findByCustIdAndCategorySortId(String custId,
			String categorySortId) {

		try {
			List<Inventory> list = OrmHelper.getInstance().getInventoryDao()
					.queryBuilder().where().eq("custId", custId).and()
					.eq("userId", DataProviderFactory.getUserId()).and()
					.eq("categorySortId", categorySortId).and()
					.eq("dayType", DataProviderFactory.getDayType()).query();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Inventory> synchronousInventory(String custId) {
		try {
			// return OrmHelper.getInstance().getInventoryDao().queryBuilder()
			// .where().eq("status", Status.UNSYNCHRONOUS).and()
			// .eq("dayType", DataProviderFactory.getDayType()).and()
			// .eq("userId", DataProviderFactory.getUserId()).query();

			QueryBuilder<Inventory, Integer> qb = OrmHelper.getInstance()
					.getInventoryDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
				.eq("dayType", DataProviderFactory.getDayType()).and()
				.eq("userId", DataProviderFactory.getUserId()).and()
				.eq("custId", custId);
			}else{
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
				.eq("dayType", DataProviderFactory.getDayType()).and()
				.eq("userId", DataProviderFactory.getUserId());
			}
			List<Inventory> list = OrmHelper.getInstance().getInventoryDao()
					.query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Inventory> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getInventoryDao().queryBuilder()
					.where().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
