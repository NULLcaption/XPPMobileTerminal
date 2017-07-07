package com.xpp.moblie.adapter.stockage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.adapter.product.ProductAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.KunnrStockDate;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.query.Stock;
import com.xpp.moblie.screens.KunnrStockActivity;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.util.ToastUtil;
import com.xpp.moblie.util.WaitingDialogUtil;

import android.R.integer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;

/**
 * Title: 库存提报后数据列表 
 * @Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年12月9日 上午10:13:54
 */
public class KunnrStockListAdapter extends BaseExpandableListAdapter {
	public List<List<Stock>> childArray = new ArrayList<List<Stock>>();
	public List<Stock> groupArray = new ArrayList<Stock>();
	private LayoutInflater childInflater;
	private LayoutInflater groupInflater;
	private Activity activity;
	private String checkTime, flag;
	private Customer customer;
	private Button btn_unit;
	private TextView market_quantity_all = (TextView) KunnrStockActivity.market_quantity_all;

	public KunnrStockListAdapter(Customer customer, Activity activity,
			List<List<Stock>> childArray, List<Stock> groupArray,
			String checkTime, String flag) {
		this.market_quantity_all.setText(KunnrStockActivity
				.getQuantityAll(childArray) + "");
		this.childArray = childArray;
		this.groupArray = groupArray;
		this.childInflater = LayoutInflater.from(activity);
		this.groupInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
		this.checkTime = checkTime;
		this.flag = flag;
		this.customer = customer;
	}

