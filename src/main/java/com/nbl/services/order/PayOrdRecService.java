package com.nbl.services.order;

import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.model.Payment;
import com.nbl.model.TradeOrder;
import com.nbl.service.business.dto.req.PayAlyInfoDto;
import com.nbl.service.business.dto.req.PayRltInfoDto;
import com.nbl.service.business.dto.res.PayAlyResultDto;

public interface PayOrdRecService {

	/**
	 * 创建支付订单
	 * 
	 * @param payAlyInfoDto
	 * @param tradeOrder
	 * @return
	 */
	public Payment createPaymentOrder(PayAlyInfoDto payAlyInfoDto, TradeOrder tradeOrder);

	public void paymentNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException;

	/**
	 * 记录会计分录
	 * 
	 * @param custId
	 * @param payOrderId
	 * @param tradeDate
	 * @param productId
	 * @param productName
	 * @param purchasePortion
	 * @throws MyBusinessCheckException
	 */
	public void recordAccBook(Long bookId, String custId, String payOrderId, String tradeDate, String productId, String productName, Long purchasePortion) throws MyBusinessRuntimeException;

	/**
	 * 更新业务订单和支付订单状态
	 */
	public void updateOrderStatus(String resultInfo, String tradeOrderId, String trdOrdStatus, String payOrdId, String payOrdStatus, String thdPayOrdId);

	/**
	 * 记账冲正
	 * 
	 * @param orgTradeId
	 * @param orgPayId
	 * @param orgBookId
	 * @param orgAmount
	 * @param orgAccountDate
	 * @param custId
	 * @param tradeDate
	 * @param productId
	 * @param productName
	 * @param amount
	 * @throws MyBusinessCheckException
	 */
	public void recordAccBookReverse(String orgTradeId, String orgPayId, Long orgBookId, Long orgAmount, String orgAccountDate, String custId, String tradeDate, String productId, String productName,
			Long amount);

	/**
	 * 记录第三方应答信息
	 * 
	 * @param thdPtyRslt
	 */
	public void recordThdResponse(PayAlyResultDto thdPtyRslt, String retrunCode, String returnMessage) throws MyBusinessCheckException;

}
