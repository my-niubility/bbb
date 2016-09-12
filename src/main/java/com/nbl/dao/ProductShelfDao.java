package com.nbl.dao;

import java.util.List;

import com.nbl.model.ProductShelf;
import com.nbl.model.vo.ProductShelfVo;

public interface ProductShelfDao {
	int deleteByPrimaryKey(Long id);

	int insert(ProductShelf record);

	int insertSelective(ProductShelf record);

	ProductShelf selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProductShelf record);

	int updateByPrimaryKey(ProductShelf record);
	
	/**
	 * @return
	 * @description:获取所有已上架的产品
	 */
	public List<ProductShelfVo> getAllUpShelfProduct();
	/**
	 * @param productId
	 * @return
	 * @description:通过产品编号获取该产品涉及的货架数
	 */
	public List<ProductShelfVo> getShelfProductByProductId(String productId);
	/**
	 * @param list
	 * @return
	 * @description:批量处理新品上架
	 */
	public int batchInsertShelf(List<ProductShelf> list);
	
	/**
	 * @param ps
	 * @return
	 * @description:处理货架更新（下架）
	 */
	public int batchUpdateShelf(ProductShelf ps);


}