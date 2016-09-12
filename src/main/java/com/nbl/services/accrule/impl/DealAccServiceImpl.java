package com.nbl.services.accrule.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.dao.AccountRuleDao;
import com.nbl.dao.DealTypeDao;
import com.nbl.model.AccountRule;
import com.nbl.model.DealTypeKey;
import com.nbl.services.accrule.DealAccService;

@Service("dealAccService")
public class DealAccServiceImpl implements DealAccService {
	@Resource
	private AccountRuleDao accountRuleDao;
	@Resource
	private DealTypeDao dealTypeDao;

	@Override
	public String getRuleId(String optCode, String stepKey) {
		DealTypeKey pk = new DealTypeKey();
		pk.setOptCode(optCode);
		pk.setStepKey(stepKey);
		return dealTypeDao.selectByPrimaryKey(pk).getRuleId();
	}

	@Override
	public AccountRule getAccRule(String ruleId) {
		return accountRuleDao.findAccountRuleByKey(ruleId);
	}

}
