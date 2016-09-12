package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.AccountRule;
@Repository
public interface AccountRuleDao {
    int deleteByPrimaryKey(String ruleId);

    int insert(AccountRule record);

    int insertSelective(AccountRule record);

    AccountRule selectByPrimaryKey(String ruleId);

    int updateByPrimaryKeySelective(AccountRule record);

    int updateByPrimaryKey(AccountRule record);
    
	/**
	 * @param ruleId
	 * @return
	 * @description:根据主键查询记账规则
	 */
	public AccountRule findAccountRuleByKey(String ruleId);
	
	/**
	 * @param accountType
	 * @return
	 * @description:根据记账类型查询记账规则
	 */
	public List<AccountRule> findAccountRuleByAccountType(String accountType);

	/**
	 * @param pageVO
	 * @param accountRule
	 * @return
	 * @description:查询记账规则
	 */
	public List<AccountRule> findAccountRuleList(@Param("pageVO")PageVO<AccountRule> pageVO, @Param("accountRule")AccountRule accountRule);

	/**
	 * @param accountRule
	 * @return
	 * @description:查询记录数
	 */
	public int getDealTypeCount(@Param("accountRule")AccountRule accountRule);
	
}