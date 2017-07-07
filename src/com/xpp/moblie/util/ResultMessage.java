package com.xpp.moblie.util;

import java.util.Map;

/**   
 * @Title: resultMessage.java 
 * @Package com.xpp.moblie.util 
 * @Description: 服务器返回值
 * @author will.xu 
 * @date 2014年2月21日 下午2:15:18 
 */
public class ResultMessage {
	private String resultCode;
	private String resultDesc;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	

}
