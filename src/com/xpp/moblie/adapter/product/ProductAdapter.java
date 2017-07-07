package com.xpp.moblie.adapter.product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.query.Stock;
import com.xpp.moblie.screens.KunnrStockActivity;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.util.MyDatePickerDialog;
import com.xpp.moblie.util.MyUtil;
import com.xpp.moblie.util.ToastUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * @Title: ProductAdapter.java
 * @Package com.xpp.moblie.adapter.product
 * @Description: TODO
 * @author will.xu
 * @date 2014年5月12日 上午10:54:05
 */

public class ProductAdapter extends BaseAdapter {

	public List<Product> productList = new ArrayList<Product>();
	private LayoutInflater layoutInflater;
	private Activity activity;
	private Customer customer;
	private List<Map<String, String>> unitList;
	private Button btn_unit;
	private String unitCode, checkTime, flag;
	private String[] unitstr = null;
	private String[] unitIdstr = null;
	private List<Stock> resultList = new ArrayList<Stock>();

	public ProductAdapter(Customer customer, List<Product> productList,
			Activity activity, String checkTime, String flag) {
		this.productList = productList;
		this.activity = activity;
		this.checkTime = checkTime;
		this.flag = flag;
		this.customer = customer;
		// //TODO 暂时写死单位
		// unitList = new ArrayList<Map<String, String>>();
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("unitDesc", "箱");
		// map.put("unitCode", "xiang");
		// unitList.add(map);
		// map = new HashMap<String, String>();
		// map.put("unitDesc", "提");
		// map.put("unitCode", "ti");
		// unitList.add(map);
		// map = new HashMap<String, String>();
		// map.put("unitDesc", "杯");
		// map.put("unitCode", "bei");
		// unitList.add(map);
	}

	public List<Stock> getResultList() {
		return resultList;
	}

