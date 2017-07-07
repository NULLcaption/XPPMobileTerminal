package com.xpp.moblie.util;

import java.io.File;
import java.math.BigDecimal;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

/**
 * Title: �����湤���� Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016��12��7�� ����10:47:11
 */
public class CleanMessageUtil {
	/**
	 * MethodsTitle: �����Ӧ�õ���������
	 * 
	 * @author: xg.chen
	 * @date:2017��2��28�� ����11:37:33
	 * @version 1.0
	 * @param context
	 */
	public static void clearAllData(Context context) {
		clearAllCache(context);
		clearAllFiles(context);
		cleanDatabases(context);
	}

	/**
	 * MethodsTitle:�����Ӧ���������ݿ�(/data/data/com.xxx.xxx/databases)
	 * 
	 * @author: xg.chen
	 * @date:2017��2��28�� ����11:43:18
	 * @version 1.0
	 * @param context
	 */
	@SuppressLint("SdCardPath")
	public static void cleanDatabases(Context context) {
		deleteDir(new File("/data/data/" + context.getPackageName()
				+ "/databases"));
	}

	/**
	 * MethodsTitle: �����ʱ���滺��
	 * 
	 * @author: xg.chen
	 * @date:2016��12��7�� ����10:56:18
	 * @version 1.0
	 * @param context
	 */
	public static void clearAllCache(Context context) {
		deleteDir(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteDir(context.getExternalCacheDir());
		}
	}

	/**
	 * MethodsTitle: ɾ������
	 * 
	 * @author: xg.chen
	 * @date:2017��2��28�� ����9:08:25
	 * @version 1.0
	 * @param context
	 */
	@SuppressLint("SdCardPath")
	public static void clearAllFiles(Context context) {
		deleteDir(context.getFilesDir());
	}

	/**
	 * MethodsTitle: ��ȡɾ���ļ�λ��
	 * 
	 * @author: xg.chen
	 * @date:2016��12��7�� ����10:56:36
	 * @version 1.0
	 * @param dir
	 * @return
	 */
	private static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			int size = 0;
			if (children != null) {
				size = children.length;
				for (int i = 0; i < size; i++) {
					boolean success = deleteDir(new File(dir, children[i]));
					if (!success) {
						return false;
					}
				}
			}
		}
		if (dir == null) {
			return true;
		} else {
			return dir.delete();
		}
	}

	/**
	 * MethodsTitle: ��ȡ��ǰ����
	 * 
	 * @author: xg.chen
	 * @date:2016��12��7�� ����10:53:01
	 * @version 1.0
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getTotalCacheSize(Context context) throws Exception {
		long cacheSize = getFolderSize(context.getCacheDir());
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cacheSize += getFolderSize(context.getExternalCacheDir());
		}
		return getFormatSize(cacheSize);
	}

	/**
	 * MethodsTitle: ��ȡ�ļ� ���裺 Context.getExternalFilesDir() -->
	 * SDCard/Android/data/���Ӧ�õİ���/files/Ŀ¼��һ���һЩ��ʱ�䱣�������
	 * Context.getExternalCacheDir() -->
	 * SDCard/Android/data/���Ӧ�ð���/cache/Ŀ¼��һ������ʱ��������
	 * 
	 * @author: xg.chen
	 * @date:2016��12��7�� ����10:54:37
	 * @version 1.0
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			int size2 = 0;
			if (fileList != null) {
				size2 = fileList.length;
				for (int i = 0; i < size2; i++) {
					// ������滹���ļ�
					if (fileList[i].isDirectory()) {
						size = size + getFolderSize(fileList[i]);
					} else {
						size = size + fileList[i].length();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * MethodsTitle: ��ʽ����λ�����㻺��Ĵ�С
	 * 
	 * @author: xg.chen
	 * @date:2016��12��7�� ����10:53:24
	 * @version 1.0
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return "0K";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}
}
