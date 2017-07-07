package com.xpp.moblie.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * Title: Time������
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2017��3��28�� ����11:38:06
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
	private static Long TIME;

	/**
	 * ��������ĸ�ʽ�����ǰ������ʱ��
	 * 
	 * */
	public TimeUtil() {
		TIME = new Date().getTime();
	}

	public static String getFormatTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(new Date(TIME));
	}

	public static String getFormatDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(new Date(TIME));
	}
	public static long getFormatDate(long time) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		
		return getDateTime(f.format(new Date(time)));
	}
	
	public static long getDateTime(String date){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return f.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getTimeByForm(String form) {
		SimpleDateFormat f;
		if (form == null || form.equals("")) {
			f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			f = new SimpleDateFormat(form);
		}
		return f.format(new Date(TIME));
	}

	public static Long getTIME() {
		return TIME;
	}

	public static void setTIME(Long tIME) {
		TIME = tIME;
	}

	public static String getStringTime() {
//		 TIME = new Date().getTime();
		return TIME.toString();
	}

	public static void addTime(long time) {
		if (TIME == null || TIME.equals(0L)) {
			TIME = new Date().getTime();
		} else {
			TIME = TIME + time;
		}

	}

	public static String enTimetoCn(String date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy��MM��dd��");  
        SimpleDateFormat sdf2 = new  SimpleDateFormat ("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        try {  
            String dateCH = sdf1.format(sdf2.parse(date.trim()));  
            return dateCH;  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }
        return "";
	}
}
