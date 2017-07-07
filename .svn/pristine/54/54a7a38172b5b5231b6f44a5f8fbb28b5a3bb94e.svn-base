package com.xpp.moblie.screens;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.xpp.moblie.adapter.order.OldOrderTotalAdapter;
import com.xpp.moblie.adapter.order.OrderTotalAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.OldOrder;
import com.xpp.moblie.query.Order;

/**
 * @Title: OrderTotalActivity.java
 * @Package com.xpp.kunnr.moblie.screens
 * @Description: orderTotal
 * @author will.xu
 * @date 2014年3月4日 下午2:04:12
 */
public class OldOrderTotalActivity extends Activity {
	private Button home_back,btn_createOrder;
	private Customer shop;
	private ListView lv_orderTotalList;
	private OldOrderTotalAdapter orderTotalAdapter;
	private GestureDetector mGestureDetector;
	private Dialog waitingDialog;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oldorder_total);
		initView();
		initData();
		registerReceiver(RefreshListener, new IntentFilter(
				XPPApplication.UPLOADDATA_RECEIVER));
		mGestureDetector = new GestureDetector(this,new LearnGestureListener ());


	}
	
	private BroadcastReceiver RefreshListener = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			String key = b.getString("type");
			if (key.equals("notification")||key.equals("order")) {
				orderTotalAdapter.list =OldOrder.findByCustId(shop.getCustId());
				orderTotalAdapter.notifyDataSetChanged();
			}else if(key.equals("close")){
				XPPApplication.exit(OldOrderTotalActivity.this);
			}
		}
	};
	
	private void initView() {
		home_back = (Button) findViewById(R.id.home_back);
		home_back.setOnClickListener(BtnClicked);
		lv_orderTotalList = (ListView) findViewById(R.id.orderTotalList);
	}
	
	
	private void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
		}
	
		new getOrderListTask().execute(shop);
//		orderTotalAdapter.list =OldOrder.findByCustId(shop.getCustId());
//		orderTotalAdapter.notifyDataSetChanged();
	}
	
	public void onDestroy() {
		super.onDestroy();
		 unregisterReceiver(RefreshListener);
	}
	private OnClickListener BtnClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(OldOrderTotalActivity.this);
				break;
	
			}
		}
	};
	
	   class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{ 
	  	     public boolean onDown(MotionEvent ev) { 
	  	       Log.d("onDownd",ev.toString()); 
	  	         return true; 
	  	    } 
	  	      public boolean onFling (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	  	    	 if (e2.getX() - e1.getX() > 100) { 
	 				XPPApplication.exit(OldOrderTotalActivity.this);
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
	   
	   
	   
		// 重写手机返回按钮功能
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
					XPPApplication.exit(OldOrderTotalActivity.this);
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
		private class getOrderListTask extends AsyncTask<Customer, Integer, Boolean>{
			@Override
			protected void onPreExecute() {
				showWaitingDialog();
			}
			@Override
			protected Boolean doInBackground(Customer... params) {
				Date d=new Date();
				GregorianCalendar gc =new GregorianCalendar();
				SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
				
				gc.setTime(d);
				gc.add(5,-1);
				String lastday = sf.format(gc.getTime());
				gc.add(5,-7);
				String firstday=sf.format(gc.getTime());
				DataProviderFactory.getProvider().getWeekOrderDownload(params[0].getCustId(), firstday, lastday);
				//				oldOrderlist=OldOrder.findAll();
//				System.out.println(oldOrders);
				
			
				return true;
			}
			protected void onPostExecute(Boolean result) {
				orderTotalAdapter = new OldOrderTotalAdapter(OldOrder.findByCustId(shop.getCustId()), OldOrderTotalActivity.this,shop);
				lv_orderTotalList.setAdapter(orderTotalAdapter);
				orderTotalAdapter.notifyDataSetChanged();
				dismissWaitingDialog();

			}
		}
		private void showWaitingDialog() {
			if (waitingDialog == null) {
			
				waitingDialog = new Dialog(this, R.style.TransparentDialog);
				waitingDialog.setContentView(R.layout.login_waiting_dialog);
				DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
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
