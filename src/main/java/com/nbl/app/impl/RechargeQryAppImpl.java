package com.nbl.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.app.RechargeQryApp;
import com.nbl.service.user.dto.req.RchgQryInfoDto;
import com.nbl.services.account.RechargeQryService;

@Service("rechargeQryApp")
public class RechargeQryAppImpl implements RechargeQryApp {
	@Autowired
	private RechargeQryService rechargeQryService;

	@Override
	public CommRespDto queryRchgOrder(RchgQryInfoDto rchgQryInfoDto) {
		return rechargeQryService.queryRechgOrder(rchgQryInfoDto);
	}

	@Override
	public String queryRchgOrderCount(RchgQryInfoDto rchgQryInfoDto) {
		return rechargeQryService.queryRchgOrderCount(rchgQryInfoDto);
	}
}
