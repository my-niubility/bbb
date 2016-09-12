package com.nbl.services.remote.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.app.WithdrawProxyApp;
import com.nbl.service.user.dto.req.WithdrawAlyThdInfoDto;
import com.nbl.services.remote.WithdrawThdService;

@Service("withdrawThdService")
public class WithdrawThdServiceImpl implements WithdrawThdService {

	@Resource
	private WithdrawProxyApp withdrawProxyApp;

	@Override
	public CommRespDto withdrawApply(WithdrawAlyThdInfoDto wthdwAlyInfoDto) {
		return withdrawProxyApp.withdrawApply(wthdwAlyInfoDto);
	}

}
