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
 * Title: �ͻ������¼�����������
 * (ò���Ǹ������ķ���)
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016��12��12�� ����3:03:49
 */
public class CustomerLocationActivity extends Activity {
	// ��λ���
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

	// UI���
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
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
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		// MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13.0f);// ��ͼ���ż���
		// mBaiduMap.setMapStatus(msu);
		// ������λͼ��
		Intent intent = this.getIntent();
		Customer customer = (Customer) intent.getSerializableExtra("customer");
		// System.out.println(customer);
		if (customer.getLatitude() == null || "".equals(customer.getLatitude())) {
			Toast toast = Toast.makeText(getApplicationContext(),
					customer.getCustName() + "û�о�γ��,��ͨ�������ͷ�ջ�ȡ",
					Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		//

		//
		// �趨���ĵ�����
		LatLng p = new LatLng(Double.parseDouble(customer.getLatitude()),
				Double.parseDouble(customer.getLongitude()));

		// �����ͼ״̬
		MapStatus mMapStatus = new MapStatus.Builder().target(p).zoom(15)
				.build();
		// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯

		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// �ı��ͼ״̬
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		mBaiduMap.setMyLocationEnabled(true);

		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		myListener = new MyLocationListenner();
		// System.out.println(customer);
		fugaiwuSingleBulid(customer);
		dianjishijian();
		// ����intent����ʱ���������ĵ�Ϊָ����
		// customers=new ArrayList<Customer>();
		// List<Customer> customers_m=Customer.findAllCustomer();//�ֻ�����ͻ�
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

	// �����������ʾ��
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
						// toast1=Toast.makeText(getApplicationContext(),"��ӿͻ���"+mapCustomer.get(bundle.getString("custid")).getCustName()+"���ݷ��б���",
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
			// System.out.println("�ݷ��б��еĿͻ�������"+Customer.findIsVisitShop());
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
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if ((null == tv1_jingdu || "".equals(tv1_jingdu))
					&& (null == tv2_weidu || "".equals(tv2_weidu))) {
				tv1_jingdu = String.valueOf(location.getLongitude());
				tv2_weidu = String.valueOf(location.getLatitude());
				// new MyCustomer().execute();//ʵʱ��ȡ�˺�Ȩ�޿�ѡ�Ŀͻ�list
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

	// ��Ӹ�����
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
		jidus = customer.getLatitude();// γ��
		weidus = customer.getLongitude();// ����
		// ����Maker�����
		if (jidus != null && weidus != null) {
			LatLng point = new LatLng(Double.valueOf(jidus),
					Double.valueOf(weidus));
			// ����Markerͼ��
			BitmapDescriptor bitmap = null;
			if (isVisitCustomer(customer)) {// �ݷù�
				bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.green0);
				// ����MarkerOption�������ڵ�ͼ�����Marker
			} else {
				bitmap = BitmapDescriptorFactory.fromResource(R.drawable.maker);
			}
			String name = "���ƣ�"
					+ customer.getCustName()
					+ "|��ַ��"
					+ (customer.getAddress() == null ? "" : customer
							.getAddress());
			Bundle mBundle = new Bundle();
			mBundle.putString("custid", customer.getCustId());
			// System.out.println("�ͻ���ţ�"+customer.getCustId());
			// OverlayOptions option = new
			// MarkerOptions().position(point).icon(bitmap).title(name);
			OverlayOptions option = new MarkerOptions().position(point)
					.icon(bitmap).title(name).extraInfo(mBundle);
			// �ڵ�ͼ�����Marker������ʾ
			mapCustomer.put(customer.getCustId(), customer);
			// System.out.println("��ӡ��ͼ��"+mapCustomer);
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
		// ��ͷ��
		if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.DTZ) != 0) {
			photo_state = 1;
		}
		// ���
		if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.LDZ) != 0) {
			left_state = 1;
		}
		// �̻�
		if (Distribution.getRecordsCount(customer.getCustId()).size() != 0) {
			distribution_state = 1;
		}

		// ����
		if (DisPlay.getRecordsCount(customer.getCustId()).size() != 0) {
			disPlay_state = 1;
		}

		// �۸�
		if (AbnormalPrice.getRecordsCount(customer.getCustId()).size() != 0) {
			abnormalPrice_state = 1;
		}

		// ����
		if (Inventory.getRecordsCount(customer.getCustId()).size() != 0) {
			inventory_state = 1;
		}

		if (customer.getIsActivity() != null) {// ���г��
			if (MarketCheck.getRecordsCount(customer.getCustId()).size() != 0) {
				marketCheck_state = 1;
			}
		}
		//���� 
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
		// ���ƶ��������С��
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
		// �˳�ʱ���ٶ�λ
		super.onDestroy();

		// �رն�λͼ��
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
