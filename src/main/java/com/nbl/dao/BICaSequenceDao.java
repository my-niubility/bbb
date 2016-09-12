package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.BICaSequenceKey;
@Repository
public interface BICaSequenceDao {
    int deleteByPrimaryKey(BICaSequenceKey key);

    int insert(BICaSequenceKey record);

    int insertSelective(BICaSequenceKey record);
}