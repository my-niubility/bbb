package com.nbl.services.portion;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.AccountReverse;

public interface PositiveService {
	
	/**
	 * @param pageVO
	 * @param reqDto
	 * @return
	 * @description:分页查询
	 */
	public List<AccountReverse> pageListQueryPositive(PageVO<AccountReverse> pageVO,AccountReverse positive);
	
	/**
	 * @param positive
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryPositive(AccountReverse positive);
	
	/**
	 * @param reverseId
	 * @return obj
	 * @description: 根据充值订单ID来查询订单明细
	 */
	public AccountReverse positiveDetail(Integer reverseId);
}
