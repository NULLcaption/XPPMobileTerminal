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
		
		
			
					smsString="�ŵ����ƣ�"+order.getCustName() + "\n";
					smsString=smsString+"��ַ��"+order.getAddress() + "\n";
					smsString=smsString+"�绰��"+order.getContractPhone() + "\n";
					String remark = "��������:" + (order.getRemark()==null?"":order.getRemark())+ "\n";
					smsString= smsString+remark;
					for (com.xpp.moblie.print.OrderDetail detail : order.getDetails()) {
						
						smsString=smsString+"\n";
						String skuName = detail.getSku();
						double qty = detail.getQuantity();
						double price = detail.getPrice();
						String column1 = detail.getOrderTypeTxt() + skuName + "\n";
						if (0 < price) {
							String column2 = MessageFormat.format(
									"{0}" + detail.getUnitDesc() + " �� {1}"
											+ "Ԫ" + "/"
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
					
						if (null != detail.getChildSkus()) {// ��Ʒ����Ʒ
							if (detail.getChildSkus().size() > 0) {
								for (com.xpp.moblie.print.OrderDetail d : detail.getChildSkus()) {
									String txt = "��:" + d.getSku();
									smsString=smsString+txt+"\n";
									String txt_num = d.getQuantity() + " "
											+ d.getUnitDesc();
									smsString=smsString+txt_num+"\n";
								
								}
							}
						}
						String total = "С�ƣ�" + String.valueOf(detail.getTotalPrice()) + "Ԫ";
						smsString=smsString+total;
						smsString=smsString+"\n"+"--------------";
					}

//					String end = "�ϼ�������" + "(��)" + order.getTotalQuantity() + "��" + "  (��)"
//							+ order.getTotalZQuantity() + "��\n";
					String end="",strb=order.getOrdertypeb(),stra=order.getOrdertypea();
//					System.out.println(str.lastIndexOf('\n')+":"+(str.length()-1));

//					if ( (!"".equals(strb)&&(strb.lastIndexOf('\n')==(strb.length()-1)))||("".equals(strb)&&stra.lastIndexOf('\n')==(stra.length()-1))) {
//						end= "�ϼ�������\n" + order.getOrdertypea()+ order.getOrdertypeb();
//						
//					}
//					else {
//						end= "�ϼ�������\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
//					}
					end= "\n�ϼ�������\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
					smsString=smsString+end;
					String totalPrice = "�ϼƽ�" + String.valueOf(order.getTotalPrice())
							+ "Ԫ";
					smsString=smsString+totalPrice;
		
			
	
		
		
		
		
	

		return smsString;
		
	}
	
	
	private static String printNormalOrder(com.xpp.moblie.print.Order order) {
		// TODO Auto-generated method stub
		String smsString="";
//		 smsString= smsString+"\n";


		String khname = "�ͻ�����:" + order.getCustName();
		
		smsString= smsString+khname;
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(order.getOrderDate()) + "\n";
		String dateStr1 = "\n�µ����ڣ�" + dateStr;
		smsString= smsString+dateStr1;


		String skuNo = "���ţ�" + order.getOrderId();
		smsString= smsString+skuNo;
		
		String user = "\n�µ��ˣ�" + order.getOrderUser();
		smsString= smsString+user;
		
		String operator = "\n��Ʊ��λ��" + order.getOperator() + "\n";
		smsString= smsString+operator;
		
		String tabHead = "�µ��˹�����룺" + order.getCustPhone() + "\n";
		smsString= smsString+tabHead;
		
		String status1 = "����״̬:" + order.getOrderStatus();
		smsString= smsString+status1;

		String status2 = "\n��������:" + order.getOrderFundStatus();
		smsString= smsString+status2;
		String remark = "\n��������:" + (order.getRemark()==null?"":order.getRemark());
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
						"{0}" + detail.getUnitDesc() + " �� {1}"
								+ "Ԫ" + "/"
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
		
			if (null != detail.getChildSkus()) {// ��Ʒ����Ʒ
				if (detail.getChildSkus().size() > 0) {
					for (com.xpp.moblie.print.OrderDetail d : detail.getChildSkus()) {
						String txt = "��:" + d.getSku();
						smsString=smsString+txt+"\n";
						String txt_num = d.getQuantity() + " "
								+ d.getUnitDesc();
						smsString=smsString+txt_num+"\n";
					
					}
				}
			}
			String total = "С�ƣ�" + String.valueOf(detail.getTotalPrice()) + "Ԫ";
			smsString=smsString+total;
			smsString=smsString+"\n"+"--------------";
		}

//		String end = "�ϼ�������" + "(��)" + order.getTotalQuantity() + "��" + "  (��)"
//				+ order.getTotalZQuantity() + "��\n";
		String end="",strb=order.getOrdertypeb(),stra=order.getOrdertypea();
//		System.out.println(str.lastIndexOf('\n')+":"+(str.length()-1));

//		if ( (!"".equals(strb)&&(strb.lastIndexOf('\n')==(strb.length()-1)))||("".equals(strb)&&stra.lastIndexOf('\n')==(stra.length()-1))) {
//			end= "�ϼ�������\n" + order.getOrdertypea()+ order.getOrdertypeb();
//			
//		}
//		else {
//			end= "�ϼ�������\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
//		}
		end= "\n�ϼ�������\n" + order.getOrdertypea()+ order.getOrdertypeb()+"\n";
		smsString=smsString+end;
		String totalPrice = "�ϼƽ�" + String.valueOf(order.getTotalPrice())
				+ "Ԫ";
		smsString=smsString+totalPrice;
//		smsString=smsString+"\n"+"--------------";
//		smsString=smsString+"\n";
		return smsString;
	}	
}
