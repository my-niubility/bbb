package com.nbl.services.accrule;

import com.nbl.model.AccountRule;

public interface DealAccService {

	/**
	 * 获取记账规则ID
	 * 
	 * @param optCode
	 * @param stepKey
	 * @return
	 */
	public String getRuleId(String optCode, String stepKey);

	/**
	 * 获取记账规则
	 * @param optCode
	 * @param stepKey
	 * @return
	 */
	public AccountRule getAccRule(String ruleId);
}
