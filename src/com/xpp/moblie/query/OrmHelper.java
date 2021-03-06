package com.xpp.moblie.query;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
/**
 * @Description:SQL orm helper class
 * @author:xg.chen
 * @time:2017年6月5日 上午10:04:14
 * @version:1.0
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "xpp.db";
	private static final int DATABASE_VERSION = 31;// 30     手机db版本,2016-10-21

	private static OrmHelper instance;
	private Dao<PhotoInfo, Integer> photoInfoDao = null;
	private Dao<MarketCheck, Integer> marketCheckDao = null;
	private Dao<Product, Integer> productDao = null;
	private Dao<Customer, Integer> customerDao = null;
	private Dao<UserInfo, Integer> userInfoDao = null;
	private Dao<AbnormalPrice, Integer> abnormalPriceDao = null;
	private Dao<Inventory, Integer> inventoryDao = null;
	private Dao<Distribution, Integer> distributionDao = null;
	private Dao<Dictionary, Integer> dictionaryDao = null;
	private Dao<DisPlay, Integer> disPlayDao = null;
	private Dao<Channel, Integer> channelDao = null;
	private Dao<Menu, Integer> menuDao = null;
	private Dao<Order, Integer> orderDao = null;
	private Dao<OrderDetail, Integer> orderDetailDao = null;
	private Dao<OldOrder, Integer> oldorderDao = null;
	private Dao<OldOrderDetail, Integer> oldorderDetailDao = null;
	private Dao<Stock, Integer> stockDao = null;
	private Dao<KunnrStockDate, Integer> kunnrStockDateDao=null;
	private Dao<OrderPrintFormat, Integer> orderPrintFormatDao=null;
	private Dao<CuanhuoQuery, Integer> CuanhuoQueryDao = null;
	private Dao<Sign, Integer> signDao = null;
	private Dao<SkuUnit, Integer> SkuUnitDao = null;
	private Dao<StockReport, Integer>StockReportDao = null;
	
	private OrmHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	public static OrmHelper getInstance() {
		return instance;
	}

	public static void createInstance(Context context) {
		if (instance == null)
			instance = new OrmHelper(context);
	}

	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, PhotoInfo.class);
			TableUtils.createTable(connectionSource, MarketCheck.class);
			TableUtils.createTable(connectionSource, Product.class);
			TableUtils.createTable(connectionSource, Customer.class);
			TableUtils.createTable(connectionSource, UserInfo.class);
			TableUtils.createTable(connectionSource, AbnormalPrice.class);
			TableUtils.createTable(connectionSource, Inventory.class);
			TableUtils.createTable(connectionSource, Distribution.class);
			TableUtils.createTable(connectionSource, Dictionary.class);
			TableUtils.createTable(connectionSource, DisPlay.class);
			TableUtils.createTable(connectionSource, Channel.class);
			TableUtils.createTable(connectionSource, Menu.class);
			TableUtils.createTable(connectionSource, Order.class);
			TableUtils.createTable(connectionSource, OrderDetail.class);
			TableUtils.createTable(connectionSource, OldOrder.class);
			TableUtils.createTable(connectionSource, OldOrderDetail.class);
			TableUtils.createTable(connectionSource, Stock.class);
			TableUtils.createTable(connectionSource, KunnrStockDate.class);
			TableUtils.createTable(connectionSource, OrderPrintFormat.class);
			TableUtils.createTable(connectionSource, CuanhuoQuery.class);
			TableUtils.createTable(connectionSource, Sign.class);
			TableUtils.createTable(connectionSource, SkuUnit.class);
			TableUtils.createTable(connectionSource, StockReport.class);
		} catch (SQLException e) {
			Log.e(OrmHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(OrmHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, PhotoInfo.class, true);
			TableUtils.dropTable(connectionSource, MarketCheck.class, true);
			TableUtils.dropTable(connectionSource, Product.class, true);
			TableUtils.dropTable(connectionSource, Customer.class, true);
			TableUtils.dropTable(connectionSource, UserInfo.class, true);
			TableUtils.dropTable(connectionSource, AbnormalPrice.class, true);
			TableUtils.dropTable(connectionSource, Inventory.class, true);
			TableUtils.dropTable(connectionSource, Distribution.class, true);
			TableUtils.dropTable(connectionSource, Dictionary.class, true);
			TableUtils.dropTable(connectionSource, DisPlay.class, true);
			TableUtils.dropTable(connectionSource, Channel.class, true);
			TableUtils.dropTable(connectionSource, Menu.class, true);
			TableUtils.dropTable(connectionSource, Order.class, true);
			TableUtils.dropTable(connectionSource, OrderDetail.class, true);
			TableUtils.dropTable(connectionSource, OldOrder.class, true);
			TableUtils.dropTable(connectionSource, OldOrderDetail.class, true);
			TableUtils.dropTable(connectionSource, Stock.class,true);
			TableUtils.dropTable(connectionSource, KunnrStockDate.class,true);
			TableUtils.dropTable(connectionSource, OrderPrintFormat.class,true);
			TableUtils.dropTable(connectionSource, CuanhuoQuery.class,true);
			TableUtils.dropTable(connectionSource, Sign.class,true);
			TableUtils.dropTable(connectionSource, SkuUnit.class,true);
			TableUtils.dropTable(connectionSource, StockReport.class,true);
			
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		photoInfoDao = null;
		marketCheckDao = null;
		productDao = null;
		customerDao = null;
		userInfoDao = null;
		abnormalPriceDao = null;
		inventoryDao = null;
		distributionDao = null;
		dictionaryDao = null;
		disPlayDao = null;
		channelDao = null;
		menuDao = null ;
		orderDao = null;
		orderDetailDao = null;
		oldorderDao = null;
		oldorderDetailDao = null;
		stockDao = null;
		kunnrStockDateDao=null;
		orderPrintFormatDao=null;
		CuanhuoQueryDao=null;
		signDao = null;
		SkuUnitDao = null;
		StockReportDao= null;
	}

	public Dao<PhotoInfo, Integer> getPhotoInfoDao() throws SQLException {
		if (photoInfoDao == null) {
			photoInfoDao = getDao(PhotoInfo.class);
		}
		return photoInfoDao;
	}

	public Dao<MarketCheck, Integer> getMarketCheckDao() throws SQLException {
		if (marketCheckDao == null) {
			marketCheckDao = getDao(MarketCheck.class);
		}
		return marketCheckDao;
	}

	public Dao<Product, Integer> getProductDao() throws SQLException {
		if (productDao == null) {
			productDao = getDao(Product.class);
		}
		return productDao;
	}

	public Dao<Customer, Integer> getCustomerDao() throws SQLException {
		if (customerDao == null) {
			customerDao = getDao(Customer.class);
		}
		return customerDao;
	}

	public Dao<UserInfo, Integer> getUserInfoDao() throws SQLException {
		if (userInfoDao == null) {
			userInfoDao = getDao(UserInfo.class);
		}
		return userInfoDao;
	}

	public Dao<AbnormalPrice, Integer> getAbnormalPriceDao()
			throws SQLException {
		if (abnormalPriceDao == null) {
			abnormalPriceDao = getDao(AbnormalPrice.class);
		}
		return abnormalPriceDao;
	}

	public Dao<Inventory, Integer> getInventoryDao() throws SQLException {
		if (inventoryDao == null) {
			inventoryDao = getDao(Inventory.class);
		}
		return inventoryDao;
	}

	public Dao<Distribution, Integer> getDistributionDao() throws SQLException {
		if (distributionDao == null) {
			distributionDao = getDao(Distribution.class);
		}
		return distributionDao;
	}

	public Dao<Dictionary, Integer> getDictionaryDao() throws SQLException {
		if (dictionaryDao == null) {
			dictionaryDao = getDao(Dictionary.class);
		}
		return dictionaryDao;
	}

	public Dao<DisPlay, Integer> getDisPlayDao() throws SQLException {
		if (disPlayDao == null) {
			disPlayDao = getDao(DisPlay.class);
		}
		return disPlayDao;
	}

	public Dao<Channel, Integer> getChannelDao() throws SQLException {
		if (channelDao == null) {
			channelDao = getDao(Channel.class);
		}
		return channelDao;
	}
	public Dao<Menu, Integer> getMenuDao() throws SQLException {
		if (menuDao == null) {
			menuDao = getDao(Menu.class);
		}
		return menuDao;
	}
	public Dao<Order, Integer> getOrderDao() throws SQLException {
		if (orderDao == null) {
			orderDao = getDao(Order.class);
		}
		return orderDao;
	}
	public Dao<OrderDetail, Integer> getOrderDetailDao() throws SQLException {
		if (orderDetailDao == null) {
			orderDetailDao = getDao(OrderDetail.class);
		}
		return orderDetailDao;
	}
	public Dao<Stock, Integer> getStockDao() throws SQLException {
		if (stockDao == null) {
			stockDao = getDao(Stock.class);
		}
		return stockDao;
	}
	public Dao<KunnrStockDate, Integer> getKunnrStockDateDao() throws SQLException {
		if (kunnrStockDateDao == null) {
			kunnrStockDateDao = getDao(KunnrStockDate.class);
		}
		return kunnrStockDateDao;
	}
	public Dao<OrderPrintFormat, Integer> getOrderPrintFormatDao() throws SQLException {
		if (orderPrintFormatDao == null) {
			orderPrintFormatDao = getDao(OrderPrintFormat.class);
		}
		return orderPrintFormatDao;
	}
	public Dao<OldOrder, Integer> getOldOrderDao() throws SQLException {
		if (oldorderDao == null) {
			oldorderDao = getDao(OldOrder.class);
		}
		return oldorderDao;
	}
	public Dao<OldOrderDetail, Integer> getOldOrderDetailDao() throws SQLException {
		if (oldorderDetailDao == null) {
			oldorderDetailDao = getDao(OldOrderDetail.class);
		}
		return oldorderDetailDao;
	}
	public Dao<CuanhuoQuery, Integer> getCuanhuoQueryDao() throws SQLException {
		if (CuanhuoQueryDao == null) {
			CuanhuoQueryDao = getDao(CuanhuoQuery.class);
		}
		return CuanhuoQueryDao;
	}
	public Dao<Sign, Integer> getSignDao() throws SQLException {
		if (signDao == null) {
			signDao = getDao(Sign.class);
		}
		return signDao;
	}
	public Dao<SkuUnit, Integer> getSkuUnitDao() throws SQLException {
		if (SkuUnitDao == null) {
			SkuUnitDao = getDao(SkuUnit.class);
		}
		return SkuUnitDao;
	}
	public Dao<StockReport, Integer> getStockReportDao() throws SQLException {
		if (StockReportDao == null) {
			StockReportDao = getDao(StockReport.class);
		}
		return StockReportDao;
	}
}