package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseOldOrderDetail;
import com.xpp.moblie.entity.BaseOrderDetail;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.OldOrder;
import com.xpp.moblie.query.OldOrderDetail;
import com.xpp.moblie.query.Order;
import com.xpp.moblie.query.OrderDetail;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.util.DataHandleUtil;
import com.xpp.moblie.util.TimeUtil;
import com.xpp.moblie.util.ToastUtil;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.screens.OrderTotalActivity.LearnGestureListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
public class OldOrderActivity extends Activity {
	private Button home_back,save;
	private TextView totalNumber, totalMoney, 
	tvtotalNumber, tvtotalMoney,totalPremiums,tvtotalPremiums,tv_orderId;
	private  ListView lv_product;//lv_orderList,
	private Customer shop;
	private TextView tvProduct, tvName;
	private  List<OldOrderDetail> list = new ArrayList<OldOrderDetail>();
	private  double number = 0, money = 0 ,premiumsQuntity= 0;
//	private orderAdapter adapter;
	private ImageView imgView,imgView1;
	private OldOrder order ;
	private OldOrder orderNew;
	private 	Button btn_unit;
	private String unitCode =null;
	private String  orderStatus ="N" , orderFundsStatus ="N";
	private RadioGroup rg_RadioGroup01,rg_RadioGroup02;
	private ExpandableListView lv_orderList;
	private OrderExpandableListAdapter orderExpandableListAdapter;
	private RadioButton rb_orderStatusY , rb_orderStatusN,rb_orderFundsY,rb_orderFundsN;
	private GestureDetector mGestureDetector;
	private String[] unitstr =null; 
	private String[] unitIdstr = null; 
	private Dialog waitingDialog;
	private List<Product> productList=new ArrayList<Product>();
	// 下拉框数据
	//private Spinner spinner1;
	//private ArrayAdapter adapter1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oldorder);
		initView();
		initData();
		mGestureDetector = new GestureDetector(this,new LearnGestureListener ());
		
	}
	private void initView() {
		home_back = (Button) findViewById(R.id.home_back);
		home_back.setOnClickListener(BtnClicked);
//		save = (Button) findViewById(R.id.save);
//		save.setOnClickListener(BtnClicked);
//		findViewById(R.id.add_product).setOnClickListener(BtnClicked);
//		findViewById(R.id.add_gift).setOnClickListener(BtnClicked);
		totalNumber = (TextView) findViewById(R.id.totalNumber);
		tvtotalNumber = (TextView) findViewById(R.id.tvtotalNumber);
		totalMoney = (TextView) findViewById(R.id.totalMoney);
		tvtotalMoney = (TextView) findViewById(R.id.tvtotalMoney);
		tvtotalPremiums= (TextView) findViewById(R.id.tvtotalPremiums);
		totalPremiums = (TextView) findViewById(R.id.totalPremiums);
		lv_orderList = (ExpandableListView) findViewById(R.id.order1);
		lv_product = (ListView) findViewById(R.id.lv_product1);
		tv_orderId = (TextView) findViewById(R.id.orderId);
		imgView = (ImageView) findViewById(R.id.imgView);
		imgView1 = (ImageView) findViewById(R.id.imgView1);
		rb_orderStatusY = (RadioButton) findViewById(R.id.orderStatusY);
		rb_orderStatusN = (RadioButton) findViewById(R.id.orderStatusN);
		rb_orderFundsY= (RadioButton) findViewById(R.id.orderFundsY);
		rb_orderFundsN= (RadioButton) findViewById(R.id.orderFundsN);
		rg_RadioGroup01 =  (RadioGroup) findViewById(R.id.RadioGroup01);
		rg_RadioGroup02 =  (RadioGroup) findViewById(R.id.RadioGroup02);
	}

	private void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
			order = (OldOrder) bun.get("oldorder");
		}
		
		if(order!=null){//修改
			/**查明细记录*/	
			list = OldOrderDetail.findByCustId(order.getCustId(),order.getUserId(),
				(order.getOrderId()==null?String.valueOf(order.getId()):order.getOrderId()));
			
			tvtotalNumber.setVisibility(View.VISIBLE);
			tvtotalMoney.setVisibility(View.VISIBLE);
			imgView.setVisibility(View.VISIBLE);
			imgView1.setVisibility(View.VISIBLE);
			tvtotalPremiums.setVisibility(View.VISIBLE);
			getTotal();
			if(order.getOrderId()!=null){
				tv_orderId.setText(Html.fromHtml( "<font color=#F0AB00>订单号:</font>"+
				"<font color=#000000>"+order.getOrderId()+"</font>"));
			}
			if ("Y".equals(order.getOrderStatus())) {
				rb_orderStatusY.setChecked(true);
				orderStatus = "Y";
			} else {
				rb_orderStatusN.setChecked(true);
			}
			
			if ("Y".equals(order.getOrderFundStatus())) {
				rb_orderFundsY.setChecked(true);
				orderFundsStatus = "Y";
			} else {
				rb_orderFundsN.setChecked(true);
			}
		}else{//创建
			rb_orderStatusN.setChecked(true);
			rb_orderFundsN.setChecked(true);
		}
		new getOrderListTask().execute(shop);
//		 adapter = new orderAdapter();
		orderExpandableListAdapter=new OrderExpandableListAdapter
				(OldOrderActivity.this,new ArrayList<List<OldOrderDetail>>(),new ArrayList<OldOrderDetail>());
		lv_orderList.setGroupIndicator(null);
		lv_orderList.setAdapter(orderExpandableListAdapter);
		notifyList();
		rg_RadioGroup01.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group,
					int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				if(radioButtonId == R.id.orderStatusY){
					orderStatus ="Y";
				}
				if(radioButtonId == R.id.orderStatusN){
					orderStatus ="N";
				}
			}
		}); 
		rg_RadioGroup02.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group,
					int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				if(radioButtonId == R.id.orderFundsY){
					orderFundsStatus ="Y";
				}
				if(radioButtonId == R.id.orderFundsN){
					orderFundsStatus ="N";
				}
			}
		}); 
		
		
		
		
		
	}

