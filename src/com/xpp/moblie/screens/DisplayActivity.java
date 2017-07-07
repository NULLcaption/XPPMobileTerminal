package com.xpp.moblie.screens;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.util.MyImageButton;
import com.xpp.moblie.util.MyUtil;
import com.xpp.moblie.util.PhotoUtil;
import com.xpp.moblie.util.PictureShowUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/** 陈列 */
public class DisplayActivity extends Activity {

	private Customer shop;
	private TextView tv_custName;
	private TableLayout singleInput, multiInputXPP, multiInputYLM;
	private String dir, photoNameAll;
	private TableLayout tl_table;
	private ScrollView sv_singleSV, sv_multiYLMSV, sv_multiXPPSV;
	private int width, height;
	private boolean viewFlag = true;// 用于控制显示陈列类型 1 标准陈列 2 多点
	private boolean brandFlag = true;// true多点XPP fasle 多点YLM

	private Button btn_multiDisplayxpp, btn_multiDisplayylm, modifyBtn;

	private List<PhotoInfo> ptList = new ArrayList<PhotoInfo>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_display);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		initView();
		initData();
		DepictSingleView();
		DepictMultiView();
	}

	public void initView() {
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.save).setOnClickListener(BtnClicked);
		btn_multiDisplayxpp = (Button) findViewById(R.id.multiDisplayxpp);
		btn_multiDisplayxpp.setOnClickListener(BtnClicked);
		btn_multiDisplayylm = (Button) findViewById(R.id.multiDisplayylm);
		btn_multiDisplayylm.setOnClickListener(BtnClicked);
		modifyBtn = (Button) findViewById(R.id.modifyBtn);
		modifyBtn.setOnClickListener(BtnClicked);
		modifyBtn.setText(getString(R.string.multi_display));
		tv_custName = (TextView) findViewById(R.id.custName);
