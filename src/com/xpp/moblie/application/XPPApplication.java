package com.xpp.moblie.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.xpp.moblie.query.Info;
import com.xpp.moblie.screens.R;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @Description:XPP application public calss
 * @author:xg.chen
 * @time:2017年6月5日 下午3:39:54
 * @version:1.0
 */
public class XPPApplication extends Application {

	private static XPPApplication instance;

	public static final int NO_NETWORK = 0;
	public static final int SUCCESS = 1;
	public static final int ERR_PASSWORD = 2;
	public static final int ERR_ROLE = 3;
	public static final int NO_USER = 4;
	public static final int FAIL_CONNECT_SERVER = 5;
	public static final int OFFLINE_ERROR_PASSWORD = 8;
	public static final int OFFLINE_LOADED = 9;
	public static final int FAIL = 10;
	public static final int UPDATE_VERSION = 11;
	public static final int NO_MOBILE = 12;
	public static final int NOTBUSINESSPHONE = 13;

	public static final String UPLOAD_FAIL = "fail";
	public static final String UPLOAD_SUCCESS = "success";
	public static final String UPLOAD_SAME = "same";
	public static final String UPLOAD_TIMEOUT = "timeout";
	public static final String UPLOAD_FAIL_CONNECT_SERVER = "5";

	public static final String INVENTORY_RECEIVER = "inventoryReceiver";
	public static final String UPLOADDATA_RECEIVER = "uploadDataReceiver";
	public static final String REFRESH_RECEIVER = "refreshReceiver";
	public static final String REFRESH_SHOP_RECEIVER = "refreshShopReceiver";
	public static final String REFRESH_MARKET_RECEIVER = "refreshMarketReceiver";

	public static List<Info> mapinfo = new ArrayList<Info>();

	//status
	public enum Status {
		UNSYNCHRONOUS, FINISHED, NEW, FAIL, NOPICTURE, DOWNLOAD, CACHE, YWYUPLOAD
	}

	//photo type
	public enum PhotoType {
		DTZ, SCJC, SPCLZ, XPPMULTI, YLMMULTI, XPPDIST, YLMDIST, YKC, ZKC, MRFXL, LDZ, SIGNIN, SIGNOUT
	}

	// 登陆角色
	public static final String MOBILE_DD = "mobile_dd";
	public static final String MOBILE_KHJL = "mobile_khjl";
	public static final String MOBILE_YWY = "mobile_ywy";
	public static final String MOBILE_CSJL = "mobile_csjl";

	// 经销商库存提报
	public static final String TAB_KUNNR_WEEK = "kunnr_week";
	public static final String TAB_KUNNR_MONTH = "kunnr_month";
	public static final String TAB_SALES_DAY = "sales_day";

	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static XPPApplication getInstance() {
		return instance;
	}

	/**
	 * @Description:exit activity
	 * @author:xg.chen 
	 * @date:2017年6月5日 下午3:54:35 
	 * @param activity
	 * @version:1.0
	 */
	public static void exit(Activity activity) {
		activity.finish();
		activity.overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}

	/**
	 * @Description:send change broad
	 * @author:xg.chen 
	 * @date:2017年6月5日 下午3:55:08 
	 * @param context
	 * @param service
	 * @param map
	 * @version:1.0
	 */
	public static void sendChangeBroad(Context context, String service,
			Map<String, String> map) {
		Intent i = new Intent(service);
		if (map != null) {
			for (Map.Entry entry : map.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				i.putExtra(key, value);
			}
		}
		context.sendBroadcast(i);
	}

	/**
	 * @Description:get version number
	 * @author:xg.chen 
	 * @date:2017年6月5日 下午3:55:38 
	 * @param context
	 * @return
	 * @version:1.0
	 */
	public static String getVersionName(Context context) {
		PackageInfo packInfo = null;
		try {
			PackageManager packageManager = context.getPackageManager();
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo.versionName;
	}

	/**
	 * @Description:open key board
	 * @author:xg.chen 
	 * @date:2017年6月5日 下午3:56:19 
	 * @param et
	 * @param activity
	 * @version:1.0
	 */
	public static void openKeyboard(final View et, Activity activity) {
		final Activity activity1 = activity;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) activity1
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		}, 300);
	}

	/**
	 * @Description:close key board
	 * @author:xg.chen 
	 * @date:2017年6月5日 下午3:56:48 
	 * @param et
	 * @param activity
	 * @version:1.0
	 */
	public static void closeKeyboard(final View et, Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}

}
