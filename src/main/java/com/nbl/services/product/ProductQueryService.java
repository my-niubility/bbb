package com.nbl.services.product;

import java.util.List;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.vo.PageVO;
import com.nbl.model.ProductAudit;
import com.nbl.model.vo.ProductTypeVo;
import com.nbl.service.business.dto.req.MutiCndQryPrdsDto;
import com.nbl.service.business.dto.req.PrdDtlInfoQryDto;
import com.nbl.service.business.dto.req.PrdExhiInfoDto;
import com.nbl.service.business.dto.req.StandardProductQueryDto;

/**
 * @author AlanMa
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :产品查询
 */
public interface ProductQueryService {

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
	 * 
	 * @param project
	 */
	public void productAudit(ProductTypeVo productVo, ProductAudit projectAudit);

	/**
	 * ID查找项目信息
	 */
	public ProductTypeVo selectByPrimaryKey(String productId, String productType);

	/**
	 * ID更新项目信息
	 */
	public void updateByPrimaryKeySelective(ProductTypeVo productVo);

	/**
	 * 根据展示类型查询需要展示的商品信息如：首页展示、推荐商品等
	 * 
	 * @param reqDto
	 * @return
	 */
	public CommRespDto productExhibitionQuery(PrdExhiInfoDto reqDto);

	/**
	 * 多条件产品信息查询
	 * 
	 * @param mutiCndQryPrdsDto
	 * @return
	 */
	public CommRespDto productsMutiCondQuery(MutiCndQryPrdsDto mutiCndQryPrdsDto);

	/**
	 * 产品详情信息查询
	 * 
	 * @param prdDtlInfoQryDto
	 * @return
	 */
	public CommRespDto productDetailsQuery(PrdDtlInfoQryDto prdDtlInfoQryDto);

	public String productsMutiCondCountQuery(MutiCndQryPrdsDto mutiCndQryPrdsDto);
	
	
	/**
	 * @param pageVO
	 * @param reqDto
	 * @return
	 * @description:标准产品分页查询（管理平台）
	 */
	public List<StandardProductQueryDto> pageListQueryStProduct(PageVO<StandardProductQueryDto> pageVO,StandardProductQueryDto reqDto);
	
	/**
	 * @param reqDto
	 * @return
	 * @description:分页查询总数（管理平台）
	 */
	public int pageCountQueryStProduct(StandardProductQueryDto reqDto);
	
	/**
	 * @param chargeId
	 * @return
	 * @description:标准产品明细查询（管理平台）
	 */
	public StandardProductQueryDto detailQuery(String productId);


}
