package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.InterstSubsidy;
@Repository
public interface InterstSubsidyDao {
    int deleteByPrimaryKey(String paymentId);

    int insert(InterstSubsidy record);

    int insertSelective(InterstSubsidy record);

    InterstSubsidy selectByPrimaryKey(String paymentId);

    int updateByPrimaryKeySelective(InterstSubsidy record);

    int updateByPrimaryKey(InterstSubsidy record);
}