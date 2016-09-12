package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.DealType;
import com.nbl.model.DealTypeKey;

@Repository
public interface DealTypeDao {
	int deleteByPrimaryKey(DealTypeKey key);

	int insert(DealType record);

	int insertSelective(DealType record);

	DealType selectByPrimaryKey(DealTypeKey key);

	int updateByPrimaryKeySelective(DealType record);

	int updateByPrimaryKey(DealType record);
	 /**
   	 * @param pageVO
   	 * @param deal
   	 * @return
   	 * @description:分页查询
   	 */
   	public List<DealType> findListPage(@Param("pageVO")PageVO<DealType> pageVO,@Param("deal")DealType deal);
   	
   	/**
   	 * @param balance
   	 * @return
   	 * @description:分页查询统计总数
   	 */
   	public int findListPageCount(@Param("deal")DealType deal);
}