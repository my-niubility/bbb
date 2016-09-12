
package com.nbl.services.parameter.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.AccountRuleDao;
import com.nbl.model.AccountRule;
import com.nbl.services.parameter.AccountRuleService;
/**
 * @author gcs
 * @createdate 2016年7月26日	
 * @version 1.0
 * 对应实现业务逻辑
 */

@Service("accountRuleService")
public class AccountRuleServiceImpl implements AccountRuleService {
	
	@Resource
	private AccountRuleDao accountRuleDao;

	/* (non-Javadoc)
	 * @see com.zlebank.services.parameter.AccountRuleService#pageListQueryAccRule(com.zlebank.common.vo.PageVO, com.zlebank.model.AccountRule)
	 */
	@Override
	public List<AccountRule> pageListQueryAccRule(PageVO<AccountRule> pageVO, AccountRule accRule) {
		return accountRuleDao.findAccountRuleList(pageVO, accRule);
	}

	/* (non-Javadoc)
	 * @see com.zlebank.services.parameter.AccountRuleService#pageCountQueryAccRule(com.zlebank.model.AccountRule)
	 */
	@Override
	public int pageCountQueryAccRule(AccountRule accRule) {
		return accountRuleDao.getDealTypeCount(accRule);
	}

}
