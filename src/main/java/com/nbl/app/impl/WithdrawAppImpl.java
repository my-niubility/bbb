package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.business.app.WithdrawApp;
import com.nbl.service.user.dto.req.WthdwAlyInfoDto;
import com.nbl.service.user.dto.req.WthdwNoticeDto;
import com.nbl.services.account.WithdrawService;

@Service("withdrawApp")
public class WithdrawAppImpl implements WithdrawApp {

	@Resource
	private WithdrawService withdrawService;

	@Override
	public CommRespDto withDrawApply(WthdwAlyInfoDto wthdwAlyInfoDto) {
		return withdrawService.withDrawApply(wthdwAlyInfoDto);
	}

	@Override
	public void rechargeNotice(WthdwNoticeDto rchgNoticeDto) throws MyBusinessCheckException {
		withdrawService.rechargeNotice(rchgNoticeDto);
	}

}
