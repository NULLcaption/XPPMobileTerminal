package com.xpp.moblie.adapter.stockage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.screens.R;


public class StockAgeListAdapter extends BaseAdapter {

	public List<Product> productList = new ArrayList<Product>();
	private LayoutInflater layoutInflater;
	private Activity activity;
	private Customer customer;
	public List<Inventory> invList = new ArrayList<Inventory>();
	//private TreeMap<String, String> state;
	private ListView skuList;
	//private List<BaseParameter> bpList;
	public StockAgeListAdapter(Activity activity, List<Product> productList,
			Customer customer, List<Inventory> invList) {
		this.activity = activity;
		this.layoutInflater = LayoutInflater.from(activity);
		this.productList = productList;
		this.customer = customer;
		this.invList = invList;
	}

	public List<Inventory> getInvList() {
		return invList;
	}

	public int getCount() {
		return productList.size();
	}

	public Object getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//System.out.println("getView+++++++");
		ViewHodler hodler = null;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = layoutInflater
					.inflate(R.layout.child_stock_age, null);
			hodler.tv_categoryName = (TextView) convertView
					.findViewById(R.id.categoryName);
			hodler.iv_recordIc = (ImageView) convertView
					.findViewById(R.id.recordIc);
			hodler.tv_time1 = (TextView) convertView.findViewById(R.id.time1);
			// hodler.tv_time2 = (TextView)
			// convertView.findViewById(R.id.time2);
			// hodler.tv_other = (TextView)
			// convertView.findViewById(R.id.other);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
			resetViewHolder(hodler);
		}
		
		
		hodler.tv_categoryName.setText(productList.get(position)
				.getCategoryDesc());
		if (findByCategoryId(invList, productList.get(position).getCategoryId()) != null
				&& findByCategoryId(invList,
						productList.get(position).getCategoryId()).size() != 0) {
			hodler.iv_recordIc.setVisibility(View.VISIBLE);
			for (Inventory inventory : findByCategoryId(invList, productList
					.get(position).getCategoryId())) {
				hodler.tv_time1.setVisibility(View.VISIBLE);
				if (!"9999".equals(inventory.getYear())) {
					hodler.tv_time1.setText(hodler.tv_time1.getText()
							.toString()
							+ " "
							+ inventory.getYear()
							+ "年"
							+ inventory.getMonth() +
							"月("
							+ inventory.getQuantity()
							
							+inventory.getUnit()+"),"
							
							);
				} else {
					hodler.tv_time1.setText(hodler.tv_time1.getText()
							.toString() + " 其他  "
							+ inventory.getQuantity()
							+ inventory.getUnit()+",");
				}
			}

		}
		final int n = position;
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// 比较器
//				state = new TreeMap<String, String>(new Comparator<String>() {
//					public int compare(String obj1, String obj2) {
//						return obj2.compareTo(obj1);
//					}
//				});
				
//				for (Inventory inventory : findByCategoryId(invList,
//						productList.get(n).getCategoryId())) {
//					state.put(inventory.getMonth(), inventory.getYear());
//					
//					
//				}
				View overdiaView = View.inflate(activity,
						R.layout.dialog_stock_age_checkbox, null);
				final Dialog overdialog = new Dialog(activity,
						R.style.dialog_xw);
//				TableLayout tl_tab = (TableLayout) overdiaView
//						.findViewById(R.id.tab);
//				addCheckBox(tl_tab, getTime(-12));
				
				
				final SkuAdapter skuAdapter=new SkuAdapter(activity,getTime(-12,productList.get(n).getCategoryId()),productList.get(n));
				
				skuList=(ListView) overdiaView.findViewById(R.id.tab);
				skuList.setAdapter(skuAdapter);
				skuList.setOnScrollListener(new OnScrollListener() {

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						  if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
	                            View currentView =view;
	                            if(currentView != null){
	                            	//System.out.println(" currentView.clearFocus();");
	                                currentView.clearFocus();
	                            }
	                        }
					}

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						// TODO Auto-generated method stub
						
					}});
			
				overdialog.setContentView(overdiaView);
				overdialog.setCanceledOnTouchOutside(false);
				Button overcancel = (Button) overdiaView
						.findViewById(R.id.dialog_cancel_btn);

				overcancel.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						overdialog.cancel();
					}
				});
				Button overok = (Button) overdiaView
						.findViewById(R.id.dialog_ok_btn);
				overok.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						//bpList=skuAdapter.getBpList();
						System.out.println("确定");
						skuList.clearFocus();
						List<Inventory> temporarilyList = new ArrayList<Inventory>();
						
						for (Inventory inventory : findByCategoryId(invList,
								productList.get(n).getCategoryId())) {
							// if(!Status.FINISHED.equals(inventory.getStatus()))
							invList.remove(inventory);
						}
						// for (Map.Entry<String, String> entity :
						// state.entrySet()) {
						// inv = new
						// Inventory(productList.get(n).getCategoryId(),entity.getValue(),entity.getKey());
						// temporarilyList.add(inv);
						// }
