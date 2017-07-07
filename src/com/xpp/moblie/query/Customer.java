package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.xpp.moblie.entity.BaseCustomer;
import com.xpp.moblie.provider.DataProviderFactory;

public class Customer extends BaseCustomer {
	private static final long serialVersionUID = 1L;

	public Customer() {
		super();
	}

	public Customer(String custName, String channelId, String channelDesc,
			String diviName, String zwl04, String address, String userId,
			String contractName, String contractPhone, String contractMobile,
			String custLevel, String custType, String businessLicense,
			String kunnr, String kunnrName, String longitude, String latitude,
			String customerImportance, String customerAnnualSale) {
		super(custName, channelId, channelDesc, diviName, zwl04, address,
				userId, contractName, contractPhone, contractMobile, custLevel,
				custType, businessLicense, kunnr, kunnrName, longitude,
				latitude, customerImportance, customerAnnualSale);
	}

	public boolean save() {
		try {
			OrmHelper.getInstance().getCustomerDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update() {
		try {
			return OrmHelper.getInstance().getCustomerDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete() {
		try {
			OrmHelper.getInstance().getCustomerDao().delete(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAll(List<Customer> list) {
		try {
			OrmHelper.getInstance().getCustomerDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * MethodsTitle: 客户管理之客户列表
	 * 
	 * @author: xg.chen
	 * @date:2016年11月7日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Customer> findAllCustomer() {
		try {

			List<Customer> list = new ArrayList<Customer>();
			QueryBuilder<Customer, Integer> qb = OrmHelper.getInstance()
					.getCustomerDao().queryBuilder();
			Where<Customer, Integer> where = qb.where();
			where.and(
					where.or(where.isNull("customerDataType"),
							where.eq("customerDataType", "customerStock")),
					where.isNull("routeDate"),
					where.eq("userId", DataProviderFactory.getUserId()));
			// List<Customer> listNotRoute=
			// OrmHelper.getInstance().getCustomerDao().queryBuilder()
			// .where().isNull("routeDate").
			// and().eq("userId", DataProviderFactory.getUserId()).
			// and().isNull("customerDataType").query();
			// and().not().eq("customerDataType", "kunnr").query();
			List<Customer> listNotRoute = OrmHelper.getInstance()
					.getCustomerDao().query(qb.prepare());

			for (int i = listNotRoute.size() - 1; i >= 0; i--) {
				list.add(listNotRoute.get(i));
			}
			QueryBuilder<Customer, Integer> qb1 = OrmHelper.getInstance()
					.getCustomerDao().queryBuilder();
			Where<Customer, Integer> where1 = qb1.where();
			where1.and(
					where1.or(where1.isNull("customerDataType"),
							where1.eq("customerDataType", "customerStock")),
					where1.isNotNull("routeDate"),
					where1.eq("userId", DataProviderFactory.getUserId()));
			List<Customer> listIsRoute = OrmHelper.getInstance()
					.getCustomerDao().query(qb1.prepare());
			// List<Customer> listIsRoute=
			// OrmHelper.getInstance().getCustomerDao().queryBuilder()
			// .where().isNotNull("routeDate").
			// and().eq("userId", DataProviderFactory.getUserId()).
			// and().isNull("customerDataType").query();
			// and().not().eq("customerDataType", "kunnr").query();

			list.addAll(listIsRoute);

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * MethodsTitle: 拜访管理之查询拜访客户
	 * 
	 * @author: xg.chen
	 * @date:2016年11月7日
	 * @return
	 */
	public static List<Customer> findIsVisitShop() {
		try {
			List<Customer> list = new ArrayList<Customer>();

			List<Customer> listNotRoute = OrmHelper.getInstance()
					.getCustomerDao().queryBuilder().where()
					.eq("isVisitShop", "1").and().isNull("routeDate").and()
					.eq("userId", DataProviderFactory.getUserId()).query();

			for (int i = listNotRoute.size() - 1; i >= 0; i--) {
				list.add(listNotRoute.get(i));
			}

			List<Customer> listIsRoute = OrmHelper.getInstance()
					.getCustomerDao().queryBuilder().where()
					.eq("isVisitShop", "1").and().isNotNull("routeDate").and()
					.eq("userId", DataProviderFactory.getUserId()).query();

			list.addAll(listIsRoute);

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Customer> findRouteShop() {
		try {
			return OrmHelper.getInstance().getCustomerDao().queryBuilder()
					.where().isNotNull("routeDate").or().isNotNull("week_day")
					.and().eq("userId", DataProviderFactory.getUserId())
					.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Customer findByCustId(String custId) {
		Customer ct = new Customer();
		try {
			List<Customer> list = OrmHelper.getInstance().getCustomerDao()
					.queryBuilder().where().eq("custId", custId).query();
			if (list != null && list.size() != 0) {
				ct = list.get(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ct;
	}

	/**
	 * MethodsTitle: 查询经销商客户
	 * 
	 * @author: xg.chen
	 * @date:2016年11月22日 上午10:58:08
	 * @version 1.0
	 * @return
	 */
	public static List<Customer> findKunnrCustomer() {
		List<Customer> list = new ArrayList<Customer>();
		try {
			list = OrmHelper.getInstance().getCustomerDao().queryBuilder()
					.where().eq("customerDataType", "kunnr").query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Customer> findCustomerStock() {
		List<Customer> list = new ArrayList<Customer>();
		try {
			list = OrmHelper.getInstance().getCustomerDao().queryBuilder()
					.where().eq("customerDataType", "customerStock").query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
