package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.xpp.moblie.entity.BaseOrderPrintFormat;


/**
 * 手机端订单打印格式自定义
 * 
 * @author judy.wang
 *
 */
public class OrderPrintFormat extends BaseOrderPrintFormat {
	private static final long serialVersionUID = 1L;

	public OrderPrintFormat() {
		super();
	}

	public OrderPrintFormat(String formatId, String kunnr,
			String propertiesCode, String propertiesTxt, String type,
			String orderDesc,String memo) {
		super(formatId, kunnr, propertiesCode, propertiesTxt, type, orderDesc,memo);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getOrderPrintFormatDao()
					.createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<OrderPrintFormat> findAll() {
		try {
			List<OrderPrintFormat> list = OrmHelper.getInstance()
					.getOrderPrintFormatDao().queryForAll();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getOrderPrintFormatDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<OrderPrintFormat> findByKunnr(String kunnr) {
		try {
			List<OrderPrintFormat> list = OrmHelper.getInstance()
					.getOrderPrintFormatDao().queryBuilder().where().eq("kunnr", kunnr).query();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
