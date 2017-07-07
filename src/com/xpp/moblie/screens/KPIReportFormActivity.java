package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseKPI;
import com.xpp.moblie.entity.Basekpis;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.UpdateTask;
import com.xpp.moblie.provider.WebService;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Menu;
import com.xpp.moblie.query.VistCust;
import com.xpp.moblie.screens.HomeActivity.SettingAdapter;
import com.xpp.moblie.screens.HomeActivity.ViewHodler;
import com.xpp.moblie.screens.R.string;
import com.xpp.moblie.util.ProgressBarUtil;
import com.xpp.moblie.util.WaitingDialogUtil;

public class KPIReportFormActivity extends Activity {

	private ViewPager viewPager;
	/** 装分页显示的view的数组 */
	private ArrayList<View> pageViews;
	private ImageView imageView;
	/** 将小圆点的图片用数组表示 */
	private ImageView[] imageViews;
	private Dialog waitingDialog;
	// 包裹小圆点的LinearLayout
	private ViewGroup viewPoints;
	private LinearLayout jxs_ck_ll,jxs_fx_ll,vistcust_ll;
	private View view1, view2, view3;
	private Button btn_jxs_ck, btn_jxs_fx,btn_vistcust;
	private TextView jxs_dest, jxs_comp, jxs_rate;
	// private TextView tvArea,tvDest,tvComp,tvRate;
	private View top;
	private Menu menu;
	private WaitingDialogUtil wdu;
	private Dialog showdialog, overdialog;
	private ViewGroup ll_visitTask;
	private ViewGroup imgGroup;
	private ListView kpiList, jxsKpiList,vistcustList;
	private int currViewIndex;
	private String userType;
	private KpiListAdapter adapter1,adapter2,adapter3;
	private JxsKpiListAdapter adapter10,adapter20;
	private VistCustListAdapter adapter30;
	List<BaseKPI> kpis10 = new ArrayList<BaseKPI>();
	List<BaseKPI> kpis20 = new ArrayList<BaseKPI>();
	List<BaseKPI> kpis30 = new ArrayList<BaseKPI>();
	List<BaseKPI> kpis1 = new ArrayList<BaseKPI>();
	List<BaseKPI> kpis2 = new ArrayList<BaseKPI>();
	List<BaseKPI> kpis3 = new ArrayList<BaseKPI>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.layout_corner);
		initView();
		wdu = new WaitingDialogUtil(showdialog, KPIReportFormActivity.this);
		initData();

	}

	private void initView() {

		LayoutInflater inflater = getLayoutInflater();
		ll_visitTask = (ViewGroup) inflater.inflate(R.layout.layout_kpi_main,
				null);
		top = ll_visitTask.findViewById(R.id.top);

		top.findViewById(R.id.home_back).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						XPPApplication.exit(KPIReportFormActivity.this);

					}
				});

		imgGroup = (ViewGroup) ll_visitTask.findViewById(R.id.viewGroup);

		pageViews = new ArrayList<View>();
		view1 = inflater.inflate(R.layout.item_kpi1, null);
		view2 = inflater.inflate(R.layout.item_kpi2, null);
		view3 = inflater.inflate(R.layout.item_kpi3, null);
		pageViews.add(view1);
		pageViews.add(view2);
		pageViews.add(view3);
		
		userType = DataProviderFactory.getUserType();
		//userType = "TYPE_JL";
		if ("TYPE_JL".equals(userType)) {
			Button b = (Button) view1.findViewById(R.id.btn_jxs_ck);
			b.setVisibility(View.GONE);
			Button b1 = (Button) view2.findViewById(R.id.btn_jxs_fx);
			b1.setVisibility(View.GONE);
		}
		
		// imgGroup = (ViewGroup) ll_visitTask.findViewById(R.id.viewGroup);
		imageViews = new ImageView[pageViews.size()];
		viewPager = (ViewPager) ll_visitTask.findViewById(R.id.guidePages);
		viewPoints = (ViewGroup) ll_visitTask.findViewById(R.id.viewGroup);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// imageViews = new ImageView[pageViews.size()];
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(KPIReportFormActivity.this);
			params.leftMargin = 2;
			imageView.setLayoutParams(params);
			imageViews[i] = imageView;

			if (i == 0) {
				// 默认选中第一张图片
				imageViews[i].setBackgroundResource(R.drawable.ic_task_ok);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.ic_task_no);
			}

			viewPoints.addView(imageViews[i]);
		}
		setContentView(ll_visitTask);
		viewPager.setAdapter(new GuidePageAdapter());
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());

		// ll = (LinearLayout) findViewById(R.id.ll);

	}

	public void initData() {
		Bundle bun = getIntent().getExtras();
		if (bun != null) {
			menu = (Menu) bun.get("menu");
			TextView tv_title = (TextView) top.findViewById(R.id.title);
			tv_title.setText(menu.getMenuDesc());
		}
		new KPITask().execute();

	}

	// 指引页面数据适配器
	class GuidePageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		// @Override
		// public void destroyItem(View arg0, int arg1, Object arg2) {
		// // TODO Auto-generated method stub
		// ((ViewPager) arg0).removeView(pageViews.get(arg1));
		// }

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(pageViews.get(position));

		}

		// @Override
		// public Object instantiateItem(View arg0, int arg1) {
		// // TODO Auto-generated method stub
		// ((ViewPager) arg0).addView(pageViews.get(arg1));
		// return pageViews.get(arg1);
		// }

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pageViews.get(position));
			
			
			List<BaseKPI> kpis = new ArrayList<BaseKPI>();
			BaseKPI kpi = new BaseKPI();
			switch (position) {
			case 0:
				
				kpiList = (ListView) view1.findViewById(R.id.list_kpi);
				btn_jxs_ck = (Button) view1.findViewById(R.id.btn_jxs_ck);
				btn_jxs_ck.setOnClickListener(BtnClicked);
				jxsKpiList = (ListView) view1.findViewById(R.id.jxs_list_kpi);
				jxs_ck_ll = (LinearLayout) view1.findViewById(R.id.jxs_ll);
				
			/*	kpi = new BaseKPI();
				kpi.setAreaComp("目标(标箱)");
				kpi.setAreaDest("完成(标箱)");
				kpi.setAreaRate("达成率");
				kpis.add(kpi);
			
				for(BaseKPI k :DataProviderFactory.getProvider().getCKKPI()){
					kpis.add(k);
				}*/
				
		
				adapter1 = new KpiListAdapter(KPIReportFormActivity.this, kpis1);
				kpiList.setAdapter(adapter1);
				
				adapter10 = new JxsKpiListAdapter(KPIReportFormActivity.this,
						kpis10);
				jxsKpiList.setAdapter(adapter10);
				break;
			case 1:
				//
				kpiList = (ListView) view2.findViewById(R.id.list_kpi);
				btn_jxs_fx = (Button) view2.findViewById(R.id.btn_jxs_fx);
				btn_jxs_fx.setOnClickListener(BtnClicked);
				jxsKpiList = (ListView) view2.findViewById(R.id.jxs_list_kpi);
				jxs_fx_ll = (LinearLayout) view2.findViewById(R.id.jxs_ll);
				
				adapter2 = new KpiListAdapter(KPIReportFormActivity.this, kpis2);
				kpiList.setAdapter(adapter2);
				adapter20 = new JxsKpiListAdapter(KPIReportFormActivity.this,kpis20);
				jxsKpiList.setAdapter(adapter20);
				
				break;
			case 2:
				vistcustList=(ListView) view3.findViewById(R.id.list_vistcust);
				kpiList = (ListView) view3.findViewById(R.id.list_kpi);
				btn_jxs_fx = (Button) view2.findViewById(R.id.btn_jxs_fx);
				jxsKpiList = (ListView) view3.findViewById(R.id.jxs_list_kpi);
				jxs_fx_ll = (LinearLayout) view2.findViewById(R.id.jxs_ll);
				btn_vistcust = (Button) view3.findViewById(R.id.btn_vistcust);	
				vistcust_ll = (LinearLayout) view3.findViewById(R.id.vistcust_ll);
				btn_vistcust.setOnClickListener(BtnClicked);
				adapter3 = new KpiListAdapter(KPIReportFormActivity.this, kpis3);
				kpiList.setAdapter(adapter3);		
				adapter30 = new VistCustListAdapter(KPIReportFormActivity.this,kpis30);
				vistcustList.setAdapter(adapter30);

				break;
			default:
				break;
			}
			return pageViews.get(position);
			
		}

	}

	private OnClickListener BtnClicked = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_jxs_ck:
				new SearchShopTask().execute();
				// btn_jxs_ck.setText(text);
				// jxs_ll.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_jxs_fx:
				new SearchShopTask().execute();
				break;
			case R.id.btn_vistcust:
				new SearchVistCust().execute();
				break;
			default:
				break;
			}
		}

	};

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

	/*************** 查询客户，添加到kpi列表 *******/
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
			return Customer.findKunnrCustomer();
		}
		protected void onPostExecute(List<Customer> result) {
			dismissWaitingDialog();
			if (result != null && result.size() != 0) {
				overdialog = null;
				View overdiaView = View.inflate(KPIReportFormActivity.this,
						R.layout.dialog_search_shop_msg, null);
				overdialog = new Dialog(KPIReportFormActivity.this,
						R.style.dialog_xw);
				sh_title = (TextView) overdiaView.findViewById(R.id.Title);
				sh_title.setText("经销商列表");
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
				Toast.makeText(getApplicationContext(), "查询无此经销商记录！",
						Toast.LENGTH_SHORT).show();
				return;
			}

		}

	}
	
	private class SearchVistCust extends
	AsyncTask<String, Integer, List<VistCust>> {
		BaseKPI kpi;
@Override
protected void onProgressUpdate(Integer... values) {
	if (isCancelled())
		return;
}
protected void onPreExecute() {
	showWaitingDialog();
}
protected List<VistCust> doInBackground(String... arg0) {
	return DataProviderFactory.getProvider().getVistCust();
}
protected void onPostExecute(List<VistCust> result) {
	dismissWaitingDialog();
	if (result != null && result.size() != 0) {
//	System.out.println(result);
		kpi = new BaseKPI();
		kpi.setAreaComp("门店数量（家）");
		kpi.setAreaDest("拜访日期");
		kpi.setAreaRate("100");
		kpis30.clear();
		kpis30.add(kpi);
		for(VistCust k :result){
			BaseKPI k1=new BaseKPI();
			k1.setAreaComp(k.getCountnum());
			k1.setAreaDest(k.getVistday());
//			Double target=Double.parseDouble(k.getCountnum())/15;
//			k1.setAreaRate(""+(int)(target*100));
			kpis30.add(k1);
		}	
//		adapter30 = new JxsKpiListAdapter(KPIReportFormActivity.this,kpis30);
//		vistcustList.setAdapter(adapter30);
		adapter30.notifyDataSetChanged();
		vistcust_ll.setVisibility(View.VISIBLE);
		return;
	} else {
		Toast.makeText(getApplicationContext(), "查询无拜访记录！",
				Toast.LENGTH_SHORT).show();
		return;
	}

}

}

	// 添加门店信息
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
		public Customer getItem(int position) {
			return data.get(position);
		}
		public long getItemId(int position) {
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;
			if (convertView == null) {
				// 获取组件布局
				hodler = new ViewHodler();
				convertView = layoutInflater.inflate(
						R.layout.dialog_search_shop_list_child, null);
				hodler.custName = (TextView) convertView
						.findViewById(R.id.custName);
				hodler.address = (TextView) convertView
						.findViewById(R.id.address);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				resetViewHolder(hodler);
			}

			hodler.custName.setText("经销商编号:" + data.get(position).getCustId());
			hodler.address
					.setText("经销商名称:" + data.get(position).getCustName() == null ? "无地址"
							: data.get(position).getCustName());

			// 绑定数据、以及事件触发
			final int n = position;
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					switch (currViewIndex) {
					case 0:
						btn_jxs_ck.setText(getItem(n).getCustName());						
						new KPICKTask().execute(getItem(n).getCustId());						
						jxs_ck_ll.setVisibility(View.VISIBLE);
						break;
					case 1:
						btn_jxs_fx.setText(getItem(n).getCustName());
						new KPIFXTask().execute(getItem(n).getCustId());
						jxs_fx_ll.setVisibility(View.VISIBLE);
						break;
					case 2:
						btn_jxs_fx.setText(getItem(n).getCustName());
						jxs_fx_ll.setVisibility(View.VISIBLE);
						break;						
					default:
						break;
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

	protected class ViewHodler {
		TextView custName = null;
		TextView address = null;
	}

	protected void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.custName.setText(null);
		pViewHolder.address.setText(null);

	}

	private class KpiListAdapter extends BaseAdapter {
		private List<BaseKPI> list;
		private Context context;
		private LayoutInflater inflater;

		public KpiListAdapter(Context context, List<BaseKPI> list) {
			this.context = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public BaseKPI getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.child_item_kpi, null);
				holder = new ViewHolder();
				holder.tvArea = (TextView) convertView.findViewById(R.id.area);
				holder.tvDest = (TextView) convertView.findViewById(R.id.dest);
				holder.tvComp = (TextView) convertView.findViewById(R.id.complet);
				holder.tvRate = (TextView) convertView.findViewById(R.id.rate);
				holder.bar = (ProgressBarUtil) convertView
						.findViewById(R.id.pgsBar);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();

			}
			BaseKPI kpi = getItem(position);
			holder.tvArea.setText(kpi.getArea());
			holder.tvDest.setText(kpi.getAreaDest());
			holder.tvComp.setText(kpi.getAreaComp());
			if (position == 0) {

				holder.tvRate.setVisibility(View.VISIBLE);
				holder.bar.setVisibility(View.GONE);
				holder.tvRate.setText("达成率");
			} else {
				holder.tvRate.setVisibility(View.GONE);
				holder.bar.setVisibility(View.VISIBLE);
				int rate = 0;
				try{
					rate=(int)Double.parseDouble(kpi.getAreaRate());
					holder.bar.setProgress(rate);
					holder.bar.setText(kpi.getAreaRate());
				}
				catch(Exception e) {
					e.printStackTrace();
					holder.bar.setProgress(0);
				}		
				
			}
			//holder.bar.setProgress(Integer.parseInt(kpi.getAreaRate()));
			return convertView;
		}

		private class ViewHolder {
			TextView tvArea, tvDest, tvComp, tvRate;
			ProgressBarUtil bar;

		}

	}

	private class JxsKpiListAdapter extends BaseAdapter {
		private List<BaseKPI> list;
		private Context context;
		private LayoutInflater inflater;

		public JxsKpiListAdapter(Context context, List<BaseKPI> list) {
			this.context = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public BaseKPI getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.child_item_jxs_kpi,
						null);
				holder = new ViewHolder();
				holder.tvDest = (TextView) convertView.findViewById(R.id.dest);
				holder.tvComp = (TextView) convertView.findViewById(R.id.complet);
				holder.tvRate = (TextView) convertView.findViewById(R.id.rate);
				holder.bar = (ProgressBarUtil) convertView
						.findViewById(R.id.pgsBar);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();

			}
			BaseKPI kpi = getItem(position);
			holder.tvDest.setText(kpi.getAreaDest());
			holder.tvComp.setText(kpi.getAreaComp());
			if (position == 0) {

				holder.tvRate.setVisibility(View.VISIBLE);
				holder.bar.setVisibility(View.GONE);
				holder.tvRate.setText("达成率");
			} else {
				holder.tvRate.setVisibility(View.GONE);
				holder.bar.setVisibility(View.VISIBLE);
				try{
					int rate=(int)Double.parseDouble(kpi.getAreaRate());				
					holder.bar.setProgress(rate);
					holder.bar.setText(kpi.getAreaRate());
				}
				catch(Exception e) {
					e.printStackTrace();
					holder.bar.setProgress(0);
				}
				//holder.bar.setProgress(Integer.parseInt(kpi.getAreaRate()));
			}

			return convertView;
		}

		private class ViewHolder {
			TextView tvDest, tvComp, tvRate;
			ProgressBarUtil bar;

		}

	}

	private class VistCustListAdapter extends BaseAdapter {
		private List<BaseKPI> list;
		private Context context;
		private LayoutInflater inflater;

		public VistCustListAdapter(Context context, List<BaseKPI> list) {
			this.context = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public BaseKPI getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.child_item_vistcust,
						null);
				holder = new ViewHolder();
				holder.tvDest = (TextView) convertView.findViewById(R.id.dest);
				holder.tvComp = (TextView) convertView.findViewById(R.id.complet);
//				holder.tvRate = (TextView) convertView.findViewById(R.id.rate);
//				holder.bar = (ProgressBarUtil) convertView
//						.findViewById(R.id.pgsBar);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();

			}
			BaseKPI kpi = getItem(position);
			holder.tvDest.setText(kpi.getAreaDest());
			holder.tvComp.setText(kpi.getAreaComp());
