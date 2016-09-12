package com.nbl.services.remote;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.WithdrawAlyThdInfoDto;

public interface WithdrawThdService {
	public CommRespDto withdrawApply(WithdrawAlyThdInfoDto wthdwAlyInfoDto);
}
