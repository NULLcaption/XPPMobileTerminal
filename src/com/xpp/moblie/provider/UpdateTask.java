package com.xpp.moblie.provider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.impl.cookie.DateParseException;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseDictionary;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.screens.CustomerActivity;
import com.xpp.moblie.screens.HomeActivity;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.util.PictureShowUtils;
import com.xpp.moblie.util.TimeUtil;
import com.xpp.moblie.util.VersionUpdate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.test.suitebuilder.TestSuiteBuilder.FailedToCreateTests;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 */
public class UpdateTask extends AsyncTask<Object, Object, Integer> {
	private static final String TAG = "UpdateTask";
	private static final int TIMEOUT = 5000;
	private  NotificationManager mNotificationManager;
	private static final Long delay = 1000L;
	private static UpdateTask instance;
	private static int FIAL = 0;
	private static int SUCCESS = 1;
	private static int FIAL_SKU = 2;
	private static int FIAL_CHANNEL = 3;
	private static int FIAL_DICTIONARY = 4;
	private static int FIAL_TIME = 5;
	private static int FIAL_TIMEOUT = 6;
	private static int NEW_VERSION = 7;
	private static int FIAL_MENU = 8;
	private static int FIAL_ROUTE = 9;
	private static int FIAL_KunnrStockDate = 10;
	private static int FIAL_ORDER = 11;
	private static int FIAL_SKUUNIT = 12;
	private final Lock running = new ReentrantLock();
//	private static Activity activity;
	private static Context context;
	private static boolean isRefresh ;//是否自动刷新页面
	public UpdateTask(Context context,boolean isRefresh) {
		UpdateTask.context = context;
		UpdateTask.isRefresh = isRefresh;
	}

	public static UpdateTask getInstance() {
		/* if (instance == null) */
		instance = new UpdateTask(context,isRefresh);
		return instance;
	}

