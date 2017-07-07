package com.xpp.moblie.util;

import android.content.Context;
import android.telephony.TelephonyManager;
/**   
 * @Title: ReadPhoneStateUtil.java 
 * @Package com.xpp.moblie.util 
 * @Description: ��ȡ�ֻ���Ϣsim�� imei��
 * @author will.xu 
 * @date 2014��4��28�� ����10:53:27 
 */

public class ReadPhoneStateUtil {
	private  TelephonyManager mTelephonyMgr ;
	public ReadPhoneStateUtil (Context context){
		 mTelephonyMgr = (TelephonyManager)context. getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	/**��ȡimsi*/
	public String  getIMSI(){
		try {
			return mTelephonyMgr.getSimSerialNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**��ȡimei*/
	public String  getIMEI(){
		try {
			return mTelephonyMgr.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**��ȡ�ֻ���*/
	public String  getPhoneNumber(){
		try {
			return mTelephonyMgr.getLine1Number();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public  String getHandSetInfo(){
		try {
			String handSetInfo= 
					"�ֻ��ͺ�:" + android.os.Build.MODEL + 
					",SDK�汾:" + android.os.Build.VERSION.SDK + 
					",ϵͳ�汾:" + android.os.Build.VERSION.RELEASE; 
					return handSetInfo; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	
		} 
	
}
