package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
/**
 * Title: 客户本地事件（？？？）
 * (貌似是个废弃的方法)
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年12月12日 下午3:03:49
 */
public class CustomerLocationActivity extends Activity {
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	private Map<String, Customer> mapCustomer = new HashMap<String, Customer>();
	MapView mMapView;
	BaiduMap mBaiduMap;
	private BitmapDescriptor mIconMaker;
	private InfoWindow mInfoWindow;
	private List<Customer> customers;

	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位
	private String tv1_jingdu;
	private String tv2_weidu;
	private Map<String, Customer> map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.customer_location);
		// setContentView(R.layout.mylocationa_ctivity);
		requestLocButton = (Button) findViewById(R.id.button1);
		mCurrentMode = LocationMode.NORMAL;
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		// MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13.0f);// 地图缩放级别
		// mBaiduMap.setMapStatus(msu);
		// 开启定位图层
		Intent intent = this.getIntent();
		Customer customer = (Customer) intent.getSerializableExtra("customer");
		// System.out.println(customer);
		if (customer.getLatitude() == null || "".equals(customer.getLatitude())) {
			Toast toast = Toast.makeText(getApplicationContext(),
					customer.getCustName() + "没有经纬度,请通过拍摄店头照获取",
					Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		//

		//
		// 设定中心点坐标
		LatLng p = new LatLng(Double.parseDouble(customer.getLatitude()),
				Double.parseDouble(customer.getLongitude()));

		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().target(p).zoom(15)
				.build();
		// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
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
		myListener = new MyLocationListenner();
		// System.out.println(customer);
		fugaiwuSingleBulid(customer);
		dianjishijian();
		// 当用intent参数时，设置中心点为指定点
		// customers=new ArrayList<Customer>();
		// List<Customer> customers_m=Customer.findAllCustomer();//手机缓存客户
		// map=new HashMap<String, Customer>();
		// for (int i = 0; i < customers_m.size(); i++) {
		// String custId=customers_m.get(i).getCustId();
		// map.put(custId, customers_m.get(i));
		// customers.add(customers_m.get(i));
		// }
		//
		// dianjishijian();
		// fugaiwu();

		// System.out.println(customer);
	}

	// 点击覆盖物显示字
	private void dianjishijian() {
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(final Marker marker) {
				final Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.location_tips);
				OnInfoWindowClickListener listener = null;
				LatLng ll = marker.getPosition();
				String name = marker.getTitle();
				button.setText(name);

				listener = new OnInfoWindowClickListener() {
					public void onInfoWindowClick() {
						mBaiduMap.hideInfoWindow();
						// Bundle bundle =marker.getExtraInfo();
						// System.out.println(bundle.getString("custid"));
						// AddVisitTask addVisitTask=new AddVisitTask();
						// addVisitTask.execute(bundle.getString("custid"));
						// Toast
						// toast1=Toast.makeText(getApplicationContext(),"添加客户："+mapCustomer.get(bundle.getString("custid")).getCustName()+"到拜访列表中",
						// Toast.LENGTH_LONG);
						// toast1.show();
					}
				};

				mInfoWindow = new InfoWindow(BitmapDescriptorFactory
						.fromView(button), ll, -47, listener);
				mBaiduMap.showInfoWindow(mInfoWindow);
				return false;
			}
		});
	}

	private class AddVisitTask extends AsyncTask<Object, Integer, String> {

		@Override
		protected String doInBackground(Object... custId) {
			Customer customer = (Customer) mapCustomer.get(custId[0]);
			customer.setUserId(DataProviderFactory.getUserId());
			customer.setIsVisitShop("1");
			customer.save();
			// System.out.println("拜访列表中的客户名单："+Customer.findIsVisitShop());
			// customer.update();
			// System.out.println(customer);
			return DataProviderFactory.getProvider().createCustomer(customer);
		}

		protected void onPostExecute(String result) {
			// System.out.println(result);
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("refresh", "isRefresh");
			// XPPApplication.sendChangeBroad(MyLocationActivity.this,
			// XPPApplication.REFRESH_SHOP_RECEIVER, map);
			// XPPApplication.exit(MyLocationActivity.this);

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
			if ((null == tv1_jingdu || "".equals(tv1_jingdu))
					&& (null == tv2_weidu || "".equals(tv2_weidu))) {
				tv1_jingdu = String.valueOf(location.getLongitude());
				tv2_weidu = String.valueOf(location.getLatitude());
				// new MyCustomer().execute();//实时获取账号权限可选的客户list
			}
			if (isFirstLoc) {
				isFirstLoc = false;
				// LatLng ll = new
				// LatLng(location.getLatitude(),location.getLongitude());
				// MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				// mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	// 添加覆盖物
	public void fugaiwu() {

		// String jidus="";
		// String weidus="";
		for (Customer customer : customers) {
			fugaiwuSingleBulid(customer);
		}
	}

	public void fugaiwuSingleBulid(Customer customer) {
		String jidus = "";
		String weidus = "";
		jidus = customer.getLatitude();// 纬度
		weidus = customer.getLongitude();// 经度
		// 定义Maker坐标点
		if (jidus != null && weidus != null) {
			LatLng point = new LatLng(Double.valueOf(jidus),
					Double.valueOf(weidus));
			// 构建Marker图标
			BitmapDescriptor bitmap = null;
			if (isVisitCustomer(customer)) {// 拜访过
				bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.green0);
				// 构建MarkerOption，用于在地图上添加Marker
			} else {
				bitmap = BitmapDescriptorFactory.fromResource(R.drawable.maker);
			}
			String name = "名称："
					+ customer.getCustName()
					+ "|地址："
					+ (customer.getAddress() == null ? "" : customer
							.getAddress());
			Bundle mBundle = new Bundle();
			mBundle.putString("custid", customer.getCustId());
			// System.out.println("客户编号："+customer.getCustId());
			// OverlayOptions option = new
			// MarkerOptions().position(point).icon(bitmap).title(name);
			OverlayOptions option = new MarkerOptions().position(point)
					.icon(bitmap).title(name).extraInfo(mBundle);
			// 在地图上添加Marker，并显示
			mapCustomer.put(customer.getCustId(), customer);
			// System.out.println("打印地图："+mapCustomer);
			mBaiduMap.addOverlay(option);
		}

	}

	public boolean isVisitCustomer(Customer customer) {
		int photo_state = 0;
		int distribution_state = 0;
		int disPlay_state = 0;
		int abnormalPrice_state = 0;
		int inventory_state = 0;
		int marketCheck_state = 0;
		int order_state = 0;
		int left_state = 0;
		// 店头照
		if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.DTZ) != 0) {
			photo_state = 1;
		}
		// 离店
		if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.LDZ) != 0) {
			left_state = 1;
		}
		// 铺货
		if (Distribution.getRecordsCount(customer.getCustId()).size() != 0) {
			distribution_state = 1;
		}

		// 陈列
		if (DisPlay.getRecordsCount(customer.getCustId()).size() != 0) {
			disPlay_state = 1;
		}

		// 价格
		if (AbnormalPrice.getRecordsCount(customer.getCustId()).size() != 0) {
			abnormalPrice_state = 1;
		}

		// 货龄
		if (Inventory.getRecordsCount(customer.getCustId()).size() != 0) {
			inventory_state = 1;
		}

		if (customer.getIsActivity() != null) {// 有市场活动
			if (MarketCheck.getRecordsCount(customer.getCustId()).size() != 0) {
				marketCheck_state = 1;
			}
		}
		//订单 
		List<Dictionary> dicts = Dictionary.findbyTypeValue("isOrderManager");
		boolean flag = true;
		for (Dictionary dict : dicts) {
			if ("N".equals(dict.getItemDesc())) {
				if (dict.getItemValue().equals(DataProviderFactory.getRoleId())) {
					flag = false;
					break;
				}
			}
		}
		// 绘制订单管理的小点
		if (flag) {
			if (Order.getRecordsCount(customer.getCustId()).size() != 0) {
				order_state = 1;
			}
		}
		if (photo_state == 1 || distribution_state == 1 || disPlay_state == 1
				|| abnormalPrice_state == 1 || inventory_state == 1
				|| marketCheck_state == 1 || order_state == 1
				|| left_state == 1) {
			return true;
		} else {
			return false;
		}
	}

	public class MyCustomer extends AsyncTask<String, Integer, List<Customer>> {
		@Override
		protected void onProgressUpdate(Integer... values) {
			if (isCancelled())
				return;

		}

		protected void onPreExecute() {
		}

		@Override
		protected List<Customer> doInBackground(String... arg0) {
			if (isCancelled())
				return null;
			if (UpdateTask.getInstance().getStatus() == AsyncTask.Status.RUNNING) {
				UpdateTask.getInstance().waitTimeout();
			}
			return DataProviderFactory.getProvider().getCustomerDw("DW",
					tv1_jingdu, tv2_weidu);
		}

		@Override
		protected void onPostExecute(List<Customer> custList) {

			if (null != custList) {
				for (Customer customer : custList) {
					if (null == map.get(customer.getCustId())) {
						fugaiwuSingleBulid(customer);

					}
				}
			}
		}

	}

	@Override
	protected void onPause() {
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
		super.onDestroy();

		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		Intent intent = this.getIntent();
		Customer customer = (Customer) intent.getSerializableExtra("customer");
		if (customer.getLatitude() == null || "".equals(customer.getLatitude()))
			return;
		mLocClient.stop();

		// Map<String, String> map = new HashMap<String, String>();
		// map.put("refresh", "isRefresh");
		// XPPApplication.sendChangeBroad(MyLocationActivity.this,
		// XPPApplication.REFRESH_SHOP_RECEIVER, map);
		// XPPApplication.exit(MyLocationActivity.this);
	}
}