	@Override
	protected void onPreExecute() {
		Log.d(TAG, "Starting Update Task");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostExecute(Integer result) {

		Context ctx = DataProviderFactory.getContext();
		// 创建一个NotificationManager的引用
		mNotificationManager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		String str = "数据同步完成....";
		switch (result) {
		case 0:
			str = "数据同步失败....";
			break;
		// case 1:
		// str = "数据同步完成....";
		// break;
		case 2:
			str = "sku同步失败....";
			break;
		case 3:
			str = "渠道信息同步失败....";
			break;
		case 4:
			str = "陈列数据同步失败....";
			break;
		case 5:
			str = "服务器时间同步失败....";
			break;
		case 6:
			str = "连接服务器超时....";
			break;
		case 7:
			str = "同步成功，监测到新版本......";
			break;
		case 8:
			str = "菜单同步失败.....";
			break;
		case 9:
			str = "线路同步失败.....";
			break;
		case 10:
			str = "库存上报时间同步失败.....";
			break;	
		case 11:
			str = "订单同步失败.....";
			break;
		case 12:
			str = "门店分销量sku同步失败.....";
			break;
		default:
			break;
		}

		Notification notification = new Notification(
				R.drawable.notification_icon, str, System.currentTimeMillis());
		// // // 点通知返回原来activity
		Intent notificationIntent = new Intent(ctx, HomeActivity.class); // 点击该通知后跳转的Activity
		notificationIntent.setAction(Intent.ACTION_MAIN);
		 notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		 notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(ctx, "香飘飘", "香飘飘", contentIntent);
		mNotificationManager.notify(0, notification);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				mNotificationManager.cancel(0);
			}
		}, delay);

		instance = null;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	protected Integer doInBackground(Object... params) {
		running.lock();
		// boolean flag = true;
		try {
			if (!DataProviderFactory.getProvider().getDictionary()) {
			}
			if (!DataProviderFactory.getProvider().getMenu()) {
				return FIAL_MENU;
			}
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("menu", "Y");
			XPPApplication.sendChangeBroad(context,
					XPPApplication.REFRESH_SHOP_RECEIVER, map1);
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String result = DataProviderFactory.getProvider().getTime();
			if ("".equals(result)) {
				return FIAL_TIME;
			}
			if (XPPApplication.UPLOAD_FAIL_CONNECT_SERVER.equals(result)) {
				return FIAL_TIMEOUT;
			}
			String day = f.format(new Date(Long.valueOf(result)));
			if(DataProviderFactory.getDayType()==null){
				DataProviderFactory.setDayType(day);
			}
			
//			BaseDictionary bd = DataProviderFactory.getProvider().getVersion();
//			if (bd != null) {
//				if (context!=null&&!bd.getItemName().equals(
//						XPPApplication.getVersionName(context))) {
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("version", bd.getDictTypeValue());
//					XPPApplication.sendChangeBroad(context,
//							XPPApplication.REFRESH_SHOP_RECEIVER, map);
////					return NEW_VERSION;
//				}
//			}
			
			
			
			if (!DataProviderFactory.getProvider().getUserType()) {
				
			}			
	
			
//			 if (!day.equals(DataProviderFactory.getDayType())) {// 隔天更新数据
//			System.out.println("隔天更新数据");		 
			if (!DataProviderFactory.getProvider().getSKUList()) {
//				return FIAL_SKU;
			}
			if (!DataProviderFactory.getProvider().getChannelList()) {
//				return FIAL_CHANNEL;
			}
//			DataProviderFactory.setDayType(day);
//			if (!DataProviderFactory.getProvider().getIndexDetail()) {
//				return FIAL_DICTIONARY;
//			}
		
			if (!DataProviderFactory.getProvider().getSkuUnit()) {
				//return FIAL_SKUUNIT;
			}
//			 }
			
			
			//查询经销商的信息
			if(!XPPApplication.MOBILE_DD.equals(DataProviderFactory.getRoleId())){
				DataProviderFactory.getProvider().getKunnrByJL();
			}
//				MarketCheck.deleteAll(MarketCheck.findAll());
			 if (!day.equals(DataProviderFactory.getDayType())) {// 隔天清理线路数据
				 for (Customer customer : Customer.findRouteShop()) {
				 AbnormalPrice.deleteAll(AbnormalPrice.getRecordsCount(customer.getCustId()));// 清除异常价格信息
					DisPlay.deleteAll(DisPlay.getRecordsCount(customer.getCustId()));// 清除陈列信息
					Distribution.deleteAll(Distribution.getRecordsCount(customer.getCustId()));// 清除铺货信息
					Inventory.deleteAll(Inventory.getRecordsCount(customer.getCustId()));// 清除库龄信息
					MarketCheck.deleteAll(MarketCheck.findByCustId(customer.getCustId()));// 清除市场活动信息
					// 删照片
								PictureShowUtils.deletePhoto(PhotoInfo
										.findAllPhotoByCustId(customer.getCustId()));
					PhotoInfo.deleteAll(PhotoInfo.findAllPhotoByCustId(customer.getCustId()));// 清除照片信息
					Product.deleteAll(Product.findByKunner(customer.getKunnr()));//清除订单产品信息
				 }
			 }
			
			 DataProviderFactory.setDayType(day);
			 if (!DataProviderFactory.getProvider().getRoute()) {
					return FIAL_ROUTE;
				}
			
			String custIdStr =null;
			List<Customer> list = Customer.findRouteShop();
				for (int i = 0; i < list.size(); i++) {
					if(i!=0){
						custIdStr = custIdStr+ ","+list.get(i).getCustId();
					}else{
						custIdStr = list.get(i).getCustId();
					}
				}
				if(!DataProviderFactory.getProvider().getOrderDownload(custIdStr)){
					return FIAL_ORDER;
				}
			
			if(custIdStr!=null){
				
				List<MarketCheck> marketCheckList =  DataProviderFactory.getProvider().getMarketActivity(custIdStr);
			for (MarketCheck marketCheck : marketCheckList) {
//				marketCheck.setId(marketCheck.getCustId()
//						+ marketCheck.getMarketDetailId()+marketCheck.getItemId());
//				marketCheck.setEmpId(DataProviderFactory.getUserId());
//				System.out.println("user id---"+marketCheck.getEmpId());
//				marketCheck.setDayType(DataProviderFactory.getDayType());
//				marketCheck.save();
				Customer cc = Customer.findByCustId(marketCheck.getCustId());
				cc.setIsActivity("1");//有市场活动的标志
				cc.update();
				}
			}
			if(isRefresh){
				 Map<String, String> map = new HashMap<String, String>();
				 map.put("refresh", "isRefresh");
				 XPPApplication.sendChangeBroad(context,
				 XPPApplication.REFRESH_SHOP_RECEIVER, map);
			}
			
			if (!DataProviderFactory.getProvider().getKunnrStockDate()) {
				return FIAL_KunnrStockDate;
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FIAL;
		} finally {
			running.unlock();
		}
	}

	public void waitTimeout() {
		try {
			running.tryLock(TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
	}

}