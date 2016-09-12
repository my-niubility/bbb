package com.nbl.services.product;

import java.util.List;
import java.util.Map;

import com.nbl.service.manager.dto.ProductShelfDto;
/**
 * @author Donald
 * @createdate 2016年7月28日
 * @version 1.0 
 * @description :产品货架管理
 */
public interface ProductShelfService {
	
	/**
	 * @param productMap
	 * @return
	 * @description:新品上架
	 */
	public boolean setNewProductShelf(Map<String, List<String>> productMap);
	
	/**
	 * @param productMap
	 * @param productAll
	 * @return
	 * @description:已上架产品的管理
	 */
	public boolean offAndUpProductShelf(Map<String, List<String>> productMap,String[] productAll);
	
	/**
	 * @return
	 * @description:获取已经上架的所有产品
	 */
	public List<ProductShelfDto> getAllUpShelfProduct();
	
}
