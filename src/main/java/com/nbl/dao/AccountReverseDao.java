package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.AccountReverse;
@Repository
public interface AccountReverseDao {
    int deleteByPrimaryKey(Integer reverseId);

    int insert(AccountReverse record);

    int insertSelective(AccountReverse record);

    AccountReverse selectByPrimaryKey(Integer reverseId);

    int updateByPrimaryKeySelective(AccountReverse record);

    int updateByPrimaryKey(AccountReverse record);
    
    /**
	 * @param reverseId
	 * @return
	 * @description:根据交易ID查询详情
	 */
    AccountReverse selectByReverseId(Integer reverseId);
    
    /**
	 * @param pageVO
	 * @param AccountReverse
	 * @return
	 * @description:分页查询
	 */
	public List<AccountReverse> findListPage(@Param("pageVO")PageVO<AccountReverse> pageVO,@Param("positive")AccountReverse positive);
	
	/**
	 * @param positive
	 * @return
	 * @description:分页查询统计总数
	 */
	public int findListPageCount(@Param("positive")AccountReverse positive);
}