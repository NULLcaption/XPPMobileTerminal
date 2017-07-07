package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.media.audiofx.Equalizer;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseStock;
import com.xpp.moblie.provider.DataProviderFactory;

/**
 * @Title: Stock.java
 * @Package com.xpp.moblie.query
 * @Description: TODO
 * @author will.xu
 * @date 2014年5月9日 下午4:40:12
 */

public class Stock extends BaseStock {
	private static final long serialVersionUID = 1L;

	public Stock() {
		super();
	}

	public Stock(String custId, String userId, String dayType,
			String categoryId, String categoryDesc, String categorySortId,
			String categorySortDesc, String unitCode, String unitDesc,
			double quantity, String productionDate, String checkTime,
			String flag, Status status) {
		super(custId, userId, dayType, categoryId, categoryDesc,
				categorySortId, categorySortDesc, unitCode, unitDesc, quantity,
				productionDate, checkTime, flag, status);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getStockDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAll(List<Stock> list) {
		try {
			OrmHelper.getInstance().getStockDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public  boolean delete() {
		try {
			OrmHelper.getInstance().getStockDao().delete(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static List<Stock> findAll() {
		try {
			return OrmHelper.getInstance().getStockDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Stock findById(Long id) {
		try {
			List<Stock> list =  OrmHelper.getInstance().getStockDao().queryBuilder().where().eq("id", id).query();
				if(list!=null&&list.size()!=0){
					return  list.get(0);
				}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static List<Stock> findByFlag(String flag) {
		try {
			return OrmHelper.getInstance().getStockDao().queryBuilder().where()
					.eq("userId", DataProviderFactory.getUserId()).and()
//					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("flag", flag).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> findCheckTimeList(String flag, String custId) {
		try {
			List<String> ll = new ArrayList<String>();
			// QueryBuilder<Stock, Integer>qb =
			// OrmHelper.getInstance().getStockDao().queryBuilder();
			List<Stock> ss = OrmHelper.getInstance().getStockDao()
					.queryBuilder().selectColumns("checkTime").distinct()
					.where().eq("userId", DataProviderFactory.getUserId())
//					.and().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("flag", flag).and().eq("custId", custId).query();
			for (Stock stock : ss) {
				ll.add(stock.getCheckTime());
			}
			return ll;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static List<Stock> findCheckTime(String flag, String custId,String checkTime) {
		try {
			List<Stock> ss = OrmHelper.getInstance().getStockDao()
					.queryBuilder()
					.where().eq("userId", DataProviderFactory.getUserId())
//					.and().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("flag", flag).and().eq("custId", custId).and().eq("checkTime", checkTime).query();
			return ss;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static List<Stock> findbyFlagAndCheckTime(String flag,
			String checkTime, String custId) {
		try {
			List<Stock> ss = new ArrayList<Stock>();
			Stock s = new Stock();
			s.setAdd(true);
			s.setCheckTime(checkTime);
			ss.add(s);
			
			QueryBuilder<Stock,Integer> qb = OrmHelper.getInstance().getStockDao().queryBuilder();
//			if(XPPApplication.TAB_SALES_DAY.equals(flag)){
//				qb.distinct().selectColumns("categorySortId").selectColumns("categorySortDesc").selectColumns("checkTime");
//			}else{
			    qb.distinct().selectColumns("categoryId").selectColumns("categoryDesc").selectColumns("checkTime");
//			}
			qb.where().eq("userId", DataProviderFactory.getUserId()).and()
//			.eq("dayType", DataProviderFactory.getDayType()).and()
			.eq("flag", flag).and().eq("custId", custId).and()
			.eq("checkTime", checkTime);
			ss.addAll(OrmHelper.getInstance().getStockDao().query(qb.prepare()));
//			ss.addAll(OrmHelper.getInstance().getStockDao().queryBuilder()
//					.selectColumns("categoryDesc").distinct().selectColumns("categoryId").selectColumns("quantity")
//					.selectColumns("checkTime").selectColumns("custId").selectColumns("userId")
//					.selectColumns("flag").selectColumns("dayType").selectColumns("unitCode").selectColumns("unitDesc").where()
//					.eq("userId", DataProviderFactory.getUserId()).and()
//					.eq("dayType", DataProviderFactory.getDayType()).and()
//					.eq("flag", flag).and().eq("custId", custId).and()
//					.eq("checkTime", checkTime).query());
			return ss;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<List<Stock>> findChildArray(String flag,
			String checkTime, String custId, List<Stock> list) {
		try {
			List<List<Stock>> ll = new ArrayList<List<Stock>>();
			List<Stock> ss = null;
			for (Stock stock : list) {
				ss = new ArrayList<Stock>();
				if (!stock.isAdd()) {
//					ss = findChild(flag, checkTime, custId, (XPPApplication.TAB_SALES_DAY.equals(flag)?stock.getCategorySortId():stock.getCategoryId()));
					ss = findChild(flag, checkTime, custId, stock.getCategoryId());
				}
				ll.add(ss);
			}

			return ll;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Stock> findChild(String flag,
			String checkTime, String custId, String categoryId){
		try {
//			if(XPPApplication.TAB_SALES_DAY.equals(flag)){
//			
//			return OrmHelper.getInstance().getStockDao().queryBuilder()
//					.where()
//					.eq("userId", DataProviderFactory.getUserId()).and()
//					.eq("flag", flag).and()
//					.eq("checkTime", checkTime).and()
//					.eq("categorySortId", categoryId).and()
//					.eq("custId", custId).query();
//			}else{
				return OrmHelper.getInstance().getStockDao().queryBuilder()
						.where()
						.eq("userId", DataProviderFactory.getUserId()).and()
						.eq("flag", flag).and()
						.eq("checkTime", checkTime).and()
						.eq("categoryId", categoryId).and()
						.eq("custId", custId).query();
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static List<Stock> findby(String flag, String custId,String categoryId,
			String checkTime,String productionDate,String unitDesc) {
		List<Stock> ss = new ArrayList<Stock>();
		try {
			if(XPPApplication.TAB_SALES_DAY.equals(flag)){
				 ss = OrmHelper.getInstance().getStockDao()
							.queryBuilder()
							.where().eq("userId", DataProviderFactory.getUserId())
//							.and().eq("dayType", DataProviderFactory.getDayType())
							.and().eq("flag", flag).and().eq("custId", custId).
							and().eq("categorySortId", categoryId).and().eq("checkTime", checkTime).
							and().eq("unitDesc", unitDesc) .query();
				 return ss;
			}else{
				ss = OrmHelper.getInstance().getStockDao()
					.queryBuilder()
					.where().eq("userId", DataProviderFactory.getUserId())
//					.and().eq("dayType", DataProviderFactory.getDayType())
					.and().eq("flag", flag).and().eq("custId", custId).
					and().eq("categoryId", categoryId).and().eq("checkTime", checkTime).
					and().eq("productionDate", productionDate).and().eq("unitDesc", unitDesc) .query();
			     return ss;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean update() {
		try {
			return OrmHelper.getInstance().getStockDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
