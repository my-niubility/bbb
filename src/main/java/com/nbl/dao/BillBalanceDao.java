package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.BillBalance;
@Repository
public interface BillBalanceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BillBalance record);

    int insertSelective(BillBalance record);

    BillBalance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillBalance record);

    int updateByPrimaryKey(BillBalance record);
    
       /**
   	 * @param pageVO
   	 * @param BillBalance
   	 * @return
   	 * @description:分页查询
   	 */
   	public List<BillBalance> findListPage(@Param("pageVO")PageVO<BillBalance> pageVO,@Param("balance")BillBalance balance);
   	
   	/**
   	 * @param balance
   	 * @return
   	 * @description:分页查询统计总数
   	 */
   	public int findListPageCount(@Param("balance")BillBalance balance);
}