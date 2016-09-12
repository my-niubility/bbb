package com.nbl.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.app.TrdOrdQryApp;
import com.nbl.service.business.dto.req.HisTrdOrdDto;
import com.nbl.service.business.dto.req.InvHisDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.req.TrdOrdQryInfoDto;
import com.nbl.services.order.TrdOrdQryService;

@Service("trdOrdQryApp")
public class TrdOrdQryAppImpl implements TrdOrdQryApp {
	@Autowired
	private TrdOrdQryService trdOrdQryService;

	@Override
	public CommRespDto queryTradeOrder(TrdOrdQryInfoDto trdOrdQryInfoDto) {
		return trdOrdQryService.queryTradeOrder(trdOrdQryInfoDto);
	}

	@Override
	public CommRespDto querySerialFund(SerFundQryDto serFundQryDto) {
		return trdOrdQryService.querySerialFund(serFundQryDto);
	}

	@Override
	public CommRespDto queryHisTrdOrd(HisTrdOrdDto hisTrdOrdDto) {
		return trdOrdQryService.queryHisTrdOrd(hisTrdOrdDto);
	}

	@Override
	public CommRespDto queryHisPrdTrd() {
		return trdOrdQryService.queryHisPrdTrd();
	}

	@Override
	public CommRespDto qryCustInvestHistory(InvHisDto invHisDto) {
		return trdOrdQryService.qryCustInvestHistory(invHisDto);
	}

	@Override
	public String queryTradeOrderCount(TrdOrdQryInfoDto trdOrdQryInfoDto) {
		return trdOrdQryService.queryTradeOrderCount(trdOrdQryInfoDto);
	}

	@Override
	public String querySerialFundCount(SerFundQryDto serFundQryDto) {
		return trdOrdQryService.querySerialFundCount(serFundQryDto);
	}

	@Override
	public CommRespDto querySerialFund4Withdr(SerFundQryDto serFundQryDto) {
		return trdOrdQryService.querySerialFund4Withdr(serFundQryDto);
	}

}
