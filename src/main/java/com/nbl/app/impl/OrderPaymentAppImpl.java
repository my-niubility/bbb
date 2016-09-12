package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.business.app.OrderPaymentApp;
import com.nbl.service.business.dto.req.PayAlyInfoDto;
import com.nbl.service.business.dto.req.PayRltInfoDto;
import com.nbl.services.order.OrderPaymentService;

@Service("orderPaymentApp")
public class OrderPaymentAppImpl implements OrderPaymentApp {

	@Resource
	private OrderPaymentService orderPaymentService;

	@Override
	public CommRespDto paymentApplyQuick(PayAlyInfoDto payAlyInfoDto) {
		return orderPaymentService.paymentApplyQuick(payAlyInfoDto);
	}

	@Override
	public CommRespDto paymentApplyBalance(PayAlyInfoDto payAlyInfoDto) {
		return orderPaymentService.paymentApplyBalance(payAlyInfoDto);
	}

	@Override
	public CommRespDto paymentApplyGateway(PayAlyInfoDto payAlyInfoDto) {
		return orderPaymentService.paymentApplyGateway(payAlyInfoDto);
	}

	@Override
	public void paymentAlyQckNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException {
		orderPaymentService.paymentAlyQckNotice(payRltInfoDto);
	}

	@Override
	public void paymentAlyBalNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException {
		orderPaymentService.paymentAlyBalNotice(payRltInfoDto);

	}

	@Override
	public void paymentAlyGtyNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException {
		orderPaymentService.paymentAlyGtyNotice(payRltInfoDto);
	}

}
