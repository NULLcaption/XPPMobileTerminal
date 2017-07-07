package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseOldOrder;
import com.xpp.moblie.entity.BaseOrder;
import com.xpp.moblie.provider.DataProviderFactory;


public class OldOrder extends BaseOldOrder {
	private static final long serialVersionUID = 1L;

	public OldOrder() {
		super();
	}

	public  OldOrder(String custId, String userId, double totalPrice,
			String totalPricaeUniteCode, String totalPricaeUniteDesc,
			String orgId, Long orderCreateDate, Status status,
			double orderQuntity,String  dateType,double totalPremiumsQuntity,String couldId,
			String orderStatus, String orderFundStatus,String orderDesc) {
		super(custId, userId, totalPrice, totalPricaeUniteCode,
				totalPricaeUniteDesc, orgId, orderCreateDate, status, 
				orderQuntity,dateType,totalPremiumsQuntity,couldId,orderStatus,orderFundStatus,orderDesc);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getOldOrderDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getOldOrderDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<OldOrder> findAll() {
		try {
			return OrmHelper.getInstance().getOldOrderDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 查询是否有记录
	public static List<OldOrder> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getOldOrderDao().queryBuilder().where()
					.eq("custId", custId).and().eq("userId", DataProviderFactory.getUserId())
					.and().eq("dateType", DataProviderFactory.getDayType()).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static boolean deleteAll(List<OldOrder> list) {
		try {
			OrmHelper.getInstance().getOldOrderDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public static List<OldOrder> findByCustId(String custId) {
		try {
			List<OldOrder> list = new ArrayList<OldOrder>();
			list = OrmHelper.getInstance().getOldOrderDao().queryBuilder().orderBy("orderId", false).where()
					.eq("custId", custId).and().eq("dateType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId())
					.query();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<OldOrder> findByOrderId(String orderId) {
		try {
			List<OldOrder> list = new ArrayList<OldOrder>();
			list = OrmHelper.getInstance().getOldOrderDao().queryBuilder().where()
					.eq("orderId", orderId)
//					.and().eq("dateType", DataProviderFactory.getDayType())
//					.and().eq("couldId", DataProviderFactory.getCouldId()).and()
//					.eq("userId", DataProviderFactory.getUserId())
					.query();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static List<OldOrder> synchronousOrder(String custId) {
		try {
			QueryBuilder<OldOrder, Integer> qb = OrmHelper.getInstance()
					.getOldOrderDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("userId", DataProviderFactory.getUserId()).and()
						.eq("custId", custId).and().eq("dateType", DataProviderFactory.getDayType());
			} else {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("userId", DataProviderFactory.getUserId())
						.and().eq("dateType", DataProviderFactory.getDayType());
			}
			List<OldOrder> list = OrmHelper.getInstance().getOldOrderDao()
					.query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getOldOrderDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
