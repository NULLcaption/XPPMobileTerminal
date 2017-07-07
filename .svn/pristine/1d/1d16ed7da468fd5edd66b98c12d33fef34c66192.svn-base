package com.xpp.moblie.screens;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseDivision;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Channel;
import com.xpp.moblie.query.CuanhuoQuery;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.util.TimeUtil;
import com.xpp.moblie.util.ToastUtil;







public class ChuanhaoActivity extends Activity {
	private EditText et_chuanhaodamacode;
	private Button btn_chuanhaofactory, btn_chuanhaodate, btn_chuanhaosku;// btn_searchAreaAddress,
	private TextView tv_title,tvProduct,tvName;
	private Dialog waitingDialog;
	private Dialog overdialog;
	private String chuanhaofactory,chuanhaodate,chuanhaosku;
	private List<Product> productList=new ArrayList<Product>();
	private  ListView lv_product;
//    private ImageView img_map;
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.layout_chuanhao);
		super.onCreate(savedInstanceState);
		initView();
		initDate();
		
	}


	private void initView() {
		
		tv_title = (TextView) findViewById(R.id.chuanhaosearch);
		btn_chuanhaofactory = (Button) findViewById(R.id.chuanhaofactory);
		btn_chuanhaodate = (Button) findViewById(R.id.chuanhaodate);
		btn_chuanhaosku= (Button) findViewById(R.id.chuanhaosku);
		et_chuanhaodamacode= (EditText) findViewById(R.id.chuanhaodamacode);
		btn_chuanhaofactory.setOnClickListener(BtnClicked);
		btn_chuanhaodate.setOnClickListener(BtnClicked);
		btn_chuanhaosku.setOnClickListener(BtnClicked);
		findViewById(R.id.searchChuanhao).setOnClickListener(BtnClicked);
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);

	}

	
	private void initDate() {
		

	}

	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(ChuanhaoActivity.this);
				break;
			case R.id.chuanhaodate:
				inputdate();
				break;
			case R.id.chuanhaofactory:
				inputfactory();
				break;
			case R.id.chuanhaosku:
				productList=Product.findProductsXpp("1");
				showProductList("1");
				break;
			case R.id.searchChuanhao:
				if ("".equals(chuanhaofactory) || chuanhaofactory==null) {
					Toast.makeText(getApplicationContext(), "请输入生产工厂...",
							Toast.LENGTH_SHORT).show();
					break;
				}
				if ("".equals(chuanhaodate)|| chuanhaodate==null) {
					Toast.makeText(getApplicationContext(), "请输入生产日期...",
							Toast.LENGTH_SHORT).show();
					break;
				}
				if ("".equals(chuanhaosku)|| chuanhaosku==null) {
					Toast.makeText(getApplicationContext(), "请输入品项...",
							Toast.LENGTH_SHORT).show();
					break;
				}
				if ("".equals(et_chuanhaodamacode.getText().toString().trim())) {
					Toast.makeText(getApplicationContext(), "请输入打码号...",
							Toast.LENGTH_SHORT).show();
					break;
				}
				new searchCuanhuoTask().execute();
//				Toast.makeText(getApplicationContext(),"生产工厂:"+ chuanhaofactory+"生产日期:"+chuanhaodate+"品项:"+chuanhaosku+"打码号:"+et_chuanhaodamacode.getText().toString().trim(),
//						Toast.LENGTH_SHORT).show();
			}
		}

		

		

	};

	private void inputfactory() {
		View view = View.inflate(ChuanhaoActivity.this,
				R.layout.dialog_order_style, null);
		overdialog = new Dialog(ChuanhaoActivity.this,
				R.style.dialog_xw);
		overdialog.setContentView(view);
//		dialog.setTitle("本品");
		TextView tView  =(TextView) view.findViewById(R.id.tView);
		final EditText et_parameter  =(EditText) view.findViewById(R.id.parameter);
		 et_parameter.setVisibility(android.view.View.GONE );
		tView.setText("生产工厂");
		
		
		
		lv_product = (ListView) view.findViewById(R.id.lv_product1);
		
//		System.out.println("打印6666666666"+productList);
		final factoryAdapter fAdapter = new factoryAdapter(Dictionary.findbyitemName("factory"),getApplicationContext());
		lv_product.setAdapter(fAdapter);
		Button cancel = (Button) view.findViewById(R.id.dialog_cancel_btn);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				overdialog.cancel();
			}
		});
		Button ok = (Button) view.findViewById(R.id.dialog_ok_btn);
//		ok.setVisibility(android.view.View.GONE);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				overdialog.cancel();
			}
		});
		overdialog.show();
		
	}
	private void inputdate() {
		showDialog(1);
		
	}

	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	protected Dialog onCreateDialog(int id) {
		DatePickerDialog dialog = null;
		
		//DatePickerDialog dialog=null;
		switch (id) {
		case 1:
			Calendar c = Calendar.getInstance();
			final long today=TimeUtil.getDateTime(DataProviderFactory.getDayType());
			c.setTimeInMillis(today);
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							chuanhaodate=year+"-"+(month<9?"0":"")+(month+1)+"-"+(dayOfMonth<10?"0":"")+dayOfMonth;
							btn_chuanhaodate.setText(chuanhaodate);
//							
//							Calendar c1 = Calendar.getInstance();//获取当前时间
//							c1.setTimeInMillis(today);
//							TimeUtil.setTIME(c1.getTimeInMillis());
//						
//							c1.set(year, month, dayOfMonth);//选定的时间
//							long select=c1.getTimeInMillis();//选定的毫秒
//							TimeUtil.setTIME(c1.getTimeInMillis());
							
					
							
				
							
							
						}
					}, c.get(Calendar.YEAR), // 传入年份
					c.get(Calendar.MONTH), // 传入月份
				
					c.get(Calendar.DAY_OF_MONTH) // 传入天数
			);
			
