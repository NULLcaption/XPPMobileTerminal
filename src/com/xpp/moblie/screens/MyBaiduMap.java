package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.List;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.query.Info;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.TextView;

public class MyBaiduMap extends Activity {
	BitmapDescriptor mCurrentMarker;
	MapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	// 初始化全局 bitmap 信息，不用时及时 recycle
	private BitmapDescriptor mIconMaker;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.my_baidu_map);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
	
		
		mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);// 地图缩放级别
		mBaiduMap.setMapStatus(msu);
		
		
		
		
		
		
		initMarkerClickEvent();
		
		if(XPPApplication.mapinfo.size()>0){
		addInfosOverlay(XPPApplication.mapinfo);
		}else{
//			ToastUtil.show(MyBaiduMap.this, "暂无商家位置信息");
		}
		
	}
	
	 
	  
	
	
			  
   
   /**
	 * 初始化图层
	 */
	public void addInfosOverlay(List<Info> infos)
	{
		mBaiduMap.clear();
		LatLng latLng = null;// 划线
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		List<LatLng> points = new ArrayList<LatLng>();
		
		
		
		for (Info info : infos)
		{
			// 获取位置
			latLng = new LatLng(info.getLatitude(), info.getLongitude());
			points.add(latLng);
			// 图标
			overlayOptions = new MarkerOptions().position(latLng).icon(mIconMaker).zIndex(5);
			marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", info);
			marker.setExtraInfo(bundle);
		}
		//绘制路线
		if(XPPApplication.mapinfo.size()>1){
			OverlayOptions ooPolyline = new PolylineOptions().width(5).color(0xAAFF0000).points(points);
			mBaiduMap.addOverlay(ooPolyline);
		}else{
		
		}
		
		  for(int i=0;i<infos.size();i++){
			   LatLng  latLng2 = new LatLng(infos.get(i).getLatitude(), infos.get(i).getLongitude());
			   if(i==0){
					OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
							.fontSize(24).fontColor(0xFFFF00FF).text("终点").rotate(-30)
							.position(latLng2);
					mBaiduMap.addOverlay(ooText);
			}else if(i==infos.size()-1){
						OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
								.fontSize(24).fontColor(0xFFFF00FF).text("起点").rotate(-30)
								.position(latLng2);
						mBaiduMap.addOverlay(ooText);
					}
		   }
		
		// 将地图移到到最后一个经纬度位置
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(u);
		
		
		
	}
	
	
	 
	
	
	private void initMarkerClickEvent()
	{
		// 对Marker的点击
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(final Marker marker)
			{
				// 获得marker中的数据
				Info info = (Info) marker.getExtraInfo().get("info");
				InfoWindow mInfoWindow;
				// 生成一个TextView用户在地图中显示InfoWindow
				TextView location = new TextView(getApplicationContext());
				location.setBackgroundResource(R.drawable.location_tips);
				location.setPadding(30, 20, 30, 50);
				location.setText(info.getName());
				// 将marker所在的经纬度的信息转化成屏幕上的坐标
				final LatLng ll = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				// 为弹出的InfoWindow添加点击事件
				mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(location), llInfo,-47,
						new OnInfoWindowClickListener()
						{

							@Override
							public void onInfoWindowClick()
							{
								// 隐藏InfoWindow
								mBaiduMap.hideInfoWindow();
							}
						});
				// 显示InfoWindow
				mBaiduMap.showInfoWindow(mInfoWindow);
//				// 设置详细信息布局为可见
//				mMarkerInfoLy.setVisibility(View.VISIBLE);
//				// 根据商家信息为详细信息布局设置信息
//				popupInfo(mMarkerInfoLy, info);
				return true;
			}
		});
	}
//   /**
//	 * 定位SDK监听函数
//	 */
//	public class MyLocationListenner implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			// map view 销毁后不在处理新接收的位置
//			if (location == null || mMapView == null)
//				return;
//			MyLocationData locData = new MyLocationData.Builder()
//					.accuracy(location.getRadius())
//					// 此处设置开发者获取到的方向信息，顺时针0-360
//					.direction(100).latitude(location.getLatitude())
//					.longitude(location.getLongitude()).build();
//			mBaiduMap.setMyLocationData(locData);
//			if (isFirstLoc) {
//				isFirstLoc = false;
//				LatLng ll = new LatLng(location.getLatitude(),
//						location.getLongitude());
//				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//				mBaiduMap.animateMapStatus(u);
//			}
//		}
//
//		public void onReceivePoi(BDLocation poiLocation) {
//		}
//	}
	
	

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
//		// 退出时销毁定位
//		mLocClient.stop();
		// 关闭定位图层
//		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		
		super.onDestroy();
	}
}

