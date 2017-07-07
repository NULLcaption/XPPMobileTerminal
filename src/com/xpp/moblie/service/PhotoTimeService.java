package com.xpp.moblie.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.UpdateTask;
import com.xpp.moblie.provider.WebService;
import com.xpp.moblie.util.TimeUtil;
/**
 * Title: 图片时间获取(貌似没有用到这个服务)
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年12月13日 上午9:19:37
 */
public class PhotoTimeService extends Service {
	
	@SuppressWarnings("unused")
	private static final String TAG = "PhotoTimeService";
	
	private MyBinder mBinder = new MyBinder();
	private Long timeNow = 0L;
	private final Long sleepTime = 900000L;
	private final Long addTime = 1000L;
	
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		public PhotoTimeService getService() {
			return PhotoTimeService.this;
		}
	}

	/**
	 * 
	 * 重写创建方法，使其自动更新时间。
	 * */
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			// timeNow=new Date().getTime();
			if (TimeUtil.getTIME() == null) {
				timeNow = Long.parseLong(WebService.getInstance().getTime());
				if ("".equals(timeNow)
						|| XPPApplication.UPLOAD_FAIL_CONNECT_SERVER
								.equals(timeNow)) {
					timeNow = new Date().getTime();
				}
			}
			TimeUtil.setTIME(timeNow);
			Log.d("从服务器获取时间成功", "获取成功");
		} catch (Exception e) {
			timeNow = new Date().getTime();
			TimeUtil.setTIME(timeNow);
			Log.e("从服务器获取时间失败", "获取失败,先从手机端取时间");
		} finally {
			updateTimesleep();// 一段时间后从远端服务器更新软件时间
			// addTimes();//一段时间增加软件
			addTimeNew();
		}

	}

	public void setTime(Long time) {
		timeNow = time;
	}

	public Long getTime() {

		try {
			timeNow = Long.parseLong(WebService.getInstance().getTime());
			Log.d("从服务器获取时间成功", "获取成功");
		} catch (Exception e) {
			Log.e("从服务器获取时间失败", "获取失败");
		}
		return timeNow;
	}

	/**
	 * 
	 * 使用定时器执行更新时间的操作
	 * */
	private void updateTimesleep() {
		ScheduledExecutorService scheduler = Executors
				.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(new Runner(), 500L, sleepTime,
				TimeUnit.MILLISECONDS);
	}

	/**
	 * 
	 * 更新时间方法
	 * */
	private class Runner implements Runnable {
		public void run() {
			try {
				timeNow = Long.parseLong(WebService.getInstance().getTime());
				if (timeNow != 0L && timeNow != 5L) {
					TimeUtil.setTIME(timeNow);
					String day = f.format(new Date(Long.valueOf(timeNow)));
					if (!DataProviderFactory.getDayType().equals(day)) {
						new UpdateTask(PhotoTimeService.this, true).execute();
					}
					Log.d("从服务器获取时间成功", "获取成功");
				}else{
					if(TimeUtil.getTIME()==null){
						timeNow = new Date().getTime();
					}
				}
				
			} catch (Exception e) {
				Log.e("从服务器获取时间失败", "获取失败");
			}
		}
	}

	// /**
	// *
	// * 使用定时器执行时间增加的操作
	// * */
	// private void addTimes(){
	// ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	// scheduler.scheduleWithFixedDelay(new TimeRunner(), addTime, addTime,
	// TimeUnit.MILLISECONDS);
	// }
	/**
	 * 
	 * 时间自动增加
	 * */

	// private class TimeRunner implements Runnable {
	// public void run() {
	// if(timeNow.equals(0L)||timeNow==null){
	// timeNow=new Date().getTime();
	// Log.e("自动增加时间失败", "从手机获得时间" );
	// }else{
	// timeNow=timeNow+addTime;
	// }
	// TimeUtil.setTIME(timeNow);
	// }
	// }

	/**
	 * 外部程序更新时间
	 * */
	public boolean updateNowTime() {
		try {
			timeNow = Long.parseLong(WebService.getInstance().getTime());
			TimeUtil.setTIME(timeNow);
			Log.d("从服务器获取时间成功", "获取成功");
			return true;
		} catch (Exception e) {
			Log.e("从服务器获取时间失败", "获取失败");
			return false;
		}

	}

	/**
	 * 自增时间（NEW）
	 * */
	private void addTimeNew() {
		Intent intent = new Intent("ELITOR_CLOCK");
		intent.putExtra("msg", addTime);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, addTime, addTime,
				pi);

	}

}
