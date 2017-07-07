package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.List;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.query.CuanhuoQuery;
import com.xpp.moblie.util.TimeUtil;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;





/**
 * @Title: OrderTotalActivity.java
 * @Package com.xpp.kunnr.moblie.screens
 * @Description: orderTotal
 * @author will.xu
 * @date 2014年3月4日 下午2:04:12
 */
public class ChuanhaolistActivity extends Activity {
	private Button home_back;

//	List<OldOrder> oldOrderlist=new ArrayList<OldOrder>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chuanhuo_list);
		initView();
		initData();
		


	}
	
	
	
	private void initView() {
		home_back = (Button) findViewById(R.id.home_back);
		home_back.setOnClickListener(BtnClicked);
		
		
	}
	
	
	private void initData() {
		ListView clist = (ListView)findViewById(R.id.chuanhaoList);
		List<CuanhuoQuery> cuanhuolist = CuanhuoQuery.findAll();//
		if (cuanhuolist==null || cuanhuolist.size()==0) {
			Toast.makeText(getApplicationContext(), "没有查到数据...",
					Toast.LENGTH_SHORT).show();
		}
		SettingAdapter settingAdapter = new SettingAdapter(
				getApplicationContext(), cuanhuolist);
		clist.setAdapter(settingAdapter);
		


		
	
		
		
		
	}
	
	public void onDestroy() {
		super.onDestroy();
		
	}
	private OnClickListener BtnClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(ChuanhaolistActivity.this);
				break;
			
			}
		}
	};
	

	   
	   
	   
		// 重写手机返回按钮功能
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
					XPPApplication.exit(ChuanhaolistActivity.this);
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
		public class SettingAdapter extends BaseAdapter {
			private List<CuanhuoQuery> data = new ArrayList<CuanhuoQuery>();//
			private LayoutInflater layoutInflater;

			public SettingAdapter(Context context, List<CuanhuoQuery> data) {
				System.out.println("print data"+data);
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
				ViewHodler hodler = null;
				if (convertView == null) {
					// 获取组件布局
					hodler = new ViewHodler();
					convertView = layoutInflater.inflate(
							R.layout.layout_chuanhao_list_child, null);
					hodler.custName = (TextView) convertView
							.findViewById(R.id.custName);
					hodler.poddate = (TextView) convertView
							.findViewById(R.id.poddate);
					hodler.skuname = (TextView) convertView
							.findViewById(R.id.skuname);
					hodler.tocustName = (TextView) convertView
							.findViewById(R.id.tocustName);
					hodler.orderDesc = (TextView) convertView
							.findViewById(R.id.orderDesc);
					convertView.setTag(hodler);
				} else {
					hodler = (ViewHodler) convertView.getTag();
					resetViewHolder(hodler);
				}

				hodler.custName.setText("客户:" + data.get(position).getKUNAG_NAME());
				String endate=data.get(position).getPODAT();
				if (!"".equals(endate.trim())) {
					endate=TimeUtil.enTimetoCn(endate);
					System.out.println(endate);
				}
				hodler.poddate.setText("到货日期:" + endate);
				hodler.skuname.setText("品项:" + data.get(position).getMAKTX());
				hodler.tocustName.setText("送达方:" + data.get(position).getKUNWE_NAME());
				hodler.orderDesc.setText("销售订单:" + data.get(position).getVBELN_VA());
				// 绑定数据、以及事件触发
				
				return convertView;
			}
		}		
		protected class ViewHodler {
			TextView custName = null;
			TextView poddate = null;
			TextView skuname = null;
			TextView tocustName = null;
			TextView orderDesc = null;
		}

		protected void resetViewHolder(ViewHodler pViewHolder) {
			pViewHolder.custName.setText(null);
			pViewHolder.poddate.setText(null);
			pViewHolder.skuname.setText(null);
			pViewHolder.tocustName.setText(null);
			pViewHolder.orderDesc.setText(null);

		}	
}
