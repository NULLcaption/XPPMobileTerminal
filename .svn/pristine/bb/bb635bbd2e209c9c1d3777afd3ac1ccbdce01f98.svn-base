package com.xpp.moblie.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.xpp.moblie.screens.R;



/**   
 * @Title: WaitingDialogUtil.java 
 * @Package com.kintiger.moblie.util 
 * @Description: TODO
 * @author will.xu 
 * @date 2014年5月4日 下午6:49:48 
 */

public class WaitingDialogUtil {
	private Dialog waitingDialog;
	private Context context;
	public WaitingDialogUtil(Dialog waitingDialog ,Context context){
		this.context = context;
		this.waitingDialog = waitingDialog;
	}
	
	
	public Dialog showWaitingDialog() {
		if (waitingDialog == null) {
			waitingDialog = new Dialog(context, R.style.TransparentDialog);
			waitingDialog.setContentView(R.layout.login_waiting_dialog);
			DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
				public void onShow(DialogInterface dialog) {
					ImageView img = (ImageView) waitingDialog
							.findViewById(R.id.loading);
					((AnimationDrawable) img.getDrawable()).start();
				}
			};
			DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					// updateButtonLook(false);
				}
			};
			waitingDialog.setOnShowListener(showListener);
			waitingDialog.setCanceledOnTouchOutside(false);
			waitingDialog.setOnCancelListener(cancelListener);
			waitingDialog.show();
		}
		return waitingDialog;
	}
	
	
	public Dialog dismissWaitingDialog() {
		if (waitingDialog != null) {
			ImageView img = (ImageView) waitingDialog
					.findViewById(R.id.loading);
			((AnimationDrawable) img.getDrawable()).stop();

			waitingDialog.dismiss();
			waitingDialog = null;
		}
		return waitingDialog;
	}

	
	
}
