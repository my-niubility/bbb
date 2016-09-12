package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.AccountBook;
import com.nbl.model.vo.AccountBookVo;
@Repository("accountBookDao")
public interface AccountBookDao {
    int deleteByPrimaryKey(Integer bookId);

    int insert(AccountBook record);

    int insertSelective(AccountBook record);

    AccountBook selectByPrimaryKey(Integer bookId);

    int updateByPrimaryKeySelective(AccountBook record);

    int updateByPrimaryKey(AccountBook record);
    
    /**
     * @param pageVO
     * @param accountBook
     * @return
     * @description:查询记账流水列表分页
     */
	public List<AccountBook> findListPage(@Param("pageVO")PageVO<AccountBookVo> pageVO,@Param("accountBookVO")AccountBookVo accountBookVo);
	/**
	 * @param AccountBook
	 * @return
	 * @description:查询记录数
	 */
	public int findListPageCount(@Param("accountBookVO")AccountBookVo accountBookVo);
	
	/**
	 * @param payId
	 * @return
	 * @description:根据payid查询记账接口流水
	 */
	public List<AccountBook> findAccountBookByPayId(String payId);

	/**
	 * @param accountBook
	 * @description:更新记账接口流水状态（包含bookState,bookId）
	 */
	public void updateAccountBookState(AccountBook accountBook);
	


    
}