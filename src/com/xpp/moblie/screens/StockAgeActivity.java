package com.xpp.moblie.screens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.adapter.stockage.StockAgeListAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.Product;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StockAgeActivity extends Activity {
	private ListView lv_stockAgeList;
	private Customer customer;
	private TextView tv_custName;
	private StockAgeListAdapter stockAgeListAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_stock_age);
		initView();
		initData();
	}
	private void initView() {
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.save).setOnClickListener(BtnClicked);
		lv_stockAgeList = (ListView) findViewById(R.id.stockAgeList);
		tv_custName = (TextView) findViewById(R.id.custName);
		
	}

	private void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			customer = (Customer) bun.get("custInfo");
		}
		if(customer!=null){
		tv_custName.setText(customer.getCustName() + "__"
				+ getString(R.string.inventory));
		 stockAgeListAdapter = new StockAgeListAdapter(StockAgeActivity.this,
				Product.findProducts("4"), customer,Inventory
				.findByCustId(customer.getCustId()));
		lv_stockAgeList.setAdapter(stockAgeListAdapter);

            
           
            
            
   
		System.out.println("货领管理");
		}
	}
	

	/** 按键监听 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(StockAgeActivity.this);
				break;
			case R.id.save:
				if(saveStockAge()){
					Map<String, String> map = new HashMap<String, String>();
				map.put("type", "inventory");
				XPPApplication.sendChangeBroad(StockAgeActivity.this,
						XPPApplication.UPLOADDATA_RECEIVER, map);
				Toast.makeText(getApplicationContext(),
						getString(R.string.save_success),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						getString(R.string.save_fail),
						Toast.LENGTH_SHORT).show();
			}
				XPPApplication.exit(StockAgeActivity.this);
				break;
				
			}
		}

	};
	
	// 重写手机返回按钮功能
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				XPPApplication.exit(StockAgeActivity.this);
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
	
	private boolean  saveStockAge(){
		try{
			List<Inventory> list =  stockAgeListAdapter.getInvList();
			Inventory i ;
			for (Inventory inventory : list) {
				i =  Inventory.findByParameter(customer.getCustId(), inventory.getCategorySortId(),inventory.getYear(),inventory.getMonth());
				if(i==null){//创建
					i = new Inventory(customer.getCustId(), inventory.getCategorySortId(), inventory.getCategorySortDecs(),
							DataProviderFactory.getUserId(), DataProviderFactory.getDayType(), Status.UNSYNCHRONOUS,
							inventory.getYear(), inventory.getMonth());
					i.setUnit(inventory.getUnit());
					i.setQuantity(inventory.getQuantity());
					i.save();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;

		}
		
		return true;
	}
	
	
}
