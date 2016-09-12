package com.nbl.services.remote;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.RechgAlyThdInfoDto;

public interface RechargeThdService {
	public CommRespDto rechargeApply(RechgAlyThdInfoDto rechgAlyThdInfoDto);
}
