package com.nbl.services.remote;

import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.user.dto.req.SerialRefDto;
import com.nbl.service.user.dto.res.SerialRefResultDto;

public interface SerialRefService {
	public SerialRefResultDto updateSerialRef(SerialRefDto serialRefDto) throws MyBusinessCheckException;
}
