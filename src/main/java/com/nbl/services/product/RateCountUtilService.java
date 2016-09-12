package com.nbl.services.product;

import java.math.BigDecimal;
import java.util.List;

import com.nbl.model.Position;
import com.nbl.model.vo.ProductTypeVo;
import com.nbl.service.business.dto.res.RateCountUtilResponseDto;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :等额本息、等额本金、到期一次付息还本、每月付息到期还本服务接口。
 */
public interface RateCountUtilService {

	/**
	 * @param product 产品信息
	 * @param cpList 持仓信息
	 * @return
	 * @description:等额本息计算实现
	 */
	public String[] averageCapital(ProductTypeVo product,List<Position> cpList);
	
	/**
	 * @param product 产品信息
	 * @param cpList 持仓信息
	 * @return
	 * @description:等额本金计算实现
	 */
	public String[] averagePrincipal(ProductTypeVo product,List<Position> cpList);
	
	/**
	 * @param product 产品信息
	 * @param cpList 持仓信息
	 * @return
	 * @description:到期一次付息还本计算实现
	 */
	public String[] periodRepayCapital(ProductTypeVo product,List<Position> cpList);
	
	/**
	 * @param product 产品信息
	 * @param cpList 持仓信息
	 * @return
	 * @description:每月付息到期还本计算实现
	 */
	public String[] monthCapPerPrincipal(ProductTypeVo product,List<Position> cpList);
	
	/**
	 * @param product 产品信息
	 * @param cpList 持仓信息
	 * @return
	 * @description:每月付息到期还本计算实现(按具体天数计算利息)
	 */
	public String[] realDayCapPerPrincipal(ProductTypeVo product,List<Position> cpList);
	
	/**
	 * @param investAmt  投资金额
	 * @param investPeriodDay 投资期限（天数）
	 * @param investPeriodMonth 投资期限（月）
	 * @param yearRate 年化利率
	 * @param type 偿还方式
	 * @param yearDay 年换天标准（360 或365）
	 * @return
	 * @description:根据投资金额、投资期限（天）、投资期限（月）、年化利率三要素算出预期收益（计算器使用）
	 */
	public RateCountUtilResponseDto  getExpectEarning(Long investAmt, Long investPeriodDay,Long investPeriodMonth, BigDecimal yearRate, String type, String yearDay);
}
