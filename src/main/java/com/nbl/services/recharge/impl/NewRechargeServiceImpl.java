package com.nbl.services.recharge.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.RechargeDao;
import com.nbl.model.Recharge;
import com.nbl.services.recharge.NewRechargeService;
@Service("newRechargeService")
public class NewRechargeServiceImpl implements NewRechargeService {
	
	@Resource
	private RechargeDao rechargeDao;
	
	@Override
	public List<Recharge> pageListQueryRecharge(PageVO<Recharge> pageVO, Recharge recharge) {
		return rechargeDao.findListPage(pageVO, recharge);
	}

	@Override
	public int pageCountQueryRecharge(Recharge recharge) {
		return rechargeDao.findListPageCount(recharge);
	}

	@Override
	public Recharge rechargeDetail(String id) {
		return rechargeDao.selectById(id);
	}

}
