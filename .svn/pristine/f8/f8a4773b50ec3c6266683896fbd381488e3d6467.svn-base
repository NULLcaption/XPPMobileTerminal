package com.xpp.moblie.screens;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.xpp.moblie.adapter.order.OrderTotalAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Order;

/**
 * @Title: OrderTotalActivity.java
 * @Package com.xpp.kunnr.moblie.screens
 * @Description: orderTotal
 * @author will.xu
 * @date 2014年3月4日 下午2:04:12
 */
public class OrderTotalActivity extends Activity {
	private Button home_back,btn_createOrder,btn_oldOrder;
	private Customer shop;
	private ListView lv_orderTotalList;
	private OrderTotalAdapter orderTotalAdapter;
	private GestureDetector mGestureDetector;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_order_total);
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
				orderTotalAdapter.list =Order.findByCustId(shop.getCustId());
				orderTotalAdapter.notifyDataSetChanged();
			}else if(key.equals("close")){
				XPPApplication.exit(OrderTotalActivity.this);
			}
		}
	};
	
	private void initView() {
		home_back = (Button) findViewById(R.id.home_back);
		home_back.setOnClickListener(BtnClicked);
		btn_createOrder = (Button) findViewById(R.id.createOrder);
		btn_createOrder.setOnClickListener(BtnClicked);
		btn_oldOrder = (Button) findViewById(R.id.oldOrder);
		btn_oldOrder.setOnClickListener(BtnClicked);
		lv_orderTotalList = (ListView) findViewById(R.id.orderTotalList);
	}
	
	
	private void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
		}
		 orderTotalAdapter = new OrderTotalAdapter(Order.findByCustId(shop.getCustId()), OrderTotalActivity.this,shop);
		lv_orderTotalList.setAdapter(orderTotalAdapter);
		
		if(Order.findByCustId(shop.getCustId()).size()==0){
			Intent	i = new Intent(OrderTotalActivity.this, OrderActivity.class);
			i.putExtra("custInfo", shop);
			startActivity(i);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
		}
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
				XPPApplication.exit(OrderTotalActivity.this);
				break;
			case R.id.createOrder:
				Intent	i = new Intent(OrderTotalActivity.this, OrderActivity.class);
				i.putExtra("custInfo", shop);
				System.out.println("打印："+shop);
				startActivity(i);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				break;
			case R.id.oldOrder:
				Intent	i1 = new Intent(OrderTotalActivity.this, OldOrderTotalActivity.class);
				i1.putExtra("custInfo", shop);
				startActivity(i1);
				overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);	
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
	 				XPPApplication.exit(OrderTotalActivity.this);
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
					XPPApplication.exit(OrderTotalActivity.this);
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
	
}
