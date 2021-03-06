package com.xpp.moblie.adapter.shopvisist;

import java.util.ArrayList;
import java.util.List;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.AbnormalPrice;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Dictionary;
import com.xpp.moblie.query.DisPlay;
import com.xpp.moblie.query.Distribution;
import com.xpp.moblie.query.Info;
import com.xpp.moblie.query.Inventory;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.query.Order;
import com.xpp.moblie.query.PhotoInfo;
import com.xpp.moblie.screens.CustStockActivity;
import com.xpp.moblie.screens.CustomerActivity;
import com.xpp.moblie.screens.EditVisistList;
import com.xpp.moblie.screens.KunnrManagementActivity;
import com.xpp.moblie.screens.PhotoViewActivity;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.screens.VisitTaskActivity;
import com.xpp.moblie.screens.CustomerActivity.KunnrAdapter;
import com.xpp.moblie.util.MyImageButton;
import com.xpp.moblie.util.OnChangedListener;
import com.xpp.moblie.util.PictureShowUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Title: 拜访管理之客户列表
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年12月12日 下午2:38:59
 */
public class ShopVisitListAdapter extends BaseAdapter {
	
	public List<Customer> parameterList = new ArrayList<Customer>();
	private LayoutInflater layoutInflater;
	private Activity activity;

	private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

	public ShopVisitListAdapter(List<Customer> parameterList, Activity activity) {
		params.leftMargin = 2;
		this.parameterList = parameterList;
		this.layoutInflater = LayoutInflater.from(activity);
		this.activity = activity;
	}

	public int getCount() {
		return parameterList.size();
	}

