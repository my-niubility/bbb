package com.nbl.services.trade;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.TradeOrder;

public interface TradeOrderService {
	/**
	 * @param pageVO
	 * @param tradeOrder
	 * @return
	 * @description:分页查询
	 */
	public List<TradeOrder> pageListQueryTradeOrder(PageVO<TradeOrder> pageVO,TradeOrder tradeOrder);
	
	/**
	 * @param tradeOrder
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryTradeOrder(TradeOrder tradeOrder);
	
	/**
	 * @param id
	 * @return obj
	 * @description: 根据交易订单ID来查询订单明细
	 */
	public TradeOrder tradeOrderDetail(String id);
	/**
	 * 根据id查询订单状态用于token判断
	 * @param id
	 * @return
	 */
	public String getOrderStatus(String id);
}
