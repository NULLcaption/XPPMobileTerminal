package com.xpp.moblie.screens;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseDivision;
import com.xpp.moblie.provider.DataProviderFactory;
import com.xpp.moblie.provider.UpdateTask;
import com.xpp.moblie.query.Channel;
import com.xpp.moblie.query.Customer;
import com.xpp.moblie.query.Sign;
import com.xpp.moblie.query.UserInfo;
import com.xpp.moblie.screens.MyLocationActivity.allCustomerTask;
import com.xpp.moblie.util.ToastUtil;

import android.R.anim;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Title: 签到考勤界面
 * Description: XPPMobileTerminal
 * @author: xg.chen
 * @date:2016年12月1日 上午11:14:09
 */
public class SignActivity extends Activity {

	private Button btn_sign_in, btn_sign_out;// btn_searchAreaAddress,

	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.layout_sign);
		super.onCreate(savedInstanceState);
		initView();
		initData();
		
	}

	
	private void initView() {
		btn_sign_in= (Button) findViewById(R.id.btn_sign_in); 
		btn_sign_out= (Button) findViewById(R.id.btn_sign_out);
		findViewById(R.id.home_back).setOnClickListener(BtnClicked);
		findViewById(R.id.btn_sign_in).setOnClickListener(BtnClicked);
		findViewById(R.id.btn_sign_out).setOnClickListener(BtnClicked);
		
	}
	
	private void initData() {
		

	}

	private OnClickListener BtnClicked = new OnClickListener() {
		public void onClick(View v) {
			Intent i0 = new Intent();
			switch (v.getId()) {
			case R.id.home_back:
				XPPApplication.exit(SignActivity.this);
				break;
			case R.id.btn_sign_in:
				System.out.println("btn_sign_in");
				i0 = new Intent(SignActivity.this, SignInActivity.class);
				startActivity(i0);
				break;
			case R.id.btn_sign_out:
				System.out.println("tn_sign_out:");
				i0 = new Intent(SignActivity.this, SignOutActivity.class);
				startActivity(i0);
				break;
		
		
			
			}
		}

	};

	// 取消手机返回按钮功能
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			XPPApplication.exit(SignActivity.this);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}





}
