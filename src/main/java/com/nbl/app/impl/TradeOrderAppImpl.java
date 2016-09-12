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
import com.nbl.model.TradeOrder;
import com.nbl.service.manager.app.TradeOrderApp;
import com.nbl.service.manager.dto.TradeOrderReqDto;
import com.nbl.services.trade.TradeOrderService;

@Service("tradeOrderApp")
public class TradeOrderAppImpl implements TradeOrderApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(TradeOrderAppImpl.class);
	
	@Resource
	private TradeOrderService tradeOrderService;
	
	@Override
	public List<TradeOrderReqDto> pageListQueryTradeOrder(PageVO<TradeOrderReqDto> pageVO, TradeOrderReqDto reqDto) {
		PageVO<TradeOrder> pgVO = new PageVO<TradeOrder>();	
		TradeOrder tradeOrder = new TradeOrder();
		BeanUtils.copyProperties(reqDto, tradeOrder);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<TradeOrder> list = new ArrayList<TradeOrder>();
		list.add(tradeOrder);
		pgVO.setList(list);
		
		List<TradeOrder> tradeOrderList = tradeOrderService.pageListQueryTradeOrder(pgVO, tradeOrder);
		if(tradeOrderList !=null && tradeOrderList.size()>0){
			Logger.info("-------List<TradeOrder> pageListQueryTradeOrder---size----:{}:",tradeOrderList.size());
			List<TradeOrderReqDto> reqList = new ArrayList<TradeOrderReqDto>();
			Iterator<TradeOrder> it = tradeOrderList.iterator();
			while (it.hasNext()) {
				TradeOrderReqDto reqDto2 = new TradeOrderReqDto();
				TradeOrder cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	@Override
	public int pageCountQueryTradeOrder(TradeOrderReqDto reqDto) {
		TradeOrder TradeOrder = new TradeOrder();
		BeanUtils.copyProperties(reqDto, TradeOrder);
		return tradeOrderService.pageCountQueryTradeOrder(TradeOrder);
	}

	@Override
	public TradeOrderReqDto tradeOrderDetail(String id) {
		TradeOrder TradeOrder = tradeOrderService.tradeOrderDetail(id);
		TradeOrderReqDto reqDto = new TradeOrderReqDto();
		BeanUtils.copyProperties(TradeOrder, reqDto);
		return reqDto;
	}

	@Override
	public String getOrderStatus(String id) {
		return tradeOrderService.getOrderStatus(id);
	}

}
