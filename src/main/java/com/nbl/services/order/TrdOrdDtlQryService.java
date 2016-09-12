package com.nbl.services.order;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.TrdOrdDtlQryDto;

public interface TrdOrdDtlQryService {
	/**
	 * 交易订单详情查询
	 * 
	 * @param trdOrdDtlQryDto
	 * @return
	 */
	public CommRespDto queryTrdOrdDtl(TrdOrdDtlQryDto trdOrdDtlQryDto);

}