//private class getOrderListTask extends AsyncTask<Customer, Integer, Boolean>{
//
//		
//		
//		@Override
//		protected Boolean doInBackground(Customer... params) {
//			List<Product> products=Product.findByKunner(params[0].getKunnr());
//			if(products==null){
//				return DataProviderFactory.getProvider().getOrderSkuList(params[0].getKunnr());
//				
//			}
//			
//			return true;
//		}
//		
//		@Override
//		protected void onPostExecute(Boolean result) {
////			if(!result){
////				ToastUtil.show(OrderActivity.this, "网络不给力，获取产品信息失败");
////				
////			}
//		}
//		
//	
//	}
	
	private OnClickListener BtnClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				if(list==null||list.size()==0||order==null){
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "close");
					XPPApplication.sendChangeBroad(OldOrderActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
				}
				XPPApplication.exit(OldOrderActivity.this);
				break;
			case R.id.save:
				if(list==null||list.size()==0){
					ToastUtil.show(getApplicationContext(), "订单不能为空，请添加订单.....");
				}else{
				commit();
				}
				break;
				
			case R.id.add_product:
				ProductParameter parm=new ProductParameter("1","A",false,null,null);
				
				new FindProductsTask().execute(parm);
				//showProductList("1","A",false,null,null);
				break;
			case R.id.add_gift://赠品
				parm=new ProductParameter("1","B",false,null,null);
				
				new FindProductsTask().execute(parm);
				//showProductList("1","B",false,null,null);
				break;
			}
		}
	};

	
	private class ProductParameter{
		String type;
		String productType;
		boolean isPremiums;
		String groupSKUId;
		String groupUnitCode;
		public ProductParameter(String type, String productType,
				boolean isPremiums, String groupSKUId, String groupUnitCode) {
			super();
			this.type = type;
			this.productType = productType;
			this.isPremiums = isPremiums;
			this.groupSKUId = groupSKUId;
			this.groupUnitCode = groupUnitCode;
			
		}
		
		
	}
	
	/****************** 获得品项列表
	 * @param type 本品，赠品参数 （表字段）
	 * @param productType 本品赠品标记
	 * @param isPremiums 是否本品所关联的赠品 true ：关联本品的赠品 ；false 随订单赠品
	 * @param groupSKUId  本品skuId
	 * @param groupUnitCode  本品单位编码
	 * @param kunner 
	 * ********/
	public void showProductList(final String type, String productType,boolean isPremiums,String groupSKUId ,String groupUnitCode){
			
		View view = View.inflate(OldOrderActivity.this,
					R.layout.dialog_order_style, null);
			final Dialog dialog = new Dialog(OldOrderActivity.this,
					R.style.dialog_xw);
			dialog.setContentView(view);
//			dialog.setTitle("本品");
			TextView tView  =(TextView) view.findViewById(R.id.tView);
			final EditText et_parameter  =(EditText) view.findViewById(R.id.parameter);
			
			if("B".equals(productType)){
				tView.setText("赠品列表");
			}else{
				tView.setText("本品列表");
			}
			
			
			lv_product = (ListView) view.findViewById(R.id.lv_product1);
//			System.out.println("打印6666666666"+productList);
			final productAdapter pAdapter = new productAdapter(productList,productType,isPremiums,groupSKUId,groupUnitCode);
			lv_product.setAdapter(pAdapter);
			et_parameter.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					pAdapter.productList = Product.findOrderProductByName1(et_parameter.getText().toString().toLowerCase(), type,shop.getKunnr(),shop);
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
					if (list.size() != 0) {
						 number =0;money=0;
						tvtotalNumber.setVisibility(View.VISIBLE);
						tvtotalMoney.setVisibility(View.VISIBLE);
						tvtotalPremiums.setVisibility(View.VISIBLE);
						imgView.setVisibility(View.VISIBLE);
						imgView1.setVisibility(View.VISIBLE);
						notifyList();
						getTotal();
						saveDetailcache();
					}
					dialog.cancel();
				}
			});
			dialog.show();
	}

	
private class getOrderListTask extends AsyncTask<Customer, Integer, Boolean>{

		@Override
		protected void onPreExecute() {
			showWaitingDialog();
		}
		
		@Override
		protected Boolean doInBackground(Customer... params) {
			//System.out.println("打印111111   "+params[0]);
//			List<Product> products=Product.findByKunner(params[0].getKunnr());//查询本地是否已有、该经销商的SKU
			List<Product> products=null;
			if(products==null){
				DataProviderFactory.getProvider().getSkuLastPrice(null,params[0].getUserId(),params[0].getCustId());
				products=Product.findByKunner(params[0].getKunnr());
//				products=DataProviderFactory.getProvider().getSkuLastPrice(params[0].getKunnr(),params[0].getUserId(),params[0].getCustId());
				//products=DataProviderFactory.getProvider().getSkuByCloud(params[0].getKunnr(),params[0].getCustId(),params[0].getUserId());//若没有从服务器获取
				//System.out.println("打印2222222222222222  "+products);
				if(products!=null){
					productList=products;
					//System.out.println("打印2222222222222222  "+products);
				}
				//return 
			}
			
			System.out.println("打印88888"+productList);
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dismissWaitingDialog();
//			if(!result){
//				ToastUtil.show(OrderActivity.this, "网络不给力，获取产品信息失败");
//				
//			}
		}
		
	
	}
	
