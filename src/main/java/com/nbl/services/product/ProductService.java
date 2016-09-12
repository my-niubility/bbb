package com.nbl.services.product;

import java.util.List;

import com.nbl.model.ProductAudit;
import com.nbl.model.vo.ProductTypeVo;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :平台产品服务接口
 */
public interface ProductService {
	
	/**
	 * @param productVo
	 * @return
	 * @description:查询产品信息
	 */
	public List<ProductTypeVo> getProductList(ProductTypeVo productVo);
	
	/**
	 * @param productVo
	 * @return
	 * @description:产品名称模糊查询产品信息
	 */
	public List<ProductTypeVo> findProductListByname(ProductTypeVo productVo);
	
	/**
	 * @param productVo
	 * @return
	 * @description:统计产品数
	 */
	public int getProductStat(ProductTypeVo productVo);
	
	
	/**
	 * 产品审核(包括:产品ID、产品审核状态 ) ，同时初始化持仓表数据
	 * @param project
	 */
	public void productAudit(String productId, ProductAudit projectAudit);
	
	/**
	 * 产品募集审核(包括:产品ID、产品审核状态 )
	 * @param project
	 */
	public void projectFound(String productId, ProductAudit projectAudit);
	

	/**
	 * ID查找项目信息
	 */
	public ProductTypeVo selectByPrimaryKey(String productId,String productType);
	
	/**
	 * ID更新项目信息
	 */
	public void updateByPrimaryKeySelective(ProductTypeVo productVo);


}