	public int getCount() {
		return productList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return productList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler = null;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.child_order_style_list, null);
			hodler.tv_product = (TextView) convertView
					.findViewById(R.id.tvProduct);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
			resetViewHolder(hodler);
		}

		hodler.tv_product.setText(productList.get(position).getCategoryDesc());
		final int n = position;
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				View overView = View.inflate(activity,
						R.layout.dialog_stock_sku, null);
				final Dialog overDialog = new Dialog(activity,
						R.style.dialog_xw);
				overDialog.setContentView(overView);
				final EditText quantity;
				final DatePicker datePicker;
				final LinearLayout ll;
				TextView tvName = (TextView) overView.findViewById(R.id.tvName);
				ll = (LinearLayout) overView.findViewById(R.id.ll);
				quantity = (EditText) overView.findViewById(R.id.quantity);
				quantity.setKeyListener(numberKeyListener);
				datePicker = (DatePicker) overView
						.findViewById(R.id.datePicker1);
				if (XPPApplication.TAB_SALES_DAY.equals(flag)) {
					ll.setVisibility(View.GONE);
					tvName.setText(productList.get(n).getCategoryDesc());
				} else {
					if (XPPApplication.TAB_KUNNR_WEEK.equals(flag)) {
						ll.setVisibility(View.GONE);
					}
					tvName.setText(productList.get(n).getCategoryDesc());
					Calendar c = Calendar.getInstance();
					datePicker.init(c.get(Calendar.YEAR),
							c.get(Calendar.MONTH),
							c.get(Calendar.DAY_OF_MONTH),
							new OnDateChangedListener() {
								public void onDateChanged(DatePicker view,
										int year, int monthOfYear,
										int dayOfMonth) {

								}
							});
					
					datePicker.setMaxDate(c.getTimeInMillis());
					c.set(Calendar.YEAR, c.get(Calendar.YEAR)-100);//控制时间范围
					datePicker.setMinDate(c.getTimeInMillis());
					((ViewGroup) ((ViewGroup) datePicker.getChildAt(0))
							.getChildAt(0)).getChildAt(2).setVisibility(
							View.GONE);
				}

				btn_unit = (Button) overView.findViewById(R.id.unit);
				if (productList.get(n).getSkuUnit() != null
						|| productList.get(n).getSkuUnitId() != null) {
					unitstr = productList.get(n).getSkuUnit().split(",");
					unitIdstr = productList.get(n).getSkuUnitId().split(",");
					btn_unit.setText(unitstr[0]);
					unitCode = unitIdstr[0];
				} else {
					ToastUtil.show(activity, "请维护单位！");
				}

				// btn_unit.setOnClickListener(unitListener);
				quantity.requestFocus();
				XPPApplication.openKeyboard(quantity, activity);

				Button overCancel = (Button) overView
						.findViewById(R.id.dialog_cancel_btn);
				overCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						XPPApplication.closeKeyboard(quantity, activity);
						overDialog.cancel();
					}
				});
				Button overOk = (Button) overView
						.findViewById(R.id.dialog_ok_btn);
				overOk.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							if (quantity.getText() == null
									|| "".equals(quantity.getText().toString())) {
								ToastUtil.show(activity, "数量不能为空");
//							} else if (Double.valueOf(quantity.getText()
//									.toString()) == 0) {
//								ToastUtil.show(activity, "数量不能为0");
							} else {
								boolean isContain = true;
								for (int i = 0; i < resultList.size(); i++) {
									if ( resultList.get(i).getCategoryId().equals(productList.get(n).getCategoryId())//XPPApplication.TAB_SALES_DAY
											//.equals(flag) ? resultList.get(i).getCategorySortId().equals(productList.get(n).getCategorySortId()):
											&& resultList.get(i).getUnitCode().equals(unitCode)
											&& ( resultList.get(i).getProductionDate()//XPPApplication.TAB_SALES_DAY.equals(flag) ? true:
															.equals(datePicker.getYear()
																	+ "-"
																	+ (datePicker
																			.getMonth() + 1)
																	+ "-"
																	+ datePicker
																			.getDayOfMonth()))) {
										resultList.get(i).setQuantity(
												Double.valueOf(quantity
														.getText().toString()));
										isContain = false;
										break;
									}
								}
								if (isContain) {
									//arthur.lin
									//修改库龄日期格式
//									String  kunnerdate="";
//									int year=datePicker.getYear();
//									int month=datePicker.getMonth()+1;
//									if(month<10){
//									kunnerdate=year+"-0"+month;
//									}else{
//									String kunnerdate= datePicker.getYear() + "-" 
//											+ MyUtil.addZeroForNum(String.valueOf(datePicker.getMonth()+1), 2);
									Stock s = new Stock(
											customer.getCustId(),
											DataProviderFactory.getUserId(),
											DataProviderFactory.getDayType(),
											productList.get(n).getCategoryId(),
											productList.get(n)
													.getCategoryDesc(),
											productList.get(n)
													.getCategorySortId(),
											productList.get(n)
													.getCategorySortDesc(),
											unitCode,
											btn_unit.getText().toString(),
											Double.valueOf(quantity.getText()
													.toString()),
													( datePicker.getYear() + "-" 
																		+ MyUtil.addZeroForNum(String.valueOf(datePicker.getMonth()+1), 2)),
//											( datePicker.getYear()
//															+ "-"
//															+ (datePicker
//																	.getMonth() + 1)),//XPPApplication.TAB_SALES_DAY.equals(flag) ? null:
											checkTime, flag,
											Status.UNSYNCHRONOUS);
									resultList.add(s);
								}
								// 添加品项去重复
								XPPApplication
										.closeKeyboard(quantity, activity);
								overDialog.cancel();
							}
						} catch (Exception e) {
							ToastUtil.show(activity, "输入有误，请重新输入");
							e.printStackTrace();
						}
					}

				});
				overDialog.show();
			}

		});

		return convertView;
	}

	protected class ViewHodler {
		TextView tv_product = null;
	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_product.setText(null);
	}

	private SimpleAdapter adapter1;

	private final OnClickListener unitListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.unit:
				unitList = new ArrayList<Map<String, String>>();
				Map<String, String> map;
				for (int i = 0; i < unitIdstr.length; i++) {
					map = new HashMap<String, String>();
					map.put("unitCode", unitIdstr[i]);
					map.put("unitDesc", unitstr[i]);
					unitList.add(map);
				}
				adapter1 = new SimpleAdapter(activity, unitList,
						R.layout.child_simple_view, new String[] { "unitDesc",
								"unitCode" }, new int[] { R.id.unitDesc,
								R.id.unitCode });

				new AlertDialog.Builder(activity)
						.setTitle("请选择")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setSingleChoiceItems(adapter1, 0,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										btn_unit.setText(unitList.get(which)
												.get("unitDesc"));
										unitCode = unitList.get(which).get(
												"unitCode");
										dialog.dismiss();
									}
								}).setNegativeButton("取消", null).show();
				break;
			}
		}
	};

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

}
