package com.nbl.services.parameter.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.DealTypeDao;
import com.nbl.model.DealType;
import com.nbl.services.parameter.DealService;

/**
 * @author gcs
 * @createdate 2016年7月26日	
 * @version 1.0
 * 实现对应接口方法
 */

@Service("dealService")
public class DealServiceImpl implements DealService {
	
	@Resource
	private DealTypeDao dealTypeDao;
	/* (non-Javadoc)
	 * @see com.zlebank.services.deal.DealService#pageListQueryDeal(com.zlebank.common.vo.PageVO, com.zlebank.model.DealType)
	 */
	@Override
	public List<DealType> pageListQueryDeal(PageVO<DealType> pageVO, DealType deal) {
		return dealTypeDao.findListPage(pageVO, deal);
	}

	/* (non-Javadoc)
	 * @see com.zlebank.services.deal.DealService#pageCountQueryDeal(com.zlebank.model.DealType)
	 */
	@Override
	public int pageCountQueryDeal(DealType deal) {
		return dealTypeDao.findListPageCount(deal);
	}

}
