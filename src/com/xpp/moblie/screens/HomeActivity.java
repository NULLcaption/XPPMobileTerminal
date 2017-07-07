package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.xpp.moblie.adapter.shopvisist.ShopVisitListAdapter;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.UpdateTask;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.Menu;
import com.xpp.moblie.query.UserInfo;
import com.xpp.moblie.screens.R.string;
import com.xpp.moblie.service.UploadDataService;
import com.xpp.moblie.util.CleanMessageUtil;
import com.xpp.moblie.util.SlideHolder;
import com.xpp.moblie.util.VersionUpdate;

/**
 * Title: ��ҳ�˵����� Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016��11��7��
 */
public class HomeActivity extends Activity {
	private SlideHolder mSlideHolder;
	private ListView lv_menu;
	private ListView lv_settingList;
	private TextView tv_title;
	private Dialog waitingDialog;
	private Button btn_shopSearch, btn_addCustomer;
	private AutoCompleteTextView tv_parameter;
	private Dialog overdialog;
	private ShopVisitListAdapter shopVisitListAdapter;
	private MenuListAdapter menuListAdapter;
	private TextView userName, tv_dayType;
	private SearchShopTask searchShopTask;
	private Handler handler = null;
	private ImageView img_maps;

	/**
	 * ����xml��Android��UI�Լ���̨���ص�����
	 */
	@SuppressLint("HandlerLeak")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);

		// ���˵�
		View toggleView = findViewById(R.id.title);
		toggleView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSlideHolder.toggle();
			}
		});

		//AndroidӦ�ó���ע��㲥������
		registerReceiver(refreshShopListListener, new IntentFilter(
				XPPApplication.REFRESH_SHOP_RECEIVER));
		
		initView();
		initData();
		
		handler = new Handler() {
			@SuppressLint("HandlerLeak")
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 0) {
					dismissWaitingDialog();
					Intent uploadDataService = new Intent(HomeActivity.this,
							UploadDataService.class);
					stopService(uploadDataService);
					XPPApplication.exit(HomeActivity.this);
					ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
					am.killBackgroundProcesses(getPackageName());

				}
			}
		};
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(refreshShopListListener);
	}

	private void initView() {
		lv_menu = (ListView) findViewById(R.id.menu_set);
		img_maps = (ImageView) findViewById(R.id.map_icon_img);
		img_maps.setOnClickListener(BtnClicked);
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		lv_settingList = (ListView) findViewById(R.id.settingList);
		tv_title = (TextView) findViewById(R.id.title);
		tv_parameter = (AutoCompleteTextView) findViewById(R.id.parameter);
		btn_shopSearch = (Button) findViewById(R.id.shopSearch);
		btn_shopSearch.setOnClickListener(BtnClicked);
		btn_addCustomer = (Button) findViewById(R.id.addCustomer);
		btn_addCustomer.setOnClickListener(BtnClicked);
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		userName = (TextView) findViewById(R.id.userName);
		tv_dayType = (TextView) findViewById(R.id.dayType);
	}

	private void initData() {
		String uname = "�û�";
		try {

			String loginname = DataProviderFactory.getLoginName();
			System.out.println("�û�:" + loginname);
			if (loginname != null) {
				uname = UserInfo.findByLoginName(loginname).getUserName();
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("DataProviderFactory.getLoginName() is null");
		}
		userName.setText(uname + getString(R.string.home_hi));
		List<Menu> MList = Menu.findAll();
		if (MList != null) {
			menuListAdapter = new MenuListAdapter(MList);
			lv_menu.setAdapter(menuListAdapter);
		}

		mSlideHolder.toggle();
		new shopVisistListTask().execute();

	}
	
	/**
	 * �㲥������BroadcastReceiverע�ᵽActivityManagerService��ȥ
	 */
	private BroadcastReceiver refreshShopListListener = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			String key = b.getString("refresh");
			String version = b.getString("version");
			String menu = b.getString("menu");
			if ("visist".equals(key)) {
				shopVisitListAdapter.parameterList = Customer.findIsVisitShop();
				shopVisitListAdapter.notifyDataSetChanged();
			} else if ("customer".equals(key)) {
				shopVisitListAdapter.parameterList = Customer.findAllCustomer();
				shopVisitListAdapter.notifyDataSetChanged();
			} else if ("isRefresh".equals(key)) {
				shopVisitListAdapter.parameterList = Customer.findIsVisitShop();
				shopVisitListAdapter.notifyDataSetChanged();
			} else if ("customerStock".equals(key)) {
				shopVisitListAdapter.parameterList = Customer.findCustomerStock();
				shopVisitListAdapter.notifyDataSetChanged();
			}
			if (version != null) {// ��֤�汾
				new VersionUpdate(HomeActivity.this, version);
			}
			if (menu != null) {
				List<Menu> MList = Menu.findAll();
				menuListAdapter = new MenuListAdapter(MList);
				lv_menu.setAdapter(menuListAdapter);
			}

		}
	};

	/**
	 * Title: ��ȡ�˵��б� Description: XPPMobileTerminal
	 * 
	 * @author: xg.chen
	 * @date:2016��11��10�� ����9:43:56
	 */
	public class MenuListAdapter extends BaseAdapter {
		public List<Menu> menuList = null;
		private LayoutInflater layoutInflater;

		public MenuListAdapter(List<Menu> menuList) {
			this.menuList = menuList;
			this.layoutInflater = LayoutInflater.from(HomeActivity.this);
		}

		public int getCount() {
			return menuList.size();
		}

		public Object getItem(int position) {
			return menuList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;

			if (convertView == null) {
				// ��ȡ�������
				hodler = new ViewHodler();
				convertView = layoutInflater.inflate(R.layout.child_menu_item,
						null);
				hodler.tv_item = (TextView) convertView
						.findViewById(R.id.tv_item);
				hodler.img = (ImageView) convertView.findViewById(R.id.img);
				hodler.ly = (LinearLayout) convertView.findViewById(R.id.lybtn);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}
			hodler.tv_item.setText(menuList.get(position).getMenuDesc());
			if (menuList.get(position).getMenuValue() == DataProviderFactory
					.getMenuId()) {
				hodler.tv_item.setTextColor(getResources().getColor(
						R.color.dialog_color));
				hodler.ly.setBackgroundResource(R.drawable.menu_list_on);
			} else {
				hodler.tv_item.setTextColor(getResources().getColor(
						R.color.white));
			}
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					switch (menuList.get(n).getMenuValue()) {
					case 0:// �ݷù���
						if (DataProviderFactory.getMenuId() != 0) {
							DataProviderFactory.setMenuId(0);
							menuListAdapter.notifyDataSetChanged();
							tv_title.setText(R.string.shop_visit);
							btn_addCustomer.setVisibility(View.GONE);
							tv_parameter.setVisibility(View.VISIBLE);
							tv_parameter.setText("");
							tv_parameter.setHint(R.string.home_search_shop);
							btn_shopSearch.setVisibility(View.VISIBLE);
							img_maps.setVisibility(View.VISIBLE);
							// �ݷù���֮��ѯ�ݷÿͻ�
							shopVisitListAdapter.parameterList = Customer
									.findIsVisitShop();
							shopVisitListAdapter.notifyDataSetChanged();
							mSlideHolder.toggle();
						}
						break;
					case 1:// �ͻ�����
						if (DataProviderFactory.getMenuId() != 1) {
							DataProviderFactory.setMenuId(1);
							menuListAdapter.notifyDataSetChanged();
							tv_title.setText(R.string.home_customer_management);
							tv_parameter.setVisibility(View.GONE);
							btn_shopSearch.setVisibility(View.GONE);
							btn_addCustomer.setVisibility(View.VISIBLE);
							shopVisitListAdapter.parameterList = Customer
									.findAllCustomer();
							shopVisitListAdapter.notifyDataSetChanged();
							img_maps.setVisibility(View.GONE);
							mSlideHolder.toggle();
						}
						break;
					case 2:// �˳�
						View overdiaView1 = View.inflate(HomeActivity.this,
								R.layout.dialog_confirmation, null);
						final Dialog overdialog1 = new Dialog(
								HomeActivity.this, R.style.dialog_xw);
						overdialog1.setContentView(overdiaView1);
						overdialog1.setCanceledOnTouchOutside(false);
						Button overcancel1 = (Button) overdiaView1
								.findViewById(R.id.dialog_cancel_btn);
						TextView TextView02 = (TextView) overdiaView1
								.findViewById(R.id.TextView02);
						TextView02.setText("ȷ���˳���");
						overcancel1.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								overdialog1.cancel();
							}
						});
						Button overok1 = (Button) overdiaView1
								.findViewById(R.id.dialog_ok_btn);
						overok1.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								overdialog1.cancel();
								showWaitingDialog();
								new Thread(new Runnable() {
									public void run() {
										Message m = new Message();
										m.what = 0;
										DataProviderFactory.getProvider()
												.uploadLoginLog("offline");
										// ��ӡ��Ӧ������
										// XPPBluetoothPrinter.getInstance().exitBluePrint();
										handler.sendMessage(m);
									}
								}).start();
								CleanMessageUtil
										.clearAllData(getApplicationContext());// �˳�ʱ�����Ӧ�õ����е�����
							}
						});
						overdialog1.show();
						break;
					case 3:// ����
						Intent i = new Intent(HomeActivity.this,
								SettingActivity.class);
						startActivity(i);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						break;
					case 4:// �����̹��� ��menu����������ᱨ��
						if (DataProviderFactory.getMenuId() != 4) {
							DataProviderFactory.setMenuId(4);
							menuListAdapter.notifyDataSetChanged();
							tv_parameter.setText("");
							tv_title.setText(R.string.home_kunnr_management);

							if (XPPApplication.MOBILE_DD
									.equals(DataProviderFactory.getRoleId())) {
								tv_parameter.setVisibility(View.VISIBLE);
								tv_parameter
										.setHint(R.string.home_search_customer);
								btn_shopSearch.setVisibility(View.VISIBLE);
								img_maps.setVisibility(View.GONE);

							} else {
								tv_parameter.setVisibility(View.GONE);
								btn_shopSearch.setVisibility(View.GONE);
								img_maps.setVisibility(View.GONE);
							}
							btn_addCustomer.setVisibility(View.GONE);
							shopVisitListAdapter.parameterList = Customer
									.findKunnrCustomer();
							shopVisitListAdapter.notifyDataSetChanged();
							mSlideHolder.toggle();
						}
						break;
					case 5:// KPI����
						Intent i2 = new Intent(HomeActivity.this,
								KPIReportFormActivity.class);
						startActivity(i2);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						break;
					case 6:// ��������ѯ
						Intent i3 = new Intent(HomeActivity.this,
								ChuanhaoActivity.class);
						startActivity(i3);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						break;
					case 7:// ����
						Intent i7 = new Intent(HomeActivity.this,
								SignActivity.class);
						startActivity(i7);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						break;
					case 8:// �ŵ�������ᱨ
						if (DataProviderFactory.getMenuId() != 8) {
							DataProviderFactory.setMenuId(8);
							menuListAdapter.notifyDataSetChanged();
							tv_title.setText(R.string.home_CustStock_management);
							tv_parameter.setText("");
							tv_parameter.setVisibility(View.VISIBLE);
							tv_parameter.setHint(R.string.home_search_shop);
							btn_shopSearch.setVisibility(View.VISIBLE);
							img_maps.setVisibility(View.GONE);

							btn_addCustomer.setVisibility(View.GONE);
							shopVisitListAdapter.parameterList = Customer
									.findCustomerStock();
							shopVisitListAdapter.notifyDataSetChanged();
							mSlideHolder.toggle();
						}

						break;
					default:
						break;

					}

				}
			});

			return convertView;
		}

		protected class ViewHodler {
			TextView tv_item = null;
			ImageView img = null;
			LinearLayout ly = null;
		}

		protected void resetViewHolder(ViewHodler pViewHolder) {
			pViewHolder.tv_item.setText(null);
			pViewHolder.tv_item.setTextColor(getResources().getColor(
					R.color.white));
			pViewHolder.img.setImageBitmap(null);
			pViewHolder.ly.setBackgroundResource(R.drawable.bg_menu);
		}

		public void exit() {
			Intent uploadDataService = new Intent(HomeActivity.this,
					UploadDataService.class);
			stopService(uploadDataService);
			XPPApplication.exit(HomeActivity.this);
			ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			am.killBackgroundProcesses(getPackageName());
		}
	}

	/** �ͻ��б� **/

	private class shopVisistListTask extends
			AsyncTask<Object, Integer, List<Customer>> {

		protected void onPreExecute() {
			shopVisitListAdapter = new ShopVisitListAdapter(
					new ArrayList<Customer>(), HomeActivity.this);
			lv_settingList.setAdapter(shopVisitListAdapter);
			showWaitingDialog();
		}

		protected List<Customer> doInBackground(Object... arg0) {
			switch (DataProviderFactory.getMenuId()) {
			case 0:
				return Customer.findIsVisitShop();
			case 1:
				return Customer.findAllCustomer();
			case 4:
				return Customer.findKunnrCustomer();
			}
			return new ArrayList<Customer>();

		}

		protected void onPostExecute(List<Customer> result) {
			shopVisitListAdapter.parameterList = result;
			shopVisitListAdapter.notifyDataSetChanged();
			tv_dayType.setText(DataProviderFactory.getDayType());
			dismissWaitingDialog();

		}

	}

	/**
	 * Title: �ݷù���֮��ѯ�ͻ�����ӵ��ݷ��б� Description: XPPMobileTerminal
	 * 
	 * @author: xg.chen
	 * @date:2016��11��7��
	 */
	private class SearchShopTask extends
			AsyncTask<String, Integer, List<Customer>> {
		private TextView sh_title;

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (isCancelled())
				return;

		}

		protected void onPreExecute() {
			showWaitingDialog();

		}

		protected List<Customer> doInBackground(String... arg0) {
			if (isCancelled())
				return null;
			String str = arg0[0];
			if (UpdateTask.getInstance().getStatus() == AsyncTask.Status.RUNNING) {
				UpdateTask.getInstance().waitTimeout();
			}
			if (DataProviderFactory.getMenuId() == 0
					|| DataProviderFactory.getMenuId() == 8) {
				return DataProviderFactory.getProvider().getCustomer(str);

			} else {
				return DataProviderFactory.getProvider().searchKunnr(str);

			}
		}

		protected void onPostExecute(List<Customer> result) {
			dismissWaitingDialog();
			if (DataProviderFactory.getMenuId() == 4) {
				if (result != null && result.size() != 0) {
					overdialog = null;
					View overdiaView = View.inflate(HomeActivity.this,
							R.layout.dialog_search_shop_msg, null);
					overdialog = new Dialog(HomeActivity.this,
							R.style.dialog_xw);
					sh_title = (TextView) overdiaView.findViewById(R.id.Title);
					sh_title.setText(string.home_getCustomerList);
					ListView clist = (ListView) overdiaView
							.findViewById(R.id.clist);
					SettingAdapter settingAdapter = new SettingAdapter(
							getApplicationContext(), result);
					clist.setAdapter(settingAdapter);
					overdialog.setContentView(overdiaView);
					overdialog.setCanceledOnTouchOutside(false);
					Button overcancel = (Button) overdiaView
							.findViewById(R.id.dialog_cancel_btn);
					overcancel.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							overdialog.cancel();
						}
					});
					overdialog.show();
					return;
				} else {
					Toast.makeText(getApplicationContext(), "��ѯ�޴˾����̼�¼��",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			if (result != null && result.size() != 0) {
				if (result.get(0).getCustId()
						.equals(XPPApplication.UPLOAD_FAIL_CONNECT_SERVER)) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.login_fail_connect),
							Toast.LENGTH_SHORT).show();
				} else {
					overdialog = null;
					View overdiaView = View.inflate(HomeActivity.this,
							R.layout.dialog_search_shop_msg, null);
					overdialog = new Dialog(HomeActivity.this,
							R.style.dialog_xw);
					ListView clist = (ListView) overdiaView
							.findViewById(R.id.clist);
					SettingAdapter settingAdapter = new SettingAdapter(
							getApplicationContext(), result);
					clist.setAdapter(settingAdapter);
					overdialog.setContentView(overdiaView);
					overdialog.setCanceledOnTouchOutside(false);
					Button overcancel = (Button) overdiaView
							.findViewById(R.id.dialog_cancel_btn);
					overcancel.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							overdialog.cancel();
						}
					});
					overdialog.show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "��ѯ�޴��ŵ��¼��",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	protected class ViewHodler {
		TextView custName = null;
		TextView address = null;
		TextView kunnr = null;
	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.custName.setText(null);
		pViewHolder.address.setText(null);
		pViewHolder.kunnr.setText(null);

	}

	// ����ŵ���Ϣ
	public class SettingAdapter extends BaseAdapter {
		private List<Customer> data = new ArrayList<Customer>();//
		private LayoutInflater layoutInflater;

		public SettingAdapter(Context context, List<Customer> data) {
			this.data = data;
			this.layoutInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return data.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;
			if (convertView == null) {
				// ��ȡ�������
				hodler = new ViewHodler();
				convertView = layoutInflater.inflate(
						R.layout.dialog_search_shop_list_child, null);
				hodler.custName = (TextView) convertView
						.findViewById(R.id.custName);
				hodler.address = (TextView) convertView
						.findViewById(R.id.address);
				hodler.kunnr = (TextView) convertView.findViewById(R.id.kunnr);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}

			if (DataProviderFactory.getMenuId() == 0
					|| DataProviderFactory.getMenuId() == 8) {
				hodler.custName.setText("����:"
						+ data.get(position).getCustName());
				hodler.kunnr.setText("����������:"
						+ data.get(position).getKunnrName());
				hodler.address
						.setText("��ַ:" + data.get(position).getAddress() == null ? "�޵�ַ"
								: data.get(position).getAddress());
			} else {
				hodler.custName.setText("�����̱��:"
						+ data.get(position).getCustId());
				hodler.address.setText("����������:"
						+ data.get(position).getCustName() == null ? "�޵�ַ"
						: data.get(position).getCustName());
			}

			// �����ݡ��Լ��¼�����
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					Customer data1 = Customer.findByCustId(data.get(n)
							.getCustId());
					if (data1 != null) {
						data.get(n).setIsVisitShop(data1.getIsVisitShop());
						data.get(n).setCustomerDataType(
								data1.getCustomerDataType());
					}
					if (DataProviderFactory.getMenuId() == 0) {
						data.get(n).setUserId(DataProviderFactory.getUserId());
						data.get(n).setIsVisitShop("1");
						data.get(n).save();
						new searchMarketActivityTask().execute(data.get(n)
								.getCustId());
					} else if (DataProviderFactory.getMenuId() == 8) {
						data.get(n).setUserId(DataProviderFactory.getUserId());
						data.get(n).setCustomerDataType("customerStock");
						data.get(n).save();
						shopVisitListAdapter.parameterList = Customer
								.findCustomerStock();
						shopVisitListAdapter.notifyDataSetChanged();
					} else {
						data.get(n).setUserId(DataProviderFactory.getUserId());
						data.get(n).save();
						List<Customer> cuList = new ArrayList<Customer>();
						cuList.add(data.get(n));
						shopVisitListAdapter.parameterList = cuList;
						shopVisitListAdapter.notifyDataSetChanged();
					}
					overdialog.cancel();
					// shopVisitListAdapter.parameterList =
					// Customer.findIsVisit();
					// shopVisitListAdapter.notifyDataSetChanged();
					// overdialog.cancel();
					// new shopVisistListTask().execute();
				}
			});
			return convertView;
		}
	}

	/***** ��ѯ�г�� ***/

	private class searchMarketActivityTask extends
			AsyncTask<String, Integer, List<MarketCheck>> {
		protected void onPreExecute() {
			showWaitingDialog();
		};

		String str = "";

		protected List<MarketCheck> doInBackground(String... arg0) {
			str = arg0[0];
			return DataProviderFactory.getProvider().getMarketActivity(str);
		}

		protected void onPostExecute(List<MarketCheck> result) {
			if (result != null && result.size() != 0) {
				Customer cc = Customer.findByCustId(str);
				cc.setIsActivity("1");// ���г���ı�־
				cc.update();
			}
			shopVisitListAdapter.parameterList = Customer.findIsVisitShop();
			shopVisitListAdapter.notifyDataSetChanged();
			dismissWaitingDialog();
			overdialog.cancel();

		}
	}

	/** �������� */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				mSlideHolder.toggle();
				break;
			case R.id.shopSearch:
				searchShop();
				break;
			case R.id.addCustomer:
				Intent i = new Intent(HomeActivity.this, CustomerActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				break;
			case R.id.map_icon_img:
				// Intent iis=new
				// Intent(HomeActivity.this,MyBaiduMap.class);//��ת����ʾ��ͼ�Ľ���
				Intent iis = new Intent(HomeActivity.this,
						MyLocationActivity.class);

				startActivity(iis);
				break;
			}
		}

	};

	/**
	 * MethodsTitle: �ݷù���֮�ͻ�����
	 * 
	 * @author: xg.chen
	 * @date:2016��11��7��
	 */
	private void searchShop() {
		if (tv_parameter.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.not_null), Toast.LENGTH_SHORT).show();
		} else {
			searchShopTask = new SearchShopTask();
			searchShopTask.execute(tv_parameter.getText().toString().trim());
		}
	}

	// ȡ���ֻ����ذ�ť����
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if (searchShopTask.getStatus()== AsyncTask.Status.RUNNING) {
			// searchShopTask.cancel(true);
			// }
			mSlideHolder.toggle();
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
