package com.xpp.moblie.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseDictionary;
import com.xpp.moblie.entity.BaseDivision;
import com.xpp.moblie.entity.BaseKPI;
import com.xpp.moblie.entity.Basekpis;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Channel;
import com.xpp.moblie.query.CuanhuoQuery;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.KunnrStockDate;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.Menu;
import com.xpp.moblie.query.OldOrder;
import com.xpp.moblie.query.OldOrderDetail;
import com.xpp.moblie.query.Order;
import com.xpp.moblie.query.OrderDetail;
import com.xpp.moblie.query.OrderPrintFormat;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.query.Sign;
import com.xpp.moblie.query.SkuUnit;
import com.xpp.moblie.query.Stock;
import com.xpp.moblie.query.StockReport;
import com.xpp.moblie.query.UserInfo;
import com.xpp.moblie.query.VistCust;
import com.xpp.moblie.util.DataHandleUtil;
import com.xpp.moblie.util.EncryptUtil;
import com.xpp.moblie.util.Helpers;
import com.xpp.moblie.util.HttpUtil;
import com.xpp.moblie.util.PhotoUtil;
import com.xpp.moblie.util.PinyinSort;
import com.xpp.moblie.util.ReadPhoneStateUtil;
import com.xpp.moblie.util.UploadUtil;
import com.xpp.moblie.util.ResultMessage;

public class WebService implements IDataProvider {
	private static WebService instance;
	private static final String TAG = "WebService";
	/** app服务地址(常用测试地址：本地，测试机，正式机) **/
//	private static String OPENAPIURL = "http://exptest.zjxpp.com:7186";//测试机
	private static String OPENAPIURL = "http://10.3.2.35:7186";// 本机
//	private static String OPENAPIURL = "http://exp.zjxpp.com:8105";//apn 生产机
	/** app服务接口 **/
	private static final String GET_PERFORMANCE_URL = "/mobilePlatform/router/login";
	private static final String TIME = "/time";
	private static final String LOGIN = "/login";
	private static final String SKULIST = "/sku";
	private static final String GETORDERSKU = "/getOrderSkuByKunner";
	private static final String GETSKULASTPRICE = "/getSkuLastPrice";
	private static final String CUSTOMER = "/customer";
	private static final String MARKETACTIITY = "/activity";
	private static final String DICTIONARY = "/dict";
	private static final String PHOTOUPLOAD = "/photo";
	private static final String ABNORMALPRICE = "/price";
	private static final String DISTRIBUTION = "/distr";
	private static final String DISPLAY = "/display";
	private static final String STOCKAGE = "/stockage";
	private static final String CHANNEL = "/channel";
	private static final String SEARCHCUSTOMER = "/searchCustomerXpp";
	private static final String SEARCHACTIVITY = "/searchActivity";
	private static final String CREATECUSTOMER = "/createCust";
	private static final String SUPERVISE = "/supervise";
	private static final String SEARCHDIVISION = "/searchDivision";
	private static final String SEARCHKUNNR = "/searchKunnr";
	private static final String VERSION = "/version";
	private static final String MENU = "/menu";
	private static final String SERRCHROUTE = "/searchRoute";
	private static final String ORDERUPLOAD = "/orderUpload";
	private static final String UPLOADLOGINLOG = "/uploadLoginLog";
	private static final String GETKUNNRBYJL = "/getKunnrByJL";
	private static final String GETKUNNRSTOCKDATE = "/getKunnrStockDate";
	private static final String UPLOADKUNNRSTOCKNEW = "/uploadkunnrStockNew";
	private static final String GETKUNNRSTOCK = "/getKunnrStock";
	private static final String GETSUPERKUNNRINFO = "/getSuperKunnrInfo";
	private static final String GETUSERTYPE = "/checkKpiMenu";
	private static final String GETKPI = "/getKpiBo";
	private static final String GETKPIV = "/getKpiVisit";
	private static final String GETCKKPI = "/getKpiBo";
	private static final String GETFXKPI = "/getKpiBo";
	private static final String ORDERDOWNLOAD = "/orderDownload";
	private static final String SERACHCUANHUO = "/searchCuanhuo";
	private static final String CERATESIGN = "/createSign";
	private static final String GETSIGNLIST = "/getSignList";
	private static final String GETVISTCUST = "/getVistCust";
	private static final String GETSKUUNIT = "/getSkuUnit";
	private static final String UPLOADCUSTOMERSTOCK = "/uploadCustomerStock";
	private static final String GETCUSTOMERSTOCKBYCUSTID = "/getCustomerStockByCustid";
	// 经销商自定义打印格式字段获取
	private static final String JXSPRINTFORMAT = "/getKunnrOrderFormat";
	private static final String GETWEEKORDERTOTAL = "/getWeekOrderTotal";

	private WebService() {
		super();
	}

	public static IDataProvider getInstance() {
		if (instance == null)
			instance = new WebService();
		return instance;
	}

	@Override
	public void startDataUpdateTasks(Activity activity) {
		Context ctx = DataProviderFactory.getContext();
		if (ctx != null) {
			SharedPreferences sp = ctx.getSharedPreferences("XPPWebService",
					Context.MODE_PRIVATE);

			if (sp.contains("lastUpdate")) {
				Date now = new Date();
				String str1 = sp.getString("lastUpdate", "");
				String str2 = Helpers.getDateStrWithoutTime(now);
				if (str1.startsWith(str2)) {
					Log.d(TAG, "No updates needed at this time.");
					return;
				}
			}

			if (UpdateTask.getInstance().getStatus() != AsyncTask.Status.RUNNING) {
				sp.edit().putInt("lastUpdatedShopSequence", -1).commit();
				new UpdateTask(activity, false).execute();
			}
		}

	}

