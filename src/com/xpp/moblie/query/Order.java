package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseOrder;
import com.xpp.moblie.provider.DataProviderFactory;


public class Order extends BaseOrder {
	private static final long serialVersionUID = 1L;

	public Order() {
		super();
	}

	public  Order(String custId, String userId, double totalPrice,
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
			OrmHelper.getInstance().getOrderDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getOrderDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Order> findAll() {
		try {
			return OrmHelper.getInstance().getOrderDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 查询是否有记录
	public static List<Order> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getOrderDao().queryBuilder().where()
					.eq("custId", custId).and().eq("userId", DataProviderFactory.getUserId())
					.and().eq("dateType", DataProviderFactory.getDayType()).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static boolean deleteAll(List<Order> list) {
		try {
			OrmHelper.getInstance().getOrderDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public static List<Order> findByCustId(String custId) {
		try {
			List<Order> list = new ArrayList<Order>();
			list = OrmHelper.getInstance().getOrderDao().queryBuilder().where()
					.eq("custId", custId).and().eq("dateType", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId())
					.query();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Order> findByOrderId(String orderId) {
		try {
			List<Order> list = new ArrayList<Order>();
			list = OrmHelper.getInstance().getOrderDao().queryBuilder().where()
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
	
	
	
	public static List<Order> synchronousOrder(String custId) {
		try {
			QueryBuilder<Order, Integer> qb = OrmHelper.getInstance()
					.getOrderDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("userId", DataProviderFactory.getUserId()).and()
						.eq("custId", custId).and().eq("dateType", DataProviderFactory.getDayType());
			} else {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("userId", DataProviderFactory.getUserId())
						.and().eq("dateType", DataProviderFactory.getDayType());
			}
			List<Order> list = OrmHelper.getInstance().getOrderDao()
					.query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getOrderDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
