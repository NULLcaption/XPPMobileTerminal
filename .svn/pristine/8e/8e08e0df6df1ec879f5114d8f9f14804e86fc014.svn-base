package com.xpp.moblie.screens;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.xpp.moblie.adapter.stockage.KunnrStockListAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.KunnrStockDate;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Stock;
import com.xpp.moblie.util.MyDatePickerDialog;
import com.xpp.moblie.util.MyImageButton;
import com.xpp.moblie.util.MyUtil;
import com.xpp.moblie.util.PhotoUtil;
import com.xpp.moblie.util.PictureShowUtils;
import com.xpp.moblie.util.TimeUtil;
import com.xpp.moblie.util.ToastUtil;
import com.xpp.moblie.util.WaitingDialogUtil;

/**
 * Title: 经销商库存提报 Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016年11月22日 下午1:36:13
 */

public class KunnrStockActivity extends Activity {
	private Customer shop;
	private TextView tv_custName;
	public static TextView market_quantity_all;
	private TextView quantitytextView;
	private ExpandableListView elv_stockList;
	private ListView lv_checkTimeList;
	private String flag;
	// private List<Stock> stockList;
	private List<String> checkTimeList = new ArrayList<String>();
	private List<String> validTimeList = new ArrayList<String>();
	private Map<String, String> checkTimeFlag = new HashMap<String, String>();
	private CheckTimeListAdapter checkTimeListAdapter;
	private KunnrStockListAdapter kunnrStockListAdapter;
	private Button btn_save, btn_addCheckTime;
	private boolean isDetail = false;
	private Dialog waitingDialog = null;
	private String dir, photoNameAll;
	private List<PhotoInfo> ptList = new ArrayList<PhotoInfo>();
	private int width, height;
	private TableLayout tl_table;
	private PhotoType pt;
	private String checkTimeNow;
	private WaitingDialogUtil wdu;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_kunnr_stock);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		initData();
		initView();
		wdu = new WaitingDialogUtil(waitingDialog, KunnrStockActivity.this);
		// stockList = Stock.findByFlag(flag);
		// 日分销提报显示时间
		checkTimeNow = KunnrStockDate.findStockDateByType(flag);
		if (flag.equals(XPPApplication.TAB_SALES_DAY)) {
			List<String> strList = Stock.findCheckTimeList(flag,
					shop.getCustId());
			long today = TimeUtil.getDateTime(DataProviderFactory.getDayType());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(today);
			TimeUtil.setTIME(c.getTimeInMillis());
			validTimeList.add(TimeUtil.getFormatDate());
			for (int i = 0; i < 3; i++) {
				c.add(Calendar.DAY_OF_MONTH, -1);
				TimeUtil.setTIME(c.getTimeInMillis());
				validTimeList.add(TimeUtil.getFormatDate());
			}
			for (String s : strList) {
				for (String str : validTimeList) {
					if (str.equals(s)) {
						checkTimeList.add(s);
					}
				}
			}
		}
		new GetKunnrStockTask().execute();
		// elv_stockList.setVisibility(View.VISIBLE);
		// lv_checkTimeList.setVisibility(View.GONE);
	}

	/**
	 * MethodsTitle: 初始化数据
	 * 
	 * @author: xg.chen
	 * @date:2016年11月22日 下午1:44:17
	 * @version 1.0
	 */
	public void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
			flag = (String) bun.get("flag");
			pt = (PhotoType) bun.get("photoType");
		}
		tv_custName = (TextView) findViewById(R.id.custName);
		tv_custName.setText(shop.getCustName());
	}

	/**
	 * MethodsTitle: 初始化页面视图
	 * 
	 * @author: xg.chen
	 * @date:2016年11月22日 下午1:44:42
	 * @version 1.0
	 */
	public void initView() {
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		btn_save = (Button) findViewById(R.id.save);
		btn_save.setOnClickListener(BtnClicked);
		btn_addCheckTime = (Button) findViewById(R.id.addCheckTime);
		btn_addCheckTime.setOnClickListener(BtnClicked);
		elv_stockList = (ExpandableListView) findViewById(R.id.stockList);
		lv_checkTimeList = (ListView) findViewById(R.id.checkTimeList);
		market_quantity_all = (TextView) findViewById(R.id.market_quantity_all);
		market_quantity_all.setVisibility(View.GONE);
		quantitytextView = (TextView) findViewById(R.id.quantitytextView);
		quantitytextView.setVisibility(View.INVISIBLE);

	}

	/**
	 * 库存提报时间判断
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	protected Dialog onCreateDialog(int id) {
		MyDatePickerDialog dialog = null;
		// DatePickerDialog dialog=null;
		switch (id) {
		case 1:
			Calendar c = Calendar.getInstance();
			final long today = TimeUtil.getDateTime(DataProviderFactory
					.getDayType());
			c.setTimeInMillis(today);
			dialog = new MyDatePickerDialog(this,
					new MyDatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							// String monthStr="";
							// if(month<9){
							// monthStr="0"+(month+1);
							// }else{
							// monthStr=month+1+"";
							// }
							Calendar c1 = Calendar.getInstance();// 获取当前时间
							c1.setTimeInMillis(today);
							TimeUtil.setTIME(c1.getTimeInMillis());
							long end = c1.getTimeInMillis();// 获取当前毫秒
							c1.add(Calendar.DAY_OF_MONTH, -3);
							long start = c1.getTimeInMillis();// 获取三天前的毫秒
							TimeUtil.setTIME(c1.getTimeInMillis());
							c1.set(year, month, dayOfMonth);// 选定的时间
							long select = c1.getTimeInMillis();// 选定的毫秒
							TimeUtil.setTIME(c1.getTimeInMillis());
							if (select > end || select < start) {
								ToastUtil.show(KunnrStockActivity.this,
										"提报时间不在有效范围内");
								return;
							}
							TimeUtil.setTIME(c1.getTimeInMillis());

							String t = year
									+ "-"
									+ MyUtil.addZeroForNum(
											String.valueOf(month + 1), 2)
									+ "-"
									+ MyUtil.addZeroForNum(
											String.valueOf(dayOfMonth), 2);
							if (!checkTimeList.contains(t)) {
								checkTimeList.add(t);
							}
							if (checkTimeListAdapter == null) {
								checkTimeListAdapter = new CheckTimeListAdapter(
										shop, checkTimeList);
								lv_checkTimeList
										.setAdapter(checkTimeListAdapter);
							} else {
								checkTimeListAdapter.checkTimeList = checkTimeList;
								checkTimeListAdapter.notifyDataSetChanged();
							}

						}
					}, 
					c.get(Calendar.YEAR), // 传入年份
					c.get(Calendar.MONTH), // 传入月份
					c.get(Calendar.DAY_OF_MONTH) // 传入天数
			);

			dialog.setTitle(XPPApplication.TAB_SALES_DAY.equals(flag) ? R.string.kunnr_sales_day
					: R.string.stock_check_time);
			DatePicker dp = dialog.getDatePicker();
			// Calendar c1 = Calendar.getInstance();
			dp.setMaxDate(c.getTimeInMillis());
			dp.setEnabled(false);
			// dp.setMinDate(c.getTimeInMillis());
			// c.set(Calendar.DATE, c.get(Calendar.DATE)-3);
			// //TimeUtil.setTIME(c.getTimeInMillis());
			// //c1.add(Calendar.DAY_OF_MONTH, -3);
			// //Log.i("lhp", TimeUtil.getFormatTime());
			//
			// dp.setMinDate(c.getTimeInMillis());
			// c1.add(Calendar.DAY_OF_MONTH, -3);
			break;
		}
		return dialog;
	}

	/**
	 * 按键监听
	 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				if (isDetail) {
					btn_save.setVisibility(View.INVISIBLE);
					if (!XPPApplication.TAB_SALES_DAY.equals(flag)) {
						btn_addCheckTime.setVisibility(View.INVISIBLE);
					} else {
						btn_addCheckTime.setVisibility(View.VISIBLE);
					}
					lv_checkTimeList.setVisibility(View.VISIBLE);
					elv_stockList.setVisibility(View.GONE);
					quantitytextView.setVisibility(View.INVISIBLE);
					market_quantity_all.setVisibility(View.GONE);
					isDetail = false;
				} else {
					XPPApplication.exit(KunnrStockActivity.this);
				}
				break;
			case R.id.save:
				System.out.println("添加按钮");
				List<Stock> bpList = new ArrayList<Stock>();
				for (List<Stock> list : kunnrStockListAdapter.childArray) {
					for (Stock stock : list) {
						if (Status.UNSYNCHRONOUS.equals(stock.getStatus())) {
							bpList.add(stock);
						}
					}
				}
				if (bpList != null && bpList.size() != 0) {
					new uploadKunnrStockTask().execute(bpList);
				} else {
					System.out.println(getString(R.string.uploaded));
					ToastUtil.show(getApplicationContext(),
							getString(R.string.uploaded));
					isDetail = false;
					elv_stockList.setVisibility(View.GONE);
					lv_checkTimeList.setVisibility(View.VISIBLE);
					btn_save.setVisibility(View.INVISIBLE);
					if (!XPPApplication.TAB_SALES_DAY.equals(flag)) {
						btn_addCheckTime.setVisibility(View.INVISIBLE);
					} else {
						btn_addCheckTime.setVisibility(View.VISIBLE);
					}
				}

				break;
			case R.id.addCheckTime:
				if (flag.equals(XPPApplication.TAB_SALES_DAY)) {
					showDialog(1);
					break;
				} else {
					boolean r = false;
					for (int i = 0; i < checkTimeList.size(); i++) {
						if (checkTimeNow.equals(checkTimeList.get(i))) {
							ToastUtil.show(getApplicationContext(),
									getString(R.string.kunnr_date_rm));
							r = true;
							break;
						}
					}

					if ("1".equals(checkTimeFlag.get(checkTimeNow))) {
						ToastUtil.show(getApplicationContext(),
								getString(R.string.kunnr_date_wxrm));
						r = true;
					}
					if (r) {
						break;
					}
					checkTimeList.add(checkTimeNow);
					checkTimeListAdapter.checkTimeList = checkTimeList;
					checkTimeListAdapter.notifyDataSetChanged();
				}
				break;

			}
		}
	};

	private class GetKunnrStockTask extends AsyncTask<Stock, Integer, Boolean> {
		Stock sp = new Stock();

		protected void onPreExecute() {
			wdu.showWaitingDialog();

			sp.setUserId(DataProviderFactory.getUserId());
			sp.setCustId(shop.getCustId());
			if (!XPPApplication.TAB_SALES_DAY.equals(flag)) {
				// SimpleDateFormat formatter = new
				// SimpleDateFormat("yyyy-mm-dd");
				// sp.setCheckTime(formatter.format(new Date()));
				// }else{
				sp.setCheckTime(checkTimeNow);
				btn_addCheckTime.setVisibility(View.INVISIBLE);
			} else {
				btn_addCheckTime.setVisibility(View.VISIBLE);
			}
			sp.setFlag(flag);
		}

		protected Boolean doInBackground(Stock... arg0) {
			return DataProviderFactory.getProvider().getKunnrStock(sp);
		}

		protected void onPostExecute(Boolean result) {

			elv_stockList.setVisibility(View.GONE);
			lv_checkTimeList.setVisibility(View.VISIBLE);
			// 判断是否为月库存和周库存上报，
			if (checkTimeList == null || checkTimeList.size() == 0) {// 检查时间列表
				if (flag.equals(XPPApplication.TAB_SALES_DAY)) {
					showDialog(1);
				} else {
					if ("无效".equals(checkTimeNow)) {
						checkTimeFlag.put(checkTimeNow, "1");
					} else {
						initPhotoTable(pt);
						checkTimeList.add(checkTimeNow);
						checkTimeFlag.put(checkTimeNow, "2");
					}
				}
			}
			checkTimeListAdapter = new CheckTimeListAdapter(shop, checkTimeList);
			lv_checkTimeList.setAdapter(checkTimeListAdapter);
			wdu.dismissWaitingDialog();
		}

	}

	/**
	 * Title: 获取经销商的提报任务汇总数据 Description: XPPMobileTerminal
	 * 
	 * @author: xg.chen
	 * @date:2016年11月22日 下午2:02:25
	 */
	private class GetSuperKunnrStockInfoTask extends
			AsyncTask<Stock, Integer, String> {
		Stock sp = new Stock();

		protected void onPreExecute() {
			wdu.showWaitingDialog();

			sp.setCustId(shop.getCustId());
			if (!XPPApplication.TAB_SALES_DAY.equals(flag)) {
				// SimpleDateFormat formatter = new
				// SimpleDateFormat("yyyy-mm-dd");
				// sp.setCheckTime(formatter.format(new Date()));
				// }else{
				sp.setCheckTime(checkTimeNow);
				btn_addCheckTime.setVisibility(View.INVISIBLE);
			} else {
				btn_addCheckTime.setVisibility(View.VISIBLE);
			}
			sp.setFlag(flag);
		}

		protected String doInBackground(Stock... arg0) {
			return DataProviderFactory.getProvider().getSuperKunnrStockInfo(sp);
		}

		protected void onPostExecute(String result) {
			if (result != null && !"".equals(result)) {
				String[] arr = result.split(",");
				quantitytextView.setText(arr[0] + "汇总箱数：");
				market_quantity_all.setText(arr[1]);
			}
			wdu.dismissWaitingDialog();
		}
	}

	/**
	 * Title: 点击保存提交数据到数据库 Description: XPPMobileTerminal
	 * 
	 * @author: xg.chen
	 * @date:2016年11月22日 下午2:00:02
	 */
	private class uploadKunnrStockTask extends
			AsyncTask<List<Stock>, Integer, String> {
		protected void onPreExecute() {
			wdu.showWaitingDialog();
		}

		protected String doInBackground(List<Stock>... arg0) {
			List<Stock> l = arg0[0];
			System.out.println("打印"
					+ DataProviderFactory.getProvider().uploadKunnrStock(l,
							null));
			return DataProviderFactory.getProvider().uploadKunnrStock(l, null);
		}

		protected void onPostExecute(String result) {
			if (XPPApplication.UPLOAD_SUCCESS.equals(result)) {
				System.out.println("dayin:"
						+ getString(R.string.upload_success));
				ToastUtil.show(getApplicationContext(),
						getString(R.string.upload_success));
				if (ptList != null && ptList.size() != 0) {
					for (PhotoInfo photoInfo : ptList) {
						PhotoInfo.submitPhoto(photoInfo);
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "photo");
					XPPApplication.sendChangeBroad(KunnrStockActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
				}
				isDetail = false;
				elv_stockList.setVisibility(View.GONE);
				lv_checkTimeList.setVisibility(View.VISIBLE);
				quantitytextView.setVisibility(View.INVISIBLE);
				market_quantity_all.setVisibility(View.GONE);
				btn_save.setVisibility(View.INVISIBLE);
				if (!XPPApplication.TAB_SALES_DAY.equals(flag)) {
					btn_addCheckTime.setVisibility(View.INVISIBLE);
				} else {
					btn_addCheckTime.setVisibility(View.VISIBLE);
				}

			} else if (XPPApplication.UPLOAD_FAIL.equals(result)) {
				ToastUtil.show(getApplicationContext(),
						getString(R.string.upload_fail));
			} else {
				ToastUtil.show(getApplicationContext(), result);
			}
			wdu.dismissWaitingDialog();
		}

	}

	// 重写手机返回按钮功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isDetail) {
				if (flag.equals(XPPApplication.TAB_SALES_DAY)) {
					checkTimeList = Stock.findCheckTimeList(flag,
							shop.getCustId());
				} else {
					if (checkTimeList == null && checkTimeList.size() == 0) {
						if ("无效".equals(checkTimeNow)) {
							checkTimeFlag.put(checkTimeNow, "1");
						} else {
							checkTimeList.add(checkTimeNow);
							checkTimeFlag.put(checkTimeNow, "2");
						}
					}
				}
				checkTimeListAdapter.notifyDataSetChanged();
				btn_save.setVisibility(View.INVISIBLE);
				if (!XPPApplication.TAB_SALES_DAY.equals(flag)) {
					btn_addCheckTime.setVisibility(View.INVISIBLE);
				} else {
					btn_addCheckTime.setVisibility(View.VISIBLE);
				}
				lv_checkTimeList.setVisibility(View.VISIBLE);
				elv_stockList.setVisibility(View.GONE);
				isDetail = false;
			} else {
				XPPApplication.exit(this);
			}
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/****** 检查时间adapter *******/
	public class CheckTimeListAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		public List<String> checkTimeList;// obj1 时间 //obj2 状态

		public CheckTimeListAdapter(Customer customer,
				List<String> checkTimeList) {
			this.layoutInflater = LayoutInflater.from(KunnrStockActivity.this);
			this.checkTimeList = checkTimeList;
		}

		public int getCount() {
			return checkTimeList.size();
		}

		public Object getItem(int position) {
			return checkTimeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;
			if (convertView == null) {
				hodler = new ViewHodler();
				convertView = layoutInflater.inflate(R.layout.child_check_time,
						null);
				hodler.tv_checkDate = (TextView) convertView
						.findViewById(R.id.checkDate);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}
			String mss = "";
			if (XPPApplication.TAB_SALES_DAY.equals(flag)) {
				mss = mss + "每日分销量:";
			} else if (XPPApplication.TAB_KUNNR_WEEK.equals(flag)) {
				mss = mss + "库存所属日期:";
			} else {
				mss = mss + "库存所属月份:";
			}
			String ms = checkTimeList.get(position);
			if (checkTimeFlag.get(ms) != null && !"无效".equals(ms)
					&& "1".equals(checkTimeFlag.get(ms))) {
				ms = ms + "无效";
			}
			mss = mss + ms;

			hodler.tv_checkDate.setText(mss);
			final int n = position;
			final String kunnrStockDate = checkTimeList.get(position);
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					if ("1".equals(checkTimeFlag.get(kunnrStockDate))) {
						ToastUtil.show(getApplicationContext(),
								getString(R.string.kunnr_date_message));
						return;
					}

					isDetail = true;
					btn_save.setVisibility(View.VISIBLE);
					btn_addCheckTime.setVisibility(View.INVISIBLE);
					elv_stockList.setVisibility(View.VISIBLE);
					lv_checkTimeList.setVisibility(View.GONE);
					List<List<Stock>> childArray = new ArrayList<List<Stock>>();
					List<Stock> groupArray = new ArrayList<Stock>();
					groupArray = Stock.findbyFlagAndCheckTime(flag,
							checkTimeList.get(n), shop.getCustId());
					childArray = Stock.findChildArray(flag,
							checkTimeList.get(n), shop.getCustId(), groupArray);
					kunnrStockListAdapter = new KunnrStockListAdapter(shop,
							KunnrStockActivity.this, childArray, groupArray,
							checkTimeList.get(n), flag);

					elv_stockList.setGroupIndicator(null);
					elv_stockList.setAdapter(kunnrStockListAdapter);

					if ("kunnr_week".equals(flag) || "kunnr_month".equals(flag)) {
						new GetSuperKunnrStockInfoTask().execute();
					} else {
						double amount = getQuantityAll(childArray);
						market_quantity_all.setText(amount + "");

					}
					market_quantity_all.setVisibility(View.VISIBLE);
					quantitytextView.setVisibility(View.VISIBLE);
				}
			});
			/** 长按删除 **/
			// convertView.setOnLongClickListener(new OnLongClickListener() {
			// public boolean onLongClick(View arg0) {
			// View overView = View.inflate(KunnrStockActivity.this,
			// R.layout.dialog_confirmation, null);
			// final Dialog overDialog = new Dialog(
			// KunnrStockActivity.this, R.style.dialog_xw);
			// overDialog.setContentView(overView);
			// Button overCancel2 = (Button) overView
			// .findViewById(R.id.dialog_cancel_btn);
			// overCancel2.setOnClickListener(new OnClickListener() {
			// public void onClick(View v) {
			// overDialog.cancel();
			// }
			// });
			// Button overOk2 = (Button) overView
			// .findViewById(R.id.dialog_ok_btn);
			// overOk2.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// Stock.deleteAll(Stock.findCheckTime(flag,
			// shop.getCustId(), checkTimeList.get(n)));
			// checkTimeList.remove(checkTimeList.get(n));
			// notifyDataSetChanged();
			// overDialog.cancel();
			// }
			// });
			// overDialog.show();
			// return false;
			// }
			//
			// });
			return convertView;
		}

		protected class ViewHodler {
			TextView tv_checkDate = null;
		}

		protected void resetViewHolder(ViewHodler pViewHolder) {
			pViewHolder.tv_checkDate.setText(null);
		}

	}

	/********************* 拍照 */

	public void initPhotoTable(PhotoType type) {
		tl_table = new TableLayout(this);
		tl_table = (TableLayout) findViewById(R.id.photoviewTable);

		ptList = PhotoInfo.findByShop(shop.getCustId(), type);

		new LoadImageAsyncTask().execute(ptList);
	}

	public class LoadImageAsyncTask extends
			AsyncTask<List<PhotoInfo>, Integer, Map<String, Bitmap>> {
		List<PhotoInfo> photoList = new ArrayList<PhotoInfo>();

		protected void onPreExecute() {
		}

		@Override
		protected void onPostExecute(Map<String, Bitmap> maps) {
			tl_table.removeAllViews();
			addRow(photoList, maps, tl_table);
		}

		@Override
		protected Map<String, Bitmap> doInBackground(List<PhotoInfo>... params) {
			photoList = params[0];
			return MyUtil.buildThum(photoList, width, height);
		}
	}

	@SuppressLint("SimpleDateFormat")
	public void addRow(List<PhotoInfo> photoList, Map<String, Bitmap> picMap,
			TableLayout table) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int size = photoList.size();
		int rowCount = size % 3;// 多余
		int row_count = 0;// 总行数
		if (rowCount != 0) {
			row_count = size / 3 + 1;
		} else {
			row_count = size / 3;
		}
		int j = 0;
		TableRow row = new TableRow(this);
		if (size != 0) {
			for (int i = 0; i < size; i++) {
				MyImageButton b = new MyImageButton(this);
				b.setMaxWidth((int) (width * 0.4));
				b.setMaxHeight((int) (height * 0.2));
				b.setHeight(height);
				dir = DataProviderFactory.getDirName
						+ photoList.get(i).getPhotoName() + ".jpg";
				Long timeL = Long.parseLong(PhotoUtil.getpicTime(photoList.get(
						i).getPhotoName()));
				String time = f.format(new Date(timeL));
				b.setText(time);
				b.setTag(R.string.tag1, dir);
				b.setTag(R.string.tag2, photoList.get(i).getPhotoName());
				if (Status.FINISHED.equals(photoList.get(i).getStatus())) {
					b.getBackground().setAlpha(0);// 去掉边框
					// b.setBackgroundResource(color.green);
				}
				b.setImageBitmap(picMap.get(dir));
				b.setScaleType(ScaleType.CENTER_INSIDE);
				b.setColor(Color.BLACK);
				b.setOnClickListener(photoItemClick);
				b.setOnLongClickListener(photoLongClick);
				row.addView(b);
				if ((i + 1) % 3 == 0) {
					j++;
					table.addView(row);
					row = new TableRow(this);
					if (size == i + 1) {
						TableRow row1 = new TableRow(this);
						addBtnRow(row1, table);
					}

				} else if (rowCount != 0 && j + 1 == row_count) {
					if (i == size - 1) {
						if (size == i + 1) {
							addBtnRow(row, table);
						} else {
							table.addView(row);
						}
					}
				}
			}
		} else {
			TableRow row1 = new TableRow(this);
			addBtnRow(row1, table);
		}
	}

	public void addBtnRow(TableRow row, TableLayout table) {
		ImageButton btn = new ImageButton(this);
		btn.getBackground().setAlpha(0);
		btn.setImageResource(R.drawable.bg_takephoto);
		btn.setMaxWidth((int) (width * 0.4));
		btn.setMaxHeight((int) (height * 0.2));
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				gotoPZ();
			}
		});
		row.addView(btn);
		table.addView(row);
	}

	private OnClickListener photoItemClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final MyImageButton b = (MyImageButton) v;
			final String p = (String) b.getTag(R.string.tag1);
			Intent intent = new Intent(KunnrStockActivity.this,
					ShowImageActivity.class);
			intent.putExtra("dir", p);
			startActivityForResult(intent, 213);
		}
	};

	private OnLongClickListener photoLongClick = new OnLongClickListener() {
		public boolean onLongClick(final View arg0) {
			View overdiaView = View.inflate(KunnrStockActivity.this,
					R.layout.dialog_confirmation, null);
			final Dialog overdialog = new Dialog(KunnrStockActivity.this,
					R.style.dialog_xw);
			overdialog.setContentView(overdiaView);
			overdialog.setCanceledOnTouchOutside(false);
			Button overcancel = (Button) overdiaView
					.findViewById(R.id.dialog_cancel_btn);
			overcancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					overdialog.cancel();
				}
			});
			Button overok = (Button) overdiaView
					.findViewById(R.id.dialog_ok_btn);
			overok.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					final MyImageButton b = (MyImageButton) arg0;
					final String pName = (String) b.getTag(R.string.tag2);
					final String path = (String) b.getTag(R.string.tag1);
					File file = new File(path);
					if (file.exists()) {
						file.delete();
						PhotoInfo.delete(PhotoInfo.getByPhotoName(pName));
						initPhotoTable(pt);
					}
					overdialog.cancel();
				}
			});
			overdialog.show();
			return false;
		}
	};

	private void gotoPZ() {
		photoNameAll = PhotoUtil.getphotoName();// 获取照片名字
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				PictureShowUtils.getDirName(), photoNameAll + ".jpg")));
		startActivityForResult(intent, 0);
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 5) {
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 5) {
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}

			if (photoNameAll != null) {
				/** 记录照片信息 */
				PhotoInfo ptInfo = new PhotoInfo();
				ptInfo.setPhotoName(photoNameAll);
				ptInfo.setPtype(pt);
				ptInfo.setStatus(Status.NEW);
				ptInfo.setEmplid(DataProviderFactory.getUserId());
				ptInfo.setCustid(shop.getCustId());
				ptInfo.setCustName(shop.getCustName());
				ptInfo.setTimestamp(PhotoUtil.getpicTime(photoNameAll));
				ptInfo.setSeq("2");
				ptInfo.setDayType(DataProviderFactory.getDayType());
				boolean b = PhotoInfo.save(ptInfo);
				String filestr = PictureShowUtils.getDirName() + photoNameAll
						+ ".jpg";
				System.out.println("照片路径：" + filestr);
				if (flag.equals(XPPApplication.TAB_KUNNR_WEEK)) {
					ptInfo.setPhototype("周库存");
				} else if (flag.equals(XPPApplication.TAB_KUNNR_MONTH)) {
					ptInfo.setPhototype("月库存");
				}

				Thread task = PhotoUtil.dealPhotoFile(filestr, ptInfo);
				if (b) {
					try {
						task.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					initPhotoTable(pt);
				}

			}
		} else if (resultCode == Activity.RESULT_CANCELED) {
			if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 5) {
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public static double getQuantityAll(List<List<Stock>> childArray) {
		double amount = 0;
		for (int i = 0; i < childArray.size(); i++) {
			List<Stock> sList = childArray.get(i);
			if (sList != null) {
				for (int j = 0; j < sList.size(); j++) {
					Stock s = sList.get(j);
					if (s != null) {
						amount = amount + s.getQuantity();
					}
				}
			}

		}
		return amount;
	}

}