	/**
	 * 获取服务器时间
	 */
	@Override
	public String getTime() {
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ TIME, null);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	//获取版本号
	public BaseDictionary getVersion() {
		BaseDictionary bd = new BaseDictionary();
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ VERSION, null);
			if (result == null
					|| XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)) {
				return bd;
			}
			Gson gson = new Gson();
			List<BaseDictionary> list = gson.fromJson(result,
					new TypeToken<List<BaseDictionary>>() {
					}.getType());
			if (list == null || list.size() == 0) {
				return null;
			}
			bd = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return bd;
	}

	// 登陆
	public int login(String password) {
		Map<String, String> params = new HashMap<String, String>();
		String chkpsw = DataProviderFactory.getRemberpsd();
		params.put("mobile", DataProviderFactory.getLoginName());
		params.put("password", password);
		try {
			//post request for open API url
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ LOGIN, params);
			//for result judgement
			if (result != null) {
				if (XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)) {// 连接超时
					UserInfo info1 = UserInfo
							.findByLoginName(DataProviderFactory.getLoginName());
					if (info1 != null) {// 离线登陆
						if (info1.getPassword().equals(
								EncryptUtil.md5Encry(password))) {
							return XPPApplication.OFFLINE_LOADED;
						}
					} else {
						return XPPApplication.FAIL_CONNECT_SERVER;
					}
				}
				Gson gson = new Gson();
				UserInfo userInfo = gson.fromJson(result, UserInfo.class);
				if (Integer.valueOf(userInfo.getStatus()) == XPPApplication.SUCCESS) {
					if ("Y".equals(chkpsw)) {
						userInfo.setRemberpsd(chkpsw);
						userInfo.setLocalpassword(password);
						DataProviderFactory.setLocalPassword(password);
						DataProviderFactory.setRemberpsd(chkpsw);
					}
					userInfo.save();
					DataProviderFactory.setUserId(userInfo.getUserId());
					DataProviderFactory.setRoleId(userInfo.getRoleId());
					DataProviderFactory.setOrgId(userInfo.getOrgId());
					DataProviderFactory.setCouldId(userInfo.getCouldId());
					uploadLoginLog("online");
				} else if (Integer.valueOf(userInfo.getStatus()) == XPPApplication.ERR_PASSWORD) {
					return XPPApplication.ERR_PASSWORD;
				} else if (Integer.valueOf(userInfo.getStatus()) == XPPApplication.NO_USER) {
					return XPPApplication.NO_USER;
				} else if (Integer.valueOf(userInfo.getStatus()) == XPPApplication.ERR_ROLE) {
					return XPPApplication.ERR_ROLE;
				} else if (Integer.valueOf(userInfo.getStatus()) == XPPApplication.NO_MOBILE) {
					return XPPApplication.NO_MOBILE;
				} else if (Integer.valueOf(userInfo.getStatus()) == XPPApplication.NOTBUSINESSPHONE) {
					return XPPApplication.NOTBUSINESSPHONE;
				} else {
					UserInfo info = UserInfo
							.findByLoginName(DataProviderFactory.getLoginName());
					if (info != null) {
						if (info.getPassword().equals(
								EncryptUtil.md5Encry(password))) {
							return XPPApplication.OFFLINE_LOADED;
						}
						return XPPApplication.OFFLINE_ERROR_PASSWORD;
					}
					return Integer.valueOf(userInfo.getStatus());
				}
			} else {// 网络不通
				return XPPApplication.NO_NETWORK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return XPPApplication.FAIL;
		}
		// 验证版本
		BaseDictionary bd = DataProviderFactory.getProvider().getVersion();
		if (bd != null) {
			if (!bd.getItemName().equals(
					XPPApplication.getVersionName(DataProviderFactory
							.getContext()))) {
				SharedPreferences settings = DataProviderFactory
						.getContext()
						.getSharedPreferences("PrefsFile", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("version", bd.getDictTypeValue());
				editor.commit();
				return XPPApplication.UPDATE_VERSION;
			}
		}
		return XPPApplication.SUCCESS;
	}

	@Override
	public boolean getSKUList() {
		try {
			// Map<String, String> params = new HashMap<String, String>();
			// params.put("cloudId", DataProviderFactory.getCouldId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SKULIST, null);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				Gson gson = new Gson();
				List<Product> skuList = gson.fromJson(result,
						new TypeToken<List<Product>>() {
						}.getType());
				Product.deleteAll();
				for (Product product : skuList) {
					product.setLastPrice("0");
					product.setStatus("N");
					if (product.getCategoryDesc() != null
							&& !"".equals(product.getCategoryDesc().trim())) {
						product.setPinyinsearchkey(PinyinSort.getPinYin(product
								.getCategoryDesc()));
					}
					product.save();
					// System.out.println(product);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getCustomerList() {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", DataProviderFactory.getUserId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ CUSTOMER, params);
			if (result != null) {
				Gson gson = new Gson();
				List<Customer> customerList = gson.fromJson(result,
						new TypeToken<List<Customer>>() {
						}.getType());
				for (Customer customer : customerList) {
					// customer.setIsVisit("1");
					customer.setUserId(DataProviderFactory.getUserId());
					customer.save();
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getActivityList() {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", DataProviderFactory.getUserId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ MARKETACTIITY, params);
			if (result != null) {
				Gson gson = new Gson();
				List<MarketCheck> marketCheckList = gson.fromJson(result,
						new TypeToken<List<MarketCheck>>() {
						}.getType());
				for (MarketCheck marketCheck : marketCheckList) {
					marketCheck.save();
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getDictionary() {
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ DICTIONARY, null);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				Gson gson = new Gson();
				List<Dictionary> dictionaryList = gson.fromJson(result,
						new TypeToken<List<Dictionary>>() {
						}.getType());
				Dictionary.deleteAll();
				for (Dictionary dictionary : dictionaryList) {
					dictionary.save();
				}
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean getChannelList() {
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ CHANNEL, null);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				Gson gson = new Gson();
				List<Channel> channelList = gson.fromJson(result,
						new TypeToken<List<Channel>>() {
						}.getType());
				Channel.deleteAll();
				for (Channel channel : channelList) {
					channel.save();
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getMenu() {
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ MENU, null);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				Gson gson = new Gson();
				List<Menu> menuList = gson.fromJson(result,
						new TypeToken<List<Menu>>() {
						}.getType());
				Menu.deleteAll();
				for (Menu menu : menuList) {
					menu.save();
				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取客户
	 */
	@Override
	public List<Customer> getCustomer(String par) {
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			Map<String, String> params = new HashMap<String, String>();
			if (!"".equals(par)) {
				params.put("info", par);
			}
			params.put("roleId", DataProviderFactory.getRoleId());
			params.put("userId", DataProviderFactory.getUserId());
			Context ctx = DataProviderFactory.getContext();
			SharedPreferences settings = ctx.getSharedPreferences(
					DataProviderFactory.PREFS_NAME, Context.MODE_PRIVATE);
			String zwl04, kunnr;
			zwl04 = settings.getString(DataProviderFactory.PREFS_ZW1041, null);
			kunnr = settings.getString(
					DataProviderFactory.PREFS_DEFAULT_KUNNR1, null);
			params.put("zwl04", zwl04);
			params.put("kunnr", kunnr);
			if (zwl04 != null || kunnr != null) {
				params.put("setflag", "Y");
			}

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SEARCHCUSTOMER, params);
			if (result != null) {
				if (result.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
					Customer cu = new Customer();
					cu.setCustId(result);
					customerList.add(cu);
					return customerList;
				}
				Gson gson = new Gson(); 
				customerList = gson.fromJson(result,
						new TypeToken<List<Customer>>() {
						}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public List<Customer> getCustomerDw(String locDw, String lon, String lat) {
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("info", "");
			params.put("roleId", DataProviderFactory.getRoleId());
			params.put("userId", DataProviderFactory.getUserId());
			params.put("locDw", locDw);
			params.put("longitude", lon);
			params.put("latitude", lat);
			Context ctx = DataProviderFactory.getContext();
			SharedPreferences settings = ctx.getSharedPreferences(
					DataProviderFactory.PREFS_NAME, Context.MODE_PRIVATE);
			String zwl04, kunnr;
			zwl04 = settings.getString(DataProviderFactory.PREFS_ZW1041, null);
			kunnr = settings.getString(
					DataProviderFactory.PREFS_DEFAULT_KUNNR1, null);
			params.put("zwl04", zwl04);
			params.put("kunnr", kunnr);
			if (zwl04 != null || kunnr != null) {
				params.put("setflag", "Y");
			}

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SEARCHCUSTOMER, params);
			if (result != null) {
				if (result.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
					Customer cu = new Customer();
					cu.setCustId(result);
					customerList.add(cu);
					return customerList;
				}
				Gson gson = new Gson();
				customerList = gson.fromJson(result,
						new TypeToken<List<Customer>>() {
						}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}

	/**
	 * 获取门店活动信息
	 */
	@Override
	public List<MarketCheck> getMarketActivity(String custId) {
		List<MarketCheck> marketCheckList = new ArrayList<MarketCheck>();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", custId);
			params.put("userId", DataProviderFactory.getUserId());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SEARCHACTIVITY, params);
			if (result != null) {
				Gson gson = new Gson();
				marketCheckList = gson.fromJson(result,
						new TypeToken<List<MarketCheck>>() {
						}.getType());
				// MarketCheck.deleteAll(MarketCheck.findAll());
				for (MarketCheck marketCheck : marketCheckList) {
					marketCheck.setId(marketCheck.getCustId()
							+ marketCheck.getMarketDetailId()
							+ marketCheck.getItemId());
					marketCheck.setEmpId(DataProviderFactory.getUserId());
					// System.out.println("user id"+marketCheck.getEmpId());
					marketCheck.setDayType(DataProviderFactory.getDayType());
					marketCheck.save();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return marketCheckList;
	}

	@Override
	public List<BaseDivision> searchDivision(String division) {
		List<BaseDivision> divisionList = new ArrayList<BaseDivision>();

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("zwl04t", division);
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SEARCHDIVISION, params);
			if (result != null) {
				if (result.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
					BaseDivision cu = new BaseDivision();
					cu.setDiviName(result);
					divisionList.add(cu);
					return divisionList;
				}
				Gson gson = new Gson();
				divisionList = gson.fromJson(result,
						new TypeToken<List<BaseDivision>>() {
						}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return divisionList;
	}

	/**
	 * 上传服务端照片
	 */
	@Override
	public boolean uploadPicture(PhotoInfo photoInfo) {
		try {
			String s = UploadUtil.getPhotoParams(DataProviderFactory.getDirName
					+ photoInfo.getPhotoName() + ".jpg");
			if (s == null) {
				return false;
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("photo", s);
			params.put("photoType", photoInfo.getPtype().toString());
			params.put("custId", photoInfo.getCustid());
			params.put("userId", photoInfo.getEmplid());
			params.put("dayType", photoInfo.getDayType());
			params.put("activityId", photoInfo.getMIADetailId());
			params.put("itemId", photoInfo.getMIAItemId());
			params.put("pzTime", PhotoUtil.getpicTime(photoInfo.getPhotoName()));

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ PHOTOUPLOAD, params);
			if (result != null) {
				if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
					photoInfo.setStatus(Status.FINISHED);
					photoInfo.update();
				} else {
					return false;
				}
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean uploadAbnormalPrice(AbnormalPrice abnormalPrice) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", abnormalPrice.getCustId());
			params.put("skuId", abnormalPrice.getCategoryId());
			params.put("price", abnormalPrice.getAbnormalPrice());
			params.put("unit", abnormalPrice.getUnit());
			params.put("userId", abnormalPrice.getUserId());
			params.put("status", abnormalPrice.getIs());
			params.put("createDate", abnormalPrice.getDayType());
			params.put("standardprice", abnormalPrice.getStandardPrice());
			params.put("promotionalprice", abnormalPrice.getPromotionalPrice());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ ABNORMALPRICE, params);
			if (result == null
					|| XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)) {
				return false;
			} else {
				if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
					abnormalPrice.setStatus(Status.FINISHED);
					abnormalPrice.update();
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean uploadDistribution(Distribution distribution) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", distribution.getCustId());
			params.put("skuId", distribution.getSkuId());
			params.put("userId", distribution.getUserId());
			params.put("quantity", "");
			params.put("createDate", distribution.getDayType());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ DISTRIBUTION, params);
			if (result == null) {
				return false;
			} else {
				if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
					distribution.setStatu(Status.FINISHED);
					distribution.update();
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean uploadDisplay(DisPlay disPlay) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", disPlay.getCustId());
			params.put("userId", disPlay.getUserId());
			params.put("dictTypeValue", disPlay.getDictTypeValue());
			params.put("itemValue", disPlay.getItemValue());
			params.put("itemDesc", disPlay.getItemDesc());
			params.put("counts", disPlay.getCounts());
			params.put("createDate", disPlay.getDayType());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ DISPLAY, params);
			if (result == null) {
				return false;
			} else {
				if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
					disPlay.setStatus(Status.FINISHED);
					disPlay.update();
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean uploadInventory(Inventory inventory) {

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", inventory.getCustId());
			params.put("userId", inventory.getUserId());
			params.put("createDate", inventory.getDayType());
			params.put("skuId", inventory.getCategorySortId());
			// params.put("startDay", inventory.getBatch());
			// params.put("endDay", inventory.getEndBatch());
			params.put("year", inventory.getYear());
			params.put("month", inventory.getMonth());
			params.put("quantity", inventory.getQuantity());
			params.put("unitDesc", inventory.getUnit());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ STOCKAGE, params);
			if (result == null) {
				return false;
			} else {
				if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
					inventory.setStatus(Status.FINISHED);
					inventory.update();
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * MethodsTitle: 门店用户创建
	 * 
	 * @author: xg.chen
	 * @date:2016年10月21日
	 * @param customer
	 * @return
	 */
	@Override
	public String createCustomer(Customer customer) {
		String result = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", customer.getCustId());
			params.put("custName", customer.getCustName());
			params.put("address", customer.getAddress());
			params.put("channelId", customer.getChannelId());
			params.put("userId", DataProviderFactory.getUserId());
			params.put("contractName", customer.getContractName());
			params.put("contractPhone", customer.getContractPhone());
			params.put("contractMobile", customer.getContractMobile());
			params.put("zwl04", customer.getZwl04());
			params.put("businessLicense", customer.getBusinessLicense());
			params.put("kunnr", customer.getKunnr());
			params.put("longitude", customer.getLongitude());// 经度
			params.put("latitude", customer.getLatitude());// 纬度
			params.put("customerImportance", customer.getCustomerImportance());// 门店重要度
			params.put("customerAnnualSale", customer.getCustomerAnnualSale());// 门店销额
			result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ CREATECUSTOMER, params);
			if (result.length() > 100) {// 返回报错信息处理
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public String uploadsupervise(MarketCheck marketCheck, Context context) {
		String result = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("marketDetailId", marketCheck.getMarketDetailId());
			params.put("hxProportion", marketCheck.getHxProportion());
			params.put("Execution", marketCheck.getExecution());
			params.put("dxExplanation", marketCheck.getDxExplanation());
			params.put("custId", marketCheck.getCustId());
			params.put("userId", DataProviderFactory.getUserId());
			params.put("itemId", marketCheck.getItemId());
			params.put("roleId", DataProviderFactory.getRoleId());
			params.put("checkPercentBus", marketCheck.getCheckPercentBus());
			params.put("reason", marketCheck.getReason());
			params.put("dayType", marketCheck.getDayType());
			result = HttpUtil.post(
					OPENAPIURL + GET_PERFORMANCE_URL + SUPERVISE, params);

			if (result.length() > 100) {// 返回报错信息处理
				return null;
			}

			if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
				marketCheck.setStatus(Status.FINISHED);
				marketCheck.update();
				if (marketCheck.update()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "Y");
					XPPApplication.sendChangeBroad(context,
							XPPApplication.REFRESH_MARKET_RECEIVER, map);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	/**
	 * 查询经销商
	 */
	@Override
	public List<Customer> searchKunnr(String kunnr) {
		List<Customer> kunnrList = new ArrayList<Customer>();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("info", kunnr);
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SEARCHKUNNR, params);
			if (result != null) {
				if (result.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
					Customer cu = new Customer();
					cu.setKunnr(result);
					kunnrList.add(cu);
					return kunnrList;
				}
				Gson gson = new Gson();
				kunnrList = gson.fromJson(result,
						new TypeToken<List<Customer>>() {
						}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return kunnrList;
	}

	@Override
	public boolean getRoute() {
		List<Customer> customerList = new ArrayList<Customer>();

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", DataProviderFactory.getUserId());
			params.put("roleId", DataProviderFactory.getRoleId());
			// System.out.println("roleId:"+DataProviderFactory.getRoleId());
			params.put("cloudId", DataProviderFactory.getCouldId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SERRCHROUTE, params);
			if (result != null
					&& !result
							.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
				Gson gson = new Gson();
				customerList = gson.fromJson(result,
						new TypeToken<List<Customer>>() {
						}.getType());
				// Log.i("lhp", ""+customerList);
				Customer.deleteAll(Customer.findRouteShop());
				for (Customer customer : customerList) {
					customer.setIsVisitShop("1");
					customer.setUserId(DataProviderFactory.getUserId());
					customer.save();
				}
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean uploadOrder(Order order) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", order.getCustId());
			params.put("userId", order.getUserId());
			params.put("orderId", order.getOrderId());
			params.put("totalPrice",
					DataHandleUtil.dealNull(order.getTotalPrice()));
			params.put("totalPricaeUniteCode", order.getTotalPricaeUniteCode());// order.getTotalPricaeUniteCode()
			params.put("totalPricaeUniteDesc", order.getTotalPricaeUniteDesc());// order.getTotalPricaeUniteDesc()
			params.put("orderDesc", order.getOrderDesc());
			params.put("orderQuntity",
					DataHandleUtil.dealNull(order.getOrderQuntity()));
			params.put("orgId", order.getOrgId());
			params.put("couldId", order.getCouldId());
			params.put("orderStatus", order.getOrderStatus());
			params.put("orderFundStatus", order.getOrderFundStatus());
			params.put("orderCreateDate", order.getOrderCreateDate().toString());
			params.put("orderDesc", order.getOrderDesc());
			List<OrderDetail> list = OrderDetail.findByCustId(
					order.getCustId(), order.getUserId(),
					(order.getOrderId() == null ? String.valueOf(order.getId())
							: order.getOrderId()));
			Gson gs = new Gson();
			params.put("orderDetail", gs.toJson(list));

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ ORDERUPLOAD, params);
			if (result == null
					|| XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)) {
				return false;
			} else {
				ResultMessage s = gs.fromJson(result, ResultMessage.class);
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", "notification");
				Customer cust = Customer.findByCustId(order.getCustId());
				if (XPPApplication.UPLOAD_SUCCESS.equals(s.getResultCode())) {
					map.put("status",
							(order.getOrderId() != null ? cust.getCustName()
									+ "订单修改成功,订单号:" + order.getOrderId() : cust
									.getCustName()
									+ "订单创建成功,订单号:"
									+ s.getResultDesc()));
					OrderDetail.updateOrderId(list, s.getResultDesc());
					order.setOrderId(s.getResultDesc());
					order.setStatus(Status.FINISHED);
					order.update();

				} else {
					map.put("status",
							(order.getOrderId() != null ? cust.getCustName()
									+ "订单修改失败,订单号:" + s.getResultDesc() : cust
									.getCustName() + "订单创建失败....."));
				}
				XPPApplication.sendChangeBroad(DataProviderFactory.ctx,
						XPPApplication.UPLOADDATA_RECEIVER, map);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 登陆信息
	 */
	@Override
	public boolean uploadLoginLog(String status) {
		ReadPhoneStateUtil ps = new ReadPhoneStateUtil(
				DataProviderFactory.getContext());
		System.out.println(ps.getHandSetInfo());
		Map<String, String> params = new HashMap<String, String>();
		if ("online".equals(status)) {// 登陆
			params.put("userId", DataProviderFactory.getUserId());
			params.put("imei", ps.getIMEI());
			params.put("imsi", ps.getIMSI());
			params.put("loginMobile", ps.getPhoneNumber());
			params.put("handSetInfo", ps.getHandSetInfo());
		} else {// 登出
			params.put("loginLogId", DataProviderFactory.getLoginLogId());
		}
		params.put("status", status);
		params.put("packageName", "superviseMoblie");
		params.put("version",
				XPPApplication.getVersionName(DataProviderFactory.getContext()));
		Gson gs = new Gson();
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ UPLOADLOGINLOG, params);
			if (result == null
					|| XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)) {
				return false;
			} else {
				ResultMessage s = gs.fromJson(result, ResultMessage.class);
				if (XPPApplication.UPLOAD_SUCCESS.equals(s.getResultCode())
						&& "online".equals(status)) {
					DataProviderFactory.setLoginLogId(s.getResultDesc());
				}
			}

		} catch (Exception e) {

		}
		return false;
	}

	@Override
	public boolean getKunnrByJL() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", DataProviderFactory.getUserId());
		params.put("roleId", DataProviderFactory.getRoleId());

		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETKUNNRBYJL, params);
			Gson gson = new Gson();
			List<Customer> customerList = gson.fromJson(result,
					new TypeToken<List<Customer>>() {
					}.getType());
			Customer.deleteAll(Customer.findKunnrCustomer());
			// 清空经销商打印格式表
			OrderPrintFormat.deleteAll();
			for (Customer customer : customerList) {
				customer.save();
				// 同步经销商订单打印格式
				getKunnrOrderFormat(customer.getCustId());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 上传库存信息
	 */
	@Override
	public String uploadKunnrStock(List<Stock> list, String type) {
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		try {
			for (Stock stock : list) {
				params.put("kunnrStock", gson.toJson(stock));
				params.put("type", type);
				params.put("roleId", DataProviderFactory.getRoleId());
				String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
						+ UPLOADKUNNRSTOCKNEW, params);
				if (result != null
						&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
								.equals(result)) {
					ResultMessage s = gson
							.fromJson(result, ResultMessage.class);
					if (XPPApplication.UPLOAD_SUCCESS.equals(s.getResultCode())) {
						if (type == null) {
							stock.setId(Long.valueOf(s.getResultDesc()));
							stock.setStatus(Status.FINISHED);
							stock.save();
						}
					} else if (XPPApplication.UPLOAD_SAME.equals(s
							.getResultCode())) {// 突然关了库存上报。。。
						return "库存上报已关闭，不能提交";
					} else if (XPPApplication.UPLOAD_TIMEOUT.equals(s
							.getResultCode())) {// 超出提报时间或者已存在重复提交的品项
						return s.getResultDesc();
					} else {
						// if ("D".equals(type)) {
						// System.out.println(stock);
						// stock.setId(Long.valueOf(s.getResultDesc()));
						// stock.setStatus(Status.FAIL);
						// stock.delete();
						// return XPPApplication.UPLOAD_SUCCESS;
						// }
						// System.out.println("stock.getCategoryDesc() +s.getResultDesc():"+s.getResultDesc());
						// System.out.println("type:"+type);
						return stock.getCategoryDesc() + s.getResultDesc();
					}
				} else {
					return "网络异常,上传失败";
				}
			}
			System.out.println("上传成功");
			return XPPApplication.UPLOAD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XPPApplication.UPLOAD_FAIL;
	}

	@Override
	public boolean getKunnrStockDate() {
		List<KunnrStockDate> kunnrStockDateList = new ArrayList<KunnrStockDate>();
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		params.put("state", "1");
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETKUNNRSTOCKDATE, params);
			// System.out.println("----"+result );

			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				kunnrStockDateList = gson.fromJson(result,
						new TypeToken<List<KunnrStockDate>>() {
						}.getType());
				KunnrStockDate.deleteAll();
				for (KunnrStockDate kunnrStockDate : kunnrStockDateList) {
					kunnrStockDate.save();
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean getKunnrStock(Stock stock) {
		List<Stock> kunnrStockList = new ArrayList<Stock>();
		List<Stock> localStockList = new ArrayList<Stock>();

		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		params.put("kunnrStock", gson.toJson(stock));

		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETKUNNRSTOCK, params);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				kunnrStockList = gson.fromJson(result,
						new TypeToken<List<Stock>>() {
						}.getType());
				localStockList = Stock.findAll();
				for (Stock stock2 : kunnrStockList) {
					// System.out.println(stock2);
					Stock s = Stock.findById(stock2.getId());
					if (s != null) {
						s.delete();
					}
					stock2.setStatus(Status.FINISHED);
					stock2.save();
				}

				// System.out.println("打印非空ID:");
				for (Stock stock3 : localStockList) {
					if (stock3.getId() != null) {
						int flag = 0;
						for (Stock stock4 : kunnrStockList) {
							if (stock3.getId() == stock4.getId()) {
								flag = 1;
								break;
							}
						}
						if (flag == 0) {
							stock3.delete();
						}
					}
				}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public List<Product> getSkuByCloud(String Kunnr, String custId,
			String userId) {
		// List<Product> products=new ArrayList<Product>();
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		// params.put("custId", custId);
		params.put("cloudId", Kunnr);
		params.put("custId", custId);
		params.put("userId", userId);

		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETORDERSKU, params);
			// System.out.println("打印99999999"+result);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				final List<Product> products = gson.fromJson(result,
						new TypeToken<List<Product>>() {
						}.getType());
				new Thread() {
					@Override
					public void run() {
						for (Product product : products) {
							if (!"0000000000".equals(product.getCloudId())) {
								// System.out.println("打印product"+product);
								if (product.getCategoryDesc() != null
										&& !"".equals(product.getCategoryDesc()
												.trim())) {
									product.setPinyinsearchkey(PinyinSort
											.getPinYin(product
													.getCategoryDesc()));
								}
								product.save();
							}
						}
					}
				}.start();
				return products;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Basekpis> getKPI() {
		List<Basekpis> List = new ArrayList<Basekpis>();
		Gson gson = new Gson();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", DataProviderFactory.getUserId());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETKPI, params);
			List = gson.fromJson(result, new TypeToken<List<Basekpis>>() {
			}.getType());
			// System.out.println("----"+result+" size = "+List.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
	}

	@Override
	public List<VistCust> getVistCust() {
		List<VistCust> List = new ArrayList<VistCust>();
		Gson gson = new Gson();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("operator_id", DataProviderFactory.getUserId());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETVISTCUST, params);
			List = gson.fromJson(result, new TypeToken<List<VistCust>>() {
			}.getType());
			// System.out.println("----"+result+" size = "+List.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List;
	}

	/*
	 * @Override public List<BaseKPI> getFXKPI() { List<BaseKPI> KPIList = new
	 * ArrayList<BaseKPI>(); List<Basekpis> List = new ArrayList<Basekpis>();
	 * Gson gson = new Gson(); BaseKPI kpi; try { Map<String, String> params =
	 * new HashMap<String, String>(); params.put("userId",
	 * DataProviderFactory.getUserId());
	 * 
	 * String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL + GETKPI,
	 * params); List = gson.fromJson(result,new TypeToken<List<Basekpis>>()
	 * {}.getType()); //
	 * System.out.println("----"+result+" custId = "+List.size());
	 * 
	 * for(Basekpis k:List){ kpi = new BaseKPI(); kpi.setArea(k.getOrgName());
	 * kpi.setAreaComp(k.getFx_actual()); kpi.setAreaDest(k.getFx_target());
	 * kpi.setAreaRate(k.getFx_rate()); KPIList.add(kpi); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return KPIList; }
	 */

	@Override
	public List<BaseKPI> getJXSCKKPI(String custId) {
		List<BaseKPI> KPIList = new ArrayList<BaseKPI>();
		List<Basekpis> List = new ArrayList<Basekpis>();
		Gson gson = new Gson();
		BaseKPI kpi;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("kunnrId", custId);
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETCKKPI, params);
			List = gson.fromJson(result, new TypeToken<List<Basekpis>>() {
			}.getType());

			// System.out.println("----"+result+" custId = "+List.size());

			for (Basekpis k : List) {
				kpi = new BaseKPI();
				kpi.setArea(k.getOrgName());
				kpi.setAreaComp(k.getCk_actual());
				kpi.setAreaDest(k.getCk_target());
				kpi.setAreaRate(k.getCk_rate());
				KPIList.add(kpi);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return KPIList;
	}

	@Override
	public List<BaseKPI> getJXSFXKPI(String custId) {
		List<BaseKPI> KPIList = new ArrayList<BaseKPI>();
		List<Basekpis> List = new ArrayList<Basekpis>();
		Gson gson = new Gson();
		BaseKPI kpi;
		Map<String, String> params = new HashMap<String, String>();
		try {

			params.put("kunnrId", custId);
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETFXKPI, params);

			List = gson.fromJson(result, new TypeToken<List<Basekpis>>() {
			}.getType());
			// System.out.println("----"+result+" custId = "+List.size());

			for (Basekpis k : List) {
				kpi = new BaseKPI();
				kpi.setArea(k.getOrgName());
				kpi.setAreaComp(k.getFx_actual());
				kpi.setAreaDest(k.getFx_target());
				kpi.setAreaRate(k.getFx_rate());
				KPIList.add(kpi);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return KPIList;
	}

	public boolean getUserType() {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", DataProviderFactory.getUserId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETUSERTYPE, params);
			if (result != null) {
				DataProviderFactory.setUserType(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<BaseKPI> getKPIVisit() {
		List<BaseKPI> KPIList = new ArrayList<BaseKPI>();
		List<Basekpis> List = new ArrayList<Basekpis>();
		Gson gson = new Gson();
		BaseKPI kpi;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", DataProviderFactory.getUserId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETKPIV, params);
			// System.out.println("----"+result);
			if (result != null) {
				List = gson.fromJson(result, new TypeToken<List<Basekpis>>() {
				}.getType());
				for (Basekpis k : List) {
					kpi = new BaseKPI();
					kpi.setArea("拜访门店");
					kpi.setAreaComp(k.getV_actual());
					kpi.setAreaDest(k.getV_target());
					kpi.setAreaRate(k.getV_rate());
					KPIList.add(kpi);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// return KPIList;
		}
		return KPIList;
	}

	/**
	 * 获取主经销商以及下面分户的库存汇总
	 */
	@Override
	public String getSuperKunnrStockInfo(Stock stock) {
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		params.put("kunnrStock", gson.toJson(stock));
		params.put("roleId", DataProviderFactory.getRoleId());
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETSUPERKUNNRINFO, params);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {

				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getOPENAPIURL() {
		return OPENAPIURL;
	}

	public static void setOPENAPIURL(String oPENAPIURL) {
		OPENAPIURL = oPENAPIURL;
	}

	@Override
	public List<Product> getSkuLastPrice(String kunnr, String userId,
			String custId) {
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		// params.put("custId", custId);
		params.put("cloudId", kunnr);
		params.put("custId", custId);
		params.put("userId", userId);
		params.put("skuId", "");
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETSKULASTPRICE, params);
			// System.out.println("打印33333333333 "+result);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				final List<Product> products = gson.fromJson(result,
						new TypeToken<List<Product>>() {
						}.getType());
				// new Thread() {
				// @Override
				// public void run() {
				// Product.deleteAll();
				for (Product product : products) {
					// if (!"0000000000".equals(product.getCloudId())) {
					// System.out.print("更新结果:"+product.update()+" ");
					// product.setPinyinsearchkey(PinyinSort.getPinYin(product.getCategoryDesc()));
					product.save();
					// }
				}
				// }
				// }.start();
				return products;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 获取经销商自定义的打印格式
	 */
	public boolean getKunnrOrderFormat(String kunnr) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("cloudId", kunnr);

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ JXSPRINTFORMAT, params);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				Gson gson = new Gson();
				final List<OrderPrintFormat> formatList = gson.fromJson(result,
						new TypeToken<List<OrderPrintFormat>>() {
						}.getType());
				new Thread() {
					public void run() {
						for (OrderPrintFormat format : formatList) {
							format.save();
						}
					}
				}.start();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean getOrderDownload(String custId) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", custId);
			params.put("userId", DataProviderFactory.getUserId());
			// params.put("cloudId", DataProviderFactory.getCouldId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ ORDERDOWNLOAD, params);
			// System.out.println(result);
			if (XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)
					|| XPPApplication.UPLOAD_FAIL.equals(result)) {
				return false;
			} else if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
				return true;
			}

			final Gson gson = new Gson();
			/** 解析总表 */
			final List<Order> orderList = gson.fromJson(result,
					new TypeToken<List<Order>>() {
					}.getType());
			new Thread() {
				public void run() {
					// List<Order> cacheOrderList = new ArrayList<Order>();
					// Order.deleteAll();
					// OrderDetail.deleteall();

					for (Order order : orderList) {
						// cacheOrderList =
						// Order.findByOrderId(order.getOrderId());
						// if(cacheOrderList.size()==0){
						Order.deleteAll(Order.findByOrderId(order.getOrderId()));
						OrderDetail.delete(OrderDetail.findByOrderId(order
								.getOrderId()));
						order.setDateType(DataProviderFactory.getDayType());
						// order.setCouldId(DataProviderFactory.getCouldId());
						order.save();
						/** 解析明细表 */
						List<OrderDetail> orderDetailList = gson.fromJson(
								order.getOrderDetailList(),
								new TypeToken<List<OrderDetail>>() {
								}.getType());

						for (OrderDetail orderDetail : orderDetailList) {

							if (orderDetail.getMappingSKUId() == null) {
								orderDetail.setMappingSKUId("");
							}
							orderDetail.setCustId(order.getCustId());
							orderDetail.setDateType(DataProviderFactory
									.getDayType());
							orderDetail.setUserId(DataProviderFactory
									.getUserId());
							orderDetail.save();
						}
						// }

					}
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}

	public boolean getWeekOrderDownload(String custId, String firstday,
			String lastday) {

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", custId);
			params.put("userId", DataProviderFactory.getUserId());
			// params.put("cloudId", DataProviderFactory.getCouldId());
			params.put("firstday", firstday);
			params.put("lastday", lastday);
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETWEEKORDERTOTAL, params);
			// System.out.println(result);
			if (XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)
					|| XPPApplication.UPLOAD_FAIL.equals(result)) {
				return false;
			} else if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
				return true;
			}

			final Gson gson = new Gson();
			/** 解析总表 */
			final List<OldOrder> orderList = gson.fromJson(result,
					new TypeToken<List<OldOrder>>() {
					}.getType());
			// System.out.println("orderList-----"+orderList);
			// new Thread() {
			// public void run() {
			// List<Order> cacheOrderList = new ArrayList<Order>();
			OldOrder.deleteAll();
			OldOrderDetail.deleteall();

			for (OldOrder order : orderList) {
				// cacheOrderList = Order.findByOrderId(order.getOrderId());
				// if(cacheOrderList.size()==0){
				// OldOrder.deleteAll(OldOrder.findByOrderId(order.getOrderId()));
				// OldOrderDetail.delete(OldOrderDetail.findByOrderId(order.getOrderId()));
				order.setDateType(DataProviderFactory.getDayType());
				order.setCouldId(DataProviderFactory.getCouldId());
				order.save();
				/** 解析明细表 */
				List<OldOrderDetail> orderDetailList = gson.fromJson(
						order.getOrderDetailList(),
						new TypeToken<List<OldOrderDetail>>() {
						}.getType());

				for (OldOrderDetail orderDetail : orderDetailList) {

					if (orderDetail.getMappingSKUId() == null) {
						orderDetail.setMappingSKUId("");
					}
					orderDetail.setCustId(order.getCustId());
					orderDetail.setDateType(DataProviderFactory.getDayType());
					orderDetail.setUserId(DataProviderFactory.getUserId());
					orderDetail.save();

				}
				// }

			}
			// }
			// }.start();
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}

	@Override
	public List<CuanhuoQuery> searchCuanhuo(String chuanhaofactory,
			String chuanhaodate, String chuanhaosku, String chuanhaodamacode) {
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		// params.put("custId", custId);
		params.put("chuanhaofactory", chuanhaofactory);
		params.put("chuanhaodate", chuanhaodate);
		params.put("chuanhaosku", chuanhaosku);
		params.put("chuanhaodamacode", chuanhaodamacode);
		CuanhuoQuery.deleteAll();
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ SERACHCUANHUO, params);
			// System.out.println("打印33333333333 "+result);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				final List<CuanhuoQuery> cuanhuos = gson.fromJson(result,
						new TypeToken<List<CuanhuoQuery>>() {
						}.getType());
				// new Thread() {
				// @Override
				// public void run() {
				// for (Product product : products) {
				// //if (!"0000000000".equals(product.getCloudId())) {
				// //System.out.print("更新结果:"+product.update()+" ");
				// product.update();
				// //}

				for (CuanhuoQuery cuanhuo : cuanhuos) {
					cuanhuo.save();
				}

				// }
				// }.start();
				// System.out.println(cuanhuos);
				return cuanhuos;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Sign createSign(Sign sign) {
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		params.put("address", sign.getAddress());
		params.put("latitude", sign.getLatitude());
		params.put("longitude", sign.getLongitude());
		params.put("operator_id", sign.getOperator_id());
		params.put("sign_type", sign.getSign_type());
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ CERATESIGN, params);
			// System.out.println(result);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				final List<Sign> signs = gson.fromJson(result,
						new TypeToken<List<Sign>>() {
						}.getType());
				return signs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Sign getSignList(Sign sign) {
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		// params.put("address",sign.getAddress());
		// params.put("latitude", sign.getLatitude());
		// params.put("longitude",sign.getLongitude());
		params.put("operator_id", sign.getOperator_id());
		params.put("sign_type", sign.getSign_type());
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETSIGNLIST, params);
			// System.out.println(result);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				final List<Sign> signs = gson.fromJson(result,
						new TypeToken<List<Sign>>() {
						}.getType());
				return signs.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean getSkuUnit() {
		try {
			// Map<String, String> params = new HashMap<String, String>();
			// params.put("cloudId", DataProviderFactory.getCouldId());
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETSKUUNIT, null);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				Gson gson = new Gson();
				List<SkuUnit> skuList = gson.fromJson(result,
						new TypeToken<List<SkuUnit>>() {
						}.getType());
				SkuUnit.deleteAll();
				for (SkuUnit product : skuList) {
					// System.out.println(product);
					product.save();
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean uploadCustomerStock(StockReport stockReport) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("custId", stockReport.getCustId());
			params.put("userId", stockReport.getUserId());
			params.put("skuId", stockReport.getSkuId());
			params.put("quantity", stockReport.getQuantity());
			params.put("categoryId", stockReport.getCategoryId());
			params.put("productionDate", stockReport.getProductionDate());
			params.put("checkTime", stockReport.getCheckTime());
			params.put("unitDesc", stockReport.getUnitDesc());
			params.put("flag", stockReport.getFlag());
			params.put("userType", stockReport.getUserType());

			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ UPLOADCUSTOMERSTOCK, params);
			if (result == null) {
				return false;
			} else {
				if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
					stockReport.setStatus(Status.FINISHED);
					stockReport.update();
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<StockReport> getCustomerStockByCustid(String custId) {
		Map<String, String> params = new HashMap<String, String>();
		Gson gson = new Gson();
		params.put("custId", custId);
		try {
			String result = HttpUtil.post(OPENAPIURL + GET_PERFORMANCE_URL
					+ GETCUSTOMERSTOCKBYCUSTID, params);
			if (result != null
					&& !XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
							.equals(result)) {
				final List<StockReport> stockReports = gson.fromJson(result,
						new TypeToken<List<StockReport>>() {
						}.getType());
				if (stockReports != null) {
					StockReport.deleteAll(StockReport.findByCustId(custId));
					for (StockReport stockReport : stockReports) {
						stockReport.setQuantity((int) Double
								.parseDouble(stockReport.getQuantity()) + "");
						stockReport.save();
					}
				}

				return stockReports;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
