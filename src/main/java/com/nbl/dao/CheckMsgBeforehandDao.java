package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.CheckMsgBeforehand;
@Repository
public interface CheckMsgBeforehandDao {
    int deleteByPrimaryKey(String id);

    int insert(CheckMsgBeforehand record);

    int insertSelective(CheckMsgBeforehand record);

    CheckMsgBeforehand selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CheckMsgBeforehand record);

    int updateByPrimaryKey(CheckMsgBeforehand record);
}