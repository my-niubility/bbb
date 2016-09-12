package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.BINpSequenceKey;
@Repository
public interface BINpSequenceDao {
    int deleteByPrimaryKey(BINpSequenceKey key);

    int insert(BINpSequenceKey record);

    int insertSelective(BINpSequenceKey record);
}