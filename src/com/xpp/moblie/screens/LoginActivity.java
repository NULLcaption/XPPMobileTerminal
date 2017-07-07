package com.xpp.moblie.screens;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.UpdateTask;
import com.xpp.moblie.query.OrmHelper;
import com.xpp.moblie.service.PhotoTimeService;
import com.xpp.moblie.service.UploadDataService;
import com.xpp.moblie.util.VersionUpdate;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;

/**
 * @Description:登陆页面
 * @author:xg.chen
 * @time:2017年4月21日 下午4:32:14
 * @version:1.0
 */
public class LoginActivity extends Activity {
	private EditText et_loginName, et_password;
	private Dialog waitingDialog;
	private LoginTask loginTask;
	private CheckBox cb_remberpsd;
	private int isupdate = 0;// 是否检查升级，0为检查

	// 初始化页面
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		OrmHelper.createInstance(getApplicationContext());
		DataProviderFactory.setContext(getApplicationContext());
		init();
	}

	// 页面数据加载
	private void init() {
		findViewById(R.id.btn_login).setOnClickListener(BtnClicked);
		et_loginName = (EditText) findViewById(R.id.userName);
		et_password = (EditText) findViewById(R.id.password);
		cb_remberpsd = (CheckBox) findViewById(R.id.remberpsd);
	}

	// 显示给当前用户
	protected void onStart() {
		super.onStart();
		TextView tv_version = (TextView) findViewById(R.id.version);
		tv_version.setText(getVersionName());
		String localpassword = DataProviderFactory.getLocalPassword();
		String loginName = DataProviderFactory.getLoginName();
		String chkpsd = DataProviderFactory.getRemberpsd();
		if (loginName != null && et_loginName != null) {
			et_loginName.setText(DataProviderFactory.getLoginName());
		}
		if ("Y".equals(chkpsd) && localpassword != null && et_password != null) {
			cb_remberpsd.setChecked(true);
			et_password.setText(DataProviderFactory.getLocalPassword());
		}
		et_password.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

	}

	// 登录按钮
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_login:
				tryLogin();
				break;
			}
		}

	};

	/**
	 * @Description:异步登录
	 * @author:xg.chen
	 * @time:2017年6月5日 下午2:40:30
	 * @version:1.0
	 */
	private class LoginTask extends AsyncTask<Object, Integer, Integer> {
		//onPreExecute()在execute(Params... params)被调用后立即执行，一般用来在执行后台任务前对UI做一些标记。
		//这里是获取是否记住密码，并设置参数
		protected void onPreExecute() {
			if (cb_remberpsd.isChecked()) {
				DataProviderFactory.setRemberpsd("Y");
			} else {
				DataProviderFactory.setRemberpsd("N");
			}
			DataProviderFactory.setLoginName(et_loginName.getText().toString());
			showWaitingDialog();
		}
		//doInBackground(Object... arg0)在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。
		//这里去后台验证登录密码
		protected Integer doInBackground(Object... arg0) {
			return DataProviderFactory.getProvider().login(
					et_password.getText().toString());
		}

		//当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
		//这里根据返回的结果集来更新UI组件的value
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			//登录成功-->首页
			case XPPApplication.SUCCESS:
				Intent i = new Intent(LoginActivity.this, HomeActivity.class);
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_success), Toast.LENGTH_LONG)
						.show();
				Intent iUpload = new Intent(LoginActivity.this,
						UploadDataService.class);
				startService(iUpload);
				Intent intenttime = new Intent(LoginActivity.this,
						PhotoTimeService.class);
				startService(intenttime);
				finish();
				new UpdateTask(LoginActivity.this, false).execute();
				DataProviderFactory.setMenuId(0);
				startActivity(i);
				overridePendingTransition(R.anim.in_from_top,
						R.anim.out_to_bottom);
				break;
			case XPPApplication.ERR_PASSWORD:
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_error_password),
						Toast.LENGTH_SHORT).show();
				break;
			case XPPApplication.NO_USER:
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_no_user), Toast.LENGTH_SHORT)
						.show();
				break;
			case XPPApplication.ERR_ROLE:
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_error_role),
						Toast.LENGTH_SHORT).show();
				break;
			case XPPApplication.OFFLINE_ERROR_PASSWORD:
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_offline_error_password),
						Toast.LENGTH_SHORT).show();
				break;
			case XPPApplication.OFFLINE_LOADED:
				Intent offlineIntent = new Intent(LoginActivity.this,
						HomeActivity.class);
				Intent offlineService = new Intent(LoginActivity.this,
						UploadDataService.class);
				startService(offlineService);
				Intent offlinetime = new Intent(LoginActivity.this,
						PhotoTimeService.class);
				startService(offlinetime);
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_offline_loaded),
						Toast.LENGTH_SHORT).show();
				finish();
				DataProviderFactory.setMenuId(0);
				offlineIntent.putExtra("menu", "Y");
				startActivity(offlineIntent);
				overridePendingTransition(R.anim.in_from_top,
						R.anim.out_to_bottom);
				break;
			case XPPApplication.FAIL:
				// if (changenet())
				// break;
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_fail), Toast.LENGTH_SHORT)
						.show();
				break;

			case XPPApplication.FAIL_CONNECT_SERVER:
				// if (changenet())
				// break;
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_fail_connect),
						Toast.LENGTH_SHORT).show();
				break;
			case XPPApplication.NO_NETWORK:
				// if (changenet())
				// break;
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_no_network),
						Toast.LENGTH_SHORT).show();
				break;
			case XPPApplication.NO_MOBILE:
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_no_mobile), Toast.LENGTH_SHORT)
						.show();
				break;
			case XPPApplication.NOTBUSINESSPHONE:
				Toast.makeText(getApplicationContext(),
						getString(R.string.login_not_business),
						Toast.LENGTH_SHORT).show();
				break;
			case XPPApplication.UPDATE_VERSION:
				if (isupdate == 0) {
					SharedPreferences settings = getSharedPreferences(
							"PrefsFile", Context.MODE_PRIVATE);
					new VersionUpdate(LoginActivity.this, settings.getString(
							"version", ""));
				} else {
					Intent i1 = new Intent(LoginActivity.this,
							HomeActivity.class);
					Toast.makeText(getApplicationContext(),
							getString(R.string.login_success),
							Toast.LENGTH_SHORT).show();
					Intent iUpload1 = new Intent(LoginActivity.this,
							UploadDataService.class);
					startService(iUpload1);
					Intent intenttime1 = new Intent(LoginActivity.this,
							PhotoTimeService.class);
					startService(intenttime1);
					finish();
					// DataProviderFactory.getProvider().startDataUpdateTasks(LoginActivity.this);
					new UpdateTask(LoginActivity.this, false).execute();
					DataProviderFactory.setMenuId(0);
					startActivity(i1);
					overridePendingTransition(R.anim.in_from_top,
							R.anim.out_to_bottom);

				}

				break;

			}
			dismissWaitingDialog();

		}
	}

	/**
	 * @Description: 异步登陆请求
	 * @author:xg.chen
	 * @date:2017年6月5日 下午2:38:38
	 * @version:1.0
	 */
	private void tryLogin() {
		String userName = et_loginName.getText().toString();
		String passWord = et_password.getText().toString();
		if (userName.length() != 0 && passWord.length() != 0) {
			loginTask = new LoginTask();
			loginTask.execute();//执行一个异步任务，需要我们在代码中调用此方法，触发异步任务的执行。
		} else {
			Toast.makeText(getApplicationContext(), "账号或密码不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
	}

	/**
	 * @Description:canceled login
	 * @author:xg.chen
	 * @date:2017年6月5日 下午2:44:50
	 * @version:1.0
	 */
	private void cancelLogin() {
		dismissWaitingDialog();
		if (loginTask != null) {
			loginTask.cancel(true);
			loginTask = null;
			return;
		}
	}

	/**
	 * @Description:get version number
	 * @author:xg.chen
	 * @date:2017年6月5日 下午2:45:47
	 * @return
	 * @version:1.0
	 */
	private String getVersionName() {
		PackageInfo packInfo = null;
		try {
			PackageManager packageManager = getPackageManager();
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo.versionName;
	}

	/**
	 * @Description:show waitting dialog
	 * @author:xg.chen
	 * @date:2017年6月5日 下午2:41:23
	 * @version:1.0
	 */
	private void showWaitingDialog() {
		if (waitingDialog == null) {
			waitingDialog = new Dialog(this, R.style.TransparentDialog);
			waitingDialog.setContentView(R.layout.login_waiting_dialog);
			DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					ImageView img = (ImageView) waitingDialog
							.findViewById(R.id.loading);
					((AnimationDrawable) img.getDrawable()).start();
				}
			};
			DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					cancelLogin();
				}
			};
			waitingDialog.setOnShowListener(showListener);
			waitingDialog.setCanceledOnTouchOutside(false);
			waitingDialog.setOnCancelListener(cancelListener);
			waitingDialog.show();
		}
	}

	/**
	 * @Description:dismiss waitting dialog
	 * @author:xg.chen
	 * @date:2017年6月5日 下午2:41:23
	 * @version:1.0
	 */
	private void dismissWaitingDialog() {
		if (waitingDialog != null) {
			ImageView img = (ImageView) waitingDialog
					.findViewById(R.id.loading);
			((AnimationDrawable) img.getDrawable()).stop();

			waitingDialog.dismiss();
			waitingDialog = null;
		}
	}

}
