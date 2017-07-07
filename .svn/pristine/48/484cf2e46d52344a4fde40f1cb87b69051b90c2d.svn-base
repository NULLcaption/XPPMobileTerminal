package com.xpp.moblie.util;

import android.content.Context;
import android.telephony.TelephonyManager;
/**   
 * @Title: ReadPhoneStateUtil.java 
 * @Package com.xpp.moblie.util 
 * @Description: 获取手机信息sim卡 imei等
 * @author will.xu 
 * @date 2014年4月28日 上午10:53:27 
 */

public class ReadPhoneStateUtil {
	private  TelephonyManager mTelephonyMgr ;
	public ReadPhoneStateUtil (Context context){
		 mTelephonyMgr = (TelephonyManager)context. getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	/**获取imsi*/
	public String  getIMSI(){
		try {
			return mTelephonyMgr.getSimSerialNumber();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**获取imei*/
	public String  getIMEI(){
		try {
			return mTelephonyMgr.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**获取手机号*/
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
					"手机型号:" + android.os.Build.MODEL + 
					",SDK版本:" + android.os.Build.VERSION.SDK + 
					",系统版本:" + android.os.Build.VERSION.RELEASE; 
					return handSetInfo; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	
		} 
	
}
