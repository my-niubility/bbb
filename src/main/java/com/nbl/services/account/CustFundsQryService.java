package com.nbl.services.account;

import com.nbl.common.dto.CommRespDto;

/**
 * 待收收益、累计收益、持有资产查询
 * @author AlanMa
 *
 */
public interface CustFundsQryService {
	public CommRespDto queryCustFunds(String custId);
}
