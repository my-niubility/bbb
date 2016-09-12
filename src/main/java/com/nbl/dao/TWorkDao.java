package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.TWork;
@Repository
public interface TWorkDao {
    int deleteByPrimaryKey(String id);

    int insert(TWork record);

    int insertSelective(TWork record);

    TWork selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TWork record);

    int updateByPrimaryKey(TWork record);
    
    TWork getWork();
}