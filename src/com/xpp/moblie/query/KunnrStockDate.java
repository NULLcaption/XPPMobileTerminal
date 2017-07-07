package com.xpp.moblie.query;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.R;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseKunnrStockDate;
import com.xpp.moblie.provider.DataProviderFactory;

public class KunnrStockDate extends BaseKunnrStockDate{
	
	public KunnrStockDate() {
		super();
	}
	
	public boolean save() {
		try {
			OrmHelper.getInstance().getKunnrStockDateDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getKunnrStockDateDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<KunnrStockDate> findAll() {
		try {
			return	OrmHelper.getInstance().getKunnrStockDateDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static String findStockDateByType(String flag) {
		String stockDate=null;
		try {
			if(flag.equals(XPPApplication.TAB_KUNNR_WEEK)){
				flag="1";
			}else{
				flag="2";
			}
			List<KunnrStockDate> list =OrmHelper.getInstance().getKunnrStockDateDao().queryBuilder().where()
					.eq("type", flag).query();
			if(list!=null&&list.size()!=0){
				stockDate=list.get(0).getYear()+'-'+list.get(0).getMonth()+"-"+list.get(0).getWeek();
//				if("1".equals(flag)){
//					stockDate=stockDate+"-"+list.get(0).getWeek();
//				}
			}else{
				stockDate="ÎÞÐ§";
			}
			return stockDate;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}