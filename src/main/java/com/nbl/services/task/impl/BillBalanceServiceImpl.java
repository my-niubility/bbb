package com.nbl.services.task.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.BillBalanceDao;
import com.nbl.model.BillBalance;
import com.nbl.services.task.BillBalanceService;

/**
 * @author gcs
 * @createdate 2016年7月25日	
 * @version 1.0
 * 
 */
@Service("billBalanceService")
public class BillBalanceServiceImpl implements BillBalanceService {

	@Resource
	private BillBalanceDao billBalanceDao;
	
	/* (non-Javadoc)
	 * @see com.zlebank.services.task.BillBalanceService#pageListQueryBalance(com.zlebank.common.vo.PageVO, com.zlebank.model.BillBalance)
	 */
	@Override
	public List<BillBalance> pageListQueryBalance(PageVO<BillBalance> pageVO, BillBalance balance) {
		return billBalanceDao.findListPage(pageVO, balance);
	}

	/* (non-Javadoc)
	 * @see com.zlebank.services.task.BillBalanceService#pageCountQueryBalance(com.zlebank.model.BillBalance)
	 */
	@Override
	public int pageCountQueryBalance(BillBalance balance) {
		return billBalanceDao.findListPageCount(balance);
	}

	/* (non-Javadoc)
	 * @see com.zlebank.services.task.BillBalanceService#BalanceDetail(java.lang.String)
	 */
	@Override
	public BillBalance BalanceDetail(Integer id) {
		return billBalanceDao.selectByPrimaryKey(id);
	}
}
