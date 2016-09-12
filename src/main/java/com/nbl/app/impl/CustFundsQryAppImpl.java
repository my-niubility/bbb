package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.app.CustFundsQryApp;
import com.nbl.services.account.CustFundsQryService;

@Service("custFundsQryApp")
public class CustFundsQryAppImpl implements CustFundsQryApp {
	@Resource
	private CustFundsQryService custFundsQryService;

	@Override
	public CommRespDto queryCustFunds(String custId) {
		return custFundsQryService.queryCustFunds(custId);
	}

}
