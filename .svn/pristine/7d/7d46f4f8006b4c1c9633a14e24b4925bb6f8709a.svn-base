package com.xpp.moblie.adapter.order;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xpp.moblie.query.UserInfo;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.print.XPPBluetoothPrinter;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Order;
import com.xpp.moblie.query.OrderDetail;
import com.xpp.moblie.query.OrderPrintFormat;
import com.xpp.moblie.screens.OrderActivity;
import com.xpp.moblie.screens.R;
import com.xpp.moblie.util.Constants;
import com.xpp.moblie.util.DataHandleUtil;
import com.xpp.moblie.util.SmsUtils;
import com.xpp.moblie.util.TimeUtil;
import com.xpp.moblie.util.WxSmsUtils;

/**   
 * @Title: OrderTotalAdapter.java 
 * @Package com.xpp.moblie.adapter.order 
 * @Description: TODO
 * @author will.xu 
 * @date 2014年3月4日 下午2:28:45 
 */

public class OrderTotalAdapter extends BaseAdapter {
	public List<Order> list = new ArrayList<Order>();
	private LayoutInflater layoutInflater;
	// private Context mContext;
	private Activity activity;
	private Customer customer;
	private IWXAPI wxApi;  
	private String wxsms;
	//实例化  
	
	
	public OrderTotalAdapter(List<Order> list ,  Activity activity,Customer customer){
		this.list= list;
		this.activity = activity;
		this.layoutInflater = LayoutInflater.from(activity);
		this.customer = customer;

	}
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHodler hodler = null;
		if (convertView == null) {
			// 获取组件布局
			hodler = new ViewHodler();
			convertView = layoutInflater
					.inflate(R.layout.child_order_total, null);
			hodler.tv_orderId=(TextView) convertView.findViewById(R.id.orderId);
			hodler.tv_status=(TextView) convertView.findViewById(R.id.status);
			hodler.tv_orderDesc=(TextView) convertView.findViewById(R.id.orderDesc);
			hodler.tv_total=(TextView) convertView.findViewById(R.id.total);
			hodler.tv_quantity=(TextView) convertView.findViewById(R.id.quantity);
			hodler.tv_fundsStatus = (TextView) convertView.findViewById(R.id.fundsStatus);
			hodler.imgs=(ImageView) convertView.findViewById(R.id.lanyadayin);//蓝牙打印
			hodler.btn_sms=(Button) convertView.findViewById(R.id.bt_sms);//短信发送
			hodler.btn_wx=(Button) convertView.findViewById(R.id.bt_wx);//微信发送
			convertView.setTag(hodler);
		} else {
			hodler = (ViewHodler) convertView.getTag();
			resetViewHolder(hodler);
		}
				
//		hodler.tv_orderId.setText(list.get(position).getOrderId()!=null?
//				list.get(position).getOrderId():activity.getString(R.string.order_submit_ing));
		hodler.tv_orderId.setText(list.get(position).getOrderId()!=null?
				list.get(position).getOrderId():"CACHE".equals(list.get(position).getStatus().toString())?activity.getString(R.string.order_chche):activity.getString(R.string.order_submit_ing));
	hodler.tv_status.setText("Y".equals(list.get(position).getOrderStatus())?
			activity.getString(R.string.order_sended):activity.getString(R.string.order_no_send));//TODO
	
	hodler.tv_fundsStatus.setText("Y".equals(list.get(position).getOrderFundStatus())?
			activity.getString(R.string.order_funds_y):activity.getString(R.string.order_funds_n));//TODO
	
