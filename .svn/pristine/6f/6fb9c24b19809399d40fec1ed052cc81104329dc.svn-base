package com.xpp.moblie.screens;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xpp.moblie.adapter.market.MarketTotoalListAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.util.DataHandleUtil;
import com.xpp.moblie.util.MyImageButton;
import com.xpp.moblie.util.MyUtil;
import com.xpp.moblie.util.PhotoUtil;
import com.xpp.moblie.util.PictureShowUtils;
import com.xpp.moblie.util.ToastUtil;

/**
 * Title: 市场活动检查
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年11月21日 上午11:54:17
 */

public class MarketActivity extends Activity {

	private ListView lv_marketTotal;
	private LinearLayout lo_marketDetailLayout;
	private TextView tv_custName;
	private TextView tv_detailId, tv_activityDesc, tv_activityDetail,
			tv_planDate, tv_myProportion,tv_startDate ,tv_endDate,tv_cpjc,tv_zdcx  ;
	private EditText et_hxProportion, et_dxExplanation, et_checkPercentBus,
			et_reason;
	private Button btn_save;
	private MarketCheck marketCheck;
	private Customer shop;
	private RadioGroup rg_RadioGroup01;
	private RadioButton rb_execution, rb_notExecution, br_notAllExecution;
	private int width;
	private int height;
	private TableLayout tl_table;
	private String dir, photoNameAll;
	private List<PhotoInfo> ptList;
	private String rate = "0";
	private MarketTotoalListAdapter marketTotoalListAdapter;
	private GestureDetector mGestureDetector;
	/**
	 * 加载元素初始化数据
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_market_show);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		initView();
		initData();
		mGestureDetector = new GestureDetector(this,new LearnGestureListener ());
		// registerReceiver(UpdateListener, new IntentFilter(
		// XPPApplication.REFRESH_MARKET_RECEIVER));
	}

	public void onDestroy() {
		super.onDestroy();
		// unregisterReceiver(UpdateListener);
	}

	// private BroadcastReceiver UpdateListener = new BroadcastReceiver() {
	// public void onReceive(Context context, Intent intent) {
	// Bundle b = intent.getExtras();
	// String key = b.getString("type");
	// if("Y".equals(key)){
	// marketTotoalListAdapter.bpList=MarketCheck.findByCustId(shop.getCustId());
	// marketTotoalListAdapter.notifyDataSetChanged();
	// }
	// }
	// };

	private void initView() {
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		tv_custName = (TextView) findViewById(R.id.custName);
		btn_save = (Button) findViewById(R.id.save);
		btn_save.setOnClickListener(BtnClicked);
		lv_marketTotal = (ListView) findViewById(R.id.markeTotaltList);
		lo_marketDetailLayout = (LinearLayout) findViewById(R.id.marketDetailLayout);
		
	}

	private void inintDetailView() {
		tv_detailId = (TextView) findViewById(R.id.activityDetailId);
		tv_activityDesc = (TextView) findViewById(R.id.activityDesc);
		tv_activityDetail = (TextView) findViewById(R.id.activityDetail);
		tv_planDate = (TextView) findViewById(R.id.planDate);
		tv_myProportion = (TextView) findViewById(R.id.myProportion);
		tv_startDate = (TextView) findViewById(R.id.startDate);
		tv_endDate = (TextView) findViewById(R.id.endDate);
		tv_cpjc = (TextView) findViewById(R.id.cpjc);
		tv_zdcx = (TextView) findViewById(R.id.zdcx);
		
		
		
		if (XPPApplication.MOBILE_DD.equals(DataProviderFactory.getRoleId())) {// 督导检查
			findViewById(R.id.ddMarketInput).setVisibility(View.VISIBLE);
			et_hxProportion = (EditText) findViewById(R.id.hxProportion);
			et_hxProportion.setKeyListener(numberKeyListener);
			et_dxExplanation = (EditText) findViewById(R.id.dxExplanation);
			rb_execution = (RadioButton) findViewById(R.id.execution);
			rb_notExecution = (RadioButton) findViewById(R.id.notExecution);
			br_notAllExecution = (RadioButton) findViewById(R.id.notAllExecution);
			rg_RadioGroup01 = (RadioGroup) findViewById(R.id.RadioGroup01);
			rg_RadioGroup01
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							int radioButtonId = group.getCheckedRadioButtonId();
							// 根据ID获取RadioButton的实例

							if (radioButtonId == R.id.execution) {// 执行
								rate = "1";
								et_hxProportion.setText(marketCheck.getMyProportion());
							}
							if (radioButtonId == R.id.notExecution) {// 未执行
								rate = "2";
								et_hxProportion.setText("0");
							}
							if (radioButtonId == R.id.notAllExecution) {// 未完全执行
								rate = "3";
//								et_hxProportion.getText().clear();
								et_hxProportion.setText(DataHandleUtil.dealNull(marketCheck.getHxProportion()));

							}
						}
					});
		
			
		} else if(XPPApplication.MOBILE_CSJL.equals(DataProviderFactory.getRoleId())){//业务自查
			findViewById(R.id.ywMarketInput).setVisibility(View.VISIBLE);
			// 业务自查
			et_checkPercentBus = (EditText) findViewById(R.id.checkPercentBus);
			et_reason = (EditText) findViewById(R.id.reason);
		}
			
	}

	private void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			marketCheck = (MarketCheck) bun.get("toDetail");
			shop = (Customer) bun.get("custInfo");
		}
		if (marketCheck != null) {// detail 页面
			lo_marketDetailLayout.setVisibility(View.VISIBLE);
			lv_marketTotal.setVisibility(View.GONE);
			btn_save.setVisibility(View.VISIBLE);
			// 明细展示
			inintDetailView();
			tv_detailId.setText(getString(R.string.market_activity_id)
					+ marketCheck.getMarketDetailId());// 明细ID
			tv_planDate.setText(getString(R.string.market_activity_planDate)
					+ marketCheck.getPlanYear() + getString(R.string.year)
					+ marketCheck.getPlanMonth() + getString(R.string.month));// 活动时间
			tv_activityDesc.setText(getString(R.string.market_activity_desc)
					+ marketCheck.getMarketActivityName());// 活动名称
			tv_myProportion.setText(getString(R.string.market_myProportion)
					+ DataHandleUtil.dealNull(marketCheck.getMyProportion()) + "                    "+getString(R.string.market_quantity)
					+ DataHandleUtil.dealNull(marketCheck.getQuantity()));
			if (marketCheck.getSkuname()!=null) {
				tv_cpjc.setText(getString(R.string.market_skuname)
						+DataHandleUtil.dealNull(marketCheck.getSkuname())+ " "+getString(R.string.market_skuprice)
						+ DataHandleUtil.dealNull(marketCheck.getSkuprice()));
			}else {
				tv_cpjc.setVisibility(View.GONE);
			}
			if (marketCheck.getCxsku()!=null) {
				tv_zdcx.setText(getString(R.string.market_cxsku)
						+DataHandleUtil.dealNull(marketCheck.getCxsku())+ "  "+getString(R.string.market_payment)
						+ DataHandleUtil.dealNull(marketCheck.getPayment()));
			}else {
				tv_zdcx.setVisibility(View.GONE);
			}
			tv_startDate.setText(getString(R.string.market_startDate)+DataHandleUtil.dealNull(marketCheck.getStartDate()));
			tv_endDate.setText(getString(R.string.market_endDate)+DataHandleUtil.dealNull(marketCheck.getEndDate()));
			tv_activityDetail
					.setText(getString(R.string.market_activity_detail)
							+ (marketCheck.getMarketDesc() == null ? ""
							: marketCheck.getMarketDesc()));// 活动详细信息

			if (XPPApplication.MOBILE_DD.equals(DataProviderFactory.getRoleId())) {// 督导检查
				et_hxProportion.setText(marketCheck.getHxProportion());
				et_dxExplanation.setText(marketCheck.getDxExplanation());
				if (marketCheck.getExecution() == null) {
					rb_execution.setChecked(false);
					rb_notExecution.setChecked(false);
					br_notAllExecution.setChecked(false);
				} else {
					switch (Integer.valueOf(marketCheck.getExecution())) {
					case 1:
						rb_execution.setChecked(true);
						break;
					case 2:
						rb_notExecution.setChecked(true);
						break;
					case 3:
						br_notAllExecution.setChecked(true);
						break;
					default:
						break;
					}

				}
				
			} else if(XPPApplication.MOBILE_CSJL.equals(DataProviderFactory.getRoleId())){// 业务自查
				et_checkPercentBus.setText(marketCheck.getCheckPercentBus());
				et_reason.setText(marketCheck.getReason());
			}
			if ("N".equals(marketCheck.getIsModify())) {
				btn_save.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "该活动已结束或者未开始，无法修改！",
				Toast.LENGTH_SHORT).show();
				if (XPPApplication.MOBILE_DD.equals(DataProviderFactory.getRoleId())){
				rb_execution.setEnabled(false);
				rb_notExecution.setEnabled(false); 
				br_notAllExecution.setEnabled(false);
				et_hxProportion.setEnabled(false);
				et_dxExplanation.setEnabled(false);
				}
				else if(XPPApplication.MOBILE_CSJL.equals(DataProviderFactory.getRoleId())){
				et_checkPercentBus.setEnabled(false);
				et_reason.setEnabled(false);
				}
			}else {
				initPhotoTable();
			}
			
		} else {// total页面
			lv_marketTotal.setVisibility(View.VISIBLE);
			lo_marketDetailLayout.setVisibility(View.GONE);
			btn_save.setVisibility(View.GONE);
			new MarketTotalListTask().execute();
		}
		tv_custName.setText(shop.getCustName());

	}

	/** 保存信息到本地 */
	private boolean saveInfo() {
		try {
			boolean flag = false;
			if (!marketCheck.getDayType().equals(
					DataProviderFactory.getDayType())) {// 隔天登陆，提交检查时，修改时间戳,重置状态
				marketCheck.setDayType(DataProviderFactory.getDayType());
				marketCheck.setStatus(null);
				marketCheck.update();
			}
			if (XPPApplication.MOBILE_DD.equals(DataProviderFactory.getRoleId())) {// 督导检查
				if (!rate.equals(marketCheck.getExecution())) {
					marketCheck.setExecution(rate);
					flag = true;
				}

				if (!et_hxProportion.getText().toString().trim()
						.equals(marketCheck.getHxProportion())) {
					marketCheck.setHxProportion(et_hxProportion.getText()
							.toString());
					if(Integer.valueOf(et_hxProportion.getText().toString())>100){
						ToastUtil.show(MarketActivity.this, "自查比例不能大于100%");
						return false ;
					}
					flag = true;
				}

				if (!et_dxExplanation.getText().toString().trim()
						.equals(marketCheck.getDxExplanation())) {
					marketCheck.setDxExplanation(et_dxExplanation.getText()
							.toString());
					flag = true;
				}
			} else if(XPPApplication.MOBILE_CSJL.equals(DataProviderFactory.getRoleId())) {// 业务自查
				if (!et_checkPercentBus.getText().toString().trim()
						.equals(marketCheck.getCheckPercentBus())) {
					marketCheck.setCheckPercentBus(et_checkPercentBus.getText()
							.toString());
					if(Integer.valueOf(et_checkPercentBus.getText().toString())>100){
						ToastUtil.show(MarketActivity.this, "自查比例不能大于100%");
						return false ;
					}
					flag = true;
				}
				if (!et_reason.getText().toString().trim()
						.equals(marketCheck.getReason())) {
					marketCheck.setReason(et_reason.getText().toString());
					flag = true;
				}
			}
//			else{
//				flag = true;
//			}

			if (flag) {
				marketCheck.setStatus(Status.UNSYNCHRONOUS);
				marketCheck.update();
			}

			if (ptList != null) {
				flag = false;
				for (PhotoInfo photoInfo : ptList) {
					flag = PhotoInfo.submitPhoto(photoInfo);
				}
				if (flag) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "photo");
					XPPApplication.sendChangeBroad(MarketActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	/** 市场活动总单List **/
	private class MarketTotalListTask extends
			AsyncTask<Object, Integer, List<MarketCheck>> {
		protected List<MarketCheck> doInBackground(Object... arg0) {
			return MarketCheck.findByCustId(shop.getCustId());
		}

		protected void onPostExecute(List<MarketCheck> result) {
			marketTotoalListAdapter = new MarketTotoalListAdapter(
					MarketActivity.this, result, shop);
			lv_marketTotal.setAdapter(marketTotoalListAdapter);
		}

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

	public void initPhotoTable() {
		tl_table = new TableLayout(this);
		tl_table = (TableLayout) findViewById(R.id.photoViewTable);

		ptList = PhotoInfo.findInId(marketCheck.getPhotoId());
		// ptList = PhotoInfo.findByShop(shop.getCustName(), PhotoType.SCJC);

		new LoadImageAsyncTask().execute(ptList);
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
				ptInfo.setPtype(PhotoType.SCJC);
				ptInfo.setStatus(Status.NEW);
				ptInfo.setEmplid(DataProviderFactory.getUserId());
				ptInfo.setCustid(shop.getCustId());
				ptInfo.setCustName(shop.getCustName());
				// ptInfo.setRouteid();// 线路ID
				ptInfo.setActid("1");// 活动编号
				ptInfo.setMIADetailId(marketCheck.getMarketDetailId());
				ptInfo.setMIAItemId(marketCheck.getItemId());
				ptInfo.setTimestamp(PhotoUtil.getpicTime(photoNameAll));
				ptInfo.setSeq("1");
				ptInfo.setDayType(DataProviderFactory.getDayType());
				boolean b = PhotoInfo.save(ptInfo);
			
				if (b) {// 市场检查表存照片信息
					if (marketCheck.getPhotoId() != null) {
						marketCheck.setPhotoId(marketCheck.getPhotoId() + ","
								+ photoNameAll);
					} else {
						marketCheck.setPhotoId(photoNameAll);
					}
					marketCheck.update();
				}
				/** 压缩 **/
				String filestr=PictureShowUtils.getDirName()+photoNameAll + ".jpg";
				System.out.println("照片路径："+filestr);
				ptInfo.setPhototype("市场检查");
				Thread task=PhotoUtil.dealPhotoFile(filestr,ptInfo);
				System.out.println(b);
				try {
					task.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				initPhotoTable();
			}
		} else if (resultCode == Activity.RESULT_CANCELED) {
			if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 5) {
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		} else if (resultCode == 10) {
			marketTotoalListAdapter.bpList = MarketCheck.findByCustId(shop.getCustId());
			marketTotoalListAdapter.notifyDataSetChanged();
			// new MarketTotalListTask().execute();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private OnClickListener photoItemClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final MyImageButton b = (MyImageButton) v;
			final String p = (String) b.getTag(R.string.tag1);
			Intent intent = new Intent(MarketActivity.this,
					ShowImageActivity.class);
			intent.putExtra("dir", p);
			startActivityForResult(intent, 213);
		}
	};

	private OnLongClickListener photoLongClick = new OnLongClickListener() {
		public boolean onLongClick(final View arg0) {
			View overdiaView = View.inflate(MarketActivity.this,
					R.layout.dialog_confirmation, null);
			final Dialog overdialog = new Dialog(MarketActivity.this,
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
						initPhotoTable();
					}
					overdialog.cancel();
				}
			});
			overdialog.show();
			return false;
		}
	};

