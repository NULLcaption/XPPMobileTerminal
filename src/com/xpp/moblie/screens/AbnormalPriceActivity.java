package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Product;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AbnormalPriceActivity extends Activity {

	private ListView lv_abnormalPriceList;
	private List<Product> productAbnormalPriceList;
	private Customer shop;
	private TextView tv_custName;
	private boolean falg = false;
	private Button btn_product, btn_competingProduct;
	private String type = "1";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_abnormal_price);
		initView();
		initData();
	}

	public void initView() {
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.save).setOnClickListener(BtnClicked);
		tv_custName = (TextView) findViewById(R.id.custName);
		btn_product = (Button) findViewById(R.id.product);
		btn_product.setOnClickListener(BtnClicked);
		btn_competingProduct = (Button) findViewById(R.id.competingProduct);
		btn_competingProduct.setOnClickListener(BtnClicked);
		lv_abnormalPriceList = (ListView) findViewById(R.id.abnormalPriceList);

	}

	public void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
		}
		tv_custName.setText(shop.getCustName() + "__"
				+ getString(R.string.abnormal_price_input));
		new AbnormalPriceTask().execute();
	}

	private class AbnormalPriceTask extends
			AsyncTask<Object, Integer, List<Product>> {
		protected List<Product> doInBackground(Object... arg0) {
			productAbnormalPriceList = new ArrayList<Product>();
			AbnormalPrice abnormalPrice = new AbnormalPrice();
			for (Product product : Product.getPriceSkuList(shop.getCustId())) {
				abnormalPrice = new AbnormalPrice();
				abnormalPrice = AbnormalPrice.findByCustIdAndCate(
						shop.getCustId(), product.getCategoryId());
				if (abnormalPrice != null) {
					product.setPrice(abnormalPrice.getAbnormalPrice());
				}
				productAbnormalPriceList.add(product);

			}
			return productAbnormalPriceList;
		}

		protected void onPostExecute(List<Product> result) {
			List<Product> list = new ArrayList<Product>();
			for (Product product : result) {
				if (product.getProductType().equals(type)) {
					list.add(product);
				}
			}
			AbnormalPriceListAdapter abnormalPriceListAdapter = new AbnormalPriceListAdapter(
					AbnormalPriceActivity.this, list);
			lv_abnormalPriceList.setAdapter(abnormalPriceListAdapter);
		}

	}

	public class AbnormalPriceListAdapter extends BaseAdapter {
		private List<Product> productList = new ArrayList<Product>();
		private LayoutInflater layoutInflater;

		public AbnormalPriceListAdapter(Activity activity,
				List<Product> productList) {
			this.productList = productList;
			this.layoutInflater = LayoutInflater.from(getApplicationContext());
		}

		public int getCount() {
			return productList.size();
		}

		public Object getItem(int position) {
			return productList.get(position);
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
						R.layout.child_abnormal_price, null);
				hodler.tv_categoryName = (TextView) convertView
						.findViewById(R.id.categoryName);
				hodler.tv_errorPrice = (TextView) convertView
						.findViewById(R.id.errorPrice);
				hodler.tv_errorPriceUnit = (TextView) convertView
						.findViewById(R.id.errorPriceUnit);

				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}
			hodler.tv_categoryName.setText(productList.get(position)
					.getCategoryDesc());
			hodler.tv_errorPrice.setText(productList.get(position).getPrice());
			if (productList.get(position).getPrice() != null) {
				hodler.tv_errorPriceUnit.setText(getString(R.string.bei));
			}

			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {

					View overdiaView = View.inflate(AbnormalPriceActivity.this,
							R.layout.dialog_price_input, null);
					final Dialog overdialog = new Dialog(
							AbnormalPriceActivity.this, R.style.dialog_xw);
					overdialog.setContentView(overdiaView);
					overdialog.setCanceledOnTouchOutside(false);
					Button overcancel = (Button) overdiaView
							.findViewById(R.id.dialog_cancel_btn);

					final EditText money = (EditText) overdiaView
							.findViewById(R.id.errorPrice);
					money.setKeyListener(numberKeyListener);

					/** 自动打开软键盘 */
					money.requestFocus();
					Timer timer = new Timer(); // 设置定时器
					timer.schedule(new TimerTask() {
						@Override
						public void run() { // 弹出软键盘的代码
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.showSoftInput(money,
									InputMethodManager.RESULT_SHOWN);
							imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
									InputMethodManager.HIDE_IMPLICIT_ONLY);
						}
					}, 300);

					// money.setKeyListener(DialerKeyListener.getInstance());//键盘默认数字
					money.setKeyListener(new NumberKeyListener() {
						public int getInputType() {
							// 0无键盘 1英文键盘 2模拟键盘 3数字键盘
							return 3;
						}

						@Override
						protected char[] getAcceptedChars() {
							char[] c = { '0', '1', '2', '3', '4', '5', '6',
									'7', '8', '9', '.' };
							return c;
						}
					});
					overcancel.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// 关闭键盘
							InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(money.getWindowToken(),
									0);
							overdialog.cancel();
						}
					});
					Button overok = (Button) overdiaView
							.findViewById(R.id.dialog_ok_btn);

					overok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (!money.getText().toString().equals("")
									&& money.getText() != null) {
								productAbnormalPriceList.get(n).setPrice(
										money.getText().toString());
								// 关闭键盘
								InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(
										money.getWindowToken(), 0);
								overdialog.cancel();
							} else {
								Toast.makeText(getApplicationContext(),
										"价格不能为空", 1).show();
							}
						}
					});
					overdialog.show();

				}
			});
			return convertView;
		}

	}

	protected class ViewHodler {
		TextView tv_categoryName = null;
		TextView tv_errorPrice = null;
		TextView tv_errorPriceUnit = null;

	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_categoryName.setText(null);
		pViewHolder.tv_errorPrice.setText(null);
		pViewHolder.tv_errorPriceUnit.setText(null);
	}

	/** 按键监听 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(AbnormalPriceActivity.this);
				break;
			case R.id.save:
				if (saveAbnormal()) {
					if (falg) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("type", "abnormalPrice");
						XPPApplication.sendChangeBroad(
								AbnormalPriceActivity.this,
								XPPApplication.UPLOADDATA_RECEIVER, map);
						Toast.makeText(getApplicationContext(),
								getString(R.string.save_success),
								Toast.LENGTH_SHORT).show();

					}
				} else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_fail), Toast.LENGTH_SHORT)
							.show();
				}

				XPPApplication.exit(AbnormalPriceActivity.this);
				break;
			case R.id.product:
				btn_product.setBackgroundResource(R.drawable.btn_orange);
				btn_competingProduct
						.setBackgroundResource(R.drawable.btn_grey_selector);
				btn_product.setEnabled(false);
				btn_competingProduct.setEnabled(true);

				break;
			case R.id.competingProduct:
				btn_competingProduct
						.setBackgroundResource(R.drawable.btn_orange);
				btn_product.setBackgroundResource(R.drawable.btn_grey_selector);
				btn_competingProduct.setEnabled(false);
				btn_product.setEnabled(true);
				break;
			}
		}
	};

	private boolean saveAbnormal() {
		for (Product abp : productAbnormalPriceList) {
			if (abp.getPrice() != null) {
				AbnormalPrice i = AbnormalPrice.findByCustIdAndCate(
						shop.getCustId(), abp.getCategoryId());
				if (i == null) {
					AbnormalPrice abnormalPrice = new AbnormalPrice(
							shop.getCustId(), abp.getCategoryId(),
							abp.getCategoryDesc(), Status.UNSYNCHRONOUS,
							DataProviderFactory.getDayType(), "Y",
							abp.getPrice(), getString(R.string.bei),
							DataProviderFactory.getUserId());
					abnormalPrice.save();
					falg = true;
				} else {
					// 原有数据upadte 再上传新的一条
					if (!i.getAbnormalPrice().equals(abp.getPrice())) {
						i.setAbnormalPrice(abp.getPrice());
						i.setStatus(Status.UNSYNCHRONOUS);
						i.update();
						falg = true;
					}
				}
			}
		}
		return true;
	}

	private final NumberKeyListener numberKeyListener = new NumberKeyListener() {
		public int getInputType() {
			// 0无键盘 1英文键盘 2模拟键盘 3数字键盘
			return 3;
		}

		@Override
		protected char[] getAcceptedChars() {
			char[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' };// ,'.'
			return c;
		}
	};

	// 重写手机返回按钮功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(AbnormalPriceActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
