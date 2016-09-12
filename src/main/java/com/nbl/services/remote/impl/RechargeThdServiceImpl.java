package com.nbl.services.remote.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.app.RechargeProxyApp;
import com.nbl.service.user.dto.req.RechgAlyThdInfoDto;
import com.nbl.services.remote.RechargeThdService;

@Service("rechargeThdService")
public class RechargeThdServiceImpl implements RechargeThdService {
	 @Resource
	private RechargeProxyApp rechargeProxyApp;

	@Override
	public CommRespDto rechargeApply(RechgAlyThdInfoDto rechgAlyThdInfoDto) {
		return rechargeProxyApp.rechargeApply(rechgAlyThdInfoDto);
	}

}
