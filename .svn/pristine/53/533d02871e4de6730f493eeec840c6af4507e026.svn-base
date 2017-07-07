package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.xpp.moblie.entity.BaseSkuUnit;

public class SkuUnit extends BaseSkuUnit{
	private static final long serialVersionUID = 1L;
	public SkuUnit() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SkuUnit(String sku_id, String sku_unit, String sku_name,
			String sku_category_id) {
		super(sku_id, sku_unit, sku_name, sku_category_id);
		// TODO Auto-generated constructor stub
	}


	public boolean save() {
		try {
			OrmHelper.getInstance().getSkuUnitDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean update() {
		try {
			return OrmHelper.getInstance().getSkuUnitDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public  boolean delete() {
		try {
			OrmHelper.getInstance().getSkuUnitDao().delete(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static List<SkuUnit> findAll() {
		try {
			return OrmHelper.getInstance().getSkuUnitDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getSkuUnitDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
