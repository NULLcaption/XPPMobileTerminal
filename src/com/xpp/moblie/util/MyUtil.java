package com.xpp.moblie.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.query.PhotoInfo;


public class MyUtil {

	@SuppressLint("SimpleDateFormat")
	private static final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * d1: pk时间today d2 :同步取到的时间
	 * @param str1 登陆获取的时间
	 * @param str2 手机中存在的时间戳
	 * @return ture 相同 不删文件夹   false时间对不上，删除
	 * */
	public boolean compareDate(String str1, String str2) {
		if (str2 == null || "null".equals(str2) || "".equals(str2)) {// 第一次登陆 today没值
			return false;
		}
//		int result = str1.compareTo(str2);
		return str1.equals(str2);
//		if (result != 0) { // 不相等
//			return true;
//		} else {
//			return false;
//		}
	}

	public String getWeekDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (i <= 0) {
			i = 7;
		}
		return String.valueOf(i);
	}
	
	
	/** 
     * 网络是否可用 
     *  
     * @param context 
     * @return 
     */  
    public static boolean isNetworkAvailable(Context context) {  
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo[] info = mgr.getAllNetworkInfo();  
        if (info != null) {  
            for (int i = 0; i < info.length; i++) {  
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
                    return true;  
                }  
            }  
        }  
        return false;  
    }
    
    /**
     * MethodsTitle: 创建图片存储的位置
     * @author: xg.chen
     * @date:2016年11月28日 下午1:36:59
     * @version 1.0
     * @param photoList
     * @param width
     * @param height
     * @return
     */
    public static Map<String, Bitmap> buildThum(List<PhotoInfo> photoList,int width ,int height) {
		Map<String, Bitmap> maps = new TreeMap<String, Bitmap>();
		List<String> paths = new ArrayList<String>();// 存路径
		for (int i = 0; i < photoList.size(); i++) {
			paths.add(DataProviderFactory.getDirName + photoList.get(i).getPhotoName() + ".jpg");
		}
		if (!paths.isEmpty()) {
			for (String path : paths) {
//				Bitmap bp =PictureShowUtils.getImageBitByPath(path,((int) (width * 0.4)) * ((int) (height * 0.2)));
//						);
//				Bitmap bp =ps.getImage(path);
				Bitmap bp =PictureShowUtils.getImageBitByPath(path,width, height);				
				if(bp!=null){
					maps.put(path, bp); // 没有得到图片。返回是NULL
				}
			}
		}
		return maps;
	}
    
    
    
    
    /**
     * MethodsTitle: 返回格式化后时间==服务器时间
     * @author: xg.chen
     * @date:2016年12月12日 上午11:53:16
     * @version 1.0
     * @param time
     * @return
     * @throws Exception
     */
    @SuppressLint("SimpleDateFormat")
	public static String getTime(String time) throws Exception{
    	if(time==null||"".equals(time)){
    		return "";
    	}
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	java.util.Date date =  sdf.parse(time);
    	SimpleDateFormat hh =new SimpleDateFormat("HH:mm:ss");
    	String str = hh.format(date) ;
    	return str;
    }
    
    @SuppressLint("SimpleDateFormat")
    public static String getDate(String time) throws Exception{
       	java.util.Date date =  f.parse(time);
       	SimpleDateFormat hh =new SimpleDateFormat("yyyyMMdd");
       	String str = hh.format(date)+"000000" ;//为了ABAP转换不报错而已
       	return str;
       }
    
    
    
    public static String addZeroForNum(String str, int strLength) {
    	  int strLen = str.length();
    	  if (strLen < strLength) {
    	   while (strLen < strLength) {
    	    StringBuffer sb = new StringBuffer();
    	    sb.append("0").append(str);//左补0
    	    str = sb.toString();
    	    strLen = str.length();
    	   }
    	  }
    	  return str;
    	 }
    
    
    
    
    
}
