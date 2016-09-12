package com.nbl.services.account;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.RchgQryInfoDto;
import com.nbl.service.user.dto.req.SerFundQryDto;

public interface RechargeQryService {
	/**
	 * 查询充值订单
	 * 
	 * @param rchgQryInfoDto
	 * @return
	 */
	public CommRespDto queryRechgOrder(RchgQryInfoDto rchgQryInfoDto);

	/**
	 * 查询资金流水中充值订单
	 * 
	 * @param serFundQryDto
	 * @return
	 */
	public CommRespDto queryRechgOrderInSerFund(SerFundQryDto serFundQryDto);

	public String queryRchgOrderCount(RchgQryInfoDto rchgQryInfoDto);
}
