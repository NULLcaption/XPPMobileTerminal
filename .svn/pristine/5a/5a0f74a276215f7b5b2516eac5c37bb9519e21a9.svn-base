package com.xpp.moblie.adapter.market;

import java.util.ArrayList;
import java.util.List;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BaseParameter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.MarketCheck;
import com.xpp.moblie.screens.MarketActivity;
import com.xpp.moblie.screens.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarketTotoalListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater layoutInflater;
	private Customer customer;
	public List<MarketCheck> bpList = new ArrayList<MarketCheck>();

	private BroadcastReceiver UpdateListener = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			String key = b.getString("type");
			if ("Y".equals(key)) {
				bpList = MarketCheck.findByCustId(customer.getCustId());
				notifyDataSetChanged();
			}
		}
	};

	public MarketTotoalListAdapter(Activity activity, List<MarketCheck> bpList,
			Customer customer) {
		this.layoutInflater = LayoutInflater.from(activity);
		this.activity = activity;
		this.bpList = bpList;
		this.customer = customer;
		activity.registerReceiver(UpdateListener, new IntentFilter(
		XPPApplication.REFRESH_MARKET_RECEIVER));
	}

	public int getCount() {
		return bpList.size();
	}

	@Override
	public Object getItem(int position) {
		return bpList.get(position);
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
			convertView = layoutInflater.inflate(
					R.layout.child_market_total_list, null);
			hodler.tv_marketId = (TextView) convertView
					.findViewById(R.id.marketId);
			hodler.tv_marketDesc = (TextView) convertView
					.findViewById(R.id.marketDesc);
			hodler.tv_planDate = (TextView) convertView
					.findViewById(R.id.planDate);
			hodler.tv_status = (TextView) convertView.findViewById(R.id.status);

			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
			resetViewHolder(hodler);
		}

		hodler.tv_marketId.setText(activity
				.getString(R.string.market_activity_id)
				+ bpList.get(position).getMarketDetailId());// id
		hodler.tv_marketDesc.setText(activity
				.getString(R.string.market_activity_desc)
				+ bpList.get(position).getMarketActivityName());// desc
		hodler.tv_planDate.setText(activity
				.getString(R.string.market_activity_planDate)
				+ bpList.get(position).getPlanYear()
				+ activity.getString(R.string.year)
				+ bpList.get(position).getPlanMonth()
				+ activity.getString(R.string.month));//

		if (DataProviderFactory.getDayType().equals(
				bpList.get(position).getDayType())
				&& Status.FINISHED.equals(bpList.get(position).getStatus())) {
			hodler.tv_status.setText(activity
					.getString(R.string.market_activity_upload_success));
		} else if (DataProviderFactory.getDayType().equals(
				bpList.get(position).getDayType())
				&& Status.UNSYNCHRONOUS
						.equals(bpList.get(position).getStatus())) {
			hodler.tv_status.setText(activity
					.getString(R.string.market_activity_waiting_for_upload));
		} else {
			hodler.tv_status.setText("");
		}
		final int n = position;
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(activity, MarketActivity.class);
				i.putExtra("toDetail", bpList.get(n));
				i.putExtra("custInfo", customer);
				// activity.startActivity(i);
				activity.startActivityForResult(i, 10);
				activity.overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});

		return convertView;
	}

	protected class ViewHodler {
		TextView tv_marketId = null;
		TextView tv_marketDesc = null;
		TextView tv_planDate = null;
		TextView tv_status = null;
	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_marketId.setText(R.string.market_activity_id);
		pViewHolder.tv_marketDesc.setText(R.string.market_activity_desc);
		pViewHolder.tv_planDate.setText(R.string.market_activity_planDate);
		pViewHolder.tv_status.setText("");

	}

}
