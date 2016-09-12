package com.nbl.app.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.service.manager.app.TradeProcessApp;
import com.nbl.service.manager.dto.IncomeDto;
import com.nbl.service.manager.dto.PositionDto;
import com.nbl.service.manager.dto.PrdRepTrmDto;
import com.nbl.service.manager.dto.PrdRepTrmRespDto;
import com.nbl.service.manager.dto.RefundDto;
import com.nbl.services.trade.TradeProcService;

@Service("tradeProcessApp")
public class TradeProcessAppImpl implements TradeProcessApp {

	@Resource
	private TradeProcService tradeProcService;

	@Override
	public List<PrdRepTrmRespDto> queryPrdRepTrm(PageVO<PrdRepTrmDto> pageVO, PrdRepTrmDto prtb) {
		return tradeProcService.queryPrdRepTrm(pageVO, prtb);
	}

	@Override
	public int queryPrdRepTrmCount(PrdRepTrmDto prtb) {
		return tradeProcService.queryPrdRepTrmCount(prtb);
	}

	@Override
	public PrdRepTrmRespDto queryPrdRepTrmDetail(String id) {
		return tradeProcService.queryPrdRepTrmDetail(id);
	}

	@Override
	public String queryInvestorInfoCount(IncomeDto reqDto) {
		return tradeProcService.queryInvestorInfoCount(reqDto);
	}

	@Override
	public List<IncomeDto> queryInvestorInfo(PageVO<IncomeDto> pageVO, IncomeDto reqDto) {
		return tradeProcService.queryInvestorInfo(pageVO, reqDto);
	}

	@Override
	public List<RefundDto> queryRefundInfo(PageVO<RefundDto> pageVO, RefundDto prtb) {
		return tradeProcService.queryRefundInfo(pageVO, prtb);
	}

	@Override
	public int queryRefundInfoCount(RefundDto rb) {
		return tradeProcService.queryRefundInfoCount(rb);
	}

	@Override
	public RefundDto queryRefundDetail(String productId) {
		return tradeProcService.queryRefundDetail(productId);
	}

	@Override
	public List<PositionDto> queryInvestorInfo(PageVO<PositionDto> pageVO, PositionDto pb) {
		return tradeProcService.queryInvestorInfo(pageVO, pb);
	}

	@Override
	public int queryInvestorInfoCount(PositionDto pb) {
		return tradeProcService.queryInvestorInfoCount(pb);
	}

	@Override
	public boolean repayUpdate(String productId, String id) {
		return tradeProcService.repayUpdate(productId,id);
	}

}