	hodler.tv_orderDesc.setText(list.get(position).getOrderDesc());
	hodler.tv_total.setText(DataHandleUtil.dealNull(list.get(position).getTotalPrice())+"元");
	hodler.tv_quantity.setText(DataHandleUtil.dealNull(list.get(position).getOrderQuntity()));
		final int n = position;
	convertView.setOnClickListener(new OnClickListener() {
		public void onClick(View arg0) {
			Intent	i = new Intent(activity, OrderActivity.class);
			i.putExtra("order", list.get(n));
			i.putExtra("custInfo", customer);
			activity.startActivity(i);
			activity.overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
		}
	});
	//微信发送
	 hodler.btn_wx.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			com.xpp.moblie.print.Order order1 = new com.xpp.moblie.print.Order();
			int counta=0,countb=0;
			Map<String, Double> pUnitMap = new HashMap<String, Double>();
			Map<String, Double> unitMap = new HashMap<String, Double>();
			  String orderId=list.get(position).getOrderId();
				order1.setCustName(customer.getCustName());//名称
				order1.setAddress(customer.getAddress());
				String phone=customer.getContractPhone();
				String mobile=customer.getContractMobile();
				if (phone!=null&& mobile!=null) {
					phone=phone+"/"+mobile;
				}else if (phone==null&& mobile!=null) {
					phone=mobile;
				}else if (phone==null&& mobile==null){
					phone="";
				}
				order1.setContractPhone(phone);
				order1.setOrderId(orderId);//单号
				order1.setOperator(customer.getKunnrName());//开票单位
				order1.setOrderUser(UserInfo.findByLoginName(
						DataProviderFactory.getLoginName())!=null?UserInfo.findByLoginName(
								DataProviderFactory.getLoginName()).getUserName():"");//下单人
				order1.setOrderDate(new Date());//日期
				order1.setCustPhone(UserInfo.findByLoginName(
						DataProviderFactory.getLoginName())!=null?UserInfo.findByLoginName(
								DataProviderFactory.getLoginName()).getMobile():"");//电话
				order1.setOrderStatus("Y".equals(list.get(position).getOrderStatus())?
				activity.getString(R.string.order_sended):activity.getString(R.string.order_no_send));//订单状态
				order1.setOrderFundStatus("Y".equals(list.get(position).getOrderFundStatus())?
			    activity.getString(R.string.order_funds_y):activity.getString(R.string.order_funds_n));//订单款项
				order1.setRemark(list.get(position).getOrderDesc());  //订单描述
				order1.setDetails(new ArrayList<com.xpp.moblie.print.OrderDetail>());
				order1.setOrdertypea("");
				order1.setOrdertypeb("");
				List<com.xpp.moblie.query.OrderDetail> details=com.xpp.moblie.query.OrderDetail.
				findByCustId(customer.getCustId(), DataProviderFactory.getUserId(), orderId);
				List<com.xpp.moblie.print.OrderDetail> detail_01s=new ArrayList<com.xpp.moblie.print.OrderDetail>();
				for (OrderDetail orderDetail : details) {
				if ("A".equals(orderDetail.getOrderType())
						|| ("B".equals(orderDetail.getOrderType()) && ""
								.equals(orderDetail.getMappingSKUId()))) {
					com.xpp.moblie.print.OrderDetail d = new com.xpp.moblie.print. OrderDetail();
					d.setSkuId(orderDetail.getSkuId());
					d.setSku(orderDetail.getCategoryDesc());
					d.setPrice(orderDetail.getPrice());//价格
					d.setQuantity(orderDetail.getQuantity());//数量
					d.setTotalPrice(orderDetail.getTotalPrice());//小计金额
					d.setOrderType(orderDetail.getOrderType());
					d.setOrderTypeTxt(orderDetail.getOrderType().equals("A")?"(本品)":"(赠品)");
					if (orderDetail.getOrderType().equals("A")) {
						if (orderDetail.getQuantity() != 0) {
							if (unitMap.get(orderDetail.getUnitDesc()) != null) {
								unitMap.put(
										orderDetail.getUnitDesc(),
										unitMap.get(orderDetail.getUnitDesc())
												+ orderDetail.getQuantity());
							} else {
								unitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
							}	
							}
					
					
						}else if (orderDetail.getOrderType().equals("B")) {
						if (pUnitMap.get(orderDetail.getUnitDesc()) != null) {
							pUnitMap.put(
									orderDetail.getUnitDesc(),
									pUnitMap.get(orderDetail.getUnitDesc())
											+ orderDetail.getQuantity());
						} else {
							pUnitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
						}
						
					}
					d.setUnitCode(orderDetail.getUnitCode());
					d.setUnitDesc(orderDetail.getUnitDesc());
					d.setPriceUnitDesc(orderDetail.getPriceUnitDesc());
					d.setChildSkus(new ArrayList<com.xpp.moblie.print.OrderDetail>());
					detail_01s.add(d);
				}
			}
				
