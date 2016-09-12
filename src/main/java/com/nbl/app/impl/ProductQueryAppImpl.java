package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.app.ProductQueryApp;
import com.nbl.service.business.dto.req.MutiCndQryPrdsDto;
import com.nbl.service.business.dto.req.PrdDtlInfoQryDto;
import com.nbl.service.business.dto.req.PrdExhiInfoDto;
import com.nbl.services.product.ProductQueryService;

@Service("productQueryApp")
public class ProductQueryAppImpl implements ProductQueryApp {

	@Resource
	private ProductQueryService productQueryService;

	@Override
	public CommRespDto productExhibitionQuery(PrdExhiInfoDto reqDto) {
		return productQueryService.productExhibitionQuery(reqDto);
	}

	@Override
	public CommRespDto productsMutiCondQuery(MutiCndQryPrdsDto mutiCndQryPrdsDto) {
		return productQueryService.productsMutiCondQuery(mutiCndQryPrdsDto);
	}

	@Override
	public CommRespDto productDetailsQuery(PrdDtlInfoQryDto prdDtlInfoQryDto) {
		return productQueryService.productDetailsQuery(prdDtlInfoQryDto);
	}

	@Override
	public String productsMutiCondCountQuery(MutiCndQryPrdsDto mutiCndQryPrdsDto) {
		return productQueryService.productsMutiCondCountQuery(mutiCndQryPrdsDto);
	}

}
