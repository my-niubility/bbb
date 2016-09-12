package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.ProductTransfer;
@Repository
public interface ProductTransferDao {
    int deleteByPrimaryKey(String id);

    int insert(ProductTransfer record);

    int insertSelective(ProductTransfer record);

    ProductTransfer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductTransfer record);

    int updateByPrimaryKey(ProductTransfer record);
}