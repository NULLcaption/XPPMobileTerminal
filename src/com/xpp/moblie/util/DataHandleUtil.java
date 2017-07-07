package com.xpp.moblie.util;

public class DataHandleUtil {
	public static  String dealNull(Object str){
		return str!=null?str.toString():"";
	}
	public static  Double dealDoubleNull(String dou){
		if(dou==null){
			return 0.0;
		}else{
			return	Double.valueOf(dou);
		}
	}
	
	
}
