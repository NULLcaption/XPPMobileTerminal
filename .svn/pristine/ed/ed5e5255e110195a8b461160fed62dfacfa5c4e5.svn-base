package com.xpp.moblie.screens;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseDivision;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.UpdateTask;
import com.xpp.moblie.query.Channel;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.query.Sign;
import com.xpp.moblie.query.UserInfo;
import com.xpp.moblie.screens.MyLocationActivity.allCustomerTask;
import com.xpp.moblie.screens.PhotoViewActivity.LoadImageAsyncTask;
import com.xpp.moblie.screens.PhotoViewActivity.UploadImageCustAsyncTask;
import com.xpp.moblie.util.MyImageButton;
import com.xpp.moblie.util.MyUtil;
import com.xpp.moblie.util.PhotoUtil;
import com.xpp.moblie.util.PictureShowUtils;
import com.xpp.moblie.util.TimeUtil;
import com.xpp.moblie.util.ToastUtil;
import com.xpp.moblie.util.UploadUtil.OnUploadProcessListener;

import android.R.anim;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * Title: 签到按钮 Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016年12月1日 上午11:16:02
 */
public class SignInActivity extends Activity implements OnUploadProcessListener {
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
	private String dir;
	private int width;
	private int height;
	// private PhotoInfo type;

	private TableLayout table;
	private ProgressDialog progressDialog;
	private ProgressBar progressBar;

