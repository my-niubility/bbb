package com.nbl.services.account;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.req.WthdrQryInfoDto;

public interface WthdrQryService {
	/**
	 * 查询提现订单
	 * 
	 * @param rchgQryInfoDto
	 * @return
	 */
	public CommRespDto queryWthdrOrder(WthdrQryInfoDto wthdrQryInfoDto);

	/**
	 * 查询资金流水中的提现订单（提现状态为提现成功）
	 * 
	 * @param rchgQryInfoDto
	 * @return
	 */
	public CommRespDto queryWthdrOrderInSerFund(SerFundQryDto serFundQryDto);

	public String queryWthdrOrderCount(WthdrQryInfoDto wthdrQryInfoDto);
	/**
	 * 查询资金流水中提现订单（提现状态为提现成功、处理中），为打通第三方支付前提现时判断账户余额（该余额已减去提现锁定金额）
	 * @param serFundQryDto
	 * @return
	 */
	public CommRespDto queryWthdrOrderInSerFund4Wthdr(SerFundQryDto serFundQryDto);
}
