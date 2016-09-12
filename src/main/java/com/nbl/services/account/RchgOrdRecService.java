package com.nbl.services.account;

import java.util.Date;

import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.model.Recharge;
import com.nbl.service.user.dto.req.RechgAlyInfoDto;

public interface RchgOrdRecService {
	/**
	 * 创建充值订单
	 * 
	 * @param rechgAlyInfoDto
	 * @return
	 */
	public Recharge createRechargeOrder(RechgAlyInfoDto rechgAlyInfoDto);

	/**
	 * 充值应答
	 * 
	 * @param rechargeSerialNum
	 *            互金平台充值流水
	 * @param retrunType
	 * @param resultInfo
	 * @param rechargeId
	 *            第三方充值订单号
	 * @param remark
	 * @throws MyBusinessCheckException
	 */
	public void rechgRespProcess(String rechargeSerialNum, String retrunType, String resultInfo, String rechargeId,
			Date updateTime, String remark) throws MyBusinessCheckException;
}
