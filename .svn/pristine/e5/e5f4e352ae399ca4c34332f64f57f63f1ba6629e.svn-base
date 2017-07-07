package com.xpp.moblie.screens;


import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TextView;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;

/**
 * Title: 经销商列表
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年11月22日 下午1:37:03
 */

@SuppressLint("ResourceAsColor")
public class KunnrManagementActivity extends TabActivity implements
		OnCheckedChangeListener {
	// private KunnrManagementTabBar tabbar;
	// private LinearLayout contentLayout;
	private RadioGroup group;
	private TabHost tabHost;
	private Customer shop;
//	private int width, height;
	private TextView tv_custName;
	private TableLayout tl_table;
//	private String dir,photoNameAll;
	private RadioButton rb_kunnrWeek,rb_kunnrMonth,rb_salesDay;
//	private List<PhotoInfo> ptList = new ArrayList<PhotoInfo>();

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_kunnr_management);

		group = (RadioGroup) findViewById(R.id.main_radio);
		group.setOnCheckedChangeListener(this);

		tabHost = this.getTabHost();
		initData();
//		initPhotoTable(PhotoType.KCSB);
		rb_kunnrWeek = (RadioButton) findViewById(R.id.kunnr_week);
		rb_kunnrWeek.setChecked(true);
		TabSpec tab_kunnrWeek = tabHost.newTabSpec(XPPApplication.TAB_KUNNR_WEEK);
		TabSpec tab_kunnrMonth = tabHost.newTabSpec(XPPApplication.TAB_KUNNR_MONTH);
		TabSpec tab_saleDay = tabHost.newTabSpec(XPPApplication.TAB_SALES_DAY);
		
		
		String roleId=DataProviderFactory.getRoleId();
		
		Intent i1 = new Intent(new Intent(this, KunnrStockActivity.class));
		i1.putExtra("custInfo", shop);
		i1.putExtra("flag", XPPApplication.TAB_KUNNR_WEEK);
		i1.putExtra("photoType", PhotoType.ZKC);
		tab_kunnrWeek.setIndicator(XPPApplication.TAB_KUNNR_WEEK).setContent(i1);
		tabHost.addTab(tab_kunnrWeek);
		
		Intent i2 = new Intent(new Intent(this, KunnrStockActivity.class));
		i2.putExtra("flag", XPPApplication.TAB_KUNNR_MONTH);
		i2.putExtra("custInfo", shop);
		i2.putExtra("photoType", PhotoType.YKC);
		tab_kunnrMonth.setIndicator(XPPApplication.TAB_KUNNR_MONTH).setContent(i2);
		tabHost.addTab(tab_kunnrMonth);
		
		if(XPPApplication.MOBILE_DD.equals(roleId)){
			//Button kunnr_week= (Button) findViewById(R.id.kunnr_week);
			Button kunnr_sales_day= (Button) findViewById(R.id.kunnr_sales_day);
			//kunnr_week.setVisibility(View.GONE);
			kunnr_sales_day.setVisibility(View.GONE);
		}else{
			Intent i3 = new Intent(new Intent(this, KunnrStockActivity.class));
			i3.putExtra("flag", XPPApplication.TAB_SALES_DAY);
			i3.putExtra("custInfo", shop);
			i3.putExtra("photoType", PhotoType.MRFXL);
			tab_saleDay.setIndicator(XPPApplication.TAB_SALES_DAY).setContent(i3);
			tabHost.addTab(tab_saleDay);
		}
	}

	public void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
		}
	}

	
	
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.kunnr_week:
			tabHost.setCurrentTabByTag(XPPApplication.TAB_KUNNR_WEEK);
			break;
		case R.id.kunnr_month:
			tabHost.setCurrentTabByTag(XPPApplication.TAB_KUNNR_MONTH);
			break;
		case R.id.kunnr_sales_day:
			tabHost.setCurrentTabByTag(XPPApplication.TAB_SALES_DAY);
			break;
		default:
			break;
		}
	}

	
