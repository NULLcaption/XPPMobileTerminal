package com.xpp.moblie.screens;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.Product;

/**
 * �������
 * **/
@SuppressLint("SimpleDateFormat")
public class InventoryActivity extends Activity {
	private Customer shop;
	private TextView tv_custName;
	private ListView lv_categorySortList, lv_inventoryList;
	private Button btn_addInventory, btn_addSKU;
	public InventoryListAdapter inventoryListAdapter;
	private List<Inventory> inventoryList = new ArrayList<Inventory>();
	// private RelativeLayout rl_addInventory;
	private boolean flag = false;// true Ʒ���б� false Ϊ�̵�����
	private Button btn_batch, btn_end_batch,btn_save;
	private Dialog dialog = null;
	private Dialog endDialog = null;
	private CategorySortListAdapter categorySortListAdapter;

	// private Calendar c ,end;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_inventory);
		initView();
		initData();
		registerReceiver(UpdateListener, new IntentFilter(
				XPPApplication.INVENTORY_RECEIVER));
	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(UpdateListener);
	}

	private void initView() {
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		// rl_addInventory = (RelativeLayout) findViewById(R.id.addInventory);
		btn_save =	(Button) findViewById(R.id.save);
		btn_save.setOnClickListener(BtnClicked);
		btn_addInventory = (Button) findViewById(R.id.saveInventory);
		btn_addInventory.setOnClickListener(BtnClicked);
		lv_categorySortList = (ListView) findViewById(R.id.categorySortList);
		lv_inventoryList = (ListView) findViewById(R.id.inventoryList);
		tv_custName = (TextView) findViewById(R.id.custName);
		btn_addSKU = (Button) findViewById(R.id.addSKU);
		btn_addSKU.setOnClickListener(BtnClicked);
	}

	private void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			shop = (Customer) bun.get("custInfo");
			Object o = bun.get("flag");
			if (o != null) {
				flag = (Boolean) o;
			}
		}
		tv_custName.setText(shop.getCustName() + "__"
				+ getString(R.string.inventory));

		if (flag) {
			showcategorySortList();
			
		} else {
			showInventoryList();
		}

	}

	/** ��ʾ�̵����� */
	private void showInventoryList() {
		lv_categorySortList.setVisibility(View.GONE);
		lv_inventoryList.setVisibility(View.VISIBLE);
		btn_addInventory.setVisibility(View.VISIBLE);
		btn_save.setBackgroundResource(R.drawable.ic_menu_order);
		new InventoryListTask().execute();

	}

	/** ��ʾƷ���б� **/
	private void showcategorySortList() {
		lv_inventoryList.setVisibility(View.GONE);
		lv_categorySortList.setVisibility(View.VISIBLE);
		btn_addInventory.setVisibility(View.GONE);
		btn_save.setBackgroundResource(R.drawable.floppy);
		new CategorySortListTask().execute();
	}

	/** �̵����� **/
	private class InventoryListTask extends
			AsyncTask<Object, Integer, List<Inventory>> {
		protected List<Inventory> doInBackground(Object... arg0) {
			return Inventory.findByCustId(shop.getCustId());
		}

		protected void onPostExecute(List<Inventory> result) {
			if (result == null || result.size() == 0) {
				btn_addSKU.setVisibility(View.VISIBLE);
				btn_addInventory.setVisibility(View.GONE);
			} else {
				btn_addInventory.setVisibility(View.VISIBLE);
				btn_addSKU.setVisibility(View.GONE);
			}
			inventoryListAdapter = new InventoryListAdapter(result);
			lv_inventoryList.setAdapter(inventoryListAdapter);
		}

	}

	/** �����̵����� **/
	private class CategorySortListTask extends
			AsyncTask<Object, Integer, List<Product>> {
		protected List<Product> doInBackground(Object... arg0) {
			List<Product> list = new ArrayList<Product>();
			List<Inventory> ivList = Inventory.findByCustId(shop.getCustId());
			for (Product product : Product.findAllCategorySortId()) {
				for (Inventory inventory : ivList) {
					if (inventory.getCategorySortId().equals(
							product.getCategorySortId())) {
						product.setPrice("1");
					}
				}
				list.add(product);
			}

			return list;
		}

		protected void onPostExecute(List<Product> result) {
			categorySortListAdapter = new CategorySortListAdapter(
					InventoryActivity.this, result);
			lv_categorySortList.setAdapter(categorySortListAdapter);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 10:
			List<Inventory> li = new ArrayList<Inventory>();
			li = Inventory.findByCustId(shop.getCustId());
			if (li != null && li.size() != 0) {
				btn_addInventory.setVisibility(View.VISIBLE);
				btn_addSKU.setVisibility(View.GONE);
			} else {
				btn_addSKU.setVisibility(View.VISIBLE);
				btn_addInventory.setVisibility(View.GONE);
			}
			inventoryListAdapter.inventoryList = li;
			inventoryListAdapter.notifyDataSetChanged();
			break;
		case 11:
			List<Inventory> li1 = new ArrayList<Inventory>();
			li1 = Inventory.findByCustId(shop.getCustId());
			if (li1 != null && li1.size() != 0) {
				btn_addInventory.setVisibility(View.VISIBLE);
				btn_addSKU.setVisibility(View.GONE);
			} else {
				btn_addSKU.setVisibility(View.VISIBLE);
				btn_addInventory.setVisibility(View.GONE);
			}
			inventoryListAdapter.inventoryList = li1;
			inventoryListAdapter.notifyDataSetChanged();
			break;
		}
	}

	private BroadcastReceiver UpdateListener = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			String key = b.getString("update");
			if (key.equals("Y")) {
				List<Inventory> li = new ArrayList<Inventory>();
				li = Inventory.findByCustId(shop.getCustId());
				if (li != null && li.size() != 0) {
					btn_addInventory.setVisibility(View.VISIBLE);
					btn_addSKU.setVisibility(View.GONE);
				} else {
					btn_addSKU.setVisibility(View.VISIBLE);
					btn_addInventory.setVisibility(View.GONE);
				}
				inventoryListAdapter.inventoryList = li;
				inventoryListAdapter.notifyDataSetChanged();
			}
		}
	};

	/** �������� */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(InventoryActivity.this);
				break;
			case R.id.save:
				if (flag) {
					if (saveInventoryCache()) {
						Intent i = new Intent(InventoryActivity.this,
								InventoryActivity.class);
						setResult(10, i);
					}
				} else {// �������ݸ�Ϊ���ϴ�
					if (sendInventory()) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("type", "inventory");
						XPPApplication.sendChangeBroad(InventoryActivity.this,
								XPPApplication.UPLOADDATA_RECEIVER, map);
						Toast.makeText(getApplicationContext(),
								getString(R.string.save_success),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(),
								getString(R.string.save_fail),
								Toast.LENGTH_SHORT).show();
					}
				}

				XPPApplication.exit(InventoryActivity.this);
				break;
			case R.id.saveInventory:
				Intent i = new Intent(InventoryActivity.this,
						InventoryActivity.class);
				i.putExtra("flag", true);// ����Ʒ���б����̵�����
				i.putExtra("custInfo", shop);// ����Ʒ���б����̵�����
				startActivityForResult(i, 10);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				break;

			case R.id.addSKU:
				Intent i1 = new Intent(InventoryActivity.this,
						InventoryActivity.class);
				i1.putExtra("flag", true);// ����Ʒ���б����̵�����
				i1.putExtra("custInfo", shop);// ����Ʒ���б����̵�����
				startActivityForResult(i1, 11);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				break;
			}
		}
	};

	private boolean saveInventoryCache() {
		try {
			List<Inventory> list = new ArrayList<Inventory>();
			list = Inventory.findByCustId(shop.getCustId());
			for (Inventory inventory : inventoryList) {
				for (Inventory ii : list) {
					if (inventory.getCategorySortId().equals(
							ii.getCategorySortId())) {// &&
														// inventory.getBatch().equals(ii.getBatch())
						Inventory.delete(ii);
						break;
					}
				}
				inventory.save();
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	private boolean sendInventory() {
		try {
			for (Inventory inventory : Inventory.findByCustId(shop.getCustId())) {
				inventory.setStatus(Status.UNSYNCHRONOUS);
				inventory.update();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@SuppressLint("ShowToast")
	public class CategorySortListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		private List<Product> productList = new ArrayList<Product>();

		public CategorySortListAdapter(Activity activity,
				List<Product> productList) {
			this.layoutInflater = LayoutInflater.from(activity);
			this.productList = productList;
		}

		public int getCount() {
			return productList.size();
		}

		public Object getItem(int position) {
			return productList.get(position);
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
						R.layout.child_abnormal_price, null);
				hodler.tv_categoryName = (TextView) convertView
						.findViewById(R.id.categoryName);
				hodler.iv_recordIc = (ImageView) convertView
						.findViewById(R.id.recordIc);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}
			hodler.tv_categoryName.setText(productList.get(position)
					.getCategorySortDesc());
			final int n = position;
			if (productList.get(n).getPrice() != null) {
				hodler.iv_recordIc.setVisibility(View.VISIBLE);

			}
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {

					View overdiaView = View.inflate(InventoryActivity.this,
							R.layout.dialog_inventory_input, null);
					final Dialog overdialog = new Dialog(
							InventoryActivity.this, R.style.dialog_xw);
					overdialog.setContentView(overdiaView);
					overdialog.setCanceledOnTouchOutside(false);
					Button overcancel = (Button) overdiaView
							.findViewById(R.id.dialog_cancel_btn);
					btn_batch = (Button) overdiaView.findViewById(R.id.batch);
					btn_end_batch = (Button) overdiaView
							.findViewById(R.id.endBatch);
					btn_batch.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							showDialog(0);
							int SDKVersion = Integer
									.valueOf(android.os.Build.VERSION.SDK);// ��ȡϵͳ�汾
							DatePicker dp = findDatePicker((ViewGroup) dialog
									.getWindow().getDecorView());
							if (dp != null) {
								if (SDKVersion < 11) {
									((ViewGroup) dp.getChildAt(0))
											.getChildAt(0).setVisibility(
													View.GONE);
								} else if (SDKVersion >= 14 && SDKVersion < 15) {
									((ViewGroup) ((ViewGroup) dp.getChildAt(0))
											.getChildAt(0)).getChildAt(0)
											.setVisibility(View.GONE);
								} else if (SDKVersion > 15) {
									((ViewGroup) ((ViewGroup) dp.getChildAt(0))
											.getChildAt(0)).getChildAt(2)
											.setVisibility(View.GONE);
								}
							}
						}
					});

					btn_end_batch.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							showDialog(1);
							int SDKVersion = Integer
									.valueOf(android.os.Build.VERSION.SDK);// ��ȡϵͳ�汾
							DatePicker dp = findDatePicker((ViewGroup) endDialog
									.getWindow().getDecorView());
							if (dp != null) {
								if (SDKVersion < 11) {
									((ViewGroup) dp.getChildAt(0))
											.getChildAt(0).setVisibility(
													View.GONE);
								} else if (SDKVersion >= 14 && SDKVersion < 15) {
									((ViewGroup) ((ViewGroup) dp.getChildAt(0))
											.getChildAt(0)).getChildAt(0)
											.setVisibility(View.GONE);
								} else if (SDKVersion > 15) {
									((ViewGroup) ((ViewGroup) dp.getChildAt(0))
											.getChildAt(0)).getChildAt(2)
											.setVisibility(View.GONE);
								}
							}
						}
					});
					overcancel.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							overdialog.cancel();
						}
					});
					Button overok = (Button) overdiaView
							.findViewById(R.id.dialog_ok_btn);
					overok.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (btn_batch.getText() == null
									|| btn_batch.getText().toString()
											.equals("")) {
								Toast.makeText(getApplicationContext(),
										"����������ʱ��", Toast.LENGTH_SHORT).show();
							} else {
								SimpleDateFormat formatter = new SimpleDateFormat(
										"yyyy-MM");
								int result = 0;
								if (btn_end_batch.getText() != null
										&& !btn_batch.getText().toString()
												.equals("")) {// ʱ��Ƚ�
									Calendar c1 = Calendar.getInstance();
									Calendar c2 = Calendar.getInstance();
									try {
										c1.setTime(formatter.parse(btn_batch
												.getText().toString()));
										c2.setTime(formatter
												.parse(btn_end_batch.getText()
														.toString()));
									} catch (java.text.ParseException e) {
										e.printStackTrace();
									}
									result = c1.compareTo(c2);
								}
								if (result > 0) {
									Toast.makeText(getApplicationContext(),
											"����ʱ�������...", Toast.LENGTH_SHORT)
											.show();
								} else {
									boolean hh = true;
									for (int i = 0; i < inventoryList.size(); i++) {// �Ƿ���ڡ����ڸ�����������
										if (productList
												.get(n)
												.getCategorySortId()
												.equals(inventoryList.get(i)
														.getCategorySortId())) {
											hh = false;
											break;
										}
									}
									if (hh) {
										Inventory ivt = new Inventory(
												shop.getCustId(),
												productList.get(n)
														.getCategorySortId(),
												productList.get(n)
														.getCategorySortDesc(),
												null,// et_quantity.getText().toString(),
												null,
												null,
												btn_batch.getText().toString(),
												btn_end_batch.getText()
														.toString(),
												DataProviderFactory.getUserId(),
												DataProviderFactory
														.getDayType(),
												Status.NEW);
										inventoryList.add(ivt);
									}
									productList.get(n).setPrice("1");
									categorySortListAdapter
											.notifyDataSetChanged();
									overdialog.cancel();
								}
							}
						}
					});
					overdialog.show();

				}
			});
			return convertView;
		}

	}

	protected class ViewHodler {
		TextView tv_categoryName = null;
		ImageView iv_recordIc = null;

	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_categoryName.setText(null);
		pViewHolder.iv_recordIc.setVisibility(View.GONE);
	}

	/**
	 * �ӵ�ǰDialog�в���DatePicker�ӿؼ�
	 */

	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}

	/**
	 * �������ڼ�ʱ��ѡ��Ի���
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog result = null;
		switch (id) {
		case 0:
			dialog = null;
			Calendar c = Calendar.getInstance();
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							btn_batch.setText(year
									+ "-"
									+ (month + 1 < 10 ? "0" + (month + 1)
											: (month + 1)));

						}
					}, c.get(Calendar.YEAR), // �������
					c.get(Calendar.MONTH), // �����·�
					c.get(Calendar.DAY_OF_MONTH) // ��������
			);
			result = dialog;
			break;
		case 1:
			endDialog = null;
			Calendar end = Calendar.getInstance();
			endDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							btn_end_batch.setText(year
									+ "-"
									+ (month + 1 < 10 ? "0" + (month + 1)
											: (month + 1)));

						}
					}, end.get(Calendar.YEAR), // �������
					end.get(Calendar.MONTH), // �����·�
					end.get(Calendar.DAY_OF_MONTH) // ��������
			);
			result = endDialog;
			break;
		}
		return result;
	}

	/*** �̵������б� **********/

	public class InventoryListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		public List<Inventory> inventoryList = new ArrayList<Inventory>();

		public InventoryListAdapter(List<Inventory> inventoryList) {
			this.layoutInflater = LayoutInflater.from(InventoryActivity.this);
			this.inventoryList = inventoryList;
		}

		public int getCount() {
			return inventoryList.size();
		}

		@Override
		public Object getItem(int position) {
			return inventoryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("ShowToast")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;
			if (convertView == null) {
				hodler = new ViewHodler();
				convertView = layoutInflater.inflate(R.layout.child_inventory,
						null);
				hodler.tv_categorySortDesc = (TextView) convertView
						.findViewById(R.id.categorySortDesc);
				hodler.tv_inventoryBatch = (TextView) convertView
						.findViewById(R.id.inventoryBatch);
				hodler.tv_inventoryEndBatch = (TextView) convertView
						.findViewById(R.id.inventoryEndBatch);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}

			hodler.tv_categorySortDesc.setText(inventoryList.get(position)
					.getCategorySortDecs());
			hodler.tv_inventoryBatch
					.setText(getString(R.string.inventory_category_sort_batch)
							+ inventoryList.get(position).getBatch());
			if (inventoryList.get(position).getEndBatch() == null
					|| "".equals(inventoryList.get(position).getEndBatch())) {
				hodler.tv_inventoryEndBatch.setText(inventoryList.get(position)
						.getEndBatch());
			} else {
				hodler.tv_inventoryEndBatch
						.setText(getString(R.string.inventory_category_sort_end_batch)
								+ inventoryList.get(position).getEndBatch());
			}
			final int n = position;
			convertView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View arg0) {

					View overdiaView = View.inflate(InventoryActivity.this,
							R.layout.dialog_confirmation, null);
					final Dialog overdialog = new Dialog(
							InventoryActivity.this, R.style.dialog_xw);
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
							Inventory.delete(inventoryList.get(n));
							sendOrderBroad();
							overdialog.cancel();
						}
					});
					overdialog.show();
					return false;

				}

			});

			convertView.setOnClickListener(new OnClickListener() {// �����޸�
						public void onClick(View arg0) {
							View overdiaView = View.inflate(
									InventoryActivity.this,
									R.layout.dialog_inventory_input, null);
							final Dialog overdialog = new Dialog(
									InventoryActivity.this, R.style.dialog_xw);
							overdialog.setContentView(overdiaView);
							overdialog.setCanceledOnTouchOutside(false);
							Button overcancel = (Button) overdiaView
									.findViewById(R.id.dialog_cancel_btn);
							btn_batch = (Button) overdiaView
									.findViewById(R.id.batch);
							btn_batch.setText(inventoryList.get(n).getBatch());
							btn_end_batch = (Button) overdiaView
									.findViewById(R.id.endBatch);
							btn_end_batch.setText((inventoryList.get(n)
									.getEndBatch() == null || ""
									.equals(inventoryList.get(n).getEndBatch())) ? ""
									: inventoryList.get(n).getEndBatch());
							btn_batch.setOnClickListener(new OnClickListener() {
								public void onClick(View arg0) {
									showDialog(0);
									int SDKVersion = Integer
											.valueOf(android.os.Build.VERSION.SDK);// ��ȡϵͳ�汾
									DatePicker dp = findDatePicker((ViewGroup) dialog
											.getWindow().getDecorView());
									if (dp != null) {
										if (SDKVersion < 11) {
											((ViewGroup) dp.getChildAt(0))
													.getChildAt(0)
													.setVisibility(View.GONE);
										} else if (SDKVersion >= 14
												&& SDKVersion < 15) {
											((ViewGroup) ((ViewGroup) dp
													.getChildAt(0))
													.getChildAt(0)).getChildAt(
													0).setVisibility(View.GONE);
										} else if (SDKVersion > 15) {
											((ViewGroup) ((ViewGroup) dp
													.getChildAt(0))
													.getChildAt(0)).getChildAt(
													2).setVisibility(View.GONE);
										}
									}
								}
							});

							btn_end_batch
									.setOnClickListener(new OnClickListener() {
										public void onClick(View arg0) {
											showDialog(1);
											int SDKVersion = Integer
													.valueOf(android.os.Build.VERSION.SDK);// ��ȡϵͳ�汾
											DatePicker dp = findDatePicker((ViewGroup) endDialog
													.getWindow().getDecorView());
											if (dp != null) {
												if (SDKVersion < 11) {
													((ViewGroup) dp
															.getChildAt(0))
															.getChildAt(0)
															.setVisibility(
																	View.GONE);
												} else if (SDKVersion >= 14
														&& SDKVersion < 15) {
													((ViewGroup) ((ViewGroup) dp
															.getChildAt(0))
															.getChildAt(0))
															.getChildAt(0)
															.setVisibility(
																	View.GONE);
												} else if (SDKVersion > 15) {
													((ViewGroup) ((ViewGroup) dp
															.getChildAt(0))
															.getChildAt(0))
															.getChildAt(2)
															.setVisibility(
																	View.GONE);
												}
											}
										}
									});
							overcancel
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											overdialog.cancel();
										}
									});
							Button overok = (Button) overdiaView
									.findViewById(R.id.dialog_ok_btn);
							overok.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									if (btn_batch.getText() == null
											|| btn_batch.getText().toString()
													.equals("")) {
										Toast.makeText(InventoryActivity.this,
												"����������ʱ��", 1).show();
									} else {
										SimpleDateFormat formatter = new SimpleDateFormat(
												"yyyy-MM");
										int result = 0;
										if (btn_end_batch.getText() != null
												&& !btn_batch.getText()
														.toString().equals("")) {// ʱ��Ƚ�
											Calendar c1 = Calendar
													.getInstance();
											Calendar c2 = Calendar
													.getInstance();
											try {
												c1.setTime(formatter
														.parse(btn_batch
																.getText()
																.toString()));
												c2.setTime(formatter
														.parse(btn_end_batch
																.getText()
																.toString()));
											} catch (java.text.ParseException e) {
												e.printStackTrace();
											}
											result = c1.compareTo(c2);
										}
										if (result > 0) {
											Toast.makeText(
													getApplicationContext(),
													"����ʱ�������...",
													Toast.LENGTH_SHORT).show();
										} else {

											inventoryList.get(n).setBatch(
													btn_batch.getText()
															.toString());
											inventoryList.get(n).setEndBatch(
													btn_end_batch.getText()
															.toString());
											inventoryList.get(n).update();
											sendOrderBroad();
											overdialog.cancel();
										}
									}
								}
							});
							overdialog.show();

						}
					});

			return convertView;
		}

		protected class ViewHodler {
			TextView tv_categorySortDesc = null;
			TextView tv_inventoryBatch = null;
			TextView tv_inventoryEndBatch = null;
		}

		protected void resetViewHolder(ViewHodler pViewHolder) {
			pViewHolder.tv_categorySortDesc.setText(null);
			pViewHolder.tv_inventoryBatch.setText(null);
			pViewHolder.tv_inventoryEndBatch.setText(null);
		}

		private void sendOrderBroad() {
			Intent i = new Intent(XPPApplication.INVENTORY_RECEIVER);
			i.putExtra("update", "Y");
			sendBroadcast(i);
		}

	}

	private final NumberKeyListener numberKeyListener = new NumberKeyListener() {
		public int getInputType() {
			// 0�޼��� 1Ӣ�ļ��� 2ģ����� 3���ּ���
			return 3;
		}

		@Override
		protected char[] getAcceptedChars() {
			char[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' };// ,'.'
			return c;
		}
	};

	// ��д�ֻ����ذ�ť����
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(InventoryActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
