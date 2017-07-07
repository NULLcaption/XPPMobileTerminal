package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.List;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseDivision;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.util.ToastUtil;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**   
 * @Title: SettingActivity.java 
 * @Package com.xpp.moblie.screens 
 * @Description: TODO
 * @author will.xu 
 * @date 2014年4月2日 下午1:58:56 
 */

public class SettingActivity extends Activity {
	private Dialog overdialog,waitingDialog;
	private EditText et_areaAddress ,et_kunnrName,et_areaAddress1 ,et_kunnrName1;
	private String zwl04,kunnr,zwl041,kunnr1;
	private	SharedPreferences settings ;
	private GestureDetector mGestureDetector;
	private int setflag;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		initView();
		initDate();
		mGestureDetector = new GestureDetector(this,new LearnGestureListener ());

	}
	
	
	class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{ 
 	     public boolean onDown(MotionEvent ev) { 
 	       Log.d("onDownd",ev.toString()); 
 	         return true; 
 	    } 
 	      public boolean onFling (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
 	    	 if (e2.getX() - e1.getX() > 100) { 
				XPPApplication.exit(SettingActivity.this);
				return true;
		 }
 	    		  return false;
  } 
 	       public boolean onTouchEvent(MotionEvent event) {
 	    	   return mGestureDetector.onTouchEvent(event);
 	       }
}
  public boolean dispatchTouchEvent(MotionEvent ev) {
      mGestureDetector.onTouchEvent(ev);
      return super.dispatchTouchEvent(ev);

}

	
	private void initView() {
		et_areaAddress = (EditText) findViewById(R.id.areaAddress);
		et_kunnrName = (EditText) findViewById(R.id.kunnrName);
		et_areaAddress1 = (EditText) findViewById(R.id.areaAddress1);
		et_kunnrName1 = (EditText) findViewById(R.id.kunnrName1);
		findViewById(R.id.save).setOnClickListener(BtnClicked);
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.searchAddress).setOnClickListener(BtnClicked);
		findViewById(R.id.searchKunnr).setOnClickListener(BtnClicked);
		findViewById(R.id.clearAddress).setOnClickListener(BtnClicked);
		findViewById(R.id.clearKunnr).setOnClickListener(BtnClicked);
		findViewById(R.id.searchAddress1).setOnClickListener(BtnClicked);
		findViewById(R.id.searchKunnr1).setOnClickListener(BtnClicked);
		findViewById(R.id.clearAddress1).setOnClickListener(BtnClicked);
		findViewById(R.id.clearKunnr1).setOnClickListener(BtnClicked);

	}

	private void initDate() {
		settings = getSharedPreferences(DataProviderFactory.PREFS_NAME,
				Context.MODE_PRIVATE);
		zwl04 = settings.getString(DataProviderFactory.PREFS_ZW104, null);
		et_areaAddress.setText(settings.getString(DataProviderFactory.PREFS_DIVINAME, null));
		kunnr = settings.getString(DataProviderFactory.PREFS_DEFAULT_KUNNR, null);
		et_kunnrName.setText(settings.getString(DataProviderFactory.PREFS_DEFAULT_KUNNR_NAME, null));
		zwl041 = settings.getString(DataProviderFactory.PREFS_ZW1041, null);
		et_areaAddress1.setText(settings.getString(DataProviderFactory.PREFS_DIVINAME1, null));
		kunnr1 = settings.getString(DataProviderFactory.PREFS_DEFAULT_KUNNR1, null);
		et_kunnrName1.setText(settings.getString(DataProviderFactory.PREFS_DEFAULT_KUNNR_NAME1, null));
	}	

	
	
	
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(SettingActivity.this);
				break;
			case R.id.clearAddress:
				zwl04 =null;
				et_areaAddress.setText("");
				break;
			case R.id.clearKunnr:
				kunnr =null;
				et_kunnrName.setText("");
				break;
			case R.id.clearAddress1:
				zwl041 =null;
				et_areaAddress1.setText("");
				break;
			case R.id.clearKunnr1:
				kunnr1 =null;
				et_kunnrName1.setText("");
				break;
			case R.id.save:
				if (zwl04 ==null) {
					et_areaAddress.setText("");
				}
				if (kunnr  ==null) {
					et_kunnrName.setText("");
				}
				
				if ("".equals(et_areaAddress.getText().toString().trim())) {
					zwl04 =null;
				}
				if ("".equals(et_kunnrName.getText().toString().trim())) {
					kunnr =null;
				}
				
				if (zwl041 ==null) {
					et_areaAddress1.setText("");
				}
				if (kunnr1  ==null) {
					et_kunnrName1.setText("");
				}
				
				if ("".equals(et_areaAddress1.getText().toString().trim())) {
					zwl041 =null;
				}
				if ("".equals(et_kunnrName1.getText().toString().trim())) {
					kunnr1 =null;
				}
				try {
					SharedPreferences.Editor editor = settings.edit();
					editor.putString(DataProviderFactory.PREFS_ZW104, zwl04);
					editor.putString(DataProviderFactory.PREFS_DIVINAME, et_areaAddress.getText().toString().trim());
					editor.putString(DataProviderFactory.PREFS_DEFAULT_KUNNR, kunnr);
					editor.putString(DataProviderFactory.PREFS_DEFAULT_KUNNR_NAME, et_kunnrName.getText().toString().trim());
					editor.putString(DataProviderFactory.PREFS_ZW1041, zwl041);
					editor.putString(DataProviderFactory.PREFS_DIVINAME1, et_areaAddress1.getText().toString().trim());
					editor.putString(DataProviderFactory.PREFS_DEFAULT_KUNNR1, kunnr1);
					editor.putString(DataProviderFactory.PREFS_DEFAULT_KUNNR_NAME1, et_kunnrName1.getText().toString().trim());
					editor.commit();
					XPPApplication.exit(SettingActivity.this);
				} catch (Exception e) {
					ToastUtil.show(getApplicationContext(), "保存失败");
				}
				
				break;
			case R.id.searchAddress:
				if (et_areaAddress.getText().toString().trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.not_null), Toast.LENGTH_SHORT)
							.show();
				} else {
					setflag=0;
					new searchDivisionTask().execute(et_areaAddress.getText()
							.toString().trim());
				}
				break;
			case R.id.searchAddress1:
				if (et_areaAddress1.getText().toString().trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.not_null), Toast.LENGTH_SHORT)
							.show();
				} else {
					setflag=1;
					new searchDivisionTask().execute(et_areaAddress1.getText()
							.toString().trim());
				}
				break;	
			case R.id.searchKunnr:
				if (et_kunnrName.getText().toString().trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.not_null), Toast.LENGTH_SHORT)
							.show();
				} else {
					setflag=0;
					new searchKunnrTask().execute(et_kunnrName.getText()
							.toString().trim());
				}
				break;
			case R.id.searchKunnr1:
				if (et_kunnrName1.getText().toString().trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.not_null), Toast.LENGTH_SHORT)
							.show();
				} else {
					setflag=1;
					new searchKunnrTask().execute(et_kunnrName1.getText()
							.toString().trim());
				}
				break;	
			}
		}

	};
	
	
	
	
	
	
	
	
	/**行政区域查询adapter**/
	public class DivisionSettingAdapter extends BaseAdapter {
		private List<BaseDivision> data = new ArrayList<BaseDivision>();//
		private LayoutInflater layoutInflater;

		public DivisionSettingAdapter(Context context, List<BaseDivision> data) {
			this.data = data;
			this.layoutInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return data.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodlerDivision hodler = null;
			if (convertView == null) {
				// 获取组件布局
				hodler = new ViewHodlerDivision();
				convertView = layoutInflater.inflate(
						R.layout.dialog_search_shop_list_child, null);
				hodler.diviName = (TextView) convertView
						.findViewById(R.id.custName);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodlerDivision) convertView.getTag();
				resetViewHolderDivision(hodler);
			}

			hodler.diviName.setText(data.get(position).getDiviName());

			// 绑定数据、以及事件触发
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					if (setflag==0) {
						zwl04 = data.get(n).getZwl04();
						et_areaAddress.setText(data.get(n).getDiviName());
					}
					else if (setflag==1) {
						zwl041 = data.get(n).getZwl04();
						et_areaAddress1.setText(data.get(n).getDiviName());
					}
					
					overdialog.cancel();
				}
			});
			return convertView;
		}
	}
	protected class ViewHodlerDivision {
		TextView diviName = null;
	}

	protected void resetViewHolderDivision(ViewHodlerDivision pViewHolder) {
		pViewHolder.diviName.setText(null);

	}
	
	
	
	private class searchDivisionTask extends
			AsyncTask<String, Integer, List<BaseDivision>> {

		protected void onPreExecute() {
			showWaitingDialog();

		}

		protected List<BaseDivision> doInBackground(String... arg0) {
			String str = arg0[0];
//			if (UpdateTask.getInstance().getStatus() == AsyncTask.Status.RUNNING) {
//				UpdateTask.getInstance().waitTimeout();
//			}
			return DataProviderFactory.getProvider().searchDivision(str);
		}

		protected void onPostExecute(List<BaseDivision> result) {
			dismissWaitingDialog();
			if (result != null && result.size() != 0) {
				if (result.get(0).getDiviName()
						.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.login_fail_connect),
							Toast.LENGTH_SHORT).show();
				} else {
					overdialog = null;
					View overdiaView = View.inflate(SettingActivity.this,
							R.layout.dialog_search_shop_msg, null);
					overdialog = new Dialog(SettingActivity.this,
							R.style.dialog_xw);
					ListView clist = (ListView) overdiaView
							.findViewById(R.id.clist);
					TextView tv_title = (TextView) overdiaView
							.findViewById(R.id.Title);
					tv_title.setText("行政区查询");
					DivisionSettingAdapter dvisionSettingAdapter = new DivisionSettingAdapter(
							getApplicationContext(), result);
					clist.setAdapter(dvisionSettingAdapter);
					overdialog.setContentView(overdiaView);
					overdialog.setCanceledOnTouchOutside(false);
					Button overcancel = (Button) overdiaView
							.findViewById(R.id.dialog_cancel_btn);
					overcancel.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							overdialog.cancel();
						}
					});
					overdialog.show();

				}
			} else {
				Toast.makeText(getApplicationContext(), "查询无记录！",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	
	/**经销商查询adapter**/
	public class KunnrAdapter extends BaseAdapter {
		private List<Customer> data = new ArrayList<Customer>();//
		private LayoutInflater layoutInflater;

		public KunnrAdapter(Context context, List<Customer> data) {
			this.data = data;
			this.layoutInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return data.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodlerDivision hodler = null;
			if (convertView == null) {
				// 获取组件布局
				hodler = new ViewHodlerDivision();
				convertView = layoutInflater.inflate(
						R.layout.dialog_search_shop_list_child, null);
				hodler.diviName = (TextView) convertView
						.findViewById(R.id.custName);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodlerDivision) convertView.getTag();
				resetViewHolderDivision(hodler);
			}
			hodler.diviName.setText(data.get(position).getKunnrName());

			// 绑定数据、以及事件触发
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					if (setflag==0) {
						kunnr = data.get(n).getKunnr();
						et_kunnrName.setText(data.get(n).getKunnrName());
					}
					else if (setflag==1) {
						kunnr1 = data.get(n).getKunnr();
						et_kunnrName1.setText(data.get(n).getKunnrName());
					}
					
					overdialog.cancel();
				}
			});
			return convertView;
		}
	}

	
	
	private class searchKunnrTask extends
			AsyncTask<String, Integer, List<Customer>> {

		protected void onPreExecute() {
			showWaitingDialog();

		}

		protected List<Customer> doInBackground(String... arg0) {
			String str = arg0[0];
			return DataProviderFactory.getProvider().searchKunnr(str);
		}

		protected void onPostExecute(List<Customer> result) {
			dismissWaitingDialog();
			if (result != null && result.size() != 0) {
				if (result.get(0).getKunnr()
						.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.login_fail_connect),
							Toast.LENGTH_SHORT).show();
				} else {
					overdialog = null;
					View overdiaView = View.inflate(SettingActivity.this,
							R.layout.dialog_search_shop_msg, null);
					overdialog = new Dialog(SettingActivity.this,
							R.style.dialog_xw);
					ListView clist = (ListView) overdiaView
							.findViewById(R.id.clist);
					TextView tv_title = (TextView) overdiaView
							.findViewById(R.id.Title);
					tv_title.setText("经销商查询");
					KunnrAdapter kunnrAdapter = new KunnrAdapter(
							getApplicationContext(), result);
					clist.setAdapter(kunnrAdapter);
					overdialog.setContentView(overdiaView);
					overdialog.setCanceledOnTouchOutside(false);
					Button overcancel = (Button) overdiaView
							.findViewById(R.id.dialog_cancel_btn);
					overcancel.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							overdialog.cancel();
						}
					});
					overdialog.show();

				}
			} else {
				Toast.makeText(getApplicationContext(), "查询无记录！",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	
	// 取消手机返回按钮功能
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(SettingActivity.this);
			return false;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
	
	
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
					// updateButtonLook(false);
				}
			};
			waitingDialog.setOnShowListener(showListener);
			waitingDialog.setCanceledOnTouchOutside(false);
			waitingDialog.setOnCancelListener(cancelListener);
			waitingDialog.show();
		}
	}

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
