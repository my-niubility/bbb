package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.CheckMsgResult;
@Repository
public interface CheckMsgResultDao {
    int deleteByPrimaryKey(String id);

    int insert(CheckMsgResult record);

    int insertSelective(CheckMsgResult record);

    CheckMsgResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CheckMsgResult record);

    int updateByPrimaryKey(CheckMsgResult record);
}