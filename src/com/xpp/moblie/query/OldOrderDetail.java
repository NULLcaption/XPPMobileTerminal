package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.entity.BaseOldOrderDetail;
import com.xpp.moblie.entity.BaseOrderDetail;
import com.xpp.moblie.provider.DataProviderFactory;


public class OldOrderDetail extends BaseOldOrderDetail {
	private static final long serialVersionUID = 1L;


	public OldOrderDetail(double quantity,String unitCode, String unitDesc, String priceUnitDesc,
			double price, double totalPrice, String orderType, String skuId,
			String categoryDesc,String custId,String dateType,String userId,String mappingSKUId,String lastPrice) {
		super(quantity, unitCode,unitDesc, priceUnitDesc, price, totalPrice, orderType,
				skuId, categoryDesc,custId,dateType,userId,mappingSKUId,lastPrice);
	}

	public OldOrderDetail() {
		super();
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getOldOrderDetailDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean delete(List<OldOrderDetail> list) {
		try {
			OrmHelper.getInstance().getOldOrderDetailDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<OldOrderDetail> findAll() {
		try {
			return OrmHelper.getInstance().getOldOrderDetailDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 查询是否有记录
	public static List<OldOrderDetail> getRecordsCount(String userId) {
		try {
			return OrmHelper.getInstance().getOldOrderDetailDao().queryBuilder()
					.where().eq("userId", userId).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<OldOrderDetail> findByCustId(String custId,String userId,String orderId) {
		try {
			List<OldOrderDetail> list = new ArrayList<OldOrderDetail>();
			if(orderId==null){
				list = OrmHelper.getInstance().getOldOrderDetailDao().queryBuilder()
						.where().eq("userId", userId).
						and().eq("custId", custId).and().eq("dateType", DataProviderFactory.getDayType()).query();
			}else{
			list = OrmHelper.getInstance().getOldOrderDetailDao().queryBuilder()
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

	public static List<OldOrderDetail> synchronousOrderDetail(String custId) {
		try {
			QueryBuilder<OldOrderDetail, Integer> qb = OrmHelper.getInstance()
					.getOldOrderDetailDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("userId", DataProviderFactory.getUserId());
			} else {
				qb.where().eq("userId", DataProviderFactory.getUserId());
			}
			List<OldOrderDetail> list = OrmHelper.getInstance()
					.getOldOrderDetailDao().query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getOldOrderDetailDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean updateOrderId(List<OldOrderDetail> list,String orderId) {
		try {
			for (OldOrderDetail orderDetail : list) {
				orderDetail.setOrderId(orderId);
				orderDetail.update();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<OldOrderDetail> findByOrderId(String orderId) {
		try {
			List<OldOrderDetail> list = new ArrayList<OldOrderDetail>();
			if(orderId==null){
				list = OrmHelper.getInstance().getOldOrderDetailDao().queryBuilder()
						.where().eq("dateType", DataProviderFactory.getDayType()).query();
			}else{
			list = OrmHelper.getInstance().getOldOrderDetailDao().queryBuilder()
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

	public static boolean deleteall() {
		try {
			OrmHelper.getInstance().getOldOrderDetailDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
