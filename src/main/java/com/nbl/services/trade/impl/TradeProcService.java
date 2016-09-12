package com.nbl.services.trade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.PositionDao;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.ProductRepayTermsDao;
import com.nbl.dao.TIncomeDao;
import com.nbl.model.Position;
import com.nbl.model.ProductCommon;
import com.nbl.model.ProductRepayTerms;
import com.nbl.model.TIncome;
import com.nbl.model.vo.IncomeVo;
import com.nbl.model.vo.PositionVo;
import com.nbl.model.vo.PrdRefundVo;
import com.nbl.model.vo.PrdRepTrmVo;
import com.nbl.service.business.constant.PositionCustType;
import com.nbl.service.business.constant.PositionHoldStatus;
import com.nbl.service.business.constant.ProductRepayIsFinish;
import com.nbl.service.business.constant.ProductStatus;
import com.nbl.service.manager.dto.IncomeDto;
import com.nbl.service.manager.dto.PositionDto;
import com.nbl.service.manager.dto.PrdRepTrmDto;
import com.nbl.service.manager.dto.PrdRepTrmRespDto;
import com.nbl.service.manager.dto.RefundDto;
import com.nbl.util.AmtParseUtil;

@Service("tradeProcService")
public class TradeProcService implements com.nbl.services.trade.TradeProcService {
	private final static Logger logger = LoggerFactory.getLogger(TradeProcService.class);

	@Resource
	private ProductRepayTermsDao productRepayTermsDao;
	@Resource
	private TIncomeDao iIncomeDao;
	@Resource
	private ProductCommonDao productCommonDao;
	@Resource
	private PositionDao positionDao;

	@Override
	public List<PrdRepTrmRespDto> queryPrdRepTrm(PageVO<PrdRepTrmDto> pageVO, PrdRepTrmDto prtb) {

		PrdRepTrmVo prt = new PrdRepTrmVo();
		BeanUtils.copyProperties(prtb, prt);
		if (prtb.getAmtStr() != null) {
			prt.setAmt(Long.parseLong(prtb.getAmtStr()));
		}
		if (prtb.getRepayInterestStr() != null) {
			prt.setRepayInterest(Long.parseLong(prtb.getRepayInterestStr()));
		}
		if (prtb.getRepayAmtStr() != null) {
			prt.setRepayAmt(Long.parseLong(prtb.getRepayAmtStr()));
		}
		if (prtb.getIsFinishCond() != null && prtb.getIsFinishCond().size() > 0) {
			prt.setIsFinish(prtb.getIsFinishCond());
		}

		List<ProductRepayTerms> resultList = productRepayTermsDao.selectByCond(pageVO, prt);
		List<PrdRepTrmRespDto> retList = null;
		if (resultList != null && resultList.size() > 0) {
			logger.info("---------size---------:{}", resultList.size());
			retList = new ArrayList<PrdRepTrmRespDto>();

			for (ProductRepayTerms dto : resultList) {
				PrdRepTrmRespDto cbean = new PrdRepTrmRespDto();
				BeanUtils.copyProperties(dto, cbean);
				cbean.setAmtStr(AmtParseUtil.longToStrAmt(dto.getAmt()));
				cbean.setRepayAmtStr(AmtParseUtil.longToStrAmt(dto.getRepayAmt()));
				cbean.setRepayInterestStr(AmtParseUtil.longToStrAmt(dto.getRepayInterest()));
				retList.add(cbean);
			}
		}
		return retList;
	}

	@Override
	public int queryPrdRepTrmCount(PrdRepTrmDto prtb) {
		PrdRepTrmVo prt = new PrdRepTrmVo();
		BeanUtils.copyProperties(prtb, prt);
		if (prtb.getAmtStr() != null) {
			prt.setAmt(Long.parseLong(prtb.getAmtStr()));
		}
		if (prtb.getRepayInterestStr() != null) {
			prt.setRepayInterest(Long.parseLong(prtb.getRepayInterestStr()));
		}
		if (prtb.getRepayAmtStr() != null) {
			prt.setRepayAmt(Long.parseLong(prtb.getRepayAmtStr()));
		}

		return productRepayTermsDao.selectByCondCount(prt);
	}

	@Override
	public PrdRepTrmRespDto queryPrdRepTrmDetail(String id) {
		PrdRepTrmRespDto prtd = new PrdRepTrmRespDto();
		ProductRepayTerms prt = productRepayTermsDao.selectByPrimaryKey(id);
		BeanUtils.copyProperties(prt, prtd);
		prtd.setAmtStr(AmtParseUtil.longToStrAmt(prt.getAmt()));
		prtd.setRepayAmtStr(AmtParseUtil.longToStrAmt(prt.getRepayAmt()));
		prtd.setRepayInterestStr(AmtParseUtil.longToStrAmt(prt.getRepayInterest()));
		return prtd;
	}

	@Override
	public String queryInvestorInfoCount(IncomeDto reqDto) {
		IncomeVo vo = new IncomeVo();
		BeanUtils.copyProperties(reqDto, vo);
		int count = iIncomeDao.findIncomeCount(vo);
		return Integer.toString(count);
	}

