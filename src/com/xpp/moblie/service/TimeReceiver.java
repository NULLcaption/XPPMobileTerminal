package com.xpp.moblie.service;


import com.xpp.moblie.util.TimeUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @wxu
 * �㲥�࣬ʵ��ʱ������
 * 
 * */
public class TimeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Long addtime = intent.getLongExtra("msg", 0L); 
		TimeUtil.addTime(addtime);
	}
}