//		singleInput = (LinearLayout) findViewById(R.id.singleInput);
//		multiInputXPP = (LinearLayout) findViewById(R.id.multiInputXPP);
//		multiInputYLM = (LinearLayout) findViewById(R.id.multiInputYLM);
		singleInput = (TableLayout) findViewById(R.id.singleInput);
		multiInputXPP = (TableLayout) findViewById(R.id.multiInputXPP);
		multiInputYLM = (TableLayout) findViewById(R.id.multiInputYLM);
		
		sv_singleSV = (ScrollView) findViewById(R.id.singleSV);
		sv_multiYLMSV = (ScrollView) findViewById(R.id.multiYLMSV);
		sv_multiXPPSV = (ScrollView) findViewById(R.id.multiXPPSV);

	}

	public void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
		}
		tv_custName.setText(shop.getCustName() + "__"
				+ getString(R.string.single_display));

	}

	// 绘制标准陈列
	private void DepictSingleView() {
		singleInput.setPadding(10, 0, 10, 0);
		TextView tv_desc = null;
		EditText et_desc = null;
		TextView tv_space1=null;
		TextView tv_space2=null;
		DisPlay dp = new DisPlay();
		List<Dictionary> list = Dictionary.findbyTypeValue("spdisplay");
		TableRow tr1 = null;
		TableRow tr2 = null;
		for (int i = 0; i < list.size(); i++) {

			if (i % 2 == 0) {
				tr1 = new TableRow(DisplayActivity.this);
				tr2 = new TableRow(DisplayActivity.this);
				TableLayout.LayoutParams params = new TableLayout.LayoutParams();
				params.setMargins(0, 15, 0, 0);
				tr1.setLayoutParams(params);
				tr2.setLayoutParams(params);
				
				
				
			}
			tv_desc = new TextView(this);
			tv_desc.setText(list.get(i).getItemName());
			tv_desc.setHeight(50);
			tv_desc.setTextColor(getResources().getColor(R.color.black));
			// tv_desc.setGravity(Gravity.CENTER);

			et_desc = new EditText(this);
			et_desc.setId(Integer.valueOf(list.get(i).getItemId()));// 设置ID
			et_desc.setHeight(40);
			et_desc.setImeOptions(EditorInfo.IME_ACTION_DONE);
			et_desc.setTextColor(getResources().getColor(R.color.black));
			et_desc.setKeyListener(numberKeyListenerx);
			et_desc.addTextChangedListener(new TextChangedListener(et_desc));
			et_desc.setBackgroundResource(R.drawable.edittext_normal);
			
			
			dp = DisPlay.findByItemValue(shop.getCustId(), "spdisplay", list
					.get(i).getItemValue());
			if (dp != null) {
				et_desc.setText(dp.getCounts());
			}

			tr1.addView(tv_desc);
			tr2.addView(et_desc);
			
		
			
			if (i % 2 != 0) {
				
				singleInput.addView(tr1);
				singleInput.addView(tr2);
			}else{
				tv_space1=new TextView(this);
				tv_space2=new TextView(this);
				tv_space1.setWidth(15);
				tv_space1.setHeight(40);
				tv_space2.setWidth(15);
				tv_space2.setHeight(40);
				tr1.addView(tv_space1);
				tr2.addView(tv_space2);
			}

			// singleInput.addView(tv_desc);
			// singleInput.addView(et_desc);

		}
		if (list.size() % 2 != 0) {
			singleInput.addView(tr1);
			singleInput.addView(tr2);
		}
		sv_singleSV.setVisibility(View.VISIBLE);
		initPhotoTable(PhotoType.SPCLZ);
	}

	// 绘制多点陈列
	private void DepictMultiView() {
		multiInputXPP.setPadding(10, 0, 10, 0);
		TextView tv_desc = null;
		EditText et_desc = null;
		TextView tv_space1=null;
		TextView tv_space2=null;
		// RelativeLayout rl = null;
		List<Dictionary> list = Dictionary.findbyTypeValueAndItemDesc(
				"mpdisplay", "XPP");
		TableRow tr1 = null;
		TableRow tr2 = null;
		DisPlay dp = new DisPlay();
		for (int i = 0; i < list.size(); i++) {
			// rl = new RelativeLayout(this);
			if (i % 2 == 0) {
				tr1 = new TableRow(DisplayActivity.this);
				tr2 = new TableRow(DisplayActivity.this);
				TableLayout.LayoutParams params = new TableLayout.LayoutParams();
				params.setMargins(0, 15, 0, 0);
				tr1.setLayoutParams(params);
				tr2.setLayoutParams(params);
			}
			tv_desc = new TextView(this);
			tv_desc.setText(list.get(i).getItemName());
			tv_desc.setTextColor(getResources().getColor(R.color.black));
			tv_desc.setHeight(50);
			// tv_desc.setGravity(Gravity.CENTER);

			et_desc = new EditText(this);
			et_desc.setId(Integer.valueOf(list.get(i).getItemId()));// 设置ID

			et_desc.setHeight(40);
			et_desc.setImeOptions(EditorInfo.IME_ACTION_DONE);
			et_desc.setTextColor(getResources().getColor(R.color.black));
			et_desc.setKeyListener(numberKeyListener);
			et_desc.setBackgroundResource(R.drawable.edittext_normal);
			et_desc.addTextChangedListener(new TextChangedListener(et_desc));
			dp = DisPlay.findByItemValueAndDesc(shop.getCustId(), "mpdisplay",
					list.get(i).getItemValue(), list.get(i).getItemDesc());
			if (dp != null) {
				et_desc.setText(dp.getCounts());
			}
			tr1.addView(tv_desc);
			tr2.addView(et_desc);
			if (i % 2 != 0) {
				multiInputXPP.addView(tr1);
				multiInputXPP.addView(tr2);
			}else{
				tv_space1=new TextView(this);
				tv_space2=new TextView(this);
				tv_space1.setWidth(15);
				tv_space1.setHeight(40);
				tv_space2.setWidth(15);
				tv_space2.setHeight(40);
				tr1.addView(tv_space1);
				tr2.addView(tv_space2);
			}

			// multiInputXPP.addView(tv_desc);
			// multiInputXPP.addView(et_desc);
			// multiInputXPP.addView(rl);
		}

		if (list.size() % 2 != 0) {
			multiInputXPP.addView(tr1);
			multiInputXPP.addView(tr2);
		}

		multiInputYLM.setPadding(10, 0, 10, 0);
		List<Dictionary> list2 = Dictionary.findbyTypeValueAndItemDesc(
				"mpdisplay", "YLM");
		// list=Dictionary.findbyTypeValueAndItemDesc(
		// "mpdisplay", "YLM");
		tr1 = null;
		tr2 = null;
		for (int i = 0; i < list2.size(); i++) {
			if (i % 2 == 0) {
				tr1 = new TableRow(DisplayActivity.this);
				tr2 = new TableRow(DisplayActivity.this);
				TableLayout.LayoutParams params = new TableLayout.LayoutParams();
				params.setMargins(0, 15, 0, 0);
				tr1.setLayoutParams(params);
				tr2.setLayoutParams(params);
			}
			// rl = new RelativeLayout(this);
			tv_desc = new TextView(this);
			tv_desc.setText(list2.get(i).getItemName());
			tv_desc.setTextColor(getResources().getColor(R.color.black));
			// tv_desc.setGravity(Gravity.CENTER);
			tv_desc.setHeight(50);
			et_desc = new EditText(this);
			int id = Integer.valueOf(list2.get(i).getItemId());

			et_desc.setId(id);// 设置ID
			//int idString = et_desc.getId();
			
			//Log.i("lhp", "id:" + idString);
			et_desc.setHeight(40);
			et_desc.setImeOptions(EditorInfo.IME_ACTION_DONE);
			et_desc.setTextColor(getResources().getColor(R.color.black));
			et_desc.setKeyListener(numberKeyListener);
			et_desc.setBackgroundResource(R.drawable.edittext_normal);
			et_desc.addTextChangedListener(new TextChangedListener(et_desc));
			dp = DisPlay.findByItemValueAndDesc(shop.getCustId(), "mpdisplay",
					list2.get(i).getItemValue(), list2.get(i).getItemDesc());
			if (dp != null) {
				et_desc.setText(dp.getCounts());
			}
			tr1.addView(tv_desc);
			tr2.addView(et_desc);
			if (i % 2 != 0) {
				multiInputYLM.addView(tr1);
				multiInputYLM.addView(tr2);
			}else{
				tv_space1=new TextView(this);
				tv_space2=new TextView(this);
				tv_space1.setWidth(15);
				tv_space1.setHeight(40);
				tv_space2.setWidth(15);
				tv_space2.setHeight(40);
				tr1.addView(tv_space1);
				tr2.addView(tv_space2);
			}
			// multiInputYLM.addView(tv_desc);
			// multiInputYLM.addView(et_desc);
			// multiInputYLM.addView(rl);
		}
		if (list2.size() % 2 != 0) {
			multiInputYLM.addView(tr1);
			multiInputYLM.addView(tr2);
		}

	}

	
	/** 按键监听 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				exitConfirm();
				// XPPApplication.exit(SingleDisplayActivity.this);
				break;
			case R.id.save:
				if(checkData()){
//				commit();
					if (savecache()) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("type", "display");
						XPPApplication.sendChangeBroad(DisplayActivity.this,
								XPPApplication.UPLOADDATA_RECEIVER, map);
						Toast.makeText(getApplicationContext(),
								getString(R.string.save_success),
								Toast.LENGTH_SHORT).show();
						XPPApplication.exit(DisplayActivity.this);
					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.save_fail), Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;
			case R.id.modifyBtn:
				if (viewFlag) {// 标准陈列 切换多点
					sv_singleSV.setVisibility(View.GONE);
					if (brandFlag) {// XXP
						sv_multiXPPSV.setVisibility(View.VISIBLE);
						initPhotoTable(PhotoType.XPPMULTI);
					} else {
						sv_multiYLMSV.setVisibility(View.VISIBLE);
						initPhotoTable(PhotoType.YLMMULTI);
					}
					viewFlag = false;
					tv_custName.setText(shop.getCustName() + "--"
							+ getString(R.string.multi_display));
					modifyBtn.setText(getString(R.string.single_display));
				} else {// 多点陈列切标准
					viewFlag = true;
					sv_multiYLMSV.setVisibility(View.GONE);
					sv_multiXPPSV.setVisibility(View.GONE);
					sv_singleSV.setVisibility(View.VISIBLE);
					initPhotoTable(PhotoType.SPCLZ);
					tv_custName.setText(shop.getCustName() + "--"
							+ getString(R.string.single_display));
					modifyBtn.setText(getString(R.string.multi_display));
				}
				break;
			case R.id.multiDisplayxpp:
				sv_multiYLMSV.setVisibility(View.GONE);
				sv_multiXPPSV.setVisibility(View.VISIBLE);
				brandFlag = true;
				initPhotoTable(PhotoType.XPPMULTI);
				break;
			case R.id.multiDisplayylm:
				sv_multiYLMSV.setVisibility(View.VISIBLE);
				sv_multiXPPSV.setVisibility(View.GONE);
				brandFlag = false;
				initPhotoTable(PhotoType.YLMMULTI);
				break;
			}
		}
	};

	private boolean savecache() {
		
		DisPlay dp = new DisPlay();
		try {
			// 保存标准陈列
			for (Dictionary dictionary : Dictionary
					.findbyTypeValue("spdisplay")) {
				EditText et = (EditText) findViewById(Integer
						.valueOf(dictionary.getItemId()));
				dp = DisPlay.findByItemValue(shop.getCustId(), "spdisplay",
						dictionary.getItemValue());
				String s = et.getText().toString();
				
				if ("".equals(s)) {
					continue;
				}
				if (dp == null && !"".equals(s)) {// 创建
					DisPlay ii = new DisPlay(shop.getCustId(),
							DataProviderFactory.getUserId(), "spdisplay",
							dictionary.getItemValue(),
							dictionary.getItemDesc(), s,
							DataProviderFactory.getDayType(),
							Status.UNSYNCHRONOUS);
					ii.save();
				} else {// 更新
					if (!s.equals(dp.getCounts())) {
						dp.setCounts(et.getText().toString());
						dp.setStatus(Status.UNSYNCHRONOUS);
						dp.update();
					}
				}
			}

			for (Dictionary dictionary : Dictionary
					.findbyTypeValue("mpdisplay")) {
				EditText et = (EditText) findViewById(Integer
						.valueOf(dictionary.getItemId()));
				dp = DisPlay.findByItemValue(shop.getCustId(), "mpdisplay",
						dictionary.getItemValue());
				String s = et.getText().toString();
				if ("".equals(s)) {
					continue;
				}
				
				if (dp == null) {// 创建
					DisPlay ii = new DisPlay(shop.getCustId(),
							DataProviderFactory.getUserId(), "mpdisplay",
							dictionary.getItemValue(),
							dictionary.getItemDesc(), s,
							DataProviderFactory.getDayType(),
							Status.UNSYNCHRONOUS);
					ii.save();
				} else {// 更新
					dp.setCounts(et.getText().toString());
					dp.setStatus(Status.UNSYNCHRONOUS);
					dp.update();
				}

			}
			List<PhotoType> list = new ArrayList<PhotoType>();
			list.add(PhotoType.SPCLZ);
			list.add(PhotoType.XPPMULTI);
			list.add(PhotoType.YLMMULTI);
			if (PhotoInfo.findByShop(shop.getCustId(), list) != 0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", "photo");
				XPPApplication.sendChangeBroad(DisplayActivity.this,
						XPPApplication.UPLOADDATA_RECEIVER, map);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//检查数据
	private boolean checkData(){
		try {
			// 保存标准陈列
			for (Dictionary dictionary : Dictionary
					.findbyTypeValue("spdisplay")) {
				EditText et = (EditText) findViewById(Integer
						.valueOf(dictionary.getItemId()));
				String s = et.getText().toString();
				if (!"".equals(s)) {
					try {
						//double 	 dd = Double.valueOf(s);
						int dd=Integer.valueOf(s);
						et.setText(String.valueOf(dd));
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),dictionary.getItemName()+":数字输入有误！" , Toast.LENGTH_SHORT).show();
						return false;
					}
				}
			}

			for (Dictionary dictionary : Dictionary
					.findbyTypeValue("mpdisplay")) {
				EditText et = (EditText) findViewById(Integer
						.valueOf(dictionary.getItemId()));
				String s = et.getText().toString();
				if (!"".equals(s)) {
					try {
						double	 dd = Double.valueOf(s);
						et.setText(String.valueOf(dd));
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),dictionary.getItemName()+":数字输入有误！" , Toast.LENGTH_SHORT).show();
						return false;
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}
	
	// if (sd != null) {// 更新
	// sd.setYgdb(et_yg.getText().toString());
	// sd.setHddb(et_hd.getText().toString());
	// sd.setGyhzdb(et_gyhz.getText().toString());
	// sd.setYlmdb(et_ylm.getText().toString());
	// return sd.update();
	// } else {// 创建
	// SingleDisplay single = new SingleDisplay
	// (shop.getCustId(), et_yg.getText().toString(),
	// et_hd.getText().toString(),
	// et_gyhz.getText().toString(),
	// et_ylm.getText().toString(),
	// DataProviderFactory.getUserId(),
	// DataProviderFactory.getDayType());
	// return single.save();
	// }

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

	public void initPhotoTable(PhotoType type) {
		tl_table = new TableLayout(this);
		tl_table = (TableLayout) findViewById(R.id.photoViewTable);

		ptList = PhotoInfo.findByShop(shop.getCustId(), type);

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
				PhotoType pt = null;
				String ptString=null;
				ptInfo.setPhotoName(photoNameAll);
				if (viewFlag) {
					pt = PhotoType.SPCLZ;
					ptString="标准陈列";
				} else {
					if (brandFlag) {
						pt = PhotoType.XPPMULTI;
						ptString="多点陈列";
					} else {
						pt = PhotoType.YLMMULTI;
						ptString="竞品多点陈列";
					}
				}
				ptInfo.setPtype(pt);
				ptInfo.setStatus(Status.NEW);
				ptInfo.setEmplid(DataProviderFactory.getUserId());
				ptInfo.setCustid(shop.getCustId());
				ptInfo.setCustName(shop.getCustName());
				// ptInfo.setRouteid();// 线路ID
				ptInfo.setActid("1");// 活动编号
				ptInfo.setTimestamp(PhotoUtil.getpicTime(photoNameAll));
				ptInfo.setSeq("1");
				ptInfo.setDayType(DataProviderFactory.getDayType());
				boolean b = PhotoInfo.save(ptInfo);
				String filestr=PictureShowUtils.getDirName()+photoNameAll + ".jpg";
				System.out.println("照片路径："+filestr);
				ptInfo.setPhototype(ptString);
				Thread task=PhotoUtil.dealPhotoFile(filestr,ptInfo);
				if (b) {// 存照片信息
					// MultiDisplay ii = new MultiDisplay();
					// if (ii.getPhotoId() != null) {
					// ii.setPhotoId(ii.getPhotoId() + ","
					// + photoNameAll);
					// } else {
					// ii.setPhotoId(photoNameAll);
					// }
					// ii.update();
					// /** 压缩 **/
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

	private OnClickListener photoItemClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final MyImageButton b = (MyImageButton) v;
			final String p = (String) b.getTag(R.string.tag1);
			Intent intent = new Intent(DisplayActivity.this,
					ShowImageActivity.class);
			intent.putExtra("dir", p);
			startActivityForResult(intent, 213);
		}
	};

	private OnLongClickListener photoLongClick = new OnLongClickListener() {
		public boolean onLongClick(final View arg0) {
			View overdiaView = View.inflate(DisplayActivity.this,
					R.layout.dialog_confirmation, null);
			final Dialog overdialog = new Dialog(DisplayActivity.this,
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
						initPhotoTable(PhotoType.SPCLZ);
					}
					overdialog.cancel();
				}
			});
			overdialog.show();
			return false;
		}
	};

	private NumberKeyListener numberKeyListener = new NumberKeyListener() {
		public int getInputType() {
			// 0无键盘 1英文键盘 2模拟键盘 3数字键盘
			return 3;
		}

		@Override
		protected char[] getAcceptedChars() {
			char[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','.' };// ,'.'
			return c;
		}
	};
	private NumberKeyListener numberKeyListenerx = new NumberKeyListener() {
		public int getInputType() {
			// 0无键盘 1英文键盘 2模拟键盘 3数字键盘
			return 3;
		}

		@Override
		protected char[] getAcceptedChars() {
			char[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };// ,'.'
			return c;
		}
	};

	// 重写手机返回按钮功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitConfirm();
			// XPPApplication.exit(SingleDisplayActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void exitConfirm() {
		View overdiaView1 = View.inflate(DisplayActivity.this,
				R.layout.dialog_confirmation, null);
		final Dialog overdialog1 = new Dialog(DisplayActivity.this,
				R.style.dialog_xw);
		overdialog1.setContentView(overdiaView1);
		overdialog1.setCanceledOnTouchOutside(false);
		Button overcancel1 = (Button) overdiaView1
				.findViewById(R.id.dialog_cancel_btn);
		TextView TextView02 = (TextView) overdiaView1
				.findViewById(R.id.TextView02);
		TextView02.setText("确认退出编辑？");
		overcancel1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				overdialog1.cancel();
			}
		});
		Button overok1 = (Button) overdiaView1.findViewById(R.id.dialog_ok_btn);
		overok1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				overdialog1.cancel();
				XPPApplication.exit(DisplayActivity.this);
			}
		});
		overdialog1.show();
	}

	private void commit() {
		View overdiaView1 = View.inflate(DisplayActivity.this,
				R.layout.dialog_confirmation, null);
		final Dialog overdialog1 = new Dialog(DisplayActivity.this,
				R.style.dialog_xw);
		overdialog1.setContentView(overdiaView1);
		overdialog1.setCanceledOnTouchOutside(false);
		Button overcancel1 = (Button) overdiaView1
				.findViewById(R.id.dialog_cancel_btn);
		TextView TextView02 = (TextView) overdiaView1
				.findViewById(R.id.TextView02);
		TextView02.setText("确认提交信息？");
		overcancel1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				overdialog1.cancel();
			}
		});
		Button overok1 = (Button) overdiaView1.findViewById(R.id.dialog_ok_btn);
		overok1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				overdialog1.cancel();
		
				if (savecache()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "display");
					XPPApplication.sendChangeBroad(DisplayActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_success),
							Toast.LENGTH_SHORT).show();
					XPPApplication.exit(DisplayActivity.this);
				} else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_fail), Toast.LENGTH_SHORT)
							.show();
				}
			}
			
		});
		overdialog1.show();
	}
	
	private class TextChangedListener implements TextWatcher {
        private EditText editText = null;
        
        private TextChangedListener(EditText e){
        	super();
        	editText = e;
        }

        @Override    
        public void onTextChanged(CharSequence s, int start, int before,    
                int count) {    
            // TODO Auto-generated method stub   
        	if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 2);
                    editText.setText(s);
                    editText.setSelection(s.length());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                editText.setText(s);
                editText.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    editText.setText(s.subSequence(0, 1));
                    editText.setSelection(1);
                    return;
                }
            } 
             int i = s.toString().length(); 
            if (i >= 2 && s.charAt(i-2) == '.' && s.toString().endsWith(".")) {
              //  if (!s.toString().substring(1, 2).equals(".")) {
                    editText.setText(s.subSequence(0, i-1));
                    editText.setSelection(i-1);
                    return;
               // }
            }
        }    

        @Override    
        public void beforeTextChanged(CharSequence s, int start, int count,    
                int after) {    
            // TODO Auto-generated method stub    
        }    

        @Override    
        public void afterTextChanged(Editable s) {    
            // TODO Auto-generated method stub       
             
        }    
    }

}
