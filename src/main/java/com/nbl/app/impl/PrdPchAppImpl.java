
package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.business.app.PrdPchApp;
import com.nbl.service.business.dto.req.PrdPchInfoDto;
import com.nbl.service.business.dto.res.PrdCheckResDto;
import com.nbl.services.order.PrdPchService;

/**
 * 商品购买
 * 
 * @author AlanMa
 *
 */
@Service("prdPchApp")
public class PrdPchAppImpl implements PrdPchApp {

	@Resource
	private PrdPchService prdPchService;

	@Override
	public CommRespDto buyNow(PrdPchInfoDto prdPchInfoDto) {
		return prdPchService.buyNow(prdPchInfoDto);
	}

	@Override
	public PrdCheckResDto buyNowChkPrdInfo(PrdPchInfoDto prdPchInfoDto) throws MyBusinessCheckException {
		return prdPchService.buyNowChkPrdInfo(prdPchInfoDto);
	}

}
