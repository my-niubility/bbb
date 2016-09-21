package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nbl.common.vo.PageVO;
import com.nbl.model.ProductCommon;
import com.nbl.model.vo.PrdBatchUpdVo;
import com.nbl.model.vo.PrdRefundVo;
import com.nbl.model.vo.ProIdxVo;
import com.nbl.model.vo.ProductCommonVo;

public interface ProductCommonDao {
	int deleteByPrimaryKey(String productId);

	int insert(ProductCommon record);

	int insertSelective(ProductCommon record);

	int updateByPrimaryKeySelective(ProductCommon record);

	int updateByPrimaryKey(ProductCommon record);

	List<ProductCommon> selectByExhType(String exhType);

	/**
	 * 多条件产品信息查询
	 * 
	 * @param pageVO
	 * @param rechargeVo
	 * @return
	 */
	List<ProductCommon> selectByMutiCond(@Param("pageVO") PageVO<ProIdxVo> pageVO, @Param("proIdxVo") ProIdxVo proIdxVo,@Param("exhTypeStatus")String exhTypeStatus);

	int selectByMutiCondCount(@Param("proIdxVo") ProIdxVo proIdxVo,@Param("exhTypeStatus")String exhTypeStatus);

	/**
	 * @param record
	 * @description:修改审核状态
	 */
	public void updateAuditStatus(ProductCommon record);

	/**
	 * @param pageVO
	 * @param productCommonVo
	 * @return
	 * @description:查询标准产品列表分页
	 */
	public List<ProductCommon> findListPage(@Param("pageVO") PageVO<ProductCommonVo> pageVO, @Param("productCommonVo") ProductCommonVo productCommonVo);

	/**
	 * @param productCommonVo
	 * @return
	 * @description:查询记录数
	 */
	public int findListPageCount(@Param("productCommonVo") ProductCommonVo productCommonVo);

	/**
	 * @param productId
	 * @return
	 * @description:根据productId查询产品明细
	 */
	ProductCommon selectByPrimaryKey(String productId);

	/**
	 * @param productId
	 * @return
	 * @description:根据productId查询产品明细
	 */
	ProductCommon selectByPrimaryKeyForUpdate(String productId);

	List<ProductCommon> selectByRefundCond(@Param("pageVO") PageVO<ProductCommon> pageVO, @Param("prdRefundVo") PrdRefundVo prdRefundVo);

	int selectByRefundCondCount(@Param("prdRefundVo") PrdRefundVo vo);

	/**
	 * 批量解锁产品份额
	 * @param vos
	 * @return
	 */
	int updateByPrdIdsAndPorts(List<PrdBatchUpdVo> vos);


}