package com.nbl.services.remote.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.user.app.SerialRefProxyApp;
import com.nbl.service.user.dto.req.SerialRefDto;
import com.nbl.service.user.dto.res.SerialRefResultDto;

@Service("serialRefServiceBus")
public class SerialRefServiceImpl implements com.nbl.services.remote.SerialRefService {
	 @Resource
	private SerialRefProxyApp serialRefProxyApp;

	@Override
	public SerialRefResultDto updateSerialRef(SerialRefDto serialRefDto) throws MyBusinessCheckException {
		return serialRefProxyApp.updateSerialRef(serialRefDto);
	}

}
