package com.nbl.services.trade;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.service.manager.dto.IncomeDto;
import com.nbl.service.manager.dto.PositionDto;
import com.nbl.service.manager.dto.PrdRepTrmDto;
import com.nbl.service.manager.dto.PrdRepTrmRespDto;
import com.nbl.service.manager.dto.RefundDto;

public interface TradeProcService {

	public List<PrdRepTrmRespDto> queryPrdRepTrm(PageVO<PrdRepTrmDto> pageVO, PrdRepTrmDto prtb);

	public int queryPrdRepTrmCount(PrdRepTrmDto prtb);

	public PrdRepTrmRespDto queryPrdRepTrmDetail(String id);

	public String queryInvestorInfoCount(IncomeDto reqDto);

	public List<IncomeDto> queryInvestorInfo(PageVO<IncomeDto> pageVO, IncomeDto reqDto);

	public List<RefundDto> queryRefundInfo(PageVO<RefundDto> pageVO, RefundDto prtb);

	public int queryRefundInfoCount(RefundDto rb);

	public RefundDto queryRefundDetail(String productId);

	public List<PositionDto> queryInvestorInfo(PageVO<PositionDto> pageVO, PositionDto pb);

	public int queryInvestorInfoCount(PositionDto pb);
	
	/**
	 * @param productId 
	 * @param id
	 * 还款更新所有数据状态
	 * */
	public boolean repayUpdate(String productId,String id);
}
