package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.app.TrdOrdQryDtlApp;
import com.nbl.service.user.dto.req.TrdOrdDtlQryDto;
import com.nbl.services.order.TrdOrdDtlQryService;

@Service("trdOrdQryDtlApp")
public class TrdOrdQryDtlAppImpl implements TrdOrdQryDtlApp {

	@Resource
	private TrdOrdDtlQryService trdOrdDtlQryService;

	@Override
	public CommRespDto queryTrdOrdDtl(TrdOrdDtlQryDto trdOrdDtlQryDto) {
		return trdOrdDtlQryService.queryTrdOrdDtl(trdOrdDtlQryDto);
	}

}
