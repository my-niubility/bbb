package com.nbl.dao;

import org.springframework.stereotype.Repository;

import com.nbl.model.ProductAudit;
@Repository
public interface ProductAuditDao {
    int deleteByPrimaryKey(String id);

    int insert(ProductAudit record);

    int insertSelective(ProductAudit record);

    ProductAudit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductAudit record);

    int updateByPrimaryKey(ProductAudit record);
    
}