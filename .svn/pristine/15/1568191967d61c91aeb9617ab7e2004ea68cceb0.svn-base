package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.entity.BaseDictionary;

public class Dictionary extends BaseDictionary {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean save(){
		 try {
			OrmHelper.getInstance().getDictionaryDao().createOrUpdate(this);
			 return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 return false;
	}
	

	public int getCount(){
		 try {
			  int   i= (int) OrmHelper.getInstance().getDictionaryDao().countOf();
			 return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 return 0;
	}
	
	public static boolean deleteAll(){
		try {
			OrmHelper.getInstance().getDictionaryDao().delete(OrmHelper.getInstance().getDictionaryDao().queryForAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	public static List<Dictionary> findbyTypeValue(String type){
		try {
			
			return OrmHelper.getInstance().getDictionaryDao().queryBuilder().orderBy("itemId", true).where().eq("dictTypeValue", type).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Dictionary> findbyTypeValueAndItemDesc(String type,String itemDesc){
		try {
			return OrmHelper.getInstance().getDictionaryDao().queryBuilder().where().eq("dictTypeValue", type).and()
					.eq("itemDesc", itemDesc).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**获取指标*/
	public static List<Dictionary> findbyRemark(String remark){
		try {
			return OrmHelper.getInstance().getDictionaryDao().queryBuilder().orderBy("itemValue", true).where().eq("remark", remark).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Dictionary> findbyitemName(String itemName){
		try {
			return OrmHelper.getInstance().getDictionaryDao().queryBuilder().orderBy("itemValue", true).where().eq("itemName",itemName).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
