package com.xpp.moblie.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Toast;

public class VersionUpdate {
	public Activity activity = null;
	private static final int UPDATA_CLIENT = 1;
	private static final int GET_UNDATAINFO_ERROR = 2;
	private static final int CANCEL_UPDATE = 3;

	private static final int DOWN_ERROR = 3;
	public   String strURL ;

	public VersionUpdate(Activity activity,String strURL) {
		this.activity = activity;
		this.strURL= strURL;
		// this.activity.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		// downLoadApk();
		showUpdataDialog();
	}

	public boolean isUpdate() {
		return true;
	}

	/*
	 * 
	 * �����Ի���֪ͨ�û����³���
	 */
	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new Builder(activity);
		builer.setTitle("�汾����");
		builer.setMessage("��⵽�°汾,������");
		// ����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ
		builer.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				downLoadApk();
			}
		});
		builer.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
//				showUpdataDialog();
			}

		});
		// ����ȡ����ťʱ���е�¼
		builer.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog dialog = builer.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/*
	 * �ӷ�����������APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // �������Ի���
		pd = new ProgressDialog(activity);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("�������ظ���");
		pd.setCanceledOnTouchOutside(false);
		pd.show();

		pd.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				showUpdataDialog();
			}

		});
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(strURL, pd); // TODO ����url
					sleep(3000);
					installApk(file);
					pd.dismiss(); // �������������Ի���
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}.start();

	}

	//
	public File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		// �����ȵĻ���ʾ��ǰ��sdcard�������ֻ��ϲ����ǿ��õ�
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// ��ȡ���ļ��Ĵ�С
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			String downloadPath = Environment.getExternalStorageDirectory()
					+ "/XPP/download/";
			File dir = new File(downloadPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(downloadPath, "XPPMobileTerminal.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// ��ȡ��ǰ������
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	// ��װapk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// ִ�ж���
		intent.setAction(Intent.ACTION_VIEW);
		// ִ�е���������
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		activity.startActivity(intent);
	}

	/*
	 * ��������������
	 */
	// private void LoginMain(){
	// Intent intent = new Intent(activity,LoginActivity.class);
	// activity.startActivity(intent);
	// //��������ǰ��activity
	// activity.finish();
	// }

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case UPDATA_CLIENT:
				// �Ի���֪ͨ�û���������
				showUpdataDialog();
				break;
			case GET_UNDATAINFO_ERROR:
				// ��������ʱ
				Toast.makeText(activity, "��ȡ������������Ϣʧ��", 1).show();
				// LoginMain();
				break;
			// case DOWN_ERROR:
			// //����apkʧ��
			// Toast.makeText(activity, "�����°汾ʧ��", 1).show();
			// // LoginMain();
			// break;
			case CANCEL_UPDATE:

				break;
			}
		}
	};

}
