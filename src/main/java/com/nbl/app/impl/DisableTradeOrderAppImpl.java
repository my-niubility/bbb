package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.service.business.app.DisableTradeOrderApp;
import com.nbl.services.order.TrdOrdMgeService;

/**
 * @author AlanMa
 *
 */
@Service("disableTradeOrderApp")
public class DisableTradeOrderAppImpl implements DisableTradeOrderApp {

	@Resource
	private TrdOrdMgeService trdOrdMgeService;

	@Override
	public void disabledTradeOrder() {
		trdOrdMgeService.disabledTradeOrder();
	}

}
