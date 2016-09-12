package com.nbl.services.parameter;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.AccountRule;

/**
 * @author gcs
 * @createdate 2016年7月26日	
 * @version 1.0
 * 对应busiess层的接口
 */

public interface AccountRuleService {
	/**
	 * @param pageVO
	 * @param reqDto
	 * @return
	 * @description:分页查询
	 */
	public List<AccountRule> pageListQueryAccRule(PageVO<AccountRule> pageVO,AccountRule accRule);
	
	/**
	 * @param accRule
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryAccRule(AccountRule accRule);
}
