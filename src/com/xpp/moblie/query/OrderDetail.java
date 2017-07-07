package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.entity.BaseOrderDetail;
import com.xpp.moblie.provider.DataProviderFactory;


public class OrderDetail extends BaseOrderDetail {
	private static final long serialVersionUID = 1L;


	public OrderDetail(double quantity,String unitCode, String unitDesc, String priceUnitDesc,
			double price, double totalPrice, String orderType, String skuId,
			String categoryDesc,String custId,String dateType,String userId,String mappingSKUId,String lastPrice) {
		super(quantity, unitCode,unitDesc, priceUnitDesc, price, totalPrice, orderType,
				skuId, categoryDesc,custId,dateType,userId,mappingSKUId,lastPrice);
	}

	public OrderDetail() {
		super();
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getOrderDetailDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean delete(List<OrderDetail> list) {
		try {
			OrmHelper.getInstance().getOrderDetailDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<OrderDetail> findAll() {
		try {
			return OrmHelper.getInstance().getOrderDetailDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 查询是否有记录
	public static List<OrderDetail> getRecordsCount(String userId) {
		try {
			return OrmHelper.getInstance().getOrderDetailDao().queryBuilder()
					.where().eq("userId", userId).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<OrderDetail> findByCustId(String custId,String userId,String orderId) {
		try {
			List<OrderDetail> list = new ArrayList<OrderDetail>();
			if(orderId==null){
				list = OrmHelper.getInstance().getOrderDetailDao().queryBuilder()
						.where().eq("userId", userId).
						and().eq("custId", custId).and().eq("dateType", DataProviderFactory.getDayType()).query();
			}else{
			list = OrmHelper.getInstance().getOrderDetailDao().queryBuilder()
					.where().eq("userId", userId).
					and().eq("custId", custId).and().eq("dateType", DataProviderFactory.getDayType()).
					and().eq("orderId", orderId).
					query();
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<OrderDetail> synchronousOrderDetail(String custId) {
		try {
			QueryBuilder<OrderDetail, Integer> qb = OrmHelper.getInstance()
					.getOrderDetailDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("userId", DataProviderFactory.getUserId());
			} else {
				qb.where().eq("userId", DataProviderFactory.getUserId());
			}
			List<OrderDetail> list = OrmHelper.getInstance()
					.getOrderDetailDao().query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getOrderDetailDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean updateOrderId(List<OrderDetail> list,String orderId) {
		try {
			for (OrderDetail orderDetail : list) {
				orderDetail.setOrderId(orderId);
				orderDetail.update();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<OrderDetail> findByOrderId(String orderId) {
		try {
			List<OrderDetail> list = new ArrayList<OrderDetail>();
			if(orderId==null){
				list = OrmHelper.getInstance().getOrderDetailDao().queryBuilder()
						.where().eq("dateType", DataProviderFactory.getDayType()).query();
			}else{
			list = OrmHelper.getInstance().getOrderDetailDao().queryBuilder()
					.where().eq("dateType", DataProviderFactory.getDayType()).
					and().eq("orderId", orderId).
					query();
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