//			if (position == 0) {

//				holder.tvRate.setVisibility(View.VISIBLE);
//				holder.bar.setVisibility(View.GONE);
//				holder.tvRate.setText("达成率");
//			} else {
//				holder.tvRate.setVisibility(View.GONE);
//				holder.bar.setVisibility(View.VISIBLE);
//				try{
//					int rate=(int)Double.parseDouble(kpi.getAreaRate());				
//					holder.bar.setProgress(rate);
//					holder.bar.setText(kpi.getAreaRate());
//				}
//				catch(Exception e) {
//					e.printStackTrace();
//					holder.bar.setProgress(0);
//				}
				//holder.bar.setProgress(Integer.parseInt(kpi.getAreaRate()));
//			}

			return convertView;
		}

		private class ViewHolder {
			TextView tvDest, tvComp, tvRate;
			ProgressBarUtil bar;

		}

	}
	// 指引页面更改事件监听器
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			currViewIndex=arg0;
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0].setBackgroundResource(R.drawable.ic_task_ok);
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(R.drawable.ic_task_no);
				}
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(KPIReportFormActivity.this);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private class KPITask extends AsyncTask<String, Integer, Integer> {
		BaseKPI kpi;
		List<BaseKPI> kpis1x = new ArrayList<BaseKPI>();
		List<BaseKPI> kpis2x = new ArrayList<BaseKPI>();
		List<BaseKPI> kpis3x = new ArrayList<BaseKPI>();
		@Override
		protected void onPreExecute() {
			showWaitingDialog();
		}
		protected Integer doInBackground(String... arg0) {
			List<Basekpis> list = new ArrayList<Basekpis>();
			List<BaseKPI> listv = new ArrayList<BaseKPI>();
//			List<VistCust> listvistCust = new ArrayList<VistCust>();
			list = DataProviderFactory.getProvider().getKPI();			
			listv = DataProviderFactory.getProvider().getKPIVisit();	
//			listvistCust = DataProviderFactory.getProvider().getVistCust();
			kpi = new BaseKPI();
			kpi.setAreaComp("实际(标箱)");
			kpi.setAreaDest("目标(标箱)");
			kpi.setAreaRate("10");
			kpis1x.add(kpi);
			kpis2x.add(kpi);
			kpi = new BaseKPI();
			kpi.setAreaComp("实际(家)");
			kpi.setAreaDest("目标(家)");
			kpi.setAreaRate("10");
			kpis3x.add(kpi);
			if (list==null) {
				return 0;
			}
			if(list.size() == 0){				
				return 0;
			}
			
			for(Basekpis k :list){
				kpi = new BaseKPI();
				kpi.setArea(k.getOrgName());
				kpi.setAreaComp(k.getFx_actual());
				kpi.setAreaDest(k.getFx_target());
				kpi.setAreaRate(k.getFx_rate());
				kpis2x.add(kpi);
				kpi = new BaseKPI();
				kpi.setArea(k.getOrgName());
				kpi.setAreaComp(k.getCk_actual());
				kpi.setAreaDest(k.getCk_target());
				kpi.setAreaRate(k.getCk_rate());				
				kpis1x.add(kpi);
			}
			
			
			for(BaseKPI k :listv){
				kpis3x.add(k);
			}		
			
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {			
			dismissWaitingDialog();
			if(result == 0){				
				Toast.makeText(getApplicationContext(),
						"获取数据失败：请检查网络", Toast.LENGTH_SHORT).show();
			}
			for(BaseKPI k :kpis1x){
				kpis1.add(k);
			}
			for(BaseKPI k :kpis2x){
				kpis2.add(k);
			}
			for(BaseKPI k :kpis3x){
				kpis3.add(k);
			}
			adapter1.notifyDataSetChanged();
			adapter2.notifyDataSetChanged();
		//	adapter3.notifyDataSetChanged();
			
		}
	}
	
	private class KPICKTask extends AsyncTask<String, Integer, Integer> {
		BaseKPI kpi;
		List<BaseKPI> kpis10x = new ArrayList<BaseKPI>();
		@Override
		protected void onPreExecute() {
			showWaitingDialog();
		}

		protected Integer doInBackground(String... arg0) {
			List<BaseKPI> list = new ArrayList<BaseKPI>();
			kpi = new BaseKPI();
			kpi.setAreaComp("实际(标箱)");
			kpi.setAreaDest("目标(标箱)");
			kpi.setAreaRate("10");
			kpis10x.add(kpi);
			list = DataProviderFactory.getProvider().getJXSCKKPI(arg0[0]);
			if (list==null) {
				return 0;
			}
			if(list.size() == 0){				
				return 0;
			}
			for(BaseKPI k :list){
				kpis10x.add(k);
			}			
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {			
			dismissWaitingDialog();
			if(result == 0){				
				Toast.makeText(getApplicationContext(),
						"获取数据失败：请检查网络", Toast.LENGTH_SHORT).show();
			}
			kpis10.clear();
			for(BaseKPI k :kpis10x){
				kpis10.add(k);
			}
			adapter10.notifyDataSetChanged();
			
		}
	}
	
	private class KPIFXTask extends AsyncTask<String, Integer, Integer> {
		BaseKPI kpi;
		List<BaseKPI> kpis20x = new ArrayList<BaseKPI>();
		@Override
		protected void onPreExecute() {
			showWaitingDialog();
		}

		protected Integer doInBackground(String... arg0) {
			List<BaseKPI> list = new ArrayList<BaseKPI>();
			kpi = new BaseKPI();
			kpi.setAreaComp("实际(标箱)");
			kpi.setAreaDest("目标(标箱)");
			kpi.setAreaRate("100");
			kpis20x.add(kpi);
			list  = DataProviderFactory.getProvider().getJXSFXKPI(arg0[0]);
			if (list==null) {
				return 0;
			}
			if(list.size() == 0){				
				return 0;
			}
			for(BaseKPI k :list){
				kpis20x.add(k);
			}			
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {			
			dismissWaitingDialog();
			if(result == 0){				
				Toast.makeText(getApplicationContext(),
						"获取数据失败：请检查网络", Toast.LENGTH_SHORT).show();
			}
			kpis20.clear();
			for(BaseKPI k :kpis20x){
				kpis20.add(k);
			}
			adapter20.notifyDataSetChanged();
			
		}
	}

}


