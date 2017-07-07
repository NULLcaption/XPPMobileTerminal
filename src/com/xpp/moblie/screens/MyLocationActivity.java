package com.xpp.moblie.screens;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

























import com.baidu.lbsapi.auth.e;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.UpdateTask;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.Order;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.util.PhotoUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MyLocationActivity extends Activity {
	
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();;
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	private Dialog waitingDialog;
	//private Map<String, Customer> mapCustomer=new HashMap<String, Customer>();
	MapView mMapView;
	BaiduMap mBaiduMap;
	private BitmapDescriptor mIconMaker;
	private InfoWindow mInfoWindow;
	private List<Customer> customers=null;//手机缓存客户
	private List<Customer> allcustomers=null;//全部客户
	private Button showButton;
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位
	private String tv1_jingdu;
	private String tv2_weidu;
	private Map<String, Customer> map;
	private MyCustomer myCustomer;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.mylocationa_ctivity);
		showButton=(Button)findViewById(R.id.showCustomers);
		showButton.setText("拜访列表客户");
		showButton.setTag("1");
		requestLocButton = (Button) findViewById(R.id.button1);
		mCurrentMode = LocationMode.NORMAL;
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);// 地图缩放级别
		mBaiduMap.setMapStatus(msu);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		//myListener = new MyLocationListenner();
		customers=new ArrayList<Customer>();
		List<Customer> customers_m=Customer.findAllCustomer();//手机缓存客户
		map=new HashMap<String, Customer>();
		for (int i = 0; i < customers_m.size(); i++) {
			String custId=customers_m.get(i).getCustId();
			map.put(custId, customers_m.get(i));
			customers.add(customers_m.get(i));
		}
	
		dianjishijian();
		fugaiwu();
	}
	
