package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.business.app.RechargeApp;
import com.nbl.service.user.dto.req.RchgNoticeDto;
import com.nbl.service.user.dto.req.RechgAlyInfoDto;
import com.nbl.services.account.RechargeService;

@Service("rechargeApp")
public class RechargeAppImpl implements RechargeApp {

	@Resource
	private RechargeService rechargeService;

	@Override
	public CommRespDto rechargeApply(RechgAlyInfoDto rechgAlyInfoDto) {
		return rechargeService.rechargeApply(rechgAlyInfoDto);
	}

	@Override
	public void rechargeNotice(RchgNoticeDto rchgNoticeDto) throws MyBusinessCheckException {
		rechargeService.rechargeNotice(rchgNoticeDto);
	}

}
