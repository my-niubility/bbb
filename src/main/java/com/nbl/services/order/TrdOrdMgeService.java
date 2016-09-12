package com.nbl.services.order;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.dto.req.CanlTrdOrdDto;

public interface TrdOrdMgeService {
	/**
	 * 取消订单
	 * 
	 * @param canlTrdOrdDto
	 * @return
	 */
	public CommRespDto cancelTradeOrder(CanlTrdOrdDto canlTrdOrdDto);

	/**
	 * 订单失效（定时任务触发）
	 * 
	 * @return
	 */
	public CommRespDto disabledTradeOrder();
}
