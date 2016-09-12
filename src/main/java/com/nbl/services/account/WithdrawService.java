package com.nbl.services.account;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.user.dto.req.WthdwAlyInfoDto;
import com.nbl.service.user.dto.req.WthdwNoticeDto;

public interface WithdrawService {
	/**
	 * 账号充值申请
	 * 
	 * @param rechgAlyInfoDto
	 * @return
	 */
	public CommRespDto withDrawApply(WthdwAlyInfoDto wthdwAlyInfoDto);

	/**
	 * 充值异步通知
	 * 
	 * @param rchgNoticeDto
	 * @return
	 * @throws MyBusinessCheckException
	 */
	public void rechargeNotice(WthdwNoticeDto rchgNoticeDto) throws MyBusinessCheckException;
}
