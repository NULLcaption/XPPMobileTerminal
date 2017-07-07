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
	 * d1: pkʱ��today d2 :ͬ��ȡ����ʱ��
	 * @param str1 ��½��ȡ��ʱ��
	 * @param str2 �ֻ��д��ڵ�ʱ���
	 * @return ture ��ͬ ��ɾ�ļ���   falseʱ��Բ��ϣ�ɾ��
	 * */
	public boolean compareDate(String str1, String str2) {
		if (str2 == null || "null".equals(str2) || "".equals(str2)) {// ��һ�ε�½ todayûֵ
			return false;
		}
//		int result = str1.compareTo(str2);
		return str1.equals(str2);
//		if (result != 0) { // �����
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
     * �����Ƿ���� 
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
     * MethodsTitle: ����ͼƬ�洢��λ��
     * @author: xg.chen
     * @date:2016��11��28�� ����1:36:59
     * @version 1.0
     * @param photoList
     * @param width
     * @param height
     * @return
     */
    public static Map<String, Bitmap> buildThum(List<PhotoInfo> photoList,int width ,int height) {
		Map<String, Bitmap> maps = new TreeMap<String, Bitmap>();
		List<String> paths = new ArrayList<String>();// ��·��
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
					maps.put(path, bp); // û�еõ�ͼƬ��������NULL
				}
			}
		}
		return maps;
	}
    
    
    
    
    /**
     * MethodsTitle: ���ظ�ʽ����ʱ��==������ʱ��
     * @author: xg.chen
     * @date:2016��12��12�� ����11:53:16
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
       	String str = hh.format(date)+"000000" ;//Ϊ��ABAPת�����������
       	return str;
       }
    
    
    
    public static String addZeroForNum(String str, int strLength) {
    	  int strLen = str.length();
    	  if (strLen < strLength) {
    	   while (strLen < strLength) {
    	    StringBuffer sb = new StringBuffer();
    	    sb.append("0").append(str);//��0
    	    str = sb.toString();
    	    strLen = str.length();
    	   }
    	  }
    	  return str;
    	 }
    
    
    
    
    
}
