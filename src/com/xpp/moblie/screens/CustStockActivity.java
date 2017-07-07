package com.xpp.moblie.screens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.adapter.stockage.SkuUnitListAdapter;
import com.xpp.moblie.adapter.stockage.StockAgeListAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.query.SkuUnit;
import com.xpp.moblie.query.StockReport;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Title: 门店分销量提报 Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016年11月22日 上午9:04:27
 */
public class CustStockActivity extends Activity {
	private ListView lv_stockAgeList;
	private Customer customer;
	private TextView tv_custName;
	private StockAgeListAdapter stockAgeListAdapter;
	private SkuUnitListAdapter skuUnitListAdapter;
	private Dialog waitingDialog;

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
		if (customer != null) {
			tv_custName.setText(customer.getCustName() + "__"
					+ getString(R.string.home_CustStock_management));
			GetStockReportTask getStockReportTask = new GetStockReportTask();
			getStockReportTask.execute("");
			// skuUnitListAdapter=new SkuUnitListAdapter(CustStockActivity.this,
			// SkuUnit.findAll(), customer, StockReport
			// .findByCustId(customer.getCustId()));
			// lv_stockAgeList.setAdapter(skuUnitListAdapter);

		}
	}

	/** 按键监听 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(CustStockActivity.this);
				break;
			case R.id.save:
				if (saveCustStock()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "StockReport");
					XPPApplication.sendChangeBroad(CustStockActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_success),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_fail), Toast.LENGTH_SHORT)
							.show();
				}
				XPPApplication.exit(CustStockActivity.this);
				break;

			}
		}

	};

	// 重写手机返回按钮功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(CustStockActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private boolean saveCustStock() {
		try {
			List<StockReport> list = skuUnitListAdapter.getInvList();
			StockReport i;
			for (StockReport inventory : list) {
				i = StockReport.findByParameter(customer.getCustId(),
						inventory.getSkuId(), inventory.getCheckTime());
				if (i == null) {
					i = new StockReport();
					i.setCustId(customer.getCustId());
					i.setSkuId(inventory.getSkuId());
					i.setSku_name(inventory.getSku_name());
					i.setFlag("sales_day");
					i.setCheckTime(inventory.getCheckTime());
					i.setProductionDate(inventory.getProductionDate());
					i.setCategoryId(inventory.getCategoryId());
					i.setStatus(Status.UNSYNCHRONOUS);
				} else {
					if (!inventory.getQuantity().equals(i.getQuantity())) {
						i.setStatus(Status.UNSYNCHRONOUS);
					} else {
						continue;
					}

				}

				// System.out.println("DataProviderFactory.getRoleId():"+DataProviderFactory.getRoleId());
				if ("mobile_dd".equals(DataProviderFactory.getRoleId())) {
					i.setUserType("D");
				} else {
					i.setUserType("A");
				}
				i.setUserId(DataProviderFactory.getUserId());
				i.setUnitDesc(inventory.getUnitDesc());
				i.setQuantity(inventory.getQuantity());
				i.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

		return true;
	}

	public class GetStockReportTask extends AsyncTask<String, Integer, String> {
		protected void onPreExecute() {
			showWaitingDialog();

		}

		@Override
		protected String doInBackground(String... custId) {
			DataProviderFactory.getProvider().getCustomerStockByCustid(
					customer.getCustId());
			return null;
		}

		@Override
		protected void onPostExecute(String signResult) {
			dismissWaitingDialog();
			skuUnitListAdapter = new SkuUnitListAdapter(CustStockActivity.this,
					SkuUnit.findAll(), customer,
					StockReport.findByCustId(customer.getCustId()));
			lv_stockAgeList.setAdapter(skuUnitListAdapter);

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
