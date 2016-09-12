package com.nbl.services.param.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.dao.GeneralParameterDao;
import com.nbl.services.param.ParamService;

@Service("paramService")
public class ParamServiceImpl implements ParamService {

	@Resource
	private GeneralParameterDao generalParameterDao;

	@Override
	public String getValue(String paramKey) {
		return generalParameterDao.getParamValueByKey(paramKey);
	}
	
}
