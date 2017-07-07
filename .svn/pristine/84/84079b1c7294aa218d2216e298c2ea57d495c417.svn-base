package com.xpp.moblie.util;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.query.OrderPrintFormat;

public class WxSmsUtils {

	public static String smsOrder(com.xpp.moblie.print.Order order) {
		String smsString="";
		
		
			
					smsString="门店名称："+order.getCustName() + "\n";
					smsString=smsString+"地址："+order.getAddress() + "\n";
					smsString=smsString+"电话："+order.getContractPhone() + "\n";
					String remark = "订单描述:" + (order.getRemark()==null?"":order.getRemark())+ "\n";
					smsString= smsString+remark;
					for (com.xpp.moblie.print.OrderDetail detail : order.getDetails()) {
						
						smsString=smsString+"\n";
						String skuName = detail.getSku();
						double qty = detail.getQuantity();
						double price = detail.getPrice();
						String column1 = detail.getOrderTypeTxt() + skuName + "\n";
						if (0 < price) {
							String column2 = MessageFormat.format(
									"{0}" + detail.getUnitDesc() + " × {1}"
											+ "元" + "/"
											+ detail.getUnitDesc(),
									new Object[] { Double.valueOf(qty),
											Double.valueOf(price) });
							smsString=smsString+MessageFormat.format("{0}{1}",
									new Object[] { column1, column2 });
						
						} else {
							smsString=smsString+column1;
							String column2 = qty + detail.getUnitDesc();
							smsString=smsString+column2;
						}
						smsString=smsString+"\n";
					
						if (null != detail.getChildSkus()) {// 本品搭赠品
							if (detail.getChildSkus().size() > 0) {
								for (com.xpp.moblie.print.OrderDetail d : detail.getChildSkus()) {
									String txt = "赠:" + d.getSku();
									smsString=smsString+txt+"\n";
									String txt_num = d.getQuantity() + " "
											+ d.getUnitDesc();
									smsString=smsString+txt_num+"\n";
								
								}
							}
						}
						String total = "小计：" + String.valueOf(detail.getTotalPrice()) + "元";
						smsString=smsString+total;
						smsString=smsString+"\n"+"--------------";
					}

//					String end = "合计数量：" + "(本)" + order.getTotalQuantity() + "箱" + "  (赠)"
//							+ order.getTotalZQuantity() + "箱\n";
					String end="",strb=order.getOrdertypeb(),stra=order.getOrdertypea();
//					System.out.println(str.lastIndexOf('\n')+":"+(str.length()-1));

//					if ( (!"".equals(strb)&&(strb.lastIndexOf('\n')==(strb.length()-1)))||("".equals(strb)&&stra.lastIndexOf('\n')==(stra.length()-1))) {
//						end= "合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb();
//						
//					}
//					else {
//						end= "合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
//					}
					end= "\n合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
					smsString=smsString+end;
					String totalPrice = "合计金额：" + String.valueOf(order.getTotalPrice())
							+ "元";
					smsString=smsString+totalPrice;
		
			
	
		
		
		
		
	

		return smsString;
		
	}
	
	
	private static String printNormalOrder(com.xpp.moblie.print.Order order) {
		// TODO Auto-generated method stub
		String smsString="";
//		 smsString= smsString+"\n";


		String khname = "客户名称:" + order.getCustName();
		
		smsString= smsString+khname;
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(order.getOrderDate()) + "\n";
		String dateStr1 = "\n下单日期：" + dateStr;
		smsString= smsString+dateStr1;


		String skuNo = "单号：" + order.getOrderId();
		smsString= smsString+skuNo;
		
		String user = "\n下单人：" + order.getOrderUser();
		smsString= smsString+user;
		
		String operator = "\n开票单位：" + order.getOperator() + "\n";
		smsString= smsString+operator;
		
		String tabHead = "下单人公务号码：" + order.getCustPhone() + "\n";
		smsString= smsString+tabHead;
		
		String status1 = "订单状态:" + order.getOrderStatus();
		smsString= smsString+status1;

		String status2 = "\n订单款项:" + order.getOrderFundStatus();
		smsString= smsString+status2;
		String remark = "\n订单描述:" + (order.getRemark()==null?"":order.getRemark());
		smsString= smsString+remark;
		smsString=smsString+"\n"+"--------------";


		for (com.xpp.moblie.print.OrderDetail detail : order.getDetails()) {
		
			smsString=smsString+"\n";
			String skuName = detail.getSku();
			double qty = detail.getQuantity();
			double price = detail.getPrice();
			String column1 = detail.getOrderTypeTxt() + skuName + "\n";
			if (0 < price) {
				String column2 = MessageFormat.format(
						"{0}" + detail.getUnitDesc() + " × {1}"
								+ "元" + "/"
								+ detail.getUnitDesc(),
						new Object[] { Double.valueOf(qty),
								Double.valueOf(price) });
				smsString=smsString+MessageFormat.format("{0}{1}",
						new Object[] { column1, column2 });
			
			} else {
				smsString=smsString+column1;
				String column2 = qty + detail.getUnitDesc();
				smsString=smsString+column2;
			}
			smsString=smsString+"\n";
		
			if (null != detail.getChildSkus()) {// 本品搭赠品
				if (detail.getChildSkus().size() > 0) {
					for (com.xpp.moblie.print.OrderDetail d : detail.getChildSkus()) {
						String txt = "赠:" + d.getSku();
						smsString=smsString+txt+"\n";
						String txt_num = d.getQuantity() + " "
								+ d.getUnitDesc();
						smsString=smsString+txt_num+"\n";
					
					}
				}
			}
			String total = "小计：" + String.valueOf(detail.getTotalPrice()) + "元";
			smsString=smsString+total;
			smsString=smsString+"\n"+"--------------";
		}

//		String end = "合计数量：" + "(本)" + order.getTotalQuantity() + "箱" + "  (赠)"
//				+ order.getTotalZQuantity() + "箱\n";
		String end="",strb=order.getOrdertypeb(),stra=order.getOrdertypea();
//		System.out.println(str.lastIndexOf('\n')+":"+(str.length()-1));

//		if ( (!"".equals(strb)&&(strb.lastIndexOf('\n')==(strb.length()-1)))||("".equals(strb)&&stra.lastIndexOf('\n')==(stra.length()-1))) {
//			end= "合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb();
//			
//		}
//		else {
//			end= "合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
//		}
		end= "\n合计数量：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
		smsString=smsString+end;
		String totalPrice = "合计金额：" + String.valueOf(order.getTotalPrice())
				+ "元";
		smsString=smsString+totalPrice;
//		smsString=smsString+"\n"+"--------------";
//		smsString=smsString+"\n";
		return smsString;
	}	
}
