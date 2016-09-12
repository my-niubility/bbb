package com.nbl.services.withdraw;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.WithDraw;

public interface NewWithdrawService {
	/**
	 * @param pageVO
	 * @param withdraw
	 * @return
	 * @description:分页查询
	 */
	public List<WithDraw> pageListQueryWithDraw(PageVO<WithDraw> pageVO,WithDraw withdraw);
	
	/**
	 * @param withdraw
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryWithdraw(WithDraw withdraw);
	
	/**
	 * @param id
	 * @return obj
	 * @description: 根据交易订单ID来查询订单明细
	 */
	public WithDraw withdrawDetail(String id);
}
