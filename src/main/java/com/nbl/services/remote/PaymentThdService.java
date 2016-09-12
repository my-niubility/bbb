package com.nbl.services.remote;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.business.dto.req.PayAlyInfoDto;

public interface PaymentThdService {

	public CommRespDto paymentApply(PayAlyInfoDto payAlyInfoDto);

}
