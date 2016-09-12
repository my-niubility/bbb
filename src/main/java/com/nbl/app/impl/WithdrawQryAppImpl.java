package com.nbl.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.app.WithdrawQryApp;
import com.nbl.service.user.dto.req.WthdrQryInfoDto;
import com.nbl.services.account.WthdrQryService;

@Service("withdrawQryApp")
public class WithdrawQryAppImpl implements WithdrawQryApp {
	@Autowired
	private WthdrQryService wthdrQryService;

	@Override
	public CommRespDto queryWthdrOrder(WthdrQryInfoDto wthdrQryInfoDto) {
		return wthdrQryService.queryWthdrOrder(wthdrQryInfoDto);
	}

	@Override
	public String queryWthdrOrderCount(WthdrQryInfoDto wthdrQryInfoDto) {
		return wthdrQryService.queryWthdrOrderCount(wthdrQryInfoDto);
	}

}
