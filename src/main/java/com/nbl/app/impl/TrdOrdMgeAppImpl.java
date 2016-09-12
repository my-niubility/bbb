package com.nbl.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.app.TrdOrdMgeApp;
import com.nbl.service.business.dto.req.CanlTrdOrdDto;
import com.nbl.services.order.TrdOrdMgeService;

@Service("trdOrdMgeApp")
public class TrdOrdMgeAppImpl implements TrdOrdMgeApp {

	@Autowired
	private TrdOrdMgeService trdOrdMgeService;

	@Override
	public CommRespDto cancelTradeOrder(CanlTrdOrdDto canlTrdOrdDto) {
		return trdOrdMgeService.cancelTradeOrder(canlTrdOrdDto);
	}

	@Override
	public CommRespDto disabledTradeOrder() {
		return trdOrdMgeService.disabledTradeOrder();
	}
}