public void showCustomers(View view) {
	
	if ("1".equals(showButton.getTag())) {
		if (allcustomers==null) {
			showWaitingDialog();
		}
		
		showButton.setText("附近客户");
		showButton.setTag("0");
		mBaiduMap.clear();
		myCustomer=new MyCustomer();
		myCustomer.execute();
		fugaiwu();
		
	} else {
		if (myCustomer != null && myCustomer.getStatus() != AsyncTask.Status.FINISHED)
 	   {myCustomer.cancel(true);
 	   }
		showButton.setText("拜访列表客户");
		showButton.setTag("1");
		mBaiduMap.clear();
		fugaiwu();
		
	}
	
	
}
private void allfugaiwu(){
	int i=0;
	if(allcustomers!=null){
	for (Customer customer : allcustomers) {
		if(i%100==0)
		Toast.makeText(getApplicationContext(), "正在加载，请耐心等待！",
				Toast.LENGTH_LONG ).show();
		i++;
		System.out.println("i:"+i);
		if (null==map.get(customer.getCustId())) {
			System.out.println(customer);
			fugaiwuSingleBulid(customer);
	
		}
	}
	}
}

	//点击覆盖物显示字
	private void dianjishijian() {
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(final Marker marker) {
				final Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.location_tips);
				OnInfoWindowClickListener listener = null;
				LatLng ll = marker.getPosition();
					String name=marker.getTitle();
					button.setText(name);
					
				listener = new OnInfoWindowClickListener() {
					public void onInfoWindowClick() {
						mBaiduMap.hideInfoWindow();
						Bundle bundle =marker.getExtraInfo();
						Customer customer=(Customer)bundle.getSerializable("customer");
						//System.out.println(customer);
						//System.out.println(bundle.getString("custid"));
						AddVisitTask addVisitTask=new AddVisitTask();
						//addVisitTask.execute(bundle.getString("custid"));
						addVisitTask.execute(customer);
						Toast toast1=Toast.makeText(getApplicationContext(),"添加客户："+customer.getCustName()+"到拜访列表中", Toast.LENGTH_LONG); 			 
						toast1.show(); 
					}
				};
				
				mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
				mBaiduMap.showInfoWindow(mInfoWindow);
				return false;
			}
		});
	}
	
	
	private class AddVisitTask extends
	AsyncTask<Object, Integer, String>{

		@Override
		protected String doInBackground(Object... custs) {
			//Customer customer=(Customer)mapCustomer.get(custId[0]);
		
			Customer customer=(Customer)custs[0];
			System.out.println("customer:"+customer);
			customer.setUserId(DataProviderFactory.getUserId());
			customer.setIsVisitShop("1");
			customer.save();
			customers.add(customer);
			//System.out.println("拜访列表中的客户名单："+Customer.findIsVisitShop());
			//customer.update();
			//System.out.println(customer);
			return DataProviderFactory.getProvider().createCustomer(customer);
		}
		protected void onPostExecute(String result){
			//System.out.println(result);
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("refresh", "isRefresh");
//			XPPApplication.sendChangeBroad(MyLocationActivity.this,
//			XPPApplication.REFRESH_SHOP_RECEIVER, map);
//			XPPApplication.exit(MyLocationActivity.this);
			
		}
	}
	
	
	
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if((null==tv1_jingdu||"".equals(tv1_jingdu))&&(null==tv2_weidu||"".equals(tv2_weidu))){
			   tv1_jingdu=String.valueOf(location.getLongitude());
			   tv2_weidu=String.valueOf(location.getLatitude());
			  // new MyCustomer().execute();//实时获取账号权限可选的客户list
			}
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
	
	//添加覆盖物
	public void fugaiwu(){
		
//		String jidus="";
//		String weidus="";
		for (Customer customer : customers) {
			
				fugaiwuSingleBulid(customer);
			
			
		}
	}

	public void fugaiwuSingleBulid(Customer customer){
		
			//System.out.println(customer.getCustName());
		String jidus="";
		String weidus="";
		jidus=customer.getLatitude();//纬度
		 weidus=customer.getLongitude();//经度
		 //定义Maker坐标点  
		 if(jidus!=null&&weidus!=null&&!"".equals(jidus)&&!"".equals(weidus)){
		 LatLng  point = new LatLng(Double.valueOf(jidus), Double.valueOf(weidus));  
		 //构建Marker图标  
		 BitmapDescriptor bitmap =null;
		 if(isVisitCustomer(customer)){//拜访过
			 bitmap= BitmapDescriptorFactory.fromResource(R.drawable.green0);  
			 //构建MarkerOption，用于在地图上添加Marker  
		 }else{
			 bitmap=BitmapDescriptorFactory.fromResource(R.drawable.maker);
		 }
		 String name="名称："+customer.getCustName()+
		 		"|地址："+(customer.getAddress()==null?"":customer.getAddress());
		 Bundle mBundle=new Bundle();
	     mBundle.putString("custid", customer.getCustId());
	     mBundle.putSerializable("customer", customer);
		// OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).title(name); 
		 OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).title(name).extraInfo(mBundle);
		 //在地图上添加Marker，并显示  
		 //mapCustomer.put(customer.getCustId(), customer);
		 //System.out.println("打印地图："+mapCustomer);
		 if ("1".equals(showButton.getTag())){
			 if ("1".equals(customer.getIsVisitShop())) {
//				 System.out.println("显示拜访客户");
//				System.out.println(customer.getCustName());
				 mBaiduMap.addOverlay(option);
			 }
		 }
		 else {
//			 System.out.println("显示全部客户");
//			 System.out.println(customer.getCustName());
			 mBaiduMap.addOverlay(option);
		}
		 
		 bitmap.recycle();
         System.gc();
		 
		}
	}
	
	public boolean isVisitCustomer(Customer customer){
		int photo_state=0;
		int distribution_state=0;
		int disPlay_state=0;
		int abnormalPrice_state=0;
		int inventory_state=0;
		int marketCheck_state=0;
		int order_state=0;
		int left_state=0;
		// 店头照
		if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.DTZ) != 0) {
			photo_state=1;
		}
		// 离店
					if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.LDZ) != 0) {
						left_state=1;
					}
		// 铺货
		if (Distribution.getRecordsCount(customer.getCustId()).size() != 0) {
			distribution_state=1;
		}
		
		// 陈列
		if (DisPlay.getRecordsCount(customer.getCustId()).size() != 0) {
			disPlay_state=1;
		}
		
		// 价格
		if (AbnormalPrice.getRecordsCount(customer.getCustId()).size() != 0) {
			abnormalPrice_state=1;
		}

		// 货龄
		if (Inventory.getRecordsCount(customer.getCustId()).size() != 0) {
			inventory_state=1;
		}

		if (customer.getIsActivity() != null) {// 有市场活动
			if (MarketCheck.getRecordsCount(customer.getCustId()).size() != 0) {
				marketCheck_state=1;
			} 
		}
