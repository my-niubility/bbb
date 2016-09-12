package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.BIOtSequenceKey;
@Repository
public interface BIOtSequenceDao {
    int deleteByPrimaryKey(BIOtSequenceKey key);

    int insert(BIOtSequenceKey record);

    int insertSelective(BIOtSequenceKey record);
}