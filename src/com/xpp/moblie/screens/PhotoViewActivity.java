package com.xpp.moblie.screens;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.util.MyImageButton;
import com.xpp.moblie.util.MyUtil;
import com.xpp.moblie.util.PhotoUtil;
import com.xpp.moblie.util.PictureShowUtils;
import com.xpp.moblie.util.TimeUtil;
import com.xpp.moblie.util.UploadUtil.OnUploadProcessListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Title: 拜访任务之拍照 Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016年11月28日 下午1:27:18
 */
@SuppressLint("NewApi")
public class PhotoViewActivity extends Activity implements OnClickListener,
		OnUploadProcessListener {
	@SuppressWarnings("unused")
	private static final String TAG = "uploadImage";
	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2; //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;
	
	private ProgressBar progressBar;
	private TextView tv_title;
	private ProgressDialog progressDialog;
	private String photoNameAll;// 文件名
	private Dialog waitingDialog;
	public Class backActivity;// 返回按钮执行Activity
	public Class successActivity;// 上传成功后 执行的Activity
	private String dir;
	private int width;
	private int height;
	private Customer shop;
	private BaseParameter type;
	private TableLayout table;
	// 设置图片路径
	private List<PhotoInfo> photoInfoList;
	private Customer customer;
	// 定位相关
	private TextView tv1, tv2, tv3;
	private LocationClient lc = null;// 声明LocationClient类
	private MyBaidulistener listener = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_photo_view);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		initView();
		initPhotoTable();
		inidingwei();
	}

	// 定位
	private void inidingwei() {
		lc = new LocationClient(getApplicationContext());
		listener = new MyBaidulistener();
		lc.registerLocationListener(listener);// 注册监听函数
		setviews();
		lc.start();

	}

	public void setviews() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		lc.setLocOption(option);
	}

	/**
	 * 实现BDLocationListener接口 Title: Description: XPPMobileTerminal
	 * 
	 * @author: xg.chen
	 * @date:2016年11月28日 下午1:30:42
	 */
	public class MyBaidulistener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location.getProvince() != null) {
				tv1.setText("" + location.getProvince() + ""
						+ location.getCity() + "" + location.getDistrict() + ""
						+ location.getStreet() + ""
						+ location.getStreetNumber());
				tv2.setText("" + location.getLongitude());
				tv3.setText("" + location.getLatitude());
				lc.stop();
				lc = null;
				System.gc();
			} else {
				tv1.setText("");
				tv2.setText("");
				tv3.setText("");
			}
		}

	}

	// 解除绑定
	@Override
	protected void onDestroy() {
		if (lc != null) {
			lc.stop();
		}
		super.onDestroy();
	}

	/**
	 * 初始化数据
	 */
	private void initView() {
		findViewById(R.id.save).setOnClickListener(this);
		findViewById(R.id.home_back).setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.dizhi_mingxi);
		tv2 = (TextView) findViewById(R.id.tvs_jingdu);
		tv3 = (TextView) findViewById(R.id.tvs_weidu);
		tv_title = (TextView) this.findViewById(R.id.phot_title);
		progressDialog = new ProgressDialog(this);
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
			backActivity = (Class) bun.get("back_class");
			successActivity = (Class) bun.get("success_class");
			type = (BaseParameter) bun.get("title");
			tv_title.setText(shop.getCustName() + "__" + type.getObj1());
		}

	}

	/**
	 * MethodsTitle: 初始化照片列表
	 * 
	 * @author: xg.chen
	 * @date:2016年11月28日 下午1:33:57
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void initPhotoTable() {
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.photoViewTable);
		PhotoType photoType = null;
		if (type.getObj2().equals("0")) {
			photoType = PhotoType.DTZ;
		}
		// TODO：空指针异常导致app闪退(getPhotoInfoDao()查询的时候有空指针异常的情况)
		if (shop.getCustId() != null) {
			photoInfoList = PhotoInfo.findByShop(shop.getCustId(), photoType);
		} else {
			Toast.makeText(getApplicationContext(), "客户编号不存在，请清理缓存删除后重试！",
					Toast.LENGTH_SHORT).show();
		}
		if (photoInfoList == null || photoInfoList.size() == 0) {
			List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
			new LoadImageAsyncTask().execute(photoInfoList);
		} else {
			new LoadImageAsyncTask().execute(photoInfoList);
		}
	}

	/**
	 * 拍照上传
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:// 上传
			if (photoInfoList == null || photoInfoList.size() == 0) {
				Toast.makeText(getApplicationContext(), "先拍照在保存",
						Toast.LENGTH_SHORT).show();
				break;
			}
			handler.sendEmptyMessage(TO_UPLOAD_FILE);
			for (PhotoInfo photoInfo : photoInfoList) {
				PhotoInfo.submitPhoto(photoInfo);
			}
			UploadImageCustAsyncTask uploadImageCustAsyncTask = new UploadImageCustAsyncTask();
			uploadImageCustAsyncTask.execute(photoInfoList);
			break;
		case R.id.home_back:// 返回home
			XPPApplication.exit(PhotoViewActivity.this);
			break;
		default:
			break;
		}
	}

	/**
	 * MethodsTitle: 拍照
	 * 
	 * @author: xg.chen
	 * @date:2016年12月13日 上午9:50:14
	 * @version 1.0
	 */
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

	@SuppressWarnings("deprecation")
	@Override
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
				ptInfo.setPtype(PhotoType.DTZ);
				ptInfo.setStatus(Status.NEW);
				ptInfo.setEmplid(DataProviderFactory.getUserId());
				ptInfo.setCustid(shop.getCustId());
				ptInfo.setCustName(shop.getCustName());
				ptInfo.setActid("1");// 活动编号
				//ptInfo.setTimestamp(PhotoUtil.getpicTime(photoNameAll));//设置时间戳--拍照文件名
				ptInfo.setTimestamp(TimeUtil.getStringTime());//设置时间戳--直接获取服务器时间
				ptInfo.setSeq("1");
				ptInfo.setDayType(DataProviderFactory.getDayType());
				String filestr = PictureShowUtils.getDirName() + photoNameAll
						+ ".jpg";
				ptInfo.setPhototype("店头照");
				Thread task = PhotoUtil.dealPhotoFile(filestr, ptInfo);
				boolean b = PhotoInfo.save(ptInfo);
				/** 压缩 **/
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
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * MethodsTitle: 图片水印
	 * @author: xg.chen
	 * @date:2017年3月28日 下午1:54:00
	 * @version 1.0
	 * @param photoList
	 * @param picMap
	 * @param table
	 */
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

	private OnClickListener photoItemClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			final MyImageButton b = (MyImageButton) v;
			final String p = (String) b.getTag(R.string.tag1);
			Intent intent = new Intent(PhotoViewActivity.this,
					ShowImageActivity.class);
			intent.putExtra("dir", p);
			startActivityForResult(intent, 213);
		}
	};

	private OnLongClickListener photoLongClick = new OnLongClickListener() {

		public boolean onLongClick(final View arg0) {
			View overdiaView = View.inflate(PhotoViewActivity.this,
					R.layout.dialog_confirmation, null);
			final Dialog overdialog = new Dialog(PhotoViewActivity.this,
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

	/**
	 * 上传服务器响应回调
	 */
	public void onUploadDone(int responseCode, String message) {
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TO_UPLOAD_FILE:// 上传照片
				handler.sendEmptyMessage(UPLOAD_FILE_DONE);
				break;
			case UPLOAD_INIT_PROCESS:
				progressBar.setMax(msg.arg1);
				break;
			case UPLOAD_IN_PROCESS:
				progressBar.setProgress(msg.arg1);
				break;
			case UPLOAD_FILE_DONE:
				sendNotice();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void onUploadProcess(int uploadSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_IN_PROCESS;
		msg.arg1 = uploadSize;
		handler.sendMessage(msg);
	}

	public void initUpload(int fileSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_INIT_PROCESS;
		msg.arg1 = fileSize;
		handler.sendMessage(msg);
	}

	private void showWaitingDialog() {
		if (waitingDialog == null) {

			waitingDialog = new Dialog(this, R.style.TransparentDialog);
			waitingDialog.setContentView(R.layout.login_waiting_dialog);
			DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
				public void onShow(DialogInterface dialog) {
					ImageView img = (ImageView) waitingDialog
							.findViewById(R.id.loading);
					((AnimationDrawable) img.getDrawable()).start();
				}
			};
			DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
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

	/**
	 * 返回键重写
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(PhotoViewActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void sendNotice() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "photo");
		map.put("custId", shop.getCustId());
		XPPApplication.sendChangeBroad(PhotoViewActivity.this,
				XPPApplication.UPLOADDATA_RECEIVER, map);
		XPPApplication.exit(PhotoViewActivity.this);
	}

	/**
	 * Title: 异步加载图片 Description: XPPMobileTerminal
	 * 
	 * @author: xg.chen
	 * @date:2016年12月12日 下午3:24:48
	 */
	public class LoadImageAsyncTask extends
			AsyncTask<List<PhotoInfo>, Integer, Map<String, Bitmap>> {
		List<PhotoInfo> photoList = new ArrayList<PhotoInfo>();

		protected void onPreExecute() {
			showWaitingDialog();
		}

		@Override
		protected void onPostExecute(Map<String, Bitmap> maps) {
			table.removeAllViews();
			addRow(photoList, maps, table);
			dismissWaitingDialog();
		}

		@Override
		protected Map<String, Bitmap> doInBackground(List<PhotoInfo>... params) {
			photoList = params[0];
			return MyUtil.buildThum(photoList, width, height);
		}
	}

	/**
	 * Title: 上传照片经纬度 Description: XPPMobileTerminal
	 * 
	 * @author: xg.chen
	 * @date:2016年11月28日 下午1:43:19
	 */
	public class UploadImageCustAsyncTask extends
			AsyncTask<List<PhotoInfo>, Integer, String> {
		String result = null;

		@Override
		protected void onPostExecute(String result) {
		}

		@SuppressWarnings({ })
		@Override
		protected String doInBackground(List<PhotoInfo>... params) {
			if (photoInfoList.size() >= 1) {
				customer = Customer.findByCustId(photoInfoList.get(0)
						.getCustid());
				customer.setCustId(photoInfoList.get(0).getCustid());
				customer.setLongitude(tv2.getText().toString());
				customer.setLatitude(tv3.getText().toString());
				customer.update();
				// 上传服务器数据（经纬度）
				result = DataProviderFactory.getProvider().createCustomer(
						customer);
			} else {
				Toast.makeText(getApplicationContext(), "先拍照在保存",
						Toast.LENGTH_SHORT).show();
			}
			return result;
		}
	}

}