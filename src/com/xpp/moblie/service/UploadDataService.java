package com.xpp.moblie.service;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.StockReport;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.screens.HomeActivity;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Order;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
/**
 * Title:更新加载数据 
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年12月13日 上午9:18:53
 */
public class UploadDataService extends Service {
	
	protected static final int UPLOAD_PHOTO = 1;// 上传照片
	protected static final int UPLOAD_ABNORMALPRICE = 2;// 上传异常价格
	protected static final int UPLOAD_DISTRIBUTION = 3;// 上传铺货
	protected static final int UPLOAD_DISPLAY = 4;// 上传陈列
	protected static final int UPLOAD_INVENTORY = 5;// 上传铺货
	protected static final int UPLOAD_MARKETCHECK = 6;// 上传督导检查
	protected static final int UPLOAD_ORDER = 7;// 上传订单
	protected static final int UPLOAD_CUSTSTOCK = 8;// 上传门店分销量
	private static final int TIME_INTERNEL = 300000;
	protected static final int UPLOAD_FILE_DONE = 0;
	
	private NotificationManager mNotificationManager;
	private Handler uploadhandler = new Handler();

	private BroadcastReceiver UpdateListener = new BroadcastReceiver() {
		@SuppressWarnings("deprecation")
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			String key = b.getString("type");
			String values = b.getString("custId");
			Message message;
			if (key.equals("photo")) {
				message = new Message();
				message.what = UPLOAD_PHOTO;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("abnormalPrice")) {
				message = new Message();
				message.what = UPLOAD_ABNORMALPRICE;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("distribution")) {
				message = new Message();
				message.what = UPLOAD_DISTRIBUTION;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("display")) {
				message = new Message();
				message.what = UPLOAD_DISPLAY;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("inventory")) {
				message = new Message();
				message.what = UPLOAD_INVENTORY;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("marketCheck")) {
				message = new Message();
				message.what = UPLOAD_MARKETCHECK;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("order")) {
				message = new Message();
				message.what = UPLOAD_ORDER;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("StockReport")) {
				message = new Message();
				message.what = UPLOAD_CUSTSTOCK;
				message.obj = values;
				handler.sendMessage(message);
			} else if (key.equals("notification")) {
				Context ctx = DataProviderFactory.getContext();
				// 创建一个NotificationManager的引用
				mNotificationManager = (NotificationManager) ctx
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(
						R.drawable.notification_icon, b.getString("status"),
						System.currentTimeMillis());
				// // // 点通知返回原来activity
				Intent notificationIntent = new Intent(ctx, HomeActivity.class); // 点击该通知后跳转的Activity
				notificationIntent.setAction(Intent.ACTION_MAIN);
				notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
						notificationIntent, 0);
				notification.setLatestEventInfo(ctx, "香飘飘", "香飘飘",
						contentIntent);
				mNotificationManager.notify(0, notification);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						mNotificationManager.cancel(0);
					}
				}, 1000L);
			}
		}
	};

	private Runnable runnable = new Runnable() {
		public void run() {
			new UpdateStatusTask().execute();
		}
	};

	public void onCreate() {
		super.onCreate();

		uploadhandler.postDelayed(runnable, TIME_INTERNEL);
		registerReceiver(UpdateListener, new IntentFilter(
				XPPApplication.UPLOADDATA_RECEIVER));
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(final Message msg) {
			try {
				switch (msg.what) {
				case UPLOAD_PHOTO:// 上传照片 OK
					new Thread() {
						public void run() {
							try {
								for (PhotoInfo photoInfo : PhotoInfo
										.synchronousPhoto((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {
									DataProviderFactory.getProvider()
											.uploadPicture(photoInfo);
								}
							} catch (Exception e) {
								Log.i("UploadDataService Error:", e.toString());
							}
						}
					}.start();
					// handler.sendEmptyMessage(UPLOAD_FILE_DONE);
					break;
				case UPLOAD_ABNORMALPRICE:// 价格管理 OK
					new Thread() {
						public void run() {
							try {
								for (AbnormalPrice abnormalPrice : AbnormalPrice
										.synchronousAbnormalPrice((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {
									DataProviderFactory.getProvider()
											.uploadAbnormalPrice(abnormalPrice);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}.start();
					// handler.sendEmptyMessage(UPLOAD_FILE_DONE);
					break;
				case UPLOAD_DISTRIBUTION:// 铺货OK
					new Thread() {
						public void run() {
							try {
								for (Distribution distribution : Distribution
										.synchronousDis((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {
									DataProviderFactory.getProvider()
											.uploadDistribution(distribution);
								}
							} catch (Exception e) {
								Log.i("UploadDataService Error:", e.toString());
							}
						}
					}.start();
					// handler.sendEmptyMessage(UPLOAD_FILE_DONE);
					break;
				case UPLOAD_DISPLAY:// 陈列OK
					new Thread() {
						public void run() {
							try {
								for (DisPlay disPlay : DisPlay
										.synchronousDisPlay((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {
									DataProviderFactory.getProvider()
											.uploadDisplay(disPlay);

								}
							} catch (Exception e) {
								Log.i("UploadDataService Error:", e.toString());
							}
						}
					}.start();
					break;
				case UPLOAD_INVENTORY:// 库龄 OK
					new Thread() {
						public void run() {
							try {
								for (Inventory inventory : Inventory
										.synchronousInventory((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {

									DataProviderFactory.getProvider()
											.uploadInventory(inventory);

								}
							} catch (Exception e) {
								Log.i("UploadDataService Error:", e.toString());
							}
						}
					}.start();
					break;
				case UPLOAD_CUSTSTOCK:// 门店分销量 OK
					new Thread() {
						public void run() {
							try {
								for (StockReport stockReport : StockReport
										.synchronousStockReport((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {

									DataProviderFactory.getProvider()
											.uploadCustomerStock(stockReport);

								}
							} catch (Exception e) {
								Log.i("UploadDataService Error:", e.toString());
							}
						}

					}.start();
					break;

				case UPLOAD_MARKETCHECK:// 市场 OK
					new Thread() {
						public void run() {
							try {
								for (MarketCheck marketCheck : MarketCheck
										.synchronousMarketCheck((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {
									DataProviderFactory.getProvider()
											.uploadsupervise(marketCheck,
													UploadDataService.this);
								}
							} catch (Exception e) {
								Log.i("UploadDataService Error:", e.toString());
							}
						}
					}.start();
					break;
				case UPLOAD_ORDER:// 订单OK
					new Thread() {
						public void run() {
							try {
								for (Order order : Order
										.synchronousOrder((msg == null || msg.obj == null) ? null
												: (String) msg.obj)) {
									DataProviderFactory.getProvider()
											.uploadOrder(order);
								}
							} catch (Exception e) {
								Log.i("UploadDataService Error:", e.toString());
							}
						}
					}.start();
					break;
				// case UPLOAD_FILE_DONE:
				// break;
				}
				super.handleMessage(msg);
			} catch (Exception e) {
				Log.i("UploadDataService Error:", e.toString());
			}

		}

	};

	private class UpdateStatusTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = cwjManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					Message message = new Message();
					message.obj = null;
					message.what = UPLOAD_PHOTO;
					handler.sendMessage(message);

					message = new Message();
					message.obj = null;
					message.what = UPLOAD_ABNORMALPRICE;
					handler.sendMessage(message);

					message = new Message();
					message.obj = null;
					message.what = UPLOAD_DISTRIBUTION;
					handler.sendMessage(message);

					message = new Message();
					message.obj = null;
					message.what = UPLOAD_DISPLAY;
					handler.sendMessage(message);

					message = new Message();
					message.obj = null;
					message.what = UPLOAD_INVENTORY;
					handler.sendMessage(message);

					message = new Message();
					message.obj = null;
					message.what = UPLOAD_MARKETCHECK;
					handler.sendMessage(message);

					message = new Message();
					message.obj = null;
					message.what = UPLOAD_ORDER;
					handler.sendMessage(message);

				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			uploadhandler.postDelayed(runnable, TIME_INTERNEL);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(UpdateListener);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