//private void showWaitingDialog() {
//	if (waitingDialog == null) {
//		DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
//			public void onShow(DialogInterface dialog) {
//				ImageView img = (ImageView) waitingDialog
//						.findViewById(R.id.loading);
//				((AnimationDrawable) img.getDrawable()).start();
//			}
//		};
////		DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
////			@Override
////			public void onCancel(DialogInterface dialog) {
////				// updateButtonLook(false);
////			}
////		};
//		waitingDialog = new Dialog(activity, R.style.TransparentDialog);
//		waitingDialog.setContentView(R.layout.login_waiting_dialog);
//		waitingDialog.setOnShowListener(showListener);
//		waitingDialog.setCanceledOnTouchOutside(false);
//		//waitingDialog.setOnCancelListener(cancelListener);
//		waitingDialog.show();
//	}
//}
//
//		private void dismissWaitingDialog() {
//	if (waitingDialog != null) {
//		ImageView img = (ImageView) waitingDialog
//				.findViewById(R.id.loading);
//		((AnimationDrawable) img.getDrawable()).stop();
//
//		waitingDialog.dismiss();
//		waitingDialog = null;
//	}
//}
	
	
	
	private class FindProductsTask extends AsyncTask<ProductParameter, Integer, ProductParameter>{

		@Override
		protected void onPreExecute() {
			showWaitingDialog();
		}
		
		@Override
		protected ProductParameter doInBackground(ProductParameter... params) {
			if(productList.size()==0){
			     //productList=Product.findByKunner(shop.getKunnr());
				productList=Product.findProductsByKunner1("1", null, shop.getKunnr(),shop);
				//List<Product> products=Product.findByKunner(params[0].getKunnr());
				System.out.println("打印7777777"+productList);
			}
			
			return params[0];
		}
		
		@Override
		protected void onPostExecute(ProductParameter result) {
			dismissWaitingDialog();
			//System.out.println("打印444444"+result);
			showProductList(result.type,result.productType,result.isPremiums,
					result.groupSKUId,result.groupUnitCode);
			
			
			
		}
		
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

	
	
	public void onDestroy() {
		super.onDestroy();
	}


	private final NumberKeyListener numberKeyListener = new NumberKeyListener() {
		public int getInputType() {
			// 0无键盘 1英文键盘 2模拟键盘 3数字键盘
			return 3;
		}

		@Override
		protected char[] getAcceptedChars() {
			char[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' ,'-'};// ,'.'
			return c;
		}
	};
	private SimpleAdapter adapter1;
	private List<Map<String, String>> unitList;

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
				
				adapter1 = new SimpleAdapter(OldOrderActivity.this, unitList,
						R.layout.child_simple_view, new String[] {
								"unitDesc", "unitCode" },
						new int[] { R.id.unitDesc, R.id.unitCode});
				
				new AlertDialog.Builder(OldOrderActivity.this)
				.setTitle("请选择")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(adapter1, 0,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog,
									int which) {
								btn_unit.setText(unitList.get(which).get("unitDesc"));
								unitCode=  unitList.get(which).get("unitCode");
								dialog.dismiss();
							}
						}).setNegativeButton("取消", null).show();
				break;
			}
		}
	};
	

//	}

	// 重写手机返回按钮功能
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			  if(list==null||list.size()==0||order==null){
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "close");
					XPPApplication.sendChangeBroad(OldOrderActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
				}
				XPPApplication.exit(OldOrderActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
/**sku列表*/
	public class productAdapter extends BaseAdapter {
		private  List<Product> productList = new ArrayList<Product>();
		private String productType;
		private boolean isPremiums;
		private String groupSKUId ;
		private String groupUnitCode;
		
		public productAdapter (List<Product>  productList,String productType,boolean isPremiums,
				String groupSKUId ,String groupUnitCode){
			this.productList = productList;
			this.productType=  productType;
			this.isPremiums = isPremiums;
			this. groupSKUId = groupSKUId;
			this.groupUnitCode = groupUnitCode;
			
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(OldOrderActivity.this).inflate(
						R.layout.child_order_style_list, null);
			} 
			tvProduct = (TextView) convertView.findViewById(R.id.tvProduct);
			tvProduct.setText(productList.get(position).getCategoryDesc());
			tvProduct.setTextColor(Color.BLACK);

			if(shop.getCustId().equals(productList.get(position).getStatus())){
				tvProduct.setTextColor(0xFF483D8B);
			}
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
					View overView = View.inflate(OldOrderActivity.this,
							R.layout.dialog_order_number, null);
					final Dialog overDialog = new Dialog(OldOrderActivity.this,
							R.style.dialog_xw);
					overDialog.setContentView(overView);
					final EditText quantity, price;
					quantity = (EditText) overView.findViewById(R.id.quantity);
					quantity.setKeyListener(numberKeyListener);
					price = (EditText) overView.findViewById(R.id.price);
					price.setSelectAllOnFocus(true);
					price.setKeyListener(numberKeyListener);
					price.setOnFocusChangeListener(new View.OnFocusChangeListener() {  
					       
					    @Override  
					    public void onFocusChange(View v, boolean hasFocus) {  
					        if(hasFocus){//获得焦点  
					        	EditText tt=(EditText)v;
					        	  //tt.setSelectAllOnFocus(true);
					              tt.selectAll();
					             // System.out.println("获得焦点");
					            //  System.out.println(v.getId());
					        }else{//失去焦点  
					             
					        }  
					    }             
					});
					//System.out.println("打印555555"+productList.get(n).getlastPrice());
					price.setText(productList.get(n).getlastPrice());
					if (shop.getCustId().equals(productList.get(n).getStatus())) {
						price.setText(productList.get(n).getlastPrice());
					}
					else {
						price.setText("0");
					}
					btn_unit = (Button) overView.findViewById(R.id.unit);
					btn_unit.setOnClickListener(unitListener);
					if(productList.get(n).getSkuUnit()!=null||productList.get(n).getSkuUnitId()!=null){
						unitstr=productList.get(n).getSkuUnit().split(",");
						unitIdstr=productList.get(n).getSkuUnitId().split(",");
						btn_unit.setText(unitstr[0]);
						unitCode = unitIdstr[0];
						}else{
							ToastUtil.show(getApplicationContext(), "请维护单位！");
						}
					  
					if("B".equals(productType)){
						overView.findViewById(R.id.llprice).setVisibility(View.GONE);
					}
					quantity.requestFocus();
					openKeyboard(quantity);
					tvName = (TextView) overView.findViewById(R.id.tvName);
					tvName.setText(productList.get(n).getCategoryDesc());
					Button overCancel = (Button) overView
							.findViewById(R.id.dialog_cancel_btn);
					overCancel.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							closeKeyboard(quantity);
							overDialog.cancel();
						}
					});
					Button overOk = (Button) overView
							.findViewById(R.id.dialog_ok_btn);
					overOk.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							try{
							if(quantity.getText()==null||"".equals(quantity.getText().toString())){
								ToastUtil.show(OldOrderActivity.this, "数量不能为空");
							}else if(!"B".equals(productType)&&(price.getText()==null||"".equals(price.getText().toString()))){
								ToastUtil.show(OldOrderActivity.this, "单价不能为空");
							}else if(Double.valueOf(quantity.getText().toString())==0){
								ToastUtil.show(OldOrderActivity.this, "数量不能为0");
							}
//							else if(!"B".equals(productType)&&(Double.valueOf(price.getText().toString())==0)){
//								ToastUtil.show(OrderActivity.this, "单价不能为0");
//							}
							else{
							OldOrderDetail orderDetail = new OldOrderDetail
											(Double.valueOf(quantity.getText().toString()), 
											unitCode,	
											btn_unit.getText().toString(), 
											"元",  
											("B".equals(productType)?0.0:Double.valueOf(price.getText().toString())), 
											("B".equals(productType)?0.0:Double.valueOf(price.getText().toString())*(Double.valueOf(quantity.getText().toString()))), 
											productType,
											productList.get(n).getCategoryId(),productList.get(n).getCategoryDesc(),
											shop.getCustId(),DataProviderFactory.getDayType(),
											DataProviderFactory.getUserId(),(isPremiums? (groupSKUId +"&"+groupUnitCode):""),productList.get(n).getlastPrice());
								boolean flag  = true;
								//添加品项去重复
								for ( BaseOldOrderDetail boDetail : list) {
									if(boDetail.getSkuId().equals(orderDetail.getSkuId())&&boDetail.getOrderType().equals(productType)
											&&boDetail.getUnitCode().equals(orderDetail.getUnitCode())&&
											boDetail.getMappingSKUId().equals(orderDetail.getMappingSKUId())){
										list.remove(boDetail);
										list.add(orderDetail);
										flag = false;
										break;
									}
								}
								if(flag){
									list.add(orderDetail);
								}
								closeKeyboard(quantity);
								overDialog.cancel();
							}
							} catch (Exception e) {
								ToastUtil.show(getApplicationContext(), "输入有误，请重新输入");
								e.printStackTrace();
							}
							}
							
						
					});
					overDialog.show();
				}

			});
			
			return convertView;
		}
	}
