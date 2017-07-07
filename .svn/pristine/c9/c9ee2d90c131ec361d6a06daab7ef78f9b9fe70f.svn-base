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
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.screens.DisplayActivity.LoadImageAsyncTask;
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
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;
/**
 * 铺货管理倒萨倒萨大
 * **/
@SuppressLint("UseSparseArrays")
public class DistributionActivity extends Activity {
	private Customer shop;
	private TextView tv_custName;
	private TableLayout tl_CategorySortList;
	private HashMap<String, String> state;
	private List<Distribution> distributionList;
	private Button btn_product ,btn_competingProduct ;
	private TableLayout photo_table;
	private List<PhotoInfo> photoList = new ArrayList<PhotoInfo>();
	private String dir, photoNameAll;
	private int width, height;
	private boolean viewFlag = true;// 用于控制显示陈列类型 1本品2 竞品
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_distribution);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		initView();
		initData();
		initPhotoTable(PhotoType.XPPDIST);
	}

	public void initView() {
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.save).setOnClickListener(BtnClicked);
		tl_CategorySortList = (TableLayout) findViewById(R.id.tab);
		tv_custName = (TextView) findViewById(R.id.custName);
		btn_product= (Button) findViewById(R.id.product);
		btn_product.setOnClickListener(BtnClicked);
		btn_competingProduct= (Button) findViewById(R.id.competingProduct);
		btn_competingProduct.setOnClickListener(BtnClicked);
		
	}

	public void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
		}
		tv_custName.setText(shop.getCustName()+"__"+getString(R.string.distribution));
		state  = new HashMap<String, String>();
		distributionList = Distribution.findByCustId(shop.getCustId());
		for (Distribution distribution : distributionList) {
			state.put(distribution.getSkuId(), "B");
		}
		addCheckBox(tl_CategorySortList,"1");
		
	}
	

	
	// 增加checkBox
	public void addCheckBox(TableLayout table,String type) {
		for (Product product : Product.findProductsXpp(type)) {// 列本品
			TableRow row = new TableRow(this);
			row.setBackgroundResource(R.drawable.item_background_clicking);
			CheckBox cb = new CheckBox(this);
			cb.setButtonDrawable(R.drawable.checkbox_selector);
			cb.setTextColor(Color.BLACK);
			cb.setText(product.getCategoryDesc());// 设置显示名
			cb.setTag(R.string.tag1, product.getCategoryId());// 设置id
			cb.setTag(R.string.tag2, "B");// 设置类型 
			cb.setButtonDrawable(R.drawable.checkbox_selector);
			String mp = state.get(product.getCategoryId());
			if (mp != null && !"".equals(mp)) {
				cb.setChecked(true);
			}
			cb.setOnCheckedChangeListener(ckListener);
			row.addView(cb);
			table.addView(row);
		}
	}

	private OnCheckedChangeListener ckListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			CheckBox b = (CheckBox) buttonView;
			String id =  (String) b.getTag(R.string.tag1);
			String name = (String) b.getTag(R.string.tag2);
			if (isChecked) {
				state.put(id, name);
			} else {
				state.remove(id);
			}
		}
	};

	public boolean saveDistribution() {
		try {
//			Distribution.deleteNotIn(shop.getCustId());
			for (Distribution distribution :distributionList) {
				boolean flag = true;
				for (Map.Entry entry : state.entrySet()) {
					String key = entry.getKey().toString();
					if(key.equals(distribution.getSkuId())){
						flag = false;
						break;
					}
				}
				if(flag){
					distribution.delete();
				}
			}
			
			for (Map.Entry entry : state.entrySet()) {
				String key = entry.getKey().toString();
				Distribution di = Distribution.findByCustIdAndSkuId(shop.getCustId(), key);
				if(di==null){
				Distribution distribution = new Distribution(shop.getCustId(), 
						key, Status.UNSYNCHRONOUS, "", DataProviderFactory.getDayType(), 
						DataProviderFactory.getUserId());
				distribution.save();
				}
			}
			
			
			
			return  true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** 按键监听 */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(DistributionActivity.this);
				break;
			case R.id.save:
				if (saveDistribution()) {
					if(state.size()!=0){
						List<PhotoType> list = new ArrayList<PhotoType>();
						list.add(PhotoType.XPPDIST);
						list.add(PhotoType.YLMDIST);
						if (PhotoInfo.findByShop(shop.getCustId(), list) != 0) {
							Map<String, String> map = new HashMap<String, String>();
							map.put("type", "photo");
							XPPApplication.sendChangeBroad(DistributionActivity.this,
									XPPApplication.UPLOADDATA_RECEIVER, map);
						}
						
						Map<String,String> map = new HashMap<String, String>();
						map.put("type", "distribution");
						map.put("custId", shop.getCustId());
						XPPApplication.sendChangeBroad(DistributionActivity.this,
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
				XPPApplication.exit(DistributionActivity.this);
				break;
			case R.id.product :
				viewFlag = true;
				btn_product.setBackgroundResource(R.drawable.btn_orange);
				btn_competingProduct.setBackgroundResource(R.drawable.btn_grey_selector);
				btn_product.setEnabled(false);
				btn_competingProduct.setEnabled(true);
				tl_CategorySortList.removeAllViews();
				addCheckBox(tl_CategorySortList,"1");
				initPhotoTable(PhotoType.XPPDIST);
				break;
			case R.id.competingProduct :
				viewFlag = false;
				btn_competingProduct.setBackgroundResource(R.drawable.btn_orange);
				btn_product.setBackgroundResource(R.drawable.btn_grey_selector);
				btn_competingProduct.setEnabled(false);
				btn_product.setEnabled(true);
				tl_CategorySortList.removeAllViews();
				addCheckBox(tl_CategorySortList,"2");
				initPhotoTable(PhotoType.YLMDIST);
				break;
				
			}
		}
	};
	public void initPhotoTable(PhotoType type) {
		photo_table = new TableLayout(this);
		photo_table = (TableLayout) findViewById(R.id.photoviewTable);

		photoList = PhotoInfo.findByShop(shop.getCustId(), type);

		new LoadImageAsyncTask().execute(photoList);
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
	public class LoadImageAsyncTask extends
	AsyncTask<List<PhotoInfo>, Integer, Map<String, Bitmap>> {
		List<PhotoInfo> photoList = new ArrayList<PhotoInfo>();

		protected void onPreExecute() {
		}

		@Override
		protected void onPostExecute(Map<String, Bitmap> maps) {
			photo_table.removeAllViews();
			addRow(photoList, maps, photo_table);
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
						pt = PhotoType.XPPDIST;
						ptString="铺货";
					} else {
						pt = PhotoType.YLMDIST;
						ptString="竞品铺货";
					}
				ptInfo.setPtype(pt);
				ptInfo.setStatus(Status.NEW);
				ptInfo.setEmplid(DataProviderFactory.getUserId());
				ptInfo.setCustid(shop.getCustId());
				ptInfo.setCustName(shop.getCustName());
				// ptInfo.setRouteid();// 线路ID
				ptInfo.setTimestamp(PhotoUtil.getpicTime(photoNameAll));
				ptInfo.setSeq("2");
				ptInfo.setDayType(DataProviderFactory.getDayType());
				boolean b = PhotoInfo.save(ptInfo);
				String filestr=PictureShowUtils.getDirName()+photoNameAll + ".jpg";
				System.out.println("照片路径："+filestr);
				ptInfo.setPhototype(ptString);
				Thread task=PhotoUtil.dealPhotoFile(filestr,ptInfo);
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
	private OnLongClickListener photoLongClick = new OnLongClickListener() {
		public boolean onLongClick(final View arg0) {
			View overdiaView = View.inflate(DistributionActivity.this,
					R.layout.dialog_confirmation, null);
			final Dialog overdialog = new Dialog(DistributionActivity.this,
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
						initPhotoTable(PhotoType.XPPDIST);
					}
					overdialog.cancel();
				}
			});
			overdialog.show();
			return false;
		}
	};
	private OnClickListener photoItemClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final MyImageButton b = (MyImageButton) v;
			final String p = (String) b.getTag(R.string.tag1);
			Intent intent = new Intent(DistributionActivity.this,
					ShowImageActivity.class);
			intent.putExtra("dir", p);
			startActivity(intent);
			//startActivityForResult(intent, 123);
		}
	};
	// 重写手机返回按钮功能
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				XPPApplication.exit(DistributionActivity.this);
				return false;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
}