//		//订单 //TODO
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
		//绘制订单管理的小点
			if(flag){
				if (Order.getRecordsCount(customer.getCustId()).size() != 0) {
					order_state=1;
				}
			}
	if(photo_state==1||distribution_state==1||disPlay_state==1||abnormalPrice_state==1||inventory_state==1||marketCheck_state==1||order_state==1||left_state==1){
		return true;
	}else{
		return false;
	}
	}
	
	
	
	public class MyCustomer extends AsyncTask<String, Integer, List<Customer>>{
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(isCancelled()) {
				dismissWaitingDialog();
				return ;}
			Toast.makeText(getApplicationContext(), "已经加载"+values[0]+" 家门店！",
					Toast.LENGTH_LONG ).show();
			
		
		}
		protected void onPreExecute() {
			if(isCancelled()) {
				dismissWaitingDialog();
				return ;}
			Toast.makeText(getApplicationContext(), "正在加载，请耐心等待！",
					Toast.LENGTH_LONG ).show();
			
		}
		@Override
		protected List<Customer> doInBackground(String... arg0) {
			if(isCancelled()) {
			dismissWaitingDialog();
			return null;
			}
			if (UpdateTask.getInstance().getStatus() == AsyncTask.Status.RUNNING) {
				UpdateTask.getInstance().waitTimeout();
			}
			allCustomerTask task=new allCustomerTask();
			task.setDaemon(true);
			task.start();
		
			try {
				task.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dismissWaitingDialog();
		
			int i=1;
			if(allcustomers!=null){
			for (Customer customer : allcustomers) {
				if(isCancelled()) return null;
				//System.out.println("i:"+i);
				if (null==map.get(customer.getCustId())) {
					//System.out.println(customer);
					fugaiwuSingleBulid(customer);
			
				}
				if(i%100==0)
					publishProgress(i);
				i++;
			}
			}
//			if (allcustomers==null) {
//				return DataProviderFactory.getProvider().getCustomerDw("DW",tv1_jingdu,tv2_weidu);
//			} else {
//				return allcustomers;
//			}
			return allcustomers;
		}

		@Override
		protected void onPostExecute(List<Customer> custList) {
			if(isCancelled()) return ;
			Toast.makeText(getApplicationContext(), "加载完毕，总共 "+custList.size()+"家门店",
					Toast.LENGTH_LONG ).show();
//			System.out.println("客户名字打印");
			
//			if (null!=custList) {
//				allcustomers=custList;
//				int i=0;
//				if(allcustomers!=null){
//				for (Customer customer : allcustomers) {
//					if(i%100==0)
//						publishProgress(i);
//					i++;
//					System.out.println("i:"+i);
//					if (null==map.get(customer.getCustId())) {
//						//System.out.println(customer);
//						fugaiwuSingleBulid(customer);
//				
//					}
//				}
//				}
//			}
			
	}
		



		
		
	}
	@Override
	protected void onPause(){
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
		Map<String, String> map = new HashMap<String, String>();
		map.put("refresh", "isRefresh");
		XPPApplication.sendChangeBroad(MyLocationActivity.this,
		XPPApplication.REFRESH_SHOP_RECEIVER, map);
		XPPApplication.exit(MyLocationActivity.this);
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
					dismissWaitingDialog();
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
	 public  class allCustomerTask extends Thread  
	 { 
	       
	     @Override  
	     public void run()  {
	    	 if (allcustomers==null){
	    		 
	       allcustomers=DataProviderFactory.getProvider().getCustomerDw("DW",tv1_jingdu,tv2_weidu);
	       }
	     }
	     }
	 @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK )  
	        {  
	        	   if (myCustomer != null && myCustomer.getStatus() != AsyncTask.Status.FINISHED)
	        	   {myCustomer.cancel(true);
	        	   }
	        	   finish();  
//	            // 创建退出对话框  
//	            AlertDialog isExit = new AlertDialog.Builder(this).create();  
//	            // 设置对话框标题  
//	            isExit.setTitle("系统提示");  
//	            // 设置对话框消息  
//	            isExit.setMessage("确定要退出吗");  
//	            // 添加选择按钮并注册监听  
//	            isExit.setButton("确定", listener);  
//	            isExit.setButton2("取消", listener);  
//	            // 显示对话框  
//	            isExit.show();  
	  
	        }  
	          
	        return false;  
	          
	    }  
	 DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
	    {  
	        public void onClick(DialogInterface dialog, int which)  
	        {  
	            switch (which)  
	            {  
	            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
	                finish();  
	                break;  
	            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
	                break;  
	            default:  
	                break;  
	            }  
	        }  
	    };    
	
	 
	 
}
