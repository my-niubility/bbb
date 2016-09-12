package com.nbl.dao;

import com.nbl.model.EnergyProduct;

public interface EnergyProductDao {
	int deleteByPrimaryKey(String productId);

	int insert(EnergyProduct record);

	int insertSelective(EnergyProduct record);

	EnergyProduct selectByPrimaryKey(String productId);

	int updateByPrimaryKeySelective(EnergyProduct record);

	int updateByPrimaryKey(EnergyProduct record);
}