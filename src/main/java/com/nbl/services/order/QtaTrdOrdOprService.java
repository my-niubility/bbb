package com.nbl.services.order;

import java.util.List;

import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.model.TradeOrder;
import com.nbl.model.vo.PrdBatchUpdVo;
import com.nbl.service.business.dto.req.PrdPchInfoDto;

public interface QtaTrdOrdOprService {

	/**
	 *  解锁份额
	 * @param productId
	 * @param purchasePortion
	 * @throws MyBusinessRuntimeException
	 */
	public void unlockQuota(String productId, Long purchasePortion) throws MyBusinessRuntimeException;

	/**
	 * 创建交易订单
	 * 
	 * @param prdPchInfoDto
	 */
	public TradeOrder createTradeOrder(PrdPchInfoDto prdPchInfoDto);


	/**
	 * 锁定产品份额
	 * @param productId
	 * @param purchasePortion
	 * @throws MyBusinessCheckException
	 */
	public void lockQuota(String productId, Long purchasePortion) throws MyBusinessCheckException;


	/**
	 * 清零产品锁定份额
	 * @param productId
	 * @param purchasePortion
	 * @throws MyBusinessCheckException
	 */
	public void cleanLockQuota(String productId,  Long purchasePortion) throws MyBusinessCheckException;

	/**
	 * 批量解锁产品份额
	 * @param prdBatchUpdVos
	 * @return
	 * @throws MyBusinessRuntimeException
	 */
	int unlockQuota(List<PrdBatchUpdVo> prdBatchUpdVos);


}
