package com.xpp.moblie.screens;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.adapter.shopvisist.VisistTaskListAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.IDataProvider;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.Order;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.util.ToastUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Title: 拜访管理之任务列表
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年11月7日
 */
public class VisitTaskActivity extends Activity implements OnGestureListener{
	private Customer customer;
	private ListView lv_visitTaskList;
	private TextView custName;
	private VisistTaskListAdapter visistTaskListAdapter;
    private GestureDetector detector;
    private ImageView customerimg_maps;

	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_visit_task);
			initView();
			initData();
			registerReceiver(RefreshListener, new IntentFilter(
					XPPApplication.UPLOADDATA_RECEIVER));
		    detector = new GestureDetector(this);
	}

  	@Override  
    public boolean onTouchEvent(MotionEvent event) {  
        return this.detector.onTouchEvent(event);  
    }  
	@Override
	protected void onStart() {
		super.onStart();
	}

	private void initView() {
		lv_visitTaskList = (ListView) findViewById(R.id.visit_task);
		custName = (TextView) findViewById(R.id.custName);
		customerimg_maps=(ImageView) findViewById(R.id.customer_map_icon_img);
		customerimg_maps.setOnClickListener(BtnClicked);
	}

	private void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			customer = (Customer) bun.get("custInfo");
		}
		custName.setText(customer.getCustName());
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);

		new shopVisistListTask().execute();
	}
	private class shopVisistListTask extends
			AsyncTask<Object, Integer, List<BaseParameter>> {
		protected List<BaseParameter> doInBackground(Object... arg0) {
			return getVisitList();
		}

		protected void onPostExecute(List<BaseParameter> result) {
			visistTaskListAdapter = new VisistTaskListAdapter(
					VisitTaskActivity.this, result, customer);
			lv_visitTaskList.setAdapter(visistTaskListAdapter);
		}

	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(RefreshListener);
	}

	private BroadcastReceiver RefreshListener = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			String key = b.getString("type");
			if (key != null || !"".equals(key)) {
				visistTaskListAdapter.taskList = getVisitList();
				visistTaskListAdapter.notifyDataSetChanged();
			}
		}
	};

	private List<BaseParameter> getVisitList() {
		List<BaseParameter> shopList = new ArrayList<BaseParameter>();
		BaseParameter bp = new BaseParameter();
		bp.setObj1("店头照");
		bp.setObj2("0");
		bp.setObj11(PhotoInfo.getRecordsCount(customer.getCustId(),
				PhotoType.DTZ));
		shopList.add(bp);

		bp = new BaseParameter();
		bp.setObj1("铺货管理");
		bp.setObj2("1");
		bp.setObj11(Distribution.getRecordsCount(customer.getCustId()).size());
		shopList.add(bp);

		bp = new BaseParameter();
		bp.setObj1("陈列管理");
		bp.setObj2("2");
		bp.setObj11(DisPlay.getRecordsCount(customer.getCustId()).size());
		shopList.add(bp);
		
		bp = new BaseParameter();
		bp.setObj1("价格管理");
		bp.setObj2("3");
		bp.setObj11(AbnormalPrice.getRecordsCount(customer.getCustId()).size());
		shopList.add(bp);

		bp = new BaseParameter();
		bp.setObj1("货龄管理");
		bp.setObj2("4");
		bp.setObj11(Inventory.getRecordsCount(customer.getCustId()).size());
		shopList.add(bp);
	
		
		if (customer.getIsActivity() != null) {
			bp = new BaseParameter();
			bp.setObj1("市场活动检查");
			bp.setObj2("5");
			bp.setObj11(MarketCheck.getRecordsCount(customer.getCustId()).size());
			shopList.add(bp);
		}
		List<Dictionary> dicts=Dictionary.findbyTypeValue("isOrderManager");
		boolean flag=true;
		for (Dictionary dict : dicts) {
			if("N".equals(dict.getItemDesc())){
				if(dict.getItemValue().equals(DataProviderFactory.getRoleId())){
					
					flag=false;
					break;
				}
			}
		}
		if(flag){
			bp = new BaseParameter();
			bp.setObj1("订单管理");
			bp.setObj2("6"); 
			bp.setObj11(Order.getRecordsCount(customer.getCustId()).size());
			shopList.add(bp);
			
		}
		bp = new BaseParameter();
		bp.setObj1("离店拍照");
		bp.setObj2("7");
		bp.setObj11(PhotoInfo.getRecordsCount(customer.getCustId(),
				PhotoType.LDZ));
		shopList.add(bp);
		return shopList;
	}

	/** 按键监听 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				sendNotice();
				break;
			case R.id.customer_map_icon_img:
//				Intent iis=new Intent(HomeActivity.this,MyBaiduMap.class);//跳转到显示地图的界面
				Intent iis = new Intent(VisitTaskActivity.this,CustomerLocationActivity.class);
				Bundle bundle = new Bundle();
				customer=Customer.findByCustId(customer.getCustId());
				bundle.putSerializable("customer", customer);
				iis.putExtras(bundle);
				startActivity(iis);
				break;	
			}
		}
	};

	// 取消手机返回按钮功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			sendNotice();
			
			return false;
		} else {
			
			return super.onKeyDown(keyCode, event);
		}
	}
	
	private void sendNotice(){
		
		Map<String ,String > map = new HashMap<String, String>();
		map.put("refresh", "visist");
		XPPApplication.sendChangeBroad(VisitTaskActivity.this, XPPApplication.REFRESH_SHOP_RECEIVER, map);
		XPPApplication.exit(VisitTaskActivity.this);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		 if (e2.getX() - e1.getX() > 100) { 
			 	sendNotice();
				return true;
		 }
		return false;
	}

	public void onLongPress(MotionEvent arg0) {
	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	public void onShowPress(MotionEvent arg0) {
		
	}

	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}
	

}
