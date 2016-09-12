package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.ProductNotice;
@Repository
public interface ProductNoticeDao {
    int deleteByPrimaryKey(String id);

    int insert(ProductNotice record);

    int insertSelective(ProductNotice record);

    ProductNotice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductNotice record);

    int updateByPrimaryKeyWithBLOBs(ProductNotice record);

    int updateByPrimaryKey(ProductNotice record);
}