package com.nbl.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.model.TradeBill;
@Repository
public interface TradeBillDao {
    int deleteByPrimaryKey(String id);

    int insert(TradeBill record);

    int insertSelective(TradeBill record);

    TradeBill selectByPrimaryKey(String id);
    
    TradeBill selectByGenCond(@Param("tb") TradeBill tradeBill);

    int updateByPrimaryKeySelective(TradeBill record);

    int updateByPrimaryKey(TradeBill record);
}