	/** 按键监听 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(MarketActivity.this);
				break;
			case R.id.save:
//				if("N".equals(marketCheck.getIsModify())){
//					Toast.makeText(getApplicationContext(),
//							getString(R.string.market_is_modify),
//							Toast.LENGTH_SHORT).show();
//					break;
//				}
				if(!"0".equals(rate)&&(et_hxProportion.getText()==null||
						et_hxProportion.getText().toString()==null||
						"".equals(et_hxProportion.getText().toString().trim()))){
					ToastUtil.show(getApplicationContext(), getString(R.string.market_hxProportion_isnotnull));
					break;
				}
				
				if (saveInfo()) {// 保存成功
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "marketCheck");
					XPPApplication.sendChangeBroad(MarketActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
					Intent i = new Intent(MarketActivity.this,
							MarketActivity.class);
					i.putExtra("flag", "changed");
					setResult(10, i);
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_success),
							Toast.LENGTH_SHORT).show();

				} else {// 保存失败
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_fail), Toast.LENGTH_SHORT)
							.show();
				}
				XPPApplication.exit(MarketActivity.this);
				break;
			}
		}
	};

	private NumberKeyListener numberKeyListener = new NumberKeyListener() {
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

	// 取消手机返回按钮功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(MarketActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{ 
 	     public boolean onDown(MotionEvent ev) { 
 	         return true; 
 	    } 
 	      public boolean onFling (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
 	    	 if (e2.getX() - e1.getX() > 100) { 
 				XPPApplication.exit(MarketActivity.this);
				return true;
		 }
 	    		  return false;
  } 
 	       public boolean onTouchEvent(MotionEvent event) {
 	    	   return mGestureDetector.onTouchEvent(event);
 	       }
}
	 public boolean dispatchTouchEvent(MotionEvent ev) {
	       mGestureDetector.onTouchEvent(ev);
	       return super.dispatchTouchEvent(ev);

	}

}