/***********订单adapter*****************************/
	public class OrderExpandableListAdapter extends BaseExpandableListAdapter {
		public List<List<OldOrderDetail>> childArray =new ArrayList<List<OldOrderDetail>>();
		public List<OldOrderDetail> groupArray = new ArrayList<OldOrderDetail>();
		private LayoutInflater childInflater;
		private LayoutInflater groupInflater;
		private Activity activity;
		public OrderExpandableListAdapter(Activity activity,List<List<OldOrderDetail>> childArray ,List<OldOrderDetail> groupArray) {
			this.childArray = childArray;
			this.groupArray = groupArray;
			this.childInflater = LayoutInflater.from(activity);
			this.groupInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.activity = activity;
		}
		
		public Object getChild(int groupPosition, int childPosition) {
			return childArray.get(groupPosition).get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHodler hodler = new ViewHodler();
			if (convertView == null) {
				convertView = childInflater.inflate(R.layout.child_order_list_p,
						null);
				hodler.tv_skuDesc = (TextView) convertView.findViewById(R.id.skuDesc);
				hodler.tv_pQuantity = (TextView) convertView.findViewById(R.id.Pquantity);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}
			hodler.tv_skuDesc.setText(childArray.get(groupPosition).get(childPosition).getCategoryDesc());
			hodler.tv_pQuantity.setText(DataHandleUtil.dealNull(childArray.get(groupPosition).get(childPosition).getQuantity())
					+childArray.get(groupPosition).get(childPosition).getUnitDesc());
		
			final int gn  = groupPosition;
			final int cn  = childPosition;
//			convertView.setOnClickListener(new OnClickListener() {//点击修改数量//TODO
//				EditText quantity1 = null;
//				EditText price1 = null;
//				TextView tvName1 = null;
//					public void onClick(View v) {
//						View overView1 = View.inflate(OldOrderActivity.this,
//								R.layout.dialog_order_number, null);
//						final Dialog overDialog = new Dialog(OldOrderActivity.this,
//								R.style.dialog_xw);
//						overDialog.setContentView(overView1);
//						quantity1 = (EditText) overView1.findViewById(R.id.quantity);
//						quantity1.setKeyListener(numberKeyListener);
//						price1 = (EditText) overView1.findViewById(R.id.price);
//						price1.setKeyListener(numberKeyListener);
//						quantity1.setText(childArray.get(gn).get(cn).getQuantity()==0?"":String.valueOf(childArray.get(gn).get(cn).getQuantity()));
//						if("B".equals(childArray.get(gn).get(cn).getOrderType())){
//							overView1.findViewById(R.id.llprice).setVisibility(View.GONE);
//						}
////						else{
////							price1.setText(childArray.get(gn).get(cn).getPrice()==0?"":String.valueOf(childArray.get(gn).get(cn).getPrice()));
////						}
//						
//						btn_unit = (Button) overView1.findViewById(R.id.unit);
//						btn_unit.setText(childArray.get(gn).get(cn).getUnitDesc());
//						unitCode=childArray.get(gn).get(cn).getUnitCode();
//						btn_unit.setOnClickListener(unitListener);
//						Product p =Product.findByCategoryId(childArray.get(gn).get(cn).getSkuId());
//						unitstr=p.getSkuUnit().split(",");
//						unitIdstr=p.getSkuUnitId().split(",");
//						
//						
//						
//						tvName1= (TextView) overView1.findViewById(R.id.tvName);
//						tvName1.setText(childArray.get(gn).get(cn).getCategoryDesc());
//						quantity1.requestFocus();
//						openKeyboard(quantity1);
//						
//						Button overCancel1 = (Button) overView1
//								.findViewById(R.id.dialog_cancel_btn);
//						overCancel1.setOnClickListener(new OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								closeKeyboard(quantity1);
//								overDialog.cancel();
//							}
//						});
//						Button overOk1 = (Button) overView1
//								.findViewById(R.id.dialog_ok_btn);
//						overOk1.setOnClickListener(new OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								try {
//								if(Double.valueOf(quantity1.getText().toString())==0){
//									ToastUtil.show(OldOrderActivity.this, "数量不能为0");
//								}else if(!"B".equals(childArray.get(gn).get(cn).getOrderType())&&(Double.valueOf(price1.getText().toString())==0)){
//									ToastUtil.show(OldOrderActivity.this, "单价不能为0");
//								}else{
//									for (int i = 0; i < list.size(); i++) {
//										if(childArray.get(gn).get(cn).equals(list.get(i))){
//											list.get(i).setQuantity(Double.valueOf(quantity1.getText().toString()));
//											if(!"B".equals(childArray.get(gn).get(cn).getOrderType())){
//											list.get(i).setPrice(Double.valueOf(price1.getText().toString()));
//											list.get(i).setTotalPrice(Double.valueOf(price1.getText().toString())*Double.valueOf(quantity1.getText().toString()));
//											}
//											list.get(i).setUnitCode(unitCode);
//											list.get(i).setUnitDesc(btn_unit.getText().toString());
//										}
//									}
//									getTotal();
//									closeKeyboard(quantity1);
//									overDialog.cancel();
//								}
//								} catch (NumberFormatException e) {
//									ToastUtil.show(getApplicationContext(), "输入有误，请重新输入");
//									e.printStackTrace();
//								}
//							}
//						});
//						overDialog.show();
//					
//				}
//			});
			
			
			/*** 长按事件 **/
//			convertView.setOnLongClickListener(new OnLongClickListener() {
//				public boolean onLongClick(View arg0) {
//					View overView = View.inflate(OldOrderActivity.this,
//							R.layout.dialog_confirmation, null);
//					final Dialog overDialog = new Dialog(OldOrderActivity.this,
//							R.style.dialog_xw);
//					overDialog.setContentView(overView);
//					Button overCancel2 = (Button) overView
//							.findViewById(R.id.dialog_cancel_btn);
//					overCancel2.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							overDialog.cancel();
//						}
//					});
//					Button overOk2 = (Button) overView
//							.findViewById(R.id.dialog_ok_btn);
//					overOk2.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							for (int i = 0; i < list.size(); i++) {
//								if(childArray.get(gn).get(cn).equals(list.get(i))){
//									list.remove(list.get(i)) ;//删除本品
//								 }
//								}
//							
//							notifyList();
//							 getTotal();
//							 saveDetailcache();
//							overDialog.cancel();
//						}
//					});
//					overDialog.show();
//					return false;
//				}
//			});
			
			
			return convertView;
		}

		public int getChildrenCount(int groupPosition) {
//			if(childArray.size()==0){
//				return 0;
//			}
			return childArray.get(groupPosition).size();
		}
		
		
		
		protected class ViewHodler {
			TextView tv_skuDesc = null;
			TextView tv_pQuantity = null;
		}

		protected void resetViewHolder(ViewHodler pViewHolder) {
			pViewHolder.tv_skuDesc.setText(null);
			pViewHolder.tv_pQuantity.setText(null);
		}
		
		
		
		/**group**/
		
		
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
			GroupViewHodler  ghodler= null ;
			if(convertView == null){
				ghodler = new GroupViewHodler();
				convertView = groupInflater
						.inflate(R.layout.child_order_style_listview, null);
				ghodler.lvProductName = (TextView) convertView.findViewById(R.id.lvProductName);
				ghodler.lvProductNumber = (TextView) convertView.findViewById(R.id.lvProductNumber);
				ghodler.lvProductPrice = (TextView) convertView.findViewById(R.id.lvProductPrice);
				ghodler.lvProductTotalprice = (TextView) convertView.findViewById(R.id.lvProductTotalprice);
				ghodler.tv_productType = (TextView) convertView.findViewById(R.id.productType);
				convertView.setTag(ghodler);
			}else{
				ghodler = (GroupViewHodler) convertView.getTag();
				resetgroupViewHolder(ghodler);
			}
			
			
			ghodler.lvProductName.setText(activity.getString(R.string.order_category)+groupArray.get(groupPosition).getCategoryDesc()); 
			ghodler.lvProductNumber.setText(groupArray.get(groupPosition).getQuantity()+ groupArray.get(groupPosition).getUnitDesc());
			ghodler.image = (ImageView) convertView.findViewById(R.id.tubiao);
//			TextView t1 = (TextView) convertView.findViewById(R.id.t1);
			TextView	zeng = (TextView) convertView.findViewById(R.id.zeng);
			zeng.setVisibility(View.GONE);
			if("B".equals(groupArray.get(groupPosition).getOrderType())){//赠品
//				zeng.setVisibility(View.GONE);
				ghodler.image.setVisibility(View.INVISIBLE);
				ghodler.tv_productType.setVisibility(View.VISIBLE);
			}else{
//				zeng.setVisibility(View.VISIBLE);
				ghodler.tv_productType.setVisibility(View.GONE);
				if(childArray.get(groupPosition).size()==0){
					ghodler.image.setVisibility(View.GONE);
				}else{
					ghodler.image.setVisibility(View.VISIBLE);
				}
				ghodler.lvProductPrice.setText(activity.getString(R.string.order_prices)+groupArray.get(groupPosition).getPrice() + "元");
				ghodler.lvProductTotalprice.setText(activity.getString(R.string.order_totalprice)+groupArray.get(groupPosition).getTotalPrice() + "元");
				// 箭头
				if (isExpanded) {
					ghodler.image.setImageResource(R.drawable.btn_browser2);
				} else {
					ghodler.image.setImageResource(R.drawable.btn_browser);
				}
			}	
			
			final int n  = groupPosition;
//			ghodler.lvProductName.setOnClickListener(new OnClickListener() {//点击修改数量
//				EditText quantity1 = null;
//				EditText price1 = null;
//				TextView tvName1 = null;
//					public void onClick(View v) {
//						View overView1 = View.inflate(OldOrderActivity.this,
//								R.layout.dialog_order_number, null);
//						final Dialog overDialog = new Dialog(OldOrderActivity.this,
//								R.style.dialog_xw);
//						overDialog.setContentView(overView1);
//						quantity1 = (EditText) overView1.findViewById(R.id.quantity);
//						quantity1.setKeyListener(numberKeyListener);
//						price1 = (EditText) overView1.findViewById(R.id.price);
//						price1.setKeyListener(numberKeyListener);
//						btn_unit = (Button) overView1.findViewById(R.id.unit);
//						btn_unit.setText(groupArray.get(n).getUnitDesc());
//						Product p =Product.findByCategoryId(groupArray.get(n).getSkuId());
//						unitstr=p.getSkuUnit().split(",");
//						unitIdstr=p.getSkuUnitId().split(",");
//					//	if("B".equals(groupArray.get(n).getOrderType())){
//					//		overView1.findViewById(R.id.llprice).setVisibility(View.GONE);
//					//	}
//						quantity1.setText(groupArray.get(n).getQuantity()==0?"":String.valueOf(groupArray.get(n).getQuantity()));
//						if("B".equals(groupArray.get(n).getOrderType())){
//							overView1.findViewById(R.id.llprice).setVisibility(View.GONE);
//						}else{
//							price1.setText(groupArray.get(n).getPrice()==0?"":String.valueOf(groupArray.get(n).getPrice()));
//						}
//						
//						
//						
//						
//						unitCode=groupArray.get(n).getUnitCode();
//						btn_unit.setOnClickListener(unitListener);
//						
//						tvName1= (TextView) overView1.findViewById(R.id.tvName);
//						tvName1.setText(groupArray.get(n).getCategoryDesc());
//						quantity1.requestFocus();
//						openKeyboard(quantity1);
//						
//						Button overCancel1 = (Button) overView1
//								.findViewById(R.id.dialog_cancel_btn);
//						overCancel1.setOnClickListener(new OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								closeKeyboard(quantity1);
//								overDialog.cancel();
//							}
//						});
//						Button overOk1 = (Button) overView1
//								.findViewById(R.id.dialog_ok_btn);
//						overOk1.setOnClickListener(new OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								try {
//								try{
//									if(Double.valueOf(quantity1.getText().toString())==0){
//										ToastUtil.show(OldOrderActivity.this, "数量不能为0");
//									}else if(!"B".equals(groupArray.get(n).getOrderType())&&(Double.valueOf(price1.getText().toString())==0)){
//										ToastUtil.show(OldOrderActivity.this, "单价不能为0");
//									}else{
//									for (int i = 0; i < list.size(); i++) {
//										if(groupArray.get(n).equals(list.get(i))){
//											list.get(i).setQuantity(Double.valueOf(quantity1.getText().toString()));
//											if(!"B".equals(groupArray.get(n).getOrderType())){
//											list.get(i).setPrice(Double.valueOf(price1.getText().toString()));
//											list.get(i).setTotalPrice(Double.valueOf(price1.getText().toString())*Double.valueOf(quantity1.getText().toString()));
//											}
//											list.get(i).setUnitCode(unitCode);
//											list.get(i).setUnitDesc(btn_unit.getText().toString());
//										}
//									}
//									getTotal();
//									closeKeyboard(quantity1);
//									overDialog.cancel();
//									}
//								} catch (Exception e) {
//									ToastUtil.show(getApplicationContext(), "输入有误，请重新输入");
//									e.printStackTrace();
//								}
//								} catch (NumberFormatException e) {
//									ToastUtil.show(getApplicationContext(), "输入有误，请重新输入");
//									e.printStackTrace();
//								}
//							}
//						});
//						overDialog.show();
//					
//				}
//			});
			
			
			/*** 长按事件 **/
//			ghodler.lvProductName.setOnLongClickListener(new OnLongClickListener() {
//				public boolean onLongClick(View arg0) {
//					View overView = View.inflate(OldOrderActivity.this,
//							R.layout.dialog_confirmation, null);
//					final Dialog overDialog = new Dialog(OldOrderActivity.this,
//							R.style.dialog_xw);
//					overDialog.setContentView(overView);
//					Button overCancel2 = (Button) overView
//							.findViewById(R.id.dialog_cancel_btn);
//					overCancel2.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							overDialog.cancel();
//						}
//					});
//					Button overOk2 = (Button) overView
//							.findViewById(R.id.dialog_ok_btn);
//					overOk2.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							for (int i = 0; i < list.size(); i++) {
//								if(groupArray.get(n).equals(list.get(i))){
//									list.remove(list.get(i)) ;//删除本品
//								 }
//								for (OldOrderDetail or : childArray.get(n)) {
//									if(or.equals(list.get(i))){
//										list.remove(list.get(i)) ;//删除本品相关赠品
//									}
//								} 
//								}
//							
//							notifyList();
//							 getTotal();
//							 saveDetailcache();
//							overDialog.cancel();
//						}
//					});
//					overDialog.show();
//					return false;
//				}
//			});
//			
//			zeng.setOnClickListener(new OnClickListener() {//点击添加赠品
//				public void onClick(View v) {
//					ProductParameter pp=new ProductParameter("1","B",true,groupArray.get(n).getSkuId(),groupArray.get(n).getUnitCode());
//					new FindProductsTask().execute(pp);
//					//showProductList("1","B",true,groupArray.get(n).getSkuId(),groupArray.get(n).getUnitCode());
//				}
//			});
			
			
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
			gViewHodler.lvProductName.setText(null);
			gViewHodler.lvProductNumber.setText(null);
			gViewHodler.lvProductPrice.setText(null);
			gViewHodler.lvProductTotalprice.setText(null);
			gViewHodler.tv_productType.setVisibility(View.GONE);
			gViewHodler.image.setVisibility(View.GONE);
		}
		private class GroupViewHodler {
			
			TextView lvProductName = null;
			TextView lvProductNumber = null;
			TextView lvProductPrice = null;
			TextView lvProductTotalprice = null;
			TextView tv_productType = null;
			ImageView image  = null;
		}
		
		
		
		
	}
	
	
	
	
