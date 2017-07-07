package com.xpp.moblie.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.util.Log;
import com.xpp.moblie.application.XPPApplication;
import com.xpp.moblie.entity.BaseProduct;
import com.xpp.moblie.provider.DataProviderFactory;

public class Product extends BaseProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Product() {
		super();
	}

	public Product(String skuId, String skuDesc, String categoryId,
			String categoryDesc, String brandsId, String brandsDesc,
			String empId,String lastPrice) {
		super(skuId, skuDesc, categoryId, categoryDesc, brandsId, brandsDesc,
				empId,lastPrice);
	}
	public static Product findByCategoryId(String categoryId) {
		try {
		List<Product>	list =  OrmHelper.getInstance().getProductDao().queryBuilder()
					.where().eq("categoryId", categoryId).query();
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Product> findByKunner(String kunner) {
		try {
		List<Product>	list =  OrmHelper.getInstance().getProductDao().queryBuilder()
					.where().eq("cloudId", kunner).query();
//		if(list!=null&&list.size()!=0){
//			return list;
//		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean save() {
		try {
			OrmHelper.getInstance().getProductDao().createOrUpdate(this);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public static boolean deleteAll() {
		try {
			OrmHelper.getInstance().getProductDao().delete(findAll());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteAll(List<Product> list) {
		try {
			OrmHelper.getInstance().getProductDao().delete(list);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update() {
		try {
			return OrmHelper.getInstance().getProductDao().update(this) > -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<Product> findAll() {
		try {
			return OrmHelper.getInstance().getProductDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Product> findProducts(String type) {
		try {
			
			return OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
					.where().eq("productType", type).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Product> findProductsXpp(String type) {
		try {
			
			return OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
					.where().eq("productType", type).and().eq("cloudId", "0000000000").query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	// public String toString (){
	// return
	// ("分割---------------skuId:"+categoryId+" 描述："+categoryDesc+"品类"+categorySortId+" 类型："+productType
	// +"\n");
	// }
	//
	// 获取品项列表
	public static List<Product> findAllCategorySortId() {
		try {
			// QueryBuilder<Product, Integer>qb =
			// OrmHelper.getInstance().getProductDao().queryBuilder();
			// qb.selectColumns("categorySortDesc").distinct().selectColumns("categorySortId").orderBy("categorySortId",
			// true).query();
			// List<Product> list
			// =OrmHelper.getInstance().getProductDao().query(qb.prepare());
			List<Product> list = findProducts("1");
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = list.size() - 1; j > i; j--) {
					if (list.get(j).getCategorySortId()
							.equals(list.get(i).getCategorySortId())) {
						list.remove(j);
					}
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	// 获取价格管理sku列表
	public static List<Product> getPriceSkuList(String custId) {
		List<Product> list = new ArrayList<Product>();
		Product p;
		//获取已铺货sku
		for (Distribution distribution : Distribution.findByCustId(custId)) {
			try {
				p = new Product();
				p = OrmHelper.getInstance().getProductDao()
						.queryForId(Integer.valueOf(distribution.getSkuId()));
				if (p != null) {
					list.add(p);
				}
				
				Collections.sort(list, new Comparator<Product>() {  
			          public int compare(Product a, Product b) {  
			            int one = ((Product)a).getSkuOrder();
			            int two = ((Product)b).getSkuOrder();   
			            return one -two ;   
			          }  
			       }); 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	
	public static List<Product> findByName(String categoryDesc,String type,String flag) {
		try {
			if(categoryDesc.trim()==null||"".equals(categoryDesc.trim())){
				return findProducts(type);
			}
			if(XPPApplication.TAB_SALES_DAY.equals(flag)){
				return OrmHelper.getInstance().getProductDao().queryBuilder()
						.where().
						like("pinyinsearchkey", "%"+categoryDesc.trim()+"%").or().
						like("brandsDesc", "%"+categoryDesc.trim()+"%").or().
						like("categorySortDesc", "%"+categoryDesc.trim()+"%").and().eq("productType", type).query();
			}else{
			return OrmHelper.getInstance().getProductDao().queryBuilder()
					.where().
					like("pinyinsearchkey", "%"+categoryDesc.trim()+"%").or().
					like("categoryDesc", "%"+categoryDesc.trim()+"%").or().
					like("brandsDesc", "%"+categoryDesc.trim()+"%").or().
					like("categorySortDesc", "%"+categoryDesc.trim()+"%").and().eq("productType", type).query();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static List<Product> findProducts(String type,String brandsId) {
//		try {
//			if(brandsId==null||"2".equals(type)){//订单管理，显示所有本品，包括xpp产品及自维护产品;显示竞品
//			return OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//					.where().eq("productType", type).query();
//			}
//			//标准信息采集只显示xpp产品
//			return OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//					.where().eq("productType", type).and().eq("brandsId", brandsId).query();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static List<Product> findProductsByKunner(String type,String brandsId,String kunner) {
		List<Product> list=new ArrayList<Product>();
		try {
			
			//标准信息采集只显示xpp产品
//			list.addAll(OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder",true)
//			.where().eq("productType", type).and().eq("cloudId","0000000000").and().eq("brandsId",brandsId).query());
//			
//			if(brandsId==null||"2".equals(type)){//订单管理，显示门店相关经销商的本品，包括xpp产品及自维护产品;显示竞品
//			list.addAll(
			list=OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
					.where().eq("productType", type).and().in("cloudId",new String[]{kunner,"0000000000"}).query();
//			list=OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//					.where().in("cloudId",new String[]{kunner,"0000000000"}).query();
//					);
			
//			list.addAll(OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//			.where().eq("productType", type).and().eq("cloudId", "0000000000").query());
//			list.addAll(OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//					.where().eq("productType", type).and().eq("cloudId", kunner).query());
//			//}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Product> findOrderProductByName(String categoryDesc,String type) {
		try {
			if(categoryDesc.trim()==null||"".equals(categoryDesc.trim())){
				//System.out.println("打印:findProductsByKunner(type,null,kunner)");
				return  findProductsXpp(type);
			}
			//System.out.println("打印： OrmHelper.getInstance().getProductDao().queryBuilder()");
			return OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
					.where().
					like("pinyinsearchkey", "%"+categoryDesc.trim()+"%").or().
					like("categoryDesc", "%"+categoryDesc.trim()+"%").or().
					like("brandsDesc", "%"+categoryDesc.trim()+"%").or().
					like("categorySortDesc", "%"+categoryDesc.trim()+"%")
					.and().eq("productType", type).and().eq("cloudId","0000000000").query();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Product> findOrderProductByName(String categoryDesc,String type,String kunner) {
		try {
			if(categoryDesc.trim()==null||"".equals(categoryDesc.trim())){
				//System.out.println("打印:findProductsByKunner(type,null,kunner)");
				return findProductsByKunner(type,null,kunner);
			}
			//System.out.println("打印： OrmHelper.getInstance().getProductDao().queryBuilder()");
			return OrmHelper.getInstance().getProductDao().queryBuilder()
					.where().
					like("pinyinsearchkey", "%"+categoryDesc.trim()+"%").or().
					like("categoryDesc", "%"+categoryDesc.trim()+"%").or().
					like("brandsDesc", "%"+categoryDesc.trim()+"%").or().
					like("categorySortDesc", "%"+categoryDesc.trim()+"%")
					.and().eq("productType", type).and().in("cloudId",new String[]{"0000000000",kunner}).query();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<Product> findProductsByKunner1(String type,String brandsId,String kunner,Customer customer) {
		List<Product> list=new ArrayList<Product>();
		List<Product> yy= new ArrayList<Product>();
		List<Product> nn = new ArrayList<Product>();
		try {
			
			//标准信息采集只显示xpp产品
//			list.addAll(OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder",true)
//			.where().eq("productType", type).and().eq("cloudId","0000000000").and().eq("brandsId",brandsId).query());
//			
//			if(brandsId==null||"2".equals(type)){//订单管理，显示门店相关经销商的本品，包括xpp产品及自维护产品;显示竞品
//			list.addAll(
			yy=OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
					.where().eq("productType", type).and().eq("status",customer.getCustId()).and().in("cloudId",new String[]{kunner,"0000000000"}).query();
			nn=OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
					.where().eq("productType", type).and().ne("status",customer.getCustId()).and().in("cloudId",new String[]{kunner,"0000000000"}).query();
			list.addAll(yy);
			list.addAll(nn);
			//			list=OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//					.where().in("cloudId",new String[]{kunner,"0000000000"}).query();
//					);
			
//			list.addAll(OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//			.where().eq("productType", type).and().eq("cloudId", "0000000000").query());
//			list.addAll(OrmHelper.getInstance().getProductDao().queryBuilder().orderBy("skuOrder", true)
//					.where().eq("productType", type).and().eq("cloudId", kunner).query());
//			//}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public static List<Product> findOrderProductByName1(String categoryDesc,String type,String kunner,Customer customer) {
		try {
			if(categoryDesc.trim()==null||"".equals(categoryDesc.trim())){
				//System.out.println("打印:findProductsByKunner(type,null,kunner)");
				return findProductsByKunner1(type,null,kunner,customer);
			}
			List<Product> yy= new ArrayList<Product>();
			List<Product> nn = new ArrayList<Product>();
			yy=OrmHelper.getInstance().getProductDao().queryBuilder()
					.where().
					like("pinyinsearchkey", "%"+categoryDesc.trim()+"%").or().
					like("categoryDesc", "%"+categoryDesc.trim()+"%").or().
					like("brandsDesc", "%"+categoryDesc.trim()+"%").or().
					like("categorySortDesc", "%"+categoryDesc.trim()+"%")
					.and().eq("productType", type).and().eq("status",customer.getCustId()).and().in("cloudId",new String[]{"0000000000",kunner}).query();
			//System.out.println("打印： OrmHelper.getInstance().getProductDao().queryBuilder()");
			nn=OrmHelper.getInstance().getProductDao().queryBuilder()
					.where().
					like("pinyinsearchkey", "%"+categoryDesc.trim()+"%").or().
					like("categoryDesc", "%"+categoryDesc.trim()+"%").or().
					like("brandsDesc", "%"+categoryDesc.trim()+"%").or().
					like("categorySortDesc", "%"+categoryDesc.trim()+"%")
					.and().eq("productType", type).and().ne("status",customer.getCustId()).and().in("cloudId",new String[]{"0000000000",kunner}).query();
			yy.addAll(nn);
			return yy;
//			return OrmHelper.getInstance().getProductDao().queryBuilder()
//					.where().
//					like("pinyinsearchkey", "%"+categoryDesc.trim()+"%").or().
//					like("categoryDesc", "%"+categoryDesc.trim()+"%").or().
//					like("brandsDesc", "%"+categoryDesc.trim()+"%").or().
//					like("categorySortDesc", "%"+categoryDesc.trim()+"%")
//					.and().eq("productType", type).and().in("cloudId",new String[]{"0000000000",kunner}).query();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