//	public void initPhotoTable(PhotoType type) {
//		tl_table = new TableLayout(this);
//		tl_table = (TableLayout) findViewById(R.id.photoviewTable);
//
//		ptList = PhotoInfo.findByShop(shop.getCustId(), type);
//
//		new LoadImageAsyncTask().execute(ptList);
//	}
//	
//	public class LoadImageAsyncTask extends
//	AsyncTask<List<PhotoInfo>, Integer, Map<String, Bitmap>> {
//		List<PhotoInfo> photoList = new ArrayList<PhotoInfo>();
//
//			protected void onPreExecute() {
//			}
//			
//			@Override
//			protected void onPostExecute(Map<String, Bitmap> maps) {
//				tl_table.removeAllViews();
//				addRow(photoList, maps, tl_table);
//			}
//			
//			@Override
//				protected Map<String, Bitmap> doInBackground(List<PhotoInfo>... params) {
//					photoList = params[0];
//					return MyUtil.buildThum(photoList, width, height);
//				}
//	}
//
//	@SuppressLint("SimpleDateFormat")
//	public void addRow(List<PhotoInfo> photoList, Map<String, Bitmap> picMap,
//			TableLayout table) {
//		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		int size = photoList.size();
//		int rowCount = size % 3;// 多余
//		int row_count = 0;// 总行数
//		if (rowCount != 0) {
//			row_count = size / 3 + 1;
//		} else {
//			row_count = size / 3;
//		}
//		int j = 0;
//		TableRow row = new TableRow(this);
//		if (size != 0) {
//			for (int i = 0; i < size; i++) {
//				MyImageButton b = new MyImageButton(this);
//				b.setMaxWidth((int) (width * 0.4));
//				b.setMaxHeight((int) (height * 0.2));
//				b.setHeight(height);
//				dir = DataProviderFactory.getDirName
//						+ photoList.get(i).getPhotoName() + ".jpg";
//				Long timeL = Long.parseLong(PhotoUtil.getpicTime(photoList.get(
//						i).getPhotoName()));
//				String time = f.format(new Date(timeL));
//				b.setText(time);
//				b.setTag(R.string.tag1, dir);
//				b.setTag(R.string.tag2, photoList.get(i).getPhotoName());
//				if (Status.FINISHED.equals(photoList.get(i).getStatus())) {
//					b.getBackground().setAlpha(0);// 去掉边框
//					// b.setBackgroundResource(color.green);
//				}
//				b.setImageBitmap(picMap.get(dir));
//				b.setScaleType(ScaleType.CENTER_INSIDE);
//				b.setColor(Color.BLACK);
//				b.setOnClickListener(photoItemClick);
//				b.setOnLongClickListener(photoLongClick);
//				row.addView(b);
//				if ((i + 1) % 3 == 0) {
//					j++;
//					table.addView(row);
//					row = new TableRow(this);
//					if (size == i + 1) {
//						TableRow row1 = new TableRow(this);
//						addBtnRow(row1, table);
//					}
//
//				} else if (rowCount != 0 && j + 1 == row_count) {
//					if (i == size - 1) {
//						if (size == i + 1) {
//							addBtnRow(row, table);
//						} else {
//							table.addView(row);
//						}
//					}
//				}
//			}
//		} else {
//			TableRow row1 = new TableRow(this);
//			addBtnRow(row1, table);
//		}
//	}
//	
//	public void addBtnRow(TableRow row, TableLayout table) {
//		ImageButton btn = new ImageButton(this);
//		btn.getBackground().setAlpha(0);
//		btn.setImageResource(R.drawable.bg_takephoto);
//		btn.setMaxWidth((int) (width * 0.4));
//		btn.setMaxHeight((int) (height * 0.2));
//		btn.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				gotoPZ();
//			}
//		});
//		row.addView(btn);
//		table.addView(row);
//	}
//	
//	
//	private OnClickListener photoItemClick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			final MyImageButton b = (MyImageButton) v;
//			final String p = (String) b.getTag(R.string.tag1);
//			Intent intent = new Intent(KunnrManagementActivity.this,
//					ShowImageActivity.class);
//			intent.putExtra("dir", p);
//			startActivityForResult(intent, 213);
//		}
//	};
//	
//	private OnLongClickListener photoLongClick = new OnLongClickListener() {
//		public boolean onLongClick(final View arg0) {
//			View overdiaView = View.inflate(KunnrManagementActivity.this,
//					R.layout.dialog_confirmation, null);
//			final Dialog overdialog = new Dialog(KunnrManagementActivity.this,
//					R.style.dialog_xw);
//			overdialog.setContentView(overdiaView);
//			overdialog.setCanceledOnTouchOutside(false);
//			Button overcancel = (Button) overdiaView
//					.findViewById(R.id.dialog_cancel_btn);
//			overcancel.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					overdialog.cancel();
//				}
//			});
//			Button overok = (Button) overdiaView
//					.findViewById(R.id.dialog_ok_btn);
//			overok.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					final MyImageButton b = (MyImageButton) arg0;
//					final String pName = (String) b.getTag(R.string.tag2);
//					final String path = (String) b.getTag(R.string.tag1);
//					File file = new File(path);
//					if (file.exists()) {
//						file.delete();
//						PhotoInfo.delete(PhotoInfo.getByPhotoName(pName));
//						initPhotoTable(PhotoType.KCSB);
//					}
//					overdialog.cancel();
//				}
//			});
//			overdialog.show();
//			return false;
//		}
//	};
//	private void gotoPZ() {
//		photoNameAll = PhotoUtil.getphotoName();// 获取照片名字
//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//				PictureShowUtils.getDirName(), photoNameAll + ".jpg")));
//		startActivityForResult(intent, 0);
//		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 5) {
//			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//		}
//	}
//	
//	
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
//			if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 5) {
//				overridePendingTransition(R.anim.push_right_in,
//						R.anim.push_right_out);
//			}
//
//			if (photoNameAll != null) {
//				/** 记录照片信息 */
//				PhotoInfo ptInfo = new PhotoInfo();
//				PhotoType pt = null;
//				ptInfo.setPhotoName(photoNameAll);
//				ptInfo.setPtype(PhotoType.KCSB);
//				ptInfo.setStatus(Status.NEW);
//				ptInfo.setEmplid(DataProviderFactory.getUserId());
//				ptInfo.setCustid(shop.getCustId());
//				ptInfo.setCustName(shop.getCustName());
//				ptInfo.setTimestamp(PhotoUtil.getpicTime(photoNameAll));
//				ptInfo.setSeq("2");
//				ptInfo.setDayType(DataProviderFactory.getDayType());
//				boolean b = PhotoInfo.save(ptInfo);
//				if (b) {
//					initPhotoTable(PhotoType.KCSB);
//				}
//
//			}
//		} else if (resultCode == Activity.RESULT_CANCELED) {
//			if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 5) {
//				overridePendingTransition(R.anim.push_right_in,
//						R.anim.push_right_out);
//			}
//		}
//
//		super.onActivityResult(requestCode, resultCode, data);
//	}
}
