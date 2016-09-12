package com.nbl.services.product.feature.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.dao.EnergyProductDao;
import com.nbl.model.EnergyProduct;
import com.nbl.services.product.feature.EnergyProductProxy;

@Service("energyProductProxy")
public class EnergyProductProxyImpl implements EnergyProductProxy {

	@Resource
	private EnergyProductDao energyProductDao;

	public EnergyProductProxyImpl() {
		super();
	}

	@Override
	public EnergyProduct selectProductFeatures(String productId) {
		return energyProductDao.selectByPrimaryKey(productId);
	}

}