			for (com.xpp.moblie.print.OrderDetail zd : detail_01s) {
				if (!"B".equals(zd.getOrderType())) {//本品搭赠
					for (OrderDetail orderDetail : details) {
						if (orderDetail.getMappingSKUId().equals(
								zd.getSkuId() + "&" + zd.getUnitCode())
								&& !"".equals(orderDetail.getMappingSKUId())) {
							com.xpp.moblie.print.OrderDetail d = new com.xpp.moblie.print. OrderDetail();
							d.setSkuId(orderDetail.getSkuId());
							d.setSku(orderDetail.getCategoryDesc());
							d.setPrice(orderDetail.getPrice());//价格
							d.setQuantity(orderDetail.getQuantity());//数量
							d.setTotalPrice(orderDetail.getTotalPrice());//小计金额
							d.setOrderType(orderDetail.getOrderType());
							d.setOrderTypeTxt(orderDetail.getOrderType().equals("A")?"(本品)":"(赠品)");
							d.setUnitCode(orderDetail.getUnitCode());
							d.setUnitDesc(orderDetail.getUnitDesc());
							d.setPriceUnitDesc(orderDetail.getPriceUnitDesc());
							if (pUnitMap.get(orderDetail.getUnitDesc()) != null) {
								pUnitMap.put(
										orderDetail.getUnitDesc(),
										pUnitMap.get(orderDetail.getUnitDesc())
												+ orderDetail.getQuantity());
							} else {
								pUnitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
							}
							zd.getChildSkus().add(d);
						}
					}
				}
			}
			order1.setDetails(detail_01s);
			
			String str = "";
			for (Entry<String, Double> entry : pUnitMap.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				str = str+"(赠)"+value + key+"  ";
				countb++;
				if (countb%2==0)
					str=str+"\n";
				
			}
			order1.setOrdertypeb(str);
		
