package com.nbl.services.portion.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.AccountReverseDao;
import com.nbl.model.AccountReverse;
import com.nbl.services.portion.PositiveService;

@Service("positiveService")
public class PositiveServiceImpl implements PositiveService {
	
	@Resource
	private AccountReverseDao accountReverseDao;
	
	@Override
	public List<AccountReverse> pageListQueryPositive(PageVO<AccountReverse> pageVO, AccountReverse positive) {
		return accountReverseDao.findListPage(pageVO, positive);
	}

	@Override
	public int pageCountQueryPositive(AccountReverse positive) {
		return accountReverseDao.findListPageCount(positive);
	}

	@Override
	public AccountReverse positiveDetail(Integer reverseId) {
		return accountReverseDao.selectByReverseId(reverseId);
	}

}
