package com.nbl.services.order;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.business.dto.req.PrdPchInfoDto;
import com.nbl.service.business.dto.res.PrdCheckResDto;

public interface PrdPchService {
	/**
	 * 立即购买
	 * 
	 * @param PrdPch
	 * @return
	 */
	public CommRespDto buyNow(PrdPchInfoDto prdPchInfoDto);

	/**
	 * 立即购买产品信息校验
	 * 
	 * @param prdPchInfoDto
	 * @return
	 */
	public PrdCheckResDto buyNowChkPrdInfo(PrdPchInfoDto prdPchInfoDto) throws MyBusinessCheckException;
}
