package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.FinanceExtends;
@Repository
public interface FinanceExtendsDao {
    int deleteByPrimaryKey(String extendsId);

    int insert(FinanceExtends record);

    int insertSelective(FinanceExtends record);

    FinanceExtends selectByPrimaryKey(String extendsId);

    int updateByPrimaryKeySelective(FinanceExtends record);

    int updateByPrimaryKey(FinanceExtends record);
}