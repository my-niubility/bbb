package com.nbl.services.task;
import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.BillBalance;
/**
 * @author gcs
 * @createdate 2016年7月25日	
 * @version 1.0
 * 
 */

public interface BillBalanceService {
	
	/**
	 * @param pageVO
	 * @param balance
	 * @return
	 * @description:分页查询
	 */
	public List<BillBalance> pageListQueryBalance(PageVO<BillBalance> pageVO,BillBalance balance);
	
	/**
	 * @param balance
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryBalance(BillBalance balance);
	
	/**
	 * @param id
	 * @return obj
	 * @description: 根据交易订单ID来查询订单明细
	 */
	public BillBalance BalanceDetail(Integer id);

}
