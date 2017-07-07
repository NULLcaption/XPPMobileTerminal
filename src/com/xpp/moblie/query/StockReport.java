package com.xpp.moblie.query;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.R.integer;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseStockReport;
import com.xpp.moblie.provider.DataProviderFactory;

public class StockReport extends BaseStockReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public StockReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public StockReport(String skuId, String sku_name, String checkTime) {
		super(skuId, sku_name, checkTime);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getStockReportDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getStockReportDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean delete(StockReport stockreport) {
		try {
			OrmHelper.getInstance().getStockReportDao().delete(stockreport);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteAll(List<StockReport> list) {
		try {
			OrmHelper.getInstance().getStockReportDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<StockReport> findAll() {
		try {
			return OrmHelper.getInstance().getStockReportDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<StockReport> findByCustId(String custId) {
		String taday=DataProviderFactory.getDayType();
		String[] nums=taday.split("-");
		String month=nums[0]+"-"+nums[1]+"%";
		int year=Integer.parseInt(nums[0]);
		int month1 = Integer.parseInt(nums[1]);
		int fxdate=16;
List<Dictionary> fxdateList=	Dictionary.findbyRemark("custfxdate");
if (fxdateList!=null && fxdateList.size()>0) {
	fxdate=Integer.parseInt(fxdateList.get(0).getItemDesc());
}
		//先加载上月
		    Calendar a = Calendar.getInstance();  
		    a.set(Calendar.YEAR, year);  
		    a.set(Calendar.MONTH, month1 - 1);  
		    a.add(Calendar.MONTH, -1);
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    String  day1 ;
		    day1 = format1.format(a.getTime());
		    String lastmonth=day1.substring(0, 7)+"%";
//			List<StockReport> list = OrmHelper.getInstance().getStockReportDao()
//			.queryBuilder().where().eq("custId", custId).and()
//			.eq("productionDate", month).query();
		    try {
		    	QueryBuilder<StockReport, Integer> qb = OrmHelper.getInstance()
						.getStockReportDao().queryBuilder();
				 Where<StockReport, Integer> where = qb.where();
		    if (Integer.parseInt(nums[2])<fxdate) {
		    	where.and(
						where.or(
								where.like("checkTime", month),where.like("checkTime", lastmonth)
								),
						where.eq("custId", custId)
								);
				List<StockReport> list = OrmHelper.getInstance().getStockReportDao()
						.query(qb.prepare());
			return list;
		    	
		    }
		    else{
		    	where.and(
						where.like("checkTime", month),
						where.eq("custId", custId)
								);
				List<StockReport> list = OrmHelper.getInstance().getStockReportDao()
						.query(qb.prepare());
				System.out.println("list:"+list);
			return list;
		    	
						
		    }
		    } catch (SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

	public static StockReport findByParameter(String custId,
			String skuId, String checkTime) {
		try {
			List<StockReport> list = OrmHelper.getInstance().getStockReportDao()
					.queryBuilder().where().eq("custId", custId).and()
					.eq("skuId", skuId).and()
					.eq("checkTime", checkTime).and()
					.query();
			// QueryBuilder<StockReport, Integer>qb =
			// OrmHelper.getInstance().getStockReportDao().queryBuilder();
			// qb.where().eq("custId", custId).and()
			// .eq("userId", DataProviderFactory.getUserId()).and()
			// .eq("dayType", DataProviderFactory.getDayType());
			// qb.where().eq("categorySortId", categorySortId);
			// qb.where().eq("year", year);
			// qb.where().eq("month", month);
			// qb.query();
			// List<StockReport> list
			// =OrmHelper.getInstance().getStockReportDao().query(qb.prepare());
			if (list != null && list.size() != 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public static List<StockReport> findByCustIdAndCategorySortId(String custId,
//			String categorySortId) {
//
//		try {
//			List<StockReport> list = OrmHelper.getInstance().getStockReportDao()
//					.queryBuilder().where().eq("custId", custId).and()
//					.eq("categorySortId", categorySortId).and()
//					.eq("checkTime", DataProviderFactory.getDayType()).query();
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static List<StockReport> synchronousInventory(String custId) {
		try {
			// return OrmHelper.getInstance().getStockReportDao().queryBuilder()
			// .where().eq("status", Status.UNSYNCHRONOUS).and()
			// .eq("dayType", DataProviderFactory.getDayType()).and()
			// .eq("userId", DataProviderFactory.getUserId()).query();

			QueryBuilder<StockReport, Integer> qb = OrmHelper.getInstance()
					.getStockReportDao().queryBuilder();
			if (custId != null) {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
				.eq("checkTime", DataProviderFactory.getDayType()).and()
				.eq("userId", DataProviderFactory.getUserId()).and()
				.eq("custId", custId);
			}else{
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
				.eq("checkTime", DataProviderFactory.getDayType()).and()
				.eq("userId", DataProviderFactory.getUserId());
			}
			List<StockReport> list = OrmHelper.getInstance().getStockReportDao()
					.query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<StockReport> getRecordsCount(String custId) {
		try {
			return OrmHelper.getInstance().getStockReportDao().queryBuilder()
					.where().eq("checkTime", DataProviderFactory.getDayType())
					.and().eq("userId", DataProviderFactory.getUserId()).and()
					.eq("custId", custId).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static List<StockReport> synchronousStockReport(String custId) {
		try {
			// return OrmHelper.getInstance().getInventoryDao().queryBuilder()
			// .where().eq("status", Status.UNSYNCHRONOUS).and()
			// .eq("dayType", DataProviderFactory.getDayType()).and()
			// .eq("userId", DataProviderFactory.getUserId()).query();
			String taday=DataProviderFactory.getDayType();
			String[] nums=taday.split("-");
			String month=nums[0]+"-"+nums[1]+"%";
			int year=Integer.parseInt(nums[0]);
			int month1 = Integer.parseInt(nums[1]);
			//先加载上月
			  Calendar a = Calendar.getInstance();  
			    a.set(Calendar.YEAR, year);  
			    a.set(Calendar.MONTH, month1 - 1);  
			    a.add(Calendar.MONTH, -1);
			    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			    String  day1 ;
			    day1 = format1.format(a.getTime());
			    String lastmonth=day1.substring(0, 7)+"%";
			QueryBuilder<StockReport, Integer> qb = OrmHelper.getInstance()
					.getStockReportDao().queryBuilder();
			 Where<StockReport, Integer> where = qb.where();
			if (custId != null) {
				
//				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
//				.like("checkTime", month).or().like("checkTime", lastmonth).and()
//				.eq("userId", DataProviderFactory.getUserId()).and()
//				.eq("custId", custId);
				where.and(
						where.or(
								where.like("checkTime", month),where.like("checkTime", lastmonth)
								),
						where.eq("userId", DataProviderFactory.getUserId()),
						where.eq("custId", custId),
						where.eq("status", Status.UNSYNCHRONOUS)
								);
			}else{
//				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
//				.like("checkTime", month).or().like("checkTime", lastmonth).and()
//				.eq("userId", DataProviderFactory.getUserId());
				where.and(
						where.or(
								where.like("checkTime", month),where.like("checkTime", lastmonth)
								),
								where.eq("userId", DataProviderFactory.getUserId()),
								where.eq("status", Status.UNSYNCHRONOUS)
								);
			}
			

			List<StockReport> list = OrmHelper.getInstance().getStockReportDao()
					.query(qb.prepare());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