			str = "";
			for (Entry<String, Double> entry : unitMap.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				str = str+"(本)"+value + key+"  ";
				counta++;
				if (counta%2==0)
					str=str+"\n";
			}
			order1.setOrdertypea(str);
//			settings = activity.getSharedPreferences(DataProviderFactory.PREFS_NAME,
//					Context.MODE_PRIVATE);
//			SharedPreferences.Editor editor = settings.edit();
//			editor.putString(DataProviderFactory.PREFS_WXRETURN , "OrderTotalActivity");
//			editor.putString(DataProviderFactory.PREFS_CUSTID ,customer.getCustId());
//			editor.commit();
				//获取自定义格式
		String smsString=WxSmsUtils.smsOrder(order1);
		commit(smsString);
//		if (wxsms==null||"".equals(wxsms)) {
//			wxsms=	smsString;
//		}
			//分享到微信好友  
//			wechatShare(0);//分享到微信朋友圈  
			
		}
		});
	 //发送短信
	  hodler.btn_sms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
					com.xpp.moblie.print.Order order1 = new com.xpp.moblie.print.Order();
					int counta=0,countb=0;
					Map<String, Double> pUnitMap = new HashMap<String, Double>();
					Map<String, Double> unitMap = new HashMap<String, Double>();
					  String orderId=list.get(position).getOrderId();
						order1.setCustName(customer.getCustName());//名称
						order1.setOrderId(orderId);//单号
						order1.setOperator(customer.getKunnrName());//开票单位
						order1.setOrderUser(UserInfo.findByLoginName(
								DataProviderFactory.getLoginName())!=null?UserInfo.findByLoginName(
										DataProviderFactory.getLoginName()).getUserName():"");//下单人
						order1.setOrderDate(new Date());//日期
						order1.setCustPhone(UserInfo.findByLoginName(
								DataProviderFactory.getLoginName())!=null?UserInfo.findByLoginName(
										DataProviderFactory.getLoginName()).getMobile():"");//电话
						order1.setOrderStatus("Y".equals(list.get(position).getOrderStatus())?
						activity.getString(R.string.order_sended):activity.getString(R.string.order_no_send));//订单状态
						order1.setOrderFundStatus("Y".equals(list.get(position).getOrderFundStatus())?
					    activity.getString(R.string.order_funds_y):activity.getString(R.string.order_funds_n));//订单款项
						order1.setRemark(list.get(position).getOrderDesc());  //订单描述
						order1.setDetails(new ArrayList<com.xpp.moblie.print.OrderDetail>());
						order1.setOrdertypea("");
						order1.setOrdertypeb("");
						List<com.xpp.moblie.query.OrderDetail> details=com.xpp.moblie.query.OrderDetail.
						findByCustId(customer.getCustId(), DataProviderFactory.getUserId(), orderId);
						List<com.xpp.moblie.print.OrderDetail> detail_01s=new ArrayList<com.xpp.moblie.print.OrderDetail>();
						for (OrderDetail orderDetail : details) {
						if ("A".equals(orderDetail.getOrderType())
								|| ("B".equals(orderDetail.getOrderType()) && ""
										.equals(orderDetail.getMappingSKUId()))) {
							com.xpp.moblie.print.OrderDetail d = new com.xpp.moblie.print. OrderDetail();
							d.setSkuId(orderDetail.getSkuId());
							d.setSku(orderDetail.getCategoryDesc());
							d.setPrice(orderDetail.getPrice());//价格
							d.setQuantity(orderDetail.getQuantity());//数量
							d.setTotalPrice(orderDetail.getTotalPrice());//小计金额
							d.setOrderType(orderDetail.getOrderType());
							d.setOrderTypeTxt(orderDetail.getOrderType().equals("A")?"(本品)":"(赠品)");
							if (orderDetail.getOrderType().equals("A")) {
								if (orderDetail.getQuantity() != 0) {
									if (unitMap.get(orderDetail.getUnitDesc()) != null) {
										unitMap.put(
												orderDetail.getUnitDesc(),
												unitMap.get(orderDetail.getUnitDesc())
														+ orderDetail.getQuantity());
									} else {
										unitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
									}	
									}
							
							
								}else if (orderDetail.getOrderType().equals("B")) {
								if (pUnitMap.get(orderDetail.getUnitDesc()) != null) {
									pUnitMap.put(
											orderDetail.getUnitDesc(),
											pUnitMap.get(orderDetail.getUnitDesc())
													+ orderDetail.getQuantity());
								} else {
									pUnitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
								}
								
							}
							d.setUnitCode(orderDetail.getUnitCode());
							d.setUnitDesc(orderDetail.getUnitDesc());
							d.setPriceUnitDesc(orderDetail.getPriceUnitDesc());
							d.setChildSkus(new ArrayList<com.xpp.moblie.print.OrderDetail>());
							detail_01s.add(d);
						}
					}
						
					for (com.xpp.moblie.print.OrderDetail zd : detail_01s) {
						if (!"B".equals(zd.getOrderType())) {//本品搭赠
							for (OrderDetail orderDetail : details) {
								if (orderDetail.getMappingSKUId().equals(
										zd.getSkuId() + "&" + zd.getUnitCode())
										&& !"".equals(orderDetail.getMappingSKUId())) {
									com.xpp.moblie.print.OrderDetail d = new com.xpp.moblie.print. OrderDetail();
									d.setSkuId(orderDetail.getSkuId());
									d.setSku(orderDetail.getCategoryDesc());
									d.setPrice(orderDetail.getPrice());//价格
									d.setQuantity(orderDetail.getQuantity());//数量
									d.setTotalPrice(orderDetail.getTotalPrice());//小计金额
									d.setOrderType(orderDetail.getOrderType());
									d.setOrderTypeTxt(orderDetail.getOrderType().equals("A")?"(本品)":"(赠品)");
									d.setUnitCode(orderDetail.getUnitCode());
									d.setUnitDesc(orderDetail.getUnitDesc());
									d.setPriceUnitDesc(orderDetail.getPriceUnitDesc());
									if (pUnitMap.get(orderDetail.getUnitDesc()) != null) {
										pUnitMap.put(
												orderDetail.getUnitDesc(),
												pUnitMap.get(orderDetail.getUnitDesc())
														+ orderDetail.getQuantity());
									} else {
										pUnitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
									}
									zd.getChildSkus().add(d);
								}
							}
						}
					}
					order1.setDetails(detail_01s);
					
					String str = "";
					for (Entry<String, Double> entry : pUnitMap.entrySet()) {
						String key = entry.getKey().toString();
						String value = entry.getValue().toString();
						str = str+"(赠)"+value + key+"  ";
						countb++;
						if (countb%2==0)
							str=str+"\n";
						
					}
					order1.setOrdertypeb(str);
				
					str = "";
					for (Entry<String, Double> entry : unitMap.entrySet()) {
						String key = entry.getKey().toString();
						String value = entry.getValue().toString();
						str = str+"(本)"+value + key+"  ";
						counta++;
						if (counta%2==0)
							str=str+"\n";
					}
					order1.setOrdertypea(str);
						//获取自定义格式
				List<OrderPrintFormat> formats=OrderPrintFormat.findAll();
				String smsString=SmsUtils.smsOrder(order1,formats);
				Uri smsToUri = Uri.parse("smsto:");
				Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
				intent.putExtra("sms_body", smsString);
				
				activity.startActivity(intent);
				
			}
		});
	
	//蓝牙打印功能
	hodler.imgs.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
						try {
							com.xpp.moblie.print.Order order1 = new com.xpp.moblie.print.Order();
							int counta=0,countb=0;
							Map<String, Double> pUnitMap = new HashMap<String, Double>();
							Map<String, Double> unitMap = new HashMap<String, Double>();
							  String orderId=list.get(position).getOrderId();
								order1.setCustName(customer.getCustName());//名称
								order1.setOrderId(orderId);//单号
								order1.setOperator(customer.getKunnrName());//开票单位
								order1.setOrderUser(UserInfo.findByLoginName(
										DataProviderFactory.getLoginName())!=null?UserInfo.findByLoginName(
												DataProviderFactory.getLoginName()).getUserName():"");//开票人
								order1.setOrderDate(new Date());//日期
								order1.setCustPhone(UserInfo.findByLoginName(
										DataProviderFactory.getLoginName())!=null?UserInfo.findByLoginName(
												DataProviderFactory.getLoginName()).getMobile():"");//公务电话
								order1.setOrderStatus("Y".equals(list.get(position).getOrderStatus())?
								activity.getString(R.string.order_sended):activity.getString(R.string.order_no_send));//订单状态
								order1.setOrderFundStatus("Y".equals(list.get(position).getOrderFundStatus())?
							    activity.getString(R.string.order_funds_y):activity.getString(R.string.order_funds_n));//订单款项
								order1.setRemark(list.get(position).getOrderDesc());   //订单描述
								order1.setDetails(new ArrayList<com.xpp.moblie.print.OrderDetail>());
								order1.setOrdertypea("");
								order1.setOrdertypeb("");
							  
								List<com.xpp.moblie.query.OrderDetail> details=com.xpp.moblie.query.OrderDetail.
								findByCustId(customer.getCustId(), DataProviderFactory.getUserId(), orderId);
								List<com.xpp.moblie.print.OrderDetail> detail_01s=new ArrayList<com.xpp.moblie.print.OrderDetail>();
								for (OrderDetail orderDetail : details) {
								if ("A".equals(orderDetail.getOrderType())
										|| ("B".equals(orderDetail.getOrderType()) && ""
												.equals(orderDetail.getMappingSKUId()))) {
									com.xpp.moblie.print.OrderDetail d = new com.xpp.moblie.print.OrderDetail();
									d.setSkuId(orderDetail.getSkuId());
									d.setSku(orderDetail.getCategoryDesc());
									d.setPrice(orderDetail.getPrice());//价格
									d.setQuantity(orderDetail.getQuantity());//数量
									d.setTotalPrice(orderDetail.getTotalPrice());//小计金额
									d.setOrderType(orderDetail.getOrderType());
									d.setOrderTypeTxt(orderDetail.getOrderType().equals("A")?"(本品)":"(赠品)");
									if (orderDetail.getOrderType().equals("A")) {
										if (orderDetail.getQuantity() != 0) {
											if (unitMap.get(orderDetail.getUnitDesc()) != null) {
												unitMap.put(
														orderDetail.getUnitDesc(),
														unitMap.get(orderDetail.getUnitDesc())
																+ orderDetail.getQuantity());
											} else {
												unitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
											}	
											}
									
									
										}else if (orderDetail.getOrderType().equals("B")) {
										if (pUnitMap.get(orderDetail.getUnitDesc()) != null) {
											pUnitMap.put(
													orderDetail.getUnitDesc(),
													pUnitMap.get(orderDetail.getUnitDesc())
															+ orderDetail.getQuantity());
										} else {
											pUnitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
										}
										
									}
									
									
									d.setUnitCode(orderDetail.getUnitCode());
									d.setUnitDesc(orderDetail.getUnitDesc());
									d.setPriceUnitDesc(orderDetail.getPriceUnitDesc());
									d.setChildSkus(new ArrayList<com.xpp.moblie.print.OrderDetail>());
									detail_01s.add(d);
								}
							}
							for (com.xpp.moblie.print.OrderDetail zd : detail_01s) {
								if (!"B".equals(zd.getOrderType())) {//本品搭赠
									for (OrderDetail orderDetail : details) {
										if (orderDetail.getMappingSKUId().equals(
												zd.getSkuId() + "&" + zd.getUnitCode())
												&& !"".equals(orderDetail.getMappingSKUId())) {
											com.xpp.moblie.print.OrderDetail d = new com.xpp.moblie.print.OrderDetail();
											d.setSkuId(orderDetail.getSkuId());
											d.setSku(orderDetail.getCategoryDesc());
											d.setPrice(orderDetail.getPrice());//价格
											d.setQuantity(orderDetail.getQuantity());//数量
											d.setTotalPrice(orderDetail.getTotalPrice());//小计金额
											d.setOrderType(orderDetail.getOrderType());
											d.setOrderTypeTxt(orderDetail.getOrderType().equals("A")?"(本品)":"(赠品)");
											d.setUnitCode(orderDetail.getUnitCode());
											d.setUnitDesc(orderDetail.getUnitDesc());
											d.setPriceUnitDesc(orderDetail.getPriceUnitDesc());
											if (pUnitMap.get(orderDetail.getUnitDesc()) != null) {
												pUnitMap.put(
														orderDetail.getUnitDesc(),
														pUnitMap.get(orderDetail.getUnitDesc())
																+ orderDetail.getQuantity());
											} else {
												pUnitMap.put(orderDetail.getUnitDesc(), orderDetail.getQuantity());
											}
											zd.getChildSkus().add(d);
										}
									}
								}
							}
							order1.setDetails(detail_01s);
							
							String str = "";
							for (Entry<String, Double> entry : pUnitMap.entrySet()) {
								String key = entry.getKey().toString();
								String value = entry.getValue().toString();
								str = str+"(赠)"+value + key+"  ";
								countb++;
								if (countb%2==0)
									str=str+"\n";
								
							}
							order1.setOrdertypeb(str);
						
							str = "";
							for (Entry<String, Double> entry : unitMap.entrySet()) {
								String key = entry.getKey().toString();
								String value = entry.getValue().toString();
								str = str+"(本)"+value + key+"  ";
								counta++;
								if (counta%2==0)
									str=str+"\n";
							}
							order1.setOrdertypea(str);
							//获取自定义格式
							List<OrderPrintFormat> formats=OrderPrintFormat.findByKunnr(customer.getKunnr());
						try {
							if(!XPPBluetoothPrinter.getInstance().printOrder(order1,formats)){
								Toast.makeText(activity, "打印机连接异常!请重启打印机!", Toast.LENGTH_LONG).show();
							}
							} catch (IllegalAccessError e) {
								Toast.makeText(activity, "打印机没连上,请打开蓝牙", Toast.LENGTH_LONG).show();
								e.printStackTrace();
							} catch (IOException e) {
								Toast.makeText(activity, "打印机没连上,请打开蓝牙", Toast.LENGTH_LONG).show();
								e.printStackTrace();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
	});
	
	
	
		return convertView;
	}
	
	
	
	
	private class ViewHodler {
		TextView tv_orderId = null;
		TextView tv_status = null;
		TextView tv_orderDesc = null;
		TextView tv_quantity = null;
		TextView tv_total = null;
		TextView tv_fundsStatus =null;
		ImageView imgs=null;
		Button   btn_sms=null;
		Button   btn_wx=null;
	}

	private void resetViewHolder(ViewHodler pViewHolder) {
		pViewHolder.tv_orderId.setText(null);
		pViewHolder.tv_status.setText(null);
		pViewHolder.tv_orderDesc.setText(null);
		pViewHolder.tv_quantity.setText(null);
		pViewHolder.tv_total.setText(null);
		pViewHolder.tv_fundsStatus.setText(null);

		
	}
	private void commit(String smsString) {
		View overdiaView1 = View.inflate(activity,
				R.layout.dialog_order_wxshare, null);
		final Dialog overdialog1 = new Dialog(activity,
				R.style.dialog_xw);
		overdialog1.setContentView(overdiaView1);
		overdialog1.setCanceledOnTouchOutside(false);
		Button overcancel1 = (Button) overdiaView1
				.findViewById(R.id.dialog_cancel_btn);
//		TextView TextView02 = (TextView) overdiaView1
//				.findViewById(R.id.TextView02);
//		TextView02.setText("确认提交订单？");
	final	EditText et_orderDesc =(EditText) overdiaView1.findViewById(R.id.wxDesc);
		if(smsString!=null){
			et_orderDesc.setText(smsString);
		}
		
		overcancel1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				overdialog1.cancel();
			}
		});
		Button overok1 = (Button) overdiaView1.findViewById(R.id.dialog_ok_btn);
		overok1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				overdialog1.cancel();
				wxsms = et_orderDesc.getText().toString();
				wxApi = WXAPIFactory.createWXAPI(activity,Constants.APP_ID);  
				wxApi.registerApp(Constants.APP_ID);  
				System.out.println("分享到微信好友 :"+Constants.APP_ID);
				wechatShare(1,wxsms);
			}
			
		});
		overdialog1.show();
	}
	
	private void wechatShare(int flag,String text){  
//	    WXWebpageObject webpage = new WXWebpageObject();  
//	    webpage.webpageUrl = "";  
//	    WXMediaMessage msg = new WXMediaMessage(webpage);  
//	    msg.title = "这里填写标题";  
//	    msg.description = "这里填写内容";  
//	    //这里替换一张自己工程里的图片资源  
//	    Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo);  
//	    msg.setThumbImage(thumb);  
//	      
//	    SendMessageToWX.Req req = new SendMessageToWX.Req();  
//	    req.transaction = String.valueOf(System.currentTimeMillis());  
//	    req.message = msg;  
//	    req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;  
//	    wxApi.sendReq(req);  
	 // 初始化一个WXTextObject对象
		
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;  

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ignored";
		msg.description = text;  

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = flag==0? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(req);  
	    
	}  	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
}
