package com.nbl.app.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.service.business.app.ChargeApp;
import com.nbl.service.business.dto.req.ChargeReqDto;
import com.nbl.service.business.dto.res.ChargeResDto;
import com.nbl.services.product.ChargeService;

@Service("chargeApp")
public class ChargeAppImpl implements ChargeApp {

	@Resource
	public ChargeService chargeService;

	@Override
	public List<ChargeResDto> pageListQueryCharge(PageVO<ChargeReqDto> pageVO, ChargeReqDto reqDto) {
		return chargeService.pageListQueryCharge(pageVO, reqDto);
	}

	@Override
	public int pageCountQueryCharge(ChargeReqDto reqDto) {
		return chargeService.pageCountQueryCharge(reqDto);
	}

	@Override
	public ChargeResDto detailQueryCharge(String chargeId) {
		return chargeService.detailQueryCharge(chargeId);
	}

}
