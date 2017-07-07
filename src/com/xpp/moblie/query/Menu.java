package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.List;

import com.xpp.moblie.entity.BaseMenu;
import com.xpp.moblie.provider.DataProviderFactory;

public class Menu extends BaseMenu {

	public Menu() {
		super();
	}

	public Menu(int id, String menuCode, String menuDesc, int menuValue,
			String menuType) {
		super(id, menuCode, menuDesc, menuValue, menuType);
	}

	private static final long serialVersionUID = 1L;

	public boolean save() {
		try {
			OrmHelper.getInstance().getMenuDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getMenuDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<Menu> findAll() {
		try {
			String ddmenu="5,8";
			List<Dictionary> fxdateList=	Dictionary.findbyRemark("ddmenu");
			if (fxdateList!=null && fxdateList.size()>0) {
				ddmenu=fxdateList.get(0).getItemDesc();
			}
			if ("mobile_dd".equals(DataProviderFactory.getRoleId())){
				    return	 OrmHelper.getInstance().getMenuDao().queryBuilder()
							.where().not().in("menuValue", ddmenu)
						  .query();
			}
				else {
					return	OrmHelper.getInstance().getMenuDao().queryForAll();
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
