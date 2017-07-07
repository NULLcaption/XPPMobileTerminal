package com.xpp.moblie.util;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;

/**   
 * @Title: MyDatePickerDialog.java 
 * @Package com.xpp.moblie.util 
 * @Description: TODO
 * @author will.xu 
 * @date 2014年5月13日 下午5:32:42 
 */

public class MyDatePickerDialog extends DatePickerDialog {

	public MyDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
	}

	
	
	@Override
	protected void onStop() {
		// super.onStop();
	}
}
