package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.RentRegister;
@Repository
public interface RentRegisterDao {
    int deleteByPrimaryKey(String rentId);

    int insert(RentRegister record);

    int insertSelective(RentRegister record);

    RentRegister selectByPrimaryKey(String rentId);

    int updateByPrimaryKeySelective(RentRegister record);

    int updateByPrimaryKey(RentRegister record);
}