	@Override
	public List<IncomeDto> queryInvestorInfo(PageVO<IncomeDto> pageVO, IncomeDto reqDto) {
		IncomeVo incomeVo = new IncomeVo();
		BeanUtils.copyProperties(reqDto, incomeVo);
		PageVO<IncomeVo> newPageVO = new PageVO<IncomeVo>();
		BeanUtils.copyProperties(pageVO, newPageVO);
		List<TIncome> incomes = iIncomeDao.findIncomes(newPageVO, incomeVo);

		List<IncomeDto> retList = null;
		if (incomes != null && incomes.size() > 0) {
			logger.info("---------size---------:{}", incomes.size());
			retList = new ArrayList<IncomeDto>();

			for (TIncome dto : incomes) {
				IncomeDto cbean = new IncomeDto();
				BeanUtils.copyProperties(dto, cbean);
				cbean.setAmtStr(AmtParseUtil.longToStrAmt(dto.getAmt()));
				cbean.setCapitalStr(AmtParseUtil.longToStrAmt(dto.getCapital()));
				cbean.setEarningStr(AmtParseUtil.longToStrAmt(dto.getEarning()));
				retList.add(cbean);
			}
		}
		return retList;
	}

	@Override
	public List<RefundDto> queryRefundInfo(PageVO<RefundDto> pageVO, RefundDto prtb) {

		PageVO<ProductCommon> newPageVO = new PageVO<ProductCommon>();
		BeanUtils.copyProperties(pageVO, newPageVO);
		PrdRefundVo prdRefundVo = new PrdRefundVo();
		BeanUtils.copyProperties(prtb, prdRefundVo);

		List<ProductCommon> resultList = productCommonDao.selectByRefundCond(newPageVO, prdRefundVo);

		List<RefundDto> retList = null;
		if (resultList != null && resultList.size() > 0) {
			logger.info("---------size---------:{}", resultList.size());
			retList = new ArrayList<RefundDto>();

			for (ProductCommon dto : resultList) {
				RefundDto cbean = new RefundDto();
				BeanUtils.copyProperties(dto, cbean);
				cbean.setUnitCostStr(AmtParseUtil.longToStrAmt(dto.getUnitCost()));
				retList.add(cbean);
			}
		}
		return retList;
	}

	@Override
	public int queryRefundInfoCount(RefundDto rb) {
		PrdRefundVo vo = new PrdRefundVo();
		BeanUtils.copyProperties(rb, vo);
		int count = productCommonDao.selectByRefundCondCount(vo);
		return count;
	}

	@Override
	public RefundDto queryRefundDetail(String productId) {
		ProductCommon prdCom = productCommonDao.selectByPrimaryKey(productId);
		RefundDto dto = new RefundDto();
		BeanUtils.copyProperties(prdCom, dto);
		return dto;
	}

	@Override
	public List<PositionDto> queryInvestorInfo(PageVO<PositionDto> pageVO, PositionDto pb) {
		PositionVo positionVo = new PositionVo();
		BeanUtils.copyProperties(pb, positionVo);
		PageVO<PositionVo> newPageVO = new PageVO<PositionVo>();
		BeanUtils.copyProperties(pageVO, newPageVO);

		List<Position> resultList = positionDao.findListPage(newPageVO, positionVo);
		List<PositionDto> retList = null;
		if (resultList != null && resultList.size() > 0) {
			logger.info("---------size---------:{}", resultList.size());
			retList = new ArrayList<PositionDto>();

			for (Position dto : resultList) {
				PositionDto cbean = new PositionDto();
				BeanUtils.copyProperties(dto, cbean);
				// cbean.setAmtStr(AmtParseUtil.longToStrAmt(dto.getAmt()));
				// cbean.setRepayAmtStr(AmtParseUtil.longToStrAmt(dto.getRepayAmt()));
				// cbean.setRepayInterestStr(AmtParseUtil.longToStrAmt(dto.getRepayInterest()));
				retList.add(cbean);
			}
		}
		return retList;
	}

	@Override
	public int queryInvestorInfoCount(PositionDto pb) {
		PositionVo positionVo = new PositionVo();
		BeanUtils.copyProperties(pb, positionVo);
		int count = positionDao.getPositionCount(positionVo);
		return count;
	}

	@Override
	@Transactional
	public boolean repayUpdate(String productId, String id) {
		int num = 0;
		//1、修改产品状态
		ProductCommon prdCom = productCommonDao.selectByPrimaryKey(productId);
		prdCom.setProductStatus(ProductStatus.PRODUCT_STATUS09.getValue());//把产品状态改为  09 已结束状态
		num = productCommonDao.updateByPrimaryKey(prdCom);
		//2、修改期次表  是否完成状态
		ProductRepayTerms prdRe = productRepayTermsDao.selectByPrimaryKey(id);
		prdRe.setIsFinish(ProductRepayIsFinish.FINISH.getValue());
		num = productRepayTermsDao.updateByPrimaryKey(prdRe);
		//3、持仓表  修改每个人的持有状态为 05已还款
		// 得到持仓人信息
		PositionVo pv = new PositionVo();
		pv.setProductId(productId);
		pv.setPositionCustType("01");
		List<Position> allcPositions=positionDao.findPositionList(pv);
		for (Position custTPosition : allcPositions) {
			custTPosition.setHoldStatus(PositionHoldStatus.ALREADYREPAYMENT.getValue());//持有状态为 05 已还款
			custTPosition.setPossessPortion(0L);//现有份额为0份
			num = positionDao.updateByPrimaryKey(custTPosition);
		}
		//4、投资收入表  
		// 得到该产品
		IncomeVo vo = new IncomeVo();
		vo.setProjectId(productId);
		List<TIncome> allIncome = iIncomeDao.getTIncomes(vo);
		for (TIncome tIncome : allIncome) {
			tIncome.setIsFinish("1");//还款状态   1 已完成
			tIncome.setEarning(tIncome.getExpectEarning());//设置实际收益利息  为   预期收益利息
			tIncome.setAnnuRate(tIncome.getExpectAnnuRate());//设置实际年化利率  为  预期年化利率
			num = iIncomeDao.updateByPrimaryKey(tIncome);
		}
		if(num > 0){
			return true;
		}else{
			return false;
		}
	}

}
