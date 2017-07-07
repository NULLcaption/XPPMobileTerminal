package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.xpp.moblie.entity.BaseCuanhuoQuery;



public class CuanhuoQuery extends BaseCuanhuoQuery {

	public CuanhuoQuery() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean save() {
		try {
			OrmHelper.getInstance().getCuanhuoQueryDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean update() {
		try {
			return OrmHelper.getInstance().getCuanhuoQueryDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public  boolean delete() {
		try {
			OrmHelper.getInstance().getCuanhuoQueryDao().delete(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static List<CuanhuoQuery> findAll() {
		try {
			return OrmHelper.getInstance().getCuanhuoQueryDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean deleteAll(List<CuanhuoQuery> list) {
		try {
			OrmHelper.getInstance().getCuanhuoQueryDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getCuanhuoQueryDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
