package com.nbl.services.trade;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.TIncome;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.res.SerFundQryRsltItemDto;

/**
 * @author gcs
 * @createdate 2016年7月23日
 * @version 1.0
 * @description 投资利息管理接口
 * 
 */

public interface IncomeService {

	/**
	 * @param pageVO
	 * @param income
	 * @return
	 * @description:分页查询
	 */
	public List<TIncome> pageListQueryIncome(PageVO<TIncome> pageVO, TIncome income);

	/**
	 * @param income
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryIncome(TIncome income);

	/**
	 * @param id
	 * @return obj
	 * @description: 根据流水ID来查询明细
	 */
	public TIncome incomeDetail(String id);

	/**
	 * 资金流水中收益查询
	 * 
	 * @param pageVO
	 * @param income
	 * @return
	 */
	public List<SerFundQryRsltItemDto> qryIncomeInFundFlow(SerFundQryDto serFundQryDto);

	/**
	 * 资金流水中收益查询数量
	 * 
	 * @param pageVO
	 * @param income
	 * @return
	 */
	public String qryIncomeInFundFlowCount(SerFundQryDto serFundQryDto);
}
