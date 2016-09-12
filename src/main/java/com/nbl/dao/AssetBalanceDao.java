package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.AssetBalance;
@Repository
public interface AssetBalanceDao {
    int deleteByPrimaryKey(String custId);

    int insert(AssetBalance record);

    int insertSelective(AssetBalance record);

    AssetBalance selectByPrimaryKey(String custId);

    int updateByPrimaryKeySelective(AssetBalance record);

    int updateByPrimaryKey(AssetBalance record);
}