//	class orderAdapter extends BaseAdapter {
//		EditText quantity1 = null;
//		EditText price1 = null;
//		TextView tvName1 = null;
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return list.get(position);
//		}
//		
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView( int position, View convertView, ViewGroup parent) {
//			ViewHodler hodler = null;
//			if(convertView == null){
//				hodler = new ViewHodler();
//				convertView = LayoutInflater.from(OrderActivity.this)
//						.inflate(R.layout.child_order_style_listview, null);
//				hodler.lvProductName = (TextView) convertView.findViewById(R.id.lvProductName);
//				hodler.lvProductNumber = (TextView) convertView.findViewById(R.id.lvProductNumber);
//				hodler.lvProductPrice = (TextView) convertView.findViewById(R.id.lvProductPrice);
//				hodler.lvProductTotalprice = (TextView) convertView.findViewById(R.id.lvProductTotalprice);
//				hodler.tv_productType = (TextView) convertView.findViewById(R.id.productType);
//				convertView.setTag(hodler);
//			}else{
//				hodler = (ViewHodler) convertView.getTag();
//				resetViewHolder(hodler);
//			}
//			hodler.lvProductName.setText(list.get(position).getCategoryDesc()); 
//			hodler.lvProductNumber.setText(list.get(position).getQuantity()+ "箱");
//			
//			if("B".equals(list.get(position).getOrderType())){
//				hodler.tv_productType.setVisibility(View.VISIBLE);
//			}else{
//				hodler.lvProductPrice.setText(getString(R.string.order_prices)+list.get(position).getPrice() + "元");
//				hodler.lvProductTotalprice.setText(getString(R.string.order_totalprice)+list.get(position).getTotalPrice() + "元");
//			}
//			/** 点击事件 **/
//			final int n = position;
//			convertView.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					View overView1 = View.inflate(OrderActivity.this,
//							R.layout.dialog_order_number, null);
//					final Dialog overDialog = new Dialog(OrderActivity.this,
//							R.style.dialog_xw);
//					overDialog.setContentView(overView1);
//					quantity1 = (EditText) overView1.findViewById(R.id.quantity);
//					quantity1.setKeyListener(numberKeyListener);
//					price1 = (EditText) overView1.findViewById(R.id.price);
//					price1.setKeyListener(numberKeyListener);
//					
//					btn_unit = (Button) overView1.findViewById(R.id.unit);
//					btn_unit.setText(list.get(n).getUnitDesc());
//					unitCode=list.get(n).getUnitCode();
//					 btn_unit.setOnClickListener(unitListener);
//					
//					tvName1= (TextView) overView1.findViewById(R.id.tvName);
//					tvName1.setText(list.get(n).getCategoryDesc());
//					quantity1.requestFocus();
//					openKeyboard(quantity1);
//					
//					Button overCancel1 = (Button) overView1
//							.findViewById(R.id.dialog_cancel_btn);
//					overCancel1.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							closeKeyboard(quantity1);
//							overDialog.cancel();
//						}
//					});
//					Button overOk1 = (Button) overView1
//							.findViewById(R.id.dialog_ok_btn);
//					overOk1.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							try {
//								list.get(n).setPrice(Double.valueOf(price1.getText().toString()));
//								list.get(n).setQuantity(Double.valueOf(quantity1.getText().toString()));
//								list.get(n).setTotalPrice(Double.valueOf(price1.getText().toString())*Double.valueOf(quantity1.getText().toString()));
//								list.get(n).setUnitCode(unitCode);
//								list.get(n).setUnitDesc(btn_unit.getText().toString());
//								getTotal();
//								closeKeyboard(quantity1);
//								overDialog.cancel();
//							} catch (NumberFormatException e) {
//								ToastUtil.show(getApplicationContext(), "输入有误，请重新输入");
//								e.printStackTrace();
//							}
//						}
//					});
//					overDialog.show();
//				}
//			});
//			/*** 长按事件 **/
//			convertView.setOnLongClickListener(new OnLongClickListener() {
//				public boolean onLongClick(View arg0) {
//					View overView = View.inflate(OrderActivity.this,
//							R.layout.dialog_confirmation, null);
//					final Dialog overDialog = new Dialog(OrderActivity.this,
//							R.style.dialog_xw);
//					overDialog.setContentView(overView);
//					Button overCancel2 = (Button) overView
//							.findViewById(R.id.dialog_cancel_btn);
//					overCancel2.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							overDialog.cancel();
//						}
//					});
//					Button overOk2 = (Button) overView
//							.findViewById(R.id.dialog_ok_btn);
//					overOk2.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							list.remove(list.get(n)) ;
//							adapter.notifyDataSetChanged();
//							 getTotal();
//							overDialog.cancel();
//						}
//					});
//					overDialog.show();
//					return false;
//				}
//			});
//			
//			return convertView;
//			
//		}
//	}
//	private void resetViewHolder(ViewHodler pViewHolder) {
//		pViewHolder.lvProductName.setText(null);
//		pViewHolder.lvProductNumber.setText(null);
//		pViewHolder.lvProductPrice.setText(null);
//		pViewHolder.lvProductTotalprice.setText(null);
//		pViewHolder.tv_productType.setVisibility(View.GONE);
//	}
//	private class ViewHodler {
//		
//		TextView lvProductName = null;
//		TextView lvProductNumber = null;
//		TextView lvProductPrice = null;
//		TextView lvProductTotalprice = null;
//		TextView tv_productType = null;
//	}
	
	
	private void commit() {
		View overdiaView1 = View.inflate(OldOrderActivity.this,
				R.layout.dialog_order_confirmation, null);
		final Dialog overdialog1 = new Dialog(OldOrderActivity.this,
				R.style.dialog_xw);
		overdialog1.setContentView(overdiaView1);
		overdialog1.setCanceledOnTouchOutside(false);
		Button overcancel1 = (Button) overdiaView1
				.findViewById(R.id.dialog_cancel_btn);
//		TextView TextView02 = (TextView) overdiaView1
//				.findViewById(R.id.TextView02);
//		TextView02.setText("确认提交订单？");
	final	EditText et_orderDesc =(EditText) overdiaView1.findViewById(R.id.orderDesc);
		if(order!=null){
			et_orderDesc.setText(order.getOrderDesc());
		}
		
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
				 String orderDesc = et_orderDesc.getText().toString().trim();
				if (savecache(orderDesc)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("type", "order");
					XPPApplication.sendChangeBroad(OldOrderActivity.this,
							XPPApplication.UPLOADDATA_RECEIVER, map);
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_success),
							Toast.LENGTH_SHORT).show();
					XPPApplication.exit(OldOrderActivity.this);
				} else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.save_fail), Toast.LENGTH_SHORT)
							.show();
				}
			}
			
		});
		overdialog1.show();
	}
	private boolean saveDetailcache() {
		
		try {
			/** 创建总单 **/
			if (order == null) {
				if (orderNew==null) {
					 orderNew = new OldOrder(shop.getCustId(),
							DataProviderFactory.getUserId(), money, "", "元",
							DataProviderFactory.getOrgId(), TimeUtil.getTIME(),
							XPPApplication.Status.CACHE, number,
							DataProviderFactory.getDayType(), premiumsQuntity,
							shop.getKunnr(), orderStatus,
							orderFundsStatus, "");
					orderNew.save();}
//				System.out.println("pring--------"+orderNew.getId());
				OrderDetail.delete(OrderDetail.findByCustId(
						orderNew.getCustId(),
						orderNew.getUserId(),
						(orderNew.getOrderId() == null ? String.valueOf(orderNew
								.getId()) : orderNew.getOrderId())));
					for (OldOrderDetail orderDetail : list) {
						orderDetail.setOrderId(String.valueOf(orderNew.getId()));
						orderDetail.save();}
				
				
				
			} else {
				order.setOrderCreateDate(TimeUtil.getTIME());
				order.setStatus(Status.CACHE);
				order.setOrderQuntity(number);
				order.setTotalPrice(money);
				order.setTotalPremiumsQuntity(premiumsQuntity);
				order.setOrderStatus(orderStatus);
				order.setOrderFundStatus(orderFundsStatus);
				order.setOrderDesc("");
				order.update();
				OrderDetail.delete(OrderDetail.findByCustId(
						order.getCustId(),
						order.getUserId(),
						(order.getOrderId() == null ? String.valueOf(order
								.getId()) : order.getOrderId())));
				/** 创建明细 **/
				for (OldOrderDetail orderDetail : list) {
					orderDetail.setOrderId((order.getOrderId() == null ? String
							.valueOf(order.getId()) : order.getOrderId()));
					orderDetail.save();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	private boolean savecache(String orderDesc) {
		
		try {
			/**创建总单**/
			if(order==null){
//				Order	 orderNew = new Order(shop.getCustId(), 
//					DataProviderFactory.getUserId(), money, "", "元", 
//					DataProviderFactory.getOrgId(), TimeUtil.getTIME(), XPPApplication.Status.UNSYNCHRONOUS,
//					number,DataProviderFactory.getDayType(),premiumsQuntity,shop.getKunnr(),
//					orderStatus,orderFundsStatus,orderDesc);
//			 orderNew.save();
//			 for (OrderDetail orderDetail : list) {
//					orderDetail.setOrderId(String.valueOf(orderNew.getId()));
//					orderDetail.save();
//				}
			 orderNew.setOrderDesc(orderDesc);
			 orderNew.setStatus(Status.UNSYNCHRONOUS);
			 orderNew.update();
			}else{
				order.setOrderCreateDate(TimeUtil.getTIME());
				order.setStatus(Status.UNSYNCHRONOUS);
				order.setOrderQuntity(number);
				order.setTotalPrice(money);
				order.setTotalPremiumsQuntity(premiumsQuntity);
				order.setOrderStatus(orderStatus);
				order.setOrderFundStatus(orderFundsStatus);
				order.setOrderDesc(orderDesc);
				order.update();
				OrderDetail.delete(OrderDetail.findByCustId(order.getCustId(),order.getUserId(),
						(order.getOrderId()==null?String.valueOf(order.getId()):order.getOrderId())));
				/**创建明细**/ 
				for (OldOrderDetail orderDetail : list) {
					orderDetail.setOrderId((order.getOrderId()==null?String.valueOf(order.getId()):order.getOrderId()));
					orderDetail.save();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	
	/**打开键盘*/
	private  void openKeyboard(final View et){
		Timer timer = new Timer(); // 设置定时器
		timer.schedule(new TimerTask() {
			@Override
			public void run() { // 弹出软键盘的代码
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et,
						InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		}, 300);
	}
	/**关闭键盘*/
	private  void closeKeyboard(final View et){
		// 关闭键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(),
				0);
	}
	
	
	private void getTotal(){
		number =0;money=0;premiumsQuntity = 0;
		Map<String,Double> pUnitMap =  new HashMap<String, Double>();
		Map<String,Double> unitMap =  new HashMap<String, Double>();

		for(BaseOldOrderDetail order : list){
//			if("B".equals(order.getOrderType())){
//				premiumsQuntity +=order.getQuantity();
//			}else{
//				number += order.getQuantity();
//				money += order.getTotalPrice();
//			}
			
			if("B".equals(order.getOrderType())){
				if(	pUnitMap.get(order.getUnitDesc())!=null){
					pUnitMap.put(order.getUnitDesc(), pUnitMap.get(order.getUnitDesc())+order.getQuantity()) ;
				}else{
					pUnitMap.put(order.getUnitDesc(), order.getQuantity());
				}
			}else{
			if(order.getQuantity()!=0){
				if(	unitMap.get(order.getUnitDesc())!=null){
					unitMap.put(order.getUnitDesc(), unitMap.get(order.getUnitDesc())+order.getQuantity()) ;
				}else{
					unitMap.put(order.getUnitDesc(), order.getQuantity());
				}
			 }
			}
			money += order.getTotalPrice();
		}
		
		
		
		String str="";
		for (Entry<String, Double> entry : pUnitMap.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			str =value+key+" "+str; 
		}
		totalPremiums.setText(str);
		str= "";
		for (Entry<String, Double> entry : unitMap.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			str =value+key+" "+str; 
		}
		totalNumber.setText(str);
		
		totalMoney.setText(money + "元");
//		totalPremiums.setText(premiumsQuntity+ "箱");
	}
	
	
	
	
	private void notifyList(){
		List<List<OldOrderDetail>> childArray = new ArrayList<List<OldOrderDetail>>();
		List<OldOrderDetail> groupArray = new ArrayList<OldOrderDetail>();
		List<OldOrderDetail> child = new ArrayList<OldOrderDetail>();
		for (OldOrderDetail orderDetail : list) {
			if("A".equals(orderDetail.getOrderType())||("B".equals(orderDetail.getOrderType())&&"".equals(orderDetail.getMappingSKUId()))){
				groupArray.add(orderDetail);
			}
		}
		
		for (OldOrderDetail od : groupArray) {
			child = new ArrayList<OldOrderDetail>();
			if(!"B".equals(od.getOrderType())){
			for (OldOrderDetail orderDetail : list) {
				if(orderDetail.getMappingSKUId().equals(od.getSkuId()+"&"+od.getUnitCode())&&!orderDetail.equals(od)){
					child.add(orderDetail);
				}
			}
			}
				childArray.add(child);
		}
		
		orderExpandableListAdapter.childArray= childArray;
		orderExpandableListAdapter.groupArray= groupArray;
		orderExpandableListAdapter.notifyDataSetChanged();
	}
	
	   class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{ 
	  	     public boolean onDown(MotionEvent ev) { 
	  	       Log.d("onDownd",ev.toString()); 
	  	         return true; 
	  	    } 
	  	      public boolean onFling (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//	  	    	 if (e2.getX() - e1.getX() > 100) { 
//	  	    		if(list==null||list.size()==0||order==null){
//						Map<String, String> map = new HashMap<String, String>();
//						map.put("type", "close");
//						XPPApplication.sendChangeBroad(OrderActivity.this,
//								XPPApplication.UPLOADDATA_RECEIVER, map);
//					}
//	 				XPPApplication.exit(OrderActivity.this);
//	 				return true;
//	 		 }
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