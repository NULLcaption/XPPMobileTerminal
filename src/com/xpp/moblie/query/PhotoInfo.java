package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.xpp.moblie.application.XPPApplication.PhotoType;
import com.xpp.moblie.application.XPPApplication.Status;
import com.xpp.moblie.entity.BasePhotoInfo;
import com.xpp.moblie.provider.DataProviderFactory;

/**
 * Title: ��Ƭ��Ϣ������ Description: XPPMobileTerminal
 * 
 * @author: xg.chen
 * @date:2016��12��12�� ����2:34:19
 */
public class PhotoInfo extends BasePhotoInfo {
	private static final long serialVersionUID = 4438987701048629672L;

	public static PhotoInfo getByPhotoName(String photo) {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryForEq("photoName", photo);
			if (result.size() != 0) {
				return result.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	/**
	 * MethodsTitle: ��ȡ��Ƭ����   String photo
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:50:28
	 * @version 1.0
	 * @param photo
	 * @return
	 */
	public String getPhotoName(String photo) {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryForEq("photoName", photo);
			if (result != null && result.size() != 0) {
				PhotoInfo info = result.get(0);
				if (Status.NEW.equals(info.getStatus())) {
					PhotoInfo.submitPhoto(info);// ��Ϊ���ϴ�״̬
				}
				String s = info.getEmplid() + "_" + info.getCustid() + "_"
						+ info.getRouteid() + "_" + info.getActid() + "_"
						+ info.getTimestamp() + "_" + info.getPtype() + "_"
						+ info.getSeq();
				return s;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	/**
	 * MethodsTitle: ��ȡ��Ƭ����  photoInfo
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:48:55
	 * @version 1.0
	 * @param info
	 * @return
	 */
	public String getPhotoName(PhotoInfo info) {
		if (Status.NEW.equals(info.getStatus())) {
			PhotoInfo.submitPhoto(info);// ��Ϊ���ϴ�״̬
		}
		String s = info.getEmplid() + "_" + info.getCustid() + "_"
				+ info.getRouteid() + "_" + info.getActid() + "_"
				+ info.getTimestamp() + "_" + info.getPtype() + "_"
				+ info.getSeq();
		return s;
	}

	public static boolean save(PhotoInfo pt) {
		try {
			OrmHelper.getInstance().getPhotoInfoDao().createOrUpdate(pt);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteAll(List<PhotoInfo> list) {
		try {
			OrmHelper.getInstance().getPhotoInfoDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// public List<PhotoInfo> findAll() {
	// try {
	// List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
	// .queryBuilder().where()
	// .eq("dayType", DataProviderFactory.getDayType()).and()
	// .eq("emplid", DataProviderFactory.getUserId()).query();
	// return result;
	// } catch (SQLException e) {
	// return null;
	// }
	/**
	 * MethodsTitle: �ύ������Ƭ
	 * @author: xg.chen
	 * @date:2017��2��24�� ����1:35:37
	 * @version 1.0
	 * @param photoInfo
	 * @return
	 */
	public static boolean submitPhoto(PhotoInfo photoInfo) {
		boolean flag = false;
		if (photoInfo.getStatus().equals(Status.NEW)) {
			photoInfo.setStatus(Status.UNSYNCHRONOUS);
			photoInfo.update();
			flag = true;
		}
		return flag;
	}
	/**
	 * MethodsTitle: �������е���Ƭ�б�
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:47:28
	 * @version 1.0
	 * @return
	 */
	public static List<PhotoInfo> findAll() {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryBuilder().query();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MethodsTitle: ����Ƭ չʾ  custId 
	 * @author: xg.chen 
	 * @date:2017��3��28�� ����10:45:37
	 * @version 1.0
	 * @param custId
	 * @param ptype
	 * @return
	 */
	public static List<PhotoInfo> findByShop(String custId, PhotoType ptype) {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryBuilder().where().eq("custid", custId).and()
					.eq("ptype", ptype).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("emplid", DataProviderFactory.getUserId()).query();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MethodsTitle: ����Ƭ չʾ  empId
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:45:01
	 * @version 1.0
	 * @param ptype
	 * @return
	 */
	public static List<PhotoInfo> findByEmplid(PhotoType ptype) {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryBuilder().where().eq("ptype", ptype).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("emplid", DataProviderFactory.getUserId()).query();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MethodsTitle: ��ѯ�Ƿ��м�¼
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:44:17
	 * @version 1.0
	 * @param custId
	 * @param ptype
	 * @return
	 */
	public static int getRecordsCount(String custId, PhotoType ptype) {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryBuilder().where().eq("custid", custId).and()
					.eq("ptype", ptype).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("emplid", DataProviderFactory.getUserId()).and()
					.ne("status", Status.NEW).query();
			return result.size();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * MethodsTitle: ����Ƭ״̬
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:44:00
	 * @version 1.0
	 * @param custId
	 * @param ptype
	 * @return
	 */
	public static int findByShop(String custId, List<PhotoType> ptype) {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryBuilder().where().eq("custid", custId).and()
					.in("ptype", ptype).and().eq("status", Status.NEW).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("emplid", DataProviderFactory.getUserId()).query();

			for (PhotoInfo photoInfo : result) {
				photoInfo.setStatus(Status.UNSYNCHRONOUS);
				photoInfo.update();
			}
			return result.size();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static List<PhotoInfo> findAllPhotoByCustId(String custId) {
		try {
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryBuilder().where().eq("custid", custId).and()
					.eq("dayType", DataProviderFactory.getDayType()).and()
					.eq("emplid", DataProviderFactory.getUserId()).query();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//
	// // ����Ƭ ����״̬ ���ϴ�
	// public List<PhotoInfo> findByNew(String custId, String ptype) {
	// try {
	// List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
	// .queryBuilder().where().eq("custid", custId).and()
	// .eq("ptype", ptype).and()
	// .eq("dayType", DataProviderFactory.getDayType()).and()
	// .eq("status", Status.NEW).query();
	// return result;
	// } catch (SQLException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	public static List<PhotoInfo> findInId(String photoName) {
		try {
			List<String> list = new ArrayList<String>();
			if (photoName != null) {
				String[] str = photoName.split(",");
				list = Arrays.asList(str);
			}
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.queryBuilder().where().in("photoName", list).query();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<PhotoInfo> synchronousPhoto(String custId) {
		try {
			// List<PhotoInfo> result =
			// OrmHelper.getInstance().getPhotoInfoDao()
			// .queryBuilder().where().eq("status", Status.UNSYNCHRONOUS)
			// .and().eq("dayType", DataProviderFactory.getDayType())
			// .query();

			QueryBuilder<PhotoInfo, Integer> qb = OrmHelper.getInstance()
					.getPhotoInfoDao().queryBuilder();
			// qb.where().eq("status", Status.UNSYNCHRONOUS).and()
			// .eq("dayType", DataProviderFactory.getDayType()).and()
			// .eq("emplid", DataProviderFactory.getUserId());
			if (custId != null) {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("dayType", DataProviderFactory.getDayType()).and()
						.eq("emplid", DataProviderFactory.getUserId()).and()
						.eq("custid", custId);
			} else {
				qb.where().eq("status", Status.UNSYNCHRONOUS).and()
						.eq("dayType", DataProviderFactory.getDayType()).and()
						.eq("emplid", DataProviderFactory.getUserId());
			}
			List<PhotoInfo> result = OrmHelper.getInstance().getPhotoInfoDao()
					.query(qb.prepare());
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * MethodsTitle: �ϴ�ͼƬ
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:32:43
	 * @version 1.0
	 * @return
	 */
	public boolean update() {
		try {
			return OrmHelper.getInstance().getPhotoInfoDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * MethodsTitle: 
	 * @author: xg.chen
	 * @date:2017��3��28�� ����10:32:53
	 * @version 1.0
	 * @param photoInfo
	 * @return
	 */
	public static boolean delete(PhotoInfo photoInfo) {
		try {
			OrmHelper.getInstance().getPhotoInfoDao().delete(photoInfo);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
