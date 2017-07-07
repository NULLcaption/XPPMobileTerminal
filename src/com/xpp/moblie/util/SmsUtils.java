package com.xpp.moblie.util;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpp.moblie.query.OrderPrintFormat;

public class SmsUtils {

	public static String smsOrder(com.xpp.moblie.print.Order order, List<OrderPrintFormat> formats) {
		String smsString="";
		if (null != formats && formats.size() > 0) {
		for (OrderPrintFormat format : formats) {
			if ("T".equals(format.getType())) {// 主题
				if ("title".equals(format.getPropertiesCode())) {// 主题-门店名称
					smsString=order.getCustName() + "\n";
					
				}
			}
			if ("H".equals(format.getType())) {// 抬头
				if ("header_custName"
						.equals(format.getPropertiesCode())) {// 门店名称
					String khname = "\n" + format.getPropertiesTxt()
							+ ":" + order.getCustName();
					smsString=smsString+khname;
				}
				if ("header_orderDate".equals(format
						.getPropertiesCode())) {// 下单日期
					String dateStr = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(order
							.getOrderDate());
					String dateStr1 = "\n" + format.getPropertiesTxt()
							+ "：" + dateStr;
					smsString=smsString+dateStr1;
					
				}
				if ("header_orderId".equals(format.getPropertiesCode())) {// 订单编号
					String skuNo = "\n" + format.getPropertiesTxt()
							+ ":" + order.getOrderId();
					smsString=smsString+skuNo;
			
				}
				if ("header_orderUser".equals(format
						.getPropertiesCode())) {// 下单人
					String user = "\n" + format.getPropertiesTxt()
							+ "：" + order.getOrderUser();
					smsString=smsString+user;
					
				}
				if ("header_orderUnit".equals(format
						.getPropertiesCode())) {// 开票单位
					String operator = "\n" + format.getPropertiesTxt()
							+ "：" + order.getOperator();
					smsString=smsString+operator;
				
				}
				if ("header_orderPhone".equals(format
						.getPropertiesCode())) {// 下单人公务号
					String tabHead = "\n" + format.getPropertiesTxt()
							+ "：" + order.getCustPhone();
					smsString=smsString+tabHead;
				
				}
				if ("header_orderStatus".equals(format
						.getPropertiesCode())) {// 订单状态
					String status1 = "\n" + format.getPropertiesTxt()
							+ "：" + order.getOrderStatus();
					smsString=smsString+status1;
				
				}
				if ("header_orderPayment".equals(format
						.getPropertiesCode())) {// 款项状态
					String status2 = "\n" + format.getPropertiesTxt()
							+ "：" + order.getOrderFundStatus();
					smsString=smsString+status2;
					;
				}
				if ("header_orderMemo".equals(format
						.getPropertiesCode())) {// 订单备注
					String remark = "\n" + format.getPropertiesTxt()
							+ "：" + (order.getRemark().trim()==null?"":order.getRemark());
					smsString=smsString+remark;
					;
				}
			}
		}// 抬头结束
		Map<String, List<com.xpp.moblie.print.OrderDetail>> map = new HashMap<String, List<com.xpp.moblie.print.OrderDetail>>();
		for (int i = 0; i < order.getDetails().size(); i++) {
			com.xpp.moblie.print.OrderDetail detail = order.getDetails().get(i);
			if (i == 0) {
				smsString=smsString+"\n"+"--------------";
				// 画虚线的
			}
			String skuName = detail.getSku();
			double qty = detail.getQuantity();
			double price = detail.getPrice();
			String column1 = "";
			String column2 = "";
			String qty_value = "";
			String price_value = "";
			String total = "";
			for (OrderPrintFormat format : formats) {
				if ("L".equals(format.getType())) {
					if ("list_skuName".equals(format
							.getPropertiesCode())) {// sku名称
						column1 = "\n" + detail.getOrderTypeTxt()
								+ skuName;
					}
					if ("list_quantity".equals(format
							.getPropertiesCode())) {// 数量
						qty_value = qty + detail.getUnitDesc();
					}
					if ("list_skuPrice".equals(format
							.getPropertiesCode()) && 0 != price) {// 单价
						price_value = price + "元";
					}
					if ("list_countPrice".equals(format
							.getPropertiesCode())) {// sku金额合计
						total = "\n小计："
								+ String.valueOf(detail.getTotalPrice())
								+ "元";
					}
				}
			}
			if (!"".equals(column1)) {
				smsString=	smsString+column1;
				
			}
			if (!"".equals(qty_value) && !"".equals(price_value)) {
				column2 = "\n" + qty_value + " × " + price_value + "/"
						+ detail.getUnitDesc();
			} else if ("".equals(qty_value) && !"".equals(price_value)) {
				column2 = "\n" + price_value;
			} else if (!"".equals(qty_value) && "".equals(price_value)) {
				column2 = "\n" + qty_value;
			}
			smsString=	smsString+column2;
			
			if (null != detail.getChildSkus()) {// 本品搭赠品
				if (detail.getChildSkus().size() > 0) {
					if (null == map.get(detail.getSkuId())) {
						map.put(detail.getSkuId(),
								detail.getChildSkus());
						for (com.xpp.moblie.print.OrderDetail d : detail.getChildSkus()) {
							
							smsString=	smsString+"\n";
							String txt = "赠:" + d.getSku();
							smsString=	smsString+txt;
							
							String txt_num = "\n" + d.getQuantity()
									+ " " + d.getUnitDesc();
							smsString=	smsString+txt_num;
							
						}
					}
				}
			}
			if (!"".equals(total)) {
				smsString=	smsString+total;
				
			}
			smsString=smsString+"\n"+"--------------";
		
		}
		for (OrderPrintFormat format : formats) {
			// 合计
			if ("B".equals(format.getType())) {
				if ("bottom_countQuantity".equals(format
						.getPropertiesCode())) {// 订单sku数量合计
//					String end = format.getPropertiesTxt() + "："
//							+ "(本)" + order.getTotalQuantity() + "箱"
//							+ "  (赠)" + order.getTotalZQuantity()
//							+ "箱\n";
					String end="",strb=order.getOrdertypeb(),stra=order.getOrdertypea();
//					System.out.println(str.lastIndexOf('\n')+":"+(str.length()-1));
				
//					if ( (!"".equals(strb)&&(strb.lastIndexOf('\n')==(strb.length()-1)))||("".equals(strb)&&stra.lastIndexOf('\n')==(stra.length()-1))) {
//						end= format.getPropertiesTxt() + "：\n" + order.getOrdertypea()+ order.getOrdertypeb();
//						
//					}
//					else {
//						end= format.getPropertiesTxt() + "：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
//					}
			end= "\n"+format.getPropertiesTxt() + "：\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
					smsString=smsString+end;
					
				}
				if ("bottom_countPrice".equals(format
						.getPropertiesCode())) {// 订单金额合计
					String totalPrice = format.getPropertiesTxt() + "："
							+ String.valueOf(order.getTotalPrice())
							+ "元\n";
					smsString=smsString+totalPrice;
					
				}
			}
			if ("F".equals(format.getType())) {
				if ("foot".equals(format.getPropertiesCode())) {// 页脚自定义备注
					if (null != format.getMemo()
							&& !"".equals(format.getMemo())) {
						String memo = "\n"+format.getMemo() + "\n";
						smsString=smsString+memo;
					
					}
				}
			}
		}
//		smsString=smsString+"\n"+"--------------";
//		smsString=smsString+"\n";
		
	} else {
		
		// 如没有自定义打印格式则使用默认格式
		smsString=printNormalOrder(order);
	}

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
