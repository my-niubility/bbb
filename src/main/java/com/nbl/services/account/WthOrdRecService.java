package com.nbl.services.account;

import java.util.Date;

import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.model.WithDraw;
import com.nbl.service.user.dto.req.WthdwAlyInfoDto;

public interface WthOrdRecService {
	/**
	 * 提现订单应答
	 * 
	 * @param wthdrwSerialNum
	 * @param resultInfo
	 * @param rechargeId
	 * @param updateTime
	 * @param remark
	 */
	public void wthdwRespProcess(String wthdrwSerialNum, String retrunType, String resultInfo, String withdrawId,
			Date updateTime, String remark) throws MyBusinessCheckException;;

	/**
	 * 创建提现订单
	 * 
	 * @param wthdwAlyInfoDto
	 * @return
	 */
	public WithDraw createWithdrawOrder(WthdwAlyInfoDto wthdwAlyInfoDto);
}
