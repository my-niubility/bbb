package com.nbl.services.remote.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.dto.req.PayAlyInfoDto;
import com.nbl.service.user.app.PaymentProxyApp;
import com.nbl.services.remote.PaymentThdService;

@Service("paymentThdService")
public class PaymentThdServiceImpl implements PaymentThdService {

	 @Resource
	private PaymentProxyApp paymentProxyApp;

	@Override
	public CommRespDto paymentApply(PayAlyInfoDto payAlyInfoDto) {
		return paymentProxyApp.paymentApply(payAlyInfoDto);
	}

}
