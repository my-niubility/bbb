
package com.nbl.app.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.model.AccountRule;
import com.nbl.service.manager.app.AccountRuleApp;
import com.nbl.service.manager.dto.AccountRuleDto;
import com.nbl.services.parameter.AccountRuleService;

/**
 * @author gcs
 * @createdate 2016年7月26日	
 * @version 1.0
 * busiess层实现记账规则接口
 */
@Service("accountRuleApp")
public class AccountRuleAppImpl implements AccountRuleApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(AccountRuleAppImpl.class);
	
	@Resource
	private AccountRuleService accountRuleService;
	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.AccountRuleApp#pageListQueryAccRule(com.zlebank.common.vo.PageVO, com.zlebank.service.manager.dto.AccountRuleDto)
	 */
	@Override
	public List<AccountRuleDto> pageListQueryAccRule(PageVO<AccountRuleDto> pageVO, AccountRuleDto reqDto) {
		PageVO<AccountRule> pgVO = new PageVO<AccountRule>();	
		AccountRule accRule = new AccountRule();
		BeanUtils.copyProperties(reqDto, accRule);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<AccountRule> list = new ArrayList<AccountRule>();
		list.add(accRule);
		pgVO.setList(list);
		
		List<AccountRule> accRuleList = accountRuleService.pageListQueryAccRule(pgVO, accRule);
		if(accRuleList !=null && accRuleList.size()>0){
			Logger.info("-------List<AccountRule> pageListQueryAccountRule---size----:{}:",accRuleList.size());
			List<AccountRuleDto> reqList = new ArrayList<AccountRuleDto>();
			Iterator<AccountRule> it = accRuleList.iterator();
			while (it.hasNext()) {
				AccountRuleDto reqDto2 = new AccountRuleDto();
				AccountRule cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.AccountRuleApp#pageCountQueryAccRule(com.zlebank.service.manager.dto.AccountRuleDto)
	 */
	@Override
	public int pageCountQueryAccRule(AccountRuleDto reqDto) {
		AccountRule accRule = new AccountRule();
		BeanUtils.copyProperties(reqDto, accRule);
		return accountRuleService.pageCountQueryAccRule(accRule);
	}


}
