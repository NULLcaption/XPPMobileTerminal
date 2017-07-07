package com.xpp.moblie.provider;

import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.xpp.moblie.entity.BaseDictionary;
import com.xpp.moblie.entity.BaseDivision;
import com.xpp.moblie.entity.BaseKPI;
import com.xpp.moblie.entity.BaseKunnrStockDate;
import com.xpp.moblie.entity.Basekpis;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.CuanhuoQuery;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.Order;
import com.xpp.moblie.query.OrderDetail;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.query.Sign;
import com.xpp.moblie.query.Stock;
import com.xpp.moblie.query.StockReport;
import com.xpp.moblie.query.VistCust;

/**
 * @Title: app服务获取数据之公共接口 Interface for Data Providers 
 * @Description:XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016年11月9日 上午9:37:44
 */
public interface IDataProvider {
	/** start data upate tasks */
	public void startDataUpdateTasks(Activity activity);

	/** 登陆 **/
	public String getTime();

	/** 登陆 **/
	public int login(String password);

	/** 获取sku列表 **/
	public boolean getSKUList();

	/** 获取门店列表 */
	public boolean getCustomerList();

	/** 获取市场活动 */
	public boolean getActivityList();

	/** 获取渠道 */
	public boolean getChannelList();

	/** 获取菜单 */
	public boolean getMenu();

	/** 获取指标 */
	public boolean getDictionary();

	/** 获取门店信息 */
	public List<Customer> getCustomer(String par);

	/** 获取门店活动信息 */
	public List<MarketCheck> getMarketActivity(String custId);

	/**
	 * 获取门店所属经销商的SKU信息
	 * 
	 * @param string2
	 * @param custId
	 * @param string
	 */
	public List<Product> getSkuByCloud(String Kunnr, String custId,
			String userId);

	/** 获取版本信息 */
	public BaseDictionary getVersion();

	/** 订单上传接口 */
	public boolean uploadOrder(Order order);

	/** 照片上传接口 */
	public boolean uploadPicture(PhotoInfo photoInfo);

	/** 异常价格上传接口 */
	public boolean uploadAbnormalPrice(AbnormalPrice abnormalPrice);

	/** 铺货管理上传接口 */
	public boolean uploadDistribution(Distribution distribution);

	/** 陈列信息上传接口 */
	public boolean uploadDisplay(DisPlay disPlay);

	/** 库龄信息上传 */
	public boolean uploadInventory(Inventory inventory);

	/** 创建用户 */
	public String createCustomer(Customer customer);

	/** 添加考勤 */
	public Sign createSign(Sign sign);

	/** 获取考勤 */
	public Sign getSignList(Sign sign);

	/** 督导检查信息上传 */
	public String uploadsupervise(MarketCheck marketCheck, Context context);

	/** 查询行政区 */
	public List<BaseDivision> searchDivision(String division);

	/** 查询经销商 */
	public List<Customer> searchKunnr(String kunnr);

	/** 查询线路 */
	public boolean getRoute();

	/**
	 * 登陆信息记录
	 * 
	 * @param online
	 *            /offline
	 * */
	public boolean uploadLoginLog(String status);

	/** 查询经销商 */
	public boolean getKunnrByJL();

	/** 上传库存信息 */
	public String uploadKunnrStock(List<Stock> list, String type);

	/** 获取库存信息 */
	public boolean getKunnrStock(Stock stock);

	/**
	 * MethodsTitle: 获取主经销商以及下面分户的库存汇总
	 * 
	 * @author: xg.chen
	 * @date:2016年11月30日 上午11:18:46
	 * @version 1.0
	 * @param stock
	 * @return
	 */
	public String getSuperKunnrStockInfo(Stock stock);

	public boolean getKunnrStockDate();

	public List<Basekpis> getKPI();

	public List<VistCust> getVistCust();

	public List<BaseKPI> getKPIVisit();

	public List<BaseKPI> getJXSCKKPI(String custId);

	public List<BaseKPI> getJXSFXKPI(String custId);

	public boolean getUserType();

	/**
	 * 获取所在经纬度2公司以内的门店信息 参数：locDw:DW lon 经度 lat 纬度
	 * */
	public List<Customer> getCustomerDw(String locDw, String lon, String lat);

	/**
	 * 获取sku最后一次提报价格（经销商+门店+提报人）
	 * 
	 * @param kunnr
	 * @param userId
	 * @param custId
	 * @return
	 */
	public List<Product> getSkuLastPrice(String kunnr, String userId,
			String custId);

	/**
	 * 获取经销商自定义打印格式
	 * */
	public boolean getKunnrOrderFormat(String kunnr);

	/** 同步订单 */
	public boolean getOrderDownload(String custId);

	boolean getWeekOrderDownload(String custId, String firstday, String lastday);

	public List<CuanhuoQuery> searchCuanhuo(String chuanhaofactory,
			String chuanhaodate, String chuanhaosku, String chuanhaodamacode);

	/** 门店分销量sku表 **/
	public boolean getSkuUnit();

	public boolean uploadCustomerStock(StockReport stockReport);

	public List<StockReport> getCustomerStockByCustid(String custid);;
}