//						Set<String> keySet = state.keySet();
//						Iterator<String> iter = keySet.iterator();
//						while (iter.hasNext()) {
//							String key = iter.next();
//							inv = new Inventory(productList.get(n)
//									.getCategoryId(), productList.get(n)
//									.getCategoryDesc(), state.get(key), key);
//							
//							temporarilyList.add(inv);
//						}
						Inventory inv;
						for (BaseParameter bp : skuAdapter.getBpList()) {
							
							if(bp.getObj11()!=0){
								//state.put(bp.getObj2(), bp.getObj1());
								inv=new Inventory(productList.get(n)
										.getCategoryId(), productList.get(n)
										.getCategoryDesc(), bp.getObj1(), bp.getObj2());
								inv.setUnit(bp.getObj4());
								inv.setQuantity(bp.getObj11()+"");
								temporarilyList.add(inv);
							}
							
							
							
						}
						
						invList.addAll(temporarilyList);
						overdialog.cancel();
						notifyDataSetChanged();
					}
				});
				overdialog.show();

			}
		});

		return convertView;
	}

	protected class ViewHodler {
		TextView tv_categoryName = null;
		ImageView iv_recordIc = null;
		TextView tv_time1 = null;
		// TextView tv_time2 = null;
		// TextView tv_other = null;
	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_categoryName.setText(null);
		pViewHolder.tv_time1.setText(null);
		pViewHolder.tv_time1.setVisibility(View.GONE);
		// pViewHolder.tv_time2.setText(null);
		// pViewHolder.tv_time2.setVisibility(View.GONE);
		// pViewHolder.tv_other.setText(null);
		// pViewHolder.tv_other.setVisibility(View.GONE);
		pViewHolder.iv_recordIc.setVisibility(View.INVISIBLE);
	}

	private List<Inventory> findByCategoryId(List<Inventory> list,
			String CategoryId) {
		List<Inventory> li = new ArrayList<Inventory>();
		for (Inventory inventory : list) {
			if (CategoryId.equals(inventory.getCategorySortId())) {
				li.add(inventory);
			}
		}
		return li;
	}

	// private List<Inventory> distanceInv(List<Inventory> list) {
	// for (int i = 0; i < list.size() - 1; i++) {
	// for (int j = list.size() - 1; j > i; j--) {
	// if (list.get(j).getYear().equals(list.get(i).getYear())) {
	// list.remove(j);
	// }
	// }
	// }
	// return list;
	// }

//	public void addCheckBox(TableLayout table, List<BaseParameter> bpList) {
//		TableRow row = new TableRow(activity);
//		// for (BaseParameter bp : bpList) {// 列本品
//		for (int i = 0; i < bpList.size(); i++) {
//			CheckBox cb = new CheckBox(activity);
//			cb.setButtonDrawable(R.drawable.checkbox_selector);
//			cb.setTextColor(Color.BLACK);
//			if ("9999".equals(bpList.get(i).getObj1())) {
//				cb.setText("其他");// 设置显示名
//			} else {
//				cb.setText(bpList.get(i).getObj1() + "-"
//						+ bpList.get(i).getObj2());// 设置显示名
//			}
//			cb.setTag(R.string.tag1, bpList.get(i).getObj1());// 年
//			cb.setTag(R.string.tag2, bpList.get(i).getObj2());// 月
//			cb.setButtonDrawable(R.drawable.checkbox_selector);
//			String mp = state.get(bpList.get(i).getObj2());
//			if (mp != null && !"".equals(mp)) {
//				cb.setChecked(true);
//			}
//			cb.setOnCheckedChangeListener(ckListener);
//			if ((i + 1) % 2 != 0) {
//				row = new TableRow(activity);
//				// row.setBackgroundResource(R.drawable.item_background_clicking);
//				row.addView(cb);
//			} else {
//				row.addView(cb);
//				table.addView(row);
//			}
//		}
//		table.addView(row);
//	}
//
//	private OnCheckedChangeListener ckListener = new OnCheckedChangeListener() {
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			CheckBox b = (CheckBox) buttonView;
//			String id = (String) b.getTag(R.string.tag2);// 月份
//			String name = (String) b.getTag(R.string.tag1);// 年
//			if (isChecked) {
//				state.put(id, name);
//			} else {
//				state.remove(id);
//			}
//		}
//	};

	/** 两个时间段的月份 */
	private List<BaseParameter> getTime(int renewalsdata,String categoryId) {
		List<BaseParameter> list = new ArrayList<BaseParameter>();
		BaseParameter bp = new BaseParameter();
		Calendar c_end = Calendar.getInstance();
		Calendar c_begin = Calendar.getInstance();
		c_begin.add(Calendar.MONTH, (renewalsdata >= 0 ? renewalsdata - 1
				: renewalsdata + 1));
		bp.setObj1(String.valueOf(c_end.get(Calendar.YEAR)));
		bp.setObj2(String.valueOf(c_end.get(Calendar.MONTH) + 1));
		bp = getQuantity(bp,categoryId);
		
		list.add(bp);
		while (c_begin.before(c_end)) {
			bp = new BaseParameter();
			if (c_end.get(Calendar.MONTH) == 0) {
				bp.setObj1(String.valueOf(c_end.get(Calendar.YEAR) - 1));
				bp.setObj2("12");
			} else {
				bp.setObj1(String.valueOf(c_end.get(Calendar.YEAR)));
				bp.setObj2(String.valueOf(c_end.get(Calendar.MONTH)));
			}
			
			bp = getQuantity(bp,categoryId);
			list.add(bp);
			c_end.add(Calendar.MONTH, -1);
		}
		bp = new BaseParameter();
		bp.setObj1("9999");
		bp.setObj2("99");
		bp = getQuantity(bp,categoryId);
		list.add(bp);
		return list;
		
	}
	
	private BaseParameter getQuantity(BaseParameter parameter,String categoryId){
		for (Inventory iv : invList) {
			if(iv.getCategorySortId().equals(categoryId)&&parameter.getObj1().equals(iv.getYear())&&parameter.getObj2().equals(iv.getMonth())){
				parameter.setObj3(iv.getUnit());
				//parameter.setObj4(iv.getUnitDesc());
				//parameter.setObj4(categoryId);
				parameter.setObj11(Integer.parseInt(iv.getQuantity()));
			return parameter;
			}
		}
		return parameter;
	}
}
