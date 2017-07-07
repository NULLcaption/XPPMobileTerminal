package com.xpp.moblie.adapter.stockage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;













import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.query.SkuUnit;
import com.xpp.moblie.screens.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SkuUnitAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Activity activity;
	private List<BaseParameter> bpList;
	private SkuUnit product;
	public SkuUnitAdapter(Activity activity,List<BaseParameter> bpList, SkuUnit product) {
	this.activity=activity;
	layoutInflater=LayoutInflater.from(activity);
	this.product=product;
	this.bpList=bpList;
	}
	


//	public void changeData(List<Inventory> invIist){
//		this.invIist=invIist;
//		
//	}
	
	
	




	@Override
	public int getCount() {
		
		return bpList.size();
	}

	public List<BaseParameter> getBpList() {
		return bpList;
	}

	public void setBpList(List<BaseParameter> bpList) {
		this.bpList = bpList;
	}

	@Override
	public BaseParameter getItem(int position) {
		// TODO Auto-generated method stub
		return bpList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler holder=null;
		if(convertView==null){
			holder=new ViewHodler();
			convertView=layoutInflater.inflate(R.layout.child_stock_age_detail, null);
			holder.tv_date=(TextView) convertView.findViewById(R.id.tv_date);
			holder.et_quantity=(EditText) convertView.findViewById(R.id.et_sku);
			holder.sp_unit=(Spinner) convertView.findViewById(R.id.sp_unit);
			
			convertView.setTag(holder);
		}else{
			holder=(ViewHodler) convertView.getTag();
			//hodler.tv_date.setText(getItem(position).getObj1()+"年"+getItem(position).getObj2()+"月");
			
		}
		//List<Dictionary> list=Dictionary.findbyRemark("unit");
		holder.et_quantity.clearFocus();
		String[] unitItems=product.getSku_unit().split(",");
		final ArrayAdapter<String> unitAdapter=new ArrayAdapter<String>(
				activity,android.R.layout.simple_spinner_item, unitItems);
		unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		holder.sp_unit.setAdapter(unitAdapter);
		final int n=position;
//	holder.et_quantity.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				if (!"".equals(s.toString())) {	
//					System.out.println("打印数字1"+n+"打印数字2"+s);
//					bpList.get(n).setObj11(Integer.parseInt(s.toString()));
//			
//				//System.out.println("打印数字"+s);
//				}
//			}
//		});
		holder.et_quantity.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String text=((EditText)v).getText().toString();
					if(!"".equals(text)){
						bpList.get(n).setObj11(Integer.parseInt(text));
						
					}
					
				}
				
			}
		});
		
		
		
		holder.sp_unit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				bpList.get(n).setObj4(parent.getItemAtPosition(position).toString());

				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		
//		hodler.btn_unit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				View overdiaView = View.inflate(activity,
//						R.layout.dialog_search_shop_msg, null);
//				overdialog = new Dialog(activity,
//						R.style.dialog_xw);
//				ListView clist = (ListView) overdiaView
//						.findViewById(R.id.clist);
//				TextView tv_title = (TextView) overdiaView
//						.findViewById(R.id.Title);
//				tv_title.setText("单位列表");
//				SettingAdapter settingAdapter = new SettingAdapter(
//						activity,Arrays.asList(unitItems));
//				clist.setAdapter(settingAdapter);
//				overdialog.setContentView(overdiaView);
//				overdialog.setCanceledOnTouchOutside(false);
//				Button overcancel = (Button) overdiaView
//						.findViewById(R.id.dialog_cancel_btn);
//				overcancel.setOnClickListener(new OnClickListener() {
//					public void onClick(View v) {
//						overdialog.cancel();
//					}
//				});
//				bpList.get(p).setObj4(settingAdapter.getUnit());
//				overdialog.show();
//				
//			}
//		});
		
		
		BaseParameter bp=bpList.get(position);
	
			holder.tv_date.setText(bp.getObj1());
	
		
		if(bp.getObj11()==0){
			holder.et_quantity.setText(null);
			
		}else{
			holder.et_quantity.setText(bp.getObj11()+"");
			
		}
	
		if(bp.getObj4()==null){
			//hodler.btn_unit.setText(unitItems[0]);
			for (int i = 0; i < unitItems.length; i++) {
				if("杯".equals(unitItems[i])){
					holder.sp_unit.setSelection(i);
				}
			}
			
		}else{
			//hodler.btn_unit.setText(getItem(position).getObj4());
			for (int i = 0; i < unitItems.length; i++) {
				if(bp.getObj4().equals(unitItems[i])){
					holder.sp_unit.setSelection(i);
				}
			}
		}
		
	
		
		return convertView;
	}


	private class ViewHodler {
		TextView tv_date = null;
		//TextView tv_unit = null;
		EditText et_quantity=null;
		//Button btn_unit=null;
		Spinner sp_unit=null;
	}
	

//	
//	public class SettingAdapter extends BaseAdapter {
//		private List<String> data = new ArrayList<String>();//
//		private LayoutInflater layoutInflater;
//		private String unit;
//		public SettingAdapter(Context context, List<String> data) {
//			this.data = data;
//			this.layoutInflater = LayoutInflater.from(context);
//		}
//
//		
//		public String getUnit() {
//			return unit;
//		}
//
//
//		public void setUnit(String unit) {
//			this.unit = unit;
//		}
//
//
//		public int getCount() {
//			return data.size();
//		}
//
//		public String getItem(int position) {
//			return data.get(position);
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHodler2 hodler2 = null;
//			if (convertView == null) {
//				// 获取组件布局
//				hodler2 = new ViewHodler2();
//				convertView = layoutInflater.inflate(
//						R.layout.dialog_search_unit_list_child, null);
//				hodler2.tv_date = (TextView) convertView
//						.findViewById(R.id.tv_unit);
//				// hodler.channelId = (TextView) convertView
//				// .findViewById(R.id.address);
//				convertView.setTag(hodler2);
//			} else {
//				hodler2 = (ViewHodler2) convertView.getTag();
//			}
//
//			hodler2.tv_date.setText(data.get(position));
//			// hodler.channelId.setText(data.get(position).getChannelId());
//			
//			// 绑定数据、以及事件触发
//			final int n = position;
//			convertView.setOnClickListener(new OnClickListener() {
//				public void onClick(View arg0) {
//					unit=getItem(n);
//					overdialog.cancel();
//				}
//			});
//			return convertView;
//		}
//		
//		private class ViewHodler2 {
//			TextView tv_date = null;
//
//		}
//		
//	}
	
}
