package com.nbl.services.withdraw.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.WithDrawDao;
import com.nbl.model.WithDraw;
import com.nbl.services.withdraw.NewWithdrawService;

@Service("newWithdrawService")
public class NewWithdrawServiceImpl implements NewWithdrawService {
	
	@Resource
	private WithDrawDao withdrawDao;
	
	@Override
	public List<WithDraw> pageListQueryWithDraw(PageVO<WithDraw> pageVO, WithDraw withdraw) {
		return withdrawDao.findListPage(pageVO, withdraw);
	}

	@Override
	public int pageCountQueryWithdraw(WithDraw withdraw) {
		return withdrawDao.findListPageCount(withdraw);
	}

	@Override
	public WithDraw withdrawDetail(String id) {
		return withdrawDao.selectById(id);
	}

}
