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
 * @Title: app�����ȡ����֮�����ӿ� Interface for Data Providers 
 * @Description:XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016��11��9�� ����9:37:44
 */
public interface IDataProvider {
	/** start data upate tasks */
	public void startDataUpdateTasks(Activity activity);

	/** ��½ **/
	public String getTime();

	/** ��½ **/
	public int login(String password);

	/** ��ȡsku�б� **/
	public boolean getSKUList();

	/** ��ȡ�ŵ��б� */
	public boolean getCustomerList();

	/** ��ȡ�г�� */
	public boolean getActivityList();

	/** ��ȡ���� */
	public boolean getChannelList();

	/** ��ȡ�˵� */
	public boolean getMenu();

	/** ��ȡָ�� */
	public boolean getDictionary();

	/** ��ȡ�ŵ���Ϣ */
	public List<Customer> getCustomer(String par);

	/** ��ȡ�ŵ���Ϣ */
	public List<MarketCheck> getMarketActivity(String custId);

	/**
	 * ��ȡ�ŵ����������̵�SKU��Ϣ
	 * 
	 * @param string2
	 * @param custId
	 * @param string
	 */
	public List<Product> getSkuByCloud(String Kunnr, String custId,
			String userId);

	/** ��ȡ�汾��Ϣ */
	public BaseDictionary getVersion();

	/** �����ϴ��ӿ� */
	public boolean uploadOrder(Order order);

	/** ��Ƭ�ϴ��ӿ� */
	public boolean uploadPicture(PhotoInfo photoInfo);

	/** �쳣�۸��ϴ��ӿ� */
	public boolean uploadAbnormalPrice(AbnormalPrice abnormalPrice);

	/** �̻������ϴ��ӿ� */
	public boolean uploadDistribution(Distribution distribution);

	/** ������Ϣ�ϴ��ӿ� */
	public boolean uploadDisplay(DisPlay disPlay);

	/** ������Ϣ�ϴ� */
	public boolean uploadInventory(Inventory inventory);

	/** �����û� */
	public String createCustomer(Customer customer);

	/** ��ӿ��� */
	public Sign createSign(Sign sign);

	/** ��ȡ���� */
	public Sign getSignList(Sign sign);

	/** ���������Ϣ�ϴ� */
	public String uploadsupervise(MarketCheck marketCheck, Context context);

	/** ��ѯ������ */
	public List<BaseDivision> searchDivision(String division);

	/** ��ѯ������ */
	public List<Customer> searchKunnr(String kunnr);

	/** ��ѯ��· */
	public boolean getRoute();

	/**
	 * ��½��Ϣ��¼
	 * 
	 * @param online
	 *            /offline
	 * */
	public boolean uploadLoginLog(String status);

	/** ��ѯ������ */
	public boolean getKunnrByJL();

	/** �ϴ������Ϣ */
	public String uploadKunnrStock(List<Stock> list, String type);

	/** ��ȡ�����Ϣ */
	public boolean getKunnrStock(Stock stock);

	/**
	 * MethodsTitle: ��ȡ���������Լ�����ֻ��Ŀ�����
	 * 
	 * @author: xg.chen
	 * @date:2016��11��30�� ����11:18:46
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
	 * ��ȡ���ھ�γ��2��˾���ڵ��ŵ���Ϣ ������locDw:DW lon ���� lat γ��
	 * */
	public List<Customer> getCustomerDw(String locDw, String lon, String lat);

	/**
	 * ��ȡsku���һ���ᱨ�۸񣨾�����+�ŵ�+�ᱨ�ˣ�
	 * 
	 * @param kunnr
	 * @param userId
	 * @param custId
	 * @return
	 */
	public List<Product> getSkuLastPrice(String kunnr, String userId,
			String custId);

	/**
	 * ��ȡ�������Զ����ӡ��ʽ
	 * */
	public boolean getKunnrOrderFormat(String kunnr);

	/** ͬ������ */
	public boolean getOrderDownload(String custId);

	boolean getWeekOrderDownload(String custId, String firstday, String lastday);

	public List<CuanhuoQuery> searchCuanhuo(String chuanhaofactory,
			String chuanhaodate, String chuanhaosku, String chuanhaodamacode);

	/** �ŵ������sku�� **/
	public boolean getSkuUnit();

	public boolean uploadCustomerStock(StockReport stockReport);

	public List<StockReport> getCustomerStockByCustid(String custid);;
}