	// private Handler mHandler = new Handler(){
	//
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 1:
	// System.out.println("我要刷新数据！");
	// KunnrStockListAdapter.this.notifyDataSetChanged();
	// break;
	// }
	// };
	// };
	public Object getChild(int groupPosition, int childPosition) {
		return childArray.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		System.out.println(" getChildView" + groupPosition + ":"
				+ childPosition);
		ViewHodler hodler = new ViewHodler();

		// do your stuff

		// set it as the view

		if (convertView == null) {
			convertView = childInflater.inflate(R.layout.child_kunnr_stock_c,
					null);
			convertView.setTag(hodler);
			hodler.tv_production_date = (TextView) convertView
					.findViewById(R.id.production_date);
			hodler.tv_quantity = (TextView) convertView
					.findViewById(R.id.quantity);
			hodler.tv_type = (TextView) convertView.findViewById(R.id.type);

		} else {
			hodler = (ViewHodler) convertView.getTag();
			resetViewHolder(hodler);
		}

		final int gn = groupPosition;
		final int cn = childPosition;
		hodler.tv_type.setText(Status.FINISHED.equals(childArray
				.get(groupPosition).get(childPosition).getStatus()) ? "已上传"
				: "待上传");
		if (XPPApplication.TAB_SALES_DAY.equals(flag)
				|| XPPApplication.TAB_KUNNR_WEEK.equals(flag)) {
			hodler.tv_production_date.setText(activity
					.getString(R.string.market_quantity) + "→ → → →");
		} else {
			hodler.tv_production_date.setText(activity
					.getString(R.string.kunnr_stock_age)
					+ childArray.get(groupPosition).get(childPosition)
							.getProductionDate());
		}
		hodler.tv_quantity.setText(childArray.get(groupPosition)
				.get(childPosition).getQuantity()
				+ childArray.get(groupPosition).get(childPosition)
						.getUnitDesc());

		if (flag.equals(XPPApplication.TAB_SALES_DAY)
				|| (childArray.get(groupPosition).get(childPosition)
						.getCheckTime().equals(KunnrStockDate
						.findStockDateByType(flag)))) {
			convertView.setOnClickListener(new OnClickListener() {//

						public void onClick(View v) {
							View overView = View.inflate(activity,
									R.layout.dialog_stock_sku, null);
							final Dialog overDialog = new Dialog(activity,
									R.style.dialog_xw);
							overDialog.setContentView(overView);
							final EditText quantity;
							final DatePicker datePicker;
							final LinearLayout ll;
							TextView tvName = (TextView) overView
									.findViewById(R.id.tvName);
							ll = (LinearLayout) overView.findViewById(R.id.ll);
							datePicker = (DatePicker) overView
									.findViewById(R.id.datePicker1);
							quantity = (EditText) overView
									.findViewById(R.id.quantity);
							quantity.setKeyListener(numberKeyListener);
							if (XPPApplication.TAB_SALES_DAY.equals(flag)) {
								ll.setVisibility(View.GONE);
								tvName.setText(childArray.get(gn).get(cn)
										.getCategorySortDesc());
							} else {
								if (XPPApplication.TAB_KUNNR_WEEK.equals(flag)) {
									ll.setVisibility(View.GONE);
								}
								// System.out.println("打印行数"+gn);
								tvName.setText(childArray.get(gn).get(cn)
										.getCategoryDesc());
								String[] str = childArray.get(gn).get(cn)
										.getProductionDate().split("-");
								datePicker.init(Integer.valueOf(str[0]),
										Integer.valueOf(str[1]), 0,
										new OnDateChangedListener() {
											public void onDateChanged(
													DatePicker view, int year,
													int monthOfYear,
													int dayOfMonth) {
												Log.i("lhp", year + "-"
														+ monthOfYear);

											}
										});
								Calendar c = Calendar.getInstance();

								datePicker.setMaxDate(c.getTimeInMillis());
								c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 100);// 控制时间范围
								datePicker.setMinDate(c.getTimeInMillis());
								((ViewGroup) ((ViewGroup) datePicker
										.getChildAt(0)).getChildAt(0))
										.getChildAt(2).setVisibility(View.GONE);

							}
							btn_unit = (Button) overView
									.findViewById(R.id.unit);
							btn_unit.setText(childArray.get(gn).get(cn)
									.getUnitDesc());
							quantity.requestFocus();
							XPPApplication.openKeyboard(quantity, activity);

							Button overCancel = (Button) overView
									.findViewById(R.id.dialog_cancel_btn);
							overCancel
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											XPPApplication.closeKeyboard(
													quantity, activity);
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
												|| "".equals(quantity.getText()
														.toString())) {
											ToastUtil.show(activity, "数量不能为空");
											// }else
											// if(Double.valueOf(quantity.getText().toString())==0){
											// ToastUtil.show(activity,
											// "数量不能为0");
										} else {
											// 添加品项去重复
											XPPApplication.closeKeyboard(
													quantity, activity);
											childArray
													.get(gn)
													.get(cn)
													.setQuantity(
															Double.valueOf(quantity
																	.getText()
																	.toString()));
											if (!XPPApplication.TAB_SALES_DAY
													.equals(flag)) {
												// arthur.lin
												// 修改库龄日期格式
												String kunnerdate = "";
												int year = datePicker.getYear();
												int month = datePicker
														.getMonth() + 1;
												if (month < 10) {
													kunnerdate = year + "-0"
															+ month;
												} else {
													kunnerdate = year + "-"
															+ month;
												}
												childArray
														.get(gn)
														.get(cn)
														.setProductionDate(
																kunnerdate);
												// childArray.get(gn).get(cn).setProductionDate(datePicker.getYear()+"-"+(datePicker.getMonth()+1));
											}
											// childArray.get(gn).get(cn).setUnitCode(unitCode);
											// childArray.get(gn).get(cn).setUnitDesc(btn_unit.getText().toString());
											childArray
													.get(gn)
													.get(cn)
													.setStatus(
															Status.UNSYNCHRONOUS);
											// childArray.get(gn).get(cn).update();
											KunnrStockListAdapter.this
													.notifyDataSetChanged();
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

			/** 长按删除 **/
			convertView.setOnLongClickListener(new OnLongClickListener() {
				public boolean onLongClick(View arg0) {

					View overView = View.inflate(activity,
							R.layout.dialog_confirmation, null);
					final Dialog overDialog = new Dialog(activity,
							R.style.dialog_xw);
					overDialog.setContentView(overView);
					Button overCancel2 = (Button) overView
							.findViewById(R.id.dialog_cancel_btn);
					overCancel2.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							overDialog.cancel();
						}
					});
					Button overOk2 = (Button) overView
							.findViewById(R.id.dialog_ok_btn);
					overOk2.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {

							class uploadGroupTask extends
									AsyncTask<List<Stock>, Integer, String> {
								Dialog dialog = null;
								WaitingDialogUtil wdu = new WaitingDialogUtil(
										dialog, activity);

								protected void onPreExecute() {
									// wdu.showWaitingDialog();
								}

								protected String doInBackground(
										List<Stock>... arg0) {
									List<Stock> l = arg0[0];
									return DataProviderFactory.getProvider()
											.uploadKunnrStock(l, "D");
								}

								protected void onPostExecute(String result) {
									System.out.println("删除1" + result);
									if (XPPApplication.UPLOAD_SUCCESS
											.equals(result)) {
										System.out.println("删除2" + result);
										childArray.get(gn).get(cn).delete();
										// childArray.get(gn).remove(childArray.get(gn).get(cn));
										// if(childArray.get(gn)==null||childArray.get(gn).size()==0){
										// groupArray.remove(groupArray.get(gn));
										// }
										if (childArray.get(gn).size() <= 1) {
											groupArray.remove(groupArray
													.get(gn));
											childArray.remove(childArray
													.get(gn));
										} else {
											childArray.get(gn).remove(
													childArray.get(gn).get(cn));
										}
										KunnrStockListAdapter.this
												.notifyDataSetChanged();
										market_quantity_all.setText(KunnrStockActivity
												.getQuantityAll(childArray)
												+ "");
										System.out.println("删除5："
												+ KunnrStockActivity
														.getQuantityAll(childArray));

									} else {
										System.out.println("删除3" + result);
										ToastUtil.show(activity,
												"删除失败，请检查网络....");
									}
									// wdu.dismissWaitingDialog();
								}
							}

							if (childArray.get(gn).get(cn).getId() != null) {// 不为空，删除后台数据
								List<Stock> l = new ArrayList<Stock>();
								l.add(childArray.get(gn).get(cn));
								new uploadGroupTask().execute(l);
							} else {
								childArray.get(gn).get(cn).delete();
								// childArray.get(gn).remove(childArray.get(gn).get(cn));
								// if(childArray.get(gn)==null||childArray.get(gn).size()==0){
								// groupArray.remove(groupArray.get(gn));
								//
								// }
								if (childArray.get(gn).size() <= 1) {
									groupArray.remove(groupArray.get(gn));
									childArray.remove(childArray.get(gn));
								} else {
									childArray.get(gn).remove(
											childArray.get(gn).get(cn));
								}
								KunnrStockListAdapter.this
										.notifyDataSetChanged();
								market_quantity_all.setText(KunnrStockActivity
										.getQuantityAll(childArray) + "");
								System.out.println("删除4："
										+ KunnrStockActivity
												.getQuantityAll(childArray));
							}
							// Message message = new Message();
							// message.what = 1;
							// mHandler.sendMessage(message);
							KunnrStockListAdapter.this.notifyDataSetChanged();

							System.out.println("删除6："
									+ KunnrStockActivity
											.getQuantityAll(childArray));
							overDialog.cancel();
						}
					});
					overDialog.show();
					return false;
				}
			});
		}

		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return childArray.get(groupPosition).size();
	}

	protected class ViewHodler {
		TextView tv_production_date = null;
		TextView tv_quantity = null;
		TextView tv_type = null;

	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_production_date.setText(null);
		pViewHolder.tv_quantity.setText(null);
		pViewHolder.tv_type.setText(null);

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

	// private SimpleAdapter adapter1;
	// private List<Map<String, String>> unitList = new ArrayList<Map<String,
	// String>>();
	// private String[] unitstr =null;
	// private String[] unitIdstr = null;
	// private String unitCode ;
	// private final OnClickListener unitListener = new OnClickListener() {
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.unit:
	// // unitList = new ArrayList<Map<String, String>>();
	// // Map<String, String> map;
	// // for (int i = 0; i < unitIdstr.length; i++) {
	// // map = new HashMap<String, String>();
	// // map.put("unitCode", unitIdstr[i]);
	// // map.put("unitDesc", unitstr[i]);
	// // unitList.add(map);
	// // }
	// // adapter1 = new SimpleAdapter(activity, unitList,
	// // R.layout.child_simple_view, new String[] {
	// // "unitDesc", "unitCode" },
	// // new int[] { R.id.unitDesc, R.id.unitCode});
	// //
	// // new AlertDialog.Builder(activity)
	// // .setTitle("请选择")
	// // .setIcon(android.R.drawable.ic_dialog_info)
	// // .setSingleChoiceItems(adapter1, 0,
	// // new DialogInterface.OnClickListener() {
	// // public void onClick(
	// // DialogInterface dialog,
	// // int which) {
	// // btn_unit.setText(unitList.get(which).get("unitDesc"));
	// // unitCode= unitList.get(which).get("unitCode");
	// // dialog.dismiss();
	// // }
	// // }).setNegativeButton("取消", null).show();
	// break;
	// }
	// }
	// };

	/** group **/

	@Override
	public Object getGroup(int groupPosition) {
		return groupArray.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupArray.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// System.out.println("打印：getGroupView");
		System.out.println(" g打印：getGroupView" + groupPosition);
		GroupViewHodler ghodler = null;
		if (convertView == null) {
			ghodler = new GroupViewHodler();
			convertView = groupInflater.inflate(R.layout.child_kunnr_stock,
					null);
			ghodler.tv_categoryName = (TextView) convertView
					.findViewById(R.id.categoryName);
			ghodler.rl_01 = (RelativeLayout) convertView
					.findViewById(R.id.rl_01);
			ghodler.add = (ImageView) convertView.findViewById(R.id.add);
			ghodler.tubiao = (ImageView) convertView.findViewById(R.id.tubiao);
			convertView.setTag(ghodler);
		} else {
			ghodler = (GroupViewHodler) convertView.getTag();
			resetgroupViewHolder(ghodler);
		}

		if (groupArray.get(groupPosition).isAdd()) {
			ghodler.rl_01.setVisibility(View.GONE);
			ghodler.add.setVisibility(View.VISIBLE);
		} else {
			ghodler.rl_01.setVisibility(View.VISIBLE);
			ghodler.add.setVisibility(View.GONE);
			ghodler.tv_categoryName.setText(groupArray.get(groupPosition)
					.getCategoryDesc()); // XPPApplication.TAB_SALES_DAY.equals(flag)?groupArray.get(groupPosition).getCategorySortDesc()
			// 箭头
			if (isExpanded) {
				ghodler.tubiao.setImageResource(R.drawable.btn_browser2);
			} else {
				ghodler.tubiao.setImageResource(R.drawable.btn_browser);
			}
		}
		final int n = groupPosition;

		if (flag.equals(XPPApplication.TAB_SALES_DAY)
				|| (groupArray.get(groupPosition).isAdd() && KunnrStockDate
						.findStockDateByType(flag).equals(
								groupArray.get(groupPosition).getCheckTime()))) {
			ghodler.add.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					showProductList("1");
				}
			});
		}
		if (flag.equals(XPPApplication.TAB_SALES_DAY)
				|| (!groupArray.get(n).isAdd() && KunnrStockDate
						.findStockDateByType(flag).equals(
								groupArray.get(n).getCheckTime()))) {// 有效期内可以删除服务器数据
			ghodler.tv_categoryName
					.setOnLongClickListener(new OnLongClickListener() {
						public boolean onLongClick(View v) {
							View overView = View.inflate(activity,
									R.layout.dialog_confirmation, null);
							final Dialog overDialog = new Dialog(activity,
									R.style.dialog_xw);
							overDialog.setContentView(overView);
							// TextView TextView02 =(TextView)
							// overView.findViewById(R.id.TextView02);
							// TextView02.setText(XPPApplication.TAB_SALES_DAY.equals(flag)?groupArray.get(n).getCategorySortDesc():groupArray.get(n).getCategoryDesc());
							Button overCancel2 = (Button) overView
									.findViewById(R.id.dialog_cancel_btn);
							overCancel2
									.setOnClickListener(new OnClickListener() {
										public void onClick(View v) {
											overDialog.cancel();
										}
									});
							Button overOk2 = (Button) overView
									.findViewById(R.id.dialog_ok_btn);
							overOk2.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {

									List<Stock> ll = Stock.findChild(flag,
											checkTime, customer.getCustId(),
											groupArray.get(n).getCategoryId());// (XPPApplication.TAB_SALES_DAY.equals(flag)
																				// ?
																				// groupArray.get(n).getCategorySortId():
																				// groupArray.get(n).getCategoryId())
									List<Stock> l = new ArrayList<Stock>();
									for (Stock stock : ll) {// 循环本地缓存
										if (stock.getId() != null) {// 不为空，加入删除后台数据list
											l.add(stock);
										}
									}
									if (l != null && l.size() != 0) {// l不为空
										new uploadKunnrStockTask().execute(l);
									}
									Stock.deleteAll(ll);// 删本地缓存
									groupArray.remove(groupArray.get(n));
									childArray.remove(childArray.get(n));
									KunnrStockListAdapter.this
											.notifyDataSetChanged();
									overDialog.cancel();
								}
							});

							overDialog.show();
							return false;

						}

					});
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	private void resetgroupViewHolder(GroupViewHodler gViewHodler) {
		gViewHodler.tv_categoryName.setText(null);
		gViewHodler.add.setVisibility(View.VISIBLE);
		gViewHodler.rl_01.setVisibility(View.VISIBLE);
	}

	private class GroupViewHodler {
		TextView tv_categoryName = null;
		ImageView tubiao = null;
		ImageView add = null;
		RelativeLayout rl_01 = null;
	}

	private class uploadKunnrStockTask extends
			AsyncTask<List<Stock>, Integer, String> {

		protected void onPreExecute() {
		}

		protected String doInBackground(List<Stock>... arg0) {
			List<Stock> l = arg0[0];
			return DataProviderFactory.getProvider().uploadKunnrStock(l, "D");
		}

		protected void onPostExecute(String result) {
			if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
				market_quantity_all.setText(KunnrStockActivity
						.getQuantityAll(childArray) + "");
			}

		}
	}

	public void showProductList(final String type) {
		View view = View.inflate(activity, R.layout.dialog_order_style, null);
		final Dialog dialog = new Dialog(activity, R.style.dialog_xw);
		dialog.setContentView(view);
		final EditText et_parameter = (EditText) view
				.findViewById(R.id.parameter);

		ListView lv_product = (ListView) view.findViewById(R.id.lv_product1);
		final ProductAdapter pAdapter = new ProductAdapter(customer,
				Product.findProducts(type), activity, checkTime, flag);
		lv_product.setAdapter(pAdapter);
		et_parameter.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				pAdapter.productList = Product.findByName(et_parameter
						.getText().toString(), type, flag);
				pAdapter.notifyDataSetChanged();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		Button cancel = (Button) view.findViewById(R.id.dialog_cancel_btn);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		Button ok = (Button) view.findViewById(R.id.dialog_ok_btn);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO保存数据
				List<Stock> ll = new ArrayList<Stock>();
				for (Stock stock : pAdapter.getResultList()) {
					// ll= Stock.findby(flag, customer.getCustId(),
					// (XPPApplication.TAB_SALES_DAY.equals(flag)?stock.getCategorySortId():stock.getCategoryId()),
					// checkTime,
					// stock.getProductionDate(),
					// stock.getUnitDesc());//检查缓存中是否有该id的数据
					ll = Stock.findby(flag, customer.getCustId(),
							stock.getCategoryId(), checkTime,
							stock.getProductionDate(), stock.getUnitDesc());// 检查缓存中是否有该id的数据
					if (ll == null || ll.size() == 0) {
						stock.save();
					} else {
						ll.get(0).setQuantity(
								stock.getQuantity() + ll.get(0).getQuantity());
						ll.get(0).setStatus(Status.UNSYNCHRONOUS);
						ll.get(0).update();
					}
				}
				List<List<Stock>> childArray = new ArrayList<List<Stock>>();
				List<Stock> groupArray = new ArrayList<Stock>();
				groupArray = Stock.findbyFlagAndCheckTime(flag, checkTime,
						customer.getCustId());
				childArray = Stock.findChildArray(flag, checkTime,
						customer.getCustId(), groupArray);
				KunnrStockListAdapter.this.groupArray = groupArray;
				KunnrStockListAdapter.this.childArray = childArray;
				KunnrStockListAdapter.this.notifyDataSetChanged();
				market_quantity_all.setText(KunnrStockActivity
						.getQuantityAll(childArray) + "");
				dialog.cancel();
			}
		});
		dialog.show();
	}

}
