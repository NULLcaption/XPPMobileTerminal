package com.xpp.moblie.screens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.util.PictureShowUtils;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.Toast;
/**
 * Title: �ݷù���֮�༭�嵥
 * ��Ҫ���ܣ�ѡ��ɾ��������ɾ��
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016��11��7��
 */
public class EditVisistList extends Activity {

	// private ListView lv_deleteList;
	private CheckBox bc_check;
	private TableLayout tl_tab;
	private List<Customer> customerList;
	private HashMap<String, Customer> state;
	private Dialog waitingDialog;
    private Integer menuid=1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_edit_visist_list);
		initView();
		initData();
	}

	private void initView() {
		state = new HashMap<String, Customer>();
		// lv_deleteList = (ListView) findViewById(R.id.deleteList);
		bc_check = (CheckBox) findViewById(R.id.checkbox);
		tl_tab = (TableLayout) findViewById(R.id.tab);
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.deleteChoose).setOnClickListener(BtnClicked);

		bc_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton chbtn, boolean isChecked) {
				if (isChecked) {
					bc_check.setText(getString(R.string.cancle_choose));
					for (Customer customer : customerList) {
						state.put(customer.getCustId(), customer);
					}
				} else {
					bc_check.setText(getString(R.string.choose_all));
					state = new HashMap<String, Customer>();
				}
				addCheckBox(tl_tab);
			}
		});
	}

	private void initData() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			BaseParameter s = (BaseParameter) bundle.get("parameterList");
//			System.out.println("menuid:"+bundle.get("menuid"));
			menuid=(Integer)bundle.get("menuid");
			if (s != null) {
				customerList = s.getCustomerList();
				addCheckBox(tl_tab);
			}
		}
	}

	// ����checkBox
	public void addCheckBox(TableLayout table) {
		table.removeAllViews();
		for (Customer customer : customerList) {// �б�Ʒ
			TableRow row = new TableRow(this);
			row.setBackgroundResource(R.drawable.item_background_clicking);
			CheckBox cb = new CheckBox(this);
			cb.setButtonDrawable(R.drawable.checkbox_selector);
			cb.setTextColor(Color.BLACK);
			cb.setText(customer.getCustName());// ������ʾ��
			cb.setTag(R.string.tag1, customer.getCustId());// ����id
			cb.setTag(R.string.tag2, customer);// ��������
			cb.setButtonDrawable(R.drawable.checkbox_selector);
			cb.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
					R.drawable.dialog_line1);
			cb.setPadding(60, 10, 10, 4);
			Customer mp = state.get(customer.getCustId());
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
			String id = (String) b.getTag(R.string.tag1);
			Customer name = (Customer) b.getTag(R.string.tag2);
			if (isChecked) {
				state.put(id, name);
				if (state.size() == customerList.size()) {
					bc_check.setChecked(true);
				}
			} else {
				state.remove(id);
				if (state==null||state.size()==0) {
					bc_check.setChecked(false);
				}
			}
		}
	};

	/** �������� */
	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_back:
				finish();
				break;
			case R.id.deleteChoose:
				// ����Ƿ���δ�ϴ�����
				if(state.size()==0){
					Toast.makeText(getApplicationContext(), "��ѡ��ɾ����.....", Toast.LENGTH_SHORT).show();
					break;
				}
				XPPApplication.mapinfo.clear();//��յ�ͼ��λ����
				String str = "";
				for (Map.Entry<String, Customer> entry : state.entrySet()) {
					int unsynNum = 0;
					 String key = entry.getKey().toString();
					 Customer value = (Customer) entry.getValue();
					 unsynNum =  AbnormalPrice.synchronousAbnormalPrice(key).size();
					 unsynNum += Distribution.synchronousDis(key).size();
					 unsynNum += DisPlay.synchronousDisPlay(key).size();
					 unsynNum += Inventory.synchronousInventory(key).size();
					 unsynNum += MarketCheck.synchronousMarketCheck(key).size();
					 unsynNum += PhotoInfo.synchronousPhoto(key).size();
					 if(unsynNum!=0){
							str = value.getCustName()+","+str;
					 }
				}
				if(!"".equals(str)){
				View overdiaView = View.inflate(EditVisistList.this,
						R.layout.dialog_confirmation, null);
				final Dialog overdialog = new Dialog(EditVisistList.this,
						R.style.dialog_xw);
				TextView tv_02 = (TextView) overdiaView.findViewById(R.id.TextView02);
				tv_02.setText(str+"��δ�ϴ���Ϣ��ȷ��ɾ����");
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
						overdialog.cancel();
						new DeleteTask().execute();
					}
				});
				overdialog.show();
				}else{
					new DeleteTask().execute();

				}
				
				break;
			}
		}

	};

	private class DeleteTask extends AsyncTask<Object, Integer, String> {
		@Override
		protected void onPostExecute(String  result) {
			if(result==null || result.length()<1){
				Toast.makeText(getApplicationContext(), "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), result+"ɾ��ʧ��", Toast.LENGTH_SHORT).show();

			}
			dismissWaitingDialog();
			Map<String, String> map = new HashMap<String, String>();
			if (DataProviderFactory.getMenuId() == 0) {
				map.put("refresh", "visist");
				// } else if(DataProviderFactory.getMenuId() == 1){
			} else if(DataProviderFactory.getMenuId() == 8){
				map.put("refresh", "customerStock");	
			}
			else{
				map.put("refresh", "customer");
			}
			XPPApplication.sendChangeBroad(EditVisistList.this,
					XPPApplication.REFRESH_SHOP_RECEIVER, map);
			XPPApplication.exit(EditVisistList.this);
		}

		@Override
		protected void onPreExecute() {
			showWaitingDialog();
		}

		protected String  doInBackground(Object... arg0) {
			String str =null;
		
			for (Map.Entry<String, Customer> entry : state.entrySet()) {
					final String key = entry.getKey().toString();
					 Customer value = (Customer) entry.getValue();
					// ɾ�����Ϣ
					 try {	
						 if (menuid==0) {
							 value.setIsVisitShop("");
							 value. update();
						} else if(menuid==8){
							 value.setCustomerDataType("");
							 value. update();
						}
						else {
							AbnormalPrice.deleteAll(AbnormalPrice.getRecordsCount(key));// ����쳣�۸���Ϣ
							DisPlay.deleteAll(DisPlay.getRecordsCount(key));// ���������Ϣ
							Distribution.deleteAll(Distribution.getRecordsCount(key));// ����̻���Ϣ
							Inventory.deleteAll(Inventory.getRecordsCount(key));// ���������Ϣ
							MarketCheck.deleteAll(MarketCheck.findByCustId(key));// ����г����Ϣ
							// ɾ��Ƭ
										PictureShowUtils.deletePhoto(PhotoInfo
												.findAllPhotoByCustId(key));
							PhotoInfo.deleteAll(PhotoInfo.findAllPhotoByCustId(key));// �����Ƭ��Ϣ
							value.delete();// ����ͻ���Ϣ
						}
					
			} catch (Exception e) {
				e.printStackTrace();
				str = value.getCustName()+","+str;
				continue;
			}
			}
			
			return str;
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
