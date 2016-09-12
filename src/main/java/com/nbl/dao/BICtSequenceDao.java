package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.BICtSequenceKey;
@Repository
public interface BICtSequenceDao {
    int deleteByPrimaryKey(BICtSequenceKey key);

    int insert(BICtSequenceKey record);

    int insertSelective(BICtSequenceKey record);
}