package com.nbl.services.remote.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.service.user.app.CustAccountApp;
import com.nbl.services.remote.CustAccQueryService;

@Service("custAccQueryService")
public class CustAccQueryImpl implements CustAccQueryService {
 	@Resource
	private CustAccountApp custAccountApp;

	@Override
	public String getAccId(String custId) {
		return custAccountApp.getAccountId(custId);
	}

	@Override
	public String getCustName(String custId) {
		return custAccountApp.getCustName(custId);
	}

}
