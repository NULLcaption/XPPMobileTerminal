package com.xpp.moblie.adapter.stockage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import android.R.integer;
import android.R.string;
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
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.StockReport;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.query.SkuUnit;
import com.xpp.moblie.screens.R;


public class SkuUnitListAdapter extends BaseAdapter {

	public List<SkuUnit> productList = new ArrayList<SkuUnit>();
	private LayoutInflater layoutInflater;
	private Activity activity;
	private Customer customer;
	public List<StockReport> invList = new ArrayList<StockReport>();
	//private TreeMap<String, String> state;
	private ListView skuList;
	//private List<BaseParameter> bpList;
	public SkuUnitListAdapter(Activity activity, List<SkuUnit> productList,
			Customer customer, List<StockReport> invList) {
		this.activity = activity;
		this.layoutInflater = LayoutInflater.from(activity);
		this.productList = productList;
		this.customer = customer;
		this.invList = invList;
	}

	public List<StockReport> getInvList() {
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
				.getSku_name());
		if (findByCategoryId(invList, productList.get(position).getSku_id()) != null
				&& findByCategoryId(invList,
						productList.get(position).getSku_id()).size() != 0) {
			hodler.iv_recordIc.setVisibility(View.VISIBLE);
			for (StockReport inventory : findByCategoryId(invList, productList
					.get(position).getSku_id())) {
				hodler.tv_time1.setVisibility(View.VISIBLE);
//				if (!"9999".equals(inventory.getYear())) {
					hodler.tv_time1.setText(hodler.tv_time1.getText()
							.toString()
							+ " "
							+ inventory.getCheckTime()
							+ "("
							+ inventory.getQuantity()
							
							+inventory.getUnitDesc()+"),"
							
							);
//				} else {
//					hodler.tv_time1.setText(hodler.tv_time1.getText()
//							.toString() + " 其他  "
//							+ inventory.getQuantity()
//							+ inventory.getUnit()+",");
//				}
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
				
//				for (StockReport inventory : findByCategoryId(invList,
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
				
				
				final SkuUnitAdapter skuAdapter=new SkuUnitAdapter(activity,getTime(-12,productList.get(n).getSku_id()),productList.get(n));
				TextView textView=(TextView)overdiaView.findViewById(R.id.TextView01);
				textView.setText(productList.get(n).getSku_name());
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
//						System.out.println("productList.get(n).getSku_id()"+productList.get(n).getSku_id());
						skuList.clearFocus();
						List<StockReport> temporarilyList = new ArrayList<StockReport>();
						
						for (StockReport inventory : findByCategoryId(invList,
								productList.get(n).getSku_id())) {
							// if(!Status.FINISHED.equals(inventory.getStatus()))
							invList.remove(inventory);
						}
						// for (Map.Entry<String, String> entity :
						// state.entrySet()) {
						// inv = new
						// StockReport(productList.get(n).getCategoryId(),entity.getValue(),entity.getKey());
						// temporarilyList.add(inv);
						// }
//						Set<String> keySet = state.keySet();
//						Iterator<String> iter = keySet.iterator();
//						while (iter.hasNext()) {
//							String key = iter.next();
//							inv = new StockReport(productList.get(n)
//									.getCategoryId(), productList.get(n)
//									.getCategoryDesc(), state.get(key), key);
//							
//							temporarilyList.add(inv);
//						}
						StockReport inv;
						for (BaseParameter bp : skuAdapter.getBpList()) {
							
							if(bp.getObj11()!=0){
								//state.put(bp.getObj2(), bp.getObj1());
								inv=new StockReport(productList.get(n).getSku_id(), productList.get(n)	.getSku_name(), bp.getObj2());
								inv.setCategoryId(productList.get(n).getSku_category_id());
								inv.setUnitDesc(bp.getObj4());
								inv.setProductionDate(bp.getObj3());
								inv.setQuantity(bp.getObj11()+"");
								temporarilyList.add(inv);
							}
							
							
							
						}
						
						invList.addAll(temporarilyList);
//						System.out.println("invList:----"+invList);
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

	private List<StockReport> findByCategoryId(List<StockReport> list,
			String skuId) {
		List<StockReport> li = new ArrayList<StockReport>();
		for (StockReport inventory : list) {
			if (skuId.equals(inventory.getSkuId())) {
				li.add(inventory);
			}
		}
		return li;
	}

	// private List<StockReport> distanceInv(List<StockReport> list) {
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
	private List<BaseParameter> getTime(int renewalsdata,String skuId) {
		List<BaseParameter> list = new ArrayList<BaseParameter>();
		BaseParameter bp = new BaseParameter();
//		Calendar c_end = Calendar.getInstance();
//		Calendar c_begin = Calendar.getInstance();
//		c_begin.add(Calendar.MONTH, (renewalsdata >= 0 ? renewalsdata - 1
//				: renewalsdata + 1));
//		bp.setObj1(String.valueOf(c_end.get(Calendar.YEAR)));
//		bp.setObj2(String.valueOf(c_end.get(Calendar.MONTH) + 1));
//		bp = getQuantity(bp,skuId);
//		
//		list.add(bp);
//		while (c_begin.before(c_end)) {
//			bp = new BaseParameter();
//			if (c_end.get(Calendar.MONTH) == 0) {
//				bp.setObj1(String.valueOf(c_end.get(Calendar.YEAR) - 1));
//				bp.setObj2("12");
//			} else {
//				bp.setObj1(String.valueOf(c_end.get(Calendar.YEAR)));
//				bp.setObj2(String.valueOf(c_end.get(Calendar.MONTH)));
//			}
//			
//			bp = getQuantity(bp,skuId);
//			list.add(bp);
//			c_end.add(Calendar.MONTH, -1);
//		}
		Calendar cale = null;
		cale = Calendar.getInstance();
		String taday=DataProviderFactory.getDayType();
		String[] nums=taday.split("-");
		  SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    String  day ;
		    String  day1 ;
		    int maxDate;
		int year=Integer.parseInt(nums[0]);
		int month = Integer.parseInt(nums[1]);
		int fxdate=16;
List<Dictionary> fxdateList=	Dictionary.findbyRemark("custfxdate");
if (fxdateList!=null && fxdateList.size()>0) {
	fxdate=Integer.parseInt(fxdateList.get(0).getItemDesc());
}
		
		
//		System.out.println("year:"+year1);
//		System.out.println("month:"+month1);
//		int year = cale.get(Calendar.YEAR);
//		int month = cale.get(Calendar.MONTH) + 1;
		//先加载上月
		  Calendar a = Calendar.getInstance();  
		  if (Integer.parseInt(nums[2])<fxdate) {
			    a.set(Calendar.YEAR, year);  
			    a.set(Calendar.MONTH, month - 1);  
			    a.add(Calendar.MONTH, -1);
			    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
			    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
			    maxDate = a.get(Calendar.DATE);  
			    for (int i = 1; i <= maxDate; i++) {
					cale = Calendar.getInstance();
					cale.add(Calendar.MONTH, -1);
					cale.set(Calendar.DAY_OF_MONTH, i);
				   day = format.format(cale.getTime());
				   day1 = format1.format(cale.getTime());
				   bp = new BaseParameter();
				   bp.setObj1(day);
				   bp.setObj2(day1);
				   bp.setObj3(day1.substring(0, 7));
				   bp = getQuantity(bp,skuId);
				   list.add(bp);
				}
			
		}
	
		    a.set(Calendar.YEAR, year);  
		    a.set(Calendar.MONTH, month - 1);  
		    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
		   maxDate = a.get(Calendar.DATE);  
//		    System.out.println("maxDate:"+maxDate);
		  
		    for (int i = 1; i <=  Integer.parseInt(nums[2]); i++) {
				cale = Calendar.getInstance();
				cale.add(Calendar.MONTH, 0);
				cale.set(Calendar.DAY_OF_MONTH, i);
			   day = format.format(cale.getTime());
			   day1 = format1.format(cale.getTime());
			   bp = new BaseParameter();
			   bp.setObj1(day);
			   bp.setObj2(day1);
			   bp.setObj3(day1.substring(0, 7));
			   bp = getQuantity(bp,skuId);
			   list.add(bp);
//			   System.out.println(day);
			}
//		bp = new BaseParameter();
//		bp.setObj1("9999");
//		bp.setObj2("99");
//		bp = getQuantity(bp,skuId);
//		list.add(bp);
		return list;
		
	}
	
	private BaseParameter getQuantity(BaseParameter parameter,String skuId){
		for (StockReport iv : invList) {
			if(iv.getSkuId().equals(skuId)&&parameter.getObj2().equals(iv.getCheckTime())){
				parameter.setObj4(iv.getUnitDesc());
				//parameter.setObj4(iv.getUnitDesc());
				//parameter.setObj4(skuId);
				parameter.setObj11(Integer.parseInt(iv.getQuantity()));
			return parameter;
			}
		}
		return parameter;
	}

}
