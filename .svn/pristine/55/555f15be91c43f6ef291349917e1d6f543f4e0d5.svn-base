package com.xpp.moblie.adapter.shopvisist;

import java.util.ArrayList;
import java.util.List;

import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Product;
import com.xpp.moblie.screens.AbnormalPriceActivity;
import com.xpp.moblie.screens.DistributionActivity;
import com.xpp.moblie.screens.InventoryActivity;
import com.xpp.moblie.screens.LeftViewActivity;
import com.xpp.moblie.screens.MarketActivity;
import com.xpp.moblie.screens.OrderActivity;
import com.xpp.moblie.screens.OrderTotalActivity;
import com.xpp.moblie.screens.PhotoViewActivity;
import com.xpp.moblie.screens.PriceManageActivity;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.screens.DisplayActivity;
import com.xpp.moblie.screens.StockAgeActivity;
import com.xpp.moblie.screens.VisitTaskActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Title: 读拜访任务列表适配器 Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016年11月21日 上午11:53:22
 */
public class VisistTaskListAdapter extends BaseAdapter {
	public List<BaseParameter> taskList = new ArrayList<BaseParameter>();
	private LayoutInflater layoutInflater;
	private Customer customer;
	private Activity activity;
	private Dialog waitingDialog;

	public VisistTaskListAdapter(Activity activity,
			List<BaseParameter> taskList, Customer customer) {
		this.layoutInflater = LayoutInflater.from(activity);
		this.taskList = taskList;
		this.activity = activity;
		this.customer = customer;
	}

	public int getCount() {
		return taskList.size();
	}

	@Override
	public Object getItem(int position) {
		return taskList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler = null;
		if (convertView == null) {
			hodler = new ViewHodler();
			convertView = layoutInflater.inflate(R.layout.child_visit_task,
					null);
			hodler.tv_taskDesc = (TextView) convertView
					.findViewById(R.id.task_desc);
			hodler.itme = (RelativeLayout) convertView.findViewById(R.id.itme);
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
			resetViewHolder(hodler);
		}

		hodler.tv_taskDesc.setText(taskList.get(position).getObj1());
		if (taskList.get(position).getObj11() != 0) {
			hodler.itme.setBackgroundResource(R.drawable.btn_orange);
			hodler.tv_taskDesc.setTextColor(activity.getResources().getColor(
					R.color.white));
		}
		final int n = position;

		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i0 = new Intent();
				switch (Integer.valueOf(taskList.get(n).getObj2())) {
				case 0:// 店头照
					i0 = new Intent(activity, PhotoViewActivity.class);
					i0.putExtra("back_class", VisitTaskActivity.class);
					i0.putExtra("success_class", VisitTaskActivity.class);
					i0.putExtra("title", taskList.get(n));
					i0.putExtra("custInfo", customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case 7:// 离店拍照
					i0 = new Intent(activity, LeftViewActivity.class);
					i0.putExtra("back_class", VisitTaskActivity.class);
					i0.putExtra("success_class", VisitTaskActivity.class);
					i0.putExtra("title", taskList.get(n));
					i0.putExtra("custInfo", customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case 1:// 铺货管理
					i0 = new Intent(activity, DistributionActivity.class);
					i0.putExtra("custInfo", customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case 2:// 陈列管理
					i0 = new Intent(activity, DisplayActivity.class);
					i0.putExtra("custInfo", customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case 3:// 价格管理 PriceManageActivity AbnormalPriceActivity
					i0 = new Intent(activity, PriceManageActivity.class);
					i0.putExtra("custInfo", customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;

				case 4:// 货龄管理
					i0 = new Intent(activity, StockAgeActivity.class);
					i0.putExtra("custInfo", customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case 5:// 市场活动检查
					i0 = new Intent(activity, MarketActivity.class);
					i0.putExtra("custInfo", customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				case 6:// 订单管理
					i0 = new Intent(activity, OrderTotalActivity.class);
					i0.putExtra("custInfo", customer);
					// new getOrderListTask().execute(customer);
					activity.startActivity(i0);
					activity.overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
					break;
				}
			}
		});

		return convertView;
	}

	// private void showWaitingDialog() {
	// if (waitingDialog == null) {
	// DialogInterface.OnShowListener showListener = new
	// DialogInterface.OnShowListener() {
	// @Override
	// public void onShow(DialogInterface dialog) {
	// ImageView img = (ImageView) waitingDialog
	// .findViewById(R.id.loading);
	// ((AnimationDrawable) img.getDrawable()).start();
	// }
	// };
	//
	// waitingDialog = new Dialog(activity, R.style.TransparentDialog);
	// waitingDialog.setContentView(R.layout.login_waiting_dialog);
	// waitingDialog.setOnShowListener(showListener);
	// waitingDialog.setCanceledOnTouchOutside(false);
	//
	// waitingDialog.show();
	// }
	// }

	protected class ViewHodler {
		TextView tv_taskDesc = null;
		RelativeLayout itme = null;

	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_taskDesc.setText(null);
		pViewHolder.tv_taskDesc.setTextColor(activity.getResources().getColor(
				R.color.black));
		pViewHolder.itme.setBackgroundResource(R.drawable.btn_grey_selector);
	}
}
