package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.BIPkSequenceKey;
@Repository
public interface BIPkSequenceDao {
    int deleteByPrimaryKey(BIPkSequenceKey key);

    int insert(BIPkSequenceKey record);

    int insertSelective(BIPkSequenceKey record);
}