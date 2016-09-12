package com.nbl.services.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.TradeOrder;
import com.nbl.services.trade.TradeOrderService;

@Service("tradeOrderService")
public class TradeOrderServiceImpl implements TradeOrderService {
	
	@Resource
	private TradeOrderDao tradeOrderDao;
	
	@Override
	public List<TradeOrder> pageListQueryTradeOrder(PageVO<TradeOrder> pageVO, TradeOrder tradeOrder) {
		return tradeOrderDao.findListPage(pageVO, tradeOrder);
	}

	@Override
	public int pageCountQueryTradeOrder(TradeOrder tradeOrder) {
		return tradeOrderDao.findListPageCount(tradeOrder);
	}

	@Override
	public TradeOrder tradeOrderDetail(String id) {
		return tradeOrderDao.selectById(id);
	}

	@Override
	public String getOrderStatus(String id) {
		
		return tradeOrderDao.getStatus(id);
	}

}