//			
			dialog.setTitle( R.string.chuanhao_date);
			DatePicker dp=dialog.getDatePicker();
//			Calendar c1 = Calendar.getInstance();
			dp.setMaxDate(c.getTimeInMillis());
			//c.set(Calendar.DATE, c.get(Calendar.DATE)-3);
//			//TimeUtil.setTIME(c.getTimeInMillis());
//			//c1.add(Calendar.DAY_OF_MONTH, -3);
//			//Log.i("lhp", TimeUtil.getFormatTime());
//			
			// dp.setMinDate(c.getTimeInMillis());
//			 c1.add(Calendar.DAY_OF_MONTH, -3);
			
			  
	           
			
			
			break;
		}
		
		return dialog;
	}
	
	// 取消手机返回按钮功能
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(ChuanhaoActivity.this);
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
	public void showProductList(final String type){
		
		View view = View.inflate(ChuanhaoActivity.this,
					R.layout.dialog_order_style, null);
			overdialog = new Dialog(ChuanhaoActivity.this,
					R.style.dialog_xw);
			overdialog.setContentView(view);
//			dialog.setTitle("本品");
			TextView tView  =(TextView) view.findViewById(R.id.tView);
			final EditText et_parameter  =(EditText) view.findViewById(R.id.parameter);
			tView.setText("品项列表");
			
			
			
			lv_product = (ListView) view.findViewById(R.id.lv_product1);
//			System.out.println("打印6666666666"+productList);
			final productAdapter pAdapter = new productAdapter(getApplicationContext(),productList);
			lv_product.setAdapter(pAdapter);
			et_parameter.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					pAdapter.productList = Product.findOrderProductByName(et_parameter.getText().toString().toLowerCase(), type);
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
					overdialog.cancel();
				}
			});
			Button ok = (Button) view.findViewById(R.id.dialog_ok_btn);
//			ok.setVisibility(android.view.View.GONE);
			ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				
					overdialog.cancel();
				}
			});
			overdialog.show();
	}
	public class productAdapter extends BaseAdapter {
		private  List<Product> productList = new ArrayList<Product>();
		private LayoutInflater layoutInflater;
		public productAdapter (Context context,List<Product>  productList){
			this.productList = productList;
			this.layoutInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return productList.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return productList.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(ChuanhaoActivity.this).inflate(
						R.layout.child_order_style_list, null);
			} 
			tvProduct = (TextView) convertView.findViewById(R.id.tvProduct);
			tvProduct.setText(productList.get(position).getCategoryDesc());
			tvProduct.setTextColor(Color.BLACK);

		
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					btn_chuanhaosku.setText(productList.get(n).getCategoryDesc());
					chuanhaosku=productList.get(n).getCategoryId();
					overdialog.cancel();
				}

			});
			
			return convertView;
		}
			
			
		}
		
		

	public class factoryAdapter extends BaseAdapter{
		private List<Dictionary> factoryList=new ArrayList<Dictionary>();
		private LayoutInflater layoutInflater;
		
		public factoryAdapter(List<Dictionary> factoryList,
				Context context) {
			super();
			this.factoryList = factoryList;
			this.layoutInflater =LayoutInflater.from(context);
			System.out.println(factoryList);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return factoryList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return factoryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(ChuanhaoActivity.this).inflate(
						R.layout.child_order_style_list, null);
			} 
			tvProduct = (TextView) convertView.findViewById(R.id.tvProduct);
			tvProduct.setText(factoryList.get(position).getItemDesc());
			
			tvProduct.setTextColor(Color.BLACK);

		
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					btn_chuanhaofactory.setText(factoryList.get(n).getItemDesc());
					chuanhaofactory=factoryList.get(n).getItemValue();
					overdialog.cancel();
				}

			});
			
			return convertView;
		}
		
	}
		
	private class  searchCuanhuoTask extends AsyncTask<CuanhuoQuery, Integer, Boolean>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showWaitingDialog();
		}
		@Override
		protected Boolean doInBackground(CuanhuoQuery... params) {
			String	chuanhaodamacode=et_chuanhaodamacode.getText().toString();
			DataProviderFactory.getProvider().searchCuanhuo(chuanhaofactory, chuanhaodate, chuanhaosku, chuanhaodamacode);
			return true;
		}
	
		
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			
			dismissWaitingDialog();
			Intent i2=new Intent(ChuanhaoActivity.this,ChuanhaolistActivity.class);
			startActivity(i2);
			
//			System.out.println("onPostExecute+dismissWaitingDialog()");
//			List<List<Lstm>> childArray = new ArrayList<List<Lstm>>();
//			List<Lstm> groupArray = new ArrayList<Lstm>();
//			groupArray = Lstm.findGroupArray(customer.getCustId());
//			childArray=Lstm.findChildArray(customer.getCustId(),groupArray);
//			lstmlistAdapter = new LstmlistAdapter(childArray, groupArray,customer,LstmActivity.this);
//			elv_lstmList.setGroupIndicator(null);
//			elv_lstmList.setAdapter(lstmlistAdapter);
//			if(!result){
//				ToastUtil.show(OrderActivity.this, "网络不给力，获取产品信息失败");
//				
//			}
		}
	
	
	}
	
	
}	