	@Override
	public Object getItem(int position) {
		return parameterList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler = null;
		if (convertView == null) {
			// 获取组件布局
			hodler = new ViewHodler();
			convertView = layoutInflater
					.inflate(R.layout.child_shop_list, null);
			hodler.tv_custName = (TextView) convertView
					.findViewById(R.id.custName);
			hodler.tv_custId = (TextView) convertView.findViewById(R.id.custId);
			hodler.tv_phone = (TextView) convertView.findViewById(R.id.phone);
			hodler.tv_bossName = (TextView) convertView
					.findViewById(R.id.bossName);
			hodler.tv_address = (TextView) convertView
					.findViewById(R.id.address);
			hodler.btn_add = (Button) convertView.findViewById(R.id.add);
			hodler.btn_delete = (Button) convertView.findViewById(R.id.delete);
			hodler.ll_taskImage = (LinearLayout) convertView
					.findViewById(R.id.taskImage);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
			resetViewHolder(hodler);
		}
		hodler.tv_custName.setText(parameterList.get(position).getCustName());
		hodler.tv_custId.setText(parameterList.get(position).getCustId());
		hodler.tv_phone
				.setText((parameterList.get(position).getContractMobile() != null ? parameterList
						.get(position).getContractMobile() : "")
						+ "  "
						+ (parameterList.get(position).getContractPhone() != null ? parameterList
								.get(position).getContractPhone() : ""));
		hodler.tv_bossName.setText(parameterList.get(position)
				.getContractName());
		hodler.tv_address.setText(parameterList.get(position).getAddress());

		// 客户管理
		if (DataProviderFactory.getMenuId() == 1) {
			if ("1".equals(parameterList.get(position).getIsVisitShop())) {// 在线路拜访中出现
				hodler.btn_delete.setVisibility(View.VISIBLE);
				hodler.btn_add.setVisibility(View.GONE);
			} else {
				hodler.btn_add.setVisibility(View.VISIBLE);
				hodler.btn_delete.setVisibility(View.GONE);
			}
		}

		if (DataProviderFactory.getMenuId() == 0) {
			XPPApplication.mapinfo.clear();// 清空地图缓存数据
			for (int i = 0; i < parameterList.size(); i++) {
				Info mapinfo = new Info();
				mapinfo.setImgId(R.drawable.maker);
				if (null != parameterList.get(i).getLatitude()
						&& null != parameterList.get(i).getLongitude()
						&& !"".equals(parameterList.get(i).getLatitude())
						&& !"".equals(parameterList.get(i).getLongitude())) {
					// System.out.println("打印--------------");
					mapinfo.setLatitude(Double.parseDouble(parameterList.get(i)
							.getLatitude()));
					mapinfo.setLongitude(Double.parseDouble(parameterList
							.get(i).getLongitude()));
					mapinfo.setName(parameterList.get(i).getCustName());
					XPPApplication.mapinfo.add(mapinfo);
				}
			}
			// 小点
			setIcTask(hodler.ll_taskImage, parameterList.get(position));
		}

		/**
		 * 菜单点击事件
		 */
		final int n = position;
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (DataProviderFactory.getMenuId() == 0) {
					Intent i = new Intent(activity, VisitTaskActivity.class);
					i.putExtra("custInfo", parameterList.get(n));
					activity.startActivity(i);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
				} else if (DataProviderFactory.getMenuId() == 1) {
					Intent i1 = new Intent(activity, CustomerActivity.class);
					i1.putExtra("custInfo", parameterList.get(n));
					activity.startActivity(i1);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
				} else if (DataProviderFactory.getMenuId() == 4) {// 经销商管理
																	// （menu：分销库存提报）
					Intent i4 = new Intent(activity,
							KunnrManagementActivity.class);
					i4.putExtra("custInfo", parameterList.get(n));
					activity.startActivity(i4);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
				} else if (DataProviderFactory.getMenuId() == 8) {
					Intent i5 = new Intent(activity, CustStockActivity.class);
					i5.putExtra("custInfo", parameterList.get(n));
					activity.startActivity(i5);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
				}

			}
		});
		/*** 长按事件 **/
		convertView.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				if (DataProviderFactory.getMenuId() == 4) {
					return false;
				}
				if (DataProviderFactory.getMenuId() == 0) {
					refreshVisist();
				} else if (DataProviderFactory.getMenuId() == 8) {
					refreshCustStock();
				} else {
					refreshCustomer();
				}
				Intent o = new Intent(activity, EditVisistList.class);
				BaseParameter bp = new BaseParameter();
				bp.setCustomerList(parameterList);
				o.putExtra("parameterList", bp);
				o.putExtra("menuid", DataProviderFactory.getMenuId());
				activity.startActivity(o);
				return false;
			}
		});

		hodler.btn_add.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				parameterList.get(n).setIsVisitShop("1");
				parameterList.get(n).update();
				refreshCustomer();
			}
		});

		hodler.btn_delete.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				parameterList.get(n).setIsVisitShop("");
				parameterList.get(n).update();
				refreshCustomer();
			}
		});

		return convertView;
	}

	private class ViewHodler {
		TextView tv_custName = null;
		TextView tv_custId = null;
		TextView tv_phone = null;
		TextView tv_bossName = null;
		TextView tv_address = null;
		Button btn_add = null;
		Button btn_delete = null;
		LinearLayout ll_taskImage = null;
	}

	private void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_custName.setText(null);
		pViewHolder.tv_custId.setText(null);
		pViewHolder.tv_phone.setText(null);
		pViewHolder.tv_bossName.setText(null);
		pViewHolder.tv_address.setText(null);
		pViewHolder.btn_add.setVisibility(View.GONE);
		pViewHolder.btn_delete.setVisibility(View.GONE);
		pViewHolder.ll_taskImage.removeAllViews();
	}

	private void refreshVisist() {
		this.parameterList = Customer.findIsVisitShop();
		this.notifyDataSetChanged();
	}

	private void refreshCustStock() {
		this.parameterList = Customer.findCustomerStock();
		this.notifyDataSetChanged();
	}

	private void refreshCustomer() {
		this.parameterList = Customer.findAllCustomer();
		this.notifyDataSetChanged();
	}

	/*** 绘制小点 **/
	private void setIcTask(LinearLayout llt, Customer customer) {
		// params.leftMargin =3;
		ImageView iv = new ImageView(activity);
		// 店头照
		if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.DTZ) != 0) {
			iv.setBackgroundResource(R.drawable.ic_task_ok);
		} else {
			iv.setBackgroundResource(R.drawable.ic_task_no);
		}
		llt.addView(iv, params);

		// 铺货
		iv = new ImageView(activity);
		if (Distribution.getRecordsCount(customer.getCustId()).size() != 0) {
			iv.setBackgroundResource(R.drawable.ic_task_ok);
		} else {
			iv.setBackgroundResource(R.drawable.ic_task_no);
		}
		llt.addView(iv, params);

		// 陈列
		iv = new ImageView(activity);
		if (DisPlay.getRecordsCount(customer.getCustId()).size() != 0) {
			iv.setBackgroundResource(R.drawable.ic_task_ok);
		} else {
			iv.setBackgroundResource(R.drawable.ic_task_no);
		}
		llt.addView(iv, params);

		// 价格
		iv = new ImageView(activity);
		if (AbnormalPrice.getRecordsCount(customer.getCustId()).size() != 0) {
			iv.setBackgroundResource(R.drawable.ic_task_ok);
		} else {
			iv.setBackgroundResource(R.drawable.ic_task_no);
		}
		llt.addView(iv, params);

		// 货龄
		iv = new ImageView(activity);
		if (Inventory.getRecordsCount(customer.getCustId()).size() != 0) {
			iv.setBackgroundResource(R.drawable.ic_task_ok);
		} else {
			iv.setBackgroundResource(R.drawable.ic_task_no);
		}
		llt.addView(iv, params);

		if (customer.getIsActivity() != null) {// 有市场活动
			iv = new ImageView(activity);
			System.out.println("市场活动:" + customer.getCustId());
			if (MarketCheck.getRecordsCount(customer.getCustId()).size() != 0) {
				iv.setBackgroundResource(R.drawable.ic_task_ok);
				System.out.println("市场活动:ok"
						+ MarketCheck.getRecordsCount(customer.getCustId())
								.size());
			} else {
				iv.setBackgroundResource(R.drawable.ic_task_no);
				System.out.println("市场活动:no");
			}
			llt.addView(iv, params);
		}
		// 订单
		List<Dictionary> dicts = Dictionary.findbyTypeValue("isOrderManager");
		boolean flag = true;
		for (Dictionary dict : dicts) {
			if ("N".equals(dict.getItemDesc())) {
				if (dict.getItemValue().equals(DataProviderFactory.getRoleId())) {

					flag = false;
					break;
				}
			}
		}
		// 绘制订单管理的小点
		if (flag) {
			iv = new ImageView(activity);
			if (Order.getRecordsCount(customer.getCustId()).size() != 0) {
				iv.setBackgroundResource(R.drawable.ic_task_ok);
			} else {
				iv.setBackgroundResource(R.drawable.ic_task_no);
			}
			llt.addView(iv, params);

		}

		// 离店照
		iv = new ImageView(activity);
		if (PhotoInfo.getRecordsCount(customer.getCustId(), PhotoType.LDZ) != 0) {
			iv.setBackgroundResource(R.drawable.ic_task_ok);
		} else {
			iv.setBackgroundResource(R.drawable.ic_task_no);
		}
		llt.addView(iv, params);

	}

}
