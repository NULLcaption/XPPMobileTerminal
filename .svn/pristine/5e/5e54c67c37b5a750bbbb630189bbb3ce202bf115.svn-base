package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseAbnormalPrice;
import com.xpp.moblie.provider.DataProviderFactory;

public class AbnormalPrice extends BaseAbnormalPrice {
	private static final long serialVersionUID = 1L;

	public AbnormalPrice() {
		super();
	}

	public AbnormalPrice(String custId, String categoryId, String categoryDesc,
			Status status, String dayType, String is, String unit,
			String userId, String promotionalPrice, String standardPrice) {
		super(custId, categoryId, categoryDesc, status, dayType, is, unit,
				userId, promotionalPrice, standardPrice);
	}

	public AbnormalPrice(String custId, String categoryId, String categoryDesc,
			Status status, String dayType, String is, String abnormalPrice,
			String unit, String userId) {
		super(custId, categoryId, categoryDesc, status, dayType, is,
				abnormalPrice, unit, userId);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getAbnormalPriceDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getAbnormalPriceDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAll(List<AbnormalPrice> list) {
		try {
			OrmHelper.getInstance().getAbnormalPriceDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<AbnormalPrice> findAll() {
		try {
			return OrmHelper.getInstance().getAbnormalPriceDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AbnormalPrice findByCustIdAndCate(String custId,
			String categoryId) {
		try {

			List<AbnormalPrice> list = OrmHelper.getInstance()
					.getAbnormalPriceDao().queryBuilder().where()
					.eq("userId", DataProviderFactory.getUserId()).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("custId", custId).and().eq("categoryId", categoryId)
					.query();
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<AbnormalPrice> synchronousAbnormalPrice(String custId) {
		try {
			// OrmHelper.getInstance().getAbnormalPriceDao()
			// .queryBuilder().where().eq("status", Status.UNSYNCHRONOUS)
			// .and().eq("dayType", DataProviderFactory.getDayType())
			// .and().eq("userId", DataProviderFactory.getUserId())
			// .query();
			QueryBuilder<AbnormalPrice, Integer> qb = OrmHelper.getInstance()
					.getAbnormalPriceDao().queryBuilder();
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
			List<AbnormalPrice> list = OrmHelper.getInstance()
					.getAbnormalPriceDao().query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<AbnormalPrice> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getAbnormalPriceDao().queryBuilder()
					.where().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).query();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