	// 设置图片路径
	// private static final String dirName = Environment
	// .getExternalStorageDirectory().toString() + "/CRC/";
	private List<PhotoInfo> photoInfoList;
	private String photoNameAll;// 文件名
	private Button btn_sign_in;// btn_searchAreaAddress,
	private TextView tv_title, sign_in_time;
	private Dialog waitingDialog;
	private Dialog overdialog;
	private TextView sign_in_address, tv3_address, sign_in_user;
	private Sign signResult;
	private Sign sign_in;
	// 定位
	private LocationClient lc = null;
	private MyBaidulistener mylistener = null;
	private String jindu, weidu, address;

	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.layout_sign_in);
		super.onCreate(savedInstanceState);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		initView();
		initData();
		initPhotoTable();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.sign_name);
		sign_in_time = (TextView) findViewById(R.id.sign_in_time);
		sign_in_address = (TextView) findViewById(R.id.sign_in_address);
		sign_in_user = (TextView) findViewById(R.id.sign_in_user);
		tv3_address = (TextView) findViewById(R.id.tv3_address);
		btn_sign_in = (Button) findViewById(R.id.btn_sign_in);

		tv3_address.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("simple text",
						tv3_address.getText());
				clipboard.setPrimaryClip(clip);
				Toast.makeText(getApplicationContext(), "地址已复制！",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.btn_sign_in).setOnClickListener(BtnClicked);

	}

	private void indingwei() {
		lc = new LocationClient(getApplicationContext());
		mylistener = new MyBaidulistener();
		lc.registerLocationListener(mylistener);
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

	public class MyBaidulistener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			for (Double i = -0.020; i <= 0.020; i = i + 0.01) {

			}
			if (location.getProvince() != null) {
				jindu = location.getLongitude() + "";
				weidu = location.getLatitude() + "";
				address = location.getProvince() + "" + location.getCity() + ""
						+ location.getDistrict() + "" + location.getStreet()
						+ "" + location.getStreetNumber();
				tv3_address.setText("当前地址：" + address);
				lc.stop();
				lc = null;
			} else {
				jindu = "";
				weidu = "";
				address = "";
				tv3_address.setText("当前地址:暂时无法获取!");
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

	private void initData() {
		indingwei();// 获取经纬度
		GetSignTask getSignTask = new GetSignTask();
		getSignTask.execute("");
	}

	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(SignInActivity.this);
				break;
			case R.id.btn_sign_in:
				sign("S");
				break;
			}
		}

	};

	/**
	 * @Description:
	 * @author:xg.che
	 * @date:2017年4月19日 上午9:34:40
	 * @version:1.0
	 */
	public void sign(final String sign_type) {
		if ("".equals(jindu)) {
			Toast.makeText(getApplicationContext(), "无法获取地址，暂时无法操作",
					Toast.LENGTH_SHORT).show();
			return;
		}
		View overdiaView = View.inflate(SignInActivity.this,
				R.layout.dialog_confirmation, null);
		final Dialog overdialog = new Dialog(SignInActivity.this,
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

		TextView titleView = (TextView) overdiaView
				.findViewById(R.id.TextView01);
		TextView confirmationView = (TextView) overdiaView
				.findViewById(R.id.TextView02);
		if ("S".equals(sign_type)) {
			titleView.setText("签到");
			confirmationView.setText(tv3_address.getText());
		} else {
			titleView.setText("签退");
			confirmationView.setText(tv3_address.getText());

		}
		Button overok = (Button) overdiaView.findViewById(R.id.dialog_ok_btn);
		overok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (photoInfoList == null || photoInfoList.size() == 0) {
					Toast.makeText(getApplicationContext(), "先拍照在保存",
							Toast.LENGTH_SHORT).show();
					return;
				}
				handler.sendEmptyMessage(TO_UPLOAD_FILE);
				for (PhotoInfo photoInfo : photoInfoList) {
					PhotoInfo.submitPhoto(photoInfo);
				}
				CreateSignTask createSignTask = new CreateSignTask();
				createSignTask.execute(sign_type);

				overdialog.cancel();
			}
		});
		overdialog.show();
	}
	/**
	 * @Description:添加签到任务
	 * @author:xg.chen
	 * @time:2017年4月19日 上午10:12:49
	 * @version:1.0
	 */
	public class CreateSignTask extends AsyncTask<String, Integer, Sign> {
		@Override
		protected void onProgressUpdate(Integer... values) {
		}
		protected void onPreExecute() {
			showWaitingDialog();
		}
		
		@SuppressLint("SimpleDateFormat")
		@Override
		protected Sign doInBackground(String... sign_type) {
			Sign sign = new Sign();
			sign.setAddress(address);
			sign.setLatitude(weidu);
			sign.setLongitude(jindu);
			sign.setSign_type(sign_type[0]);
			sign.setOperator_id(DataProviderFactory.getUserId());
			signResult = DataProviderFactory.getProvider().createSign(sign);
			if (signResult != null) {
				signResult.setCreate_date(new Date());
				signResult.save();
			}
			return signResult;
		}
		@Override
		protected void onPostExecute(Sign signResult) {
			dismissWaitingDialog();
			System.out.println("result" + signResult);
			if (signResult != null) {
				if ("S".equals(signResult.getSign_type())) {
					setsignin(signResult);
					Toast.makeText(getApplicationContext(), "签到成功！",
							Toast.LENGTH_SHORT).show();
				}
			}

		}

	}

	public class GetSignTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onProgressUpdate(Integer... values) {

		}

		protected void onPreExecute() {
			showWaitingDialog();

		}

		@SuppressLint("SimpleDateFormat")
		@Override
		protected String doInBackground(String... sign_type) {
			Sign sign = new Sign();
			// sign.setAddress(address);
			// sign.setLatitude(weidu);
			// sign.setLongitude(jindu);
			// sign.setSign_type(sign_type[0]);
			sign.setOperator_id(DataProviderFactory.getUserId());
			// System.out.println(sign);
			sign.setSign_type("S");
			sign_in = sign.getTodaySign(sign);
			if (sign_in == null) {
				sign_in = DataProviderFactory.getProvider().getSignList(sign);
				if (sign_in != null) {
					/*String time = TimeUtil.getFormatDate();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date date = null;
					try {
						date = sdf.parse(time);
					} catch (ParseException e) {
						e.printStackTrace();
					}*/
					sign_in.setCreate_date(new Date());
					sign_in.save();
				}
			}
			return "" + sign_in;
		}

		@Override
		protected void onPostExecute(String signResult) {
			dismissWaitingDialog();
			if (sign_in != null) {
				setsignin(sign_in);
			}

		}
	}

	/**
	 * @Description:设置签到详细信息
	 * @author:xg.chen
	 * @date:2017年4月19日 上午9:27:15
	 * @version:1.0
	 */
	public void setsignin(Sign sign_in) {
		sign_in_address.setText("签到地点:" + sign_in.getAddress());
		sign_in_time.setText("签到时间:" + sign_in.getSign_date());
		UserInfo sign_in_userInfo = UserInfo.findById(sign_in.getOperator_id());
		String user;
		if (sign_in_userInfo != null) {
			user = sign_in_userInfo.getUserName();
		} else {
			user = sign_in.getOperator_id();
		}
		sign_in_user.setText("签到人:" + user);
	}

	/**
	 * @Description:获取照片
	 * @author:xg.chen
	 * @date:2017年4月19日 上午9:28:28
	 * @version:1.0
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
				ptInfo.setPtype(PhotoType.SIGNIN);
				ptInfo.setStatus(Status.NEW);
				ptInfo.setEmplid(DataProviderFactory.getUserId());
				if (address != null && !"".equals(address)) {
					ptInfo.setPhotoaddress(address);
				} else {
					ptInfo.setPhotoaddress("");
				}

				// ptInfo.setCustid(shop.getCustId());
				// ptInfo.setCustName(shop.getCustName());
				// ptInfo.setRouteid();// 线路ID
				ptInfo.setActid("1");// 活动编号
				ptInfo.setTimestamp(PhotoUtil.getpicTime(photoNameAll));
				ptInfo.setSeq("1");
				ptInfo.setDayType(DataProviderFactory.getDayType());
				String filestr = PictureShowUtils.getDirName() + photoNameAll
						+ ".jpg";
				System.out.println("照片路径：" + filestr);
				ptInfo.setPhototype("签到拍照");
				Thread task = PhotoUtil.dealPhotoFile(filestr, ptInfo);
				boolean b = PhotoInfo.save(ptInfo);
				System.out.println(b);
				/** 压缩 **/
				try {
					task.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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

	public void initPhotoTable() {
		table = new TableLayout(this);
		table = (TableLayout) findViewById(R.id.photoViewTable);
		PhotoType photoType = PhotoType.SIGNIN;
		photoInfoList = PhotoInfo.findByEmplid(photoType);
		new LoadImageAsyncTask().execute(photoInfoList);
	}

	/**
	 * 异步加载图片
	 * 
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
				// b.setHeight(height);
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
				if (address != null && !"".equals(address)) {
					gotoPZ();
				} else {
					Toast.makeText(getApplicationContext(), "无法获取地址，暂时无法操作",
							Toast.LENGTH_SHORT).show();
				}

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
			Intent intent = new Intent(SignInActivity.this,
					ShowImageActivity.class);
			intent.putExtra("dir", p);
			startActivityForResult(intent, 213);
		}
	};

	private OnLongClickListener photoLongClick = new OnLongClickListener() {

		public boolean onLongClick(final View arg0) {
			View overdiaView = View.inflate(SignInActivity.this,
					R.layout.dialog_confirmation, null);
			final Dialog overdialog = new Dialog(SignInActivity.this,
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
				// int all = 0;
				// int f = 0;
				// for (PhotoInfo photoInfo : photoInfoList) {
				// if (Status.NEW.equals(photoInfo.getStatus())
				// || Status.UNSYNCHRONOUS.equals(photoInfo
				// .getStatus())){
				// if (!DataProviderFactory.getProvider().uploadPicture(
				// photoInfo)){
				// f++;
				// }
				// all++;
				// }
				// }
				// if(all!=0){
				// Toast.makeText(getApplicationContext(),
				// f == 0 ? "上传成功" : "失败" + String.valueOf(f) + "条",
				// Toast.LENGTH_SHORT).show();
				// }
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

	private void sendNotice() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "photo");
		map.put("custId", "");
		XPPApplication.sendChangeBroad(SignInActivity.this,
				XPPApplication.UPLOADDATA_RECEIVER, map);
		// XPPApplication.exit(SignInActivity.this);
	}

	// 取消手机返回按钮功能
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(SignInActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
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
